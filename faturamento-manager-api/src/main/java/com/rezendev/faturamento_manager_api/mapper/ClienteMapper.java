package com.rezendev.faturamento_manager_api.mapper;

import com.rezendev.faturamento_manager_api.model.dto.ClienteDTO;
import com.rezendev.faturamento_manager_api.model.entity.Cliente;

public class ClienteMapper {
    public static ClienteDTO entityToDTO(Cliente cliente) {
        return ClienteDTO.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .email(cliente.getEmail())
                .build();
    }

    public static Cliente DTOtoEntity(ClienteDTO clienteDTO) {
        return Cliente.builder()
                .nome(clienteDTO.getNome())
                .email(clienteDTO.getEmail())
                .build();
    }
}
