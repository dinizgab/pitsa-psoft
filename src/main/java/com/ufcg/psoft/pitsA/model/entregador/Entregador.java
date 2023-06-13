package com.ufcg.psoft.pitsA.model.entregador;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
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

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToOne
    private Estabelecimento estabelecimento;

    @Column
    @Builder.Default
    private boolean disponivel = false;

    public void alteraDisponibilidade() {
        this.disponivel = !this.disponivel;

        if (this.disponivel) {
            estabelecimento.associaEntregadorDisponivel(this);
        }
    }
}