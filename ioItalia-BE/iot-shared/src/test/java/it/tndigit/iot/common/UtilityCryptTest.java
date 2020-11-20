package it.tndigit.iot.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;


class UtilityCryptTest {

    private static final String VALORE = "Lo zio paperino";
    private static final String VALORE_CRYPT = "5wcnu/lRT++cfToCW/8afX1kKaDuIzHNocrhIK26/g==";

    @Autowired
    UtilityCrypt utilityCrypt ;

    @BeforeEach
    void before(){
        utilityCrypt= new UtilityCrypt();
        utilityCrypt.setSalt("RrPdwdiAsDAOqJdDHSO7");
        utilityCrypt.setSecretKey("ICmpuCr4PGYJ2nsKqp3y");
    }

    @Test
    void encrypt() {
        String valoreCryptato = utilityCrypt.encrypt(VALORE);
        System.out.println(valoreCryptato);
        assertEquals(VALORE_CRYPT,valoreCryptato);
    }



    @Test
    void decrypt() {
        String valoreCryptato = utilityCrypt.decrypt(VALORE_CRYPT);
        assertEquals(valoreCryptato,VALORE);


    }




}