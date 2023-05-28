package com.ufcg.psoft.pitsA.service.pedido;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;
import com.ufcg.psoft.pitsA.repository.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoAtualizarServiceImpl implements PedidoAtualizarService {
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    ModelMapper modelMapper;
    public PedidoReadResponseDTO atualizarPedido(Pedido pedido) {
        return modelMapper.map(pedidoRepository.save(pedido), PedidoReadResponseDTO.class);
    }
}
