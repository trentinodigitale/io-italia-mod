package it.tndigit.iot.service.mapper;

import it.tndigit.iot.domain.ServizioPO;
import it.tndigit.iot.service.dto.ServizioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity DeliberaPO and its DTO DeliberaDTO.
 */

@Mapper(componentModel = "spring")
public interface ServizioMapper extends EntityMapper<ServizioDTO, ServizioPO > {


    @Mapping(source = "idObj", target = "idObj")
    ServizioDTO toDto(ServizioPO entePO);

    @Mapping(source = "idObj", target = "idObj")
    ServizioPO toEntity(ServizioDTO enteDTO);


}
