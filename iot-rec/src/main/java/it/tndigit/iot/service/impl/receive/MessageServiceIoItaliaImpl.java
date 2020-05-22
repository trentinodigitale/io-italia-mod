package it.tndigit.iot.service.impl.receive;

import it.tndigit.ioitalia.service.dto.*;
import it.tndigit.ioitalia.web.rest.DefaultApi;
import it.tndigit.iot.common.UtilityCrypt;
import it.tndigit.iot.costanti.TipoCryptoMessage;
import it.tndigit.iot.costanti.TipoStatus;
import it.tndigit.iot.domain.message.MessagePO;
import it.tndigit.iot.domain.message.NotificationPO;
import it.tndigit.iot.exception.IotException;
import it.tndigit.iot.repository.MessageRepository;
import it.tndigit.iot.service.MessageServiceReceive;
import it.tndigit.iot.service.dto.message.MessageDTO;
import it.tndigit.iot.service.dto.message.NotificationDTO;
import it.tndigit.iot.service.impl.MessageServiceAbstract;
import it.tndigit.iot.service.mapper.MessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

/**
 *
 *
 */

@Component
@Slf4j
public class MessageServiceIoItaliaImpl extends MessageServiceAbstract implements MessageServiceReceive {


    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    DefaultApi defaultApi;

    @Autowired
    UtilityCrypt utilityCrypt;

    @Override
    public MessageDTO sendMessage(MessageDTO messageDTO){
        return null;
    }

    @RabbitListener(queues = "IO_ITALIA_QUEUE" )
    @Transactional
    public void receiveSendMessage(MessageDTO messageDTO)  {

        Boolean errore= Boolean.FALSE;

        log.info(" RICEVUTO MESSAGGIO IO_ITALIA CON ID " + messageDTO.getIdObj());
        defaultApi.getApiClient().setApiKey(messageDTO.getServizioDTO().getTokenIoItalia());
        try{
            LimitedProfile limitedProfile= defaultApi.getProfile(messageDTO.getCodiceFiscale());
            if (!limitedProfile.isSenderAllowed()){
                messageDTO.setErrorSend("Impossibile mandare il messaggio, utente NON abilitato");
                errore=Boolean.TRUE;
            }
        }catch (RestClientException rce){
            messageDTO.setErrorSend("Impossibile mandare il messaggio, utente NON abilitato");
            errore=Boolean.TRUE;
        }



        if (!errore){
            try{
                InlineResponse201 inlineResponse201 =  defaultApi.submitMessageforUser(messageDTO.getCodiceFiscale(), convertMessage(messageDTO));
                messageDTO.setExternID(inlineResponse201.getId());
            }catch (HttpClientErrorException hcE){
                messageDTO.setErrorSend(hcE.getStatusCode() + " "  + hcE.getMessage());
            }
            catch (Exception ex){
                messageDTO.setErrorSend(ex.getMessage());
            }
        }

        Optional<MessagePO> messagePOCaricato = messageRepository.findById(messageDTO.getIdObj());
        if (messagePOCaricato.isPresent()){

            MessagePO messagePO = messagePOCaricato.get();
            messagePO.setExternID(messageDTO.getExternID());
            messagePO.setErrorSend(messageDTO.getErrorSend());
            if (messagePO.getTipoCryptoMessage().equals(TipoCryptoMessage.CRYPTO)){
                messagePO.setOggetto(utilityCrypt.encrypt(messageDTO.getOggetto()));
                messagePO.setTesto(utilityCrypt.encrypt(messageDTO.getTesto()));
            }
            messagePO.setErrorSend(messageDTO.getErrorSend());
            try{
                messageRepository.saveAndFlush(messagePO);
                entityManager.flush();
                entityManager.detach(messagePO);
            }catch (Exception ex){

            }finally {
                entityManager.close();
            }


        }
    }


    @Override
    public MessageDTO getMessage(MessageDTO messageDTO) {
        defaultApi.getApiClient().setApiKey(messageDTO.getServizioDTO().getTokenIoItalia());
        MessageResponseWithContent messageResponseWithContent= defaultApi.getMessage(messageDTO.getCodiceFiscale() , messageDTO.getExternID());
        this.convertNotification(messageResponseWithContent,messageDTO);
        this.saveCheck(messageDTO);
        return messageDTO;
    }


    /**
     *
     * @author Mirko Pianetti
     * @param messageDTO
     *
     * Save the IOItalia portal check on db
     * Insert a NotificationPO  object  if the state are't already present in the database
     * Update the note field and lastChance field of the NotificationPO objerct is it is already present in the database
     */
    private void saveCheck(MessageDTO messageDTO) {

        //get the last nofication for insert or update it
        Optional<NotificationDTO> notificationDTOOptional =  messageDTO.getNotificationDTOS()
                .stream()
                .filter(notificationDTO -> notificationDTO.getIdObj()==null)
                .findFirst();
        if (notificationDTOOptional.isPresent()){
            //Read on db to obtain the correct notification
            Optional<NotificationPO> notificationPOOptional =
                    notificationRepository.findByMessagePO_IdObjAndEmailNotificationAndWebhookNotificationAndStatus(
                            messageDTO.getIdObj(),
                            notificationDTOOptional.get().getEmailNotification(),
                            notificationDTOOptional.get().getWebhookNotification(),
                            notificationDTOOptional.get().getStatus()
                    );
            if (notificationPOOptional.isPresent()){
                notificationPOOptional.get().setLastChance(LocalDateTime.now());
                notificationPOOptional.get().setNote(notificationPOOptional.get().getNote() + notificationDTOOptional.get().getNote() );
                notificationRepository.saveAndFlush(notificationPOOptional.get());
            }else {
                notificationRepository.saveAndFlush(notificationMapper.toEntity(notificationDTOOptional.get()));
            }
        }
    }


    /**
     *
     * @param messageDTO
     * @return NewMessage
     * @throws IotException
     *
     * Convert the NewMessage object of IoItalia in MessaggeDTO
     *
     *
     */

    protected NewMessage convertMessage(MessageDTO messageDTO) {

        try {
            NewMessage newMessage =new NewMessage();
            MessageContent messageContent = new MessageContent();

            newMessage.setContent(messageContent);
            newMessage.setTimeToLive(messageDTO.getTimeToLive());


            //Gestione scadenza Messaggio
            if (messageDTO.getScadenza()!=null){
                newMessage.getContent().setDueDate(messageDTO.getScadenza());
            }

            newMessage.getContent().setMarkdown(messageDTO.getTesto());
            newMessage.getContent().setSubject(messageDTO.getOggetto());

            convertPayment(messageDTO, newMessage, new PaymentData());
            convertPrescription(messageDTO, newMessage, new PrescriptionData());

            return  newMessage;
        } catch (IotException ex) {
            throw (ex);
        } catch (Exception e) {
            IotException iotException = new IotException(
                    e.getMessage(),
                    this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[1].getMethodName(), null);
            throw (iotException);
        }
    }

    private void convertPayment(MessageDTO messageDTO, NewMessage newMessage, PaymentData paymentData) {
        if (messageDTO.getPaymentDTO() != null && messageDTO.getPaymentDTO().getIdObj()!=null){
            newMessage.getContent().setPaymentData(paymentData);
            newMessage.getContent().getPaymentData().setInvalidAfterDueDate(messageDTO.getPaymentDTO().getInvalid_after_due_date());
            newMessage.getContent().getPaymentData().setAmount(messageDTO.getPaymentDTO().getImporto());
            newMessage.getContent().getPaymentData().setNoticeNumber(messageDTO.getPaymentDTO().getNumeroAvviso());
        }
    }


    private void convertPrescription(MessageDTO messageDTO, NewMessage newMessage, PrescriptionData prescriptionData) {
        if (messageDTO.getPrescriptionDTO() != null && messageDTO.getPrescriptionDTO().getIdObj()!=null){
            newMessage.getContent().setPrescriptionData(prescriptionData );
            newMessage.getContent().getPrescriptionData().setIup(messageDTO.getPrescriptionDTO().getIup());
            newMessage.getContent().getPrescriptionData().setNre(messageDTO.getPrescriptionDTO().getNre());
            newMessage.getContent().getPrescriptionData().setPrescriberFiscalCode(messageDTO.getPrescriptionDTO().getCodiceFiscaleDottore());
        }
    }


    private void convertNotification(MessageResponseWithContent messageResponseWithContent, MessageDTO messageDTO)throws IotException{
        if (log.isDebugEnabled()){
            log.debug("Inizio elaborazione di conversione notirifa");
        }
        NotificationDTO notificationDTO = applicationContext.getBean(NotificationDTO.class);
        notificationDTO.setMessageDTO(messageDTO);
        if (messageResponseWithContent.getNotification()!=null && messageResponseWithContent.getNotification().getEmail()!=null){
            notificationDTO.setEmailNotification(TipoStatus.valueOf(messageResponseWithContent.getNotification().getEmail()));
        }

        if (messageResponseWithContent.getNotification()!=null && messageResponseWithContent.getNotification().getWebhook()!=null){
            notificationDTO.setWebhookNotification(TipoStatus.valueOf(messageResponseWithContent.getNotification().getWebhook()));
        }

        notificationDTO.setStatus(TipoStatus.valueOf(messageResponseWithContent.getStatus()));
        notificationDTO.setLastChance(LocalDateTime.now());
        notificationDTO.setNote(" -- Check effettuato il " + LocalDateTime.now().toString() + " n ");

        if (messageDTO.getNotificationDTOS()== null){
            messageDTO.setNotificationDTOS(new HashSet<>());
        }
        messageDTO.getNotificationDTOS().add(notificationDTO);
    }
}
