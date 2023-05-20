package com.ufcg.psoft.pitsA.dto.pedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteListarPedidoDTO {
    private Long pedidoId;
    private String codigoAcesso;
}
