package com.buensabor.buensabor.service.impl;

import com.buensabor.buensabor.service.IProvinciaService;
import com.buensabor.buensabor.repository.IProvinciaRepository;
import com.buensabor.buensabor.entities.Provincia;
import com.buensabor.buensabor.service.util.ProvinciaClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProvinciaService implements IProvinciaService {
    @Autowired
    private IProvinciaRepository provinciaRepository;
    @Autowired
    private ProvinciaClient provinciaClient;

    @Override
    public List<Provincia> getProvinciaByPaisId(Long paisId) {
        return provinciaRepository.findByPaisId(paisId);
    }

    public void saveProvinciasFromApi() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String response = provinciaClient.fetchProvincias();
        JsonNode root = objectMapper.readTree(response);
        JsonNode provinciasNode = root.path("provincias");
        if (provinciasNode.isArray()) {
            List<Provincia> provincias = new ArrayList<>();
            for (JsonNode node : provinciasNode) {
                Provincia provincia = new Provincia();
                provincia.setId(Long.valueOf(node.get("id").asText()));
                provincia.setNombre(node.get("nombre").asText());
                provincias.add(provincia);
            }
            provinciaRepository.saveAll(provincias);
        }
    }
}
