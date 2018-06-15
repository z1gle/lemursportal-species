package org.wcs.lemurs.modele_vue;

import org.wcs.lemurs.model.*;
import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "idtaxonomibase"))
@Table(name = "vue_recherche_taxonomi")
public class VueRechercheTaxonomi extends BaseModel implements Serializable {
    
    public VueRechercheTaxonomi() {
        
    }
    
    public VueRechercheTaxonomi(int id, String dwcorder, String dwcclass, String dwcfamily, String genus, String scientificname) { 
        this.setId(id);
        this.dwcorder = dwcorder;
        this.dwcclass = dwcclass;
        this.dwcfamily = dwcfamily;
        this.genus = genus;
        this.scientificname = scientificname;
    }    

    @Column(name = "higherclassification")
    private String higherclassification;

    @Column(name = "kingdom")
    private String kingdom;

    @Column(name = "phylum")
    private String phylum;

    @Column(name = "dwcclass")
    private String dwcclass;

    @Column(name = "dwcorder")
    private String dwcorder;

    @Column(name = "dwcfamily")
    private String dwcfamily;

    @Column(name = "genus")
    private String genus;

    @Column(name = "genussource")
    private String genussource;

    @Column(name = "specificepithet")
    private String specificepithet;

    @Column(name = "specificepithetsource")
    private String specificepithetsource;

    @Column(name = "infraspecificepithet")
    private String infraspecificepithet;

    @Column(name = "infraspecificepithetsource")
    private String infraspecificepithetsource;

    @Column(name = "scientificname")
    private String scientificname;

    @Column(name = "scientificnameauthorship")
    private String scientificnameauthorship;

    @Column(name = "acceptednameusage")
    private String acceptednameusage;

    @Column(name = "basisofrecord")
    private String basisofrecord;

    @Column(name = "frenchvernacularname")
    private String frenchvernacularname;

    @Column(name = "malagasyvernacularname")
    private String malagasyvernacularname;

    @Column(name = "englishvernacularname")
    private String englishvernacularname;

    @Column(name = "germany_vernacular_name")
    private String germanyVernacularName;

    @Column(name = "length_and_weight")
    private String lengthAndWeight;

    @Column(name = "length_and_weight_source")
    private String lengthAndWeightSource;

    @Column(name = "color_en")
    private String colorEn;

    @Column(name = "color_fr")
    private String colorFr;

    @Column(name = "color_source")
    private String colorSource;

    @Column(name = "habitat_fr")
    private String habitatFr;

    @Column(name = "habitat_en")
    private String habitatEn;

    @Column(name = "habitatsource")
    private String habitatsource;

    @Column(name = "population_density")
    private String populationDensity;

    @Column(name = "population_density_source")
    private String populationDensitySource;

    @Column(name = "ecology_fr")
    private String ecologyFr;

    @Column(name = "ecology_en")
    private String ecologyEn;

    @Column(name = "ecologysource")
    private String ecologysource;

    @Column(name = "behavior_fr")
    private String behaviorFr;

    @Column(name = "behavior_en")
    private String behaviorEn;

    @Column(name = "behaviorsource")
    private String behaviorsource;

    @Column(name = "threat_fr")
    private String threatFr;

    @Column(name = "threat_en")
    private String threatEn;

    @Column(name = "threatsource")
    private String threatsource;

    @Column(name = "conservation_status")
    private String conservationStatus;

    @Column(name = "iucn_source")
    private String iucnSource;

    @Column(name = "protectedareaoccurrences")
    private String protectedareaoccurrences;

    @Column(name = "protected_area_occurrences_sources")
    private String protectedAreaOccurrencesSources;

    @Column(name = "species_expert")
    private String speciesExpert;

    @Column(name = "new_and_updates")
    private String newAndUpdates;

    @Column(name = "top_five_publication")
    private String topFivePublication;

    @Column(name = "test")
    private String test;

    public String getHigherclassification() {
        return higherclassification;
    }

    public void setHigherclassification(String higherclassification) {
        this.higherclassification = higherclassification;
    }

    public String getKingdom() {
        return kingdom;
    }

    public void setKingdom(String kingdom) {
        this.kingdom = kingdom;
    }

    public String getPhylum() {
        return phylum;
    }

    public void setPhylum(String phylum) {
        this.phylum = phylum;
    }

    public String getDwcclass() {
        return dwcclass;
    }

    public void setDwcclass(String dwcclass) {
        this.dwcclass = dwcclass;
    }

    public String getDwcorder() {
        return dwcorder;
    }

    public void setDwcorder(String dwcorder) {
        this.dwcorder = dwcorder;
    }

    public String getDwcfamily() {
        return dwcfamily;
    }

    public void setDwcfamily(String dwcfamily) {
        this.dwcfamily = dwcfamily;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getGenussource() {
        return genussource;
    }

    public void setGenussource(String genussource) {
        this.genussource = genussource;
    }

    public String getSpecificepithet() {
        return specificepithet;
    }

    public void setSpecificepithet(String specificepithet) {
        this.specificepithet = specificepithet;
    }

    public String getSpecificepithetsource() {
        return specificepithetsource;
    }

    public void setSpecificepithetsource(String specificepithetsource) {
        this.specificepithetsource = specificepithetsource;
    }

    public String getInfraspecificepithet() {
        return infraspecificepithet;
    }

    public void setInfraspecificepithet(String infraspecificepithet) {
        this.infraspecificepithet = infraspecificepithet;
    }

    public String getInfraspecificepithetsource() {
        return infraspecificepithetsource;
    }

    public void setInfraspecificepithetsource(String infraspecificepithetsource) {
        this.infraspecificepithetsource = infraspecificepithetsource;
    }

    public String getScientificname() {
        return scientificname;
    }

    public void setScientificname(String scientificname) {
        this.scientificname = scientificname;
    }

    public String getScientificnameauthorship() {
        return scientificnameauthorship;
    }

    public void setScientificnameauthorship(String scientificnameauthorship) {
        this.scientificnameauthorship = scientificnameauthorship;
    }

    public String getAcceptednameusage() {
        return acceptednameusage;
    }

    public void setAcceptednameusage(String acceptednameusage) {
        this.acceptednameusage = acceptednameusage;
    }

    public String getBasisofrecord() {
        return basisofrecord;
    }

    public void setBasisofrecord(String basisofrecord) {
        this.basisofrecord = basisofrecord;
    }

    public String getFrenchvernacularname() {
        return frenchvernacularname;
    }

    public void setFrenchvernacularname(String frenchvernacularname) {
        this.frenchvernacularname = frenchvernacularname;
    }

    public String getMalagasyvernacularname() {
        return malagasyvernacularname;
    }

    public void setMalagasyvernacularname(String malagasyvernacularname) {
        this.malagasyvernacularname = malagasyvernacularname;
    }

    public String getEnglishvernacularname() {
        return englishvernacularname;
    }

    public void setEnglishvernacularname(String englishvernacularname) {
        this.englishvernacularname = englishvernacularname;
    }

    public String getGermanyVernacularName() {
        return germanyVernacularName;
    }

    public void setGermanyVernacularName(String germanyVernacularName) {
        this.germanyVernacularName = germanyVernacularName;
    }

    public String getLengthAndWeight() {
        return lengthAndWeight;
    }

    public void setLengthAndWeight(String lengthAndWeight) {
        this.lengthAndWeight = lengthAndWeight;
    }

    public String getLengthAndWeightSource() {
        return lengthAndWeightSource;
    }

    public void setLengthAndWeightSource(String lengthAndWeightSource) {
        this.lengthAndWeightSource = lengthAndWeightSource;
    }

    public String getColorEn() {
        return colorEn;
    }

    public void setColorEn(String colorEn) {
        this.colorEn = colorEn;
    }

    public String getColorFr() {
        return colorFr;
    }

    public void setColorFr(String colorFr) {
        this.colorFr = colorFr;
    }

    public String getColorSource() {
        return colorSource;
    }

    public void setColorSource(String colorSource) {
        this.colorSource = colorSource;
    }

    public String getHabitatFr() {
        return habitatFr;
    }

    public void setHabitatFr(String habitatFr) {
        this.habitatFr = habitatFr;
    }

    public String getHabitatEn() {
        return habitatEn;
    }

    public void setHabitatEn(String habitatEn) {
        this.habitatEn = habitatEn;
    }

    public String getHabitatsource() {
        return habitatsource;
    }

    public void setHabitatsource(String habitatsource) {
        this.habitatsource = habitatsource;
    }

    public String getPopulationDensity() {
        return populationDensity;
    }

    public void setPopulationDensity(String populationDensity) {
        this.populationDensity = populationDensity;
    }

    public String getPopulationDensitySource() {
        return populationDensitySource;
    }

    public void setPopulationDensitySource(String populationDensitySource) {
        this.populationDensitySource = populationDensitySource;
    }

    public String getEcologyFr() {
        return ecologyFr;
    }

    public void setEcologyFr(String ecologyFr) {
        this.ecologyFr = ecologyFr;
    }

    public String getEcologyEn() {
        return ecologyEn;
    }

    public void setEcologyEn(String ecologyEn) {
        this.ecologyEn = ecologyEn;
    }

    public String getEcologysource() {
        return ecologysource;
    }

    public void setEcologysource(String ecologysource) {
        this.ecologysource = ecologysource;
    }

    public String getBehaviorFr() {
        return behaviorFr;
    }

    public void setBehaviorFr(String behaviorFr) {
        this.behaviorFr = behaviorFr;
    }

    public String getBehaviorEn() {
        return behaviorEn;
    }

    public void setBehaviorEn(String behaviorEn) {
        this.behaviorEn = behaviorEn;
    }

    public String getBehaviorsource() {
        return behaviorsource;
    }

    public void setBehaviorsource(String behaviorsource) {
        this.behaviorsource = behaviorsource;
    }

    public String getThreatFr() {
        return threatFr;
    }

    public void setThreatFr(String threatFr) {
        this.threatFr = threatFr;
    }

    public String getThreatEn() {
        return threatEn;
    }

    public void setThreatEn(String threatEn) {
        this.threatEn = threatEn;
    }

    public String getThreatsource() {
        return threatsource;
    }

    public void setThreatsource(String threatsource) {
        this.threatsource = threatsource;
    }

    public String getConservationStatus() {
        return conservationStatus;
    }

    public void setConservationStatus(String conservationStatus) {
        this.conservationStatus = conservationStatus;
    }

    public String getIucnSource() {
        return iucnSource;
    }

    public void setIucnSource(String iucnSource) {
        this.iucnSource = iucnSource;
    }

    public String getProtectedareaoccurrences() {
        return protectedareaoccurrences;
    }

    public void setProtectedareaoccurrences(String protectedareaoccurrences) {
        this.protectedareaoccurrences = protectedareaoccurrences;
    }

    public String getProtectedAreaOccurrencesSources() {
        return protectedAreaOccurrencesSources;
    }

    public void setProtectedAreaOccurrencesSources(String protectedAreaOccurrencesSources) {
        this.protectedAreaOccurrencesSources = protectedAreaOccurrencesSources;
    }

    public String getSpeciesExpert() {
        return speciesExpert;
    }

    public void setSpeciesExpert(String speciesExpert) {
        this.speciesExpert = speciesExpert;
    }

    public String getNewAndUpdates() {
        return newAndUpdates;
    }

    public void setNewAndUpdates(String newAndUpdates) {
        this.newAndUpdates = newAndUpdates;
    }

    public String getTopFivePublication() {
        return topFivePublication;
    }

    public void setTopFivePublication(String topFivePublication) {
        this.topFivePublication = topFivePublication;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

}
