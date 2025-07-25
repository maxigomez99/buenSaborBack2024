package com.buensabor.buensabor.repository;

import com.buensabor.buensabor.entities.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPromocionRepository extends JpaRepository<Promocion, Long> {
    // Puedes agregar métodos personalizados para consultas específicas si los necesitas
    List<Promocion> findByEmpresaId(Long empresaId);
}
