package br.com.pipocaagil.corraagil.Cadastro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço para gerenciar operações de cadastro.
 */
@Service
public class CadastroService {

    @Autowired
    private CadastroRepository cadastroRepository;

    /**
     * Lista todos os cadastros.
     *
     * @return lista de CadastroModel
     */
    public List<CadastroModel> listarTodos() {
        return cadastroRepository.findAll();
    }

    /**
     * Busca um cadastro pelo ID.
     *
     * @param id ID do cadastro
     * @return Optional contendo o CadastroModel, se encontrado
     */
    public Optional<CadastroModel> buscar(Long id) {
        return cadastroRepository.findById(id);
    }

    /**
     * Salva um novo cadastro.
     *
     * @param cadastroModel dados do novo cadastro
     * @return CadastroModel salvo
     */
    public CadastroModel salvar(CadastroModel cadastroModel) {
        return cadastroRepository.save(cadastroModel);
    }

    /**
     * Atualiza um cadastro existente.
     *
     * @param id ID do cadastro a ser atualizado
     * @param cadastroModel dados atualizados do cadastro
     * @return CadastroModel atualizado
     * @throws CadastroNotFoundException se o cadastro não for encontrado
     */
    public CadastroModel atualizar(Long id, CadastroModel cadastroModel) {
        return cadastroRepository.findById(id).map(existingCadastro -> {
            existingCadastro.setNomeCompleto(cadastroModel.getNomeCompleto());
            existingCadastro.setEmail(cadastroModel.getEmail());
            existingCadastro.setSenha(cadastroModel.getSenha());
            return cadastroRepository.save(existingCadastro);
        }).orElseThrow(() -> new CadastroNotFoundException("Cadastro não encontrado!"));
    }

    /**
     * Deleta um cadastro pelo ID.
     *
     * @param id ID do cadastro a ser deletado
     */
    public void deletar(Long id) {
        cadastroRepository.deleteById(id);
    }

    /**
     * Autentica um usuário pelo email e senha.
     *
     * @param email Email do usuário
     * @param senha Senha do usuário
     * @return CadastroModel autenticado ou null se falhar
     */
    public CadastroModel autenticar(String email, String senha) {
        CadastroModel cadastro = cadastroRepository.findByEmail(email);
        if (cadastro != null && cadastro.getSenha().equals(senha)) {
            return cadastro;
        }
        return null;
    }

    /**
     * Atualiza a senha de um cadastro.
     *
     * @param id ID do cadastro
     * @param novaSenha nova senha
     * @throws CadastroNotFoundException se o cadastro não for encontrado
     */
    public void atualizarSenha(Long id, String novaSenha) throws CadastroNotFoundException {
        CadastroModel cadastro = cadastroRepository.findById(id)
                .orElseThrow(() -> new CadastroNotFoundException("Cadastro não encontrado"));
        cadastro.setSenha(novaSenha);
        cadastroRepository.save(cadastro);
    }

    /**
     * Busca um cadastro pelo email.
     *
     * @param email Email do cadastro
     * @return CadastroModel correspondente ao email
     */
    public CadastroModel buscarPorEmail(String email) {
        return cadastroRepository.findByEmail(email);
    }

    /**
     * Verifica se um email já está cadastrado.
     *
     * @param email Email a ser verificado
     * @return true se o email já estiver cadastrado, false caso contrário
     */
    public boolean emailJaCadastrado(String email) {
        return cadastroRepository.findByEmail(email) != null;
    }
}