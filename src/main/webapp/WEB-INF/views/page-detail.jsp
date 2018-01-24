<%@page import="org.wcs.lemurs.model.TaxonomiBase"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/inc/header.jsp"/>  
<main class="site-content" role="main" ng-controller="object">
    <%
        TaxonomiBase taxo = (TaxonomiBase) request.getAttribute("taxo");
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
                            <div class="item active">
                                <img src="resources/assets/img/bg-lemurs.jpg" class="img-responsive">
                            </div>
                            <div class="item">
                                <img src="resources/assets/img/lem.jpg" class="img-responsive">
                            </div>
                            <div class="item">
                                <img src="resources/assets/img/sclaters-lemur.jpg" class="img-responsive">
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


                    <div class="map-detail-obs">
                        <a href="#"><img class="img-responsive img-rounded" src="resources/assets/img/carte.png"/></a>
                    </div>
                    <p align="center">
                        <button type="button" class="btn btn-primary"><span class="fa fa-download"></span> Télécharger la fiche</button>
                    </p>
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

</main>
<script src="<c:url value="/resources/assets/js/angular.js"/>"></script>
<script src="<c:url value="/resources/assets/js/appconfig.js"/>"></script>
<!--<script src="<c:url value="/resources/assets/js/controller/modification.js"/>"></script>-->
<jsp:include page="/WEB-INF/inc/footer.jsp"/>  