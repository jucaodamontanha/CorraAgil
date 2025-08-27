package br.com.pipocaagil.corraagil.security;

import br.com.pipocaagil.corraagil.model.CadastroModel; // Importe o CadastroModel
import br.com.pipocaagil.corraagil.repository.CadastroRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CadastroRepository cadastroRepository;

    public UserDetailsServiceImpl(CadastroRepository cadastroRepository) {
        this.cadastroRepository = cadastroRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca o Optional e lida com a ausência do valor
        Optional<CadastroModel> optionalCadastro = cadastroRepository.findByEmail(email);

        CadastroModel cadastro = optionalCadastro
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + email));

        return new User(
                cadastro.getEmail(),
                cadastro.getSenha(),
                Collections.emptyList()
        );
    }
}