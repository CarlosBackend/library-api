package io.github.CarlosBackend.libraryapi.controller;
import io.github.CarlosBackend.libraryapi.controller.dto.UsuarioDTO;
import io.github.CarlosBackend.libraryapi.controller.mappers.UsuarioMapper;
import io.github.CarlosBackend.libraryapi.model.Usuario;
import io.github.CarlosBackend.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final UsuarioMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UsuarioDTO dto){
        var usuario = mapper.toEntity(dto);
        usuarioService.salvar(usuario);
    }
}
