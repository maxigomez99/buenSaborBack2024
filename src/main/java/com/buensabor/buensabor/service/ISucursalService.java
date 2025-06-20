package com.buensabor.buensabor.service;

import com.buensabor.buensabor.entities.Sucursal;
import com.buensabor.buensabor.dto.sucursal.SucursalDto;
import java.util.List;
public interface ISucursalService {
    public Sucursal save(Sucursal sucursal) throws Exception;
    public boolean delete(Long id) throws Exception;
    public Sucursal updateDto(Long id, SucursalDto sucursalDto) throws Exception;
    public List<Sucursal> traerTodo() throws Exception;
    public Sucursal traerPorId(Long id) throws Exception;
    public Sucursal reactivate(Long id) throws Exception;
    public List<Sucursal> traerPorEmpresaId(Long empresaId) throws Exception;
    public Sucursal guardarSucursalDto(SucursalDto sucursalDto) throws Exception;
    public List<Sucursal> obtenerSucursalesActivas() throws Exception ;
    public List<Sucursal> traerTodoPorEmpresaId(Long empresaId) throws Exception;
}
