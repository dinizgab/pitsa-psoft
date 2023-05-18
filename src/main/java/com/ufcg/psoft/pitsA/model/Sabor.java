package com.ufcg.psoft.pitsA.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.internal.bytebuddy.build.ToStringPlugin;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sabores")
public class Sabor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private boolean tipo;

    @Column(nullable = false)
    private Double precoMedio;

    @Column(nullable = false)
    private Double precoGrande;

    @Column(nullable = false)
    @Builder.Default
    private boolean disponivel = true;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    Estabelecimento estabelecimento;

    @ManyToMany(mappedBy = "interessesSabores", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    @JsonIgnore
    private List<Cliente> interesses = new ArrayList<>();
}
