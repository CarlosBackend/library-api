package io.github.CarlosBackend.libraryapi.security;

import io.github.CarlosBackend.libraryapi.model.Usuario;
import io.github.CarlosBackend.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUsertDetailsService implements UserDetailsService {

    private final UsuarioService service;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = service.obterLogin(login);
        if(usuario == null){
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(usuario.getRoles().toArray(new String[usuario.getRoles().size()]))
                .build();
    }
}
