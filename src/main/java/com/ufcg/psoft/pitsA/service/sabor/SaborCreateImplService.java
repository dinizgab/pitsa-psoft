package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.SaborDTO;
import com.ufcg.psoft.pitsA.exception.saborException.SaborJaExistenteException;
import com.ufcg.psoft.pitsA.model.Sabor;
import com.ufcg.psoft.pitsA.repository.SaborRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaborCreateImplService implements SaborCreateService{

    @Autowired
    private SaborRepository saborRepository;

    private ModelMapper modelMapper;

    @Override
    public SaborDTO create(SaborDTO saborDTO) {
        Sabor saborExistente = saborRepository.findByName(saborDTO.getNome());
        if (saborExistente != null) {
            throw new SaborJaExistenteException();
        }
        Sabor sabor = modelMapper.map(saborDTO, Sabor.class);
        Sabor saborSalvo = saborRepository.save(sabor);
        return modelMapper.map(saborSalvo, SaborDTO.class);
    }
}

