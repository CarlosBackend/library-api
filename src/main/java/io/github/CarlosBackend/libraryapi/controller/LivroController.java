package io.github.CarlosBackend.libraryapi.controller;
import io.github.CarlosBackend.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.CarlosBackend.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.CarlosBackend.libraryapi.controller.mappers.LivroMapper;
import io.github.CarlosBackend.libraryapi.model.GeneroLivro;
import io.github.CarlosBackend.libraryapi.model.Livro;
import io.github.CarlosBackend.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    @GetMapping
    public ResponseEntity <List<ResultadoPesquisaLivroDTO>> pesquisar(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "nome-autror", required = false)
            String nomeAutor,
            @RequestParam(value = "genero", required = false)
            GeneroLivro generoLivro,
            @RequestParam(value = "ano-publicacao", required = false)
            Integer anoPublicacao){

        var resultado = service.pesquisa(isbn, titulo, nomeAutor, generoLivro, anoPublicacao);
        var lista = resultado.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }
}
