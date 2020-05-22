package it.tndigit.iot.repository;

import it.tndigit.iot.costanti.TipoMessage;
import it.tndigit.iot.domain.message.MessagePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MessageRepository extends JpaRepository< MessagePO, Long > {
    Optional< MessagePO > findByIdObjAndAndCodiceFiscaleAndExternIDIsNotNull (Long idObj, String codiceFiscale);
    Optional< MessagePO > findByIdObjAndAndCodiceFiscale (Long idObj, String codiceFiscale);
    Optional< MessagePO > findByCodiceIdentificativoAndCodiceFiscale(String codiceIdentificativo, String codiceFiscale);

    List< MessagePO > findByTipoMessageAndExternIDNotNull(TipoMessage tipoMessage);

//    @Query("SELECT messaggio FROM MessagePO messaggio"
//          +  " WHERE messaggio.tipoMessage = 'IO_ITALIA' " +
//            "  AND messaggio.externID IS NOT NULL " +
//            "  AND messaggio.idObj NOT IN (SELECT distinct notify.messagePO.idObj FROM NotificationPO notify WHERE notify.status <> 'ACCEPTED')")


    @Query(value = "select * from iottmessage msg " +
            " LEFT OUTER JOIN ( " +
            "       SELECT  idmessage, max(ultimotentativo), max(status) as statusMax from iottnotification " +
            " left outer join iottmessage msg on msg.id_obj = idmessage " +
            " where msg.tipomessaggio like 'IO_ITALIA' " +
            " and msg.idexternal is not null " +
            " group by idmessage )" +
            " as notifiche on notifiche.idmessage=msg.id_obj " +
            " where (notifiche.statusMax <> 'PROCESSED' or notifiche.statusMax is null) " +
            " and msg.tipomessaggio like 'IO_ITALIA' " +
            " and msg.idexternal is not null", nativeQuery = true)
    List< MessagePO > findMessageForCheck();


}
