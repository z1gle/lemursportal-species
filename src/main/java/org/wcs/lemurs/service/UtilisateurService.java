/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.service;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wcs.lemurs.model.Role;
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
            List<VueRoleUtilisateur> toSave = new ArrayList<>();
            for (int i : idRoles) {
                VueRoleUtilisateur temp = new VueRoleUtilisateur();
                temp.setIdRole(i);
                temp.setIdUtilisateur(idUtilisateur);
                temp.setDesignation(getRoleInListById(i, roles).getDesignation());
                toSave.add(temp);
            }
            VueRoleUtilisateur temp = new VueRoleUtilisateur();
            temp.setIdUtilisateur(idUtilisateur);
            List<VueRoleUtilisateur> toDelete = (List<VueRoleUtilisateur>) (List<?>) findMultiCritere(temp);
            for(VueRoleUtilisateur v : toDelete) delete(session, v);
            for(VueRoleUtilisateur v : toSave) save(session, v);
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

}
