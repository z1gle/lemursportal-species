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
@Table(name = "video_taxonomi")
public class VideoTaxonomi extends BaseModel implements Serializable {

    @Column(name="id_taxonomi")
    private Integer idTaxonomi;
    
    @Column(name="id_utilisateur_upload")
    private Integer idUtilisateurUpload;
    
    @Column(name="lien")
    private String lien;        
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name="date_video")
    private Date dateVideo;

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
