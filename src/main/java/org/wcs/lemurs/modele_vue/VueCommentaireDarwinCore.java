package org.wcs.lemurs.modele_vue;


import org.wcs.lemurs.model.*;
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
@Table(name = "vue_commentaire_darwin_core")
public class VueCommentaireDarwinCore extends BaseModel implements Serializable {

    @Column(name="iddarwincore")
    private Integer idDarwinCore;
    
    @Column(name="idutilisateur")
    private Integer idUtilisateur;
    
    @Column(name="commentaire")
    private String commentaire;
    
    @Column(name="nom")
    private String nom;
    
    @Column(name="prenoms")
    private String prenom;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name="datecommentaire")
    private Date dateCommentaire;

    public Integer getIdDarwinCore() {
        return idDarwinCore;
    }

    public void setIdDarwinCore(Integer idDarwinCore) {
        this.idDarwinCore = idDarwinCore;
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
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
}
