package com.buensabor.buensabor.service;

import com.buensabor.buensabor.entities.Localidad;
import java.util.List;

public interface ILocalidadService {
    public boolean eliminar(Long id) throws Exception;
    public Localidad guardar(Localidad entity) throws Exception;
    public Localidad modificar(Long id, Localidad entity) throws Exception;
    public Localidad buscarPorId(Long id) throws Exception;
    public List<Localidad> buscarTodos() throws Exception;
    public boolean reactivar(Long id) throws Exception;
    public List<Localidad> getLocalidadesByProvinciaId(Long provinciaId);

}
