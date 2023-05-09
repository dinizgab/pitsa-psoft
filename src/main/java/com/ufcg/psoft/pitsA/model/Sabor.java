package com.ufcg.psoft.pitsA.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne()
    Estabelecimento estabelecimento;
}
