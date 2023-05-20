package com.ufcg.psoft.pitsA.model.pedido;

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

    @Transient
    private List<SaborPedidoDTO> sabores;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    private Cliente cliente;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    private Estabelecimento estabelecimentoPedido;
}
