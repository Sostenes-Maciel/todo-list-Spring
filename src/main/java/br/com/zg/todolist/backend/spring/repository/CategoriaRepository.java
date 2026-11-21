package br.com.zg.todolist.backend.spring.repository;

import br.com.zg.todolist.backend.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}