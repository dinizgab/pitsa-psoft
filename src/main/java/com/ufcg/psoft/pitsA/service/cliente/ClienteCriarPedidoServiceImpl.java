package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoPostDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoValidaDTO;
import com.ufcg.psoft.pitsA.exception.cliente.ClienteNaoExisteException;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.cliente.Cliente;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;
import com.ufcg.psoft.pitsA.model.pedido.PizzaPedidoTamanho;
import com.ufcg.psoft.pitsA.model.pedido.PizzaPedidoTipo;
import com.ufcg.psoft.pitsA.model.sabor.Sabor;
import com.ufcg.psoft.pitsA.repository.ClienteRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoAdicionaPedidoService;
import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoListarService;
import com.ufcg.psoft.pitsA.service.pedido.PedidoCriarService;
import com.ufcg.psoft.pitsA.service.pedido.ValidaPedidoService;
import com.ufcg.psoft.pitsA.service.sabor.SaborListarMultiploService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteCriarPedidoServiceImpl implements ClienteCriarPedidoService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    PedidoCriarService pedidoCriarService;
    @Autowired
    EstabelecimentoListarService estabelecimentoListarService;
    @Autowired
    EstabelecimentoAdicionaPedidoService estabelecimentoAdicionaPedidoService;
    @Autowired
    ValidaPedidoService validaPedidoService;
    @Autowired
    SaborListarMultiploService saborListarMultiploService;
    @Autowired
    AutenticaCodigoAcessoService autenticador;
    @Autowired
    ModelMapper modelMapper;

    @Override
    @Transactional
    public PedidoReadResponseDTO criarPedido(Long id, PedidoPostDTO postBody) {
        Long estabelecimentoId = postBody.getIdEstabelecimento();
        String codigoAcesso = postBody.getCodigoAcesso();
        String endereco = postBody.getEndereco();
        PizzaPedidoTipo tipoPedido = postBody.getTipo();
        PizzaPedidoTamanho tamanho = postBody.getTamanho();
        List<Long> saboresId = postBody.getSaboresId();

        Cliente cliente = clienteRepository.findById(id).orElseThrow(ClienteNaoExisteException::new);
        autenticador.autenticar(cliente.getCodigoAcesso(), codigoAcesso);

        List<Sabor> sabores = saborListarMultiploService.listarMultiplos(saboresId);
        PedidoValidaDTO validaDTO = PedidoValidaDTO.builder()
                        .tipoPedido(tipoPedido)
                        .tamanho(tamanho)
                        .quantidadeSabores(sabores.size())
                        .build();
        validaPedidoService.validaPedido(validaDTO);

        Estabelecimento estabelecimento = estabelecimentoListarService.listar(estabelecimentoId).get(0);
        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .estabelecimentoPedido(estabelecimento)
                .tipo(tipoPedido)
                .tamanho(tamanho)
                .sabores(sabores)
                .build();

        if (endereco.equals("")) {
            pedido.setEndereco(cliente.getEndereco());
        } else {
            pedido.setEndereco(endereco);
        }

        Pedido pedidoSalvo = pedidoCriarService.criar(pedido);

        cliente.getPedidos().add(pedidoSalvo);
        clienteRepository.save(cliente);

        estabelecimentoAdicionaPedidoService.adicionaPedido(estabelecimento, pedidoSalvo);

        Double valorTotal = pedidoSalvo.calculaValorTotal();
        PedidoReadResponseDTO parsedPedido = modelMapper.map(pedidoSalvo, PedidoReadResponseDTO.class);
        parsedPedido.setValorTotal(valorTotal);

        return parsedPedido;
    }
}
