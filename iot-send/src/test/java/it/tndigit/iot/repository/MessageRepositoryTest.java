package it.tndigit.iot.repository;

import it.tndigit.iot.costanti.TipoMessage;
import it.tndigit.iot.domain.ServizioPO;
import it.tndigit.iot.domain.message.MessagePO;
import it.tndigit.iot.generate.MessageGenerate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "/script/insertServizio.sql")
public class MessageRepositoryTest {

    private static Long ID_SERVIZIO = 10000L;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ServizioRepository servizioRepository;

    @Autowired
    private MessageGenerate messageGenerate;

    private MessagePO messagePO;
    private ServizioPO servizioPO;


    @BeforeEach
    public void initTest() {

        messagePO =  messageGenerate.getObjectPO();
        servizioPO=servizioRepository.getOne(ID_SERVIZIO);
        messagePO.setServizioPO(servizioPO);
    }


    @Test()
    @Transactional
    public void findMessageWithIDOBJ_And_CodiceFiscale() {
         messagePO = messageRepository.save(messagePO);
         Optional< MessagePO > messagePOOptional = messageRepository.findByIdObjAndAndCodiceFiscale(messagePO.getIdObj(), messagePO.getCodiceFiscale());
         assertTrue(messagePOOptional.isPresent(),"Controllo dei presenza del messaggio");
         assertEquals(messagePOOptional.get().getServizioPO().getIdObj(),ID_SERVIZIO);
    }

    @Test
    @Transactional
    public void findByTipoMessageAndExternIDNotNull() {
        messagePO = messageRepository.save(messagePO);
        List< MessagePO > list = messageRepository.findByTipoMessageAndExternIDNotNull(TipoMessage.IO_ITALIA);
        assertFalse(list.isEmpty(),"Controllo che la lista non sia vuota");
        assertEquals(list.get(0).getTipoMessage(), TipoMessage.IO_ITALIA);
    }


    @Test
    @Sql(scripts = {"/script/insertServizio.sql","/script/insertMessageRepository.sql"})
    @Transactional
    void findMessageForCheck() {

        List< MessagePO > list = messageRepository.findMessageForCheck();
        assertFalse(list.isEmpty(),"Controllo che la lista non sia vuota");
        assertEquals(list.get(0).getTipoMessage(), TipoMessage.IO_ITALIA);
        assertEquals(list.size(),2);

    }
}