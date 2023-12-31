package com.ufcg.psoft.pitsA.dto.sabor;

import com.ufcg.psoft.pitsA.model.sabor.TipoSabor;
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
public class SaborPutDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Tipo é obrigatório")
    private TipoSabor tipo;

    @Positive(message = "Preço deve ser maior ou igual a zero")
    private Double precoMedio;

    @Positive(message = "Preço deve ser maior ou igual a zero")
    private Double precoGrande;

    @NotBlank(message = "O id do estabelecimento nao pode ser vazio")
    private Long estabelecimentoId;

    @NotBlank(message = "Codigo de acesso nao pode ser vazio")
    private String codigoAcesso;
}