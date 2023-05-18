package com.ufcg.psoft.pitsA.model;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private String codigoAcesso;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    @ToStringExclude
    @EqualsAndHashCode.Exclude
    private List<Sabor> interessesSabores = new ArrayList<>();
}
