package com.buensabor.buensabor.repository;

import com.buensabor.buensabor.entities.ImagenCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IImagenClienteRepository extends JpaRepository<ImagenCliente, Long>{
}
