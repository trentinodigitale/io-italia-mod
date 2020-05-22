package it.tndigit.iot.service.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Getter
@Setter
public class LoggingDTO extends CommonDTO {

    private String method;
    private String returnCode;
    private String message;
    private Long idMessage;




}
