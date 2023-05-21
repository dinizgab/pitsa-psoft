package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoListarDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadDTO;
import com.ufcg.psoft.pitsA.exception.estabelecimento.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.pitsA.exception.pedido.PedidoNaoEncontradoException;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstabelecimentoListarPedidoServiceImpl implements EstabelecimentoListarPedidoService {
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    AutenticaCodigoAcessoService autenticador;
    @Autowired
    ModelMapper modelMapper;

    public List<PedidoReadDTO> listarPedidos(Long estabelecimentoId, PedidoListarDTO listarPedidoDTO) {
        Long pedidoId = listarPedidoDTO.getPedidoId();
        String codigoAcesso = listarPedidoDTO.getCodigoAcesso();

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId).orElseThrow(EstabelecimentoNaoExisteException::new);
        autenticador.autenticar(estabelecimento.getCodigoAcesso(), codigoAcesso);

        if (pedidoId == null) {
            return estabelecimento.getPedidos()
                    .stream()
                    .map(pedido -> {
                        PedidoReadDTO parsedPedido = modelMapper.map(pedido, PedidoReadDTO.class);
                        parsedPedido.setValorTotal(pedido.calculaValorTotal());

                        return parsedPedido;
                    })
                    .collect(Collectors.toList());
        } else {
            Pedido resultado = estabelecimento.getPedidos()
                    .stream()
                    .filter(pedido -> pedido.getId().equals(pedidoId))
                    .findFirst()
                    .orElseThrow(PedidoNaoEncontradoException::new);

            PedidoReadDTO parsedPedido = modelMapper.map(resultado, PedidoReadDTO.class);
            parsedPedido.setValorTotal(resultado.calculaValorTotal());

            return List.of(parsedPedido);
        }
    }
}
