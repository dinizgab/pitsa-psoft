package com.ufcg.psoft.pitsA.service.pedido;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;
import com.ufcg.psoft.pitsA.model.Entregador;
import com.ufcg.psoft.pitsA.model.pedido.EstadoPedido;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;
import com.ufcg.psoft.pitsA.repository.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoPatchEntregadorServiceImpl implements PedidoPatchEntregadorService {
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    ModelMapper modelMapper;

    public PedidoReadResponseDTO alterarEntregador(Pedido pedido, Entregador entregador) {
        pedido.setEntregador(entregador);
        pedido.setEstado(EstadoPedido.EM_ROTA);

        return modelMapper.map(pedidoRepository.save(pedido), PedidoReadResponseDTO.class);
    }
}
