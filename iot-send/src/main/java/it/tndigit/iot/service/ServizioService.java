package it.tndigit.iot.service;

import it.tndigit.iot.service.dto.ServizioDTO;

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
    Optional< ServizioDTO > findOne(Long id);

    /**
     * Delete the "id" ente.
     *
     * @param id the id of the entity
     */
    void delete(Long id);


}
