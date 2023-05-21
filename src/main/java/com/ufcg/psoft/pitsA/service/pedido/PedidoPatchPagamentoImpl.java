package com.ufcg.psoft.pitsA.service.pedido;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;
import com.ufcg.psoft.pitsA.exception.pedido.PedidoNaoEncontradoException;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;
import com.ufcg.psoft.pitsA.model.pedido.TipoPagamento;
import com.ufcg.psoft.pitsA.repository.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoPatchPagamentoImpl implements PedidoPatchPagamento {
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public PedidoReadResponseDTO alterarPagamento(Long pedidoId, TipoPagamento tipoPagamento) {
        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(PedidoNaoEncontradoException::new);

        pedido.setTipoPagamento(tipoPagamento);

        PedidoReadResponseDTO pedidoAlterado = modelMapper.map(pedidoRepository.save(pedido), PedidoReadResponseDTO.class);
        pedidoAlterado.setValorTotal(pedido.calculaValorTotal());

        return pedidoAlterado;
    }
}
