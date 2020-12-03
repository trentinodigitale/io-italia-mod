package it.tndigit.iot.service.impl;

import it.tndigit.iot.repository.NotificationRepository;
import it.tndigit.iot.repository.ServizioRepository;
import it.tndigit.iot.repository.UtenteAbilitatoRepository;
import it.tndigit.iot.service.MessageServiceReceive;
import it.tndigit.iot.service.mapper.MessageMapper;
import it.tndigit.iot.service.mapper.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import javax.persistence.EntityManager;

public abstract class MessageServiceAbstract implements MessageServiceReceive {

    @Autowired
    protected NotificationRepository notificationRepository;

    @Autowired
    protected NotificationMapper notificationMapper;

    @Autowired
    protected ServizioRepository servizioRepository;

    @Autowired
    protected UtenteAbilitatoRepository utenteAbilitatoRepository;

    @Autowired
    protected MessageMapper messageMapper;

    @Autowired
    protected EntityManager entityManager;





}
