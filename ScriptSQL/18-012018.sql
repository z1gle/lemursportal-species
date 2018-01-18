CREATE TABLE public.historiquestatus
(
  id serial,
  iddwc integer REFERENCES darwin_core,
  idexpert integer REFERENCES utilisateur,
  validation character varying,
  dateModification timestamp,  
  CONSTRAINT pk_historiquestatus PRIMARY KEY (id)
);