package br.com.pipocaagil.corraagil.mapper;

import br.com.pipocaagil.corraagil.dto.CorridaRequestDTO;
import br.com.pipocaagil.corraagil.dto.CorridaResponseDTO;
import br.com.pipocaagil.corraagil.model.CorridaModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CorridaMapper {

    CorridaModel toModel(CorridaRequestDTO dto);

    @Mapping(source = "cadastro.id", target = "cadastroId")
    CorridaResponseDTO toResponseDTO(CorridaModel model);
}