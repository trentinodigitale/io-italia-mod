package it.tndigit.iot.service.mapper;

import it.tndigit.iot.domain.message.MessagePO;
import it.tndigit.iot.service.dto.message.MessageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity DeliberaPO and its DTO DeliberaDTO.
 */

@Mapper(componentModel = "spring", uses = {NotificationMapper.class, ServizioMapper.class, PaymentMapper.class, PrescriptionMapper.class})
public interface MessageMapper extends EntityMapper< MessageDTO, MessagePO > {


    @Mapping(source = "idObj", target = "idObj")
    @Mapping(source = "notificationPOS", target = "notificationDTOS")
    @Mapping(source = "servizioPO", target = "servizioDTO")
    @Mapping(source = "paymentPO", target = "paymentDTO")
    @Mapping(source = "prescriptionPO", target = "prescriptionDTO")
    MessageDTO toDto(MessagePO messagePO);

    @Mapping(source = "idObj", target = "idObj")
    @Mapping(source = "notificationDTOS", target = "notificationPOS")
    @Mapping(source = "servizioDTO", target = "servizioPO")
    @Mapping(source = "paymentDTO", target = "paymentPO")
    @Mapping(source = "prescriptionDTO", target = "prescriptionPO")
    @Mapping(source = "scadenza", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSz", target = "scadenza")
    MessagePO toEntity(MessageDTO messageDTO);

}
