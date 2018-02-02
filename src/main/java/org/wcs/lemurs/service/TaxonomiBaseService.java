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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.wcs.lemurs.dao.HibernateDao;
import org.wcs.lemurs.model.BaseModel;
import org.wcs.lemurs.model.PhotoTaxonomi;
import org.wcs.lemurs.model.TaxonomiBase;
import org.wcs.lemurs.model.Utilisateur;
import org.wcs.lemurs.modele_association.AssignationExpert;
import org.wcs.lemurs.util.PhotoService;

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
    @Autowired(required = true)
    private PhotoService photoService;

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
                valiny.addAll(checkSaveGenre(valeur, tous, idExpert, (String) param.get(0)));
            }
        }
        return valiny;
    }

    public void checkSaveFamille(List<AssignationExpert> valeur, List<String> tous, int idExpert, String famille) throws Exception {
        List<AssignationExpert> especeToSave = new ArrayList<>();
        for (AssignationExpert ase : valeur) {
            for (String s : tous) {
                if (ase.getGenre() != null && ase.getGenre().compareTo(s) == 0) {
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
        Session session = null;
        Transaction tr = null;
        try {
            session = this.hibernateDao.getSessionFactory().openSession();
            tr = session.beginTransaction();
            List<AssignationExpert> valiny = new ArrayList<>();
            List<AssignationExpert> ase = checkGenre(valeur, idExpert);
            for (int i = 0; i < valeur.length; i++) {
                if (valeur[i].contains("typeFamille")) {
                    List<String> nom = new ArrayList<>();
                    nom.add("dwcfamille");
                    List<Object> param = new ArrayList<>();
                    param.add(valeur[i].split("-")[valeur[i].split("-").length - 1]);
                    List<String> tous = (List<String>) (List<?>) executeSqlList("select distinct genus from taxonomi_base where dwcfamily = :dwcfamille", nom, param);
                    checkSaveFamille(ase, tous, idExpert, (String) param.get(0));
                    valiny.addAll(ase);
                }
            }
            AssignationExpert temp = new AssignationExpert();
            temp.setIdExpert(idExpert);
            List<AssignationExpert> remove = (List<AssignationExpert>) (List<?>) this.findMultiCritere(temp);
            for (AssignationExpert ae : remove) {
                this.delete(session, ae);
            }
            for (AssignationExpert v : ase) {
                save(session, v);
            }
            tr.commit();
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<PhotoTaxonomi> enregistrerPhoto(MultipartFile photo, TaxonomiBase taxonomi, Utilisateur utilisateur, boolean profil, String cheminReal) throws IOException, Exception {
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
            String nomPhoto = "taxonomi_id_" + taxonomi.getId() + "_chercheur_id_" + utilisateur.getId() + "_date_" + datePhoto.getTime() + ".jpg";
            deplacerPhoto(fileTemp, cheminReal + cheminDepuisServeur, nomPhoto);
            PhotoTaxonomi photoTaxonomi = new PhotoTaxonomi();
            photoTaxonomi.setIdTaxonomi(taxonomi.getId());                        
            setProfilOfPhoto(session, photoTaxonomi, profil);
            photoTaxonomi.setChemin("resources/assets/img/photos/" + nomPhoto);
            photoTaxonomi.setDatePhoto(datePhoto);
            photoTaxonomi.setIdUtilisateurUpload(utilisateur.getId());
            photoTaxonomi.setProfil(profil);
            save(session, photoTaxonomi);
            photoTaxonomi = new PhotoTaxonomi();
            photoTaxonomi.setIdTaxonomi(taxonomi.getId());
            tr.commit();
            return (List<PhotoTaxonomi>) (List<?>) this.findMultiCritere(session, photoTaxonomi);
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

    public void setProfilOfPhoto(Session session, PhotoTaxonomi photoTaxonomiWithIdTaxonomi, boolean profil) throws Exception {
        if (photoTaxonomiWithIdTaxonomi.getIdTaxonomi() != 0 || photoTaxonomiWithIdTaxonomi.getIdTaxonomi() != null) {
            List<PhotoTaxonomi> listeToCheck = (List<PhotoTaxonomi>) (List<?>) this.findMultiCritere(session, photoTaxonomiWithIdTaxonomi);
            int iterator = 0;
            for (PhotoTaxonomi p : listeToCheck) {
                if (profil) {
                    if (p.getProfil()) {
                        p.setProfil(Boolean.FALSE);
                        save(session, p);
                        photoTaxonomiWithIdTaxonomi.setProfil(profil);
                        break;
                    }
                } else {
                    iterator++;
                }
            }
            if (iterator == listeToCheck.size()) {
                photoTaxonomiWithIdTaxonomi.setProfil(Boolean.TRUE);
            }
            else photoTaxonomiWithIdTaxonomi.setProfil(profil);
        }
    }
}
