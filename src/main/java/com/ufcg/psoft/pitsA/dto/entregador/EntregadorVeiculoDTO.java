package com.ufcg.psoft.pitsA.dto.entregador;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntregadorVeiculoDTO {
    private String placaVeiculo;
    private String corVeiculo;
    private String tipoVeiculo;
}
