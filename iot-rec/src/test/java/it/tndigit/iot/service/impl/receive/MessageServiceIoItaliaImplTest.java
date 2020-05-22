package it.tndigit.iot.service.impl.receive;

import it.tndigit.ioitalia.client.invoker.ApiClient;
import it.tndigit.ioitalia.service.dto.InlineResponse201;
import it.tndigit.ioitalia.service.dto.LimitedProfile;
import it.tndigit.ioitalia.service.dto.NewMessage;
import it.tndigit.ioitalia.web.rest.DefaultApi;
import it.tndigit.iot.costanti.TipoCryptoMessage;
import it.tndigit.iot.domain.message.MessagePO;
import it.tndigit.iot.domain.message.NotificationPO;
import it.tndigit.iot.generate.MessageGenerate;
import it.tndigit.iot.generate.NotificationGenerate;
import it.tndigit.iot.generate.ServizioGenerate;
import it.tndigit.iot.repository.MessageRepository;
import it.tndigit.iot.service.dto.message.MessageDTO;
import it.tndigit.iot.service.mapper.MessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest()
@DisplayName( "Conversione messaggi io Italia")
@Transactional
@Slf4j
class MessageServiceIoItaliaImplTest {

    @Autowired
    MessageServiceIoItaliaImpl messageServiceIoItalia;

    @Autowired
    MessageGenerate messageGenerate;

    @Autowired
    NotificationGenerate notificationGenerate;

    @Autowired
    ServizioGenerate servizioGenerate;

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    MessagePO messagePO;

    @MockBean
    DefaultApi defaultApi;

    @MockBean
    LimitedProfile limitedProfile;

    @MockBean
    InlineResponse201 inlineResponse201;

    @MockBean
    MessageRepository messageRepository;


    @Test
    @DisplayName("Conversione messaggio base")
    void convertMessage() {
        MessageDTO messageDTO = messageMapper.toDto(messageGenerate.getObjectPO());
        NewMessage newMessage = messageServiceIoItalia.convertMessage(messageDTO);
        assertNull(newMessage.getFiscalCode(), "Gestione Codice Fiscale");
        assertEquals(messageDTO.getScadenza(), newMessage.getContent().getDueDate(), "Gestione Scadenza");
        assertEquals(messageDTO.getOggetto(), newMessage.getContent().getSubject(), "Gestione oggetto messaggio");
        assertEquals(messageDTO.getTesto(), newMessage.getContent().getMarkdown(), "Gestione testo messaggio");

    }


    @Test
    @DisplayName("Conversione messaggio con Pagamento")
    void convertMessagePaymento() {
        MessageDTO messageDTO = messageMapper.toDto(messageGenerate.getObjectPOPayment(messagePO));
        messageDTO.getPaymentDTO().setIdObj(111111L);
        NewMessage newMessage = messageServiceIoItalia.convertMessage(messageDTO);

        assertEquals(messageDTO.getPaymentDTO().getImporto(), newMessage.getContent().getPaymentData().getAmount(), "Gestione Importo Pagamento");
        assertEquals(messageDTO.getPaymentDTO().getNumeroAvviso(), newMessage.getContent().getPaymentData().getNoticeNumber(), "Gestione Numero Avviso Pagamento");
        assertEquals(messageDTO.getPaymentDTO().getInvalid_after_due_date(), newMessage.getContent().getPaymentData().isInvalidAfterDueDate() , "Gestione Pagamento dopo scadenza");

    }

    @Test
    @DisplayName("Conversione messaggio con prescrizione")
    void convertMessagePrescription() {
        MessageDTO messageDTO = messageMapper.toDto(messageGenerate.getObjectPOPrescription(messagePO));
        messageDTO.getPrescriptionDTO().setIdObj(111111L);
        NewMessage newMessage = messageServiceIoItalia.convertMessage(messageDTO);

        assertEquals(messageDTO.getPrescriptionDTO().getCodiceFiscaleDottore(), newMessage.getContent().getPrescriptionData().getPrescriberFiscalCode(), "Gestione Codice Fiscale dottore in Prescrizione");
        assertEquals(messageDTO.getPrescriptionDTO().getIup(), newMessage.getContent().getPrescriptionData().getIup(), "Gestione IUP prescrizione");
        assertEquals(messageDTO.getPrescriptionDTO().getNre(), newMessage.getContent().getPrescriptionData().getNre(), "Gestione NRE prescrizione");

    }

    @Test
    void receiveSendMessage() {

        final String EXTERNA_ID="CodiceRitornoIOITALIA";

        messagePO.setServizioPO(servizioGenerate.getObjectPO());
        messagePO.setTipoCryptoMessage(TipoCryptoMessage.CRYPTO);
        limitedProfile.setSenderAllowed(Boolean.TRUE);

        Mockito.when(limitedProfile.isSenderAllowed()).thenReturn(Boolean.TRUE);
        Mockito.when(defaultApi.getApiClient()).thenReturn(new ApiClient());
        Mockito.when(defaultApi.getProfile(Mockito.anyString())).thenReturn(limitedProfile);
        Mockito.when(defaultApi.submitMessageforUser(Mockito.anyString(), Mockito.any(NewMessage.class))).thenReturn(inlineResponse201);
        Mockito.when(inlineResponse201.getId()).thenReturn(EXTERNA_ID);
        Mockito.when(messageRepository.findById(Mockito.any())).thenReturn(Optional.of(messagePO));

        MessageDTO messageDTO = messageMapper.toDto(messageGenerate.getObjectPOPrescription(messagePO));
        messageServiceIoItalia.receiveSendMessage(messageDTO);

        Assertions.assertEquals(EXTERNA_ID,messageDTO.getExternID());
        Assertions.assertEquals(messageDTO.getErrorSend(),null);
        Assertions.assertNotEquals(messageDTO.getOggetto(),messagePO.getOggetto());
        Assertions.assertNotEquals(messageDTO.getTesto(),messagePO.getTesto());


    }

    @Test
    void receiveSendMessageException() {

        final String EXTERNA_ID="CodiceRitornoIOITALIA";


        messagePO.setServizioPO(servizioGenerate.getObjectPO());

        Mockito.when(limitedProfile.isSenderAllowed()).thenReturn(Boolean.FALSE);
        Mockito.when(defaultApi.getApiClient()).thenReturn(new ApiClient());
        Mockito.when(defaultApi.getProfile(Mockito.anyString())).thenReturn(limitedProfile);
        Mockito.when(defaultApi.submitMessageforUser(Mockito.anyString(), Mockito.any(NewMessage.class))).thenReturn(inlineResponse201);
        Mockito.when(inlineResponse201.getId()).thenReturn(EXTERNA_ID);
        Mockito.when(messageRepository.findById(Mockito.any())).thenReturn(Optional.of(messagePO));
        MessageDTO messageDTO = messageMapper.toDto(messageGenerate.getObjectPOPrescription(messagePO));
        messageDTO.setExternID("");
        messageServiceIoItalia.receiveSendMessage(messageDTO);
        Assertions.assertEquals(messageDTO.getExternID(),"");
        Assertions.assertEquals(messageDTO.getErrorSend(),"Impossibile mandare il messaggio, utente NON abilitato");

//        Exception exception = assertThrows(NumberFormatException.class, () -> {
//            messageServiceIoItalia.receiveSendMessage(messageDTO);
//        });





    }

    @Test
    void testConvertMessage() {
        messagePO.setServizioPO(servizioGenerate.getObjectPO());

        NotificationPO notificationPO = notificationGenerate.getObjectPO();
        Set< NotificationPO > listaNot = new HashSet<>();
        listaNot.add(notificationPO);
        messagePO.setNotificationPOS(listaNot);

        MessageDTO messageDTO = messageMapper.toDto(messagePO);
        NewMessage newMessage = messageServiceIoItalia.convertMessage(messageDTO);

        Assertions.assertEquals(newMessage.getContent().getSubject(), messageDTO.getOggetto());
        Assertions.assertEquals(newMessage.getContent().getMarkdown(), messageDTO.getTesto());


    }
}