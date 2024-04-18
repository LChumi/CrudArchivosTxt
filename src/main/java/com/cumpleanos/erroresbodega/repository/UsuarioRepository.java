/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
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
