/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import static org.wcs.lemurs.controller.BaseController.ROLE_CHERCHEUR;
import static org.wcs.lemurs.controller.BaseController.ROLE_EXPERT;
import org.wcs.lemurs.model.CommentaireDarwinCore;
import org.wcs.lemurs.model.DarwinCore;
import org.wcs.lemurs.model.Utilisateur;
import org.wcs.lemurs.model.ValidationDarwinCore;
import org.wcs.lemurs.modele_vue.VueRoleUtilisateur;
import org.wcs.lemurs.service.DarwinCoreService;
import org.wcs.lemurs.util.UploadFile;

/**
 *
 * @author rudyr
 */
@RestController
public class DarwinCoreController {

    @Autowired(required = true)
    @Qualifier("darwinCoreService")
    private DarwinCoreService darwinCoreService;

    @RequestMapping(value = "/detailLemurien")
    public ModelAndView darwinportal(HttpSession session, @RequestParam("id") Integer id) {
        ModelAndView valiny = new ModelAndView("observation-detail");
        Utilisateur u = new Utilisateur();
        List<String> remarques = new ArrayList<>();
        Integer chercheur = -1;
        Integer expert = -1;

        try {
            u = (Utilisateur) session.getAttribute("utilisateur");

            VueRoleUtilisateur vru = new VueRoleUtilisateur();
            vru.setIdUtilisateur(u.getId());
            List<VueRoleUtilisateur> list = (List<VueRoleUtilisateur>) (List<?>) darwinCoreService.findMultiCritere(vru);

            for (VueRoleUtilisateur v : list) {
                if (v.getDesignation().compareTo(ROLE_EXPERT) == 0) {
                    expert = 0;
                } else if (v.getDesignation().compareTo(ROLE_CHERCHEUR) == 0) {
                    chercheur = 0;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        DarwinCore dwc = new DarwinCore();
        dwc.setId(id);
        ValidationDarwinCore vdc = new ValidationDarwinCore();
        vdc.setIdDarwinCore(id);
        try {
            darwinCoreService.findById(dwc);
            vdc = ((List<ValidationDarwinCore>) (List<?>) darwinCoreService.findMultiCritere(vdc)).get(0);
            if (!vdc.getValidationExpert()) {
                if (!vdc.isAcceptedSpeces()) {
                    remarques.add("Accepted speces à vérifier");
                }
                if (!vdc.isAnnee()) {
                    remarques.add("Année à vérifier");
                }
                if (!vdc.isCollecteur()) {
                    remarques.add("collecteur à vérifier");
                }
                if (!vdc.isGps()) {
                    remarques.add("GPS à vérifier");
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(DarwinCoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
        valiny.addObject("dwc", dwc);
        valiny.addObject("remarques", remarques);
        valiny.addObject("utilisateur", u);
        valiny.addObject("chercheur", chercheur);
        valiny.addObject("expert", expert);
        return valiny;
    }

    @RequestMapping(value = "/addDarwinCore")
    public ModelAndView addDarwinCore(@RequestParam("id") Integer id) {
        ModelAndView val = new ModelAndView("cruddwc");
        val.addObject("id", id);
        return val;
    }

    @RequestMapping(value = "/commentDwc", method = RequestMethod.POST, headers = "Accept=application/json")
    public void comment(HttpSession session, @RequestBody CommentaireDarwinCore cdc) throws Exception {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        try {
            if (darwinCoreService.checkRole(utilisateur, ROLE_EXPERT) && !cdc.getCommentaire().isEmpty()) {
                cdc.setDateCommentaire(Calendar.getInstance().getTime());
                darwinCoreService.save(cdc);
            }
        } catch (NullPointerException e) {
        }

    }

    @RequestMapping(value = "/getCommentDwc", method = RequestMethod.POST, headers = "Accept=application/json")
    public List<CommentaireDarwinCore> getComment(HttpSession session, @RequestBody DarwinCore dwc) throws Exception {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (darwinCoreService.checkRole(utilisateur, ROLE_EXPERT) || (darwinCoreService.checkRole(utilisateur, ROLE_CHERCHEUR) && utilisateur.getId() == dwc.getIdUtilisateurUpload())) {
            CommentaireDarwinCore cdc = new CommentaireDarwinCore();
            cdc.setIdDarwinCore(dwc.getId());
            return (List<CommentaireDarwinCore>) (List<?>) darwinCoreService.findMultiCritere(cdc);
        }
        return null;
    }

    @RequestMapping(value = "/findByespeceDwc", method = RequestMethod.POST, headers = "Accept=application/json")
    public List<DarwinCore> findByespece(@RequestBody DarwinCore dwc) throws Exception {
        return darwinCoreService.findMutliCritere(dwc);
    }

    @RequestMapping(value = "/loadDwc", method = RequestMethod.POST, headers = "Accept=application/json")
    public DarwinCore load(@RequestBody DarwinCore dwc) throws Exception {
        darwinCoreService.findById(dwc);
        return dwc;
    }

    @RequestMapping(value = "/findByespeceDwcs", method = RequestMethod.POST, headers = "Accept=application/json")
    public List<DarwinCore> findByespeces(@RequestBody HashMap<String, String> requestData) throws Exception {
        String chercheur = requestData.get("chercheur");
        int validation = Integer.parseInt(requestData.get("validation"));
        return darwinCoreService.findValidation(validation, chercheur);
    }

    @RequestMapping(value = "/getallDwc", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<DarwinCore> getall() throws Exception {
        return darwinCoreService.findAll();
    }

    @RequestMapping(value = "/saveDwc", method = RequestMethod.POST, headers = "Accept=application/json")
    public void saveOrupdate(HttpSession session, @RequestBody DarwinCore dwc) throws Exception {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (darwinCoreService.checkRole(utilisateur, ROLE_CHERCHEUR) && utilisateur.getId() == dwc.getIdUtilisateurUpload()) {
            List<DarwinCore> ldc = new ArrayList<>();
            ldc.add(dwc);
            darwinCoreService.upload(ldc);
        }
    }

    @RequestMapping(value = "/validerDwc", method = RequestMethod.POST, headers = "Accept=application/json")
    public void valider(HttpSession session, @RequestBody DarwinCore dwc) throws Exception {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (darwinCoreService.checkRole(utilisateur, ROLE_EXPERT)) {
            ValidationDarwinCore vdc = new ValidationDarwinCore();
            vdc.setIdDarwinCore(dwc.getId());
            vdc = ((List<ValidationDarwinCore>) (List<?>) darwinCoreService.findMultiCritere(vdc)).get(0);
            vdc.setValidationExpert(Boolean.TRUE);
            vdc.setIdExpert(utilisateur.getId());
            vdc.setDateValidation(Calendar.getInstance().getTime());
            darwinCoreService.save(vdc);
        }
    }

    @RequestMapping(value = "/deleteDwc", method = RequestMethod.POST, headers = "Accept=application/json")
    public void delete(@RequestBody DarwinCore dwc) throws Exception {
        darwinCoreService.delete(dwc);
    }

    @RequestMapping(value = "/processExcel", method = RequestMethod.POST)
    public ModelAndView processExcel(Model model, @RequestParam("excelfile") MultipartFile excelfile, HttpSession session, HttpServletRequest request) {
        ModelAndView val = new ModelAndView("redirect: ");
        try {
            Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");
            if (u == null) {
                return new ModelAndView("loginTemp");
            }
            //  check chercheur
            VueRoleUtilisateur vru = new VueRoleUtilisateur();
            vru.setIdUtilisateur(u.getId());
            List<VueRoleUtilisateur> list = (List<VueRoleUtilisateur>) (List<?>) darwinCoreService.findMultiCritere(vru);
            boolean chercheur = Boolean.FALSE;
            for (VueRoleUtilisateur v : list) {
                if (v.getDesignation().compareTo(ROLE_CHERCHEUR) == 0) {
                    chercheur = Boolean.TRUE;
                    break;
                }
            }
            if (!chercheur) {
                return new ModelAndView("loginTemp");
            }
            List<DarwinCore> liste_darwin_core = UploadFile.import_darwin_core_excel(excelfile.getInputStream());
            for (DarwinCore d : liste_darwin_core) {
                d.setIdUtilisateurUpload(u.getId());
            }
            darwinCoreService.upload(liste_darwin_core);
            
            if(!chercheur) {
                Integer b = 0;
                val.addObject("role", b);
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return val;
    }
}
