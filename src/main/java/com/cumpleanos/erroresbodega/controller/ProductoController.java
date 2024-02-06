package com.cumpleanos.erroresbodega.controller;

import com.cumpleanos.erroresbodega.models.ProductoView;
import com.cumpleanos.erroresbodega.services.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producto")
@CrossOrigin("*")
public class ProductoController{

    @Autowired
    private ProductoService productoService;

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
}
