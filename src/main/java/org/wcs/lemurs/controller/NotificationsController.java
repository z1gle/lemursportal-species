/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.wcs.lemurs.controller.BaseController.ROLE_ADMINISTRATEUR;
import org.wcs.lemurs.model.Notifications;
import org.wcs.lemurs.model.Utilisateur;
import org.wcs.lemurs.modele_vue.VueDetailAssociationFormulaireObservation;
import org.wcs.lemurs.modele_vue.VueDetailAssociationNotificationObservation;
import org.wcs.lemurs.service.NotificationsService;

/**
 *
 * @author rudyr
 */
@RestController
public class NotificationsController {

    @Autowired(required = true)
    @Qualifier("notificationsService")
    private NotificationsService notificationsService;

//    @RequestMapping(value = "/modeles/{element}", method = RequestMethod.GET, headers = "Accept=application/json")
//    public List<String> list(@PathVariable("element") String element,
//            @RequestParam(value = "page", required = false) Integer page,
//            @RequestParam(value = "limite", required = false) Integer limite) throws Exception {
//        List<String> response = new ArrayList<>();
//        if (page == null) {
//            page = 1;
//        }
//        if (limite == null) {
//            limite = 10;
//        }
//        List<Modele> modeles = (List<Modele>) (List<?>) baseService.findAll(new Modele(), page, limite);
//        for (Modele m : modeles) {
//            String methode = "get" + element.substring(0, 1).toUpperCase() + element.substring(1);
//            response.add((String) m.getClass().getMethod(methode, null).invoke(m, null));
//        }
//        return response;
//    }
    @RequestMapping(value = "/notifications", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Notifications> list(HttpSession session,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limite", required = false) Integer limite,
            @RequestParam(value = "vue", required = false) Integer vue) throws Exception {
        if (page == null) {
            page = -1;
        }
        if (limite == null) {
            limite = -1;
        }
        Utilisateur u = null;
        Notifications notification = new Notifications();
        try {
            u = (Utilisateur) session.getAttribute("utilisateur");
            notification.setIdCible(u.getId());
        } catch (NullPointerException npe) {
            notification.setIdCible(-1);
        }
        if (vue != null) {
            notification.setVue(vue);
        } else {
            notification.setVue(-1);
        }
        return (List<Notifications>) (List<?>) notificationsService.findAll(notification, page, limite);
    }

    @RequestMapping(value = "/detailAssociationFormulaireObservations", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<VueDetailAssociationFormulaireObservation> listDAFO(HttpSession session,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limite", required = false) Integer limite,
            @RequestParam(value = "formulaire", required = false) Integer idFormulaire) throws Exception {
        if (page == null) {
            page = -1;
        }
        if (limite == null) {
            limite = -1;
        }
        Utilisateur u = null;
        try {
            u = (Utilisateur) session.getAttribute("utilisateur");
            VueDetailAssociationFormulaireObservation temp = new VueDetailAssociationFormulaireObservation();
            temp.setIdFormulaire(idFormulaire);
            if (!notificationsService.checkRole(u, ROLE_ADMINISTRATEUR)) {
                temp.setIdUtilisateurUpload(u.getId());
            }
            return (List<VueDetailAssociationFormulaireObservation>) (List<?>) notificationsService.findAll(temp, page, limite);
        } catch (NullPointerException npe) {
            return null;
        }
    }

    @RequestMapping(value = "/detailAssociationNotificationObservations", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<VueDetailAssociationNotificationObservation> listDANO(HttpSession session,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limite", required = false) Integer limite,
            @RequestParam(value = "notification", required = false) Integer idNotification) throws Exception {
        if (page == null) {
            page = -1;
        }
        if (limite == null) {
            limite = -1;
        }
        Utilisateur u = null;
        try {
            u = (Utilisateur) session.getAttribute("utilisateur");
            VueDetailAssociationNotificationObservation temp = new VueDetailAssociationNotificationObservation();
            temp.setIdNotification(idNotification);
            if (!notificationsService.checkRole(u, ROLE_ADMINISTRATEUR)) {
                temp.setIdUtilisateurUpload(u.getId());
            }
            return (List<VueDetailAssociationNotificationObservation>) (List<?>) notificationsService.findAll(temp, page, limite);
        } catch (NullPointerException npe) {
            return null;
        }
    }

    @RequestMapping(value = "/notification/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public Notifications detail(@PathVariable Integer id) throws Exception {
        Notifications notification = new Notifications();
        notification.setId(id);
        notificationsService.findById(notification);
        return notification;
    }

    @RequestMapping(value = "/notifications/total", method = RequestMethod.GET, headers = "Accept=application/json")
    public Integer total() throws Exception {
        return notificationsService.countTotal(new Notifications());
    }

//    @RequestMapping(value = "/notifications", method = RequestMethod.POST, headers = "Accept=application/json")
//    public Boolean add(HttpSession session) throws Exception {
//        try {
//            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
//            String cheminReal = session.getServletContext().getRealPath("/resources/assets/modele/ascii/");
//            Modele modele = new Modele();
//            modele.setName(fileName);
//            modeleService.enregistrerModele(modeleAsc, utilisateur, cheminReal, modele);
//            return Boolean.TRUE;
//        } catch (Exception e) {
//            System.err.println(e);
//            return Boolean.FALSE;
//        }
//    }
    @PutMapping("/notification/{id}")    
    public Boolean update(HttpSession session,
            @PathVariable Integer id,
            @RequestBody(required = false) Notifications notification,
            @RequestParam(required = false, value = "seen") Integer seen) throws Exception {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateur == null) {
            return false;
        }
        try {
            if (notification == null && seen != null) {
                notification = new Notifications();
                notification.setId(id);
                notificationsService.findById(notification);
                notification.setVue(1);
            }
            notificationsService.save(notification);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
//    @RequestMapping(value = "/modele/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
//    public Boolean delete(HttpSession session, @PathVariable Integer id) throws Exception {
//        try {
//            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
//            String cheminReal = session.getServletContext().getRealPath("/resources/assets/modele/ascii/");
//            Modele modele = new Modele();
//            modele.setId(id);
//            modeleService.delete(modele);
//            return Boolean.TRUE;
//        } catch (Exception e) {
//            System.err.println(e);
//            return Boolean.FALSE;
//        }
//    }
}
