package it.tndigit.iot.service.impl;

import it.tndigit.iot.costanti.TipoRuoli;
import it.tndigit.iot.repository.MessageRepository;
import it.tndigit.iot.schedule.AuthenticationUtil;
import it.tndigit.iot.service.MessageServiceCheck;
import it.tndigit.iot.service.ScheduledService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@Slf4j
public class ScheduledServiceImpl implements ScheduledService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageServiceCheck messageService;


    @Async
    public void timerCheckIoItalia() {
        AuthenticationUtil.configureAuthentication(TipoRuoli.JOB);
        log.info(" - Schedulazione cron partita alle ore " + LocalDateTime.now());

        messageRepository.findMessageForCheck()
                .stream()
                .peek(messagePO -> log.info("Elaborazione messaggio con id" + messagePO.getIdObj()))
                .forEach(messagePO -> {
                    messageService.checkMessage(messagePO.getIdObj(), messagePO.getCodiceFiscale());
                });

        log.info("Effettuati numero " + messageRepository.findMessageForCheck().size() + " check");
        AuthenticationUtil.clearAuthentication();


    }
}
