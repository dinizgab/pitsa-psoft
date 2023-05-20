package com.ufcg.psoft.pitsA.service.pedido;

import com.ufcg.psoft.pitsA.dto.pedido.PedidoPostDTO;
import com.ufcg.psoft.pitsA.dto.pedido.SaborPedidoDTO;
import com.ufcg.psoft.pitsA.model.Cliente;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.sabor.Sabor;
import com.ufcg.psoft.pitsA.model.sabor.TipoSabor;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;
import com.ufcg.psoft.pitsA.model.pedido.PizzaPedidoTamanho;
import com.ufcg.psoft.pitsA.model.pedido.PizzaPedidoTipo;
import com.ufcg.psoft.pitsA.repository.ClienteRepository;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import com.ufcg.psoft.pitsA.repository.PedidoRepository;
import com.ufcg.psoft.pitsA.repository.SaborRepository;
import com.ufcg.psoft.pitsA.service.cliente.ClienteCriarPedidoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Testes para os services de pedido")
public class PedidoCriarServiceTests {
    @Autowired
    ClienteCriarPedidoService driverCriar;
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    SaborRepository saborRepository;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ModelMapper modelMapper;
    Cliente cliente;
    Estabelecimento estabelecimento;
    PedidoPostDTO pedidoInteira;
    PedidoPostDTO pedidoMeia;

    @BeforeEach
    void setUp() {
        estabelecimento = Estabelecimento.builder()
                        .codigoAcesso("123456")
                        .build();

        Sabor sabor1 = Sabor.builder()
                .nome("Calabresa")
                .tipo(TipoSabor.SALGADO)
                .precoGrande(55.0)
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

        Sabor sabor3 = Sabor.builder()
                .nome("Chocolate")
                .tipo(TipoSabor.DOCE)
                .precoGrande(45.0)
                .precoMedio(25.0)
                .estabelecimento(estabelecimento)
                .build();

        cliente = clienteRepository.save(
                Cliente.builder()
                        .nome("Gabriel")
                        .endereco("Rua 13 de maio, 123")
                        .codigoAcesso("123456")
                        .build()
        );

        estabelecimento.getCardapio().add(sabor1);
        estabelecimento.getCardapio().add(sabor2);
        estabelecimento.getCardapio().add(sabor3);
        estabelecimentoRepository.save(estabelecimento);
        saborRepository.saveAll(Arrays.asList(sabor1, sabor2, sabor3));

        pedidoInteira = PedidoPostDTO.builder()
                .codigoAcesso(cliente.getCodigoAcesso())
                .idEstabelecimento(estabelecimento.getId())
                .endereco("Rua 20 de novembro, 321")
                .tamanho(PizzaPedidoTamanho.GRANDE)
                .tipo(PizzaPedidoTipo.INTEIRA)
                .build();
        pedidoInteira.getSabores().add(modelMapper.map(sabor3, SaborPedidoDTO.class));

        pedidoMeia = PedidoPostDTO.builder()
                .codigoAcesso(cliente.getCodigoAcesso())
                .idEstabelecimento(estabelecimento.getId())
                .endereco("")
                .tamanho(PizzaPedidoTamanho.GRANDE)
                .tipo(PizzaPedidoTipo.MEIA)
                .build();

        pedidoMeia.getSabores().add(modelMapper.map(sabor1, SaborPedidoDTO.class));
        pedidoMeia.getSabores().add(modelMapper.map(sabor1, SaborPedidoDTO.class));
    }

    @AfterEach
    void tearDown() {
        pedidoRepository.deleteAll();
        estabelecimentoRepository.deleteAll();
        saborRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("Quando criamos um novo pedido de uma pizza inteira")
    void testeCriandoNovoPedidoPizzaInteira() {
        Long clienteId = cliente.getId();
        Pedido resultado = driverCriar.criarPedido(clienteId, pedidoInteira);

        assertAll(
                () -> assertEquals("Rua 20 de novembro, 321", resultado.getEndereco()),
                () -> assertTrue(resultado.getTamanho().isGrande()),
                () -> assertTrue(resultado.getTipo().isInteira()),
                () -> assertEquals(1, resultado.getSabores().size()),
                () -> assertTrue(cliente.getPedidos().contains(resultado)),
                () -> assertTrue(estabelecimento.getPedidos().contains(resultado))

        );
    }

    @Test
    @Transactional
    @DisplayName("Quando criamos um novo pedido de uma pizza meio a meio")
    void testeCriandoNovoPedidoPizzaMeio() {
        Long clienteId = cliente.getId();
        Pedido resultado = driverCriar.criarPedido(clienteId, pedidoMeia);

        assertAll(
                () -> assertEquals(cliente.getEndereco(), resultado.getEndereco()),
                () -> assertTrue(resultado.getTamanho().isGrande()),
                () -> assertTrue(resultado.getTipo().isMeia()),
                () -> assertEquals(2, resultado.getSabores().size()),
                () -> assertTrue(cliente.getPedidos().contains(resultado)),
                () -> assertTrue(estabelecimento.getPedidos().contains(resultado))
        );
    }
}

// Pedido
// List<SaborPedido>, Endereco, Cliente, CalculaValor

// CalculaValor calcula o valor total do pedido



// SaborPedido
// List<Sabor>, Tamanho, Valor

// Valor sera calculado de acordo com a lista de pizzas
// Sera uma Lista de Sabor, porque se o tamanho for grande ela pode ter mais de um sabor;

// TODO
// Adicionar codigo de acesso do cliente no momento que o cliente for fazer um pedido ao estabelecimento
// Se endereco principal nao for informado, o pedido deve ter o endereco que esta no cliente