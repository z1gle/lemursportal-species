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
import org.wcs.lemurs.model.DarwinCore;

/**
 *
 * @author rudyr
 */
@Repository
@Transactional
public class DarwinCoreDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = false)
    public void save(DarwinCore darwinCore) {
        entityManager.persist(darwinCore);
    }

    @Transactional(readOnly = false)
    public void update(DarwinCore darwinCore) {
        entityManager.merge(darwinCore);
    }

    @Transactional(readOnly = false)
    public void delete(DarwinCore darwinCore) {
        entityManager.remove(darwinCore);
    }

    @Transactional(readOnly = true)
    public DarwinCore findById(int iddwc) {
        DarwinCore darwinCore = null;
        try {
            String sql = "select darwincore from Darwincore darwincore where darwincore.iddwc = " + iddwc;
            darwinCore = (DarwinCore) entityManager.createQuery(sql).getSingleResult();
        } catch (Exception ex) {
            throw ex;
        }
        return darwinCore;
    }
    
    @Transactional(readOnly = true)
    public List<DarwinCore> findAll() {
        List<DarwinCore> list_darwinCore = null;
        try {
            String sql = "select darwincore from Darwincore darwincore";
            list_darwinCore = entityManager.createQuery(sql).getResultList();
        } catch (Exception ex) {
            throw ex;
        }
        return list_darwinCore;
    }
}
