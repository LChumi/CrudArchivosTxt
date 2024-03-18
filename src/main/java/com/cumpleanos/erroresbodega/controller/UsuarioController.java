package com.cumpleanos.erroresbodega.controller;

import com.cumpleanos.erroresbodega.models.Usuario;
import com.cumpleanos.erroresbodega.models.auth.LoginRequest;
import com.cumpleanos.erroresbodega.services.ImagenService;
import com.cumpleanos.erroresbodega.services.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ImagenService imagenService;

    private static final Logger console = LoggerFactory.getLogger(UsuarioController.class);

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        try {
            String username= request.getUsername();
            String password= request.getPassword();

            Usuario user = usuarioService.login(username,password);

            if (user !=null ){
                console.info("Usuario ok"+user.getUsr_nombre());
                return ResponseEntity.ok(user);
            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales invalidas");
            }
        }catch (Exception e){
            console.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor");
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Usuario> porId(@PathVariable String id){
        try{
            Usuario usuario = usuarioService.buscarPorId(id);

            if (usuario !=null ){
                console.info("Usuario ok {}",usuario.getUsr_nombre());
                return ResponseEntity.ok(usuario);
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/imagen/{nombre}", produces = "image/jpeg")
    public ResponseEntity<Resource> getImage(@PathVariable String nombre){
        Resource resource = imagenService.getImage(nombre);
        if (resource.exists()){
            return ResponseEntity.ok(resource);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}
