package org.wcs.lemurs.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "id"))
})
@Table(name = "modele")
public class Modele extends BaseModel implements Serializable {

    @Column(name = "date_creation")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;

    @Column(name = "url")
    private String url;    

    @Column(name = "filename")
    private String name;

    @Column(name = "chemin")
    private String path;

    @Column(name = "id_utilisateur")
    private Integer idUserUpload;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getIdUserUpload() {
        return idUserUpload;
    }

    public void setIdUserUpload(Integer idUserUpload) {
        this.idUserUpload = idUserUpload;
    }
        
    public String getDateString() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            return formatter.format(date);
        } catch (NullPointerException npe) {
            System.out.println("la valeur de last access date est vide");
            return "..-..-....";
        }
    }    
}
