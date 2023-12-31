package com.ufcg.psoft.pitsA.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.pitsA.dto.entregador.EntregadorReadDTO;
import com.ufcg.psoft.pitsA.dto.estabelecimento.*;
import com.ufcg.psoft.pitsA.dto.sabor.SaborReadDTO;
import com.ufcg.psoft.pitsA.exception.ErrorMessage;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.entregador.Entregador;
import com.ufcg.psoft.pitsA.model.entregador.TipoVeiculoEntregador;
import com.ufcg.psoft.pitsA.model.sabor.Sabor;
import com.ufcg.psoft.pitsA.model.sabor.TipoSabor;
import com.ufcg.psoft.pitsA.repository.EntregadorRepository;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import com.ufcg.psoft.pitsA.repository.SaborRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

        final String URI_ESTABELECIMENTO = "/v1/estabelecimento";
        EstabelecimentoPutDTO estabelecimentoPutDTO;
        EstabelecimentoPostDTO estabelecimentoPostDTO;

        @BeforeEach
        void setup() {
            estabelecimentoPutDTO = EstabelecimentoPutDTO.builder()
                    .codigoAcesso("111111")
                    .codigoAcessoAlterado("123456")
                    .build();
            estabelecimentoPostDTO = EstabelecimentoPostDTO.builder()
                    .codigoAcesso("222222")
                    .build();
        }

        @Test
        @DisplayName("Quando buscamos por todos os estabelecimentos salvos")
        void quandoBuscamosPorTodosEstabelecimentosSalvos() throws Exception {
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

            List<Estabelecimento> resultado = objectMapper.readValue(responseJsonString, new TypeReference<>() {
            });

            assertAll(
                    () -> assertEquals(3, resultado.size())
            );
        }

        @Test
        @DisplayName("Quando buscamos um estabelecimento salvo pelo id")
        void quandoBuscamosPorUmEstabelecimentoSalvo() throws Exception {
            Long estabelecimentoId = estabelecimento.getId();

            String responseJsonString = driver.perform(get(URI_ESTABELECIMENTO + "/" + estabelecimentoId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<Estabelecimento> listaResultados = objectMapper.readValue(responseJsonString, new TypeReference<>() {});
            Estabelecimento resultado = listaResultados.stream().findFirst().orElse(Estabelecimento.builder().build());

            assertAll(
                    () -> assertEquals(estabelecimentoId, resultado.getId().longValue()),
                    () -> assertEquals(estabelecimento.getCodigoAcesso(), resultado.getCodigoAcesso())
            );
        }

        @Test
        @DisplayName("Quando criamos um novo estabelecimento")
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
            EstabelecimentoPutDTO putBodyInvalido = EstabelecimentoPutDTO.builder()
                    .codigoAcesso("111111")
                    .codigoAcessoAlterado("123456")
                    .build();


            String responseJsonString = driver.perform(put(URI_ESTABELECIMENTO + "/" + estabelecimentoId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(putBodyInvalido)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Estabelecimento resultado = objectMapper.readValue(responseJsonString, Estabelecimento.class);

            assertAll(
                    () -> assertEquals(estabelecimentoId, resultado.getId().longValue()),
                    () -> assertEquals("123456", resultado.getCodigoAcesso())
            );
        }

        @Test
        @DisplayName("Quando alteramos um estabelecimento com codigo de acesso invalido")
        void quandoAlteraEstabelecimentoInvalido() throws Exception {
            Long estabelecimentoId = estabelecimento.getId();

            EstabelecimentoPutDTO patchBodyInvalido = EstabelecimentoPutDTO.builder()
                    .codigoAcesso("555555")
                    .codigoAcessoAlterado("123456")
                    .build();

            String responseJsonString = driver.perform(put(URI_ESTABELECIMENTO + "/" + estabelecimentoId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(patchBodyInvalido)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            ErrorMessage resultado = objectMapper.readValue(responseJsonString, ErrorMessage.class);
            assertAll(
                    () -> assertEquals("O codigo de acesso informado eh invalido", resultado.getMessage())
            );
        }

        @Test
        @DisplayName("Quando excluímos um estabelecimento salvo com codigo de acesso valido")
        void quandoExcluimosEstabelecimentoValido() throws Exception {
            Long estabelecimentoId = estabelecimento.getId();
            EstabelecimentoDeleteDTO deleteBodyValido = EstabelecimentoDeleteDTO.builder()
                    .codigoAcesso("111111")
                    .build();

            String responseJsonString = driver.perform(delete(URI_ESTABELECIMENTO + "/" + estabelecimentoId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(deleteBodyValido)))
                    .andExpect(status().isNoContent())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            assertTrue(responseJsonString.isBlank());
        }
    }

    @Nested
    @DisplayName("Testes de endpoints basicos de estabelecimentos")
    class EstabelecimentoAprovacaoTeste {
        final String URI_ESTABELECIMENTO = "/v1/estabelecimento";
        @Autowired
        EntregadorRepository entregadorRepository;
        Entregador entregador;
        Long entregadorId;
        Estabelecimento estabelecimento;
        Long estabelecimentoId;

        @BeforeEach
        void setUp() {
            entregador = entregadorRepository.save(
                    Entregador.builder()
                            .nome("Julio")
                            .codigoAcesso("777777")
                            .corVeiculo("Azul")
                            .tipoVeiculo(TipoVeiculoEntregador.CARRO)
                            .placaVeiculo("AAA-1111")
                            .build()
            );

            estabelecimento = estabelecimentoRepository.save(
                    Estabelecimento.builder()
                            .codigoAcesso("111111")
                            .entregadoresPendentes(new ArrayList<>(Collections.singletonList(entregador)))
                            .build()
            );

            entregadorId = entregador.getId();
            estabelecimentoId = estabelecimento.getId();
        }

        @AfterEach
        void tearDown() {
            entregadorRepository.deleteAll();
            estabelecimentoRepository.deleteAll();
        }

        @Transactional
        @Test
        @DisplayName("Quando aprovamos um entregador valido")
        void quandoAprovamosUmEntregadorValido() throws Exception {
            EstabelecimentoAprovaEntregadorDTO aprovaBodyValido = EstabelecimentoAprovaEntregadorDTO.builder()
                    .codigoAcesso("111111")
                    .aprovar(StatusAprovacaoEntregador.APROVADO)
                    .entregadorId(entregadorId)
                    .build();

            String responseJsonString = driver.perform(patch(URI_ESTABELECIMENTO + "/" + estabelecimentoId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(aprovaBodyValido)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EntregadorReadDTO resultado = objectMapper.readValue(responseJsonString, EntregadorReadDTO.class);

            assertAll(
                    () -> assertTrue(estabelecimento.getEntregadoresAprovados().contains(entregador)),
                    () -> assertFalse(estabelecimento.getEntregadoresPendentes().contains(entregador)),
                    () -> assertEquals(0, estabelecimento.getEntregadoresPendentes().size()),
                    () -> assertEquals(entregador.getNome(), resultado.getNome()),
                    () -> assertEquals(entregador.getPlacaVeiculo(), resultado.getPlacaVeiculo()),
                    () -> assertEquals(entregador.getTipoVeiculo(), resultado.getTipoVeiculo()),
                    () -> assertEquals(entregador.getCorVeiculo(), resultado.getCorVeiculo())
            );
        }

        @Transactional
        @Test
        @DisplayName("Testa alteracao com codigo de acesso invalido")
        void quandoAprovamosUmEntregadorCodigoInvalido() throws Exception {
            EstabelecimentoAprovaEntregadorDTO aprovaBodyInvalido = EstabelecimentoAprovaEntregadorDTO.builder()
                    .entregadorId(entregadorId)
                    .aprovar(StatusAprovacaoEntregador.REJEITADO)
                    .codigoAcesso("222222")
                    .build();

            String responseJsonString = driver.perform(patch(URI_ESTABELECIMENTO + "/" + estabelecimentoId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(aprovaBodyInvalido)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            ErrorMessage resultado = objectMapper.readValue(responseJsonString, ErrorMessage.class);
            assertAll(
                    () -> assertEquals("O codigo de acesso informado eh invalido", resultado.getMessage())
            );
        }

        @Transactional
        @Test
        @DisplayName("Quando rejeitamos um entregador valido")
        void quandoRejeitamosUmEntregador() throws Exception {
            EstabelecimentoAprovaEntregadorDTO aprovaBodyValido = EstabelecimentoAprovaEntregadorDTO.builder()
                    .codigoAcesso("111111")
                    .aprovar(StatusAprovacaoEntregador.REJEITADO)
                    .entregadorId(entregadorId)
                    .build();

            String responseJsonString = driver.perform(patch(URI_ESTABELECIMENTO + "/" + estabelecimentoId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(aprovaBodyValido)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EntregadorReadDTO resultado = objectMapper.readValue(responseJsonString, EntregadorReadDTO.class);

            assertAll(
                    () -> assertFalse(estabelecimento.getEntregadoresPendentes().contains(entregador)),
                    () -> assertFalse(estabelecimento.getEntregadoresAprovados().contains(entregador)),
                    () -> assertEquals(0, estabelecimento.getEntregadoresPendentes().size()),
                    () -> assertEquals(entregador.getNome(), resultado.getNome()),
                    () -> assertEquals(entregador.getPlacaVeiculo(), resultado.getPlacaVeiculo()),
                    () -> assertEquals(entregador.getTipoVeiculo(), resultado.getTipoVeiculo()),
                    () -> assertEquals(entregador.getCorVeiculo(), resultado.getCorVeiculo())
            );
        }

        @Transactional
        @Test
        @DisplayName("Testa aprovacao do entregador nao pendente")
        void quandoAprovamosUmEntregadorNaoPendente() throws Exception {
            EstabelecimentoAprovaEntregadorDTO aprovaBodyInvalido = EstabelecimentoAprovaEntregadorDTO.builder()
                    .entregadorId(null)
                    .aprovar(StatusAprovacaoEntregador.REJEITADO)
                    .codigoAcesso("111111")
                    .build();

            String responseJsonString = driver.perform(patch(URI_ESTABELECIMENTO + "/" + estabelecimentoId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(aprovaBodyInvalido)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            ErrorMessage resultado = objectMapper.readValue(responseJsonString, ErrorMessage.class);
            assertAll(
                    () -> assertEquals("O Entregador consultado nao esta na lista de pendencia desse estabelecimento!", resultado.getMessage())
            );
        }
    }

    @Nested
    @DisplayName("Testes de endpoints de modificar disponibilidade de sabor")
    class EstabelecimentoSaborDisponibilidadeTeste {
        final String URI_ESTABELECIMENTO = "/v1/estabelecimento";
        @Autowired
        SaborRepository saborRepository;
        Sabor sabor;

        @BeforeEach
        void setUp() {
            sabor = saborRepository.save(Sabor.builder()
                    .nome("Chocolate")
                    .tipo(TipoSabor.DOCE)
                    .estabelecimento(estabelecimento)
                    .precoGrande(50.0)
                    .precoMedio(25.0)
                    .build());

            estabelecimento.getCardapio().add(sabor);

            estabelecimento = estabelecimentoRepository.save(estabelecimento);
        }

        @Test
        @DisplayName("Quando alteramos a disponibilidade com sucesso")
        void testeAlteraDisponibilidadeSucesso() throws Exception {
            Long estabelecimentoId = estabelecimento.getId();
            EstabelecimentoPatchDispDTO patchBody = EstabelecimentoPatchDispDTO.builder()
                    .codigoAcesso(estabelecimento.getCodigoAcesso())
                    .saborId(sabor.getId())
                    .build();

            String responseJsonString = driver.perform(patch(URI_ESTABELECIMENTO + "/" + estabelecimentoId + "/sabor")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(patchBody)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            SaborReadDTO resultado = objectMapper.readValue(responseJsonString, SaborReadDTO.class);


            assertAll(
                    () -> assertEquals(sabor.getNome(), resultado.getNome()),
                    () -> assertFalse(resultado.isDisponivel()),
                    () -> assertEquals(sabor.getPrecoGrande(), resultado.getPrecoGrande()),
                    () -> assertEquals(sabor.getPrecoMedio(), resultado.getPrecoMedio())
            );
        }

        @Test
        @DisplayName("Quando o codigo de acesso e invalido")
        void testeQuandoCodigoAcessoInvalido() throws Exception {
            Long estabelecimentoId = estabelecimento.getId();
            EstabelecimentoPatchDispDTO patchBody = EstabelecimentoPatchDispDTO.builder()
                    .codigoAcesso("abcabcabc")
                    .saborId(sabor.getId())
                    .build();

            String responseJsonString = driver.perform(patch(URI_ESTABELECIMENTO + "/" + estabelecimentoId + "/sabor")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(patchBody)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            ErrorMessage resultado = objectMapper.readValue(responseJsonString, ErrorMessage.class);

            assertEquals(resultado.getMessage(), "O codigo de acesso informado eh invalido");
        }
    }
}
