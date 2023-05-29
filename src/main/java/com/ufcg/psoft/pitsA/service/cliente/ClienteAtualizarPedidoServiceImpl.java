package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoPutDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;
import com.ufcg.psoft.pitsA.exception.cliente.ClienteNaoExisteException;
import com.ufcg.psoft.pitsA.exception.pedido.PedidoNaoEncontradoException;
import com.ufcg.psoft.pitsA.model.Cliente;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;
import com.ufcg.psoft.pitsA.repository.ClienteRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import com.ufcg.psoft.pitsA.service.pedido.PedidoAtualizarService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteAtualizarPedidoServiceImpl implements ClienteAtualizarPedidoService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    PedidoAtualizarService pedidoAtualizarService;
    @Autowired
    AutenticaCodigoAcessoService autenticador;
    @Autowired
    ModelMapper modelMapper;

    public PedidoReadResponseDTO atualizarPedido(Long clienteId, PedidoPutDTO putBody) {
        Long pedidoId = putBody.getPedidoId();

        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(ClienteNaoExisteException::new);
        autenticador.autenticar(cliente.getCodigoAcesso(), putBody.getCodigoAcesso());

        Pedido pedido = cliente.getPedidos()
                .stream()
                .filter(p -> p.getId().equals(pedidoId))
                .findFirst()
                .orElseThrow(PedidoNaoEncontradoException::new);

        pedido.setEndereco(putBody.getEndereco());
        pedido.setTipo(putBody.getTipo());
        pedido.setTamanho(putBody.getTamanho());

        return pedidoAtualizarService.atualizarPedido(pedido);
    }
}
