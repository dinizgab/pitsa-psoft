package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.dto.estabelecimento.EstabelecimentoPatchDispDTO;
import com.ufcg.psoft.pitsA.dto.sabor.SaborReadDTO;
import com.ufcg.psoft.pitsA.exception.auth.CodigoAcessoInvalidoException;
import com.ufcg.psoft.pitsA.model.Cliente;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.sabor.Interessado;
import com.ufcg.psoft.pitsA.model.sabor.Sabor;
import com.ufcg.psoft.pitsA.model.sabor.TipoSabor;
import com.ufcg.psoft.pitsA.repository.ClienteRepository;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import com.ufcg.psoft.pitsA.repository.SaborRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayName("Testes para troca de disponibilidade de sabor")
public class EstabelecimentoSaborDispTests {
    @Autowired
    EstabelecimentoPatchDispSaborService driver;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    SaborRepository saborRepository;
    @Autowired
    ClienteRepository clienteRepository;
    Estabelecimento estabelecimento;
    Sabor saborDisponivel;
    Sabor saborIndisponivel;
    Cliente cliente;

    @BeforeEach
    @Transactional
    void setUp() {
        estabelecimento = Estabelecimento.builder()
                        .codigoAcesso("123456")
                .build();

        saborDisponivel = saborRepository.save(Sabor.builder()
                        .nome("Frango com bacon")
                        .precoGrande(50.0)
                        .precoMedio(25.0)
                        .estabelecimento(estabelecimento)
                        .tipo(TipoSabor.SALGADO)
                .build()
        );

        cliente = Cliente.builder()
                .nome("Gabriel")
                .endereco("5432 Costa Sousa, 342")
                .codigoAcesso("123456")
                .build();

        Set<Interessado> interessados = new HashSet<>();
        interessados.add(cliente);

        saborIndisponivel = saborRepository.save(Sabor.builder()
                .nome("Carne de sol")
                .precoGrande(30.0)
                .precoMedio(15.0)
                .estabelecimento(estabelecimento)
                .interessados(interessados)
                .tipo(TipoSabor.SALGADO)
                .disponivel(false)
                .build()
        );

        List<Sabor> cardapio = new ArrayList<>();
        cardapio.add(saborDisponivel);
        cardapio.add(saborIndisponivel);

        estabelecimento.setCardapio(cardapio);
        estabelecimento = estabelecimentoRepository.save(estabelecimento);
        cliente = clienteRepository.save(cliente);
    }

    @AfterEach
    void tearDown() {
        clienteRepository.deleteAll();
        estabelecimentoRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("Teste altera disponibilidade valido")
    void testeAlteraDisponibilidadeValido() {
        Long saborId = saborDisponivel.getId();
        Long estabelecimentoId = estabelecimento.getId();

        EstabelecimentoPatchDispDTO alteraDisponibilidadeDTO = EstabelecimentoPatchDispDTO.builder()
                .codigoAcesso("123456")
                .saborId(saborId)
                .build();

        SaborReadDTO resultado = driver.alteraDisponibilidade(estabelecimentoId, alteraDisponibilidadeDTO);

        assertFalse(resultado.isDisponivel());
    }

    @Test
    @Transactional
    @DisplayName("Teste altera disponibilidade invalido")
    void testeAlteraDisponibilidadeInvalido() {
        Long saborId = saborDisponivel.getId();
        Long estabelecimentoId = estabelecimento.getId();

        EstabelecimentoPatchDispDTO alteraDisponibilidadeDTO = EstabelecimentoPatchDispDTO.builder()
                .codigoAcesso("654321")
                .saborId(saborId)
                .build();

        assertThrows(CodigoAcessoInvalidoException.class, () -> driver.alteraDisponibilidade(estabelecimentoId, alteraDisponibilidadeDTO));
    }
}
