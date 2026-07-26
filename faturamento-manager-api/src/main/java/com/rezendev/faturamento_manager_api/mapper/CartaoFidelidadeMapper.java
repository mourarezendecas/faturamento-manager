package com.rezendev.faturamento_manager_api.mapper;

import com.rezendev.faturamento_manager_api.model.dto.CartaoFidelidadeDTO;
import com.rezendev.faturamento_manager_api.model.entity.CartaoFidelidade;
import com.rezendev.faturamento_manager_api.model.entity.Cliente;

public class CartaoFidelidadeMapper {
    public static CartaoFidelidadeDTO entityToDTO(CartaoFidelidade cartaoFidelidade) {
        return CartaoFidelidadeDTO.builder()
                .id(cartaoFidelidade.getId())
                .pontos(cartaoFidelidade.getPontos())
                .idCliente(cartaoFidelidade.getCliente().getId())
                .build();
    }

    public static CartaoFidelidade DTOToEntity(CartaoFidelidadeDTO cartaoFidelidadeDTO) {

        Cliente cliente = Cliente.builder()
                .id(cartaoFidelidadeDTO.getIdCliente())
                .build();

        return CartaoFidelidade.builder()
                .id(cartaoFidelidadeDTO.getId())
                .pontos(cartaoFidelidadeDTO.getPontos())
                .cliente(cliente)
                .build();
    }
}
