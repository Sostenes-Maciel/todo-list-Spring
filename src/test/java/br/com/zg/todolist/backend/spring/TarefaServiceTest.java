package br.com.zg.todolist.backend.spring;

import br.com.zg.todolist.backend.model.Categoria;
import br.com.zg.todolist.backend.model.Status;
import br.com.zg.todolist.backend.model.Tarefa;
import br.com.zg.todolist.backend.spring.repository.TarefaRepository;

import br.com.zg.todolist.backend.spring.service.CategoriaService;
import br.com.zg.todolist.backend.spring.service.TarefaService;
import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private TarefaService tarefaService;

    @Test
    void deveListarTodasAsTarefas() {

        Tarefa tarefa = novaTarefa("Estudar Spring", Status.TODO);

        given(tarefaRepository.findAll()).willReturn(List.of(tarefa));

        List<Tarefa> resultado = tarefaService.listarTodas();

        assertEquals(1, resultado.size());
        assertEquals("Estudar Spring", resultado.getFirst().getNome());
    }

    @Test
    void deveBuscarTarefaPorId() {

        Tarefa tarefa = novaTarefa("Estudar JPA", Status.DOING);

        given(tarefaRepository.findById(1L))
                .willReturn(Optional.of(tarefa));

        Tarefa resultado = tarefaService.buscarPorId(1L);

        assertEquals("Estudar JPA", resultado.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoTarefaNaoExiste() {

        given(tarefaRepository.findById(999L))
                .willReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> tarefaService.buscarPorId(999L)
        );
    }

    @Test
    void deveCriarTarefaAssociadaACategoria() {

        Categoria categoria = new Categoria("Estudos");

        Tarefa tarefa = novaTarefa("Estudar Spring Boot", Status.TODO);

        given(categoriaService.buscarPorId(1L))
                .willReturn(categoria);

        given(tarefaRepository.save(any(Tarefa.class)))
                .willAnswer(invocacao -> invocacao.getArgument(0));

        Tarefa resultado = tarefaService.criar(tarefa, 1L);

        assertEquals("Estudar Spring Boot", resultado.getNome());
        assertSame(categoria, resultado.getCategoria());

        verify(tarefaRepository).save(tarefa);
    }

    @Test
    void deveCriarTarefaSemCategoria() {

        Tarefa tarefa = novaTarefa("Tarefa independente", Status.TODO);

        given(tarefaRepository.save(any(Tarefa.class)))
                .willAnswer(invocacao -> invocacao.getArgument(0));

        Tarefa resultado = tarefaService.criar(tarefa, null);

        assertNull(resultado.getCategoria());

        verify(categoriaService, never()).buscarPorId(any());
        verify(tarefaRepository).save(tarefa);
    }

    @Test
    void deveAtualizarTarefaExistente() {

        Tarefa existente = novaTarefa("Nome antigo", Status.TODO);
        Tarefa atualizada = novaTarefa("Nome novo", Status.DONE);

        Categoria categoria = new Categoria("Trabalho");

        given(tarefaRepository.findById(1L))
                .willReturn(Optional.of(existente));

        given(categoriaService.buscarPorId(2L))
                .willReturn(categoria);

        given(tarefaRepository.save(any(Tarefa.class)))
                .willAnswer(invocacao -> invocacao.getArgument(0));

        Tarefa resultado = tarefaService.atualizar(
                1L,
                atualizada,
                2L
        );

        assertEquals("Nome novo", resultado.getNome());
        assertEquals(Status.DONE, resultado.getStatus());
        assertSame(categoria, resultado.getCategoria());

        verify(tarefaRepository).save(existente);
    }

    @Test
    void deveExcluirTarefaExistente() {

        Tarefa tarefa = novaTarefa("Excluir tarefa", Status.TODO);

        given(tarefaRepository.findById(1L))
                .willReturn(Optional.of(tarefa));

        tarefaService.excluir(1L);

        verify(tarefaRepository).delete(tarefa);
    }

    private Tarefa novaTarefa(String nome, Status status) {

        return new Tarefa.TarefaBuilder()
                .comNome(nome)
                .comDescricao("Descricao de teste")
                .comPrioridade(3)
                .comStatus(status)
                .build();
    }
    @Test
    void deveLancarExcecaoQuandoCategoriaNaoExistirAoCriarTarefa() {

        Tarefa tarefa = novaTarefa("Estudar Spring", Status.TODO);

        given(categoriaService.buscarPorId(99L))
                .willThrow(new EntityNotFoundException(
                        "Categoria não encontrada: 99"
                ));

        assertThrows(
                EntityNotFoundException.class,
                () -> tarefaService.criar(tarefa, 99L)
        );

        verify(tarefaRepository, never()).save(any(Tarefa.class));
    }
    @Test
    void deveLancarExcecaoAoAtualizarTarefaInexistente() {

        Tarefa dadosAtualizados = novaTarefa(
                "Tarefa atualizada",
                Status.DOING
        );

        given(tarefaRepository.findById(99L))
                .willReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> tarefaService.atualizar(
                        99L,
                        dadosAtualizados,
                        null
                )
        );

        verify(tarefaRepository, never()).save(any(Tarefa.class));
    }
    @Test
    void deveRejeitarPrioridadeInvalidaAoCriarTarefa() {

        Tarefa tarefa = mock(Tarefa.class);

        given(tarefa.getStatus()).willReturn(Status.TODO);
        given(tarefa.getPrioridade()).willReturn(6);

        doCallRealMethod().when(tarefa).setPrioridade(6);

        IllegalArgumentException excecao = assertThrows(
                IllegalArgumentException.class,
                () -> tarefaService.criar(tarefa, null)
        );

        assertEquals(
                "Erro: O número da prioridade deve ser entre 1 e 5",
                excecao.getMessage()
        );

        verify(tarefaRepository, never()).save(any(Tarefa.class));
    }
    @Test
    void deveRejeitarTarefaSemStatus() {

        Tarefa tarefa = mock(Tarefa.class);

        given(tarefa.getStatus()).willReturn(null);

        IllegalArgumentException excecao = assertThrows(
                IllegalArgumentException.class,
                () -> tarefaService.criar(tarefa, null)
        );

        assertEquals(
                "O status da tarefa é obrigatório.",
                excecao.getMessage()
        );

        verify(tarefaRepository, never()).save(any(Tarefa.class));
    }
    @Test
    void deveRejeitarDadosNulosAoCriarTarefa() {

        assertThrows(
                IllegalArgumentException.class,
                () -> tarefaService.criar(null, null)
        );

        verifyNoInteractions(tarefaRepository, categoriaService);
    }
}