package com.buensabor.buensabor.service;

import com.buensabor.buensabor.dto.pedido.PedidoDto;
import com.buensabor.buensabor.entities.Pedido;
import com.buensabor.buensabor.entities.UsuarioEmpleado;
import java.util.List;

public interface IPedidoService {
    public Pedido crearPedido(Pedido pedido) throws Exception;
    public Pedido actualizarPedido(Long id,Pedido pedido) throws Exception;
    public Pedido buscarPorId(Long id) throws Exception;
    public boolean eliminarPedido(Long id) throws Exception;
    public List<PedidoDto> traerPedidos(Long sucursalId) throws Exception;
    public List<Pedido> traerPedidos2(UsuarioEmpleado usuario) throws Exception;
    public List<PedidoDto> traerPedidosPorClienteId(Long clienteId) throws Exception;
}

