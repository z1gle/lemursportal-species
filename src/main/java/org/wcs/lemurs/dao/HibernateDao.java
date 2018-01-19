/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.dao;

import org.wcs.lemurs.model.BaseModel;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rudyr
 */
@Repository
@Transactional
public class HibernateDao {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(BaseModel obj) throws Exception {
        Session session = null;
        Transaction tr = null;
        try {
            session = getSessionFactory().openSession();
            tr = session.beginTransaction();
            session.saveOrUpdate(obj);
            tr.commit();
        } catch (Exception ex) {
            if (tr != null) {
                tr.rollback();
            }
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void save(Session session, BaseModel obj) throws Exception {
        session.saveOrUpdate(obj);
    }

    public void delete(BaseModel obj) throws Exception {
        Session session = null;
        Transaction tr = null;
        try {
            session = getSessionFactory().openSession();
            tr = session.beginTransaction();
            session.delete(obj);
            tr.commit();
        } catch (Exception ex) {
            if (tr != null) {
                tr.rollback();
            }
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void findById(BaseModel obj) throws Exception {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            session.load(obj, obj.getId());
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void findById(Session session, BaseModel obj) throws Exception {
        session.load(obj, obj.getId());
    }

    public List<BaseModel> findAll(BaseModel obj) throws Exception {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(obj.getClass());
            return criteria.list();
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<BaseModel> findMultiCritere(BaseModel obj) throws Exception {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            Example example = Example.create(obj);
            example.ignoreCase();
            example.excludeZeroes();
            example.enableLike(MatchMode.ANYWHERE);
            Criteria criteria = session.createCriteria(obj.getClass()).add(example);
            return criteria.list();
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<BaseModel> findMultiCritere(Session session, BaseModel obj) throws Exception {
        Example example = Example.create(obj);
        example.ignoreCase();
        example.excludeZeroes();
        example.enableLike(MatchMode.ANYWHERE);
        Criteria criteria = session.createCriteria(obj.getClass()).add(example);
        return criteria.list();
    }

    public void commit() {
        Session session = null;
        Transaction tr = null;
        try {
            session = getSessionFactory().openSession();
            tr = session.beginTransaction();
            tr.commit();
        } catch (Exception ex) {
            if (tr != null) {
                tr.rollback();
            }
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<BaseModel> findMultiCritere(Session session, BaseModel obj, String colonne, int ordre) throws Exception {
        Criteria criteria = session.createCriteria(obj.getClass());
        Example example = Example.create(obj);
        example.enableLike(MatchMode.ANYWHERE);
        example.ignoreCase();
        criteria.add(example);
//        criteria.setFirstResult(getFirstResult(page, nombre));
//        criteria.setMaxResults(nombre);
        if (ordre == 0) {
            criteria.addOrder(Order.asc(colonne));
        } else {
            criteria.addOrder(Order.desc(colonne));
        }
        return criteria.list();
    }

    public List<BaseModel> findMultiCritere(BaseModel obj, String colonne, int ordre) throws Exception {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            return findMultiCritere(session, obj, colonne, ordre);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
