package io.github.CarlosBackend.libraryapi.repository;

import io.github.CarlosBackend.libraryapi.model.Autor;
import io.github.CarlosBackend.libraryapi.model.GeneroLivro;
import io.github.CarlosBackend.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @see
 */
public interface LivroRepository extends JpaRepository<Livro, UUID> {

    //Query Method

    List<Livro> findByAutor(Autor autor);

    // select * from livro where titulo = titulo
    List<Livro> findByTitulo(String titulo);

    List<Livro> findByIsbn(String isbn);

    //Select * from livro where titulo = titulo and preco = preco
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    // select * from where data_publicacao betwen ? and ?
    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    //JPQL -> referencia as entidades e as propiedades
    // select l.* from livro as l order by l.titulo
    @Query(" select l from Livro as l order by l.titulo,l.preco")
    List<Livro> listarTodosOrdenadoPorTituloEPreco();

    /**
     * select a.*
     * from livro l
     * join autor a on a.id = l.id_autor
     * @return
     */
    @Query("select a from Livro l join l.autor a ")
    List<Autor> listarAutoresDosLivros();

    @Query("select distinct l.titulo from Livro l")
    List<String> listarNomesDiferentesLivros();

    @Query("""
            select l.genero
                        from Livro l
                        join l.autor a
                        where a.nacionalidade = 'Brasileira'
                        order by l.genero
            """)
    List<String> listarGenerosAutoresBrasileiros();

    @Query("select l from  Livro l where l.genero = :genero order by :paramOrdernacao")
    List<Livro> findByGenero(
            @Param("genero") GeneroLivro generoLivro,
            @Param("paramOrdernacao") String paramOrdernacao);

    @Query("select l from  Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroPositionalParam(GeneroLivro generoLivro, String nomePropriedade);

    @Modifying
    @Transactional
    @Query(" delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro generoLivro);

    @Modifying
    @Transactional
    @Query(" update Livro set dataPublicacao = ?1")
    void updateDataPublicacao(LocalDate dataPublicacao);
}
