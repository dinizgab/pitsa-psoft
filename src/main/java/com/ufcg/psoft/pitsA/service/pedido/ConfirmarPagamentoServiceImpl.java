package com.ufcg.psoft.pitsA.service.pedido;

import com.ufcg.psoft.pitsA.dto.pedido.ConfirmarPagamentoDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;
import com.ufcg.psoft.pitsA.exception.cliente.ClienteNaoExisteException;
import com.ufcg.psoft.pitsA.model.cliente.Cliente;
import com.ufcg.psoft.pitsA.model.pedido.TipoPagamento;
import com.ufcg.psoft.pitsA.repository.ClienteRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmarPagamentoServiceImpl implements ConfirmarPagamentoService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    PedidoPatchPagamento pedidoPatchPagamento;
    @Autowired
    AutenticaCodigoAcessoService autenticador;

    @Override
    public PedidoReadResponseDTO confirmarPagamento(Long pedidoId, ConfirmarPagamentoDTO confirmarBody) {
        Long clienteId = confirmarBody.getClienteId();
        String codigoAcesso = confirmarBody.getCodigoAcesso();
        TipoPagamento tipoPagamento = confirmarBody.getTipoPagamento();

        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(ClienteNaoExisteException::new);
        autenticador.autenticar(cliente.getCodigoAcesso(), codigoAcesso);

        return pedidoPatchPagamento.alterarPagamento(pedidoId, tipoPagamento);
    }
}
