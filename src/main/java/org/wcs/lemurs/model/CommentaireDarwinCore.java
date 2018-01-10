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
@Table(name = "commentaire_darwin_core")
public class CommentaireDarwinCore extends BaseModel implements Serializable {

    @Column(name="iddarwincore")
    private int idDarwinCore;
    
    @Column(name="idutilisateur")
    private int idUtilisateur;
    
    @Column(name="commentaire")
    private String commentaire;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name="datecommentaire")
    private Date dateCommentaire;

    public int getIdDarwinCore() {
        return idDarwinCore;
    }

    public void setIdDarwinCore(int idDarwinCore) {
        this.idDarwinCore = idDarwinCore;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDateCommentaire() {
        return dateCommentaire;
    }

    public void setDateCommentaire(Date dateCommentaire) {
        this.dateCommentaire = dateCommentaire;
    }            
}
