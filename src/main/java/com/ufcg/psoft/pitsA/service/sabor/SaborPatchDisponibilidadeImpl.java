package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.exception.sabor.SaborNaoExistenteException;
import com.ufcg.psoft.pitsA.model.Sabor;
import com.ufcg.psoft.pitsA.repository.SaborRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaborPatchDisponibilidadeImpl implements SaborPatchDisponibilidade {
    @Autowired
    SaborRepository saborRepository;

    @Override
    public void alteraDisponibilidade(Long saborId) {
        Sabor sabor = saborRepository.findById(saborId).orElseThrow(SaborNaoExistenteException::new);

        sabor.setDisponivel(!sabor.isDisponivel());
        saborRepository.save(sabor);
    }
}
