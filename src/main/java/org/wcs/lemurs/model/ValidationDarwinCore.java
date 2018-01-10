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
    private boolean acceptedSpeces;
    
    @Column(name="gps")
    private boolean gps;
    
    @Column(name="annee")
    private boolean annee;
    
    @Column(name="collecteur")
    private boolean collecteur;

    public int getIdDarwinCore() {
        return idDarwinCore;
    }

    public void setIdDarwinCore(int idDarwinCore) {
        this.idDarwinCore = idDarwinCore;
    }

    public boolean isAcceptedSpeces() {
        return acceptedSpeces;
    }

    public void setAcceptedSpeces(boolean acceptedSpeces) {
        this.acceptedSpeces = acceptedSpeces;
    }

    public boolean isGps() {
        return gps;
    }

    public void setGps(boolean gps) {
        this.gps = gps;
    }

    public boolean isAnnee() {
        return annee;
    }

    public void setAnnee(boolean annee) {
        this.annee = annee;
    }

    public boolean isCollecteur() {
        return collecteur;
    }

    public void setCollecteur(boolean collecteur) {
        this.collecteur = collecteur;
    }        
}
