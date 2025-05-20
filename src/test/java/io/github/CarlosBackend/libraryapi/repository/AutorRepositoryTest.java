package io.github.CarlosBackend.libraryapi.repository;
import io.github.CarlosBackend.libraryapi.model.Autor;
import io.github.CarlosBackend.libraryapi.model.GeneroLivro;
import io.github.CarlosBackend.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;
    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Carlos");
        autor.setNacionalidade("Brasileira");
        autor.setDate(LocalDate.of(1998,12,22));

        var autorSalvo = repository.save(autor);
        System.out.println("Autor Salvo: " + autorSalvo);
    }
    @Test
    public void atualziarTest(){
       var id = UUID.fromString("c16b31c1-7f4b-436d-b37f-62af15521a93");
        Optional<Autor> possivelAutor = repository.findById(id);

        if(possivelAutor.isPresent()){

            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do autor: ");
            System.out.println(autorEncontrado);

            autorEncontrado.setNome("Carlos Henrique");
            autorEncontrado.setDate(LocalDate.of(1998,12,22));

            repository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }
    @Test
    public void countest(){
        System.out.println("Contagem de autores: " + repository.count());
    }
    @Test
    public void deletePorIdTest(){
        var id = UUID.fromString("42f6c5c3-0830-4ca8-9d1f-f22958144012");
        repository.deleteById(id);
    }
    @Test
    public void deletePorTest(){
        var id = UUID.fromString("b81e9cc1-1ac2-4cc2-b429-c4895f313ba6");
        var maria = repository.findById(id).get();
        repository.delete(maria);
    }
    @Test
    void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Antonio");
        autor.setNacionalidade("Americano");
        autor.setDate(LocalDate.of(1970,8,5));

        Livro livro = new Livro();
        livro.setIsbn("20847-84874");
        livro.setPreco(BigDecimal.valueOf(204));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("Roubo da casa assombrada");
        livro.setDataPublicacao(LocalDate.of(1999,1,2));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("99999-84874");
        livro2.setPreco(BigDecimal.valueOf(650));
        livro2.setGenero(GeneroLivro.MISTERIO);
        livro2.setTitulo("Roubo da casa assombrada");
        livro2.setDataPublicacao(LocalDate.of(2000,1,2));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);
        //livroRepository.saveAll(autor.getLivros());
    }
    @Test
    void listarLivrosDoAutor(){
        var id = UUID.fromString("c16b31c1-7f4b-436d-b37f-62af15521a93");
        var autor = repository.findById(id).get();

        List<Livro> livroList = livroRepository.findByAutor(autor);
        autor.setLivros(livroList);

        System.out.println("Livros do autor: ");
        autor.getLivros().forEach(System.out::println);
    }
    
}
