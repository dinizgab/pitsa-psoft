package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.SaborDTO;
import com.ufcg.psoft.pitsA.exception.sabor.SaborJaExistenteException;
import com.ufcg.psoft.pitsA.model.Sabor;
import com.ufcg.psoft.pitsA.repository.SaborRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaborUpdateImplService implements SaborUpDateService {

   @Autowired
   SaborRepository saborRepository;
   @Autowired
   SaborFindByName saborFindByName;
   @Autowired
   private ModelMapper modelMapper;

    @Override
    public SaborDTO update(SaborDTO saborDTO) {
        SaborDTO saborExistente = saborFindByName.findByName(saborDTO.getNome());
        if (saborExistente != null) {
            throw new SaborJaExistenteException();
        }
        Sabor sabor = modelMapper.map(saborDTO, Sabor.class);
        Sabor saborSalvo = saborRepository.save(sabor);
        return modelMapper.map(saborSalvo, SaborDTO.class);
    }
}
