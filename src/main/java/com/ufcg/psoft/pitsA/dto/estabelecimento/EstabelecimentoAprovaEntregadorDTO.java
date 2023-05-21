package com.ufcg.psoft.pitsA.dto.estabelecimento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstabelecimentoAprovaEntregadorDTO {
    private Long entregadorId;
    private String codigoAcesso;
    private StatusAprovacaoEntregador aprovar;
}
