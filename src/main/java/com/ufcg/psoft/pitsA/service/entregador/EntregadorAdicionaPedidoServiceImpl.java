package com.ufcg.psoft.pitsA.service.entregador;

import com.ufcg.psoft.pitsA.model.Entregador;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;
import com.ufcg.psoft.pitsA.repository.EntregadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorAdicionaPedidoServiceImpl implements EntregadorAdicionaPedidoService {
    @Autowired
    EntregadorRepository entregadorRepository;

    public void adicionaPedidoEntregador(Entregador entregador, Pedido pedido) {
        entregador.adicionaPedido(pedido);
        entregadorRepository.save(entregador);
    }
}
