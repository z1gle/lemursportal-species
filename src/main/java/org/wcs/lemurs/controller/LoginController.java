/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import static org.wcs.lemurs.controller.BaseController.ROLE_CHERCHEUR;
import org.wcs.lemurs.model.Utilisateur;
import org.wcs.lemurs.modele_vue.VueRoleUtilisateur;
import org.wcs.lemurs.service.AutentificationService;

/**
 *
 * @author rudyr
 */
@RestController
public class LoginController {

    @Autowired(required = true)
    @Qualifier("autentificationService")
    private AutentificationService autentificationService;

    @RequestMapping(value = "/autentification", method = RequestMethod.POST, headers = "Accept=application/json")
    public ModelAndView autentification(@RequestParam("login") String login, @RequestParam("password") String pw, HttpSession session, HttpServletRequest request) {
        if (login.isEmpty() || pw.isEmpty()) {
            return new ModelAndView("login");
        }
        ModelAndView valiny = new ModelAndView("redirect:profil");
        Integer b = -1;
        try {
            Utilisateur val = autentificationService.checkLogin(login, pw);
            if (val == null) {
                return new ModelAndView("login");
            }
            session.setAttribute("utilisateur", val);
            return valiny;
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("login");
        }
    }

    @RequestMapping(value = "/logout")
    public ModelAndView logout(HttpSession session, HttpServletRequest request) {
        try {
            session.invalidate();
            return new ModelAndView("login");
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("login");
        }
    }
}
