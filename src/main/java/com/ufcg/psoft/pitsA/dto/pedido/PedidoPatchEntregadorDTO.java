package com.ufcg.psoft.pitsA.dto.pedido;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoPatchEntregadorDTO {
    private Long pedidoId;
    private Long entregadorId;
    private String codigoAcesso;

}
