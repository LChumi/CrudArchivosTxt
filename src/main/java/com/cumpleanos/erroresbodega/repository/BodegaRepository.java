/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.repository;

import com.cumpleanos.erroresbodega.models.Bodega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BodegaRepository extends JpaRepository<Bodega,Long> {

    @Query("SELECT b FROM Bodega b WHERE b.bod_usuario = :usuario AND  b.bod_empresa = :empresa")
    public List<Bodega> findByBod_usuarioAndBod_empresa(Long usuario, Long empresa);
}
