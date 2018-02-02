package org.wcs.lemurs.model;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;



@Entity
@AttributeOverrides({
    @AttributeOverride(name="id",column=@Column(name="id"))   
})
@Table(name = "photo_darwin_core")
public class PhotoDarwinCore extends BaseModel implements Serializable {

    @Column(name="id_darwin_core")
    private Integer idDarwinCore;
    
    @Column(name="id_utilisateur_upload")
    private Integer idUtilisateurUpload;
    
    @Column(name="chemin")
    private String chemin;
    
    @Column(name="profil")
    private Boolean profil;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name="date_photo")
    private Date datePhoto;

    public Integer getIdDarwinCore() {
        return idDarwinCore;
    }

    public void setIdDarwinCore(Integer idDarwinCore) {
        this.idDarwinCore = idDarwinCore;
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

       
}
