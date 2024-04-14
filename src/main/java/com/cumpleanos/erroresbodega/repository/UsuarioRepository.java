/*
 * Copyright (c) 2024.
 *  Este código es propiedad de Luis Chumi y está protegido por las leyes de derechos de autor.
 *  Se concede el permiso para usar, copiar, modificar y distribuir este software con la condición de que se incluya este aviso en todas las copias o partes sustanciales del software.
 *  Para obtener ayuda, soporte o permisos adicionales, contacta a Luis Chumi en luischumi.9@gmail.com.
 */

package com.cumpleanos.erroresbodega.repository;

import com.cumpleanos.erroresbodega.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    @Query(value = "SELECT U FROM Usuario  U WHERE UPPER(U.usr_id) LIKE UPPER(:usr_id) And UPPER(U.usr_clave)  LIKE UPPER(:usr_clave)")
    public Usuario findByUsr_idAndUsr_clave(String usr_id,String usr_clave);

    @Query(value = "SELECT U FROM Usuario U WHERE U.usr_codigo=:codigo")
    public  Usuario findByUsr_codigo(Long codigo);

    @Query(value = "SELECT U FROM Usuario U WHERE U.usr_id=UPPER(:id) ")
    public Usuario findByUsr_id(String id);
}
