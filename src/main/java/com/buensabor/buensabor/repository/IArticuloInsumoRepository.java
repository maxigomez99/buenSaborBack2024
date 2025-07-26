package com.buensabor.buensabor.repository;

import com.buensabor.buensabor.entities.ArticuloInsumo;
import com.buensabor.buensabor.entities.ImagenArticulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Set;

@Repository
public interface IArticuloInsumoRepository extends JpaRepository<ArticuloInsumo, Long>{

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

    Set<ArticuloInsumo> findBySucursal_Id(Long sucursalId);
    boolean existsByCodigoAndSucursal_Id(String codigo, Long sucursalId);
    boolean existsByDenominacionAndSucursal_Id(String denominacion, Long sucursalId);

    List<ArticuloInsumo> findByCategoriaIdAndEliminadoFalse(Long categoriaId);
    @Query("SELECT ai FROM ArticuloInsumo ai WHERE ai.stockActual > 0 AND ai.sucursal.id = :sucursalId")
    List<ArticuloInsumo> findInsumosConStockPorSucursal(Long sucursalId);

    Set<ImagenArticulo> findByArticulo_Id(Long id);

    void delete(ImagenArticulo imagenVieja);
}