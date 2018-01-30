/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import static org.wcs.lemurs.controller.BaseController.ROLE_ADMINISTRATEUR;
import static org.wcs.lemurs.controller.BaseController.ROLE_CHERCHEUR;
import static org.wcs.lemurs.controller.BaseController.ROLE_EXPERT;
import org.wcs.lemurs.model.CommentaireDarwinCore;
import org.wcs.lemurs.model.Utilisateur;
import org.wcs.lemurs.model.ValidationDarwinCore;
import org.wcs.lemurs.modele_vue.VueRoleUtilisateur;
import org.wcs.lemurs.modele_vue.VueValidationDarwinCore;
import org.wcs.lemurs.service.UtilisateurService;

/**
 *
 * @author rudyr
 */
@RestController
public class UtilisateurControleur {

    @Autowired(required = true)
    @Qualifier("utilisateurService")
    private UtilisateurService utilisateurService;

    @RequestMapping(value = "/profil")
    public ModelAndView profil(HttpSession session, HttpServletRequest request) {
        try {
            Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");
            if (u == null) {
                return new ModelAndView("redirect:login");
            }
            Integer observationTotale = 0;
            Integer observationEnAttente = 0;
            Integer observationValide = 0;
            Integer observationQuestionnable = 0;
            
            Integer observationTotaleChercheur = 0;
            Integer observationEnAttenteChercheur = 0;
            Integer observationValideChercheur = 0;
            Integer observationQuestionnableChercheur = 0;
            
            VueRoleUtilisateur vru = new VueRoleUtilisateur();
            vru.setIdUtilisateur(u.getId());
            List<VueRoleUtilisateur> roles = (List<VueRoleUtilisateur>) (List<?>) utilisateurService.findMultiCritere(vru);
            CommentaireDarwinCore cdc = new CommentaireDarwinCore();
            cdc.setIdUtilisateur(u.getId());
            Integer nbrCommentaire = utilisateurService.findMultiCritere(cdc).size();
            for (VueRoleUtilisateur v : roles) {
                if (v.getDesignation().compareTo(ROLE_EXPERT) == 0) {
//                    observationTotale = utilisateurService.getListObservationAndEtatFor(u).size();
                    List<VueValidationDarwinCore> listeTotale = utilisateurService.getListObservationAndEtatFor(u);
                    for(VueValidationDarwinCore vv : listeTotale) {
                        if(vv.getValidationexpert()==0) observationQuestionnable++;
                        else if(vv.getValidationexpert()==-1) observationEnAttente++;
                        else if(vv.getValidationexpert()==1) observationValide++;
                    }
                    observationTotale = listeTotale.size();
                }
                if (v.getDesignation().compareTo(ROLE_CHERCHEUR) == 0) {
                    VueValidationDarwinCore temp = new VueValidationDarwinCore();
                    temp.setIdUtilisateurUpload(u.getId());
                    List<VueValidationDarwinCore> listeTotale = (List<VueValidationDarwinCore>)(List<?>)utilisateurService.findMultiCritere(temp);
                    for(VueValidationDarwinCore vv : listeTotale) {
                        if(vv.getValidationexpert()==0) observationQuestionnableChercheur++;
                        else if(vv.getValidationexpert()==-1) observationEnAttenteChercheur++;
                        else if(vv.getValidationexpert()==1) observationValideChercheur++;
                    }
                    observationTotaleChercheur = listeTotale.size();
                }
            }

            ModelAndView val = new ModelAndView("profil-utilisateur");
            val.addObject("nbrCommentaire", nbrCommentaire);
            val.addObject("utilisateur", u);
            val.addObject("roles", roles);
            val.addObject("observationEnAttente", observationEnAttente);
            val.addObject("observationQuestionnable", observationQuestionnable);
            val.addObject("observationValide", observationValide);
            val.addObject("observationTotale", observationTotale);
            val.addObject("observationEnAttenteChercheur", observationEnAttenteChercheur);
            val.addObject("observationQuestionnableChercheur", observationQuestionnableChercheur);
            val.addObject("observationValideChercheur", observationValideChercheur);
            val.addObject("observationTotaleChercheur", observationTotaleChercheur);
            return val;
        } catch (NullPointerException npe) {
            return new ModelAndView("redirect:login");
        } catch (Exception ex) {
            Logger.getLogger(UtilisateurControleur.class.getName()).log(Level.SEVERE, null, ex);
            return new ModelAndView("redirect:login");
        }
    }

    @RequestMapping(value = "/listeUtilisateur")
    public ModelAndView listeUtilisateur(HttpSession session, HttpServletRequest request) {
        try {
            Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");
            if (u == null) {
                return new ModelAndView("redirect:login");
            }

//            VueRoleUtilisateur vru = new VueRoleUtilisateur();
//            vru.setIdUtilisateur(u.getId());
//            List<VueRoleUtilisateur> roles = (List<VueRoleUtilisateur>) (List<?>) baseService.findMultiCritere(vru);
            ModelAndView val = new ModelAndView("liste-utilisateur");
//            val.addObject("utilisateur", u);
//            val.addObject("roles", roles);
            return val;
        } catch (NullPointerException npe) {
            return new ModelAndView("redirect:login");
        } catch (Exception ex) {
            Logger.getLogger(UtilisateurControleur.class.getName()).log(Level.SEVERE, null, ex);
            return new ModelAndView("redirect:login");
        }
    }

    @RequestMapping(value = "/findAllUtilisateur", method = RequestMethod.POST, headers = "Accept=application/json")
    public List<Utilisateur> findAllUtilisateur() throws Exception {
        return (List<Utilisateur>) (List<?>) utilisateurService.findMultiCritere(new Utilisateur());
    }

    @RequestMapping(value = "/rechercherUtilisateur", method = RequestMethod.POST, headers = "Accept=application/json")
    public List<Utilisateur> rechercherUtilisateur(@RequestBody Utilisateur utilisateur) throws Exception {
        return (List<Utilisateur>) (List<?>) utilisateurService.findMultiCritere(utilisateur);
    }

    @RequestMapping(value = "/assignationExpert")
    public ModelAndView assignationExpert(HttpSession session, @RequestParam(value = "idExpert") Integer idExpert) {
        try {
            Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");
            VueRoleUtilisateur vru = new VueRoleUtilisateur();
            vru.setIdUtilisateur(u.getId());
            List<VueRoleUtilisateur> list = (List<VueRoleUtilisateur>) (List<?>) utilisateurService.findMultiCritere(vru);
            if (utilisateurService.checkRole(u, ROLE_ADMINISTRATEUR)) {
                ModelAndView valiny = new ModelAndView("assignationExpert");
                Utilisateur exp = new Utilisateur();
                exp.setId(idExpert);
                utilisateurService.findById(exp);
                if (!utilisateurService.checkRole(exp, ROLE_EXPERT)) {
                    idExpert = -999;
                }
                valiny.addObject("idExpert", idExpert);
                return valiny;
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ModelAndView("redirect:login");
    }

    @RequestMapping(value = "/detailUtilisateur")
    public ModelAndView detailUtilisateur(HttpSession session, @RequestParam(value = "idUtilisateur") Integer idUtilisateur) {
        try {
            Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");
            VueRoleUtilisateur vru = new VueRoleUtilisateur();
            vru.setIdUtilisateur(u.getId());
            List<VueRoleUtilisateur> list = (List<VueRoleUtilisateur>) (List<?>) utilisateurService.findMultiCritere(vru);
            if (utilisateurService.checkRole(u, ROLE_ADMINISTRATEUR)) {
                ModelAndView valiny = new ModelAndView("insertion_modification_utilisateur");
                valiny.addObject("idUtilisateur", idUtilisateur);
                return valiny;
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ModelAndView("redirect:login");
    }

    @RequestMapping(value = "/getDetailUtilisateur", method = RequestMethod.POST, headers = "Accept=application/json")
    public Utilisateur getDetailUtilisateur(HttpSession session, @RequestBody HashMap<String, Integer> utilisateur) throws Exception {
        try {
            Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");
            VueRoleUtilisateur vru = new VueRoleUtilisateur();
            vru.setIdUtilisateur(u.getId());
            List<VueRoleUtilisateur> list = (List<VueRoleUtilisateur>) (List<?>) utilisateurService.findMultiCritere(vru);
            if (utilisateurService.checkRole(u, ROLE_ADMINISTRATEUR)) {
                Utilisateur ut = new Utilisateur();
                ut.setId(utilisateur.get("id"));
                if (utilisateur.get("id") < 0) {
                    return new Utilisateur();
                }
                utilisateurService.findById(ut);
                return ut;
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Utilisateur();
    }

    @RequestMapping(value = "/saveUtilisateur", method = RequestMethod.POST, headers = "Accept=application/json")
    public void getDetailUtilisateur(HttpSession session, @RequestBody Utilisateur utilisateur) throws Exception {
        try {
            Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");
            VueRoleUtilisateur vru = new VueRoleUtilisateur();
            vru.setIdUtilisateur(u.getId());
            List<VueRoleUtilisateur> list = (List<VueRoleUtilisateur>) (List<?>) utilisateurService.findMultiCritere(vru);
            if (utilisateurService.checkRole(u, ROLE_ADMINISTRATEUR)) {
                utilisateurService.save(utilisateur);
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @RequestMapping(value = "/deleteUtilisateur", method = RequestMethod.GET, headers = "Accept=application/json")
    public void deleteUtilisateur(HttpSession session, @RequestParam(value = "idUtilisateur") Integer idUtilisateur) throws Exception {
        try {
            Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");
            VueRoleUtilisateur vru = new VueRoleUtilisateur();
            vru.setIdUtilisateur(u.getId());
            List<VueRoleUtilisateur> list = (List<VueRoleUtilisateur>) (List<?>) utilisateurService.findMultiCritere(vru);
            if (utilisateurService.checkRole(u, ROLE_ADMINISTRATEUR)) {
                Utilisateur ut = new Utilisateur();
                ut.setId(idUtilisateur);
                utilisateurService.delete(ut);
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @RequestMapping(value = "/checkRoleUtilisateur", method = RequestMethod.POST, headers = "Accept=application/json")
    public Boolean checkRoleUtilisateur(HttpSession session, @RequestBody HashMap<String, String> utilisateurRole) throws Exception {
        try {
            int idUtilisateur = Integer.parseInt(utilisateurRole.get("utilisateur"));
            String role = utilisateurRole.get("role");
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setId(idUtilisateur);
            utilisateurService.findById(utilisateur);
            if (role.contains("Expert")) {
                role = ROLE_EXPERT;
            }
            return utilisateurService.checkRole(utilisateur, role);
        } catch (Exception ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @RequestMapping(value = "/getRoleUtilisateur", method = RequestMethod.POST, headers = "Accept=application/json")
    public List<VueRoleUtilisateur> getRoleUtilisateur(HttpSession session, @RequestBody HashMap<String, String> utilisateurRole) throws Exception {
        try {
            int idUtilisateur = Integer.parseInt(utilisateurRole.get("utilisateur"));
            VueRoleUtilisateur vru = new VueRoleUtilisateur();
            vru.setIdUtilisateur(idUtilisateur);
            return (List<VueRoleUtilisateur>) (List<?>) utilisateurService.findMultiCritere(vru);
        } catch (Exception ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @RequestMapping(value = "/setRoles", method = RequestMethod.GET, headers = "Accept=application/json")
    public void setRoles(HttpSession session, @RequestParam(value = "col[]") int[] idRoles, @RequestParam(value = "idUtilisateur") int idUtilisateur) throws Exception {
        try {
            utilisateurService.setRole(idRoles, idUtilisateur);
        } catch (Exception ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
