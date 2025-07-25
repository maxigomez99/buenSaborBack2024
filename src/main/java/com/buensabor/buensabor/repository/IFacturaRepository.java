package com.buensabor.buensabor.repository;

import com.buensabor.buensabor.entities.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFacturaRepository extends JpaRepository<Factura, Long>{
}
