package it.tndigit.iot.domain;

import it.tndigit.iot.domain.common.DatePO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "IOTTUTENTEABILITATO", uniqueConstraints = {@UniqueConstraint(name = "uk_codid", columnNames = {"COD_IDENTIFICATIVO"}),
        @UniqueConstraint(name = "uk_email", columnNames = {"EMAIL"})})
@Inheritance(strategy = InheritanceType.JOINED)
@Component
@Scope("prototype")
@Getter
@Setter
public class UtenteAbilitatoPO extends DatePO {
    @NotNull
    @Column(name = "EMAIL", unique = true)
    private String email;

    @NotNull
    @Column(name = "COD_IDENTIFICATIVO", unique = true)
    private String codiceIdentificativo;
}
