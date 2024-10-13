package br.com.pipocaagil.CorraAgil.infra.security;

import br.com.pipocaagil.CorraAgil.repositories.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository repository;

    @Override
    @Bean
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain  filterChain) throws ServletException, IOException {
        // Recupera o Token do Header da request
        var tokenJWT = recoverToken(request);

        // Validação para autenticação da requisição do usuário logado 'getSubject()' do TokenService
        if (tokenJWT != null) {
            // Variável para buscar usuário com token de validação
            var subjectLoginEmail = tokenService.validateToken(tokenJWT);

            // Validação entre getSubject() e usuário no banco de dados
            var usuario = repository.findByLogin(subjectLoginEmail);

            // Object Reference do DTO Spring para um usuário logado no sistema
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

            // Autenticação do usuário 'logado com o token' na API
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            return authHeader.replace("Bearer ", "");
        }
        return null;
    }
}
