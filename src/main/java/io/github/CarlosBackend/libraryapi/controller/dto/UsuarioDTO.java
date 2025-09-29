package io.github.CarlosBackend.libraryapi.controller.dto;
import java.util.List;

public record UsuarioDTO(String login, String senha, List<String> roles) {
}
