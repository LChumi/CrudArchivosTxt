package com.cumpleanos.erroresbodega.services;

import com.cumpleanos.erroresbodega.models.Usuario;

public interface UsuarioService {

    Usuario login(String usuario,String password);

    Usuario buscarPorCodigo(Long codigo);

    Usuario buscarPorId(String id);
}
