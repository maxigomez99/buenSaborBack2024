package com.buensabor.buensabor.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.buensabor.buensabor.entities.Provincia;

import java.util.List;

@Repository
public interface IProvinciaRepository extends JpaRepository<Provincia, Long> {
    List<Provincia> findByPaisId(Long paisId);
}
