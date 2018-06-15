<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/inc/header.jsp"/>  
<main class="site-content" role="main" ng-controller="darwin">

    <!-- taxonomie -->
    <section id="taxonomie">
        <div class="banner-interieur" style="background:url(/lemurs/resources/assets/img/parallax/fexpert.jpg) no-repeat center center;">
            <div class="container" style="margin-top: 5%;">
                <div class="col-md-6 col-md-offset-3">
                    <!-- Search Form -->
                    <form role="form">
                        <!-- Search Field -->
                        <div class="row search-header">
                            <h4 class="text-left">Rechercher un espèce</h4>
                            <div class="form-group">
                                <div class="input-group">
                                    <input class="form-control" type="text" name="search" placeholder="Nom scientifique de l'espèce" required/>
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
        <div class="visu">
            <div class="container">
                <h1 class="titre-page">Visualisation cartographique<span></span></h1>
                <div class="row" style="margin-top: 10px;">
                    <form class="col-md-12" style="float: right; max-width: 100%;" id="form-search">
                        <!-- Search Field -->                                                    
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
                </div>
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
                    <button type="button" class="btn btn-primary" onclick="window.history.back();">
                        &nbsp;Page précédent
                    </button>
                    <p>&nbsp;</p>
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
                                        <button style=" margin-bottom: 5px; color: white;" type="button" class="btn btn-primary form-control" onclick="addMarkers2();">
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
                                    modele
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
<script src="<c:url value="/resources/assets/js/angular.js"/>"></script>
<script src="<c:url value="/resources/assets/js/appconfig.js"/>"></script>
<script src="<c:url value="/resources/assets/js/controller/visualisationcontroller.js"/>"></script>

<script>
                                                            window.onload = function () {
                                                                rechercheGlobalePaginee("a", 1, 10);
                                                            };

                                                            var map;
                                                            var markers;
                                                            var markersGlobal;
                                                            var markersSearch;
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
                                                                        map.setZoom(13); // Why 17? Because it looks good.
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
                                                            }

                                                            // Mettre l'attribut map sur les markers
                                                            function setMapOnAll(map) {
                                                                for (var i = 0; i < markers.length; i++) {
                                                                    markers[i].setMap(map);
                                                                }
                                                            }

                                                            function setMapOnAllGlobal(map) {
                                                                for (var i = 0; i < markersGlobal.length; i++) {
                                                                    markersGlobal[i].setMap(map);
                                                                }
                                                            }

                                                            function setMapOnAllSearch(map) {
                                                                for (var i = 0; i < markersSearch.length; i++) {
                                                                    markersSearch[i].setMap(map);
                                                                }
                                                            }

                                                            // Effacer les markers : mettre l'attribut map du marker à null
                                                            function clearMarkers() {
                                                                setMapOnAll(null);
                                                            }
                                                            ;
                                                            function clearMarkersGlobal() {
                                                                setMapOnAllGlobal(null);
                                                            }
                                                            ;
                                                            function clearMarkersSearch() {
                                                                setMapOnAllSearch(null);
                                                            }
                                                            ;

                                                            // Ajouter les markers pour le recherche globale
                                                            function addMarkerGlobal(mark) {
                                                                var marker = new google.maps.Marker({
                                                                    position: {lat: parseFloat(mark.decimallatitude), lng: parseFloat(mark.decimallongitude)},
                                                                    map: map
                                                                });
                                                                var infowindow = new google.maps.InfoWindow();
                                                                infowindow.setContent('<div><strong>' + mark.scientificname + '</strong><br>');
//                                                                infowindow.open(map, marker);
                                                                markersGlobal.push(marker);
                                                                map.setCenter(ctr);
                                                                // Ajouter event on click to the marker
                                                                google.maps.event.addListener(marker, 'click', function () {
                                                                    infowindow.open(map, this);
                                                                });
                                                            };
                                                            
                                                            function addMarkersGlobal(mark) {
                                                                clearMarkersGlobal();
                                                                table = [];
                                                                for (var i = 0; i < mark.length; i++) {
                                                                    addMarkerGlobal(mark[i]);
                                                                }
                                                            };                                                            

                                                            // Ajouter les markers pour les recherches multicritères
                                                            function addMarkerSearch(mark) {
//                                                                console.log(mark);
                                                                var marker = new google.maps.Marker({
                                                                    position: {lat: parseFloat(mark.decimallatitude), lng: parseFloat(mark.decimallongitude)},
                                                                    map: map
                                                                });
                                                                var infowindow = new google.maps.InfoWindow();
                                                                infowindow.setContent('<div><strong>' + mark.scientificname + '</strong><br>');
//                                                                infowindow.open(map, marker);
                                                                markersSearch.push(marker);
                                                                map.setCenter(ctr);
                                                                // Ajouter event on click to the marker
                                                                google.maps.event.addListener(marker, 'click', function () {
                                                                    infowindow.open(map, this);
                                                                });
                                                            };
                                                            
                                                            function addMarkersSearch(mark) {
                                                                clearMarkersSearch();
                                                                table = [];
                                                                for (var i = 0; i < mark.length; i++) {
                                                                    addMarkerSearch(mark[i]);
                                                                }
                                                            };
                                                            
                                                            // Ancienne version de l'ajout des markers via l'explorateur
//                                                            function addMarkers() {
//                                                                var col = $('[name="espece[]"]');
//                                                                clearMarkers();
//                                                                markers = [];
//                                                                for (var i = 0; i < col.length; i++) {
//                                                                    if (col[i].checked == true) {
//                                                                        var temp = col[i].value;
//                                                                        var latlong = temp.split(",");
//                                                                        var marker = new google.maps.Marker({
//                                                                            position: {lat: parseFloat(latlong[0]), lng: parseFloat(latlong[1])},
//                                                                            map: map
//                                                                        });
//                                                                        var infowindow = new google.maps.InfoWindow();
//                                                                        infowindow.setContent('<div><strong>' + latlong[2] + '</strong><br>');
//                                                                        infowindow.open(map, marker);
//                                                                        markers.push(marker);
//                                                                        map.setCenter(ctr);
//                                                                    }
//                                                                }
//                                                            }
//                                                            ;

                                                            // Version recent pour ajouter les markers depuis l'explorateur à gauche
//                                                            function afficherInfo(inc) {
//                                                                console.log(infowindowExploration[inc].open(map, markers[inc]));
//                                                            }
                                                            function addMarkers2() {
                                                                var col = $('[name="espece[]"]');
                                                                clearMarkers();
                                                                infowindowExploration = [];
                                                                markers = [];
                                                                for (var i = 0; i < col.length; i++) {
                                                                    if (col[i].checked == true) {
                                                                        $.ajax({
                                                                            method: 'POST',
                                                                            url: 'findByespeceDwcPaginatedSearch?espece=' + col[i].value + '&validation=' + -999 + '&validationMine=' + -999 + '&etat[]=' + '' + '&page=' + -1,
                                                                            dataType: 'json',
                                                                            success: function (mark) {
                                                                                for (var i = 0; i < mark.length; i++) {
                                                                                    var marker = new google.maps.Marker({
                                                                                        position: {lat: parseFloat(mark[i].dwc.decimallatitude), lng: parseFloat(mark[i].dwc.decimallongitude)},
                                                                                        map: map
                                                                                    });
                                                                                    var infowindow = new google.maps.InfoWindow();
                                                                                    infowindow.setContent('<div><strong>' + mark[i].dwc.scientificname + '</strong><br>');
                                                                                    infowindowExploration.push(infowindow);
                                                                                    markers.push(marker);
                                                                                    map.setCenter(ctr);
                                                                                    // Ajouter event on click to the marker
                                                                                    google.maps.event.addListener(marker, 'click', function () {
                                                                                        infowindow.open(map, this);
                                                                                    });
                                                                                }
//                                                                                for (var i = 0; i < markers.length; i++) {    
//                                                                                    afficherInfo(i);
//                                                                                    google.maps.event.addListener(markers[i], 'click', function() {afficherInfo(i);});
//                                                                                }
                                                                            }
                                                                        });
                                                                    }
                                                                }
                                                            };

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
                                                            };

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
                                                                            addMarkersGlobal(json.dwc);
                                                                        } else
                                                                            clearMarkersGlobal();
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
                                                                        addMarkersSearch(json);
                                                                    }
                                                                });
                                                            }
</script>
<jsp:include page="/WEB-INF/inc/footer.jsp"/>  