<%@page import="org.wcs.lemurs.modele_vue.VueRoleUtilisateur"%>
<%@page import="java.util.List"%>
<%@page import="org.wcs.lemurs.model.Utilisateur"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/inc/header.jsp"/>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<main id="controller" class="site-content" role="main" ng-controller="darwin">
    <input type="hidden" id="pageEnCours">
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
    <!--CSS for search global-->
    <style>
        #rechercheGlobale {
            background-color: white;
            background-image: url('resources/assets/img/icons/searchicon.png');
            background-position: 0px 4px;
            background-repeat: no-repeat;
            background-size: 16px;
            width: 15%;
            -webkit-transition: width 0.4s ease-in-out;
            transition: width 0.4s ease-in-out;
            height: 20px;
            border-radius: 15px;
            border-style: solid;
            border-width: 0px;
            margin-top: 4px;
            padding-left: 28px;
            padding-top: 4px;
            font-size: 14px;
            font-weight: 600;
        }

        /* When the input field gets focus, change its width to 100% */
        #rechercheGlobale:focus {
            width: 50%;
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
    <!--Import file style-->
    <style>
        #csv-xl {
            display: none;            
        }
        #uploadForm {
            display: table-row;            
            backround:pink;
        }
        span[role=button] {
            display: table-cell;
            font-family: Arial;
            font-size: 1rem;
            padding: 8px 8px;
            border-top-left-radius: 5px;
            border-bottom-left-radius: 5px;
            border: 2px solid #504014;  
            color: #ffffff;
            cursor: pointer;
            background-color: #504014;
            outline: none;
        }
        span[role=button]:hover,
        span[role=button]:focus {
            box-shadow: 0 0 5px #595959;
            background-color: #8a6d3b;
            border-color: #8a6d3b;
            outline: 2px solid transparent;
        }
        .hide {
            position: absolute;
            width: 1px;
            height: 1px;
            padding: 0;
            margin: -1px;
            overflow: hidden;
            clip: rect(0,0,0,0);
            border: 0;
        }
        #filename {
            display: table-cell;
            margin-left: -5px;
            width: 80%;
            font-family: Arial;
            font-size: 1rem;
            padding: 8px 8px;
            border-top-right-radius: 5px;
            border-bottom-right-radius: 5px;
            border: 2px solid #504014;  
            color: #000000;
            background-color: #ffffff;
        }
        #filename:focus {
            box-shadow: 0 0 5px #595959;
            border-color: #8a6d3b;
            outline: 2px solid transparent;
        }
        #buttonlabel {
            display: inline;
        }
    </style>
    <!--Modal style-->
    <style>        
        .modal-header {
            background-color: #8a6d3b;
        }
        .modal-title {
            color: white;
            font-weight: 510;
        }

    </style>
    <!-- darwin -->
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
                                    <input id="form-search" ng-keyup="$event.keyCode == 13 ? rechercherAvancee() : null" name="espece" type="text" placeholder="<spring:message code="research.scientificname"/>" class="checkbox-inline" style="height: 20px; border-radius: 15px; width: 26%; border-style: solid;border-width: 1px; float: left;">                                        
                                    <c:choose>
                                        <c:when test="${utilisateur.nom!=''&&utilisateur.nom!=null}">
                                            <label style="float: left; margin-top: -3px;" class="checkbox-inline">
                                                <input ng-click="rechercherAvancee()" id="publique" name="etat[]" type="checkbox" value="1" checked="">
                                                <span class="badge" style="margin-left: 15px;"><spring:message code="checkbox.public"/></span>
                                            </label>
                                            <label style="float: left; margin-top: -3px;" class="checkbox-inline">
                                                <input ng-click="rechercherAvancee()" id="privee" name="etat[]" type="checkbox" value="0">
                                                <span class="badge" style=""><spring:message code="checkbox.private"/></span>
                                            </label>
                                        </c:when>
                                        <c:otherwise>
                                            <label style="float: left; margin-top: -3px;" class="checkbox-inline">
                                                <input ng-click="rechercherAvancee()" id="publique" name="etat[]" disabled="" type="checkbox" value="1" checked="">
                                                <span class="badge" style="margin-left: 15px;"><spring:message code="checkbox.public"/></span>
                                            </label>
                                            <label style="float: left; margin-top: -3px;" class="checkbox-inline">
                                                <input ng-click="rechercherAvancee()" id="privee" disabled="" name="etat[]" type="checkbox" value="0">
                                                <span class="badge" style=""><spring:message code="checkbox.private"/></span>
                                            </label>
                                        </c:otherwise>
                                    </c:choose>
                                    <select name="validation" id="etat" class="checkbox-inline" style="height: 20px; border-radius: 15px; border-style: solid;border-width: 1px; width: 26%; float: left;">                                        
                                        <option value="-999"><spring:message code="data.status.all_occurences"/></option>                                        
                                        <option value="1"><spring:message code="data.status.all_reliable_reviews_data"/></option>
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
                                    <a href="#" title="search" style="padding: 0px;height: 20px;float: left;margin-left: 6px;" ng-click="rechercherAvancee()" class="btn"><i style="color: darkgrey" class="fa fa-search"></i></a>
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
        <div class="vignette-result" style="margin-top: 0px;">
            <div class="container-fluid">
                <div class="row" style="margin-top: 10px;">
                    <!-- Stat -->
                    <c:if test="${nbr > 0}">
                        <a href="newObservations" style="float: left;"><span id="nbr"><c:out value="${nbr}"></c:out></span> <spring:message code="observation.new_occurences"/></a><br>
                    </c:if>                    
                    <h1 style="font-size:  14px;font-weight:  600;width:  167px;float: left;margin-top: 9px; color: #a18029;"><spring:message code="observation.title"/> |</h1>
                    <h5 style="float: right;" class="stat " ng-cloak><spring:message code="observation.table.page"/>: <b>{{pageEnCours}}/{{lastPage}}</b> | <spring:message code="observation.table.total"/>: <b>{{liste[0].total}}</b></h5>                    
                    <a title="<spring:message code="observation.icon.title.template"/>" style="width: 3%; display: inline-block; float: left;" class="btn" href="resources/assets/Model_darwin_core.csv"><i class="fa fa-file-excel-o"></i></a>
                        <c:if test="${utilisateur.nom!=''&&utilisateur.nom!=null}">
                        <a href="#" title="<spring:message code="observation.icon.title.upload"/>" style="width: 3%; display: inline-block; float: left;" onclick="$('#modal-upload-dwc').modal({backdrop: 'static'});" class="btn"><i class="fa fa-upload"></i></a>
                        </c:if>
                    <a href="#" title="<spring:message code="observation.icon.title.download_observation"/>" style="width: 3%; display: inline-block; float: left;" data-toggle='modal' data-target='#modal-download_formular' class="btn"><i style="margin-top: 4px;" class="fa fa-download"></i></a>                    
                        <%
                            Integer adminOuModerateur = ((Integer) request.getAttribute("adminOuModerateur"));
                            if (adminOuModerateur == 0) {
                        %>
                    <a href="#" title="<spring:message code="observation.icon.title.upload_link"/>" style="width: 3%; display: inline-block; float: left; margin-right: 2px;" data-toggle='modal' data-target='#modal-upload_by_link' class="btn"><i style="margin-top: 4px;" class="fa fa-cloud-upload"></i></a>
                        <%
                            }
                        %>
                    <!--<a href="#" title="Global research" style="width: 3%; display: inline-block; float: left; margin-right: 2px;" data-toggle='modal' data-target='#modal-upload_by_link' class="btn"><i class="fa fa-search"></i></a>-->
                    <input ng-keyup="$event.keyCode == 13 ? rechercheGlobale() : null" title="<spring:message code="research.global_research"/>" id="rechercheGlobale" type="text" style="display: inline-block; float: left; margin-left: 8px;">                                        
                    <!-- End Stat -->                    
                </div>
                <div class="row">                    
                    <jsp:include page="/WEB-INF/inc/loader-spinner.jsp"/>
                    <form>
                        <div class="table-responsive" id="liste">                        
                            <table class="table table-hover">
                                <tbody>
                                    <tr style="background-color: black; color: #deaa45; font-weight: 700;">
                                        <%
                                            Integer expert = ((Integer) request.getAttribute("expert"));
                                            if (expert == 0) {
                                        %>
                                        <td class="text-center"></td>
                                        <%}%>
                                        <td class="number text-center">Id</td>
                                        <td class="text-center"><spring:message code="table.dwc.scientificname"/> </td>
                                        <td class="text-center"><spring:message code="table.dwc.location"/></td>
                                        <td class="text-center"><spring:message code="table.dwc.class"/></td>
                                        <td class="text-center"><spring:message code="table.dwc.order"/></td>                                        
                                        <td class="text-center"><spring:message code="table.dwc.genus"/></td>
                                        <td class="text-center"><spring:message code="table.dwc.date"/></td>
                                        <td class="text-center"><spring:message code="table.dwc.institution"/></td>
                                        <%
                                            Integer role = ((Integer) request.getAttribute("role"));
                                            Integer idChercheur = ((Integer) request.getAttribute("idChercheur"));
                                            if (role == 0) {
                                        %>
                                        <td class="text-center"><spring:message code="table.dwc.remark"/></td>
                                        <%}%>
                                        <%if (expert == 0) {%>
                                        <td class="text-center">Etat</td>
                                        <%}%>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr ng-cloak ng-repeat="dwc in liste">
                                        <%if (expert == 0) {%>
                                        <td ng-cloak ng-if="dwc.validation == 1" class="number text-center">                                            
                                            <div class="checkbox checkbox-info checkbox-circle" style="margin-bottom: 0px; margin-top: 0px;">
                                                <input ng-cloak id="ckb{{dwc.dwc.id}}" name="dwc[]" value="{{dwc.dwc.id}}" type="checkbox">
                                                <label ng-cloak for="ckb{{dwc.dwc.id}}"></label>
                                            </div>
                                        </td>
                                        <td ng-cloak ng-if="dwc.validation != 1" class="number text-center"></td>
                                        <%}%>
                                        <td ng-cloak class="number text-center"><a style="color: #818181;" href="detailLemurien?id={{dwc.dwc.id}}">{{dwc.dwc.id}}</a></td>
                                        <td ng-cloak class="text-center"><a href="detailLemurien?id={{dwc.dwc.id}}">{{dwc.dwc.scientificname}}</a></td>
                                        <td ng-cloak class="text-center">{{dwc.dwc.locality}}</td>
                                        <td ng-cloak class="text-center">{{dwc.dwc.darwinclass}}</td>
                                        <td ng-cloak class="text-center">{{dwc.dwc.darwinorder}}</td>                                        
                                        <td ng-cloak class="text-center">{{dwc.dwc.genus}}</td>
                                        <td ng-cloak class="text-center">{{dwc.dwc.dwcyear}}</td>
                                        <td ng-cloak class="text-center">{{dwc.dwc.institutioncode}}</td>    
                                        <%if (role == 0) {%>
                                        <td class="">
                                            <ul>
                                                <li ng-cloak ng-if="dwc.dwc.idUtilisateurUpload == <%out.print(idChercheur);%> && dwc.dwc.annee == false"><spring:message code="remark.year"/></li>
                                                <li ng-cloak ng-if="dwc.dwc.idUtilisateurUpload == <%out.print(idChercheur);%> && dwc.dwc.accepted_speces == false"><spring:message code="remark.scientificname"/></li>
                                                <li ng-cloak ng-if="dwc.dwc.idUtilisateurUpload == <%out.print(idChercheur);%> && dwc.dwc.collecteur == false"><spring:message code="remark.collector"/></li>
                                                <li ng-cloak ng-if="dwc.dwc.idUtilisateurUpload == <%out.print(idChercheur);%> && dwc.dwc.gps == false"><spring:message code="remark.gps"/></li>
                                            </ul>
                                        </td>    
                                        <%}%>
                                        <%if (expert == 0) {%>
                                        <td ng-cloak ng-if="dwc.dwc.validationexpert == -1" class="number text-center"><spring:message code="data.status.awaiting_review"/></td>
                                        <td ng-cloak ng-if="dwc.validation == 1 && dwc.dwc.validationexpert == 0" class="number text-center"><spring:message code="data.status.questionnable_review"/></td>
                                        <td ng-cloak ng-if="dwc.validation == 1 && dwc.dwc.validationexpert == 1" class="number text-center"><spring:message code="data.status.reliable_review"/></td>
                                        <td ng-cloak ng-if="dwc.validation != 1 && dwc.dwc.validationexpert != -1" class="number text-center"></td>
                                        <%} else {%>
                                        <td></td>
                                        <%}%>
                                        <td ng-cloak ng-if="dwc.dwc.lienSource != null" class="text-center"><a href="http://data.rebioma.net/#tab=occ&view=Detail&id={{dwc.dwc.idRebioma}}&p=false&page=1&asearch=Id = {{dwc.dwc.idRebioma}}&type=all occurrences" target="_blank">Rebioma</a></td>
                                        <td ng-cloak ng-if="dwc.dwc.lienSource == null" class="text-center"></a></td>
                                    </tr>                                                   
                                </tbody>
                            </table>                                                                                                            
                        </div>
                        <!-- BEGIN PAGINATION -->
                        <ul class="pagination" style="margin: 0px;">
                            <li class="" id="previous"><a href="#" ng-cloak ng-click="rechercher(1)">«</a></li>
                            <!--                        <li class="active"><a href="#">1</a></li>                        -->
                            <li><a href="#" ng-cloak ng-click="rechercher(temp)" ng-repeat="temp in pages">{{temp}}</a></li>
                            <li><a href="#" ng-cloak ng-click="rechercherFin()" id="next">»</a></li>
                            <input type="hidden" id="pageFin">
                        </ul>
                        <!-- END PAGINATION -->
                        <%if (expert == 0) {%>
                        <button onclick="showCommentairFirst();" style="float: right; margin-left: 2px;" class="btn btn-success">Questionnable</button>
                        <button onclick="validate(1);" style="float: right; background-color: #4CAF50!important;" class="btn btn-success">Valider</button>   
                        <%}%>
                    </form>
                </div>
            </div>
        </div>
        <!-- End Contenu -->

    </section>
    <div id='modal-liste-colonnes' class='modal fade' role='dialog' style='display:none !important' tabindex="-1">
        <div class='modal-dialog'>
            <div class='modal-content'>
                <div class="modal-header">
                    <button data-dismiss='modal' class='close' type='button'>x</button>
                    <h4 class="modal-title"><center>Colonnes pris en compte</center></h4>
                </div>
                <div class='modal-body'>
                    <div class='row'>
                        <div class='col-md-10 col-md-offset-1' style="max-height: 500px; overflow-y:  scroll;">         
                            <div class="col-sm-12">
                                <input ng-cloak ng-click="checkAll()" type="checkbox" id="checkAll">Tout Sélectionner
                            </div>
                            <br>
                            <br>
                            <div class="col-sm-12" ng-cloak ng-repeat="col in colonnes">
                                <input type="checkbox" name="col[]" value="{{col.index}}">{{col.valeur}}
                            </div>                                    
                        </div>
                    </div>
                </div>
                <div class='modal-footer'>
                    <button type='button' class='btn btn-default btn-sm' data-dismiss='modal'><spring:message code="global.cancel"/></button>
                    <button type='button' id="" ng-click="getDwcCsv()" class='btn btn-success btn-sm' data-dismiss='modal'><spring:message code="global.continue"/>
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
    <div id='modal-upload_spinner' class='modal fade' role='dialog' style='display:none !important' tabindex="-1">
        <div class='modal-dialog'>
            <div class='modal-content'>
                <div class="modal-header">
                    <button data-dismiss='modal' class='close' type='button'>x</button>
                    <h4 class="modal-title"><center>Upload</center></h4>
                </div>
                <div class='modal-body'>
                    <div class='row'>
                        <div class='col-md-10 col-md-offset-1'>                                                        
                            <img src="resources/assets/img/loaderB32.gif" class="img-responsive" style="margin: 5px auto;">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id='modal-upload_by_link' class='modal fade' role='dialog' style='display:none !important' tabindex="-1">
        <div class='modal-dialog'>
            <div class='modal-content'>
                <div class="modal-header">
                    <button data-dismiss='modal' class='close' type='button'>x</button>
                    <h4 class="modal-title"><center>Upload</center></h4>
                </div>
                <div class='modal-body'>
                    <div class='row'>
                        <div class='col-md-10 col-md-offset-1'>                            
                            <div class="col-sm-12">
                                <textarea placeholder="Ecrire le lien du fichier ici" id="link" class="form-control"></textarea>                                
                            </div>                                    
                        </div>
                    </div>
                </div>
                <div class='modal-footer'>
                    <button type='button' class='btn btn-default btn-sm' onclick="$('#link').val('')" data-dismiss='modal'><spring:message code="global.cancel"/></button>
                    <button type='button' ng-click="uploadByLink()" class='btn btn-success btn-sm' data-dismiss='modal'><spring:message code="global.continue"/>
                </div>
            </div>
        </div>
    </div>
    <div id='modal-alert' class='modal fade' role='dialog' style='display:none !important' tabindex="-1">
        <div class='modal-dialog'>
            <div class='modal-content'>
                <div class="modal-header">
                    <button data-dismiss='modal' class='close' type='button'>x</button>
                    <h4 class="modal-title"><center>ERREUR</center></h4>
                </div>
                <div class='modal-body'>
                    <div class='row'>
                        <div class='col-md-10 col-md-offset-1'>                            
                            <div class="col-sm-12">
                                <span id="messageAlerte">Un erreur est survenu lors du t&eacute;l&eacute;chargement. Veuiller v&eacute;rifier votre acr&eacute;ditation ou la structure des donn&eacute;es.</span>
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

    <div id='modal-upload-dwc' class='modal fade' role='dialog' style='display:none !important' tabindex="-1">
        <div class='modal-dialog'>
            <div class='modal-content'>
                <div class="modal-header">
                    <button data-dismiss='modal' class='close' type='button'>x</button>
                    <h4 class="modal-title"><center><spring:message code="observation.upload.title"/></center></h4>
                </div>
                <div class='modal-body row'>                       
                    <form id="uploadForm" method="POST" class="col-md-offset-1 col-md-11" enctype="multipart/form-data">
                        Format file
                        <select class="form-control" id="sep" style="width: 92%;height: 42px;border-radius: 5px;">
                            <option value="2"><spring:message code="observation.popup.upload_dwc.semicolon"/></option>
                            <option value="0"><spring:message code="observation.popup.upload_dwc.comma"/></option>
                            <option value="1">Excel (*.xlsx)</option>                                                        
                        </select><br>
                        <label for="csv-xl" id="buttonlabel">
                            <span role="button" aria-controls="filename" tabindex="0">
                                File path
                            </span>
                        </label>
                        <input type="file" name="excelfile" ng-model="file" id="csv-xl">
                        <label for="filename" class="hide">
                            uploaded file
                        </label>
                        <input type="text" id="filename" autocomplete="off" readonly placeholder="no file uploaded">  <br>
                        <!--<input id="publique" type="checkbox" value="1"> publique <br>-->
                        <div class="checkbox checkbox-info checkbox-circle chk">
                            <input id="publique-modal" type="checkbox" value="1">
                            <label for="publique-modal"><spring:message code="checkbox.public"/></label>
                        </div>
                        <spring:message code="observation.popup.upload_dwc.term"/> <a href="#" onclick="window.open('resources/assets/policy.pdf', '_blank')">(<spring:message code="global.download"/>)</a>
                    </form>
                </div>
                <div class='modal-footer'>                        
                    <input data-toggle='modal' data-target='#modal-upload_spinner' data-dismiss='modal' class="btn btn-success btn-sm" type="submit" id="publique" ng-click="upload()" value="<spring:message code="global.import"/>">                        
                    <button type='button' class='btn btn-default btn-sm' onclick="$('#link').val('')" data-dismiss='modal'><spring:message code="global.cancel"/></button>                    
                </div>                    
            </div>
        </div>
    </div>
    <div id='modal-download_formular' class='modal fade' role='dialog' style='display:none !important' tabindex="-1">
        <div class='modal-dialog'>
            <div class='modal-content'>
                <div class="modal-header">
                    <button data-dismiss='modal' class='close' type='button'>x</button>
                    <h4 class="modal-title"><spring:message code="observation.popup.download_osbervation.title_of_popup"/></h4>                    
                </div>
                <div class='modal-body row'>                       
                    <form id="download_formular" method="POST" class="col-md-offset-1 col-md-11">
                        <label><spring:message code="observation.popup.download_osbervation.title"/></label>                        
                        <input type="text" id="form-name" class="form-control" ng-model="downloadInformation.titre">
                        <label><spring:message code="observation.popup.download_osbervation.name"/><span class="obligatory">*</span></label>                        
                        <input type="text" required id="obligatory-name" ng-blur="checkObligatory('obligatory-name', 0)" class="form-control must-not-empty" ng-model="downloadInformation.nom">
                        <label><spring:message code="observation.popup.download_osbervation.firstname"/><span class="obligatory">*</span></label>                        
                        <input type="text" required id="obligatory-firstname" ng-blur="checkObligatory('obligatory-firstname', 1)" class="form-control must-not-empty" ng-model="downloadInformation.prenom">
                        <label><spring:message code="observation.popup.download_osbervation.profession"/></label>                        
                        <input type="text" id="form-name" class="form-control" ng-model="downloadInformation.profession">
                        <label><spring:message code="observation.popup.download_osbervation.email"/><span class="obligatory">*</span></label>                        
                        <input type="text" required id="obligatory-email" ng-blur="checkObligatory('obligatory-email', 2)" class="form-control must-not-empty" ng-model="downloadInformation.eMail">
                        <label><spring:message code="observation.popup.download_osbervation.institution"/></label>                        
                        <input type="text" id="form-name" class="form-control" ng-model="downloadInformation.institution">
                        <label><spring:message code="observation.popup.download_osbervation.use"/><span class="obligatory">*</span></label>                        
                        <input type="text" required id="obligatory-use" ng-blur="checkObligatory('obligatory-use', 3)" class="form-control must-not-empty" ng-model="downloadInformation.utilisation">
                    </form>
                </div>
                <div class='modal-footer download-footer'>                        
                    <button type='button' class='btn btn-default btn-sm' data-dismiss='modal'><spring:message code="global.cancel"/></button>                    
                    <input data-dismiss='modal' disabled class="btn btn-success btn-sm" type="submit" id="download-continue" ng-click="getColonnes()" value="<spring:message code="global.continue"/>">                        
                </div>                    
            </div>
        </div>
    </div>    
    <!-- end darwin -->

</main>
<script>
    function showModal(status) {
        if (status == 0)
            $("#modal-ajout-confirmation-questionnable").modal({backdrop: 'static'});
        else
            $("#modal-ajout-confirmation-valide").modal({backdrop: 'static'});
    }
    ;
    /*                        function showCommentair() {
     $('#boutonQuestionnable').html("<button type='button' id='boutonQuestionnable' onclick = 'continueValidate(0,1)' class='btn btn-success btn-sm' data-dismiss='modal'><spring:message code="global.continue"/>");
     $("#modal-ajout-commentaire-questionnable").modal({backdrop: 'static'});
     }*/
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
                    console.log(json.etat);
                    angular.element('#controller').scope().findForValidation();
                    angular.element('#controller').scope().$apply();
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
                    console.log(json.etat);
                    angular.element('#controller').scope().getalls();
                    angular.element('#controller').scope().$apply();
                } else if (json.etat == 0) {
                    $('.messageMod').html('L\'observation N° ' + json.n + ' a déja été marqué comme ' + json.status + ' par ' + json.expert);
                    showModal(status);
                }
                $('#commentaires').value = "";
            }
        });
    }
    ;
</script>
<!--Import file style js-->
<script>
    /* trigger upload on space & enter
     = standard button functionality*/
    $('#buttonlabel span[role=button]').bind('keypress keyup', function (e) {
        if (e.which === 32 || e.which === 13) {
            e.preventDefault();
            $('#csv-xl').click();
        }
    });

    /* return chosen filename to additional input*/
    $('#csv-xl').change(function (e) {
        var filename = $('#csv-xl').val().split('\\').pop();
        $('#filename').val(filename);
        $('#filename').attr('placeholder', filename);
        $('#filename').focus();
    });
</script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.6/angular.min.js"></script>
<script src="<c:url value="/resources/assets/js/appconfig.js"/>"></script>
<script src="<c:url value="/resources/assets/js/controller/darwincontroller.js"/>"></script>
<jsp:include page="/WEB-INF/inc/footer.jsp"/>  