package it.tndigit.iot.service.dto;

import lombok.Getter;
import lombok.Setter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import java.io.Serializable;

@Component
@Scope("prototype")
@Getter
@Setter
public class ServizioDTO extends CommonDTO implements Serializable {

    private static final long serialVersionUID = 257211671246793156L;
    private String nomeEnte;
    private String nomeDipartimento;
    private String nomeServizio;
    private String codiceIdentificativo;
    private String codiceServizioIoItalia;
    private String codiceFiscale;
    private String email;
    private String emailPec;
    private String tokenIoItalia;


}
