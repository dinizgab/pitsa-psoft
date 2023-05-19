package com.ufcg.psoft.pitsA.controller;

import com.ufcg.psoft.pitsA.dto.cliente.ClienteCardapioDTO;
import com.ufcg.psoft.pitsA.dto.cliente.ClienteDeleteDTO;
import com.ufcg.psoft.pitsA.dto.cliente.ClienteInteresseDTO;
import com.ufcg.psoft.pitsA.dto.cliente.ClientePostPutDTO;
import com.ufcg.psoft.pitsA.exception.ErrorMessage;
import com.ufcg.psoft.pitsA.exception.auth.CodigoAcessoInvalidoException;
import com.ufcg.psoft.pitsA.service.cliente.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/v1/cliente",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ClienteV1Controller {
    @Autowired
    ClienteListarService clienteListarService;
    @Autowired
    ClienteAtualizarService clienteAtualizarService;
    @Autowired
    ClienteCriarService clienteCriarService;
    @Autowired
    ClienteRemoverService clienteExcluirService;
    @Autowired
    ClienteListarCardapioService clienteListarCardapioService;
    @Autowired
    ClientePatchInteresseSaborService clientePatchInteresseSaborService;


    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUmCliente(
            @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteListarService.listar(id));
    }

    @GetMapping("")
    public ResponseEntity<?> buscarTodosClientes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteListarService.listar(null));
    }

    @PostMapping()
    public ResponseEntity<?> salvarCliente(
            @RequestBody @Valid ClientePostPutDTO cliente) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(clienteCriarService.salvar(cliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCliente(
            @PathVariable Long id,
            @RequestBody @Valid ClientePostPutDTO clientePostPutRequestDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteAtualizarService.alterar(id, clientePostPutRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirCliente (
            @PathVariable Long id,
            @RequestBody @Valid  ClienteDeleteDTO deleteBody
    ) {
        clienteExcluirService.remover(id, deleteBody);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }

    @GetMapping("/{id}/cardapio")
    public ResponseEntity<?> buscarUmCardapio(
            @PathVariable("id") Long clienteId,
            @RequestBody @Valid ClienteCardapioDTO clienteCardapioDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteListarCardapioService.listarCardapio(clienteId, clienteCardapioDTO));
    }

    // TODO - Trocar esses bodies por Request Params
    @PatchMapping("/{id}/sabor")
    public ResponseEntity<?> demonstrarInteresseSabor(
            @PathVariable("id") Long clienteId,
            @RequestBody @Valid ClienteInteresseDTO clienteInteresseDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientePatchInteresseSaborService.demonstraInteresse(clienteId, clienteInteresseDTO));
    }

    @ExceptionHandler(CodigoAcessoInvalidoException.class)
    public ResponseEntity<?> codigoAcessoInvalido(CodigoAcessoInvalidoException err) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(err));
    }
}
