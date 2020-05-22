package it.tndigit.iot.web.validator;

import it.tndigit.iot.common.CodiceFiscaleCheck;
import it.tndigit.iot.common.UtilityIot;
import it.tndigit.iot.service.dto.message.MessageDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class MessageValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return MessageDTO.class.equals(aClass);
    }


    @Override
    public void validate(Object target, Errors errors) {


        MessageDTO messageDTO = (MessageDTO) target;

        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codiceFiscale", "messages.codiceFiscale.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tipoMessage", "messages.tipoMessage.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tipoCryptoMessage", "messages.tipoCryptoMessage.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oggetto", "messages.oggetto.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "testo", "messages.testo.empty");


        /**
         * TODO: Mirko
         *
         * Effettuare la validazione sul tipo di messaggio
         * Se il messaggio è di tipo Email controllo se è presente la mail
         * Se il messaggio è di tipo Telegram controllo se è presente il numero di telefono
         *
         */


        switch (messageDTO.getTipoMessage()){
            case EMAIL:
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "messages.email.empty");
                break;
            case IO_ITALIA:
                //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scadenza", "messages.scadenza.empty");
                //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codiceFiscale", "messages.codiceFiscale.empty");

                if (messageDTO.getPrescriptionDTO()!=null){
                    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "prescriptionDTO.nre", "messages.prescription.nre.empty");
                    if (messageDTO.getPrescriptionDTO().getNre()==null || messageDTO.getPrescriptionDTO().getNre().length()!=15){
                        errors.rejectValue("prescriptionDTO.nre","messages.prescription.nre.size");
                    }
                    if (messageDTO.getPrescriptionDTO().getCodiceFiscaleDottore()!=null
                            && !messageDTO.getPrescriptionDTO().getCodiceFiscaleDottore().isEmpty()){

                         String erroriCF = CodiceFiscaleCheck.isValidCheckSumCF(messageDTO.getPrescriptionDTO().getCodiceFiscaleDottore());
                         if (!erroriCF.isEmpty()){
                            errors.rejectValue("prescriptionDTO.codiceFiscaleDottore",erroriCF);
                        }
                    }

                    if (messageDTO.getPrescriptionDTO().getIup()!=null && !messageDTO.getPrescriptionDTO().getIup().isEmpty() && messageDTO.getPrescriptionDTO().getIup().length()!=10){
                        errors.rejectValue("prescriptionDTO.iup","messages.prescription.iup.size", new Object[]{"10"}, "");


                    }

                }
                if (messageDTO.getPaymentDTO()!=null){
                    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "paymentDTO.importo", "messages.pagamento.importo.empty");
                    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "paymentDTO.numeroAvviso", "messages.pagamento.numeroAvviso.empty");
                    if (messageDTO.getPaymentDTO().getImporto()==null || messageDTO.getPaymentDTO().getImporto()<20){
                        errors.rejectValue("paymentDTO.importo","messages.pagamento.importo.size");
                    }
                    if (errors.getAllErrors().size()==0 && !UtilityIot.checkCodicePagamento(messageDTO.getPaymentDTO().getNumeroAvviso())){
                        errors.rejectValue("paymentDTO.numeroAvviso","messages.pagamento.numeroAvviso.error");

                    }
                }



                break;

        }



    }


    public void validate(Object target, Errors errors, Boolean withPayment) {

        MessageDTO messageDTO = (MessageDTO) target;
        this.validate(target,errors);

        if (withPayment){
            //Inserire la validazione

        }


    }




}
