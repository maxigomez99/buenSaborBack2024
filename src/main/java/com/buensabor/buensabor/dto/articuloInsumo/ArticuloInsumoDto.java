package com.buensabor.buensabor.dto.articuloInsumo;

import com.buensabor.buensabor.dto.BaseDto;
import com.buensabor.buensabor.dto.categoria.CategoriaDto;
import com.buensabor.buensabor.entities.Categoria;
import com.buensabor.buensabor.entities.ImagenArticulo;
import com.buensabor.buensabor.entities.Sucursal;
import com.buensabor.buensabor.entities.UnidadMedida;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ArticuloInsumoDto extends BaseDto{

    private Double precioCompra;
    private Integer stockActual;
    private Integer stockMaximo;
    private Boolean esParaElaborar;
    private Integer stockMinimo;
    private String denominacion;
    private String descripcion;
    private String codigo;
    private Double precioVenta;
    private Set<ImagenArticulo> imagenes = new HashSet<>();
    private UnidadMedida unidadMedida;
    private CategoriaDto categoria;
    private Sucursal sucursal;
}