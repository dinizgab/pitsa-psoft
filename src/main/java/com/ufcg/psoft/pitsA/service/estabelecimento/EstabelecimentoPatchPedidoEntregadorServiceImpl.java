package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoPatchEntregadorDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;
import com.ufcg.psoft.pitsA.exception.entregador.EntregadorNaoEstaAprovadoException;
import com.ufcg.psoft.pitsA.exception.estabelecimento.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.pitsA.exception.pedido.PedidoNaoEncontradoException;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.entregador.Entregador;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import com.ufcg.psoft.pitsA.service.entregador.EntregadorAdicionaPedidoService;
import com.ufcg.psoft.pitsA.service.pedido.PedidoPatchEntregadorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoPatchPedidoEntregadorServiceImpl implements EstabelecimentoPatchPedidoEntregadorService {
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    PedidoPatchEntregadorService pedidoPatchEntregadorService;
    @Autowired
    EntregadorAdicionaPedidoService entregadorAdicionaPedidoService;
    @Autowired
    AutenticaCodigoAcessoService autenticador;
    @Autowired
    ModelMapper modelMapper;

    public PedidoReadResponseDTO alterarEntregador(Long estabelecimentoId, PedidoPatchEntregadorDTO patchBody) {
        String codigoAcesso = patchBody.getCodigoAcesso();
        Long entregadorId = patchBody.getEntregadorId();
        Long pedidoId = patchBody.getPedidoId();

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId).orElseThrow(EstabelecimentoNaoExisteException::new);
        autenticador.autenticar(estabelecimento.getCodigoAcesso(), codigoAcesso);

        Entregador resultadoEntregador = estabelecimento
                .getEntregadoresAprovados()
                .stream().filter(entregador -> entregador.getId().equals(entregadorId))
                .findFirst().orElseThrow(EntregadorNaoEstaAprovadoException::new);

        Pedido resultadoPedido = estabelecimento
                .getPedidos()
                .stream().filter(pedido -> pedido.getId().equals(pedidoId))
                .findFirst().orElseThrow(PedidoNaoEncontradoException::new);


        entregadorAdicionaPedidoService.adicionaPedidoEntregador(resultadoEntregador, resultadoPedido);
        return pedidoPatchEntregadorService.alterarEntregador(resultadoPedido, resultadoEntregador);
    }
}
