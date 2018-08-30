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
import org.wcs.lemurs.modele_association.AssociationNotificationsObservation;

@Entity
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "id"))
})
@Table(name = "notifications")
public class Notifications extends BaseModel implements Serializable {

    @Column(name = "id_cible")
    private Integer idCible;
    
    @Column(name = "categorie")
    private String categorie;
    
    @Column(name = "id_utilisateur")
    private Integer idUtilisateur;
    
    @Column(name = "id_formulaire")
    private Integer idFormulaire;
    
    @Column(name = "date_creation")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;

    @Column(name = "nombre_fille")
    private Integer nbrFille;    

    @Column(name = "vue")
    private Integer vue;    
    
    @Transient
    private List<AssociationNotificationsObservation> listeAssociationNotificationsObservation;
        
    @Transient
    private DownloadInformation formulaire;
        
    @Transient
    private Utilisateur utilisateur;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getIdCible() {
        return idCible;
    }

    public void setIdCible(Integer idCible) {
        this.idCible = idCible;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }    

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Integer getIdFormulaire() {
        return idFormulaire;
    }

    public void setIdFormulaire(Integer idFormulaire) {
        this.idFormulaire = idFormulaire;
    }

    public Integer getNbrFille() {
        return nbrFille;
    }

    public void setNbrFille(Integer nbrFille) {
        this.nbrFille = nbrFille;
    }

    public Integer getVue() {
        return vue;
    }

    public void setVue(Integer vue) {
        this.vue = vue;
    }    

    public List<AssociationNotificationsObservation> getListeAssociationNotificationsObservation() {
        return listeAssociationNotificationsObservation;
    }

    public void setListeAssociationNotificationsObservation(List<AssociationNotificationsObservation> listeAssociationNotificationsObservation) {
        this.listeAssociationNotificationsObservation = listeAssociationNotificationsObservation;
    }        

    public DownloadInformation getFormulaire() {
        return formulaire;
    }

    public void setFormulaire(DownloadInformation formulaire) {
        this.formulaire = formulaire;
    }        

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
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
    
    public String getDateTimeString() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            return formatter.format(date);
        } catch (NullPointerException npe) {
            System.out.println("la valeur de last access date est vide");
            return "..-..-.... ..:..:..";
        }
    }    
}
