package io.github.CarlosBackend.libraryapi.service;

import io.github.CarlosBackend.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.CarlosBackend.libraryapi.model.Autor;
import io.github.CarlosBackend.libraryapi.repository.AutorRepository;
import io.github.CarlosBackend.libraryapi.repository.LivroRepository;
import io.github.CarlosBackend.libraryapi.validador.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;


    public Autor salvar(Autor autor){
        validator.validar(autor);
        return autorRepository.save(autor);
    }
    public void atualizar(Autor autor){
        if(autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessário que o autor já esteja salvo na base de dados");
        }
        validator.validar(autor);
        autorRepository.save(autor);
    }
    public List<Autor> listar(){
        return autorRepository.findAll();
    }
    public long count(){
        return autorRepository.count();
    }

    public Optional<Autor> obterPorId(UUID id){
        return autorRepository.findById(id);
    }

    public void deletar(Autor autor){
        if(possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException("Não é permitido deletar um autor que possui livros cadastrados");
        }
        autorRepository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade){
        if(nome != null && nacionalidade != null){
            return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
        }
        if(nome != null){
            return autorRepository.findByNome(nome);
        }
        if(nacionalidade != null){
            return autorRepository.findByNacionalidade(nacionalidade);
        }
        return autorRepository.findAll();
    }
    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }
}
