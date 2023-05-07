package com.ufcg.psoft.mercadofacil.service;

import java.util.Arrays;
import java.util.List;

import com.ufcg.psoft.mercadofacil.dto.EntregadorPostPutDTO;
import com.ufcg.psoft.mercadofacil.dto.EntregadorReadDTO;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.repository.EntregadorRepository;
import com.ufcg.psoft.mercadofacil.service.entregador.EntregadorAtualizarService;
import com.ufcg.psoft.mercadofacil.service.entregador.EntregadorCriarService;
import com.ufcg.psoft.mercadofacil.service.entregador.EntregadorListarService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Testes do service de Entregadores")
public class EntregadorServiceTest {
    @Autowired
    EntregadorCriarService driverCriar;
    @Autowired
    EntregadorListarService driverListar;
    @Autowired
    EntregadorAtualizarService driverAtualizar;

    @Autowired
    EntregadorRepository entregadorRepository;
    EntregadorPostPutDTO entregador;

    @BeforeEach
    void setUp() {
        entregador = EntregadorPostPutDTO.builder()
                        .nome("Gabriel Pombo Diniz")
                        .placaVeiculo("RTJ-1235")
                        .tipoVeiculo("Carro")
                        .corVeiculo("Azul")
                        .codigoAcesso("123456")
                        .build();
    }

    @AfterEach
    void tearDown() {
        entregadorRepository.deleteAll();
    }

    @Test
    @DisplayName("Quando cria um novo entregador no sistema")
    void testeCriarEntregador() {
        EntregadorReadDTO resultado = driverCriar.salvar(entregador);

        assertAll(
                () -> assertEquals("Gabriel Pombo Diniz", resultado.getNome()),
                () -> assertEquals("RTJ-1235", resultado.getPlacaVeiculo()),
                () -> assertEquals("Carro", resultado.getTipoVeiculo()),
                () -> assertEquals("Azul", resultado.getCorVeiculo())
        );
    }

    @Test
    @DisplayName("Quando lista todos os entregadores cadastrados")
    void testeListaTodosEntregadores() {
        Entregador entregador1 = Entregador.builder()
                .nome("Cleber Junior")
                .placaVeiculo("QJS-1235")
                .tipoVeiculo("Moto")
                .corVeiculo("Preto")
                .codigoAcesso("653423")
                .build();

        Entregador entregador2 = Entregador.builder()
                .nome("Cleber")
                .placaVeiculo("JHG-9843")
                .tipoVeiculo("Moto")
                .corVeiculo("Prata")
                .codigoAcesso("943845")
                .build();

        entregadorRepository.saveAll(Arrays.asList(entregador1, entregador2));
        List<EntregadorReadDTO> resultado = driverListar.listar(null);

        assertEquals(3, resultado.size());
    }

/*    @Test
    @DisplayName("Quando lista um entregador pelo seu ID")
    void testeListaEntregadorPorId() {
        entregadorRepository.save(
                Entregador.builder()
                        .nome("Cleber")
                        .placaVeiculo("JHG-9843")
                        .tipoVeiculo("Moto")
                        .corVeiculo("Prata")
                        .codigoAcesso("943845")
                        .build()
        );

        Entregador resultado = entregadorRepository.findById(7L).get();

        assertAll(
                () -> assertEquals("Cleber", resultado.getNome()),
                () -> assertEquals("JHG-9843", resultado.getPlacaVeiculo()),
                () -> assertEquals("Moto", resultado.getTipoVeiculo()),
                () -> assertEquals("Prata", resultado.getCorVeiculo()),
                () -> assertEquals("943845", resultado.getCodigoAcesso())
        );
    }

    @Test
    @DisplayName("Quando atualizar um entregador cadastrado")
    void testeAtualizarEntregador() {
        EntregadorDTO entregadorAtualizado = EntregadorDTO.builder()
                    .nome("Junior")
                    .placaVeiculo("ASD-1234")
                    .tipoVeiculo("Moto")
                    .corVeiculo("Roxo")
                    .codigoAcesso("843924")
                    .build();

        Entregador resultado = driverAtualizar.atualizar(4L, entregadorAtualizado);

        assertAll(
                () -> assertEquals("Junior", resultado.getNome()),
                () -> assertEquals("ASD-1234", resultado.getPlacaVeiculo()),
                () -> assertEquals("Moto", resultado.getTipoVeiculo()),
                () -> assertEquals("Roxo", resultado.getCorVeiculo()),
                () -> assertEquals("843924", resultado.getCodigoAcesso())
        );
    }

    @Test
    @DisplayName("Quando remover um entregador cadastrado")
    void testeRemoverEntregador() {
        List<Entregador> resultBefore = entregadorRepository.findAll();

        entregadorRepository.deleteById(4L);

        List<Entregador> resultAfter = entregadorRepository.findAll();

        assertAll(
                () -> assertEquals(1, resultBefore.size()),
                () -> assertEquals(0, resultAfter.size())
        );
    }*/
}
