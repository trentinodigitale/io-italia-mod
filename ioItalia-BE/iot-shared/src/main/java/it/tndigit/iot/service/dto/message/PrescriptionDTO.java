package it.tndigit.iot.service.dto.message;

import it.tndigit.iot.service.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Getter
@Setter
public class PrescriptionDTO extends CommonDTO {


    private static final long serialVersionUID = -8224097411612917752L;

    private String nre = null;
    private String iup = null;
    private String codiceFiscaleDottore = null;






}
