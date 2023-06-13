package com.ufcg.psoft.pitsA.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.pitsA.exception.estabelecimento.NenhumEntregadorDisponivelException;
import com.ufcg.psoft.pitsA.exception.pedido.PedidoNaoEncontradoException;
import com.ufcg.psoft.pitsA.model.entregador.Entregador;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;
import com.ufcg.psoft.pitsA.model.sabor.Sabor;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "estabelecimentos")
public class Estabelecimento {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("codigoAcesso")
    @Column(nullable = false)
    private String codigoAcesso;

    @OneToMany(mappedBy = "estabelecimento", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Entregador> entregadoresPendentes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "estabelecimento", fetch = FetchType.LAZY)
    private List<Entregador> entregadoresAprovados = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "estabelecimento", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Sabor> cardapio = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "estabelecimentoPedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pedido> pedidos = new ArrayList<>();

    public void adicionaEntregadorPendente(Entregador entregador) {
        this.entregadoresPendentes.add(entregador);
    }

    public void aprovaEntregador(Entregador entregador) {
        this.entregadoresPendentes.remove(entregador);
        this.entregadoresAprovados.add(entregador);
    }

    public void reprovaEntregador(Entregador entregador) {
        this.entregadoresPendentes.remove(entregador);
    }

    public Pedido encontraPedido(Long id) {
        return this.pedidos
                .stream()
                .filter(pedido -> pedido.getId().equals(id))
                .findFirst()
                .orElseThrow(PedidoNaoEncontradoException::new);
    }

    public Entregador proximoEntregador() {
        return this.getEntregadoresAprovados()
                .stream()
                .filter(Entregador::isDisponivel)
                .findFirst()
                .orElseThrow(NenhumEntregadorDisponivelException::new);
    }

    public void associaEntregadorDisponivel(Entregador entregador) {
        for (Pedido p : pedidos) {
            if (p.getEstado().isPronto()) {
                p.setEstadoEmRota(entregador);
                break;
            }
        }
    }

    public void recebeNotificacaoPedidoEntregue(String nome) {
        System.out.println("\nEstabelecimento " + this.id);
        System.out.println("Hey! O pedido do cliente " + nome + " foi entregue!\n");
    }
}
