package br.com.zg.todolist.backend.spring;

import br.com.zg.todolist.backend.model.Status;
import br.com.zg.todolist.backend.model.Tarefa;
import br.com.zg.todolist.backend.spring.controller.TarefaRestController;
import br.com.zg.todolist.backend.spring.service.TarefaService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TarefaRestController.class)
class TarefaRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TarefaService tarefaService;

    @Test
    void deveListarTarefas() throws Exception {

        Tarefa tarefa = criarTarefaMockada(
                1L, "Estudar Spring", Status.TODO
        );

        given(tarefaService.listarTodas()).willReturn(List.of(tarefa));

        mockMvc.perform(get("/tarefas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Estudar Spring"))
                .andExpect(jsonPath("$[0].status").value("TODO"));
    }

    @Test
    void deveRetornarListaVazia() throws Exception {

        given(tarefaService.listarTodas()).willReturn(List.of());

        mockMvc.perform(get("/tarefas"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void deveBuscarTarefaPorId() throws Exception {

        Tarefa tarefa = criarTarefaMockada(
                10L, "Estudar JPA", Status.DOING
        );

        given(tarefaService.buscarPorId(10L)).willReturn(tarefa);

        mockMvc.perform(get("/tarefas/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.nome").value("Estudar JPA"))
                .andExpect(jsonPath("$.status").value("DOING"));
    }

    @Test
    void deveCriarTarefa() throws Exception {

        Tarefa tarefa = criarTarefaMockada(
                15L, "Nova tarefa", Status.TODO
        );

        given(tarefaService.criar(any(Tarefa.class), isNull()))
                .willReturn(tarefa);

        String json = """
                {
                    "nome": "Nova tarefa",
                    "descricao": "Descricao de teste",
                    "dataTermino": "2026-11-25",
                    "prioridade": 4,
                    "categoriaId": null,
                    "status": "TODO",
                    "horarioAlarme": null
                }
                """;

        mockMvc.perform(post("/tarefas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(15))
                .andExpect(jsonPath("$.nome").value("Nova tarefa"));

        verify(tarefaService).criar(any(Tarefa.class), isNull());
    }

    @Test
    void deveAtualizarTarefa() throws Exception {

        Tarefa tarefa = criarTarefaMockada(
                10L, "Tarefa atualizada", Status.DONE
        );

        given(tarefaService.atualizar(
                eq(10L),
                any(Tarefa.class),
                isNull()
        )).willReturn(tarefa);

        String json = """
                {
                    "nome": "Tarefa atualizada",
                    "descricao": "Descricao atualizada",
                    "dataTermino": "2026-11-26",
                    "prioridade": 3,
                    "categoriaId": null,
                    "status": "DONE",
                    "horarioAlarme": null
                }
                """;

        mockMvc.perform(put("/tarefas/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Tarefa atualizada"))
                .andExpect(jsonPath("$.status").value("DONE"));
    }

    @Test
    void deveExcluirTarefa() throws Exception {

        mockMvc.perform(delete("/tarefas/10"))
                .andExpect(status().isNoContent());

        verify(tarefaService).excluir(10L);
    }

    @Test
    void deveRetornar404QuandoTarefaNaoExiste() throws Exception {

        given(tarefaService.buscarPorId(999L))
                .willThrow(new jakarta.persistence.EntityNotFoundException(
                        "Tarefa não encontrada: 999"
                ));

        mockMvc.perform(get("/tarefas/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem")
                        .value("Tarefa não encontrada: 999"));
    }

    @Test
    void deveRejeitarPrioridadeInvalida() throws Exception {

        String json = """
                {
                    "nome": "Tarefa inválida",
                    "descricao": "Teste de validação",
                    "prioridade": 6,
                    "status": "TODO"
                }
                """;

        mockMvc.perform(post("/tarefas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(tarefaService);
    }

    private Tarefa criarTarefaMockada(
            Long id,
            String nome,
            Status status
    ) {

        Tarefa tarefa = org.mockito.Mockito.mock(Tarefa.class);

        given(tarefa.getId()).willReturn(id);
        given(tarefa.getVersion()).willReturn(0L);
        given(tarefa.getNome()).willReturn(nome);
        given(tarefa.getDescricao()).willReturn("Descricao de teste");
        given(tarefa.getDataTermino())
                .willReturn(LocalDate.of(2026, 11, 25));
        given(tarefa.getPrioridade()).willReturn(4);
        given(tarefa.getCategoria()).willReturn(null);
        given(tarefa.getStatus()).willReturn(status);
        given(tarefa.getHorarioAlarme()).willReturn(null);

        return tarefa;
    }
    @Test
    void deveRetornar404AoAtualizarTarefaInexistente() throws Exception {

        given(tarefaService.atualizar(
                eq(999L),
                any(Tarefa.class),
                isNull()
        )).willThrow(new jakarta.persistence.EntityNotFoundException(
                "Tarefa não encontrada: 999"
        ));

        String json = """
            {
                "nome": "Tarefa inexistente",
                "descricao": "Teste de atualizacao",
                "prioridade": 3,
                "status": "TODO"
            }
            """;

        mockMvc.perform(put("/tarefas/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem")
                        .value("Tarefa não encontrada: 999"));
    }
    @Test
    void deveRetornar404AoExcluirTarefaInexistente() throws Exception {

        org.mockito.Mockito.doThrow(
                new jakarta.persistence.EntityNotFoundException(
                        "Tarefa não encontrada: 999"
                )
        ).when(tarefaService).excluir(999L);

        mockMvc.perform(delete("/tarefas/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem")
                        .value("Tarefa não encontrada: 999"));

        verify(tarefaService).excluir(999L);
    }
    @Test
    void deveRetornar400QuandoJsonForInvalido() throws Exception {

        String json = """
            {
                "nome": "Tarefa inválida",
                "prioridade": 3,
                "status": "TODO"
            """;

        mockMvc.perform(post("/tarefas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem")
                        .value("Formato JSON ou valor informado inválido."));

        verifyNoInteractions(tarefaService);
    }
    @Test
    void deveRetornar400QuandoStatusForInvalido() throws Exception {

        String json = """
            {
                "nome": "Tarefa de teste",
                "descricao": "Validar status",
                "prioridade": 3,
                "status": "STATUS_INVALIDO"
            }
            """;

        mockMvc.perform(post("/tarefas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem")
                        .value("Formato JSON ou valor informado inválido."));

        verifyNoInteractions(tarefaService);
    }
    @Test
    void deveRetornar404QuandoCategoriaNaoExistirAoCriarTarefa()
            throws Exception {

        given(tarefaService.criar(any(Tarefa.class), eq(999L)))
                .willThrow(new jakarta.persistence.EntityNotFoundException(
                        "Categoria não encontrada: 999"
                ));

        String json = """
            {
                "nome": "Nova tarefa",
                "descricao": "Teste de categoria",
                "prioridade": 3,
                "categoriaId": 999,
                "status": "TODO"
            }
            """;

        mockMvc.perform(post("/tarefas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem")
                        .value("Categoria não encontrada: 999"));
    }
}