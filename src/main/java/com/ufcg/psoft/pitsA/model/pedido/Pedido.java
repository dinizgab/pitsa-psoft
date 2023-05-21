package com.ufcg.psoft.pitsA.model.pedido;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufcg.psoft.pitsA.dto.pedido.SaborPedidoDTO;
import com.ufcg.psoft.pitsA.model.Cliente;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.sabor.Sabor;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

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

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false)
    private Cliente cliente;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false)
    private Estabelecimento estabelecimentoPedido;

    @Transient
    private List<SaborPedidoDTO> sabores;

    @JsonIgnore
    @Transient
    @Builder.Default
    private CalculadoraPedido calculadoraPedido = new CalculadoraPedidoImpl();

    public double calculaValorTotal() {
        return calculadoraPedido.calculaTotal(sabores, tipo, tamanho);
    }
}
