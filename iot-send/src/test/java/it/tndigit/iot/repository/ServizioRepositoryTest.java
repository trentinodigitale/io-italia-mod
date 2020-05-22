package it.tndigit.iot.repository;

import it.tndigit.iot.domain.ServizioPO;
import it.tndigit.iot.generate.ServizioGenerate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ServizioRepositoryTest {


    @Autowired
    private ServizioRepository servizioRepository;

    @Autowired
    private ServizioGenerate servizioGenerate;


    @Test
    void findByCodiceIdentificativo() {
       ServizioPO servizioPO = servizioRepository.save(servizioGenerate.getObjectPO());
       Optional< ServizioPO > servizioPOOptional = servizioRepository.findAllByCodiceIdentificativo(servizioPO.getCodiceIdentificativo());
       assertTrue(servizioPOOptional.isPresent());

    }
}