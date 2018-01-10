/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.wcs.lemurs.model.Utilisateur;
import org.wcs.lemurs.modele_vue.VueRoleUtilisateur;
import org.wcs.lemurs.service.BaseService;

/**
 *
 * @author rudyr
 */
@Controller
public class BaseController {
    public static String ROLE_CHERCHEUR = "Chercheur";
    
    @Autowired(required = true)
    @Qualifier("baseService")
    private BaseService baseService;
    
    @RequestMapping(value="/")  
    public ModelAndView darwinportal(HttpSession session){
        ModelAndView valiny = new ModelAndView("darwinportal");
        Utilisateur u = null;
        Integer b = -1;
        try {
            u = (Utilisateur) session.getAttribute("utilisateur");
            VueRoleUtilisateur vru = new VueRoleUtilisateur();
            vru.setIdUtilisateur(u.getId());
            List<VueRoleUtilisateur> list = (List<VueRoleUtilisateur>)(List<?>)baseService.findMultiCritere(vru);
            
            for(VueRoleUtilisateur v : list) {
                if(v.getDesignation().compareTo(ROLE_CHERCHEUR)==0) b = 0;                
            }            
        } catch (Exception ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        valiny.addObject("role", b);                           
        return valiny;
    }
    
    @RequestMapping(value="/taxonomi")  
    public ModelAndView taxoportal(){
        return new ModelAndView("taxonomiportal");  
    }
    
    @RequestMapping(value="/addDarwinCore")  
    public ModelAndView addDarwinCore(){
        return new ModelAndView("cruddwc");
    }
    
    @RequestMapping(value="/login")  
    public ModelAndView login(){
        return new ModelAndView("loginTemp");  
    }
}
