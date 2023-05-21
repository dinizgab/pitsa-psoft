package com.ufcg.psoft.pitsA.service.estabelecimento;

@FunctionalInterface
public interface EstabelecimentoRemoverPedidoService {
    void removerPedido(Long estabelecimentoId, Long pedidoId);
}
