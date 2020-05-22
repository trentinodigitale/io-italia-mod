package it.tndigit.iot.domain;

import it.tndigit.iot.domain.common.DatePO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "IOTTSERVIZIO", uniqueConstraints = @UniqueConstraint(name = "codIdPK", columnNames= { "COD_IDENTIFICATIVO"} ))
@Inheritance(strategy = InheritanceType.JOINED)
@Component
@Scope("prototype")
@Getter
@Setter
public class ServizioPO extends DatePO {

    @NotNull
    @Column(name = "NOME_ENTE",unique = true)
    private String nomeEnte;

    @NotNull
    @Column(name = "NOME_DIPARTIMENTO")
    private String nomeDipartimento;

    @NotNull
    @Column(name = "NOME_SERVIZIO",unique = true)
    private String nomeServizio;

    @Column(name = "CODICE_SERVIZIO_IOITALIA")
    private String codiceServizioIoItalia;

    @NotNull
    @Column(name = "CODICEFISCALE")
    private String codiceFiscale;

    @NotNull
    @Column(name = "EMAIL")
    private String email;

    @NotNull
    @Column(name = "EMAIL_PEC",unique = true)
    private String emailPec;

    @Column(name = "TOKEN")
    private String tokenIoItalia;

    @NotNull
    @Column(name = "COD_IDENTIFICATIVO",unique = true)
    private String codiceIdentificativo;


}
