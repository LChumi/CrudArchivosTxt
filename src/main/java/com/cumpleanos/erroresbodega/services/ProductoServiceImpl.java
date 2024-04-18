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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService{

    @Autowired
    private ProductoRepository productoRepository;
    @Override
    public ProductoView getByProIdOrProId1(String data) {
        Long bodegaDefecto= 10000580L;
        List<ProductoView> productoViews =productoRepository.getByPro_idOrPro_id1(bodegaDefecto,data);
        return productoViews.isEmpty() ? null: productoViews.get(0);
    }

    @Override
    public ProductoView porBarraOItem(Long bodega, String data) {
        List<ProductoView> producto=productoRepository.findByPro_idAndBod_codigo(bodega, data);
        return producto.isEmpty() ? null : producto.get(0);
    }
}
