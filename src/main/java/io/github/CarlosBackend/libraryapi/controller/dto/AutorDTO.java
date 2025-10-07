package io.github.CarlosBackend.libraryapi.controller.dto;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,
        // @NotBlank é usada para Strings
        @NotBlank(message = "Campo obrigatorio")
        @Size(min = 2,max =100, message = "O nome deve ter no máximo 100 caracteres")
        String nome,
        @NotNull(message = "Campo obrigatorio")
        @Past(message = "A data de nascimento deve ser anterior a data atual")
        LocalDate dataNascimento,
        @NotBlank(message = "Campo obrigatorio")
        @Size(max = 50, min = 2,message = "A nacionalidade deve ter no máximo 50 caracteres")
        String nacionalidade) {

}

