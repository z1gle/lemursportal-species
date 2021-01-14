/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.wcs.lemurs.model.DarwinCore;
import org.wcs.lemurs.model.Kml;
import org.wcs.lemurs.model.ShpInfo;
import org.wcs.lemurs.model.Utilisateur;
import org.wcs.lemurs.service.DarwinCoreService;
import org.wcs.lemurs.service.ShapeFileService;

/**
 *
 * @author rudyr
 */
@RestController
public class ShpController {

    @Autowired(required = true)
    @Qualifier("shapeFileService")
    private ShapeFileService shpService;
    @Autowired(required = true)
    @Qualifier("darwinCoreService")
    private DarwinCoreService darwinCoreService;

    @GetMapping(value = "gestionShp")
    public ModelAndView darwinportal(HttpSession session) {
        ModelAndView valiny = new ModelAndView("shp");
        Utilisateur u = null;
        Integer showAddButton = -1;
        try {
            u = (Utilisateur) session.getAttribute("utilisateur");
            if (shpService.checkRole(u, BaseController.ROLE_ADMINISTRATEUR) || shpService.checkRole(u, BaseController.ROLE_MODERATEUR)) {
                showAddButton = 0;
            }
        } catch (NullPointerException npe) {

        } catch (Exception ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        valiny.addObject("showAddButton", showAddButton);
        return valiny;
    }

    @PostMapping(value = "shp")
    public HashMap<String, Object> save(HttpSession session,
            @RequestParam MultipartFile shapefile, @RequestParam String path,
            @RequestParam(required = false) String shapeLabel) {

        HashMap<String, Object> response = new HashMap<>();

        try {
            Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");
            if (!shpService.checkRole(u, BaseController.ROLE_ADMINISTRATEUR) && !shpService.checkRole(u, BaseController.ROLE_MODERATEUR)) {
                response.put("state", Boolean.FALSE);
                response.put("message", "Action not allowed");
                return response;
            }
            ShpInfo shpInfo = new ShpInfo();
            shpInfo.setShapeLabel(shapeLabel);
            shpInfo.setNomChampGeometrique("geom");
            shpInfo.setShapePrecision(0.01);
            String extension = "";
            String filename = "";
            int i = shapefile.getOriginalFilename().lastIndexOf('.');
            if (i > 0) {
                extension = shapefile.getOriginalFilename().substring(i + 1);
                filename = shapefile.getOriginalFilename().substring(0, i);
            }
            if (!extension.equalsIgnoreCase("shp")) {
                throw new Exception("The file is not a shapeFile");
            }
            shpInfo.setShapeTable(filename);
            shpInfo.setPath(path);
            shpService.save(shpInfo);
            // shpService.saveShp(shapefile, shpInfo);
        } catch (Exception ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
            response.put("state", Boolean.FALSE);
            response.put("message", ex.getMessage());
            return response;
        }
        response.put("state", Boolean.TRUE);
        return response;
    }

//    @RequestMapping(value = "/modeles/{element}", method = RequestMethod.GET, headers = "Accept=application/json")
//    public List<String> list(@PathVariable("element") String element,
//            @RequestParam(value = "page", required = false) Integer page,
//            @RequestParam(value = "limite", required = false) Integer limite) throws Exception {
//        List<String> response = new ArrayList<>();
//        if (page == null) {
//            page = 1;
//        }
//        if (limite == null) {
//            limite = 10;
//        }
//        List<Modele> modeles = (List<Modele>) (List<?>) shpService.findAll(new Modele(), page, limite);
//        for (Modele m : modeles) {
//            String methode = "get" + element.substring(0, 1).toUpperCase() + element.substring(1);
//            response.add((String) m.getClass().getMethod(methode, null).invoke(m, null));
//        }
//        return response;
//    }
//
    @GetMapping(value = "/shapefiles", headers = "Accept=application/json")
    public List<ShpInfo> list(@RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limite", required = false) Integer limite) throws Exception {
        if (page == null) {
            page = 1;
        }
        if (limite == null) {
            limite = 10;
        }
        return (List<ShpInfo>) (List<?>) shpService.findAll(new ShpInfo(), page, limite);
    }

//    @GetMapping(value = "/kml/{idShp}", headers = "Accept=application/json")
//    public List<ShpInfo> kml(@PathVariable Integer idShp) throws Exception {
//        ShpInfo shp = new ShpInfo();
//        shp.setId(idShp);
//        shp = (ShpInfo) shpService.findAll(shp).get(0);
//        
//    }
    @GetMapping(value = "/kmls", headers = "Accept=application/json")
    public List<Kml> kml(@RequestParam Integer idShp) throws Exception {
        return shpService.findAllKML(idShp);
    }

    @GetMapping(value = "/kmls/overlay", headers = "Accept=application/json")
    public Map<String, String> kmlOverlay(@RequestParam String tableName,
            @RequestParam List<Integer> gids,
            HttpSession session) throws Exception {
        return shpService.getKmlFile(tableName, gids, 0.01, session.getServletContext().getRealPath("/resources/assets/modele/kml/"));
    }

    @GetMapping(value = "/kmls/placemarks", headers = "Accept=application/json")
    public List<DarwinCore> kmlPlacemark(@RequestParam String tableName,
            @RequestParam List<Integer> gids,
            HttpSession session) throws Exception {
        List<HashMap<String, List<Double>>> listPoints = shpService.getGeoLocation(tableName, gids, 0.01);
        List<DarwinCore> valiny = new ArrayList<>();
        for (HashMap<String, List<Double>> hm : listPoints) {
            valiny.addAll(darwinCoreService.getDarwinCoreDao().findByLatLong((List<Double>) hm.get("latitude"), (List<Double>) hm.get("longitude")));
        }
        List<DarwinCore> farany = valiny.stream().distinct().collect(Collectors.toList());
        return farany;
    }

    @GetMapping(value = "/shapefiles/total", headers = "Accept=application/json")
    public Integer total() throws Exception {
        return shpService.countTotal(new ShpInfo());
    }
//
//    @RequestMapping(value = "/modeles", method = RequestMethod.POST, headers = "Accept=application/json")
//    public Boolean add(HttpSession session, @RequestParam("modele") MultipartFile modeleAsc, @RequestParam String fileName) throws Exception {
//        try {
//            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
//            String cheminReal = session.getServletContext().getRealPath("/resources/assets/modele/ascii/");
//            Modele modele = new Modele();
//            modele.setName(fileName);
//            modeleService.enregistrerModele(modeleAsc, utilisateur, cheminReal, modele);
//            return Boolean.TRUE;
//        } catch (Exception e) {
//            System.err.println(e);
//            return Boolean.FALSE;
//        }
//    }
//
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
//    @RequestMapping(value = "/modeles", method = RequestMethod.GET, headers = "Accept=application/json")
//    public Retour list(@RequestParam(value = "page", required = false) Integer page,
//            @RequestParam(value = "limite", required = false) Integer limite) throws Exception {
//        if (page == null) {
//            page = 1;
//        }
//        if (limite == null) {
//            limite = 10;
//        }
//        Retour valiny = new Retour();
//        try {
//            valiny.setEtat(Boolean.TRUE);
//            valiny.setRetour(shpService.findAll(new Modele(), page, limite));
//        } catch (Exception e) {
//            valiny.setEtat(Boolean.FALSE);
//            valiny.setRetour(e.getMessage());
//        }
//        return valiny;
//    }
}
