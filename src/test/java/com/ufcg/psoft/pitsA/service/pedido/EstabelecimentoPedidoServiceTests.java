package com.ufcg.psoft.pitsA.service.pedido;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadBodyDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;
import com.ufcg.psoft.pitsA.model.sabor.SaborPedido;
import com.ufcg.psoft.pitsA.model.Cliente;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;
import com.ufcg.psoft.pitsA.model.pedido.PizzaPedidoTamanho;
import com.ufcg.psoft.pitsA.model.pedido.PizzaPedidoTipo;
import com.ufcg.psoft.pitsA.model.sabor.Sabor;
import com.ufcg.psoft.pitsA.model.sabor.TipoSabor;
import com.ufcg.psoft.pitsA.repository.ClienteRepository;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import com.ufcg.psoft.pitsA.repository.PedidoRepository;
import com.ufcg.psoft.pitsA.repository.SaborRepository;
import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoListarPedidoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Testes para os services de pedido com estabelecimento")
public class EstabelecimentoPedidoServiceTests {
    @Autowired
    EstabelecimentoListarPedidoService driverListar;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    SaborRepository saborRepository;
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ModelMapper modelMapper;
    Estabelecimento estabelecimento;
    Cliente cliente;
    Long pedidoId;

    @BeforeEach
    void setUp() {
        cliente = clienteRepository.save(
                Cliente.builder()
                        .nome("Gabriel")
                        .endereco("Rua 13 de maio, 123")
                        .codigoAcesso("123456")
                        .build()
        );

        estabelecimento = Estabelecimento.builder()
                .codigoAcesso("123456")
                .build();

        Sabor sabor1 = Sabor.builder()
                .nome("Calabresa")
                .tipo(TipoSabor.SALGADO)
                .precoGrande(35.0)
                .precoMedio(30.0)
                .estabelecimento(estabelecimento)
                .build();

        Sabor sabor2 = Sabor.builder()
                .nome("4 Queijos")
                .tipo(TipoSabor.SALGADO)
                .precoGrande(55.0)
                .precoMedio(30.0)
                .estabelecimento(estabelecimento)
                .build();

        estabelecimento.getCardapio().add(sabor1);
        estabelecimento.getCardapio().add(sabor2);
        estabelecimentoRepository.save(estabelecimento);
        saborRepository.saveAll(Arrays.asList(sabor1, sabor2));

        Pedido pedido1 = pedidoRepository.save(Pedido.builder()
                .endereco("Rua 13 de maio, 123")
                .tamanho(PizzaPedidoTamanho.GRANDE)
                .tipo(PizzaPedidoTipo.MEIA)
                .sabores(new ArrayList<>())
                .estabelecimentoPedido(estabelecimento)
                .cliente(cliente)
                .build());

        Pedido pedido2 = pedidoRepository.save(Pedido.builder()
                .endereco("")
                .tamanho(PizzaPedidoTamanho.MEDIA)
                .tipo(PizzaPedidoTipo.INTEIRA)
                .sabores(new ArrayList<>())
                .estabelecimentoPedido(estabelecimento)
                .cliente(cliente)
                .build());

        pedidoId = pedido1.getId();
        pedido1.getSabores().add(modelMapper.map(sabor1, SaborPedido.class));
        pedido1.getSabores().add(modelMapper.map(sabor2, SaborPedido.class));
        pedido2.getSabores().add(modelMapper.map(sabor2, SaborPedido.class));

        estabelecimento.getPedidos().add(pedido1);
        estabelecimento.getPedidos().add(pedido2);
        estabelecimentoRepository.save(estabelecimento);

        cliente.getPedidos().add(pedido1);
        cliente.getPedidos().add(pedido2);
        clienteRepository.save(cliente);
    }

    @AfterEach
    void tearDown() {
        saborRepository.deleteAll();
        pedidoRepository.deleteAll();
        clienteRepository.deleteAll();
        estabelecimentoRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("Teste de listagem de todos os pedidos do estabelecimento")
    void testeListarTodosPedidosEstabelecimento() {
        Long estabelecimentoId = estabelecimento.getId();

        PedidoReadBodyDTO listarBody = PedidoReadBodyDTO.builder()
                .pedidoId(null)
                .codigoAcesso("123456")
                .build();

        List<PedidoReadResponseDTO> resultado = driverListar.listarPedidos(estabelecimentoId, listarBody);
        assertEquals(2, resultado.size());
    }

    @Test
    @Transactional
    @DisplayName("Teste de listagem de pedido pelo id")
    void testeListarPedidoIdEstabelecimento() {
        Long estabelecimentoId = estabelecimento.getId();

        PedidoReadBodyDTO listarBody = PedidoReadBodyDTO.builder()
                .pedidoId(pedidoId)
                .codigoAcesso("123456")
                .build();

        PedidoReadResponseDTO resultado = driverListar.listarPedidos(estabelecimentoId, listarBody).get(0);

        assertAll(
                () -> assertEquals("Rua 13 de maio, 123", resultado.getEndereco()),
                () -> assertTrue(resultado.getTipo().isMeia()),
                () -> assertTrue(resultado.getTamanho().isGrande()),
                () -> assertEquals(45.0, resultado.getValorTotal())
        );
    }
}
