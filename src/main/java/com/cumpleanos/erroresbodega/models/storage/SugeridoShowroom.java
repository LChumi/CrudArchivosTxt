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

@Getter
@Setter
public class SugeridoShowroom implements Serializable, Comparable<SugeridoShowroom> {

    private static final AtomicLong idGenerator = new AtomicLong(0);

    @Setter(AccessLevel.NONE)
    private Long id;
    private String fecha;
    private String detalle;
    private String usuario;
    private List<ProductoShowroom> productoShowrooms;

    public void generarNuevoId(){
        this.id = idGenerator.incrementAndGet();
    }

    @Override
    public int compareTo(SugeridoShowroom o) {
        LocalDate fechaActual = LocalDate.parse(this.fecha,DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate fechaSugerido = LocalDate.parse(o.getFecha(),DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return fechaSugerido.compareTo(fechaActual);
    }

    public void agregarProducto(ProductoShowroom productoNuevo){
        for (ProductoShowroom producto : productoShowrooms){
            if (producto.getBarra().equals(productoNuevo.getBarra())){
                if (productoNuevo.getCantidad() <= 0) {
                    producto.setCantidad(producto.getCantidad()+1);
                }else {
                    producto.setCantidad(producto.getCantidad()+ productoNuevo.getCantidad());
                }
                if (productoNuevo.getObservacion()!=null){
                    producto.setObservacion(productoNuevo.getObservacion());
                }
                return;
            }
        }
        if (productoNuevo.getCantidad() <=0){
            productoNuevo.setCantidad(1);
        }
        productoNuevo.generarNuevoId();
        productoShowrooms.add(productoNuevo);
    }

    public void eliminarProducto(ProductoShowroom productoEliminar){
        productoShowrooms.removeIf(producto -> producto.getBarra().equals(productoEliminar.getBarra()));
    }
}
