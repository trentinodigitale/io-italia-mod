package it.tndigit.iot.generate;

import it.tndigit.iot.costanti.TipoCryptoMessage;
import it.tndigit.iot.costanti.TipoMessage;
import it.tndigit.iot.domain.message.MessagePO;
import it.tndigit.iot.domain.message.PaymentPO;
import it.tndigit.iot.domain.message.PrescriptionPO;
import it.tndigit.iot.service.dto.message.MessageDTO;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;


@Service
public class MessageGenerate extends AbstractGenerate< MessagePO, MessageDTO > {

    @Override
    public MessagePO getObjectPO() {
        MessagePO po = applicationContext.getBean(MessagePO.class);
        return getObjectPO(po);
    }

    @Override
    public MessagePO getObjectPO(MessagePO po) {

        if (po == null){
            po = applicationContext.getBean(MessagePO.class);
        }

        po.setCodiceFiscale(RandomStringUtils.randomAlphabetic(16));
        po.setEmail(RandomStringUtils.randomAlphanumeric(10));
        po.setTipoMessage(TipoMessage.IO_ITALIA);
        po.setTipoCryptoMessage(TipoCryptoMessage.NO_CRYPTO);
        po.setExternID(RandomStringUtils.randomAlphanumeric(10));
        po.setOggetto(RandomStringUtils.randomAlphanumeric(90));
        po.setTesto(RandomStringUtils.randomAlphanumeric(1000));
        po.setCodiceIdentificativo(RandomStringUtils.randomAlphanumeric(100));
        po.setTimeToLive(3600);

        return po;
    }


    public MessagePO getObjectPOPayment(MessagePO po) {

        po = this.getObjectPO(po);
        po.setPaymentPO(applicationContext.getBean(PaymentPO.class));
        //deve trasferire un Euro
        po.getPaymentPO().setImporto(100);
        po.getPaymentPO().setNumeroAvviso("092110723176061487");
        po.getPaymentPO().setInvalid_after_due_date(Boolean.TRUE);



        return po;
    }

    public MessagePO getObjectPOPrescription(MessagePO po) {

        po = this.getObjectPO(po);
        po.setTipoCryptoMessage(TipoCryptoMessage.CRYPTO);
        po.setPrescriptionPO(applicationContext.getBean(PrescriptionPO.class));
        //deve trasferire un Euro
        po.getPrescriptionPO().setCodiceFiscaleDottore("TRTVVD80A01L378N");
        po.getPrescriptionPO().setNre("AAAAAAAAAAAAAAA");
        po.getPrescriptionPO().setIup("AAAAAAAAAA");



        return po;
    }

    @Override
    public MessageDTO getObjectDTO(MessageDTO dto) {
        return null;
    }


}
