package it.tndigit.iot.repository;

import it.tndigit.iot.domain.message.MessagePO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Sql(scripts = {"/script/insertServizio.sql", "/script/insertMessage.sql"})
@Transactional
@DisplayName( "Trova Notifiche IO Italia")
public class MessageRepositoryQueryTest {

    @Autowired
    MessageRepository messageRepository;

    @Test()
    @DisplayName("Find Messaggi per Check")
    public void find() {

        messageRepository.findAll();
        List< MessagePO > listaMessaggi = messageRepository.findMessageForCheck();

        Long conteggioMessaggi= listaMessaggi
                .stream()
                .filter(messagePO -> messagePO.getIdObj().equals(2L))
                .count();
        assertTrue(conteggioMessaggi==0);

        conteggioMessaggi= listaMessaggi
                .stream()
                .count();


        assertTrue(conteggioMessaggi.equals(13L));

    }


}