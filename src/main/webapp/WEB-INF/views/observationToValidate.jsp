<%@page import="org.wcs.lemurs.modele_vue.VueRoleUtilisateur"%>
<%@page import="java.util.List"%>
<%@page import="org.wcs.lemurs.model.Utilisateur"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/inc/header.jsp"/>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<main id="controller" class="site-content" role="main" ng-controller="darwin">
    <input type="hidden" id="pageEnCours">
    <%
        Integer ve = ((Integer) request.getAttribute("validationExpert"));
        if (ve == null || ve > 1 || ve < -1) {
            ve = -2;
        }
    %>
    <input type="hidden" id="ve" value="<%out.print(ve);%>">
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
            <div class="row" style="background-color: beige;margin-left: -15px; margin-bottom: -10px;">                                
            </div>
        </div>
        <!-- Contenu -->
        <div class="vignette-result" style="margin-top: 0px;">
            <div class="container-fluid">
                <div class="row" style="margin-top: 10px;">
                    <!-- Stat -->                    
                    <h1 style="font-size:  14px;font-weight:  600;width:  167px;float: left;margin-top: 9px; color: #a18029;">Liste des obsérvations |</h1>
                    <h5 style="float: right;" class="stat " ng-cloak>Page: <b>{{pageEnCours}}/{{lastPage}}</b> | Observation total: <b>{{liste[0].total}}</b></h5>                                        
                    <input ng-keyup="$event.keyCode == 13 ? rechercheGlobale() : null" title="Global research" id="rechercheGlobale" type="text" style="display: inline-block; float: left; margin-left: 8px;">                                                            
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
                                        <td class="text-center">Nom scientifique </td>
                                        <td class="text-center">Localisation</td>
                                        <td class="text-center">Classe</td>
                                        <td class="text-center">Ordre</td>                                        
                                        <td class="text-center">Genre</td>
                                        <td class="text-center">Date</td>
                                        <td class="text-center">Institution</td>
                                        <%
                                            Integer role = ((Integer) request.getAttribute("role"));
                                            Integer idChercheur = ((Integer) request.getAttribute("idChercheur"));
                                            if (role == 0) {
                                        %>
                                        <td class="text-center">Remarque</td>
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
                                                <li ng-cloak ng-if="dwc.dwc.idUtilisateurUpload == <%out.print(idChercheur);%> && dwc.dwc.annee == false">vérifier la colonne année</li>
                                                <li ng-cloak ng-if="dwc.dwc.idUtilisateurUpload == <%out.print(idChercheur);%> && dwc.dwc.accepted_speces == false"></li>
                                                <li ng-cloak ng-if="dwc.dwc.idUtilisateurUpload == <%out.print(idChercheur);%> && dwc.dwc.collecteur == false">vérifier la colonne collecteur</li>
                                                <li ng-cloak ng-if="dwc.dwc.idUtilisateurUpload == <%out.print(idChercheur);%> && dwc.dwc.gps == false">vérifier la colonne gps</li>
                                            </ul>
                                        </td>    
                                        <%}%>
                                        <%if (expert == 0) {%>
                                        <td ng-cloak ng-if="dwc.dwc.validationexpert == -1" class="number text-center">en attente</td>
                                        <td ng-cloak ng-if="dwc.validation == 1 && dwc.dwc.validationexpert == 0" class="number text-center">questionnable</td>
                                        <td ng-cloak ng-if="dwc.validation == 1 && dwc.dwc.validationexpert == 1" class="number text-center">validé</td>
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
                    <button type='button' class='btn btn-default btn-sm' onclick="continueValidate(1, 0)" data-dismiss='modal'>Annuler</button>
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
                    <button type='button' class='btn btn-default btn-sm' onclick="continueValidate(1, 0)" data-dismiss='modal'>Annuler</button>
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
                    <button type='button' class='btn btn-default btn-sm' onclick="continueValidate(0, 0)" data-dismiss='modal'>Annuler</button>
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
                    <button type='button' class='btn btn-default btn-sm' onclick="$('#commentaires').val('')" data-dismiss='modal'>Annuler</button>
                    <button type='button' id="boutonQuestionnable" class='btn btn-success btn-sm' data-dismiss='modal'>Continuer</button>
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
    function showCommentairFirst() {
        $('#boutonQuestionnable').html("<button type='button' id='boutonQuestionnable' onclick = 'validate(0)' class='btn btn-success btn-sm' data-dismiss='modal'>Continuer</button>");
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
            processData: false,
            contentType: false,
            cache: false,
            success: function (json) {
                if (json.etat == 1) {
                    console.log(json.etat);
                    angular.element('#controller').scope().ffv();
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
                    angular.element('#controller').scope().ffv();
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
<script src="<c:url value="/resources/assets/js/controller/observationToValidateController.js"/>"></script>
<jsp:include page="/WEB-INF/inc/footer.jsp"/>  