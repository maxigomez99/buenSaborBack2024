package com.buensabor.buensabor.repository;

import com.buensabor.buensabor.entities.UnidadMedida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUnidadMedidaRepository extends JpaRepository<UnidadMedida, Long> {

    // Verificar si la unidad de medida está siendo utilizada por algún artículo
    @Query("SELECT COUNT(a) > 0 FROM Articulo a WHERE a.unidadMedida.id = :unidadMedidaId")
    boolean isUsedByAnyArticulo(@Param("unidadMedidaId") Long unidadMedidaId);

    // Verificar si existe otra unidad de medida con la misma denominación
    @Query("SELECT COUNT(u) > 0 FROM UnidadMedida u WHERE LOWER(u.denominacion) = LOWER(:denominacion)")
    boolean existsByDenominacion(@Param("denominacion") String denominacion);

    // Verificar si existe otra unidad de medida con la misma denominación (excluyendo el ID actual)
    @Query("SELECT COUNT(u) > 0 FROM UnidadMedida u WHERE LOWER(u.denominacion) = LOWER(:denominacion) AND u.id != :id")
    boolean existsByDenominacionAndIdNot(@Param("denominacion") String denominacion, @Param("id") Long id);

    // Verificar si existe otra unidad de medida con la misma abreviatura
    @Query("SELECT COUNT(u) > 0 FROM UnidadMedida u WHERE LOWER(u.abreviatura) = LOWER(:abreviatura)")
    boolean existsByAbreviatura(@Param("abreviatura") String abreviatura);

    // Verificar si existe otra unidad de medida con la misma abreviatura (excluyendo el ID actual)
    @Query("SELECT COUNT(u) > 0 FROM UnidadMedida u WHERE LOWER(u.abreviatura) = LOWER(:abreviatura) AND u.id != :id")
    boolean existsByAbreviaturaAndIdNot(@Param("abreviatura") String abreviatura, @Param("id") Long id);

    // Método para obtener todas las unidades de medida (incluidas las eliminadas)
    @Query("SELECT u FROM UnidadMedida u")
    List<UnidadMedida> findAllIncludingDeleted();

    // Método para obtener solo las unidades de medida activas (no eliminadas)
    @Query("SELECT u FROM UnidadMedida u WHERE u.eliminado = false")
    List<UnidadMedida> findAllActive();
}
