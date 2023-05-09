package com.ufcg.psoft.mercadofacil.service.sabor;

import com.ufcg.psoft.mercadofacil.repository.SaborRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaborRemoverImplService implements SaborRemoverService{

    @Autowired
    private SaborRepository saborRepository;

    @Override
    public void remover(Long id) {
        saborRepository.deleteById(id);
    }

}
