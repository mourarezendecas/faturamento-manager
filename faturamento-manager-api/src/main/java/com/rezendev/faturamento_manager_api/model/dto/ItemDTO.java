package com.rezendev.faturamento_manager_api.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    private Long id;

    @NotNull(message = "A descricao do item e obrigatoria")
    private String descricao;

    @NotNull(message = "A quantidade de itens e obrigatoria")
    private Integer quantidade;

    @NotNull(message = "O preco unitario e obrigatorio")
    private BigDecimal precoUnitario;

    @NotNull(message = "O id do pedido e obrigatorio")
    private Long idPedido;
}
