package com.ufcg.psoft.pitsA.service.pedido;

import com.ufcg.psoft.pitsA.dto.pedido.*;
import com.ufcg.psoft.pitsA.exception.pedido.TamanhoPedidoInvalidosException;
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
import com.ufcg.psoft.pitsA.service.cliente.ClienteListarPedidoService;
import com.ufcg.psoft.pitsA.service.cliente.ClienteRemoverPedidoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Testes para os services de pedido com cliente")
public class ClientePedidoServiceTests {
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
        pedidoMeia.getSabores().add(modelMapper.map(sabor2, SaborPedidoDTO.class));
    }

    @AfterEach
    void tearDown() {
        saborRepository.deleteAll();
        pedidoRepository.deleteAll();
        estabelecimentoRepository.deleteAll();
        clienteRepository.deleteAll();
    }

    @Nested
    @DisplayName("Testes do service de criacao de pedidos")
    class CriarServiceTests {
        @Autowired
        ClienteCriarPedidoService driverCriar;

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
                    () -> assertTrue(estabelecimento.getPedidos().contains(resultado)),
                    () -> assertEquals(45.0, resultado.calculaValorTotal())

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
                    () -> assertTrue(estabelecimento.getPedidos().contains(resultado)),
                    () -> assertEquals(45.0, resultado.calculaValorTotal())
            );
        }

        @Test
        @Transactional
        @DisplayName("Quando criamos um novo pedido com tamanho e tipo invalido")
        void testeNovoPedidoTamanhoInvalido() {
            Long clienteId = cliente.getId();
            PedidoPostDTO pedidoInvalido = PedidoPostDTO.builder()
                    .codigoAcesso(cliente.getCodigoAcesso())
                    .idEstabelecimento(estabelecimento.getId())
                    .endereco("")
                    .tamanho(PizzaPedidoTamanho.MEDIA)
                    .tipo(PizzaPedidoTipo.MEIA)
                    .build();

            assertThrows(TamanhoPedidoInvalidosException.class, () -> driverCriar.criarPedido(clienteId, pedidoInvalido));
        }
    }

    @Nested
    @DisplayName("Testes do service listar pedidos")
    class ListarServiceTests {
        @Autowired
        ClienteListarPedidoService driverListar;
        Long pedidoId;

        @BeforeEach
        void setUp() {
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

            pedido1.getSabores().add(modelMapper.map(sabor1, SaborPedidoDTO.class));
            pedido1.getSabores().add(modelMapper.map(sabor2, SaborPedidoDTO.class));
            pedido2.getSabores().add(modelMapper.map(sabor2, SaborPedidoDTO.class));

            cliente.getPedidos().add(pedido1);
            cliente.getPedidos().add(pedido2);

            pedidoId = pedido1.getId();
        }

        @Test
        @Transactional
        @DisplayName("Quando listamos todos os pedidos de um cliente")
        void testeListarPedidosCliente() {
            Long clienteId = cliente.getId();

            PedidoListarDTO listarDTO = PedidoListarDTO.builder()
                    .codigoAcesso(cliente.getCodigoAcesso())
                    .pedidoId(null)
                    .build();
            List<PedidoReadDTO> resultado = driverListar.listarPedidos(clienteId, listarDTO);

            assertEquals(2, resultado.size());
        }

        @Test
        @Transactional
        @DisplayName("Quando listamos um pedido de um cliente com o Id")
        void testeListarPedidoClientePorId() {
            Long clienteId = cliente.getId();

            PedidoListarDTO listarDTO = PedidoListarDTO.builder()
                    .codigoAcesso(cliente.getCodigoAcesso())
                    .pedidoId(pedidoId)
                    .build();
            PedidoReadDTO resultado = driverListar.listarPedidos(clienteId, listarDTO).get(0);

            assertAll(
                    () -> assertEquals(cliente.getNome(), resultado.getCliente().getNome()),
                    () -> assertEquals(cliente.getEndereco(), resultado.getEndereco()),
                    () -> assertTrue(resultado.getTipo().isMeia()),
                    () -> assertEquals(estabelecimento, resultado.getEstabelecimento()),
                    () -> assertTrue(resultado.getTamanho().isGrande()),
                    () -> assertEquals(45.0, resultado.getValorTotal())
            );
        }
    }

    @Nested
    @DisplayName("Testes do service de remover pedido")
    class RemoverServiceTests {
        @Autowired
        ClienteRemoverPedidoService driverRemover;
        Long pedidoId;

        @BeforeEach
        void setUp() {
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

            cliente.getPedidos().add(pedido1);
            cliente.getPedidos().add(pedido2);

            estabelecimento.getPedidos().add(pedido1);
            estabelecimento.getPedidos().add(pedido2);

            clienteRepository.save(cliente);
            estabelecimentoRepository.save(estabelecimento);

            pedidoId = pedido1.getId();
        }

        @Test
        @Transactional
        @DisplayName("Quando removemos um pedido de um cliente")
        void testeRemoverPedidossCliente() {
            Long clienteId = cliente.getId();
            Long estabelecimentoId = estabelecimento.getId();

            ClienteRemoverPedidoDTO removerDTO = ClienteRemoverPedidoDTO.builder()
                    .codigoAcesso(cliente.getCodigoAcesso())
                    .estabelecimentoId(estabelecimentoId)
                    .pedidoId(pedidoId)
                    .build();

            assertEquals(2, cliente.getPedidos().size());
            assertEquals(2,estabelecimento.getPedidos().size());

            driverRemover.removerPedido(clienteId, removerDTO);

            assertEquals(1, cliente.getPedidos().size());
            assertEquals(1, estabelecimento.getPedidos().size());
        }
    }
}

// Pedido
// List<SaborPedidoDTO>, Endereco, Cliente, CalculaValor, Tamanho (GRANDE ou MEDIA), Tipo (INTEIRA ou MEIA)

// CalculaValor
// Metodo que recebe a lista de sabores do pedido e retorna o calculo do valor total do pedido

// SaborPedidoDTO
// Nome, PrecoGrande, PrecoMedia, Tipo (SALGADA ou DOCE)

// TODO - Adicionar a possibilidade de fazer um pedido com varias pizzas
// TODO - Trocar a lista de sabores do PedidoPostDTO por uma lista de Long e depois procurar as pizzas pelos IDs

// TODO - Adicionar o resto das operacoes do CRUD, remover, atualizar e ler um pedido (So podem ser feitas pelo estabelecimento ou usuario com seus respectivos codigos de acesso)

// TODO - Adicionar um service para adicionar um tipo de pagamento -- confirmar  o pagamento -- no pedido (Cliente informa o pedido, metodo de pagamento e codigo de acesso)
// Metodos de pagamento = Cartao de credito, debito e pix

