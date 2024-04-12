/*
 * Copyright (c) 2024.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
 */

package com.cumpleanos.erroresbodega.models.storage;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Setter
@Getter
public class MovimientosProductosDTO implements Serializable, Comparable<MovimientosProductosDTO> {

    private static final AtomicLong idGenerator = new AtomicLong(0);

    @Setter(AccessLevel.NONE)
    private Long id;
    private String fecha;
    private String nombre;
    private List<ProductoDTO> productos;

    public Long generarNuevoId(){
        return this.id=idGenerator.incrementAndGet();
    }

    @Override
    public int compareTo(MovimientosProductosDTO o) {
        LocalDate fechaActual = LocalDate.parse(this.fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate fechaMovimiento = LocalDate.parse(this.fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return fechaMovimiento.compareTo(fechaActual);
    }

    public void agregarProducto(ProductoDTO productoNuevo){
        for (ProductoDTO producto: productos){
            if(producto.getBarra().equals(productoNuevo.getBarra())){
                producto.setCantidad(producto.getCantidad()+1);
                return;
            }
        }
        productoNuevo.generarNuevoId();
        productoNuevo.setCantidad(1);
        productos.add(productoNuevo);
    }
}
