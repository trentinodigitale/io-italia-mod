package it.tndigit.iot.web.rest;

import it.tndigit.ioitalia.client.invoker.ApiClient;
import it.tndigit.ioitalia.service.dto.InlineResponse201;
import it.tndigit.ioitalia.service.dto.LimitedProfile;
import it.tndigit.ioitalia.web.rest.DefaultApi;
import it.tndigit.iot.domain.ServizioPO;
import it.tndigit.iot.domain.message.MessagePO;
import it.tndigit.iot.generate.MessageGenerate;
import it.tndigit.iot.generate.ServizioGenerate;
import it.tndigit.iot.repository.MessageRepository;
import it.tndigit.iot.repository.ServizioRepository;
import it.tndigit.iot.service.MessageServiceSend;
import it.tndigit.iot.service.dto.message.MessageDTO;
import it.tndigit.iot.service.mapper.MessageMapper;
import it.tndigit.iot.web.validator.MessageValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest()
@DisplayName( "Gestione Invio Messaggi")
@Transactional
@Slf4j
@PropertySource("classpath:message.properties")
public class MessageResourceTest extends AbstractResourceTest{


    @Autowired
    MessageServiceSend messageService;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    MessageMapper messageMapper;


    @Autowired
    ServizioRepository servizioRepository;

    @Autowired
    MessageValidator messageValidator;

    @Autowired
    MessageGenerate messageGenerate;

    @Autowired
    ServizioGenerate servizioGenerate;

    @MockBean
    DefaultApi defaultApi;

    MockMvc restMessageMockMvc;

    @Autowired
    ServizioPO servizioPO;


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


    @BeforeEach
    public void initTest() {
        servizioRepository.deleteAll();
        ServizioPO servizioPO = servizioGenerate.getObjectPO();
        servizioPO.setEmailPec("aaa@aaa.it");
        servizioPO.setCodiceIdentificativo("AAABBBCCC");
        servizioRepository.saveAndFlush(servizioPO);

        Mockito.when(defaultApi.getApiClient()).thenReturn(new ApiClient());
        LimitedProfile limitedProfile = new LimitedProfile();
        limitedProfile.setSenderAllowed(true);
        Mockito.when(defaultApi.getProfile(Mockito.any())).thenReturn(limitedProfile);

        InlineResponse201 inlineResponse201 = new InlineResponse201();
        inlineResponse201.setId(RandomStringUtils.randomAlphanumeric(20));
        Mockito.when(defaultApi.submitMessageforUser(Mockito.any(),Mockito.any())).thenReturn(inlineResponse201);

    }



    @Test
    @WithMockUser(username = "AAABBBCCC",roles = "USER")
    @DisplayName("Creazione messagggio Normale")
    @Transactional
    public void createMessage() throws Exception {
        messageRepository.deleteAll();

        MessagePO messagePO = messageGenerate.getObjectPO(new MessagePO());

        int databaseSizeBeforeCreate = messageRepository.findAll().size();

        // Create the Area
        MessageDTO messageDTO = messageMapper.toDto(messagePO);

        restMessageMockMvc.perform(post("/api/v1/message/{codiceFiscale}",messageDTO.getCodiceFiscale())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codiceIdentificativo").isNotEmpty());

        // Validate the Area in the database
        List< MessagePO > messagePOList = messageRepository.findAll();
        assertThat(messagePOList).hasSize(databaseSizeBeforeCreate + 1);
        MessagePO testMessage = messagePOList.get(messagePOList.size() - 1);
        assertThat(testMessage.getCodiceFiscale()).isEqualTo(messagePO.getCodiceFiscale());
        assertThat(testMessage.getEmail()).isEqualTo(messagePO.getEmail());
        assertThat(testMessage.getScadenza()).isEqualTo(messagePO.getScadenza());
        assertThat(testMessage.getExternID()).isEqualTo(messagePO.getExternID());
        assertThat(testMessage.getOggetto()).isEqualTo(messagePO.getOggetto());
        assertThat(testMessage.getTesto()).isEqualTo(messagePO.getTesto());
        assertThat(testMessage.getTipoCryptoMessage()).isEqualTo(messagePO.getTipoCryptoMessage());
        assertThat(testMessage.getCodiceIdentificativo()).isNotEmpty();
    }


    @Test
    @WithMockUser(username = "AAABBBCCC",roles = "USER")
    @DisplayName("Creazione messagggio con pagamento")
    @Transactional
    public void createMessagePayment() throws Exception {
        messageRepository.deleteAll();
        MessagePO messagePO = messageGenerate.getObjectPOPayment(new MessagePO());

        int databaseSizeBeforeCreate = messageRepository.findAll().size();

        // Create the Area
        MessageDTO messageDTO = messageMapper.toDto(messagePO);

        restMessageMockMvc.perform(post("/api/v1/message/{codiceFiscale}",messageDTO.getCodiceFiscale())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codiceIdentificativo").isNotEmpty());

        // Validate the Area in the database
        List< MessagePO > messagePOList = messageRepository.findAll();
        assertThat(messagePOList).hasSize(databaseSizeBeforeCreate + 1);
        MessagePO testMessage = messagePOList.get(messagePOList.size() - 1);
        assertNotNull(testMessage.getPaymentPO());

    }

    @Test
    @WithMockUser(username = "AAABBBCCC",roles = "USER")
    @DisplayName("Creazione messagggio con pagamento a importo 0")
    @Transactional

    public void createMessagePaymentError() throws Exception {
        messageRepository.deleteAll();
        MessagePO messagePO = messageGenerate.getObjectPOPayment(new MessagePO());

        int databaseSizeBeforeCreate = messageRepository.findAll().size();
        MessageDTO messageDTO = messageMapper.toDto(messagePO);
        messageDTO.getPaymentDTO().setImporto(0);

        restMessageMockMvc.perform(post("/api/v1/message/{codiceFiscale}",messageDTO.getCodiceFiscale())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.erroreImprevisto").isNotEmpty());

    }

    @Test
    @WithMockUser(username = "AAABBBCCC",roles = "USER")
    @DisplayName("Creazione messagggio con pagamento con numero Avviso errato")
    @Transactional

    public void createMessagePaymentAvvisoError() throws Exception {
        messageRepository.deleteAll();
        MessagePO messagePO = messageGenerate.getObjectPOPayment(new MessagePO());

        int databaseSizeBeforeCreate = messageRepository.findAll().size();
        MessageDTO messageDTO = messageMapper.toDto(messagePO);
        messageDTO.getPaymentDTO().setNumeroAvviso("456dasf");

        restMessageMockMvc.perform(post("/api/v1/message/{codiceFiscale}",messageDTO.getCodiceFiscale())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.erroreImprevisto").isNotEmpty());

    }


    @Test
    @DisplayName("Creazione messagggio Senza Servizio")
    public void createMessageWithOutServizio() throws Exception {

        MessagePO messagePO = messageGenerate.getObjectPO(new MessagePO());

        int databaseSizeBeforeCreate = messageRepository.findAll().size();
        // Create the Area
        MessageDTO messageDTO = messageMapper.toDto(messagePO);
        restMessageMockMvc.perform(post("/api/v1/message/{codiceFiscale}", messageDTO.getCodiceFiscale())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{'message':'Bad Request: Impossibile inviare il messaggio, Servizio NON presente'}"));
    }



    @Test
    @WithMockUser(username = "AAABBBCCC",roles = "USER")
    @DisplayName("Creazione messagggio con Prescrizione")
    @Transactional
    public void createMessagePrescription() throws Exception {
        messageRepository.deleteAll();
        MessagePO messagePO = messageGenerate.getObjectPOPrescription(new MessagePO());
        int databaseSizeBeforeCreate = messageRepository.findAll().size();
        MessageDTO messageDTO = messageMapper.toDto(messagePO);

        messagePO.getPrescriptionPO().setIup("");

        restMessageMockMvc.perform(post("/api/v1/message/{codiceFiscale}",messageDTO.getCodiceFiscale())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
                .andExpect(status().isCreated());

        // Validate the Area in the database
        List< MessagePO > messagePOList = messageRepository.findAll();
        assertThat(messagePOList).hasSize(databaseSizeBeforeCreate + 1);
        MessagePO testMessage = messagePOList.get(messagePOList.size() - 1);
        assertNotNull(testMessage.getPrescriptionPO());

    }
    @Test
    @WithMockUser(username = "AAABBBCCC",roles = "USER")
    @DisplayName("Creazione messagggio con Prescrizione")
    @Transactional
    public void createMessagePrescriptionErrorNRE() throws Exception {
        messageRepository.deleteAll();
        MessagePO messagePO = messageGenerate.getObjectPOPrescription(new MessagePO());

        messagePO.getPrescriptionPO().setNre("aa");
        int databaseSizeBeforeCreate = messageRepository.findAll().size();
        MessageDTO messageDTO = messageMapper.toDto(messagePO);
        restMessageMockMvc.perform(post("/api/v1/message/{codiceFiscale}",messageDTO.getCodiceFiscale())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.erroreImprevisto").isNotEmpty());

    }

    @Test
    @WithMockUser(username = "AAABBBCCC",roles = "USER")
    @DisplayName("Creazione messagggio con Prescrizione")
    @Transactional
    public void createMessagePrescriptionErrorIUP() throws Exception {
        messageRepository.deleteAll();
        MessagePO messagePO = messageGenerate.getObjectPOPrescription(new MessagePO());

        messagePO.getPrescriptionPO().setIup("aa");
        int databaseSizeBeforeCreate = messageRepository.findAll().size();
        MessageDTO messageDTO = messageMapper.toDto(messagePO);
        restMessageMockMvc.perform(post("/api/v1/message/{codiceFiscale}",messageDTO.getCodiceFiscale())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.erroreImprevisto").isNotEmpty());

    }



    @Test
    @WithMockUser(username = "AAABBBCCC",roles = "USER")
    @DisplayName("Creazione messagggio con con Oggetto Corto")
    @Transactional
    public void createMessageErrorOggetto() throws Exception {
        messageRepository.deleteAll();
        MessagePO messagePO = messageGenerate.getObjectPOPrescription(new MessagePO());
        messagePO.setOggetto("aaa");
        int databaseSizeBeforeCreate = messageRepository.findAll().size();
        MessageDTO messageDTO = messageMapper.toDto(messagePO);
        restMessageMockMvc.perform(post("/api/v1/message/{codiceFiscale}",messageDTO.getCodiceFiscale())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.erroreImprevisto").isNotEmpty());

    }

    @Test
    @WithMockUser(username = "AAABBBCCC",roles = "USER")
    @DisplayName("Creazione messaggio con con Oggetto Non corretto")
    @Transactional
    public void createMessagePrescriptionErrorOggetto() throws Exception {
        messageRepository.deleteAll();
        MessagePO messagePO = messageGenerate.getObjectPOPrescription(new MessagePO());
        messagePO.setOggetto(RandomStringUtils.randomAlphanumeric(9));

        MessageDTO messageDTO = messageMapper.toDto(messagePO);
        restMessageMockMvc.perform(post("/api/v1/message/{codiceFiscale}",messageDTO.getCodiceFiscale())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.erroreImprevisto").isNotEmpty());

        messagePO.setOggetto(RandomStringUtils.randomAlphanumeric(121));
        messageDTO = messageMapper.toDto(messagePO);
        restMessageMockMvc.perform(post("/api/v1/message/{codiceFiscale}",messageDTO.getCodiceFiscale())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.erroreImprevisto").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "AAABBBCCC",roles = "USER")
    @DisplayName("Creazione messaggio con con Testo Non corretto")
    @Transactional
    public void createMessageErrorTesto() throws Exception {
        messageRepository.deleteAll();
        MessagePO messagePO = messageGenerate.getObjectPOPrescription(new MessagePO());
        messagePO.setTesto(RandomStringUtils.randomAlphanumeric(79));

        MessageDTO messageDTO = messageMapper.toDto(messagePO);
        restMessageMockMvc.perform(post("/api/v1/message/{codiceFiscale}",messageDTO.getCodiceFiscale())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.erroreImprevisto").isNotEmpty());

        messagePO.setTesto(RandomStringUtils.randomAlphanumeric(10001));
        messageDTO = messageMapper.toDto(messagePO);
        restMessageMockMvc.perform(post("/api/v1/message/{codiceFiscale}",messageDTO.getCodiceFiscale())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.erroreImprevisto").isNotEmpty());
    }


}
