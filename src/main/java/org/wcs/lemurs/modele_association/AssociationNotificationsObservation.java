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
@Table(name = "association_notification_observation")
public class AssociationNotificationsObservation extends BaseModel implements Serializable {

    @Column(name="id_notification")
    private Integer idNotification;
    
    @Column(name="id_observation")
    private Integer idObservation;
    
    @Column(name="commentaire")
    private String commentaire;

    public Integer getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(Integer idNotification) {
        this.idNotification = idNotification;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }    

    public Integer getIdObservation() {
        return idObservation;
    }

    public void setIdObservation(Integer idObservation) {
        this.idObservation = idObservation;
    }        
}
