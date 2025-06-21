package com.buensabor.buensabor.controller;

import com.buensabor.buensabor.dto.sucursal.SucursalDto;
import com.buensabor.buensabor.entities.Domicilio;
import com.buensabor.buensabor.entities.Localidad;
import com.buensabor.buensabor.entities.Sucursal;
import com.buensabor.buensabor.service.ISucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sucursal")

public class SucursalController {
    @Autowired
    private ISucursalService sucursalService;

    //crear sucursal
    @PostMapping("/crear-con-imagen")

    public ResponseEntity<?> crearSucursalConImagen(@RequestBody SucursalDto sucursalDto) {
        try {
            // Map Domicilio data
            Domicilio domicilio = Domicilio.builder()
                    .calle(sucursalDto.getCalle())
                    .numero(Integer.parseInt(sucursalDto.getNumero()))
                    .cp(Integer.parseInt(sucursalDto.getCp()))
                    .localidad(Localidad.builder().nombre(sucursalDto.getIdLocalidad().toString()).build()) // Ejemplo ajustado // Example mapping
                    .build();

            // Map Sucursal data
            Sucursal sucursal = Sucursal.builder()
                    .nombre(sucursalDto.getNombre())
                    .horarioApertura(sucursalDto.getHorarioApertura())
                    .horarioCierre(sucursalDto.getHorarioCierre())
                    .domicilio(domicilio) // Set Domicilio
                    .imagen(sucursalDto.getImagen()) // Base64 image
                    .build();

            Sucursal nuevaSucursal = sucursalService.save(sucursal);

            return ResponseEntity.ok(nuevaSucursal);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear la sucursal: " + e.getMessage());
        }
    }

    //region CRUD Basico
    @GetMapping("/traer-todo/")
    public ResponseEntity<?> mostrarLista(){
        try {
            return ResponseEntity.ok(sucursalService.traerTodo());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        try {
            return ResponseEntity.ok(sucursalService.traerPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/reactivate/{id}")
    public ResponseEntity<?> reactivar(@PathVariable Long id){
        try {
            return ResponseEntity.ok(sucursalService.reactivate(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize(" hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(sucursalService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    //@PreAuthorize(" hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> actualizar(@PathVariable Long id,@RequestBody SucursalDto sucursalDto){
        try {
            return ResponseEntity.ok(sucursalService.updateDto(id, sucursalDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/lista-sucursal/{empresaId}")
    public ResponseEntity<?> traerPorEmpresaId(@PathVariable Long empresaId){
        try {
            return ResponseEntity.ok(sucursalService.traerPorEmpresaId(empresaId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/lista-todo-sucursal/{empresaId}")
    public ResponseEntity<?> traerTodoPorEmpresaId(@PathVariable Long empresaId){
        try {
            return ResponseEntity.ok(sucursalService.traerTodoPorEmpresaId(empresaId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/")
    //@PreAuthorize(" hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> guardarSucursalDto(@RequestBody SucursalDto sucursalDto){
        try {
            return ResponseEntity.ok(sucursalService.guardarSucursalDto(sucursalDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/traerSucursales/")
    public ResponseEntity<?> traerSucursales(){
        try {
            return ResponseEntity.ok(sucursalService.obtenerSucursalesActivas());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
