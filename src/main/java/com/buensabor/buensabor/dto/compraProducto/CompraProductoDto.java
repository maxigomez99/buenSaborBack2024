package com.buensabor.buensabor.dto.compraProducto;

import com.buensabor.buensabor.dto.BaseDto;
import com.buensabor.buensabor.entities.Categoria;
import com.buensabor.buensabor.entities.ImagenArticulo;
import com.buensabor.buensabor.entities.Sucursal;
import com.buensabor.buensabor.entities.UnidadMedida;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CompraProductoDto extends BaseDto {

    private String denominacion;
    private String descripcion;
    private Integer tiempoEstimadoMinutos;
    private String preparacion;
    private String codigo;
    private Double precioVenta;
    private List<ImagenArticulo> imagenes;
    //private UnidadMedida unidadMedida;
    private Long categoriaId;
    private Long sucursalId;
    private Long cantidadMaximaCompra;


}