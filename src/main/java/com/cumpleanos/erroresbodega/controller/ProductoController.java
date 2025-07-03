/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
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

    @GetMapping("/BuscarProducto")
    public ResponseEntity<ProductoView> bucarProducto(@RequestParam String data){

        try{
            ProductoView productoView = productoService.getByProIdOrProId1(data);
            if (productoView ==null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(productoView,HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage(),"\n Ocurrio un error en el servicio");
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
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/BuscarProductoItem/{bodega}")
    public ResponseEntity<ProductoView> buscarProdIdAndId1(@PathVariable long bodega,@RequestParam String data,@RequestParam String item){
        try{
            ProductoView producto = productoService.porBarraEItem(bodega , data ,item );

            if (producto ==null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(producto,HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verCosto/")
    public double verCosto(@RequestBody ProductoView v){
        try{
            return functionService.obtenerCostoTotalComprasEmpresas(v.getProEmpresa(), v.getProCodigo());
        }catch (Exception e){
            log.error(e.getMessage());
            return 0;
        }
    }

    @GetMapping("/get/matches/{bodega}")
    public ResponseEntity<String> getMatches(@PathVariable long bodega,@RequestParam String data, @RequestParam String item){
        try{
            String novedad = productoService.getMatches(bodega , data , item);

            if (novedad ==null || novedad.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(novedad);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
