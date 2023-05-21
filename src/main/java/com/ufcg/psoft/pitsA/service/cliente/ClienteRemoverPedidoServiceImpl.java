package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.pedido.ClienteRemoverPedidoDTO;
import com.ufcg.psoft.pitsA.exception.cliente.ClienteNaoExisteException;
import com.ufcg.psoft.pitsA.model.Cliente;
import com.ufcg.psoft.pitsA.repository.ClienteRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoRemoverPedidoService;
import com.ufcg.psoft.pitsA.service.pedido.PedidoRemoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteRemoverPedidoServiceImpl implements ClienteRemoverPedidoService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    PedidoRemoverService pedidoRemoverService;
    @Autowired
    EstabelecimentoRemoverPedidoService estabelecimentoRemoverPedidoService;
    @Autowired
    AutenticaCodigoAcessoService autenticador;

    @Override
    public void removerPedido(Long clienteId, ClienteRemoverPedidoDTO removeBody) {
        Long pedidoId = removeBody.getPedidoId();
        Long estabelecimentoId = removeBody.getEstabelecimentoId();
        String codigoAcesso = removeBody.getCodigoAcesso();

        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(ClienteNaoExisteException::new);
        autenticador.autenticar(cliente.getCodigoAcesso(), codigoAcesso);

        cliente.getPedidos().removeIf(pedido -> pedido.getId().equals(pedidoId));
        clienteRepository.save(cliente);

        estabelecimentoRemoverPedidoService.removerPedido(estabelecimentoId, pedidoId);
        pedidoRemoverService.removerPedido(pedidoId);
    }
}
