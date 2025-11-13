package com.luizalebs.comunicacao_api.business.mapper;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ComunicacaoConverter {

    ComunicacaoEntity paraComunicacaoEntity(ComunicacaoInDTO dto);

    ComunicacaoOutDTO paraDTO(ComunicacaoEntity entity);
}
