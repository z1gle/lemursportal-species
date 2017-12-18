/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author rudyr
 */
@MappedSuperclass
public class BaseModel {
    @Id
    @GeneratedValue(generator="seq")
    private int id;
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
