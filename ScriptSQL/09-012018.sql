CREATE TABLE public.utilisateur
(
  id serial,
  login character varying,
  motdepasse character varying,
  nom character varying,
  prenom character varying,
  email character varying,
  telephone character varying,
  poste character varying,
  localisation character varying,
  biographie text,
  CONSTRAINT pk_utilisateur PRIMARY KEY (id)
);

CREATE TABLE public.role
(
  id serial,
  designation character varying,
  CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE public.role_utilisateur
(
  id serial,
  idrole integer NOT NULL REFERENCES role,
  idutilisateur integer NOT NULL REFERENCES utilisateur,
  CONSTRAINT pk_role_utilisateur PRIMARY KEY (id)  
);

INSERT INTO role (designation) VALUES('Administrateur');
INSERT INTO role (designation) VALUES('Modérateur');
INSERT INTO role (designation) VALUES('Expert vérificateur');
INSERT INTO role (designation) VALUES('Chercheur');
INSERT INTO role (designation) VALUES('Utilisateur simple');

INSERT INTO utilisateur (login, motdepasse, nom, prenom, email, telephone, poste, localisation, biographie) VALUES('administrateur', '123', 'RAKOTOARINIVO', 'Zacharie', 'r.zach@gmail.com', 0323200123, 'Responsable technique', 'Madagascar', 'Lorem ipsum dolor not si amet lorem ipsum not.');
INSERT INTO utilisateur (login, motdepasse, nom, prenom, email, telephone, poste, localisation, biographie) VALUES('moderateur', '123', 'RAKOTOARINIVO', 'Jean', 'r.jean@gmail.com', 0323200133, 'Responsable technique', 'Madagascar', 'Lorem ipsum dolor not si amet lorem ipsum not.');
INSERT INTO utilisateur (login, motdepasse, nom, prenom, email, telephone, poste, localisation, biographie) VALUES('expert', '123', 'RAKOTONARIVO', 'Christophe', 'r.chris@gmail.com', 0323200323, 'Responsable technique', 'Madagascar', 'Lorem ipsum dolor not si amet lorem ipsum not.');
INSERT INTO utilisateur (login, motdepasse, nom, prenom, email, telephone, poste, localisation, biographie) VALUES('chercheur', '123', 'RABEZANDRY', 'Yvan', 'r.yvan@gmail.com', 0323200333, 'Responsable technique', 'Madagascar', 'Lorem ipsum dolor not si amet lorem ipsum not.');
INSERT INTO utilisateur (login, motdepasse, nom, prenom, email, telephone, poste, localisation, biographie) VALUES('simple', '123', 'RAMANANARIVO', 'Albert', 'r.albert@gmail.com', 0323200223, 'Responsable technique', 'Madagascar', 'Lorem ipsum dolor not si amet lorem ipsum not.');

INSERT INTO role_utilisateur (idrole, idutilisateur) VALUES(1,1);
INSERT INTO role_utilisateur (idrole, idutilisateur) VALUES(2,2);
INSERT INTO role_utilisateur (idrole, idutilisateur) VALUES(3,3);
INSERT INTO role_utilisateur (idrole, idutilisateur) VALUES(4,4);
INSERT INTO role_utilisateur (idrole, idutilisateur) VALUES(5,5);

CREATE VIEW vue_role_utilisateur AS SELECT role_utilisateur.*, role.designation from role join role_utilisateur on role.id = role_utilisateur.idrole;