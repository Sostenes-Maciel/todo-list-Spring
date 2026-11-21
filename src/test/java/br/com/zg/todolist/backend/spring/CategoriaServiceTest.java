package br.com.zg.todolist.backend.spring;

import br.com.zg.todolist.backend.model.Categoria;
import br.com.zg.todolist.backend.spring.repository.CategoriaRepository;

import br.com.zg.todolist.backend.spring.service.CategoriaService;
import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @Test
    void deveListarTodasAsCategorias() {

        Categoria estudos = new Categoria("Estudos");
        Categoria trabalho = new Categoria("Trabalho");

        given(categoriaRepository.findAll())
                .willReturn(List.of(estudos, trabalho));

        List<Categoria> resultado = categoriaService.listarTodas();

        assertEquals(2, resultado.size());
        assertEquals("Estudos", resultado.get(0).getNome());
        assertEquals("Trabalho", resultado.get(1).getNome());

        verify(categoriaRepository).findAll();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremCategorias() {

        given(categoriaRepository.findAll())
                .willReturn(List.of());

        List<Categoria> resultado = categoriaService.listarTodas();

        assertTrue(resultado.isEmpty());

        verify(categoriaRepository).findAll();
    }

    @Test
    void deveBuscarCategoriaPorId() {

        Categoria categoria = new Categoria("Estudos");

        given(categoriaRepository.findById(1L))
                .willReturn(Optional.of(categoria));

        Categoria resultado = categoriaService.buscarPorId(1L);

        assertNotNull(resultado);
        assertSame(categoria, resultado);
        assertEquals("Estudos", resultado.getNome());

        verify(categoriaRepository).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoCategoriaNaoExistir() {

        given(categoriaRepository.findById(99L))
                .willReturn(Optional.empty());

        EntityNotFoundException excecao = assertThrows(
                EntityNotFoundException.class,
                () -> categoriaService.buscarPorId(99L)
        );

        assertEquals(
                "Categoria não encontrada: 99",
                excecao.getMessage()
        );

        verify(categoriaRepository).findById(99L);
    }
}