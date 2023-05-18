package com.ufcg.psoft.pitsA.dto.sabor;

import com.ufcg.psoft.pitsA.model.Estabelecimento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaborDTO {

    private String nome;

    private boolean tipo;

    private boolean disponivel;

    private Double precoMedio;

    private Double precoGrande;

    private Estabelecimento estabelecimento;
}
