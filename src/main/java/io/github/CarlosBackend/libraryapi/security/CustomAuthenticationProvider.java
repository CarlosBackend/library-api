package io.github.CarlosBackend.libraryapi.security;

import io.github.CarlosBackend.libraryapi.model.Usuario;
import io.github.CarlosBackend.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UsuarioService service;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String senha = authentication.getCredentials().toString();

        Usuario usuarioEncontrado = service.obterLogin(login);
        if(usuarioEncontrado == null){
            throw new UsernameNotFoundException("Usuário  e/ou senha inválidos");
        }

        String senhaCriptografada = usuarioEncontrado.getSenha();
        boolean senhasBatem = passwordEncoder.matches(senha, senhaCriptografada);
        if(senhasBatem){
            return new CustomAuthentication(usuarioEncontrado);
        }
        throw new UsernameNotFoundException("Usuário  e/ou senha inválidos");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
