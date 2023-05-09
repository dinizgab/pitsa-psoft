package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.exception.sabor.SaborJaExistenteException;
import com.ufcg.psoft.pitsA.model.Sabor;
import com.ufcg.psoft.pitsA.repository.SaborRepository;
import com.ufcg.psoft.pitsA.dto.SaborDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaborCreateImplService implements SaborCreateService{

    @Autowired
    private SaborRepository saborRepository;
    @Autowired
    private SaborFindByName saborFindByName;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SaborDTO create(SaborDTO saborDTO) {
        SaborDTO saborExistente = saborFindByName.findByName(saborDTO.getNome());
        if (saborExistente != null) {
            throw new SaborJaExistenteException();
        }

        Sabor sabor = modelMapper.map(saborDTO, Sabor.class);
        Sabor saborSalvo = saborRepository.save(sabor);
        return modelMapper.map(saborSalvo, SaborDTO.class);
    }
}

