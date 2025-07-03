/*
 * Copyright (c) 2025 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.models.specification;

import com.cumpleanos.erroresbodega.models.ProductoView;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class ProductoViewSpecifications {

    public static Specification<ProductoView> matchByBodegaAndProIdOrProId1(Long bodega, String barra, String item) {
        return (root, query, cb) -> {
            Predicate predBodega = cb.equal(root.get("bodCodigo"), bodega);

            Predicate predBarraProId = cb.equal(root.get("proId"), barra);
            Predicate predItemProId1 = cb.equal(root.get("proId1"), item);

            Predicate orPredicate = cb.or(predBarraProId, predItemProId1);

            return cb.and(predBodega, orPredicate);
        };
    }
}
