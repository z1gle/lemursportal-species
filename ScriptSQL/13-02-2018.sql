CREATE TABLE public.video_darwin_core
(
  id serial,
  id_darwin_core integer,
  id_utilisateur_upload integer,
  lien character varying,
  date_video timestamp without time zone,
  CONSTRAINT pk_video_darwin_core PRIMARY KEY (id),
  CONSTRAINT video_darwin_core_id_darwin_core_fkey FOREIGN KEY (id_darwin_core)
      REFERENCES public.darwin_core (iddwc) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT video_darwin_core_id_utilisateur_upload_fkey FOREIGN KEY (id_utilisateur_upload)
      REFERENCES public.utilisateur (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE public.video_taxonomi
(
  id serial,
  id_taxonomi integer,
  id_utilisateur_upload integer,
  lien character varying,
  date_video timestamp without time zone,
  CONSTRAINT pk_video_taxonomi PRIMARY KEY (id),
  CONSTRAINT video_taxonomi_id_taxonomi_fkey FOREIGN KEY (id_taxonomi)
      REFERENCES public.taxonomi_base (idtaxonomibase) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT video_taxonomi_id_utilisateur_upload_fkey FOREIGN KEY (id_utilisateur_upload)
      REFERENCES public.utilisateur (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE OR REPLACE VIEW public.resume_utilisateur AS 
 select idutilisateur, commentaire, photo, video from (select idutilisateur, count(commentaire) as commentaire from commentaire_darwin_core group by idutilisateur)as cm
left join
(select id_utilisateur_upload as id_utilisateur_p, count(chemin) as photo from photo_darwin_core group by id_utilisateur_upload) as pt
on cm.idutilisateur = pt.id_utilisateur_p
left join 
(select id_utilisateur_upload as id_utilisateur_v, count(lien) as video from video_darwin_core group by id_utilisateur_upload) as vd
on cm.idutilisateur = vd.id_utilisateur_v;