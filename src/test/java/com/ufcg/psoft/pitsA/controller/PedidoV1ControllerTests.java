package com.ufcg.psoft.pitsA.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoPostDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadResponseDTO;
import com.ufcg.psoft.pitsA.dto.pedido.SaborPedidoDTO;
import com.ufcg.psoft.pitsA.model.Cliente;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.pedido.PizzaPedidoTamanho;
import com.ufcg.psoft.pitsA.model.pedido.PizzaPedidoTipo;
import com.ufcg.psoft.pitsA.model.sabor.Sabor;
import com.ufcg.psoft.pitsA.model.sabor.TipoSabor;
import com.ufcg.psoft.pitsA.repository.ClienteRepository;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import com.ufcg.psoft.pitsA.repository.SaborRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de pedidos")
public class PedidoV1ControllerTests {
    public final String URI_CLIENTES = "/v1/pedidos";
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
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        estabelecimento = estabelecimentoRepository.save(
                Estabelecimento.builder()
                        .codigoAcesso("111111")
                        .build()
        );

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

        saborRepository.saveAll(Arrays.asList(sabor1, sabor2, sabor3));

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
        estabelecimentoRepository.deleteAll();
    }

    @Nested
    @DisplayName("Testes dos endpoints de cliente com pedidos")
    class ClientePedidosEndpointsTests {
        PedidoPostDTO pedidoPostComEnderecoDTO;
        PedidoPostDTO pedidoPostSemEnderecoDTO;

        @BeforeEach
        void setUp() {
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
            pedidoPostComEnderecoDTO.getSabores().add(SaborPedidoDTO.builder()
                            .nome("Calabresa")
                            .tipo(TipoSabor.SALGADO)
                            .precoMedio(30.0)
                            .precoGrande(50.0)
                    .build());

            pedidoPostSemEnderecoDTO.getSabores().add(SaborPedidoDTO.builder()
                            .nome("Calabresa")
                            .tipo(TipoSabor.SALGADO)
                            .precoMedio(30.0)
                            .precoGrande(50.0)
                    .build());

            pedidoPostSemEnderecoDTO.getSabores().add(SaborPedidoDTO.builder()
                            .nome("Chocolate")
                            .tipo(TipoSabor.DOCE)
                            .precoMedio(25.0)
                            .precoGrande(60.0)
                    .build());
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
    }
}
