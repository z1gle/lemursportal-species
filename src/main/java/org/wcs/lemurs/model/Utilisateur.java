package org.wcs.lemurs.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "id"))
})
@Table(name = "utilisateur")
public class Utilisateur extends BaseModel implements Serializable {

//    @Column(name="login")
//    private String login;
    @Column(name = "date_naissance")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateNaissance;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "last_access_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date lastAccessDate;

    @Column(name = "password")
    private String motdepasse;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenoms")
    private String prenom;

    @Column(name = "email")
    private String email;

    @Column(name = "date_inscription")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateInscription;

    @Column(name = "institution")
    private String institution;

    @Column(name = "photo_profil")
    private String photoProfil;

    @Column(name = "provider")
    private String provider;

    @Column(name = "publication")
    private String publication;

//    @Column(name="telephone")
//    private String telephone;
    @Column(name = "poste_occupe")
    private String poste;

//    @Column(name="localisation")
//    private String localisation;
    @Column(name = "biographie")
    private String biographie;

//    public String getLogin() {
//        return login;
//    }
//
//    public void setLogin(String login) {
//        this.login = login;
//    }
    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public String getTelephone() {
//        return telephone;
//    }
//
//    public void setTelephone(String telephone) {
//        this.telephone = telephone;
//    }
    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

//    public String getLocalisation() {
//        return localisation;
//    }
//
//    public void setLocalisation(String localisation) {
//        this.localisation = localisation;
//    }
    public String getBiographie() {
        return biographie;
    }

    public void setBiographie(String biographie) {
        this.biographie = biographie;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public String getDateNaissanceString() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            return formatter.format(dateNaissance);
        } catch (NullPointerException npe) {
            System.out.println("la valeur de date de naissance est vide");
            return "..-..-....";
        }
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getLastAccessDate() {
        return lastAccessDate;
    }

    public String getLastAccessDateString() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            return formatter.format(lastAccessDate);
        } catch (NullPointerException npe) {
            System.out.println("la valeur de last access date est vide");
            return "..-..-....";
        }
    }

    public void setLastAccessDate(Date lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public String getDateInscriptionString() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            return formatter.format(dateInscription);
        } catch (NullPointerException npe) {
            System.out.println("la valeur de date d'inscription est vide");
            return "..-..-....";
        }
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getPhotoProfil() {
        return photoProfil;
    }

    public void setPhotoProfil(String photoProfil) {
        this.photoProfil = photoProfil;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

}
