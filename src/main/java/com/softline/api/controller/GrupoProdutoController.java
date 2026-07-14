package com.softline.api.controller;

import com.softline.api.repository.GrupoProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grupos-produto")
@RequiredArgsConstructor
public class GrupoProdutoController {

    private final GrupoProdutoRepository repository;

    @GetMapping
    public ResponseEntity<?> index() {
        return ResponseEntity.ok(repository.findAll(Sort.by("descricao")));
    }
}
