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
@Table(name = "draft_scientificname_from_lemursportal")
public class DraftScientificnameLemursportal extends BaseModel implements Serializable {

    @Column(name="scientificname")
    private String scientificname;
    
    @Column(name="public")
    private Integer publique;
    
    @Column(name="private")
    private Integer privee;
    
    @Column(name="waiting")
    private Integer waiting;
    
    @Column(name="questionnable")
    private Integer questionnable;
    
    @Column(name="reliable")
    private Integer reliable;        
    
    @Column(name="invalid")
    private Integer invalid;
    
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

    public Integer getWaiting() {
        return waiting;
    }

    public void setWaiting(Integer waiting) {
        this.waiting = waiting;
    }

    public Integer getQuestionnable() {
        return questionnable;
    }

    public void setQuestionnable(Integer questionnable) {
        this.questionnable = questionnable;
    }

    public Integer getReliable() {
        return reliable;
    }

    public void setReliable(Integer reliable) {
        this.reliable = reliable;
    }

    public Integer getInvalid() {
        return invalid;
    }

    public void setInvalid(Integer invalid) {
        this.invalid = invalid;
    }

    public Integer getTout() {
        return tout;
    }

    public void setTout(Integer tout) {
        this.tout = tout;
    }

    
}
