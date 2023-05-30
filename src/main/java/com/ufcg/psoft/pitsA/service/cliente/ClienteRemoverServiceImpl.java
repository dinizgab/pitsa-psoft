package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.cliente.ClienteDeleteDTO;
import com.ufcg.psoft.pitsA.exception.cliente.ClienteNaoExisteException;
import com.ufcg.psoft.pitsA.model.cliente.Cliente;
import com.ufcg.psoft.pitsA.repository.ClienteRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteRemoverServiceImpl implements ClienteRemoverService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    AutenticaCodigoAcessoService autenticador;

    public void remover(Long id, ClienteDeleteDTO clienteDeleteDTO) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(ClienteNaoExisteException::new);
        autenticador.autenticar(cliente.getCodigoAcesso(), clienteDeleteDTO.getCodigoAcesso());

        clienteRepository.deleteById(id);
    }
}
