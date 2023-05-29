package com.ufcg.psoft.pitsA.service.pedido;

import com.ufcg.psoft.pitsA.model.pedido.Pedido;
import com.ufcg.psoft.pitsA.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoCriarServiceImpl implements PedidoCriarService {
    @Autowired
    PedidoRepository pedidoRepository;

    @Override
    public Pedido criar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }
}
