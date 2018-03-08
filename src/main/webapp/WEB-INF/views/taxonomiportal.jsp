<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/inc/header.jsp"/>  
<main class="site-content" role="main" ng-controller="taxonomi">

    <!-- darwin -->
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
                                    <input class="form-control" type="text" ng-model="taxonomi.test" name="search" placeholder="Nom scientifique de l'espèce" required/>
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
        <div class="vignette-result">
            <div class="container">
                <h1 class="titre-page">Taxonomie - <span>Tableau</span></h1>
                <div class="row">
                    <div class="col-md-12">
                        <h5 class="stat" ng-show="recherche.length === 0">Tous (<b>{{liste.length}}</b> trouvés)</h5>
                        <h5 class="stat" ng-show="recherche.length !== 0">{{recherche}} (<b>{{liste.length}}</b> trouvés)</h5>
                        <div class="padding"></div>
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <h3 class="panel-title">&nbsp;</h3>
                                <span class="pull-right">
                                    <!-- Tabs -->
                                    <ul class="nav panel-tabs">
                                        <li><a href="#"><i class="fa fa-list"></i> Liste</a></li>
                                        <li><a href="#"><i class="fa fa-globe"></i> Localisation</a></li>                                        
                                            <%
                                                Integer moderateur = (Integer) request.getAttribute("moderateur");
                                                if (moderateur == 0) {
                                            %>
                                        <li><a href="" onclick="window.location='taxonomiCsv'"><i class="fa fa-download"></i> Telecharger_CSV</a></li>
                                        <!--<li></li>-->
                                        <li class="add-one"><a href="" ng-click="editer(form)"><i class="fa fa-plus"></i> Ajouter</a></li>
                                            <%}%>
                                    </ul>
                                </span>
                            </div>

                            <!-- TABLE RESULT -->                            
                            <div class="table-responsive">
                                <jsp:include page="/WEB-INF/inc/loader-spinner.jsp"/>
                                <table class="table table-hover">
                                    <tbody>
                                        <tr>
                                            <td class="number text-center">#</td>
                                            <td class="text-center">Ordre</td>
                                            <td class="text-center">Classe</td>
                                            <td class="text-center">Famille</td>
                                            <td class="text-center">Genre</td>
                                            <td class="text-center">Espèce</td>
                                            <td class="text-center">Détails</td>                                            
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
                            <!-- END TABLE RESULT -->

                            <!-- BEGIN PAGINATION -->
                            <ul class="pagination">
                                <li class="disabled"><a href="#">«</a></li>
                                <li class="active"><a href="#">1</a></li>
                                <li><a href="#">2</a></li>
                                <li><a href="#">3</a></li>
                                <li><a href="#">4</a></li>
                                <li><a href="#">5</a></li>
                                <li><a href="#">»</a></li>
                            </ul>
                            <!-- END PAGINATION -->

                            <!-- END RESULT -->
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
<script src="<c:url value="/resources/assets/js/controller/taxonomicontroller.js"/>"></script>
<jsp:include page="/WEB-INF/inc/footer.jsp"/>  