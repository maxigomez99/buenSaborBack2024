package com.buensabor.buensabor.repository;

import com.buensabor.buensabor.entities.UnidadMedida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUnidadMedidaRepository extends JpaRepository<UnidadMedida, Long> {
    // Puedes agregar métodos específicos de consulta si los necesitas
}
