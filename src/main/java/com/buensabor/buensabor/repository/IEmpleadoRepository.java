package com.buensabor.buensabor.repository;

import com.buensabor.buensabor.entities.Empleado;
import com.buensabor.buensabor.enums.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IEmpleadoRepository extends JpaRepository<Empleado, Long> {

    Empleado findByUsuarioEmpleado_Id(Long idUsuarioCliente);
    List<Empleado> findBySucursalId(Long sucursalId);
    Empleado findByEmail(String email);
    boolean existsByEmail(String email);
    Long countBySucursalIdAndRolAndEliminadoFalse(Long sucursalId, Rol rol);
}
