package com.cumpleanos.erroresbodega.services;

import com.cumpleanos.erroresbodega.models.Usuario;
import com.cumpleanos.erroresbodega.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario login(String usuario, String password) {
        return usuarioRepository.findByUsr_idAndUsr_clave(usuario, password);
    }
}
