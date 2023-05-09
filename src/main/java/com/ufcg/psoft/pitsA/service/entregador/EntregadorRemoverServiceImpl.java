package com.ufcg.psoft.pitsA.service.entregador;

import com.ufcg.psoft.pitsA.repository.EntregadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorRemoverServiceImpl implements EntregadorRemoverService {
    @Autowired
    EntregadorRepository entregadorRepository;

    @Override
    public void remover(Long id) {
        entregadorRepository.deleteById(id);
    }

}
