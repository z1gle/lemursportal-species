package org.wcs.lemurs.modele_association;

import org.wcs.lemurs.model.*;
import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;



@Entity
@SQLDelete(sql = "DELETE FROM role_utilisateur WHERE id = ?", check=ResultCheckStyle.NONE)
@AttributeOverrides({
    @AttributeOverride(name="id",column=@Column(name="id"))   
})
@Table(name = "role_utilisateur")
public class RoleUtilisateur extends BaseModel implements Serializable {

    @Column(name="idrole")
    private Integer idRole;
    
    @Column(name="idutilisateur")
    private Integer idUtilisateur;

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

      
}
