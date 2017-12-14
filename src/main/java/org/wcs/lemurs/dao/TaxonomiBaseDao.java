/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.wcs.lemurs.model.TaxonomiBase;

/**
 *
 * @author rudyr
 */
@Repository
@Transactional
public class TaxonomiBaseDao {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Transactional(readOnly = false)
    public void save(TaxonomiBase taxonomiBase) {
        entityManager.persist(taxonomiBase);
    }
    
    @Transactional(readOnly = false)
    public void update(TaxonomiBase taxonomiBase) {
        entityManager.merge(taxonomiBase);
    }
    
    @Transactional(readOnly = false)
    public void delete(TaxonomiBase taxonomiBase) {
        entityManager.remove(taxonomiBase);
    }
    
    public TaxonomiBase findById(int idtaxonomibase) {
        TaxonomiBase taxonomiBase = null;
        try {
            String sql = "select taxonomibase from Taxonomibase taxonomibase where taxonomibase.idtaxonomibase = " + idtaxonomibase;
            taxonomiBase = (TaxonomiBase) entityManager.createQuery(sql).getSingleResult();
        } catch (Exception ex) {
            throw ex;
        }
        return taxonomiBase;
    }
    
    public List<TaxonomiBase> findAll() {
        List<TaxonomiBase> list_taxonomiBase = null;
        try {
            String sql = "select taxonomibase from Taxonomibase taxonomibase";
            list_taxonomiBase = entityManager.createQuery(sql).getResultList();
        } catch (Exception ex) {
            throw ex;
        }
        return list_taxonomiBase;
    }
}
