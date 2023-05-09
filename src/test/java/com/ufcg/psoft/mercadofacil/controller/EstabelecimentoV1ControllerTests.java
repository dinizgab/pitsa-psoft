package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.mercadofacil.dto.*;
import com.ufcg.psoft.mercadofacil.exception.CustomErrorType;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de Entregadores")
public class EstabelecimentoV1ControllerTests {

    @Autowired
    MockMvc driver;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    ObjectMapper objectMapper = new ObjectMapper();
    Estabelecimento estabelecimento;

    @BeforeEach
    void setup() {
        estabelecimento = estabelecimentoRepository.save(
                Estabelecimento.builder()
                        .codigoAcesso("111111")
                        .build()
        );
    }

    @AfterEach
    void tearDown() {
        estabelecimentoRepository.deleteAll();
    }

    @Nested
    @DisplayName("Testes de endpoints basicos de entregadores")
    class EntregadorVerificacaoFluxosBasicosApiRest {

        final String URI_ESTABELECIMENTO = "/v1/estabelecimentos";
        EstabelecimentoPostPutDTO estabelecimentoPutDTO;
        EstabelecimentoPostPutDTO estabelecimentoPostDTO;

        @BeforeEach
        void setup() {
            estabelecimentoPutDTO = EstabelecimentoPostPutDTO.builder()
                    .codigoAcesso("222222")
                    .codigoAcessoAlterado("123456")
                    .build();
            estabelecimentoPostDTO = EstabelecimentoPostPutDTO.builder()
                    .codigoAcesso("222222")
                    .build();
        }

        @Test
        @DisplayName("Quando buscamos por todos os entregadores salvos")
        void quandoBuscamosPorTodosEntregadorSalvos() throws Exception {
            Estabelecimento estabelecimento1 = Estabelecimento.builder()
                    .codigoAcesso("444444")
                    .build();

            Estabelecimento estabelecimento2 = Estabelecimento.builder()
                    .codigoAcesso("555555")
                    .build();
            estabelecimentoRepository.saveAll(Arrays.asList(estabelecimento1, estabelecimento2));

            String responseJsonString = driver.perform(get(URI_ESTABELECIMENTO)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<EntregadorReadDTO> resultado = objectMapper.readValue(responseJsonString, new TypeReference<>(){});

            assertAll(
                    () -> assertEquals(3, resultado.size())
            );

        }

        @Test
        @DisplayName("Quando buscamos um entregador salvo pelo id")
        void quandoBuscamosPorUmEntregadorSalvo() throws Exception {
            Long estabelecimentoId = estabelecimento.getId();

            String responseJsonString = driver.perform(get(URI_ESTABELECIMENTO + "/" + estabelecimentoId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<EntregadorReadDTO> listaResultados = objectMapper.readValue(responseJsonString, new TypeReference<>(){});
            EntregadorReadDTO resultado = listaResultados.stream().findFirst().orElse(EntregadorReadDTO.builder().build());

            assertAll(
                    () -> assertEquals(estabelecimentoId, resultado.getId().longValue()),
                    () -> assertEquals(estabelecimento.getCodigoAcesso(), resultado.getNome())
            );
        }

        @Test
        @DisplayName("Quando criamos um novo entregador")
        void quandoCriarEntregadorValido() throws Exception {
            String responseJsonString = driver.perform(post(URI_ESTABELECIMENTO)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(estabelecimentoPostDTO)))
                    .andExpect(status().isCreated())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Estabelecimento resultado = objectMapper.readValue(responseJsonString, Estabelecimento.class);

            assertAll(
                    () -> assertEquals(estabelecimentoPostDTO.getCodigoAcesso(), resultado.getCodigoAcesso())
            );
        }

        @Test
        @DisplayName("Quando alteramos um estabelecimento com codigo de acesso valido")
        void quandoAlteramosEstabelecimentoValido() throws Exception {
            Long estabelecimentoId = estabelecimento.getId();

            String responseJsonString = driver.perform(put(URI_ESTABELECIMENTO + "/" + estabelecimentoId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(estabelecimentoPutDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Estabelecimento resultado = objectMapper.readValue(responseJsonString, Estabelecimento.class);

            assertAll(
                    () -> assertEquals(estabelecimentoId, resultado.getId().longValue()),
                    () -> assertEquals("123456" ,resultado.getCodigoAcesso())
            );
        }

        @Test
        @DisplayName("Quando alteramos um estabelecimento com codigo de acesso invalido")
        void quandoAlteradorEstabelecimentoInvalido() throws Exception {
            Long estabelecimentoId = estabelecimento.getId();

            EstabelecimentoPostPutDTO patchBodyInvalido = EstabelecimentoPostPutDTO.builder()
                    .codigoAcesso("555555")
                    .codigoAcessoAlterado("")
                    .build();

            String responseJsonString = driver.perform(put(URI_ESTABELECIMENTO + "/" + estabelecimentoId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(patchBodyInvalido)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            assertAll(
                    () -> assertEquals("O codigo de acesso informado eh invalido", resultado.getMessage())
            );
        }

        @Test
        @DisplayName("Quando excluímos um estabelecimento salvo com codigo de acesso valido")
        void quandoExcluimosEstabelecimentoValido() throws Exception {
            Long estabelecimentoId = estabelecimento.getId();
            EstabelecimentoDeleteDTO deleteBodyValido = EstabelecimentoDeleteDTO.builder()
                    .codigoAcesso("222222")
                    .build();

            String responseJsonString = driver.perform(delete(URI_ESTABELECIMENTO + "/" + estabelecimentoId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(deleteBodyValido)))
                    .andExpect(status().isNoContent())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            assertTrue(responseJsonString.isBlank());
        }

        @Test
        @DisplayName("Quando excluímos um estabelecimento salvo com codigo de acesso invalido")
        void quandoExcluimosEstabelecimentoInvalido() throws Exception {
            Long estabelecimentoId = estabelecimento.getId();
            EstabelecimentoDeleteDTO deleteBodyInvalido = EstabelecimentoDeleteDTO.builder()
                    .codigoAcesso("111111")
                    .build();

            String responseJsonString = driver.perform(delete(URI_ESTABELECIMENTO + "/" + estabelecimentoId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(deleteBodyInvalido)))
                    .andExpect(status().isNoContent())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            assertAll(
                    () -> assertEquals("O codigo de acesso informado eh invalido", resultado.getMessage())
            );
        }
    }
}
