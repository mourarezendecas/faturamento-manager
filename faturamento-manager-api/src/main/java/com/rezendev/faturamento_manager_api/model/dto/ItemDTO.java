package com.rezendev.faturamento_manager_api.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    private Long id;

    private LocalDateTime dataPedido;

    @NotNull(message = "O id do cliente e obrigatorio")
    private Long idCliente;

    @NotEmpty(message = "O pedido deve ter ao menos um item")
    @Valid
    private List<ItemDTO> itens;
}
