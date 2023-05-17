package com.ufcg.psoft.pitsA.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(mappedBy = "estabelecimentos", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<Entregador> entregadoresPendentes = new HashSet<>();

    @Builder.Default
    @ManyToMany(mappedBy = "estabelecimentos", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Entregador> entregadoresAprovados = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "estabelecimento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sabor> cardapio = new ArrayList<>();
}
