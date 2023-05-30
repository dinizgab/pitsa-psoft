package com.ufcg.psoft.pitsA.model.sabor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufcg.psoft.pitsA.model.cliente.Cliente;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.cliente.Interessado;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
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
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    Estabelecimento estabelecimento;

    @OneToMany(targetEntity = Cliente.class)
    @Builder.Default
    private Set<Interessado> interessados = new HashSet<>();

    public void adicionaInteressado(Interessado interessado) {
        interessados.add(interessado);
    }

    public void removeInteressado(Interessado interessado) {
        interessados.remove(interessado);
    }

    private void notifica() {
        this.interessados.forEach(Interessado::recebeNotificacaoDisponibilidade);
    }

    public void alteraDisponibilidade() {
        this.disponivel = !this.disponivel;
        if (this.disponivel) this.notifica();
    }
}
