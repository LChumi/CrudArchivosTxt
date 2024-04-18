/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.services;

import com.cumpleanos.erroresbodega.models.Bodega;
import com.cumpleanos.erroresbodega.repository.BodegaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BodegaServiceImpl implements BodegaService{

    @Autowired
    private BodegaRepository bodegaRepository;

    @Override
    public List<Bodega> listarBodegas(Long usuario, Long empresa) {
        return bodegaRepository.findByBod_usuarioAndBod_empresa(usuario, empresa);
    }
}
