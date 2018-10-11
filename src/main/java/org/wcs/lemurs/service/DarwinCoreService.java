/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.service;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.wcs.lemurs.controller.BaseController;
import org.wcs.lemurs.dao.DarwinCoreDao;
import org.wcs.lemurs.exception.StatusAlreadyExistException;
import org.wcs.lemurs.model.BaseModel;
import org.wcs.lemurs.model.CommentaireDarwinCore;
import org.wcs.lemurs.model.DarwinCore;
import org.wcs.lemurs.model.Notifications;
import org.wcs.lemurs.model.ObservationParAdmin;
import org.wcs.lemurs.model.PhotoDarwinCore;
import org.wcs.lemurs.model.Role;
import org.wcs.lemurs.model.Utilisateur;
import org.wcs.lemurs.model.ValidationDarwinCore;
import org.wcs.lemurs.model.VideoDarwinCore;
import org.wcs.lemurs.model.json.Liste;
import org.wcs.lemurs.modele_association.AssignationExpert;
import org.wcs.lemurs.modele_association.AssociationDownloadInformationObservation;
import org.wcs.lemurs.modele_association.AssociationNotificationsObservation;
import org.wcs.lemurs.modele_association.HistoriqueStatus;
import org.wcs.lemurs.modele_association.RoleUtilisateur;
import org.wcs.lemurs.modele_vue.VueDarwinCoreRechercheGlobale;
import org.wcs.lemurs.modele_vue.VueRechercheDarwinCore;
import org.wcs.lemurs.modele_vue.VueValidationDarwinCore;

/**
 *
 * @author rudyr
 */
@Service
@Transactional
public class DarwinCoreService extends MailService {

    @Autowired(required = true)
    @Qualifier("darwinCoreDao")
    private DarwinCoreDao darwinCoreDao;

    //LAT-LONG
    List<BigDecimal[]> terreMadagascarLeft = new ArrayList<>();
    List<BigDecimal[]> terreMadagascarRight = new ArrayList<>();

    public DarwinCoreService() {
        terreMadagascarLeft.add(new BigDecimal[]{BigDecimal.valueOf(-11.966838), BigDecimal.valueOf(49.353167)});
        terreMadagascarLeft.add(new BigDecimal[]{BigDecimal.valueOf(-12.439312), BigDecimal.valueOf(48.825824)});
        terreMadagascarLeft.add(new BigDecimal[]{BigDecimal.valueOf(-12.868090), BigDecimal.valueOf(48.913714)});
        terreMadagascarLeft.add(new BigDecimal[]{BigDecimal.valueOf(-13.424405), BigDecimal.valueOf(48.781878)});
        terreMadagascarLeft.add(new BigDecimal[]{BigDecimal.valueOf(-13.680735), BigDecimal.valueOf(47.815082)});
        terreMadagascarLeft.add(new BigDecimal[]{BigDecimal.valueOf(-14.703218), BigDecimal.valueOf(47.375628)});
        terreMadagascarLeft.add(new BigDecimal[]{BigDecimal.valueOf(-15.720935), BigDecimal.valueOf(46.101214)});
        terreMadagascarLeft.add(new BigDecimal[]{BigDecimal.valueOf(-16.227913), BigDecimal.valueOf(44.431292)});
        terreMadagascarLeft.add(new BigDecimal[]{BigDecimal.valueOf(-17.615281), BigDecimal.valueOf(43.816058)});
        terreMadagascarLeft.add(new BigDecimal[]{BigDecimal.valueOf(-19.945002), BigDecimal.valueOf(44.387347)});
        terreMadagascarLeft.add(new BigDecimal[]{BigDecimal.valueOf(-22.159477), BigDecimal.valueOf(43.244769)});
        terreMadagascarLeft.add(new BigDecimal[]{BigDecimal.valueOf(-24.379672), BigDecimal.valueOf(43.640277)});
        terreMadagascarLeft.add(new BigDecimal[]{BigDecimal.valueOf(-25.693562), BigDecimal.valueOf(45.266253)});

        terreMadagascarRight.add(new BigDecimal[]{BigDecimal.valueOf(-11.966838), BigDecimal.valueOf(49.353167)});
        terreMadagascarRight.add(new BigDecimal[]{BigDecimal.valueOf(-15.339869), BigDecimal.valueOf(50.495746)});
        terreMadagascarRight.add(new BigDecimal[]{BigDecimal.valueOf(-17.657160), BigDecimal.valueOf(49.572894)});
        terreMadagascarRight.add(new BigDecimal[]{BigDecimal.valueOf(-25.137875), BigDecimal.valueOf(47.068011)});
        terreMadagascarRight.add(new BigDecimal[]{BigDecimal.valueOf(-25.693562), BigDecimal.valueOf(45.266253)});
    }

    public boolean latLongIn(double latitude, double longitude) {
        BigDecimal lat = BigDecimal.valueOf(latitude);
        BigDecimal lon = BigDecimal.valueOf(longitude);
        BigDecimal[] point = {lat, lon};
        BigDecimal temp = BigDecimal.valueOf(999);
        BigDecimal tempSeconde = BigDecimal.valueOf(999);
        int first = 0;
        int seconde = 0;
        if (lat.abs().compareTo(terreMadagascarLeft.get(0)[0].abs()) < 0) {
            return false;
        }
        if (lat.abs().compareTo(terreMadagascarLeft.get(12)[0].abs()) > 0) {
            return false;
        }
        for (int i = 0; i < terreMadagascarLeft.size(); i++) {
            BigDecimal difference = lat.subtract(terreMadagascarLeft.get(i)[0]).abs();
            if (difference.compareTo(temp.abs()) < 0) {
                seconde = first;
                first = i;
                tempSeconde = temp;
                temp = difference;
            } else if (difference.compareTo(tempSeconde.abs()) < 0) {
                seconde = i;
                tempSeconde = difference;
            }
        }
        if (situationX(terreMadagascarLeft.get(first), terreMadagascarLeft.get(seconde), point) == 0) {
            return true;
        } else if (situationX(terreMadagascarLeft.get(first), terreMadagascarLeft.get(seconde), point) < 0) {
            return false;
        }
        temp = BigDecimal.valueOf(999);
        tempSeconde = BigDecimal.valueOf(999);
        first = 0;
        seconde = 0;
        for (int i = 0; i < terreMadagascarRight.size(); i++) {
            BigDecimal difference = lat.subtract(terreMadagascarRight.get(i)[0]).abs();
            if (difference.compareTo(temp.abs()) < 0) {
                seconde = first;
                first = i;
                tempSeconde = temp;
                temp = difference;
            } else if (difference.compareTo(tempSeconde.abs()) < 0) {
                seconde = i;
                tempSeconde = difference;
            }
        }
        if (situationX(terreMadagascarRight.get(first), terreMadagascarRight.get(seconde), point) == 0) {
            return true;
        } else if (situationX(terreMadagascarRight.get(first), terreMadagascarRight.get(seconde), point) > 0) {
            return false;
        }
        return true;
    }

    public int situationX(BigDecimal[] p1, BigDecimal[] p2, BigDecimal[] point) {
        BigDecimal x = point[0].subtract(p1[0].subtract(p2[0].subtract(p1[0]).divide(p2[1].subtract(p1[1]), 6).multiply(p1[1]))).divide(p2[0].subtract(p1[0]).divide(p2[1].subtract(p1[1]), 6), 6);
        if (point[1].compareTo(x) < 0) {
            return -1;
        } else if (point[1].compareTo(x) > 0) {
            return 1;
        }
        return 0;
    }

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

    private void prettySyntax(DarwinCore dw) {
        if (dw.getFamily() != null) {
            dw.setFamily(dw.getFamily().toUpperCase());
        }

        if (dw.getGenus() != null) {
            dw.setGenus(dw.getGenus().toUpperCase());
        }

        if (dw.getScientificname() != null && !dw.getScientificname().isEmpty()) {
            String low = dw.getScientificname().toLowerCase();
            try {
                String[] listLow = low.split(" ");
                low = "";
                String s = listLow[0];
                s = s.substring(0, 1).toUpperCase() + s.substring(1);
                low += s + " ";
                low += listLow[1];
                dw.setScientificname(low);
            } catch (Exception e) {
                System.err.println("Le scientificname est fausse. Elle doit au moins contenir 2 mots et ainsi de suite");
            }
        }

        if (dw.getDecimallatitude() != null) {
            String temp = dw.getDecimallatitude();
            temp = temp.replace(',', '.');
            temp = temp.replace(" ", "");
            dw.setDecimallatitude(temp);
        }

        if (dw.getDecimallongitude() != null) {
            String temp = dw.getDecimallongitude();
            temp = temp.replace(',', '.');
            temp = temp.replace(" ", "");
            dw.setDecimallongitude(temp);
        }
    }

    public void correctionSyntax() throws Exception {
        Session session = null;
        Transaction tr = null;
        try {
            session = getHibernateDao().getSessionFactory().openSession();
            tr = session.beginTransaction();
            List<DarwinCore> liste = (List<DarwinCore>) (List<?>) this.findMultiCritere(session, new DarwinCore());
            for (DarwinCore dw : liste) {
                try {
                    prettySyntax(dw);
                } catch (java.lang.StringIndexOutOfBoundsException | java.lang.ArrayIndexOutOfBoundsException sobe) {
                    continue;
                }
                this.save(session, dw);
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

    public void correctionSyntax(DarwinCore dw) {
        try {
            if (dw.getFamily() != null) {
                dw.setFamily(formatterDarwinCore(dw.getFamily().toUpperCase()));
            }
            if (dw.getGenus() != null) {
                dw.setGenus(formatterDarwinCore(dw.getGenus().toUpperCase()));
            }
            if (dw.getScientificname() != null && !dw.getScientificname().isEmpty()) {
                String low = dw.getScientificname().toLowerCase();
                String[] listLow = low.split(" ");
                low = "";
                for (String s : listLow) {
                    s = s.substring(0, 1).toUpperCase() + s.substring(1);
                    low += s + " ";
                }
                low = low.substring(0, low.length() - 1);
                dw.setScientificname(formatterDarwinCore(low));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }
    }

    public List<DarwinCore> upload(List<DarwinCore> list_dw) throws Exception {
        Session session = null;
        Transaction tr = null;
        int index = 1;
        try {
            session = getHibernateDao().getSessionFactory().openSession();
            tr = session.beginTransaction();
            for (DarwinCore dw : list_dw) {
                if (dw.getScientificname() == null) {
                    continue;
                }
                prettySyntax(dw);
                if (dw.getIdRebioma() != null) {
                    try {
                        DarwinCore dcTemp = new DarwinCore();
                        dcTemp.setIdRebioma(dw.getIdRebioma());
                        List<DarwinCore> liste = (List<DarwinCore>) (List<?>) this.findMultiCritere(session, dcTemp);
                        DarwinCore tempDwc = liste.get(0);
                        dw.setId(tempDwc.getId());
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("insertion nouveau darwin_core de rebioma");
                    }
                }
                correctionSyntax(dw);
                try {
                    this.save(session, dw);
                } catch (NonUniqueObjectException nuoe) {
                    System.out.println(dw);
                    session.merge(dw);
                }
                ValidationDarwinCore vdc = new ValidationDarwinCore();
                vdc.setIdDarwinCore(dw.getId());
                try {
                    vdc = (ValidationDarwinCore) findMultiCritere(session, vdc).get(0);
                } catch (Exception e) {
                }
                vdc.setAcceptedSpeces(checkVerbatimspecies(session, dw));
                try {
                    vdc.setAnnee(!dw.getDwcyear().isEmpty() && dw.getDwcyear().compareTo("-") != 0);
                } catch (NullPointerException npe) {
                    vdc.setAnnee(Boolean.FALSE);
                }
                try {
                    vdc.setCollecteur(!dw.getRecordedby().isEmpty() && dw.getRecordedby().compareTo("-") != 0);
                } catch (NullPointerException npe) {
                    vdc.setCollecteur(Boolean.FALSE);
                }
                try {
                    vdc.setGps(!dw.getDecimallatitude().isEmpty() && !dw.getDecimallongitude().isEmpty() && dw.getDecimallatitude().compareTo("-") != 0 && dw.getDecimallongitude().compareTo("-") != 0);
                    if (vdc.isGps()) {
                        double lat = Double.parseDouble(dw.getDecimallatitude());
                        double lon = Double.parseDouble(dw.getDecimallongitude());
                        vdc.setGps(latLongIn(lat, lon));
                    }
                } catch (NullPointerException npe) {
                    vdc.setGps(Boolean.FALSE);
                }
                vdc.setValidationExpert(-1);
                save(session, vdc);
            }

            // Get all Admin
            Role role = new Role();
            role.setDesignation(BaseController.ROLE_ADMINISTRATEUR);
            role = (Role) super.findAll(session, role, -1, -1).get(0);
            RoleUtilisateur roleUtilisateur = new RoleUtilisateur();
            roleUtilisateur.setIdRole(role.getId());
            List<RoleUtilisateur> ruAdmin = (List<RoleUtilisateur>) (List<?>) super.findAll(session, roleUtilisateur, -1, -1);

            // Make a notification for admin
            for (RoleUtilisateur admin : ruAdmin) {
                Notifications notification = new Notifications();
                notification.setCategorie(BaseController.NOTIFICATION_ADDED);
                notification.setDate(Calendar.getInstance().getTime());
                if (list_dw.get(0).getIdUtilisateurUpload() != null) {
                    notification.setIdUtilisateur(list_dw.get(0).getIdUtilisateurUpload());
                    if (admin.getIdUtilisateur() == list_dw.get(0).getIdUtilisateurUpload()) {
                        continue;
                    }
                }
                notification.setListeAssociationNotificationsObservation(new ArrayList<>());
                list_dw.stream().map((dw) -> {
                    AssociationNotificationsObservation ano = new AssociationNotificationsObservation();
                    ano.setIdObservation(dw.getId());
                    return ano;
                }).forEachOrdered((ano) -> {
                    notification.getListeAssociationNotificationsObservation().add(ano);
                });
                notification.setNbrFille(list_dw.size());
                notification.setVue(-1);
                notification.setIdCible(admin.getIdUtilisateur());
                super.save(session, notification);
            }
            tr.commit();
            try {
                this.sendMailToExpert(list_dw);
            } catch (Exception e) {
                System.out.println("L'envoie de l'email est un echec, veuiller vérifier si les conditions sont optimales");
            }
            return list_dw;
        } catch (Exception ex) {
            tr.rollback();
            System.out.println("Erreur lors de l'upload : l'erreur suit");
            ex.printStackTrace();
            String message = "Line :" + index + " | \n " + ex.getMessage();
            throw new Exception(message);
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
        if (dw.getInfraspecificepithet() == null || dw.getInfraspecificepithet().equals(" ") || dw.getInfraspecificepithet().isEmpty()) {
            dw.setInfraspecificepithet("-");
        }
        String qry = "select count(*) from (select *,genus || specificepithet || infraspecificepithet as verbatimspecies from taxonomi_base) as sous where sous.verbatimspecies ilike :verbatimspecies";
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
            parameter.add(MailService.formatterDarwinCore(a.getEspece()));
            return (List<VueValidationDarwinCore>) (List<?>) this.executeSqlListBaseModel(session, "select * from vue_validation_darwin_core where scientificname = :sn and annee = true and collecteur = true and accepted_speces = true and gps = true", name, parameter, new VueValidationDarwinCore());
        }
        if ((a.getGenre() != null && !a.getGenre().isEmpty()) && (a.getEspece() == null || a.getEspece().isEmpty()) && (a.getFamille() == null || a.getFamille().isEmpty())) {
            List<String> name = new ArrayList<>();
            name.add("sn");
            List<Object> parameter = new ArrayList<>();
            parameter.add(MailService.formatterDarwinCore(a.getGenre()));
            return (List<VueValidationDarwinCore>) (List<?>) this.executeSqlListBaseModel(session, "select * from vue_validation_darwin_core where genus = :sn and annee = true and collecteur = true and accepted_speces = true and gps = true", name, parameter, new VueValidationDarwinCore());
        }
        if ((a.getEspece() == null || a.getEspece().isEmpty()) && (a.getGenre() == null || a.getGenre().isEmpty()) && (a.getFamille() != null && !a.getFamille().isEmpty())) {
            List<String> name = new ArrayList<>();
            name.add("sn");
            List<Object> parameter = new ArrayList<>();
            parameter.add(MailService.formatterDarwinCore(a.getFamille()));
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

    public List<VueValidationDarwinCore> getListObservationAndEtatFor(Utilisateur utilisateur, int page, int nombre) throws Exception {
        AssignationExpert aes = new AssignationExpert();
        aes.setIdExpert(utilisateur.getId());
        List<AssignationExpert> domaine = (List<AssignationExpert>) (List<?>) this.findAll(aes, page, nombre);
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

    public boolean checkValidable(Session session, VueValidationDarwinCore dwc, Utilisateur u) throws Exception {
        AssignationExpert ase = new AssignationExpert();
        ase.setIdExpert(u.getId());
        List<AssignationExpert> listeAseTemp = (List<AssignationExpert>) (List<?>) this.findMultiCritere(ase);
        for (AssignationExpert aeTemp : listeAseTemp) {
            if (!dwc.getAnnee() || !dwc.getAccepted_speces() || !dwc.getCollecteur() || !dwc.getGps()) {
                return false;
            }
            if (dwc.getIdRebioma() != null || dwc.getIdUtilisateurUpload() == null) {
                return false;
            }
            try {
                String df = dwc.getFamily();
                String uf = MailService.formatterDarwinCore(aeTemp.getFamille());
                if (MailService.formatterDarwinCore(df).compareToIgnoreCase(uf) == 0) {
                    return true;
                }
            } catch (NullPointerException npe) {
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (MailService.formatterDarwinCore(dwc.getGenus()).compareToIgnoreCase(MailService.formatterDarwinCore(aeTemp.getGenre())) == 0) {
                    return true;
                }
            } catch (NullPointerException npe) {
            }
            try {
                if (MailService.formatterDarwinCore(dwc.getScientificname()).compareToIgnoreCase(MailService.formatterDarwinCore(aeTemp.getEspece())) == 0) {
                    return true;
                }
            } catch (NullPointerException npe) {
            }
        }
        return false;
    }

    public boolean checkValidable(Session session, VueDarwinCoreRechercheGlobale dwc, Utilisateur u) throws Exception {
        AssignationExpert ase = new AssignationExpert();
        ase.setIdExpert(u.getId());
        List<AssignationExpert> listeAseTemp = (List<AssignationExpert>) (List<?>) this.findMultiCritere(ase);
        for (AssignationExpert aeTemp : listeAseTemp) {
            if (!dwc.getAnnee() || !dwc.getAccepted_speces() || !dwc.getCollecteur() || !dwc.getGps()) {
                return false;
            }
            if (dwc.getIdRebioma() != null || dwc.getIdUtilisateurUpload() == null) {
                return false;
            }
            try {
                String df = dwc.getFamily();
                String uf = MailService.formatterDarwinCore(aeTemp.getFamille());
                if (MailService.formatterDarwinCore(df).compareToIgnoreCase(uf) == 0) {
                    return true;
                }
            } catch (NullPointerException npe) {
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (MailService.formatterDarwinCore(dwc.getGenus()).compareToIgnoreCase(MailService.formatterDarwinCore(aeTemp.getGenre())) == 0) {
                    return true;
                }
            } catch (NullPointerException npe) {
            }
            try {
                if (MailService.formatterDarwinCore(dwc.getScientificname()).compareToIgnoreCase(MailService.formatterDarwinCore(aeTemp.getEspece())) == 0) {
                    return true;
                }
            } catch (NullPointerException npe) {
            }
        }
        return false;
    }

    public List<HashMap<String, Object>> findWithCheckAndEtat(Utilisateur utilisateur, VueValidationDarwinCore darwinCore, int nombre, int page) throws Exception {
        List<HashMap<String, Object>> valiny = new ArrayList<>();
        List<VueValidationDarwinCore> val = (List<VueValidationDarwinCore>) (List<?>) findAll(utilisateur, darwinCore, page, nombre);
        List<PhotoDarwinCore> photos = new ArrayList<>();
        Session session = null;
        try {
            session = getHibernateDao().getSessionFactory().openSession();
            for (VueValidationDarwinCore dwc : val) {
                HashMap<String, Object> temp = new HashMap<>();
                temp.put("dwc", dwc);
                try {
                    if (checkValidable(session, dwc, utilisateur)) {
                        temp.put("validation", 1);
                    } else {
                        temp.put("validation", 0);
                    }
                } catch (java.lang.NullPointerException npe) {
                    temp.put("validation", 0);
                }
                valiny.add(temp);
            }
            PhotoDarwinCore photoDarwinCoreTemp = new PhotoDarwinCore();
            photoDarwinCoreTemp.setProfil(Boolean.TRUE);
            photos = (List<PhotoDarwinCore>) (List<?>) this.findMultiCritere(photoDarwinCoreTemp);
        } catch (Exception e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
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
                v.put("photo", "resources/assets/img/photos/default.jpg");
            }
        }
        return valiny;
    }

    public List<HashMap<String, Object>> findForValidation(Utilisateur utilisateur, BaseModel darwinCore, int nombre, int page) throws Exception {
        List<HashMap<String, Object>> valiny = new ArrayList<>();
        List<BaseModel> val = darwinCoreDao.findAllToValidate(utilisateur, darwinCore, page, nombre);
        Session session = null;
        try {
            session = getHibernateDao().getSessionFactory().openSession();
            try {
                List<VueValidationDarwinCore> temps = (List<VueValidationDarwinCore>) (List<?>) val;
                for (VueValidationDarwinCore dwc : temps) {
                    HashMap<String, Object> temp = new HashMap<>();
                    temp.put("dwc", dwc);
                    try {
                        if (checkValidable(session, dwc, utilisateur)) {
                            temp.put("validation", 1);
                        } else {
                            temp.put("validation", 0);
                        }
                    } catch (java.lang.NullPointerException npe) {
                        temp.put("validation", 0);
                    }
                    valiny.add(temp);
                }
            } catch (java.lang.ClassCastException cce) {
                List<VueDarwinCoreRechercheGlobale> temps = (List<VueDarwinCoreRechercheGlobale>) (List<?>) val;
                for (VueDarwinCoreRechercheGlobale dwc : temps) {
                    HashMap<String, Object> temp = new HashMap<>();
                    temp.put("dwc", dwc);
                    try {
                        if (checkValidable(session, dwc, utilisateur)) {
                            temp.put("validation", 1);
                        } else {
                            temp.put("validation", 0);
                        }
                    } catch (java.lang.NullPointerException npe) {
                        temp.put("validation", 0);
                    }
                    valiny.add(temp);
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return valiny;
    }

    public List<HashMap<String, Object>> findWithCheckAndEtat(Utilisateur utilisateur, VueDarwinCoreRechercheGlobale darwinCore, int nombre, int page) throws Exception {
        List<HashMap<String, Object>> valiny = new ArrayList<>();
        List<VueDarwinCoreRechercheGlobale> val = (List<VueDarwinCoreRechercheGlobale>) (List<?>) findAll(utilisateur, darwinCore, page, nombre);
        List<PhotoDarwinCore> photos = new ArrayList<>();
        Session session = null;
        try {
            session = getHibernateDao().getSessionFactory().openSession();
            for (VueDarwinCoreRechercheGlobale dwc : val) {
                HashMap<String, Object> temp = new HashMap<>();
                temp.put("dwc", dwc);
                try {
                    if (checkValidable(session, dwc, utilisateur)) {
                        temp.put("validation", 1);
                    } else {
                        temp.put("validation", 0);
                    }
                } catch (java.lang.NullPointerException npe) {
                    temp.put("validation", 0);
                }
                valiny.add(temp);
            }
            PhotoDarwinCore photoDarwinCoreTemp = new PhotoDarwinCore();
            photoDarwinCoreTemp.setProfil(Boolean.TRUE);
            photos = (List<PhotoDarwinCore>) (List<?>) this.findMultiCritere(photoDarwinCoreTemp);
        } catch (Exception e) {
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
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
                v.put("photo", "resources/assets/img/photos/default.jpg");
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
            if (dwc.getIdRebioma() == null && dwc.getIdUtilisateurUpload() != null) {
                temp.put("validation", 1);
            } else {
                temp.put("validation", 0);
            }
            valiny.add(temp);
        }
        return valiny;
    }

    public List<HashMap<String, Object>> findObservationAndEtatCheck(Utilisateur utilisateur, int etatValidation) throws Exception {
        List<HashMap<String, Object>> valiny = new ArrayList<>();
        List<VueValidationDarwinCore> val = getListObservationAndEtatFor(utilisateur);
        if (etatValidation == -999) {
            for (VueValidationDarwinCore dwc : val) {
                HashMap<String, Object> temp = new HashMap<>();
                temp.put("dwc", dwc);
                if (dwc.getIdRebioma() == null && dwc.getIdUtilisateurUpload() != null) {
                    temp.put("validation", 1);
                } else {
                    temp.put("validation", 0);
                }
                valiny.add(temp);
            }
        } else {
            for (VueValidationDarwinCore dwc : val) {
                if (dwc.getValidationexpert() == etatValidation) {
                    HashMap<String, Object> temp = new HashMap<>();
                    temp.put("dwc", dwc);
                    if (dwc.getIdRebioma() == null && dwc.getIdUtilisateurUpload() != null) {
                        temp.put("validation", 1);
                    } else {
                        temp.put("validation", 0);
                    }
                    valiny.add(temp);
                }
            }
        }
        return valiny;
    }

    public List<HashMap<String, Object>> findObservationAndEtatCheck(Utilisateur utilisateur, int etatValidation, int page, int nombre) throws Exception {
        List<HashMap<String, Object>> valiny = new ArrayList<>();
        List<VueValidationDarwinCore> val = getListObservationAndEtatFor(utilisateur, page, nombre);
        if (etatValidation == -999) {
            for (VueValidationDarwinCore dwc : val) {
                HashMap<String, Object> temp = new HashMap<>();
                temp.put("dwc", dwc);
                if (dwc.getIdRebioma() == null && dwc.getIdUtilisateurUpload() != null) {
                    temp.put("validation", 1);
                } else {
                    temp.put("validation", 0);
                }
                valiny.add(temp);
            }
        } else {
            for (VueValidationDarwinCore dwc : val) {
                if (dwc.getValidationexpert() == etatValidation) {
                    HashMap<String, Object> temp = new HashMap<>();
                    temp.put("dwc", dwc);
                    if (dwc.getIdRebioma() == null && dwc.getIdUtilisateurUpload() != null) {
                        temp.put("validation", 1);
                    } else {
                        temp.put("validation", 0);
                    }
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
                if (dwc.getIdRebioma() == null && dwc.getIdUtilisateurUpload() != null) {
                    temp.put("validation", 1);
                } else {
                    temp.put("validation", 0);
                }
                valiny.add(temp);
            }
        } else {
            for (VueValidationDarwinCore dwc : val) {
                if (dwc.getValidationexpert() == etatValidation) {
                    HashMap<String, Object> temp = new HashMap<>();
                    temp.put("dwc", dwc);
                    if (dwc.getIdRebioma() == null && dwc.getIdUtilisateurUpload() != null) {
                        temp.put("validation", 1);
                    } else {
                        temp.put("validation", 0);
                    }
                    valiny.add(temp);
                }
            }
        }
        return valiny;
    }

    public List<HashMap<String, Object>> findObservationAndEtatCheckOf(Utilisateur utilisateur, int etatValidation, int page, int nombre) throws Exception {
        List<HashMap<String, Object>> valiny = new ArrayList<>();
        VueValidationDarwinCore vvdTemp = new VueValidationDarwinCore();
        vvdTemp.setIdUtilisateurUpload(utilisateur.getId());
        List<VueValidationDarwinCore> val = (List<VueValidationDarwinCore>) (List<?>) findAll(vvdTemp, page, nombre);
        if (etatValidation == -999) {
            for (VueValidationDarwinCore dwc : val) {
                HashMap<String, Object> temp = new HashMap<>();
                temp.put("dwc", dwc);
                if (dwc.getIdRebioma() == null && dwc.getIdUtilisateurUpload() != null) {
                    temp.put("validation", 1);
                } else {
                    temp.put("validation", 0);
                }
                valiny.add(temp);
            }
        } else {
            for (VueValidationDarwinCore dwc : val) {
                if (dwc.getValidationexpert() == etatValidation) {
                    HashMap<String, Object> temp = new HashMap<>();
                    temp.put("dwc", dwc);
                    if (dwc.getIdRebioma() == null && dwc.getIdUtilisateurUpload() != null) {
                        temp.put("validation", 1);
                    } else {
                        temp.put("validation", 0);
                    }
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
        List<DarwinCore> ltemp = new ArrayList<>();
        ltemp.add(dwc);
//        this.sendMailToChercheur(vdc);
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
//        this.sendMailToChercheur(session, vdc);
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
            List<DarwinCore> ltemp = new ArrayList<>();
            ltemp.add(observation);
//            this.sendMailToExpert(ltemp);
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

    public List<PhotoDarwinCore> enregistrerPhoto(MultipartFile photo, DarwinCore darwinCore, Utilisateur utilisateur, boolean profil, String cheminReal, String datePrisePhoto) throws IOException, Exception {
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
            photoDarwinCore.setDatePrisPhotoString(datePrisePhoto);
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

    public void uploadDwc(String url) throws Exception {
        URL dwcCsv = new URL(url);
        BufferedReader in = new BufferedReader(new InputStreamReader(dwcCsv.openStream()));
        String header = in.readLine();
        Field[] attriburs = DarwinCore.class.getDeclaredFields();
        List<HashMap<String, Object>> fonctions = getFonctionNumeroColonneDwc(attriburs, header, ";");
        List<DarwinCore> listeToSave = new ArrayList<>();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            inputLine = inputLine.replaceAll(";", " ;");
            String[] dwcLine = inputLine.split(";");
            DarwinCore dwcTemp = new DarwinCore();
            for (HashMap<String, Object> fonction : fonctions) {
                Method m = (Method) fonction.get("fonction");
                try {
                    m.invoke(dwcTemp, dwcLine[(Integer) fonction.get("id")]);
                } catch (IllegalArgumentException iae) {
                    try {
                        String sToInt = dwcLine[(Integer) fonction.get("id")];
                        sToInt = sToInt.replaceAll(" ", "");
                        Integer tempInt = Integer.decode(sToInt);
                        m.invoke(dwcTemp, tempInt);
                    } catch (Exception iaex) {
                        throw iaex;
                    }
                } catch (IndexOutOfBoundsException aioobe) {
                    try {
                        m.invoke(dwcTemp, "");
                    } catch (IllegalArgumentException iae) {
                    }
                }
            }
            listeToSave.add(dwcTemp);
        }
        for (DarwinCore t : listeToSave) {
            t.setLienSource(url);
            t.setPublique(Boolean.TRUE);
        }
        upload(listeToSave);
        in.close();
    }

    public List<HashMap<String, Object>> getFonctionNumeroColonneDwc(Field[] attributs, String header, String sep) throws NoSuchMethodException {
        String[] colonnesHeader = header.split(sep);
        List<HashMap<String, Object>> fonctions = new ArrayList<>();
        for (Field f : attributs) {
            for (int i = 0; i < colonnesHeader.length; i++) {
                String csv = colonnesHeader[i].toLowerCase();
                String base = f.getName().toLowerCase();
                base = base.replaceAll("dwc", "");
                base = base.replaceAll("darwinclass", "class");
                base = base.replaceAll("darwinorder", "order");
                base = base.replaceAll("idrebioma", "id");
                if (base.compareTo(csv) == 0) {
                    HashMap<String, Object> fonction = new HashMap<>();
                    fonction.put("id", i);
                    try {
                        fonction.put("fonction", DarwinCore.class.getMethod("set" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1), String.class));
                    } catch (NoSuchMethodException nsme) {
                        fonction.put("fonction", DarwinCore.class.getMethod("set" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1), Integer.class));
                    }
                    fonctions.add(fonction);
                    break;
                }
            }
        }
        return fonctions;
    }

    public void delete(DarwinCore dwc) {
        Session session = null;
        Transaction tr = null;
        try {
            session = this.darwinCoreDao.getSessionFactory().openSession();
            ValidationDarwinCore vdc = new ValidationDarwinCore();
            vdc.setIdDarwinCore(dwc.getId());
            List<ValidationDarwinCore> listeVdcToDelete = (List<ValidationDarwinCore>) (List<?>) super.findAll(session, vdc, -1, -1);
            AssociationDownloadInformationObservation adio = new AssociationDownloadInformationObservation();
            adio.setIdObservation(dwc.getId());
            List<AssociationDownloadInformationObservation> listAdio = (List<AssociationDownloadInformationObservation>) (List<?>) super.findAll(session, adio, -1, -1);
            PhotoDarwinCore pdc = new PhotoDarwinCore();
            pdc.setIdDarwinCore(dwc.getId());
            List<PhotoDarwinCore> listePdc = (List<PhotoDarwinCore>) (List<?>) super.findAll(session, pdc, -1, -1);
            VideoDarwinCore vidc = new VideoDarwinCore();
            vidc.setIdDarwinCore(dwc.getId());
            List<VideoDarwinCore> listeVidc = (List<VideoDarwinCore>) (List<?>) super.findAll(session, vidc, -1, -1);
            CommentaireDarwinCore cdc = new CommentaireDarwinCore();
            cdc.setIdDarwinCore(dwc.getId());
            List<CommentaireDarwinCore> listeCdc = (List<CommentaireDarwinCore>) (List<?>) super.findAll(session, cdc, -1, -1);
            HistoriqueStatus hs = new HistoriqueStatus();
            hs.setIdDwc(dwc.getId());
            List<HistoriqueStatus> listeHs = (List<HistoriqueStatus>) (List<?>) super.findAll(session, hs, -1, -1);

            tr = session.beginTransaction();
            if (!listAdio.isEmpty()) {
                for (AssociationDownloadInformationObservation v : listAdio) {
                    super.delete(session, v);
                }
            }
            if (!listeVdcToDelete.isEmpty()) {
                for (ValidationDarwinCore v : listeVdcToDelete) {
                    super.delete(session, v);
                }
            }
            if (!listePdc.isEmpty()) {
                for (PhotoDarwinCore p : listePdc) {
                    super.delete(session, p);
                }
            }
            if (!listeVidc.isEmpty()) {
                for (VideoDarwinCore v : listeVidc) {
                    super.delete(session, v);
                }
            }
            if (!listeCdc.isEmpty()) {
                for (CommentaireDarwinCore v : listeCdc) {
                    super.delete(session, v);
                }
            }
            if (!listeHs.isEmpty()) {
                for (HistoriqueStatus v : listeHs) {
                    super.delete(session, v);
                }
            }
            super.delete(session, dwc);
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void delete(Session session, DarwinCore dwc) throws Exception {
        try {
            ValidationDarwinCore vdc = new ValidationDarwinCore();
            vdc.setIdDarwinCore(dwc.getId());
            List<ValidationDarwinCore> listeVdcToDelete = (List<ValidationDarwinCore>) (List<?>) super.findAll(session, vdc, -1, -1);
            AssociationDownloadInformationObservation adio = new AssociationDownloadInformationObservation();
            adio.setIdObservation(dwc.getId());
            List<AssociationDownloadInformationObservation> listAdio = (List<AssociationDownloadInformationObservation>) (List<?>) super.findAll(session, adio, -1, -1);
            PhotoDarwinCore pdc = new PhotoDarwinCore();
            pdc.setIdDarwinCore(dwc.getId());
            List<PhotoDarwinCore> listePdc = (List<PhotoDarwinCore>) (List<?>) super.findAll(session, pdc, -1, -1);
            VideoDarwinCore vidc = new VideoDarwinCore();
            vidc.setIdDarwinCore(dwc.getId());
            List<VideoDarwinCore> listeVidc = (List<VideoDarwinCore>) (List<?>) super.findAll(session, vidc, -1, -1);
            CommentaireDarwinCore cdc = new CommentaireDarwinCore();
            cdc.setIdDarwinCore(dwc.getId());
            List<CommentaireDarwinCore> listeCdc = (List<CommentaireDarwinCore>) (List<?>) super.findAll(session, cdc, -1, -1);
            HistoriqueStatus hs = new HistoriqueStatus();
            hs.setIdDwc(dwc.getId());
            List<HistoriqueStatus> listeHs = (List<HistoriqueStatus>) (List<?>) super.findAll(session, hs, -1, -1);

            if (!listAdio.isEmpty()) {
                for (AssociationDownloadInformationObservation v : listAdio) {
                    super.delete(session, v);
                }
            }
            if (!listeVdcToDelete.isEmpty()) {
                for (ValidationDarwinCore v : listeVdcToDelete) {
                    super.delete(session, v);
                }
            }
            if (!listePdc.isEmpty()) {
                for (PhotoDarwinCore p : listePdc) {
                    super.delete(session, p);
                }
            }
            if (!listeVidc.isEmpty()) {
                for (VideoDarwinCore v : listeVidc) {
                    super.delete(session, v);
                }
            }
            if (!listeCdc.isEmpty()) {
                for (CommentaireDarwinCore v : listeCdc) {
                    super.delete(session, v);
                }
            }
            if (!listeHs.isEmpty()) {
                for (HistoriqueStatus v : listeHs) {
                    super.delete(session, v);
                }
            }
            super.delete(session, dwc);
        } catch (Exception e) {
            throw e;
        }
    }

    public void delete(int[] liste, Utilisateur u, Boolean admin) {
        Session session = null;
        Transaction tr = null;
        try {
            int count = 0;
            session = this.darwinCoreDao.getSessionFactory().openSession();
            tr = session.beginTransaction();

            for (Integer i : liste) {
                DarwinCore dwc = new DarwinCore();
                dwc.setId(i);
                findById(session, dwc);
                if (admin || dwc.getIdUtilisateurUpload().equals(u.getId())) {
                    delete(session, dwc);
                    count++;
                } else {
                    System.err.println("Vous n'avez pas l'accréditation nécessaire à la supression de l'observation id = " + dwc.getId());
                }
            }
            if (u != null) {
                try {
                    ObservationParAdmin opa = new ObservationParAdmin();
                    opa.setIdUtilisateur(u.getId());
                    opa = (ObservationParAdmin) super.findAll(session, opa, 1, 1).get(0);
                    opa.setNbrObservation(opa.getNbrObservation() - count);
                    super.save(session, opa);
                } catch (IndexOutOfBoundsException ioobe) {
                    System.out.println("l'utilisateur n'est pas encore dans la table ObservationParAdmin");
                }
            }

            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

//    public List<DarwinCore> findMultiCritere(Utilisateur u, DarwinCore dwc) throws Exception {
//        if (u == null || u.getId() == null) {
//            return (List<DarwinCore>) (List<?>) super.findMultiCritere(dwc);
//        }
//        dwc.setPublique(Boolean.TRUE);
//        List<DarwinCore> valinyPublique = (List<DarwinCore>) (List<?>) super.findMultiCritere(dwc);
//        dwc.setPublique(Boolean.FALSE);
//        dwc.setIdUtilisateurUpload(u.getId());
//        valinyPublique.addAll((List<DarwinCore>) (List<?>) super.findMultiCritere(dwc));
//        return valinyPublique;
//    }
//    
//    public List<VueRechercheDarwinCore> findMultiCritere(Utilisateur u, VueRechercheDarwinCore dwc) throws Exception {
//        if (u == null || u.getId() == null) {
//            return (List<VueRechercheDarwinCore>) (List<?>) super.findMultiCritere(dwc);
//        }
//        dwc.setPublique(Boolean.TRUE);
//        List<VueRechercheDarwinCore> valinyPublique = (List<VueRechercheDarwinCore>) (List<?>) super.findMultiCritere(dwc);
//        dwc.setPublique(Boolean.FALSE);
//        dwc.setIdUtilisateurUpload(u.getId());
//        valinyPublique.addAll((List<VueRechercheDarwinCore>) (List<?>) super.findMultiCritere(dwc));
//        return valinyPublique;
//    }
//    
//    public List<VueValidationDarwinCore> findMultiCritere(Utilisateur u, VueValidationDarwinCore dwc) throws Exception {
//        if (u == null || u.getId() == null) {
//            return (List<VueValidationDarwinCore>) (List<?>) super.findMultiCritere(dwc);
//        }
//        dwc.setPublique(Boolean.TRUE);
//        List<VueValidationDarwinCore> valinyPublique = (List<VueValidationDarwinCore>) (List<?>) super.findMultiCritere(dwc);
//        dwc.setPublique(Boolean.FALSE);
//        dwc.setIdUtilisateurUpload(u.getId());
//        valinyPublique.addAll((List<VueValidationDarwinCore>) (List<?>) super.findMultiCritere(dwc));
//        return valinyPublique;
//    }
//    
//    public List<DarwinCore> findMultiCritere(Session session, Utilisateur u, DarwinCore dwc) throws Exception {
//        if (u == null || u.getId() == null) {
//            return (List<DarwinCore>) (List<?>) super.findMultiCritere(session, dwc);
//        }
//        dwc.setPublique(Boolean.TRUE);
//        List<DarwinCore> valinyPublique = (List<DarwinCore>) (List<?>) super.findMultiCritere(session, dwc);
//        dwc.setPublique(Boolean.FALSE);
//        dwc.setIdUtilisateurUpload(u.getId());
//        valinyPublique.addAll((List<DarwinCore>) (List<?>) super.findMultiCritere(session, dwc));
//        return valinyPublique;
//    }
//    
//    public List<VueRechercheDarwinCore> findMultiCritere(Session session, Utilisateur u, VueRechercheDarwinCore dwc) throws Exception {
//        if (u == null || u.getId() == null) {
//            return (List<VueRechercheDarwinCore>) (List<?>) super.findMultiCritere(session, dwc);
//        }
//        dwc.setPublique(Boolean.TRUE);
//        List<VueRechercheDarwinCore> valinyPublique = (List<VueRechercheDarwinCore>) (List<?>) super.findMultiCritere(session, dwc);
//        dwc.setPublique(Boolean.FALSE);
//        dwc.setIdUtilisateurUpload(u.getId());
//        valinyPublique.addAll((List<VueRechercheDarwinCore>) (List<?>) super.findMultiCritere(session, dwc));
//        return valinyPublique;
//    }
//    
//    public List<VueValidationDarwinCore> findMultiCritere(Session session, Utilisateur u, VueValidationDarwinCore dwc) throws Exception {
//        if (u == null || u.getId() == null) {
//            return (List<VueValidationDarwinCore>) (List<?>) super.findMultiCritere(session, dwc);
//        }
//        dwc.setPublique(Boolean.TRUE);
//        List<VueValidationDarwinCore> valinyPublique = (List<VueValidationDarwinCore>) (List<?>) super.findMultiCritere(session, dwc);
//        dwc.setPublique(Boolean.FALSE);
//        dwc.setIdUtilisateurUpload(u.getId());
//        valinyPublique.addAll((List<VueValidationDarwinCore>) (List<?>) super.findMultiCritere(session, dwc));
//        return valinyPublique;
//    }
    public List<DarwinCore> findAll(Utilisateur utilisateur, DarwinCore darwinCore) throws Exception {
        return darwinCoreDao.findAll(utilisateur, darwinCore);
    }

    public List<DarwinCore> findAll(Utilisateur utilisateur, VueRechercheDarwinCore darwinCore) throws Exception {
        return darwinCoreDao.findAll(utilisateur, darwinCore);
    }

    public List<DarwinCore> findAll(Utilisateur utilisateur, VueRechercheDarwinCore darwinCore, int page, int nombre) throws Exception {
        return darwinCoreDao.findAll(utilisateur, darwinCore, page, nombre);
    }

    public List<DarwinCore> findAll(Utilisateur utilisateur, DarwinCore darwinCore, int page, int nombre) throws Exception {
        return darwinCoreDao.findAll(utilisateur, darwinCore, page, nombre);
    }

    public List<DarwinCore> findAll(Utilisateur utilisateur, VueValidationDarwinCore darwinCore) throws Exception {
        return darwinCoreDao.findAll(utilisateur, darwinCore);
    }

    public List<DarwinCore> findAll(Utilisateur utilisateur, VueValidationDarwinCore darwinCore, int page, int nombre) throws Exception {
        return darwinCoreDao.findAll(utilisateur, darwinCore, page, nombre);
    }

    public List<DarwinCore> findAll(Utilisateur utilisateur, VueDarwinCoreRechercheGlobale darwinCore, int page, int nombre) throws Exception {
        return darwinCoreDao.findAll(utilisateur, darwinCore, page, nombre);
    }
//    public List<BaseModel> findAll(BaseModel darwinCore, int page, int nombre) throws Exception {
//        return darwinCoreDao.findAll(darwinCore, page, nombre);
//    }

    //Latest function
    public Liste findAll(Utilisateur u, VueValidationDarwinCore obj, int page, int nombre, boolean liste) throws Exception {
        Session session = null;
        Liste valiny = new Liste();
        try {
            session = darwinCoreDao.getSessionFactory().openSession();
            valiny.setTotal(darwinCoreDao.countTotalDwc(session, u, obj));
            valiny.setLastPage((int) Math.ceil(valiny.getTotal() / new Double(nombre)));
            valiny.setPage(page);
            valiny.setListe((List<BaseModel>) (List<?>) darwinCoreDao.findAll(session, u, obj, page, nombre));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return valiny;
    }

    public List<VueValidationDarwinCore> findAllByid(List<Integer> ids) throws Exception {
        Session session = null;
        List<VueValidationDarwinCore> valiny = new ArrayList<>();
        try {
            session = darwinCoreDao.getSessionFactory().openSession();
            for (int i : ids) {
                VueValidationDarwinCore vdc = new VueValidationDarwinCore();
                vdc.setId(i);
                super.findById(session, vdc);
                valiny.add(vdc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return valiny;
    }

//    public void removeNull(DarwinCore dwc, String exchangeTo) throws Exception {
//        Field[] colonnes = DarwinCore.class.getDeclaredFields();
//        for (Field f : colonnes) {
//            if (DarwinCore.class.getMethod("get" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1)).invoke(dwc) == null) {                
//                try {
//                    DarwinCore.class.getMethod("set" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1)).invoke(dwc, exchangeTo);
//                } catch (java.lang.NoSuchMethodException e) {
//                    e.printStackTrace();
//                }                
//            }
//        }
//    }
    public DarwinCoreDao getDarwinCoreDao() {
        return darwinCoreDao;
    }

    public void setDarwinCoreDao(DarwinCoreDao DarwinCoreDao) {
        this.darwinCoreDao = DarwinCoreDao;
    }

}
