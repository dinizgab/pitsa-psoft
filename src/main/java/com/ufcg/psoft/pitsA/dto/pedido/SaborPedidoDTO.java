package com.ufcg.psoft.pitsA.dto.pedido;

import com.ufcg.psoft.pitsA.model.sabor.TipoSabor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaborPedidoDTO {
    private String nome;

    private TipoSabor tipo;

    private double precoGrande;

    private double precoMedio;
}
