/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.wcs.lemurs.model.DarwinCore;
import org.wcs.lemurs.model.ObservationParAdmin;
import org.wcs.lemurs.model.Utilisateur;
import org.wcs.lemurs.service.BaseService;

/**
 *
 * @author rudyr
 */
@Controller
public class BaseController {
//    public static String ROLE_CHERCHEUR = "Chercheur";

    public static String ROLE_EXPERT = "EXPERT";
    public static String ROLE_MODERATEUR = "MODERATEUR";
    public static String ROLE_ADMINISTRATEUR = "ADMIN";

    public static String NOTIFICATION_DOWNLOAD = "Download";
    public static String NOTIFICATION_VALIDATION = "Validation";
    public static String NOTIFICATION_ADDED = "Added";

    @Autowired(required = true)
    @Qualifier("baseService")
    private BaseService baseService;

    @RequestMapping(value = "/")
    public ModelAndView darwinportal(HttpSession session) {
        ModelAndView valiny = new ModelAndView("darwinportal");
        Utilisateur u = null;
        Integer b = -1;
        Integer expert = -1;
        Integer adminModerateur = -1;
        try {
            u = (Utilisateur) session.getAttribute("utilisateur");
            if (u != null) {
                b = 0;
            }
            if (baseService.checkRole(u, ROLE_EXPERT)) {
                expert = 0;
            }

            if (baseService.checkRole(u, ROLE_ADMINISTRATEUR) || baseService.checkRole(u, ROLE_MODERATEUR)) {
                adminModerateur = 0;
                try {
                    ObservationParAdmin opa = new ObservationParAdmin();
                    opa.setIdUtilisateur(u.getId());
                    try {
                        opa = (ObservationParAdmin) baseService.findAll(opa).get(0);
                    } catch (IndexOutOfBoundsException ioobe) {
                        System.out.println("save new ObservationParAdmin because admin id=" + u.getId() + " don't have it yet");
                        opa.setNbrObservation(baseService.countTotal(new DarwinCore()));
                        baseService.save(opa);
                    }
                    Integer nbr = baseService.countTotal(new DarwinCore()) - opa.getNbrObservation();
                    valiny.addObject("nbr", nbr);
                } catch (Exception ex) {
                    Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
                    valiny.addObject("nbr", -1);
                }
            }
        } catch (NullPointerException npe) {

        } catch (Exception ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        valiny.addObject("role", b);
        valiny.addObject("expert", expert);
        valiny.addObject("adminOuModerateur", adminModerateur);
        return valiny;
    }

    @RequestMapping(value = "/observationsForExperts")
    public ModelAndView obsForExp(HttpSession session, @RequestParam(required = false, value = "ve") Integer ve) {
        ModelAndView valiny = new ModelAndView("observationToValidate");
        Utilisateur u = null;
        Integer b = -1;
        Integer expert = -1;
        Integer adminModerateur = -1;
        try {
            u = (Utilisateur) session.getAttribute("utilisateur");
            if (u != null) {
                b = 0;
            }
            if (baseService.checkRole(u, ROLE_EXPERT)) {
                expert = 0;
            }

            if (baseService.checkRole(u, ROLE_ADMINISTRATEUR) || baseService.checkRole(u, ROLE_MODERATEUR)) {
                adminModerateur = 0;
                try {
                    ObservationParAdmin opa = new ObservationParAdmin();
                    opa.setIdUtilisateur(u.getId());
                    try {
                        opa = (ObservationParAdmin) baseService.findAll(opa).get(0);
                    } catch (IndexOutOfBoundsException ioobe) {
                        System.out.println("save new ObservationParAdmin because admin id=" + u.getId() + " don't have it yet");
                        opa.setNbrObservation(baseService.countTotal(new DarwinCore()));
                        baseService.save(opa);
                    }
                    Integer nbr = baseService.countTotal(new DarwinCore()) - opa.getNbrObservation();
                    valiny.addObject("nbr", nbr);
                } catch (Exception ex) {
                    Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
                    valiny.addObject("nbr", -1);
                }
            }
        } catch (NullPointerException npe) {

        } catch (Exception ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        valiny.addObject("role", b);
        valiny.addObject("expert", expert);
        valiny.addObject("adminOuModerateur", adminModerateur);
        valiny.addObject("validationExpert", ve);
        return valiny;
    }

    @RequestMapping(value = "/darwinportal")
    public ModelAndView darwinportals(HttpSession session) {
        ModelAndView valiny = new ModelAndView("darwinportal");
        Utilisateur u = null;
        Integer b = -1;
        Integer expert = -1;
        Integer idChercheur = 0;
        Integer adminModerateur = -1;
        try {
            u = (Utilisateur) session.getAttribute("utilisateur");
            idChercheur = u.getId();
            b = 0;
            if (baseService.checkRole(u, ROLE_EXPERT)) {
                expert = 0;
            }
            if (baseService.checkRole(u, ROLE_ADMINISTRATEUR) || baseService.checkRole(u, ROLE_MODERATEUR)) {
                adminModerateur = 0;
                try {
                    ObservationParAdmin opa = new ObservationParAdmin();
                    opa.setIdUtilisateur(u.getId());
                    try {
                        opa = (ObservationParAdmin) baseService.findAll(opa).get(0);
                    } catch (IndexOutOfBoundsException ioobe) {
                        System.out.println("save new ObservationParAdmin because admin id=" + u.getId() + " don't have it yet");
                        opa.setNbrObservation(baseService.countTotal(new DarwinCore()));
                        baseService.save(opa);
                    }
                    Integer nbr = baseService.countTotal(new DarwinCore()) - opa.getNbrObservation();
                    valiny.addObject("nbr", nbr);
                } catch (Exception ex) {
                    Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
                    valiny.addObject("nbr", -1);
                }
            }
        } catch (NullPointerException npe) {

        } catch (Exception ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

        valiny.addObject("role", b);
        valiny.addObject("idChercheur", idChercheur);
        valiny.addObject("expert", expert);
        valiny.addObject("adminOuModerateur", adminModerateur);
        return valiny;
    }

    @RequestMapping(value = "/newObservations")
    public ModelAndView newObs(HttpSession session) {
        ModelAndView valiny = new ModelAndView("newObservation");
        Utilisateur u = null;
        Integer b = -1;
        Integer expert = -1;
        Integer idChercheur = 0;
        Integer adminModerateur = -1;
        try {
            u = (Utilisateur) session.getAttribute("utilisateur");
            idChercheur = u.getId();
            b = 0;
            if (baseService.checkRole(u, ROLE_EXPERT)) {
                expert = 0;
            }
            if (baseService.checkRole(u, ROLE_ADMINISTRATEUR) || baseService.checkRole(u, ROLE_MODERATEUR)) {
                adminModerateur = 0;
                try {
                    ObservationParAdmin opa = new ObservationParAdmin();
                    opa.setIdUtilisateur(u.getId());
                    try {
                        opa = (ObservationParAdmin) baseService.findAll(opa).get(0);
                    } catch (IndexOutOfBoundsException ioobe) {
                        System.out.println("save new ObservationParAdmin because admin id=" + u.getId() + " don't have it yet");
                        opa.setNbrObservation(baseService.countTotal(new DarwinCore()));
                        baseService.save(opa);
                    }
                    Integer nbr = baseService.countTotal(new DarwinCore()) - opa.getNbrObservation();
                    valiny.addObject("nbr", nbr);
                } catch (Exception ex) {
                    Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
                    valiny.addObject("nbr", -1);
                }
            } else {
                return new ModelAndView("login");
            }
        } catch (NullPointerException npe) {

        } catch (Exception ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

        valiny.addObject("role", b);
        valiny.addObject("idChercheur", idChercheur);
        valiny.addObject("expert", expert);
        valiny.addObject("adminOuModerateur", adminModerateur);
        return valiny;
    }

    @RequestMapping(value = "/taxonomi")
    public ModelAndView taxoportal(HttpSession session) throws Exception {
        Integer moderateur = -1;
        ModelAndView valiny = new ModelAndView("taxonomiportal");
        if (session.getAttribute("utilisateur") != null) {
            Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");
            try {
                if (baseService.checkRole(u, ROLE_MODERATEUR)) {
                    moderateur = 0;
                }
            } catch (Exception e) {
                // Il doit y avoir un probleme concernant le role
            }
        }
        valiny.addObject("moderateur", moderateur);
        return valiny;
    }

    @RequestMapping(value = "/login")
    public ModelAndView login() {
        // obtain locale from LocaleContextHolder
//        Locale currentLocale = LocaleContextHolder.getLocale();
//        model.addAttribute("locale", currentLocale);
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/modification-observations")
    public ModelAndView observationModification(HttpSession session) {
        ModelAndView valiny = new ModelAndView("modifObservation");
        Utilisateur u = null;
        Integer b = -1;
        Integer expert = -1;
        Integer adminModerateur = -1;
        try {
            u = (Utilisateur) session.getAttribute("utilisateur");
            if (u != null) {
                b = 0;
            }
            if (baseService.checkRole(u, ROLE_EXPERT)) {
                expert = 0;
            }

            if (baseService.checkRole(u, ROLE_ADMINISTRATEUR) || baseService.checkRole(u, ROLE_MODERATEUR)) {
                adminModerateur = 0;
            }
        } catch (NullPointerException npe) {

        } catch (Exception ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        valiny.addObject("role", b);
        valiny.addObject("expert", expert);
        valiny.addObject("adminOuModerateur", adminModerateur);
        return valiny;
    }
}
