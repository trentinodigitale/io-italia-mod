package it.tndigit.iot.generate;

import it.tndigit.iot.service.dto.CommonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public abstract class AbstractGenerate <T extends Object, D extends CommonDTO >{

    @Autowired
    protected ApplicationContext applicationContext;


    public abstract T getObjectPO();

    public abstract T getObjectPO(T po);

    public abstract D getObjectDTO(D dto);



}
