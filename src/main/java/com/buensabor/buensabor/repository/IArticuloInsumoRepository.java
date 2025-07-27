package com.buensabor.buensabor.repository;


import com.buensabor.buensabor.entities.ArticuloInsumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IArticuloInsumoRepository extends JpaRepository<ArticuloInsumo, Long> {
    List<ArticuloInsumo> findByEliminadoFalse();

    boolean existsByCodigoAndEliminadoFalse(String codigo);
    boolean existsByDenominacionAndEliminadoFalse(String denominacion);
    boolean existsByIdAndEliminadoTrue(Long id);
    boolean existsByCodigoAndEliminadoTrue(String codigo);
    boolean existsByDenominacionAndEliminadoTrue(String denominacion);
    ArticuloInsumo findByCodigoAndEliminadoTrue(String codigo);
    ArticuloInsumo findByDenominacionAndEliminadoTrue(String denominacion);
    ArticuloInsumo findByIdAndEliminadoFalse(Long id);

    List<ArticuloInsumo> findByCategoriaId(Long categoriaId);

    //region Validaciones para actualizar un insumo
    boolean existsByCodigoAndEliminadoFalseAndIdNot(String codigo, Long id);
    boolean existsByDenominacionAndEliminadoFalseAndIdNot(String denominacion, Long id);
    //endregion

    @Query("SELECT ai FROM ArticuloInsumo ai " +
            "LEFT JOIN FETCH ai.categoria " +
            "LEFT JOIN FETCH ai.unidadMedida " +
            "LEFT JOIN FETCH ai.imagenes " +
            "WHERE ai.sucursal.id = :sucursalId AND ai.eliminado = false")
    Set<ArticuloInsumo> findBySucursal_Id(@Param("sucursalId") Long sucursalId);

    @Query("SELECT ai FROM ArticuloInsumo ai " +
            "LEFT JOIN FETCH ai.categoria " +
            "LEFT JOIN FETCH ai.unidadMedida " +
            "LEFT JOIN FETCH ai.imagenes " +
            "WHERE ai.sucursal.id = :sucursalId")
    Set<ArticuloInsumo> findAllBySucursal_Id(@Param("sucursalId") Long sucursalId);

    boolean existsByCodigoAndSucursal_Id(String codigo, Long sucursalId);
    boolean existsByDenominacionAndSucursal_Id(String denominacion, Long sucursalId);

    List<ArticuloInsumo> findByCategoriaIdAndEliminadoFalse(Long categoriaId);
    @Query("SELECT ai FROM ArticuloInsumo ai WHERE ai.stockActual > 0 AND ai.sucursal.id = :sucursalId")
    List<ArticuloInsumo> findInsumosConStockPorSucursal(Long sucursalId);
}
