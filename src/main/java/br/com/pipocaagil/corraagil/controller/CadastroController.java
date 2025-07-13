package br.com.pipocaagil.corraagil.controller;

import br.com.pipocaagil.corraagil.model.CadastroModel;
import br.com.pipocaagil.corraagil.exception.CadastroNotFoundException;
import br.com.pipocaagil.corraagil.service.CadastroService;
import br.com.pipocaagil.corraagil.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gerenciar operações de cadastro.
 */
@RestController
@RequestMapping("/cadastro")
public class CadastroController {

    @Autowired
    private CadastroService cadastroService;

    @Autowired
    private EmailService emailService;

    /**
     * Retorna uma lista de todos os cadastros.
     *
     * @return lista de CadastroModel
     */
    @GetMapping("/todos")
    public List<CadastroModel> getAllCadastroModel() {
        return cadastroService.listarTodos();
    }

    /**
     * Busca um cadastro pelo ID.
     *
     * @param id ID do cadastro
     * @return ResponseEntity com o CadastroModel encontrado ou status 404 se não encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<CadastroModel> buscar(@PathVariable Long id) {
        return cadastroService.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria um novo cadastro.
     *
     * @param cadastroModel dados do novo cadastro
     * @return ResponseEntity com mensagem de sucesso ou conflito se o email já estiver cadastrado
     */
    @PostMapping
    public ResponseEntity<String> createCadastroModel(@Valid @RequestBody CadastroModel cadastroModel) {
        if (cadastroService.emailJaCadastrado(cadastroModel.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já cadastrado com este email.");
        }

        CadastroModel savedCadastro = cadastroService.salvar(cadastroModel);

        // Conteúdo HTML do e-mail (sem botão)
        String htmlContent = """
    <div style="font-family: Arial, sans-serif; border: 1px solid #ccc; padding: 0; max-width: 600px; margin: auto;">
        <div style="text-align: center;">
            <img src='cid:logoCorraAgil' alt='CorraÁGIL' style='width: 100%; max-height: 300px; object-fit: cover;' />
        </div>
        <div style="padding: 20px;">
            <p>Olá,</p>
            <p>Você efetuou o cadastro do seu e-mail em nosso app, estamos fazendo a verificação e validação.</p>
            <p style="margin-top: 40px;">Equipe, <strong>CorraÁGIL</strong>.</p>
        </div>
    </div>
""";

        emailService.sendConfirmationEmail(
                cadastroModel.getEmail(),
                "Confirmação de Cadastro",
                htmlContent
        );

        return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro realizado com sucesso!");
    }


    /**
     * Atualiza um cadastro existente.
     *
     * @param id ID do cadastro a ser atualizado
     * @param cadastroModel dados atualizados do cadastro
     * @return ResponseEntity com o CadastroModel atualizado ou status 404 se não encontrado
     */
    @PutMapping("/{id}")
    public ResponseEntity<CadastroModel> atualizar(@PathVariable Long id, @Valid @RequestBody CadastroModel cadastroModel) {
        try {
            CadastroModel updatedCadastro = cadastroService.atualizar(id, cadastroModel);
            return ResponseEntity.ok(updatedCadastro);
        } catch (CadastroNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deleta um cadastro pelo ID.
     *
     * @param id ID do cadastro a ser deletado
     * @return ResponseEntity com status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCadastroModel(@PathVariable Long id) {
        cadastroService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Realiza login de um usuário.
     *
     * @param cadastroModel dados do usuário para login
     * @return ResponseEntity com mensagem de sucesso ou falha no login
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody CadastroModel cadastroModel) {
        CadastroModel usuarioAutenticado = cadastroService.autenticar(cadastroModel.getEmail(), cadastroModel.getSenha());
        if (usuarioAutenticado != null) {
            return ResponseEntity.ok("Login bem-sucedido!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha no login. Verifique suas credenciais.");
        }
    }

    /**
     * Atualiza a senha de um cadastro.
     *
     * @param id ID do cadastro
     * @param novaSenha nova senha
     * @return ResponseEntity com status 200 (OK) ou 404 se não encontrado
     */
    @PutMapping("/{id}/reset")
    public ResponseEntity<Void> atualizarSenha(@PathVariable Long id, @RequestBody String novaSenha) {
        try {
            cadastroService.atualizarSenha(id, novaSenha);
            return ResponseEntity.ok().build();
        } catch (CadastroNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}