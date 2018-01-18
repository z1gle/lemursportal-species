/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import static org.wcs.lemurs.controller.BaseController.ROLE_MODERATEUR;
import org.wcs.lemurs.model.TaxonomiBase;
import org.wcs.lemurs.model.Utilisateur;
import org.wcs.lemurs.service.TaxonomiBaseService;

/**
 *
 * @author rudyr
 */
@RestController
public class TaxonomiBaseController {

    @Autowired(required = true)
    @Qualifier("taxonomiBaseService")
    private TaxonomiBaseService taxonomiBaseService;

    @RequestMapping(value = "/findByespeceTaxo", method = RequestMethod.POST, headers = "Accept=application/json")
    public List<TaxonomiBase> findByespece(@RequestBody TaxonomiBase t) throws Exception {
        return taxonomiBaseService.findMultiCritere(t);
    }

//    @RequestMapping(value = "/detailLemurien", method = RequestMethod.POST, headers = "Accept=application/json")
//    public TaxonomiBase detailLemurien(@RequestParam(value = "idLemurien") int id) throws Exception {
//        TaxonomiBase val = new TaxonomiBase();
//        val.setId(id);
//        taxonomiBaseService.findById(id);
//        return taxonomiBaseService.findMultiCritere(t);
//    }
    @RequestMapping(value = "/getallTaxo", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<TaxonomiBase> getall() throws Exception {
        List<TaxonomiBase> resultat = taxonomiBaseService.findAll();
        return resultat;
    }

    @RequestMapping(value = "/saveTaxo", method = RequestMethod.POST, headers = "Accept=application/json")
    public void saveOrupdate(HttpSession session, @RequestBody TaxonomiBase t) throws Exception {
        Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");
        if (taxonomiBaseService.checkRole(u, ROLE_MODERATEUR)) {
            taxonomiBaseService.save(t);
        }
    }

    @RequestMapping(value = "/deleteTaxo", method = RequestMethod.POST, headers = "Accept=application/json")
    public void delete(HttpSession session, @RequestBody TaxonomiBase t) throws Exception {
        Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");
        if (taxonomiBaseService.checkRole(u, ROLE_MODERATEUR)) {
            taxonomiBaseService.delete(t);
        }
    }
    
    // temp    
    @RequestMapping(value = "/getFamille", method = RequestMethod.POST, headers = "Accept=application/json")
    public List<String> getFamily() throws Exception {
        return (List<String>)(List<?>)taxonomiBaseService.executeSqlList("select distinct dwcfamily from taxonomi_base");
    }
    
    @RequestMapping(value = "/getGenre", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<String> getGenre(@RequestParam(value = "famille") String requestData) throws Exception {
        List<String> nom = new ArrayList<>();
        nom.add("dwcf");
        List<Object> param = new ArrayList<>();
        param.add(requestData);
        return (List<String>)(List<?>)taxonomiBaseService.executeSqlList("select distinct genus from taxonomi_base where dwcfamily = :dwcf", nom, param);
    }
    
    @RequestMapping(value = "/getEspece", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<String> getEspece(@RequestParam(value = "genre") String requestData) throws Exception {
        List<String> nom = new ArrayList<>();
        nom.add("dwcgen");
        List<Object> param = new ArrayList<>();
        param.add(requestData);
        return (List<String>)(List<?>)taxonomiBaseService.executeSqlList("select distinct scientificname from taxonomi_base where genus = :dwcgen", nom, param);
    }
    
    @RequestMapping(value = "/assigner", method = RequestMethod.POST, headers = "Accept=application/json")
    public ModelAndView validerAll(HttpSession session, @RequestParam(value = "valeur[]") String[] valeur, @RequestParam(value = "idExpert") int idExpert) throws Exception {
        taxonomiBaseService.checkFamille(valeur, idExpert);
        ModelAndView valiny = new ModelAndView("redirect:profil");
        return valiny;
    }
}
