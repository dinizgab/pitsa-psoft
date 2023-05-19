package com.ufcg.psoft.pitsA.dto.entregador;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.pitsA.model.TipoVeiculoEntregador;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntregadorPostPutDTO {
    @JsonProperty("nome")
    @NotBlank(message = "Nome obrigatorio")
    private String nome;

    @JsonProperty("placaVeiculo")
    @NotBlank(message = "A placa do veiculo e obrigatoria")
    private String placaVeiculo;

    @JsonProperty("tipoVeiculo")
    @NotNull(message = "O tipo do veiculo e obrigatorio")
    private TipoVeiculoEntregador tipoVeiculo;

    @JsonProperty("corVeiculo")
    @NotBlank(message = "A cor do veiculo e obrigatoria")
    private String corVeiculo;

    @JsonProperty("codigoAcesso")
    @NotBlank(message = "O codigo de acesso e obrigatorio")
    private String codigoAcesso;
}
