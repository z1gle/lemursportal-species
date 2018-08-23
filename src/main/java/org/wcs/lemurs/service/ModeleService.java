/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.wcs.lemurs.model.Modele;
import org.wcs.lemurs.model.Utilisateur;

/**
 *
 * @author rudyr
 */
@Service
@Transactional
public class ModeleService extends MailService {    

    public void enregistrerModele(MultipartFile modeleAsc, Utilisateur utilisateur, String cheminReal, Modele modele) throws IOException, Exception {
        File fileTemp = File.createTempFile("temp", ".asc");
        Session session = null;
        Transaction tr = null;
        try {
            session = getHibernateDao().getSessionFactory().openSession();
            tr = session.beginTransaction();
            try (InputStream is = modeleAsc.getInputStream(); 
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(fileTemp))) {
                int i;
                while ((i = is.read()) != -1) {
                    stream.write(i);
                }
                stream.flush();
            }            
            Date date = Calendar.getInstance().getTime();
            String nom = Long.toString(date.getTime()) + ".asc";
            deplacerPhoto(fileTemp, cheminReal, nom);

//            Modele modele = new Modele();
            modele.setDate(date);
            modele.setIdUserUpload(utilisateur.getId());
//            modele.setName(name);
            modele.setPath(cheminReal+nom);
            modele.setUrl("overlay?f="+nom);

            super.save(session, modele);            
            tr.commit();            
        } catch (IOException e) {
            if (tr != null) {
                tr.rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
   
}
