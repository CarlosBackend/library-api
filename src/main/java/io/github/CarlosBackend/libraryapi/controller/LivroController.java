package io.github.CarlosBackend.libraryapi.controller;

import io.github.CarlosBackend.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.CarlosBackend.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.CarlosBackend.libraryapi.controller.mappers.LivroMapper;
import io.github.CarlosBackend.libraryapi.model.Livro;
import io.github.CarlosBackend.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {
    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        // mapear dto para entidade
        Livro livro = mapper.toEntity(dto);
        // enviar a entidade para o service validar e salvar na base
        service.salvar(livro);
        // criar a url para acesso dos dados do livro
        var url = gerarHeaderLocation(livro.getId());
        // e retornar codigo created com header location
        return ResponseEntity.created(url).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obeterDetalhes(@PathVariable("id") String id) {
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto = mapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    service.deletar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
