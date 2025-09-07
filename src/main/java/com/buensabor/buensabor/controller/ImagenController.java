package com.buensabor.buensabor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/api/imagenes")
@CrossOrigin(origins = "*")
public class ImagenController {

    private static final Logger logger = LoggerFactory.getLogger(ImagenController.class);
    private final Path root = Paths.get("src/main/resources/img");

    public ImagenController() {
        try {
            Files.createDirectories(root);
            logger.info("Directorio de imágenes creado: {}", root.toAbsolutePath());
        } catch (IOException e) {
            logger.error("Error creando directorio de imágenes", e);
            throw new RuntimeException("No se pudo crear el directorio de imágenes!", e);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        try {
            Path path = this.root.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/imagenes/")
                    .path(file.getOriginalFilename())
                    .toUriString();
            logger.info("Archivo subido: {} -> {}", file.getOriginalFilename(), uri);
            return ResponseEntity.ok(uri);
        } catch (Exception e) {
            logger.error("Error subiendo archivo: {}", file.getOriginalFilename(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> get(@PathVariable String filename) {
        logger.info("Solicitando imagen: {}", filename);

        try {
            // Primero intenta desde resources (para archivos empaquetados)
            Resource resource = new ClassPathResource("img/" + filename);

            if (resource.exists() && resource.isReadable()) {
                String contentType = "image/jpeg";
                if (filename.toLowerCase().endsWith(".png")) {
                    contentType = "image/png";
                } else if (filename.toLowerCase().endsWith(".gif")) {
                    contentType = "image/gif";
                }

                logger.info("Sirviendo imagen desde resources: {} con tipo: {}", filename, contentType);

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                        .header(HttpHeaders.CACHE_CONTROL, "max-age=3600")
                        .body(resource);
            }

            // Si no está en resources, busca en el directorio del proyecto
            Path file = this.root.resolve(filename);
            logger.info("Ruta completa del archivo: {}", file.toAbsolutePath());

            if (!Files.exists(file)) {
                logger.warn("Archivo no encontrado: {}", file.toAbsolutePath());
                return ResponseEntity.notFound().build();
            }

            resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(file);
                if (contentType == null) {
                    contentType = "image/jpeg";
                }

                logger.info("Sirviendo imagen desde directorio: {} con tipo: {}", filename, contentType);

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                        .header(HttpHeaders.CACHE_CONTROL, "max-age=3600")
                        .body(resource);
            } else {
                logger.warn("Archivo no legible: {}", file.toAbsolutePath());
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error sirviendo imagen: {}", filename, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}