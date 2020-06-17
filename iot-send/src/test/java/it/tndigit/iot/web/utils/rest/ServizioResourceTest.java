package it.tndigit.iot.web.utils.rest;

import it.tndigit.iot.domain.ServizioPO;
import it.tndigit.iot.generate.ServizioGenerate;
import it.tndigit.iot.repository.ServizioRepository;
import it.tndigit.iot.service.ServizioService;
import it.tndigit.iot.service.dto.ServizioDTO;
import it.tndigit.iot.service.mapper.ServizioMapper;
import it.tndigit.iot.web.rest.ServizioResource;
import it.tndigit.iot.web.validator.ServizioValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@DisplayName("Gestione Servizio")
public class ServizioResourceTest extends AbstractResourceTest{

    @Autowired
    private ServizioService servizioService;

    @Autowired
    private ServizioValidator servizioValidator;

    @Autowired
    private ServizioRepository servizioRepository;

    @Autowired
    private ServizioMapper enteMapper;

    @Autowired
    private ServizioGenerate servizioGenerate;

    private MockMvc restServizioMockMvc;

    private ServizioPO servizioPO;



    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServizioResource areaResource = new ServizioResource(servizioService, servizioValidator);
        this.restServizioMockMvc = MockMvcBuilders.standaloneSetup(areaResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setConversionService(TestUtil.createFormattingConversionService())
                .setMessageConverters(jacksonMessageConverter).build();
    }

    @Test
    @Transactional
    @DisplayName("Creazione Servizio")
    public void createServizio()  throws  Exception{

        servizioRepository.deleteAll();
        int databaseSizeBeforeCreate = servizioRepository.findAll().size();

        // Create Servizio
        servizioPO =  servizioGenerate.getObjectPO(new ServizioPO());
        ServizioDTO enteDTO = enteMapper.toDto(servizioPO);
        restServizioMockMvc.perform(post("/api/v1/servizio")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(enteDTO)))
                .andExpect(status().isCreated());

        // Validate the Area in the database
        List< ServizioPO > enteList = servizioRepository.findAll();
        assertThat(enteList).hasSize(databaseSizeBeforeCreate + 1);
        ServizioPO testEnte = enteList.get(enteList.size() - 1);
        assertThat(testEnte.getCodiceFiscale()).isEqualTo(servizioPO.getCodiceFiscale());
        assertThat(testEnte.getEmail()).isEqualTo(servizioPO.getEmail());
        assertThat(testEnte.getEmailPec()).isEqualTo(servizioPO.getEmailPec());
        assertThat(testEnte.getNomeDipartimento()).isEqualTo(servizioPO.getNomeDipartimento());
        assertThat(testEnte.getNomeEnte()).isEqualTo(servizioPO.getNomeEnte());
        assertThat(testEnte.getTokenIoItalia()).isEqualTo(servizioPO.getTokenIoItalia());
        assertThat(testEnte.getCodiceIdentificativo()).isEqualTo(servizioPO.getCodiceIdentificativo());
        assertThat(testEnte.getIdObj()).isGreaterThan(0L);
        Long idObjfirst = testEnte.getIdObj();


        ServizioPO servizioPO_2 = servizioGenerate.getObjectPO(new ServizioPO());
        ServizioDTO enteDTO_2 = enteMapper.toDto(servizioPO_2);
        enteDTO_2.setCodiceIdentificativo(enteDTO.getCodiceIdentificativo());
        //Controllo che non possano esserci due servizi con lo stesso codice



        restServizioMockMvc.perform(post("/api/v1/servizio")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(enteDTO_2)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.erroreImprevisto").isNotEmpty());

    }



    @Test
    @Transactional
    @DisplayName("Creazione Servizio con IDOBJ")
    public void createServizioWithIdObj()  throws  Exception {

        servizioRepository.deleteAll();
        int databaseSizeBeforeCreate = servizioRepository.findAll().size();

        servizioPO =  servizioGenerate.getObjectPO(new ServizioPO());
        ServizioDTO enteDTO = enteMapper.toDto(servizioPO);
        enteDTO.setIdObj(1234L);

        restServizioMockMvc.perform(post("/api/v1/servizio")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(enteDTO)))
                .andExpect(status().isNotAcceptable());
    }


    @Test
    @Transactional
    @DisplayName("Creazione Servizio no Validate")
    public void createServizioNoValidate()  throws  Exception {

        servizioRepository.deleteAll();
        servizioPO =  servizioGenerate.getObjectPO(new ServizioPO());
        servizioPO.setNomeServizio("");
        ServizioDTO enteDTO = enteMapper.toDto(servizioPO);
        enteDTO.setIdObj(1234L);

        restServizioMockMvc.perform(post("/api/v1/servizio")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(enteDTO)))
                .andExpect(status().isNotAcceptable());
    }




    @Test
    @Transactional
    @DisplayName("Creazione Servizio Multiplo")
    public void createServizioSequence()  throws  Exception{

        servizioRepository.deleteAll();
        int databaseSizeBeforeCreate = servizioRepository.findAll().size();
        // Create Servizio
        servizioPO =  servizioGenerate.getObjectPO(new ServizioPO());
        ServizioDTO enteDTO = enteMapper.toDto(servizioPO);
        restServizioMockMvc.perform(post("/api/v1/servizio")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(enteDTO)))
                .andExpect(status().isCreated());

        // Validate the Area in the database
        List< ServizioPO > enteList = servizioRepository.findAll();
        assertThat(enteList).hasSize(databaseSizeBeforeCreate + 1);
        ServizioPO testEnte = enteList.get(enteList.size() - 1);
        Long idObjfirst = testEnte.getIdObj();


        servizioPO =  servizioGenerate.getObjectPO(new ServizioPO());


        ServizioDTO servizioDTO = enteMapper.toDto(servizioPO);
        restServizioMockMvc.perform(post("/api/v1/servizio")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(servizioDTO)))
                .andExpect(status().isCreated());

        enteList = servizioRepository.findAll();
        assertThat(enteList).hasSize(databaseSizeBeforeCreate + 2);
        ServizioPO testEnteTwo = enteList.get(enteList.size() - 1);
        assertEquals(testEnteTwo.getIdObj(),idObjfirst+1);

    }


    @Test
    @DisplayName("Get Servizio")
    @Transactional
    @Sql(scripts = {"/script/insertServizio.sql"})
    public void getServizio ()throws Exception{
        restServizioMockMvc.perform(get("/api/v1/servizio/{codIdentificativo}", "asdfkljuoerqewuronr"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.codiceIdentificativo").value("asdfkljuoerqewuronr"));

        restServizioMockMvc.perform(get("/api/v1/servizio/{idObj}", 20000L))
                .andExpect(status().isNoContent());

    }

    @Test
    @DisplayName("Update Servizio")
    @Transactional
    @Sql(scripts = {"/script/insertServizio.sql"})
    public void updateServizio ()throws Exception{

        Optional< ServizioPO > servizioPOOptional = servizioRepository.findAll().stream().findFirst();
        ServizioDTO servizioDTO = enteMapper.toDto(servizioPOOptional.get());
        servizioDTO.setEmail(ServizioGenerate.EMAIL);

        restServizioMockMvc.perform(put("/api/v1/servizio")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(servizioDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(ServizioGenerate.EMAIL));



    }




    @Test
    @DisplayName("Delete Servizio")
    public void deleteServizio()throws  Exception {

        ServizioPO servizioPODelete = servizioRepository.saveAndFlush(servizioGenerate.getObjectPO());
        int databaseSizeBeforeDelete = servizioRepository.findAll().size();

        restServizioMockMvc.perform(delete("/api/v1/servizio/{idObj}",servizioPODelete.getIdObj())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        List< ServizioPO > servizioPOS = servizioRepository.findAll();
        assertThat(servizioPOS).hasSize(databaseSizeBeforeDelete -1);



    }
}
