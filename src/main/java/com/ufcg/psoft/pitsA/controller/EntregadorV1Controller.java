package com.ufcg.psoft.pitsA.controller;

import com.ufcg.psoft.pitsA.dto.EntregadorPatchEstabelecimentoDTO;
import com.ufcg.psoft.pitsA.dto.EntregadorPostPutDTO;
import com.ufcg.psoft.pitsA.service.entregador.*;

import com.ufcg.psoft.pitsA.service.entregador.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/v1/entregador",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class EntregadorV1Controller {

    @Autowired
    EntregadorListarService entregadorListarService;
    @Autowired
    EntregadorCriarService entregadorCriarService;
    @Autowired
    EntregadorAtualizarService entregadorAtualizarService;
    @Autowired
    EntregadorRemoverService entregadorExcluirService;
    @Autowired
    EntregadorPatchEstabelecimentoService entregadorPatchEstabelecimentoService;


    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUmEntregador(
            @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entregadorListarService.listar(id));
    }

    @GetMapping("")
    public ResponseEntity<?> buscarTodosEntregadores() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entregadorListarService.listar(null));
    }

    @PostMapping()
    public ResponseEntity<?> salvarEntregador(
            @RequestBody @Valid EntregadorPostPutDTO entregador) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(entregadorCriarService.salvar(entregador));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarEntregador(
            @PathVariable Long id,
            @RequestBody @Valid EntregadorPostPutDTO entregadorPostPutRequestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entregadorAtualizarService.atualizar(id, entregadorPostPutRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirEntregador(
            @PathVariable Long id
    ) {
        entregadorExcluirService.remover(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarEntregador(
            @PathVariable Long id,
            @RequestBody @Valid EntregadorPatchEstabelecimentoDTO entregadorPatchEstabelecimentoDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entregadorPatchEstabelecimentoService.alteraParcialmente(id, entregadorPatchEstabelecimentoDTO));
    }
}
