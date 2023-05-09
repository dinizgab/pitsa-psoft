package com.ufcg.psoft.mercadofacil.service.sabor;

import com.ufcg.psoft.mercadofacil.exception.saborException.SaborJaExistenteException;
import com.ufcg.psoft.mercadofacil.model.Sabor;
import com.ufcg.psoft.mercadofacil.repository.SaborRepository;
import com.ufcg.psoft.mercadofacil.dto.SaborDTO;
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

