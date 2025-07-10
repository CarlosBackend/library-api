package io.github.CarlosBackend.libraryapi.validador;

import io.github.CarlosBackend.libraryapi.model.Livro;
import io.github.CarlosBackend.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component // Para que o spring reconheça o componente
@RequiredArgsConstructor // Para que o lombok gere o construtor padrão
public class LivroValidator {
    private final LivroRepository repository;

    public void validar (Livro livro){
        if (existeLivroComIsbn(livro)){
            throw new IllegalArgumentException("Livro já cadastrado com o isbn informado");
        }
    }
    private boolean existeLivroComIsbn(Livro livro) {
        Optional<Livro> livroEncontrado = repository.findByIsbn(livro.getIsbn());
        if(livro.getId() == null){
            return livroEncontrado.isPresent();
        }
        return livroEncontrado
                .map(Livro::getId)
                .stream()
                .anyMatch(id -> !id.equals(livro.getId())); // Se o id do livro for igual ao id do livro que está sendo validado, não é válido
    }
}
