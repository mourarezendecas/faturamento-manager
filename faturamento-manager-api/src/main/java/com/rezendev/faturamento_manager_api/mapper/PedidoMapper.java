package com.rezendev.faturamento_manager_api.mapper;

import com.rezendev.faturamento_manager_api.model.dto.PedidoDTO;
import com.rezendev.faturamento_manager_api.model.entity.Cliente;
import com.rezendev.faturamento_manager_api.model.entity.Pedido;

import java.util.stream.Collectors;

public class PedidoMapper {
    public static PedidoDTO entityToDTO(Pedido pedido) {
        return PedidoDTO.builder()
                .id(pedido.getId())
                .dataPedido(pedido.getDataPedido())
                .clienteID(pedido.getCliente().getId())
                .itens(
                        pedido.getItens()
                                .stream()
                                .map(ItemMapper::entityToDTO)
                                .collect(Collectors.toList())
                )
                .status(pedido.getStatus())
                .build();
    }

    public static Pedido DTOToEntity(PedidoDTO pedidoDTO, Cliente cliente) {

        Pedido pedido = Pedido.builder()
                .id(pedidoDTO.getId())
                .dataPedido(pedidoDTO.getDataPedido())
                .cliente(cliente)
                .build();

        if (pedidoDTO.getItens() != null) {
            pedido.setItens(
                    pedidoDTO.getItens()
                            .stream()
                            .map(ItemMapper::dtoToEntity)
                            .peek(item -> item.setPedido(pedido))
                            .collect(Collectors.toList())
            );
        }

        return pedido;
    }
}
