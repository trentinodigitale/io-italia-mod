package it.tndigit.iot.service.dto.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import it.tndigit.iot.costanti.TipoCryptoMessage;
import it.tndigit.iot.service.dto.CommonDTO;
import it.tndigit.iot.service.dto.ServizioDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Component
@Scope("prototype")
@Getter
@Setter
public class MessageDTO extends CommonDTO implements Serializable {

    private static final long serialVersionUID = 6540592208421994223L;
    @Size(min = 16, max = 16)
    private String codiceFiscale;

    private it.tndigit.iot.costanti.TipoMessage TipoMessage;

    private TipoCryptoMessage tipoCryptoMessage;

    private ServizioDTO servizioDTO;

    private String externID;

    private Integer timeToLive = 3600;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String oggetto;

    private String testo;

    private String email;

    private String telefono;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private String scadenza;

    private Set< NotificationDTO > notificationDTOS;

    private PaymentDTO paymentDTO;

    private PrescriptionDTO prescriptionDTO;

    private String errorSend;

    private String codiceIdentificativo;

}
