package br.com.zg.todolist.backend.Spring.service;

import br.com.zg.todolist.backend.model.Categoria;
import br.com.zg.todolist.backend.model.Tarefa;
import br.com.zg.todolist.backend.Spring.repository.TarefaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final CategoriaService categoriaService;

    public TarefaService(
            TarefaRepository tarefaRepository,
            CategoriaService categoriaService
    ) {
        this.tarefaRepository = tarefaRepository;
        this.categoriaService = categoriaService;
    }

    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }

    public Tarefa buscarPorId(Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Tarefa não encontrada: " + id
                ));
    }

    @Transactional
    public Tarefa criar(Tarefa tarefa, Long categoriaId) {
        validarTarefa(tarefa);

        tarefa.setCategoria(resolverCategoria(categoriaId));

        return tarefaRepository.save(tarefa);
    }

    @Transactional
    public Tarefa atualizar(Long id, Tarefa dadosAtualizados, Long categoriaId) {
        validarTarefa(dadosAtualizados);

        Tarefa tarefa = buscarPorId(id);

        tarefa.setNome(dadosAtualizados.getNome());
        tarefa.setDescricao(dadosAtualizados.getDescricao());
        tarefa.setDataTermino(dadosAtualizados.getDataTermino());
        tarefa.setPrioridade(dadosAtualizados.getPrioridade());
        tarefa.setStatus(dadosAtualizados.getStatus());
        tarefa.setHorarioAlarme(dadosAtualizados.getHorarioAlarme());
        tarefa.setCategoria(resolverCategoria(categoriaId));

        return tarefaRepository.save(tarefa);
    }

    @Transactional
    public void excluir(Long id) {
        Tarefa tarefa = buscarPorId(id);
        tarefaRepository.delete(tarefa);
    }

    private Categoria resolverCategoria(Long categoriaId) {
        if (categoriaId == null) {
            return null;
        }

        return categoriaService.buscarPorId(categoriaId);
    }

    private void validarTarefa(Tarefa tarefa) {
        if (tarefa == null) {
            throw new IllegalArgumentException(
                    "Os dados da tarefa são obrigatórios."
            );
        }

        if (tarefa.getStatus() == null) {
            throw new IllegalArgumentException(
                    "O status da tarefa é obrigatório."
            );
        }

        tarefa.setPrioridade(tarefa.getPrioridade());
    }
}