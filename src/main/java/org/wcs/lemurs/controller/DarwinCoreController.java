/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.controller;

import java.util.List;
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
import org.wcs.lemurs.model.DarwinCore;
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

    @RequestMapping(value = "/findByespeceDwc", method = RequestMethod.POST, headers = "Accept=application/json")
    public List<DarwinCore> findByespece(@RequestBody DarwinCore dwc) throws Exception {
        return darwinCoreService.findMutliCritere(dwc);
    }

    @RequestMapping(value = "/getallDwc", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<DarwinCore> getall() throws Exception {
        return darwinCoreService.findAll();
    }

    @RequestMapping(value = "/saveDwc", method = RequestMethod.POST, headers = "Accept=application/json")
    public void saveOrupdate(@RequestBody DarwinCore dwc) throws Exception {
        darwinCoreService.save(dwc);
    }

    @RequestMapping(value = "/deleteDwc", method = RequestMethod.POST, headers = "Accept=application/json")
    public void delete(@RequestBody DarwinCore dwc) throws Exception {
        darwinCoreService.delete(dwc);
    }

    @RequestMapping(value = "/processExcel", method = RequestMethod.POST)
    public ModelAndView processExcel(Model model, @RequestParam("excelfile") MultipartFile excelfile) {
        try {
            List<DarwinCore> liste_darwin_core = UploadFile.import_darwin_core_excel(excelfile.getInputStream());
            darwinCoreService.upload(liste_darwin_core);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelAndView("darwinportal");
    }
}
