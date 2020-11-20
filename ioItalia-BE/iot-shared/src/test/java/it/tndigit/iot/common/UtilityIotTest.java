package it.tndigit.iot.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class UtilityIotTest {


    @Test
    void checkCodicePagamento() {
        Assertions.assertTrue(UtilityIot.checkCodicePagamento("367058651934797158"),"Codice Pagamento Corretto");
        Assertions.assertFalse(UtilityIot.checkCodicePagamento("36705865asdfas797158"),"Codice Pagamento Errato");
    }


    @Test
    void checkCodiceFiscale() {
        Assertions.assertTrue(CodiceFiscaleCheck.isValidCheckSumCF("GGNPLT80A01L378N").length()==0,"Codice Pagamento Corretto");
        Assertions.assertTrue(CodiceFiscaleCheck.isValidCheckSumCF("TRTVVD80A01L378N").length()==0,"Codice Pagamento Corretto");

        String errore = CodiceFiscaleCheck.isValidCheckSumCF("TRTVVD80A01L378J");
        Assertions.assertFalse(errore.length()==0,errore);

        errore = CodiceFiscaleCheck.isValidCheckSumCF("TRTVVDAA0A01L378J");
        Assertions.assertFalse(errore.length()==0,errore);


    }
}