package com.ufcg.psoft.pitsA.service.pedido;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoValidaDTO;
import com.ufcg.psoft.pitsA.exception.pedido.TamanhoPedidoInvalidosException;
import com.ufcg.psoft.pitsA.model.pedido.PizzaPedidoTamanho;
import com.ufcg.psoft.pitsA.model.pedido.PizzaPedidoTipo;
import org.springframework.stereotype.Service;

@Service
public class ValidaPedidoServiceImpl implements ValidaPedidoService {

    public void validaPedido(PedidoValidaDTO validaDTO) throws TamanhoPedidoInvalidosException {
        PizzaPedidoTipo tipoPedido = validaDTO.getTipoPedido();
        PizzaPedidoTamanho tamanho = validaDTO.getTamanho();
        Integer quantidadeSabores = validaDTO.getQuantidadeSabores();

        if ((quantidadeSabores == 2 || tipoPedido.isMeia()) && tamanho.isMedia())
            throw new TamanhoPedidoInvalidosException();
    }
}
