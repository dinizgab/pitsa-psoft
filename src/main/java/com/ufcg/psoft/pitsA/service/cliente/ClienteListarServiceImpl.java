package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.cliente.ClienteReadDTO;
import com.ufcg.psoft.pitsA.exception.cliente.ClienteNaoExisteException;
import com.ufcg.psoft.pitsA.model.Cliente;
import com.ufcg.psoft.pitsA.repository.ClienteRepository;
import jakarta.persistence.ManyToOne;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ClienteListarServiceImpl implements ClienteListarService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<ClienteReadDTO> listar(Long id) {
        if (id != null && id > 0) {
            Cliente resultadoBusca = clienteRepository.findById(id).orElseThrow(ClienteNaoExisteException::new);

            return Arrays.asList(modelMapper.map(resultadoBusca, ClienteReadDTO.class));
        } else {
            return clienteRepository.findAll().stream().map(cliente -> modelMapper.map(cliente, ClienteReadDTO.class)).toList();
        }
    }
}
