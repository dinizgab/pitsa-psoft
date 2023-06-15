package com.ufcg.psoft.pitsA.model.cliente;

import com.ufcg.psoft.pitsA.dto.entregador.EntregadorVeiculoDTO;

public interface Interessado {
    void recebeNotificacaoDisponibilidade();

    void recebeNotificacaoPedidoEmRota(String nome, EntregadorVeiculoDTO veiculo);
}
