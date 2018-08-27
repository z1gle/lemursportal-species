package org.wcs.lemurs.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import org.wcs.lemurs.modele_association.AssociationDownloadInformationObservation;

@Entity
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "id"))
})
@Table(name = "download_information")
public class DownloadInformation extends BaseModel implements Serializable {

    @Column(name = "date_telechargement")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;

    @Column(name = "nom")
    private String nom;    

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "titre")
    private String titre;
    
    @Column(name = "profession")
    private String profession;
    
    @Column(name = "e_mail")
    private String eMail;
    
    @Column(name = "institution")
    private String institution;
    
    @Column(name = "utilisation")
    private String utilisation;    
    
    @Transient
    private List<AssociationDownloadInformationObservation> listeObservations;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getUtilisation() {
        return utilisation;
    }

    public void setUtilisation(String utilisation) {
        this.utilisation = utilisation;
    }    

    public List<AssociationDownloadInformationObservation> getListeObservations() {
        return listeObservations;
    }

    public void setListeObservations(List<AssociationDownloadInformationObservation> listeObservations) {
        this.listeObservations = listeObservations;
    }
        
        
    public String getDateString() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            return formatter.format(date);
        } catch (NullPointerException npe) {
            System.out.println("la valeur de last access date est vide");
            return "..-..-....";
        }
    }    
}
