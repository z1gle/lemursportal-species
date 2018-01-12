/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.wcs.lemurs.model.DarwinCore;

/**
 *
 * @author rudyr
 */
@Repository
@Transactional
public class DarwinCoreDao extends HibernateDao {

    public List<DarwinCore> validationDarwinCore(int validation, String chercheur) throws Exception {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            String qry = "SELECT "
                    + "    d.iddwc,"
                    + "    d.institutioncode,"
                    + "    d.collectioncode,"
                    + "    d.datasetname,"
                    + "    d.ownerinstitutioncode,"
                    + "    d.basisofrecord,"
                    + "    d.informationwithheld,"
                    + "    d.datageneralizations,"
                    + "    d.dynamicproperties,"
                    + "    d.scientificname,"
                    + "    d.acceptednameusage,"
                    + "    d.higherclassification,"
                    + "    d.kingdom,"
                    + "    d.phylum,"
                    + "    d.darwinclass,"
                    + "    d.darwinorder,"
                    + "    d.family,"
                    + "    d.genus,"
                    + "    d.subgenus,"
                    + "    d.specificepithet,"
                    + "    d.infraspecificepithet,"
                    + "    d.taxonrank,"
                    + "    d.verbatimtaxonrank,"
                    + "    d.scientificnameauthorship,"
                    + "    d.vernacularname,"
                    + "    d.nomenclaturalcode,"
                    + "    d.taxonremarks,"
                    + "    d.catalognumber,"
                    + "    d.occurrenceremarks,"
                    + "    d.recordnumber,"
                    + "    d.recordedby,"
                    + "    d.individualcount,"
                    + "    d.sex,"
                    + "    d.lifestage,"
                    + "    d.reproductivecondition,"
                    + "    d.behavior,"
                    + "    d.preparations,"
                    + "    d.disposition,"
                    + "    d.othercatalognumbers,"
                    + "    d.previousidentifications,"
                    + "    d.associatedmedia,"
                    + "    d.associatedreferences,"
                    + "    d.associatedoccurrences,"
                    + "    d.associatedsequences,"
                    + "    d.associatedtaxa,"
                    + "    d.samplingprotocol,"
                    + "    d.samplingeffort,"
                    + "    d.eventdate,"
                    + "    d.eventtime,"
                    + "    d.startdayofyear,"
                    + "    d.enddayofyear,"
                    + "    d.dwcyear,"
                    + "    d.dwcmonth,"
                    + "    d.dwcday,"
                    + "    d.verbatimeventdate,"
                    + "    d.habitat,"
                    + "    d.fieldnumber,"
                    + "    d.fieldnotes,"
                    + "    d.eventremarks,"
                    + "    d.highergeography,"
                    + "    d.continent,"
                    + "    d.waterbody,"
                    + "    d.islandgroup,"
                    + "    d.island,"
                    + "    d.country,"
                    + "    d.countrycode,"
                    + "    d.stateprovince,"
                    + "    d.county,"
                    + "    d.municipality,"
                    + "    d.locality,"
                    + "    d.verbatimlocality,"
                    + "    d.verbatimelevation,"
                    + "    d.minimumelevationinmeters,"
                    + "    d.maximumelevationinmeters,"
                    + "    d.verbatimdepth,"
                    + "    d.minimumdepthinmeters,"
                    + "    d.maximumdepthinmeters,"
                    + "    d.minimumdistanceabovesurfaceinmeters,"
                    + "    d.maximumdistanceabovesurfaceinmeters,"
                    + "    d.locationaccordingto,"
                    + "    d.locationremarks,"
                    + "    d.verbatimcoordinates,"
                    + "    d.verbatimlatitude,"
                    + "    d.verbatimlongitude,"
                    + "    d.verbatimcoordinatesystem,"
                    + "    d.verbatimsrs,"
                    + "    d.decimallatitude,"
                    + "    d.decimallongitude,"
                    + "    d.geodeticdatum,"
                    + "    d.coordinateuncertaintyinmeters,"
                    + "    d.coordinateprecision,"
                    + "    d.pointradiusspatialfit,"
                    + "    d.footprintwkt,"
                    + "    d.footprintsrs,"
                    + "    d.footprintspatialfit,"
                    + "    d.georeferencedby,"
                    + "    d.georeferenceprotocol,"
                    + "    d.georeferencesources,"
                    + "    d.georeferenceverificationstatus,"
                    + "    d.georeferenceremarks,"
                    + "    d.identifiedby,"
                    + "    d.dateidentified,"
                    + "    d.identificationreferences,"
                    + "    d.identificationremarks,"
                    + "    d.identificationqualifier,"
                    + "    d.typestatus,"
                    + "    d.idutilisateurupload    "
                    + "   FROM utilisateur u"
                    + "     JOIN darwin_core d ON d.idutilisateurupload = u.id"
                    + "     JOIN validation_darwin_core v ON v.iddarwincore = d.iddwc"
                    + "     WHERE 1=1";
            if(validation == 1) {
                qry = qry + "	AND v.validationexpert = true";
            } else if(validation == 0) {
                qry = qry + "	AND v.validationexpert = false";
            } if(!chercheur.isEmpty()) {
                qry = qry + "	AND (u.prenom ilike :chercheur OR u.nom ilike :chercheur)"; 
            }                                        
            Query query = session.createSQLQuery(qry).addEntity(new DarwinCore().getClass());
            if(!chercheur.isEmpty()) query.setParameter("chercheur", "%"+chercheur+"%");
            List<DarwinCore> list = query.list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
