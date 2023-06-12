package com.ufcg.psoft.pitsA.model.entregador;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "entregadores")
public class Entregador {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("nome")
    @Column(nullable = false)
    private String nome;

    @JsonProperty("preco")
    @Column(nullable = false)
    private String placaVeiculo;

    @JsonProperty("codigoDeBarras")
    @Column(nullable = false)
    private TipoVeiculoEntregador tipoVeiculo;

    @JsonProperty("fabricante")
    @Column(nullable = false)
    private String corVeiculo;

    @JsonProperty("codigoAcesso")
    @Column(nullable = false)
    private String codigoAcesso;

    @Column
    @Builder.Default
    private boolean disponivel = false;

    @Builder.Default
    @OneToMany(mappedBy = "entregador", fetch = FetchType.LAZY)
    private List<Pedido> pedidos = new ArrayList<>();

    public void adicionaPedido(Pedido pedido) {
        this.pedidos.add(pedido);
    }

    public void alteraDisponibilidade() {this.disponivel = !this.disponivel;}
}