package com.rezendev.faturamento_manager_api.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Long id;

    @NotBlank(message = "O nome e obrigatorio")
    private String nome;

    @NotBlank(message = "O email e obrigatorio")
    private String email;
}
