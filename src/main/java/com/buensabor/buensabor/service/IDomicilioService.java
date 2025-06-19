package com.buensabor.buensabor.service;

import com.buensabor.buensabor.entities.Domicilio;
import java.util.List;
public interface IDomicilioService {
    Boolean eliminar(Long id) throws Exception;
    Domicilio guardar(Domicilio entity) throws Exception;
    Domicilio modificar(Long id, Domicilio entity) throws Exception;
    Domicilio buscarPorId(Long id) throws Exception;
    List<Domicilio> buscarTodos() throws Exception;
    Boolean reactivar(Long id) throws Exception;
}
