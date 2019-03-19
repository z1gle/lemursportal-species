/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.wcs.lemurs.model.DraftInstitutionCodeInaturalist;
import org.wcs.lemurs.model.DraftInstitutionCodeLemursportal;
import org.wcs.lemurs.model.DraftInstitutionCodeRebioma;
import org.wcs.lemurs.model.DraftScientificnameInaturalist;
import org.wcs.lemurs.model.DraftScientificnameLemursportal;
import org.wcs.lemurs.model.DraftScientificnameRebioma;
import org.wcs.lemurs.service.BaseService;

/**
 *
 * @author rudyr
 */
@RestController
public class DraftController {

    @Autowired(required = true)
    @Qualifier("baseService")
    private BaseService baseService;

    @RequestMapping(value = "showDraft")
    public ModelAndView draft() {
        ModelAndView valiny = new ModelAndView("draft");
        return valiny;
    }

    @RequestMapping(value = "/drafts", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<List<?>> list(@RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limite", required = false) Integer limite,
            @RequestParam(value = "scientificname", required = false) Integer sc) throws Exception {
        if (page == null) {
            page = 1;
        }
        if (limite == null) {
            limite = 10;
        }
        List<List<?>> valiny = new ArrayList<>();
        List<?> list1;
        List<?> list2;
        List<?> list3;
        if (sc <= 0) {
            list1 = (List<DraftInstitutionCodeInaturalist>) 
                    (List<?>) baseService.findAll(new DraftInstitutionCodeInaturalist());
            list2 = (List<DraftInstitutionCodeRebioma>) 
                    (List<?>) baseService.findAll(new DraftInstitutionCodeRebioma());
            list3 = (List<DraftInstitutionCodeLemursportal>) 
                    (List<?>) baseService.findAll(new DraftInstitutionCodeLemursportal());
        } else {
            list1 = (List<DraftScientificnameInaturalist>) 
                    (List<?>) baseService.findAll(new DraftScientificnameInaturalist());
            list2 = (List<DraftScientificnameRebioma>) 
                    (List<?>) baseService.findAll(new DraftScientificnameRebioma());
            list3 = (List<DraftScientificnameLemursportal>) 
                    (List<?>) baseService.findAll(new DraftScientificnameLemursportal());
        }
        valiny.add(list1);
        valiny.add(list2);
        valiny.add(list3);
        return valiny;
    }

//    @RequestMapping(value = "/modeles/total", method = RequestMethod.GET, headers = "Accept=application/json")
//    public Integer total() throws Exception {                
//        return baseService.countTotal(new Modele());
//    }
}
