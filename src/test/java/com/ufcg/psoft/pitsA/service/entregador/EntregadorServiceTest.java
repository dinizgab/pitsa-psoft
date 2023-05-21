package com.ufcg.psoft.pitsA.service.entregador;

import com.ufcg.psoft.pitsA.dto.entregador.EntregadorDeleteDTO;
import com.ufcg.psoft.pitsA.dto.entregador.EntregadorPostPutDTO;
import com.ufcg.psoft.pitsA.dto.entregador.EntregadorReadDTO;
import com.ufcg.psoft.pitsA.model.Entregador;
import com.ufcg.psoft.pitsA.model.TipoVeiculoEntregador;
import com.ufcg.psoft.pitsA.repository.EntregadorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    EntregadorRemoverService driverRemover;

    @Autowired
    EntregadorRepository entregadorRepository;
    EntregadorPostPutDTO entregador;

    @BeforeEach
    void setUp() {
        entregador = EntregadorPostPutDTO.builder()
                        .nome("Gabriel Pombo Diniz")
                        .placaVeiculo("RTJ-1235")
                        .tipoVeiculo(TipoVeiculoEntregador.CARRO)
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
                () -> assertEquals(TipoVeiculoEntregador.CARRO, resultado.getTipoVeiculo()),
                () -> assertEquals("Azul", resultado.getCorVeiculo())
        );
    }

    @Test
    @DisplayName("Quando lista todos os entregadores cadastrados")
    void testeListaTodosEntregadores() {
        Entregador entregador1 = Entregador.builder()
                .nome("Cleber Junior")
                .placaVeiculo("QJS-1235")
                .tipoVeiculo(TipoVeiculoEntregador.MOTO)
                .corVeiculo("Preto")
                .codigoAcesso("653423")
                .build();

        Entregador entregador2 = Entregador.builder()
                .nome("Cleber")
                .placaVeiculo("JHG-9843")
                .tipoVeiculo(TipoVeiculoEntregador.MOTO)
                .corVeiculo("Prata")
                .codigoAcesso("943845")
                .build();

        entregadorRepository.saveAll(Arrays.asList(entregador1, entregador2));
        List<EntregadorReadDTO> resultado = driverListar.listar(null);

        assertEquals(2, resultado.size());
    }

    @Test
    @DisplayName("Quando lista um entregador pelo seu ID")
    void testeListaEntregadorPorId() {
        Long entregadorId = entregadorRepository.save(
                Entregador.builder()
                        .nome("Cleber")
                        .placaVeiculo("JHG-9843")
                        .tipoVeiculo(TipoVeiculoEntregador.MOTO)
                        .corVeiculo("Prata")
                        .codigoAcesso("943845")
                        .build()
        ).getId();

        EntregadorReadDTO resultado = driverListar.listar(entregadorId).get(0);

        assertAll(
                () -> assertEquals("Cleber", resultado.getNome()),
                () -> assertEquals("JHG-9843", resultado.getPlacaVeiculo()),
                () -> assertEquals(TipoVeiculoEntregador.MOTO, resultado.getTipoVeiculo()),
                () -> assertEquals("Prata", resultado.getCorVeiculo())
        );
    }

    @Test
    @DisplayName("Quando atualizar um entregador cadastrado")
    void testeAtualizarEntregador() {
        Long entregadorId = entregadorRepository.save(Entregador.builder()
                .nome("Cleber Junior")
                .placaVeiculo("QJS-1235")
                .tipoVeiculo(TipoVeiculoEntregador.MOTO)
                .corVeiculo("Preto")
                .codigoAcesso("653423")
                .build()
        ).getId();

        EntregadorPostPutDTO entregadorAtualizado = EntregadorPostPutDTO.builder()
                    .nome("Juliano junior")
                    .placaVeiculo("ASD-1234")
                    .tipoVeiculo(TipoVeiculoEntregador.CARRO)
                    .corVeiculo("Vermelho")
                    .codigoAcesso("653423")
                    .build();

        EntregadorReadDTO resultado = driverAtualizar.atualizar(entregadorId, entregadorAtualizado);

        assertAll(
                () -> assertEquals("Juliano junior", resultado.getNome()),
                () -> assertEquals("ASD-1234", resultado.getPlacaVeiculo()),
                () -> assertEquals(TipoVeiculoEntregador.CARRO, resultado.getTipoVeiculo()),
                () -> assertEquals("Vermelho", resultado.getCorVeiculo())
        );
    }

    @Test
    @DisplayName("Quando remover um entregador cadastrado")
    void testeRemoverEntregador() {
        Long entregadorId = entregadorRepository.save(Entregador.builder()
                .nome("Cleber Junior")
                .placaVeiculo("QJS-1235")
                .tipoVeiculo(TipoVeiculoEntregador.MOTO)
                .corVeiculo("Preto")
                .codigoAcesso("653423")
                .build()
        ).getId();
        EntregadorDeleteDTO deleteBody = EntregadorDeleteDTO.builder()
                .codigoAcesso("653423")
                .build();


        List<Entregador> resultBefore = entregadorRepository.findAll();

        driverRemover.remover(entregadorId, deleteBody);

        List<Entregador> resultAfter = entregadorRepository.findAll();

        assertAll(
                () -> assertEquals(1, resultBefore.size()),
                () -> assertEquals(0, resultAfter.size())
        );
    }
}
