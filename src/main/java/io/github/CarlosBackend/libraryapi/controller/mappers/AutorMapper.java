package io.github.CarlosBackend.libraryapi.controller.mappers;

import io.github.CarlosBackend.libraryapi.controller.dto.AutorDTO;
import io.github.CarlosBackend.libraryapi.model.Autor;
import org.mapstruct.Mapper;

// Se nao colocar o componentModel, o mapstruct nao vai funcionar
@Mapper(componentModel = "spring")
public interface AutorMapper {


    Autor toEntity(AutorDTO dto);
    AutorDTO toDTO(Autor autor);
}
