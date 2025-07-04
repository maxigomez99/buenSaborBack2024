package com.buensabor.buensabor.repository;

import com.buensabor.buensabor.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEmpresaRepository extends JpaRepository<Empresa, Long> {
    boolean existsByCuil(Long cuil);
    boolean existsByNombre(String nombre);
    List<Empresa> findByEliminadoFalse();

    Empresa findByNombre(String nombre);
    Empresa findByCuil(Long cuil);
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Empresa e WHERE LOWER(e.nombre) = LOWER(:nombre) AND e.id != :id")
    boolean existsByNombreAndNotId(@Param("nombre") String nombre, @Param("id") Long id);
}
