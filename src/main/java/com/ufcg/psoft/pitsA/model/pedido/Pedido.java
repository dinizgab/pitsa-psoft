package com.ufcg.psoft.pitsA.model.pedido;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufcg.psoft.pitsA.dto.entregador.EntregadorVeiculoDTO;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.cliente.Cliente;
import com.ufcg.psoft.pitsA.model.entregador.Entregador;
import com.ufcg.psoft.pitsA.model.sabor.Sabor;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private PizzaPedidoTamanho tamanho;

    @Column(nullable = false)
    private PizzaPedidoTipo tipo;

    @Column
    private TipoPagamento tipoPagamento;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false)
    // TODO - Desacoplar essa relacao arrumando um jeito de implementar uma interface
    private Cliente cliente;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false)
    private Estabelecimento estabelecimentoPedido;

    @OneToMany()
    private List<Sabor> sabores;

    @ManyToOne(optional = true)
    private Entregador entregador;

    @Column
    @Builder.Default
    private EstadoPedido estado = EstadoPedido.RECEBIDO;

    @JsonIgnore
    @Transient
    @Builder.Default
    private CalculadoraPedido calculadoraPedido = new CalculadoraPedidoImpl();

    public void setEstadoEmRota() {
        this.estado = EstadoPedido.EM_ROTA;
        this.notificaCliente();
    }

    public void setEstadoEntregue() {
        this.estado = EstadoPedido.ENTREGUE;
        this.notificaEstabelecimento();
    }

    private void notificaCliente() {
        String nome = entregador.getNome();
        EntregadorVeiculoDTO veiculo = EntregadorVeiculoDTO.builder()
                .corVeiculo(entregador.getCorVeiculo())
                .placaVeiculo(entregador.getPlacaVeiculo())
                .tipoVeiculo(entregador.getTipoVeiculo().getTipoVeiculo())
                .build();

        this.cliente.recebeNotificacaoPedidoEmRota(nome, veiculo);
    }

    private void notificaEstabelecimento() {
        String nomeCliente = this.cliente.getNome();
        this.estabelecimentoPedido.recebeNotificacaoPedidoEntregue(nomeCliente);
    }

    public Double calculaValorTotal() {
        return calculadoraPedido.calculaTotal(sabores, tipo, tamanho, tipoPagamento);
    }
}
