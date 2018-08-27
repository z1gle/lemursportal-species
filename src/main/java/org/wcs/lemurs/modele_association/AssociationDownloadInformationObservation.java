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
@Table(name = "assocation_download_information_observation")
public class AssociationDownloadInformationObservation extends BaseModel implements Serializable {

    @Column(name="id_di")
    private Integer idDownloadInformation;
    
    @Column(name="id_obs")
    private Integer idObservation;

    public Integer getIdDownloadInformation() {
        return idDownloadInformation;
    }

    public void setIdDownloadInformation(Integer idDownloadInformation) {
        this.idDownloadInformation = idDownloadInformation;
    }

    public Integer getIdObservation() {
        return idObservation;
    }

    public void setIdObservation(Integer idObservation) {
        this.idObservation = idObservation;
    }        
}
