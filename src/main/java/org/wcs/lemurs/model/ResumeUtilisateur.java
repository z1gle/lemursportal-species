package org.wcs.lemurs.model;


import java.math.BigInteger;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;



@Entity
@AttributeOverrides({
    @AttributeOverride(name="id",column=@Column(name="idutilisateur"))   
})
@Table(name = "resume_utilisateur")
public class ResumeUtilisateur extends BaseModel {

    @Column(name="commentaire")
    private BigInteger nbrCommentaire;
    
    @Column(name="photo")
    private BigInteger nbrPhoto;
    
    @Column(name="video")
    private BigInteger nbrVideo;

    public BigInteger getNbrCommentaire() {
        return nbrCommentaire;
    }

    public void setNbrCommentaire(BigInteger nbrCommentaire) {
        this.nbrCommentaire = nbrCommentaire;
    }

    public BigInteger getNbrPhoto() {
        return nbrPhoto;
    }

    public void setNbrPhoto(BigInteger nbrPhoto) {
        this.nbrPhoto = nbrPhoto;
    }

    public BigInteger getNbrVideo() {
        return nbrVideo;
    }

    public void setNbrVideo(BigInteger nbrVideo) {
        this.nbrVideo = nbrVideo;
    }        
}
