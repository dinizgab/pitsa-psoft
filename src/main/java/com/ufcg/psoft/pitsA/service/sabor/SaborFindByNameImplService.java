package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.SaborDTO;
import com.ufcg.psoft.pitsA.exception.PitsAException;
import com.ufcg.psoft.pitsA.exception.saborException.SaborNaoExistenteException;
import com.ufcg.psoft.pitsA.model.Sabor;
import com.ufcg.psoft.pitsA.repository.SaborRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaborFindByNameImplService implements SaborFindByName{

    @Autowired
    private SaborRepository saborRepository;
    private ModelMapper modelMapper;

    @Override
    public SaborDTO findByName(String name) {
        try {
            Sabor sabor = saborRepository.findByName(name);
            return modelMapper.map(sabor, SaborDTO.class);
        } catch (PitsAException e) {
            throw new SaborNaoExistenteException();
        }
    }
}
