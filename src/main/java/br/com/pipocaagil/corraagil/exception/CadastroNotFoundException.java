package br.com.pipocaagil.corraagil.exception;

/**
 * Exceção lançada quando um cadastro não é encontrado.
 */
public class CadastroNotFoundException extends RuntimeException {
    /**
     * Construtor que aceita uma mensagem de erro.
     *
     * @param message mensagem de erro
     */
    public CadastroNotFoundException(String message) {
        super(message);
    }
}