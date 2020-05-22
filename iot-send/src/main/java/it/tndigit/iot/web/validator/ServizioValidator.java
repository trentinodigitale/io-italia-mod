package it.tndigit.iot.web.validator;

import it.tndigit.iot.service.dto.ServizioDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class ServizioValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return ServizioDTO.class.equals(aClass);
    }


    @Override
    public void validate(Object target, Errors errors) {
        ServizioDTO enteDTO = (ServizioDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nomeEnte", "servizio.nomeEnte.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nomeDipartimento", "servizio.nomeDipartimento.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nomeServizio", "servizio.nomeServizio.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codiceFiscale", "servizio.codiceFiscale.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "servizio.email.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailPec", "servizio.emailPec.empty");


    }
}
