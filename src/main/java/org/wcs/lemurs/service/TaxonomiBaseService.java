/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wcs.lemurs.dao.HibernateDao;
import org.wcs.lemurs.model.BaseModel;
import org.wcs.lemurs.model.TaxonomiBase;

/**
 *
 * @author rudyr
 */
@Service
@Transactional
public class TaxonomiBaseService {
    
    @Autowired(required = true)
    @Qualifier("hibernateDao")
    private HibernateDao hibernateDao;
    
    @Transactional
    public void save(TaxonomiBase taxonomiBase) throws Exception {
        hibernateDao.save(taxonomiBase);
    }
    
    @Transactional
    public void update(TaxonomiBase taxonomiBase) throws Exception {
        hibernateDao.save(taxonomiBase);
    }
    
    @Transactional
    public void delete(TaxonomiBase taxonomiBase) throws Exception {
        hibernateDao.delete(taxonomiBase);
    }
    
    public TaxonomiBase findById(int idtaxonomibase) throws Exception{
        TaxonomiBase taxonomiBase = new TaxonomiBase();
        taxonomiBase.setId(idtaxonomibase);
        hibernateDao.findById(taxonomiBase);
        return taxonomiBase;
    }
    
    public List<TaxonomiBase> findAll() throws Exception {
        List<BaseModel> list_bm = hibernateDao.findAll(new TaxonomiBase());
        List<TaxonomiBase> res = new ArrayList<>();
        for (BaseModel bm : list_bm) {
            
            res.add((TaxonomiBase) bm);
        }
        return res;
    }
    
    public List<TaxonomiBase> findMultiCritere(TaxonomiBase taxonomiBase) throws Exception {
        List<BaseModel> list_bm = hibernateDao.findMultiCritere(taxonomiBase);
        List<TaxonomiBase> res = new ArrayList<>();
        for (BaseModel bm : list_bm) {
            
            res.add((TaxonomiBase) bm);
        }
        return res;
    }
}
