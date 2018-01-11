/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.service;

import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wcs.lemurs.dao.HibernateDao;
import org.wcs.lemurs.model.BaseModel;
import org.wcs.lemurs.model.Utilisateur;
import org.wcs.lemurs.modele_vue.VueRoleUtilisateur;

/**
 *
 * @author rudyr
 */
@Service
@Transactional
@Component
public class BaseService {

    @Autowired(required = true)
    @Qualifier("hibernateDao")
    private HibernateDao hibernateDao;    
    
    public void save(BaseModel bm) throws Exception {
        this.getHibernateDao().save(bm);
    }
    
    public void save(Session session, BaseModel bm) throws Exception {
        this.getHibernateDao().save(session, bm);
    }
    
    public void delete(BaseModel bm) throws Exception {
        this.getHibernateDao().delete(bm);
    }
    
    public void findById(BaseModel bm) throws Exception {
        this.getHibernateDao().findById(bm);
    }
    
    public List<BaseModel> findAll(BaseModel bm) throws Exception {
        return this.getHibernateDao().findAll(bm);
    }
    
    public List<BaseModel> findMultiCritere(BaseModel bm) throws Exception {
        return this.getHibernateDao().findMultiCritere(bm);
    }
    
    public List<BaseModel> rechercher(BaseModel bm) throws Exception {
        return this.getHibernateDao().findAllCritere(bm);
    }
    
    public boolean checkRole(Utilisateur utilisateur, String role) throws Exception {
        //  check chercheur
        VueRoleUtilisateur vru = new VueRoleUtilisateur();
        vru.setIdUtilisateur(utilisateur.getId());
        List<VueRoleUtilisateur> list = (List<VueRoleUtilisateur>) (List<?>) findMultiCritere(vru);
        for (VueRoleUtilisateur v : list) {
            if (v.getDesignation().compareTo(role) == 0) {
                return Boolean.TRUE;                
            }
        }
        return Boolean.FALSE;
    }

    public HibernateDao getHibernateDao() {
        return hibernateDao;
    }

    public void setHibernateDao(HibernateDao hibernateDao) {
        this.hibernateDao = hibernateDao;
    }
    
    
}
