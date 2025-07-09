/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.services;

import com.cumpleanos.erroresbodega.models.ProductoView;
import com.cumpleanos.erroresbodega.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.cumpleanos.erroresbodega.models.specification.ProductoViewSpecifications.matchByBodegaAndProIdOrProId1;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService{

    private final  ProductoRepository productoRepository;

    @Override
    public ProductoView getByProIdOrProId1(String data) {
        Long bodegaDefecto= 10000580L;
        List<ProductoView> productoViews =productoRepository.getByProIdOrPro_id1(bodegaDefecto,data);
        return productoViews.isEmpty() ? null: productoViews.get(0);
    }

    @Override
    public ProductoView porBarraOItem(Long bodega, String data) {
        List<ProductoView> producto=productoRepository.findByProIdAndBodCodigo(bodega, data);
        return producto.isEmpty() ? null : producto.get(0);
    }

    @Override
    public ProductoView porBarraEItem(Long bodega, String data, String item) {
        List<ProductoView> producto=productoRepository.findByProIdAndProId1AndBodCodigo(data, item, bodega);
        return producto.isEmpty() ? null : producto.get(0);
    }

    @Override
    public String getMatches(Long bodega, String data, String item) {
        String novedad = "";
        List<ProductoView> coincidencias= productoRepository.findAll(
                matchByBodegaAndProIdOrProId1(bodega, data, item)
        );

        // Filtrar barra y obtener nombre
        Optional<ProductoView> productoBarra = coincidencias.stream()
                .filter(p -> p.getProId().equals(data))
                .findFirst();

        // Filtrar item y obtener nombre
        Optional<ProductoView> productoItem = coincidencias.stream()
                .filter(p -> p.getProId1().equals(item))
                .findFirst();

        boolean barraExiste = productoBarra.isPresent();
        boolean itemExiste = productoItem.isPresent();
        boolean itemExisteConPrefijoDiferente = coincidencias.stream()
                .anyMatch(p -> normalizarItems(p.getProId1()).equals(normalizarItems(item)));

        if (barraExiste && itemExiste) {
            novedad = "REPOSICION";
        } else if (itemExisteConPrefijoDiferente) {
            novedad = "ITEM CAMBIA DE PREFIJO (EP/IP) (REPOSICION)";
        } else if (!barraExiste && itemExiste) {
            String nombreProducto = productoItem.map(ProductoView::getNombre).orElse("NOMBRE NO DISPONIBLE");
            novedad = "ITEM EXISTE CON OTRA BARRA: " + nombreProducto;
        } else if (barraExiste && !itemExiste) {
            String nombreProducto = productoBarra.map(ProductoView::getNombre).orElse("NOMBRE NO DISPONIBLE");
            novedad = "LA BARRA SE ENCUENTRA REGISTRADA EN OTRO ITEM: " + nombreProducto;
        } else {
            novedad = "NUEVO";
        }

        return novedad;
    }


    private String normalizarItems(String item){
        return item.replaceFirst("^(EP|IC)", "");
    }
}
