package com.buensabor.buensabor.service;

import com.buensabor.buensabor.entities.Empleado;
import java.util.List;
public interface IEmpleadoService {
    public Empleado crearEmpleado(Empleado empleado) throws Exception;
    public Empleado actualizarEmpleado(Long id, Empleado empleado) throws Exception;
    public Empleado buscarPorId(Long idEmpleado) throws Exception;
    public boolean eliminarEmpleado(Long idEmpleado) throws Exception;
    public List<Empleado> traerEmpleados() throws Exception;
}
