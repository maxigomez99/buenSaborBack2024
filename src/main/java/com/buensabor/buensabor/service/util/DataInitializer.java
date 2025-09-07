package com.buensabor.buensabor.service.util;

import com.buensabor.buensabor.entities.Pais;
import com.buensabor.buensabor.entities.Provincia;
import com.buensabor.buensabor.repository.IPaisRepository;
import com.buensabor.buensabor.repository.IProvinciaRepository;
import com.buensabor.buensabor.repository.ILocalidadRepository;
import com.buensabor.buensabor.service.impl.LocalidadService;
import com.buensabor.buensabor.service.impl.ProvinciaService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@Component
public class DataInitializer {

    @Autowired
    private IProvinciaRepository provinciaRepository;

    @Autowired
    private LocalidadService localidadService;

    @Autowired
    private ProvinciaService serviceProvincia;
    @Autowired
    private ILocalidadRepository localidadRepository;
    @Autowired
    private IPaisRepository paisRepository;

    @PostConstruct
    public void initData() {
        Optional<Pais> paisOptional = paisRepository.findByNombre("Argentina");
        Pais argentina = paisOptional.orElseGet(() -> {
            Pais nuevoPais = new Pais();
            nuevoPais.setId(0L); // Establece el ID a 0
            nuevoPais.setNombre("Argentina");
            return paisRepository.save(nuevoPais);
        });


        if (provinciaRepository.count() == 0) {
            try {
                serviceProvincia.saveProvinciasFromApi();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Suponiendo que tienes un método similar para verificar si ya existen localidades
        if (localidadRepository.findAll().isEmpty()) {
            List<Provincia> provincias = provinciaRepository.findAll();
            provincias.forEach(provincia -> localidadService.guardarLocalidadesDeProvincia(String.valueOf(provincia.getId())));
        }
    }
}