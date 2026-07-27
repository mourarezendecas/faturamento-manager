package com.rezendev.faturamento_manager_api.model.dto;

import com.rezendev.faturamento_manager_api.model.enums.StatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtualizarStatusDTO {

    @NotNull(message = "O status é obrigatório")
    private StatusEnum status;
}
