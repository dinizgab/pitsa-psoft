package com.ufcg.psoft.pitsA.model.pedido;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufcg.psoft.pitsA.model.Cliente;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.sabor.Sabor;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private PizzaPedidoTamanho tamanho;

    @Column(nullable = false)
    private PizzaPedidoTipo tipo;

    @Column
    private TipoPagamento tipoPagamento;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false)
    private Cliente cliente;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false)
    private Estabelecimento estabelecimentoPedido;

    @OneToMany()
    private List<Sabor> sabores;

    @Column
    @Builder.Default
    private EstadoPedido estado = EstadoPedido.RECEBIDO;

    @JsonIgnore
    @Transient
    @Builder.Default
    private CalculadoraPedido calculadoraPedido = new CalculadoraPedidoImpl();

    public Double calculaValorTotal() {
        return calculadoraPedido.calculaTotal(sabores, tipo, tamanho, tipoPagamento);
    }
}
