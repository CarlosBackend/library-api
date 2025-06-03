package io.github.CarlosBackend.libraryapi.controller.dto;
import io.github.CarlosBackend.libraryapi.model.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(
        @NotBlank(message = "Campo obrigatorio")
        String isbn,
        @NotBlank(message = "Campo obrigatorio")
        String titulo,
        @NotNull (message = "Campo obrigatorio")
        @Past(message = "nao pode ser uma data futura")
        LocalDate dataPublicacao,

        GeneroLivro genero,

        BigDecimal preco,
        @NotNull(message = "Campo obrigatorio")
        UUID idAutor
        ) {
}
