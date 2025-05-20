package io.github.CarlosBackend.libraryapi.repository;

import io.github.CarlosBackend.libraryapi.model.Autor;
import io.github.CarlosBackend.libraryapi.model.GeneroLivro;
import io.github.CarlosBackend.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {
    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTeste(){
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Cienca da computação");
        livro.setDataPublicacao(LocalDate.of(1983,3,22));

        //Autor autor =  autorRepository.findById(UUID.fromString("c16b31c1-7f4b-436d-b37f-62af15521a93")).orElse(null);
        //livro.setAutor(autor);


        repository.save(livro);
    }

    @Test
    void salvarAutorELivroTest(){
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(80));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Terceiro Livro");
        livro.setDataPublicacao(LocalDate.of(1982,3,22));


        Autor autor = new Autor();
        autor.setNome("Jose");
        autor.setNacionalidade("Brasileira");
        autor.setDate(LocalDate.of(1952,11,12));

        livro.setAutor(autor);
        repository.save(livro);
        autorRepository.save(autor);
    }

    @Test
    void salvarCascadeTeste(){
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(80));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Ciencias");
        livro.setDataPublicacao(LocalDate.of(1980,3,20));


        Autor autor = new Autor();
        autor.setNome("Joao");
        autor.setNacionalidade("Brasileira");
        autor.setDate(LocalDate.of(1952,11,12));

        livro.setAutor(autor);
        repository.save(livro);
    }
    @Test
    void atualizarAutorDoLivro(){
        // Autor atual
        UUID id = UUID.fromString("3f8feb05-b4c3-4627-93d9-482d14c786e6");
        var livroParaAtualizar = repository.findById(id)
                .orElse(null);

        //Novo Autor
        UUID idAutor = UUID.fromString("c16b31c1-7f4b-436d-b37f-62af15521a93");
        //Setando novo autor para o livro

        Autor maria = autorRepository.findById(idAutor).orElse(null);
        livroParaAtualizar.setAutor(maria);
        // Atualizando os dados do livro
        repository.save(livroParaAtualizar);
    }

    @Test
    void deletar(){
        UUID id = UUID.fromString("3f8feb05-b4c3-4627-93d9-482d14c786e6");
        repository.deleteById(id);
    }
    @Test
    void deletarCascade(){
        UUID id = UUID.fromString("3f8feb05-b4c3-4627-93d9-482d14c786e6");
        repository.deleteById(id);
    }
    @Test
    @Transactional
    void buscarLivroTest(){
        UUID id = UUID.fromString("79c87007-567b-4ba2-bd30-50a3c63e032e");
        Livro livro =  repository.findById(id).orElse(null);

        System.out.println("Livro: ");
        System.out.println(livro.getTitulo());

        System.out.println("Autor: ");
        System.out.println(livro.getAutor().getNome());
    }
    @Test
    void buscarLivrosPorTituloTest(){
        List<Livro> livros = repository.findByTitulo("Roubo da casa assombrada");
        livros.forEach(System.out::println);
    }
    @Test
    void buscarLivrosPorIsbnTest(){
        List<Livro> livros = repository.findByIsbn("20847-84874");
        livros.forEach(System.out::println);
    }
    @Test
    void buscarLivrosPorTituloEPrecoTest(){
        var preco = BigDecimal.valueOf(204.00);
        String titulo = "Roubo da casa assombrada";

        List<Livro> livros = repository.findByTituloAndPreco(titulo, preco);
    }
    @Test
    void listarLivrosComQueryJPQL(){
        var resultado = repository.listarTodosOrdenadoPorTituloEPreco();
        resultado.forEach(System.out::println);
    }
    @Test
    void listarAutoresDosLivros(){
        var resultado = repository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }
    @Test
    void listarTitulosNaoRepetidosDosLivros(){
        var resultado = repository.listarNomesDiferentesLivros();
        resultado.forEach(System.out::println);
    }
    @Test
    void listarGenerosDosAutoresBrasileiros(){
        var resultado = repository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }
    @Test
    void listarPorGeneroQueryParam(){
        var resultado = repository.findByGenero(GeneroLivro.MISTERIO, "preco");
        resultado.forEach(System.out::println);
    }
    @Test
    void listarPorPositionalParam(){
        var resultado = repository.findByGeneroPositionalParam(GeneroLivro.FANTASIA, "preco");
        resultado.forEach(System.out::println);
    }
    @Test
    void deletePorGenero(){
        repository.deleteByGenero(GeneroLivro.FANTASIA);
    }
    @Test
    void updateDataPublicacao(){
        repository.updateDataPublicacao(LocalDate.of(2000,1,1));
    }
}
