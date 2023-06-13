package com.ufcg.psoft.pitsA.controller;

import com.ufcg.psoft.pitsA.dto.pedido.*;
import com.ufcg.psoft.pitsA.exception.ErrorMessage;
import com.ufcg.psoft.pitsA.exception.auth.CodigoAcessoInvalidoException;
import com.ufcg.psoft.pitsA.service.cliente.*;
import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoListarPedidoService;
import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoPatchPedidoEntregadorService;
import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoPatchPedidoEstadoService;
import com.ufcg.psoft.pitsA.service.pedido.ConfirmarPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/v1/pedidos",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class PedidoV1Controller {
    @Autowired
    ClienteListarPedidoService clienteListarPedidoService;
    @Autowired
    ClienteConfirmarEntregaPedidoService clienteConfirmarEntregaPedidoService;
    @Autowired
    ClienteAtualizarPedidoService clienteAtualizarPedidoService;
    @Autowired
    ClienteRemoverPedidoService clienteRemoverPedidoService;
    @Autowired
    ConfirmarPagamentoService confirmarPagamentoService;
    @Autowired
    ClienteCriarPedidoService clienteCriarPedidoService;
    @Autowired
    EstabelecimentoListarPedidoService estabelecimentoListarPedidoService;
    @Autowired
    EstabelecimentoPatchPedidoEntregadorService estabelecimentoPatchPedidoEntregadorService;
    @Autowired
    EstabelecimentoPatchPedidoEstadoService estabelecimentoPatchPedidoEstadoService;

    @PatchMapping("/{id}")
    public ResponseEntity<?> confirmarPagamento(
            @PathVariable Long id,
            @RequestBody ConfirmarPagamentoDTO confirmarPagamentoDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(confirmarPagamentoService.confirmarPagamento(id, confirmarPagamentoDTO));
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<?> listarPedidosCliente(
            @PathVariable("id") Long id,
            @RequestBody PedidoReadBodyDTO readBody
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteListarPedidoService.listarPedidos(id, readBody));
    }

    @PostMapping("/cliente/{id}")
    public ResponseEntity<?> criarNovoPedido(
            @PathVariable("id") Long id,
            @RequestBody PedidoPostDTO postBody
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(clienteCriarPedidoService.criarPedido(id, postBody));
    }

    @PutMapping("/cliente/{id}")
    public ResponseEntity<?> atualizarPedido(
            @PathVariable("id") Long id,
            @RequestBody PedidoPutDTO putBody
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteAtualizarPedidoService.atualizarPedido(id, putBody));
    }

    @PatchMapping("/cliente/{id}")
    public ResponseEntity<?> confirmarEntregaPedido(
            @PathVariable("id") Long id,
            @RequestBody PedidoConfirmaEntregaDTO patchBody
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteConfirmarEntregaPedidoService.confirmarEntrega(id, patchBody));
    }

    @DeleteMapping("/cliente/{id}")
    public ResponseEntity<?> cancelarUmPedido(
            @PathVariable("id") Long id,
            @RequestBody ClienteRemoverPedidoDTO removeBody
    ) {
        clienteRemoverPedidoService.removerPedido(id, removeBody);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }

    @GetMapping("/estabelecimento/{id}")
    public ResponseEntity<?> listarPedidosEstabelecimento(
            @PathVariable("id") Long id,
            @RequestBody PedidoReadBodyDTO readBody
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoListarPedidoService.listarPedidos(id, readBody));
    }

    @PatchMapping("/estabelecimento/{id}")
    public ResponseEntity<?> alterarEstadoPedido(
            @PathVariable("id") Long id,
            @RequestBody PedidoReadBodyDTO readBodyDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoPatchPedidoEstadoService.alterarEstadoPedido(id, readBodyDTO));
    }

    @PatchMapping("/estabelecimento/entregador/{id}")
    public ResponseEntity<?> alterarEntregadorPedido(
            @PathVariable("id") Long id,
            @RequestBody PedidoPatchEntregadorDTO patchEntregadorBody
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoPatchPedidoEntregadorService.alterarEntregador(id, patchEntregadorBody));
    }

    @ExceptionHandler(CodigoAcessoInvalidoException.class)
    public ResponseEntity<?> codigoAcessoInvalido(CodigoAcessoInvalidoException err) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(err));
    }
}
