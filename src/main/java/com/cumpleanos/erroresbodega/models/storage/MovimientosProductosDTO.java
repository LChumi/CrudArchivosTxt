/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
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

    public void generarNuevoId(){
        this.id = idGenerator.incrementAndGet();
    }

    @Override
    public int compareTo(MovimientosProductosDTO movimiento) {
        LocalDate fechaActual = LocalDate.parse(this.fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate fechaMovimiento = LocalDate.parse(movimiento.getFecha(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return fechaMovimiento.compareTo(fechaActual);
    }

    public void agregarProducto(ProductoDTO productoNuevo){
        // Verificar si el producto ya existe en la lista
        for (ProductoDTO producto : productos){
            if(producto.getBarra().equals(productoNuevo.getBarra())){
                // Si la cantidad no se especifica, simplemente aumentar en 1
                if ( productoNuevo.getCantidadDigitada() <= 0) {
                    producto.setCantidadDigitada(producto.getCantidadDigitada() + 1);
                } else {
                    // Si se especifica una cantidad, sumarla a la cantidad existente
                    producto.setCantidadDigitada(producto.getCantidadDigitada() + productoNuevo.getCantidadDigitada());
                }
                if (productoNuevo.getNovedad() !=null && !productoNuevo.getNovedad().isEmpty()){
                    producto.setNovedad(productoNuevo.getNovedad());
                }
                return;
            }
        }
        productoNuevo.generarNuevoId();
        productoNuevo.setCantidadDigitada(0);
        productos.add(productoNuevo);
    }

    public void eliminarProducto(ProductoDTO productoEliminar){
        productos.removeIf(producto -> producto.getBarra().equals(productoEliminar.getBarra()));
    }

}
