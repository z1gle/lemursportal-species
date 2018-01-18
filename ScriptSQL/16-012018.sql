CREATE TABLE public.assignationExpert
(
  id serial,
  idexpert integer REFERENCES utilisateur,
  famille character varying,
  genre character varying,
  espece character varying,  
  CONSTRAINT pk_assignationexpert PRIMARY KEY (id)
);