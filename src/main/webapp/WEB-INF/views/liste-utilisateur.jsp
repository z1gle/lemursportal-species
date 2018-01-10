<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/inc/header.jsp"/>  
<main class="site-content" role="main" ng-controller="utilisateur">

    <!-- taxonomie -->
    <section id="taxonomie">
        <div class="banner-interieur" style="background:url(/lemurs/resources/assets/img/parallax/fexpert.jpg) no-repeat center center;">
            <div class="container" style="margin-top: 5%;">
                <div class="col-md-6 col-md-offset-3">
                    <!-- Search Form -->
                    <form ng-submit="rechercher()">
                        <!-- Search Field -->
                        <div class="row search-header">
                            <h4 class="text-left">Rechercher</h4>
                            <div class="form-group">
                                <div class="input-group">
                                    <input class="form-control" type="text" ng-model="user.nom" name="search" placeholder="Nom de l'utilisateur" required/>
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
        <div class="utilisateur">
            <div class="container">
                <h1 class="titre-page">Utilsateur - <span>Liste</span></h1>
                <div class="row">
                    <div class="col-md-12"> 
                        <h5 class="stat">685 utilisateurs</h5>
                        <div class="padding"></div>

                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <h3 class="panel-title">&nbsp;</h3>
                                <span class="pull-right">
                                    <!-- Tabs -->
                                    <ul class="nav panel-tabs">
                                        <li class="add-one"><a href="#"><i class="fa fa-plus"></i> Ajouter</a></li>
                                    </ul>
                                </span>
                            </div>

                            <!-- TABLE RESULT -->
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <tbody>
                                        <tr ng-repeat="dwc in liste">
                                            <td class="number text-center">{{dwc.id}}</td>
                                            <td class="text-center">{{dwc.prenom}} {{dwc.nom}}</td>
                                            <td class="text-center">{{dwc.role}}</td>
                                            <td class="text-center">0</td>
                                            <td class="text-center">0</td>
                                            <td class="text-center">0</td>
                                            <td class="text-center">0</td>
                                        </tr>                                                
                                    </tbody>
                                </table>
                            </div>
                            <!-- END TABLE RESULT -->

                            <!-- BEGIN PAGINATION -->
                            <ul class="pagination">
                                <li class="disabled"><a href="#"><</a></li>
                                <li class="active"><a href="#">1</a></li>
                                <li><a href="#">2</a></li>
                                <li><a href="#">3</a></li>
                                <li><a href="#">4</a></li>
                                <li><a href="#">5</a></li>
                                <li><a href="#">></a></li>
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

</main>
<script src="<c:url value="/resources/assets/js/angular.js"/>"></script>
<script src="<c:url value="/resources/assets/js/appconfig.js"/>"></script>
<script src="<c:url value="/resources/assets/js/controller/utilisateur.js"/>"></script>
<jsp:include page="/WEB-INF/inc/footer.jsp"/>  
