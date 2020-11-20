package it.tndigit.iot.web.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.tndigit.iot.service.ScheduledService;
import it.tndigit.iot.web.rest.utils.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api( value="API SCHEDULAZIONE",
        tags = "GESTIONE SCHEDULAZIONE")
public class ScheduledResource {


    private static final String ENTITY_NAME = "messageDTO";


    private ScheduledService scheduledService;

    public ScheduledResource(ScheduledService scheduledService) {
        this.scheduledService = scheduledService;
    }

    @GetMapping("/scheduled/check-ioitalia")
    @ApiOperation("Crea un nuovo messaggio dato un codicefiscale")
    public ResponseEntity< Boolean > invokeScheduled() {

        scheduledService.timerCheckIoItalia();

        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, "" ))
                .body(Boolean.TRUE);

    }



}