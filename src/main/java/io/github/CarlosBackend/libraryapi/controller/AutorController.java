package io.github.CarlosBackend.libraryapi.controller;
import io.github.CarlosBackend.libraryapi.controller.dto.AutorDTO;
import io.github.CarlosBackend.libraryapi.model.Autor;
import io.github.CarlosBackend.libraryapi.service.AutorService;
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

//http://localhost:8080/autores
public class AutorController {

    private final AutorService service;

    public AutorController(AutorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO autor){
        var autorEntidade = autor.mapearParaAutor();
        service.salvar(autorEntidade);

        //http://localhost:8080/autores/kdfjnejsnfefbsjfeb
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(autorEntidade.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);
        if(autorOptional.isPresent()){
            Autor autor = autorOptional.get();
            AutorDTO dto = new AutorDTO(autor.getId(),
                    autor.getNome(),
                    autor.getDate(),
                    autor.getNacionalidade());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id){
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = service.obterPorId(idAutor);
            if(autorOptional.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            service.deletar(autorOptional.get());
            return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade){
        List<Autor> resultado = service.pesquisa(nome, nacionalidade);
        List<AutorDTO> lista = resultado.stream().map(autor -> new AutorDTO(autor.getId(),
                autor.getNome(),
                autor.getDate(),
                autor.getNacionalidade())
        ).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable("id") String id, @RequestBody AutorDTO dto){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if(autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Autor autor = autorOptional.get();
        autor.setNome(dto.nome());
        autor.setDate(dto.dataNascimento());
        autor.setNacionalidade(dto.nacionalidade());
        service.atualizar(autor);
        return ResponseEntity.noContent().build();
    }
}
