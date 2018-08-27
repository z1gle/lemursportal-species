/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wcs.lemurs.model.DarwinCore;
import org.wcs.lemurs.model.DownloadInformation;
import org.wcs.lemurs.model.Utilisateur;
import org.wcs.lemurs.modele_association.AssociationDownloadInformationObservation;
import org.wcs.lemurs.modele_vue.VueValidationDarwinCore;
import org.wcs.lemurs.service.DarwinCoreService;
import org.wcs.lemurs.service.DownloadInformationService;

/**
 *
 * @author rudyr
 */
@RestController
public class DownloadInformationController {

    @Autowired(required = true)
    @Qualifier("downloadInformationService")
    private DownloadInformationService downloadInformationService;
    @Autowired(required = true)
    @Qualifier("darwinCoreService")
    private DarwinCoreService darwinCoreService;

//    @RequestMapping(value = "/modeles/{element}", method = RequestMethod.GET, headers = "Accept=application/json")
//    public List<String> list(@PathVariable("element") String element,
//            @RequestParam(value = "page", required = false) Integer page,
//            @RequestParam(value = "limite", required = false) Integer limite) throws Exception {
//        List<String> response = new ArrayList<>();
//        if (page == null) page = 1;
//        if (limite == null) limite = 10;
//        List<Modele> modeles = (List<Modele>) (List<?>) baseService.findAll(new Modele(), page, limite);
//        for (Modele m : modeles) {
//            String methode = "get" + element.substring(0, 1).toUpperCase() + element.substring(1);
//            response.add((String) m.getClass().getMethod(methode, null).invoke(m, null));
//        }
//        return response;
//    }
//    @RequestMapping(value = "/modeles", method = RequestMethod.GET, headers = "Accept=application/json")
//    public List<Modele> list(@RequestParam(value = "page", required = false) Integer page,
//            @RequestParam(value = "limite", required = false) Integer limite) throws Exception {        
//        if (page == null) page = 1;
//        if (limite == null) limite = 10;
//        return (List<Modele>) (List<?>) baseService.findAll(new Modele(), page, limite);
//    }
//    @RequestMapping(value = "/modeles/total", method = RequestMethod.GET, headers = "Accept=application/json")
//    public Integer total() throws Exception {                
//        return baseService.countTotal(new Modele());
//    }
    @RequestMapping(value = "/downloadInformations", method = RequestMethod.POST, headers = "Accept=application/json")
    public Boolean add(@RequestBody DownloadInformation di,
            @RequestParam String espece,
            @RequestParam Integer validation,
            @RequestParam Integer validationMine,
            @RequestParam(value = "etat[]") List<String> etat,
            @RequestParam(value = "col[]") int[] colonnes,
            @RequestParam Integer page,
            HttpSession session) throws Exception {
        try {
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
            List<VueValidationDarwinCore> listeTemp = (List<VueValidationDarwinCore>) (List<?>) darwinCoreService.findAll(u, vvdc);
            List<AssociationDownloadInformationObservation> adio = new ArrayList<>();
            for (VueValidationDarwinCore dwc : listeTemp) {
                AssociationDownloadInformationObservation adioTemp = new AssociationDownloadInformationObservation();
                adioTemp.setIdObservation(dwc.getId());
                adio.add(adioTemp);
            }
            di.setListeObservations(adio);
            downloadInformationService.save(di);
            return Boolean.TRUE;
        } catch (Exception e) {
            System.err.println(e);
            return Boolean.FALSE;
        }
    }

//    @RequestMapping(value = "/modele/{id}", method = RequestMethod.POST, headers = "Accept=application/json")
//    public Boolean update(HttpSession session, @RequestParam("modele") MultipartFile modeleAsc, @RequestParam String fileName, @PathVariable Integer id) throws Exception {                
//        try {
//            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
//            String cheminReal = session.getServletContext().getRealPath("/resources/assets/modele/ascii/");
//            Modele modele = new Modele();
//            modele.setName(fileName);
//            modele.setId(id);
//            modeleService.enregistrerModele(modeleAsc, utilisateur, cheminReal, modele);
//            return Boolean.TRUE;
//        } catch (Exception e) {
//            System.err.println(e);
//            return Boolean.FALSE;
//        }
//    }
//    
//    @RequestMapping(value = "/modele/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
//    public Boolean delete(HttpSession session, @PathVariable Integer id) throws Exception {                
//        try {
//            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
//            String cheminReal = session.getServletContext().getRealPath("/resources/assets/modele/ascii/");
//            Modele modele = new Modele();            
//            modele.setId(id);
//            modeleService.delete(modele);
//            return Boolean.TRUE;
//        } catch (Exception e) {
//            System.err.println(e);
//            return Boolean.FALSE;
//        }
//    }
}
