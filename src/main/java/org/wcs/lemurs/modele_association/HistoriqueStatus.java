package org.wcs.lemurs.modele_association;

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
@Table(name = "historiquestatus")
public class HistoriqueStatus extends BaseModel implements Serializable {

    @Column(name="idexpert")
    private Integer idExpert;
    
    @Column(name="iddwc")
    private Integer idDwc;
    
    @Column(name="validation")
    private String validation;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name="datemodification")
    private Date dateModification;

    public Integer getIdExpert() {
        return idExpert;
    }

    public void setIdExpert(Integer idExpert) {
        this.idExpert = idExpert;
    }

    public Integer getIdDwc() {
        return idDwc;
    }

    public void setIdDwc(Integer idDwc) {
        this.idDwc = idDwc;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }        
}
