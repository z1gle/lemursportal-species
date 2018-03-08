<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/inc/header.jsp"/>  
<main class="site-content" role="main" ng-controller="controller">
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
                                    <input class="form-control" type="text" ng-model="taxonomi.scientificname" name="search" placeholder="Nom scientifique de l'espèce" required/>
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
                <h1 class="titre-page">Utilisateur - <span>Assignation expertise</span></h1>
                <button style="float: right;" class="btn btn-primary" onclick="history.back()">Précédent</button>
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
                                        <li><a href="#"><i class="fa fa-download"></i> CSV</a></li>
                                        <li class="add-one"><a href="" ng-click="editer(form)"><i class="fa fa-plus"></i> Ajouter</a></li>                                            
                                    </ul>
                                </span>
                            </div>
                            <div class="row"></div>
                            <div class="row">
                                <div class="col-md-4">
                                    <h4>Famille</h4>
                                </div>
                                <div style="display: none!important;" id="titreGenre" class="col-md-4">
                                    <h4>Genre</h4>
                                </div>
                                <div style="display: none!important;" id="titreEspece" class="col-md-4">
                                    <h4>Espece</h4>
                                </div>
                            </div>
                            <div class="row">                                
                                <form method="POST" action="assigner">
                                    <ul>
                                        <li ng-repeat="famille in liste">
                                            <div class="row">
                                                <div class="col-md-4">
                                                    <input id="{{famille}}" type="checkbox" name="valeur[]" value="typeFamille-{{famille}}" ng-click="getGenre(famille)" >  {{famille}}
                                                </div>
                                                <div class="col-md-8">
                                                    <ul id="genre-{{famille}}">
                                                    </ul>                                                    
                                                </div>
                                            </div>
                                        </li>
                                    </ul>      
                                    <%
                                        Integer idExpert = ((Integer) request.getAttribute("idExpert"));                                        
                                    %>
                                    <input type="hidden" name="valeur[]" value="">
                                    <input type="hidden" name="idExpert" value="<%out.print(idExpert);%>">
                                    <input style="float: right;" type="submit" class="btn btn-success">
                                </form>
                            </div>                                                        
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
<script src="<c:url value="/resources/assets/js/controller/assignationExpert.js"/>"></script>
<script>
                                                        function getEspece(famille, genre) {
                                                            if ($("#" + "typeGenre-famille-" + famille + "-genre-" + genre).is(":checked")) {
                                                                $.ajax({
                                                                    url: 'getEspece?genre=' + genre,
                                                                    dataType: 'json',
                                                                    success: function (json) {
                                                                        var option = "";
                                                                        for (var i = 0; i < json.length; i++) {
                                                                            option = option + "<li>" + '<input name="valeur[]" type="checkbox" checked value="typeEspece-famille-' + famille + '-genre-' + genre + '-espece-' + json[i] + '" >';
                                                                            option = option + json[i];
                                                                            option = option + "</li>";
                                                                        }
                                                                        $("#espece-" + genre).append(option).trigger('change');
                                                                        $("#titreEspece").show();
                                                                    }
                                                                });
                                                            } else {                                                                
                                                                $("#espece-" + genre).html('<ul id="espece-'+genre+'"></ul>');
//                                                                $("#titreEspece").hide();
                                                            }
                                                        }

</script>
<jsp:include page="/WEB-INF/inc/footer.jsp"/>  