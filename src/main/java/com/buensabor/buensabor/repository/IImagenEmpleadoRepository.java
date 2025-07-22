package com.buensabor.buensabor.repository;

import com.buensabor.buensabor.entities.ImagenEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IImagenEmpleadoRepository extends JpaRepository<ImagenEmpleado, Long> {
}

