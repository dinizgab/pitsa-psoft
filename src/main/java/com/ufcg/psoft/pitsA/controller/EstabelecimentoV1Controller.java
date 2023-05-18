package com.ufcg.psoft.pitsA.controller;

import com.ufcg.psoft.pitsA.dto.estabelecimento.EstabelecimentoAprovaEntregadorDTO;
import com.ufcg.psoft.pitsA.dto.estabelecimento.EstabelecimentoDeleteDTO;
import com.ufcg.psoft.pitsA.dto.estabelecimento.EstabelecimentoPostDTO;
import com.ufcg.psoft.pitsA.dto.estabelecimento.EstabelecimentoPutDTO;
import com.ufcg.psoft.pitsA.exception.ErrorMessage;
import com.ufcg.psoft.pitsA.exception.auth.CodigoAcessoInvalidoException;
import com.ufcg.psoft.pitsA.exception.entregador.EntregadorNaoEstaPendenteException;
import com.ufcg.psoft.pitsA.service.estabelecimento.*;
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
    EstabelecimentoAprovaService estabelecimentoAprovaService;
    @Autowired
    EstabelecimentoRemoverService estabelecimentoExcluirService;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUmEntregador(
            @PathVariable Long id
    ) {
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
            @RequestBody @Valid EstabelecimentoPostDTO estabelecimento) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(estabelecimentoCriarService.salvar(estabelecimento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarEstabelecimento(
            @PathVariable Long id,
            @RequestBody @Valid EstabelecimentoPutDTO putBody) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoAtualizarService.atualizar(id, putBody));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirEstabelecimento(
            @PathVariable Long id,
            @RequestBody @Valid EstabelecimentoDeleteDTO deleteBody
    ) {
        estabelecimentoExcluirService.remover(id, deleteBody);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> aprovaEntregador(
            @PathVariable Long id,
            @RequestBody @Valid EstabelecimentoAprovaEntregadorDTO aprovaBody) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoAprovaService.aprova(id, aprovaBody));
    }

    @ExceptionHandler(CodigoAcessoInvalidoException.class)
    public ResponseEntity<?> codigoAcessoInvalido(CodigoAcessoInvalidoException err) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(err));
    }

    @ExceptionHandler(EntregadorNaoEstaPendenteException.class)
    public ResponseEntity<?> entregadorNaoPendente(EntregadorNaoEstaPendenteException err) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(err));
    }
}
