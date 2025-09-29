package io.github.CarlosBackend.libraryapi.controller.mappers;

import io.github.CarlosBackend.libraryapi.controller.dto.UsuarioDTO;
import io.github.CarlosBackend.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    Usuario toEntity(UsuarioDTO dto);
    UsuarioDTO toDTO(Usuario usuario);
}
