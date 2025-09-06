package com.buensabor.buensabor.repository;

import com.buensabor.buensabor.entities.TipoPromocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITipoPromocionRepository extends JpaRepository<TipoPromocion, Long> {

    // Verificar si existe otro tipo de promoción con la misma denominación
    @Query("SELECT COUNT(tp) > 0 FROM TipoPromocion tp WHERE LOWER(tp.denominacion) = LOWER(:denominacion)")
    boolean existsByDenominacion(@Param("denominacion") String denominacion);

    // Verificar si existe otro tipo de promoción con la misma denominación (excluyendo el ID actual)
    @Query("SELECT COUNT(tp) > 0 FROM TipoPromocion tp WHERE LOWER(tp.denominacion) = LOWER(:denominacion) AND tp.id != :id")
    boolean existsByDenominacionAndIdNot(@Param("denominacion") String denominacion, @Param("id") Long id);

    // Método para obtener todos los tipos de promoción (incluidos los eliminados)
    @Query("SELECT tp FROM TipoPromocion tp")
    List<TipoPromocion> findAllIncludingDeleted();

    // Método para obtener solo los tipos de promoción activos (no eliminados)
    @Query("SELECT tp FROM TipoPromocion tp WHERE tp.eliminado = false")
    List<TipoPromocion> findAllActive();
}
