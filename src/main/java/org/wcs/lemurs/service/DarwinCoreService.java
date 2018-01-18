/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Collections.list;
import java.util.HashMap;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wcs.lemurs.dao.DarwinCoreDao;
import org.wcs.lemurs.exception.StatusAlreadyExistException;
import org.wcs.lemurs.model.BaseModel;
import org.wcs.lemurs.model.DarwinCore;
import org.wcs.lemurs.model.Utilisateur;
import org.wcs.lemurs.model.ValidationDarwinCore;
import org.wcs.lemurs.modele_association.AssignationExpert;
import org.wcs.lemurs.modele_association.HistoriqueStatus;

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
    public void upload(List<DarwinCore> list_dw) throws Exception {
        Session session = null;
        Transaction tr = null;
        try {
            session = getHibernateDao().getSessionFactory().openSession();
            tr = session.beginTransaction();
            for (DarwinCore dw : list_dw) {
                save(session, dw);
                ValidationDarwinCore vdc = new ValidationDarwinCore();
                vdc.setIdDarwinCore(dw.getId());
                vdc.setAcceptedSpeces(checkVerbatimspecies(session, dw));
                try {
                    vdc.setAnnee(!dw.getDwcyear().isEmpty() && dw.getDateidentified().compareTo("-") != 0);
                } catch (NullPointerException npe) {
                    vdc.setAnnee(Boolean.FALSE);
                }
                try {
                    vdc.setCollecteur(!dw.getRecordedby().isEmpty() && dw.getIdentifiedby().compareTo("-") != 0);
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
        } catch (Exception ex) {
            tr.rollback();
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
            return (List<DarwinCore>) (List<?>) this.executeSqlListBaseModel(session, "select * from darwin_core where scientificname = :sn", name, parameter, new DarwinCore());
        }
        if ((a.getGenre() != null && !a.getGenre().isEmpty()) && (a.getEspece() == null || a.getEspece().isEmpty()) && (a.getFamille() == null || a.getFamille().isEmpty())) {
            List<String> name = new ArrayList<>();
            name.add("sn");
            List<Object> parameter = new ArrayList<>();
            parameter.add(a.getGenre());
            return (List<DarwinCore>) (List<?>) this.executeSqlListBaseModel(session, "select * from darwin_core where genus = :sn", name, parameter, new DarwinCore());
        }
        if ((a.getEspece() == null || a.getEspece().isEmpty()) && (a.getGenre() == null || a.getGenre().isEmpty()) && (a.getFamille() != null && !a.getFamille().isEmpty())) {
            List<String> name = new ArrayList<>();
            name.add("sn");
            List<Object> parameter = new ArrayList<>();
            parameter.add(a.getFamille());
            return (List<DarwinCore>) (List<?>) this.executeSqlListBaseModel(session, "select * from darwin_core where family = :sn", name, parameter, new DarwinCore());
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

    public List<HashMap<String, Object>> findWithCheck(Utilisateur utilisateur, DarwinCore darwinCore) throws Exception {
        List<HashMap<String, Object>> valiny = new ArrayList<>();
        List<DarwinCore> val = findMutliCritere(darwinCore);
        try {            
            List<DarwinCore> toCheck = getListObservationFor(utilisateur);
//            val.removeAll(toCheck);
            for(int i = 0; i < val.size(); i++) {
                for(int j = 0; j < toCheck.size(); j++) {
                    if(val.get(i).getId()==toCheck.get(j).getId()) {
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

    public HistoriqueStatus checkStatus(DarwinCore observation) throws Exception {
        HistoriqueStatus status = new HistoriqueStatus();
        status.setIdDwc(observation.getId());
        return (HistoriqueStatus) super.findMultiCritere(status, "dateModification", 1).get(0);
    }

    public HistoriqueStatus checkStatus(Session session, DarwinCore observation) throws Exception {
        HistoriqueStatus status = new HistoriqueStatus();
        status.setIdDwc(observation.getId());
        return (HistoriqueStatus) super.findMultiCritere(session, status, "dateModification", 1).get(0);
    }

    public void changeStatus(DarwinCore observation, Utilisateur expert, String newStatus) throws Exception {
        try {
            HistoriqueStatus status = checkStatus(observation);
            if (status == null) {
                throw new NullPointerException();
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
        } catch (Exception e) {
            throw e;
        }
    }

    public void changeStatus(Session session, DarwinCore observation, Utilisateur expert, String newStatus) throws Exception {
        try {
            HistoriqueStatus status = checkStatus(session, observation);
            if (status == null) {
                throw new NullPointerException();
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
        } catch (Exception e) {
            throw e;
        }
    }

    public void changeStatusAll(List<DarwinCore> observations, Utilisateur expert, String newStatus) throws StatusAlreadyExistException, Exception {
        Session session = null;
        try {
            session = this.getHibernateDao().getSessionFactory().openSession();
            for (DarwinCore observation : observations) {
                try {
                    changeStatus(session, observation, expert, newStatus);
                } catch (StatusAlreadyExistException ex) {
                    ex.getObservationRestante().addAll(observations.subList(observations.indexOf(ex.getObservationEnCours()), observations.size()));
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

    public void validerAll(List<DarwinCore> observation, Utilisateur expert) throws Exception {
        changeStatusAll(observation, expert, "valide");
    }

    public void questionnable(DarwinCore observation, Utilisateur expert) throws Exception {
        changeStatus(observation, expert, "questionnable");
    }

    public void questionnableAll(List<DarwinCore> observation, Utilisateur expert) throws Exception {
        changeStatusAll(observation, expert, "questionnable");
    }

    public DarwinCoreDao getDarwinCoreDao() {
        return DarwinCoreDao;
    }

    public void setDarwinCoreDao(DarwinCoreDao DarwinCoreDao) {
        this.DarwinCoreDao = DarwinCoreDao;
    }

}
