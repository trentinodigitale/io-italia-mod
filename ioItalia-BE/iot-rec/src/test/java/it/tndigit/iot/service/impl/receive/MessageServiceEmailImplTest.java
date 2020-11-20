package it.tndigit.iot.service.impl.receive;

import it.tndigit.iot.costanti.TipoMessage;
import it.tndigit.iot.domain.message.MessagePO;
import it.tndigit.iot.generate.MessageGenerate;
import it.tndigit.iot.repository.MessageRepository;
import it.tndigit.iot.repository.NotificationRepository;
import it.tndigit.iot.service.dto.message.MessageDTO;
import it.tndigit.iot.service.mapper.MessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest()
@DisplayName( "Conversione messaggi Email")
@Transactional
@Slf4j
class MessageServiceEmailImplTest {

//    @MockBean
    JavaMailSender javaMailSender;

    private MimeMessage mimeMessage;

     @Autowired
     MessageServiceEmailImpl messageServiceEmail;

    @Autowired
     MessageGenerate messageGenerate;

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    protected EntityManager entityManager;

    @BeforeEach
    public void before(){

        mimeMessage = new MimeMessage((Session) null);
        javaMailSender = mock(JavaMailSender.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

    }


    @Test
    @Sql(scripts = {"/script/insertServizio.sql" , "/script/insertMessage.sql"})
    @DisplayName( "Ricezione messaggi tipo Email")
    void receiveSendMessage() {
        Optional< MessagePO > messagePOOptional =
                messageRepository
                .findAll()
                    .stream()
                        .filter(messagePO -> messagePO.getTipoMessage().equals(TipoMessage.EMAIL))
                        .findFirst();
        MessageDTO messageDTO = messageMapper.toDto(messagePOOptional.get());

        Integer numeroNotifiche = messageDTO.getNotificationDTOS().size();
        messageDTO.setTipoMessage(TipoMessage.EMAIL);
        messageServiceEmail.receiveSendMessage(messageDTO);

        notificationRepository.findAll();
        entityManager.flush();
        entityManager.detach(messagePOOptional.get());
        Integer numeroNotificheNew = messageRepository.findById(messageDTO.getIdObj()).get().getNotificationPOS().size();

        Assertions.assertEquals(numeroNotifiche + 1, numeroNotificheNew,"Conteggio notifiche ");



    }
}