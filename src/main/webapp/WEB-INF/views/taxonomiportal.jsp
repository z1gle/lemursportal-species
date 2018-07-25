<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/inc/header.jsp"/>  
<main class="site-content" role="main" ng-controller="taxonomi">
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
                /*background-color: beige;*/
            }        
        }
    </style>
    <!-- darwin -->
    <section id="taxonomie">
        <div class="banner-interieur-pliss" style="background:url(resources/assets/img/parallax/fexpert_modif.jpg) no-repeat center center; height: 125px; background-color: beige;"></div>        
        <!-- Contenu -->
        <div class="vignette-result" style="margin-top: 0px;">
            <div class="container-fluid">
                <div class="row header-pliss" style="">
                    <!-- Stat -->                                        
                    <h1 style="font-size:  14px;font-weight:  600;width:  90px;float: left;margin-top: 9px; color: #a18029;"><spring:message code="species.label.taxonomi"/> |</h1>
                    <h5 style="float: right;" class="stat " ng-cloak>Page: <b>1/1</b> | Espece total: <b>{{liste.length}}</b></h5>                                        
                    <%
                        Integer moderateur = (Integer) request.getAttribute("moderateur");
                        if (moderateur == 0) {
                    %>
                    <a href="#" title="add new specie" style="width: 3%; display: inline-block; float: left; margin-right: 2px;margin-top: 2px;" ng-click="editer(form)" class="btn"><i class="fa fa-plus"></i></a>
                        <%}%>
                    <input ng-model="taxonomi.test" name="search" ng-keyup="$event.keyCode == 13 ? rechercher() : null" title="Global research" id="rechercheGlobale" type="text" style="display: inline-block; float: left; margin-left: 8px;margin-top: 5px;">                    
                    <!-- End Stat -->                    
                </div>                
                <div class="row">
                    <div class="panel panel-primary">                            
                        <!-- TABLE RESULT -->                            
                        <div class="table-responsive">
                            <jsp:include page="/WEB-INF/inc/loader-spinner.jsp"/>
                            <table class="table table-hover">
                                <tbody>
                                    <tr style="background-color: black; color: #deaa45; font-weight: 700;">
                                        <td class="number text-center">Id</td>
                                        <td class="text-center"><spring:message code="species.label.taxonomi.order"/></td>
                                        <td class="text-center"><spring:message code="species.label.taxonomi.class"/></td>
                                        <td class="text-center"><spring:message code="species.label.taxonomi.family"/></td>
                                        <td class="text-center"><spring:message code="species.label.taxonomi.genus"/></td>
                                        <td class="text-center"><spring:message code="species.label.taxonomi.species"/></td>
                                        <td class="text-center"><spring:message code="species.label.tableau.details"/></td>                                            
                                        <%if (moderateur == 0) {%>
                                        <td class="text-center">Action</td>
                                        <%}%>
                                    </tr>
                                    <tr ng-repeat="taxo in liste">
                                        <td class="number text-center">{{$index + 1}}</td>
                                        <td class="text-center">{{taxo.dwcorder}}</td>
                                        <td class="text-center">{{taxo.dwcclass}}</td>
                                        <td class="text-center">{{taxo.dwcfamily}}</td>
                                        <td class="text-center">{{taxo.genus}}</td>
                                        <td class="text-center">{{taxo.scientificname}}</td>
                                        <td class="text-center"><a ng-click="detailTaxo(taxo.id)" href=""><i class="fa fa-eye"></i></a></td>                                            
                                                <%if (moderateur == 0) {%>
                                        <td class="text-center del">
                                            <a href="" ng-click="editer(taxo)"><i class="fa fa-edit"></i></a>&nbsp;&nbsp;
                                            <a href="" ng-click="delete(taxo)"><i class="fa fa-remove"></i></a>
                                        </td>
                                        <%}%>
                                    </tr>                                                   
                                </tbody>
                            </table>
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
                    <div class="col-md-12">
                        <form>
                            <table class="table-responsive" style="margin-left: 15px; margin-right: 15px;">
                                <tr>
                                    <td style="width: 145px; padding-bottom: 2px;">
                                        <label>Higher classification : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model="form.higherclassification">
                                    </td>
                                    <td style="width: 145px;">
                                        <label>Kingdom : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model="form.kingdom">
                                    </td>                                    
                                </tr>
                                <tr>
                                    <td style="width: 145px;">
                                        <label>Phylum : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model="form.phylum">
                                    </td>
                                    <td style="width: 145px; padding-bottom: 2px;">
                                        <label>Darwin core class : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model="form.dwcclass">
                                    </td>                                                                        
                                </tr>
                                <tr>
                                    <td style="width: 145px;">
                                        <label>Darwin core order : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model="form.dwcorder">
                                    </td>
                                    <td style="width: 145px;">
                                        <label>Darwin core family : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model="form.dwcfamily">
                                    </td>                                                                        
                                </tr>
                                <tr>
                                    <td style="width: 145px; padding-bottom: 2px;">
                                        <label>Genus : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.genus">
                                    </td>
                                    <td style="width: 145px;">
                                        <label>Genus source  : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.genussource">
                                    </td>                                                                        
                                </tr>
                                <tr>
                                    <td style="width: 145px;">
                                        <label>Specific epithet  : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.specificepithet">
                                    </td>
                                    <td style="width: 145px; padding-bottom: 2px;">
                                        <label>Specific epithet source  : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.specificepithetsource">
                                    </td>                                                                        
                                </tr>
                                <tr>
                                    <td style="width: 145px;">
                                        <label>Infraspecific epithet : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.infraspecificepithet">
                                    </td>
                                    <td style="width: 145px;">
                                        <label>Infraspecificepithetsource : </label>                                     
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.infraspecificepithetsource">
                                    </td>                                                                        
                                </tr>
                                <tr>
                                    <td style="width: 145px; padding-bottom: 2px;">
                                        <label>Scientific ng-model : </label>                                     
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.scientificname">
                                    </td>
                                    <td style="width: 1px;">
                                        <label>Scientific ng-model author ship : </label>                                     
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.scientificnameauthorship">
                                    </td>                                                                        
                                </tr>
                                <tr>
                                    <td style="width: 145px;">
                                        <label>Accepted ng-model usage : </label>                                     
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.acceptednameusage">
                                    </td>
                                    <td style="width: 145px; padding-bottom: 2px;">
                                        <label>Basis of record : </label>                                     
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.basisofrecord">
                                    </td>                                                                        
                                </tr>
                                <tr>
                                    <td style="width: 145px;">
                                        <label>French vernacular ng-model : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.frenchvernacularname">
                                    </td>
                                    <td style="width: 145px;">
                                        <label>Malagasy vernacular ng-model : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.malagasyvernacularname">
                                    </td>                                    
                                </tr>
                                <tr>
                                    <td style="width: 145px; padding-bottom: 2px;">
                                        <label>English vernacular ng-model : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.englishvernacularname">
                                    </td>
                                    <td style="width: 145px;">
                                        <label>Habitat_fr : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.habitatFr">
                                    </td>                                    
                                </tr>
                                <tr>
                                    <td style="width: 145px;">
                                        <label>Habitat_en : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.habitatEn">
                                    </td>
                                    <td style="width: 145px; padding-bottom: 2px;">
                                        <label>Habitat source : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.habitatsource">
                                    </td>
                                    
                                </tr>
                                <tr>
                                    <td style="width: 145px;">
                                        <label>Ecology_fr : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.ecologyFr">
                                    </td>
                                    <td style="width: 145px;">
                                        <label>Ecology_en : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.ecologyEn">
                                    </td>                                    
                                </tr>
                                <tr>
                                    <td style="width: 145px; padding-bottom: 2px;">
                                        <label>Ecology source : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.ecologysource">
                                    </td>
                                    <td style="width: 145px;">
                                        <label>Behavior_fr : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.behaviorFr">
                                    </td>                                    
                                </tr>
                                <tr>
                                    <td style="width: 145px; padding-bottom: 2px;">
                                        <label>Behavior_en : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.behaviorEn">
                                    </td>
                                    <td style="width: 145px;">
                                        <label>Behavior source : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.behaviorsource">
                                    </td>                                    
                                </tr>
                                <tr>
                                    <td style="width: 145px; padding-bottom: 2px;">
                                        <label>Threat_fr : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.threatFr">
                                    </td>
                                    <td style="width: 145px;">
                                        <label>Threat_en : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.threatEn">
                                    </td>                                    
                                </tr>
                                <tr>
                                    <td style="width: 145px; padding-bottom: 2px;">
                                        <label>Threat source : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.threatsource">
                                    </td>
                                    <td style="width: 145px;">
                                        <label>Morphology_fr : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.morphologyFr">
                                    </td>                                    
                                </tr>
                                <tr>
                                    <td style="width: 145px; padding-bottom: 2px;">
                                        <label>Morphology_en : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.morphologyEn">
                                    </td>
                                    <td style="width: 145px;">
                                        <label>Protected area occurrences : </label>                                    
                                    </td>
                                    <td style="width: 145px;">
                                        <input style="width: 90%;" type="text" ng-model ="form.protectedareaoccurrences">
                                    </td>
                                </tr>
                            </table>
                            <div style="float: right;margin-right: 90px;margin-bottom: 5px;">
                                <input class="btn btn-primary" ng-click="annuler()" value="Annuler">
                                <%if (moderateur == 0) {%>
                                <input type="submit" class="btn btn-success" ng-click="save()" value="Enregistrer">
                                <%}%>
                            </div>
                        </form>
                    </div>                    
                </div>
            </div>
        </div>
    </div>




</main>
<script src="<c:url value="/resources/assets/js/angular.js"/>"></script>
<script src="<c:url value="/resources/assets/js/appconfig.js"/>"></script>
<script src="<c:url value="/resources/assets/js/controller/taxonomicontroller.js"/>"></script>
<jsp:include page="/WEB-INF/inc/footer.jsp"/>  