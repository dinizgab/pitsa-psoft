package com.ufcg.psoft.pitsA.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.pitsA.dto.EntregadorPatchEstabelecimentoDTO;
import com.ufcg.psoft.pitsA.dto.EntregadorReadDTO;
import com.ufcg.psoft.pitsA.exception.auth.CodigoAcessoInvalidoException;
import com.ufcg.psoft.pitsA.model.Entregador;
import com.ufcg.psoft.pitsA.dto.EntregadorPostPutDTO;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.repository.EntregadorRepository;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de Entregadores")
public class EntregadorV1ControllerTests {
    @Autowired
    MockMvc driver;
    @Autowired
    EntregadorRepository entregadorRepository;
    ObjectMapper objectMapper = new ObjectMapper();
    Entregador entregador;

    @BeforeEach
    void setup() {
        entregador = entregadorRepository.save(Entregador.builder()
                .nome("Gabriel Pombo Diniz")
                .placaVeiculo("RTJ-1235")
                .tipoVeiculo("Carro")
                .corVeiculo("Azul")
                .codigoAcesso("123456")
                .build()
        );
    }

    @AfterEach
    void tearDown() {
        entregadorRepository.deleteAll();
    }

    @Nested
    @DisplayName("Testes de endpoints basicos de entregadores")
    class ProdutoVerificacaoFluxosBasicosApiRest {

        final String URI_ENTREGADORES = "/v1/entregadores";
        EntregadorPostPutDTO entregadorPutRequestDTO;
        EntregadorPostPutDTO entregadorPostRequestDTO;

        @BeforeEach
        void setup() {
            entregadorPostRequestDTO = EntregadorPostPutDTO.builder()
                    .nome("Cleber")
                    .placaVeiculo("JHG-9843")
                    .tipoVeiculo("Moto")
                    .corVeiculo("Prata")
                    .codigoAcesso("943845")
                    .build();
            entregadorPutRequestDTO = EntregadorPostPutDTO.builder()
                    .nome("Junior 2")
                    .placaVeiculo("ASA-1235")
                    .tipoVeiculo("Moto")
                    .corVeiculo("Azul")
                    .codigoAcesso("123456")
                    .build();
        }

        @Test
        @DisplayName("Quando buscamos por todos os entregadores salvos")
        void quandoBuscamosPorTodosEntregadorSalvos() throws Exception {
            Entregador entregador1 = Entregador.builder()
                    .nome("Gabriel Pombo Diniz")
                    .placaVeiculo("RTJ-1235")
                    .tipoVeiculo("Carro")
                    .corVeiculo("Azul")
                    .codigoAcesso("123456")
                    .build();

            Entregador entregador2 = Entregador.builder()
                    .nome("Cleber Junior")
                    .placaVeiculo("QJS-1235")
                    .tipoVeiculo("Moto")
                    .corVeiculo("Preto")
                    .codigoAcesso("653423")
                    .build();
            entregadorRepository.saveAll(Arrays.asList(entregador1, entregador2));

            String responseJsonString = driver.perform(get(URI_ENTREGADORES)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<EntregadorReadDTO> resultado = objectMapper.readValue(responseJsonString, new TypeReference<>() {
            });

            assertAll(
                    () -> assertEquals(3, resultado.size())
            );

        }

        @Test
        @DisplayName("Quando buscamos um entregador salvo pelo id")
        void quandoBuscamosPorUmEntregadorSalvo() throws Exception {
            Long entregadorId = entregador.getId();

            String responseJsonString = driver.perform(get(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<EntregadorReadDTO> listaResultados = objectMapper.readValue(responseJsonString, new TypeReference<>() {
            });
            EntregadorReadDTO resultado = listaResultados.stream().findFirst().orElse(EntregadorReadDTO.builder().build());

            assertAll(
                    () -> assertEquals(entregadorId, resultado.getId().longValue()),
                    () -> assertEquals(entregador.getNome(), resultado.getNome()),
                    () -> assertEquals(entregador.getTipoVeiculo(), resultado.getTipoVeiculo()),
                    () -> assertEquals(entregador.getPlacaVeiculo(), resultado.getPlacaVeiculo()),
                    () -> assertEquals(entregador.getCorVeiculo(), resultado.getCorVeiculo())
            );
        }

        @Test
        @DisplayName("Quando criamos um novo entregador")
        void quandoCriarEntregadorValido() throws Exception {
            String responseJsonString = driver.perform(post(URI_ENTREGADORES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorPostRequestDTO)))
                    .andExpect(status().isCreated())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EntregadorReadDTO resultado = objectMapper.readValue(responseJsonString, EntregadorReadDTO.EntregadorReadDTOBuilder.class).build();

            assertAll(
                    () -> assertEquals(entregadorPostRequestDTO.getNome(), resultado.getNome()),
                    () -> assertEquals(entregadorPostRequestDTO.getTipoVeiculo(), resultado.getTipoVeiculo()),
                    () -> assertEquals(entregadorPostRequestDTO.getPlacaVeiculo(), resultado.getPlacaVeiculo()),
                    () -> assertEquals(entregadorPostRequestDTO.getCorVeiculo(), resultado.getCorVeiculo())
            );
        }

        @Test
        @DisplayName("Quando alteramos um entregador com codigo de acesso valido")
        void quandoAlteramosEntregadorValido() throws Exception {
            Long entregadorId = entregador.getId();

            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregadorId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EntregadorReadDTO resultado = objectMapper.readValue(responseJsonString, EntregadorReadDTO.EntregadorReadDTOBuilder.class).build();

            assertAll(
                    () -> assertEquals(entregadorId, resultado.getId().longValue()),
                    () -> assertEquals(entregadorPutRequestDTO.getNome(), resultado.getNome()),
                    () -> assertEquals(entregadorPutRequestDTO.getTipoVeiculo(), resultado.getTipoVeiculo()),
                    () -> assertEquals(entregadorPutRequestDTO.getPlacaVeiculo(), resultado.getPlacaVeiculo()),
                    () -> assertEquals(entregadorPutRequestDTO.getCorVeiculo(), resultado.getCorVeiculo())
            );
        }

        @Test
        @DisplayName("Quando alteramos um entregador com codigo de acesso invalido")
        void quandoAlteradorEntregadorInvalido() throws Exception {
            Long entregadorId = entregador.getId();

            EntregadorPostPutDTO entregadorCodigoAcessoInvalido = EntregadorPostPutDTO.builder()
                    .nome("Junior 2")
                    .placaVeiculo("ASA-1235")
                    .tipoVeiculo("Moto")
                    .corVeiculo("Azul")
                    .codigoAcesso("654321")
                    .build();

            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregadorId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorCodigoAcessoInvalido)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CodigoAcessoInvalidoException resultado = objectMapper.readValue(responseJsonString, CodigoAcessoInvalidoException.class);

            assertAll(
                    () -> assertEquals("O codigo de acesso informado eh invalido", resultado.getMessage())
            );
        }

        // TODO - Colocar a validacao do codigo de acesso do entregador
        @Test
        @DisplayName("Quando excluímos um entregador salvo com codigo de acesso valido")
        void quandoExcluimosEntregadorValido() throws Exception {
            Long entregadorId = entregador.getId();

            String responseJsonString = driver.perform(delete(URI_ENTREGADORES + "/" + entregadorId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            assertTrue(responseJsonString.isBlank());
        }
    }

    @Nested
    @DisplayName("Testes associacao entregador-estabelecimento")
    class TesteAssociacaoEntregador {
        final String URI_ENTREGADORES = "/v1/entregadores";
        @Autowired
        EstabelecimentoRepository estabelecimentoRepository;
        Estabelecimento estabelecimento;

        @BeforeEach
        void setUp() {
            estabelecimento = estabelecimentoRepository.save(Estabelecimento.builder()
                    .codigoAcesso("654321")
                    .build()
            );
        }

        @AfterEach
        void tearDown() {
            estabelecimentoRepository.deleteAll();
        }

        @Test
        @DisplayName("Quando alteramos um entregador com codigo de acesso valido")
        void quandoAlteramosEntregadorValido() throws Exception {
            Long entregadorId = entregador.getId();
            String codigoAcesso = entregador.getCodigoAcesso();

            EntregadorPatchEstabelecimentoDTO entregadorEstabelecimentoDTO =
                    EntregadorPatchEstabelecimentoDTO.builder()
                            .estabelecimentoId(estabelecimento.getId())
                            .codigoAcesso(codigoAcesso)
                            .build();

            String responseJsonString = driver.perform(patch(URI_ENTREGADORES + "/" + entregadorId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorEstabelecimentoDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Entregador resultado = objectMapper.readValue(responseJsonString, Entregador.class);
            Estabelecimento resultadoEstabelecimento = estabelecimentoRepository.findById(estabelecimento.getId()).get();

            assertAll(
                    () -> assertEquals(entregadorId, entregador.getId().longValue()),
                    () -> assertEquals(resultado.getNome(), entregador.getNome()),
                    () -> assertEquals(resultado.getTipoVeiculo(), entregador.getTipoVeiculo()),
                    () -> assertEquals(resultado.getPlacaVeiculo(), entregador.getPlacaVeiculo()),
                    () -> assertEquals(resultado.getCorVeiculo(), entregador.getCorVeiculo()),
                    () -> assertTrue(resultado.getEstabelecimentos().contains(estabelecimento)),
                    // TODO - Corrigir o getEntregadores que nao retorna nenhum valor
                    () -> assertTrue(resultadoEstabelecimento.getEntregadoresPendentes().contains(entregador))
            );
        }

        @Test
        @DisplayName("Quando associamos um entregador com codigo de acesso invalido")
        void quandoAlteradorEntregadorInvalido() throws Exception {
            Long entregadorId = entregador.getId();
            String codigoAcesso = "777777";

            EntregadorPatchEstabelecimentoDTO entregadorEstabelecimentoDTO =
                    EntregadorPatchEstabelecimentoDTO.builder()
                            .estabelecimentoId(estabelecimento.getId())
                            .codigoAcesso(codigoAcesso)
                            .build();

            String responseJsonString = driver.perform(patch(URI_ENTREGADORES + "/" + entregadorId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorEstabelecimentoDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CodigoAcessoInvalidoException resultado = objectMapper.readValue(responseJsonString, CodigoAcessoInvalidoException.class);

            assertAll(
                    () -> assertEquals("O codigo de acesso informado eh invalido", resultado.getMessage())
            );
        }
    }
}
