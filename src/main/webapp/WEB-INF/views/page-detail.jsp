<%@page import="org.wcs.lemurs.model.TaxonomiBase"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/inc/header.jsp"/>  
<main class="site-content" role="main" ng-controller="taxonomi">
    <!--Header style-->
    <style>
        @media only screen and (max-width: 992px) and (min-width: 767px){
            .header-pliss {
                padding-top: 72px;
                height: -10px;
                /*background-color: beige;*/
            }        
        }
    </style>
    <%
        TaxonomiBase taxo = (TaxonomiBase) request.getAttribute("taxo");
        Integer moderateur = (Integer) request.getAttribute("moderateur");
    %>
    <input type="hidden" value="<%out.print(taxo.getId());%>" id="idDwc"/>
    <!--<input type="hidden" value="getDetailTaxo" id="load"/>-->
    <!-- taxonomie -->
    <section id="taxonomie">
        <div class="banner-interieur-pliss" style="background:url(resources/assets/img/parallax/fexpert_modif.jpg) no-repeat center center; height: 125px; background-color: beige;"></div>        
        <div class="container-fluid header-pliss">
            <div class="row header-pliss" style="margin-bottom: 10px;border-bottom:  solid;border-bottom-width: 1px;border-bottom-color: beige;">               
                <h1 style="font-size:  14px;font-weight:  600;width:  100%;float: left;margin-top: 9px; color: #a18029; margin-left: 30px;"><%out.print(taxo.getAcceptednameusage());%> | <small>Details</small></h1>                
            </div>                
        </div>
        <!-- Contenu -->
        <div class="detail-obs">
            <div class="container-fluid">
                <!--<h1 class="titre-page">Details - <span><%out.print(taxo.getAcceptednameusage());%></span></h1>-->
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
                    <p class="name-img"><%out.print(taxo.getAcceptednameusage());%></p>

<!--                    <iframe class="img-responsive"
                            src="https://www.youtube.com/embed/tgbNymZ7vqY">
                    </iframe>-->

                    <!-- /.carousel -->


                    <div class="map-detail-obs" id="map">
                        <!--                        <a href="#"><img class="img-responsive img-rounded" src="resources/assets/img/carte.png"/></a>-->
                    </div>
                    <p align="center">
                        <button type="button" onclick="window.location = 'ficheTaxonomi?id=<%out.print(taxo.getId());%>';" class="btn btn-primary"><span class="fa fa-download"></span> Télécharger la fiche</button>
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
                        <button type="button" class="btn btn-primary" ng-click="editer(liste)">Modifier</button>
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
                            <div class="row">
                                <li>                                
                                    <div class="col-md-4">
                                        Kingdom:
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getKingdom());%>
                                    </div>                                                                                                     
                                </li>
                            </div>
                            <div class="row">
                                <li>
                                    <div class="col-md-4">
                                        Phylum:
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getPhylum());%>
                                    </div>                                     
                                </li>
                            </div>   
                            <div class="row">    
                                <li>                                
                                    <div class="col-md-4">
                                        Class:
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getDwcclass());%>
                                    </div>                                                                     
                                </li>
                            </div>   
                            <div class="row">
                                <li>                                
                                    <div class="col-md-4">
                                        Order:
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getDwcorder());%>
                                    </div>                                                                        
                                </li>             
                            </div>
                            <div class="row">    
                                <li>                                
                                    <div class="col-md-4">
                                        Family:
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getDwcfamily());%>
                                    </div>                                                                     
                                </li>
                            </div>   
                            <div class="row">    
                                <li>                                
                                    <div class="col-md-4">
                                        Genus:
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getGenus());%>
                                    </div>                                                                     
                                </li>
                            </div>   
                            <div class="row">    
                                <li>

                                    <div class="col-md-4">
                                        Scientific Name:
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getScientificname());%>
                                    </div>                                                                     
                                </li>
                            </div>   
                            <div class="row">
                                <li>                                
                                    <div class="col-md-4">
                                        French Vernacular Name:
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getFrenchvernacularname());%>
                                    </div>                                                                     
                                </li>
                            </div>   
                            <div class="row">
                                <li>                                
                                    <div class="col-md-4">
                                        Malagasy Vernacular Name:
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getMalagasyvernacularname());%>
                                    </div>                                                                     
                                </li>
                            </div>   
                            <div class="row">
                                <li>                                
                                    <div class="col-md-4">
                                        English Vernacular Name:
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getEnglishvernacularname());%>
                                    </div>                                                                     
                                </li>
                            </div>
                            <div class="row">
                                <li>
                                    <div class="col-md-4">
                                        Germany Vernacular Name:
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getGermanyVernacularName());%>
                                    </div>                                                                        
                                </li>                            
                            </div>
                        </ul>
                    </div>

                    <div class="desc-primary">
                        <p class="titre">
                            Morphologie
                        </p>
                        <ul>
                            <div class="row">
                                <li>
                                    <div class="col-md-4">
                                        Length and weight:
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getLengthAndWeight());%>
                                    </div>                                                                        
                                </li>                            
                            </div>
                            <div class="row">
                                <li>
                                    <div class="col-md-4">
                                        Length and weight source:
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getLengthAndWeightSource());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-4">
                                        Color (EN): 
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getColorEn());%>
                                    </div>                                                                        
                                </li>                            
                            </div>
                            <div class="row">
                                <li>
                                    <div class="col-md-4">
                                        Color (FR):
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getColorFr());%>
                                    </div>                                                                        
                                </li>                            
                            </div>
                            <div class="row">
                                <li>
                                    <div class="col-md-4">
                                        Color source:
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getColorSource());%>
                                    </div>                                                                        
                                </li>                            
                            </div>
                        </ul>
                    </div> 

                    <div class="desc-primary">
                        <p class="titre">
                            Habitat et Densité de la population
                        </p>
                        <ul>
                            <div class="row">
                                <li>
                                    <div class="col-md-4">
                                        Habitat (EN):
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getHabitatEn());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-4">
                                        Habitat (FR):
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getHabitatFr());%>
                                    </div>                                                                        
                                </li>                            
                            </div>
                            <div class="row">
                                <li>
                                    <div class="col-md-4">
                                        Habitat Source: 
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getHabitatsource());%>
                                    </div>                                                                        
                                </li>                            
                            </div>
                            <div class="row">
                                <li>
                                    <div class="col-md-4">
                                        Population density:
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getPopulationDensity());%>
                                    </div>                                                                        
                                </li>                            
                            </div>
                            <div class="row">
                                <li>
                                    <div class="col-md-4">
                                        Population Density Source:
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getPopulationDensitySource());%>
                                    </div>                                                                        
                                </li>                            
                            </div>
                        </ul> 
                    </div> 

                    <div class="desc-primary">
                        <p class="titre">
                            Ecologie et comportement
                        </p>
                        <ul>
                            <div class="row">
                                <li>
                                    <div class="col-md-4">
                                        Ecology (EN):
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getEcologyEn());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-4">
                                        Ecology (FR):
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getEcologyFr());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-4">
                                        Ecology Source:
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getEcologysource());%>
                                    </div>                                                                        
                                </li>                            
                            </div>
                            <div class="row">
                                <li>
                                    <div class="col-md-4">
                                        Social behavior (EN):
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getBehaviorEn());%>
                                    </div>                                                                        
                                </li>                            
                            </div>
                            <div class="row">
                                <li>
                                    <div class="col-md-4">
                                        Social behavior (FR):
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getBehaviorFr());%>
                                    </div>                                                                        
                                </li>                            
                            </div>
                            <div class="row">
                                <li>
                                    <div class="col-md-4">
                                        Social behavior Source:
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getBehaviorsource());%>
                                    </div>                                                                        
                                </li>                            
                            </div>
                        </ul> 
                    </div> 

                    <div class="desc-primary">
                        <p class="titre">
                            Menaces
                        </p>
                        <ul>
                            <div class="row">
                                <li>
                                    <div class="col-md-4">
                                        Menaces	Threat (EN):
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getThreatEn());%>
                                    </div>                                                                        
                                </li>                            
                            </div>
                            <div class="row">
                                <li>
                                    <div class="col-md-4">
                                        Threat (FR):
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getThreatFr());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-4">
                                        Threat Source:
                                    </div>
                                    <div class="col-md-8">
                                        <%out.print(taxo.getThreatsource());%>
                                    </div>                                                                        
                                </li>                            
                            </div>
                        </ul>                        
                    </div>          
                </div>

                <%
                    if (moderateur == 0) {
                %>   
                <div class="clearfix"></div>
                <div class="col-md-12">                    
                    <div class="tabbable-panel">                        
                        <button style="float: right;" class="btn btn-primary" onclick="$('#modal-upload-photo').modal({backdrop: 'static'});">Ajout Photo</button>
                        <form id="uploadVideo" method="POST">
                            <input id="lien" style="float: left;" type="text" placeholder="veuiller insérer le lien de la vidéo">                    
                            <button ng-click="uploadVideo()">Enregistrer</button>
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
                                        <div class="row">
                                            <li>
                                                <div class="col-md-4">
                                                    Conservation Statut IUCN:
                                                </div>
                                                <div class="col-md-8">
                                                    <%out.print(taxo.getConservationStatus());%>
                                                </div>                                                                        
                                            </li>                            
                                        </div>
                                        <div class="row">
                                            <li>
                                                <div class="col-md-4">
                                                    IUCN Source:
                                                </div>
                                                <div class="col-md-8">
                                                    <%out.print(taxo.getIucnSource());%>
                                                </div>                                                                        
                                            </li>                            
                                        </div>
                                    </ul>                                    
                                </div>
                                <div class="tab-pane" id="tab_aire">
                                    <ul>
                                        <div class="row">
                                            <li>
                                                <div class="col-md-4">
                                                    Protected area occurrences:
                                                </div>
                                                <div class="col-md-8">
                                                    <%out.print(taxo.getProtectedareaoccurrences());%>
                                                </div>                                                                        
                                            </li>                            
                                        </div>
                                        <div class="row">
                                            <li>
                                                <div class="col-md-4">
                                                    protected area occurrences Sources:
                                                </div>
                                                <div class="col-md-8">
                                                    <%out.print(taxo.getProtectedAreaOccurrencesSources());%>
                                                </div>                                                                        
                                            </li>                            
                                        </div>
                                    </ul>
                                </div>
                                <div class="tab-pane" id="tab_expert">
                                    <ul>
                                        <div class="row">
                                            <li>
                                                <div class="col-md-4">
                                                    Species expert: 
                                                </div>
                                                <div class="col-md-8">
                                                    <%out.print(taxo.getSpeciesExpert());%>
                                                </div>                                                                        
                                            </li>                            
                                        </div>
                                    </ul>
                                </div>
                                <div class="tab-pane" id="tab_actu">
                                    <ul>
                                        <div class="row">
                                            <li>
                                                <div class="col-md-4">
                                                    News and updates:
                                                </div>
                                                <div class="col-md-8">
                                                    <%out.print(taxo.getNewAndUpdates());%>
                                                </div>                                                                        
                                            </li>                            
                                        </div>                                        
                                    </ul>
                                </div>
                                <div class="tab-pane" id="tab_pub">
                                    <ul>
                                        <div class="row">
                                            <li>
                                                <div class="col-md-4">
                                                    Top five publications:
                                                </div>
                                                <div class="col-md-8">
                                                    <%out.print(taxo.getTopFivePublication());%>
                                                </div>                                                                        
                                            </li>                            
                                        </div>
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
            <div class="modal-content"><h1 class="titre-page">Taxonomie - <span>Edition</span></h1>
                <div class="row">
                    <div class="col-sm-1"></div>
                    <form>
                        <div class="col-md-10 col-md-offset-1 insert-modif input-group">
                            <div class="col-md-4">
                                <p><label>Higher Classification :</label><input class="form-control" type="text" ng-model="form.higherclassification"></p>
                                <p><label>Kingdom :</label><input class="form-control" type="text" ng-model="form.kingdom"></p>
                                <p><label>Phylum :</label><input class="form-control" type="text" ng-model="form.phylum"></p>
                                <p><label>Class :</label><input class="form-control" type="text" ng-model="form.dwcclass"></p>
                                <p><label>Order :</label><input class="form-control" type="text" ng-model="form.dwcorder"></p>
                                <p><label>Family :</label><input class="form-control" type="text" ng-model="form.dwcfamily"></p>
                                <p><label>Genus :</label><input class="form-control" type="text" ng-model ="form.genus"></p>
                                <p><label>Genus Source :</label><input class="form-control" type="text" ng-model ="form.genussource"></p>                                
                                <p><label>Scientific Name :</label><input class="form-control" type="text" ng-model ="form.scientificname"></p>
                                <p><label>Scientific Name Authorship :</label><input class="form-control" type="text" ng-model ="form.scientificnameauthorship"></p>
                                <p><label>Accepted Name Usage :</label><input class="form-control" type="text" ng-model ="form.acceptednameusage"></p>
                                <p><label>Specific Epithet :</label><input class="form-control" type="text" ng-model ="form.specificepithet"></p>
                                <p><label>Specific epithet source :</label><input class="form-control" type="text" ng-model ="form.specificepithetsource"></p>
                                <p><label>Infraspecific epithet :</label><input class="form-control" type="text" ng-model ="form.infraspecificepithet"></p>
                                <p><label>Infraspecific Epithet Source :</label><input class="form-control" type="text" ng-model ="form.infraspecificepithetsource"></p>
                                <p><label>Basis Of Record :</label><input class="form-control" type="text" ng-model ="form.basisofrecord"></p>
                            </div>

                            <div class="col-md-4">                                
                                <p><label>French Vernacular Name :</label><input class="form-control" type="text" ng-model ="form.frenchvernacularname"></p>
                                <p><label>Malagasy Vernacular Name :</label><input class="form-control" type="text" ng-model ="form.malagasyvernacularname"></p>
                                <p><label>English Vernacular Name :</label><input class="form-control" type="text" ng-model ="form.englishvernacularname"></p>
                                <p><label>Germany Vernacular Name :</label><input class="form-control" type="text" ng-model ="form.germanyVernacularName"></p>
                                <p><label>Length and weight :</label><input class="form-control" type="text" ng-model ="form.lengthAndWeight"></p>
                                <p><label>Length and weight source :</label><input class="form-control" type="text" ng-model ="form.lengthAndWeightSource"></p>
                                <p><label>Color (EN) :</label><input class="form-control" type="text" ng-model ="form.colorEn"></p>
                                <p><label>Color (FR) :</label><input class="form-control" type="text" ng-model ="form.colorFr"></p>
                                <p><label>Color source :</label><input class="form-control" type="text" ng-model ="form.colorSource"></p>
                                <p><label>Habitat (EN) :</label><input class="form-control" type="text" ng-model ="form.habitatEn"></p>
                                <p><label>Habitat (FR) :</label><input class="form-control" type="text" ng-model ="form.habitatFr"></p>
                                <p><label>Habitat Source :</label><input class="form-control" type="text" ng-model ="form.habitatsource"></p>
                                <p><label>Population density :</label><input class="form-control" type="text" ng-model ="form.populationDensity"></p>
                                <p><label>Population density source:</label><input class="form-control" type="text" ng-model ="form.populationDensitySource"></p>
                                <p><label>Ecology (EN) :</label><input class="form-control" type="text" ng-model ="form.ecologyEn"></p>                                
                            </div>
                            <div class="col-md-4">
                                <p><label>Ecology (FR) :</label><input class="form-control" type="text" ng-model ="form.ecologyFr"></p>
                                <p><label>Ecology Source :</label><input class="form-control" type="text" ng-model ="form.ecologysource"></p>
                                <p><label>Social behavior (EN) :</label><input class="form-control" type="text" ng-model ="form.behaviorEn"></p>
                                <p><label>Social behavior (FR) :</label><input class="form-control" type="text" ng-model ="form.behaviorFr"></p>
                                <p><label>Social behavior Source :</label><input class="form-control" type="text" ng-model ="form.behaviorsource"></p>
                                <p><label>Threat (EN) :</label><input class="form-control" type="text" ng-model ="form.threatEn"></p>
                                <p><label>Threat (FR) :</label><input class="form-control" type="text" ng-model ="form.threatFr"></p>
                                <p><label>Threat Source :</label><input class="form-control" type="text" ng-model ="form.threatsource"></p>
                                <p><label>Conservation Statut IUCN :</label><input class="form-control" type="text" ng-model ="form.conservationStatus"></p>
                                <p><label>IUCN source :</label><input class="form-control" type="text" ng-model ="form.iucnSource"></p>
                                <p><label>Protected area occurrences :</label><input class="form-control" type="text" ng-model ="form.protectedareaoccurrences"></p>
                                <p><label>protected area occurrencesSources :</label><input class="form-control" type="text" ng-model ="form.protectedAreaOccurrencesSources"></p>
                                <p><label>Species expert :</label><input class="form-control" type="text" ng-model ="form.speciesExpert"></p>
                                <p><label>News and updates :</label><input class="form-control" type="text" value = "<%out.print(taxo.getNewAndUpdates());%>"></p>
                                <p><label>Top five publications :</label><input class="form-control" type="text" ng-model ="form.topFivePublication"></p>                                
                            </div>
                        </div>           
                        <div class="pull-right divider" style="margin-right: 112px;">
                            <input class="btn btn-primary" ng-click="annuler()" value="Annuler">
                            <%if (moderateur == 0) {%>
                            <input type="submit" class="btn btn-success" ng-click="save()" value="Enregistrer">                            
                            <%}%>
                        </div>                                                            
                    </form>
                    <div class="col-sm-1"></div>
                </div>
            </div>
        </div>
    </div>
    <div id='modal-alert' class='modal fade' role='dialog' style='display:none !important' tabindex="-1">
        <div class='modal-dialog'>
            <div class='modal-content'>
                <div class="modal-header">
                    <button data-dismiss='modal' class='close' type='button'>x</button>
                    <h4 class="modal-title"><center>REMARQUE</center></h4>
                </div>
                <div class='modal-body'>
                    <div class='row'>
                        <div class='col-md-10 col-md-offset-1'>                            
                            <div class="col-sm-12">
                                {{alerte}}
                            </div>                                    
                        </div>
                    </div>
                </div>
                <div class='modal-footer'>
                    <button type='button' class='btn btn-default btn-sm' onclick="$('#link').val('')" data-dismiss='modal'>OK</button>                    
                </div>
            </div>
        </div>
    </div>

    <div id='modal-upload-photo' class='modal fade' role='dialog' style='display:none !important' tabindex="-1">
        <div class='modal-dialog'>
            <div class='modal-content'>
                <div class="modal-header">
                    <button data-dismiss='modal' class='close' type='button'>x</button>
                    <h4 class="modal-title"><center>Ajouter une photo</center></h4>
                </div>
                <form id="uploadPhoto" method="POST" enctype="multipart/form-data">
                    <div class='modal-body'>                    
                        <input style="margin-bottom: 1px;" id="photo" class="form-control" type="file">
                        <div style="margin-bottom: 1px;" class="form-control"><input style="margin-right: 5px;" type="checkbox" value="1" id="profil" name="profil">Photo profil</div>
                        <input class="form-control" type="date" id="datePrisePhoto" name="datePrisePhoto" placeholder="veuiller insérer la date quand la photo a été prise">                                            
                        Termes et conditions
                        <a href="#" onclick="window.open('resources/assets/policy.pdf','_blank')">telecharger ici</a>
                    </div>
                    <div class='modal-footer'>
                        <div style="float: left;">*Les termes et conditions doivent être accépter pour envoyer des photos</div>
                        <button class="btn btn-success" ng-click="uploadPhoto()">Accepter et enregistrer</button>
                        <button type='button' class='btn btn-default btn-sm' onclick="$('#link').val('')" data-dismiss='modal'>Annuler</button>                    
                    </div>
                </form>
            </div>
        </div>
    </div>

</main>
<script src="<c:url value="/resources/assets/js/angular.js"/>"></script>
<script src="<c:url value="/resources/assets/js/appconfig.js"/>"></script>
<script src="<c:url value="/resources/assets/js/controller/detailTaxonomiController.js"/>"  charset="utf-8"></script>
<script>
                            function initMap() {
                            var centre = {lat: - 18.9136800, lng: 47.5361300};
                            var mark = {lat: - 18.9136800, lng: 47.5361300};
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