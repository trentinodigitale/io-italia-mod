package it.tndigit.iot.service.mapper;

import it.tndigit.iot.domain.message.NotificationPO;
import it.tndigit.iot.service.dto.message.NotificationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity DeliberaPO and its DTO DeliberaDTO.
 */

@Mapper(componentModel = "spring")
public interface NotificationMapper extends EntityMapper< NotificationDTO, NotificationPO > {


    @Mapping(source = "idObj", target = "idObj")
    @Mapping(source = "messagePO", target = "messageDTO")
    NotificationDTO toDto(NotificationPO notificationPO);

    @Mapping(source = "idObj", target = "idObj")
    @Mapping(source = "messageDTO", target = "messagePO")
    NotificationPO toEntity(NotificationDTO notificationDTO);


}
