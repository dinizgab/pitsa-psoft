package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.repository.SaborRepository;
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
