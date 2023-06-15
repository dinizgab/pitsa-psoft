package com.ufcg.psoft.pitsA.model.cliente;

import com.ufcg.psoft.pitsA.dto.entregador.EntregadorVeiculoDTO;

public abstract class InteressadoAdapter implements Interessado {
    @Override
    public void recebeNotificacaoDisponibilidade() {}

    @Override
    public void recebeNotificacaoPedidoEmRota(String nome, EntregadorVeiculoDTO veiculo) {}
}
