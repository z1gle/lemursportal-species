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


    <!--            <form action="processExcel" method="POST"
                           enctype="multipart/form-data">
                    <div>Importer un fichier Excel:</div>
                    <input name="excelfile" type="file">
                    <input type="submit" value="Importer">
                </form>
                <br>-->

    <form>                
        <table class="table-condensed">
            <tr>
                <td>
                    <label>Institution code : </label>
                    <input type="text" name="institutioncode" value="{{object.institutioncode}}" ng-model="object.institutioncode">
                </td>
                <td>
                    <label>Collection code : </label>
                    <input type="text" name="collectioncode" value="{{object.collectioncode}}" ng-model="object.collectioncode">
                </td>
                <td>
                    <label>Dataset name : </label>
                    <input type="text" name="datasetname" value="{{object.datasetname}}" ng-model="object.datasetname">
                </td>
            </tr>

            <tr>
                <td>
                    <label>Owner institution code : </label>
                    <input type="text" name="ownerinstitutioncode" value="{{object.ownerinstitutioncode}}" ng-model="object.ownerinstitutioncode">
                </td>
                <td>
                    <label>Basis of record : </label>
                    <input type="text" name="collectioncode" value="{{object.collectioncode}}" ng-model="object.collectioncode">
                </td>
                <td>
                    <label>Information with held : </label>
                    <input type="text" name="informationwithheld" value="{{object.informationwithheld}}" ng-model="object.informationwithheld">
                </td>
            </tr>
            <tr>
                <td>
                    <label>Data generalizations  : </label>
                    <input type="text" name ="datageneralizations" value="{{object.datageneralizations}}" ng-model="object.datageneralizations">
                </td>
                <td>
                    <label>Dynamic properties  : </label>
                    <input type="text" name ="dynamicproperties" value="{{object.dynamicproperties}}" ng-model="object.dynamicproperties">
                </td>
                <td>
                    <label>Scientific name  : </label>
                    <input type="text" name ="scientificname" value="{{object.scientificname}}" ng-model="object.scientificname">
                </td>
            </tr>
            <tr>
                <td>
                    <label>Accepted name usage  : </label>
                    <input type="text" name ="acceptednameusage" value="{{object.acceptednameusage}}" ng-model="object.acceptednameusage">
                </td>

                <td>
                    <label>Higher classification : </label>
                    <input type="text" name ="higherclassification" value="{{object.higherclassification}}" ng-model="object.higherclassification">
                </td>
                <td>
                    <label>Kingdom : </label> 
                    <input type="text" name ="kingdom" value="{{object.kingdom}}" ng-model="object.kingdom">
                </td>
            </tr>
            <tr>
                <td>
                    <label>Phylum : </label> 
                    <input type="text" name ="phylum" value="{{object.phylum}}" ng-model="object.phylum">
                </td>

                <td>
                    <label>Darwin class : </label> 
                    <input type="text" name ="darwinclass" value="{{object.darwinclass}}" ng-model="object.darwinclass">
                </td>
                <td>
                    <label>Darwin order : </label> 
                    <input type="text" name ="darwinorder" value="{{object.darwinorder}}" ng-model="object.darwinorder">
                </td>
            </tr>
            <tr>
                <td>
                    <label>Family : </label> 
                    <input type="text" name ="family" value="{{object.family}}" ng-model="object.family">
                </td>
                <td>
                    <label>Genus : </label>
                    <input type="text" name ="genus" value="{{object.genus}}" ng-model="object.genus">
                </td>
                <td>
                    <label>Sub genus  : </label>
                    <input type="text" name ="subgenus" value="{{object.subgenus}}" ng-model="object.subgenus">
                </td>
            </tr>
            <tr>
                <td>
                    <label>Specific epithet : </label>
                    <input type="text" name ="specificepithet" value="{{object.specificepithet}}" ng-model="object.specificepithet">
                </td>
                <td>
                    <label>Infra specific epithet : </label>
                    <input type="text" name ="infraspecificepithet" value="{{object.infraspecificepithet}}" ng-model="object.infraspecificepithet">
                </td>
                <td>
                    <label>Taxon rank : </label>
                    <input type="text" name ="taxonrank" value="{{object.taxonrank}}" ng-model="object.taxonrank">
                </td>
            </tr><tr>
                <td>
                    <label>Verbatim taxon rank : </label>
                    <input type="text" name ="verbatimtaxonrank" value="{{object.verbatimtaxonrank}}" ng-model="object.verbatimtaxonrank">
                </td>
                <td>
                    <label>Scientific name authorship : </label>
                    <input type="text" name ="scientificnameauthorship" value="{{object.scientificnameauthorship}}" ng-model="object.scientificnameauthorship">
                </td>
                <td>
                    <label>Vernacular name : </label>
                    <input type="text" name ="vernacularname" value="{{object.vernacularname}}" ng-model="object.vernacularname">
                </td>
            </tr>
            <tr>
                <td>
                    <label>Nomenclatural code : </label>
                    <input type="text" name ="nomenclaturalcode" value="{{object.nomenclaturalcode}}" ng-model="object.nomenclaturalcode">
                </td>
                <td>
                    <label>Taxon remarks : </label>
                    <input type="text" name ="taxonremarks" value="{{object.taxonremarks}}" ng-model="object.taxonremarks">
                </td>
                <td>
                    <label>Catalog number : </label>
                    <input type="text" name ="catalognumber" value="{{object.catalognumber}}" ng-model="object.catalognumber">
                </td>
            </tr><tr>
                <td>
                    <label>Occurrence remarks : </label>
                    <input type="text" name ="occurrenceremarks" value="{{object.occurrenceremarks}}" ng-model="object.occurrenceremarks">
                </td>
                <td>
                    <label>Record number : </label>
                    <input type="text" name ="recordnumber" value="{{object.recordnumber}}" ng-model="object.recordnumber">
                </td>
                <td>
                    <label>Recorded by : </label>
                    <input type="text" name ="recordedby" value="{{object.recordedby}}" ng-model="object.recordedby">
                </td>
            </tr>
            <tr>
                <td>
                    <label>Individual count : </label>
                    <input type="text" name ="individualcount" value="{{object.individualcount}}" ng-model="object.individualcount">
                </td>
                <td>
                    <label>Sex : </label>
                    <input type="text" name ="sex" value="{{object.sex}}" ng-model="object.sex">
                </td>
                <td>
                    <label>Life stage : </label>
                    <input type="text" name ="lifestage" value="{{object.lifestage}}" ng-model="object.lifestage">
                </td>
            </tr>
            <tr>
                <td>
                    <label>Reproductive condition : </label>
                    <input type="text" name ="reproductivecondition" value="{{object.reproductivecondition}}" ng-model="object.reproductivecondition">
                </td>
                <td>
                    <label>Behavior : </label>
                    <input type="text" name ="behavior" value="{{object.behavior}}" ng-model="object.behavior">
                </td>
                <td>
                    <label>Preparations : </label>
                    <input type="text" name ="preparations" value="{{object.preparations}}" ng-model="object.preparations">
                </td>
            </tr><tr>
                <td>
                    <label>Disposition : </label>
                    <input type="text" name ="disposition" value="{{object.disposition}}" ng-model="object.disposition">
                </td>
                <td>
                    <label>Other catalog numbers : </label>
                    <input type="text" name ="othercatalognumbers" value="{{object.othercatalognumbers}}" ng-model="object.othercatalognumbers">
                </td>
                <td>
                    <label>Previousidentifications : </label>
                    <input type="text" name ="previousidentifications" value="{{object.previousidentifications}}" ng-model="object.previousidentifications">
                </td>
            </tr>
            <tr>
                <td>
                    <label>Associated media : </label>
                    <input type="text" name ="associatedmedia" value="{{object.associatedmedia}}" ng-model="object.associatedmedia">
                </td>
                <td>
                    <label>Associated references : </label>
                    <input type="text" name ="associatedreferences" value="{{object.associatedreferences}}" ng-model="object.associatedreferences">
                </td>
                <td>
                    <label>Associated occurrences : </label>
                    <input type="text" name ="associatedoccurrences" value="{{object.associatedoccurrences}}" ng-model="object.associatedoccurrences">
                </td>
            </tr>
            <tr>
                <td>
                    <label>Associated sequences : </label>
                    <input type="text" name ="associatedsequences" value="{{object.associatedsequences}}" ng-model="object.associatedsequences">
                </td>
                <td>
                    <label>Associated taxa : </label>
                    <input type="text" name ="associatedtaxa" value="{{object.associatedtaxa}}" ng-model="object.associatedtaxa">
                </td>
                <td>
                    <label>Sampling protocol : </label>
                    <input type="text" name ="samplingprotocol" value="{{object.samplingprotocol}}" ng-model="object.samplingprotocol">
                </td>
            </tr>
            <tr>
                <td>
                    <label>Sampling effort : </label>
                    <input type="text" name ="samplingeffort" value="{{object.samplingeffort}}" ng-model="object.samplingeffort">
                </td>
                <td>
                    <label>Eventdate : </label>
                    <input type="text" name ="eventdate" value="{{object.eventdate}}" ng-model="object.eventdate">
                </td>
                <td>
                    <label>Eventtime : </label>
                    <input type="text" name ="eventtime" value="{{object.eventtime}}" ng-model="object.eventtime">
                </td>
            </tr>
            <tr>
                <td>
                    <label>Start day of year : </label>
                    <input type="text" name ="startdayofyear" value="{{object.startdayofyear}}" ng-model="object.startdayofyear">
                </td>
                <td>
                    <label>End day of year : </label>
                    <input type="text" name ="enddayofyear" value="{{object.enddayofyear}}" ng-model="object.enddayofyear">
                </td>
                <td>
                    <label>Darwin core year : </label>
                    <input type="text" name ="dwcyear" value="{{object.dwcyear}}" ng-model="object.dwcyear">
                </td>
            </tr><tr>
                <td>
                    <label>Darwin core month : </label>
                    <input type="text" name ="dwcmonth" value="{{object.dwcmonth}}" ng-model="object.dwcmonth">
                </td>
                <td>
                    <label>Darwin coreday : </label>
                    <input type="text" name ="dwcday" value="{{object.dwcday}}" ng-model="object.dwcday">
                </td>
                <td>
                    <label>Verbatim event date : </label>
                    <input type="text" name ="verbatimeventdate" value="{{object.verbatimeventdate}}" ng-model="object.verbatimeventdate">
                </td>
            </tr><tr>
                <td>
                    <label>Habitat : </label>
                    <input type="text" name ="habitat" value="{{object.habitat}}" ng-model="object.habitat">
                </td>
                <td>
                    <label>Field number : </label>
                    <input type="text" name ="fieldnumber" value="{{object.fieldnumber}}" ng-model="object.fieldnumber">
                </td>
                <td>
                    <label>Field notes : </label>
                    <input type="text" name ="fieldnotes" value="{{object.fieldnotes}}" ng-model="object.fieldnotes">
                </td>
            </tr><tr>
                <td>
                    <label>Event remarks : </label>
                    <input type="text" name ="eventremarks" value="{{object.eventremarks}}" ng-model="object.eventremarks">
                </td>
                <td>
                    <label>Higher geography : </label>
                    <input type="text" name ="highergeography" value="{{object.highergeography}}" ng-model="object.highergeography">
                </td>
                <td>
                    <label>Continent : </label>
                    <input type="text" name ="continent" value="{{object.continent}}" ng-model="object.continent">
                </td>
            </tr><tr>
                <td>
                    <label>Water body : </label>
                    <input type="text" name ="waterbody" value="{{object.waterbody}}" ng-model="object.waterbody">
                </td>
                <td>
                    <label>Island group : </label>
                    <input type="text" name ="islandgroup" value="{{object.islandgroup}}" ng-model="object.islandgroup">
                </td>
                <td>
                    <label>Island : </label>
                    <input type="text" name ="island" value="{{object.island}}" ng-model="object.island">
                </td>
            </tr><tr>
                <td>
                    <label>Country : </label>
                    <input type="text" name ="country" value="{{object.country}}" ng-model="object.country">
                </td>
                <td>
                    <label>Country code : </label>
                    <input type="text" name ="countrycode" value="{{object.countrycode}}" ng-model="object.countrycode">
                </td>
                <td>
                    <label>State province : </label>
                    <input type="text" name ="stateprovince" value="{{object.stateprovince}}" ng-model="object.stateprovince">
                </td>
            </tr><tr>
                <td>
                    <label>County : </label>
                    <input type="text" name ="county" value="{{object.county}}" ng-model="object.county">
                </td>

                <td>
                    <label>Municipality : </label>
                    <input type="text" name ="municipality" value="{{object.municipality}}" ng-model="object.municipality">
                </td>
                <td>
                    <label>Locality : </label>
                    <input type="text" name ="locality" value="{{object.locality}}" ng-model="object.locality">
                </td>
            </tr><tr>
                <td>
                    <label>Verbatim locality : </label>
                    <input type="text" name ="verbatimlocality" value="{{object.verbatimlocality}}" ng-model="object.verbatimlocality">
                </td>

                <td>
                    <label>Verbatim elevation : </label>
                    <input type="text" name ="verbatimelevation" value="{{object.verbatimelevation}}" ng-model="object.verbatimelevation">
                </td>
                <td>
                    <label>Minimum elevation in meters : </label>
                    <input type="text" name ="minimumelevationinmeters" value="{{object.minimumelevationinmeters}}" ng-model="object.minimumelevationinmeters">
                </td>
            </tr><tr>
                <td>
                    <label>Maximum elevation in meters : </label>
                    <input type="text" name ="maximumelevationinmeters" value="{{object.maximumelevationinmeters}}" ng-model="object.maximumelevationinmeters">
                </td>

                <td>
                    <label>Verbatim depth : </label>
                    <input type="text" name ="verbatimdepth" value="{{object.verbatimdepth}}" ng-model="object.verbatimdepth">
                </td>
                <td>
                    <label>Minimum depth in meters : </label>
                    <input type="text" name ="minimumdepthinmeters" value="{{object.minimumdepthinmeters}}" ng-model="object.minimumdepthinmeters">
                </td>
            </tr><tr>
                <td>
                    <label>Maximum depth in meters : </label>
                    <input type="text" name ="maximumdepthinmeters" value="{{object.maximumdepthinmeters}}" ng-model="object.maximumdepthinmeters">
                </td>

                <td>
                    <label>Minimum distance above surface (m) : </label>
                    <input type="text" name ="minimumdistanceabovesurfaceinmeters" value="{{object.minimumdistanceabovesurfaceinmeters}}" ng-model="object.minimumdistanceabovesurfaceinmeters">
                </td>
                <td>
                    <label>Max distance above surface (m) : </label>
                    <input type="text" name ="maximumdistanceabovesurfaceinmeters" value="{{object.maximumdistanceabovesurfaceinmeters}}" ng-model="object.maximumdistanceabovesurfaceinmeters">
                </td>
            </tr><tr>
                <td>
                    <label>Location according to : </label>
                    <input type="text" name ="locationaccordingto" value="{{object.locationaccordingto}}" ng-model="object.locationaccordingto">
                </td>

                <td>
                    <label>Location remarks : </label>
                    <input type="text" name ="locationremarks" value="{{object.locationremarks}}" ng-model="object.locationremarks">
                </td>
                <td>
                    <label>Verbatim coordinates : </label>
                    <input type="text" name ="verbatimcoordinates" value="{{object.verbatimcoordinates}}" ng-model="object.verbatimcoordinates">
                </td>
            </tr><tr>
                <td>
                    <label>Verbatim latitude : </label>
                    <input type="text" name ="verbatimlatitude" value="{{object.verbatimlatitude}}" ng-model="object.verbatimlatitude">
                </td>

                <td>
                    <label>Verbatim longitude : </label>
                    <input type="text" name ="verbatimlongitude" value="{{object.verbatimlongitude}}" ng-model="object.verbatimlongitude">
                </td>
                <td>
                    <label>Verbatim coordinate system : </label>
                    <input type="text" name ="verbatimcoordinatesystem" value="{{object.verbatimcoordinatesystem}}" ng-model="object.verbatimcoordinatesystem">
                </td>
            </tr><tr>
                <td>
                    <label>Verbatim srs : </label>
                    <input type="text" name ="verbatimsrs" value="{{object.verbatimsrs}}" ng-model="object.verbatimsrs">
                </td>

                <td>
                    <label>Decimal latitude : </label>
                    <input type="text" name ="decimallatitude" value="{{object.decimallatitude}}" ng-model="object.decimallatitude">
                </td>
                <td>
                    <label>Decimal longitude : </label>
                    <input type="text" name ="decimallongitude" value="{{object.decimallongitude}}" ng-model="object.decimallongitude">
                </td>
            </tr><tr>
                <td>
                    <label>Geodetic datum : </label>
                    <input type="text" name ="geodeticdatum" value="{{object.geodeticdatum}}" ng-model="object.geodeticdatum">
                </td>

                <td>
                    <label>Coordinate uncertainty in meters : </label>
                    <input type="text" name ="coordinateuncertaintyinmeters" value="{{object.coordinateuncertaintyinmeters}}" ng-model="object.coordinateuncertaintyinmeters">
                </td>
                <td>
                    <label>Coordinate precision : </label>
                    <input type="text" name ="coordinateprecision" value="{{object.coordinateprecision}}" ng-model="object.coordinateprecision">
                </td>
            </tr><tr>
                <td>
                    <label>Point radius spatial fit : </label>
                    <input type="text" name ="pointradiusspatialfit" value="{{object.pointradiusspatialfit}}" ng-model="object.pointradiusspatialfit">
                </td>

                <td>
                    <label>Foot print wkt : </label>
                    <input type="text" name ="footprintwkt" value="{{object.footprintwkt}}" ng-model="object.footprintwkt">
                </td>
                <td>
                    <label>Foot print srs : </label>
                    <input type="text" name ="footprintsrs" value="{{object.footprintsrs}}" ng-model="object.footprintsrs">
                </td>
            </tr><tr>
                <td>
                    <label>Foot prints patial fit : </label>
                    <input type="text" name ="footprintspatialfit" value="{{object.footprintspatialfit}}" ng-model="object.footprintspatialfit">
                </td>

                <td>
                    <label>Georeferenced by : </label>
                    <input type="text" name ="georeferencedby" value="{{object.georeferencedby}}" ng-model="object.georeferencedby">
                </td>
                <td>
                    <label>Georeference protocol : </label>
                    <input type="text" name ="georeferenceprotocol" value="{{object.georeferenceprotocol}}" ng-model="object.georeferenceprotocol">
                </td>
            </tr><tr>
                <td>
                    <label>Georeference sources : </label>
                    <input type="text" name ="georeferencesources" value="{{object.georeferencesources}}" ng-model="object.georeferencesources">
                </td>

                <td>
                    <label>Georeference verification status : </label>
                    <input type="text" name ="georeferenceverificationstatus" value="{{object.georeferenceverificationstatus}}" ng-model="object.georeferenceverificationstatus">
                </td>
                <td>
                    <label>Georeference remarks : </label>
                    <input type="text" name ="georeferenceremarks" value="{{object.georeferenceremarks}}" ng-model="object.georeferenceremarks">
                </td>
            </tr><tr>
                <td>
                    <label>Identified by : </label>
                    <input type="text" name ="identifiedby" value="{{object.identifiedby}}" ng-model="object.identifiedby">
                </td>

                <td>
                    <label>Date identified : </label>
                    <input type="date" name ="dateidentified" value="{{object.dateidentified}}" ng-model="object.dateidentified">
                </td>
                <td>
                    <label>Identification references : </label>
                    <input type="text" name ="identificationreferences" value="{{object.identificationreferences}}" ng-model="object.identificationreferences">
                </td>
            </tr>
            <tr>
                <td>
                    <label>Identification remarks : </label>
                    <input type="text" name ="identificationremarks" value="{{object.identificationremarks}}" ng-model="object.identificationremarks">
                </td>
                <td>
                    <label>Identification qualifier : </label>
                    <input type="text" name ="identificationqualifier" value="{{object.identificationqualifier}}" ng-model="object.identificationqualifier">
                <td>
            </tr>
            <tr>
                <td></td>
                <td>

                </td>
                <td style="text-align: right">
                    <input type="submit" ng-click="save()" class="btn btn-success" value="Ajouter">
                    <button class="btn btn-danger" onclick="window.history.back()">Annuler</button>
                </td>
            </tr>
        </table>

    </form>
    <!--<a href="/lemurs/">link</a>-->
</main>
<script src="<c:url value="/resources/assets/js/angular.js"/>"></script>
<script src="<c:url value="/resources/assets/js/appconfig.js"/>"></script>
<script src="<c:url value="/resources/assets/js/controller/modification.js"/>"></script>
<jsp:include page="/WEB-INF/inc/footer.jsp"/>  