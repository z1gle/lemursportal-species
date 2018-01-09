/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wcs.lemurs.dao.HibernateDao;
import org.wcs.lemurs.model.BaseModel;
import org.wcs.lemurs.model.Utilisateur;

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

    public Utilisateur checkLogin(String login, String pw) throws Exception {
        Utilisateur u = new Utilisateur();
        u.setLogin(login);
        u.setMotdepasse(pw);
        List<Utilisateur> liste = (List<Utilisateur>)(List<?>)hibernateDao.findMultiCritere(u);
        if(liste.isEmpty())return null;
        return liste.get(0);
    }   
    
    public void save(BaseModel bm) throws Exception {
        this.getHibernateDao().save(bm);
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

    public HibernateDao getHibernateDao() {
        return hibernateDao;
    }

    public void setHibernateDao(HibernateDao hibernateDao) {
        this.hibernateDao = hibernateDao;
    }
    
    
}
