/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import static org.wcs.lemurs.controller.BaseController.ROLE_ADMINISTRATEUR;
import static org.wcs.lemurs.controller.BaseController.ROLE_EXPERT;
import static org.wcs.lemurs.controller.BaseController.ROLE_MODERATEUR;
import org.wcs.lemurs.exception.StatusAlreadyExistException;
import org.wcs.lemurs.model.BaseModel;
import org.wcs.lemurs.model.CommentaireDarwinCore;
import org.wcs.lemurs.model.DarwinCore;
import org.wcs.lemurs.model.ObservationParAdmin;
import org.wcs.lemurs.model.PhotoDarwinCore;
import org.wcs.lemurs.model.Utilisateur;
import org.wcs.lemurs.model.ValidationDarwinCore;
import org.wcs.lemurs.model.VideoDarwinCore;
import org.wcs.lemurs.model.json.Retour;
import org.wcs.lemurs.modele_vue.VueCommentaireDarwinCore;
import org.wcs.lemurs.modele_vue.VueDarwinCoreRechercheGlobale;
import org.wcs.lemurs.modele_vue.VueRechercheDarwinCore;
import org.wcs.lemurs.modele_vue.VueRoleUtilisateur;
import org.wcs.lemurs.modele_vue.VueValidationDarwinCore;
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
        ModelAndView valiny = new ModelAndView("page-detail_observation");
        Utilisateur u;
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
                }
                chercheur = 0;
                
            }
        } catch (Exception ex) {
//            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            u = new Utilisateur();
//            u.setId(-1);
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
//            Logger.getLogger(DarwinCoreController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
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
            if ((darwinCoreService.checkRole(utilisateur, ROLE_EXPERT) || (dwc.getIdUtilisateurUpload() == utilisateur.getId())) && !cdc.getCommentaire().isEmpty()) {
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
    public List<VueCommentaireDarwinCore> getComment(HttpSession session, @RequestBody DarwinCore dwc) throws Exception {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (darwinCoreService.checkRole(utilisateur, ROLE_EXPERT) || (utilisateur.getId().intValue() == dwc.getIdUtilisateurUpload().intValue())) {
            VueCommentaireDarwinCore cdc = new VueCommentaireDarwinCore();
            cdc.setIdDarwinCore(dwc.getId());
            return (List<VueCommentaireDarwinCore>) (List<?>) darwinCoreService.findMultiCritere(cdc, "dateCommentaire", 0);
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
    
    @RequestMapping(value = "/findDwcMulticritere", method = RequestMethod.POST, headers = "Accept=application/json")
    public List<DarwinCore> findByespeces(HttpSession session, @RequestBody HashMap<String, Object> requestData) throws Exception {
        Utilisateur u = new Utilisateur();
        String espece = requestData.get("espece").toString();
        int validation = (Integer) (requestData.get("validation"));
        int validationMine = (Integer) (requestData.get("validationMine"));
        List<String> etat = (List<String>) requestData.get("etat");
        VueValidationDarwinCore vvdc = new VueValidationDarwinCore();
        if (!espece.isEmpty()) {
            vvdc.setScientificname(espece);
        }
        if (validationMine != -999) {
            u = (Utilisateur) session.getAttribute("utilisateur");
            if (validationMine == -1000) {
                vvdc.setIdUtilisateurUpload(u.getId());
            } else {
                vvdc.setValidationexpert(validationMine);
                vvdc.setIdUtilisateurUpload(u.getId());
            }
        } else if (validation != -999) {
            vvdc.setValidationexpert(validation);
        }
        if (etat.size() != 0 && etat.size() < 2) {
            if (Integer.parseInt(etat.get(0)) == 0) {
                vvdc.setPublique(Boolean.FALSE);
            } else if (Integer.parseInt(etat.get(0)) == 1) {
                vvdc.setPublique(Boolean.TRUE);
            }
        }
        return darwinCoreService.findAll(u, vvdc);
    }
    
    @RequestMapping(value = "/findDwcMulticritereGet", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<DarwinCore> findByespeces(HttpSession session, @RequestParam String espece, @RequestParam Integer validation, @RequestParam Integer validationMine, @RequestParam(value = "etat[]") List<String> etat) throws Exception {
        Utilisateur u = new Utilisateur();
        try {
            u = (Utilisateur) session.getAttribute("utilisateur");
        } catch (Exception e) {
            u = new Utilisateur();
            System.out.println("aucun utilisateur connecté");
        }
//        String espece = requestData.get("espece").toString();
//        int validation = (Integer) (requestData.get("validation"));
//        int validationMine = (Integer) (requestData.get("validationMine"));
//        List<String> etat = (List<String>) requestData.get("etat");
        VueValidationDarwinCore vvdc = new VueValidationDarwinCore();
        if (!espece.isEmpty()) {
            vvdc.setScientificname(espece);
        }
        if (validationMine != -999) {
            if (validationMine == -1000) {
                vvdc.setIdUtilisateurUpload(u.getId());
            } else {
                vvdc.setValidationexpert(validationMine);
                vvdc.setIdUtilisateurUpload(u.getId());
            }
        } else if (validation != -999) {
            vvdc.setValidationexpert(validation);
        }
        if (etat.size() != 0 && etat.size() < 2) {
            if (Integer.parseInt(etat.get(0)) == 0) {
                vvdc.setPublique(Boolean.FALSE);
                vvdc.setIdUtilisateurUpload(u.getId());
            } else if (Integer.parseInt(etat.get(0)) == 1) {
                vvdc.setPublique(Boolean.TRUE);
            }
        }
        // last modif
        List<DarwinCore> val = darwinCoreService.findAll(u, vvdc);
        if (validationMine != -999 && validation != -999) {
            vvdc.setValidationexpert(validation);
            vvdc.setIdUtilisateurUpload(null);
            val.addAll(darwinCoreService.findAll(u, vvdc));
        }
        // end last modif
        return val;
    }
    
    @RequestMapping(value = "/getallDwc", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<DarwinCore> getall() throws Exception {
        return darwinCoreService.findAll();
    }
    
    @RequestMapping(value = "/getNew", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<HashMap<String, Object>> getNew(@RequestParam Integer nbr) throws Exception {
        List<HashMap<String, Object>> valiny = new ArrayList<>();
        List<BaseModel> temp = darwinCoreService.findAll(new DarwinCore(), "id", 1, 1, nbr);
        for (BaseModel bm : temp) {
            HashMap<String, Object> tempInterieur = new HashMap<>();
            tempInterieur.put("dwc", bm);
            tempInterieur.put("total", temp.size());
            tempInterieur.put("validation", 0);
            valiny.add(tempInterieur);
        }
        return valiny;
    }
    
    @RequestMapping(value = "/majAdmin", method = RequestMethod.GET, headers = "Accept=application/json")
    public ModelAndView majAdmin(HttpSession session) throws Exception {
        Utilisateur u = null;
        try {
            u = (Utilisateur) session.getAttribute("utilisateur");
            if (darwinCoreService.checkRole(u, ROLE_ADMINISTRATEUR) || darwinCoreService.checkRole(u, ROLE_MODERATEUR)) {
                ObservationParAdmin opa = new ObservationParAdmin();
                opa.setIdUtilisateur(u.getId());
                opa = (ObservationParAdmin) darwinCoreService.findAll(opa).get(0);
                opa.setNbrObservation(darwinCoreService.countTotal(new DarwinCore()));
                darwinCoreService.save(opa);
            }
        } catch (NullPointerException npe) {
            
        } catch (Exception ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ModelAndView("redirect:darwinportal");
    }
    
    @RequestMapping(value = "/findByespeceDwc", method = RequestMethod.POST, headers = "Accept=application/json")
    public List<HashMap<String, Object>> getall(HttpSession session,
            @RequestBody VueValidationDarwinCore dwcs) throws Exception {
        int page = 1;
        int nombre = 20;
        Long total = null;
        try {
//            total = (BigInteger) darwinCoreService.executeSqlList("select count(*) from vue_validation_darwin_core").get(0);
            //check e-mail            
            //mailService.sendMailToExpert(darwinCoreService.findMutliCritere(new DarwinCore()));
            //
//            ReportGenerator rg = new ReportGenerator();
//            rg.main(new String[]{"test", "tata"});
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            List<HashMap<String, Object>> valiny = darwinCoreService.findWithCheckAndEtat(utilisateur, dwcs, nombre, page);
            total = darwinCoreService.getDarwinCoreDao().countTotalDwc(utilisateur, dwcs);
            try {
                HashMap<String, Object> temp = valiny.get(0);
                temp.put("total", total);
            } catch (java.lang.IndexOutOfBoundsException iaoob) {
                System.out.println("Il n'y a aucune observation dans la base de donnée");
//                throw iaoob;
            }
//            HashMap<String, Object> temp = valiny.get(0);
//            temp.put("total", total);
            return valiny;
        } catch (Exception e) {
            e.printStackTrace();
            Utilisateur utilisateur = new Utilisateur();
            List<HashMap<String, Object>> valiny = darwinCoreService.findWithCheckAndEtat(utilisateur, dwcs, nombre, page);
            HashMap<String, Object> temp = valiny.get(0);
            temp.put("total", total);
            return valiny;
        }
    }
    
    @RequestMapping(value = "/findForValidation", method = RequestMethod.POST, headers = "Accept=application/json")
    public List<HashMap<String, Object>> getToValidate(HttpSession session,
            @RequestBody(required = false) VueValidationDarwinCore dwcs,
            @RequestParam(required = false, value = "page") Integer page) throws Exception {
        if (page == null) {
            page = 1;
        }
        int nombre = 20;
        Long total = null;
        if (dwcs == null) {
            dwcs = new VueValidationDarwinCore();
        }
        try {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            List<HashMap<String, Object>> valiny = darwinCoreService.findForValidation(utilisateur, dwcs, nombre, page);
            total = darwinCoreService.getDarwinCoreDao().CountAllToValidate(utilisateur, dwcs);
            try {
                HashMap<String, Object> temp = valiny.get(0);
                temp.put("total", total);
            } catch (java.lang.IndexOutOfBoundsException iaoob) {
                System.out.println("Il n'y a aucune observation dans la base de donnée");
            }
            return valiny;
        } catch (Exception e) {
            e.printStackTrace();
            Utilisateur utilisateur = new Utilisateur();
            List<HashMap<String, Object>> valiny = darwinCoreService.findWithCheckAndEtat(utilisateur, dwcs, nombre, page);
            HashMap<String, Object> temp = valiny.get(0);
            temp.put("total", total);
            return valiny;
        }
    }
    
    @RequestMapping(value = "/findForValidationGlobal", method = RequestMethod.POST, headers = "Accept=application/json")
    public List<HashMap<String, Object>> getToValidateGlobal(HttpSession session,
            @RequestBody(required = false) VueDarwinCoreRechercheGlobale dwcs,
            @RequestParam(required = false, value = "page") Integer page) throws Exception {
        if (page == null) {
            page = 1;
        }
        int nombre = 20;
        Long total = null;
        if (dwcs == null) {
            dwcs = new VueDarwinCoreRechercheGlobale();
        }
        try {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            List<HashMap<String, Object>> valiny = darwinCoreService.findForValidation(utilisateur, dwcs, nombre, page);
            total = darwinCoreService.getDarwinCoreDao().CountAllToValidate(utilisateur, dwcs);
            try {
                HashMap<String, Object> temp = valiny.get(0);
                temp.put("total", total);
            } catch (java.lang.IndexOutOfBoundsException iaoob) {
                System.out.println("Il n'y a aucune observation dans la base de donnée");
            }
            return valiny;
        } catch (Exception e) {
            e.printStackTrace();
            Utilisateur utilisateur = new Utilisateur();
            List<HashMap<String, Object>> valiny = darwinCoreService.findWithCheckAndEtat(utilisateur, dwcs, nombre, page);
            HashMap<String, Object> temp = valiny.get(0);
            temp.put("total", total);
            return valiny;
        }
    }
    
    @RequestMapping(value = "/findByespeceDwcAvancee", method = RequestMethod.GET)
    public List<HashMap<String, Object>> getallAvancee(HttpSession session,
            @RequestParam String espece,
            @RequestParam Integer validation,
            @RequestParam Integer validationMine,
            @RequestParam(value = "etat[]") List<String> etat) throws Exception {
        int page = 1;
        int nombre = 20;
        Long total = null;
        
        Utilisateur u = new Utilisateur();
        try {
            u = (Utilisateur) session.getAttribute("utilisateur");
        } catch (Exception e) {
            u = new Utilisateur();
            System.out.println("aucun utilisateur connecté");
        }
        VueValidationDarwinCore vvdc = new VueValidationDarwinCore();
        if (!espece.isEmpty()) {
            vvdc.setScientificname(espece);
        }
        if (validationMine != -999) {
            if (validationMine == -1000) {
                vvdc.setIdUtilisateurUpload(u.getId());
            } else {
                vvdc.setValidationexpert(validationMine);
                vvdc.setIdUtilisateurUpload(u.getId());
            }
        } else if (validation != -999) {
            vvdc.setValidationexpert(validation);
        }
        if (etat.size() != 0 && etat.size() < 2) {
            if (Integer.parseInt(etat.get(0)) == 0) {
                vvdc.setPublique(Boolean.FALSE);
                vvdc.setIdUtilisateurUpload(u.getId());
            } else if (Integer.parseInt(etat.get(0)) == 1) {
                vvdc.setPublique(Boolean.TRUE);
            }
        }
        try {
            total = darwinCoreService.getDarwinCoreDao().countTotalDwc(u, vvdc);
            
            List<HashMap<String, Object>> valiny = darwinCoreService.findWithCheckAndEtat(u, vvdc, nombre, page);
            try {
                HashMap<String, Object> temp = valiny.get(0);
                temp.put("total", total);
            } catch (java.lang.IndexOutOfBoundsException iaoob) {
                System.out.println("Il n'y a aucune observation dans la base de donnée");
            }
            return valiny;
        } catch (Exception e) {
            e.printStackTrace();
            Utilisateur utilisateur = new Utilisateur();
            List<HashMap<String, Object>> valiny = darwinCoreService.findWithCheckAndEtat(utilisateur, vvdc, nombre, page);
            HashMap<String, Object> temp = valiny.get(0);
            temp.put("total", total);
            return valiny;
        }
    }
    
    @RequestMapping(value = "/findByespeceDwcPaginated", method = RequestMethod.POST, headers = "Accept=application/json")
    public List<HashMap<String, Object>> getallPaginated(HttpSession session,
            @RequestParam String dwcs,
            @RequestParam Integer page) throws Exception {
        VueValidationDarwinCore vvdc = new VueValidationDarwinCore();
        int nombre = 20;
        BigInteger total = null;
        try {
            vvdc.setScientificname(dwcs);
            if (dwcs.compareTo("null") == 0) {
                vvdc.setScientificname(null);
                total = (BigInteger) darwinCoreService.executeSqlList("select count(*) from vue_validation_darwin_core").get(0);
            } else {
                List<String> name = new ArrayList<>();
                name.add("sn");
                List<Object> parameter = new ArrayList<>();
                parameter.add(dwcs);
                total = (BigInteger) darwinCoreService.executeSqlList("select count(*) from vue_validation_darwin_core where scientificname = :sn", name, parameter);
            }
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            List<HashMap<String, Object>> valiny = darwinCoreService.findWithCheckAndEtat(utilisateur, vvdc, nombre, page);
            HashMap<String, Object> temp = valiny.get(0);
            temp.put("total", total);
            return valiny;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Utilisateur utilisateur = new Utilisateur();
                List<HashMap<String, Object>> valiny = darwinCoreService.findWithCheckAndEtat(utilisateur, vvdc, nombre, page);
                HashMap<String, Object> temp = valiny.get(0);
                temp.put("total", total);
                return valiny;
            } catch (Exception ex) {
                throw ex;
            }
        }
    }
    
    @RequestMapping(value = "/findByespeceDwcPaginatedSearch", method = RequestMethod.POST, headers = "Accept=application/json")
    public List<HashMap<String, Object>> getallPaginatedSearch(HttpSession session,
            @RequestParam String espece,
            @RequestParam Integer validation,
            @RequestParam Integer validationMine,
            @RequestParam(value = "etat[]") List<String> etat,
            @RequestParam Integer page) throws Exception {
        VueValidationDarwinCore vvdc = new VueValidationDarwinCore();
        int nombre = 20;
        Long total = null;
        
        Utilisateur u = new Utilisateur();
        try {
            u = (Utilisateur) session.getAttribute("utilisateur");
        } catch (Exception e) {
            u = new Utilisateur();
            System.out.println("aucun utilisateur connecté");
        }
//        VueValidationDarwinCore vvdc = new VueValidationDarwinCore();
        if (!espece.isEmpty()) {
            vvdc.setScientificname(espece);
        }
        if (validationMine != -999) {
            if (validationMine == -1000) {
                vvdc.setIdUtilisateurUpload(u.getId());
            } else {
                vvdc.setValidationexpert(validationMine);
                vvdc.setIdUtilisateurUpload(u.getId());
            }
        } else if (validation != -999) {
            vvdc.setValidationexpert(validation);
        }
        if (etat.size() != 0 && etat.size() < 2) {
            if (Integer.parseInt(etat.get(0)) == 0) {
                vvdc.setPublique(Boolean.FALSE);
                vvdc.setIdUtilisateurUpload(u.getId());
            } else if (Integer.parseInt(etat.get(0)) == 1) {
                vvdc.setPublique(Boolean.TRUE);
            }
        }
        total = darwinCoreService.getDarwinCoreDao().countTotalDwc(u, vvdc);
        
        try {
            List<HashMap<String, Object>> valiny = darwinCoreService.findWithCheckAndEtat(u, vvdc, nombre, page);
            HashMap<String, Object> temp = valiny.get(0);
            temp.put("total", total);
            return valiny;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Utilisateur utilisateur = new Utilisateur();
                List<HashMap<String, Object>> valiny = darwinCoreService.findWithCheckAndEtat(utilisateur, vvdc, nombre, page);
                HashMap<String, Object> temp = valiny.get(0);
                temp.put("total", total);
                return valiny;
            } catch (Exception ex) {
                throw ex;
            }
        }
    }

//    @RequestMapping(value = "/getObservationEnAttenteDeValidation", method = RequestMethod.POST, headers = "Accept=application/json")
//    public List<HashMap<String, Object>> getObservationEnAttenteDeValidation(HttpSession session) throws Exception {
//        List<HashMap<String, Object>> valiny = new ArrayList<>();
//        try {
//            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
//            return darwinCoreService.findObservationAndEtatCheck(utilisateur);
//        } catch (Exception e) {
//            throw e;
//        }
//    }
    @RequestMapping(value = "/getObservationEnAttenteDeValidation", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<HashMap<String, Object>> getObservationEnAttenteDeValidation(HttpSession session,
            @RequestParam("etatValidation") Integer etatValidation) throws Exception {
        List<HashMap<String, Object>> valiny = new ArrayList<>();
        try {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            if (etatValidation == 20 || etatValidation == 21 || etatValidation == -21 || etatValidation == -2999) {
                etatValidation = Integer.parseInt(Integer.toString(etatValidation).replaceAll("2", ""));
                return darwinCoreService.findObservationAndEtatCheckOf(utilisateur, etatValidation);
            } else {
                return darwinCoreService.findObservationAndEtatCheck(utilisateur, etatValidation);
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
    @RequestMapping(value = "/observationAValider")
    public ModelAndView observationAValider(HttpSession session,
            @RequestParam("etatValidation") Integer etatValidation
    ) {
        ModelAndView val = new ModelAndView("observationAValider");
        val.addObject("etatValidation", etatValidation);
        return val;
    }
    
    @RequestMapping(value = "/saveDwc", method = RequestMethod.POST, headers = "Accept=application/json")
    public void saveOrupdate(HttpSession session,
            @RequestBody DarwinCore dwc) throws Exception {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateur.getId().intValue() == dwc.getIdUtilisateurUpload().intValue()) {
            List<DarwinCore> ldc = new ArrayList<>();
            ldc.add(dwc);
            darwinCoreService.upload(ldc);
        }
    }
    
    @RequestMapping(value = "/delDwc", method = RequestMethod.POST, headers = "Accept=application/json")
    public void del(HttpSession session,
            @RequestParam Integer idDwc) throws Exception {
        DarwinCore dwc = darwinCoreService.findById(idDwc);
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateur.getId().intValue() == dwc.getIdUtilisateurUpload().intValue()) {
            darwinCoreService.delete(dwc);
        } else {
            throw new Exception("Vous n'avez pas l'accréditation nécessaire à cette action");
        }
    }
    
    @RequestMapping(value = "/validerDwc", method = RequestMethod.POST, headers = "Accept=application/json")
    public void valider(HttpSession session,
            @RequestBody DarwinCore dwc) throws Exception {
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
    public void validerAll(HttpSession session,
            @RequestParam(value = "dwc[]") int[] dwc) throws Exception {
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
    public HashMap<String, String> validerListDwc(HttpSession session,
            @RequestParam(value = "dwc[]") int[] dwc,
            @RequestParam(value = "status") int status,
            @RequestParam(value = "commentaires") String commentaire) throws Exception {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        HashMap<String, String> valiny = new HashMap<>();
        if (darwinCoreService.checkRole(utilisateur, ROLE_EXPERT)) {
            try {
                if (status == 1) {
                    darwinCoreService.validerAll(dwc, utilisateur);
                } else {
                    darwinCoreService.questionnableAll(dwc, utilisateur, commentaire);
                }
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
    public HashMap<String, String> validerListDwc(HttpSession session,
            @RequestParam(value = "continuer") int continuer,
            @RequestParam(value = "status") int status,
            @RequestParam(value = "commentaires") String commentaire) throws Exception {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        HashMap<String, String> valiny = new HashMap<>();
        if (darwinCoreService.checkRole(utilisateur, ROLE_EXPERT)) {
            StatusAlreadyExistException saee = (StatusAlreadyExistException) session.getAttribute("exception");
            List<DarwinCore> dwc = saee.getObservationRestante();
            if (continuer == 1) {
                if (status == 1) {
                    darwinCoreService.validerForced(saee.getObservationEnCours(), utilisateur);
                } else {
                    darwinCoreService.questionnableForced(saee.getObservationEnCours(), utilisateur, commentaire);
                }
            }
            try {
                if (status == 1) {
                    darwinCoreService.validerAll(dwc, utilisateur);
                } else {
                    darwinCoreService.questionnableAll(dwc, utilisateur, commentaire);
                }
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
    
    @RequestMapping(value = "/dwcCsv")
    public void dwcCsv(HttpSession session, HttpServletResponse response,
            @RequestParam String espece,
            @RequestParam Integer validation,
            @RequestParam Integer validationMine,
            @RequestParam(value = "etat[]") List<String> etat,
            @RequestParam(value = "col[]") int[] colonnes,
            @RequestParam Integer page) throws Exception {
        
        VueValidationDarwinCore vvdc = new VueValidationDarwinCore();
        int nombre = 20;
        Long total = null;
        
        Utilisateur u = new Utilisateur();
        try {
            u = (Utilisateur) session.getAttribute("utilisateur");
        } catch (Exception e) {
            u = new Utilisateur();
            System.out.println("aucun utilisateur connecté");
        }
//        VueValidationDarwinCore vvdc = new VueValidationDarwinCore();
        if (!espece.isEmpty()) {
            vvdc.setScientificname(espece);
        }
        if (validationMine != -999) {
            if (validationMine == -1000) {
                vvdc.setIdUtilisateurUpload(u.getId());
            } else {
                vvdc.setValidationexpert(validationMine);
                vvdc.setIdUtilisateurUpload(u.getId());
            }
        } else if (validation != -999) {
            vvdc.setValidationexpert(validation);
        }
        if (etat.size() != 0 && etat.size() < 2) {
            if (Integer.parseInt(etat.get(0)) == 0) {
                vvdc.setPublique(Boolean.FALSE);
                vvdc.setIdUtilisateurUpload(u.getId());
            } else if (Integer.parseInt(etat.get(0)) == 1) {
                vvdc.setPublique(Boolean.TRUE);
            }
        }
        
        int idU;
        try {
            idU = u.getId();
        } catch (NullPointerException npe) {
            idU = -999;
        }
        List<DarwinCore> listeTemp = darwinCoreService.findAll(u, vvdc);
        List<VueValidationDarwinCore> liste = (List<VueValidationDarwinCore>) (List<?>) listeTemp;
        response.setHeader("Content-Type", "text/csv");
        response.setHeader("Content-Disposition", "attachment;filename=\"observations.csv\"");
        UploadFile upf = new UploadFile();
        upf.writeDwcCsv(liste, colonnes, ';', response.getOutputStream(), idU);
    }
    
    @RequestMapping(value = "/getColonnesDwc", method = RequestMethod.POST, headers = "Accept=application/json")
    public List<HashMap<String, String>> getColonnesDwc(HttpSession session) throws Exception {
        List<HashMap<String, String>> valiny = new ArrayList<>();
        List<String> liste = darwinCoreService.listeColonnes(new DarwinCore());
        liste.remove(liste.size() - 1);
        for (int i = 0; i < liste.size(); i++) {
            HashMap<String, String> temp = new HashMap<>();
            temp.put("index", Integer.toString(i));
            temp.put("valeur", liste.get(i));
            valiny.add(temp);
        }
        return valiny;
    }
    
    @RequestMapping(value = "/getListPhotoDarwinCore", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<PhotoDarwinCore> getListPhotoTaxonomi(@RequestParam(value = "idDarwinCore") Integer idDarwinCore) throws Exception {
        PhotoDarwinCore photo = new PhotoDarwinCore();
        photo.setIdDarwinCore(idDarwinCore);
        return (List<PhotoDarwinCore>) (List<?>) darwinCoreService.findMultiCritere(photo);
    }
    
    @RequestMapping(value = "/getListVideoDarwinCore", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<VideoDarwinCore> getListVideoTaxonomi(@RequestParam(value = "idDarwinCore") Integer idDarwinCore) throws Exception {
        VideoDarwinCore photo = new VideoDarwinCore();
        photo.setIdDarwinCore(idDarwinCore);
        return (List<VideoDarwinCore>) (List<?>) darwinCoreService.findMultiCritere(photo);
    }
    
    @RequestMapping(value = "/uploadImageDarwinCore")
    public List<PhotoDarwinCore> uploadImageDarwinCore(ModelMap model, HttpSession session,
            @RequestParam("profil") Integer profil,
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("idDarwinCore") Integer idDarwinCore,
            @RequestParam("datePrisePhoto") String datePrisePhoto
    ) {
        try {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            DarwinCore darwinCore = new DarwinCore();
            darwinCore.setId(idDarwinCore);
            darwinCoreService.findById(darwinCore);
            if (darwinCore.getIdUtilisateurUpload().intValue() != utilisateur.getId()) {
                return null;
            }
            String realPath = session.getServletContext().getRealPath("/");
            return darwinCoreService.enregistrerPhoto(photo, darwinCore, utilisateur, profil == 1, realPath, datePrisePhoto);
        } catch (Exception e) {
        }
        return null;
    }
    
    @RequestMapping(value = "/uploadVideoDarwinCore")
    public List<VideoDarwinCore> uploadVideoDarwinCore(HttpSession session,
            @RequestParam("lien") String lien,
            @RequestParam("idDarwinCore") Integer idDarwinCore
    ) {
        try {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            DarwinCore darwinCore = new DarwinCore();
            darwinCore.setId(idDarwinCore);
            darwinCoreService.findById(darwinCore);
            if (darwinCore.getIdUtilisateurUpload().intValue() != utilisateur.getId()) {
                return null;
            }
            VideoDarwinCore vdc = new VideoDarwinCore();
            vdc.setDateVideo(Calendar.getInstance().getTime());
            vdc.setIdDarwinCore(idDarwinCore);
            vdc.setIdUtilisateurUpload(utilisateur.getId());
            vdc.setLien(lien);
            darwinCoreService.save(vdc);
            vdc = new VideoDarwinCore();
            vdc.setIdDarwinCore(idDarwinCore);
            return (List<VideoDarwinCore>) (List<?>) darwinCoreService.findMultiCritere(vdc);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/processExcel", method = RequestMethod.POST)
    public List<HashMap<String, Object>> processExcel(Model model,
            @RequestParam("excelfile") MultipartFile excelfile,
            @RequestParam("publique") Boolean publique,
            @RequestParam(required = false, value = "sep") Integer separator,
            HttpSession session,
            HttpServletRequest request) throws Exception {
        try {
            Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");
            //  check chercheur
            VueRoleUtilisateur vru = new VueRoleUtilisateur();
            vru.setIdUtilisateur(u.getId());
            List<VueRoleUtilisateur> list = (List<VueRoleUtilisateur>) (List<?>) darwinCoreService.findMultiCritere(vru);
            boolean chercheur = Boolean.TRUE;
            List<DarwinCore> liste_darwin_core;
            if (excelfile.getOriginalFilename().contains(".csv")) {
                String sep = ";";
                if (separator != null && separator.equals(0)) {
                    sep = ",";
                }
                liste_darwin_core = UploadFile.import_darwin_core_csv(excelfile.getInputStream(), sep);
            } else {
                liste_darwin_core = UploadFile.import_darwin_core_excel(excelfile.getInputStream());
            }
            for (DarwinCore d : liste_darwin_core) {
                d.setIdUtilisateurUpload(u.getId());
                if(d.getDecimallatitude().contains(",")) {
                    d.setDecimallatitude(d.getDecimallatitude().replace(",", "."));
                    d.setDecimallongitude(d.getDecimallongitude().replace(",", "."));
                }
            }
            for (DarwinCore dwc : liste_darwin_core) {
                dwc.setPublique(publique);
            }
            List<DarwinCore> upload = darwinCoreService.upload(liste_darwin_core);
            
            List<HashMap<String, Object>> valiny = darwinCoreService.formaterHashMapForAffichage(u, upload);
            for (HashMap<String, Object> v : valiny) {
                v.put("photo", "resources/assets/img/photos/default.jpg");
            }
//            darwinCoreService.correctionSyntax();
            return valiny;
        } catch (Exception e) {
            e.printStackTrace();
            List<HashMap<String, Object>> valiny = new ArrayList<>();
            HashMap<String, Object> erreur = new HashMap<>();
            erreur.put("message", e.getMessage());
            valiny.add(erreur);
            return valiny;
        }
    }
    
    @RequestMapping(value = "/uploadByLink")
    public void uploadByLink(HttpSession session,
            @RequestParam("url") String url) throws Exception {
        try {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            if (darwinCoreService.checkRole(utilisateur, ROLE_MODERATEUR) || darwinCoreService.checkRole(utilisateur, ROLE_ADMINISTRATEUR)) {
                darwinCoreService.uploadDwc(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    @RequestMapping(value = "/visualisation")
    public ModelAndView visualisation(HttpSession session
    ) {
        ModelAndView val = new ModelAndView("page-visualisation");
        return val;
    }
    
    @RequestMapping(value = "/getFamilleDwc", method = RequestMethod.POST, headers = "Accept=application/json")
    public List<String> getFamily() throws Exception {
        return (List<String>) (List<?>) darwinCoreService.executeSqlList("select distinct family from darwin_core");
    }
    
    @RequestMapping(value = "/getGenreDwc", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<String> getGenre(@RequestParam(value = "famille") String requestData) throws Exception {
        List<String> nom = new ArrayList<>();
        nom.add("dwcf");
        List<Object> param = new ArrayList<>();
        param.add("%" + requestData + "%");
        return (List<String>) (List<?>) darwinCoreService.executeSqlList("select distinct genus from darwin_core where family ilike :dwcf", nom, param);
    }
    
    @RequestMapping(value = "/getEspeceDwc", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<DarwinCore> getEspece(@RequestParam(value = "genre") String requestData) throws Exception {
        List<String> nom = new ArrayList<>();
        nom.add("dwcgen");
        List<Object> param = new ArrayList<>();
        param.add("%" + requestData + "%");
        System.out.println(requestData);
        DarwinCore dwc = new DarwinCore();
        dwc.setGenus(requestData);
        return (List<DarwinCore>) (List<?>) darwinCoreService.findMultiCritere(dwc);
    }
    
    @RequestMapping(value = "/rechercherGlobaleEspeceDcw", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<HashMap<String, Object>> rechercherGlobaleEspeceDwc(HttpSession session,
            @RequestParam String champ) throws Exception {
        
        int page = 1;
        int nombre = 20;
        Long total = null;
        
        Utilisateur u = null;
        try {
            u = (Utilisateur) session.getAttribute("utilisateur");
        } catch (Exception e) {
            System.out.println("User not logged in");
            u = null;
        }
        VueDarwinCoreRechercheGlobale vrdc = new VueDarwinCoreRechercheGlobale();
        vrdc.setChamp(champ);
        
        try {
            total = darwinCoreService.getDarwinCoreDao().countTotalDwc(u, vrdc);
            
            List<HashMap<String, Object>> valiny = darwinCoreService.findWithCheckAndEtat(u, vrdc, nombre, page);
            try {
                HashMap<String, Object> temp = valiny.get(0);
                temp.put("total", total);
            } catch (java.lang.IndexOutOfBoundsException iaoob) {
                System.out.println("Il n'y a aucune observation dans la base de donnée");
            }
            return valiny;
        } catch (Exception e) {
            e.printStackTrace();
            Utilisateur utilisateur = new Utilisateur();
            List<HashMap<String, Object>> valiny = darwinCoreService.findWithCheckAndEtat(utilisateur, vrdc, nombre, page);
            HashMap<String, Object> temp = valiny.get(0);
            temp.put("total", total);
            return valiny;
        }
    }
    
    @RequestMapping(value = "/rechercherGlobaleEspeceDcwPage", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<HashMap<String, Object>> rechercherGlobaleEspeceDwc(HttpSession session,
            @RequestParam String champ,
            @RequestParam Integer page) throws Exception {
        
        int nombre = 20;
        Long total = null;
        
        Utilisateur u = null;
        try {
            u = (Utilisateur) session.getAttribute("utilisateur");
        } catch (Exception e) {
            System.out.println("User not logged in");
            u = null;
        }
        VueDarwinCoreRechercheGlobale vrdc = new VueDarwinCoreRechercheGlobale();
        vrdc.setChamp(champ);
        
        try {
            total = darwinCoreService.getDarwinCoreDao().countTotalDwc(u, vrdc);
            
            List<HashMap<String, Object>> valiny = darwinCoreService.findWithCheckAndEtat(u, vrdc, nombre, page);
            try {
                HashMap<String, Object> temp = valiny.get(0);
                temp.put("total", total);
            } catch (java.lang.IndexOutOfBoundsException iaoob) {
                System.out.println("Il n'y a aucune observation dans la base de donnée");
            }
            return valiny;
        } catch (Exception e) {
            e.printStackTrace();
            Utilisateur utilisateur = new Utilisateur();
            List<HashMap<String, Object>> valiny = darwinCoreService.findWithCheckAndEtat(utilisateur, vrdc, nombre, page);
            HashMap<String, Object> temp = valiny.get(0);
            temp.put("total", total);
            return valiny;
        }
    }
    
    @RequestMapping(value = "/rechercherEspeceDcw", method = RequestMethod.GET, headers = "Accept=application/json")
    public HashMap<String, Object> rechercherEspeceDwc(HttpSession session,
            @RequestParam String champ) throws Exception {
        HashMap<String, Object> valiny = new HashMap<>();
        if (champ.isEmpty()) {
            valiny.put("etat", Boolean.FALSE);
            return valiny;
        }
        Utilisateur u = null;
        try {
            u = (Utilisateur) session.getAttribute("utilisateur");
        } catch (Exception e) {
            System.out.println("User not logged in");
            u = null;
        }
        VueRechercheDarwinCore vrdc = new VueRechercheDarwinCore();
        vrdc.setChamp(champ);
        List<DarwinCore> dwc = darwinCoreService.findAll(u, vrdc);
        valiny.put("etat", !dwc.isEmpty());
        valiny.put("dwc", dwc);
        return valiny;
    }
    
    @RequestMapping(value = "/rechercherEspeceDcwPaginee", method = RequestMethod.GET, headers = "Accept=application/json")
    public HashMap<String, Object> rechercherEspeceDwcPaginee(HttpSession session,
            @RequestParam String champ,
            @RequestParam Integer page,
            @RequestParam Integer limite) throws Exception {
        HashMap<String, Object> valiny = new HashMap<>();
        if (champ.isEmpty()) {
            valiny.put("etat", Boolean.FALSE);
            return valiny;
        }
        Utilisateur u = null;
        try {
            u = (Utilisateur) session.getAttribute("utilisateur");
        } catch (Exception e) {
            System.out.println("User not logged in");
            u = null;
        }
        VueRechercheDarwinCore vrdc = new VueRechercheDarwinCore();
        vrdc.setChamp(champ);
        List<DarwinCore> dwc = darwinCoreService.findAll(u, vrdc, page, limite);
        valiny.put("etat", !dwc.isEmpty());
        valiny.put("dwc", dwc);
        return valiny;
    }
    
    @PostMapping(value = "/observations", headers = "Accept=application/json")
    public Retour observationsaa(HttpSession session,
            @RequestBody(required = false) VueValidationDarwinCore dwc,
            @RequestParam(value = "page", required = false) Integer page) throws Exception {
        Retour valiny = new Retour();
        if (null == page) {
            page = 1;
        }
        Utilisateur u = null;
        try {
            u = (Utilisateur) session.getAttribute("utilisateur");
            if (!darwinCoreService.checkRole(u, ROLE_ADMINISTRATEUR)) {
//                u = (Utilisateur) darwinCoreService.findAll(u).get(0);
                dwc.setIdUtilisateurUpload(u.getId());
            }
            valiny.setEtat(Boolean.TRUE);
            valiny.setRetour(darwinCoreService.findAll(u, dwc, page, 20, true));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("User not logged in");
            valiny.setEtat(Boolean.FALSE);
            valiny.setRetour(e.getMessage());
        }
        return valiny;
    }
    
    @RequestMapping(value = "/observations/delete", method = RequestMethod.POST, headers = "Accept=application/json")
    public boolean delete(HttpSession session,
            @RequestParam(value = "liste[]") int[] liste
    ) {
        try {
            List<DarwinCore> listeDwc = new ArrayList<>();
            Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");
            if (!darwinCoreService.checkRole(u, ROLE_ADMINISTRATEUR)) {
                darwinCoreService.delete(liste, u, Boolean.FALSE);
            } else {
                darwinCoreService.delete(liste, u, Boolean.TRUE);
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }
    
    @RequestMapping(value = "/observations/update/status", method = RequestMethod.POST, headers = "Accept=application/json")
    public boolean updateStatus(HttpSession session,
            @RequestParam(value = "liste[]") int[] liste,
            @RequestParam Integer status
    ) {
        try {
            List<DarwinCore> listeDwc = new ArrayList<>();
            Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");
            for (Integer i : liste) {
                DarwinCore dwc = new DarwinCore();
                dwc.setId(i);
                darwinCoreService.findById(dwc);
                if (!darwinCoreService.checkRole(u, ROLE_ADMINISTRATEUR)) {
                    if (!dwc.getIdUtilisateurUpload().equals(u.getId())) {
                        throw new Exception("Vous n'avez pas l'acréditation nécessaire pour cette action");
                    }
                }
                listeDwc.add(dwc);
            }
            Boolean publique = false;
            if (status == 0) {
                publique = true;
            }
            for (DarwinCore dwc : listeDwc) {
                dwc.setPublique(publique);
                darwinCoreService.save(dwc);
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }
    
    @RequestMapping(value = "/correctScientificnameSyntax", headers = "Accept=application/json")
    public Boolean darwinportal(HttpSession session) {
        Utilisateur u;
        try {
            u = (Utilisateur) session.getAttribute("utilisateur");
            
            VueRoleUtilisateur vru = new VueRoleUtilisateur();
            vru.setIdUtilisateur(u.getId());
            List<VueRoleUtilisateur> list = (List<VueRoleUtilisateur>) (List<?>) darwinCoreService.findMultiCritere(vru);
            
            for (VueRoleUtilisateur v : list) {
                if (v.getDesignation().compareTo(ROLE_ADMINISTRATEUR) == 0) {
                    darwinCoreService.correctionSyntax();
                    return true;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
