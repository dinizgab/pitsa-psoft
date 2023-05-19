package com.ufcg.psoft.pitsA.service.cliente;

import com.ufcg.psoft.pitsA.dto.cliente.ClienteDeleteDTO;
import com.ufcg.psoft.pitsA.dto.cliente.ClientePostPutDTO;
import com.ufcg.psoft.pitsA.dto.cliente.ClienteReadDTO;
import com.ufcg.psoft.pitsA.exception.auth.CodigoAcessoInvalidoException;
import com.ufcg.psoft.pitsA.model.Cliente;
import com.ufcg.psoft.pitsA.repository.ClienteRepository;
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
@DisplayName("Testes dos servicos de Cliente")
public class ClienteServiceTests {
    @Autowired
    ClienteCriarService driverCriar;
    @Autowired
    ClienteListarService driverListar;
    @Autowired
    ClienteAtualizarService driverAtualizar;
    @Autowired
    ClienteRemoverService driverRemover;
    @Autowired
    ClienteRepository clienteRepository;
    ClientePostPutDTO cliente;

    @BeforeEach
    void setUp() {
        cliente = ClientePostPutDTO.builder()
                .nome("Gabriel Pombo Diniz")
                .endereco("Rua 13 de maio, 778")
                .codigoAcesso("676767")
                .build();
    }

    @AfterEach
    void tearDown() {
        clienteRepository.deleteAll();
    }

    @Test
    @DisplayName("Quando cria um novo entregador no sistema")
    void testeCriarCliente() {
        ClienteReadDTO resultado = driverCriar.salvar(cliente);

        assertAll(
                () -> assertEquals("Gabriel Pombo Diniz", resultado.getNome()),
                () -> assertEquals("Rua 13 de maio, 778", resultado.getEndereco())
        );
    }

    // TODO - Corrigir o erro nesses testes na hora de listar -- Modelmapper nao consegue fazer o mapeamento
    @Test
    @DisplayName("Quando lista todos os entregadores cadastrados")
    void testeListaTodosClientes() {
        Cliente cliente1 = Cliente.builder()
                .nome("Junior Junior")
                .endereco("Rua 17 de abril, 777")
                .codigoAcesso("999999")
                .build();

        Cliente cliente2 = Cliente.builder()
                .nome("Cleber Junior")
                .endereco("Rua 28 de outubro, 2810")
                .codigoAcesso("101010")
                .build();

        clienteRepository.saveAll(Arrays.asList(cliente1, cliente2));
        List<ClienteReadDTO> resultado = driverListar.listar(null);

        assertEquals(2, resultado.size());
    }

    @Test
    @DisplayName("Quando lista um entregador pelo seu ID")
    void testeListaClientePorId() {
        Long clienteId = clienteRepository.save(
                Cliente.builder()
                        .nome("Cleber Junior")
                        .endereco("Rua 28 de outubro, 2810")
                        .codigoAcesso("101010")
                        .build()
        ).getId();

        ClienteReadDTO resultado = driverListar.listar(clienteId).get(0);

        assertAll(
                () -> assertEquals("Cleber Junior", resultado.getNome()),
                () -> assertEquals("Rua 28 de outubro, 2810", resultado.getEndereco())
        );
    }

    @Test
    @DisplayName("Quando atualizar um Cliente cadastrado com codigo de acesso valido")
    void testeAtualizarClienteCodigoValido() {
        Long clienteId = clienteRepository.save(Cliente.builder()
                .nome("Gabriel Pombo Diniz")
                .endereco("Rua 13 de maio, 778")
                .codigoAcesso("676767")
                .build()
        ).getId();

        ClientePostPutDTO clienteAtualizado = ClientePostPutDTO.builder()
                .nome("Juliano junior")
                .endereco("Rua Legal, 100")
                .codigoAcesso("676767")
                .build();

        ClienteReadDTO resultado = driverAtualizar.alterar(clienteId, clienteAtualizado);

        assertAll(
                () -> assertEquals("Juliano junior", resultado.getNome()),
                () -> assertEquals("Rua Legal, 100", resultado.getEndereco())
        );
    }

    @Test
    @DisplayName("Quando atualizar um Cliente cadastrado com codigo de acesso invalido")
    void testeAtualizarClienteCodigoInvalido() {
        Long clienteId = clienteRepository.save(Cliente.builder()
                .nome("Gabriel Pombo Diniz")
                .endereco("Rua 13 de maio, 778")
                .codigoAcesso("111111")
                .build()
        ).getId();

        ClientePostPutDTO putDTO = ClientePostPutDTO.builder()
                .nome("Juliano junior")
                .endereco("Rua Legal, 100")
                .codigoAcesso("653432")
                .build();

        assertThrows(CodigoAcessoInvalidoException.class, () -> driverAtualizar.alterar(clienteId, putDTO));
    }

    @Test
    @DisplayName("Quando remover um cliente cadastrado com o codigo de acessp valido")
    void testeRemoverClienteCodigoValido() {
        Long clienteId = clienteRepository.save(Cliente.builder()
                .nome("Cleber Junior")
                .endereco("Sem ideia, 0000")
                .codigoAcesso("123456")
                .build()
        ).getId();
        ClienteDeleteDTO deleteDTO = ClienteDeleteDTO.builder()
                .codigoAcesso("123456")
                .build();


        List<Cliente> resultBefore = clienteRepository.findAll();

        driverRemover.remover(clienteId, deleteDTO);

        List<Cliente> resultAfter = clienteRepository.findAll();

        assertAll(
                () -> assertEquals(1, resultBefore.size()),
                () -> assertEquals(0, resultAfter.size())
        );
    }

    @Test
    @DisplayName("Quando remover um cliente cadastrado com o codigo de acesso invalido")
    void testeRemoverClienteCodigoInvalido() {
        Long clienteId = clienteRepository.save(Cliente.builder()
                .nome("Cleber Junior")
                .endereco("Sem ideia, 0000")
                .codigoAcesso("123456")
                .build()
        ).getId();
        ClienteDeleteDTO deleteDTO = ClienteDeleteDTO.builder()
                .codigoAcesso("654321")
                .build();

        assertThrows(CodigoAcessoInvalidoException.class, () -> driverRemover.remover(clienteId, deleteDTO));
    }
}
