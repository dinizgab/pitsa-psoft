package com.ufcg.psoft.pitsA.dto.cliente;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufcg.psoft.pitsA.model.Sabor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteReadDTO {
    private String nome;
    private String endereco;
    private List<Sabor> interessesSabores;
}
