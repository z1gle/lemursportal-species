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

    <!--Check box for validation style-->
    <style>
        .checkbox {
            padding-left: 20px; }
        .checkbox label {
            display: inline-block;
            position: relative;
            padding-left: 5px; }
        .checkbox label::before {
            content: "";
            display: inline-block;
            position: absolute;
            width: 17px;
            height: 17px;
            left: 0;
            margin-left: -20px;
            border: 1px solid #cccccc;
            border-radius: 3px;
            background-color: #fff;
            -webkit-transition: border 0.15s ease-in-out, color 0.15s ease-in-out;
            -o-transition: border 0.15s ease-in-out, color 0.15s ease-in-out;
            transition: border 0.15s ease-in-out, color 0.15s ease-in-out; }
        .checkbox label::after {
            display: inline-block;
            position: absolute;
            width: 16px;
            height: 16px;
            left: 0;
            top: 0;
            margin-left: -20px;
            padding-left: 3px;
            padding-top: 1px;
            font-size: 11px;
            color: #555555; }
        .checkbox input[type="checkbox"] {
            opacity: 0; }
        .checkbox input[type="checkbox"]:focus + label::before {
            outline: thin dotted;
            outline: 5px auto -webkit-focus-ring-color;
            outline-offset: -2px; }
        .checkbox input[type="checkbox"]:checked + label::after {
            font-family: 'FontAwesome';
            content: "\f00c"; }
        .checkbox input[type="checkbox"]:disabled + label {
            opacity: 0.65; }
        .checkbox input[type="checkbox"]:disabled + label::before {
            background-color: #eeeeee;
            cursor: not-allowed; }
        .checkbox.checkbox-circle label::before {
            border-radius: 50%; }
        .checkbox.checkbox-inline {
            margin-top: 0; }        
        .checkbox-info input[type="checkbox"]:checked + label::before {
            background-color: #A18029;
            border-color: #A18029; }
        .checkbox-info input[type="checkbox"]:checked + label::after {
            color: #fff; }        
        .chk label::after {
            display: inline-block;
            position: absolute;
            width: 16px;
            height: 16px;
            left: 0;
            top: -3px;
            margin-left: -20px;
            padding-left: 3px;
            padding-top: 1px;
            font-size: 11px;
            color: #555555;
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
                                    <a href="#" title="search" style="padding: 0px;height: 20px;float: left;margin-left: 6px;" onclick="rechercheSearch()" class="btn"><i style="color: darkgrey" class="fa fa-search"></i></a>                                    
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
                    <h1 style="margin-left: 10px; font-size:  14px;font-weight:  600;width:  100%;float: left;margin-top: 9px; color: #a18029;"><spring:message code="map.title"/> |</h1>                    
                    <!-- End Stat -->                    
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
                </div>

                <div class="clearfix"></div>

                <div class="col-md-4">
                    <div class="list-group panel">

                        <div class="panel-heading">
                            <h6 class="grid-title" align="left"><i class="fa fa-filter"></i> <spring:message code="map.filter.label"/> :</h6>
                            <div class="clearfix"></div>
                            <div class="tabb">
                                <!-- Tabs -->
                                <ul class="nav panel-tabs">
                                    <li class="active"><a href="#carto" data-toggle="tab"><spring:message code="map.filter.map"/></a></li>
                                    <li><a href="#modele" data-toggle="tab"><spring:message code="map.filter.model"/></a></li>
                                    <li><a href="#tendance" data-toggle="tab"><spring:message code="map.filter.trend"/></a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="panel-body">
                            <div class="tab-content">
                                <div class="tab-pane active" id="carto">
                                    <c:if test="${utilisateur.nom!=''&&utilisateur.nom!=null}">
                                        <%
                                            Integer expert = ((Integer) request.getAttribute("expert"));
                                            if (expert == 0) {
                                        %>
                                        <a href="#validation-body" class="list-group-item list-group-item strong" style="background: #74ac00;" data-toggle="collapse" data-parent="#MainMenu"><i class="fa fa-map-pin"></i> Validation &nbsp;<i class="fa fa-caret-down"></i></a>
                                        <div class="list-group-submenu" id="validation-body">
                                            <a class="list-group-item">
                                                <select id="status-for-validation" class="form-control">
                                                    <option value="-1"><spring:message code="data.status.all_awaiting_review"/></option>
                                                    <option value="1"><spring:message code="data.status.all_reliable_reviews_data"/></option>
                                                    <option value="0"><spring:message code="data.status.all_questionnable_reviews_data"/></option>
                                                </select>
                                            </a>
                                            <a class="list-group-item">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <input class="controls form-control" id="search-for-validation" type="text" placeholder="Saisir la recherche">
                                                        <span class="input-group-btn">
                                                            <button style="max-height: 25px; padding-top: 3px;" onclick="searchForValidation()" class="btn btn-primary btn-success" type="button"><i class="fa fa-search"></i></button>
                                                        </span>
                                                    </div>
                                                </div>
                                            </a>
                                        </div>
                                        <%
                                            }
                                        %>
                                    </c:if>
                                    <a href="#carto" class="list-group-item list-group-item strong" style="background: #74ac00;" data-toggle="collapse" data-parent="#MainMenu"><i class="fa fa-map-pin"></i> <spring:message code="map.cartography.s-explorer"/>  &nbsp;<i class="fa fa-caret-down"></i></a>
                                    <div class="list-group-submenu" id="carto">                                        
                                        <div style=" margin-left: 5px;" class="row" ng-repeat="famille in familles" class="list-group-item">
                                            <input id="{{famille}}" type="checkbox" name="valeur[]" value="typeFamille-{{famille}}" ng-click="getGenre(famille)" >  {{famille}}
                                            <ul style="list-style-type:none; margin-left: 5px;" id="genre-{{famille}}"></ul>
                                        </div>
                                        <button style=" margin-bottom: 5px; color: white;" type="button" class="btn btn-primary form-control" onclick="addMarkersTaxonomi();">
                                            <spring:message code="map.cartography.s-explorer.show"/>
                                        </button>                                        
                                    </div>

                                    <a href="#lieu" class="list-group-item list-group-item strong" style="background: #74ac00;" data-toggle="collapse" data-parent="#MainMenu"><i class="fa fa-map"></i> <spring:message code="map.cartography.location"/> <i class="fa fa-caret-down"></i></a>
                                    <div class="collapse list-group-submenu" id="lieu">
                                        <a class="list-group-item">
                                            <input id="pac-input" class="controls form-control" type="text" placeholder="Entrer le lieu a localiser">
                                        </a>
                                    </div>

                                    <a href="#searchglobal" class="list-group-item list-group-item strong" style="background: #74ac00;" data-toggle="collapse" data-parent="#MainMenu"><i class="fa fa-map"></i> <spring:message code="map.cartography.g-research"/> <i class="fa fa-caret-down"></i></a>
                                    <div class="collapse list-group-submenu" id="searchglobal">
                                        <a class="list-group-item">                                            
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
                                    <input type="text" placeholder="<spring:message code="species.label.search"/>" class="form-control" ng-change="sort()" ng-model="filtreModele">
                                    <div class="list-group-submenu" id="mod" style="max-height: 320px; overflow-x: hidden; overflow-y: auto;">
                                        <div style=" margin-left: 5px;" class="row" class="list-group-item">
                                            <label style="display: block; font-weight: 400;" ng-repeat="modele in modelesSorted">
                                                <input id="{{modele.id}}" name="md" type="radio" ng-click="changeLastUrlOverlay(modele.url)">  {{modele.name}}
                                            </label><br>
                                        </div>
                                    </div>
                                    <button style="width: 100%;" class="btn" onclick="resetMap()">reset</button>                                    
                                </div>                            
                                <div class="tab-pane" id="tendance">
                                    tendance de population
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
                    <div class="row">
                        <div class="col-md-11" style="padding-right: 0px">
                            <div id="map" ></div>
                            <div class="table-responsive" id="liste" style="display: none;">                        
                                <table class="table table-hover">
                                    <tbody id="table-list-dwc">
                                        <tr style="background-color: black; color: #deaa45; font-weight: 700;">
                                            <%
                                                if ((Integer) request.getAttribute("expert") == 0) {
                                            %>
                                            <td class="text-center" id="nocheckcheck"></td>
                                            <td class="number text-center" id="checkcheck" style="display: none;">
                                                <div class="checkbox checkbox-info checkbox-circle" style="margin-bottom: 0px; margin-top: 0px;">
                                                    <input id="checkAll" type="checkbox" onclick="checkAll()">
                                                    <label for="checkAll"></label>
                                                </div>
                                            </td>
                                            <%
                                                }
                                            %>
                                            <td class="number text-center">Id</td>
                                            <td class="text-center">Nom scientifique </td>
                                            <td class="text-center">Recorder by</td>
                                            <td class="text-center">Latitude</td>
                                            <td class="text-center">Longitude</td>                                        
                                            <td class="text-center">Locality</td>
                                            <td class="text-center">Year</td>                                            
                                        </tr>                                        
                                    </tbody>
                                </table>
                                <%if ((Integer) request.getAttribute("expert") == 0) {%>
                                <button onclick="showCommentairFirst();" style="float: right; margin-left: 2px;" class="btn btn-success">Questionnable</button>
                                <button onclick="validate(1);" style="float: right; background-color: #4CAF50!important;" class="btn btn-success">Valider</button>   
                                <%}%>
                            </div>
                        </div>
                        <div class="col-md-1">
                            <a class="btn" title="<spring:message code="map.map-area.icone.map"/>" onclick="mapMode();"><i class="fa fa-map-marker" style="width: 14px;"></i></a>
                            <a class="btn" title="<spring:message code="map.map-area.icone.list"/>" onclick="listMode();"><i class="fa fa-list"></i></a><br>                                                        
                            <a class="btn" title="<spring:message code="map.map-area.icone.shapefiles"/>" data-toggle='modal' data-target='#modal-shp'><i class="fa fa-area-chart" style="width: 14px;"></i></a><br>
                            <a class="btn" title="<spring:message code="map.map-area.icone.download"/>" onclick="downloadList();"><i class="fa fa-download"></i></a>
                        </div>
                    </div>
                </div>                
                <div class="clearfix"></div>                
            </div>
        </div>
        <!-- End Contenu -->

    </section>
    <div id='modal-shp' class='modal fade' role='dialog' style='display:none !important' tabindex="-1">
        <div class='modal-dialog'>
            <div class='modal-content'>
                <div class="modal-header">
                    <button data-dismiss='modal' class='close' type='button'>x</button>
                    <h4 class="modal-title"><center>Shapefiles</center></h4>
                </div>
                <div class='modal-body'>
                    <div class='row'>
                        <div class='col-md-10 col-md-offset-1' style="max-height: 500px; overflow-y:  auto;">         
                            <div style=" margin-left: 5px;" class="row" ng-cloak ng-repeat="shapefile in shp" class="list-group-item">
                                <input id="shp-{{shapefile.id}}" type="checkbox" ng-click="getKmlFromShp(shapefile.id, shapefile.shapeTable)"> {{shapefile.shapeTable}}
                                <ul style="list-style-type:none; margin-left: 5px;" id="kml-{{shapefile.shapeTable}}"></ul>                                
                            </div>
                        </div>
                    </div>
                </div>
                <div class='modal-footer'>
                    <button type='button' class='btn btn-default btn-sm' data-dismiss='modal'>Annuler</button>
                    <button type='button' id="" onclick="showKml()" class='btn btn-success btn-sm' data-dismiss='modal'>Afficher</button>
                </div>
            </div>
        </div>
    </div>
    <!-- end taxonomie -->

    <div id='modal-ajout-confirmation-valide' class='modal fade' role='dialog' style='display:none !important' tabindex="-1">
        <div class='modal-dialog'>
            <div class='modal-content'>
                <div class="modal-header">
                    <button data-dismiss='modal' class='close' type='button'>x</button>
                    <h4 class="modal-title"><center>Confirmation</center></h4>
                </div>
                <div class='modal-body'>
                    <div class='row'>
                        <div class='col-md-10 col-md-offset-1'>                            
                            <div class="col-sm-12">
                                <label id="" class="control-label messageMod">L'observation N° # a déja été marqué comme # par #</label>
                                <label class="control-label">Voulez-vous continuer à modifier son status?</label>                                    
                            </div>                                    
                        </div>
                    </div>

                </div>
                <div class='modal-footer'>
                    <button type='button' class='btn btn-default btn-sm' onclick="continueValidate(1, 0)" data-dismiss='modal'><spring:message code="global.cancel"/></button>
                    <button type='button' class='btn btn-success btn-sm' onclick="continueValidate(1, 1)" data-dismiss='modal'>Valider</button>
                </div>
            </div>
        </div>
    </div>
    <div id='modal-ajout-confirmation-valide' class='modal fade' role='dialog' style='display:none !important' tabindex="-1">
        <div class='modal-dialog'>
            <div class='modal-content'>
                <div class="modal-header">
                    <button data-dismiss='modal' class='close' type='button'>x</button>
                    <h4 class="modal-title"><center>Confirmation</center></h4>
                </div>
                <div class='modal-body'>
                    <div class='row'>
                        <div class='col-md-10 col-md-offset-1'>                            
                            <div class="col-sm-12">
                                <label id="" class="control-label messageMod">L'observation N° # a déja été marqué comme # par #</label>
                                <label class="control-label">Voulez-vous continuer à modifier son status?</label>                                    
                            </div>                                    
                        </div>
                    </div>

                </div>
                <div class='modal-footer'>
                    <button type='button' class='btn btn-default btn-sm' onclick="continueValidate(1, 0)" data-dismiss='modal'><spring:message code="global.cancel"/></button>
                    <button type='button' class='btn btn-success btn-sm' onclick="continueValidate(1, 1)" data-dismiss='modal'>Valider</button>
                </div>
            </div>
        </div>
    </div>
    <div id='modal-ajout-confirmation-questionnable' class='modal fade' role='dialog' style='display:none !important' tabindex="-1">
        <div class='modal-dialog'>
            <div class='modal-content'>
                <div class="modal-header">
                    <button data-dismiss='modal' class='close' type='button'>x</button>
                    <h4 class="modal-title"><center>Confirmation</center></h4>
                </div>
                <div class='modal-body'>
                    <div class='row'>
                        <div class='col-md-10 col-md-offset-1'>                            
                            <div class="col-sm-12">
                                <label id="" class="control-label messageMod">L'observation N° # a déja été marqué comme # par #</label>
                                <label class="control-label">Voulez-vous continuer à modifier son status?</label>                                    
                            </div>                                    
                        </div>
                    </div>

                </div>
                <div class='modal-footer'>
                    <button type='button' class='btn btn-default btn-sm' onclick="continueValidate(0, 0)" data-dismiss='modal'><spring:message code="global.cancel"/></button>
                    <button type='button' class='btn btn-success btn-sm' onclick = 'continueValidate(0, 1)' data-dismiss='modal'>Valider</button>
                </div>
            </div>
        </div>
    </div>
    <div id='modal-ajout-commentaire-questionnable' class='modal fade' role='dialog' style='display:none !important' tabindex="-1">
        <div class='modal-dialog'>
            <div class='modal-content'>
                <div class="modal-header">
                    <button data-dismiss='modal' class='close' type='button'>x</button>
                    <h4 class="modal-title"><center>Commentaire</center></h4>
                </div>
                <div class='modal-body'>
                    <div class='row'>
                        <div class='col-md-10 col-md-offset-1'>                            
                            <div class="col-sm-12">
                                <textarea id="commentaires" class="form-control"></textarea>                                
                            </div>                                    
                        </div>
                    </div>
                </div>
                <div class='modal-footer'>
                    <button type='button' class='btn btn-default btn-sm' onclick="$('#commentaires').val('')" data-dismiss='modal'><spring:message code="global.cancel"/></button>
                    <button type='button' id="boutonQuestionnable" class='btn btn-success btn-sm' data-dismiss='modal'><spring:message code="global.continue"/>
                </div>
            </div>
        </div>
    </div>
</main>

<script>
    var map;
    var markers;
    var listDwc;
    var listDwcValidation;
    var listMarkersPolygone;
    var ctr;
    var kmls;
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
        listMarkersPolygone = [];
        listDwc = [];
        listDwcValidation = [];
        kmls = [];
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
            var dwcPolygone = [];
            getObservationFromDrawnPolygone(latitude, longitude, markersPolygone, dwcPolygone);
            google.maps.event.addListener(polygon, 'click', function (event) {
                hideMultipleMarker(markersPolygone);
                hideMultipleDwc(dwcPolygone);
                polygon.setMap(null);
                makeTable();
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

    function addMultipleMarkerClear(mark, cible) {
        clearMultipleMarker(cible);
        for (var i = 0; i < mark.length; i++) {
            addOneMarker(mark[i], cible);
        }
    }

    function addMultipleDwcClear(mark, cible) {
        clearMultipleDwc(cible);
        for (var i = 0; i < mark.length; i++) {
            cible.push(mark[i]);
        }
    }

    function addMultipleMarker(mark, cible, withoutClear) {
        for (var i = 0; i < mark.length; i++) {
            addOneMarker(mark[i], cible);
        }
    }

    function addMultipleDwc(mark, cible, withoutClear) {
        for (var i = 0; i < mark.length; i++) {
            cible.push(mark[i]);
        }
    }

    function clearMultipleMarker(cible) {
        hideMultipleMarker(cible);
        cible.length = 0;
    }

    function clearMultipleDwc(cible) {
        cible.length = 0;
    }

    function hideMultipleMarker(cible) {
        setMapMultipleMarker(cible, null);
    }

    function hideMultipleDwc(cible) {
        for (var i = 0; i < cible.length; i++) {
            cible[i].id = -999;
        }
    }

    function setMapMultipleMarker(cible, map) {
        for (var i = 0; i < cible.length; i++) {
            cible[i].setMap(map);
        }
    }
    // Fin Ajout marker //

    // Gestion kml et shp//
    function addOneKml(mark, cible) {
        var link = window.location.href;
        console.log(link);
        var linkRoot = link.substring(0, link.lastIndexOf('/') + 1);
        var ctaLayer = new google.maps.KmlLayer({
            url: linkRoot + mark.link,
            map: map
        });
        cible.push(ctaLayer);
    }

    function getUrlsForKml() {
        var gid = $('[name="gids-kml"]');
        if (gid.length == 0) {
            clearKMLs();
            return [];
        }
        var listShp = [];
        var listUrl = [];
        for (var i = 0; i < gid.length; i++) {
            if (gid[i].checked == true) {
                var detail = gid[i].id.split("!!");
                var shp = detail[0];
                var gids = detail[1];
                listShp.push({shp, gids});
            }
        }
        var indexUrl = 0;
        var baseUrl = 'kmls/overlay?tableName=';
        var tempShp = listShp[0].shp;
        listUrl.push(baseUrl + tempShp);
        for (var i = 0; i < listShp.length; i++) {
            if (listShp[i].shp != tempShp) {
                tempShp = listShp[i].shp;
                listUrl.push(baseUrl + tempShp);
                indexUrl++;
            }
            listUrl[indexUrl] = listUrl[indexUrl] + '&gids=' + listShp[i].gids;
        }
        return listUrl;
    }

    function getUrlsForKmlPlacemarks() {
        var gid = $('[name="gids-kml"]');
        if (gid.length == 0) {
            clearKMLs();
            return [];
        }
        var listShp = [];
        var listUrl = [];
        for (var i = 0; i < gid.length; i++) {
            if (gid[i].checked == true) {
                var detail = gid[i].id.split("!!");
                var shp = detail[0];
                var gids = detail[1];
                listShp.push({shp, gids});
            }
        }
        var indexUrl = 0;
        var baseUrl = 'kmls/placemarks?tableName=';
        var tempShp = listShp[0].shp;
        listUrl.push(baseUrl + tempShp);
        for (var i = 0; i < listShp.length; i++) {
            if (listShp[i].shp != tempShp) {
                tempShp = listShp[i].shp;
                listUrl.push(baseUrl + tempShp);
                indexUrl++;
            }
            listUrl[indexUrl] = listUrl[indexUrl] + '&gids=' + listShp[i].gids;
        }
        return listUrl;
    }

    function showOneKml(url, urlMarks, cible, dwcs) {
        console.log('debut');
        $.ajax({
            method: 'GET',
            url: url,
            dataType: 'json',
            success: function (mark) {
                console.log(mark);
                addOneKml(mark, kmls);
                getObservationFromKml(urlMarks, cible, dwcs);
            },
            error: function (error) {
                console.log(error);
            }
        });
        console.log('fin');
    }

    function showKml() {
        clearKMLs();
        var urls = getUrlsForKml();
        var urlPlacemarks = getUrlsForKmlPlacemarks();
        var markersKML = [];
        var dwcsKLM = [];
        for (var i = 0; i < urls.length; i++) {
            showOneKml(urls[i], urlPlacemarks[i], markersKML, dwcsKLM);
        }
    }

    function clearKMLs() {
        for (var i = 0; i < kmls.length; i++) {
            kmls[i].setMap(null);
        }
        clearMultipleDwc(kmls);
        clearMultipleDwc(listDwc);
        clearMultipleMarker(markers);
    }

    // GetObservationFromKml
    function getObservationFromKml(url, cible, dwcs) {
        $.ajax({
            method: 'GET',
            url: url,
            dataType: 'json',
            success: function (mark) {
                addMultipleMarker(mark, cible, 'yes');
                addMultipleDwc(mark, dwcs, 'yes');
                for (var i = 0; i < cible.length; i++) {
                    markers.push(cible[i]);
                    listDwc.push(dwcs[i]);
                }
                makeTable();
            }
        });
    }

    // Fin gestion KMl et shp

    // Traitement du tableau
    function makeTable() {
        if (listDwcValidation.length != 0) {
            makeTableForValidation();
        } else {
            console.log("call of makeTable()");
            $('.removable').remove();
            var body = '';
            for (var i = 0; i < listDwc.length; i++) {
                if (listDwc[i].id > -999) {
                    var row = '<tr class="removable">';
    <%
        if ((Integer) request.getAttribute("expert") == 0) {
    %>
                    row += '<td></td>';
    <%
        }
    %>
                    row += '<td>' + listDwc[i].id + '</td>';
                    row += '<td><a href="detailLemurien?id=' + listDwc[i].id + '"><strong>' + listDwc[i].scientificname + '</strong></a></td>';
                    row += '<td>' + listDwc[i].recordedby + '</td>';
                    row += '<td>' + listDwc[i].decimallatitude + '</td>';
                    row += '<td>' + listDwc[i].decimallongitude + '</td>';
                    row += '<td>' + listDwc[i].locality + '</td>';
                    row += '<td>' + listDwc[i].dwcyear + '</td>';
                    row += '</tr>';
                    body += row;
                }
            }
            $('#table-list-dwc').append(body);
        }
    }
    // Traitement du tableau
    function makeTableForValidation() {
        console.log('call of makeTableForValidation');
        $('.removable').remove();
        var body = '';
        for (var i = 0; i < listDwcValidation.length; i++) {
            if (listDwcValidation[i].dwc.id > -999) {
                var row = '<tr class="removable">';
                if (listDwcValidation[i].validation == 1) {
                    console.log(1);
                    row += '<td class="number text-center">';
                    row += '<div class="checkbox checkbox-info checkbox-circle" style="margin-bottom: 0px; margin-top: 0px;">';
                    row += '<input id="ckb' + listDwcValidation[i].dwc.id + '" name="dwc[]" value="' + listDwcValidation[i].dwc.id + '" type="checkbox">';
                    row += '<label for="ckb' + listDwcValidation[i].dwc.id + '"></label>';
                    row += '</div>';
                    row += '</td>';
                } else {
                    row += '<td></td>';
                    console.log(0);
                }
                row += '<td>' + listDwcValidation[i].dwc.id + '</td>';
                row += '<td><a href="detailLemurien?id=' + listDwcValidation[i].dwc.id + '"><strong>' + listDwcValidation[i].dwc.scientificname + '</strong></a></td>';
                row += '<td>' + listDwcValidation[i].dwc.recordedby + '</td>';
                row += '<td>' + listDwcValidation[i].dwc.decimallatitude + '</td>';
                row += '<td>' + listDwcValidation[i].dwc.decimallongitude + '</td>';
                row += '<td>' + listDwcValidation[i].dwc.locality + '</td>';
                row += '<td>' + listDwcValidation[i].dwc.dwcyear + '</td>';
                row += '</tr>';
                body += row;
            }
        }
        $('#table-list-dwc').append(body);
    }

    function downloadURI(uri, name) {
        var link = document.createElement("a");
        link.download = name;
        link.href = uri;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        delete link;
    }

    // Map mode and list mode
    function mapMode() {
        $('#liste').hide();
        $('#map').show();
    }
    function listMode() {
        $('#map').hide();
        makeTable();
        $('#liste').show();
    }
    function downloadList() {
        var ids = '';
        for (var i = 0; i < listDwc.length; i++) {
            ids += 'id=' + listDwc[i].id + '&';
        }
        ids += 'zch=y';
        downloadURI('downloadList?' + ids, 'list_observation_from_map.csv');
    }

    // GetObservationFromDrawnPolygone
    function getObservationFromDrawnPolygone(lat, lng, cible, dwcs) {
        $.ajax({
            method: 'GET',
            url: 'observationFromPolygone?' + lat + '&' + lng,
            dataType: 'json',
            success: function (mark) {
                addMultipleMarker(mark, cible, 'yes');
                addMultipleDwc(mark, dwcs, 'yes');
                for (var i = 0; i < cible.length; i++) {
                    markers.push(cible[i]);
                    listDwc.push(dwcs[i]);
                }
                makeTable();
            }
        });
    }

    function addMarkersTaxonomi() {
        var col = $('[name="espece[]"]');
        clearMultipleMarker(markers);
        clearMultipleDwc(listDwc);
        for (var i = 0; i < col.length; i++) {
            if (col[i].checked == true) {
                $.ajax({
                    method: 'POST',
                    url: 'findByespeceDwcPaginatedSearch?espece=' + col[i].value + '&validation=' + -999 + '&validationMine=' + -999 + '&etat[]=' + '' + '&page=' + -1,
                    dataType: 'json',
                    success: function (mark) {
                        for (var i = 0; i < mark.length; i++) {
                            addOneMarker(mark[i].dwc, markers);
                            listDwc.push(mark[i].dwc);
                        }
                        makeTable();
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
                    addMultipleMarkerClear(json.dwc, markers);
                    addMultipleDwcClear(json.dwc, listDwc);
                } else {
                    clearMultipleMarker(markers);
                    clearMultipleDwc(listDwc);
                }
                makeTable();
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
                addMultipleMarkerClear(json, markers);
                addMultipleDwcClear(json, listDwc);
                makeTable();
            }
        });
    }

    // search for validation
    function searchForValidation() {
        var validationExp = $('#status-for-validation').val();
        var searchExp = $('#search-for-validation').val();
        var data = {validationexpert: validationExp, champ: searchExp};

        $.ajax({
            url: 'findForValidationGlobal?page=-1',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            method: 'POST',
            dataType: 'json',
            data: JSON.stringify(data),
            success: function (mark) {
                listDwcValidation.length = 0;
                clearMultipleDwc(listDwc);
                clearMultipleMarker(markers);
                var pas = 0;
                for (var i = 0; i < mark.length; i++) {
                    if (mark[i].validation == 1) {
                        pas++;
                        addOneMarker(mark[i].dwc, markers);
                        listDwc.push(mark[i].dwc);
                        listDwcValidation.push(mark[i]);
                    }
                }
                if (pas > 0) {
                    $('#nocheckcheck').hide();
                    $('#checkcheck').show();
                } else {
                    $('#nocheckcheck').show();
                    $('#checkcheck').hide();
                }
                makeTableForValidation();
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
<script>
    /**
     * Validation
     * */

    function showModal(status) {
        if (status == 0)
            $("#modal-ajout-confirmation-questionnable").modal({backdrop: 'static'});
        else
            $("#modal-ajout-confirmation-valide").modal({backdrop: 'static'});
    }
    ;

    function showCommentairFirst() {
        $('#boutonQuestionnable').html("<button type='button' id='boutonQuestionnable' onclick = 'validate(0)' class='btn btn-success btn-sm' data-dismiss='modal'><spring:message code="global.continue"/>");
        $("#modal-ajout-commentaire-questionnable").modal({backdrop: 'static'});
    }

    function validate(status) {
        var valeurs = $('[name="dwc[]"]');
        var data = "?";
        for (var i = 0; i < valeurs.length; i++) {
            if (valeurs[i].checked == true) {
                data = data + valeurs[i].name + "=" + valeurs[i].value + "&";
                console.log(data);
            }
        }
        var temp = $('#commentaires').val();
        console.log(temp);
        if (temp == undefined)
            temp = "";
        data = data + "status=" + status + "&commentaires=" + temp;
        console.log(data);
        $.ajax({
            type: 'get',
            url: 'validerListDwc' + data,
            /*                                dataType: 'json',
             enctype: 'multipart/form-data',*/
            processData: false,
            contentType: false,
            cache: false,
            success: function (json) {
                if (json.etat == 1) {
                    searchForValidation();
                } else if (json.etat == 0) {
                    $('.messageMod').html('L\'observation N° ' + json.n + ' a déja été marqué comme ' + json.status + ' par ' + json.expert);
                    showModal(status);
                }
                $('#commentaires').value = "";
            }
        });
    }
    ;

    function continueValidate(status, etat) {
        var data = "?continuer=";
        var temp = $('#commentaires').val();
        console.log(temp);
        if (temp == undefined)
            temp = "";
        data = data + etat + "&status=" + status + "&commentaires=" + temp;
        console.log(data);
        $.ajax({
            type: 'get',
            url: 'continuerValiderListDwc' + data,
            processData: false,
            contentType: false,
            cache: false,
            success: function (json) {
                if (json.etat == 1) {
                    searchForValidation();
                } else if (json.etat == 0) {
                    $('.messageMod').html('L\'observation N° ' + json.n + ' a déja été marqué comme ' + json.status + ' par ' + json.expert);
                    showModal(status);
                }
            }
        });
    }
    ;

    function checkAll() {
        console.log('checked all');
        $('[name="dwc[]"]').prop('checked', $('#checkAll').is(":checked"));
    }
</script>
<script src="<c:url value="/resources/assets/js/angular.js"/>"></script>
<script src="<c:url value="/resources/assets/js/appconfig.js"/>"></script>
<script src="<c:url value="/resources/assets/js/controller/visualisationcontroller.js"/>"></script>
<jsp:include page="/WEB-INF/inc/footer.jsp"/>