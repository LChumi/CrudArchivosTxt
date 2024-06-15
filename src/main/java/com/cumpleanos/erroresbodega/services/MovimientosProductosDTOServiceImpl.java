/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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

    public MovimientosProductosDTO agregarProductoNarancay(Long id, String detalle, ProductoDTO productoDTO) throws IOException {
        try {
            Future<MovimientosProductosDTO> future = movimientosNarancay.editarMovimiento(id, detalle, productoDTO);
            return future.get(); // Espera hasta que el Future esté completado y devuelve el resultado
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restores the interrupted status
            throw new IOException("El hilo fue interrumpido", e);
        } catch (ExecutionException e) {
            throw new IOException("Error al agregar el producto", e.getCause());
        }
    }
    public MovimientosProductosDTO agregarProductoZhucay(Long id, String detalle, ProductoDTO productoDTO) throws IOException{
        try {
            Future<MovimientosProductosDTO> future = movimientosZhucay.editarMovimiento(id, detalle, productoDTO);
            return future.get();
        }catch (InterruptedException e){
            Thread.currentThread().interrupt(); // Restores the interrupted status
            throw new IOException("El hilo fue interrumpido", e);
        }catch (ExecutionException e){
            throw new IOException("Error al agregar el producto", e.getCause());
        }
    }

    public ByteArrayInputStream exportarExcelNarancay(MovimientosProductosDTO movimiento) throws IOException{
        return movimientosNarancay.exportarExcel(movimiento);
    }
    public ByteArrayInputStream exportarExcelZhucay(MovimientosProductosDTO movimiento) throws IOException{
        return movimientosZhucay.exportarExcel(movimiento);
    }

    public MovimientosProductosDTO elimiarProductoNarancay(Long id, String detalle, ProductoDTO productoDTO) throws IOException{
        return movimientosNarancay.editarMovimientoEliminar(id, detalle, productoDTO);
    }
    public MovimientosProductosDTO eliminarProductoZhucay(Long id, String detalle, ProductoDTO productoDTO) throws IOException{
        return movimientosZhucay.editarMovimientoEliminar(id, detalle, productoDTO);
    }

}
