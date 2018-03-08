/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.service;

/**
 *
 * @author vvizard
 */
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import org.springframework.transaction.annotation.Transactional;
import org.wcs.lemurs.model.DarwinCore;
import org.wcs.lemurs.model.Email;
import org.wcs.lemurs.model.Utilisateur;
import org.wcs.lemurs.model.ValidationDarwinCore;
import org.wcs.lemurs.modele_association.AssignationExpert;

@org.springframework.stereotype.Service
@Transactional
public class MailService extends BaseService {

    public static String formatterDarwinCore(String valeur) {
        if(valeur == null) return null;
        while (valeur.charAt(0) == ' ') {
            valeur = valeur.substring(1);
        }
        while (valeur.charAt(valeur.length() - 1) == ' ') {
            valeur = valeur.substring(0, valeur.length() - 1);
        }
        return valeur;
    }

    public List<Utilisateur> getUtilisateursConcernee(org.hibernate.Session session, DarwinCore dw) throws Exception {
        List<Utilisateur> valiny = new ArrayList<>();
        List<AssignationExpert> listeAE = new ArrayList<>();
        AssignationExpert ae = new AssignationExpert();
        ae.setEspece(formatterDarwinCore(dw.getScientificname()));
        List<AssignationExpert> asse = (List<AssignationExpert>) (List<?>) this.findMultiCritereSansLike(session, ae);
        listeAE.addAll(asse);
        ae = new AssignationExpert();
        ae.setGenre(formatterDarwinCore(dw.getGenus()));
        asse = (List<AssignationExpert>) (List<?>) this.findMultiCritereSansLike(session, ae);
        listeAE.addAll(asse);
        ae = new AssignationExpert();
        ae.setFamille(formatterDarwinCore(dw.getFamily()));
        asse = (List<AssignationExpert>) (List<?>) this.findMultiCritereSansLike(session, ae);
        listeAE.addAll(asse);
        for (AssignationExpert a : listeAE) {
            Utilisateur u = new Utilisateur();
            u.setId(a.getIdExpert());
            try {
                super.findById(session, u);
                valiny.add(u);
            } catch (org.hibernate.NonUniqueObjectException nuoe) {
            }
        }
        return valiny;
    }

    public List<Utilisateur> getUtilisateursConcernee(DarwinCore dw) throws Exception {
        org.hibernate.Session session = null;
        try {
            session = this.getHibernateDao().getSessionFactory().openSession();
            return getUtilisateursConcernee(dw);
        } catch (Exception e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public boolean checkIfContains(List<Utilisateur> liste, Utilisateur u) {
        for (Utilisateur us : liste) {
            if (us.getId().intValue() == u.getId().intValue()) {
                return true;
            }
        }
        return false;
    }

    public void checkAndAddIfNotIn(List<Utilisateur> depart, List<Utilisateur> destination) {
        int i = 0;
        if (destination.isEmpty()) {
            destination.add(depart.get(0));
            i++;
        }
        while (i < depart.size()) {
            if (checkIfContains(destination, depart.get(i))) {
                i++;
                continue;
            }
            destination.add(depart.get(i));
            i++;
        }
    }

    private int prepareCheckAndAddIfNotInDwc(List<DarwinCore> depart, List<Integer> destination, int i) throws NullPointerException {
        try {
            if (destination.isEmpty()) {
                destination.add(depart.get(0).getIdUtilisateurUpload());
                return i++;
            }
        } catch (NullPointerException npe) {
            i++;
            return prepareCheckAndAddIfNotInDwc(depart, destination, i);
        }
        return i;
    }

    private Boolean checkIfContainsInt(List<Integer> destination, Integer depart) {
        for (Integer i : destination) {
            if (i.intValue() == depart.intValue()) {
                return true;
            }
        }
        return false;
    }

    public void checkAndAddIfNotInDwc(List<DarwinCore> depart, List<Integer> destination) {
        int i = 0;
        prepareCheckAndAddIfNotInDwc(depart, destination, i);
        while (i < depart.size()) {
            if (checkIfContainsInt(destination, depart.get(i).getIdUtilisateurUpload())) {
                i++;
                continue;
            }
            destination.add(depart.get(i).getIdUtilisateurUpload());
            i++;
        }
    }

    public List<Utilisateur> getUtilisateursConcernee(List<DarwinCore> listeDw) throws Exception {
        List<Utilisateur> listeTemp = new ArrayList<>();
        org.hibernate.Session session = null;
        try {
            session = this.getHibernateDao().getSessionFactory().openSession();
            for (DarwinCore dw : listeDw) {
                try {
                    checkAndAddIfNotIn(getUtilisateursConcernee(session, dw), listeTemp);
                } catch(java.lang.IndexOutOfBoundsException ioobE) {
                    System.out.println("Il n'y a pas d'expert dans ce domaine");
                }                
            }
            return listeTemp;
        } catch (Exception e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Utilisateur> getChercheurConcernee(List<DarwinCore> listeDw) throws Exception {
        List<Integer> ids = new ArrayList<>();
        List<Utilisateur> lu = new ArrayList<>();

        org.hibernate.Session session = null;
        try {
            session = this.getHibernateDao().getSessionFactory().openSession();
            this.checkAndAddIfNotInDwc(listeDw, ids);
            for (Integer i : ids) {
                Utilisateur u = new Utilisateur();
                u.setId(i);
                try {
                    this.findById(session, u);
                    lu.add(u);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return lu;
        } catch (Exception e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void sendMailToExpert(List<DarwinCore> listes) throws Exception {
        List<Email> emails = (List<Email>) (List<?>) super.findMultiCritere(new Email());
        Email email = emails.get(emails.size() - 1);
        
        List<DarwinCore> liste = new ArrayList<>();
        for(DarwinCore dww : listes) {
            if(dww.getIdRebioma() == null && dww.getIdUtilisateurUpload() != null) {
                liste.add(dww);
            }            
        }

        List<Utilisateur> listeExperts = this.getUtilisateursConcernee(liste);

        // Contenu
        String sujet = "Lemurs: notification d'ajout d'observation";
        String texte = "De nouveaux observations dans votre domaine d'expertise ont été ajoutés";

        // Sender's email ID needs to be mentioned
        String from = email.getEmail();

        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.debug", "true");
        properties.put("mail.store.protocol", "pop3");
        properties.put("mail.transport.protocol", "smtp");
        final String username = email.getEmail();//
        final String password = email.getPassword();

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        for (Utilisateur u : listeExperts) {
            send(session, from, u.getEmail(), sujet, texte);
        }
    }
    
    private String getStatus(int stat) {
        if(stat < 0) return "En attente";
        else if(stat == 0) return "Questionnable";
        else return "Valide";
    }
    
    public void sendMailToChercheur(org.hibernate.Session sessions, ValidationDarwinCore vdc) throws Exception {
        List<Email> emails = (List<Email>) (List<?>) super.findMultiCritere(new Email());
        Email email = emails.get(emails.size() - 1);

        DarwinCore dwc = new DarwinCore();
        Utilisateur chercheur = new Utilisateur();
        Utilisateur expert = new Utilisateur();
        
        dwc.setId(vdc.getIdDarwinCore());
        this.findById(sessions, dwc);
        expert.setId(vdc.getIdExpert());
        this.findById(sessions, expert);
        chercheur.setId(dwc.getIdUtilisateurUpload());
        try {
            this.findById(sessions, chercheur);
        } catch(org.hibernate.NonUniqueObjectException nuoe) {
            chercheur = (Utilisateur) sessions.get(Utilisateur.class, 1);
        }
        

        // Contenu
        String sujet = "Lemurs: notification de modification de status";
        String texte = "Le status de l'observation numéro "+dwc.getId()+" sur l'espece "+dwc.getScientificname()+" a été mis sur "+getStatus(vdc.getValidationExpert())+" par l'expert "+expert.getNom()+" "+expert.getPrenom()+".";

        // Sender's email ID needs to be mentioned
        String from = email.getEmail();
        
        // Destination's email ID needs to be mentioned
        String to = chercheur.getEmail();

        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.debug", "true");
        properties.put("mail.store.protocol", "pop3");
        properties.put("mail.transport.protocol", "smtp");
        final String username = email.getEmail();//
        final String password = email.getPassword();

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        
        send(session, from, to, sujet, texte);
    }
    
    public void sendMailToChercheur(ValidationDarwinCore vdc) throws Exception {
        org.hibernate.Session session = null;
        try {
            session = this.getHibernateDao().getSessionFactory().openSession();
            sendMailToChercheur(session, vdc);
        } catch (Exception e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

//    public void sendMailToChercheur(List<DarwinCore> liste) throws Exception {
//        List<Email> emails = (List<Email>) (List<?>) super.findMultiCritere(new Email());
//        Email email = emails.get(emails.size() - 1);
//
//        List<Utilisateur> listeChercheur = this.getChercheurConcernee(liste);
//
//        // Contenu
//        String sujet = "Lemurs: notification de modification de status";
//        String texte = liste.size() + " observations dans votre domaine d'expertise ont été ajoutés";
//
//        // Sender's email ID needs to be mentioned
//        String from = email.getEmail();
//
//        // Assuming you are sending email from localhost
//        String host = "localhost";
//
//        // Get system properties
//        Properties properties = System.getProperties();
//
//        // Setup mail server
//        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
//        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
//        properties.setProperty("mail.smtp.port", "465");
//        properties.setProperty("mail.smtp.socketFactory.port", "465");
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.debug", "true");
//        properties.put("mail.store.protocol", "pop3");
//        properties.put("mail.transport.protocol", "smtp");
//        final String username = email.getEmail();//
//        final String password = email.getPassword();
//
//        // Get the default Session object.
//        Session session = Session.getDefaultInstance(properties, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username, password);
//            }
//        });
//
////        for (Utilisateur u : listeExperts) {
////            send(session, from, u.getEmail(), sujet, texte);
////        }
//    }

    private void send(Session session, String from, String to, String sujet, String text) throws Exception {
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(sujet);

            // Now set the actual message
            message.setText(text);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
