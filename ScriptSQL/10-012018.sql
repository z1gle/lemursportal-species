ALTER TABLE darwin_core
  ADD idutilisateurupload integer REFERENCES utilisateur;


CREATE TABLE public.validation_darwin_core
(
  id serial,
  iddarwincore integer REFERENCES darwin_core,
  accepted_speces boolean,
  gps boolean,
  annee boolean,
  collecteur boolean,  
  CONSTRAINT pk_validation_darwin_core PRIMARY KEY (id)
);

CREATE TABLE public.commentaire_darwin_core
(
  id serial,
  iddarwincore integer REFERENCES darwin_core,
  idutilisateur integer REFERENCES utilisateur,
  commentaire text,
  datecommentaire timestamp,  
  CONSTRAINT pk_commentaire_darwin_core PRIMARY KEY (id)
);

--CREATE VIEW vue_validation_darwin_core AS SELECT u.nom, u.prenom,d.*,v.*  from utilisateur u join darwin_core d 
--on d.idutilisateurupload = u.id join validation_darwin_core v on v.iddarwincore = d.iddwc;