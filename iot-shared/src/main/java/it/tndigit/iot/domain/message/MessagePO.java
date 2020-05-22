package it.tndigit.iot.domain.message;

import it.tndigit.iot.costanti.TipoCryptoMessage;
import it.tndigit.iot.costanti.TipoMessage;
import it.tndigit.iot.domain.ServizioPO;
import it.tndigit.iot.domain.common.DatePO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "IOTTMESSAGE",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"CODICEFISCALE","IDOBJ"})})
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners({ AuditingEntityListener.class })
@Component
@Scope("prototype")
@Getter
@Setter
public class MessagePO extends DatePO {


    @ManyToOne()
    @JoinColumn(name = "IDOBJ_SERVIZIO")
    private ServizioPO servizioPO;

    @NotNull
    @Column(name = "CODICEFISCALE")
    private String codiceFiscale;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPOMESSAGGIO")
    @NotNull
    private TipoMessage tipoMessage;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPOCRYPTO")
    @NotNull
    private TipoCryptoMessage tipoCryptoMessage;

    @Column(name = "IDEXTERNAL")
    private String externID;

    @Column(name = "OGGETTO",length = 120)
    @NotNull
    private String oggetto;


    @Column(name = "TESTO",length = 10000)
    @NotNull
    private String testo;


    @Column(name = "SCADENZA")
    private LocalDateTime scadenza;

    @Column(name = "TIMETOLIVE")
    @NotNull
    private Integer timeToLive;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "TELEFONO")
    private String telefono;


    @OneToMany(mappedBy = "messagePO", fetch=FetchType.LAZY, cascade = CascadeType.ALL )
    private Set<NotificationPO> notificationPOS;

    @Column(name = "ERROR_SEND", length = 1000)
    private String errorSend;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "IDPAGAMENTO")
    private PaymentPO paymentPO;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "IDPRESCRIPTION")
    private PrescriptionPO prescriptionPO;

    @NotNull
    @Column(name = "COD_IDENTIFICATIVO",unique = true)
    private String codiceIdentificativo;

}
