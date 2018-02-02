CREATE TABLE public.photo_taxonomi
(
  id serial,
  id_taxonomi integer REFERENCES taxonomi_base,
  id_utilisateur_upload integer REFERENCES utilisateur,
  chemin character varying,
  profil boolean,
  date_photo timestamp,  
  CONSTRAINT pk_photo_taxonomi PRIMARY KEY (id)
);

CREATE TABLE public.photo_darwin_core
(
  id serial,
  id_darwin_core integer REFERENCES darwin_core,
  id_utilisateur_upload integer REFERENCES utilisateur,
  chemin character varying,
  profil boolean,
  date_photo timestamp,  
  CONSTRAINT pk_photo_darwin_core PRIMARY KEY (id)
);