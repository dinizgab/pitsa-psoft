package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.dto.entregador.EntregadorReadDTO;
import com.ufcg.psoft.pitsA.dto.estabelecimento.EstabelecimentoAprovaEntregadorDTO;

@FunctionalInterface
public interface EstabelecimentoAprovaService {
    EntregadorReadDTO aprova(Long id, EstabelecimentoAprovaEntregadorDTO estabelecimentoDTO);
}
