package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.dto.EntregadorReadDTO;
import com.ufcg.psoft.pitsA.dto.EstabelecimentoAprovaEntregadorDTO;

@FunctionalInterface
public interface EstabelecimentoAprovaService {
    EntregadorReadDTO aprova(Long id, EstabelecimentoAprovaEntregadorDTO estabelecimentoDTO);
}
