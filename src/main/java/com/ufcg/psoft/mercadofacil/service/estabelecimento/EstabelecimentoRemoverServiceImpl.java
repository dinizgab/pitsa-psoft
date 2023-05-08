package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoRemoverServiceImpl implements EstabelecimentoRemoverService {
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public void remover(Long id) {
        estabelecimentoRepository.deleteById(id);
    }
}
