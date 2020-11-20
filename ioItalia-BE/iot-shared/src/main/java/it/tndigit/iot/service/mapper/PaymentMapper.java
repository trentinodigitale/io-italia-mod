package it.tndigit.iot.service.mapper;

import it.tndigit.iot.domain.message.PaymentPO;
import it.tndigit.iot.service.dto.message.PaymentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity DeliberaPO and its DTO DeliberaDTO.
 */

@Mapper(componentModel = "spring")
public interface PaymentMapper extends EntityMapper<PaymentDTO, PaymentPO > {

    @Mapping(source = "idObj", target = "idObj")
    PaymentDTO toDto(PaymentPO paymentPO);

    @Mapping(source = "idObj", target = "idObj")
    PaymentPO toEntity(PaymentDTO paymentDTO);

}
