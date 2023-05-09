package com.ufcg.psoft.pitsA.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.pitsA.model.Entregador;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstabelecimentoPatchEntregadorDTO {
    @JsonProperty("entregadores")
    private Set<Entregador> entregadores;
}
