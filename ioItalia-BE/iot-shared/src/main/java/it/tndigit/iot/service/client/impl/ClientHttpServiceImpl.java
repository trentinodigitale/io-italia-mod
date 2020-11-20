package it.tndigit.iot.service.client.impl;

import it.tndigit.auth.client.invoker.ApiClient;
import it.tndigit.auth.web.controller.GestioneAuthApi;
import it.tndigit.iot.service.client.ClientHttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//@Component
public class ClientHttpServiceImpl implements ClientHttpService {

//    @Value("${server.origin.auth}")
    String basePathAuth;


    @Autowired
    protected GestioneAuthApi gestioneAuthApi;


    protected void configureApiClientAuth(ApiClient apiClient) {
        apiClient.setBasePath(basePathAuth);
    }


    public GestioneAuthApi getAuthRest()  {
        configureApiClientAuth(gestioneAuthApi.getApiClient());
        gestioneAuthApi.getApiClient().setBasePath(basePathAuth);
        return gestioneAuthApi;

    }


}
