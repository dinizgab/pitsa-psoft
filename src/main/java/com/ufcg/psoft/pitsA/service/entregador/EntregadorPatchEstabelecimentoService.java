package com.ufcg.psoft.pitsA.service.entregador;

import com.ufcg.psoft.pitsA.dto.EntregadorPatchEstabelecimentoDTO;
import com.ufcg.psoft.pitsA.model.Entregador;

@FunctionalInterface
public interface EntregadorPatchEstabelecimentoService {
    Entregador alteraParcialmente(Long id, EntregadorPatchEstabelecimentoDTO entregadorDTO);
}
