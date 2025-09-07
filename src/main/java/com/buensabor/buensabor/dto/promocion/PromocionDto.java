package com.buensabor.buensabor.dto.promocion;

import com.buensabor.buensabor.dto.BaseDto;

import com.buensabor.buensabor.dto.articuloManufacturado.ArticuloManufacturadoCantidadDto;
import com.buensabor.buensabor.entities.ImagenPromocion;
import com.buensabor.buensabor.entities.Sucursal;
import com.buensabor.buensabor.enums.TipoPromocion;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PromocionDto extends BaseDto {

    private String denominacion;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private LocalTime horaDesde;
    private LocalTime horaHasta;
    private String descripcionDescuento;
    private Double precioPromocional;
    private TipoPromocion tipoPromocion;
    private int cantidadMaximaCompra;
    private Set<PromocionDetalleDto> promocionDetallesDto = new HashSet<>();
    //private Set<ImagenPromocion> imagenes = new HashSet<>();
    private String imagen;
    //private Set<Sucursal> sucursales = new HashSet<>();
    private List<ArticuloManufacturadoCantidadDto> articulosManufacturadosCantidad;
}