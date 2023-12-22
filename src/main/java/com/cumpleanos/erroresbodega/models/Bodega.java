package com.cumpleanos.erroresbodega.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "BODEGA")
public class Bodega {

    @Id
    @Column(name = "BOD_CODIGO")
    private Long bod_codigo;

    @Column(name = "BOD_EMPRESA")
    private Long bod_empresa;

    @Column(name = "BOD_NOMBRE")
    private String bod_nombre;

    @Column(name = "BOD_ID")
    private String bod_id;


}
