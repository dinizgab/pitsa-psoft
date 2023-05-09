package com.ufcg.psoft.pitsA.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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
    private String tipoVeiculo;

    @JsonProperty("fabricante")
    @Column(nullable = false)
    private String corVeiculo;

    @JsonProperty("codigoAcesso")
    @Column(nullable = false)
    private String codigoAcesso;

    @Builder.Default
    @JsonProperty("estabelecimento")
    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Estabelecimento> estabelecimentos = new HashSet<>();
}