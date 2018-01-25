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
import org.wcs.lemurs.exception.StatusAlreadyExistException;
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
        Integer boutonValider = -1;

        try {
            u = (Utilisateur) session.getAttribute("utilisateur");

            VueRoleUtilisateur vru = new VueRoleUtilisateur();
            vru.setIdUtilisateur(u.getId());
            List<VueRoleUtilisateur> list = (List<VueRoleUtilisateur>) (List<?>) darwinCoreService.findMultiCritere(vru);

            for (VueRoleUtilisateur v : list) {
                if (v.getDesignation().compareTo(ROLE_EXPERT) == 0) {
                    expert = 0;
                    boutonValider = 0;
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
            if (vdc.getValidationExpert() == null || vdc.getValidationExpert() < 1) {
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
            } else {
                try {
                    if (vdc.getIdExpert() == u.getId()) {
                        boutonValider = -1;
                    }
                } catch (NullPointerException npe) {
                    boutonValider = -1;
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
        valiny.addObject("boutonValider", boutonValider);
        return valiny;
    }

    @RequestMapping(value = "/addDarwinCore")
    public ModelAndView addDarwinCore(@RequestParam("id") Integer id) {
        ModelAndView val = new ModelAndView("cruddwc");
        val.addObject("id", id);
        return val;
    }
//jsldjfslkdjfsldjflsjdfsjdk

    @RequestMapping(value = "/commentDwc", method = RequestMethod.POST, headers = "Accept=application/json")
    public void comment(HttpSession session, @RequestBody CommentaireDarwinCore cdc) throws Exception {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        DarwinCore dwc = new DarwinCore();
        dwc.setId(cdc.getIdDarwinCore());
        darwinCoreService.findById(dwc);
        try {
            if ((darwinCoreService.checkRole(utilisateur, ROLE_EXPERT) || (darwinCoreService.checkRole(utilisateur, ROLE_CHERCHEUR) && dwc.getIdUtilisateurUpload() == utilisateur.getId())) && !cdc.getCommentaire().isEmpty()) {
                cdc.setDateCommentaire(Calendar.getInstance().getTime());
                darwinCoreService.save(cdc);
            }
//            if (darwinCoreService.checkRole(utilisateur, ROLE_EXPERT)) {
//                ValidationDarwinCore vdc = new ValidationDarwinCore();
//                vdc.setIdDarwinCore(dwc.getId());
//                vdc = ((List<ValidationDarwinCore>) (List<?>) darwinCoreService.findMultiCritere(vdc)).get(0);
//                vdc.setValidationExpert(0);
//                vdc.setIdExpert(utilisateur.getId());
//                vdc.setDateValidation(Calendar.getInstance().getTime());
//                darwinCoreService.save(vdc);
//            }
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

//    @RequestMapping(value = "/findByespeceDwc", method = RequestMethod.POST, headers = "Accept=application/json")
//    public List<DarwinCore> findByespece(@RequestBody DarwinCore dwc) throws Exception {
//        return darwinCoreService.findMutliCritere(dwc);
//    }
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

    @RequestMapping(value = "/findByespeceDwc", method = RequestMethod.POST, headers = "Accept=application/json")
    public List<HashMap<String, Object>> getall(HttpSession session, @RequestBody DarwinCore dwcs) throws Exception {
        try {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            return darwinCoreService.findWithCheck(utilisateur, dwcs);
        } catch (Exception e) {
            Utilisateur utilisateur = new Utilisateur();
            return darwinCoreService.findWithCheck(utilisateur, dwcs);
        }
    }

    @RequestMapping(value = "/getObservationEnAttenteDeValidation", method = RequestMethod.POST, headers = "Accept=application/json")
    public List<HashMap<String, Object>> getObservationEnAttenteDeValidation(HttpSession session) throws Exception {
        List<HashMap<String, Object>> valiny = new ArrayList<>();
        try {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            return darwinCoreService.findObservationCheck(utilisateur);
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/observationAValider")
    public ModelAndView observationAValider() {
        ModelAndView val = new ModelAndView("observationAValider");
        return val;
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
            vdc.setValidationExpert(1);
            vdc.setIdExpert(utilisateur.getId());
            vdc.setDateValidation(Calendar.getInstance().getTime());
            darwinCoreService.save(vdc);
        }
    }

    //Optimisable
    @RequestMapping(value = "/validerDwcs", method = RequestMethod.GET, headers = "Accept=application/json")
    public void validerAll(HttpSession session, @RequestParam(value = "dwc[]") int[] dwc) throws Exception {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (darwinCoreService.checkRole(utilisateur, ROLE_EXPERT)) {
            for (int i = 0; i < dwc.length; i++) {
                ValidationDarwinCore vdc = new ValidationDarwinCore();
                vdc.setIdDarwinCore(dwc[i]);
                vdc = ((List<ValidationDarwinCore>) (List<?>) darwinCoreService.findMultiCritere(vdc)).get(0);
                vdc.setValidationExpert(1);
                vdc.setIdExpert(utilisateur.getId());
                vdc.setDateValidation(Calendar.getInstance().getTime());
                darwinCoreService.save(vdc);
            }
        }
    }

    @RequestMapping(value = "/validerListDwc", method = RequestMethod.GET, headers = "Accept=application/json")
    public HashMap<String, String> validerListDwc(HttpSession session, @RequestParam(value = "dwc[]") int[] dwc, @RequestParam(value = "status") int status, @RequestParam(value = "commentaires") String commentaire) throws Exception {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        HashMap<String, String> valiny = new HashMap<>();
        if (darwinCoreService.checkRole(utilisateur, ROLE_EXPERT)) {
            try {
                if(status == 1) darwinCoreService.validerAll(dwc, utilisateur);
                else darwinCoreService.questionnableAll(dwc, utilisateur, commentaire);
                valiny.put("etat", "1");
            } catch (StatusAlreadyExistException e) {
                session.setAttribute("exception", e);
                valiny.put("etat", "0");
                valiny.put("n", Integer.toString(e.getObservationEnCours().getId()));
                valiny.put("status", e.getStatusObservationEnCours().getValidation());
                Utilisateur temp = new Utilisateur();
                temp.setId(e.getStatusObservationEnCours().getIdExpert());
                darwinCoreService.findById(temp);
                valiny.put("expert", temp.getNom() + " " + temp.getPrenom());
            }
        }
        return valiny;
    }

    @RequestMapping(value = "/continuerValiderListDwc", method = RequestMethod.GET, headers = "Accept=application/json")
    public HashMap<String, String> validerListDwc(HttpSession session, @RequestParam(value = "continuer") int continuer, @RequestParam(value = "status") int status, @RequestParam(value = "commentaires") String commentaire) throws Exception {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        HashMap<String, String> valiny = new HashMap<>();
        if (darwinCoreService.checkRole(utilisateur, ROLE_EXPERT)) {
            StatusAlreadyExistException saee = (StatusAlreadyExistException) session.getAttribute("exception");
            List<DarwinCore> dwc = saee.getObservationRestante();
            if (continuer == 1) {
                if(status == 1) darwinCoreService.validerForced(saee.getObservationEnCours(), utilisateur);
                else darwinCoreService.questionnableForced(saee.getObservationEnCours(), utilisateur, commentaire);
            }
            try {
                if(status == 1) darwinCoreService.validerAll(dwc, utilisateur);
                else darwinCoreService.questionnableAll(dwc, utilisateur, commentaire);
                valiny.put("etat", "1");
                session.removeAttribute("exception");
            } catch (StatusAlreadyExistException e) {
                session.setAttribute("exception", e);
                valiny.put("etat", "0");
                valiny.put("n", Integer.toString(e.getObservationEnCours().getId()));
                valiny.put("status", e.getStatusObservationEnCours().getValidation());
                Utilisateur temp = new Utilisateur();
                temp.setId(e.getStatusObservationEnCours().getIdExpert());
                darwinCoreService.findById(temp);
                valiny.put("expert", temp.getNom() + " " + temp.getPrenom());
            }
        }
        return valiny;
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
                return new ModelAndView("login");
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
                return new ModelAndView("login");
            }
            List<DarwinCore> liste_darwin_core = UploadFile.import_darwin_core_excel(excelfile.getInputStream());
            for (DarwinCore d : liste_darwin_core) {
                d.setIdUtilisateurUpload(u.getId());
            }
            darwinCoreService.upload(liste_darwin_core);

            if (!chercheur) {
                Integer b = 0;
                val.addObject("role", b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return val;
    }
}
