/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.controller;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.wcs.lemurs.model.DarwinCore;
import org.wcs.lemurs.model.Utilisateur;
import org.wcs.lemurs.modele_vue.VueRoleUtilisateur;
import org.wcs.lemurs.service.BaseService;

/**
 *
 * @author rudyr
 */
@RestController
public class UtilisateurControleur {

    @Autowired(required = true)
    @Qualifier("baseService")
    private BaseService baseService;

    @RequestMapping(value = "/profil")
    public ModelAndView profil(HttpSession session, HttpServletRequest request) {
        try {
            Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");
            if (u == null) {
                return new ModelAndView("loginTemp");
            }

            VueRoleUtilisateur vru = new VueRoleUtilisateur();
            vru.setIdUtilisateur(u.getId());
            List<VueRoleUtilisateur> roles = (List<VueRoleUtilisateur>) (List<?>) baseService.rechercher(vru);

            ModelAndView val = new ModelAndView("profil-utilisateur");
            val.addObject("utilisateur", u);
            val.addObject("roles", roles);
            return val;
        } catch (NullPointerException npe) {
            return new ModelAndView("loginTemp");
        } catch (Exception ex) {
            Logger.getLogger(UtilisateurControleur.class.getName()).log(Level.SEVERE, null, ex);
            return new ModelAndView("loginTemp");
        }
    }

    @RequestMapping(value = "/listeUtilisateur")
    public ModelAndView listeUtilisateur(HttpSession session, HttpServletRequest request) {
        try {
            Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");
            if (u == null) {
                return new ModelAndView("loginTemp");
            }

//            VueRoleUtilisateur vru = new VueRoleUtilisateur();
//            vru.setIdUtilisateur(u.getId());
//            List<VueRoleUtilisateur> roles = (List<VueRoleUtilisateur>) (List<?>) baseService.findMultiCritere(vru);

            ModelAndView val = new ModelAndView("liste-utilisateur");
//            val.addObject("utilisateur", u);
//            val.addObject("roles", roles);
            return val;
        } catch (NullPointerException npe) {
            return new ModelAndView("loginTemp");
        } catch (Exception ex) {
            Logger.getLogger(UtilisateurControleur.class.getName()).log(Level.SEVERE, null, ex);
            return new ModelAndView("loginTemp");
        }
    }
    
    @RequestMapping(value = "/findAllUtilisateur", method = RequestMethod.POST, headers = "Accept=application/json")
    public List<Utilisateur> findAllUtilisateur() throws Exception {
        return (List<Utilisateur>)(List<?>) baseService.findMultiCritere(new Utilisateur());
    }
    
    @RequestMapping(value = "/rechercherUtilisateur", method = RequestMethod.POST, headers = "Accept=application/json")
    public List<Utilisateur> rechercherUtilisateur(@RequestBody Utilisateur utilisateur) throws Exception {
        return (List<Utilisateur>)(List<?>)baseService.findMultiCritere(utilisateur);
    }
}
