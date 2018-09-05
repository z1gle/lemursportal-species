<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/inc/header.jsp"/>  
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<main class="site-content" role="main" ng-controller="darwin">
    <!--Style for search-bar-->
    <style>                
        /* check box for public and private */
        .badge-checkboxes .checkbox input[type="checkbox"],
        .badge-checkboxes label.checkbox-inline input[type="checkbox"] {
            /*  Hide the checkbox, but keeps tabbing to it possible. */
            position: absolute;
            clip: rect(0 0 0 0);
        }

        .badge-checkboxes .checkbox label,
        .badge-checkboxes label.checkbox-inline {
            padding-left:0; /* Remove space normally used for the checkbox */
        }

        .badge-checkboxes .checkbox input[type="checkbox"]:checked:focus + .badge,
        .badge-checkboxes label.checkbox-inline input[type="checkbox"]:checked:focus + .badge {
            box-shadow:0 0 2pt 1pt #333;  /* Outline when checkbox is focused/tabbed to */
        }

        .badge-checkboxes .checkbox input[type="checkbox"]:focus + .badge,
        .badge-checkboxes label.checkbox-inline input[type="checkbox"]:focus + .badge {
            box-shadow:0 0 2pt 1pt #999;  /* Outline when checkbox is focused/tabbed to */
        }

        .badge-checkboxes .checkbox input[type="checkbox"] + .badge,
        .badge-checkboxes label.checkbox-inline input[type="checkbox"] + .badge {
            border:1px solid #999; /* Add outline to badge */

            /* Make text in badge not selectable */
            -webkit-touch-callout: none;
            -webkit-user-select: none;
            -khtml-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
        }

        /* Give badges for disabled checkboxes an opacity of 50% */
        .badge-checkboxes .checkbox input[type="checkbox"]:disabled + .badge,
        .badge-checkboxes label.checkbox-inline input[type="checkbox"]:disabled + .badge
        {
            -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=50)";
            filter: alpha(opacity=50);
            -moz-opacity: 0.5;
            -khtml-opacity: 0.5;
            opacity: 0.5;   
        }

        /* Remove badge background-color and set text color for not checked options */
        .badge-checkboxes .checkbox input[type="checkbox"]:not(:checked) + .badge,
        .badge-checkboxes label.checkbox-inline input[type="checkbox"]:not(:checked) + .badge{
            background-color:Transparent;
            color:#999;
        }

        /*The following css only required for Bootstrap <= 3.1.0 */
        .badge-checkboxes .checkbox {
            padding-left:0; /* Remove space normally used for the checkbox */
        }
        .badge-checkboxes .disabled label,
        .badge-checkboxes label.checkbox-inline.disabled {
            cursor:not-allowed
        }        
    </style>

    <!--Header style-->
    <style>
        @media only screen and (max-width: 992px) and (min-width: 767px){
            .header-pliss {
                padding-top: 72px;
                height: -10px;
                background-color: beige;
            }        
        }
    </style>
    <!-- taxonomie -->
    <section id="taxonomie">
        <div class="banner-interieur-pliss" style="background:url(resources/assets/img/parallax/fexpert_modif.jpg) no-repeat center center; height: 125px; background-color: beige;"></div>
        <div class="container-fluid header-pliss">
            <div class="row" style="background-color: beige;margin-left: -15px; height: 45px; margin-bottom: -10px;">
                <!--<div class="col-md-8 col-sm-8">-->
                <form id="form-search">
                    <!-- Search Field -->                                                    
                    <div class="form-group" style="margin-bottom: 0px; margin-top: 5px; margin-left: 10px;">
                        <div class="input-group" style="width: 100%;">                                             
                            <div class="form-group badge-checkboxes">                                            
                                <div>
                                    <input id="form-search" ng-keyup="$event.keyCode == 13 ? rechercherAvancee() : null" name="espece" type="text" placeholder="search by scientific name" class="checkbox-inline" style="height: 20px; border-radius: 15px; width: 26%; border-style: solid;border-width: 1px; float: left;">                                        
                                    <c:choose>
                                        <c:when test="${utilisateur.nom!=''&&utilisateur.nom!=null}">
                                            <label style="float: left; margin-top: -3px;" class="checkbox-inline">
                                                <input id="publique" name="etat[]" type="checkbox" value="1" checked="">
                                                <span class="badge" style="margin-left: 15px;">Publique</span>
                                            </label>
                                            <label style="float: left; margin-top: -3px;" class="checkbox-inline">
                                                <input id="privee" name="etat[]" type="checkbox" value="0">
                                                <span class="badge" style="">Privée</span>
                                            </label>
                                        </c:when>
                                        <c:otherwise>
                                            <label style="float: left; margin-top: -3px;" class="checkbox-inline">
                                                <input id="publique" name="etat[]" disabled="" type="checkbox" value="1" checked="">
                                                <span class="badge" style="margin-left: 15px;">Publique</span>
                                            </label>
                                            <label style="float: left; margin-top: -3px;" class="checkbox-inline">
                                                <input id="privee" disabled="" name="etat[]" type="checkbox" value="0">
                                                <span class="badge" style="">Privée</span>
                                            </label>
                                        </c:otherwise>
                                    </c:choose>
                                    <select name="validation" id="etat" class="checkbox-inline" style="height: 20px; border-radius: 15px; border-style: solid;border-width: 1px; width: 26%; float: left;">
                                        <option value="1"><spring:message code="data.status.all_reliable_reviews_data"/></option>
                                        <option value="-999"><spring:message code="data.status.all_occurences"/></option>
                                        <option value="-1"><spring:message code="data.status.all_awaiting_review"/></option>
                                        <option value="0"><spring:message code="data.status.all_questionnable_reviews_data"/></option>
                                    </select>
                                    <c:if test="${utilisateur.nom!=''&&utilisateur.nom!=null}">
                                        <select name="validationMine" id="myEtat" class="checkbox-inline" style="height: 20px; border-radius: 15px; border-style: solid;border-width: 1px; width: 26%; float: left;">
                                            <option value="-999"><spring:message code="data.status.disable"/></option>
                                            <option value="-1000"><spring:message code="data.status.my_occurences"/></option>
                                            <option value="1"><spring:message code="data.status.my_reliable_reviews_data"/></option>
                                            <option value="-1"><spring:message code="data.status.my_awaiting_review"/></option>
                                            <option value="0"><spring:message code="data.status.my_questionnable_reviews_data"/></option>
                                            <option value="-2"><spring:message code="data.status.my_invalidated"/></option>
                                        </select>
                                    </c:if>
                                    <!--<span class="input-group-btn">-->
                                    <a href="#" title="search" style="padding: 0px;height: 20px;float: left;margin-left: 6px;" onclick="rechercheSearch()" class="btn"><i style="color: darkgrey" class="fa fa-search"></i></a>
                                    <!--</span>-->
                                    <!--<select name="validationMine" id="myEtat" ng-model="modelePourFaireMarcherOnChange.id" ng-change="rechercherAvancee()" class="checkbox-inline" style="height: 20px; border-radius: 15px; border-style: solid;border-width: 1px; width: 5%; float: left;">-->
                                </div>                                            
                            </div>
                        </div>
                    </div>                        
                </form>
            </div>                
        </div>
        <!-- Contenu -->
        <div class="visu">
            <div class="container-fluid  header-pliss">                
                <div class="row" style="margin-bottom: 10px;margin-top: 10px;border-bottom:  solid;border-bottom-width: 1px;border-bottom-color: beige;">
                    <!-- Stat -->                                        
                    <h1 style="margin-left: 10px; font-size:  14px;font-weight:  600;width:  100%;float: left;margin-top: 9px; color: #a18029;">Visualisation cartographique |</h1>
                    <!--                    <h5 style="float: right;" class="stat " ng-cloak>Page: <b>{{pageEnCours}}/{{lastPage}}</b> | Observation total: <b>{{liste[0].total}}</b></h5>                    -->                                        
                    <!--<input ng-keyup="$event.keyCode == 13 ? rechercheGlobale() : null" title="Global research" id="rechercheGlobale" type="text" style="display: inline-block; float: left; margin-left: 8px;">-->                    
                    <!-- End Stat -->                    
                </div>
                <!--                <div class="row" style="margin-top: 10px;">
                                    <form class="col-md-12" style="float: right; max-width: 100%;" id="form-search">
                                         Search Field                                                     
                                        <div class="form-group">
                                            <div class="input-group">     
                <c:if test="${utilisateur.nom!=''&&utilisateur.nom!=null}">
                    <select id="validation" name="validationMine" style="max-width: 20%; float: right;" class="form-control" >                                    
                        <option value="-999">Mes données</option>
                        <option value="-1000">Tous</option>
                        <option value="1">Validé</option>
                        <option value="0">Questionnable</option>
                        <option value="-1">En attente de validation</option>
                    </select>
                </c:if>
                <select id="validation" name="validation" style="max-width: 20%; float: right;" class="form-control" >                                    
                    <option value="-999">Données</option>
                    <option value="1">Validé</option>
                    <option value="0">Questionnable</option>
                    <option value="-1">En attente de validation</option>
                </select>
                <input style="max-width: 20%; float: left;" class="form-control" type="text"  name="espece" placeholder="Espèce à rechercher"/>                            
                <ul style=" margin-left: 10px;">
                    <li style="display: inline; margin-left: 10px;"><input name="etat[]" value="1" type="checkbox" checked> Publique</li>
                <c:if test="${utilisateur.nom!=''&&utilisateur.nom!=null}">
                <li style="display: inline; margin-left: 10px;"><input name="etat[]" value="0" type="checkbox"> Sensible</li>
                </c:if>
        </ul>                                
        <span class="input-group-btn">
            <button  style="margin-top: -8px; margin-bottom: 0px; max-height: 25px; padding-top: 3px;" onclick="rechercheSearch()" class="btn btn-primary btn-success" type="submit"><i class="fa fa-search"></i></button>
        </span>
        <a href="" class=" btn btn-default"><i class="fa fa-remove"></i></a>
    </div>
</div>                        
</form>                    
</div>-->
                <div class="container">
                    <div class="col-md-12 advance">
                        <div id="filter-panel" class="collapse filter-panel">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <form class="form-inline" role="form">
                                        <div class="form-group col-md-12 rech">
                                            <label class="filter-col" style="margin-right:0;" for="">RECHERCHER :</label>
                                            <input type="text" class="form-control">
                                        </div>
                                        <div class="clearfix"></div> 

                                        <div class="form-group col-md-4">
                                            <label class="filter-col" style="margin-right:0;" for="">Localisaton :</label>
                                            <select class="form-control">
                                                <option value="">Antananirvo</option>
                                                <option value="">Antomboho</option>
                                                <option value="">Antananirvo</option>
                                                <option selected="selected" value="Antomboho">Antomboho</option>
                                                <option value="">Antananirvo</option>
                                                <option value="">Antananirvo</option>
                                            </select> 

                                            <div class="checkbox" style="margin-left:10px; margin-right:10px; width:100%;">
                                                <label class="filter-col" style="margin-right:0;" for="">Class :</label>
                                                <label><input type="checkbox"> Female</label>
                                                <label><input type="checkbox"> Male</label>
                                            </div> 
                                        </div>

                                        <div class="form-group col-md-4">    
                                            <div class="checkbox" style="margin-left:10px; margin-right:10px; width:100%;">
                                                <label class="filter-col" style="margin-right:0;" for="">Species :</label>
                                                <label><input type="checkbox"> Female</label>
                                                <label><input type="checkbox"> Male</label>
                                            </div>

                                            <div class="checkbox" style="margin-left:10px; margin-right:10px; width:100%;">
                                                <label class="filter-col" style="margin-right:0;" for="">Class :</label>
                                                <label><input type="checkbox"> Female</label>
                                                <label><input type="checkbox"> Male</label>
                                            </div>
                                        </div>

                                        <div class="form-group  col-md-4">    
                                            <div class="checkbox" style="margin-left:10px; margin-right:10px; width:100%;">
                                                <label class="filter-col" style="margin-right:0;" for="">Kingdom :</label>
                                                <label><input type="checkbox"> Female</label>
                                                <label><input type="checkbox"> Male</label>
                                            </div>

                                            <div class="checkbox" style="margin-left:10px; margin-right:10px; width:100%;">
                                                <label class="filter-col" style="margin-right:0;" for="">Class :</label>
                                                <label><input type="checkbox"> Female</label>
                                                <label><input type="checkbox"> Male</label>
                                            </div>
                                        </div>

                                    </form>
                                </div>
                            </div>
                        </div>	
                    </div>
                    <!--                    <button type="button" class="btn btn-primary" data-toggle="collapse" data-target="#filter-panel">-->
                    <!--                    <button type="button" class="btn btn-primary" onclick="window.history.back();">
                                            &nbsp;Page précédent
                                        </button>
                                        <p>&nbsp;</p>-->
                </div>

                <div class="clearfix"></div>

                <div class="col-md-4">
                    <div class="list-group panel">



                        <div class="panel-heading">
                            <h6 class="grid-title" align="left"><i class="fa fa-filter"></i> FILTRER :</h6>
                            <div class="clearfix"></div>
                            <div class="tabb">
                                <!-- Tabs -->
                                <ul class="nav panel-tabs">
                                    <li class="active"><a href="#carto" data-toggle="tab">Carto</a></li>
                                    <li><a href="#modele" data-toggle="tab">Modèle</a></li>
                                    <li><a href="#tendance" data-toggle="tab">Tendance de population</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="panel-body">
                            <div class="tab-content">
                                <div class="tab-pane active" id="carto">
                                    <a href="#carto" class="list-group-item list-group-item strong" style="background: #74ac00;" data-toggle="collapse" data-parent="#MainMenu"><i class="fa fa-map-pin"></i> Species explorer  &nbsp;<i class="fa fa-caret-down"></i></a>
                                    <div class="list-group-submenu" id="carto">
                                        <!--<a ng-repeat="famille in familles" href="#"  class="list-group-item">-->
                                        <div style=" margin-left: 5px;" class="row" ng-repeat="famille in familles" class="list-group-item">
                                            <input id="{{famille}}" type="checkbox" name="valeur[]" value="typeFamille-{{famille}}" ng-click="getGenre(famille)" >  {{famille}}
                                            <ul style="list-style-type:none; margin-left: 5px;" id="genre-{{famille}}"></ul>
                                        </div>
                                        <button style=" margin-bottom: 5px; color: white;" type="button" class="btn btn-primary form-control" onclick="addMarkersTaxonomi();">
                                            Afficher
                                        </button>                                        
                                    </div>                                    

                                    <a href="#lieu" class="list-group-item list-group-item strong" style="background: #74ac00;" data-toggle="collapse" data-parent="#MainMenu"><i class="fa fa-map"></i> Localisation <i class="fa fa-caret-down"></i></a>
                                    <div class="collapse list-group-submenu" id="lieu">
                                        <a class="list-group-item">
                                            <input id="pac-input" class="controls form-control" type="text" placeholder="Entrer le lieu a localiser">
                                        </a>
                                    </div>

                                    <a href="#searchglobal" class="list-group-item list-group-item strong" style="background: #74ac00;" data-toggle="collapse" data-parent="#MainMenu"><i class="fa fa-map"></i> Recherche globale <i class="fa fa-caret-down"></i></a>
                                    <div class="collapse list-group-submenu" id="searchglobal">
                                        <a class="list-group-item">
                                            <!--<input class="controls form-control" type="text" placeholder="Saisir la recherche">-->
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <input class="controls form-control" id="rechercheGlobale" type="text" placeholder="Saisir la recherche">
                                                    <span class="input-group-btn">
                                                        <button style="max-height: 25px; padding-top: 3px;" onclick="rechercheGlobale()" class="btn btn-primary btn-success" type="button"><i class="fa fa-search"></i></button>
                                                    </span>
                                                </div>
                                            </div>
                                        </a>
                                    </div>

                                </div>

                                <div class="tab-pane" id="modele">
                                    <a href="#mod" class="list-group-item list-group-item strong" style="background: #74ac00;" data-toggle="collapse"><i class="fa fa-map-pin"></i> Liste modèles  &nbsp;<i class="fa fa-caret-down"></i></a>
                                    <button style="width: 100%;" class="btn" onclick="resetMap()">reset</button>
                                    <div class="list-group-submenu" id="mod">
                                        <div style=" margin-left: 5px;" class="row" class="list-group-item">
                                            <label style="display: block; font-weight: 400;" ng-repeat="modele in modeles">
                                                <input id="{{modele.id}}" name="md" type="radio" ng-click="changeLastUrlOverlay(modele.url)">  {{modele.name}}
                                            </label><br>
                                        </div>
                                    </div>
                                    <!-- BEGIN PAGINATION -->
                                    <ul class="pagination" style="margin: 0px;">
                                        <li class="" id="previous"><a href="#" ng-click="rechercher(1)">«</a></li>                                        
                                        <li><a href="#" ng-click="rechercher(temp)" ng-repeat="temp in pages">{{temp}}</a></li>
                                        <li><a href="#"ng-click="rechercherFin()" id="next">»</a></li>
                                        <input type="hidden" id="pageFin">
                                    </ul>
                                    <!-- END PAGINATION -->
                                </div>                            
                                <div class="tab-pane" id="tendance">
                                    pop
                                </div>

                            </div>
                        </div>


                    </div>

                </div>
                <style>
                    #map {
                        height: 550px;
                        width: 100%;                        
                    }
                </style>
                <div class="col-md-8">
                    <div id="map" ></div>
                </div>

                <div class="clearfix"></div>
            </div>
        </div>
        <!-- End Contenu -->

    </section>
    <!-- end taxonomie -->

</main>

<script>
    var map;
    var markers;
    var markersGlobal;
    var markersSearch;
//    var markersPolygone;
    var ctr;

    function initMap() {
        var centre = {lat: -18.9136800, lng: 47.5361300};
        var mark = {lat: -18.9136800, lng: 47.5361300};
        map = new google.maps.Map(document.getElementById('map'), {
            zoom: 6,
            center: centre
        });
        var marker = new google.maps.Marker({
            position: mark,
            map: map
        });
        marker.setVisible(false);

        // markers pour tout type de recherche
        markers = [];
        markersGlobal = [];
        markersSearch = [];
//        markersPolygone = [];

        var input = (document.getElementById('pac-input'));

        // Autocomplete pour les recherches de localisation
        var autocomplete = new google.maps.places.Autocomplete(input);
        autocomplete.bindTo('bounds', map);
        autocomplete.addListener('place_changed', function () {
            marker.setVisible(false);
            var place = autocomplete.getPlace();
            if (!place.geometry) {
                window.alert("Google map ne parvient pas a trouver l'endroit nomé : '" + place.name + "'");
                return;
            }

            // If the place has a geometry, then present it on a map.
            if (place.geometry.viewport) {
                map.fitBounds(place.geometry.viewport);
            } else {
                map.setCenter(place.geometry.location);
                ctr = place.geometry.location;
                map.setZoom(10); // Why 10? Because it looks good.
            }
            marker.setIcon(/** @type {google.maps.Icon} */({
                url: place.icon,
                size: new google.maps.Size(71, 71),
                origin: new google.maps.Point(0, 0),
                anchor: new google.maps.Point(17, 34),
                scaledSize: new google.maps.Size(35, 35)
            }));
            marker.setPosition(place.geometry.location);
            marker.setVisible(true);
            var address = '';
            if (place.address_components) {
                address = [
                    (place.address_components[0] && place.address_components[0].short_name || ''),
                    (place.address_components[1] && place.address_components[1].short_name || ''),
                    (place.address_components[2] && place.address_components[2].short_name || '')
                ].join(' ');
            }
        });

        /*Drawing polygone*/
        var drawingManager = new google.maps.drawing.DrawingManager({
            drawingControl: true,
            drawingControlOptions: {
                position: google.maps.ControlPosition.TOP_CENTER,
                drawingModes: ['polygon']
            }
        });
        drawingManager.setMap(map);

        google.maps.event.addListener(drawingManager, 'polygoncomplete', function (polygon) {
            var coordinates = (polygon.getPath().getArray());
            var latitude = "latitude=";
            var longitude = "longitude=";
            for (var i = 0; i < coordinates.length; i++) {
                if (i === coordinates.length - 1) {
                    latitude += coordinates[i].lat();
                    longitude += coordinates[i].lng();
                } else {
                    latitude += coordinates[i].lat() + '&latitude=';
                    longitude += coordinates[i].lng() + '&longitude=';
                }
            }
            
            var markersPolygone = [];
            getObservationFromDrawnPolygone(latitude, longitude, markersPolygone);
            google.maps.event.addListener(polygon, 'click', function (event) {
                clearMultipleMarker(markersPolygone);
                polygon.setMap(null);
            });
        });
    }
    
    // Ajout marker //
    function addOneMarker(mark, cible) {
        var marker = new google.maps.Marker({
            position: {lat: parseFloat(mark.decimallatitude), lng: parseFloat(mark.decimallongitude)},
            map: map
        });
        var infowindow = new google.maps.InfoWindow();
        infowindow.setContent('<div><a href="detailLemurien?id=' + mark.id + '"><strong>' + mark.scientificname + '</strong></a><br> <table><tr><td>Id :</td><td style="padding-left:5px;">' + mark.id + '</td></tr><tr><td>Recorded by :</td><td style="padding-left:5px;">' + mark.recordedby + '</td></tr><tr><td>Decimal Latitude :</td><td style="padding-left:5px;">' + mark.decimallatitude + '</td></tr><tr><td>Decimal Longitude :</td><td style="padding-left:5px;">' + mark.decimallongitude + '</td></tr><tr><td>Locality :</td><td style="padding-left:5px;">' + mark.locality + '</td></tr><tr><td>Year :</td><td style="padding-left:5px;">' + mark.dwcyear + '</td></tr></table>');
        cible.push(marker);
        map.setCenter(ctr);
        google.maps.event.addListener(marker, 'click', function () {
            infowindow.open(map, this);
        });
    }

    function addMultipleMarker(mark, cible) {
        clearMultipleMarker(cible);
        table = [];
        for (var i = 0; i < mark.length; i++) {
            addOneMarker(mark[i], cible);
        }
    }

    function addMultipleMarker(mark, cible, withoutClear) {
        table = [];
        for (var i = 0; i < mark.length; i++) {
            addOneMarker(mark[i], cible);
        }
    }

    function clearMultipleMarker(cible) {
        hideMultipleMarker(cible);
        cible = [];
    }

    function hideMultipleMarker(cible) {
        setMapMultipleMarker(cible, null);
    }

    function setMapMultipleMarker(cible, map) {
        for (var i = 0; i < cible.length; i++) {
            cible[i].setMap(map);
        }
    }    
    // Fin Ajout marker //


    // GetObservationFromDrawnPolygone
    function getObservationFromDrawnPolygone(lat, lng, cible) {
        $.ajax({
            method: 'GET',
            url: 'observationFromPolygone?' + lat + '&' + lng,
            dataType: 'json',
            success: function (mark) {
                addMultipleMarker(mark, cible, 'yes');
            }
        });
    }    

    function addMarkersTaxonomi() {
        var col = $('[name="espece[]"]');
        clearMultipleMarker(markers);
        for (var i = 0; i < col.length; i++) {
            if (col[i].checked == true) {
                $.ajax({
                    method: 'POST',
                    url: 'findByespeceDwcPaginatedSearch?espece=' + col[i].value + '&validation=' + -999 + '&validationMine=' + -999 + '&etat[]=' + '' + '&page=' + -1,
                    dataType: 'json',
                    success: function (mark) {
                        for (var i = 0; i < mark.length; i++) {
                            addOneMarker(mark[i].dwc, markers);
                        }
                    }
                });
            }
        }
    }

    // Fonction pour chercher les espèces pour l'explorateur à gauche
    function getEspece(famille, genre) {
        if (document.getElementById("typeGenre-famille-" + famille + "-genre-" + genre).checked) {
            $.ajax({
                url: 'getEspece?genre=' + genre,
                dataType: 'json',
                success: function (json) {
                    var option = "";
                    for (var i = 0; i < json.length; i++) {
                        option = option + "<li>" + '<input name="espece[]" type="checkbox" value="' + json[i] + '" >';
                        option = option + json[i];
                        option = option + "</li>";
                    }
                    $("#espece-" + genre).append(option).trigger('change');
                }
            });
        } else {
            $("#espece-" + genre).html('<ul id="espece-' + genre + '"></ul>');
        }
    }

    // Fonctions pour les recherches globales
    function rechercheGlobale() {
        var champ = $('#rechercheGlobale').val();
        rechercheGlobaleArg(champ);
    }

    function rechercheGlobaleArg(arg) {
        $.ajax({
            url: 'rechercherEspeceDcw?champ=' + arg,
            dataType: 'json',
            success: function (json) {
                if (json.etat == true) {
                    addMultipleMarker(json.dwc, markersGlobal);
                } else
                    clearMultipleMarker(markersGlobal);
            }
        });
    }
    // Avec pagination
    function rechercheGlobalePaginee(champ, page, limite) {
        $.ajax({
            url: 'rechercherEspeceDcwPaginee?champ=' + champ + '&page=' + page + '&limite=' + limite,
            dataType: 'json',
            success: function (json) {
                if (json.etat == true) {
                    addMarkersGlobal(json.dwc);
                } else
                    clearMarkersGlobal();
            }
        });
    }

    // Fonction pour recherche multicritère
    function rechercheSearch() {
        var etat = $('[name="etat[]"]');
        var state = [];
        var etatS = "";
        var j = 0;
        for (var i = 0; i < etat.length; i++) {
            if (etat[i].checked == true) {
                state[j] = etat[i].value;
                j++;
                etatS += etat[i].value;
                if (i > 0) {
                    etatS += "&etat[]=" + etat[i].value;
                }
            }
        }
        var validationMine = parseInt($('[name="validationMine"]').val());
        console.log(validationMine);
        if (isNaN(validationMine))
            validationMine = -999;

        var formData = {
            'validation': parseInt($('select[name=validation]').val()),
            'etat': state,
            'validationMine': validationMine,
            'espece': $('input[name=espece]').val()
        };
        var dta = "validation=" + parseInt($('select[name=validation]').val()) + "&etat[]=" + state + "&validationMine=" + validationMine + "&espece=" + $('input[name=espece]').val();
        console.log(dta);
        $.ajax({
            method: 'GET',
            data: dta,
            url: 'findDwcMulticritereGet',
            success: function (json) {
                addMultipleMarker(json, markersSearch);
            }
        });
    }
</script>
<script src="<c:url value="https://maps.googleapis.com/maps/api/js?key=AIzaSyAJDf3t9NTT8GXrMdlSvbLTxVLvTdUXc20&libraries=places,drawing&callback=initMap"/>"></script>
<script>
    /**
     * Overlay
     */

    overlay = null;
    // Hide overlay
    function resetMap() {
        $('[name=md]').prop('checked', false);
        overlay.setMap(null);
    }
    // Show overlay
    function addOverlay(image) {
        // bounds for all png from lemursportal webservice
        var bounds = new google.maps.LatLngBounds(
                new google.maps.LatLng(-31.95216223, 33.75),
                new google.maps.LatLng(-11.17840187, 56.25));
        if (overlay != null) {
            overlay.setMap(null);
        }
        overlay = new ModelOverlay(bounds, image, map);
    }
    ModelOverlay.prototype = new google.maps.OverlayView();

    /** @constructor */
    function ModelOverlay(bounds, image, map) {

        // Initialize all properties.
        this.bounds_ = bounds;
        this.image_ = image;
        this.map_ = map;

        // Define a property to hold the image's div. We'll
        // actually create this div upon receipt of the onAdd()
        // method so we'll leave it null for now.
        this.div_ = null;

        // Explicitly call setMap on this overlay.
        this.setMap(map);
    }

    // onAdd is called when the map's panes are ready and the overlay has been
    // added to the map.
    ModelOverlay.prototype.onAdd = function () {

        var div = document.createElement('div');
        div.style.borderStyle = 'none';
        div.style.borderWidth = '0px';
        div.style.position = 'absolute';

        // Create the img element and attach it to the div.
        var img = document.createElement('img');
        img.src = this.image_;
        img.style.width = '100%';
        img.style.height = '100%';
        img.style.position = 'absolute';
        img.style.opacity = '0.75';
        div.appendChild(img);

        this.div_ = div;

        // Add the element to the "overlayLayer" pane.
        var panes = this.getPanes();
        panes.overlayLayer.appendChild(div);
    };

    ModelOverlay.prototype.draw = function () {

        // We use the south-west and north-east
        // coordinates of the overlay to peg it to the correct position and size.
        // To do this, we need to retrieve the projection from the overlay.
        var overlayProjection = this.getProjection();

        // Retrieve the south-west and north-east coordinates of this overlay
        // in LatLngs and convert them to pixel coordinates.
        // We'll use these coordinates to resize the div.
        var sw = overlayProjection.fromLatLngToDivPixel(this.bounds_.getSouthWest());
        var ne = overlayProjection.fromLatLngToDivPixel(this.bounds_.getNorthEast());

        // Resize the image's div to fit the indicated dimensions.
        var div = this.div_;
        div.style.left = sw.x + 'px';
        div.style.top = ne.y + 'px';
        div.style.width = (ne.x - sw.x) + 'px';
        div.style.height = (sw.y - ne.y) + 'px';
    };

    // The onRemove() method will be called automatically from the API if
    // we ever set the overlay's map property to 'null'.
    ModelOverlay.prototype.onRemove = function () {
        this.div_.parentNode.removeChild(this.div_);
        this.div_ = null;
    };
</script>
<script src="<c:url value="/resources/assets/js/angular.js"/>"></script>
<script src="<c:url value="/resources/assets/js/appconfig.js"/>"></script>
<script src="<c:url value="/resources/assets/js/controller/visualisationcontroller.js"/>"></script>
<jsp:include page="/WEB-INF/inc/footer.jsp"/>  