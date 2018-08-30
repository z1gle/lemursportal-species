/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.service;

import java.util.Calendar;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wcs.lemurs.controller.BaseController;
import org.wcs.lemurs.model.DownloadInformation;
import org.wcs.lemurs.model.Notifications;
import org.wcs.lemurs.modele_association.AssociationDownloadInformationObservation;

/**
 *
 * @author zacharie
 */
@Service
@Transactional
public class DownloadInformationService extends NotificationsService {

    public void save(DownloadInformation di, List<Integer> cibles) throws Exception {
        Session session = null;
        Transaction tr = null;
        try {
            session = super.getHibernateDao().getSessionFactory().openSession();
            tr = session.beginTransaction();

            if (di.getDate() == null) {
                di.setDate(Calendar.getInstance().getTime());
            }
            super.save(session, di);

            for (AssociationDownloadInformationObservation a : di.getListeObservations()) {
                if (a.getIdDownloadInformation() == null) {
                    a.setIdDownloadInformation(di.getId());
                }
                super.save(session, a);
            }
            if (cibles != null) {
                for (Integer i : cibles) {
                    Notifications notification = new Notifications();
                    notification.setCategorie(BaseController.NOTIFICATION_DOWNLOAD);
                    notification.setIdFormulaire(di.getId());
                    notification.setNbrFille(di.getListeObservations().size());
                    notification.setVue(-1);
                    notification.setIdCible(i);
                    super.save(session, notification);
                }
            } else {
                Notifications notification = new Notifications();
                notification.setCategorie(BaseController.NOTIFICATION_DOWNLOAD);
                notification.setIdFormulaire(di.getId());
                notification.setNbrFille(di.getListeObservations().size());
                notification.setVue(-1);
                super.save(session, notification);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
                throw e;
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
