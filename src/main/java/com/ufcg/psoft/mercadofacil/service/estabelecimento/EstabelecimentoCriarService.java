package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.EstabelecimentoPostPutDTO;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;

@FunctionalInterface
public interface EstabelecimentoCriarService {
    Estabelecimento salvar(EstabelecimentoPostPutDTO estabelecimentoSave);
}
