package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoAdicionaPedidoServiceImpl implements EstabelecimentoAdicionaPedidoService {
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    public void adicionaPedido(Estabelecimento estabelecimento, Pedido pedido) {
        estabelecimento.getPedidos().add(pedido);

        estabelecimentoRepository.save(estabelecimento);
    }
}
