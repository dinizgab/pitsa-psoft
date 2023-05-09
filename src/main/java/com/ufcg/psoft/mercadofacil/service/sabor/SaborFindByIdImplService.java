package com.ufcg.psoft.mercadofacil.service.sabor;

import com.ufcg.psoft.mercadofacil.dto.SaborDTO;
import com.ufcg.psoft.mercadofacil.exception.saborException.SaborNaoExistenteException;
import com.ufcg.psoft.mercadofacil.model.Sabor;
import com.ufcg.psoft.mercadofacil.repository.SaborRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaborFindByIdImplService implements SaborFindByIdService{

    @Autowired
    private SaborRepository saborRepository;

    private ModelMapper modelMapper;

    @Override
    public SaborDTO findById(Long id){
        Sabor sabor = saborRepository.findById(id).orElseThrow(SaborNaoExistenteException::new);
        return modelMapper.map(sabor, SaborDTO.class);
    }
}
