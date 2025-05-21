package io.github.CarlosBackend.libraryapi.controller.dto;


import io.github.CarlosBackend.libraryapi.model.Autor;

import java.time.LocalDate;

public record AutorDTO(
        String nome,
        LocalDate dataNascimento,
        String nacionalidade) {

    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDate(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }
}

