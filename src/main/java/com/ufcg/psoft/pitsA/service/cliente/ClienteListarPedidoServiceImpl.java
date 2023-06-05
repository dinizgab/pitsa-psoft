package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadBodyDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;
import com.ufcg.psoft.pitsA.exception.cliente.ClienteNaoExisteException;
import com.ufcg.psoft.pitsA.exception.pedido.PedidoNaoEncontradoException;
import com.ufcg.psoft.pitsA.model.cliente.Cliente;
import com.ufcg.psoft.pitsA.model.pedido.EstadoPedido;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;
import com.ufcg.psoft.pitsA.repository.ClienteRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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

    public List<PedidoReadResponseDTO> listarPedidos(Long clienteId, PedidoReadBodyDTO listarPedidoDTO) {
        Long pedidoId = listarPedidoDTO.getPedidoId();
        String codigoAcesso = listarPedidoDTO.getCodigoAcesso();
        EstadoPedido estadoFiltro = listarPedidoDTO.getEstadoFiltro();

        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(ClienteNaoExisteException::new);
        autenticador.autenticar(cliente.getCodigoAcesso(), codigoAcesso);

        if (pedidoId == null) {
            List<PedidoReadResponseDTO> pedidos = cliente.getPedidos()
                    .stream()
                    .sorted(Comparator.comparing(p -> p.getEstado().ordinal()))
                    .map(p -> {
                        PedidoReadResponseDTO parsedPedido = modelMapper.map(p, PedidoReadResponseDTO.class);
                        parsedPedido.setValorTotal(p.calculaValorTotal());

                        return parsedPedido;
                    })
                    .toList();

            return estadoFiltro != null ?
                    pedidos
                    .stream()
                            .filter(p -> p.getEstado().equals(estadoFiltro))
                            .collect(Collectors.toList())
                    : pedidos;
        } else {
            Pedido resultado = cliente.getPedidos()
                    .stream()
                    .filter(pedido -> pedido.getId().equals(pedidoId))
                    .findFirst()
                    .orElseThrow(PedidoNaoEncontradoException::new);

            PedidoReadResponseDTO parsedPedido = modelMapper.map(resultado, PedidoReadResponseDTO.class);
            parsedPedido.setValorTotal(resultado.calculaValorTotal());

            return List.of(parsedPedido);
        }
    }
}
