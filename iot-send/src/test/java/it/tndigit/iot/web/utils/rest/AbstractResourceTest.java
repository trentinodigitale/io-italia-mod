package it.tndigit.iot.web.utils.rest;


import it.tndigit.iot.web.utils.errors.ExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import javax.persistence.EntityManager;


public abstract class AbstractResourceTest {


    @Autowired
    protected MessageSource messageSource;

    @Autowired
    protected MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    protected PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    protected ExceptionTranslator exceptionTranslator;


    @Autowired
    protected EntityManager em;

}
