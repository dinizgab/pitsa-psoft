package com.ufcg.psoft.pitsA.service.entregador;

import com.ufcg.psoft.pitsA.dto.entregador.EntregadorPatchEstabelecimentoDTO;
import com.ufcg.psoft.pitsA.model.entregador.Entregador;

@FunctionalInterface
public interface EntregadorPatchEstabelecimentoService {
    Entregador alteraParcialmente(Long id, EntregadorPatchEstabelecimentoDTO entregadorDTO);
}
