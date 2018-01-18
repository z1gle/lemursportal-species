/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wcs.lemurs.dao.HibernateDao;
import org.wcs.lemurs.model.BaseModel;
import org.wcs.lemurs.model.TaxonomiBase;
import org.wcs.lemurs.modele_association.AssignationExpert;

/**
 *
 * @author rudyr
 */
@Service
@Transactional
public class TaxonomiBaseService extends BaseService {

    @Autowired(required = true)
    @Qualifier("hibernateDao")
    private HibernateDao hibernateDao;

    @Transactional
    public void save(TaxonomiBase taxonomiBase) throws Exception {
        hibernateDao.save(taxonomiBase);
    }

    @Transactional
    public void update(TaxonomiBase taxonomiBase) throws Exception {
        hibernateDao.save(taxonomiBase);
    }

    @Transactional
    public void delete(TaxonomiBase taxonomiBase) throws Exception {
        hibernateDao.delete(taxonomiBase);
    }

    public TaxonomiBase findById(int idtaxonomibase) throws Exception {
        TaxonomiBase taxonomiBase = new TaxonomiBase();
        taxonomiBase.setId(idtaxonomibase);
        hibernateDao.findById(taxonomiBase);
        return taxonomiBase;
    }

    public List<TaxonomiBase> findAll() throws Exception {
        List<BaseModel> list_bm = hibernateDao.findAll(new TaxonomiBase());
        List<TaxonomiBase> res = new ArrayList<>();
        for (BaseModel bm : list_bm) {

            res.add((TaxonomiBase) bm);
        }
        return res;
    }

    public List<TaxonomiBase> findMultiCritere(TaxonomiBase taxonomiBase) throws Exception {
        List<BaseModel> list_bm = hibernateDao.findMultiCritere(taxonomiBase);
        List<TaxonomiBase> res = new ArrayList<>();
        for (BaseModel bm : list_bm) {

            res.add((TaxonomiBase) bm);
        }
        return res;
    }

    public void upload(List<TaxonomiBase> list_taxo) throws Exception {

        for (TaxonomiBase taxo : list_taxo) {

            save(taxo);
        }
    }

    public List<AssignationExpert> checkSaveGenre(String[] valeur, List<String> tous, int idExpert, String genre) throws Exception {
        List<AssignationExpert> especeToSave = new ArrayList<>();
        for (int i = 0; i < valeur.length; i++) {
            for (String s : tous) {
                if (valeur[i].contains(s)) {
                    AssignationExpert a = new AssignationExpert();
                    a.setIdExpert(idExpert);
                    a.setEspece(s);
                    especeToSave.add(a);
                    break;
                }
            }
        }
        if (especeToSave.size() == tous.size()) {
            AssignationExpert ae = new AssignationExpert();
            ae.setIdExpert(idExpert);
            ae.setGenre(genre);
            especeToSave.clear();
            especeToSave.add(ae);
        } 
        return especeToSave;
    }

    public List<AssignationExpert> checkGenre(String[] valeur, int idExpert) throws Exception {
        List<AssignationExpert> valiny = new ArrayList<>();
        for (int i = 0; i < valeur.length; i++) {
            if (valeur[i].contains("typeGenre")) {
                List<String> nom = new ArrayList<>();
                nom.add("dwcgen");
                List<Object> param = new ArrayList<>();
                param.add(valeur[i].split("-")[valeur[i].split("-").length - 1]);
                List<String> tous = (List<String>) (List<?>) executeSqlList("select distinct scientificname from taxonomi_base where genus = :dwcgen", nom, param);
                valiny.addAll(checkSaveGenre(valeur, tous, idExpert, (String)param.get(0)));
            }
        }
        return valiny;
    }
    
    public void checkSaveFamille(List<AssignationExpert> valeur, List<String> tous, int idExpert, String famille) throws Exception {
        List<AssignationExpert> especeToSave = new ArrayList<>();
        for (AssignationExpert ase : valeur) {
            for (String s : tous) {
                if(ase.getGenre()!=null && ase.getGenre().compareTo(s)==0) {
                    especeToSave.add(ase);
                    break;
                }                
            }
        }
        if (especeToSave.size() == tous.size()) {
            AssignationExpert ae = new AssignationExpert();
            ae.setIdExpert(idExpert);
            ae.setFamille(famille);
            valeur.removeAll(especeToSave);
            valeur.add(ae);
        }         
    }
    
    public void checkFamille(String[] valeur, int idExpert) throws Exception {
        List<AssignationExpert> valiny = new ArrayList<>();
        List<AssignationExpert> ase = checkGenre(valeur, idExpert);
        for (int i = 0; i < valeur.length; i++) {
            if (valeur[i].contains("typeFamille")) {
                List<String> nom = new ArrayList<>();
                nom.add("dwcfamille");
                List<Object> param = new ArrayList<>();
                param.add(valeur[i].split("-")[valeur[i].split("-").length - 1]);
                List<String> tous = (List<String>) (List<?>) executeSqlList("select distinct genus from taxonomi_base where dwcfamily = :dwcfamille", nom, param);                
                checkSaveFamille(ase, tous, idExpert, (String)param.get(0));
                valiny.addAll(ase);
            }
        }
        for(AssignationExpert v : ase) save(v);
    }
}
