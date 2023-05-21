package com.ufcg.psoft.pitsA.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.pitsA.dto.pedido.*;
import com.ufcg.psoft.pitsA.model.Cliente;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;
import com.ufcg.psoft.pitsA.model.pedido.PizzaPedidoTamanho;
import com.ufcg.psoft.pitsA.model.pedido.PizzaPedidoTipo;
import com.ufcg.psoft.pitsA.model.pedido.TipoPagamento;
import com.ufcg.psoft.pitsA.model.sabor.Sabor;
import com.ufcg.psoft.pitsA.model.sabor.TipoSabor;
import com.ufcg.psoft.pitsA.repository.ClienteRepository;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import com.ufcg.psoft.pitsA.repository.PedidoRepository;
import com.ufcg.psoft.pitsA.repository.SaborRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de pedidos")
public class PedidoV1ControllerTests {
    @Autowired
    MockMvc driver;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    SaborRepository saborRepository;
    Estabelecimento estabelecimento;
    Cliente cliente;
    Sabor sabor;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        estabelecimento = estabelecimentoRepository.save(
                Estabelecimento.builder()
                        .codigoAcesso("111111")
                        .build()
        );

        sabor = Sabor.builder()
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

        saborRepository.saveAll(Arrays.asList(sabor, sabor2, sabor3));

        cliente = clienteRepository.save(
                Cliente.builder()
                        .nome("Gabriel")
                        .endereco("Rua 13 de maio, 123")
                        .codigoAcesso("123456")
                        .build()
        );
    }

    @AfterEach
    void tearDown() {
        clienteRepository.deleteAll();
        estabelecimentoRepository.deleteAll();
    }

    @Nested
    @DisplayName("Testes dos endpoints de cliente com pedidos")
    class ClientePedidosEndpointsTests {
        public final String URI_CLIENTES = "/v1/pedidos";
        PedidoPostDTO pedidoPostComEnderecoDTO;
        PedidoPostDTO pedidoPostSemEnderecoDTO;
        @Autowired
        PedidoRepository pedidoRepository;
        Pedido pedido;

        @BeforeEach
        void setUp() {
            pedido = Pedido.builder()
                    .estabelecimentoPedido(estabelecimento)
                    .cliente(cliente)
                    .endereco("")
                    .sabores(new ArrayList<>())
                    .tipo(PizzaPedidoTipo.INTEIRA)
                    .tamanho(PizzaPedidoTamanho.GRANDE)
                    .build();

            pedidoPostComEnderecoDTO = PedidoPostDTO.builder()
                            .codigoAcesso(cliente.getCodigoAcesso())
                            .idEstabelecimento(estabelecimento.getId())
                            .tipo(PizzaPedidoTipo.INTEIRA)
                            .tamanho(PizzaPedidoTamanho.GRANDE)
                            .endereco("Rua das Laranjeiras, 213")
                    .build();

            pedidoPostSemEnderecoDTO = PedidoPostDTO.builder()
                    .codigoAcesso(cliente.getCodigoAcesso())
                            .idEstabelecimento(estabelecimento.getId())
                            .tipo(PizzaPedidoTipo.MEIA)
                            .tamanho(PizzaPedidoTamanho.GRANDE)
                            .endereco("")
                    .build();

            // Adicionando os sabores nos pedidos
            pedidoPostComEnderecoDTO.getSaboresId().add(1L);
            pedidoPostSemEnderecoDTO.getSaboresId().add(2L);
            pedidoPostSemEnderecoDTO.getSaboresId().add(3L);

            pedido.getSabores().add(sabor);
            pedido = pedidoRepository.save(pedido);
        }

        @AfterEach
        void tearDown() {
            pedidoRepository.deleteAll();
        }

        @Test
        @DisplayName("Quando listamos um pedido de um cliente")
        void quandoListarUmPedido() throws Exception {
            Double valorTotal = 35.0;
            PedidoReadBodyDTO readBody = PedidoReadBodyDTO.builder()
                            .pedidoId(pedido.getId())
                            .codigoAcesso(cliente.getCodigoAcesso())
                    .build();

            String responseJsonString = driver.perform(get(URI_CLIENTES + "/cliente/" + cliente.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(readBody)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<PedidoReadResponseDTO> resultadoList = objectMapper.readValue(responseJsonString, new TypeReference<>() {});
            PedidoReadResponseDTO resultado = resultadoList.get(0);

            assertAll(
                    () -> assertEquals(pedido.getEndereco(), resultado.getEndereco()),
                    () -> assertTrue(resultado.getTamanho().isGrande()),
                    () -> assertTrue(resultado.getTipo().isInteira()),
                    () -> assertEquals(valorTotal, resultado.getValorTotal())
            );
        }

        @Test
        @DisplayName("Quando listamos todos os pedidos de um cliente")
        void quandoListarTodosPedidos() throws Exception {

            pedidoRepository.save(Pedido.builder()
                    .estabelecimentoPedido(estabelecimento)
                    .cliente(cliente)
                    .endereco("")
                    .sabores(new ArrayList<>())
                    .tipo(PizzaPedidoTipo.INTEIRA)
                    .tamanho(PizzaPedidoTamanho.GRANDE)
                    .build()
            );

            PedidoReadBodyDTO readBody = PedidoReadBodyDTO.builder()
                    .pedidoId(pedido.getId())
                    .codigoAcesso(cliente.getCodigoAcesso())
                    .build();

            String responseJsonString = driver.perform(get(URI_CLIENTES + "/cliente/" + cliente.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(readBody)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<PedidoReadResponseDTO> resultadoList = objectMapper.readValue(responseJsonString, new TypeReference<>() {});
            assertEquals(2, resultadoList.size());
        }

        @Test
        @DisplayName("Quando criamos um novo pedido com endereco")
        void quandoCriarUmPedidoEndereco() throws Exception {
            Double valorTotal = 50.0;
            String responseJsonString = driver.perform(post(URI_CLIENTES + "/cliente/" + cliente.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(pedidoPostComEnderecoDTO)))
                    .andExpect(status().isCreated())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            PedidoReadResponseDTO resultado = objectMapper.readValue(responseJsonString, PedidoReadResponseDTO.class);

            assertAll(
                    () -> assertEquals(pedidoPostComEnderecoDTO.getEndereco(), resultado.getEndereco()),
                    () -> assertTrue(resultado.getTamanho().isGrande()),
                    () -> assertTrue(resultado.getTipo().isInteira()),
                    () -> assertEquals(valorTotal, resultado.getValorTotal())
            );
        }

        @Test
        @DisplayName("Quando criamos um novo pedido sem endereco")
        void quandoCriarUmPedidoSemEndereco() throws Exception {
            Double valorTotal = 50.0;
            String responseJsonString = driver.perform(post(URI_CLIENTES + "/cliente/" + cliente.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(pedidoPostSemEnderecoDTO)))
                    .andExpect(status().isCreated())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            PedidoReadResponseDTO resultado = objectMapper.readValue(responseJsonString, PedidoReadResponseDTO.class);

            assertAll(
                    () -> assertEquals(cliente.getEndereco(), resultado.getEndereco()),
                    () -> assertTrue(resultado.getTamanho().isGrande()),
                    () -> assertTrue(resultado.getTipo().isMeia()),
                    () -> assertEquals(valorTotal, resultado.getValorTotal())
            );
        }

        @Test
        @DisplayName("Quando removemos um pedido existente")
        void quandoRemoverPedidoExistente() throws Exception {
            Long clienteId = cliente.getId();
            ClienteRemoverPedidoDTO pedidoRemoverBody = ClienteRemoverPedidoDTO.builder()
                    .pedidoId(pedido.getId())
                    .estabelecimentoId(estabelecimento.getId())
                    .codigoAcesso(cliente.getCodigoAcesso())
                    .build();

            String responseJsonString = driver.perform(delete(URI_CLIENTES + "/cliente/" + clienteId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(pedidoRemoverBody)))
                    .andExpect(status().isNoContent())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            assertTrue(responseJsonString.isEmpty());
        }

        @Test
        @DisplayName("Quando um cliente confirma o pagamento do pedido")
        void quandoClienteConfirmaPagamento() throws Exception {
            Long clienteId = cliente.getId();
            ConfirmarPagamentoDTO confirmarPagamentoDTO = ConfirmarPagamentoDTO.builder()
                    .clienteId(clienteId)
                    .tipoPagamento(TipoPagamento.CREDITO)
                    .codigoAcesso(cliente.getCodigoAcesso())
                    .build();

            Pedido pedido = pedidoRepository.save(
                    Pedido.builder()
                            .cliente(cliente)
                            .estabelecimentoPedido(estabelecimento)
                            .tamanho(PizzaPedidoTamanho.GRANDE)
                            .tipo(PizzaPedidoTipo.INTEIRA)
                            .endereco(cliente.getEndereco())
                            .build()
            );

            String responseJsonString = driver.perform(patch(URI_CLIENTES + "/" + pedido.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(confirmarPagamentoDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            PedidoReadResponseDTO resultado = objectMapper.readValue(responseJsonString, PedidoReadResponseDTO.class);
            assertAll(
                    () -> assertTrue(resultado.getTipoPagamento().isCredito()),
                    () -> assertEquals(cliente.getEndereco(), resultado.getEndereco()),
                    () -> assertTrue(resultado.getTipo().isInteira()),
                    () -> assertTrue(resultado.getTamanho().isGrande())
            );

        }
    }

    @Nested
    @DisplayName("Testes dos endpoints de estabelecimento com pedidos")
    class EstabelecimentoPedidosEndpointsTests {
        public final String URI_CLIENTES = "/v1/pedidos";
        @Autowired
        PedidoRepository pedidoRepository;
        Pedido pedido;

        @BeforeEach
        void setUp() {
            pedido = Pedido.builder()
                    .estabelecimentoPedido(estabelecimento)
                    .cliente(cliente)
                    .endereco("")
                    .sabores(new ArrayList<>())
                    .tipo(PizzaPedidoTipo.INTEIRA)
                    .tamanho(PizzaPedidoTamanho.GRANDE)
                    .build();

            pedido.getSabores().add(sabor);
            pedido = pedidoRepository.save(pedido);
        }

        // TODO - Finalizar esse teste
        @Test
        @DisplayName("Quando listamos os pedidos de um estabelecimento")
        void quandoListarPedidosEstabelecimento() throws Exception {
            PedidoReadBodyDTO readBody = PedidoReadBodyDTO.builder()
                    .pedidoId(pedido.getId())
                    .codigoAcesso(cliente.getCodigoAcesso())
                    .build();

            String responseJsonString = driver.perform(get(URI_CLIENTES + "/estabelecimento/" + cliente.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(readBody)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<PedidoReadResponseDTO> resultadoList = objectMapper.readValue(responseJsonString, new TypeReference<>() {});
            PedidoReadResponseDTO resultado = resultadoList.get(0);
        }
    }
}
