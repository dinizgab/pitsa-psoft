package com.ufcg.psoft.pitsA.service.pedido;

import com.ufcg.psoft.pitsA.dto.entregador.EntregadorReadDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoPatchEntregadorDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadBodyDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;
import com.ufcg.psoft.pitsA.exception.auth.CodigoAcessoInvalidoException;
import com.ufcg.psoft.pitsA.exception.entregador.EntregadorNaoEstaAprovadoException;
import com.ufcg.psoft.pitsA.exception.pedido.PedidoNaoEncontradoException;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.cliente.Cliente;
import com.ufcg.psoft.pitsA.model.entregador.Entregador;
import com.ufcg.psoft.pitsA.model.entregador.TipoVeiculoEntregador;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;
import com.ufcg.psoft.pitsA.model.pedido.PizzaPedidoTamanho;
import com.ufcg.psoft.pitsA.model.pedido.PizzaPedidoTipo;
import com.ufcg.psoft.pitsA.model.sabor.Sabor;
import com.ufcg.psoft.pitsA.model.sabor.TipoSabor;
import com.ufcg.psoft.pitsA.repository.*;
import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoListarPedidoService;
import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoPatchPedidoEntregadorService;
import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoPatchPedidoEstadoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Testes para os services de pedido com estabelecimento")
public class EstabelecimentoPedidoServiceTests {
    @Autowired
    EstabelecimentoListarPedidoService driverListar;
    @Autowired
    EstabelecimentoPatchPedidoEstadoService driverPatchEstado;
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
        pedido1.getSabores().add(sabor1);
        pedido1.getSabores().add(sabor2);
        pedido2.getSabores().add(sabor2);

        estabelecimento.getPedidos().add(pedido1);
        estabelecimento.getPedidos().add(pedido2);
        estabelecimentoRepository.save(estabelecimento);

        cliente.getPedidos().add(pedido1);
        cliente.getPedidos().add(pedido2);
        clienteRepository.save(cliente);
    }

    @AfterEach
    void tearDown() {
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
                () -> assertTrue(resultado.getEstado().isRecebido()),
                () -> assertEquals(45.0, resultado.getValorTotal())
        );
    }

    @Test
    @Transactional
    @DisplayName("Teste de alterar o estado de um pedido pelo id")
    void testeAlterarEstadoPedido() {
        Long estabelecimentoId = estabelecimento.getId();

        PedidoReadBodyDTO patchBody = PedidoReadBodyDTO.builder()
                .pedidoId(pedidoId)
                .codigoAcesso("123456")
                .build();

        PedidoReadResponseDTO resultado = driverPatchEstado.alterarEstadoPedido(estabelecimentoId, patchBody);

        assertAll(
                () -> assertEquals("Rua 13 de maio, 123", resultado.getEndereco()),
                () -> assertTrue(resultado.getTipo().isMeia()),
                () -> assertTrue(resultado.getTamanho().isGrande()),
                () -> assertTrue(resultado.getEstado().isPronto())
        );
    }

    @Nested
    @DisplayName("Testes de atribuicao de entregador ao pedido")
    class TestesAtribuiEntregadorPedido {
        @Autowired
        EstabelecimentoPatchPedidoEntregadorService driverPatchEntregador;
        @Autowired
        EntregadorRepository entregadorRepository;
        Entregador entregador;

        @BeforeEach
        void setUp() {
            entregador = Entregador.builder()
                            .codigoAcesso("123456")
                            .nome("Cleber 123 da Silva 4")
                            .corVeiculo("Azul")
                            .placaVeiculo("ABC-1235")
                            .tipoVeiculo(TipoVeiculoEntregador.CARRO)
                            .estabelecimentos(new HashSet<>())
                            .build();

            entregador.getEstabelecimentos().add(estabelecimento);
            estabelecimento.getEntregadoresAprovados().add(entregador);

            entregador = entregadorRepository.save(entregador);
            estabelecimentoRepository.save(estabelecimento);
        }

        @Test
        @Transactional
        @DisplayName("Teste de atribuir um entregador a um pedido")
        void testeAtribuiEntregadorPedido() {
            Long estabelecimentoId = estabelecimento.getId();
            Long entregadorId = entregador.getId();

            PedidoPatchEntregadorDTO patchBody = PedidoPatchEntregadorDTO.builder()
                    .pedidoId(pedidoId)
                    .entregadorId(entregadorId)
                    .codigoAcesso("123456")
                    .build();

            PedidoReadResponseDTO resultado = driverPatchEntregador.alterarEntregador(estabelecimentoId, patchBody);
            EntregadorReadDTO entregadorResultado = modelMapper.map(entregador, EntregadorReadDTO.class);

            assertAll(
                    () -> assertEquals("Rua 13 de maio, 123", resultado.getEndereco()),
                    () -> assertTrue(resultado.getTipo().isMeia()),
                    () -> assertTrue(resultado.getTamanho().isGrande()),
                    () -> assertEquals(resultado.getEntregador(), entregadorResultado),
                    () -> assertTrue(resultado.getEstado().isRota())
            );
        }

        @Test
        @Transactional
        @DisplayName("Teste de atribuir um entregador a um pedido com codigo acesso invalido")
        void testeAtribuiEntregadorPedidoCodigoAcessoInvalido() {
            Long estabelecimentoId = estabelecimento.getId();
            Long entregadorId = entregador.getId();

            PedidoPatchEntregadorDTO patchBody = PedidoPatchEntregadorDTO.builder()
                    .pedidoId(pedidoId)
                    .entregadorId(entregadorId)
                    .codigoAcesso("654321")
                    .build();

            assertThrows(CodigoAcessoInvalidoException.class, () -> driverPatchEntregador.alterarEntregador(estabelecimentoId, patchBody));
        }

        @Test
        @Transactional
        @DisplayName("Teste de atribuir um entregador a um pedido com pedido nao presente no estabelecimento")
        void testeAtribuiEntregadorPedidoInvalido() {
            Long estabelecimentoId = estabelecimento.getId();
            Long entregadorId = entregador.getId();

            PedidoPatchEntregadorDTO patchBody = PedidoPatchEntregadorDTO.builder()
                    .pedidoId(14L)
                    .entregadorId(entregadorId)
                    .codigoAcesso("123456")
                    .build();

            assertThrows(PedidoNaoEncontradoException.class, () -> driverPatchEntregador.alterarEntregador(estabelecimentoId, patchBody));
        }

        @Test
        @Transactional
        @DisplayName("Teste de atribuir um entregador a um pedido com entregador nao aprovado")
        void testeAtribuiEntregadorPedidoEntregadorNaoAprovado() {
            Long estabelecimentoId = estabelecimento.getId();

            PedidoPatchEntregadorDTO patchBody = PedidoPatchEntregadorDTO.builder()
                    .pedidoId(pedidoId)
                    .entregadorId(13L)
                    .codigoAcesso("123456")
                    .build();

            assertThrows(EntregadorNaoEstaAprovadoException.class, () -> driverPatchEntregador.alterarEntregador(estabelecimentoId, patchBody));
        }
    }


}
