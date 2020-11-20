package it.tndigit.iot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest()
@DisplayName( "Gestione Servizio")
@Transactional
@Slf4j
class ServizioServiceImplTest {

    @Autowired
    ServizioServiceImpl servizioService;

//    @Test
//    void getCodIdentificativo() {
//
//        String valore = "String di prova per test" + LocalDate.now();
//        Assertions.assertNotNull(servizioService.getCodIdentificativo(valore));
//
//    }
//
//        @Test
//    void getCodIdentificativo() {
//
//        String valore = "String di prova per test" + LocalDate.now();
//        Assertions.assertNotNull(servizioService.getCodIdentificativo(valore));
//
//    }
//
//
}