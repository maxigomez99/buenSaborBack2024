package com.buensabor.buensabor.repository;

import com.buensabor.buensabor.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {
    // Puedes agregar métodos personalizados de consulta si los necesitas
}
