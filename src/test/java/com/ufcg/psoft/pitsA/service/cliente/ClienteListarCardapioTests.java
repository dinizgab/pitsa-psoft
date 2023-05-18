package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.cliente.ClienteCardapioDTO;
import com.ufcg.psoft.pitsA.dto.sabor.SaborReadDTO;
import com.ufcg.psoft.pitsA.exception.auth.CodigoAcessoInvalidoException;
import com.ufcg.psoft.pitsA.model.Cliente;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.Sabor;
import com.ufcg.psoft.pitsA.repository.ClienteRepository;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Testes do servico de listagem de cardapio do cliente")
public class ClienteListarCardapioTests {
    @Autowired
    ClienteListarCardapioService driver;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    Estabelecimento estabelecimento;
    Cliente cliente;

    @BeforeEach
    void setUp() {
        estabelecimento = Estabelecimento.builder()
                        .codigoAcesso("123456")
                        .build();

        cliente = clienteRepository.save(
                Cliente.builder()
                        .nome("Gabriel")
                        .endereco("Rua Irineu Joffily, 5234")
                        .codigoAcesso("654321")
                        .build()
        );

        List<Sabor> cardapio = new ArrayList<>();
        cardapio.add(Sabor.builder()
                        .estabelecimento(estabelecimento)
                        .precoGrande(55.0)
                        .precoMedio(27.5)
                        .nome("Calabresa")
                        .disponivel(false)
                .build()
        );
        cardapio.add(Sabor.builder()
                .estabelecimento(estabelecimento)
                .precoGrande(50.0)
                .precoMedio(25.0)
                .nome("4 Queijos")
                .disponivel(true)
                .build()
        );

        cardapio.add(Sabor.builder()
                .estabelecimento(estabelecimento)
                .precoGrande(60.0)
                .precoMedio(30.0)
                .nome("Frango com bacon")
                .disponivel(false)
                .build()
        );

        estabelecimento.setCardapio(cardapio);
        estabelecimento = estabelecimentoRepository.save(estabelecimento);
    }

    @AfterEach
    void tearDown() {
        estabelecimentoRepository.deleteAll();
        clienteRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("Quando listamos o cardapio com codigo de acesso valido")
    void testeListarCardapioValido() {
        Long clienteId = cliente.getId();
        Long estabelecimentoId = estabelecimento.getId();

        ClienteCardapioDTO clienteCardapioDTO = ClienteCardapioDTO.builder()
                .estabelecimentoId(estabelecimentoId)
                .codigoAcesso(cliente.getCodigoAcesso())
                .build();

        List<SaborReadDTO> resultado = driver.listarCardapio(clienteId, clienteCardapioDTO);
        System.out.println(resultado);
        assertAll(
                () -> assertEquals(3, resultado.size()),
                () -> assertTrue(resultado.get(0).isDisponivel()),
                () -> assertFalse(resultado.get(1).isDisponivel())
        );
    }

    @Test
    @Transactional
    @DisplayName("Quando listamos o cardapio com codigo de acesso invalido")
    void testeListarCardapioInvalido() {
        Long clienteId = cliente.getId();
        Long estabelecimentoId = estabelecimento.getId();

        ClienteCardapioDTO clienteCardapioDTO = ClienteCardapioDTO.builder()
                .estabelecimentoId(estabelecimentoId)
                .codigoAcesso("1111111")
                .build();

        assertThrows(CodigoAcessoInvalidoException.class, () -> driver.listarCardapio(clienteId, clienteCardapioDTO));
    }
}
