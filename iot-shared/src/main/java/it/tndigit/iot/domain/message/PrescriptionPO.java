package it.tndigit.iot.domain.message;


import it.tndigit.iot.domain.common.CommonPO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "IOTTPRESCRIPTION")
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners({ AuditingEntityListener.class })
@Component
@Scope("prototype")
@Getter
@Setter
public class PrescriptionPO extends CommonPO {
   private static final long serialVersionUID = 6140202288313216199L;


   @NotNull
   @Column(name = "NRE")
   private String nre;

   @Column(name = "IUP")
   private String iup;

   @Column(name = "CODICE_FISCALE_DOTTORE")
   private String codiceFiscaleDottore;



}
