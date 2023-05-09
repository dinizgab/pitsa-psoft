package com.ufcg.psoft.mercadofacil.service.sabor;

import com.ufcg.psoft.mercadofacil.dto.SaborDTO;
import com.ufcg.psoft.mercadofacil.model.Sabor;
import com.ufcg.psoft.mercadofacil.repository.SaborRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaborFindAllImplService implements SaborFindAllService{

    @Autowired
    private SaborRepository saborRepository;

    private ModelMapper modelMapper;

    @Override
    public List<SaborDTO> findAll() {
        List<Sabor> sabores = saborRepository.findAll();
        return sabores.stream()
                .map(sabor -> modelMapper.map(sabor, SaborDTO.class))
                .collect(Collectors.toList());
    }
}

