package org.wcs.lemurs.model;

import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "id"))
})
@Table(name = "observation_par_admin")
public class ObservationParAdmin extends BaseModel implements Serializable {    

    @Column(name = "nbr_observation")
    private Integer nbrObservation;    

    @Column(name = "id_utilisateur")
    private Integer idUtilisateur;

    public Integer getNbrObservation() {
        return nbrObservation;
    }

    public void setNbrObservation(Integer nbrObservation) {
        this.nbrObservation = nbrObservation;
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    
}
