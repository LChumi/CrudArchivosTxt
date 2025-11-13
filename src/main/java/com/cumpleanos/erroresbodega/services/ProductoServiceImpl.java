/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.services;

import com.cumpleanos.erroresbodega.models.Producto;
import com.cumpleanos.erroresbodega.models.ProductoView;
import com.cumpleanos.erroresbodega.repository.ProductoRepository;
import com.cumpleanos.erroresbodega.repository.ProductoViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.cumpleanos.erroresbodega.models.specification.ProductoSpecifications.getMatchesByEmpresaAndProIdOrProId1;
import static com.cumpleanos.erroresbodega.models.specification.ProductoViewSpecifications.matchByBodegaAndProIdOrProId1;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService{

    private final ProductoViewRepository productoViewRepository;
    private final ProductoRepository productoRepository;

    @Override
    public ProductoView getByProIdOrProId1(String data) {
        Long bodegaDefecto= 10000580L;
        List<ProductoView> productoViews = productoViewRepository.getByProIdOrPro_id1(bodegaDefecto,data);
        return productoViews.isEmpty() ? null: productoViews.get(0);
    }

    @Override
    public ProductoView porBarraOItem(Long bodega, String data) {
        List<ProductoView> producto= productoViewRepository.findByProIdAndBodCodigo(bodega, data);
        return producto.isEmpty() ? null : producto.get(0);
    }

    @Override
    public ProductoView porBarraEItem(Long bodega, String data, String item) {
        List<ProductoView> producto= productoViewRepository.findByProIdAndProId1AndBodCodigo(data, item, bodega);
        return producto.isEmpty() ? null : producto.get(0);
    }

    @Override
    public String getMatches(Long bodega, String data, String item) {
        String novedad = "";
        List<ProductoView> coincidencias= productoViewRepository.findAll(
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

    @Override
    public String existInEmpresas(Long empresa, String barra , String item) {

        if (empresa == null || barra == null || item == null) {
            throw new IllegalArgumentException("Empresa, barra y item no pueden ser nulos");
        }

        String novedad = "";
        List<Producto> coincidencias = productoRepository.findAll(
                getMatchesByEmpresaAndProIdOrProId1(empresa, barra, item)
        );

        Map<String, Producto> indexByProId = coincidencias.stream()
                .filter(p -> p.getProId() != null)
                .collect(Collectors.toMap(Producto::getProId, Function.identity(), (a, b) -> a));

        Map<String, Producto> indexByProId1 = coincidencias.stream()
                .filter(p -> p.getProId1() != null)
                .collect(Collectors.toMap(Producto::getProId1, Function.identity(), (a, b) -> a));

        Producto productoBarra = indexByProId.get(barra);
        Producto productoItem = indexByProId1.get(item);

        boolean barraExist = productoBarra != null;
        boolean itemExist = productoItem != null;

        if (barraExist && itemExist) {
            novedad = "REPOSICIÓN";
        } else if (!barraExist && itemExist) {
            String nombreProducto = productoItem.getProNombre() != null
                    ? productoItem.getProNombre()
                    : "NOMBRE NO DISPONIBLE";
            novedad = "ITEM EXISTE CON OTRA BARRA: " + nombreProducto;
        } else if (barraExist && !itemExist) {
            String nombreProducto = productoBarra.getProNombre() != null
                    ? productoBarra.getProNombre()
                    : "NOMBRE NO DISPONIBLE";
            novedad = "LA BARRA SE ENCUENTRA REGISTRADA EN OTRO ITEM: " + nombreProducto;
        } else {
            novedad = "NUEVO";
        }
        return novedad;
    }

}
