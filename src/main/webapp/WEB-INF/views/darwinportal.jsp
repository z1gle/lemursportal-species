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
                    <ul class="nav nav-tabs">
                        <li class="" id="tab-pellicule"><a href="" onclick="pellicule()">Pellicule</a></li>
                        <li class="" id="tab-liste"><a href="" onclick="liste()">Liste</a></li>
                    </ul>
                    <!-- End Stat -->
                    <div class="col-md-12" id="pellicule">                        
                        <!-- Vignette -->
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-12" ng-repeat="dwc in liste">
                            <div class="vignette">
                                <img src="<c:url value="/resources/assets/img/fexpert.jpg"/>" alt="{{dwc.dwc.scientificname}}" >
                                <div class="offer">
                                    <span><i class="fa fa-comment"></i>120</span>
                                    <span class="pull-right valid"><i class="fa fa-check"></i></span>
                                </div>
                                <div class="detail">
                                    <a href="detailLemurien?id={{dwc.dwc.id}}">
                                        <h3>{{dwc.dwc.scientificname}}</h3>
                                        <p><i class="fa fa-angle-down"></i></p>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <!-- End Vignette -->
                    </div>
                    <div class="table-responsive row" id="liste">
                        <form>
                            <table class="table table-hover">
                                <tbody>
                                    <tr>                                                                                
                                        <%
                                            Integer expert = ((Integer) request.getAttribute("expert"));
                                            if (expert == 0) {
                                        %>
                                        <td class="text-center"></td>
                                        <%}%>
                                        <td class="number text-center">#</td>
                                        <td class="text-center">Nom scientifique </td>
                                        <td class="text-center">Localisation</td>
                                        <td class="text-center">Ordre</td>
                                        <td class="text-center">Classe</td>
                                        <td class="text-center">Genre</td>
                                        <td class="text-center">Date</td>
                                        <td class="text-center">Statistique</td>
                                    </tr>
                                    <tr ng-repeat="dwc in liste">
                                        <%if (expert == 0) {%>
                                        <td ng-if="dwc.validation == 1" class="number text-center"><input name="dwc[]" value="{{dwc.dwc.id}}" type="checkbox"></td>
                                        <td ng-if="dwc.validation == 0" class="number text-center"></td>
                                        <%}%>
                                        <td class="number text-center">{{dwc.dwc.id}}</td>
                                        <td class="text-center">{{dwc.dwc.scientificname}}</td>
                                        <td class="text-center">{{dwc.dwc.locality}}</td>
                                        <td class="text-center">{{dwc.dwc.darwinorder}}</td>
                                        <td class="text-center">{{dwc.dwc.darwinclass}}</td>
                                        <td class="text-center">{{dwc.dwc.genus}}</td>
                                        <td class="text-center">{{dwc.dwc.dateidentified}}(vérifier)</td>
                                        <td class="text-center">780(vérifier)</td>                                            
                                    </tr>                                                   
                                </tbody>
                            </table>
                            <%if (expert == 0) {%>
                            <button onclick="validate(0);" style="float: right; margin-left: 2px;" class="btn btn-success">Questionnable</button>
                            <button onclick="validate(1);" style="float: right; background-color: #4CAF50!important;" class="btn btn-success">Valider</button>
                            <%}%>
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
                    <%
                        Integer role = ((Integer) request.getAttribute("role"));
                        if (role == 0) {
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
                    <button type='button' class='btn btn-success btn-sm' onclick="continueValidate(0, 1)" data-dismiss='modal'>Continuer</button>
                </div>
            </div>
        </div>
    </div>
    <!-- end darwin -->

</main>
<script src="<c:url value="/resources/assets/js/angular.js"/>"></script>
<script src="<c:url value="/resources/assets/js/appconfig.js"/>"></script>
<script src="<c:url value="/resources/assets/js/controller/darwincontroller.js"/>"></script>
<script>
                        function liste() {
                            document.getElementById("pellicule").style.display = 'none';
                            document.getElementById("liste").style.display = 'block';
                            document.getElementById("tab-liste").class = 'active';
                            document.getElementById("tab-pellicule").class = '';
//        $("liste").show();        
//        $("pellicule").hide();
                        }
                        function pellicule() {
//        $("liste").hide();
//        $("pellicule").show();
                            document.getElementById("pellicule").style.display = 'block';
                            document.getElementById("liste").style.display = 'none';
                            document.getElementById("tab-pellicule").class = 'active';
                            document.getElementById("tab-liste").class = '';
                        }
                        pellicule();
                        function showModal(status) {
                            if (status == 0)
                                $("#modal-ajout-confirmation-questionnable").modal({backdrop: 'static'});
                            else
                                $("#modal-ajout-confirmation-valide").modal({backdrop: 'static'});
                        }
                        ;

                        function validate(status) {
                            var valeurs = $('[name="dwc[]"]');
                            var data = "?";
                            for (var i = 0; i < valeurs.length; i++) {
                                if (valeurs[i].checked == true) {
                                    data = data + valeurs[i].name + "=" + valeurs[i].value + "&";
                                    console.log(data);
                                }
                            }
                            data = data + "status=" + status;
                            $.ajax({
                                type: 'get',
                                url: 'validerListDwc' + data,
//                                dataType: 'json',
//                                enctype: 'multipart/form-data',
                                processData: false,
                                contentType: false,
                                cache: false,
                                success: function (json) {
                                    if (json.etat == 1) {
                                        console.log(json.etat);
                                        location.reload();
                                    } else if (json.etat == 0) {
                                        $('.messageMod').html('L\'observation N° ' + json.n + ' a déja été marqué comme ' + json.status + ' par ' + json.expert);
                                        showModal(status);
                                    }
                                }
                            });
                        }
                        ;

                        function continueValidate(status, etat) {
                            var data = "?continuer=";
                            data = data + etat + "&status=" + status;
                            $.ajax({
                                type: 'get',
                                url: 'continuerValiderListDwc' + data,
                                processData: false,
                                contentType: false,
                                cache: false,
                                success: function (json) {
                                    if (json.etat == 1) {
                                        console.log(json.etat);
                                        location.reload();
                                    } else if (json.etat == 0) {
                                        $('.messageMod').html('L\'observation N° ' + json.n + ' a déja été marqué comme ' + json.status + ' par ' + json.expert);
                                        showModal(status);
                                    }
                                }
                            });
                        }
                        ;
</script>
<jsp:include page="/WEB-INF/inc/footer.jsp"/>  