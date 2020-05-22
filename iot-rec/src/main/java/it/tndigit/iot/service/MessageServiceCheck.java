package it.tndigit.iot.service;

import it.tndigit.iot.exception.IotException;
import it.tndigit.iot.service.dto.message.MessageDTO;

import java.util.Optional;

public interface MessageServiceCheck {

    Optional< MessageDTO > checkMessage(Long idObj, String codiceFiscale) throws IotException;

}
