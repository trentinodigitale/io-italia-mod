package it.tndigit.iot.web.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import it.tndigit.iot.exception.IotException;
import it.tndigit.iot.service.ServizioService;
import it.tndigit.iot.service.dto.ServizioDTO;
import it.tndigit.iot.web.rest.utils.HeaderUtil;
import it.tndigit.iot.web.validator.ServizioValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * REST controller for managing Servizio.
 */
@RestController
@SwaggerDefinition()
@Api( value="API Servizio",
        tags = "GESTIONE SERVIZIO")

public class ServizioResource extends AbstractResource {

    private final Logger log = LoggerFactory.getLogger(ServizioResource.class);

    private static final String ENTITY_NAME = "ServizioPO";


    private final ServizioService servizioService;

    private final ServizioValidator servizioValidator;

    public ServizioResource(ServizioService ServizioService, ServizioValidator ServizioValidator) {

        this.servizioService = ServizioService;
        this.servizioValidator = ServizioValidator;
    }

    /**
     * POST  /servizio : Create un nuovo Servizio.
     *
     * @param servizioDTO the ServizioDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new attoDTO, or with status 400 (Bad Request) if the area has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/servizio")
    @ApiOperation("Inserisce un nuovo Servizio")
    public ResponseEntity< ServizioDTO > createServizio(@RequestBody ServizioDTO servizioDTO,
                                                        BindingResult bindingResult,
                                                        HttpServletResponse response){
        if (servizioDTO.getIdObj() != null) {
            servizioDTO.setErroreImprevisto("L'identificativo NON deve presente in fase di inseriento servizio");
            return new ResponseEntity<>(servizioDTO, HttpStatus.NOT_ACCEPTABLE);
        }

        servizioValidator.validate(servizioDTO,bindingResult);
        if (bindingResult.getErrorCount()>0){
            servizioDTO.setErroreImprevisto(bindingResult.getAllErrors().get(0).toString());
            return new ResponseEntity<>(servizioDTO, HttpStatus.NOT_ACCEPTABLE);
        }

        try{
            ServizioDTO result = servizioService.save(servizioDTO);
            return ResponseEntity.created(new URI("/v1/api/servizio/" + result.getIdObj()))
                    .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getIdObj().toString()))
                    .body(result);
        }catch (IotException e){
            servizioDTO.setErroreImprevisto(e.getMessage());
            return new ResponseEntity<>(servizioDTO, HttpStatus.UNPROCESSABLE_ENTITY);
        }catch (AccessDeniedException ex){
            return new ResponseEntity<>(servizioDTO, HttpStatus.FORBIDDEN);
        }
        catch (Exception ex){
            servizioDTO.setErroreImprevisto(ex.getMessage());
            return new ResponseEntity<>(servizioDTO, HttpStatus.NOT_ACCEPTABLE);

        }
    }

    /**
     * GET  /servizio/:id : get the "id" Servizio.
     *
     * @param idObj the id of the ServizioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the areaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/servizio/{idObj}")
    @ApiOperation("Ritorna un Servizio dato un determinato idObj")
    public ResponseEntity< ServizioDTO > getServizio(@PathVariable Long idObj) {
        log.debug("REST request to get Area : {}", idObj);

        Optional< ServizioDTO > ServizioDTO = servizioService.findOne(idObj);
        if (ServizioDTO.isPresent()) {
            return new ResponseEntity<>(ServizioDTO.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);


    }


    /**
     * PUT  /servizio : Updates an existing atto.
     *
     * @param ServizioDTO the ServizioDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated attoDTO,
     * or with status 400 (Bad Request) if the areaDTO is not valid,
     * or with status 500 (Internal Server Error) if the areaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/servizio")
    @ApiOperation("Aggiorna un Servizio esistServizio")
    public ResponseEntity< ServizioDTO > updateServizio(@RequestBody ServizioDTO ServizioDTO, BindingResult bindingResult) throws URISyntaxException {
        if (ServizioDTO.getIdObj() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        servizioValidator.validate(ServizioDTO,bindingResult);

        if (bindingResult.getErrorCount()>0){
            ServizioDTO.setErroreImprevisto(bindingResult.getAllErrors().get(0).toString());
            return new ResponseEntity<>(ServizioDTO, HttpStatus.NOT_ACCEPTABLE);
        }

        ServizioDTO = servizioService.save(ServizioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ServizioDTO.getIdObj().toString()))
            .body(ServizioDTO);
    }


    /**
     * DELETE  /servizio/:id : delete the "id" area.
     *
     * @param idObj the id of the areaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/servizio/{idObj}")
    @ApiOperation("Cancella un Servizio dato un determinato idObj")
    public ResponseEntity< Void > deleteServizio(@PathVariable Long idObj) {
        servizioService.delete(idObj);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, idObj.toString())).build();
    }
}
