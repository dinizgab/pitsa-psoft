package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.repository.EntregadorRepository;
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
