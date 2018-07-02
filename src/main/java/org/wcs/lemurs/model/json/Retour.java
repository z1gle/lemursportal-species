/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.model.json;

/**
 *
 * @author WCS
 */
public class Retour {
    private Boolean etat;
    private Object retour;

    public Retour() {
    }

    public Retour(Boolean etat, Object retour) {
        this.etat = etat;
        this.retour = retour;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public Object getRetour() {
        return retour;
    }

    public void setRetour(Object retour) {
        this.retour = retour;
    }
    
    
}
