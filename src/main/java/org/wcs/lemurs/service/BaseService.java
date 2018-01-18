/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.service;

import java.lang.reflect.Method;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
    
//    public List<BaseModel> findAllSansDoublon(BaseModel bm) throws Exception {
//        return this.getHibernateDao().findAll(bm);
//    }
    
    public List<BaseModel> findMultiCritere(BaseModel bm) throws Exception {
        return this.getHibernateDao().findMultiCritere(bm);
    }
    
    public List<BaseModel> findMultiCritere(Session session, BaseModel bm, String colonne, int ordre) throws Exception {
        return this.getHibernateDao().findMultiCritere(session, bm, colonne, ordre);
    }
    
    public List<BaseModel> findMultiCritere(BaseModel bm, String colonne, int ordre) throws Exception {
        return this.getHibernateDao().findMultiCritere(bm, colonne, ordre);
    }
    
//    public List<BaseModel> rechercher(BaseModel bm) throws Exception {
//        return this.getHibernateDao().findAllCritere(bm);
//    }
    
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
    
    public void checkDoublon(List<BaseModel> liste, String colonne) throws Exception {
        if(!liste.isEmpty()) {
            Method methode = liste.get(0).getClass().getMethod("get"+colonne.substring(0, 1).toUpperCase()+colonne.substring(1),null);
            for(int i = 0; i < liste.size(); i++) {
                String test = (String)methode.invoke(liste.get(i), null);
                for(int j = i+1; j < liste.size(); j++) {                  
                    if(((String)methode.invoke(liste.get(j), null)).compareTo(test)==0)liste.remove(j);
                    j--;
                }
            }
        } 
    }
    
    public List<Object> executeSqlList(String sql) throws Exception {
        Session session = null;
        Transaction tr = null;
        try {
            System.out.println("ato @executeSql " + sql);
            session = hibernateDao.getSessionFactory().openSession();
            SQLQuery query = session.createSQLQuery(sql);
            return query.list();                       
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    public List<Object> executeSqlList(Session session, String sql) throws Exception {        
        try {
            System.out.println("ato @executeSql " + sql);            
            SQLQuery query = session.createSQLQuery(sql);
            return query.list();                       
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    public List<Object> executeSqlList(String sql, List<String> name, List<Object> parameter) throws Exception {
        Session session = null;
        Transaction tr = null;
        try {
            System.out.println("ato @executeSql " + sql);
            session = hibernateDao.getSessionFactory().openSession();
            SQLQuery query = session.createSQLQuery(sql);
            if(name.size()!=parameter.size()) throw new Exception("taille des listes innégales");
            int pasParam = 0;
            for(String s : name) {
                Object o = parameter.get(pasParam);
                o.getClass().cast(o);
                query.setParameter(s, o);
            }            
            return query.list();                       
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    public List<BaseModel> executeSqlListBaseModel(String sql, List<String> name, List<Object> parameter, BaseModel entite) throws Exception {
        Session session = null;
        Transaction tr = null;
        try {
            System.out.println("ato @executeSql " + sql);
            session = hibernateDao.getSessionFactory().openSession();
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(entite.getClass());
            if(name.size()!=parameter.size()) throw new Exception("taille des listes innégales");
            int pasParam = 0;
            for(String s : name) {
                Object o = parameter.get(pasParam);
                o.getClass().cast(o);
                query.setParameter(s, o);
            }            
            return query.list();                       
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    public List<Object> executeSqlList(Session session, String sql, List<String> name, List<Object> parameter) throws Exception {        
        try {
            System.out.println("ato @executeSql " + sql);            
            SQLQuery query = session.createSQLQuery(sql);
            if(name.size()!=parameter.size()) throw new Exception("taille des listes innégales");
            int pasParam = 0;
            for(String s : name) {
                Object o = parameter.get(pasParam);
                o.getClass().cast(o);
                query.setParameter(s, o);
            }            
            return query.list();                       
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    public List<BaseModel> executeSqlListBaseModel(Session session, String sql, List<String> name, List<Object> parameter, BaseModel entite) throws Exception {        
        try {
            System.out.println("ato @executeSql " + sql);            
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(entite.getClass());
            if(name.size()!=parameter.size()) throw new Exception("taille des listes innégales");
            int pasParam = 0;
            for(String s : name) {
                Object o = parameter.get(pasParam);
                o.getClass().cast(o);
                query.setParameter(s, o);
            }            
            return query.list();                       
        } catch (Exception ex) {
            throw ex;
        }
    }

    public HibernateDao getHibernateDao() {
        return hibernateDao;
    }

    public void setHibernateDao(HibernateDao hibernateDao) {
        this.hibernateDao = hibernateDao;
    }
    
    
}
