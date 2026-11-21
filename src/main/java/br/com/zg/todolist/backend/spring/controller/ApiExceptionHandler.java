package br.com.zg.todolist.backend.spring.controller;

import br.com.zg.todolist.backend.spring.dto.ErroResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroResponse> tratarNaoEncontrado(
            EntityNotFoundException excecao
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErroResponse(excecao.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroResponse> tratarDadosInvalidos(
            IllegalArgumentException excecao
    ) {
        return ResponseEntity.badRequest()
                .body(new ErroResponse(excecao.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> tratarValidacao(
            MethodArgumentNotValidException excecao
    ) {

        String mensagem = excecao.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .findFirst()
                .orElse("Dados inválidos.");

        return ResponseEntity.badRequest()
                .body(new ErroResponse(mensagem));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponse> tratarFormatoInvalido(
            HttpMessageNotReadableException excecao
    ) {
        return ResponseEntity.badRequest()
                .body(new ErroResponse(
                        "Formato JSON ou valor informado inválido."
                ));
    }
}