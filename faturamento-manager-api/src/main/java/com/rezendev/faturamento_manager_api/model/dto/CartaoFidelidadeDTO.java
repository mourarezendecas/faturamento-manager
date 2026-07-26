package com.rezendev.faturamento_manager_api.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartaoFidelidadeDTO {
    private Long id;

    @NotNull(message = "Os pontos sao obrigatorios")
    private Integer pontos;

    @NotNull(message = "O id do cliente e obrigatorio")
    private Long idCliente;
}
