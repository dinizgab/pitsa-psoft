package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EstabelecimentoListarServiceImpl implements EstabelecimentoListarService{
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public List<Estabelecimento> listar(Long id) {
        if (id != null && id > 0) return Arrays.asList(estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoExisteException::new));

        return estabelecimentoRepository.findAll();
    }
}
