<%@page import="org.wcs.lemurs.modele_vue.VueRoleUtilisateur"%>
<%@page import="java.util.List"%>
<%@page import="org.wcs.lemurs.model.Utilisateur"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/inc/header.jsp"/>  
<main class="site-content" role="main" ng-controller="controller">
    <!--<input type="hidden" value="commentDwc" id="set"/>-->
    <!--<input type="hidden" value="" id="idDwc"/>-->
    <!--<input type="hidden" value="" id="idUtilisateur"/>-->
    <input type="hidden" value="getObservationEnAttenteDeValidation" id="load"/>
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
                <div class="row">
                    <form class="col-md-6" style="float: right; max-width: 70%;" ng-submit="rechercherMulti()" id="form-search">
                        <!-- Search Field -->                                                    
                        <div class="form-group">
                            <div class="input-group">
                                <select id="validation" name="validation" style="max-width: 50%; float: right;" class="form-control" >                                    
                                    <option value="-1">Validation</option>
                                    <option value="1">Validé</option>
                                    <option value="0">En attente de validation</option>                                
                                </select>
                                <input style="max-width: 50%; float: right;" class="form-control" type="text"  name="chercheur" placeholder="Nom prénom du chercheur"/>                            
                                <span class="input-group-btn">
                                    <button class="btn btn-primary btn-success" type="submit"><i class="fa fa-search"></i></button>
                                </span>
                            </div>
                        </div>                        
                    </form>
                    <h1 style="max-width: 30%;" class="titre-page col-md-6">Observation - <span>Liste</span></h1>  
                </div>
                <div class="row">
                    <!-- Stat -->
                    <h5 style="width: 10%; display: inline-block; float: right;" class="stat">Tous (<b>{{liste.length}}</b>)</h5>                    
                    <!-- End Stat -->                    
                    <div class="table-responsive row" id="liste">
                        <form id="formValider">
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
                                        <td class="text-center">Statistique</td>
                                        <td class="text-center">Etat</td>
                                    </tr>
                                    <tr ng-repeat="dwc in liste">                                        
                                        <td class="number text-center"><input name="dwc[]" value="{{dwc.dwc.id}}" type="checkbox"></td>
                                        <td class="number text-center">{{dwc.dwc.id}}</td>
                                        <td class="text-center">{{dwc.dwc.scientificName}}</td>
                                        <td class="text-center">{{dwc.dwc.locality}}</td>
                                        <td class="text-center">{{dwc.dwc.darwinOrder}}</td>
                                        <td class="text-center">{{dwc.dwc.darwinClass}}</td>
                                        <td class="text-center">{{dwc.dwc.genus}}</td>
                                        <td class="text-center">{{dwc.dwc.dateidentified}}(vérifier)</td>
                                        <td class="text-center">780(vérifier)</td>   
                                        <td ng-if="dwc.validation == 1 && dwc.dwc.validationexpert == -1" class="number text-center">en attente</td>
                                        <td ng-if="dwc.validation == 1 && dwc.dwc.validationexpert == 0" class="number text-center">questionnable</td>
                                        <td ng-if="dwc.validation == 1 && dwc.dwc.validationexpert == 1" class="number text-center">validé</td>
                                    </tr>                                                   
                                </tbody>
                            </table>                            
                            <button onclick="showCommentairFirst();" style="float: right; margin-left: 2px;" class="btn btn-success">Questionnable</button>
                            <button onclick="validate(1);" style="float: right; background-color: #4CAF50!important;" class="btn btn-success">Valider</button>                                                        
                        </form>
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
                    <!--<button onclick="showCommentair();"></button>-->
                    <!-- END PAGINATION -->
                </div>
            </div>
        </div>
        <!-- End Contenu -->

    </section>
    <!-- end darwin -->
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
                    <button type='button' class='btn btn-success btn-sm' onclick = 'continueValidate(0,1)' data-dismiss='modal'>Valider</button>
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

</main>
<script src="<c:url value="/resources/assets/js/angular.js"/>"></script>
<script src="<c:url value="/resources/assets/js/appconfig.js"/>"></script>
<script src="<c:url value="/resources/assets/js/controller/liste.js"/>"></script>
<script>
                        function showModal(status) {
                            if(status == 0) $("#modal-ajout-confirmation-questionnable").modal({backdrop: 'static'});
                            else $("#modal-ajout-confirmation-valide").modal({backdrop: 'static'});
                        };
                        
//                        function showCommentair() {
//                            $('#boutonQuestionnable').html("<button type='button' id='boutonQuestionnable' onclick = 'continueValidate(0,1)' class='btn btn-success btn-sm' data-dismiss='modal'>Continuer</button>");
//                            $("#modal-ajout-commentaire-questionnable").modal({backdrop: 'static'});
//                        }
                        function showCommentairFirst() {                            
                            $("#modal-ajout-commentaire-questionnable").modal({backdrop: 'static'});
                            $('#boutonQuestionnable').html("<button type='button' id='boutonQuestionnable' onclick = 'validate(0)' class='btn btn-success btn-sm' data-dismiss='modal'>Continuer</button>");
                        }

                        function validate(status) {                      
                            var valeurs = $('[name="dwc[]"]');
                            var data = "?";
                            for(var i = 0; i < valeurs.length; i++) {
                                if(valeurs[i].checked == true) {
                                    data = data + valeurs[i].name+"="+valeurs[i].value+"&";
                                    console.log(data);
                                }
                            }
                            var temp = $('#commentaires').val();                            
                            if(temp == undefined) temp = "";
                            data = data + "status="+status+"&commentaires="+temp;                            
                            $.ajax({                                
                                type: 'get',
                                url: 'validerListDwc'+data,
//                                dataType: 'json',
//                                enctype: 'multipart/form-data',
                                processData: false,
                                contentType: false,
                                cache: false,
                                success: function (json) {
                                    if(json.etat == 1) {
                                        console.log(json.etat);
                                        window.location = 'profil';
                                    }
                                    else if(json.etat == 0) {
                                        $('.messageMod').html('L\'observation N° '+json.n+' a déja été marqué comme '+json.status+' par '+json.expert);
                                        showModal(status);
                                    }
                                }
                            });
                        };
                        
                        function continueValidate(status, etat) {                      
                            var data = "?continuer=";         
                            var temp = $('#commentaires').val();                            
                            if(temp == undefined) temp = "";
                            data = data + etat+"&status="+status+"&commentaires="+temp;                            
                            $.ajax({                                
                                type: 'get',
                                url: 'continuerValiderListDwc'+data,
                                processData: false,
                                contentType: false,
                                cache: false,
                                success: function (json) {
                                    if(json.etat == 1) {
                                        console.log(json.etat);
                                        window.location = 'profil';
                                    }
                                    else if(json.etat == 0) {
                                        $('.messageMod').html('L\'observation N° '+json.n+' a déja été marqué comme '+json.status+' par '+json.expert);
                                        showModal(status);
                                    }
                                }
                            });
                        };
</script>
<jsp:include page="/WEB-INF/inc/footer.jsp"/>  