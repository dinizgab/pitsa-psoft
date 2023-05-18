package com.ufcg.psoft.pitsA.dto.cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteInteresseDTO {
    @NotBlank(message = "O codigo de acesso nao pode estar vazio")
    private String codigoAcesso;
    @NotNull(message = "O id do estabelecimento nao pode ser vazio")
    private Long estabelecimentoId;
    @NotBlank(message = "O id do sabor nao pode ser vazio")
    private Long saborId;
}
