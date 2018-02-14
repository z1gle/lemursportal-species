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
@Table(name = "video_darwin_core")
public class VideoDarwinCore extends BaseModel implements Serializable {

    @Column(name="id_darwin_core")
    private Integer idDarwinCore;
    
    @Column(name="id_utilisateur_upload")
    private Integer idUtilisateurUpload;
    
    @Column(name="lien")
    private String lien;        
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name="date_video")
    private Date dateVideo;

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

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }    

    public Date getDateVideo() {
        return dateVideo;
    }

    public void setDateVideo(Date dateVideo) {
        this.dateVideo = dateVideo;
    }

       
}
