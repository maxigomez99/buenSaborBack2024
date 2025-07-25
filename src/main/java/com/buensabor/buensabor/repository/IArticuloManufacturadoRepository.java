package com.buensabor.buensabor.repository;

import com.buensabor.buensabor.entities.ArticuloManufacturado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IArticuloManufacturadoRepository extends JpaRepository<ArticuloManufacturado, Long> {
    // Aquí puedes agregar métodos de consulta personalizados si los necesitas
}
