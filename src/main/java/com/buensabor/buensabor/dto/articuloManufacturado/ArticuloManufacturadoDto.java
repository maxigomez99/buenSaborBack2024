package com.buensabor.buensabor.dto.articuloManufacturado;

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
public class ArticuloManufacturadoDto extends BaseDto {
    private String denominacion;
    private String descripcion;
    private Double precioVenta;
    private String codigo;
    private String imagen;
    private Set<ImagenArticulo> imagenes = new HashSet<>();
    private UnidadMedida unidadMedida;
    private Integer tiempoEstimadoMinutos;
    private String preparacion;
    private CategoriaDto categoria;
    private Sucursal sucursal;
    private Set<ArticuloManufacturadoDetalleDto> articuloManufacturadoDetallesDto = new HashSet<>();
    private int cantidadMaximaDisponible;

}