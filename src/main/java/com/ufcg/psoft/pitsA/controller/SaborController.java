package com.ufcg.psoft.pitsA.controller;

import com.ufcg.psoft.pitsA.dto.sabor.SaborReadDTO;
import com.ufcg.psoft.pitsA.dto.sabor.SaborDeleteDTO;
import com.ufcg.psoft.pitsA.dto.sabor.SaborPostDTO;
import com.ufcg.psoft.pitsA.dto.sabor.SaborPutDTO;
import com.ufcg.psoft.pitsA.service.sabor.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sabores")
public class SaborController {

    @Autowired
    SaborCreateService saborCreateService;

    @Autowired
    SaborListarService saborListarService;

    @Autowired
    SaborUpdateService saborUpdateService;

    @Autowired
    SaborRemoverService saborRemoverService;

    @PostMapping("/{id}")
    public ResponseEntity<SaborReadDTO> create(
            @PathVariable Long estabelecimentoId,
            @RequestBody @Valid SaborPostDTO saborDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saborCreateService.create(estabelecimentoId, saborDTO));

    }

    @GetMapping
    public ResponseEntity<List<SaborReadDTO>> findAll() {
        List<SaborReadDTO> saboresDTO = saborListarService.listar(null);
        return ResponseEntity.ok(saboresDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(saborListarService.listar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaborReadDTO> update
            (@PathVariable Long id, @RequestBody SaborPutDTO saborDTO) {
        SaborReadDTO updatedSabor = saborUpdateService.update(id, saborDTO);
        return ResponseEntity.ok(updatedSabor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(
            @PathVariable Long id,
            @RequestBody@Valid SaborDeleteDTO saborDTO
    ) {
        saborRemoverService.remover(id, saborDTO);
        return ResponseEntity.noContent().build();
    }
}
