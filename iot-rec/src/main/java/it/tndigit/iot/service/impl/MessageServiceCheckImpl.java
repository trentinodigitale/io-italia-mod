package it.tndigit.iot.service.impl;

import it.tndigit.iot.domain.message.MessagePO;
import it.tndigit.iot.repository.MessageRepository;
import it.tndigit.iot.service.MessageServiceCheck;
import it.tndigit.iot.service.MessageServiceReceive;
import it.tndigit.iot.service.dto.message.MessageDTO;
import it.tndigit.iot.service.mapper.MessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class MessageServiceCheckImpl implements MessageServiceCheck {

    private static String CODICEFISCALE= " codice fiscale ";


    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    ApplicationContext applicationContext;

    @Override
    @Transactional
    public Optional< MessageDTO > checkMessage(Long idObj, String codiceFiscale)  {
        log.info("INIZIO elaborazione check per id " + idObj + ", "+ CODICEFISCALE+  codiceFiscale);

        //Cerco il messaggio nella base dati
        Optional< MessagePO > messagePO = messageRepository.findByIdObjAndAndCodiceFiscaleAndExternIDIsNotNull(idObj,codiceFiscale);

        if (messagePO.isPresent()){
            //Converto il messaggio
            MessageDTO messageDTO = messageMapper.toDto(messagePO.get());
            MessageServiceReceive messageService = (MessageServiceReceive) applicationContext.getBean(messagePO.get().getTipoMessage().getMessageService());
            messageDTO = messageService.getMessage(messageDTO);

            log.info("FINE elaborazione check per id " + idObj + "," + CODICEFISCALE + codiceFiscale);
            return Optional.of(messageDTO);
        }


        log.info("IMPOSSIBILE elaborare check per id " + idObj + ", " +CODICEFISCALE+ codiceFiscale);
        return Optional.empty();

    }
}
