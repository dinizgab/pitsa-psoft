package com.ufcg.psoft.pitsA.controller;

import com.ufcg.psoft.pitsA.dto.pedido.ClienteRemoverPedidoDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoPostDTO;
import com.ufcg.psoft.pitsA.dto.pedido.PedidoReadBodyDTO;
import com.ufcg.psoft.pitsA.exception.ErrorMessage;
import com.ufcg.psoft.pitsA.exception.auth.CodigoAcessoInvalidoException;
import com.ufcg.psoft.pitsA.service.cliente.ClienteCriarPedidoService;
import com.ufcg.psoft.pitsA.service.cliente.ClienteListarPedidoService;
import com.ufcg.psoft.pitsA.service.cliente.ClienteRemoverPedidoService;
import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoListarPedidoService;
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
    ClienteRemoverPedidoService clienteRemoverPedidoService;
    @Autowired
    EstabelecimentoListarPedidoService estabelecimentoListarPedidoService;
    @Autowired
    ClienteCriarPedidoService clienteCriarPedidoService;

    @GetMapping("/cliente/{id}")
    public ResponseEntity<?> listarPedidosCliente (
            @PathVariable("id") Long id,
            @RequestBody PedidoReadBodyDTO readBody
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteListarPedidoService.listarPedidos(id, readBody));
    }

    @PostMapping("/cliente/{id}")
    public ResponseEntity<?> criarNovoPedido (
            @PathVariable("id") Long id,
            @RequestBody PedidoPostDTO postBody
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(clienteCriarPedidoService.criarPedido(id, postBody));
    }

    @DeleteMapping("/cliente/{id}")
    public ResponseEntity<?> removerUmPedido (
            @PathVariable("id") Long id,
            @RequestBody ClienteRemoverPedidoDTO removeBody
    ) {
        clienteRemoverPedidoService.removerPedido(id, removeBody);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }

    @GetMapping("/estabelecimento/{id}")
    public ResponseEntity<?> listarPedidosEstabelecimento (
            @PathVariable("id") Long id,
            @RequestBody PedidoReadBodyDTO readBody
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoListarPedidoService.listarPedidos(id, readBody));
    }

    @ExceptionHandler(CodigoAcessoInvalidoException.class)
    public ResponseEntity<?> codigoAcessoInvalido(CodigoAcessoInvalidoException err) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(err));
    }
}
