package it.tndigit.iot.service.mapper;

import it.tndigit.iot.domain.message.PrescriptionPO;
import it.tndigit.iot.service.dto.message.PrescriptionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity DeliberaPO and its DTO DeliberaDTO.
 */

@Mapper(componentModel = "spring")
public interface PrescriptionMapper extends EntityMapper< PrescriptionDTO, PrescriptionPO > {

    @Mapping(source = "idObj", target = "idObj")
    PrescriptionDTO toDto(PrescriptionPO prescriptionPO);

    @Mapping(source = "idObj", target = "idObj")
    PrescriptionPO toEntity(PrescriptionDTO prescriptionDTO);

}
