package br.com.zg.todolist.backend.spring.controller;

import br.com.zg.todolist.backend.model.Tarefa;
import br.com.zg.todolist.backend.spring.dto.TarefaRequest;
import br.com.zg.todolist.backend.spring.dto.TarefaResponse;
import br.com.zg.todolist.backend.spring.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaRestController {

    private final TarefaService tarefaService;

    public TarefaRestController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping
    public ResponseEntity<List<TarefaResponse>> listarTodas() {

        List<TarefaResponse> tarefas = tarefaService.listarTodas()
                .stream()
                .map(TarefaResponse::from)
                .toList();

        return ResponseEntity.ok(tarefas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaResponse> buscarPorId(
            @PathVariable Long id
    ) {

        Tarefa tarefa = tarefaService.buscarPorId(id);

        return ResponseEntity.ok(TarefaResponse.from(tarefa));
    }

    @PostMapping
    public ResponseEntity<TarefaResponse> criar(
            @Valid @RequestBody TarefaRequest requisicao
    ) {

        Tarefa tarefa = tarefaService.criar(
                requisicao.toEntity(),
                requisicao.categoriaId()
        );

        URI localizacao = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tarefa.getId())
                .toUri();

        return ResponseEntity
                .created(localizacao)
                .body(TarefaResponse.from(tarefa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody TarefaRequest requisicao
    ) {

        Tarefa tarefa = tarefaService.atualizar(
                id,
                requisicao.toEntity(),
                requisicao.categoriaId()
        );

        return ResponseEntity.ok(TarefaResponse.from(tarefa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        tarefaService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}