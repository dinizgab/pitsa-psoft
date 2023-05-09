package com.ufcg.psoft.pitsA.service.entregador;

import com.ufcg.psoft.pitsA.dto.EntregadorReadDTO;
import com.ufcg.psoft.pitsA.exception.EntregadorNaoExisteException;
import com.ufcg.psoft.pitsA.model.Entregador;
import com.ufcg.psoft.pitsA.repository.EntregadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntregadorListarServiceImpl implements EntregadorListarService{
    @Autowired
    EntregadorRepository entregadorRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<EntregadorReadDTO> listar(Long id) {
        List<EntregadorReadDTO> listaResultado = new ArrayList<>();

        if (id != null && id > 0) {
            Entregador resultadoBusca = entregadorRepository.findById(id).orElseThrow(EntregadorNaoExisteException::new);

            listaResultado.add(modelMapper.map(resultadoBusca, EntregadorReadDTO.class));
        } else {
            List<Entregador> resultadoBusca = entregadorRepository.findAll();

            for (Entregador entregador : resultadoBusca) {
                EntregadorReadDTO entregadorReadDTO = modelMapper.map(entregador, EntregadorReadDTO.class);
                listaResultado.add(entregadorReadDTO);
            }
        }

        return listaResultado;
    }
}
