package io.github.CarlosBackend.libraryapi.controller;

import io.github.CarlosBackend.libraryapi.controller.dto.AutorDTO;
import io.github.CarlosBackend.libraryapi.controller.dto.ErroResposta;
import io.github.CarlosBackend.libraryapi.controller.mappers.AutorMapper;
import io.github.CarlosBackend.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.CarlosBackend.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.CarlosBackend.libraryapi.model.Autor;
import io.github.CarlosBackend.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
//http://localhost:8080/autores
public class AutorController implements GenericController {

    private final AutorService service;
    private final AutorMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO dto) {
        var autor = mapper.toEntity(dto);
        service.salvar(autor);
        URI location = gerarHeaderLocation(autor.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    // Endpoint para obter detalhes de um autor pelo ID
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        // Converte a string ID recebida para um UUID
        var idAutor = UUID.fromString(id);
        // Verifica se o autor existe no banco de dados
        return service.obterPorId(idAutor)
                .map(autor -> {
                    // Converte a entidade Autor para DTO usando mapper
                    AutorDTO dto = mapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);
        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> resultado = service.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> lista = resultado.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable("id") String id, @RequestBody @Valid AutorDTO dto) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Autor autor = autorOptional.get();
        autor.setNome(dto.nome());
        autor.setDataNascimento(dto.dataNascimento());
        autor.setNacionalidade(dto.nacionalidade());
        service.atualizar(autor);
        return ResponseEntity.noContent().build();
    }
}
