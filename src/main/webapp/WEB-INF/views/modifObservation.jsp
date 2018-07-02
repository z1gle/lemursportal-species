<%@page import="org.wcs.lemurs.modele_vue.VueRoleUtilisateur"%>
<%@page import="java.util.List"%>
<%@page import="org.wcs.lemurs.model.Utilisateur"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/inc/header.jsp"/>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.6/angular.min.js"></script>
<main id="controller" class="site-content" role="main" ng-controller="darwin">
    <input type="hidden" id="pageEnCours">

    <!-- darwin -->
    <section id="taxonomie">
        <div class="banner-interieur" style="background:url(resources/assets/img/parallax/fexpert.jpg) no-repeat center center;">
            <div class="container" style="margin-top: 5%;">
                <div class="col-md-6 col-md-offset-3">
                    <!-- Search Form -->
                    <form ng-submit="rechercher(1)">
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
                <div class="row" style="margin-top: 10px;">
                    <form class="col-md-12" style="float: right; max-width: 100%;" id="form-search">
                        <!-- Search Field -->                                                    
                        <div class="form-group">
                            <div class="input-group">     
                                <c:if test="${utilisateur.nom!=''&&utilisateur.nom!=null}">
                                    <select id="validation" name="validationMine" style="max-width: 20%; float: right; max-height: 25px; padding-top: 1px; font-size: 12px;" class="form-control" >                                    
                                        <option value="-999">Données</option>
                                        <option value="-1000">Tous</option>
                                        <option value="1">Validé</option>
                                        <option value="0">Questionnable</option>
                                        <option value="-1">En attente de validation</option>
                                        <option value="-2">Invalide</option>
                                    </select>
                                </c:if>                                
                                <input style="max-width: 20%; float: left; max-height: 25px; padding-top: 1px; font-size: 12px;" class="form-control" type="text"  name="espece" placeholder="Espèce à rechercher"/>                            
                                <ul style=" margin-left: 10px;">
                                    <li style="display: inline; margin-left: 10px;"><input name="etat[]" value="1" type="checkbox" checked> Publique</li>
                                        <c:if test="${utilisateur.nom!=''&&utilisateur.nom!=null}">
                                        <li style="display: inline; margin-left: 10px;"><input name="etat[]" value="0" type="checkbox"> Sensible</li>
                                        </c:if>
                                </ul>                                
                                <span class="input-group-btn">
                                    <button  style="margin-top: -8px; margin-bottom: 0px; max-height: 25px; padding-top: 3px;" ng-click="rechercherAvancee()" class="btn btn-primary btn-success" type="submit"><i class="fa fa-search"></i></button>
                                </span>                                
                            </div>
                        </div>                        
                    </form>                    
                </div>
                <div class="row">
                    <!-- Stat -->                    
                    <h5 style="width: 30%; display: inline-block; float: right;" class="stat " ng-cloak>Page: <b>{{page}}/{{lastPage}}</b> | Observation total: <b>{{total}}</b></h5>                    
                    <jsp:include page="/WEB-INF/inc/loader-spinner.jsp"/>
                    <div class="table-responsive row " ng-cloak id="liste">
                        <form>
                            <table class="table table-hover">
                                <tbody>
                                    <tr>                                                                                                                        
                                        <td class="text-center"></td>
                                        <td class="number text-center">#</td>
                                        <td class="text-center">Nom scientifique </td>
                                        <td class="text-center">Localisation</td>
                                        <td class="text-center">Ordre</td>
                                        <td class="text-center">Classe</td>
                                        <td class="text-center">Genre</td>
                                        <td class="text-center">Date</td>
                                        <td class="text-center">Institution</td>                                        
                                        <td class="text-center">Remarque</td>                                        
                                        <td class="text-center">Etat</td>                                        
                                    </tr>
                                    <tr ng-repeat="dwc in liste">                                        
                                        <td class="number text-center"><input name="dwc[]" value="{{dwc.id}}" type="checkbox"></td>
                                        <td class="number text-center"><a href="detailLemurien?id={{dwc.id}}">{{dwc.id}}</a></td>
                                        <td class="text-center">{{dwc.scientificname}}</td>
                                        <td class="text-center">{{dwc.locality}}</td>
                                        <td class="text-center">{{dwc.darwinorder}}</td>
                                        <td class="text-center">{{dwc.darwinclass}}</td>
                                        <td class="text-center">{{dwc.genus}}</td>
                                        <td class="text-center">{{dwc.dwcyear}}</td>
                                        <td class="text-center">{{dwc.institutioncode}}</td>                                            
                                        <td class="">
                                            <ul>
                                                <li ng-if="dwc.annee == false">vérifier la colonne année</li>
                                                <li ng-if="dwc.accepted_speces == false">vérifier les champs du verbamite speces</li>
                                                <li ng-if="dwc.collecteur == false">vérifier la colonne collecteur</li>
                                                <li ng-if="dwc.gps == false">vérifier la colonne gps</li>
                                            </ul>
                                        </td>                                                                                    
                                        <td ng-if="dwc.validationexpert == -1" class="number text-center">en attente</td>
                                        <td ng-if="dwc.validationexpert == 0" class="number text-center">questionnable</td>
                                        <td ng-if="dwc.validationexpert == 1" class="number text-center">validé</td>                                        
                                        <td ng-if="dwc.lienSource != null" class="text-center"><a href="http://data.rebioma.net/#tab=occ&view=Detail&id={{dwc.idRebioma}}&p=false&page=1&asearch=Id = {{dwc.idRebioma}}&type=all occurrences" target="_blank">Rebioma</a></td>
                                    </tr>                                                   
                                </tbody>
                            </table>                                                        
                        </form>
                    </div>
                    <!-- BEGIN PAGINATION -->
                    <ul class="pagination">
<!--                        <li class="" id="previous"><a href="#" ng-click="rechercher(1)">«</a></li>
                                                <li class="active"><a href="#">1</a></li>                        
                        <li><a href="#" ng-click="rechercher(temp)" ng-repeat="temp in pages">{{temp}}</a></li>
                        <li><a href="#"ng-click="rechercherFin()" id="next">»</a></li>
                        <input type="hidden" id="pageFin">-->
                    </ul>
                    <!-- END PAGINATION -->

                    <div style="float: right; max-width: 350px; margin-top: 18px;" class="form-group">
                        <div class="input-group">
                            <button class="btn btn-danger" data-toggle='modal' data-target='#modal-confirmation-suppression'>Supprimer</button>
                            <button class="btn btn-primary" data-toggle='modal' data-target='#modal-confirmation-changement-status'>Changer le status</button>                            
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- End Contenu -->

    </section>
    <div id='modal-confirmation-suppression' class='modal fade' role='dialog' style='display:none !important' tabindex="-1">
        <div class='modal-dialog'>
            <div class='modal-content'>
                <div class="modal-header">
                    <button data-dismiss='modal' class='close' type='button'>x</button>
                    <h4 class="modal-title"><center>Confirmation de suppression</center></h4>
                </div>
                <div class='modal-body'>
                    <div class='row'>
                        <div class='col-md-10 col-md-offset-1'>                            
                            <div class="col-sm-12">
                                <label id="" class="control-label messageMod">La suppression des données est irréversible.</label>
                                <label class="control-label">Voulez-vous continuer la suppression de ces observations?</label>                                    
                            </div>                                    
                        </div>                         
                    </div>
                </div>
                <div class='modal-footer'>
                    <button type='button' class='btn btn-default btn-sm' data-dismiss='modal'>Annuler</button>
                    <button type='button' id="" onclick="supprimer()" class='btn btn-success btn-sm' data-dismiss='modal'>Continuer</button>
                </div>
            </div>
        </div>
    </div>
    <div id='modal-confirmation-changement-status' class='modal fade' role='dialog' style='display:none !important' tabindex="-1">
        <div class='modal-dialog'>
            <div class='modal-content'>
                <div class="modal-header">
                    <button data-dismiss='modal' class='close' type='button'>x</button>
                    <h4 class="modal-title"><center>Changement de status</center></h4>
                </div>
                <div class='modal-body'>
                    <div class='row'>
                        <div class='col-md-10 col-md-offset-1'>                            
                            <div class="col-sm-12">
                                <label id="" class="control-label messageMod">Veuiller choisir le status désiré</label>                                
                            </div>                                    
                        </div>                         
                    </div>
                </div>
                <div class='modal-footer'>
                    <button type='button' class='btn btn-default btn-sm' data-dismiss='modal'>Annuler</button>
                    <button type='button' onclick="changerStatus(0)" class='btn btn-success btn-sm' data-dismiss='modal'>Publique</button>
                    <button type='button' onclick="changerStatus(1)" class='btn btn-success btn-sm' data-dismiss='modal'>Privée</button>
                </div>
            </div>
        </div>
    </div>    
    <div id='modal-error' class='modal fade' role='dialog' style='display:none !important' tabindex="-1">
        <div class='modal-dialog'>
            <div class='modal-content'>
                <div class="modal-header">
                    <button data-dismiss='modal' class='close' type='button'>x</button>
                    <h4 class="modal-title"><center>Erreur</center></h4>
                </div>
                <div class='modal-body'>
                    <div class='row'>
                        <div class='col-md-10 col-md-offset-1'>                            
                            <div class="col-sm-12">
                                <label id="" class="control-label messageMod">Une erreur est survenu lors de l'execution de la procédure.</label>
                                <label id="" class="control-label messageMod">Veuiller réessayer ultérieurement.</label>
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
    <!-- end darwin -->

</main>
<script src="<c:url value="/resources/assets/js/appconfig.js"/>"></script>
<script src="<c:url value="/resources/assets/js/controller/modifObservations.js"/>"></script>
<script>        
                        function supprimer() {
                            var valeurs = $('[name="dwc[]"]');
                            var data = "?";
                            for (var i = 0; i < valeurs.length; i++) {
                                if (valeurs[i].checked === true) {
                                    data = data + "liste[]" + "=" + valeurs[i].value + "&";
                                }
                            }
                            console.log(data);
                            $.ajax({
                                type: 'post',
                                url: 'observations/delete' + data + 'temp=0',
                                processData: false,
                                contentType: false,
                                cache: false,
                                success: function (json) {
                                    if (json === true) {
                                        location.reload();
                                    } else {
                                        $('#modal-error').modal({backdrop: 'static'});
                                    }
                                },
                                error: function (json) {
                                    $('#modal-error').modal({backdrop: 'static'});
                                }
                            });
                        }
                        ;
                        function changerStatus(status) {
                            var valeurs = $('[name="dwc[]"]');
                            var data = "?";
                            for (var i = 0; i < valeurs.length; i++) {
                                if (valeurs[i].checked === true) {
                                    data = data + "liste[]" + "=" + valeurs[i].value + "&";
                                }
                            }
                            data = data + "status=" + status;
                            console.log(data);
                            $.ajax({
                                type: 'post',
                                url: 'observations/update/status' + data,
                                processData: false,
                                contentType: false,
                                cache: false,
                                success: function (json) {
                                    if (json === true) {
                                        location.reload();
                                    } else {
                                        $('#modal-error').modal({backdrop: 'static'});
                                    }
                                },
                                error: function (json) {
                                    $('#modal-error').modal({backdrop: 'static'});
                                }
                            });
                        }
                        ;
</script>
<jsp:include page="/WEB-INF/inc/footer.jsp"/>  