package it.tndigit.iot.service.client;

import org.springframework.web.client.RestClientException;

import java.util.Optional;

public interface ClientHttpAuthService extends ClientHttpService {
    Optional< String > getPublicKey() throws RestClientException;
}
