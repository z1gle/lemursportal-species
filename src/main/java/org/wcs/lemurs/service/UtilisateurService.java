/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wcs.lemurs.model.ResumeUtilisateur;
import org.wcs.lemurs.model.Role;
import org.wcs.lemurs.model.Utilisateur;
import org.wcs.lemurs.modele_association.RoleUtilisateur;
import org.wcs.lemurs.modele_vue.VueRoleUtilisateur;

/**
 *
 * @author rudyr
 */
@Service
@Transactional
public class UtilisateurService extends DarwinCoreService {

    public void setRole(int[] idRoles, int idUtilisateur) throws Exception {
        Session session = null;
        Transaction tr = null;
        try {
            session = this.getHibernateDao().getSessionFactory().openSession();
            tr = session.beginTransaction();
            List<Role> roles = (List<Role>) (List<?>) this.findMultiCritere(session, new Role(), "id", 0);
            List<RoleUtilisateur> toSave = new ArrayList<>();
            for (int i : idRoles) {
                RoleUtilisateur temp = new RoleUtilisateur();
                temp.setIdRole(i);
                temp.setIdUtilisateur(idUtilisateur);
                toSave.add(temp);
            }
            RoleUtilisateur temp = new RoleUtilisateur();
            temp.setIdUtilisateur(idUtilisateur);
            List<RoleUtilisateur> toDelete = (List<RoleUtilisateur>) (List<?>) findMultiCritere(session, temp);
//            for (RoleUtilisateur v : toDelete) {
//                for (RoleUtilisateur s : toSave) {
//                    if (v.getIdRole() == s.getIdRole()) {
//                        toDelete.remove(v);
//                        toSave.remove(s);
//                    }
//                }
//                delete(session, v);
//            }
            for (int i = 0; i < toDelete.size(); i++) {
                for (int j = 0; j < toSave.size(); j++) {
                    if (toDelete.get(i).getIdRole() == toSave.get(j).getIdRole()) {
                        toDelete.remove(toDelete.get(i));
                        toSave.remove(toSave.get(j));
                        i--;
                        break;
                    }
                }
            }
            if (!toDelete.isEmpty()) {
                for (RoleUtilisateur v : toDelete) {
                    delete(session, v);
                }
            }
            if (!toSave.isEmpty()) {
                for (RoleUtilisateur v : toSave) {
                    save(session, v);
                }
            }
            tr.commit();
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private Role getRoleInListById(int id, List<Role> liste) {
        for (Role r : liste) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    public ResumeUtilisateur resumeUtilisateur(int id) throws Exception {
        ResumeUtilisateur valiny = new ResumeUtilisateur();
        valiny.setId(id);
        try {
            super.findById(valiny);
            if (valiny.getNbrCommentaire() == null) {
                valiny.setNbrCommentaire(BigInteger.ZERO);
            }
            if (valiny.getNbrPhoto() == null) {
                valiny.setNbrPhoto(BigInteger.ZERO);
            }
            if (valiny.getNbrVideo() == null) {
                valiny.setNbrVideo(BigInteger.ZERO);
            }
        } catch (ObjectNotFoundException e) {
            valiny.setNbrCommentaire(BigInteger.ZERO);
            valiny.setNbrPhoto(BigInteger.ZERO);
            valiny.setNbrVideo(BigInteger.ZERO);
        }
        return valiny;
    }

    public List<HashMap<String, Object>> findListeUtilisateur(Utilisateur utilisateur) throws Exception {
        List<HashMap<String, Object>> valiny = new ArrayList<>();

        Session session = null;
        try {
            if (utilisateur == null) {
                utilisateur = new Utilisateur();
            }
            session = super.getHibernateDao().getSessionFactory().openSession();
            List<Utilisateur> utilisateurs = (List<Utilisateur>) (List<?>) this.findMultiCritere(session, utilisateur);
            for (Utilisateur u : utilisateurs) {
                HashMap<String, Object> valLigne = new HashMap<>();
                valLigne.put("utilisateur", u);
                VueRoleUtilisateur roles = new VueRoleUtilisateur();
                roles.setIdUtilisateur(u.getId());
                valLigne.put("roles", this.findMultiCritere(session, roles));
                ResumeUtilisateur resume = new ResumeUtilisateur();
                resume.setId(u.getId());
                try {
                    this.findById(resume);
                    if (resume.getNbrCommentaire() == null) {
                        resume.setNbrCommentaire(BigInteger.ZERO);
                    }
                    if (resume.getNbrPhoto() == null) {
                        resume.setNbrPhoto(BigInteger.ZERO);
                    }
                    if (resume.getNbrVideo() == null) {
                        resume.setNbrVideo(BigInteger.ZERO);
                    }
                } catch (ObjectNotFoundException e) {
                    resume.setNbrCommentaire(BigInteger.ZERO);
                    resume.setNbrPhoto(BigInteger.ZERO);
                    resume.setNbrVideo(BigInteger.ZERO);
                }
                valLigne.put("resume", resume);
                valiny.add(valLigne);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return valiny;
    }

}
