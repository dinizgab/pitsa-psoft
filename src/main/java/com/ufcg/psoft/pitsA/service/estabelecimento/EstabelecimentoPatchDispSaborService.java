package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.dto.estabelecimento.EstabelecimentoPatchDispDTO;
import com.ufcg.psoft.pitsA.dto.sabor.SaborDTO;

@FunctionalInterface
public interface EstabelecimentoPatchDispSaborService {
    SaborDTO alteraDisponibilidade(Long estabelecimentoId, EstabelecimentoPatchDispDTO estabelecimentoPatchDispDTO);
}
