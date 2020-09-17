CREATE SCHEMA suiot01;

CREATE SEQUENCE suiot01.nxtnbr
    NO MINVALUE
    NO MAXVALUE;

CREATE TABLE suiot01.iottservizio (
              id_obj int8 NOT NULL,
              datainserimento timestamp NULL,
              datamodifica timestamp NULL,
              utenteinserimento varchar(100) NOT NULL,
              utentemodifica varchar(100) NULL,
              "version" int4 NOT NULL,
              codicefiscale varchar(255) NOT NULL,
              cod_identificativo varchar(255) NOT NULL,
              codice_servizio_ioitalia varchar(255) NULL,
              email varchar(255) NOT NULL,
              email_pec varchar(255) NOT NULL,
              nome_dipartimento varchar(255) NOT NULL,
              nome_ente varchar(255) NOT NULL,
              nome_servizio varchar(255) NOT NULL,
              "token" varchar(255) NULL,
              CONSTRAINT codidpk UNIQUE (cod_identificativo),
              CONSTRAINT iottservizio_pkey PRIMARY KEY (id_obj),
              CONSTRAINT uk_827xbx4gcdoc21su2uvre1tce UNIQUE (cod_identificativo),
              CONSTRAINT uk_etudv72civnsa018n5xtdruqi UNIQUE (nome_servizio),
              CONSTRAINT uk_fa7eowcwm345gasvlan1st4xj UNIQUE (email_pec)
);

INSERT INTO suiot01.iottservizio (id_obj,datainserimento,datamodifica,utenteinserimento,utentemodifica,"version",codicefiscale,cod_identificativo,codice_servizio_ioitalia,email,email_pec,nome_dipartimento,nome_ente,nome_servizio,"token") VALUES
(nextval('suiot01.nxtnbr'),'2020-06-05 15:06:16.690','2020-06-05 15:06:16.690','anonymousUser','anonymousUser',0,'00355870221','1075ba81-aabb-45d1-98b7-11d442378693','01DQFBC7CNN6A68HFS91Q3DFW6','servizio.innovazionedigitale@pec.comune.trento.it','servizio.innovazionedigitale@pec.comune.trento.it','INNOVAZIONE E SERVIZI DIGITALI','Comune di Trento','Cartella del Cittadino','408bfcd96a5442d3a4b6b1d44a5eae97');

INSERT INTO suiot01.iottservizio (id_obj,datainserimento,datamodifica,utenteinserimento,utentemodifica,"version",codicefiscale,cod_identificativo,codice_servizio_ioitalia,email,email_pec,nome_dipartimento,nome_ente,nome_servizio,"token") VALUES
(nextval('suiot01.nxtnbr'),'2020-06-16 07:27:51.309','2020-06-22 06:51:38.033','anonymousUser','anonymousUser',4,'asdas','5a60cb17e643386489c32947c3050400','jhjj','das','dsa','asd','proiva','serviio','das');

INSERT INTO suiot01.iottservizio (id_obj,datainserimento,datamodifica,utenteinserimento,utentemodifica,"version",codicefiscale,cod_identificativo,codice_servizio_ioitalia,email,email_pec,nome_dipartimento,nome_ente,nome_servizio,"token") VALUES
(nextval('suiot01.nxtnbr'),'2020-06-17 14:02:16.324','2020-06-17 14:02:16.324','anonymousUser','anonymousUser',0,'00337460224','e7dd132d-4c19-4fcf-9714-308f76ed7380','01E853DE32RYSEB7YDFEA53RB0','appag@provincia.tn.it','appag@pec.provincia.tn.it','Agenzia Provinciale per i Pagamenti in Agricoltura (APPAG)','Provincia Autonoma di Trento','MyAPPAG','d3ac694854be4319a1168a05090bf3b5');

INSERT INTO suiot01.iottservizio (id_obj,datainserimento,datamodifica,utenteinserimento,utentemodifica,"version",codicefiscale,cod_identificativo,codice_servizio_ioitalia,email,email_pec,nome_dipartimento,nome_ente,nome_servizio,"token") VALUES
(nextval('suiot01.nxtnbr'),'2020-09-11 14:25:55.797','2020-09-11 14:25:55.797','anonymousUser','anonymousUser',0,'00337460224','630b4bf7-1733-4e85-a0dd-7ab8924fe46c','01EHYQVYCJ9Q3YG7XNRMAR5D1N','serv.appalti.lav.pubb@provincia.tn.it','serv.appalti.lav.pubb@pec.provincia.tn.it','Agenzia provinciale per gli Appalti e Contratti - APAC','Provincia Autonoma di Trento X','APAC','873c13ef88be4eedb4532b9798de6fb0');