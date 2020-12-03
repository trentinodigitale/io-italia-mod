package it.tndigit.iot.repository;


import it.tndigit.iot.domain.UtenteAbilitatoPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteAbilitatoRepository extends JpaRepository<UtenteAbilitatoPO, Long > {
    Optional<UtenteAbilitatoPO> findByEmailAndCodiceIdentificativo(String email, String codiceIdendificativo);
}
