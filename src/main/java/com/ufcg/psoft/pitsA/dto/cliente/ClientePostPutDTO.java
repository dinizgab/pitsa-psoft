package com.ufcg.psoft.pitsA.dto.cliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientePostPutDTO {
    private String nome;
    private String endereco;
    private String codigoAcesso;
}
