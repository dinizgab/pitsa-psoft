package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.exception.estabelecimento.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.Sabor;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoRemoveSaborServiceImpl implements EstabelecimentoRemoveSaborService {
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public void removeSabor(Long id, Sabor sabor) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoExisteException::new);

        estabelecimento.getCardapio().remove(sabor);

        estabelecimentoRepository.save(estabelecimento);
    }
}
