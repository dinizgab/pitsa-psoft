package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.exception.estabelecimento.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.pitsA.exception.estabelecimento.SaborNaoPertenceCardapio;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.Sabor;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoAtualizaSaborServiceImpl implements EstabelecimentoAtualizaSaborService {
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public void atualizaSabor(Long id, Sabor sabor) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoExisteException::new);

        Sabor resultBusca = estabelecimento.getCardapio()
                .stream()
                .filter(result -> result.getId().equals(sabor.getId()))
                .findFirst()
                .orElseThrow(SaborNaoPertenceCardapio::new);

        estabelecimento.getCardapio().remove(resultBusca);
        estabelecimento.getCardapio().add(sabor);

        estabelecimentoRepository.save(estabelecimento);
    }
}
