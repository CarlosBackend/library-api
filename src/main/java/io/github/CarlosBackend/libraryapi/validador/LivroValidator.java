package io.github.CarlosBackend.libraryapi.validador;

import io.github.CarlosBackend.libraryapi.exceptions.CampoInvalidoException;
import io.github.CarlosBackend.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.CarlosBackend.libraryapi.model.Livro;
import io.github.CarlosBackend.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component // Para que o spring reconheça o componente
@RequiredArgsConstructor // Para que o lombok gere o construtor padrão
public class LivroValidator {
    private static final int ano_exigencias_preco = 2020;
    private final LivroRepository repository;

    public void validar (Livro livro){
        if (existeLivroComIsbn(livro)){
            throw new RegistroDuplicadoException("Livro já cadastrado com o isbn informado");
        }

        if (isPrecoObrigatorioNulo(livro)){
            throw new CampoInvalidoException("preco","O preço do livro é obrigatório para os anos de 2020 ou superior");
        }
    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {
        return livro.getPreco() == null &&
                livro.getDataPublicacao().getYear()>= ano_exigencias_preco;
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
