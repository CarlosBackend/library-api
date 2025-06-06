package io.github.CarlosBackend.libraryapi.service;

import io.github.CarlosBackend.libraryapi.model.Livro;
import io.github.CarlosBackend.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {
    private final LivroRepository livroRepository;

    public Livro salvar(Livro livro) {
        livroRepository.save(livro);
        return livro;
    }
    public Optional<Livro> obterPorId(UUID id){
        return livroRepository.findById(id);
    }
    public void deletar(Livro livro){
        livroRepository.delete(livro);
    }
    public void atualizar(Livro livro){
        livroRepository.save(livro);
    }
}
