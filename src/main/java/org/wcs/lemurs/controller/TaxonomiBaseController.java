/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.controller;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import org.wcs.lemurs.model.BaseModel;
import org.wcs.lemurs.model.PhotoTaxonomi;
import org.wcs.lemurs.model.TaxonomiBase;
import org.wcs.lemurs.model.Utilisateur;
import org.wcs.lemurs.model.VideoTaxonomi;
import org.wcs.lemurs.modele_vue.VueRechercheTaxonomi;
import org.wcs.lemurs.service.ExportService;
import org.wcs.lemurs.service.TaxonomiBaseService;
import org.wcs.lemurs.util.ReportGenerator;
import org.wcs.lemurs.util.UploadFile;

/**
 *
 * @author rudyr
 */
@RestController
public class TaxonomiBaseController {

    @Autowired(required = true)
    @Qualifier("taxonomiBaseService")
    private TaxonomiBaseService taxonomiBaseService;

    @Autowired(required = true)
    @Qualifier("exportService")
    private ExportService exportService;

    @RequestMapping(value = "/findByespeceTaxo", method = RequestMethod.POST, headers = "Accept=application/json")
    public List<BaseModel> findByespece(@RequestBody VueRechercheTaxonomi t) throws Exception {
        return (List<BaseModel>) (List<?>) taxonomiBaseService.findMultiCritere(t, "genus", 0);
    }
    
    //Check pour return BaseModel
    @RequestMapping(value = "/findByespeceTaxoId", method = RequestMethod.POST, headers = "Accept=application/json")
    public VueRechercheTaxonomi findByespeceId(@RequestBody VueRechercheTaxonomi t) throws Exception {
        taxonomiBaseService.findById(t);
        return t;
    }

//    @RequestMapping(value = "/detailLemurien", method = RequestMethod.POST, headers = "Accept=application/json")
//    public TaxonomiBase detailLemurien(@RequestParam(value = "idLemurien") int id) throws Exception {
//        TaxonomiBase val = new TaxonomiBase();
//        val.setId(id);
//        taxonomiBaseService.findById(id);
//        return taxonomiBaseService.findMultiCritere(t);
//    }
    @CrossOrigin
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
        return (List<String>) (List<?>) taxonomiBaseService.executeSqlList("select distinct dwcfamily from taxonomi_base");
    }

    @RequestMapping(value = "/getGenre", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<String> getGenre(@RequestParam(value = "famille") String requestData) throws Exception {
        List<String> nom = new ArrayList<>();
        nom.add("dwcf");
        List<Object> param = new ArrayList<>();
        param.add("%"+requestData+"%");
        return (List<String>) (List<?>) taxonomiBaseService.executeSqlList("select distinct genus from taxonomi_base where dwcfamily ilike :dwcf", nom, param);
    }

    @RequestMapping(value = "/getEspece", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<String> getEspece(@RequestParam(value = "genre") String requestData) throws Exception {
        List<String> nom = new ArrayList<>();
        nom.add("dwcgen");
        List<Object> param = new ArrayList<>();
        param.add("%"+requestData+"%");
        return (List<String>) (List<?>) taxonomiBaseService.executeSqlList("select distinct scientificname from taxonomi_base where genus ilike :dwcgen", nom, param);
    }

    @RequestMapping(value = "/assigner", method = RequestMethod.POST, headers = "Accept=application/json")
    public ModelAndView validerAll(HttpSession session, @RequestParam(value = "valeur[]") String[] valeur, @RequestParam(value = "idExpert") int idExpert) throws Exception {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (!taxonomiBaseService.checkRole(utilisateur, ROLE_ADMINISTRATEUR)) {
            return new ModelAndView("redirect:login");
        }
        Utilisateur exp = new Utilisateur();
        exp.setId(idExpert);
        taxonomiBaseService.findById(exp);
        if (taxonomiBaseService.checkRole(exp, ROLE_EXPERT)) {
            taxonomiBaseService.checkFamille(valeur, idExpert);
        }
        ModelAndView valiny = new ModelAndView("redirect:detailUtilisateur?idUtilisateur=" + idExpert);
        return valiny;
    }

    @RequestMapping(value = "/taxonomiCsv")
    public void taxonomiCsv(HttpSession session, HttpServletResponse response) throws Exception {
        List<TaxonomiBase> liste = taxonomiBaseService.findAll();
        response.setHeader("Content-Type", "text/csv");
        response.setHeader("Content-Disposition", "attachment;filename=\"taxonomi.csv\"");
        UploadFile upf = new UploadFile();
        upf.writeCsv(liste, ';', response.getOutputStream());
    }

    @RequestMapping(value = "/getDetailTaxo")
    public ModelAndView getDetailTaxo(HttpSession session, @RequestParam("id") Integer id) {
        ModelAndView valiny = new ModelAndView("page-detail");
        Integer moderateur = -1;
        try {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            if (taxonomiBaseService.checkRole(utilisateur, ROLE_MODERATEUR)) {
                moderateur = 0;
            }
        } catch (Exception e) {
        }
        TaxonomiBase taxo = new TaxonomiBase();
        taxo.setId(id);
        try {
            taxonomiBaseService.findById(taxo);
        } catch (Exception ex) {
            Logger.getLogger(DarwinCoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
        valiny.addObject("taxo", taxo);
        valiny.addObject("moderateur", moderateur);
        return valiny;
    }
    
    @RequestMapping(value = "/getListPhotoTaxonomi", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<PhotoTaxonomi> getListPhotoTaxonomi(@RequestParam(value = "idTaxonomi") Integer idTaxonomi) throws Exception {
       PhotoTaxonomi photo = new PhotoTaxonomi();
       photo.setIdTaxonomi(idTaxonomi);
       return (List<PhotoTaxonomi>)(List<?>)taxonomiBaseService.findMultiCritere(photo);
    }

    @RequestMapping(value = "/uploadImageTaxonomi")
    public List<PhotoTaxonomi> uploadImageTaxonomi(ModelMap model, HttpSession session, @RequestParam("profil") Integer profil, @RequestParam("photo") MultipartFile photo, @RequestParam("idTaxonomi") Integer idTaxonomi, @RequestParam("datePrisePhoto") String datePrisePhoto) {        
        try {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            if (taxonomiBaseService.checkRole(utilisateur, ROLE_MODERATEUR)) {
                TaxonomiBase taxonomi = new TaxonomiBase();
                taxonomi.setId(idTaxonomi);                
                String realPath = session.getServletContext().getRealPath("/");
//                System.out.println(realPath);
                return taxonomiBaseService.enregistrerPhoto(photo, taxonomi, utilisateur, profil == 1, realPath, datePrisePhoto);
            }            
        } catch (Exception e) {            
            e.printStackTrace();
        }        
        return null;
    }
    
    @RequestMapping(value = "/uploadVideoTaxonomi")
    public List<VideoTaxonomi> uploadVideoTaxonomi(HttpSession session, @RequestParam("lien") String lien, @RequestParam("idTaxonomi") Integer idDarwinCore) {
        try {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            if (taxonomiBaseService.checkRole(utilisateur, ROLE_MODERATEUR)) {
                TaxonomiBase taxonomi = new TaxonomiBase();
                taxonomi.setId(idDarwinCore);
                taxonomiBaseService.findById(taxonomi);                
                VideoTaxonomi vdc = new VideoTaxonomi();
                vdc.setDateVideo(Calendar.getInstance().getTime());
                vdc.setIdTaxonomi(idDarwinCore);
                vdc.setIdUtilisateurUpload(utilisateur.getId());
                vdc.setLien(lien);
                taxonomiBaseService.save(vdc);
                vdc = new VideoTaxonomi();
                vdc.setIdTaxonomi(idDarwinCore);
                return (List<VideoTaxonomi>)(List<?>)taxonomiBaseService.findMultiCritere(vdc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    
//    @RequestMapping(value = "/uploadByLink")
//    public void uploadByLink(HttpSession session, @RequestParam("url") String url) {        
//        try {
//            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
//            if (taxonomiBaseService.checkRole(utilisateur, ROLE_MODERATEUR)) {
//                taxonomiBaseService.uploadTaxonomi(url);
//            }            
//        } catch (Exception e) {         
//            e.printStackTrace();
//        }        
//    }
    
    @RequestMapping(value = "/ficheTaxonomi")
    public void ficheTaxonomi(HttpSession session, HttpServletResponse response, @RequestParam(value = "id") int id) throws Exception {             
        TaxonomiBase donneeToDownload = new TaxonomiBase();
        donneeToDownload.setId(id);
        taxonomiBaseService.findById(donneeToDownload);
        response.setHeader("Content-Type", "application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=\"fiche_"+donneeToDownload.getScientificname().replaceAll(" ", "_")+".pdf\"");
        try {
            File file = File.createTempFile("temp", ".pdf");                                   
            String realPath = session.getServletContext().getRealPath("/");
            PhotoTaxonomi profil = new PhotoTaxonomi();
            profil.setProfil(Boolean.TRUE);
            profil.setIdTaxonomi(donneeToDownload.getId());
            List<PhotoTaxonomi> liste = (List<PhotoTaxonomi>)(List<?>)taxonomiBaseService.findMultiCritere(profil);
            String s = "";
            if(liste!=null) {
                if(!liste.isEmpty()) {
                    s = liste.get(0).getChemin();
                }
            }
            System.out.println(realPath);
            new ReportGenerator().generateJasperReportPDF(file, donneeToDownload, realPath, s);
//            exportService.genererPdfSujet(file, donneeToDownload, realPath);
            Files.copy(file.toPath(), response.getOutputStream());
        } catch (Exception ex) {
            throw ex;
        }        
    }
}
