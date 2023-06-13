package com.ufcg.psoft.pitsA.dto.sabor;

import com.ufcg.psoft.pitsA.dto.cliente.ClienteReadDTO;
import com.ufcg.psoft.pitsA.model.sabor.TipoSabor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaborReadDTO {

    private String nome;

    private TipoSabor tipo;

    private boolean disponivel;

    private Double precoMedio;

    private Double precoGrande;

    private List<ClienteReadDTO> interessados;
}
