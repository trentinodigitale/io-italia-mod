package it.tndigit.iot.service.impl;

import it.tndigit.iot.common.UtilityIot;
import it.tndigit.iot.domain.ServizioPO;
import it.tndigit.iot.domain.message.MessagePO;
import it.tndigit.iot.exception.IotException;
import it.tndigit.iot.repository.MessageRepository;
import it.tndigit.iot.repository.ServizioRepository;
import it.tndigit.iot.service.MessageServiceSend;
import it.tndigit.iot.service.dto.ServizioDTO;
import it.tndigit.iot.service.dto.message.MessageDTO;
import it.tndigit.iot.service.mapper.MessageMapper;
import it.tndigit.iot.service.mapper.ServizioMapper;
import it.tndigit.iot.utils.MessageBundleBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.persistence.EntityManager;
import javax.ws.rs.ForbiddenException;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
@Slf4j
public class
MessageServiceSendImpl implements MessageServiceSend {



    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    protected ServizioRepository servizioRepository;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private ServizioMapper enteMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    MessageBundleBuilder messageSource;


    @Autowired
    EntityManager entityManager;

    protected Optional< ServizioDTO > getServizio(){
        String utente  = UtilityIot.getUserName();

        if (utente==null || utente.isEmpty()){
            return Optional.empty();
        }else {
            Optional< ServizioPO > entePOOptional = servizioRepository.findAllByCodiceIdentificativo(utente);
            if(entePOOptional.isPresent()){
                return Optional.of(enteMapper.toDto(entePOOptional.get()));
            }
            return  Optional.empty();
        }
    }

    @Override
    @Transactional
    public MessageDTO sendMessageInCode(MessageDTO messageDTO) {
        log.info("INVIO MESSAGGIO IN CODA" + messageDTO.getOggetto());
        Optional< ServizioDTO > servizioDTOOptional = getServizio();
        if (!servizioDTOOptional.isPresent()){
            throw  new IotException("Impossibile inviare il messaggio, Servizio NON presente");
        }
        messageDTO.setServizioDTO(servizioDTOOptional.get());

        //Assegno il codice identificativo al mesasggio

        String digestString = messageDTO.getTesto() + messageDTO.getOggetto() + messageDTO.getCodiceFiscale() + LocalDateTime.now().toString();
        String codiceIdentificativo=DigestUtils.md5DigestAsHex(digestString.getBytes());
        messageDTO.setCodiceIdentificativo(codiceIdentificativo);

        log.info("Codice di partenza " + digestString);
        log.info("Codice Calcolato " + codiceIdentificativo);

        //Save the new message on the database
        MessagePO messagePO = messageMapper.toEntity(messageDTO);
        log.info("SALVATAGGIO MESSAGGIO CON IDENTIFICATIVO " + messageDTO.getCodiceIdentificativo());
        messagePO = messageRepository.saveAndFlush(messagePO);
        messageDTO = messageMapper.toDto(messagePO);
        rabbitTemplate.convertAndSend(messageDTO.getTipoMessage().name() + "_QUEUE", messageDTO );
        entityManager.detach(messagePO);
        return messageDTO;
    }





    @Override
    public Optional< MessageDTO > getMessage(Long idObj, String codiceFiscale) {
       Optional< MessagePO > messagePO = messageRepository.findByIdObjAndAndCodiceFiscale(idObj,codiceFiscale);
        if (messagePO.isPresent()){
            MessageDTO messageDTO = messageMapper.toDto(messagePO.get());
            return Optional.of(messageDTO);
        }

        return Optional.empty();

    }

    @Override
    public Optional< MessageDTO > getMessage(String codiceIdentificativo, String codiceFiscale) {
        Optional< MessagePO > messagePO = messageRepository.findByCodiceIdentificativoAndCodiceFiscale(codiceIdentificativo,codiceFiscale);

        if (messagePO.isPresent()){
            //Se il messaggio è presente controllo che la richiesta sia stata effettuata dall'ente corretto

            if (UtilityIot.getUserName().equals(messagePO.get().getServizioPO().getCodiceIdentificativo())){
                MessageDTO messageDTO = messageMapper.toDto(messagePO.get());
                return Optional.of(messageDTO);
            }else {

                throw  new IotException(messageSource.getMessage("message.get.servizioErrato"));
            }


        }

        return Optional.empty();

    }

}

