package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.exception.estabelecimento.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoRemoverPedidoServiceImpl implements EstabelecimentoRemoverPedidoService {
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public void removerPedido(Long estabelecimendoId, Long pedidoId) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimendoId).orElseThrow(EstabelecimentoNaoExisteException::new);

        estabelecimento.getPedidos().removeIf(pedido -> pedido.getId().equals(pedidoId));
        estabelecimentoRepository.save(estabelecimento);
    }
}
