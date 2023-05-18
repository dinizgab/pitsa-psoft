package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.cliente.ClienteInteresseDTO;
import com.ufcg.psoft.pitsA.dto.cliente.ClienteReadDTO;
import com.ufcg.psoft.pitsA.exception.auth.CodigoAcessoInvalidoException;
import com.ufcg.psoft.pitsA.exception.sabor.SaborEstaDisponivelException;
import com.ufcg.psoft.pitsA.model.Cliente;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.Sabor;
import com.ufcg.psoft.pitsA.repository.ClienteRepository;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import com.ufcg.psoft.pitsA.repository.SaborRepository;
import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoPatchDispSaborService;
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
@DisplayName("Testes do service do cliente demonstrar interesse a um sabor")
public class ClienteInteresseSaborServiceTest {
    @Autowired
    ClientePatchInteresseSaborService driver;
    @Autowired
    EstabelecimentoPatchDispSaborService estabelecimentoPatchDispSaborService;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    SaborRepository saborRepository;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    Cliente cliente;
    Sabor sabor;
    Estabelecimento estabelecimento;

    // TODO - Quando alterar a disponibilidade do sabor, a lista sera limpa e printara no terminal quem estava dentro dela

    @BeforeEach
    void setUp() {
        cliente = clienteRepository.save(
                Cliente.builder()
                        .codigoAcesso("123456")
                        .nome("Gabriel")
                        .endereco("Rua das umburanas, 777")
                        .build()
        );

        estabelecimento = Estabelecimento.builder()
                        .codigoAcesso("123456")
                        .build();

        sabor = saborRepository.save(
                Sabor.builder()
                        .nome("Calabresa")
                        .precoGrande(50.0)
                        .precoMedio(25.0)
                        .tipo(true)
                        .disponivel(false)
                        .estabelecimento(estabelecimento)
                        .build()
        );

        List<Sabor> cardapio = new ArrayList<>();
        cardapio.add(sabor);
        estabelecimento.setCardapio(cardapio);

        estabelecimentoRepository.save(estabelecimento);
    }

    @AfterEach
    void tearDown() {
        clienteRepository.deleteAll();
        saborRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("Quando um cliente demonstra interesse com sucesso")
    void testeDemonstraInteresseComSucesso() {
        Long clienteId = cliente.getId();
        Long estabelecimentoId = estabelecimento.getId();

        ClienteInteresseDTO clienteInteresseDTO = ClienteInteresseDTO.builder()
                .codigoAcesso("123456")
                .saborId(sabor.getId())
                .estabelecimentoId(estabelecimentoId)
                .build();

        ClienteReadDTO resultado = driver.demonstraInteresse(clienteId, clienteInteresseDTO);

        assertTrue(resultado.getInteressesSabores().contains(sabor));
    }

    @Test
    @Transactional
    @DisplayName("Quando um cliente demonstra interesse em um sabor ja disponivel")
    void testeDemonstraInteresseSaborDisponivel() {
        Long clienteId = cliente.getId();
        Long estabelecimentoId = estabelecimento.getId();

        Sabor saborInvalido = saborRepository.save(
                Sabor.builder()
                        .nome("4 Queijos")
                        .precoGrande(50.0)
                        .precoMedio(25.0)
                        .tipo(true)
                        .disponivel(true)
                        .estabelecimento(estabelecimento)
                        .build()
        );

        ClienteInteresseDTO clienteInteresseDTO = ClienteInteresseDTO.builder()
                .codigoAcesso("123456")
                .saborId(saborInvalido.getId())
                .estabelecimentoId(estabelecimentoId)
                .build();

        assertThrows(SaborEstaDisponivelException.class, () -> driver.demonstraInteresse(clienteId, clienteInteresseDTO));
    }

    @Test
    @Transactional
    @DisplayName("Quando um cliente demonstra interesse com um codigo invalido")
    void testeDemonstraInteresseCodigoInvalido() {
        Long clienteId = cliente.getId();
        Long estabelecimentoId = estabelecimento.getId();

        ClienteInteresseDTO clienteInteresseDTO = ClienteInteresseDTO.builder()
                .codigoAcesso("654321")
                .saborId(sabor.getId())
                .estabelecimentoId(estabelecimentoId)
                .build();

        assertThrows(CodigoAcessoInvalidoException.class, () -> driver.demonstraInteresse(clienteId, clienteInteresseDTO));
    }
}
