package com.ufcg.psoft.pitsA.service.pedido;

import com.ufcg.psoft.pitsA.exception.pedido.PedidoJaEstaProntoException;
import com.ufcg.psoft.pitsA.exception.pedido.PedidoNaoEncontradoException;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;
import com.ufcg.psoft.pitsA.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoRemoverServiceImpl implements PedidoRemoverService {
    @Autowired
    PedidoRepository pedidoRepository;

    @Override
    public void removerPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(PedidoNaoEncontradoException::new);
        if (pedido.getEstado().isPronto() || pedido.getEstado().isRota() || pedido.getEstado().isEntregue())
            throw new PedidoJaEstaProntoException();

        pedidoRepository.deleteById(pedidoId);
    }
}
