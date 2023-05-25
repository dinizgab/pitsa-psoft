package com.ufcg.psoft.pitsA.model;

import com.ufcg.psoft.pitsA.model.pedido.Pedido;
import com.ufcg.psoft.pitsA.model.sabor.Interessado;
import com.ufcg.psoft.pitsA.model.sabor.Sabor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Cliente implements Interessado {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private String codigoAcesso;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Pedido> pedidos = new ArrayList<>();

    @Override
    public void recebeNotificacao() {
        System.out.print("\nO sabor que voce demonstrou interesse esta disponivel novamente: ");
        System.out.println(this.nome);
    }
}
