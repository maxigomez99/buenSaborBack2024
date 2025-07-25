package com.buensabor.buensabor.repository;

import com.buensabor.buensabor.entities.UnidadMedida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUnidadMedidaRepository extends JpaRepository<UnidadMedida, Long> {

    // Verificar si la unidad de medida está siendo utilizada por algún artículo
    @Query("SELECT COUNT(a) > 0 FROM Articulo a WHERE a.unidadMedida.id = :unidadMedidaId")
    boolean isUsedByAnyArticulo(@Param("unidadMedidaId") Long unidadMedidaId);
}
