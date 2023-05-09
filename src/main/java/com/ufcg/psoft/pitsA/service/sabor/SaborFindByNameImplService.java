package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.SaborDTO;
import com.ufcg.psoft.pitsA.exception.saborException.SaborJaExistenteException;
import com.ufcg.psoft.pitsA.exception.saborException.SaborNaoExistenteException;
import com.ufcg.psoft.pitsA.model.Sabor;
import com.ufcg.psoft.pitsA.repository.SaborRepository;
import com.ufcg.psoft.pitsA.exception.PitsAException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaborFindByNameImplService implements SaborFindByName {

    @Autowired
    private SaborRepository saborRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SaborDTO findByName(String name) {
        List<Sabor> saborExistente = saborRepository.findAll();

        for (Sabor sabor : saborExistente) {
            if (sabor.getNome().equals(name)) {
                return modelMapper.map(sabor, SaborDTO.class);
            }
        }

        throw new SaborNaoExistenteException();
    }
}
