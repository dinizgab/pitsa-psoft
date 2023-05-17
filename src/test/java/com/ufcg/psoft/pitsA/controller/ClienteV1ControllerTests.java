package com.ufcg.psoft.pitsA.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.pitsA.dto.cliente.ClienteDeleteDTO;
import com.ufcg.psoft.pitsA.dto.cliente.ClientePostPutDTO;
import com.ufcg.psoft.pitsA.dto.cliente.ClienteReadDTO;
import com.ufcg.psoft.pitsA.dto.estabelecimento.EstabelecimentoDeleteDTO;
import com.ufcg.psoft.pitsA.dto.estabelecimento.EstabelecimentoPutDTO;
import com.ufcg.psoft.pitsA.model.Cliente;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.repository.ClienteRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do controller de clientes")
public class ClienteV1ControllerTests {
    @Autowired
    MockMvc driver;
    @Autowired
    ClienteRepository clienteRepository;
    ObjectMapper objectMapper = new ObjectMapper();
    Cliente cliente;

    @BeforeEach
    void setup() {
        cliente = clienteRepository.save(
                Cliente.builder()
                        .nome("Gabriel")
                        .endereco("Rua 123 da Silva 4")
                        .codigoAcesso("111111")
                        .build()
        );
    }

    @AfterEach
    void tearDown() {
        clienteRepository.deleteAll();
    }

    @Nested
    @DisplayName("Testes de endpoints basicos de clientes")
    class EntregadorVerificacaoFluxosBasicosApiRest {

        final String URI_CLIENTE = "/v1/cliente";
        ClientePostPutDTO clientePutDTO;
        ClientePostPutDTO clientePostDTO;

        @BeforeEach
        void setup() {
            clientePutDTO = ClientePostPutDTO.builder()
                    .nome("Juliana")
                    .endereco("Rua 4321 da Costa Cunha")
                    .codigoAcesso("111111")
                    .build();
            clientePostDTO = ClientePostPutDTO.builder()
                    .codigoAcesso("222222")
                    .nome("Cleber")
                    .endereco("71384781")
                    .build();
        }

        @Test
        @DisplayName("Quando buscamos por todos os clientes salvos")
        void quandoBuscamosPorTodosClientesSalvos() throws Exception {
            Cliente cliente1 = Cliente.builder()
                    .nome("BBBBBBBBB")
                    .endereco("222222222222222")
                    .codigoAcesso("444444")
                    .build();

            Cliente cliente2 = Cliente.builder()
                    .nome("AAAAAA")
                    .endereco("1111111111111111")
                    .codigoAcesso("555555")
                    .build();
            clienteRepository.saveAll(Arrays.asList(cliente1, cliente2));

            String responseJsonString = driver.perform(get(URI_CLIENTE)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<ClienteReadDTO> resultado = objectMapper.readValue(responseJsonString, new TypeReference<>(){});

            assertAll(
                    () -> assertEquals(3, resultado.size())
            );

        }

        @Test
        @DisplayName("Quando buscamos um cliente salvo pelo id")
        void quandoBuscamosPorUmClienteSalvo() throws Exception {
            Long clienteId = cliente.getId();

            String responseJsonString = driver.perform(get(URI_CLIENTE + "/" + clienteId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<ClienteReadDTO> listaResultados = objectMapper.readValue(responseJsonString, new TypeReference<>(){});
            ClienteReadDTO resultado = listaResultados.stream().findFirst().orElse(ClienteReadDTO.builder().build());

            assertAll(
                    () -> assertEquals(cliente.getNome(), resultado.getNome()),
                    () -> assertEquals(cliente.getEndereco(), resultado.getEndereco())
            );
        }

        @Test
        @DisplayName("Quando criamos um novo cliente")
        void quandoCriarClienteValido() throws Exception {
            String responseJsonString = driver.perform(post(URI_CLIENTE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clientePostDTO)))
                    .andExpect(status().isCreated())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            ClienteReadDTO resultado = objectMapper.readValue(responseJsonString, ClienteReadDTO.class);

            assertAll(
                    () -> assertEquals(clientePostDTO.getNome(), resultado.getNome()),
                    () -> assertEquals(clientePostDTO.getEndereco(), resultado.getEndereco())
            );
        }

        @Test
        @DisplayName("Quando alteramos um cliente com codigo de acesso valido")
        void quandoAlteramosClienteValido() throws Exception {
            Long clienteId = cliente.getId();

            String responseJsonString = driver.perform(put(URI_CLIENTE + "/" + clienteId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clientePutDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            ClienteReadDTO resultado = objectMapper.readValue(responseJsonString, ClienteReadDTO.class);

            assertAll(
                    () -> assertEquals(clientePutDTO.getEndereco() ,resultado.getEndereco()),
                    () -> assertEquals(clientePutDTO.getNome() ,resultado.getNome())
            );
        }

        @Test
        @DisplayName("Quando excluímos um cliente salvo com codigo de acesso valido")
        void quandoExcluimosClienteValido() throws Exception {
            Long clienteId = cliente.getId();
            ClienteDeleteDTO deleteBodyValido = ClienteDeleteDTO.builder()
                    .codigoAcesso("111111")
                    .build();

            String responseJsonString = driver.perform(delete(URI_CLIENTE + "/" + clienteId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(deleteBodyValido)))
                    .andExpect(status().isNoContent())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            assertTrue(responseJsonString.isBlank());
        }
    }
}
