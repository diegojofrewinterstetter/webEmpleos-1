package com.webempleos.app.specification;

import com.webempleos.app.models.entity.Publicacion;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PublicacionSpecification {

    public static Specification<Publicacion> publicacionSpecification(Publicacion publicacion) {
        return (from, query, criteriaBuilder) -> {
            List<Predicate> condiciones = new ArrayList<>();
            if (publicacion.getTitulo() != null && !publicacion.getTitulo().trim().isEmpty()) {
                Predicate condicion = criteriaBuilder.like(from.get("titulo"), "%" + publicacion.getTitulo());
                condiciones.add(condicion);
            }

            if (publicacion.getDisponibilidad() != null && !publicacion.getDisponibilidad().trim().isEmpty()) {
                Predicate condicion = criteriaBuilder.like(from.get("disponibilidad"), "%" + publicacion.getTitulo() + "%");
                condiciones.add(condicion);
            }

            if (publicacion.getCategoria() != null && !publicacion.getCategoria().getNombre().trim().isEmpty()) {
                Predicate condicion = criteriaBuilder.like(from.get("categoria").get("nombre"),"%" + publicacion.getCategoria().getNombre() + "%");
                condiciones.add(condicion);
            }
            return criteriaBuilder.or(condiciones.toArray(new Predicate[condiciones.size()]));
        };
    }
}
