package it.tndigit.iot.service;

import it.tndigit.iot.exception.IotException;
import it.tndigit.iot.service.dto.message.MessageDTO;

import java.util.Optional;

public interface MessageServiceSend {


    /**
     *
     * Invia il messaggio
     *
     * @return MessageDTO
     * @throws IotException
     */

    MessageDTO sendMessageInCode(MessageDTO messageDTO) throws IotException;

    Optional< MessageDTO > getMessage(Long idObj, String codiceFiscale) throws IotException;
    Optional< MessageDTO > getMessage(String codiceIdentificativo, String codiceFiscale) throws IotException;


}
