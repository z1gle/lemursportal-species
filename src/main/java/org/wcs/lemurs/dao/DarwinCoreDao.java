/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.wcs.lemurs.model.DarwinCore;
import org.wcs.lemurs.model.Utilisateur;
import org.wcs.lemurs.modele_association.AssignationExpert;
import org.wcs.lemurs.modele_association.RoleUtilisateur;
import org.wcs.lemurs.modele_vue.VueDarwinCoreRechercheGlobale;
import org.wcs.lemurs.modele_vue.VueRechercheDarwinCore;
import org.wcs.lemurs.modele_vue.VueValidationDarwinCore;

/**
 *
 * @author rudyr
 */
@Repository
@Transactional
public class DarwinCoreDao extends HibernateDao {

    public List<DarwinCore> validationDarwinCore(int validation, String chercheur) throws Exception {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            String qry = "SELECT "
                    + "    d.*"
                    + "   FROM utilisateur u"
                    + "     JOIN darwin_core d ON d.idutilisateurupload = u.id"
                    + "     JOIN validation_darwin_core v ON v.iddarwincore = d.iddwc"
                    + "     WHERE 1=1";
            if (validation == 1) {
                qry = qry + "	AND v.validationexpert = true";
            } else if (validation == 0) {
                qry = qry + "	AND v.validationexpert = false";
            }
            if (!chercheur.isEmpty()) {
                qry = qry + "	AND (u.prenoms ilike :chercheur OR u.nom ilike :chercheur)";
            }
            Query query = session.createSQLQuery(qry).addEntity(new DarwinCore().getClass());
            if (!chercheur.isEmpty()) {
                query.setParameter("chercheur", "%" + chercheur + "%");
            }
            List<DarwinCore> list = query.list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    // Sans recherche si DarwinCore.publique = null
    public List<DarwinCore> findAll(Session session, Utilisateur utilisateur, DarwinCore darwinCore, int page, int nombre) throws Exception {
        // page < 0 => tout prendre
        // nombre < 0 => tout prendre
        if (utilisateur == null) {
            darwinCore.setPublique(Boolean.TRUE);
            return (List<DarwinCore>) (List<?>) super.findAll(session, darwinCore, page, nombre);
        }
        if (utilisateur.getId() == null) {
            darwinCore.setPublique(Boolean.TRUE);
            return (List<DarwinCore>) (List<?>) super.findAll(session, darwinCore, page, nombre);
        }
        if (darwinCore.getPublique() != null) {
            return (List<DarwinCore>) (List<?>) super.findAll(session, darwinCore, page, nombre);
        }

        Criteria criteria = session.createCriteria(darwinCore.getClass());
        Example example = Example.create(darwinCore);
        example.enableLike(MatchMode.ANYWHERE);
        example.ignoreCase();
        criteria.add(example);
        Criterion rest1 = Restrictions.and(Restrictions.eq("publique", Boolean.TRUE));
        Criterion rest2 = Restrictions.and(Restrictions.eq("publique", Boolean.FALSE), Restrictions.eq("idUtilisateurUpload", utilisateur.getId()));
        System.out.println("utilisateur valide");
        //Check if expert
        boolean expert = false;
        RoleUtilisateur role = new RoleUtilisateur();
        role.setIdUtilisateur(utilisateur.getId());
        List<RoleUtilisateur> listeRole = (List<RoleUtilisateur>) (List<?>) super.findMultiCritere(session, role);
        if (!listeRole.isEmpty()) {
            for (RoleUtilisateur ru : listeRole) {
                if (ru.getIdRole() == 101) {
                    System.out.println("Utilisateur expert");
                    AssignationExpert ae = new AssignationExpert();
                    ae.setIdExpert(utilisateur.getId());
                    List<AssignationExpert> listeAe = (List<AssignationExpert>) (List<?>) super.findMultiCritere(session, ae);
                    if (!listeAe.isEmpty()) {
                        List<String> espece = new ArrayList<>();
                        List<String> genre = new ArrayList<>();
                        List<String> famille = new ArrayList<>();
                        for (AssignationExpert ase : listeAe) {
                            if (ase.getFamille() != null) {
                                famille.add(ase.getFamille());
                            }
                            if (ase.getGenre() != null) {
                                genre.add(ase.getGenre());
                            }
                            if (ase.getEspece() != null) {
                                espece.add(ase.getEspece());
                            }
                        }
                        if (genre.isEmpty()) {
                            genre.add("null");
                        }
                        if (espece.isEmpty()) {
                            espece.add("null");
                        }
                        if (famille.isEmpty()) {
                            famille.add("null");
                        }
                        Criterion rest3 = Restrictions.in("genus", genre);
                        Criterion rest4 = Restrictions.in("family", famille);
                        Criterion rest5 = Restrictions.in("scientificname", espece);

                        Criterion restCheckAssignation = Restrictions.or(rest3, rest4, rest5);
                        Criterion restFarany = Restrictions.and(Restrictions.eq("publique", Boolean.FALSE), restCheckAssignation);

                        criteria.add(Restrictions.or(rest1, rest2, restFarany));
                        System.out.println("Utilisateur tonga hatramin'ny farany");
                        expert = true;
                        break;
                    }
                }
            }
        }
        if (!expert) {
            criteria.add(Restrictions.or(rest1, rest2));
        }

        if (page > 0 && nombre > 0) {
            criteria.setFirstResult(getFirstResult(page, nombre));
            criteria.setMaxResults(nombre);
        }
        return criteria.list();
    }

    public List<DarwinCore> findAll(Utilisateur utilisateur, DarwinCore darwinCore, int page, int nombre) throws Exception {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            return findAll(session, utilisateur, darwinCore, page, nombre);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<DarwinCore> findAll(Session session, Utilisateur utilisateur, DarwinCore darwinCore) throws Exception {
        int page = -1;
        int nombre = -1;
        return findAll(session, utilisateur, darwinCore, page, nombre);
    }

    public List<DarwinCore> findAll(Utilisateur utilisateur, DarwinCore darwinCore) throws Exception {
        int page = -1;
        int nombre = -1;
        return findAll(utilisateur, darwinCore, page, nombre);
    }

    public List<DarwinCore> findAll(Session session, Utilisateur utilisateur, VueRechercheDarwinCore darwinCore, int page, int nombre) throws Exception {
        // page < 0 => tout prendre
        // nombre < 0 => tout prendre
        if (utilisateur == null) {
            darwinCore.setPublique(Boolean.TRUE);
            return (List<DarwinCore>) (List<?>) super.findAll(session, darwinCore, page, nombre);
        }
        if (utilisateur.getId() == null) {
            darwinCore.setPublique(Boolean.TRUE);
            return (List<DarwinCore>) (List<?>) super.findAll(session, darwinCore, page, nombre);
        }
        if (darwinCore.getPublique() != null) {
            return (List<DarwinCore>) (List<?>) super.findAll(session, darwinCore, page, nombre);
        }

        Criteria criteria = session.createCriteria(darwinCore.getClass());
        Example example = Example.create(darwinCore);
        example.enableLike(MatchMode.ANYWHERE);
        example.ignoreCase();
        criteria.add(example);
        Criterion rest1 = Restrictions.and(Restrictions.eq("publique", Boolean.TRUE));
        Criterion rest2 = Restrictions.and(Restrictions.eq("publique", Boolean.FALSE), Restrictions.eq("idUtilisateurUpload", utilisateur.getId()));
        System.out.println("utilisateur valide");
        //Check if expert
        boolean expert = false;
        RoleUtilisateur role = new RoleUtilisateur();
        role.setIdUtilisateur(utilisateur.getId());
        List<RoleUtilisateur> listeRole = (List<RoleUtilisateur>) (List<?>) super.findMultiCritere(session, role);
        if (!listeRole.isEmpty()) {
            for (RoleUtilisateur ru : listeRole) {
                if (ru.getIdRole() == 101) {
                    System.out.println("Utilisateur expert");
                    AssignationExpert ae = new AssignationExpert();
                    ae.setIdExpert(utilisateur.getId());
                    List<AssignationExpert> listeAe = (List<AssignationExpert>) (List<?>) super.findMultiCritere(session, ae);
                    if (!listeAe.isEmpty()) {
                        List<String> espece = new ArrayList<>();
                        List<String> genre = new ArrayList<>();
                        List<String> famille = new ArrayList<>();
                        for (AssignationExpert ase : listeAe) {
                            if (ase.getFamille() != null) {
                                famille.add(ase.getFamille());
                            }
                            if (ase.getGenre() != null) {
                                genre.add(ase.getGenre());
                            }
                            if (ase.getEspece() != null) {
                                espece.add(ase.getEspece());
                            }
                        }
                        if (genre.isEmpty()) {
                            genre.add("null");
                        }
                        if (espece.isEmpty()) {
                            espece.add("null");
                        }
                        if (famille.isEmpty()) {
                            famille.add("null");
                        }
                        Criterion rest3 = Restrictions.in("genus", genre);
                        Criterion rest4 = Restrictions.in("family", famille);
                        Criterion rest5 = Restrictions.in("scientificname", espece);

                        Criterion restCheckAssignation = Restrictions.or(rest3, rest4, rest5);
                        Criterion restFarany = Restrictions.and(Restrictions.eq("publique", Boolean.FALSE), restCheckAssignation);

                        criteria.add(Restrictions.or(rest1, rest2, restFarany));
                        System.out.println("Utilisateur tonga hatramin'ny farany");
                        expert = true;
                        break;
                    }
                }
            }
        }
        if (!expert) {
            criteria.add(Restrictions.or(rest1, rest2));
        }

        if (page > 0 && nombre > 0) {
            criteria.setFirstResult(getFirstResult(page, nombre));
            criteria.setMaxResults(nombre);
        }
        return criteria.list();
    }

    public List<DarwinCore> findAll(Utilisateur utilisateur, VueRechercheDarwinCore darwinCore, int page, int nombre) throws Exception {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            return findAll(session, utilisateur, darwinCore, page, nombre);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<DarwinCore> findAll(Session session, Utilisateur utilisateur, VueRechercheDarwinCore darwinCore) throws Exception {
        int page = -1;
        int nombre = -1;
        return findAll(session, utilisateur, darwinCore, page, nombre);
    }

    public List<DarwinCore> findAll(Utilisateur utilisateur, VueRechercheDarwinCore darwinCore) throws Exception {
        int page = -1;
        int nombre = -1;
        return findAll(utilisateur, darwinCore, page, nombre);
    }

    public List<DarwinCore> findAll(Session session, Utilisateur utilisateur, VueValidationDarwinCore darwinCore, int page, int nombre) throws Exception {
        // page < 0 => tout prendre
        // nombre < 0 => tout prendre
        if (utilisateur == null) {
            darwinCore.setPublique(Boolean.TRUE);
            return (List<DarwinCore>) (List<?>) super.findAll(session, darwinCore, page, nombre);
        }
        if (utilisateur.getId() == null) {
            darwinCore.setPublique(Boolean.TRUE);
            return (List<DarwinCore>) (List<?>) super.findAll(session, darwinCore, page, nombre);
        }
        if (darwinCore.getPublique() != null) {
            return (List<DarwinCore>) (List<?>) super.findAll(session, darwinCore, page, nombre);
        }

        //invalide
        boolean invalide = false;
        if (darwinCore.getValidationexpert() != null) {
        if (darwinCore.getValidationexpert() == -2) {
            darwinCore.setValidationexpert(null);
            invalide = true;
        }
        }

        Criteria criteria = session.createCriteria(darwinCore.getClass());
        Example example = Example.create(darwinCore);
        example.enableLike(MatchMode.ANYWHERE);
        example.ignoreCase();
        criteria.add(example);
        Criterion rest1 = Restrictions.and(Restrictions.eq("publique", Boolean.TRUE));
        Criterion rest2 = Restrictions.and(Restrictions.eq("publique", Boolean.FALSE), Restrictions.eq("idUtilisateurUpload", utilisateur.getId()));
        System.out.println("utilisateur valide");
        //Check if expert
        boolean expert = false;
        RoleUtilisateur role = new RoleUtilisateur();
        role.setIdUtilisateur(utilisateur.getId());
        List<RoleUtilisateur> listeRole = (List<RoleUtilisateur>) (List<?>) super.findMultiCritere(session, role);
        if (!listeRole.isEmpty()) {
            for (RoleUtilisateur ru : listeRole) {
                if (ru.getIdRole() == 101) {
                    System.out.println("Utilisateur expert");
                    AssignationExpert ae = new AssignationExpert();
                    ae.setIdExpert(utilisateur.getId());
                    List<AssignationExpert> listeAe = (List<AssignationExpert>) (List<?>) super.findMultiCritere(session, ae);
                    if (!listeAe.isEmpty()) {
                        List<String> espece = new ArrayList<>();
                        List<String> genre = new ArrayList<>();
                        List<String> famille = new ArrayList<>();
                        for (AssignationExpert ase : listeAe) {
                            if (ase.getFamille() != null) {
                                famille.add(ase.getFamille());
                            }
                            if (ase.getGenre() != null) {
                                genre.add(ase.getGenre());
                            }
                            if (ase.getEspece() != null) {
                                espece.add(ase.getEspece());
                            }
                        }
                        if (genre.isEmpty()) {
                            genre.add("null");
                        }
                        if (espece.isEmpty()) {
                            espece.add("null");
                        }
                        if (famille.isEmpty()) {
                            famille.add("null");
                        }

                        Criterion rest3 = Restrictions.in("genus", genre);
                        Criterion rest4 = Restrictions.in("family", famille);
                        Criterion rest5 = Restrictions.in("scientificname", espece);

                        Criterion restCheckAssignation = Restrictions.or(rest3, rest4, rest5);
                        Criterion restFarany = Restrictions.and(Restrictions.eq("publique", Boolean.FALSE), restCheckAssignation);

                        criteria.add(Restrictions.or(rest1, rest2, restFarany));

                        //invalide
                        if (invalide) {
                            criteria.add(Restrictions.or(Restrictions.eq("annee", Boolean.FALSE), Restrictions.eq("collecteur", Boolean.FALSE), Restrictions.eq("accepted_speces", Boolean.FALSE), Restrictions.eq("gps", Boolean.FALSE)));
                            System.out.println("recherche sur les données invalides");
                        }

                        System.out.println("Utilisateur tonga hatramin'ny farany");
                        expert = true;
                        break;
                    }
                }
            }
        }
        if (!expert) {
            criteria.add(Restrictions.or(rest1, rest2));
        }

        if (page > 0 && nombre > 0) {
            criteria.setFirstResult(getFirstResult(page, nombre));
            criteria.setMaxResults(nombre);
        }
        return criteria.list();
    }

    public List<DarwinCore> findAll(Session session, Utilisateur utilisateur, VueDarwinCoreRechercheGlobale darwinCore, int page, int nombre) throws Exception {
        // page < 0 => tout prendre
        // nombre < 0 => tout prendre
        if (utilisateur == null) {
            darwinCore.setPublique(Boolean.TRUE);
            return (List<DarwinCore>) (List<?>) super.findAll(session, darwinCore, page, nombre);
        }
        if (utilisateur.getId() == null) {
            darwinCore.setPublique(Boolean.TRUE);
            return (List<DarwinCore>) (List<?>) super.findAll(session, darwinCore, page, nombre);
        }
        if (darwinCore.getPublique() != null) {
            return (List<DarwinCore>) (List<?>) super.findAll(session, darwinCore, page, nombre);
        }

        //invalide
        boolean invalide = false;
        if (darwinCore.getValidationexpert() != null) {
            if (darwinCore.getValidationexpert() == -2) {
                darwinCore.setValidationexpert(null);
                invalide = true;
            }
        }

        Criteria criteria = session.createCriteria(darwinCore.getClass());
        Example example = Example.create(darwinCore);
        example.enableLike(MatchMode.ANYWHERE);
        example.ignoreCase();
        criteria.add(example);
        Criterion rest1 = Restrictions.and(Restrictions.eq("publique", Boolean.TRUE));
        Criterion rest2 = Restrictions.and(Restrictions.eq("publique", Boolean.FALSE), Restrictions.eq("idUtilisateurUpload", utilisateur.getId()));
        System.out.println("utilisateur valide");
        //Check if expert
        boolean expert = false;
        RoleUtilisateur role = new RoleUtilisateur();
        role.setIdUtilisateur(utilisateur.getId());
        List<RoleUtilisateur> listeRole = (List<RoleUtilisateur>) (List<?>) super.findMultiCritere(session, role);
        if (!listeRole.isEmpty()) {
            for (RoleUtilisateur ru : listeRole) {
                if (ru.getIdRole() == 101) {
                    System.out.println("Utilisateur expert");
                    AssignationExpert ae = new AssignationExpert();
                    ae.setIdExpert(utilisateur.getId());
                    List<AssignationExpert> listeAe = (List<AssignationExpert>) (List<?>) super.findMultiCritere(session, ae);
                    if (!listeAe.isEmpty()) {
                        List<String> espece = new ArrayList<>();
                        List<String> genre = new ArrayList<>();
                        List<String> famille = new ArrayList<>();
                        for (AssignationExpert ase : listeAe) {
                            if (ase.getFamille() != null) {
                                famille.add(ase.getFamille());
                            }
                            if (ase.getGenre() != null) {
                                genre.add(ase.getGenre());
                            }
                            if (ase.getEspece() != null) {
                                espece.add(ase.getEspece());
                            }
                        }
                        if (genre.isEmpty()) {
                            genre.add("null");
                        }
                        if (espece.isEmpty()) {
                            espece.add("null");
                        }
                        if (famille.isEmpty()) {
                            famille.add("null");
                        }

                        Criterion rest3 = Restrictions.in("genus", genre);
                        Criterion rest4 = Restrictions.in("family", famille);
                        Criterion rest5 = Restrictions.in("scientificname", espece);

                        Criterion restCheckAssignation = Restrictions.or(rest3, rest4, rest5);
                        Criterion restFarany = Restrictions.and(Restrictions.eq("publique", Boolean.FALSE), restCheckAssignation);

                        criteria.add(Restrictions.or(rest1, rest2, restFarany));

                        //invalide
                        if (invalide) {
                            criteria.add(Restrictions.or(Restrictions.eq("annee", Boolean.FALSE), Restrictions.eq("collecteur", Boolean.FALSE), Restrictions.eq("accepted_speces", Boolean.FALSE), Restrictions.eq("gps", Boolean.FALSE)));
                            System.out.println("recherche sur les données invalides");
                        }

                        System.out.println("Utilisateur tonga hatramin'ny farany");
                        expert = true;
                        break;
                    }
                }
            }
        }
        if (!expert) {
            criteria.add(Restrictions.or(rest1, rest2));
        }

        if (page > 0 && nombre > 0) {
            criteria.setFirstResult(getFirstResult(page, nombre));
            criteria.setMaxResults(nombre);
        }
        return criteria.list();
    }

    public long countTotalDwc(Session session, Utilisateur utilisateur, VueValidationDarwinCore darwinCore) throws Exception {
        if (utilisateur == null) {
            darwinCore.setPublique(Boolean.TRUE);
            return super.countTotal(session, darwinCore);
        }
        if (utilisateur.getId() == null) {
            darwinCore.setPublique(Boolean.TRUE);
            return super.countTotal(session, darwinCore);
        }
        if (darwinCore.getPublique() != null) {
            return super.countTotal(session, darwinCore);
        }

        //invalide
        boolean invalide = false;
        if (darwinCore.getValidationexpert() != null) {
            if (darwinCore.getValidationexpert() == -2) {
                darwinCore.setValidationexpert(null);
                invalide = true;
            }
        }

        Criteria criteria = session.createCriteria(darwinCore.getClass());
        Example example = Example.create(darwinCore);
        example.enableLike(MatchMode.ANYWHERE);
        example.ignoreCase();
        criteria.add(example);
        Criterion rest1 = Restrictions.and(Restrictions.eq("publique", Boolean.TRUE));
        Criterion rest2 = Restrictions.and(Restrictions.eq("publique", Boolean.FALSE), Restrictions.eq("idUtilisateurUpload", utilisateur.getId()));
        System.out.println("utilisateur valide");
        //Check if expert
        boolean expert = false;
        RoleUtilisateur role = new RoleUtilisateur();
        role.setIdUtilisateur(utilisateur.getId());
        List<RoleUtilisateur> listeRole = (List<RoleUtilisateur>) (List<?>) super.findMultiCritere(session, role);
        if (!listeRole.isEmpty()) {
            for (RoleUtilisateur ru : listeRole) {
                if (ru.getIdRole() == 101) {
                    System.out.println("Utilisateur expert");
                    AssignationExpert ae = new AssignationExpert();
                    ae.setIdExpert(utilisateur.getId());
                    List<AssignationExpert> listeAe = (List<AssignationExpert>) (List<?>) super.findMultiCritere(session, ae);
                    if (!listeAe.isEmpty()) {
                        List<String> espece = new ArrayList<>();
                        List<String> genre = new ArrayList<>();
                        List<String> famille = new ArrayList<>();
                        for (AssignationExpert ase : listeAe) {
                            if (ase.getFamille() != null) {
                                famille.add(ase.getFamille());
                            }
                            if (ase.getGenre() != null) {
                                genre.add(ase.getGenre());
                            }
                            if (ase.getEspece() != null) {
                                espece.add(ase.getEspece());
                            }
                        }
                        if (genre.isEmpty()) {
                            genre.add("null");
                        }
                        if (espece.isEmpty()) {
                            espece.add("null");
                        }
                        if (famille.isEmpty()) {
                            famille.add("null");
                        }
                        Criterion rest3 = Restrictions.in("genus", genre);
                        Criterion rest4 = Restrictions.in("family", famille);
                        Criterion rest5 = Restrictions.in("scientificname", espece);

                        Criterion restCheckAssignation = Restrictions.or(rest3, rest4, rest5);
                        Criterion restFarany = Restrictions.and(Restrictions.eq("publique", Boolean.FALSE), restCheckAssignation);

                        //invalide
                        if (invalide) {
                            criteria.add(Restrictions.or(Restrictions.eq("annee", Boolean.FALSE), Restrictions.eq("collecteur", Boolean.FALSE), Restrictions.eq("accepted_speces", Boolean.FALSE), Restrictions.eq("gps", Boolean.FALSE)));
                            System.out.println("recherche sur les données invalides");
                        }

                        criteria.add(Restrictions.or(rest1, rest2, restFarany));
                        System.out.println("Utilisateur tonga hatramin'ny farany");
                        expert = true;
                        break;
                    }
                }
            }
        }
        if (!expert) {
            criteria.add(Restrictions.or(rest1, rest2));
        }
        Long valiny = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        if (invalide) {
            darwinCore.setValidationexpert(-2);
        }
        return valiny;
    }

    public long countTotalDwc(Session session, Utilisateur utilisateur, VueDarwinCoreRechercheGlobale darwinCore) throws Exception {
        if (utilisateur == null) {
            darwinCore.setPublique(Boolean.TRUE);
            return super.countTotal(session, darwinCore);
        }
        if (utilisateur.getId() == null) {
            darwinCore.setPublique(Boolean.TRUE);
            return super.countTotal(session, darwinCore);
        }
        if (darwinCore.getPublique() != null) {
            return super.countTotal(session, darwinCore);
        }

        //invalide
        boolean invalide = false;
        if (darwinCore.getValidationexpert() != null) {
            if (darwinCore.getValidationexpert() == -2) {
                darwinCore.setValidationexpert(null);
                invalide = true;
            }
        }

        Criteria criteria = session.createCriteria(darwinCore.getClass());
        Example example = Example.create(darwinCore);
        example.enableLike(MatchMode.ANYWHERE);
        example.ignoreCase();
        criteria.add(example);
        Criterion rest1 = Restrictions.and(Restrictions.eq("publique", Boolean.TRUE));
        Criterion rest2 = Restrictions.and(Restrictions.eq("publique", Boolean.FALSE), Restrictions.eq("idUtilisateurUpload", utilisateur.getId()));
        System.out.println("utilisateur valide");
        //Check if expert
        boolean expert = false;
        RoleUtilisateur role = new RoleUtilisateur();
        role.setIdUtilisateur(utilisateur.getId());
        List<RoleUtilisateur> listeRole = (List<RoleUtilisateur>) (List<?>) super.findMultiCritere(session, role);
        if (!listeRole.isEmpty()) {
            for (RoleUtilisateur ru : listeRole) {
                if (ru.getIdRole() == 101) {
                    System.out.println("Utilisateur expert");
                    AssignationExpert ae = new AssignationExpert();
                    ae.setIdExpert(utilisateur.getId());
                    List<AssignationExpert> listeAe = (List<AssignationExpert>) (List<?>) super.findMultiCritere(session, ae);
                    if (!listeAe.isEmpty()) {
                        List<String> espece = new ArrayList<>();
                        List<String> genre = new ArrayList<>();
                        List<String> famille = new ArrayList<>();
                        for (AssignationExpert ase : listeAe) {
                            if (ase.getFamille() != null) {
                                famille.add(ase.getFamille());
                            }
                            if (ase.getGenre() != null) {
                                genre.add(ase.getGenre());
                            }
                            if (ase.getEspece() != null) {
                                espece.add(ase.getEspece());
                            }
                        }
                        if (genre.isEmpty()) {
                            genre.add("null");
                        }
                        if (espece.isEmpty()) {
                            espece.add("null");
                        }
                        if (famille.isEmpty()) {
                            famille.add("null");
                        }
                        Criterion rest3 = Restrictions.in("genus", genre);
                        Criterion rest4 = Restrictions.in("family", famille);
                        Criterion rest5 = Restrictions.in("scientificname", espece);

                        Criterion restCheckAssignation = Restrictions.or(rest3, rest4, rest5);
                        Criterion restFarany = Restrictions.and(Restrictions.eq("publique", Boolean.FALSE), restCheckAssignation);

                        //invalide
                        if (invalide) {
                            criteria.add(Restrictions.or(Restrictions.eq("annee", Boolean.FALSE), Restrictions.eq("collecteur", Boolean.FALSE), Restrictions.eq("accepted_speces", Boolean.FALSE), Restrictions.eq("gps", Boolean.FALSE)));
                            System.out.println("recherche sur les données invalides");
                        }

                        criteria.add(Restrictions.or(rest1, rest2, restFarany));
                        System.out.println("Utilisateur tonga hatramin'ny farany");
                        expert = true;
                        break;
                    }
                }
            }
        }
        if (!expert) {
            criteria.add(Restrictions.or(rest1, rest2));
        }
        Long valiny = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        if (invalide) {
            darwinCore.setValidationexpert(-2);
        }
        return valiny;
    }

    public long countTotalDwc(Utilisateur utilisateur, VueValidationDarwinCore darwinCore) throws Exception {
        Session session = null;
        try {
            session = this.getSessionFactory().openSession();
            return countTotalDwc(session, utilisateur, darwinCore);
        } catch (Exception e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public long countTotalDwc(Utilisateur utilisateur, VueDarwinCoreRechercheGlobale darwinCore) throws Exception {
        Session session = null;
        try {
            session = this.getSessionFactory().openSession();
            return countTotalDwc(session, utilisateur, darwinCore);
        } catch (Exception e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<DarwinCore> findAll(Utilisateur utilisateur, VueValidationDarwinCore darwinCore, int page, int nombre) throws Exception {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            return findAll(session, utilisateur, darwinCore, page, nombre);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<DarwinCore> findAll(Utilisateur utilisateur, VueDarwinCoreRechercheGlobale darwinCore, int page, int nombre) throws Exception {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            return findAll(session, utilisateur, darwinCore, page, nombre);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<DarwinCore> findAll(Session session, Utilisateur utilisateur, VueValidationDarwinCore darwinCore) throws Exception {
        int page = -1;
        int nombre = -1;
        return findAll(session, utilisateur, darwinCore, page, nombre);
    }

    public List<DarwinCore> findAll(Utilisateur utilisateur, VueValidationDarwinCore darwinCore) throws Exception {
        int page = -1;
        int nombre = -1;
        return findAll(utilisateur, darwinCore, page, nombre);
    }

}
