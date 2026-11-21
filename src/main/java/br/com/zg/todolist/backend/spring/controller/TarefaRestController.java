package br.com.zg.todolist.backend.spring.controller;

import br.com.zg.todolist.backend.model.Tarefa;
import br.com.zg.todolist.backend.spring.dto.ErroResponse;
import br.com.zg.todolist.backend.spring.dto.TarefaRequest;
import br.com.zg.todolist.backend.spring.dto.TarefaResponse;
import br.com.zg.todolist.backend.spring.service.TarefaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@Tag(
        name = "Tarefas",
        description = "Operações de gerenciamento de tarefas"
)
public class TarefaRestController {

    private final TarefaService tarefaService;

    public TarefaRestController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping
    @Operation(
            summary = "Listar tarefas",
            description = "Retorna todas as tarefas cadastradas."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de tarefas retornada com sucesso.",
            content = @Content(
                    array = @ArraySchema(
                            schema = @Schema(implementation = TarefaResponse.class)
                    )
            )
    )
    public ResponseEntity<List<TarefaResponse>> listarTodas() {

        List<TarefaResponse> tarefas = tarefaService.listarTodas()
                .stream()
                .map(TarefaResponse::from)
                .toList();

        return ResponseEntity.ok(tarefas);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar tarefa por ID",
            description = "Localiza uma tarefa pelo seu identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Tarefa encontrada.",
                    content = @Content(
                            schema = @Schema(implementation = TarefaResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tarefa não encontrada.",
                    content = @Content(
                            schema = @Schema(implementation = ErroResponse.class)
                    )
            )
    })
    public ResponseEntity<TarefaResponse> buscarPorId(
            @Parameter(description = "Identificador da tarefa", example = "1")
            @PathVariable Long id
    ) {

        Tarefa tarefa = tarefaService.buscarPorId(id);

        return ResponseEntity.ok(TarefaResponse.from(tarefa));
    }

    @PostMapping
    @Operation(
            summary = "Criar tarefa",
            description = "Cadastra uma nova tarefa no sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Tarefa criada com sucesso.",
                    content = @Content(
                            schema = @Schema(implementation = TarefaResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados informados inválidos.",
                    content = @Content(
                            schema = @Schema(implementation = ErroResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Categoria informada não encontrada.",
                    content = @Content(
                            schema = @Schema(implementation = ErroResponse.class)
                    )
            )
    })
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
    @Operation(
            summary = "Atualizar tarefa",
            description = "Atualiza os dados de uma tarefa existente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Tarefa atualizada com sucesso.",
                    content = @Content(
                            schema = @Schema(implementation = TarefaResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados informados inválidos.",
                    content = @Content(
                            schema = @Schema(implementation = ErroResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tarefa ou categoria não encontrada.",
                    content = @Content(
                            schema = @Schema(implementation = ErroResponse.class)
                    )
            )
    })
    public ResponseEntity<TarefaResponse> atualizar(
            @Parameter(description = "Identificador da tarefa", example = "1")
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
    @Operation(
            summary = "Excluir tarefa",
            description = "Remove uma tarefa pelo seu identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Tarefa excluída com sucesso."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tarefa não encontrada.",
                    content = @Content(
                            schema = @Schema(implementation = ErroResponse.class)
                    )
            )
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "Identificador da tarefa", example = "1")
            @PathVariable Long id
    ) {

        tarefaService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}