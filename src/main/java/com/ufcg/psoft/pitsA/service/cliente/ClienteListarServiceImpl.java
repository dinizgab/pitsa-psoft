package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.cliente.ClienteReadDTO;
import com.ufcg.psoft.pitsA.exception.cliente.ClienteNaoExisteException;
import com.ufcg.psoft.pitsA.model.cliente.Cliente;
import com.ufcg.psoft.pitsA.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

            return Collections.singletonList(modelMapper.map(resultadoBusca, ClienteReadDTO.class));
        } else {
            List<Cliente> resultadoBusca = clienteRepository.findAll();

            return resultadoBusca
                    .stream().map(cliente -> modelMapper.map(cliente, ClienteReadDTO.class))
                    .toList();
        }
    }
}
