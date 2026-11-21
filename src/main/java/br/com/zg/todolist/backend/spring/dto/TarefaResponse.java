package br.com.zg.todolist.backend.spring.dto;

import br.com.zg.todolist.backend.model.Tarefa;
import br.com.zg.todolist.backend.model.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TarefaResponse(
        Long id,
        Long version,
        String nome,
        String descricao,
        LocalDate dataTermino,
        int prioridade,
        Long categoriaId,
        Status status,
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