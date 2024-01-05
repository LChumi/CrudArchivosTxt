package com.cumpleanos.erroresbodega.services;

import com.cumpleanos.erroresbodega.models.Producto;
import com.cumpleanos.erroresbodega.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService{

    @Autowired
    private ProductoRepository productoRepository;
    @Override
    public Producto getByProIdOrProId1(String data) {
        Long bodegaDefecto= 10000580L;
        List<Producto> productos=productoRepository.getByPro_idOrPro_id1(bodegaDefecto,data);
        return productos.isEmpty() ? null:productos.get(0);
    }
}
