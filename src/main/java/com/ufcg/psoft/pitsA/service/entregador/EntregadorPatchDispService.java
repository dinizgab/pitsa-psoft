package com.ufcg.psoft.pitsA.service.entregador;

import com.ufcg.psoft.pitsA.dto.entregador.EntregadorDispDTO;
import com.ufcg.psoft.pitsA.dto.entregador.EntregadorReadDTO;

@FunctionalInterface
public interface EntregadorPatchDispService {
    EntregadorReadDTO alteraDisponibilidadeEntrega(Long entregadorId, EntregadorDispDTO patchDTO);
}
