package it.tndigit.iot.generate;

import it.tndigit.iot.costanti.TipoStatus;
import it.tndigit.iot.domain.message.NotificationPO;
import it.tndigit.iot.service.dto.message.NotificationDTO;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class NotificationGenerate extends AbstractGenerate< NotificationPO, NotificationDTO > {

    @Override
    public NotificationPO getObjectPO() {
        NotificationPO po = applicationContext.getBean(NotificationPO.class);
        return getObjectPO(po);
    }

    @Override
    public NotificationPO getObjectPO(NotificationPO po) {

        if (po == null){
            po = applicationContext.getBean(NotificationPO.class);
        }

        po.setNote(RandomStringUtils.randomAlphabetic(100));
        po.setEmailNotification(TipoStatus.SENT);
        po.setWebhookNotification(TipoStatus.SENT);
        po.setLastChance(LocalDateTime.now());
        po.setStatus(TipoStatus.PROCESSED);


        return po;
    }

    @Override
    public NotificationDTO getObjectDTO(NotificationDTO dto) {
        return null;
    }


}
