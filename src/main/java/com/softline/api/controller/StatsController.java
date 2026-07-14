package com.softline.api.controller;

import com.softline.api.repository.ClienteRepository;
import com.softline.api.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {

    private final ProdutoService produtoService;
    private final ClienteRepository clienteRepository;

    @GetMapping
    public ResponseEntity<?> stats() {
        return ResponseEntity.ok(Map.of(
                "produtos", produtoService.somarValorVenda(),
                "clientes", clienteRepository.count(),
                "valor_total", produtoService.somarValorVenda()
        ));
    }
}
