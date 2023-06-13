package com.ufcg.psoft.pitsA.service.entregador;

import com.ufcg.psoft.pitsA.dto.entregador.EntregadorDispDTO;
import com.ufcg.psoft.pitsA.dto.entregador.EntregadorReadDTO;
import com.ufcg.psoft.pitsA.exception.auth.CodigoAcessoInvalidoException;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.entregador.Entregador;
import com.ufcg.psoft.pitsA.model.entregador.TipoVeiculoEntregador;
import com.ufcg.psoft.pitsA.repository.EntregadorRepository;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Testes do service de disponibilidade d")
public class EntregadorDispServiceTest {
    @Autowired
    EntregadorPatchDispService driver;
    Long entregadorId;
    Long estabelecimentoId;
    @Autowired
    EntregadorRepository entregadorRepository;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @BeforeEach
    void setup() {
        Estabelecimento estabelecimento = estabelecimentoRepository.save(Estabelecimento.builder()
                .codigoAcesso("123456")
                .build()
        );

        Entregador entregador = entregadorRepository.save(Entregador.builder()
                        .nome("Julio")
                        .codigoAcesso("123467")
                        .corVeiculo("Verde")
                        .placaVeiculo("FAS-5432")
                        .tipoVeiculo(TipoVeiculoEntregador.CARRO)
                        .estabelecimento(estabelecimento)
                        .disponivel(false)
                .build()
        );


        estabelecimento.getEntregadoresAprovados().add(entregador);
        entregadorId = entregador.getId();
        estabelecimentoId = estabelecimentoRepository.save(estabelecimento).getId();
    }

    @AfterEach
    void tearDown() {
        entregadorRepository.deleteAll();
        estabelecimentoRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("Testes de alterar a disponibilidade do entregador com sucesso")
    void testAlteraDisponibilidade() {
        EntregadorDispDTO patchDispDTO = EntregadorDispDTO.builder()
                    .codigoAcesso("123467")
                .build();

        EntregadorReadDTO resultado = driver.alteraDisponibilidadeEntrega(entregadorId, patchDispDTO);

        assertAll(
                () -> assertEquals("Julio", resultado.getNome()),
                () -> assertEquals("Verde", resultado.getCorVeiculo()),
                () -> assertEquals("FAS-5432", resultado.getPlacaVeiculo()),
                () -> assertTrue(resultado.isDisponivel())
        );
    }

    @Test
    @DisplayName("Testes de alterar a disponibilidade do entregador com codigo acesso invalido")
    void testAlteraDisponibilidadeInvalido() {
        EntregadorDispDTO patchDispDTO = EntregadorDispDTO.builder()
                .codigoAcesso("111111")
                .build();

        assertThrows(CodigoAcessoInvalidoException.class, () -> driver.alteraDisponibilidadeEntrega(entregadorId, patchDispDTO));
    }
}
