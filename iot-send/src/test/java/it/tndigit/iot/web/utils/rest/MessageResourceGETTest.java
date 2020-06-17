package it.tndigit.iot.web.utils.rest;

import it.tndigit.iot.service.MessageServiceSend;
import it.tndigit.iot.service.ServizioService;
import it.tndigit.iot.utils.MessageBundleBuilder;
import it.tndigit.iot.web.rest.MessageResource;
import it.tndigit.iot.web.validator.MessageValidator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest()
@DisplayName( "Gestione Get Messaggi")
@Transactional
@Slf4j
public class MessageResourceGETTest extends AbstractResourceTest{

    @Autowired
    MessageValidator messageValidator;

    @Autowired
    ServizioService servizioService;

    @Autowired
    MessageBundleBuilder messageBundleBuilder;

    @Autowired
    MessageServiceSend messageService;

    MockMvc restMessageMockMvc;
//
//    @MockBean
//    UtilityIot utilityIot;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MessageResource messageResource = new MessageResource(messageValidator,messageService);
        this.restMessageMockMvc = MockMvcBuilders.standaloneSetup(messageResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setConversionService(TestUtil.createFormattingConversionService())
                .setMessageConverters(jacksonMessageConverter).build();
    }


    @Test
    @Sql(scripts = {"/script/insertServizio.sql","/script/insertMessage.sql"})
    @WithMockUser(username = "asdfkljuoerqewuronr", password = "", roles = "USER")
    void getMessageCodice() throws Exception{
        restMessageMockMvc.perform(get("/api/v1/message/{codiceIdentificativo}/{codiceFiscale}", "dv41f5av54d64sdva6","AAAAAA11A11A111A"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.codiceIdentificativo").value("dv41f5av54d64sdva6"));
    }



    @Test
    @Sql(scripts = {"/script/insertServizio.sql","/script/insertMessage.sql"})
    @WithMockUser(username = "AAAAAA", password = "", roles = "USER")
    void getMessageCodiceException() throws Exception{
        restMessageMockMvc.perform(get("/api/v1/message/{codiceIdentificativo}/{codiceFiscale}", "dv41f5av54d64sdva6","AAAAAA11A11A111A"))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.erroreImprevisto").value(messageBundleBuilder.getMessage("message.get.servizioErrato")));



    }



}
