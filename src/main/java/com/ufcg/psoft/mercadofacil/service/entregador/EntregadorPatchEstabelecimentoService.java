package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.dto.EntregadorPatchEstabelecimentoDTO;
import com.ufcg.psoft.mercadofacil.model.Entregador;

@FunctionalInterface
public interface EntregadorPatchEstabelecimentoService {
    Entregador alteraParcialmente(Long id, EntregadorPatchEstabelecimentoDTO entregadorDTO);
}
