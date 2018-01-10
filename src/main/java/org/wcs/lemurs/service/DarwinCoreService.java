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
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wcs.lemurs.model.BaseModel;
import org.wcs.lemurs.model.DarwinCore;
import org.wcs.lemurs.model.ValidationDarwinCore;

/**
 *
 * @author rudyr
 */
@Service
@Transactional
public class DarwinCoreService extends BaseService {

//    @Autowired(required = true)
//    @Qualifier("hibernateDao")
//    private HibernateDao hibernateDao;

//    @Transactional
//    public void save(DarwinCore darwinCore) throws Exception {
//        save(darwinCore);
//    }
//    
//    @Transactional
//    public void save(Session session, DarwinCore darwinCore) throws Exception {
//        save(session, darwinCore);
//    }
//
//    @Transactional
//    public void update(DarwinCore darwinCore) throws Exception {
//        save(darwinCore);
//    }
//
//    @Transactional
//    public void delete(DarwinCore darwinCore) throws Exception {
//        hibernateDao.delete(darwinCore);
//    }

    public DarwinCore findById(int iddwc) throws Exception {
        DarwinCore darwinCore = new DarwinCore();
        darwinCore.setId(iddwc);
        super.findById(darwinCore);
        return darwinCore;
    }

    public List<DarwinCore> findAll() throws Exception {
        List<BaseModel> list_bm = super.findAll(new DarwinCore());
        List<DarwinCore> res = new ArrayList<>();
        for (BaseModel bm : list_bm) {
            res.add((DarwinCore) bm);
        }
        return res;
    }

    public List<DarwinCore> findMutliCritere(DarwinCore darwinCore) throws Exception {
        List<BaseModel> list_bm = super.findMultiCritere(darwinCore);
        List<DarwinCore> res = new ArrayList<>();
        for (BaseModel bm : list_bm) {
            res.add((DarwinCore) bm);
        }
        return res;
    }

//    public void upload(List<DarwinCore> list_dw) throws Exception {
//
//        for (DarwinCore dw : list_dw) {
//            
//            save(dw);
//        }
//    }
    public void upload(List<DarwinCore> list_dw) throws Exception {
        Session session = null;
        Transaction tr = null;
        try {
            session = getHibernateDao().getSessionFactory().openSession();
            tr = session.beginTransaction();
            for (DarwinCore dw : list_dw) {
                save(session, dw);
                ValidationDarwinCore vdc = new ValidationDarwinCore();
                vdc.setIdDarwinCore(dw.getId());
                vdc.setAcceptedSpeces(checkVerbatimspecies(session, dw));
                vdc.setAnnee(!dw.getDateidentified().isEmpty()&&dw.getDateidentified().compareTo("-")!=0);
                vdc.setCollecteur(!dw.getIdentifiedby().isEmpty()&&dw.getIdentifiedby().compareTo("-")!=0);
                vdc.setGps(!dw.getDecimallatitude().isEmpty()&&!dw.getDecimallongitude().isEmpty()&dw.getDecimallatitude().compareTo("-")!=0&&dw.getDecimallongitude().compareTo("-")!=0);
                save(session, vdc);
            }
            tr.commit();
        } catch (Exception ex) {
            tr.rollback();
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public boolean checkVerbatimspecies(Session session, DarwinCore dw) throws Exception {
        String qry = "select count(*) from (select *,genus || specificepithet || infraspecificepithet as verbatimspecies from taxonomi_base) as sous where sous.verbatimspecies = :verbatimspecies";
        Query query = session.createSQLQuery(qry);
        String verbatimspecies = dw.getGenus() + dw.getSpecificepithet() + dw.getInfraspecificepithet();
        query.setParameter("verbatimspecies", verbatimspecies);
        Integer val = ((BigInteger)query.list().get(0)).intValue();
        return val > 0;
    }

//    public HibernateDao getHibernateDao() {
//        return hibernateDao;
//    }
//
//    public void setHibernateDao(HibernateDao hibernateDao) {
//        this.hibernateDao = hibernateDao;
//    }

}
