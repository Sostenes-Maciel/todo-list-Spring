package br.com.zg.todolist.backend.spring.dto;

import br.com.zg.todolist.backend.model.Status;
import br.com.zg.todolist.backend.model.Tarefa;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Dados para criação ou atualização de uma tarefa")
public record TarefaRequest(

        @Schema(description = "Nome da tarefa", example = "Estudar Spring Boot")
        String nome,

        @Schema(description = "Descrição da tarefa", example = "Implementar API REST")
        String descricao,

        @Schema(description = "Data de término", example = "2026-12-01")
        LocalDate dataTermino,

        @NotNull
        @Min(1)
        @Max(5)
        @Schema(description = "Prioridade da tarefa, de 1 a 5", example = "4")
        Integer prioridade,

        @Schema(description = "Identificador da categoria", example = "1", nullable = true)
        Long categoriaId,

        @Schema(description = "Status da tarefa", example = "TODO",
                allowableValues = {"TODO", "DOING", "DONE"})
        Status status,

        @Schema(description = "Horário do alarme", example = "2026-12-01T09:00:00")
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