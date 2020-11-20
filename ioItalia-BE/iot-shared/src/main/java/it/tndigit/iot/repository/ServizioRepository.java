package it.tndigit.iot.repository;

import it.tndigit.iot.domain.ServizioPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ServizioRepository extends JpaRepository< ServizioPO, Long > {
    Optional< ServizioPO > findAllByCodiceIdentificativo(String codiceIdendificativo);
}
