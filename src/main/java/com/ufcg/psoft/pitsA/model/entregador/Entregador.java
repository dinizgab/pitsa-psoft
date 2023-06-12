package com.ufcg.psoft.pitsA.model.entregador;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

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

    public void alteraDisponibilidade() {
        this.disponivel = !this.disponivel;

        // TODO - Trigger de quando o entregador se tornar diponivel novamente (Estabelecimento procura novamente os pedidos prontos e associa eles a um entregador)
    }
}