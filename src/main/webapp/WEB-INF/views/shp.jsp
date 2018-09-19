<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/inc/header.jsp"/>  
<main class="site-content" role="main" ng-controller="shapefile">
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
                /*background-color: beige;*/
            }        
        }
    </style>
    
    <!--Style for input type file-->
    <style>
        #shapefile-asc {
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
    <!-- darwin -->
    <section id="shapefile">
        <div class="banner-interieur-pliss" style="background:url(resources/assets/img/parallax/fexpert_modif.jpg) no-repeat center center; height: 125px; background-color: beige;"></div>        
        <!-- Contenu -->
        <div class="vignette-result" style="margin-top: 0px;">
            <div class="container-fluid">
                <div class="row header-pliss" style="">
                    <!-- Stat -->                                        
                    <h1 style="font-size:  14px;font-weight:  600;width:  90px;float: left;margin-top: 9px; color: #a18029;">Shapefile |</h1>
                    <h5 style="float: right;" class="stat " ng-cloak>Page: <b>1/1</b> | Espece total: <b>{{total}}</b></h5>                                        
                    <%
                        Integer showAddButton = (Integer) request.getAttribute("showAddButton");
                        if (showAddButton == 0) {
                    %>
                    <a data-toggle='modal' data-target='#modal-add-edit-shapefile' href="#" title="add new shapefile" style="width: 3%; display: inline-block; float: left; margin-right: 2px;margin-top: 2px;" ng-click="ajouter()" class="btn"><i class="fa fa-plus"></i></a>
                        <%}%>
                    <input ng-model="taxonomi.test" name="search" ng-keyup="$event.keyCode == 13 ? rechercher() : null" title="Global research" id="rechercheGlobale" type="text" style="display: inline-block; float: left; margin-left: 8px;margin-top: 5px;">                    
                    <!-- End Stat -->                    
                </div>                
                <div class="row">
                    <div class="panel panel-primary">                            
                        <!-- TABLE RESULT -->                            
                        <div class="table-responsive">
                            <jsp:include page="/WEB-INF/inc/loader-spinner.jsp"/>
                            <table class="table table-hover">
                                <tbody>                                                                                                            
                                    <tr style="background-color: black; color: #deaa45; font-weight: 700;">
                                        <td class="number text-center">Id</td>                                        
                                        <td class="text-center">Shapetable</td>
                                        <!--<td class="text-center">Categorie</td>-->
                                        <td class="text-center">Shapeprecision</td>
                                        <td class="text-center">Nom champ geometrique</td>
                                        <td class="text-center">Path</td>                                        
                                        <%if (showAddButton == 0) {%>
                                        <td class="text-center">Action</td>
                                        <%}%>                                        
                                    </tr>
                                    <tr ng-repeat="mod in shapefiles">
                                        <td class="number text-center">{{mod.id}}</td>
                                        <td class="text-center">{{mod.shapeTable}}</td>
                                        <!--<td class="text-center">{{mod.idCategorie}}</td>-->
                                        <td class="text-center">{{mod.shapePrecision}}</td>                                        
                                        <td class="text-center">{{mod.nomChampGeometrique}}</td>
                                        <td class="text-center">{{mod.path}}</td>                                        
                                                <%if (showAddButton == 0) {%>
                                        <td class="text-center del">
                                            <a href="#" data-toggle='modal' data-target='#modal-add-edit-shapefile' ng-click="editer(mod.id)"><i class="fa fa-edit"></i></a>&nbsp;&nbsp;
                                            <a href="#" data-toggle='modal' data-target='#modal-delete-shapefile' ng-click="del(mod.id)"><i class="fa fa-remove"></i></a>
                                        </td>
                                        <%}%>
                                    </tr>                                                   
                                </tbody>
                            </table>
                        </div>                            
                    </div>
                    <!-- BEGIN PAGINATION -->
                    <ul class="pagination" style="margin: 0px;">
                        <li class="" id="previous"><a href="#" ng-click="rechercher(1)">«</a></li>
                        <!--                        <li class="active"><a href="#">1</a></li>                        -->
                        <li><a href="#" ng-click="rechercher(temp)" ng-repeat="temp in pages">{{temp}}</a></li>
                        <li><a href="#"ng-click="rechercherFin()" id="next">»</a></li>
                        <input type="hidden" id="pageFin">
                    </ul>
                    <!-- END PAGINATION -->
                </div>
            </div>
        </div>
        <!-- End Contenu -->

    </section>
    <!-- end shapefile -->

    <!--Modal edit or new-->
    <div id='modal-add-edit-shapefile' class='modal fade' role='dialog' style='display:none !important' tabindex="-1">
        <div class='modal-dialog'>
            <div class='modal-content'>
                <div class="modal-header">
                    <button data-dismiss='modal' class='close' type='button'>x</button>
                    <h4 class="modal-title"><center>Ajout/Modification</center></h4>
                </div>
                <div class='modal-body row'>
                    <form id="uploadForm" method="POST" class="col-md-offset-1 col-md-11" enctype="multipart/form-data">
                        <input type="hidden" id="idShapefile" value="-1">
                        Name :
                        <input type="text" class="form-control" name="name" id="newShapefileName" style="width: 92%; border-radius: 4px; height: 43px;"><br>
<!--                        Categorie :
                        <input type="text" class="form-control" name="categorie" id="newShapefileCategorie" style="width: 92%; border-radius: 4px; height: 43px;"><br>-->
                        <label for="shapefile-asc" id="buttonlabel">
                            <span role="button" aria-controls="filename" tabindex="0">
                                File path
                            </span>
                        </label>
                        <input type="file" name="excelfile" ng-model="file" id="shapefile-asc">
                        <label for="filename" class="hide">
                            uploaded file
                        </label>
                        <input type="text" id="filename" autocomplete="off" readonly placeholder="no file uploaded">  <br>                        
                    </form>
                </div>
                <div class='modal-footer'>                        
                    <input class="btn btn-success btn-sm" type="submit" id="publique" ng-click="upload()" value="Importer">                        
                    <button type='button' class='btn btn-default btn-sm' data-dismiss='modal'>Annuler</button>                    
                </div>                    
            </div>
        </div>
    </div>    
    
    <!--Modal delete-->
    <div id='modal-delete-shapefile' class='modal fade' role='dialog' style='display:none !important' tabindex="-1">
        <div class='modal-dialog'>
            <input type="hidden" id="idToDelete" value="-1">
            <div class='modal-content'>
                <div class="modal-header">
                    <button data-dismiss='modal' class='close' type='button'>x</button>
                    <h4 class="modal-title"><center>Confirmation suppression</center></h4>
                </div>
                <div class='modal-body row col-md-offset-1'>
                    Cette action est irreversible, voulez-vous vraiment supprimer ce shapefile?
                </div>
                <div class='modal-footer'>                        
                    <input class="btn btn-success btn-sm" type="submit" id="publique" ng-click="deleteModel()" value="OUI">                        
                    <button type='button' class='btn btn-default btn-sm' data-dismiss='modal'>NON</button>                    
                </div>                    
            </div>
        </div>
    </div>    




</main>
<script src="<c:url value="/resources/assets/js/angular.js"/>"></script>
<script src="<c:url value="/resources/assets/js/appconfig.js"/>"></script>
<script src="<c:url value="/resources/assets/js/controller/shapefilecontroller.js"/>"></script>
<!--Import file style js-->
<script>
                        // trigger upload on space & enter
// = standard button functionality
                        $('#buttonlabel span[role=button]').bind('keypress keyup', function (e) {
                            if (e.which === 32 || e.which === 13) {
                                e.preventDefault();
                                $('#shapefile-asc').click();
                            }
                        });

// return chosen filename to additional input
                        $('#shapefile-asc').change(function (e) {
                            var filename = $('#shapefile-asc').val().split('\\').pop();
                            $('#filename').val(filename);
                            $('#filename').attr('placeholder', filename);
                            $('#filename').focus();
                        });
</script>
<jsp:include page="/WEB-INF/inc/footer.jsp"/>  