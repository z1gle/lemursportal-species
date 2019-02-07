/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author rudyr
 */
@MappedSuperclass
public class BaseModel {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    @Transient
    public static final String BASIS_OF_RECORD_FROM_INATURALIST = "HumanObservation";

    @Transient
    public static final String NOMENCLATURAL_CODE_FROM_INATURALIST = "ICZN";
    
    @Transient
    public static final String INATURALIST_LINK_TO_GET_OBSERVATIONS = "https://"
            + "www.inaturalist.org/observations/";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
