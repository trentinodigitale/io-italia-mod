package it.tndigit.iot.service.impl.receive;

import it.tndigit.iot.common.UtilityEmail;
import it.tndigit.iot.costanti.TipoStatus;
import it.tndigit.iot.domain.message.MessagePO;
import it.tndigit.iot.exception.IotException;
import it.tndigit.iot.repository.MessageRepository;
import it.tndigit.iot.service.MessageServiceReceive;
import it.tndigit.iot.service.dto.message.MessageDTO;
import it.tndigit.iot.service.dto.message.NotificationDTO;
import it.tndigit.iot.service.impl.MessageServiceAbstract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service
@Transactional
@Slf4j
public class MessageServiceEmailImpl extends MessageServiceAbstract implements MessageServiceReceive {

    @Autowired
    ApplicationContext applicationContext;


    @Autowired
    MessageRepository messageRepository;


    @Autowired
    private UtilityEmail utilityEmail;

    @Override
    public MessageDTO sendMessage(MessageDTO messageDTO) throws IotException {
        return null;
    }

    @Override
    public MessageDTO getMessage(MessageDTO messageDTO) throws IotException {
        return null;
    }

    @Override
    @RabbitListener(queues = "EMAIL_QUEUE")
    @Transactional
    public void receiveSendMessage(MessageDTO messageDTO){
        log.info(" RICEVUTO MESSAGGIO EMAIL CON ID " + messageDTO.getIdObj());

        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(messageDTO.getEmail());
            mailMessage.setSubject(messageDTO.getOggetto());
            mailMessage.setText(messageDTO.getTesto());
            mailMessage.setFrom("io-trentino@tndigit.it");
            String invio = utilityEmail.sendEmail(mailMessage);
            this.createNotification(messageDTO, invio);

            MessagePO messagePO = messageMapper.toEntity(messageDTO);
            messageRepository.saveAndFlush(messagePO);
            //this.saveNotification(messageDTO);

        }catch (Exception ex){
            log.error(ex.getMessage());

        }

//
//        //Controllo che il messaggio sia presente nella base dati, potrebbe essere stato cancellato
//        Optional< MessagePO > messagePO = messageRepository.findById(messageDTO.getIdObj());
//
//        if (!messagePO.isPresent()){
//            log.warn("Attenzione, messaggio "+ messageDTO.getIdObj() + " NON trovato nella base dati");
//        }else{
//
//        }
    }


    private void createNotification(MessageDTO messageDTO, String invio) {
        NotificationDTO notificationDTO = applicationContext.getBean(NotificationDTO.class);
        notificationDTO.setMessageDTO(messageDTO);
        if (invio.isEmpty()){
            notificationDTO.setEmailNotification(TipoStatus.SENT);
            notificationDTO.setStatus(TipoStatus.ACCEPTED);
        }else {
            notificationDTO.setEmailNotification(TipoStatus.FAILED);
            notificationDTO.setStatus(TipoStatus.FAILED);
            messageDTO.setErrorSend(invio);
        }

        notificationDTO.setLastChance(LocalDateTime.now());

        if (messageDTO.getNotificationDTOS() == null) {
            messageDTO.setNotificationDTOS(new HashSet<>());
        }
        messageDTO.getNotificationDTOS().add(notificationDTO);

    }


//    private void saveNotification(MessageDTO messageDTO) {
//        try{
//            messageDTO.getNotificationDTOS()
//                    .stream()
//                    .map(notificationDTO -> notificationMapper.toEntity(notificationDTO))
//                    .forEach(
//                            notificationPO -> notificationRepository.saveAndFlush(notificationPO)
//                    );
//        }catch (DataIntegrityViolationException diEx){
//            log.error("Messaggio " + messageDTO.getIdObj()  + "Non presente nella base dati -- Attenzione!!");
//
//        }
//        catch (Exception ex){
//            log.error(ex.getMessage());
//
//        }
//
//
//    }

}
