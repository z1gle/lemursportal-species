/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.wcs.lemurs.dao.DarwinCoreDao;
import org.wcs.lemurs.exception.StatusAlreadyExistException;
import org.wcs.lemurs.model.BaseModel;
import org.wcs.lemurs.model.CommentaireDarwinCore;
import org.wcs.lemurs.model.DarwinCore;
import org.wcs.lemurs.model.PhotoDarwinCore;
import org.wcs.lemurs.model.Utilisateur;
import org.wcs.lemurs.model.ValidationDarwinCore;
import org.wcs.lemurs.modele_association.AssignationExpert;
import org.wcs.lemurs.modele_association.HistoriqueStatus;
import org.wcs.lemurs.modele_vue.VueValidationDarwinCore;

/**
 *
 * @author rudyr
 */
@Service
@Transactional
public class DarwinCoreService extends BaseService {

    @Autowired(required = true)
    @Qualifier("darwinCoreDao")
    private DarwinCoreDao DarwinCoreDao;

//    @Transactional
//    public void save(DarwinCore darwinCore) throws Exception {
//        save(darwinCore);
//    }
//    
//    @Transactional
//    public void save(Session session, DarwinCore darwinCore) throws Exception {
//        save(session, darwinCore);
//    }
//
//    @Transactional
//    public void update(DarwinCore darwinCore) throws Exception {
//        save(darwinCore);
//    }
//
//    @Transactional
//    public void delete(DarwinCore darwinCore) throws Exception {
//        hibernateDao.delete(darwinCore);
//    }
    public DarwinCore findById(int iddwc) throws Exception {
        DarwinCore darwinCore = new DarwinCore();
        darwinCore.setId(iddwc);
        super.findById(darwinCore);
        return darwinCore;
    }

    public List<DarwinCore> findAll() throws Exception {
        List<BaseModel> list_bm = super.findAll(new DarwinCore());
        List<DarwinCore> res = new ArrayList<>();
        for (BaseModel bm : list_bm) {
            res.add((DarwinCore) bm);
        }
        return res;
    }

    public List<DarwinCore> findMutliCritere(DarwinCore darwinCore) throws Exception {
        List<BaseModel> list_bm = super.findMultiCritere(darwinCore);
        List<DarwinCore> res = new ArrayList<>();
        for (BaseModel bm : list_bm) {
            res.add((DarwinCore) bm);
        }
        return res;
    }

    public List<DarwinCore> findValidation(int validation, String chercheur) throws Exception {
        return getDarwinCoreDao().validationDarwinCore(validation, chercheur);
    }

//    public void upload(List<DarwinCore> list_dw) throws Exception {
//
//        for (DarwinCore dw : list_dw) {
//            
//            save(dw);
//        }
//    }
    public List<DarwinCore> upload(List<DarwinCore> list_dw) throws Exception {
        Session session = null;
        Transaction tr = null;
        try {
            session = getHibernateDao().getSessionFactory().openSession();
            tr = session.beginTransaction();
            for (DarwinCore dw : list_dw) {
                save(session, dw);
                ValidationDarwinCore vdc = new ValidationDarwinCore();
                vdc.setIdDarwinCore(dw.getId());
                try {
                    vdc = (ValidationDarwinCore) findMultiCritere(vdc).get(0);
                } catch (Exception e) {
                }
                vdc.setAcceptedSpeces(checkVerbatimspecies(session, dw));
                try {
                    vdc.setAnnee(!dw.getDwcyear().isEmpty() && dw.getDateidentified().compareTo("-") != 0);
                } catch (NullPointerException npe) {
                    vdc.setAnnee(Boolean.FALSE);
                }
                try {
                    vdc.setCollecteur(!dw.getRecordedby().isEmpty() && dw.getRecordedby().compareTo("-") != 0);
                } catch (NullPointerException npe) {
                    vdc.setCollecteur(Boolean.FALSE);
                }
                try {
                    vdc.setGps(!dw.getDecimallatitude().isEmpty() && !dw.getDecimallongitude().isEmpty() & dw.getDecimallatitude().compareTo("-") != 0 && dw.getDecimallongitude().compareTo("-") != 0);
                } catch (NullPointerException npe) {
                    vdc.setGps(Boolean.FALSE);
                }
                vdc.setValidationExpert(-1);
                save(session, vdc);
            }
            tr.commit();
            return list_dw;
        } catch (Exception ex) {
            tr.rollback();
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<VueValidationDarwinCore> getValidation(List<DarwinCore> liste) throws Exception {
        Session session = null;
        try {
            session = getHibernateDao().getSessionFactory().openSession();
            List<VueValidationDarwinCore> valiny = new ArrayList<>();
            for (DarwinCore d : liste) {
                VueValidationDarwinCore temp = new VueValidationDarwinCore();
                temp.setId(d.getId());
                findById(session, temp);
                valiny.add(temp);
            }
            return valiny;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<HashMap<String, Object>> formaterHashMapForAffichage(Utilisateur utilisateur, List<DarwinCore> liste) throws Exception {
        Session session = null;
        try {
            session = getHibernateDao().getSessionFactory().openSession();
            List<VueValidationDarwinCore> valiny = new ArrayList<>();
            for (DarwinCore d : liste) {
                VueValidationDarwinCore temp = new VueValidationDarwinCore();
                temp.setId(d.getId());
                findById(session, temp);
                valiny.add(temp);
            }
            List<HashMap<String, Object>> valinyFarany = new ArrayList<>();
            int iterator = 0;
            for (VueValidationDarwinCore dwc : valiny) {
                HashMap<String, Object> temp = new HashMap<>();
                temp.put("dwc", dwc);
                if (this.checkDomaineExpertise(utilisateur, liste.get(iterator))) {
                    temp.put("validation", 1);
                }
                valinyFarany.add(temp);
                iterator++;
            }
            return valinyFarany;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public boolean checkVerbatimspecies(Session session, DarwinCore dw) throws Exception {
        String qry = "select count(*) from (select *,genus || specificepithet || infraspecificepithet as verbatimspecies from taxonomi_base) as sous where sous.verbatimspecies = :verbatimspecies";
        Query query = session.createSQLQuery(qry);
        String verbatimspecies = dw.getGenus() + dw.getSpecificepithet() + dw.getInfraspecificepithet();
        query.setParameter("verbatimspecies", verbatimspecies);
        Integer val = ((BigInteger) query.list().get(0)).intValue();
        return val > 0;
    }

    public List<DarwinCore> GetListObservation(Session session, AssignationExpert a) throws Exception {
        if ((a.getEspece() != null && !a.getEspece().isEmpty()) && (a.getGenre() == null || a.getGenre().isEmpty()) && (a.getFamille() == null || a.getFamille().isEmpty())) {
            List<String> name = new ArrayList<>();
            name.add("sn");
            List<Object> parameter = new ArrayList<>();
            parameter.add(a.getEspece());
            return (List<DarwinCore>) (List<?>) this.executeSqlListBaseModel(session, "select * from vue_validation_darwin_core where scientificname = :sn and annee = true and collecteur = true and accepted_speces = true and gps = true", name, parameter, new DarwinCore());
        }
        if ((a.getGenre() != null && !a.getGenre().isEmpty()) && (a.getEspece() == null || a.getEspece().isEmpty()) && (a.getFamille() == null || a.getFamille().isEmpty())) {
            List<String> name = new ArrayList<>();
            name.add("sn");
            List<Object> parameter = new ArrayList<>();
            parameter.add(a.getGenre());
            return (List<DarwinCore>) (List<?>) this.executeSqlListBaseModel(session, "select * from vue_validation_darwin_core where genus = :sn and annee = true and collecteur = true and accepted_speces = true and gps = true", name, parameter, new DarwinCore());
        }
        if ((a.getEspece() == null || a.getEspece().isEmpty()) && (a.getGenre() == null || a.getGenre().isEmpty()) && (a.getFamille() != null && !a.getFamille().isEmpty())) {
            List<String> name = new ArrayList<>();
            name.add("sn");
            List<Object> parameter = new ArrayList<>();
            parameter.add(a.getFamille());
            return (List<DarwinCore>) (List<?>) this.executeSqlListBaseModel(session, "select * from vue_validation_darwin_core where family = :sn and annee = true and collecteur = true and accepted_speces = true and gps = true", name, parameter, new DarwinCore());
        } else {
            return null;
        }
    }

    public List<VueValidationDarwinCore> GetListObservationEtat(Session session, AssignationExpert a) throws Exception {
        if ((a.getEspece() != null && !a.getEspece().isEmpty()) && (a.getGenre() == null || a.getGenre().isEmpty()) && (a.getFamille() == null || a.getFamille().isEmpty())) {
            List<String> name = new ArrayList<>();
            name.add("sn");
            List<Object> parameter = new ArrayList<>();
            parameter.add(a.getEspece());
            return (List<VueValidationDarwinCore>) (List<?>) this.executeSqlListBaseModel(session, "select * from vue_validation_darwin_core where scientificname = :sn and annee = true and collecteur = true and accepted_speces = true and gps = true", name, parameter, new VueValidationDarwinCore());
        }
        if ((a.getGenre() != null && !a.getGenre().isEmpty()) && (a.getEspece() == null || a.getEspece().isEmpty()) && (a.getFamille() == null || a.getFamille().isEmpty())) {
            List<String> name = new ArrayList<>();
            name.add("sn");
            List<Object> parameter = new ArrayList<>();
            parameter.add(a.getGenre());
            return (List<VueValidationDarwinCore>) (List<?>) this.executeSqlListBaseModel(session, "select * from vue_validation_darwin_core where genus = :sn and annee = true and collecteur = true and accepted_speces = true and gps = true", name, parameter, new VueValidationDarwinCore());
        }
        if ((a.getEspece() == null || a.getEspece().isEmpty()) && (a.getGenre() == null || a.getGenre().isEmpty()) && (a.getFamille() != null && !a.getFamille().isEmpty())) {
            List<String> name = new ArrayList<>();
            name.add("sn");
            List<Object> parameter = new ArrayList<>();
            parameter.add(a.getFamille());
            return (List<VueValidationDarwinCore>) (List<?>) this.executeSqlListBaseModel(session, "select * from vue_validation_darwin_core where family = :sn and annee = true and collecteur = true and accepted_speces = true and gps = true", name, parameter, new VueValidationDarwinCore());
        } else {
            return null;
        }
    }

    public List<DarwinCore> getListObservationFor(Utilisateur utilisateur) throws Exception {
        AssignationExpert aes = new AssignationExpert();
        aes.setIdExpert(utilisateur.getId());
        List<AssignationExpert> domaine = (List<AssignationExpert>) (List<?>) this.findMultiCritere(aes);
        List<DarwinCore> valiny = new ArrayList<>();
        Session session = null;
        try {
            session = this.getHibernateDao().getSessionFactory().openSession();
            for (AssignationExpert a : domaine) {
                valiny.addAll(this.GetListObservation(session, a));
            }
            return valiny;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<VueValidationDarwinCore> getListObservationAndEtatFor(Utilisateur utilisateur) throws Exception {
        AssignationExpert aes = new AssignationExpert();
        aes.setIdExpert(utilisateur.getId());
        List<AssignationExpert> domaine = (List<AssignationExpert>) (List<?>) this.findMultiCritere(aes);
        List<VueValidationDarwinCore> valiny = new ArrayList<>();
        Session session = null;
        try {
            session = this.getHibernateDao().getSessionFactory().openSession();
            for (AssignationExpert a : domaine) {
                valiny.addAll(this.GetListObservationEtat(session, a));
            }
            return valiny;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<HashMap<String, Object>> findWithCheck(Utilisateur utilisateur, DarwinCore darwinCore) throws Exception {
        List<HashMap<String, Object>> valiny = new ArrayList<>();
        List<DarwinCore> val = findMutliCritere(darwinCore);
        try {
            List<DarwinCore> toCheck = getListObservationFor(utilisateur);
//            val.removeAll(toCheck);
            for (int i = 0; i < val.size(); i++) {
                for (int j = 0; j < toCheck.size(); j++) {
                    if (val.get(i).getId() == toCheck.get(j).getId()) {
                        val.remove(i);
                        i--;
                        break;
                    }
                }
            }
            for (DarwinCore dwc : toCheck) {
                HashMap<String, Object> temp = new HashMap<>();
                temp.put("dwc", dwc);
                temp.put("validation", 1);
                valiny.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (DarwinCore dwc : val) {
            HashMap<String, Object> temp = new HashMap<>();
            temp.put("dwc", dwc);
            temp.put("validation", 0);
            valiny.add(temp);
        }
        return valiny;
    }

    public List<HashMap<String, Object>> findWithCheckAndEtat(Utilisateur utilisateur, VueValidationDarwinCore darwinCore) throws Exception {
        List<HashMap<String, Object>> valiny = new ArrayList<>();
        List<VueValidationDarwinCore> val = (List<VueValidationDarwinCore>) (List<?>) super.findMultiCritere(darwinCore);
        List<PhotoDarwinCore> photos = new ArrayList<>();
        try {
            List<VueValidationDarwinCore> toCheck = getListObservationAndEtatFor(utilisateur);
//            val.removeAll(toCheck);
            for (int i = 0; i < val.size(); i++) {
                for (int j = 0; j < toCheck.size(); j++) {
                    if (val.get(i).getId() == toCheck.get(j).getId()) {
                        val.remove(i);
                        i--;
                        break;
                    }
                }
            }
            for (VueValidationDarwinCore dwc : toCheck) {
                HashMap<String, Object> temp = new HashMap<>();
                temp.put("dwc", dwc);
                temp.put("validation", 1);
                valiny.add(temp);
            }
            PhotoDarwinCore photoDarwinCoreTemp = new PhotoDarwinCore();
            photoDarwinCoreTemp.setProfil(Boolean.TRUE);
            photos = (List<PhotoDarwinCore>) (List<?>) this.findMultiCritere(photoDarwinCoreTemp);
        } catch (Exception e) {
            throw e;
        }
        for (VueValidationDarwinCore dwc : val) {
            HashMap<String, Object> temp = new HashMap<>();
            temp.put("dwc", dwc);
            temp.put("validation", 0);
//            int iterator = 0;
//            for (PhotoDarwinCore pdc : photos) {
//                if (dwc.getId().intValue() == pdc.getIdDarwinCore().intValue()) {
//                    temp.put("photo", pdc.getChemin());
//                    break;
//                } else {
//                    iterator++;
//                }
//            }
//            if (iterator == photos.size()) {
//                temp.put("photo", photos.get(0).getChemin().substring(0, photos.get(0).getChemin().lastIndexOf("/")) + "default.jpg");
//            }
            valiny.add(temp);
        }
        for (HashMap<String, Object> v : valiny) {
            int iterator = 0;
            for (PhotoDarwinCore pdc : photos) {
                if (((VueValidationDarwinCore) v.get("dwc")).getId().intValue() == pdc.getIdDarwinCore().intValue()) {
                    v.put("photo", pdc.getChemin());
                    break;
                } else {
                    iterator++;
                }
            }
            if (iterator == photos.size()) {
                v.put("photo", photos.get(0).getChemin().substring(0, photos.get(0).getChemin().lastIndexOf("/")) + "/default.jpg");
            }
        }
        return valiny;
    }

    public List<HashMap<String, Object>> findObservationCheck(Utilisateur utilisateur) throws Exception {
        List<HashMap<String, Object>> valiny = new ArrayList<>();
        List<DarwinCore> val = getListObservationFor(utilisateur);
        for (DarwinCore dwc : val) {
            HashMap<String, Object> temp = new HashMap<>();
            temp.put("dwc", dwc);
            temp.put("validation", 1);
            valiny.add(temp);
        }
        return valiny;
    }

//    public List<HashMap<String, Object>> findObservationAndEtatCheck(Utilisateur utilisateur) throws Exception {
//        List<HashMap<String, Object>> valiny = new ArrayList<>();
//        List<VueValidationDarwinCore> val = getListObservationAndEtatFor(utilisateur);
//        for (VueValidationDarwinCore dwc : val) {
//            HashMap<String, Object> temp = new HashMap<>();
//            temp.put("dwc", dwc);
//            temp.put("validation", 1);
//            valiny.add(temp);
//        }
//        return valiny;
//    }
    public List<HashMap<String, Object>> findObservationAndEtatCheck(Utilisateur utilisateur, int etatValidation) throws Exception {
        List<HashMap<String, Object>> valiny = new ArrayList<>();
        List<VueValidationDarwinCore> val = getListObservationAndEtatFor(utilisateur);
        if (etatValidation == -999) {
            for (VueValidationDarwinCore dwc : val) {
                HashMap<String, Object> temp = new HashMap<>();
                temp.put("dwc", dwc);
                temp.put("validation", 1);
                valiny.add(temp);
            }
        } else {
            for (VueValidationDarwinCore dwc : val) {
                if (dwc.getValidationexpert() == etatValidation) {
                    HashMap<String, Object> temp = new HashMap<>();
                    temp.put("dwc", dwc);
                    temp.put("validation", 1);
                    valiny.add(temp);
                }
            }
        }
        return valiny;
    }

    public List<HashMap<String, Object>> findObservationAndEtatCheckOf(Utilisateur utilisateur, int etatValidation) throws Exception {
        List<HashMap<String, Object>> valiny = new ArrayList<>();
        VueValidationDarwinCore vvdTemp = new VueValidationDarwinCore();
        vvdTemp.setIdUtilisateurUpload(utilisateur.getId());
        List<VueValidationDarwinCore> val = (List<VueValidationDarwinCore>) (List<?>) findMultiCritere(vvdTemp);
        if (etatValidation == -999) {
            for (VueValidationDarwinCore dwc : val) {
                HashMap<String, Object> temp = new HashMap<>();
                temp.put("dwc", dwc);
                temp.put("validation", 1);
                valiny.add(temp);
            }
        } else {
            for (VueValidationDarwinCore dwc : val) {
                if (dwc.getValidationexpert() == etatValidation) {
                    HashMap<String, Object> temp = new HashMap<>();
                    temp.put("dwc", dwc);
                    temp.put("validation", 1);
                    valiny.add(temp);
                }
            }
        }
        return valiny;
    }

    public boolean checkDomaineExpertise(Utilisateur utilisateur, DarwinCore dwc) throws Exception {
        AssignationExpert aes = new AssignationExpert();
        aes.setIdExpert(utilisateur.getId());
        List<AssignationExpert> domaine = (List<AssignationExpert>) (List<?>) this.findMultiCritere(aes);
        for (AssignationExpert a : domaine) {
            try {
                if (a.getFamille().compareTo(dwc.getFamily()) == 0) {
                    return true;
                }
            } catch (NullPointerException npe) {
            }
            try {
                if (a.getGenre().compareTo(dwc.getGenus()) == 0) {
                    return true;
                }
            } catch (NullPointerException npe) {
            }
            try {
                if (a.getEspece().compareTo(dwc.getScientificname()) == 0) {
                    return true;
                }
            } catch (NullPointerException npe) {
            }
        }
        return false;
    }

    public void changeStatusValidationDarwinCore(DarwinCore dwc, Utilisateur utilisateur, String status) throws Exception {
        ValidationDarwinCore vdc = new ValidationDarwinCore();
        vdc.setIdDarwinCore(dwc.getId());
        vdc = ((List<ValidationDarwinCore>) (List<?>) findMultiCritere(vdc)).get(0);
        int eqStatus = -1;
        if (status.compareTo("valide") == 0) {
            eqStatus = 1;
        }
        if (status.compareTo("questionnable") == 0) {
            eqStatus = 0;
        }
        vdc.setValidationExpert(eqStatus);
        vdc.setIdExpert(utilisateur.getId());
        vdc.setDateValidation(Calendar.getInstance().getTime());
        save(vdc);
    }

    public void changeStatusValidationDarwinCore(Session session, DarwinCore dwc, Utilisateur utilisateur, String status) throws Exception {
        ValidationDarwinCore vdc = new ValidationDarwinCore();
        vdc.setIdDarwinCore(dwc.getId());
        try {
            vdc = ((List<ValidationDarwinCore>) (List<?>) findMultiCritere(session, vdc)).get(0);
        } catch (IndexOutOfBoundsException ioobe) {
        }
        int eqStatus = -1;
        if (status.compareTo("valide") == 0) {
            eqStatus = 1;
        }
        if (status.compareTo("questionnable") == 0) {
            eqStatus = 0;
        }
        vdc.setValidationExpert(eqStatus);
        vdc.setIdExpert(utilisateur.getId());
        vdc.setDateValidation(Calendar.getInstance().getTime());
        save(vdc);
    }

    public HistoriqueStatus checkStatus(DarwinCore observation) throws Exception {
        HistoriqueStatus status = new HistoriqueStatus();
        status.setIdDwc(observation.getId());
        try {
            return (HistoriqueStatus) super.findMultiCritere(status, "dateModification", 1).get(0);
        } catch (IndexOutOfBoundsException i) {
            throw new NullPointerException();
        }
    }

    public HistoriqueStatus checkStatus(Session session, DarwinCore observation) throws Exception {
        HistoriqueStatus status = new HistoriqueStatus();
        status.setIdDwc(observation.getId());
        try {
            return (HistoriqueStatus) super.findMultiCritere(session, status, "dateModification", 1).get(0);
        } catch (IndexOutOfBoundsException i) {
            throw new NullPointerException();
        }
    }

    public void changeStatus(DarwinCore observation, Utilisateur expert, String newStatus) throws Exception {
        try {
            HistoriqueStatus status = checkStatus(observation);
            if (status == null) {
                throw new NullPointerException();
            } else if (status.getIdExpert() == expert.getId() && status.getValidation().compareTo(newStatus) == 0) {
            } else {
                StatusAlreadyExistException ex = new StatusAlreadyExistException();
                ex.setObservationEnCours(observation);
                ex.setStatusObservationEnCours(status);
                throw ex;
            }
        } catch (NullPointerException npe) {
            HistoriqueStatus hs = new HistoriqueStatus();
            hs.setDateModification(Calendar.getInstance().getTime());
            hs.setIdDwc(observation.getId());
            hs.setIdExpert(expert.getId());
            hs.setValidation(newStatus);
            save(hs);
            changeStatusValidationDarwinCore(observation, expert, newStatus);
        } catch (Exception e) {
            throw e;
        }
    }

    public void changeStatusForced(DarwinCore observation, Utilisateur expert, String newStatus) throws Exception {
        HistoriqueStatus hs = new HistoriqueStatus();
        hs.setDateModification(Calendar.getInstance().getTime());
        hs.setIdDwc(observation.getId());
        hs.setIdExpert(expert.getId());
        hs.setValidation(newStatus);
        save(hs);
        changeStatusValidationDarwinCore(observation, expert, newStatus);
    }

    public void changeStatus(Session session, DarwinCore observation, Utilisateur expert, String newStatus) throws Exception {
        try {
            HistoriqueStatus status = checkStatus(session, observation);
            if (status == null) {
                throw new NullPointerException();
            } else if (status.getIdExpert() == expert.getId() && status.getValidation().compareTo(newStatus) == 0) {
            } else {
                StatusAlreadyExistException ex = new StatusAlreadyExistException();
                ex.setObservationEnCours(observation);
                ex.setStatusObservationEnCours(status);
                throw ex;
            }
        } catch (NullPointerException npe) {
            HistoriqueStatus hs = new HistoriqueStatus();
            hs.setDateModification(Calendar.getInstance().getTime());
            hs.setIdDwc(observation.getId());
            hs.setIdExpert(expert.getId());
            hs.setValidation(newStatus);
            save(hs);
            changeStatusValidationDarwinCore(session, observation, expert, newStatus);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<DarwinCore> findAll(Session session, int[] dwc) throws Exception {
        List<DarwinCore> valiny = new ArrayList<>();
        for (int i = 0; i < dwc.length; i++) {
            DarwinCore temp = new DarwinCore();
            temp.setId(dwc[i]);
            findById(session, temp);
            valiny.add(temp);
        }
        return valiny;
    }

    public void changeStatusAll(List<DarwinCore> observations, Utilisateur expert, String newStatus) throws StatusAlreadyExistException, Exception {
        Session session = null;
        try {
            session = this.getHibernateDao().getSessionFactory().openSession();
            for (DarwinCore observation : observations) {
                try {
                    changeStatus(session, observation, expert, newStatus);
                } catch (StatusAlreadyExistException ex) {
                    try {
                        ex.getObservationRestante().addAll(observations.subList(observations.indexOf(ex.getObservationEnCours()) + 1, observations.size()));
                    } catch (NullPointerException npe) {
                        ex.setObservationRestante(observations.subList(observations.indexOf(ex.getObservationEnCours()) + 1, observations.size()));
                    }
                    throw ex;
                } catch (Exception ex) {
                    throw ex;
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void changeStatusAll(int[] idsObservations, Utilisateur expert, String newStatus) throws StatusAlreadyExistException, Exception {
        Session session = null;
        try {
            session = this.getHibernateDao().getSessionFactory().openSession();
            List<DarwinCore> observations = findAll(session, idsObservations);
            for (DarwinCore observation : observations) {
                try {
                    changeStatus(session, observation, expert, newStatus);
                } catch (StatusAlreadyExistException ex) {
                    try {
                        ex.getObservationRestante().addAll(observations.subList(observations.indexOf(ex.getObservationEnCours()) + 1, observations.size()));
                    } catch (NullPointerException npe) {
                        ex.setObservationRestante(observations.subList(observations.indexOf(ex.getObservationEnCours()) + 1, observations.size()));
                    }
                    throw ex;
                } catch (Exception ex) {
                    throw ex;
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void valider(DarwinCore observation, Utilisateur expert) throws Exception {
        changeStatus(observation, expert, "valide");
    }

    public void validerForced(DarwinCore observation, Utilisateur expert) throws Exception {
        changeStatusForced(observation, expert, "valide");
    }

    public void validerAll(List<DarwinCore> observation, Utilisateur expert) throws Exception {
        changeStatusAll(observation, expert, "valide");
    }

    public void validerAll(int[] observation, Utilisateur expert) throws Exception {
        changeStatusAll(observation, expert, "valide");
    }

    public void questionnable(DarwinCore observation, Utilisateur expert, String commentaire) throws Exception {
        changeStatus(observation, expert, "questionnable");
        CommentaireDarwinCore cdc = new CommentaireDarwinCore();
        cdc.setCommentaire(commentaire);
        cdc.setDateCommentaire(Calendar.getInstance().getTime());
        cdc.setIdDarwinCore(observation.getId());
        cdc.setIdUtilisateur(expert.getId());
        save(cdc);
    }

    public void questionnableForced(DarwinCore observation, Utilisateur expert, String commentaire) throws Exception {
        changeStatusForced(observation, expert, "questionnable");
        CommentaireDarwinCore cdc = new CommentaireDarwinCore();
        cdc.setCommentaire(commentaire);
        cdc.setDateCommentaire(Calendar.getInstance().getTime());
        cdc.setIdDarwinCore(observation.getId());
        cdc.setIdUtilisateur(expert.getId());
        save(cdc);
    }

    public void questionnableAll(List<DarwinCore> observation, Utilisateur expert, String commentaire) throws Exception {
        changeStatusAll(observation, expert, "questionnable");
        for (DarwinCore d : observation) {
            CommentaireDarwinCore cdc = new CommentaireDarwinCore();
            cdc.setCommentaire(commentaire);
            cdc.setDateCommentaire(Calendar.getInstance().getTime());
            cdc.setIdDarwinCore(d.getId());
            cdc.setIdUtilisateur(expert.getId());
            save(cdc);
        }
    }

    public void questionnableAll(int[] observation, Utilisateur expert, String commentaire) throws Exception {
        changeStatusAll(observation, expert, "questionnable");
        for (int i = 0; i < observation.length; i++) {
            CommentaireDarwinCore cdc = new CommentaireDarwinCore();
            cdc.setCommentaire(commentaire);
            cdc.setDateCommentaire(Calendar.getInstance().getTime());
            cdc.setIdDarwinCore(observation[i]);
            cdc.setIdUtilisateur(expert.getId());
            save(cdc);
        }
    }

    public List<PhotoDarwinCore> enregistrerPhoto(MultipartFile photo, DarwinCore darwinCore, Utilisateur utilisateur, boolean profil, String cheminReal) throws IOException, Exception {
        File fileTemp = File.createTempFile("temp", ".img");
        Session session = null;
        Transaction tr = null;
        try {
            session = getHibernateDao().getSessionFactory().openSession();
            tr = session.beginTransaction();
            try (InputStream is = photo.getInputStream(); BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(fileTemp))) {
                int i;
                while ((i = is.read()) != -1) {
                    stream.write(i);
                }
                stream.flush();
            }
            String cheminDepuisServeur = "resources\\assets\\img\\photos\\";
            Date datePhoto = Calendar.getInstance().getTime();
            String nomPhoto = "darwin_core_id_" + darwinCore.getId() + "_chercheur_id_" + utilisateur.getId() + "_date_" + datePhoto.getTime() + ".jpg";
            deplacerPhoto(fileTemp, cheminReal + cheminDepuisServeur, nomPhoto);

            PhotoDarwinCore photoDarwinCore = new PhotoDarwinCore();
            photoDarwinCore.setIdDarwinCore(darwinCore.getId());

            setProfilOfPhoto(session, photoDarwinCore, profil);

            photoDarwinCore.setChemin("resources/assets/img/photos/" + nomPhoto);
            photoDarwinCore.setDatePhoto(datePhoto);
            photoDarwinCore.setIdUtilisateurUpload(utilisateur.getId());
//            photoDarwinCore.setProfil(profil);
            save(session, photoDarwinCore);
            tr.commit();
            photoDarwinCore = new PhotoDarwinCore();
            photoDarwinCore.setIdDarwinCore(darwinCore.getId());
            return (List<PhotoDarwinCore>) (List<?>) this.findMultiCritere(session, photoDarwinCore);
        } catch (IOException e) {
            if (tr != null) {
                tr.rollback();
            }
            System.out.println("error : " + e.getMessage());
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void setProfilOfPhoto(Session session, PhotoDarwinCore photoDarwinCoreWithIdDarwinCore, boolean profil) throws Exception {
        if (photoDarwinCoreWithIdDarwinCore.getIdDarwinCore() != 0 || photoDarwinCoreWithIdDarwinCore.getIdDarwinCore() != null) {
            List<PhotoDarwinCore> listeToCheck = (List<PhotoDarwinCore>) (List<?>) this.findMultiCritere(session, photoDarwinCoreWithIdDarwinCore);
            int iterator = 0;
            for (PhotoDarwinCore p : listeToCheck) {
                if (profil) {
                    if (p.getProfil()) {
                        p.setProfil(Boolean.FALSE);
                        save(session, p);
                        photoDarwinCoreWithIdDarwinCore.setProfil(profil);
                        break;
                    }
                } else {
                    if (p.getProfil()) {
                        break;
                    }
                    iterator++;
                }
            }
            if (iterator == listeToCheck.size()) {
                photoDarwinCoreWithIdDarwinCore.setProfil(Boolean.TRUE);
            } else {
                photoDarwinCoreWithIdDarwinCore.setProfil(profil);
            }
        }
    }

    public DarwinCoreDao getDarwinCoreDao() {
        return DarwinCoreDao;
    }

    public void setDarwinCoreDao(DarwinCoreDao DarwinCoreDao) {
        this.DarwinCoreDao = DarwinCoreDao;
    }

}
