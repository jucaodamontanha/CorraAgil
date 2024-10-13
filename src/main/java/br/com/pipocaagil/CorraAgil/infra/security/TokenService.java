package br.com.pipocaagil.CorraAgil.infra.security;

import br.com.pipocaagil.CorraAgil.domain.UsuarioModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;
    @Bean
    public String generateToken(UsuarioModel usuarioModel) {
        try {
            // Algoritmo de criptografia para utilizar
            Algorithm algorithm = Algorithm.HMAC256(secret);

            // Criação do Token
            return JWT.create()
                    .withIssuer("API Pipoca Agil")
                    .withSubject(usuarioModel.getLogin()) // Subject 'getLogin()' para validar de acordo com 'SecurityFilter'
                    .withExpiresAt(this.generateExpirationDate()) // Tempo de Validade
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao autenticar!", e);
        }
    }

     @Bean
    public String validateToken(String validateToken) {
        try {
            // Algoritmo de criptografia para utilizar
            Algorithm algorithm = Algorithm.HMAC256(secret);

            // Realiza a verificação do token "generateToken()" e valida
            return JWT.require(algorithm)
                    .withIssuer("API Pipoca Agil")
                    .build()
                    .verify(validateToken)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
