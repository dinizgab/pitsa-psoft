package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.model.Estabelecimento;

import java.util.List;

@FunctionalInterface
public interface EstabelecimentoListarService {
    List<Estabelecimento> listar(Long id);
}
