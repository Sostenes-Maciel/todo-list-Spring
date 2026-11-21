package br.com.zg.todolist.backend.repository;

import br.com.zg.todolist.backend.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}