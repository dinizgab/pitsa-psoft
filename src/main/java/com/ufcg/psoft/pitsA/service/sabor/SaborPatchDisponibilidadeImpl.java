package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.sabor.SaborReadDTO;
import com.ufcg.psoft.pitsA.exception.sabor.SaborNaoExistenteException;
import com.ufcg.psoft.pitsA.model.sabor.Sabor;
import com.ufcg.psoft.pitsA.repository.SaborRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaborPatchDisponibilidadeImpl implements SaborPatchDisponibilidade {
    @Autowired
    SaborRepository saborRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public SaborReadDTO alteraDisponibilidade(Long saborId) {
        Sabor sabor = saborRepository.findById(saborId).orElseThrow(SaborNaoExistenteException::new);

        sabor.alteraDisponibilidade();

        return modelMapper.map(saborRepository.save(sabor), SaborReadDTO.class);
    }
}
