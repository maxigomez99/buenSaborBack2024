package com.buensabor.buensabor.service;

import com.buensabor.buensabor.entities.Provincia;
import java.util.List;
public interface IProvinciaService {


    public List<Provincia> getProvinciaByPaisId(Long paisId);


}
