package com.buensabor.buensabor.controller;

import com.buensabor.buensabor.dto.sucursal.SucursalDto;
import com.buensabor.buensabor.entities.Domicilio;
import com.buensabor.buensabor.entities.Empresa;
import com.buensabor.buensabor.entities.Localidad;
import com.buensabor.buensabor.entities.Sucursal;
import com.buensabor.buensabor.repository.IEmpresaRepository;
import com.buensabor.buensabor.repository.ILocalidadRepository;
import com.buensabor.buensabor.service.ISucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/sucursal")

public class SucursalController {
    @Autowired
    private ISucursalService sucursalService;
    @Autowired
    private ILocalidadRepository localidadRepository;
    @Autowired
    private IEmpresaRepository empresaRepository;

    @PostMapping("/crear-con-imagen")
    public ResponseEntity<?> crearSucursalConImagen(@RequestBody SucursalDto sucursalDto) {
        try {
            Sucursal nuevaSucursal = sucursalService.guardarSucursalDto(sucursalDto);
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

    @CrossOrigin(origins = "http://localhost:5173")
    @PatchMapping("/{id}/toggle-estado")
    public ResponseEntity<?> toggleEstado(@PathVariable Long id) {
        try {
            Sucursal sucursalActualizada = sucursalService.toggleEstado(id);
            String mensaje = sucursalActualizada.isEliminado() ?
                "Sucursal desactivada exitosamente" :
                "Sucursal activada exitosamente";

            return ResponseEntity.ok().body(Map.of(
                "mensaje", mensaje,
                "sucursal", sucursalActualizada
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
