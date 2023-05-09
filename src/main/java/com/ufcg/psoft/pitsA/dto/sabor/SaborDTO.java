package com.ufcg.psoft.pitsA.dto.sabor;

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

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Tipo é obrigatório")
    private boolean tipo;

    @Positive(message = "Preço deve ser maior ou igual a zero")
    private Double precoMedio;

    @Positive(message = "Preço deve ser maior ou igual a zero")
    private Double precoGrande;
}
