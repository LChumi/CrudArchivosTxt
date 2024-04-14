/*
 * Copyright (c) 2024.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
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
