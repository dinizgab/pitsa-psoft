package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.cliente.ClienteCardapioDTO;
import com.ufcg.psoft.pitsA.dto.sabor.SaborReadDTO;
import com.ufcg.psoft.pitsA.exception.cliente.ClienteNaoExisteException;
import com.ufcg.psoft.pitsA.model.Cliente;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.Sabor;
import com.ufcg.psoft.pitsA.repository.ClienteRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoListarService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteListarCardapioServiceImpl implements ClienteListarCardapioService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    EstabelecimentoListarService estabelecimentoListarService;
    @Autowired
    AutenticaCodigoAcessoService autenticador;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<SaborReadDTO> listarCardapio(Long clienteId, ClienteCardapioDTO clienteCardapioDTO) {
        Long estabelecimentoId = clienteCardapioDTO.getEstabelecimentoId();
        Cliente clienteCadastrado = clienteRepository.findById(clienteId).orElseThrow(ClienteNaoExisteException::new);

        autenticador.autenticar(clienteCadastrado.getCodigoAcesso(), clienteCardapioDTO.getCodigoAcesso());

        Estabelecimento estabelecimento = estabelecimentoListarService.listar(estabelecimentoId).get(0);

        return estabelecimento.getCardapio()
                .stream()
                .map(sabor -> modelMapper.map(sabor, SaborReadDTO.class))
                .sorted(Comparator.comparing(SaborReadDTO::isDisponivel)
                        .reversed())
        .collect(Collectors.toList());
    }
}
