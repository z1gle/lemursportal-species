package org.wcs.lemurs.modele_association;

import org.wcs.lemurs.model.*;
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
@Table(name = "assignationexpert")
public class AssignationExpert extends BaseModel implements Serializable {

    @Column(name="idexpert")
    private Integer idExpert;
    
    @Column(name="famille")
    private String famille;
    
    @Column(name="genre")
    private String genre;
    
    @Column(name="espece")
    private String espece;

    public Integer getIdExpert() {
        return idExpert;
    }

    public void setIdExpert(Integer idExpert) {
        this.idExpert = idExpert;
    }    

    public String getFamille() {
        return famille;
    }

    public void setFamille(String famille) {
        this.famille = famille;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getEspece() {
        return espece;
    }

    public void setEspece(String espece) {
        this.espece = espece;
    }
    
}
