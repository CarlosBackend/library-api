package io.github.CarlosBackend.libraryapi.repository.specs;
import io.github.CarlosBackend.libraryapi.model.GeneroLivro;
import io.github.CarlosBackend.libraryapi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    public static Specification<Livro> isbnsEqual(String isbn){
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("isbn"), isbn);

    }
    public static Specification<Livro> titulosLike(String titulo){
        return (root, query, criteriaBuilder)
                // upper (livro.titulo) like (%:param%)
                -> criteriaBuilder.like(criteriaBuilder.upper( root.get("titulo")), "%" + titulo.toUpperCase() + "%");
    }
    public static Specification<Livro> generoEqual(GeneroLivro genero){
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("genero"), genero);
    }

    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao){
        // and to_char(data_publicacao, 'YYYY') ==:anoPublicacao
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.function("to_char",String.class,
                        root.get("dataPublicacao"),criteriaBuilder.literal("YYYY")),anoPublicacao.toString());
    }
    public static Specification<Livro> nomeAutorLike(String  nome){
        return (root, query, criteriaBuilder)
                -> {
            Join<Object, Object> joinAutor = root.join("autor", JoinType.INNER);
            return criteriaBuilder.like(criteriaBuilder.upper(joinAutor.get("nome")), "%" + nome.toUpperCase() + "%");

        };
    }
}
