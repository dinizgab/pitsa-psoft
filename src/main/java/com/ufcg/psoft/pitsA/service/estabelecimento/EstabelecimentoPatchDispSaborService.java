package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.dto.estabelecimento.EstabelecimentoPatchDispDTO;
import com.ufcg.psoft.pitsA.dto.sabor.SaborReadDTO;

@FunctionalInterface
public interface EstabelecimentoPatchDispSaborService {
    SaborReadDTO alteraDisponibilidade(Long estabelecimentoId, EstabelecimentoPatchDispDTO estabelecimentoPatchDispDTO);
}
