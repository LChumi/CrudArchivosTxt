/*
 * Copyright (c) 2024.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
 */

package com.cumpleanos.erroresbodega.controller;

import com.cumpleanos.erroresbodega.models.ProductoView;
import com.cumpleanos.erroresbodega.services.FunctionOracleService;
import com.cumpleanos.erroresbodega.services.ProductoService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producto")
@CrossOrigin("*")
@Slf4j
public class ProductoController{

    private final ProductoService productoService;
    private final FunctionOracleService functionService;

    @Autowired
    public ProductoController(ProductoService productoService, FunctionOracleService service){
        this.productoService = productoService;
        this.functionService = service;
    }

    private static final Logger console= LoggerFactory.getLogger(ProductoController.class);

    @GetMapping("/BuscarProducto")
    public ResponseEntity<ProductoView> bucarProducto(@RequestParam String data){

        try{
            ProductoView productoView = productoService.getByProIdOrProId1(data);
            if (productoView ==null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(productoView,HttpStatus.OK);
        }catch (Exception e){
            console.error(e.getMessage(),"\n Ocurrio un error en el servicio");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/BuscarProducto/{bodega}")
    public ResponseEntity<ProductoView> buscarProdBod(@PathVariable long bodega,@RequestParam String data){
        try{
            ProductoView producto = productoService.porBarraOItem(bodega , data );

            if (producto ==null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(producto,HttpStatus.OK);
        }catch (Exception e){
            console.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verCosto/")
    public double verCosto(@RequestBody ProductoView v){
        try{
            return functionService.obtenerCostoTotalComprasEmpresas(v.getPro_empresa(), v.getPro_codigo());
        }catch (Exception e){
            log.error(e.getMessage());
            return 0;
        }
    }
}
