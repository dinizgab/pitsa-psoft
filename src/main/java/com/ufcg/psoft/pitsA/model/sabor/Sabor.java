package com.ufcg.psoft.pitsA.model.sabor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufcg.psoft.pitsA.model.Cliente;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private TipoSabor tipo;

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

    // TODO - Arrumar um jeito de salvar a interface Interessado
    @OneToMany
    @Builder.Default
    private Set<Cliente> interessados = new HashSet<>();

    public void adicionaInteressado(Cliente interessado) {
        interessados.add(interessado);
    }

    public void removeInteressado(Cliente interessado) {
        interessados.remove(interessado);
    }

    public void notifica() {
        this.interessados.forEach(interessado -> interessado.recebeNotificacao());
    }

    public void alteraDisponibilidade() {
        this.disponivel = !this.disponivel;
    }
}
