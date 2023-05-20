package com.ufcg.psoft.pitsA.dto.pedido;

import com.ufcg.psoft.pitsA.model.pedido.PizzaPedidoTamanho;
import com.ufcg.psoft.pitsA.model.pedido.PizzaPedidoTipo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoValidaDTO {
    private Integer quantidadeSabores;
    private PizzaPedidoTipo tipoPedido;
    private PizzaPedidoTamanho tamanho;
}
