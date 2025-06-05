package io.github.CarlosBackend.libraryapi.validador;
import io.github.CarlosBackend.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.CarlosBackend.libraryapi.model.Autor;
import io.github.CarlosBackend.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {
    //Valida se o autor já existe na base de dados
    //Caso exista, lança uma exceção de RegistroDuplicadoException
    //Caso não exista, continua o processo de salvar o autor
    //Caso exista, mas o id do autor não foi informado, lança uma exceção de RegistroDuplicadoException
    //Caso exista e o id do autor foi informado, verifica se o autor já existe na base de dados
    //Caso não exista, continua o processo de atualizar o autor
    //Caso exista, mas o id do autor não foi informado, lança uma exceção de RegistroDuplicadoException
    //Caso exista e o id do autor foi informado, verifica se o autor já existe na base de dados
    //Caso exista, lança uma exceção de RegistroDuplicadoException
    private AutorRepository repository;

    public AutorValidator(AutorRepository repository) {
        this.repository = repository;
    }

    public void validar(Autor autor){
        if(existeAutorCadastrado(autor)){
            throw new RegistroDuplicadoException("Autor já existe");
        }
    }

    private boolean existeAutorCadastrado(Autor autor) {
        Optional<Autor> autorEncontrado = repository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade()
        );

        if (autor.getId() == null) {
            return autorEncontrado.isPresent();
        }

        return autorEncontrado
                .map(a -> !autor.getId().equals(a.getId()))
                .orElse(false);
    }
}
