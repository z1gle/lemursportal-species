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
import org.springframework.web.servlet.ModelAndView;
import org.wcs.lemurs.model.Utilisateur;
import org.wcs.lemurs.service.BaseService;

/**
 *
 * @author rudyr
 */
@Controller
public class BaseController {
    public static String ROLE_CHERCHEUR = "Chercheur";
    public static String ROLE_EXPERT = "Expert vérificateur";
    public static String ROLE_MODERATEUR = "Modérateur";
    
    @Autowired(required = true)
    @Qualifier("baseService")
    private BaseService baseService;
    
    @RequestMapping(value="/")  
    public ModelAndView darwinportal(HttpSession session){
        ModelAndView valiny = new ModelAndView("darwinportal");
        Utilisateur u = null;
        Integer b = -1;
        Integer expert = -1;
        try {
            u = (Utilisateur) session.getAttribute("utilisateur");
            if(baseService.checkRole(u, ROLE_CHERCHEUR)) b = 0;
            else if(baseService.checkRole(u, ROLE_EXPERT)) expert = 0;            
        } catch(NullPointerException npe) {
            
        }
        catch (Exception ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        valiny.addObject("role", b);      
        valiny.addObject("expert", expert);      
        return valiny;
    }
    
    @RequestMapping(value="/darwinportal")  
    public ModelAndView darwinportals(HttpSession session){
        ModelAndView valiny = new ModelAndView("darwinportal");
        Utilisateur u = null;
        Integer b = -1;
        Integer expert = -1;
        try {
            u = (Utilisateur) session.getAttribute("utilisateur");
            if(baseService.checkRole(u, ROLE_CHERCHEUR)) b = 0;
            else if(baseService.checkRole(u, ROLE_EXPERT)) expert = 0;            
        } catch(NullPointerException npe) {
            
        }
        catch (Exception ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        valiny.addObject("role", b);      
        valiny.addObject("expert", expert);      
        return valiny;
    }
    
    @RequestMapping(value="/taxonomi")  
    public ModelAndView taxoportal(HttpSession session){         
        Integer moderateur = -1;
        ModelAndView valiny = new ModelAndView("taxonomiportal");          
        try {
            Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");
            if(baseService.checkRole(u, ROLE_MODERATEUR)) moderateur = 0;
        } catch (Exception ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        valiny.addObject("moderateur", moderateur);
        return valiny;
    }        
    
    @RequestMapping(value="/login")  
    public ModelAndView login(){
        return new ModelAndView("login");  
    }            
}
