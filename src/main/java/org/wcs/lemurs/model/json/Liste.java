/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.model.json;

import java.util.List;
import org.wcs.lemurs.model.BaseModel;

/**
 *
 * @author WCS
 */
public class Liste {
    private Long total;
    private Integer page;
    private Integer lastPage;
    private List<BaseModel> liste;
    
    public Liste() {
    }
    
    public Liste(Long total, Integer page, Integer lastPage, List<BaseModel> liste) {
        this.total = total;
        this.page = page;
        this.lastPage = lastPage;
        this.liste = liste;
    }    
    
    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public void setLastPage(Integer lastPage) {
        this.lastPage = lastPage;
    }

    public List<BaseModel> getListe() {
        return liste;
    }

    public void setListe(List<BaseModel> liste) {
        this.liste = liste;
    }
    
    
}
