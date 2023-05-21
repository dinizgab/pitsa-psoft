package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.dto.estabelecimento.EstabelecimentoDeleteDTO;
import com.ufcg.psoft.pitsA.dto.estabelecimento.EstabelecimentoPostDTO;
import com.ufcg.psoft.pitsA.dto.estabelecimento.EstabelecimentoPutDTO;
import com.ufcg.psoft.pitsA.exception.auth.CodigoAcessoInvalidoException;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import jakarta.transaction.Transactional;
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
        Estabelecimento resultado = driverCriar.salvar(EstabelecimentoPostDTO.builder()
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

    @Transactional
    @Test
    @DisplayName("Teste quando listamos um estabelecimentos pelo id")
    void testeListaEstabelecimentoPorId() {
        Long estabelecimentoId = estabelecimentoRepository.save(Estabelecimento.builder()
                .codigoAcesso("456321")
                .build()
        ).getId();

        List<Estabelecimento> resultado = driverListar.listar(estabelecimentoId);

        assertAll(
                () -> assertEquals(estabelecimentoId, resultado.get(0).getId()),
                () -> assertEquals("456321", resultado.get(0).getCodigoAcesso())
        );
    }


    @Test
    @DisplayName("Quando atualizamos um estabelecimento codigo valido")
    void testeAtualizaEstabelecimentoValido() {
        Long estabelecimentoId = estabelecimentoRepository.save(Estabelecimento.builder()
                .codigoAcesso("789342")
                .build()).getId();

        EstabelecimentoPutDTO putBody = EstabelecimentoPutDTO.builder()
                .codigoAcesso("789342")
                .codigoAcessoAlterado("123456")
                .build();

        Estabelecimento resultado = driverAtualizar.atualizar(estabelecimentoId, putBody);

        assertAll(
                () -> assertEquals(estabelecimentoId, resultado.getId()),
                () -> assertEquals("123456", resultado.getCodigoAcesso())
        );
    }

    @Test
    @DisplayName("Quando atualizamos um estabelecimento codigo invalido")
    void testeAtualizaEstabelecimentoInvalido() {
        Long estabelecimentoId = estabelecimentoRepository.save(Estabelecimento.builder()
                .codigoAcesso("789342")
                .build()).getId();

        EstabelecimentoPutDTO putBody = EstabelecimentoPutDTO.builder()
                .codigoAcesso("111111")
                .codigoAcessoAlterado("123456")
                .build();

        assertThrows(CodigoAcessoInvalidoException.class, () -> driverAtualizar.atualizar(estabelecimentoId, putBody));
    }

    @Test
    @DisplayName("Quando removemos um estabelecimento")
    void testeRemoveEstabelecimento() {
        Long estabelecimentoId = estabelecimentoRepository.save(Estabelecimento.builder()
                .codigoAcesso("789342")
                .build()).getId();

        EstabelecimentoDeleteDTO deleteBody = EstabelecimentoDeleteDTO.builder()
                .codigoAcesso("789342")
                .build();

        List<Estabelecimento> resultBefore = estabelecimentoRepository.findAll();

        driverRemover.remover(estabelecimentoId, deleteBody);

        List<Estabelecimento> resultAfter = estabelecimentoRepository.findAll();

        assertAll(
                () -> assertEquals(2, resultBefore.size()),
                () -> assertEquals(1, resultAfter.size())
        );
    }

    @Test
    @DisplayName("Quando removemos um estabelecimento codigo invalido")
    void testeRemoveEstabelecimentoInvalido() {
        Long estabelecimentoId = estabelecimentoRepository.save(Estabelecimento.builder()
                .codigoAcesso("789342")
                .build()).getId();

        EstabelecimentoDeleteDTO deleteBody = EstabelecimentoDeleteDTO.builder()
                .codigoAcesso("111111")
                .build();

        assertThrows(CodigoAcessoInvalidoException.class, () -> driverRemover.remover(estabelecimentoId, deleteBody));
    }
}
