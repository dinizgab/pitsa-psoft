package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.sabor.SaborPutDTO;
import com.ufcg.psoft.pitsA.dto.sabor.SaborReadDTO;
import com.ufcg.psoft.pitsA.dto.sabor.SaborDeleteDTO;
import com.ufcg.psoft.pitsA.dto.sabor.SaborPostDTO;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.Sabor;
import com.ufcg.psoft.pitsA.model.TipoSaborPizza;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import com.ufcg.psoft.pitsA.repository.SaborRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Testes dos servicos de sabor")
public class SaborServiceTests {
    @Autowired
    SaborCreateService driverCriar;
    @Autowired
    SaborListarService driverListar;
    @Autowired
    SaborUpdateService driverAtualizar;
    @Autowired
    SaborRemoverService driverRemover;
    @Autowired
    SaborRepository saborRepository;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    Sabor sabor;
    Estabelecimento estabelecimento;


    @BeforeEach
    @Transactional
    void setUp() {
        estabelecimento = Estabelecimento.builder()
                .codigoAcesso("123456")
                .build();

        sabor = saborRepository.save(
                Sabor.builder()
                        .nome("Calabresa")
                        .tipo(TipoSaborPizza.SALGADO)
                        .precoGrande(44.0)
                        .precoMedio(22.0)
                        .estabelecimento(estabelecimento)
                        .build()
        );

        estabelecimento.getCardapio().add(sabor);
        estabelecimento = estabelecimentoRepository.save(estabelecimento);
    }

    @AfterEach
    void tearDown() {
        saborRepository.deleteAll();
        estabelecimentoRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("Criando um novo Sabor num estabelecimento")
    void testeCriaNovoSabor() {
        SaborPostDTO postBody = SaborPostDTO.builder()
                .codigoAcesso("123456")
                .nome("4 queijos")
                .tipo(TipoSaborPizza.SALGADO)
                .precoGrande(44.0)
                .precoMedio(22.0)
                .build();

        SaborReadDTO resultado = driverCriar.create(estabelecimento.getId(), postBody);

        assertAll(
                () -> assertEquals(estabelecimento, resultado.getEstabelecimento()),
                () -> assertEquals("4 queijos", resultado.getNome()),
                () -> assertTrue(resultado.getTipo().isSalgado()),
                () -> assertEquals(44.0, resultado.getPrecoGrande()),
                () -> assertEquals(22.0, resultado.getPrecoMedio())
        );
    }

    @Test
    @DisplayName("Lista sabor pelo id")
    void testeListaSaborId() {
        Sabor resultado = driverListar.listar(sabor.getId()).get(0);

        assertAll(
                () -> assertEquals("Calabresa", resultado.getNome()),
                () -> assertEquals(44.0, resultado.getPrecoGrande()),
                () -> assertEquals(22.0, resultado.getPrecoMedio()),
                () -> assertTrue(resultado.getTipo().isSalgado()),
                () -> assertNull(resultado.getEstabelecimento())
        );
    }

    @Test
    @DisplayName("Lista todos os sabores")
    void testeListaTodosSabores() {
            Sabor sabor1 = Sabor.builder()
                    .nome("Calabresa")
                    .tipo(TipoSaborPizza.SALGADO)
                    .precoGrande(44.0)
                    .precoMedio(22.0)
                    .build();

            Sabor sabor2 = Sabor.builder()
                    .nome("Frango")
                    .tipo(TipoSaborPizza.SALGADO)
                    .precoGrande(44.0)
                    .precoMedio(22.0)
                    .build();

            List<Sabor> resultado = saborRepository.saveAll(Arrays.asList(sabor1, sabor2));
            assertEquals(2, resultado.size());
    }

    @Test
    @Transactional
    @DisplayName("Atualiza um sabor")
    void testeAtualizaSabor() {
        SaborPutDTO putBody = SaborPutDTO.builder()
                .nome("Frango com bacon")
                .precoGrande(20.0)
                .precoMedio(10.0)
                .tipo(TipoSaborPizza.SALGADO)
                .codigoAcesso("123456")
                .estabelecimentoId(estabelecimento.getId())
                .build();


        SaborReadDTO resultado = driverAtualizar.update(sabor.getId(), putBody);

        assertAll(
                () -> assertEquals(putBody.getNome(), resultado.getNome()),
                () -> assertEquals(putBody.getPrecoGrande(), resultado.getPrecoGrande()),
                () -> assertEquals(putBody.getPrecoMedio(), resultado.getPrecoMedio()),
                () -> assertTrue(resultado.getTipo().isSalgado())
        );
    }

    @Test
    @DisplayName("Remove um sabor")
    void testeRemoveSabor() {
        saborRepository.save(Sabor.builder()
                .nome("Calabresa")
                .tipo(TipoSaborPizza.SALGADO)
                .precoGrande(44.0)
                .precoMedio(22.0)
                .estabelecimento(estabelecimento)
                .build());

        SaborDeleteDTO deleteBody = SaborDeleteDTO
                .builder()
                .estabelecimentoId(estabelecimento.getId())
                .codigoAcesso("123456")
                .build();

        List<Sabor> resultBefore = saborRepository.findAll();

        driverRemover.remover(sabor.getId(), deleteBody);

        List<Sabor> resultAfter = saborRepository.findAll();

        assertAll(
                () -> assertEquals(2, resultBefore.size()),
                () -> assertEquals(1, resultAfter.size()),
                () -> assertFalse(estabelecimento.getCardapio().contains(sabor))
        );
    }
}
