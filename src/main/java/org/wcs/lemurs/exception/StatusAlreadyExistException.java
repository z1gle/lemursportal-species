/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.exception;

import java.util.List;
import org.wcs.lemurs.model.DarwinCore;
import org.wcs.lemurs.modele_association.HistoriqueStatus;

/**
 *
 * @author vvizard
 */
public class StatusAlreadyExistException extends Exception {
    DarwinCore observationEnCours;
    HistoriqueStatus statusObservationEnCours;
    List<DarwinCore> observationRestante;

    public DarwinCore getObservationEnCours() {
        return observationEnCours;
    }

    public void setObservationEnCours(DarwinCore observationEnCours) {
        this.observationEnCours = observationEnCours;
    }

    public List<DarwinCore> getObservationRestante() {
        return observationRestante;
    }

    public void setObservationRestante(List<DarwinCore> observationRestante) {
        this.observationRestante = observationRestante;
    }        

    public HistoriqueStatus getStatusObservationEnCours() {
        return statusObservationEnCours;
    }

    public void setStatusObservationEnCours(HistoriqueStatus statusObservationEnCours) {
        this.statusObservationEnCours = statusObservationEnCours;
    }        
}
