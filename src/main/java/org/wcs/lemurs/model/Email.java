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
@Table(name = "email")
public class Email extends BaseModel implements Serializable {

    @Column(name="email")
    private String email;
    
    @Column(name="pass")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }       
}
