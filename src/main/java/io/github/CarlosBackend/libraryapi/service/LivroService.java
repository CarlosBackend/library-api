package io.github.CarlosBackend.libraryapi.service;
import io.github.CarlosBackend.libraryapi.model.GeneroLivro;
import io.github.CarlosBackend.libraryapi.model.Livro;
import io.github.CarlosBackend.libraryapi.repository.LivroRepository;
import io.github.CarlosBackend.libraryapi.repository.specs.LivroSpecs;
import io.github.CarlosBackend.libraryapi.validador.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.CarlosBackend.libraryapi.repository.specs.LivroSpecs.anoPublicacaoEqual;

@Service
@RequiredArgsConstructor
public class LivroService {
    private final LivroRepository livroRepository;
    private final LivroValidator validator;

    public Livro salvar(Livro livro) {
        validator.validar(livro);
        livroRepository.save(livro);
        return livro;
    }

    public Optional<Livro> obterPorId(UUID id) {
        return livroRepository.findById(id);
    }

    public void deletar(Livro livro) {
        livroRepository.delete(livro);
    }

    public void atualizar(Livro livro) {
        if(livro.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessário que o livro já esteja salvo na base de dados");
        }
        validator.validar(livro);
        livroRepository.save(livro);
    }

    // isbn, titulo, nome autor, genero, ano de publicacao
    public Page<Livro> pesquisa(String isbn,
                                String titulo,
                                String nomeAutor,
                                GeneroLivro generoLivro,
                                Integer anoPublicacao,
                                Integer pagina,
                                Integer tamanhoPagina
                                ) {

        // select * from livro where 0 = 0
        Specification<Livro> specs = Specification.where((root, query, criteriaBuilder)
                -> criteriaBuilder.conjunction());
        if(isbn != null){
            specs = specs.and(LivroSpecs.isbnsEqual(isbn));
        }
        if(titulo != null){
            specs = specs.and(LivroSpecs.titulosLike(titulo));
        }
        if(generoLivro != null){
            specs = specs.and(LivroSpecs.titulosLike(generoLivro.name()));
        }
        if(anoPublicacao != null){
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        }
        if(nomeAutor != null){
            specs = specs.and(LivroSpecs.nomeAutorLike(nomeAutor));
            //select * from livro where upper(autor.nome) like %:nome%
        }
        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);
        return livroRepository.findAll(specs, pageRequest);
    }
}
