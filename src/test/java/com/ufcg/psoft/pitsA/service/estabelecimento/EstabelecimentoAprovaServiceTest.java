package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.dto.entregador.EntregadorPatchEstabelecimentoDTO;
import com.ufcg.psoft.pitsA.dto.entregador.EntregadorReadDTO;
import com.ufcg.psoft.pitsA.dto.estabelecimento.EstabelecimentoAprovaEntregadorDTO;
import com.ufcg.psoft.pitsA.dto.estabelecimento.StatusAprovacaoEntregador;
import com.ufcg.psoft.pitsA.exception.auth.CodigoAcessoInvalidoException;
import com.ufcg.psoft.pitsA.exception.entregador.EntregadorNaoEstaPendenteException;
import com.ufcg.psoft.pitsA.model.Entregador;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.TipoVeiculoEntregador;
import com.ufcg.psoft.pitsA.repository.EntregadorRepository;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import com.ufcg.psoft.pitsA.service.entregador.EntregadorPatchEstabelecimentoService;

import static org.junit.jupiter.api.Assertions.*;

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
                        .tipoVeiculo(TipoVeiculoEntregador.CARRO)
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
                .aprovar(StatusAprovacaoEntregador.APROVADO)
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

    @Transactional
    @Test
    @DisplayName("Teste quando ocorre a rejeicao do entregador")
    void testaRejeicao() {
        assertTrue(estabelecimento.getEntregadoresPendentes().contains(entregador));

        EstabelecimentoAprovaEntregadorDTO estabelecimentoDTO = EstabelecimentoAprovaEntregadorDTO.builder()
                .entregadorId(entregadorId)
                .codigoAcesso("111111")
                .aprovar(StatusAprovacaoEntregador.REJEITADO)
                .build();

        EntregadorReadDTO resultado = driver.aprova(estabelecimentoId, estabelecimentoDTO);

        assertAll(
                () -> assertFalse(estabelecimento.getEntregadoresAprovados().contains(entregador)),
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
    @DisplayName("Testa codigo de acesso invalido")
    void testaCodigoInvalido() {
        assertTrue(estabelecimento.getEntregadoresPendentes().contains(entregador));

        EstabelecimentoAprovaEntregadorDTO estabelecimentoDTO = EstabelecimentoAprovaEntregadorDTO.builder()
                .entregadorId(entregadorId)
                .aprovar(StatusAprovacaoEntregador.APROVADO)
                .codigoAcesso("222222")
                .build();

        assertThrows(CodigoAcessoInvalidoException.class, () -> driver.aprova(estabelecimentoId, estabelecimentoDTO));
    }

    @Transactional
    @Test
    @DisplayName("Testa entregador nao esta presente na lista de pendencia")
    void testaEntregadorNaoPresente() {
        EstabelecimentoAprovaEntregadorDTO estabelecimentoDTO = EstabelecimentoAprovaEntregadorDTO.builder()
                .entregadorId(null)
                .aprovar(StatusAprovacaoEntregador.APROVADO)
                .codigoAcesso("111111")
                .build();

        assertThrows(EntregadorNaoEstaPendenteException.class, () -> driver.aprova(estabelecimentoId, estabelecimentoDTO));
    }
}
