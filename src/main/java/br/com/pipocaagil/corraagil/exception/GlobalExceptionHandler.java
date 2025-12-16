package br.com.pipocaagil.corraagil.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Manipulador global de exceções para tratar validações, erros de recurso não encontrado,
 * e violações de integridade de dados (como duplicidade de e-mail).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 1. Trata erros de validação (@Valid no DTO)
     * Retorna: 400 BAD REQUEST
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * 2. Trata erros de recurso não encontrado (Lançado pelo Service)
     * Retorna: 404 NOT FOUND
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * 3. Trata violações de integridade de dados (Ex: E-mail duplicado devido ao @Column(unique = true))
     * Retorna: 409 CONFLICT
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        // Tenta capturar a causa mais específica (pode variar por banco de dados)
        String mensagemCausa = ex.getMostSpecificCause().getMessage();
        String erroFormatado = "Violação de integridade de dados.";

        // Tentativa de ser mais específico sobre o e-mail duplicado
        // Os termos "unique constraint" ou "Duplicate entry" são comuns em bancos SQL
        if (mensagemCausa != null && (mensagemCausa.contains("unique constraint") || mensagemCausa.contains("Duplicate entry") || mensagemCausa.contains("Detail: Key"))) {
            erroFormatado = " O e-mail informado já está cadastrado.";
            return new ResponseEntity<>(erroFormatado, HttpStatus.CONFLICT); // 409 Conflict
        }

        // Para outras violações (ex: chave estrangeira), retorna um erro mais genérico
        return new ResponseEntity<>("Erro de integridade de dados: " + erroFormatado, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 4. Trata todas as outras exceções não capturadas
     * Retorna: 500 INTERNAL SERVER ERROR
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        // Idealmente, você logaria o 'ex' aqui para depuração.
        return new ResponseEntity<>("Ocorreu um erro interno no servidor: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}