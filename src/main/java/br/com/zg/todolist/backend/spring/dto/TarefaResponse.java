package br.com.zg.todolist.backend.spring.dto;

import br.com.zg.todolist.backend.model.Status;
import br.com.zg.todolist.backend.model.Tarefa;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Representação de uma tarefa")
public record TarefaResponse(

        @Schema(description = "Identificador da tarefa", example = "1")
        Long id,

        @Schema(description = "Versão da entidade", example = "0")
        Long version,

        @Schema(description = "Nome da tarefa", example = "Estudar Spring Boot")
        String nome,

        @Schema(description = "Descrição da tarefa", example = "Implementar API REST")
        String descricao,

        @Schema(description = "Data de término", example = "2026-12-01")
        LocalDate dataTermino,

        @Schema(description = "Prioridade", example = "4")
        int prioridade,

        @Schema(description = "Identificador da categoria", example = "1")
        Long categoriaId,

        @Schema(description = "Status", example = "TODO")
        Status status,

        @Schema(description = "Horário do alarme", example = "2026-12-01T09:00:00")
        LocalDateTime horarioAlarme

) {

    public static TarefaResponse from(Tarefa tarefa) {

        Long categoriaId = tarefa.getCategoria() != null
                ? tarefa.getCategoria().getId()
                : null;

        return new TarefaResponse(
                tarefa.getId(),
                tarefa.getVersion(),
                tarefa.getNome(),
                tarefa.getDescricao(),
                tarefa.getDataTermino(),
                tarefa.getPrioridade(),
                categoriaId,
                tarefa.getStatus(),
                tarefa.getHorarioAlarme()
        );
    }
}