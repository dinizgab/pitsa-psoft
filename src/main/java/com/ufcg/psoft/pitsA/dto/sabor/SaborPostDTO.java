package com.ufcg.psoft.pitsA.dto.sabor;

import com.ufcg.psoft.pitsA.model.TipoSaborPizza;
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
public class SaborPostDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    private TipoSaborPizza tipo;

    @Positive(message = "Preço deve ser maior ou igual a zero")
    private Double precoMedio;

    @Positive(message = "Preço deve ser maior ou igual a zero")
    private Double precoGrande;

    @NotBlank(message = "Codigo de acesso nao pode ser vazio")
    private String codigoAcesso;
}