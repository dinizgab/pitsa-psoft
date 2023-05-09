package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.dto.SaborDTO;
import com.ufcg.psoft.mercadofacil.service.sabor.*;
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
    private SaborCreateService saborCreateService;

    @Autowired
    private SaborFindByIdService saborFindByIdService;

    @Autowired
    private SaborFindByName saborFindByName;

    @Autowired
    private SaborUpDateService saborUpdateService;

    @Autowired
    private SaborRemoverService saborRemoverService;

    @Autowired
    private SaborFindAllService saborFindAllService;

    @PostMapping()
    public ResponseEntity<SaborDTO> create(@RequestBody @Valid SaborDTO saborDTO){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saborCreateService.create(saborDTO));

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(saborFindByIdService.findById(id));
    }

    @GetMapping("/{name}")
    public ResponseEntity<SaborDTO> getSaborByName(@PathVariable String name) {
        SaborDTO saborDTO = saborFindByName.findByName(name);
        return ResponseEntity.ok().body(saborDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaborDTO> update
            (@PathVariable Long id, @RequestBody SaborDTO saborDTO) {
        SaborDTO updatedSabor = saborUpdateService.update(saborDTO);
        return ResponseEntity.ok(updatedSabor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        saborRemoverService.remover(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<SaborDTO>> findAll() {
        List<SaborDTO> saboresDTO = saborFindAllService.findAll();
        return ResponseEntity.ok(saboresDTO);
    }
}
