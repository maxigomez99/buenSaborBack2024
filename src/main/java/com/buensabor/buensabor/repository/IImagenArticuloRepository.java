package com.buensabor.buensabor.repository;

import com.buensabor.buensabor.entities.ImagenArticulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IImagenArticuloRepository extends JpaRepository<ImagenArticulo, Long> {
    List<ImagenArticulo> findByEliminadoFalse();
    Set<ImagenArticulo> findByArticulo_Id(Long id);
}

