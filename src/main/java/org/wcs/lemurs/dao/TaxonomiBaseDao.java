/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.dao;

import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Session;
import org.wcs.lemurs.model.BaseModel;
import org.wcs.lemurs.modele_vue.VueRechercheTaxonomi;

/**
 *
 * @author rudyr
 */
@Repository
@Transactional
public class TaxonomiBaseDao extends HibernateDao {

    public List<BaseModel> findMulticritereListeAll(Session session, VueRechercheTaxonomi modele, String colonne, int ordre) {
        String query = "Select new VueRechercheTaxonomi(id, dwcorder, dwcclass, dwcfamily, genus, scientificname) from VueRechercheTaxonomi where 1=1";
        boolean scName = false;
        boolean test = false;        
        try {
            if (!modele.getScientificname().isEmpty()) {
                query += " and scientificname like :scientificname";
                scName = true;
            }
        } catch (NullPointerException npe) {
            // modele.getScientificname() is null so let's move on.
        }
        try {
            if (modele.getTest() != null && !modele.getTest().isEmpty()) {
                query += " and test like :test";
                test = true;
            }
        } catch (NullPointerException npe) {
            // modele.getTest() is null so let's move on.
        }
        try {
            if (!colonne.isEmpty()) {
                if(ordre == 0) {
                    query += " order by " + colonne + " ASC";                    
                } else if (ordre > 0) {
                    query += " order by " + colonne + " DESC";                    
                }                 
            }
        } catch (NullPointerException npe) {
            // colonne is null so let's move on.
        }
        Query querys = session.createQuery(query);
        if (scName) {
            querys.setString("scientificname", "%"+modele.getScientificname()+"%");
        }
        if (test) {
            querys.setString("test", "%"+modele.getTest()+"%");
        }   
        List<BaseModel> val = null;
        try {
            val = (List<BaseModel>) querys.list();
            return val;
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }                        
    }
    
    public List<BaseModel> findMulticritereListeAll(VueRechercheTaxonomi obj, String colonne, int ordre) throws Exception {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            return findMulticritereListeAll(session, obj, colonne, ordre);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
}
