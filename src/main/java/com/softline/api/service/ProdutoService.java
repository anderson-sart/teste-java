package com.softline.api.service;

import com.softline.api.dto.PageResponse;
import com.softline.api.dto.ProdutoRequest;
import com.softline.api.model.Produto;
import com.softline.api.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;

    public PageResponse<Produto> listar(String search, int page, int perPage, String sortBy, String sortDir) {
        var sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        var pageable = PageRequest.of(page - 1, perPage, sort);
        Page<Produto> result = (search == null || search.isBlank())
                ? repository.findAll(pageable)
                : repository.search(search, pageable);
        return toPageResponse(result, page, perPage);
    }

    public Produto buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
    }

    public Produto criar(ProdutoRequest req) {
        var produto = new Produto();
        mapear(produto, req);
        return repository.save(produto);
    }

    public Produto atualizar(Long id, ProdutoRequest req) {
        var produto = buscar(id);
        mapear(produto, req);
        return repository.save(produto);
    }

    public void deletar(Long id) {
        var produto = buscar(id);
        produto.setDeletedAt(LocalDateTime.now());
        repository.save(produto);
    }

    public BigDecimal somarValorVenda() {
        return repository.sumValorVenda();
    }

    private void mapear(Produto p, ProdutoRequest req) {
        p.setDescricao(req.descricao());
        p.setCodigoBarras(req.codigoBarras());
        p.setValorVenda(req.valorVenda());
        p.setPesoBruto(req.pesoBruto());
        p.setPesoLiquido(req.pesoLiquido());
    }

    private <T> PageResponse<T> toPageResponse(Page<T> page, int pageNum, int perPage) {
        return new PageResponse<>(
                page.getContent(),
                page.getTotalElements(),
                pageNum,
                perPage,
                page.getTotalPages()
        );
    }
}
