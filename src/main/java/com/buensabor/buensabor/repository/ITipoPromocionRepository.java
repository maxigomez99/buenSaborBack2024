package com.buensabor.buensabor.repository;

import com.buensabor.buensabor.entities.TipoPromocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipoPromocionRepository extends JpaRepository<TipoPromocion, Long> {
    // Puedes agregar métodos específicos de consulta si los necesitas
}
