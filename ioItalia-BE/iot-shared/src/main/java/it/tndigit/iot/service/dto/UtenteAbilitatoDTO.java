package it.tndigit.iot.service.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Scope("prototype")
@Getter
@Setter
public class UtenteAbilitatoDTO extends CommonDTO implements Serializable {
    private static final long serialVersionUID = 257211671277893156L;

    private String codiceIdentificativo;
    private String email;
}
