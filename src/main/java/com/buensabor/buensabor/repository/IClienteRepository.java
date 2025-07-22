package com.buensabor.buensabor.repository;

import com.buensabor.buensabor.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByUsuarioCliente_Id(Long idUsuarioCliente);
    Cliente findByEmail(String email);
    boolean existsByEmail(String email);
}
