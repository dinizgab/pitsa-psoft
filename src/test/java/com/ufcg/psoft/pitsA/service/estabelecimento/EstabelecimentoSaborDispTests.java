package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.dto.estabelecimento.EstabelecimentoPatchDispDTO;
import com.ufcg.psoft.pitsA.dto.sabor.SaborDTO;
import com.ufcg.psoft.pitsA.exception.auth.CodigoAcessoInvalidoException;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.Sabor;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import com.ufcg.psoft.pitsA.repository.SaborRepository;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@DisplayName("Testes para troca de disponibilidade de sabor")
public class EstabelecimentoSaborDispTests {
    @Autowired
    EstabelecimentoPatchDispSaborService driver;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    SaborRepository saborRepository;
    Estabelecimento estabelecimento;
    Sabor sabor;

    @BeforeEach
    @Transactional
    void setUp() {
        estabelecimento = Estabelecimento.builder()
                        .codigoAcesso("123456")
                .build();

        sabor = saborRepository.save(Sabor.builder()
                        .nome("Carne de sol")
                        .precoGrande(30.0)
                        .precoMedio(15.0)
                        .estabelecimento(estabelecimento)
                        .tipo(true)
                .build()
        );

        List<Sabor> cardapio = new ArrayList<>();
        cardapio.add(sabor);

        estabelecimento.setCardapio(cardapio);
        estabelecimento = estabelecimentoRepository.save(estabelecimento);
    }

    @AfterEach
    void tearDown() {
        estabelecimentoRepository.deleteAll();
        saborRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("Teste altera disponibilidade valido")
    void testeAlteraDisponibilidadeValido() {
        Long saborId = sabor.getId();
        Long estabelecimentoId = estabelecimento.getId();

        EstabelecimentoPatchDispDTO alteraDisponibilidadeDTO = EstabelecimentoPatchDispDTO.builder()
                .codigoAcesso("123456")
                .saborId(saborId)
                .build();

        SaborDTO resultado = driver.alteraDisponibilidade(estabelecimentoId, alteraDisponibilidadeDTO);

        assertFalse(resultado.isDisponivel());
    }

    @Test
    @Transactional
    @DisplayName("Teste altera disponibilidade invalido")
    void testeAlteraDisponibilidadeInvalido() {
        Long saborId = sabor.getId();
        Long estabelecimentoId = estabelecimento.getId();

        EstabelecimentoPatchDispDTO alteraDisponibilidadeDTO = EstabelecimentoPatchDispDTO.builder()
                .codigoAcesso("654321")
                .saborId(saborId)
                .build();

        assertThrows(CodigoAcessoInvalidoException.class, () -> driver.alteraDisponibilidade(estabelecimentoId, alteraDisponibilidadeDTO));
    }
}
