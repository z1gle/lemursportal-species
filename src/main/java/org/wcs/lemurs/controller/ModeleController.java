/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.wcs.lemurs.model.Modele;
import org.wcs.lemurs.model.Utilisateur;
import org.wcs.lemurs.service.BaseService;
import org.wcs.lemurs.service.ModeleService;

/**
 *
 * @author rudyr
 */
@RestController
public class ModeleController {

    @Autowired(required = true)
    @Qualifier("baseService")
    private BaseService baseService;
    @Autowired(required = true)
    @Qualifier("modeleService")
    private ModeleService modeleService;
    
    @RequestMapping(value = "gestionModele")
    public ModelAndView darwinportal(HttpSession session) {
        ModelAndView valiny = new ModelAndView("modele");
        Utilisateur u = null;
        Integer showAddButton = -1;
        try {
            u = (Utilisateur) session.getAttribute("utilisateur");            
            if (baseService.checkRole(u, BaseController.ROLE_ADMINISTRATEUR) || baseService.checkRole(u, BaseController.ROLE_MODERATEUR)) {
                showAddButton = 0;
            }
        } catch (NullPointerException npe) {

        } catch (Exception ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }        
        valiny.addObject("showAddButton", showAddButton);
        return valiny;
    }

    @RequestMapping(value = "/modeles/{element}", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<String> list(@PathVariable("element") String element,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limite", required = false) Integer limite) throws Exception {
        List<String> response = new ArrayList<>();
        if (page == null) page = 1;
        if (limite == null) limite = 10;
        List<Modele> modeles = (List<Modele>) (List<?>) baseService.findAll(new Modele(), page, limite);
        for (Modele m : modeles) {
            String methode = "get" + element.substring(0, 1).toUpperCase() + element.substring(1);
            response.add((String) m.getClass().getMethod(methode, null).invoke(m, null));
        }
        return response;
    }
    
    @RequestMapping(value = "/modeles", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Modele> list(@RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limite", required = false) Integer limite,
            @RequestParam(value = "champ", required = false) String champ) throws Exception {        
        if (page == null) page = 1;
        if (limite == null) limite = 10;
        Modele modele = new Modele();
        if (champ != null && !champ.isEmpty()) {
            modele.setName(champ);
        }
        return (List<Modele>) (List<?>) baseService.findAll(modele, page, limite);
    }
    
    @RequestMapping(value = "/modeles/total", method = RequestMethod.GET, headers = "Accept=application/json")
    public Integer total() throws Exception {                
        return baseService.countTotal(new Modele());
    }
    
    @RequestMapping(value = "/modeles", method = RequestMethod.POST, headers = "Accept=application/json")
    public Boolean add(HttpSession session, @RequestParam("modele") MultipartFile modeleAsc, @RequestParam String fileName) throws Exception {                
        try {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            String cheminReal = session.getServletContext().getRealPath("/resources/assets/modele/ascii/");
            Modele modele = new Modele();
            modele.setName(fileName);
            modeleService.enregistrerModele(modeleAsc, utilisateur, cheminReal, modele);
            return Boolean.TRUE;
        } catch (Exception e) {
            System.err.println(e);
            return Boolean.FALSE;
        }
    }
    
    @RequestMapping(value = "/modele/{id}", method = RequestMethod.POST, headers = "Accept=application/json")
    public Boolean update(HttpSession session, @RequestParam("modele") MultipartFile modeleAsc, @RequestParam String fileName, @PathVariable Integer id) throws Exception {                
        try {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            String cheminReal = session.getServletContext().getRealPath("/resources/assets/modele/ascii/");
            Modele modele = new Modele();
            modele.setName(fileName);
            modele.setId(id);
            modeleService.enregistrerModele(modeleAsc, utilisateur, cheminReal, modele);
            return Boolean.TRUE;
        } catch (Exception e) {
            System.err.println(e);
            return Boolean.FALSE;
        }
    }
    
    @RequestMapping(value = "/modele/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public Boolean delete(HttpSession session, @PathVariable Integer id) throws Exception {                
        try {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            String cheminReal = session.getServletContext().getRealPath("/resources/assets/modele/ascii/");
            Modele modele = new Modele();            
            modele.setId(id);
            modeleService.delete(modele);
            return Boolean.TRUE;
        } catch (Exception e) {
            System.err.println(e);
            return Boolean.FALSE;
        }
    }

//    @RequestMapping(value = "/modeles", method = RequestMethod.GET, headers = "Accept=application/json")
//    public Retour list(@RequestParam(value = "page", required = false) Integer page,
//            @RequestParam(value = "limite", required = false) Integer limite) throws Exception {
//        if (page == null) {
//            page = 1;
//        }
//        if (limite == null) {
//            limite = 10;
//        }
//        Retour valiny = new Retour();
//        try {
//            valiny.setEtat(Boolean.TRUE);
//            valiny.setRetour(baseService.findAll(new Modele(), page, limite));
//        } catch (Exception e) {
//            valiny.setEtat(Boolean.FALSE);
//            valiny.setRetour(e.getMessage());
//        }
//        return valiny;
//    }
}
