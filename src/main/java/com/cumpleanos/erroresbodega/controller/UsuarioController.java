package com.cumpleanos.erroresbodega.controller;

import com.cumpleanos.erroresbodega.models.Usuario;
import com.cumpleanos.erroresbodega.models.auth.LoginRequest;
import com.cumpleanos.erroresbodega.services.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

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

    @GetMapping("/id/{codigo}")
    public ResponseEntity<Usuario> porCodigo(@PathVariable Long codigo){
        try {
            Usuario usuario = usuarioService.buscarPorCodigo(codigo);

            if (usuario !=null){
                console.info("Usuario ok {}",usuario.getUsr_nombre());
                return ResponseEntity.ok(usuario);
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
