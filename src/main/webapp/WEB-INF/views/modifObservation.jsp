<%@page import="org.wcs.lemurs.modele_vue.VueRoleUtilisateur"%>
<%@page import="java.util.List"%>
<%@page import="org.wcs.lemurs.model.Utilisateur"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/inc/header.jsp"/>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.6/angular.min.js"></script>
<main id="controller" class="site-content" role="main" ng-controller="darwin">
    <input type="hidden" id="pageEnCours">

    <style>
        /*adjust the height of all part of the row*/        
        .row {
            margin-right: 0;
            margin-left: 0;
        }  
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

        /* The following CSS not required for the badge styled checkboxes: */
        section + section  {
            margin-top:20px;
        }

        label + .checkbox  {
            margin-top:0;   
        }
    </style>

    <!-- darwin -->
    <section id="taxonomie" style="padding-bottom: 0px;">
        <div class="banner-interieur-pliss" style="background:url(resources/assets/img/parallax/fexpert_modif.jpg) no-repeat center center; height: 125px; background-color: beige;"></div>
        <!-- Contenu -->        
        <div class="container-fluid" style="overflow: hidden;">
            <div class="row" style="">                               
                <div class="row">
                    <!-- Stat -->                    
                    <div class="clearfix"></div>
                    <div class="row" style="width: 103%;background-color: beige;margin-left: -15px;">
                        <div class="col-md-8 col-sm-8">
                            <form id="form-search">
                                <!-- Search Field -->                                                    
                                <div class="form-group" style="margin-bottom: 0px; margin-top: 5px;">
                                    <div class="input-group" style="width: 100%;">                                             
                                        <div class="form-group badge-checkboxes">                                            
                                            <div>
                                                <input id="species" ng-keyup="$event.keyCode == 13 ? search(1) : null" type="text" placeholder="search" class="checkbox-inline" style="height: 20px; border-radius: 15px; width: 35%; border-style: solid;border-width: 1px;">
                                                <select id="etat" ng-model="modelePourFaireMarcherOnChange.id" ng-change="search(1)" class="checkbox-inline" style="height: 20px; border-radius: 15px; border-style: solid;border-width: 1px; width: 35%;">
                                                    <option value="-999">Tous</option>
                                                    <option value="1">Valide</option>
                                                    <option value="-1">En attente de validation</option>
                                                    <option value="0">Questionnable</option>
                                                    <option value="-2">Invalide</option>
                                                </select>
<!--                                                <select ng-model="ctrl.value" ng-change="ctrl.change()">
                                                    <option ng-repeat="v in ctrl.values" ng-selected="v == ctrl.value">{{ v}}</option>
                                                </select>  -->
                                                <label style="float: right;" class="checkbox-inline">
                                                    <input ng-click="search(1)" id="publique" name="etat[]" type="checkbox" value="true" checked="">
                                                    <span class="badge" style="margin-left: 15px;">Publique</span>
                                                </label>
                                                <label style="float: right;" class="checkbox-inline">
                                                    <input ng-click="search(1)" id="privee" name="etat[]" type="checkbox" value="true">
                                                    <span class="badge" style="">Privée</span>
                                                </label>      
                                            </div>                                            
                                        </div>
                                    </div>
                                </div>                        
                            </form>
                        </div>
                        <div class="col-md-4 col-sm-4" >
                            <h5 style="float: right;" class="stat " ng-cloak>Page: <b>{{page}}/{{lastPage}}</b> | Observation total: <b>{{total}}</b></h5>                    
                        </div>
                    </div>                        
                    <jsp:include page="/WEB-INF/inc/loader-spinner.jsp"/>
                    <div class="table-responsive row " ng-cloak id="liste" style="width: 103%; margin-left: -15px;">
                        <form>
                            <table class="table table-hover">
                                <tbody>
                                    <tr style="background-color: black; color: #deaa45; font-weight: 700;">                                                                                                                        
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
                        <li class=""><a href="" ng-click="findAll(pagination.debut)"><i class="fa fa-angle-double-left"></i></a></li>
                        <li class=""><a href="" ng-click="findAll(pagination.previous)"><i class="fa fa-angle-left"></i></a></li>
                        <!--<div ng:repeat="page in pagination.table">-->
                        <li ng:repeat="page in pagination.table" class=""><a href="" ng-click="findAll(page)">{{page}}</a></li>
                        <!--</div>-->
                        <li class=""><a href="" ng-click="findAll(pagination.next)"><i class="fa fa-angle-right"></i></a></li>
                        <li class=""><a href="" ng-click="findAll(pagination.fin)"><i class="fa fa-angle-double-right"></i></a></li>
                    </ul>
                    <!-- END PAGINATION -->

                    <div style="float: right; max-width: 350px; margin-top: 18px;" class="form-group">
                        <div class="input-group">
                            <button class="btn btn-danger" data-toggle='modal' data-target='#modal-confirmation-suppression'>Supprimer</button>
                            <button class="btn btn-primary" data-toggle='modal' data-target='#modal-confirmation-changement-status'>Changer le status</button>                            
                        </div>
                    </div>
                </div>
                <!--                </div>-->
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
<!--<link rel="stylesheet" href="resources/assets/css/bootstrap-multiselect.css" type="text/css">
<script type="text/javascript" src="resources/assets/js/bootstrap-multiselect.js"></script>-->
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