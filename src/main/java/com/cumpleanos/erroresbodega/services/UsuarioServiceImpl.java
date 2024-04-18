/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
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
