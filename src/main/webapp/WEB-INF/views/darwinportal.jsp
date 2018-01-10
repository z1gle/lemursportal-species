<%@page import="org.wcs.lemurs.modele_vue.VueRoleUtilisateur"%>
<%@page import="java.util.List"%>
<%@page import="org.wcs.lemurs.model.Utilisateur"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/inc/header.jsp"/>  
<main class="site-content" role="main" ng-controller="darwin">

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
                                    <input class="form-control" type="text" ng-model="darwin.scientificname" name="search" placeholder="Nom scientifique de l'espèce" required/>
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
                <h1 class="titre-page">Observation - <span>Liste</span></h1>
                <div class="row">
                    <div class="col-md-12">
                        <!-- Stat -->
                        <h5 class="stat">Tous (<b>{{liste.length}}</b>)</h5>
                        <!-- End Stat -->
                        <!-- Vignette -->
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-12" ng-repeat="dwc in liste">
                            <div class="vignette">
                                <img src="<c:url value="/resources/assets/img/fexpert.jpg"/>" alt="{{dwc.scientificname}}" >
                                <div class="offer">
                                    <span><i class="fa fa-comment"></i>120</span>
                                    <span class="pull-right valid"><i class="fa fa-check"></i></span>
                                </div>
                                <div class="detail">
                                    <a href="#">
                                        <h3>{{dwc.scientificname}}</h3>
                                        <p><i class="fa fa-angle-down"></i></p>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <!-- End Vignette -->
                    </div>
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
                    <%                        
                        Integer role = ((Integer) request.getAttribute("role"));
                        if(role == 0){
                    %>
                    <form action="processExcel" method="POST" style="float: right;" enctype="multipart/form-data">
                        <div>Importer un fichier Excel:</div>
                        <input name="excelfile" type="file">
                        <input type="submit" value="Importer">
                    </form>
                    <%}%>
                    <!-- END PAGINATION -->
                </div>
            </div>
        </div>
        <!-- End Contenu -->

    </section>
    <!-- end darwin -->

</main>
<script src="<c:url value="/resources/assets/js/angular.js"/>"></script>
<script src="<c:url value="/resources/assets/js/appconfig.js"/>"></script>
<script src="<c:url value="/resources/assets/js/controller/darwincontroller.js"/>"></script>
<jsp:include page="/WEB-INF/inc/footer.jsp"/>  