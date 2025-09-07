package com.buensabor.buensabor.repository;

import com.buensabor.buensabor.entities.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
public interface IArticuloRepository extends JpaRepository<Articulo, Long> {

    Articulo findById(long id);

}
