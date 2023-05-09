package com.ufcg.psoft.mercadofacil.service.sabor;

import com.ufcg.psoft.mercadofacil.dto.SaborDTO;
import com.ufcg.psoft.mercadofacil.exception.saborException.SaborNaoExistenteException;
import com.ufcg.psoft.mercadofacil.model.Sabor;
import com.ufcg.psoft.mercadofacil.repository.SaborRepository;
import com.ufcg.psoft.pitsA.exception.PitsAException;
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
