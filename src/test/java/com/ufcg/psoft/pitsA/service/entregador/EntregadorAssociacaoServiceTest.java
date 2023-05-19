package com.ufcg.psoft.pitsA.service.entregador;

import com.ufcg.psoft.pitsA.dto.entregador.EntregadorPatchEstabelecimentoDTO;
import com.ufcg.psoft.pitsA.exception.auth.CodigoAcessoInvalidoException;
import com.ufcg.psoft.pitsA.model.Entregador;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.TipoVeiculoEntregador;
import com.ufcg.psoft.pitsA.repository.EntregadorRepository;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

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
                .tipoVeiculo(TipoVeiculoEntregador.CARRO)
                .estabelecimentos(new HashSet<>())
                .build()
        ).getId();

        estabelecimentoId = estabelecimentoRepository.save(Estabelecimento.builder()
                .codigoAcesso("123456")
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
        EntregadorPatchEstabelecimentoDTO entregadorDTO = EntregadorPatchEstabelecimentoDTO.builder()
                .codigoAcesso("123467")
                .estabelecimentoId(estabelecimentoId)
                .build();

        Entregador resultado = driver.alteraParcialmente(entregadorId, entregadorDTO);

        Estabelecimento resultadoEstabelecimento = estabelecimentoRepository.findById(estabelecimentoId).get();

        assertTrue(resultadoEstabelecimento.getEntregadoresPendentes().contains(resultado));
        assertTrue(resultado.getEstabelecimentos().contains(resultadoEstabelecimento));
    }

    @Test
    @DisplayName("Quando associamos um entregador a um estabelecimento com o codigo de acesso invalido")
    void testeAssociaEntregadorInvalido() {
        EntregadorPatchEstabelecimentoDTO entregadorDTO = EntregadorPatchEstabelecimentoDTO.builder()
                .codigoAcesso("123456")
                .estabelecimentoId(estabelecimentoId)
                .build();

        assertThrows(CodigoAcessoInvalidoException.class, () -> driver.alteraParcialmente(entregadorId, entregadorDTO));
    }
}
