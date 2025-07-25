package com.buensabor.buensabor.repository;

import com.buensabor.buensabor.entities.UsuarioCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioClienteRepository extends JpaRepository<UsuarioCliente, Long> {
    UsuarioCliente findByUsername(String username);
    boolean existsByUsername(String username);

}
