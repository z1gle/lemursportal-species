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
import org.wcs.lemurs.model.Notifications;
import org.wcs.lemurs.model.Utilisateur;
import org.wcs.lemurs.modele_association.AssociationNotificationsObservation;

/**
 *
 * @author rudyr
 */
@Service
@Transactional
public class NotificationsService extends BaseService {

    public void save(Notifications notification) {
        Session session = null;
        Transaction tr = null;
        try {
            session = super.getHibernateDao().getSessionFactory().openSession();
            tr = session.beginTransaction();
            save(session, notification);
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

    public void save(Session session, Notifications notification) throws Exception {
        if (notification.getNbrFille() == null) {
            notification.setNbrFille(notification.getListeAssociationNotificationsObservation().size());
        }
        if (notification.getDate() == null) {
            notification.setDate(Calendar.getInstance().getTime());
        }
        super.save(session, notification);
        try {
            for (AssociationNotificationsObservation dw : notification.getListeAssociationNotificationsObservation()) {
                dw.setIdNotification(notification.getId());
                super.save(session, dw);
            }
        } catch (NullPointerException npe) {
            //It's mean that there are no observations
        }
    }

    public void findById(Session session, Notifications notification) throws Exception {
        super.findById(session, notification);
        if (notification.getIdFormulaire() != null) {
            DownloadInformation di = new DownloadInformation();
            di.setId(notification.getIdFormulaire());
            super.findById(session, di);
            notification.setFormulaire(di);
        } else {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setId(notification.getIdUtilisateur());
            super.findById(session, utilisateur);
            notification.setUtilisateur(utilisateur);
        }
    }
    
    public void findById(Notifications notification) throws Exception {
        Session session = null;        
        try {
            session = super.getHibernateDao().getSessionFactory().openSession();
            findById(session, notification);
        } catch (Exception e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

}
