package com.cumpleanos.erroresbodega.repository;

import com.cumpleanos.erroresbodega.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    @Query(value = "SELECT U FROM Usuario  U WHERE UPPER(U.usr_id) LIKE UPPER(:usr_id) And UPPER(U.usr_clave)  LIKE UPPER(:usr_clave)")
    public Usuario findByUsr_idAndUsr_clave(String usr_id,String usr_clave);

    @Query(value = "SELECT U FROM Usuario U WHERE U.usr_codigo=:codigo")
    public  Usuario findByUsr_codigo(Long codigo);
}
