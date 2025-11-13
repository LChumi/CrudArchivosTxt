/*
 * Copyright (c) 2025 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.models.specification;

import com.cumpleanos.erroresbodega.models.Producto;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class ProductoSpecifications {

    public static Specification<Producto> getMatchesByEmpresaAndProIdOrProId1(
            Long empresa, String barra, String item) {

        return (root, query, cb) -> {
            Predicate predEmpresa = cb.equal(root.get("proEmpresa"), empresa);
            Predicate predBarra = cb.equal(root.get("proId"), barra);
            Predicate predItem = cb.equal(root.get("proId1"), item);

            return cb.and(predEmpresa, cb.or(predBarra, predItem));
        };
    }
}