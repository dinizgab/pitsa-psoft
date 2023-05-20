package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoPostDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoValidaDTO;
import com.ufcg.psoft.pitsA.dto.pedido.SaborPedidoDTO;
import com.ufcg.psoft.pitsA.exception.cliente.ClienteNaoExisteException;
import com.ufcg.psoft.pitsA.exception.estabelecimento.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.pitsA.model.Cliente;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;
import com.ufcg.psoft.pitsA.model.pedido.PizzaPedidoTamanho;
import com.ufcg.psoft.pitsA.model.pedido.PizzaPedidoTipo;
import com.ufcg.psoft.pitsA.repository.ClienteRepository;
import com.ufcg.psoft.pitsA.repository.PedidoRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoAdicionaPedidoService;
import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoListarService;
import com.ufcg.psoft.pitsA.service.pedido.PedidoCriarService;
import com.ufcg.psoft.pitsA.service.pedido.ValidaPedidoService;
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
    AutenticaCodigoAcessoService autenticador;

    @Override
    public Pedido criarPedido(Long id, PedidoPostDTO postBody) {
        Long estabelecimentoId = postBody.getIdEstabelecimento();
        String codigoAcesso = postBody.getCodigoAcesso();
        String endereco = postBody.getEndereco();
        PizzaPedidoTipo tipoPedido = postBody.getTipo();
        PizzaPedidoTamanho tamanho = postBody.getTamanho();
        List<SaborPedidoDTO> sabores = postBody.getSabores();

        Cliente cliente = clienteRepository.findById(id).orElseThrow(ClienteNaoExisteException::new);

        PedidoValidaDTO validaDTO = PedidoValidaDTO.builder()
                        .tipoPedido(tipoPedido)
                        .tamanho(tamanho)
                        .quantidadeSabores(sabores.size())
                        .build();

        autenticador.autenticar(cliente.getCodigoAcesso(), codigoAcesso);
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
        return pedidoSalvo;
    }
}
