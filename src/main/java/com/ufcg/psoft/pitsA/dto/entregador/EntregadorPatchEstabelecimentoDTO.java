package com.ufcg.psoft.pitsA.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntregadorPatchEstabelecimentoDTO {
    private Long estabelecimentoId;
    private String codigoAcesso;
}