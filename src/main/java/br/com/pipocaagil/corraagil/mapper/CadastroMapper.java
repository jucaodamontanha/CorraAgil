package br.com.pipocaagil.corraagil.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import br.com.pipocaagil.corraagil.model.CadastroModel;
import br.com.pipocaagil.corraagil.dto.CadastroRequestDTO;
import br.com.pipocaagil.corraagil.dto.CadastroResponseDTO;

@Mapper(componentModel = "spring")
public interface CadastroMapper {

    CadastroMapper INSTANCE = Mappers.getMapper(CadastroMapper.class);

    // Mapeia do RequestDTO para o Model (para salvar no banco)
    CadastroModel toModel(CadastroRequestDTO requestDTO);

    // Mapeia do Model para o ResponseDTO (para retornar ao cliente)
    CadastroResponseDTO toResponseDTO(CadastroModel model);
}