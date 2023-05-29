package com.ufcg.psoft.pitsA.dto.pedido;

import com.ufcg.psoft.pitsA.model.pedido.TipoPagamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmarPagamentoDTO {
    private Long clienteId;
    private String codigoAcesso;
    private TipoPagamento tipoPagamento;
}
