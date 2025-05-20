package io.github.CarlosBackend.libraryapi.service;

import io.github.CarlosBackend.libraryapi.model.Autor;
import io.github.CarlosBackend.libraryapi.model.GeneroLivro;
import io.github.CarlosBackend.libraryapi.model.Livro;
import io.github.CarlosBackend.libraryapi.repository.AutorRepository;
import io.github.CarlosBackend.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void salvarLivroComFoto(){
        // salvar o livro
        // repository.save(livro);

        // pega o id do livro = livro.getId();
        // var id = livro.getId();

        // salvar foto do livro -> bucket na nuvem
        // livro.setNomeArquivo(id + ".png");
    }

    @Transactional
    public void atualizarSemAtualizar(){
        var livro = livroRepository
                .findById(UUID.fromString("3f8feb05-b4c3-4627-93d9-482d14c786e6"))
                .orElse(null);

        livro.setTitulo("Guerra de Guerreiros");
        livro.setPreco(BigDecimal.valueOf(120));

    }


    @Transactional
    public void executar(){

        //Salva o autor
        Autor autor = new Autor();
        autor.setNome("Celso");
        autor.setNacionalidade("Brasileira");
        autor.setDate(LocalDate.of(1974,3,3));

        autorRepository.save(autor);

        //salva o livro
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(80));
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setTitulo("Teologia de Deus e Cristo");
        livro.setDataPublicacao(LocalDate.of(1982,3,22));



        livro.setAutor(autor);
        livroRepository.save(livro);

        //Verifica se o autor foi salvo corretamente
        if(autor.getNome().equals("Jose")){
            throw new RuntimeException("Erro ao salvar o autor");
        }
    }
}
