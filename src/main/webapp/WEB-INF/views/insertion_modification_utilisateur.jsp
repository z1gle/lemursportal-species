<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/inc/header.jsp"/>  
<main class="site-content" role="main" ng-controller="object">
    <%
        Integer idUtilisateur = ((Integer) request.getAttribute("idUtilisateur"));
    %>
    <input type="hidden" value="<%out.print(idUtilisateur);%>" id="idDwc"/>
    <input type="hidden" value="getDetailUtilisateur" id="load"/>
    <input type="hidden" value="listeUtilisateur" id="redirection"/>
    <input type="hidden" value="saveUtilisateur" id="set"/>
    <!-- taxonomie -->
    <section id="taxonomie">
        <!--        <div class="banner-interieur" style="background:url(img/parallax/fexpert.jpg) no-repeat center center;">
                    <div class="container" style="margin-top: 5%;">
                        <div class="col-md-6 col-md-offset-3">
                             Search Form 
        
                             End of Search Form 
                        </div>
                    </div>
                </div>-->
        <!-- Contenu -->
        <div class="detail-result">
            <div class="container">
                <h1 class="titre-page">Utilisateur - <span>Edition</span></h1>
                <div class="row form-group">
                    <form>
                        <div class="col-md-12 insert-modif input-group">
                            <div class="col-md-12">
                                <p><label>Nom :</label><input class="form-control" type="text" value="{{object.nom}}" ng-model="object.nom"></p>
                                <p><label>Prenom :</label><input class="form-control" type="text" value="{{object.prenom}}" ng-model="object.prenom"></p>
                                <p><label>Identifiant :</label><input class="form-control" type="text" value="{{object.login}}" ng-model="object.login"></p>
                                <p><label>Mot de passe :</label><input class="form-control" type="password" value="{{object.motdepasse}}" ng-model="object.motdepasse"></p>
                                <p><label>E-mail :</label><input class="form-control" type="text" value="{{object.email}}" ng-model="object.email"></p>
                                <p><label>Téléphone :</label><input class="form-control" type="text" value="{{object.telephone}}" ng-model="object.telephone"></p>
                                <p><label>Poste :</label><input class="form-control" type="text" value="{{object.poste}}" ng-model="object.poste"></p>
                                <p><label>Localisation :</label><input class="form-control" type="text" value="{{object.localisation}}" ng-model="object.localisation"></p>
                                <p><label>Biographie :</label><input class="form-control" type="text" value="{{object.biographie}}" ng-model="object.biographie"></p>                                
                            </div>                            
                        </div>
                        <div class="pull-left divider">
                            <button type="button" class="btn btn-primary" ng-click="getRole_Administrateur(object.id)">  Assignation rôle</button>
                            <button type="button" class="btn btn-primary" ng-click="checkRole_Administrateur(object.id)">  Assignation domaine d'expertise</button>
                        </div>
                        <div class="pull-right divider">
                            <button type="button" class="btn btn-primary" ng-click="save()"><span class="fa fa-save"></span>  &nbsp;Enregistrer</button>
                        </div> 
                    </form> 
                </div>
            </div>
        </div>
        <!-- End Contenu -->

    </section>
    <div id='modal-remarque' class='modal fade' role='dialog' style='display:none !important' tabindex="-1">
        <div class='modal-dialog'>
            <div class='modal-content'>
                <div class="modal-header">
                    <button data-dismiss='modal' class='close' type='button'>x</button>
                    <h4 class="modal-title"><center>Remarque</center></h4>
                </div>
                <div class='modal-body'>
                    <div class='row'>
                        <div class='col-md-10 col-md-offset-1'>                            
                            <div class="col-sm-12">
                                Cette action ne peut être effectué car cet utilisateur n'est pas un expert vérificateur.
                            </div>                                    
                        </div>
                    </div>
                </div>
                <div class='modal-footer'>
                    <button type='button' class='btn btn-default btn-sm' data-dismiss='modal'>OK</button>                                     
                </div>
            </div>
        </div>
    </div>
    <div id='modal-role' class='modal fade' role='dialog' style='display:none !important' tabindex="-1">
        <div class='modal-dialog'>
            <div class='modal-content'>
                <div class="modal-header">
                    <button data-dismiss='modal' class='close' type='button'>x</button>
                    <h4 class="modal-title"><center>Assignation des rôles</center></h4>
                </div>
                <div class='modal-body'>
                    <div class='row'>
                        <div class='col-md-10 col-md-offset-1'>         
<!--                            <div class="col-sm-12">
                                <input ng-click="checkAll()" type="checkbox" id="checkAll">Tout Sélectionner
                            </div>
                            <br>
                            <br>-->
                            <div class="col-sm-12">
                                <input id="administrateur" type="checkbox" name="col[]" value="1">Administrateur
                            </div>   
                            <div class="col-sm-12">
                                <input id="moderateur" type="checkbox" name="col[]" value="2">Modérateur
                            </div>   
                            <div class="col-sm-12">
                                <input id="xp" type="checkbox" name="col[]" value="3">Expert vérificateur
                            </div>   
                            <div class="col-sm-12">
                                <input id="chercheur" type="checkbox" name="col[]" value="4">Chercheur    
                            </div>                               
                        </div>                                    
                    </div>
                </div>
                <div class='modal-footer'>
                    <button type='button' class='btn btn-default btn-sm' data-dismiss='modal'>Annuler</button>
                    <button type='button' id="boutonQuestionnable" ng-click="setRole(object.id)" class='btn btn-success btn-sm' data-dismiss='modal'>Continuer</button>
                </div>
            </div>            
        </div>
    </div>
</div>
<!-- end taxonomie -->

</main>
<script src="<c:url value="/resources/assets/js/angular.js"/>"></script>
<script src="<c:url value="/resources/assets/js/appconfig.js"/>"></script>
<script src="<c:url value="/resources/assets/js/controller/modification.js"/>"></script>
<jsp:include page="/WEB-INF/inc/footer.jsp"/>  