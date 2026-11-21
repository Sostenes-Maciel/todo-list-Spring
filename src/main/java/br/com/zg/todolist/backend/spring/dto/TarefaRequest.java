package br.com.zg.todolist.backend.spring.dto;

import br.com.zg.todolist.backend.model.Status;
import br.com.zg.todolist.backend.model.Tarefa;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TarefaRequest(
        String nome,
        String descricao,
        LocalDate dataTermino,
        @NotNull @Min(1) @Max(5) Integer prioridade,
        Long categoriaId,
        Status status,
        LocalDateTime horarioAlarme
) {

    public Tarefa toEntity() {
        return new Tarefa.TarefaBuilder()
                .comNome(nome)
                .comDescricao(descricao)
                .comDataTermino(dataTermino)
                .comPrioridade(prioridade)
                .comStatus(status)
                .comHorarioAlarme(horarioAlarme)
                .build();
    }
}