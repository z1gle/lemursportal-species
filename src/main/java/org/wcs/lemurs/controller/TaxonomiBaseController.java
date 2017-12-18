/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wcs.lemurs.model.TaxonomiBase;
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

    @RequestMapping(value = "/getallTaxo", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<TaxonomiBase> getall() throws Exception {
        List<TaxonomiBase> resultat = taxonomiBaseService.findAll();
        return resultat;
    }

    @RequestMapping(value = "/saveTaxo", method = RequestMethod.POST, headers = "Accept=application/json")
    public void saveOrupdate(@RequestBody TaxonomiBase t) throws Exception {
        taxonomiBaseService.save(t);
    }

    @RequestMapping(value = "/deleteTaxo", method = RequestMethod.POST, headers = "Accept=application/json")
    public void delete(@RequestBody TaxonomiBase t) throws Exception {
        taxonomiBaseService.delete(t);
    }
}
