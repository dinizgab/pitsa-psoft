package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
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
