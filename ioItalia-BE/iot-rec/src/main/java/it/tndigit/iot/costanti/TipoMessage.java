package it.tndigit.iot.costanti;

import it.tndigit.iot.service.impl.receive.MessageServiceEmailImpl;
import it.tndigit.iot.service.impl.receive.MessageServiceIoItaliaImpl;

/**
 * @author Mirko
 * @see Enum
 *
 * Discriminate the type of message in order to send it
 */

public enum TipoMessage {
    IO_ITALIA(MessageServiceIoItaliaImpl.class),
    EMAIL(MessageServiceEmailImpl.class);

    private Class messageService;


    TipoMessage(Class messageService) {
        this.messageService = messageService;
    }

    public Class getMessageService() {
        return messageService;
    }
}

