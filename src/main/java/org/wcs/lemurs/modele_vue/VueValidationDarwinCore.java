package org.wcs.lemurs.modele_vue;

import org.wcs.lemurs.model.*;
import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;



@Entity
@AttributeOverrides({
    @AttributeOverride(name="id",column=@Column(name="iddwc"))   
})
@Table(name = "vue_validation_darwin_core")
public class VueValidationDarwinCore extends BaseModel implements Serializable {

    @Column(name="nom")
    private String nom;
    
    @Column(name="prenom")
    private String prenom;
    
    @Column(name="institutioncode")
    private String institutionCode;
    
    @Column(name="collectioncode")
    private String collectionCode;
    
    @Column(name="datasetname")
    private String datasetName;
    
    @Column(name="ownerinstitutioncode")
    private String ownerInstitutionCode;
    
    @Column(name="basisofrecord")
    private String basisOfRecord;
    
    @Column(name="informationwithheld")
    private String informationWithHeld;
    
    @Column(name="datageneralizations")
    private String dataGeneralizations;
    
    @Column(name="dynamicproperties")
    private String dynamicProperties;
    
    @Column(name="scientificname")
    private String scientificName;
    
    @Column(name="acceptednameusage")
    private String acceptedNameUsage;
    
    @Column(name="higherclassification")
    private String higherClassification;
    
    @Column(name="kingdom")
    private String kingdom;
    
    @Column(name="phylum")
    private String phylum;
    
    @Column(name="darwinclass")
    private String darwinClass;
    
    @Column(name="darwinorder")
    private String darwinOrder;
    
    @Column(name="family")
    private String family;
    
    @Column(name="genus")
    private String genus;
    
    @Column(name="subgenus")
    private String subgenus;
    
    @Column(name="specificepithet")
    private String specificEpithet;
    
    @Column(name="infraspecificepithet")
    private String infraspecificEpithet;
    
    @Column(name="taxonrank")
    private String taxonrank;
    
    @Column(name="verbatimtaxonrank")
    private String verbatimTaxonrank;
    
    @Column(name="scientificnameauthorship")
    private String scientificNameAuthorship;
    
    @Column(name="vernacularname")
    private String vernacularName;
    
    @Column(name="nomenclaturalcode")
    private String nomenclaturalCode;
    
    @Column(name="taxonremarks")
    private String taxonRemarks;
    
    @Column(name="catalognumber")
    private String cataloNumber;
    
    @Column(name="occurrenceremarks")
    private String occurrenceRemarks;
    
    @Column(name="recordnumber")
    private String recordNumber;
    
    @Column(name="recordedby")
    private String recordedBy;
    
    @Column(name="individualcount")
    private String individualCount;
    
    @Column(name="sex")
    private String sex;
    
    @Column(name="lifestage")
    private String lifestage;
    
    @Column(name="reproductivecondition")
    private String reproductiveCondition;
    
    @Column(name="behavior")
    private String behavior;
    
    @Column(name="preparations")
    private String preparations;
    
    @Column(name="disposition")
    private String disposition;
    
    @Column(name="othercatalognumbers")
    private String otherCatalogNumbers;
    
    @Column(name="previousidentifications")
    private String previousIdentifications;
    
    @Column(name="associatedmedia")
    private String associatedMedia;
    
    @Column(name="associatedreferences")
    private String associatedReferences;
    
    @Column(name="associatedoccurrences")
    private String associatedOccurrences;
    
    @Column(name="associatedsequences")
    private String associatedSequences;
    
    @Column(name="associatedtaxa")
    private String associatedTaxa;
    
    @Column(name="samplingprotocol")
    private String samplingProtocol;
    
    @Column(name="samplingeffort")
    private String samplingEffort;
    
    @Column(name="eventdate")
    private String eventDate;
    
    @Column(name="eventtime")
    private String eventTime;
    
    @Column(name="startdayofyear")
    private String startDayOfYear;
    
    @Column(name="enddayofyear")
    private String endDayOfYear;
    
    @Column(name="dwcyear")
    private String dwcYear;
    
    @Column(name="dwcmonth")
    private String dwcMonth;
    
    @Column(name="dwcday")
    private String dwcDay;
    
    @Column(name="verbatimeventdate")
    private String verbatimEventDate;
    
    @Column(name="habitat")
    private String habitat;
    
    @Column(name="fieldnumber")
    private String fieldNumber;
    
    @Column(name="fieldnotes")
    private String fieldNotes;
    
    @Column(name="eventremarks")
    private String eventRemarks;
    
    @Column(name="highergeography")
    private String higherGeography;
    
    @Column(name="continent")
    private String continent;
    
    @Column(name="waterbody")
    private String waterBody;
    
    @Column(name="islandgroup")
    private String islandGroup;
    
    @Column(name="island")
    private String island;
    
    @Column(name="country")
    private String country;
    
    @Column(name="countrycode")
    private String countryCode;
    
    @Column(name="stateprovince")
    private String stateProvince;
    
    @Column(name="county")
    private String county;
    
    @Column(name="municipality")
    private String municipality;
    
    @Column(name="locality")
    private String locality;
    
    @Column(name="verbatimlocality")
    private String verbatimLocality;
    
    @Column(name="verbatimelevation")
    private String verbatimElevation;
    
    @Column(name="minimumelevationinmeters")
    private String minimumElevationInMeters;
    
    @Column(name="maximumelevationinmeters")
    private String maximumElevationInMeters;
    
    @Column(name="verbatimdepth")
    private String verbatimDepth;
    
    @Column(name="minimumdepthinmeters")
    private String minimumDepthInmeters;
    
    @Column(name="maximumdepthinmeters")
    private String maximumDepthInMeters;
    
    @Column(name="minimumdistanceabovesurfaceinmeters")
    private String minimumDistanceAboveSurfaceInMeters;
    
    @Column(name="maximumdistanceabovesurfaceinmeters")
    private String maximumDistanceAboveSurfaceInMeters;
        
    @Column(name="locationaccordingto")
    private String locationAccordingTo;
    
    @Column(name="locationremarks")
    private String locationRemarks;
    
    @Column(name="verbatimcoordinates")
    private String verbatimCoordinates;
    
    @Column(name="verbatimlatitude")
    private String verbatimLatitude;
    
    @Column(name="verbatimlongitude")
    private String verbatimLongitude;
    
    @Column(name="verbatimcoordinatesystem")
    private String verbatimCoordinateSystem;
    
    @Column(name="verbatimsrs")
    private String verbatimSrs;
    
    @Column(name="decimallatitude")
    private String decimalLatitude;
    
    @Column(name="decimallongitude")
    private String decimalLongitude;
    
    @Column(name="geodeticdatum")
    private String geodeticdatum;
    
    @Column(name="coordinateuncertaintyinmeters")
    private String coordinateUncertaintyInMeters;
    
    @Column(name="coordinateprecision")
    private String coordinatePrecision;
    
    @Column(name="pointradiusspatialfit")
    private String pointRadiusSpatialFit;
    
    @Column(name="footprintwkt")
    private String footPrintWkt;
    
    @Column(name="footprintsrs")
    private String footPrintSrs;
    
    @Column(name="footprintspatialfit")
    private String footPrintSpatialFit;
    
    @Column(name="georeferencedby")
    private String georeferencedBy;
    
    @Column(name="georeferenceprotocol")
    private String georeferenceProtocol;
    
    @Column(name="georeferencesources")
    private String georeferenceSources;
    
    @Column(name="georeferenceverificationstatus")
    private String georeferenceVerificationStatus;
    
    @Column(name="georeferenceremarks")
    private String georeferenceRemarks;
    
    @Column(name="identifiedby")
    private String identifiedBy;
    
    @Column(name="dateidentified")
    private String dateIdentified;
    
    @Column(name="identificationreferences")
    private String identificationReferences;
    
    @Column(name="identificationremarks")
    private String identificationRemarks;
    
    @Column(name="identificationqualifier")
    private String identificationQualifier;
    
    @Column(name="typestatus")
    private String typeStatus;
    
    @Column(name="idutilisateurupload")
    private Integer idUtilisateurUpload;
    
    @Column(name="gps")
    private Boolean gps;
    
    @Column(name="annee")
    private Boolean annee;
    
    @Column(name="collecteur")
    private Boolean collecteur;
    
    @Column(name="accepted_speces")
    private Boolean accepted_speces;
    
    @Column(name="validationexpert")
    private Integer validationexpert;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    public String getCollectionCode() {
        return collectionCode;
    }

    public void setCollectionCode(String collectionCode) {
        this.collectionCode = collectionCode;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public String getOwnerInstitutionCode() {
        return ownerInstitutionCode;
    }

    public void setOwnerInstitutionCode(String ownerInstitutionCode) {
        this.ownerInstitutionCode = ownerInstitutionCode;
    }

    public String getBasisOfRecord() {
        return basisOfRecord;
    }

    public void setBasisOfRecord(String basisOfRecord) {
        this.basisOfRecord = basisOfRecord;
    }

    public String getInformationWithHeld() {
        return informationWithHeld;
    }

    public void setInformationWithHeld(String informationWithHeld) {
        this.informationWithHeld = informationWithHeld;
    }

    public String getDataGeneralizations() {
        return dataGeneralizations;
    }

    public void setDataGeneralizations(String dataGeneralizations) {
        this.dataGeneralizations = dataGeneralizations;
    }

    public String getDynamicProperties() {
        return dynamicProperties;
    }

    public void setDynamicProperties(String dynamicProperties) {
        this.dynamicProperties = dynamicProperties;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getAcceptedNameUsage() {
        return acceptedNameUsage;
    }

    public void setAcceptedNameUsage(String acceptedNameUsage) {
        this.acceptedNameUsage = acceptedNameUsage;
    }

    public String getHigherClassification() {
        return higherClassification;
    }

    public void setHigherClassification(String higherClassification) {
        this.higherClassification = higherClassification;
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

    public String getDarwinClass() {
        return darwinClass;
    }

    public void setDarwinClass(String darwinClass) {
        this.darwinClass = darwinClass;
    }

    public String getDarwinOrder() {
        return darwinOrder;
    }

    public void setDarwinOrder(String darwinOrder) {
        this.darwinOrder = darwinOrder;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getSubgenus() {
        return subgenus;
    }

    public void setSubgenus(String subgenus) {
        this.subgenus = subgenus;
    }

    public String getSpecificEpithet() {
        return specificEpithet;
    }

    public void setSpecificEpithet(String specificEpithet) {
        this.specificEpithet = specificEpithet;
    }

    public String getInfraspecificEpithet() {
        return infraspecificEpithet;
    }

    public void setInfraspecificEpithet(String infraspecificEpithet) {
        this.infraspecificEpithet = infraspecificEpithet;
    }

    public String getTaxonrank() {
        return taxonrank;
    }

    public void setTaxonrank(String taxonrank) {
        this.taxonrank = taxonrank;
    }

    public String getVerbatimTaxonrank() {
        return verbatimTaxonrank;
    }

    public void setVerbatimTaxonrank(String verbatimTaxonrank) {
        this.verbatimTaxonrank = verbatimTaxonrank;
    }

    public String getScientificNameAuthorship() {
        return scientificNameAuthorship;
    }

    public void setScientificNameAuthorship(String scientificNameAuthorship) {
        this.scientificNameAuthorship = scientificNameAuthorship;
    }

    public String getVernacularName() {
        return vernacularName;
    }

    public void setVernacularName(String vernacularName) {
        this.vernacularName = vernacularName;
    }

    public String getNomenclaturalCode() {
        return nomenclaturalCode;
    }

    public void setNomenclaturalCode(String nomenclaturalCode) {
        this.nomenclaturalCode = nomenclaturalCode;
    }

    public String getTaxonRemarks() {
        return taxonRemarks;
    }

    public void setTaxonRemarks(String taxonRemarks) {
        this.taxonRemarks = taxonRemarks;
    }

    public String getCataloNumber() {
        return cataloNumber;
    }

    public void setCataloNumber(String cataloNumber) {
        this.cataloNumber = cataloNumber;
    }

    public String getOccurrenceRemarks() {
        return occurrenceRemarks;
    }

    public void setOccurrenceRemarks(String occurrenceRemarks) {
        this.occurrenceRemarks = occurrenceRemarks;
    }

    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }

    public String getRecordedBy() {
        return recordedBy;
    }

    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }

    public String getIndividualCount() {
        return individualCount;
    }

    public void setIndividualCount(String individualCount) {
        this.individualCount = individualCount;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLifestage() {
        return lifestage;
    }

    public void setLifestage(String lifestage) {
        this.lifestage = lifestage;
    }

    public String getReproductiveCondition() {
        return reproductiveCondition;
    }

    public void setReproductiveCondition(String reproductiveCondition) {
        this.reproductiveCondition = reproductiveCondition;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public String getPreparations() {
        return preparations;
    }

    public void setPreparations(String preparations) {
        this.preparations = preparations;
    }

    public String getDisposition() {
        return disposition;
    }

    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }

    public String getOtherCatalogNumbers() {
        return otherCatalogNumbers;
    }

    public void setOtherCatalogNumbers(String otherCatalogNumbers) {
        this.otherCatalogNumbers = otherCatalogNumbers;
    }

    public String getPreviousIdentifications() {
        return previousIdentifications;
    }

    public void setPreviousIdentifications(String previousIdentifications) {
        this.previousIdentifications = previousIdentifications;
    }

    public String getAssociatedMedia() {
        return associatedMedia;
    }

    public void setAssociatedMedia(String associatedMedia) {
        this.associatedMedia = associatedMedia;
    }

    public String getAssociatedReferences() {
        return associatedReferences;
    }

    public void setAssociatedReferences(String associatedReferences) {
        this.associatedReferences = associatedReferences;
    }

    public String getAssociatedOccurrences() {
        return associatedOccurrences;
    }

    public void setAssociatedOccurrences(String associatedOccurrences) {
        this.associatedOccurrences = associatedOccurrences;
    }

    public String getAssociatedSequences() {
        return associatedSequences;
    }

    public void setAssociatedSequences(String associatedSequences) {
        this.associatedSequences = associatedSequences;
    }

    public String getAssociatedTaxa() {
        return associatedTaxa;
    }

    public void setAssociatedTaxa(String associatedTaxa) {
        this.associatedTaxa = associatedTaxa;
    }

    public String getSamplingProtocol() {
        return samplingProtocol;
    }

    public void setSamplingProtocol(String samplingProtocol) {
        this.samplingProtocol = samplingProtocol;
    }

    public String getSamplingEffort() {
        return samplingEffort;
    }

    public void setSamplingEffort(String samplingEffort) {
        this.samplingEffort = samplingEffort;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getStartDayOfYear() {
        return startDayOfYear;
    }

    public void setStartDayOfYear(String startDayOfYear) {
        this.startDayOfYear = startDayOfYear;
    }

    public String getEndDayOfYear() {
        return endDayOfYear;
    }

    public void setEndDayOfYear(String endDayOfYear) {
        this.endDayOfYear = endDayOfYear;
    }

    public String getDwcYear() {
        return dwcYear;
    }

    public void setDwcYear(String dwcYear) {
        this.dwcYear = dwcYear;
    }

    public String getDwcMonth() {
        return dwcMonth;
    }

    public void setDwcMonth(String dwcMonth) {
        this.dwcMonth = dwcMonth;
    }

    public String getDwcDay() {
        return dwcDay;
    }

    public void setDwcDay(String dwcDay) {
        this.dwcDay = dwcDay;
    }

    public String getVerbatimEventDate() {
        return verbatimEventDate;
    }

    public void setVerbatimEventDate(String verbatimEventDate) {
        this.verbatimEventDate = verbatimEventDate;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public String getFieldNumber() {
        return fieldNumber;
    }

    public void setFieldNumber(String fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

    public String getFieldNotes() {
        return fieldNotes;
    }

    public void setFieldNotes(String fieldNotes) {
        this.fieldNotes = fieldNotes;
    }

    public String getEventRemarks() {
        return eventRemarks;
    }

    public void setEventRemarks(String eventRemarks) {
        this.eventRemarks = eventRemarks;
    }

    public String getHigherGeography() {
        return higherGeography;
    }

    public void setHigherGeography(String higherGeography) {
        this.higherGeography = higherGeography;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getWaterBody() {
        return waterBody;
    }

    public void setWaterBody(String waterBody) {
        this.waterBody = waterBody;
    }

    public String getIslandGroup() {
        return islandGroup;
    }

    public void setIslandGroup(String islandGroup) {
        this.islandGroup = islandGroup;
    }

    public String getIsland() {
        return island;
    }

    public void setIsland(String island) {
        this.island = island;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getVerbatimLocality() {
        return verbatimLocality;
    }

    public void setVerbatimLocality(String verbatimLocality) {
        this.verbatimLocality = verbatimLocality;
    }

    public String getVerbatimElevation() {
        return verbatimElevation;
    }

    public void setVerbatimElevation(String verbatimElevation) {
        this.verbatimElevation = verbatimElevation;
    }

    public String getMinimumElevationInMeters() {
        return minimumElevationInMeters;
    }

    public void setMinimumElevationInMeters(String minimumElevationInMeters) {
        this.minimumElevationInMeters = minimumElevationInMeters;
    }

    public String getMaximumElevationInMeters() {
        return maximumElevationInMeters;
    }

    public void setMaximumElevationInMeters(String maximumElevationInMeters) {
        this.maximumElevationInMeters = maximumElevationInMeters;
    }

    public String getVerbatimDepth() {
        return verbatimDepth;
    }

    public void setVerbatimDepth(String verbatimDepth) {
        this.verbatimDepth = verbatimDepth;
    }

    public String getMinimumDepthInmeters() {
        return minimumDepthInmeters;
    }

    public void setMinimumDepthInmeters(String minimumDepthInmeters) {
        this.minimumDepthInmeters = minimumDepthInmeters;
    }

    public String getMaximumDepthInMeters() {
        return maximumDepthInMeters;
    }

    public void setMaximumDepthInMeters(String maximumDepthInMeters) {
        this.maximumDepthInMeters = maximumDepthInMeters;
    }

    public String getMinimumDistanceAboveSurfaceInMeters() {
        return minimumDistanceAboveSurfaceInMeters;
    }

    public void setMinimumDistanceAboveSurfaceInMeters(String minimumDistanceAboveSurfaceInMeters) {
        this.minimumDistanceAboveSurfaceInMeters = minimumDistanceAboveSurfaceInMeters;
    }

    public String getMaximumDistanceAboveSurfaceInMeters() {
        return maximumDistanceAboveSurfaceInMeters;
    }

    public void setMaximumDistanceAboveSurfaceInMeters(String maximumDistanceAboveSurfaceInMeters) {
        this.maximumDistanceAboveSurfaceInMeters = maximumDistanceAboveSurfaceInMeters;
    }

    public String getLocationAccordingTo() {
        return locationAccordingTo;
    }

    public void setLocationAccordingTo(String locationAccordingTo) {
        this.locationAccordingTo = locationAccordingTo;
    }

    public String getLocationRemarks() {
        return locationRemarks;
    }

    public void setLocationRemarks(String locationRemarks) {
        this.locationRemarks = locationRemarks;
    }

    public String getVerbatimCoordinates() {
        return verbatimCoordinates;
    }

    public void setVerbatimCoordinates(String verbatimCoordinates) {
        this.verbatimCoordinates = verbatimCoordinates;
    }

    public String getVerbatimLatitude() {
        return verbatimLatitude;
    }

    public void setVerbatimLatitude(String verbatimLatitude) {
        this.verbatimLatitude = verbatimLatitude;
    }

    public String getVerbatimLongitude() {
        return verbatimLongitude;
    }

    public void setVerbatimLongitude(String verbatimLongitude) {
        this.verbatimLongitude = verbatimLongitude;
    }

    public String getVerbatimCoordinateSystem() {
        return verbatimCoordinateSystem;
    }

    public void setVerbatimCoordinateSystem(String verbatimCoordinateSystem) {
        this.verbatimCoordinateSystem = verbatimCoordinateSystem;
    }

    public String getVerbatimSrs() {
        return verbatimSrs;
    }

    public void setVerbatimSrs(String verbatimSrs) {
        this.verbatimSrs = verbatimSrs;
    }

    public String getDecimalLatitude() {
        return decimalLatitude;
    }

    public void setDecimalLatitude(String decimalLatitude) {
        this.decimalLatitude = decimalLatitude;
    }

    public String getDecimalLongitude() {
        return decimalLongitude;
    }

    public void setDecimalLongitude(String decimalLongitude) {
        this.decimalLongitude = decimalLongitude;
    }

    public String getGeodeticdatum() {
        return geodeticdatum;
    }

    public void setGeodeticdatum(String geodeticdatum) {
        this.geodeticdatum = geodeticdatum;
    }

    public String getCoordinateUncertaintyInMeters() {
        return coordinateUncertaintyInMeters;
    }

    public void setCoordinateUncertaintyInMeters(String coordinateUncertaintyInMeters) {
        this.coordinateUncertaintyInMeters = coordinateUncertaintyInMeters;
    }

    public String getCoordinatePrecision() {
        return coordinatePrecision;
    }

    public void setCoordinatePrecision(String coordinatePrecision) {
        this.coordinatePrecision = coordinatePrecision;
    }

    public String getPointRadiusSpatialFit() {
        return pointRadiusSpatialFit;
    }

    public void setPointRadiusSpatialFit(String pointRadiusSpatialFit) {
        this.pointRadiusSpatialFit = pointRadiusSpatialFit;
    }

    public String getFootPrintWkt() {
        return footPrintWkt;
    }

    public void setFootPrintWkt(String footPrintWkt) {
        this.footPrintWkt = footPrintWkt;
    }

    public String getFootPrintSrs() {
        return footPrintSrs;
    }

    public void setFootPrintSrs(String footPrintSrs) {
        this.footPrintSrs = footPrintSrs;
    }

    public String getFootPrintSpatialFit() {
        return footPrintSpatialFit;
    }

    public void setFootPrintSpatialFit(String footPrintSpatialFit) {
        this.footPrintSpatialFit = footPrintSpatialFit;
    }

    public String getGeoreferencedBy() {
        return georeferencedBy;
    }

    public void setGeoreferencedBy(String georeferencedBy) {
        this.georeferencedBy = georeferencedBy;
    }

    public String getGeoreferenceProtocol() {
        return georeferenceProtocol;
    }

    public void setGeoreferenceProtocol(String georeferenceProtocol) {
        this.georeferenceProtocol = georeferenceProtocol;
    }

    public String getGeoreferenceSources() {
        return georeferenceSources;
    }

    public void setGeoreferenceSources(String georeferenceSources) {
        this.georeferenceSources = georeferenceSources;
    }

    public String getGeoreferenceVerificationStatus() {
        return georeferenceVerificationStatus;
    }

    public void setGeoreferenceVerificationStatus(String georeferenceVerificationStatus) {
        this.georeferenceVerificationStatus = georeferenceVerificationStatus;
    }

    public String getGeoreferenceRemarks() {
        return georeferenceRemarks;
    }

    public void setGeoreferenceRemarks(String georeferenceRemarks) {
        this.georeferenceRemarks = georeferenceRemarks;
    }

    public String getIdentifiedBy() {
        return identifiedBy;
    }

    public void setIdentifiedBy(String identifiedBy) {
        this.identifiedBy = identifiedBy;
    }

    public String getDateIdentified() {
        return dateIdentified;
    }

    public void setDateIdentified(String dateIdentified) {
        this.dateIdentified = dateIdentified;
    }

    public String getIdentificationReferences() {
        return identificationReferences;
    }

    public void setIdentificationReferences(String identificationReferences) {
        this.identificationReferences = identificationReferences;
    }

    public String getIdentificationRemarks() {
        return identificationRemarks;
    }

    public void setIdentificationRemarks(String identificationRemarks) {
        this.identificationRemarks = identificationRemarks;
    }

    public String getIdentificationQualifier() {
        return identificationQualifier;
    }

    public void setIdentificationQualifier(String identificationQualifier) {
        this.identificationQualifier = identificationQualifier;
    }

    public String getTypeStatus() {
        return typeStatus;
    }

    public void setTypeStatus(String typeStatus) {
        this.typeStatus = typeStatus;
    }

    public Integer getIdUtilisateurUpload() {
        return idUtilisateurUpload;
    }

    public void setIdUtilisateurUpload(Integer idUtilisateurUpload) {
        this.idUtilisateurUpload = idUtilisateurUpload;
    }

    public Boolean getGps() {
        return gps;
    }

    public void setGps(Boolean gps) {
        this.gps = gps;
    }

    public Boolean getAnnee() {
        return annee;
    }

    public void setAnnee(Boolean annee) {
        this.annee = annee;
    }

    public Boolean getCollecteur() {
        return collecteur;
    }

    public void setCollecteur(Boolean collecteur) {
        this.collecteur = collecteur;
    }

    public Boolean getAccepted_speces() {
        return accepted_speces;
    }

    public void setAccepted_speces(Boolean accepted_speces) {
        this.accepted_speces = accepted_speces;
    }

    public Integer getValidationexpert() {
        return validationexpert;
    }

    public void setValidationexpert(Integer validationexpert) {
        this.validationexpert = validationexpert;
    }
    
    
}
