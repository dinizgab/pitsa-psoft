package com.ufcg.psoft.pitsA.service.pedido;

import com.ufcg.psoft.pitsA.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoRemoverServiceImpl implements PedidoRemoverService {
    @Autowired
    PedidoRepository pedidoRepository;

    @Override
    public void removerPedido(Long pedidoId) {
        pedidoRepository.deleteById(pedidoId);
    }
}
