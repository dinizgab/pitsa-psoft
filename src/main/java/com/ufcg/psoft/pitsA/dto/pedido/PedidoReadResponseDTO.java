package com.ufcg.psoft.pitsA.dto.pedido;

import com.ufcg.psoft.pitsA.dto.cliente.ClienteReadDTO;
import com.ufcg.psoft.pitsA.model.pedido.PizzaPedidoTamanho;
import com.ufcg.psoft.pitsA.model.pedido.PizzaPedidoTipo;
import com.ufcg.psoft.pitsA.model.pedido.TipoPagamento;
import com.ufcg.psoft.pitsA.model.sabor.Sabor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoReadResponseDTO {
    private ClienteReadDTO cliente;

    private String endereco;

    private PizzaPedidoTamanho tamanho;

    private PizzaPedidoTipo tipo;

    private TipoPagamento tipoPagamento;

    private List<Sabor> sabores;

    private Double valorTotal;
}
