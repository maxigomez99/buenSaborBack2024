package com.buensabor.buensabor.repository;

import com.buensabor.buensabor.dto.articuloManufacturado.ArticuloManufacturadoVendidoDto;
import com.buensabor.buensabor.entities.PedidoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IPedidoDetalleRepository extends JpaRepository<PedidoDetalle, Long> {

    @Query("SELECT new com.buensabor.buensabor.dto.articuloManufacturado.ArticuloManufacturadoVendidoDto(am.denominacion, SUM(pd.cantidad)) " +
            "FROM PedidoDetalle pd " +
            "JOIN pd.articulo am " +
            "WHERE pd.pedido.sucursal.id = :sucursalId " +
            "AND pd.pedido.estado <> com.buensabor.buensabor.enums.Estado.CANCELADO " +
            "AND TYPE(am) = com.buensabor.buensabor.entities.ArticuloManufacturado " +
            "GROUP BY am.denominacion")
    List<ArticuloManufacturadoVendidoDto> findArticulosManufacturadosVendidosPorSucursal(Long sucursalId);


}