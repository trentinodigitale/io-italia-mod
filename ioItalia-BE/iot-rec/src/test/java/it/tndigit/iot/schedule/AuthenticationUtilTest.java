package it.tndigit.iot.schedule;

import it.tndigit.iot.costanti.TipoRuoli;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;


@SpringBootTest
class AuthenticationUtilTest {



    @Test
    void Authentication() {
        AuthenticationUtil.configureAuthentication(TipoRuoli.JOB);
        Collection< SimpleGrantedAuthority > authorities = (Collection< SimpleGrantedAuthority >)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        Assertions.assertTrue(authorities
                .stream()
                .filter(simpleGrantedAuthority -> simpleGrantedAuthority.getAuthority().equals(TipoRuoli.ROLE_JOB.toString()))
                .count()>0L);

        AuthenticationUtil.clearAuthentication();
        Assertions.assertNull(SecurityContextHolder.getContext().getAuthentication());
    }


}