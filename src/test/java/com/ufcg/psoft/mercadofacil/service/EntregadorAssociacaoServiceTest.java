package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.exception.CodigoAcessoInvalidoException;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.repository.EntregadorRepository;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import com.ufcg.psoft.mercadofacil.service.entregador.EntregadorPatchEstabelecimentoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Testes para associacao do entregador a um estabelecimento")
public class EntregadorAssociacaoServiceTest {
    @Autowired
    EntregadorPatchEstabelecimentoService driver;
    Long entregadorId;
    Long estabelecimentoId;
    @Autowired
    EntregadorRepository entregadorRepository;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @BeforeEach
    void setup() {
        entregadorId = entregadorRepository.save(Entregador.builder()
                .nome("Julio")
                .codigoAcesso("123467")
                .corVeiculo("Verde")
                .placaVeiculo("FAS-5432")
                .tipoVeiculo("Carro")
                .estabelecimentos(new ArrayList<>())
                .build()
        ).getId();

        estabelecimentoId = estabelecimentoRepository.save(Estabelecimento.builder()
                .codigoAcesso("123456")
                .entregadores(new ArrayList<>())
                .build()
        ).getId();
    }

    @AfterEach
    void tearDown() {
        entregadorRepository.deleteAll();
        estabelecimentoRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("Quando associamos um entregador a um estabelecimento com o codigo de acesso valido")
    void testeAssociaEntregadorValido() {
        String codigoAcesso = "123467";
        Entregador resultado = driver.alteraParcialmente(entregadorId, estabelecimentoId, codigoAcesso);

        Estabelecimento resultadoEstabelecimento = estabelecimentoRepository.findById(estabelecimentoId).get();

        assertTrue(resultadoEstabelecimento.getEntregadores().contains(resultado));
        assertTrue(resultado.getEstabelecimentos().contains(resultadoEstabelecimento));
    }

    @Test
    @DisplayName("Quando associamos um entregador a um estabelecimento com o codigo de acesso invalido")
    void testeAssociaEntregadorInvalido() {
        assertThrows(CodigoAcessoInvalidoException.class, () -> driver.alteraParcialmente(entregadorId, estabelecimentoId, "123456"));
    }
}
