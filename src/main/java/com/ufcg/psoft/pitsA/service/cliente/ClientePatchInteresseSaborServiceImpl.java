package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.cliente.ClienteInteresseDTO;
import com.ufcg.psoft.pitsA.dto.cliente.ClienteReadDTO;
import com.ufcg.psoft.pitsA.exception.cliente.ClienteNaoExisteException;
import com.ufcg.psoft.pitsA.exception.sabor.SaborEstaDisponivelException;
import com.ufcg.psoft.pitsA.model.Cliente;
import com.ufcg.psoft.pitsA.model.Sabor;
import com.ufcg.psoft.pitsA.repository.ClienteRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoAtualizaSaborService;
import com.ufcg.psoft.pitsA.service.sabor.SaborListarService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientePatchInteresseSaborServiceImpl implements ClientePatchInteresseSaborService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    SaborListarService saborListarService;
    @Autowired
    EstabelecimentoAtualizaSaborService estabelecimentoAtualizaSaborService;

    @Autowired
    AutenticaCodigoAcessoService autenticador;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public ClienteReadDTO demonstraInteresse(Long clienteId, ClienteInteresseDTO clienteInteresseDTO) {
        Long saborId = clienteInteresseDTO.getSaborId();
        Long estabelecimentoId = clienteInteresseDTO.getEstabelecimentoId();

        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(ClienteNaoExisteException::new);
        autenticador.autenticar(cliente.getCodigoAcesso(), clienteInteresseDTO.getCodigoAcesso());

        Sabor sabor = saborListarService.listar(clienteInteresseDTO.getSaborId()).get(0);
        if (sabor.isDisponivel()) throw new SaborEstaDisponivelException();

        cliente.getInteressesSabores().add(sabor);
        sabor.getInteresses().add(cliente);

        estabelecimentoAtualizaSaborService.atualizaSabor(estabelecimentoId, sabor);

        return modelMapper.map(clienteRepository.save(cliente), ClienteReadDTO.class);
    }
}
