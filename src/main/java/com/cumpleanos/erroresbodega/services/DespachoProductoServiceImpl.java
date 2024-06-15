/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.services;

import com.cumpleanos.erroresbodega.models.DespachoProducto;
import com.cumpleanos.erroresbodega.repository.DespachoProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class DespachoProductoServiceImpl implements DespachoProductoService {

    @Autowired
    private DespachoProductoRepository productoRepository;

    @Override
    public List<DespachoProducto> listarProductos(BigInteger cco_codigo) {
        return productoRepository.findByCcoCodigo(cco_codigo);
    }

    @Override
    public DespachoProducto producto(BigInteger codigo, String pro_id) {
        return productoRepository.findByCcoCodigoAndProId(codigo, pro_id);
    }
}
