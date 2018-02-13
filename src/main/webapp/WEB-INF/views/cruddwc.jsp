<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/inc/header.jsp"/>  
<main class="site-content" role="main" ng-controller="object">    
    <input type="hidden" value="saveDwc" id="set"/>
    <%
        Integer id = ((Integer) request.getAttribute("id"));
    %>
    <input type="hidden" value="<%out.print(id);%>" id="idDwc"/>
    <input type="hidden" value="loadDwc" id="load"/>
    <input type="hidden" value="detailLemurien?id=<%out.print(id);%>" id="redirection"/>
    <h1 class="h">INSERTION/MODIFICATION DARWIN CORE</h1>


    

    
    <!--<a href="/lemurs/">link</a>-->


    <!-- taxonomie -->
    <section id="taxonomie">
        <div class="banner-interieur" style="background:url(resources/assets/img/parallax/fexpert.jpg) no-repeat center center;">
            <div class="container" style="margin-top: 5%;">
                <div class="col-md-6 col-md-offset-3">
                    <!-- Search Form -->
                    <form ng-submit="rechercher()">
                        <!-- Search Field -->
                        <div class="row search-header">
                            <h4 class="text-left">Rechercher un espèce</h4>
                            <div class="form-group">
                                <div class="input-group">
                                    <input class="form-control" type="text" ng-model="darwin.scientificName" name="search" placeholder="Nom scientifique de l'espèce" required/>
                                    <span class="input-group-btn">
                                        <button class="btn btn-primary btn-success" type="submit"><i class="fa fa-search"></i></button>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </form>
                    <!-- End of Search Form -->
                </div>
            </div>
        </div>
        <!-- Contenu -->
        <div class="detail-result">
            <div class="container">
                <h1 class="titre-page">Darwin core - <span>Edition</span></h1>
                <div class="row form-group">
                    <form>
                        <div class="col-md-12 insert-modif input-group">
                            <div class="col-md-4">
                                <p><label>Accepted Name Usage :</label><input class="form-control" type="text" ng-model="object.acceptedNameUsage"></p>
                                <p><label>Associated Media :</label><input class="form-control" type="text" ng-model="object.acceptedNameUsage"></p>
                                <p><label>Associated Occurrences :</label><input class="form-control" type="text" ng-model="object.associatedoccurrences"></p>
                                <p><label>Associated References :</label><input class="form-control" type="text" ng-model="object.associatedreferences"></p>
                                <p><label>Associated Sequences :</label><input class="form-control" type="text" ng-model="object.associatedsequences"></p>
                                <p><label>Associated Taxa :</label><input class="form-control" type="text" ng-model="object.associatedtaxa"></p>
                                <p><label>Basis Of Record :</label><input class="form-control" type="text" ng-model="object.basisofrecord"></p>
                                <p><label>Behavior :</label><input class="form-control" type="text" ng-model="object.behavior"></p>
                                <p><label>Catalog Number :</label><input class="form-control" type="text" ng-model="object.catalognumber"></p>
                                <p><label>Collection Code :</label><input class="form-control" type="text" ng-model="object.collectioncode"></p>
                                <p><label>Continent :</label><input class="form-control" type="text" ng-model="object.continent"></p>
                                <p><label>Coordinate Precision :</label><input class="form-control" type="text" ng-model="object.coordinateprecision"></p>
                                <p><label>Coordinate Uncertainty In Meters :</label><input class="form-control" type="text" ng-model="object.coordinateuncertaintyinmeters"></p>
                                <p><label>Country :</label><input class="form-control" type="text" ng-model="object.country"></p>
                                <p><label>Country Code :</label><input class="form-control" type="text" ng-model="object.countrycode"></p>
                                <p><label>County :</label><input class="form-control" type="text" ng-model="object.county"></p>
                                <p><label>Class :</label><input class="form-control" type="text" ng-model="object.darwinclass"></p>
                                <p><label>Order :</label><input class="form-control" type="text" ng-model="object.darwinorder"></p>
                                <p><label>Data Generalizations :</label><input class="form-control" type="text" ng-model="object.datageneralizations"></p>
                                <p><label>Data Set Name :</label><input class="form-control" type="text" ng-model="object.datasetname"></p>
                                <p><label>Date Identified :</label><input class="form-control" type="text" ng-model="object.dateidentified"></p>
                                <p><label>Latitude :</label><input class="form-control" type="text" ng-model="object.decimallatitude"></p>
                                <p><label>Longitude :</label><input class="form-control" type="text" ng-model="object.decimallongitude"></p>
                                <p><label>Disposition :</label><input class="form-control" type="text" ng-model="object.Disposition"></p>
                                <p><label>Day :</label><input class="form-control" type="text" ng-model="object.dwcday"></p>
                                <p><label>Month :</label><input class="form-control" type="text" ng-model="object.dwcmonth"></p>
                                <p><label>Year :</label><input class="form-control" type="text" ng-model="object.dwcyear"></p>
                                <p><label>Dynamic Properties :</label><input class="form-control" type="text" ng-model="object.dynamicproperties"></p>
                                <p><label>End Day Of Year :</label><input class="form-control" type="text" ng-model="object.enddayofyear"></p>
                                <p><label>Establishment Means :</label><input class="form-control" type="text" ng-model="object.establishmentMeans"></p>
                                <p><label>Class :</label><input class="form-control" type="text" ng-model="object.darwinclass"></p>
                                <p><label>Event Id :</label><input class="form-control" type="text" ng-model="object.eventId"></p>
                                <p><label>Event Date :</label><input class="form-control" type="text" ng-model="object.eventdate"></p>
                                <p><label>Event Remarks :</label><input class="form-control" type="text" ng-model="object.eventremarks"></p>
                                <p><label>Event Time :</label><input class="form-control" type="text" ng-model="object.eventtime"></p>
                                <p><label>Family :</label><input class="form-control" type="text" ng-model="object.family"></p>
                                <p><label>Field Notes :</label><input class="form-control" type="text" ng-model="object.fieldnotes"></p>
                                <p><label>Field Number :</label><input class="form-control" type="text" ng-model="object.fieldnumber"></p>
                                <p><label>Foot Print Spatial Fit :</label><input class="form-control" type="text" ng-model="object.footprintspatialfit"></p>
                                <p><label>Foot Print SRS :</label><input class="form-control" type="text" ng-model="object.footprintsrs"></p>
                                <p><label>Foot Print WKT :</label><input class="form-control" type="text" ng-model="object.footprintwkt"></p>
                                <p><label>Genus :</label><input class="form-control" type="text" ng-model="object.genus"></p>
                                <p><label>Geodeticdatum :</label><input class="form-control" type="text" ng-model="object.geodeticdatum"></p>                                
                            </div>

                            <div class="col-md-4">
                                <p><label>Georeferenced By :</label><input class="form-control" type="text" ng-model="object.georeferencedby"></p>
                                <p><label>Georeference Protocol :</label><input class="form-control" type="text" ng-model="object.georeferenceprotocol"></p>
                                <p><label>Georeference Remarks :</label><input class="form-control" type="text" ng-model="object.georeferenceremarks"></p>
                                <p><label>Georeference Sources :</label><input class="form-control" type="text" ng-model="object.georeferencesources"></p>
                                <p><label>Georeference Verification Status :</label><input class="form-control" type="text" ng-model="object.georeferenceverificationstatus"></p>
                                <p><label>Habitat :</label><input class="form-control" type="text" ng-model="object.habitat"></p>
                                <p><label>Higher Geography Id :</label><input class="form-control" type="text" ng-model="object.higherGeographyId"></p>
                                <p><label>Higher Classification :</label><input class="form-control" type="text" ng-model="object.higherclassification"></p>
                                <p><label>Higher Geography :</label><input class="form-control" type="text" ng-model="object.highergeography"></p>
                                <p><label>Identification Id :</label><input class="form-control" type="text" ng-model="object.identificationId"></p>
                                <p><label>Identification Qualifier :</label><input class="form-control" type="text" ng-model="object.identificationqualifier"></p>
                                <p><label>Identification References :</label><input class="form-control" type="text" ng-model="object.identificationreferences"></p>
                                <p><label>Identification Remarks :</label><input class="form-control" type="text" ng-model="object.identificationremarks"></p>
                                <p><label>Identified By :</label><input class="form-control" type="text" ng-model="object.identifiedby"></p>
                                <p><label>Individual Id :</label><input class="form-control" type="text" ng-model="object.individualId"></p>
                                <p><label>Individual Count :</label><input class="form-control" type="text" ng-model="object.individualcount"></p>
                                <p><label>Information With Held :</label><input class="form-control" type="text" ng-model="object.informationwithheld"></p>
                                <p><label>Infraspecific Epithet :</label><input class="form-control" type="text" ng-model="object.infraspecificepithet"></p>
                                <p><label>Institution Code :</label><input class="form-control" type="text" ng-model="object.institutioncode"></p>
                                <p><label>Island :</label><input class="form-control" type="text" ng-model="object.island"></p>
                                <p><label>Island Group :</label><input class="form-control" type="text" ng-model="object.islandgroup"></p>
                                <p><label>Kingdom :</label><input class="form-control" type="text" ng-model="object.kingdom"></p>
                                <p><label>Lifestage :</label><input class="form-control" type="text" ng-model="object.lifestage"></p>
                                <p><label>Locality :</label><input class="form-control" type="text" ng-model="object.locality"></p>
                                <p><label>Location Id :</label><input class="form-control" type="text" ng-model="object.locationId"></p>
                                <p><label>Location According To :</label><input class="form-control" type="text" ng-model="object.locationaccordingto"></p>
                                <p><label>Location Remarks :</label><input class="form-control" type="text" ng-model="object.locationremarks"></p>
                                <p><label>Maximum Depth In Meters :</label><input class="form-control" type="text" ng-model="object.maximumdepthinmeters"></p>
                                <p><label>Maximum Distance Above Surface In Meters :</label><input class="form-control" type="text" ng-model="object.maximumdistanceabovesurfaceinmeters"></p>
                                <p><label>Maximum Elevation In Meters :</label><input class="form-control" type="text" ng-model="object.maximumelevationinmeters"></p>
                                <p><label>Minimum Depth In Meters :</label><input class="form-control" type="text" ng-model="object.minimumdepthinmeters"></p>
                                <p><label>Minimum Distance Above Surface In Meters :</label><input class="form-control" type="text" ng-model="object.minimumdistanceabovesurfaceinmeters"></p>
                                <p><label>Minimum Elevation In Meters :</label><input class="form-control" type="text" ng-model="object.minimumelevationinmeters"></p>
                                <p><label>Municipality :</label><input class="form-control" type="text" ng-model="object.municipality"></p>
                                <p><label>Name According To :</label><input class="form-control" type="text" ng-model="object.nameAccordingTo"></p>
                                <p><label>Name According To Id :</label><input class="form-control" type="text" ng-model="object.nameAccordingToId"></p>
                                <p><label>Name Published In :</label><input class="form-control" type="text" ng-model="object.namePublishedIn"></p>
                                <p><label>Name Published In Id :</label><input class="form-control" type="text" ng-model="object.namePublishedInId"></p>
                                <p><label>Nomenclatural Status :</label><input class="form-control" type="text" ng-model="object.nomenclaturalStatus"></p>
                                <p><label>Nomenclatural Code :</label><input class="form-control" type="text" ng-model="object.nomenclaturalcode"></p>
                                <p><label>Occurrence Details :</label><input class="form-control" type="text" ng-model="object.occurrenceDetails"></p>
                                <p><label>Occurrence Id :</label><input class="form-control" type="text" ng-model="object.occurrenceId"></p>
                                <p><label>Occurrence Status :</label><input class="form-control" type="text" ng-model="object.occurrenceStatus"></p>                                
                            </div>

                            <div class="col-md-4">
                                <p><label>Occurrence Remarks :</label><input class="form-control" type="text" ng-model="object.occurrenceremarks"></p>
                                <p><label>Original Name Usage :</label><input class="form-control" type="text" ng-model="object.originalNameUsage"></p>
                                <p><label>Original Name Usage Id :</label><input class="form-control" type="text" ng-model="object.originalNameUsageId"></p>
                                <p><label>Other Catalog Numbers :</label><input class="form-control" type="text" ng-model="object.othercatalognumbers"></p>
                                <p><label>Owner Institution Code :</label><input class="form-control" type="text" ng-model="object.ownerinstitutioncode"></p>
                                <p><label>Parent Name Usage :</label><input class="form-control" type="text" ng-model="object.parentNameUsage"></p>
                                <p><label>Parent Name Usage Id :</label><input class="form-control" type="text" ng-model="object.parentNameUsageId"></p>
                                <p><label>Phylum :</label><input class="form-control" type="text" ng-model="object.phylum"></p>
                                <p><label>Point Radius Spatial Fit :</label><input class="form-control" type="text" ng-model="object.pointradiusspatialfit"></p>
                                <p><label>Preparations :</label><input class="form-control" type="text" ng-model="object.preparations"></p>
                                <p><label>Previous Identifications :</label><input class="form-control" type="text" ng-model="object.previousidentifications"></p>
                                <p><label>Recorded By :</label><input class="form-control" type="text" ng-model="object.recordedby"></p>
                                <p><label>Record Number :</label><input class="form-control" type="text" ng-model="object.recordnumber"></p>
                                <p><label>Reproductive Condition :</label><input class="form-control" type="text" ng-model="object.reproductivecondition"></p>
                                <p><label>Sampling Effort :</label><input class="form-control" type="text" ng-model="object.samplingeffort"></p>
                                <p><label>Sampling Protocol :</label><input class="form-control" type="text" ng-model="object.samplingprotocol"></p>
                                <p><label>Scientific Name Id :</label><input class="form-control" type="text" ng-model="object.scientificNameId"></p>
                                <p><label>Scientific Name :</label><input class="form-control" type="text" ng-model="object.scientificname"></p>
                                <p><label>Scientific Name Authorship :</label><input class="form-control" type="text" ng-model="object.scientificnameauthorship"></p>
                                <p><label>Sex :</label><input class="form-control" type="text" ng-model="object.sex"></p>
                                <p><label>Specific Epithet :</label><input class="form-control" type="text" ng-model="object.specificepithet"></p>
                                <p><label>Start Day Of Year :</label><input class="form-control" type="text" ng-model="object.startdayofyear"></p>
                                <p><label>Stateprovince :</label><input class="form-control" type="text" ng-model="object.stateprovince"></p>
                                <p><label>Subgenus :</label><input class="form-control" type="text" ng-model="object.subgenus"></p>
                                <p><label>Taxon Concept Id :</label><input class="form-control" type="text" ng-model="object.taxonConceptId"></p>
                                <p><label>Taxon Id :</label><input class="form-control" type="text" ng-model="object.taxonId"></p>
                                <p><label>Taxonomic Status :</label><input class="form-control" type="text" ng-model="object.taxonomicStatus"></p>
                                <p><label>Taxon Rank :</label><input class="form-control" type="text" ng-model="object.taxonrank"></p>
                                <p><label>Taxon Remarks :</label><input class="form-control" type="text" ng-model="object.taxonremarks"></p>
                                <p><label>Type Status :</label><input class="form-control" type="text" ng-model="object.typestatus"></p>
                                <p><label>Verbatim Coordinates :</label><input class="form-control" type="text" ng-model="object.verbatimcoordinates"></p>
                                <p><label>Verbatim Coordinate System :</label><input class="form-control" type="text" ng-model="object.verbatimcoordinatesystem"></p>
                                <p><label>Verbatim Depth :</label><input class="form-control" type="text" ng-model="object.verbatimdepth"></p>
                                <p><label>Verbatim Elevation :</label><input class="form-control" type="text" ng-model="object.verbatimelevation"></p>
                                <p><label>Verbatim Event Date :</label><input class="form-control" type="text" ng-model="object.verbatimeventdate"></p>
                                <p><label>Verbatim Latitude :</label><input class="form-control" type="text" ng-model="object.verbatimlatitude"></p>
                                <p><label>Verbatim Locality :</label><input class="form-control" type="text" ng-model="object.verbatimlocality"></p>
                                <p><label>Verbatim Longitude :</label><input class="form-control" type="text" ng-model="object.verbatimlongitude"></p>
                                <p><label>Verbatim SRS :</label><input class="form-control" type="text" ng-model="object.verbatimsrs"></p>
                                <p><label>Verbatim Taxon Rank :</label><input class="form-control" type="text" ng-model="object.verbatimtaxonrank"></p>
                                <p><label>Vernacular Name :</label><input class="form-control" type="text" ng-model="object.vernacularname"></p>
                                <p><label>Waterbody :</label><input class="form-control" type="text" ng-model="object.waterbody"></p>                                
                            </div>
                        </div>
                        <div class="pull-right divider">
                            <input type="submit" ng-click="save()" class="btn btn-success" value="Ajouter">
                            <button class="btn btn-danger" onclick="window.history.back()">Annuler</button>
                        </div> 
                    </form> 
                </div>
            </div>
        </div>
        <!-- End Contenu -->

    </section>
    <!-- end taxonomie -->

</main>
<script src="<c:url value="/resources/assets/js/angular.js"/>"></script>
<script src="<c:url value="/resources/assets/js/appconfig.js"/>"></script>
<script src="<c:url value="/resources/assets/js/controller/modification.js"/>"></script>
<jsp:include page="/WEB-INF/inc/footer.jsp"/>  