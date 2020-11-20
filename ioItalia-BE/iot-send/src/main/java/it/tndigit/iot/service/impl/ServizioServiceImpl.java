package it.tndigit.iot.service.impl;

import it.tndigit.iot.domain.ServizioPO;
import it.tndigit.iot.exception.IotException;
import it.tndigit.iot.repository.ServizioRepository;
import it.tndigit.iot.service.ServizioService;
import it.tndigit.iot.service.dto.ServizioDTO;
import it.tndigit.iot.service.mapper.ServizioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServizioServiceImpl implements ServizioService {


    @Autowired
    private ServizioMapper enteMapper;

    @Autowired
    private ServizioRepository servizioRepository;



    @Override
    @Transactional
    public ServizioDTO save(ServizioDTO enteDTO) {

        try{
            ServizioPO servizioPO = enteMapper.toEntity(enteDTO);
//            if (servizioPO.getIdObj()==null && (enteDTO.getCodiceIdentificativo()==null || enteDTO.getCodiceIdentificativo().isEmpty())){
//                String digestString = enteDTO.getNomeServizio() + LocalDate.now().toString();
//
//                servizioPO.setCodiceIdentificativo(getCodIdentificativo(digestString)) ;
//
//            }
            servizioPO = servizioRepository.saveAndFlush(servizioPO);
            return enteMapper.toDto(servizioPO);
        }catch (DataIntegrityViolationException diEx){
            throw  new IotException("Servizio" , "Servizio già presente " + diEx.getMessage());
        }catch (Exception ex){
            throw  new IotException(ex.getMessage());

        }



    }

//    /**
//     * Get one Ente by id.
//     *
//     * @param id the id of the entity
//     * @return the entity
//     */
//    @Override
//    @Transactional(readOnly = true)
//    public Optional<ServizioDTO> findOne(Long id) {
//        Optional< ServizioPO > entePO = servizioRepository.findById(id);
//
//        if (entePO.isPresent()){
//            ServizioDTO enteDTO = enteMapper.toDto(entePO.get());
//            return Optional.of(enteDTO);
//        }
//        return Optional.empty();
//    }



    /**
     * Get one Ente by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServizioDTO> findOne(String codIdentificativo) {
        Optional< ServizioPO > entePO = servizioRepository.findAllByCodiceIdentificativo(codIdentificativo);

        if (entePO.isPresent()){
            ServizioDTO enteDTO = enteMapper.toDto(entePO.get());
            return Optional.of(enteDTO);
        }
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        servizioRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page< ServizioDTO > findAll(Pageable paging) {
        Page<ServizioDTO> servizioPOPage = servizioRepository.findAll(paging).map(enteMapper::toDto);
        return servizioPOPage;

    }


    //    protected String getCodIdentificativo (String valore ){
//        return DigestUtils.md5DigestAsHex(valore.getBytes());
//    }
}
