package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.cliente.ClienteInteresseDTO;
import com.ufcg.psoft.pitsA.dto.cliente.ClienteReadDTO;
import com.ufcg.psoft.pitsA.exception.auth.CodigoAcessoInvalidoException;
import com.ufcg.psoft.pitsA.exception.sabor.SaborEstaDisponivelException;
import com.ufcg.psoft.pitsA.model.Cliente;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DisplayName("Testes do service do cliente demonstrar interesse a um sabor")
public class ClienteInteresseSaborServiceTest {
    @Autowired
    ClientePatchInteresseSaborService driver;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    SaborRepository saborRepository;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    Cliente cliente;
    Sabor sabor;
    Estabelecimento estabelecimento;

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
                        .tipo(TipoSabor.SALGADO)
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
        estabelecimentoRepository.deleteAll();
        clienteRepository.deleteAll();
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

        assertTrue(sabor.getInteressados().contains(cliente));
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
                        .tipo(TipoSabor.SALGADO)
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
