package com.ufcg.psoft.pitsA.model.sabor;

import com.ufcg.psoft.pitsA.model.pedido.Pedido;
import com.ufcg.psoft.pitsA.model.sabor.TipoSabor;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaborPedido {
    private String nome;

    private TipoSabor tipo;

    private double precoGrande;

    private double precoMedio;

    private Pedido pedido;
}
