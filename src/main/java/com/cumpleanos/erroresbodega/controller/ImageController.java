/*
 * Copyright (c) 2025 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.controller;

import com.cumpleanos.erroresbodega.services.images.GenericImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@CrossOrigin("*")
@RestController
@RequestMapping("/images")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ImageController {

    private final GenericImageService service;

    @Value("${file.images.products}")
    private String productsPath;

    @Value("${file.images.bunna}")
    private String bunnaPath;

    @Value("${file.images.logos}")
    private String logosPath;

    @Value("${file.storage.path.imagenes}")
    private String empleadosPath;

    @GetMapping("/producto/{imageName}")
    public ResponseEntity<Resource> getImageProduct(@PathVariable String imageName){
        try {
            Resource resource = service.getImageFrom(productsPath, imageName, "default.jpg");

            return getResourceResponseEntity(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/fotos/mes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Long>> getPhotosData(){
        Map<String, Long> info =service.getPhotosByDayInMonth(productsPath);
        return ResponseEntity.ok(info);
    }

    @GetMapping("/producto/{imageName}/bunna")
    public ResponseEntity<Resource> getImageProductBunna(@PathVariable String imageName){
        try {
            Resource resource = service.getImageFrom(bunnaPath, imageName, "default.jpg");

            return getResourceResponseEntity(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/producto/exist/{imageName}")
    public ResponseEntity<Short> getImageProductExist(@PathVariable String imageName){
            short exist = service.imageExist(productsPath, imageName);
            return ResponseEntity.ok().body(exist);
    }

    @GetMapping("/usuario/{usrid}")
    public ResponseEntity<Resource> getImageUser(@PathVariable String usrid){
        try {
            Resource resource = service.getImageFrom(empleadosPath, usrid, "IMPC.jpg");

            return getResourceResponseEntity(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/logo/{empresaId}")
    public ResponseEntity<Resource> getImageLogo(@PathVariable String empresaId ){
        try {
            Resource resource = service.getImageFrom(logosPath, empresaId, "assist.jpg");

            return getResourceResponseEntity(resource);
        }catch (MalformedURLException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private ResponseEntity<Resource> getResourceResponseEntity(Resource resource) {
        if (resource == null){
            return ResponseEntity.notFound().build();
        }

        String contentType = service.getContentType(resource);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS))
                .body(resource);
    }
}
