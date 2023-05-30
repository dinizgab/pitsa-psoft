package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoConfirmaEntregaDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;
import com.ufcg.psoft.pitsA.exception.cliente.ClienteNaoExisteException;
import com.ufcg.psoft.pitsA.model.cliente.Cliente;
import com.ufcg.psoft.pitsA.model.pedido.EstadoPedido;
import com.ufcg.psoft.pitsA.repository.ClienteRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import com.ufcg.psoft.pitsA.service.pedido.PedidoPatchEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteConfirmarEntregaPedidoServiceImpl implements ClienteConfirmarEntregaPedidoService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    PedidoPatchEstadoService pedidoPatchEstadoService;
    @Autowired
    AutenticaCodigoAcessoService autenticador;

    public PedidoReadResponseDTO confirmarEntrega(Long clienteId, PedidoConfirmaEntregaDTO patchBody) {
        Long pedidoId = patchBody.getPedidoId();
        String codigoAcesso = patchBody.getCodigoAcesso();

        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(ClienteNaoExisteException::new);
        autenticador.autenticar(cliente.getCodigoAcesso(), codigoAcesso);

        return pedidoPatchEstadoService.alteraEstado(pedidoId, EstadoPedido.ENTREGUE);
    }
}
