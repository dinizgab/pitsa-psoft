package com.ufcg.psoft.pitsA.dto.sabor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaborDeleteDTO {
    @NotBlank(message = "O id do estabelecimento nao pode ser vazio")
    private Long estabelecimentoId;

    @NotBlank(message = "Codigo de acesso nao pode ser vazio")
    private String codigoAcesso;
}