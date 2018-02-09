<%@page import="org.wcs.lemurs.model.TaxonomiBase"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/inc/header.jsp"/>  
<main class="site-content" role="main" ng-controller="taxonomi">
    <%
        TaxonomiBase taxo = (TaxonomiBase) request.getAttribute("taxo");
        Integer moderateur = (Integer) request.getAttribute("moderateur");
    %>
    <input type="hidden" value="<%out.print(taxo.getId());%>" id="idDwc"/>
    <!--<input type="hidden" value="getDetailTaxo" id="load"/>-->
    <!-- taxonomie -->
    <section id="taxonomie">
        <div class="banner-interieur" style="background:url(resources/assets/img/parallax/fexpert.jpg) no-repeat center center;">
            <div class="container" style="margin-top: 5%;">
                <div class="col-md-6 col-md-offset-3">
                    <!-- Search Form -->
                    <form role="form">
                        <!-- Search Field -->
                        <div class="row search-header">
                            <h4 class="text-left">Rechercher</h4>
                            <div class="form-group">
                                <div class="input-group">
                                    <input class="form-control" type="text" name="search" placeholder="nom scientifique de l'espèce" required/>
                                    <span class="input-group-btn">
                                        <button class="btn btn-primary btn-success" type="button"><i class="fa fa-search"></i></button>
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
        <div class="detail-obs">
            <div class="container">
                <h1 class="titre-page">Details - <span><%out.print(taxo.getAcceptednameusage());%></span></h1>
                <div class="col-md-3">

                    <div id="myCarousel" class="carousel slide">
                        <!-- Indicators -->
                        <div class="carousel-inner">                                                        
                            <div ng-repeat="photo in photos" ng-if="photo.profil == true" class="item active">
                                <img ng-src="{{photo.chemin}}" class="img-responsive">
                            </div>
                            <div ng-repeat="photo in photos" ng-if="photo.profil == false" class="item">
                                <img ng-src="{{photo.chemin}}" class="img-responsive">
                            </div>                            
                        </div>
                        <!-- Controls -->
                        <a class="left carousel-control" href="#myCarousel" data-slide="prev">
                            <span class="icon-prev"></span>
                        </a>
                        <a class="right carousel-control" href="#myCarousel" data-slide="next">
                            <span class="icon-next"></span>
                        </a>  
                    </div>
                    <p class="name-img"><%out.print(taxo.getAcceptednameusage());%> - Mammadia</p>

                    <!-- /.carousel -->


                    <div class="map-detail-obs" id="map">
                        <!--                        <a href="#"><img class="img-responsive img-rounded" src="resources/assets/img/carte.png"/></a>-->
                    </div>
                    <p align="center">
                        <button type="button" class="btn btn-primary"><span class="fa fa-download"></span> Télécharger la fiche</button>
                    </p>
                    <%
                        if (moderateur == 0) {
                    %>                    
                    <!--<p align="center">
                        <button type="button" class="btn btn-primary" onclick="window.location='taxonomiCsv'"><i class="fa fa-download"></i> Telecharger_CSV</a></button>
                    </p>-->
                    <!--                    <p align="center">
                                            <button type="button" class="btn btn-primary"><i class="fa fa-upload"></i>Upload_CSV</button>
                                        </p>-->
                    <br>
                    <p align="center">
                        <button type="button" class="btn btn-primary" ng-click="editer(liste[<%out.print(taxo.getId());%>])">Modifier</button>
                    </p>
                    <!--                    <p align="center">
                                            <button type="button" class="btn btn-primary" ng-click="editer(form)">Ajouter</button>
                                        </p>-->
                    <%}%>
                    <p>&nbsp;</p>
                </div>

                <div class="col-md-9">
                    <div class="desc-primary">
                        <p class="titre">
                            Taxonomie
                        </p>
                        <ul>
                            <li>Kingdom: <%out.print(taxo.getKingdom());%></li>
                            <li>Phylum: <%out.print(taxo.getPhylum());%></li>
                            <li>Class: <%out.print(taxo.getDwcclass());%></li>
                            <li>Order: <%out.print(taxo.getDwcorder());%></li>
                            <li>Family: <%out.print(taxo.getDwcfamily());%></li>
                            <li>Genus: <%out.print(taxo.getGenus());%></li>
                            <li>Scientific Name: <%out.print(taxo.getScientificname());%></li>
                            <li>French Vernacular Name: <%out.print(taxo.getFrenchvernacularname());%></li>
                            <li>Malagasy Vernacular Name: <%out.print(taxo.getMalagasyvernacularname());%></li>
                            <li>English Vernacular Name: <%out.print(taxo.getEnglishvernacularname());%></li>
                            <li>Germany Vernacular Name: <%out.print(taxo.getGermanyVernacularName());%></li>

                        </ul>
                    </div>

                    <div class="desc-primary">
                        <p class="titre">
                            Morphologie
                        </p>
                        <ul>
                            <li>Length and weight: <%out.print(taxo.getLengthAndWeight());%></li>
                            <li>Length and weight source: <%out.print(taxo.getLengthAndWeightSource());%></li>
                            <li>Color (EN): <%out.print(taxo.getColorEn());%></li>
                            <li>Color (FR): <%out.print(taxo.getColorFr());%></li>
                            <li>Color source: <%out.print(taxo.getColorSource());%></li>
                        </ul>
                    </div> 

                    <div class="desc-primary">
                        <p class="titre">
                            Habitat et Densité de la population
                        </p>
                        <ul>
                            <li>Habitat (EN): <%out.print(taxo.getHabitatEn());%></li>
                            <li>Habitat (FR): <%out.print(taxo.getHabitatFr());%></li>
                            <li>Habitat Source: <%out.print(taxo.getHabitatsource());%></li>
                            <li>Population density: <%out.print(taxo.getPopulationDensity());%></li>
                            <li>Population Density Source: <%out.print(taxo.getPopulationDensitySource());%></li>
                        </ul> 
                    </div> 

                    <div class="desc-primary">
                        <p class="titre">
                            Ecologie et comportement
                        </p>
                        <ul>
                            <li>Ecology (EN): <%out.print(taxo.getEcologyEn());%></li>
                            <li>Ecology (FR): <%out.print(taxo.getEcologyFr());%></li>
                            <li>Ecology Source: <%out.print(taxo.getEcologysource());%></li>
                            <li>Social behavior (EN): <%out.print(taxo.getBehaviorEn());%></li>
                            <li>Social behavior (FR): <%out.print(taxo.getBehaviorFr());%></li>
                            <li>Social behavior Source: <%out.print(taxo.getBehaviorsource());%></li>
                        </ul> 
                    </div> 

                    <div class="desc-primary">
                        <p class="titre">
                            Menaces
                        </p>
                        <ul>
                            <li>Menaces	Threat (EN): <%out.print(taxo.getThreatEn());%></li>
                            <li>Threat (FR): <%out.print(taxo.getThreatFr());%></li>
                            <li>Threat Source: <%out.print(taxo.getThreatsource());%></li>
                        </ul>                        
                    </div>          
                </div>

                <%
                    if (moderateur == 0) {
                %>   
                <div class="clearfix"></div>
                <div class="col-md-12">
                    <div class="tabbable-panel">
                        <form id="uploadPhoto" method="POST" enctype="multipart/form-data">
                            <input id="photo" style="float: left;" type="file">
                            <div style="float: left; margin-left: 35px; margin-right: 35px;"><input style="margin-right: 5px;" type="checkbox" value="1" id="profil" name="profil">profil</div>
                            <button ng-click="uploadPhoto()">Enregistrer</button>
                        </form>
                        <%}%>
                    </div>
                </div>

                <div class="clearfix"></div>
                <div class="col-md-12">
                    <div class="tabbable-panel">
                        <div class="tabbable-line">
                            <ul class="nav nav-tabs ">
                                <li class="active">
                                    <a href="#tab_uicn" data-toggle="tab">
                                        Statut de conservation</a>
                                </li>
                                <li>
                                    <a href="#tab_aire" data-toggle="tab">
                                        Aires protégées</a>
                                </li>
                                <li>
                                    <a href="#tab_expert" data-toggle="tab">
                                        Experts</a>
                                </li>
                                <li>
                                    <a href="#tab_actu" data-toggle="tab">
                                        Actualités</a>
                                </li>
                                <li>
                                    <a href="#tab_pub" data-toggle="tab">
                                        Publications</a>
                                </li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane active" id="tab_uicn">
                                    <ul>
                                        <li>Conservation Statut IUCN: <%out.print(taxo.getConservationStatus());%></li>
                                        <li>IUCN Source: <%out.print(taxo.getIucnSource());%></li>
                                    </ul>                                    
                                </div>
                                <div class="tab-pane" id="tab_aire">
                                    <ul>
                                        <li>Protected area occurrences: <%out.print(taxo.getProtectedareaoccurrences());%></li>
                                        <li>protected area occurrences Sources: <%out.print(taxo.getProtectedAreaOccurrencesSources());%></li>
                                    </ul>
                                </div>
                                <div class="tab-pane" id="tab_expert">
                                    <ul>
                                        <li>Species expert: <%out.print(taxo.getSpeciesExpert());%></li>
                                    </ul>
                                </div>
                                <div class="tab-pane" id="tab_actu">
                                    <ul>
                                        <li>News and updates: <%out.print(taxo.getNewAndUpdates());%></li>
                                    </ul>
                                </div>
                                <div class="tab-pane" id="tab_pub">
                                    <ul>
                                        <li>Top five publications: <%out.print(taxo.getTopFivePublication());%></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- End Contenu -->

    </section>
    <!-- end taxonomie -->
    <!--Modal edit or new-->
    <div id="editOrnew" class="modal fade" role="dialog">
        <div class="modal-backdrop">
            <div class="modal-content">
                <h1 class="h">INSERTION/MODIFICATION TAXONOMI</h1>
                <div class="row">
                    <div class="col-sm-1"></div>
                    <form>
                        <table class="table-condensed">
                            <tr>
                                <td>
                                    <label>Higher classification : </label>
                                    <input type="text" ng-model="form.higherclassification">
                                </td>
                                <td>
                                    <label>Kingdom : </label>
                                    <input type="text" ng-model="form.kingdom">
                                </td>
                                <td>
                                    <label>Phylum : </label>
                                    <input type="text" ng-model="form.phylum">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label>Darwin core class : </label>
                                    <input type="text" ng-model="form.dwcclass">
                                </td>
                                <td>
                                    <label>Darwin core order : </label>
                                    <input type="text" ng-model="form.dwcorder">
                                </td>
                                <td>
                                    <label>Darwin core family : </label>
                                    <input type="text" ng-model="form.dwcfamily">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label>Genus : </label>
                                    <input type="text" ng-model ="form.genus">
                                </td>
                                <td>
                                    <label>Genus source  : </label>
                                    <input type="text" ng-model ="form.genussource">
                                </td>
                                <td>
                                    <label>Specific epithet  : </label>
                                    <input type="text" ng-model ="form.specificepithet">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label>Specific epithet source  : </label>
                                    <input type="text" ng-model ="form.specificepithetsource">
                                </td>

                                <td>
                                    <label>Infraspecific epithet : </label>
                                    <input type="text" ng-model ="form.infraspecificepithet">
                                </td>
                                <td>
                                    <label>Infraspecificepithetsource : </label> 
                                    <input type="text" ng-model ="form.infraspecificepithetsource">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label>Scientific ng-model : </label> 
                                    <input type="text" ng-model ="form.scientificname">
                                </td>

                                <td>
                                    <label>Scientific ng-model author ship : </label> 
                                    <input type="text" ng-model ="form.scientificnameauthorship">
                                </td>
                                <td>
                                    <label>Accepted ng-model usage : </label> 
                                    <input type="text" ng-model ="form.acceptednameusage">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label>Basis of record : </label> 
                                    <input type="text" ng-model ="form.basisofrecord">
                                </td>
                                <td>
                                    <label>French vernacular ng-model : </label>
                                    <input type="text" ng-model ="form.frenchvernacularname">
                                </td>
                                <td>
                                    <label>Malagasy vernacular ng-model : </label>
                                    <input type="text" ng-model ="form.malagasyvernacularname">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label>English vernacular ng-model : </label>
                                    <input type="text" ng-model ="form.englishvernacularname">
                                </td>
                                <td>
                                    <label>Habitat_fr : </label>
                                    <input type="text" ng-model ="form.habitatFr">
                                </td>
                                <td>
                                    <label>Habitat_en : </label>
                                    <input type="text" ng-model ="form.habitatEn">
                                </td>
                            </tr><tr>
                                <td>
                                    <label>Habitat source : </label>
                                    <input type="text" ng-model ="form.habitatsource">
                                </td>
                                <td>
                                    <label>Ecology_fr : </label>
                                    <input type="text" ng-model ="form.ecologyFr">
                                </td>
                                <td>
                                    <label>Ecology_en : </label>
                                    <input type="text" ng-model ="form.ecologyEn">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label>Ecology source : </label>
                                    <input type="text" ng-model ="form.ecologysource">
                                </td>
                                <td>
                                    <label>Behavior_fr : </label>
                                    <input type="text" ng-model ="form.behaviorFr">
                                </td>
                                <td>
                                    <label>Behavior_en : </label>
                                    <input type="text" ng-model ="form.behaviorEn">
                                </td>
                            </tr><tr>
                                <td>
                                    <label>Behavior source : </label>
                                    <input type="text" ng-model ="form.behaviorsource">
                                </td>
                                <td>
                                    <label>Threat_fr : </label>
                                    <input type="text" ng-model ="form.threatFr">
                                </td>
                                <td>
                                    <label>Threat_en : </label>
                                    <input type="text" ng-model ="form.threatEn">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label>Threat source : </label>
                                    <input type="text" ng-model ="form.threatsource">
                                </td>
                                <td>
                                    <label>Morphology_fr : </label>
                                    <input type="text" ng-model ="form.morphologyFr">
                                </td>
                                <td>
                                    <label>Morphology_en : </label>
                                    <input type="text" ng-model ="form.morphologyEn">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label>Protected area occurrences : </label>
                                    <input type="text" ng-model ="form.protectedareaoccurrences">
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>

                                </td>
                                <td style="text-align: right">
                                    <input class="btn btn-primary" ng-click="annuler()" value="Annuler">
                                    <%if (moderateur == 0) {%>
                                    <input type="submit" class="btn btn-success" ng-click="save()" value="Enregistrer">
                                    <%}%>
                                </td>
                            </tr>
                        </table>
                    </form>
                    <div class="col-sm-1"></div>
                </div>
            </div>
        </div>
    </div>

</main>
<script src="<c:url value="/resources/assets/js/angular.js"/>"></script>
<script src="<c:url value="/resources/assets/js/appconfig.js"/>"></script>
<script src="<c:url value="/resources/assets/js/controller/taxonomicontroller.js"/>"  charset="utf-8"></script>
<script>
    function initMap() {
        var centre = {lat:  -18.9136800, lng: 47.5361300};
        var mark = {lat:  -18.9136800, lng: 47.5361300};
        var map = new google.maps.Map(document.getElementById('map'), {
            zoom: 6,
            center: centre
        });
        var marker = new google.maps.Marker({
            position: mark,
            map: map
        });
    }
    google.maps.event.addDomListener(window, "load", initMap);
</script>
<jsp:include page="/WEB-INF/inc/footer.jsp"/>  