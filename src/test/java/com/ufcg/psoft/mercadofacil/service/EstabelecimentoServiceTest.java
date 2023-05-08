package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.dto.EstabelecimentoPutPostDTO;
import com.ufcg.psoft.mercadofacil.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import com.ufcg.psoft.mercadofacil.service.estabelecimento.EstabelecimentoAtualizarService;
import com.ufcg.psoft.mercadofacil.service.estabelecimento.EstabelecimentoCriarService;
import com.ufcg.psoft.mercadofacil.service.estabelecimento.EstabelecimentoListarService;
import com.ufcg.psoft.mercadofacil.service.estabelecimento.EstabelecimentoRemoverService;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@DisplayName("Testes para o service de Estabelecimento")
public class EstabelecimentoServiceTest {
    @Autowired
    EstabelecimentoCriarService driverCriar;
    @Autowired
    EstabelecimentoListarService driverListar;
    @Autowired
    EstabelecimentoAtualizarService driverAtualizar;
    @Autowired
    EstabelecimentoRemoverService driverRemover;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    Estabelecimento estabelecimento;

    @BeforeEach
    void setUp() {
        estabelecimento = estabelecimentoRepository.save(
                Estabelecimento.builder()
                        .codigoAcesso("123456")
                        .build()
        );
    }

    @AfterEach
    void tearDown() {
        estabelecimentoRepository.deleteAll();
    }

    @Test
    @DisplayName("Teste quando criamos um novo estabelecimento")
    void testeCriaNovoEstabelecimento() {
        Estabelecimento resultado = driverCriar.salvar(Estabelecimento.builder()
                .codigoAcesso("654321")
                .build()
        );

        assertEquals("654321", resultado.getCodigoAcesso());
    }

    @Test
    @DisplayName("Teste quando listamos todos os estabelecimentos cadastrados")
    void testeListaTodosEstabelecimentos() {
        Estabelecimento estabelecimento1 = Estabelecimento.builder()
                .codigoAcesso("456321")
                .build();

        Estabelecimento estabelecimento2 = Estabelecimento.builder()
                .codigoAcesso("321456")
                .build();

        estabelecimentoRepository.saveAll(Arrays.asList(estabelecimento1, estabelecimento2));

        List<Estabelecimento> resultado = driverListar.listar(null);
        assertEquals(3, resultado.size());
    }

    @Test
    @DisplayName("Teste quando listamos um estabelecimentos pelo id")
    void testeListaEstabelecimentoPorId() {
        Long estabelecimentoId = estabelecimentoRepository.save(Estabelecimento.builder()
                .codigoAcesso("456321")
                .build()
        ).getId();

        List<Estabelecimento> resultado = driverListar.listar(estabelecimentoId);

        System.out.println(resultado);
        System.out.println(estabelecimentoId);
        assertAll(
                () -> assertEquals(estabelecimentoId, resultado.get(0).getId()),
                () -> assertEquals("456321", resultado.get(0).getCodigoAcesso())
        );
    }


    @Test
    @DisplayName("Quando atualizamos um estabelecimento")
    void testeAtualizaEstabelecimento() {
        Long estabelecimentoId = estabelecimentoRepository.save(Estabelecimento.builder()
                .codigoAcesso("789342")
                .build()).getId();

        EstabelecimentoPutPostDTO estabelecimentoAtualizado = EstabelecimentoPutPostDTO.builder()
                .codigoAcesso("123456")
                .build();

        Estabelecimento resultado = driverAtualizar.atualizar(estabelecimentoId, estabelecimentoAtualizado);

        assertAll(
                () -> assertEquals(estabelecimentoId, resultado.getId()),
                () -> assertEquals("123456", resultado.getCodigoAcesso())
        );
    }

    @Test
    @DisplayName("Quando removemos um estabelecimento")
    void testeRemoveEstabelecimento() {
        Long estabelecimentoId = estabelecimentoRepository.save(Estabelecimento.builder()
                .codigoAcesso("789342")
                .build()).getId();

        List<Estabelecimento> resultBefore = estabelecimentoRepository.findAll();

        driverRemover.remover(estabelecimentoId);

        List<Estabelecimento> resultAfter = estabelecimentoRepository.findAll();

        assertAll(
                () -> assertEquals(2, resultBefore.size()),
                () -> assertEquals(1, resultAfter.size())
        );
    }
}
