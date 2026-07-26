package com.rezendev.faturamento_manager_api.service;

import com.rezendev.faturamento_manager_api.model.dto.PedidoDTO;
import com.rezendev.faturamento_manager_api.model.entity.Pedido;
import com.rezendev.faturamento_manager_api.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    public List<PedidoDTO> listarPedidos(){
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidos;
    }

}
