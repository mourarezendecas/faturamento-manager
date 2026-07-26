package com.rezendev.faturamento_manager_api.mapper;

import com.rezendev.faturamento_manager_api.model.dto.ItemDTO;
import com.rezendev.faturamento_manager_api.model.entity.Item;
import com.rezendev.faturamento_manager_api.model.entity.Pedido;

public class ItemMapper {
    public static ItemDTO entityToDTO(Item item) {
        return ItemDTO.builder()
                .id(item.getId())
                .descricao(item.getDescricao())
                .quantidade(item.getQuantidade())
                .precoUnitario(item.getPrecoUnitario())
                .idPedido(item.getPedido().getId())
                .build();
    }

    public static Item dtoToEntity(ItemDTO itemDTO) {
        Pedido pedido = Pedido.builder()
                .id(itemDTO.getId())
                .build();

        return Item.builder()
                .id(itemDTO.getId())
                .descricao(itemDTO.getDescricao())
                .quantidade(itemDTO.getQuantidade())
                .precoUnitario(itemDTO.getPrecoUnitario())
                .pedido(pedido)
                .build();
    }
}
