package br.com.pipocaagil.CorraAgil.infra.security;

import br.com.pipocaagil.CorraAgil.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationService implements UserDetailsService {
    @Autowired
    private UsuarioRepository repository;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar o usuário no banco de dados
        return this.repository.findByLogin(username);
    }
}
