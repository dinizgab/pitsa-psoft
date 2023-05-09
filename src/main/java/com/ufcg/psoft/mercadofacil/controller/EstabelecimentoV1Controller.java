package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.dto.EntregadorPostPutDTO;
import com.ufcg.psoft.mercadofacil.dto.EstabelecimentoDeleteDTO;
import com.ufcg.psoft.mercadofacil.dto.EstabelecimentoPostPutDTO;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.service.entregador.EntregadorAtualizarService;
import com.ufcg.psoft.mercadofacil.service.entregador.EntregadorCriarService;
import com.ufcg.psoft.mercadofacil.service.entregador.EntregadorListarService;
import com.ufcg.psoft.mercadofacil.service.entregador.EntregadorRemoverService;

import com.ufcg.psoft.mercadofacil.service.estabelecimento.EstabelecimentoAtualizarService;
import com.ufcg.psoft.mercadofacil.service.estabelecimento.EstabelecimentoCriarService;
import com.ufcg.psoft.mercadofacil.service.estabelecimento.EstabelecimentoListarService;
import com.ufcg.psoft.mercadofacil.service.estabelecimento.EstabelecimentoRemoverService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/v1/estabelecimento",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class EstabelecimentoV1Controller {

    @Autowired
    EstabelecimentoListarService estabelecimentoListarService;
    @Autowired
    EstabelecimentoCriarService estabelecimentoCriarService;
    @Autowired
    EstabelecimentoAtualizarService estabelecimentoAtualizarService;
    @Autowired
    EstabelecimentoRemoverService estabelecimentoExcluirService;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUmEntregador(
            @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoListarService.listar(id));
    }

    @GetMapping("")
    public ResponseEntity<?> buscarTodosEstabelecimentoes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoListarService.listar(null));
    }

    @PostMapping()
    public ResponseEntity<?> salvarEstabelecimento(
            @RequestBody @Valid EstabelecimentoPostPutDTO estabelecimento) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(estabelecimentoCriarService.salvar(estabelecimento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarEstabelecimento(
            @PathVariable Long id,
            @RequestBody @Valid EstabelecimentoPostPutDTO putBody) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoAtualizarService.atualizar(id, putBody));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirEstabelecimento(
            @PathVariable Long id,
            @RequestBody @Valid EstabelecimentoDeleteDTO deleteBody
    ) throws Exception {
        estabelecimentoExcluirService.remover(id, deleteBody);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }

}
