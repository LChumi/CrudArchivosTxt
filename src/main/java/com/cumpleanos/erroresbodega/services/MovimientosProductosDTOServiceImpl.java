/*
 * Copyright (c) 2024.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
 */

package com.cumpleanos.erroresbodega.services;

import com.cumpleanos.erroresbodega.config.RutasConfig;
import com.cumpleanos.erroresbodega.models.storage.MovimientosProductosDTO;
import com.cumpleanos.erroresbodega.models.storage.ProductoDTO;
import com.cumpleanos.erroresbodega.utils.MovimientosUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class MovimientosProductosDTOServiceImpl{

    private final MovimientosUtils movimientosNarancay;
    private final MovimientosUtils movimientosZhucay;

    @Autowired
    public MovimientosProductosDTOServiceImpl(RutasConfig rutasConfig){
        movimientosNarancay = new MovimientosUtils(rutasConfig.getRutaMovimientosNarancay());
        movimientosZhucay = new MovimientosUtils(rutasConfig.getRutaMovimientosZhucay());
    }

    public List<MovimientosProductosDTO> listarNarancay() throws IOException{
        return movimientosNarancay.listar();
    }
    public List<MovimientosProductosDTO> listarZhucay() throws IOException{
        return movimientosZhucay.listar();
    }

    public MovimientosProductosDTO buscarMovimientosNarancay(Long id, String detalle ) throws Exception{
        return movimientosNarancay.getMovimiento(id, detalle);
    }
    public MovimientosProductosDTO buscarMovimientosZhucay(Long id, String detalle ) throws Exception{
        return movimientosZhucay.getMovimiento(id, detalle);
    }

    public MovimientosProductosDTO guardarNarancay(MovimientosProductosDTO dto) throws IOException{
        return movimientosNarancay.guardar(dto);
    }
    public MovimientosProductosDTO guardarZhucay(MovimientosProductosDTO dto) throws IOException{
        return movimientosZhucay.guardar(dto);
    }

    public MovimientosProductosDTO agregarProductoNarancay(Long id, String detalle, ProductoDTO productoDTO) throws IOException{
        return movimientosNarancay.editarMovimiento(id, detalle, productoDTO);
    }
    public MovimientosProductosDTO agregarProductoZhucay(Long id, String detalle, ProductoDTO productoDTO) throws IOException{
        return movimientosZhucay.editarMovimiento(id, detalle, productoDTO);
    }

    public ByteArrayInputStream exportarExcelNarancay(MovimientosProductosDTO movimiento) throws IOException{
        return movimientosNarancay.exportarExcel(movimiento);
    }
    public ByteArrayInputStream exportarExcelZhucay(MovimientosProductosDTO movimiento) throws IOException{
        return movimientosZhucay.exportarExcel(movimiento);
    }

}
