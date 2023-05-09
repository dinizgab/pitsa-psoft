package com.ufcg.psoft.pitsA.service;

import com.ufcg.psoft.pitsA.dto.EntregadorPatchEstabelecimentoDTO;
import com.ufcg.psoft.pitsA.dto.EntregadorReadDTO;
import com.ufcg.psoft.pitsA.dto.EstabelecimentoAprovaEntregadorDTO;
import com.ufcg.psoft.pitsA.exception.auth.CodigoAcessoInvalidoException;
import com.ufcg.psoft.pitsA.exception.entregador.EntregadorNaoEstaPendenteException;
import com.ufcg.psoft.pitsA.model.Entregador;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.repository.EntregadorRepository;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import com.ufcg.psoft.pitsA.service.entregador.EntregadorPatchEstabelecimentoService;

import static org.junit.jupiter.api.Assertions.*;

import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoAprovaService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Testes de quando o estabelecimento aprova o entregador")
public class EstabelecimentoAprovaServiceTest {
    @Autowired
    EstabelecimentoAprovaService driver;
    @Autowired
    EntregadorPatchEstabelecimentoService entregadorPatchEstabelecimentoService;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
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
                        .tipoVeiculo("Carro")
                        .placaVeiculo("AAA-1111")
                        .build()
        );

        estabelecimento = estabelecimentoRepository.save(
                Estabelecimento.builder()
                        .codigoAcesso("111111")
                        .build()
        );

        entregadorId = entregador.getId();
        estabelecimentoId = estabelecimento.getId();

        EntregadorPatchEstabelecimentoDTO entregadorDTO = EntregadorPatchEstabelecimentoDTO.builder()
                .estabelecimentoId(estabelecimentoId)
                .codigoAcesso("777777")
                .build();

        entregadorPatchEstabelecimentoService.alteraParcialmente(entregadorId, entregadorDTO);
    }

    @AfterEach
    void tearDown() {
        entregadorRepository.deleteAll();
        estabelecimentoRepository.deleteAll();
    }

    @Transactional
    @Test
    @DisplayName("Teste quando ocorre a aprovacao com o codigo de acesso valido")
    void testaAprovacaoCodigoValido() {
        assertTrue(estabelecimento.getEntregadoresPendentes().contains(entregador));

        EstabelecimentoAprovaEntregadorDTO estabelecimentoDTO = EstabelecimentoAprovaEntregadorDTO.builder()
                .entregadorId(entregadorId)
                .codigoAcesso("111111")
                .build();

        EntregadorReadDTO resultado = driver.aprova(estabelecimentoId, estabelecimentoDTO);

        assertAll(
                () -> assertTrue(estabelecimento.getEntregadoresAprovados().contains(entregador)),
                () -> assertEquals(0, estabelecimento.getEntregadoresPendentes().size()),
                () -> assertEquals(entregador.getNome(), resultado.getNome()),
                () -> assertEquals(entregador.getPlacaVeiculo(), resultado.getPlacaVeiculo()),
                () -> assertEquals(entregador.getTipoVeiculo(), resultado.getTipoVeiculo()),
                () -> assertEquals(entregador.getCorVeiculo(), resultado.getCorVeiculo())
        );
    }

    @Test
    void testaRejeicao() {

    }

    @Transactional
    @Test
    @DisplayName("Testa codigo de acesso invalido")
    void testaCodigoInvalido() {
        assertTrue(estabelecimento.getEntregadoresPendentes().contains(entregador));

        EstabelecimentoAprovaEntregadorDTO estabelecimentoDTO = EstabelecimentoAprovaEntregadorDTO.builder()
                .entregadorId(entregadorId)
                .codigoAcesso("222222")
                .build();

        assertThrows(CodigoAcessoInvalidoException.class, () -> driver.aprova(estabelecimentoId, estabelecimentoDTO));
    }

    @Transactional
    @Test
    @DisplayName("Testa codigo de acesso invalido")
    void testeEntregadorNaoPendente() {
        assertTrue(estabelecimento.getEntregadoresPendentes().contains(entregador));

        EstabelecimentoAprovaEntregadorDTO estabelecimentoDTO = EstabelecimentoAprovaEntregadorDTO.builder()
                .entregadorId(12L)
                .codigoAcesso("111111")
                .build();

        assertThrows(EntregadorNaoEstaPendenteException.class, () -> driver.aprova(estabelecimentoId, estabelecimentoDTO));
    }
}
