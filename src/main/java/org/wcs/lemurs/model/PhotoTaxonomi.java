package org.wcs.lemurs.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Table(name = "photo_taxonomi")
public class PhotoTaxonomi extends BaseModel implements Serializable {

    @Column(name = "id_taxonomi")
    private Integer idTaxonomi;

    @Column(name = "id_utilisateur_upload")
    private Integer idUtilisateurUpload;

    @Column(name = "chemin")
    private String chemin;

    @Column(name = "profil")
    private Boolean profil;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "date_photo")
    private Date datePhoto;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "date_pris_photo")
    private Date datePrisPhoto;

    public Integer getIdTaxonomi() {
        return idTaxonomi;
    }

    public void setIdTaxonomi(Integer idTaxonomi) {
        this.idTaxonomi = idTaxonomi;
    }

    public Integer getIdUtilisateurUpload() {
        return idUtilisateurUpload;
    }

    public void setIdUtilisateurUpload(Integer idUtilisateurUpload) {
        this.idUtilisateurUpload = idUtilisateurUpload;
    }

    public String getChemin() {
        return chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public Boolean getProfil() {
        return profil;
    }

    public void setProfil(Boolean profil) {
        this.profil = profil;
    }

    public Date getDatePhoto() {
        return datePhoto;
    }

    public void setDatePhoto(Date datePhoto) {
        this.datePhoto = datePhoto;
    }

    public Date getDatePrisPhoto() {
        return datePrisPhoto;
    }

    public void setDatePrisPhoto(Date datePrisPhoto) {
        this.datePrisPhoto = datePrisPhoto;
    }

    public void setDatePrisPhotoString(String datePrisPhoto) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            this.datePrisPhoto = formatter.parse(datePrisPhoto);
        } catch (NullPointerException npe) {
            System.out.println("la valeur de date de naissance est vide");            
        } catch (ParseException ex) {
            Logger.getLogger(PhotoTaxonomi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
