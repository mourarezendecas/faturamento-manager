package com.rezendev.faturamento_manager_api.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
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
public class PedidoDTO {
    private Long id;

    @NotBlank(message = "Data do pedido não deve ser nula!")
    @JsonProperty("data_pedido")
    private LocalDateTime dataPedido;

    @NotBlank(message = "ID do cliente não deve ser nulo!")
    @JsonProperty("id_cliente")
    private Long clienteID;

    private List<ItemDTO> itens;
}
