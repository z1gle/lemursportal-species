package org.wcs.lemurs.model;


import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;



@Entity
@AttributeOverrides({
    @AttributeOverride(name="id",column=@Column(name="id"))   
})
@Table(name = "validation_darwin_core")
public class ValidationDarwinCore extends BaseModel implements Serializable {

    @Column(name="iddarwincore")
    private int idDarwinCore;
    
    @Column(name="accepted_speces")
    private Boolean acceptedSpeces;
    
    @Column(name="gps")
    private Boolean gps;
    
    @Column(name="annee")
    private Boolean annee;
    
    @Column(name="collecteur")
    private Boolean collecteur;

    public int getIdDarwinCore() {
        return idDarwinCore;
    }

    public void setIdDarwinCore(int idDarwinCore) {
        this.idDarwinCore = idDarwinCore;
    }

    public Boolean isAcceptedSpeces() {
        return acceptedSpeces;
    }

    public void setAcceptedSpeces(Boolean acceptedSpeces) {
        this.acceptedSpeces = acceptedSpeces;
    }

    public Boolean isGps() {
        return gps;
    }

    public void setGps(Boolean gps) {
        this.gps = gps;
    }

    public Boolean isAnnee() {
        return annee;
    }

    public void setAnnee(Boolean annee) {
        this.annee = annee;
    }

    public Boolean isCollecteur() {
        return collecteur;
    }

    public void setCollecteur(Boolean collecteur) {
        this.collecteur = collecteur;
    }        
}
