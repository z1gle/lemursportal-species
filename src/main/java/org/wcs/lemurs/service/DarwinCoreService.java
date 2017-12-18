/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.service;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wcs.lemurs.dao.HibernateDao;
import org.wcs.lemurs.model.BaseModel;
import org.wcs.lemurs.model.DarwinCore;
import org.wcs.lemurs.util.UploadFile;

/**
 *
 * @author rudyr
 */
@Service
@Transactional
public class DarwinCoreService {

    @Autowired(required = true)
    @Qualifier("hibernateDao")
    private HibernateDao hibernateDao;

    @Transactional
    public void save(DarwinCore darwinCore) throws Exception {
        hibernateDao.save(darwinCore);
    }

    @Transactional
    public void update(DarwinCore darwinCore) throws Exception {
        hibernateDao.save(darwinCore);
    }

    @Transactional
    public void delete(DarwinCore darwinCore) throws Exception {
        hibernateDao.delete(darwinCore);
    }

    public DarwinCore findById(int iddwc) throws Exception {
        DarwinCore darwinCore = new DarwinCore();
        darwinCore.setId(iddwc);
        hibernateDao.findById(darwinCore);
        return darwinCore;
    }

    public List<DarwinCore> findAll() throws Exception {
        List<BaseModel> list_bm = hibernateDao.findAll(new DarwinCore());
        List<DarwinCore> res = new ArrayList<>();
        for (BaseModel bm : list_bm) {
            res.add((DarwinCore) bm);
        }
        return res;
    }

    public List<DarwinCore> findMutliCritere(DarwinCore darwinCore) throws Exception {
        List<BaseModel> list_bm = hibernateDao.findMultiCritere(darwinCore);
        List<DarwinCore> res = new ArrayList<>();
        for (BaseModel bm : list_bm) {
            res.add((DarwinCore) bm);
        }
        return res;
    }

    public void toUpload(String path) throws Exception {

        List<String> list_requete = UploadFile.import_file(path);
        Session session = hibernateDao.getSessionFactory().openSession();
        for (String requete : list_requete) {

            Query query = session.createQuery(requete);
            query.executeUpdate();
        }
        session.close();
    }
}
