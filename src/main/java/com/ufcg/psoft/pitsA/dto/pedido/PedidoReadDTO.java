package com.ufcg.psoft.pitsA.dto.pedido;

import com.ufcg.psoft.pitsA.dto.cliente.ClienteReadDTO;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.pedido.PizzaPedidoTamanho;
import com.ufcg.psoft.pitsA.model.pedido.PizzaPedidoTipo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoReadDTO {
    private ClienteReadDTO cliente;
    private String endereco;
    private PizzaPedidoTamanho tamanho;
    private PizzaPedidoTipo tipo;
    private Estabelecimento estabelecimento;
    private List<SaborPedidoDTO> sabores;
    private Double valorTotal;
}
