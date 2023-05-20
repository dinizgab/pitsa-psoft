package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.pedido.ClienteListarPedidoDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadDTO;
import com.ufcg.psoft.pitsA.exception.cliente.ClienteNaoExisteException;
import com.ufcg.psoft.pitsA.exception.pedido.PedidoNaoEncontradoException;
import com.ufcg.psoft.pitsA.model.Cliente;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;
import com.ufcg.psoft.pitsA.repository.ClienteRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteListarPedidoServiceImpl implements ClienteListarPedidoService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    AutenticaCodigoAcessoService autenticador;
    @Autowired
    ModelMapper modelMapper;

    public List<PedidoReadDTO> listarPedidos(Long clienteId, ClienteListarPedidoDTO listarPedidoDTO) {
        Long pedidoId = listarPedidoDTO.getPedidoId();
        String codigoAcesso = listarPedidoDTO.getCodigoAcesso();

        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(ClienteNaoExisteException::new);
        autenticador.autenticar(cliente.getCodigoAcesso(), codigoAcesso);

        if (pedidoId == null) {
            return cliente.getPedidos()
                    .stream()
                    .map(pedido -> {
                        PedidoReadDTO parsedPedido = modelMapper.map(pedido, PedidoReadDTO.class);
                        parsedPedido.setValorTotal(pedido.calculaValorTotal());

                        return parsedPedido;
                    })
                    .collect(Collectors.toList());
        } else {
            Pedido resultado = cliente.getPedidos()
                    .stream()
                    .filter(pedido -> pedido.getId().equals(pedidoId))
                    .findFirst()
                    .orElseThrow(PedidoNaoEncontradoException::new);

            PedidoReadDTO parsedPedido = modelMapper.map(resultado, PedidoReadDTO.class);
            parsedPedido.setValorTotal(resultado.calculaValorTotal());

            return List.of(parsedPedido);
        }
    }
}
