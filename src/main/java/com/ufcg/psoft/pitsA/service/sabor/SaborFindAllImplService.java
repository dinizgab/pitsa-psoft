package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.SaborDTO;
import com.ufcg.psoft.pitsA.model.Sabor;
import com.ufcg.psoft.pitsA.repository.SaborRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaborFindAllImplService implements SaborFindAllService{

    @Autowired
    private SaborRepository saborRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<SaborDTO> findAll() {
        List<Sabor> sabores = saborRepository.findAll();
        return sabores.stream()
                .map(sabor -> modelMapper.map(sabor, SaborDTO.class))
                .collect(Collectors.toList());
    }
}

