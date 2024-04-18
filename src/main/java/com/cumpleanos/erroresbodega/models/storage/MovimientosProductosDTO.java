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
    private String detalle;
    private String usuario;
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
        // Verificar si el producto ya existe en la lista
        for (ProductoDTO producto : productos){
            if(producto.getBarra().equals(productoNuevo.getBarra())){
                // Si la cantidad no se especifica, simplemente aumentar en 1
                if ( productoNuevo.getCantidad() <= 0) {
                    producto.setCantidad(producto.getCantidad() + 1);
                } else {
                    // Si se especifica una cantidad, sumarla a la cantidad existente
                    producto.setCantidad(producto.getCantidad() + productoNuevo.getCantidad());
                }
                return;
            }
        }
        // Si el producto no existe en la lista, agregarlo con la cantidad especificada o 1 por defecto
        if ( productoNuevo.getCantidad() <= 0) {
            productoNuevo.setCantidad(1); // Establecer la cantidad predeterminada como 1 si no se especifica
        }
        productoNuevo.generarNuevoId();
        productos.add(productoNuevo);
    }

    public void eliminarProducto(ProductoDTO productoEliminar){
        productos.removeIf(producto -> producto.getBarra().equals(productoEliminar.getBarra()));
    }

}
