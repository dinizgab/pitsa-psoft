package com.ufcg.psoft.pitsA.service.pedido;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;
import com.ufcg.psoft.pitsA.exception.pedido.PedidoNaoEncontradoException;
import com.ufcg.psoft.pitsA.model.pedido.EstadoPedido;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;
import com.ufcg.psoft.pitsA.repository.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoPatchEstadoServiceImpl implements PedidoPatchEstadoService {
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    ModelMapper modelMapper;

    public PedidoReadResponseDTO alteraEstado(Long pedidoId, EstadoPedido estado) {
        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(PedidoNaoEncontradoException::new);

        if (estado.isEntregue()) {
            pedido.setEstadoEntregue();
        } else if (estado.isRota()) {
            pedido.setEstadoEmRota();
        } else {
            pedido.setEstado(estado);
        }

        return modelMapper.map(pedidoRepository.save(pedido), PedidoReadResponseDTO.class);
    }
}
