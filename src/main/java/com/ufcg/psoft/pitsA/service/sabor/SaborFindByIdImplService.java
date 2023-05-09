package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.SaborDTO;
import com.ufcg.psoft.pitsA.exception.saborException.SaborNaoExistenteException;
import com.ufcg.psoft.pitsA.model.Sabor;
import com.ufcg.psoft.pitsA.repository.SaborRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaborFindByIdImplService implements SaborFindByIdService{

    @Autowired
    private SaborRepository saborRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SaborDTO findById(Long id){
        Sabor sabor = saborRepository.findById(id).orElseThrow(SaborNaoExistenteException::new);
        return modelMapper.map(sabor, SaborDTO.class);
    }
}
