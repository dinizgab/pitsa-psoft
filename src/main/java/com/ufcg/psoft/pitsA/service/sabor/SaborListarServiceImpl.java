package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.sabor.SaborReadDTO;
import com.ufcg.psoft.pitsA.exception.entregador.EntregadorNaoExisteException;
import com.ufcg.psoft.pitsA.model.Sabor;
import com.ufcg.psoft.pitsA.repository.SaborRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class SaborListarServiceImpl implements SaborListarService {

    @Autowired
    SaborRepository saborRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<Sabor> listar(Long id){
        if (id != null && id > 0) {
            Sabor resultadoBusca = saborRepository.findById(id).orElseThrow(EntregadorNaoExisteException::new);

            return Arrays.asList(modelMapper.map(resultadoBusca, Sabor.class));
        } else {
            return saborRepository.findAll().stream().map(sabor -> modelMapper.map(sabor, Sabor.class)).toList();
        }
    }
}
