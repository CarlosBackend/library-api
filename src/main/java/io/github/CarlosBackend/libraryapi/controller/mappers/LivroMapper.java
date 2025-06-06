package io.github.CarlosBackend.libraryapi.controller.mappers;

import io.github.CarlosBackend.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.CarlosBackend.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.CarlosBackend.libraryapi.model.Livro;
import io.github.CarlosBackend.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;

// Se nao colocar o componentModel, o mapstruct nao vai funcionar
@Mapper(componentModel = "spring", uses = {AutorMapper.class})
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
