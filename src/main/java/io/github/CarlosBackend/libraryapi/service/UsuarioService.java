package io.github.CarlosBackend.libraryapi.service;

import io.github.CarlosBackend.libraryapi.model.Usuario;
import io.github.CarlosBackend.libraryapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;


    public void salvar(Usuario usuario){
        var senha = usuario.getSenha();
        usuario.setSenha(encoder.encode(senha));
        usuarioRepository.save(usuario);
    }
    public Usuario obterLogin(String login){
        return usuarioRepository.findByLogin(login);
    }
}
