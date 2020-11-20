package it.tndigit.iot.web.validator;

import it.tndigit.iot.service.dto.ServizioDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class ServizioValidator implements Validator {


    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);



    @Override
    public boolean supports(Class<?> aClass) {
        return ServizioDTO.class.equals(aClass);
    }


    @Override
    public void validate(Object target, Errors errors) {
        ServizioDTO servizioDTO = (ServizioDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nomeEnte", "servizio.nomeEnte.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nomeDipartimento", "servizio.nomeDipartimento.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nomeServizio", "servizio.nomeServizio.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codiceFiscale", "servizio.codiceFiscale.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "servizio.email.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailPec", "servizio.emailPec.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codiceIdentificativo", "servizio.codiceIdentificativo.empty");

        if (errors.getAllErrors().isEmpty()){
            if (!validate(servizioDTO.getEmail())){
                errors.rejectValue("email","messages.servizio.email.format",null , "");
            }
            if (!validate(servizioDTO.getEmailPec())){
                errors.rejectValue("email","messages.servizio.email.format",null , "");
            }
        }









    }


    public boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

}
