package com.softline.api.service;

import com.softline.api.dto.ClienteRequest;
import com.softline.api.dto.PageResponse;
import com.softline.api.model.Cliente;
import com.softline.api.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public PageResponse<Cliente> listar(String search, int page, int perPage, String sortBy, String sortDir) {
        var sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        var pageable = PageRequest.of(page - 1, perPage, sort);
        Page<Cliente> result = (search == null || search.isBlank())
                ? repository.findAll(pageable)
                : repository.search(search, pageable);
        return new PageResponse<>(result.getContent(), result.getTotalElements(), page, perPage, result.getTotalPages());
    }

    public Cliente buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
    }

    public Cliente criar(ClienteRequest req) {
        var cliente = new Cliente();
        mapear(cliente, req);
        return repository.save(cliente);
    }

    public Cliente atualizar(Long id, ClienteRequest req) {
        var cliente = buscar(id);
        mapear(cliente, req);
        return repository.save(cliente);
    }

    public void deletar(Long id) {
        var cliente = buscar(id);
        cliente.setDeletedAt(LocalDateTime.now());
        repository.save(cliente);
    }

    private void mapear(Cliente c, ClienteRequest req) {
        c.setNome(req.nome());
        c.setFantasia(req.fantasia());
        c.setDocumento(req.documento());
        c.setEndereco(req.endereco());
    }
}
