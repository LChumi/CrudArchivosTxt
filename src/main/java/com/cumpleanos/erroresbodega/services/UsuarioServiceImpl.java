/*
 * Copyright (c) 2024.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
 */

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

    @Override
    public Usuario buscarPorCodigo(Long codigo) {
        return usuarioRepository.findByUsr_codigo(codigo);
    }

    @Override
    public Usuario buscarPorId(String id) {
        return usuarioRepository.findByUsr_id(id);
    }
}
