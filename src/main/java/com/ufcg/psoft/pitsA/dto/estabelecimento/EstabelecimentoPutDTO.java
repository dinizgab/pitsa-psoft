package com.ufcg.psoft.pitsA.dto.estabelecimento;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstabelecimentoPutDTO {
    @NotBlank(message = "O codigo de acesso e obrigatorio")
    private String codigoAcesso;
    @NotBlank(message = "O codigo de acesso para alterar e obrigatorio")
    private String codigoAcessoAlterado;
}
