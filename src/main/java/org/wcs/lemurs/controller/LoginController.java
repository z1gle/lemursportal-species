/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.wcs.lemurs.model.DarwinCore;
import org.wcs.lemurs.model.ObservationParAdmin;
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

    @Autowired(required = true)
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/autentification", method = RequestMethod.POST, headers = "Accept=application/json")
    public ModelAndView autentification(@RequestParam("login") String login, @RequestParam("password") String pw, HttpSession session) {
        if (login.isEmpty() || pw.isEmpty()) {
            return new ModelAndView("login");
        }
        ModelAndView valiny = new ModelAndView("redirect:profil");
        Integer b = -1;
        try {
            Utilisateur val = new Utilisateur();
            val.setEmail(login);
            val = (Utilisateur) autentificationService.findMultiCritere(val).get(0);
//            System.out.println(bCryptPasswordEncoder.matches(pw, val.getMotdepasse()));
            if (!bCryptPasswordEncoder.matches(pw, val.getMotdepasse())) {
                return new ModelAndView("login");
            }
            if (val == null) {
                return new ModelAndView("login");
            }
            session.setAttribute("utilisateur", val);
//            session.setAttribute("token", autentificationService.encrypte(login, pw));
            VueRoleUtilisateur vru = new VueRoleUtilisateur();
            vru.setIdUtilisateur(val.getId());
            List<VueRoleUtilisateur> roles = (List<VueRoleUtilisateur>) (List<?>) autentificationService.findMultiCritere(vru);
            for (VueRoleUtilisateur v : roles) {                
                session.setAttribute("role" + Integer.toString(v.getIdRole()), v.getIdRole());
            }
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

//    @RequestMapping(value = "/testTk")
//    public String tt() {
//        try {
//            RestClientService rcs = new RestClientService();
//            return rcs.postAutenticate("http://localhost:8085/LemursPortal-web/authenticate", "123", "zacharie.hr@gmail.com");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "login";
//        }
//    }
//    @GetMapping(value = "/checkToken")
//    public @ResponseBody String ckTk() {
//        RestClientService rcs = new RestClientService();
//        String retour = rcs.get("http://localhost:8085/LemursPortal-web/ckSD");
//        return retour.split(",")[0].split(":")[1].replaceAll("\"", "");
//    }
//    @RequestMapping(value = "/testTk")
//    public ModelAndView ckTk(@RequestParam("token") String id, HttpSession session) {
//        try {
//            Integer idU = autentificationService.decrypte(id);
//            Utilisateur val = new Utilisateur();
//            val.setId(idU);
//            autentificationService.findById(val);
//            session.setAttribute("utilisateur", val);
//            VueRoleUtilisateur vru = new VueRoleUtilisateur();
//            vru.setIdUtilisateur(val.getId());
//            List<VueRoleUtilisateur> roles = (List<VueRoleUtilisateur>) (List<?>) autentificationService.findMultiCritere(vru);
//            for (VueRoleUtilisateur v : roles) {
//                session.setAttribute("role" + Integer.toString(v.getIdRole()), v.getIdRole());
//            }
//            return new ModelAndView("redirect:profil");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ModelAndView("redirect:darwinportal");
//        }
//    }
    @RequestMapping(value = "/testTk", headers = "Accept=application/json")
    public HashMap<String, Object> goVisu(@RequestParam("token") String id, HttpSession session) {
        HashMap<String, Object> valiny = new HashMap<>();
        try {
            Integer idU = autentificationService.decrypte(id);
            Utilisateur val = new Utilisateur();
            val.setId(idU);
            autentificationService.findById(val);
            session.setAttribute("utilisateur", val);
            VueRoleUtilisateur vru = new VueRoleUtilisateur();
            vru.setIdUtilisateur(val.getId());
            List<VueRoleUtilisateur> roles = (List<VueRoleUtilisateur>) (List<?>) autentificationService.findMultiCritere(vru);
            for (VueRoleUtilisateur v : roles) {
                session.setAttribute("role" + Integer.toString(v.getIdRole()), v.getIdRole());
            }
            valiny.put("etat", Boolean.TRUE);
            valiny.put("message", "done");
        } catch (Exception e) {
            valiny.put("etat", Boolean.FALSE);
            valiny.put("message", e.getMessage());
        }
        return valiny;
    }

    @PostMapping(value = "/ckSD", headers = "Accept=application/json")
    public HashMap<String, Object> checkSessForum(HttpSession session) throws IOException {
        HashMap<String, Object> val = new HashMap<>();
        try {
            Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");
            val.put("etat", Boolean.TRUE);
            val.put("message", autentificationService.encrypte(u.getId().toString()));
//            return autentificationService.encrypte(u.getId().toString());
        } catch (Exception e) {
            val.put("etat", Boolean.FALSE);
            val.put("message", e.getMessage());
//            return "Erreur de tentative d'établissment de la connection";
        }
        return val;
    }
}
