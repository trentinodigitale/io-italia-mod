package it.tndigit.iot.domain.message;


import it.tndigit.iot.costanti.TipoStatus;
import it.tndigit.iot.domain.common.CommonPO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "IOTTNOTIFICATION")
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners({ AuditingEntityListener.class })
@Component
@Scope("prototype")
@Getter
@Setter
public class NotificationPO extends CommonPO {
    private static final long serialVersionUID = -8978926117208941821L;

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn(name = "IDMESSAGE",updatable = false)
    private MessagePO messagePO;

    @Column(name = "EMAIL_NOTIFICATION")
    @Enumerated(EnumType.STRING)
    private TipoStatus emailNotification;

    @Column(name = "WEBHOOD_NOTIFICATION")
    @Enumerated(EnumType.STRING)
    private TipoStatus webhookNotification;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private TipoStatus status;

    @CreatedDate
    @Column(name = "DATAINSERIMENTO", insertable = true, updatable = false)
    private LocalDateTime dataInserimento;

    @Column(name = "ULTIMOTENTATIVO")
    private LocalDateTime lastChance;

    @Column(name = "NOTE", length = 10000)
    private String note;


}
