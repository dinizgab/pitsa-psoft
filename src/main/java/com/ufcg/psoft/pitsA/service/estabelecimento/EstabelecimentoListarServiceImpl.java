package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.exception.estabelecimento.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EstabelecimentoListarServiceImpl implements EstabelecimentoListarService {
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public List<Estabelecimento> listar(Long id) {
        if (id != null && id > 0)
            return Arrays.asList(estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoExisteException::new));

        return estabelecimentoRepository.findAll();
    }
}
