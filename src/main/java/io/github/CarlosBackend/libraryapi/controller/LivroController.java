package io.github.CarlosBackend.libraryapi.controller;

import io.github.CarlosBackend.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.CarlosBackend.libraryapi.controller.dto.ErroResposta;
import io.github.CarlosBackend.libraryapi.controller.mappers.LivroMapper;
import io.github.CarlosBackend.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.CarlosBackend.libraryapi.model.Livro;
import io.github.CarlosBackend.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController{
    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto){
        try{
            // mapear dto para entidade
            Livro livro = mapper.toEntity(dto);
            // enviar a entidade para o service validar e salvar na base
            service.salvar(livro);
            // criar a url para acesso dos dados do livro
            var url = gerarHeaderLocation(livro.getId());
            // e retornar codigo created com header location
            return ResponseEntity.created(url).build();
        }catch (RegistroDuplicadoException e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }
}
