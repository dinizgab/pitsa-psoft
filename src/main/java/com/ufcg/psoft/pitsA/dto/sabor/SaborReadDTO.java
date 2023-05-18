package com.ufcg.psoft.pitsA.dto.sabor;

import com.ufcg.psoft.pitsA.model.Estabelecimento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaborReadDTO {

    private String nome;

    private boolean tipo;

    private boolean disponivel;

    private Double precoMedio;

    private Double precoGrande;

    private Estabelecimento estabelecimento;
}
