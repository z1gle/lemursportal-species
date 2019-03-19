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
@Table(name = "draft_scientificname_from_rebioma")
public class DraftScientificnameRebioma extends BaseModel implements Serializable {

    @Column(name="scientificname")
    private String scientificname;
    
    @Column(name="public")
    private Integer publique;
    
    @Column(name="private")
    private Integer privee;
    
    @Column(name="tout")
    private Integer tout;

    public String getScientificname() {
        return scientificname;
    }

    public void setScientificname(String scientificname) {
        this.scientificname = scientificname;
    }

    public Integer getPublique() {
        return publique;
    }

    public void setPublique(Integer publique) {
        this.publique = publique;
    }

    public Integer getPrivee() {
        return privee;
    }

    public void setPrivee(Integer privee) {
        this.privee = privee;
    }

    public Integer getTout() {
        return tout;
    }

    public void setTout(Integer tout) {
        this.tout = tout;
    }

    
}
