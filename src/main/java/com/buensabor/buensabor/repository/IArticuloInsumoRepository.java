package com.buensabor.buensabor.repository;

import com.buensabor.buensabor.entities.ArticuloInsumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IArticuloInsumoRepository extends JpaRepository<ArticuloInsumo, Long> {
    // Métodos personalizados si son necesarios
    List<ArticuloInsumo> findByEsParaElaborar(Boolean esParaElaborar);
}
