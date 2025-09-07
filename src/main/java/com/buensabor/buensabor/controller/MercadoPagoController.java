package com.buensabor.buensabor.controller;

import com.buensabor.buensabor.dto.compraProducto.CompraPedidoDto;
import com.buensabor.buensabor.entities.Pedido;
import com.buensabor.buensabor.entities.mercadoPago.PreferenceMP;
import com.buensabor.buensabor.service.impl.PedidoService;
import com.buensabor.buensabor.service.mercadoPago.MercadoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/MercadoPago")
public class MercadoPagoController {
    @Autowired
    private MercadoPagoService mercadoPagoService;
    @Autowired
    private PedidoService pedidoService;


    @PostMapping("/crear_preference_mp")
    public PreferenceMP crearPreferenceMP(@RequestBody CompraPedidoDto pedido) {
        try {
            Pedido pedidoActualizado = pedidoService.buscarPorId(pedido.getId());
            PreferenceMP preferenceMP = mercadoPagoService.getPreferenciaIdMercadoPago(pedidoActualizado);
            pedidoActualizado.setPreferenceMPId(preferenceMP.getId());
            pedidoService.actualizarPedido(pedido.getId(), pedidoActualizado);
            return preferenceMP;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}