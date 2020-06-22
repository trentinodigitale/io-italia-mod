package it.tndigit.iot.service;

import it.tndigit.iot.service.dto.ServizioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ServizioService {


    /**
     * Save a EnteDTO.
     *
     * @param enteDTO the entity to save
     * @return the persisted entity
     */

    ServizioDTO save(ServizioDTO enteDTO);


    /**
     * Get the "id" ente.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional< ServizioDTO > findOne(String id);


    /**
     * Get the "id" ente.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Page< ServizioDTO > findAll(Pageable paging );


    /**
     * Delete the "id" ente.
     *
     * @param id the id of the entity
     */
    void delete(Long id);


}
