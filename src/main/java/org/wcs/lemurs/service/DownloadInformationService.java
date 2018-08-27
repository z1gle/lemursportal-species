/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.service;

import java.util.Calendar;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wcs.lemurs.model.DownloadInformation;
import org.wcs.lemurs.modele_association.AssociationDownloadInformationObservation;

/**
 *
 * @author zacharie
 */
@Service
@Transactional
public class DownloadInformationService extends BaseService {

    public void save(DownloadInformation di) throws Exception {
        Session session = null;
        Transaction tr = null;
        try {
            session = super.getHibernateDao().getSessionFactory().openSession();            
            tr = session.beginTransaction();
            
            if(di.getDate()==null) {
                di.setDate(Calendar.getInstance().getTime());
            }
            super.save(session, di);
            
            for (AssociationDownloadInformationObservation a : di.getListeObservations()) {
                if(a.getIdDownloadInformation()==null) {
                    a.setIdDownloadInformation(di.getId());
                }
                super.save(session, a);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
