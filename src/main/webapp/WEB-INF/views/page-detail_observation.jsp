<%@page import="java.util.List"%>
<%@page import="org.wcs.lemurs.model.Utilisateur"%>
<%@page import="org.wcs.lemurs.model.DarwinCore"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/inc/header.jsp"/>  
<main class="site-content" role="main" ng-controller="controller">
    <%
        DarwinCore dwc = (DarwinCore) request.getAttribute("dwc");
        Utilisateur u = (Utilisateur) request.getAttribute("utilisateur");
    %>
    <input type="hidden" value="<%out.print(dwc.getId());%>" id="idDwc"/>
    <input type="hidden" value="commentDwc" id="set"/>    
    <input type="hidden" value="<%out.print(dwc.getIdUtilisateurUpload());%>" id="idUtilisateur"/>
    <input type="hidden" value="getCommentDwc" id="load"/>
    <!--<input type="hidden" value="getDetailTaxo" id="load"/>-->
    <!-- dwcnomie -->
    <section id="dwcnomie">
        <div class="banner-interieur" style="background:url(resources/assets/img/parallax/fexpert.jpg) no-repeat center center;">
            <div class="container" style="margin-top: 5%;">
                <div class="col-md-6 col-md-offset-3">
                    <!-- Search Form -->
                    <form role="form">
                        <!-- Search Field -->
                        <div class="row search-header">
                            <h4 class="text-left">Rechercher</h4>
                            <div class="form-group">
                                <div class="input-group">
                                    <input class="form-control" type="text" name="search" placeholder="nom scientifique de l'espèce" required/>
                                    <span class="input-group-btn">
                                        <button class="btn btn-primary btn-success" type="button"><i class="fa fa-search"></i></button>
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
        <div class="detail-obs">
            <div class="container">
                <h1 class="titre-page">Details - <span><%out.print(dwc.getAcceptednameusage());%></span></h1>
                <div class="col-md-3">
                    <%
                        List<String> remarques = null;
                        Integer chercheur = ((Integer) request.getAttribute("chercheur"));
                        Integer expert = ((Integer) request.getAttribute("expert"));
                        if (expert == 0 || (chercheur == 0 && dwc.getIdUtilisateurUpload() == u.getId())) {
                            remarques = (List<String>) request.getAttribute("remarques");
                            if (!remarques.isEmpty()) {
                                for (String s : remarques) {
                    %>
                    <span class="tags" style="background: #d9534f;"><%out.print(s);%></span><br>
                    <%
                                }
                            }
                        }
                    %>
                    <div id="myCarousel" class="carousel slide">
                        <!-- Indicators -->
                        <div class="carousel-inner">
                            <div class="item active">
                                <img src="resources/assets/img/bg-lemurs.jpg" class="img-responsive">
                            </div>
                            <div class="item">
                                <img src="resources/assets/img/lem.jpg" class="img-responsive">
                            </div>
                            <div class="item">
                                <img src="resources/assets/img/sclaters-lemur.jpg" class="img-responsive">
                            </div>
                        </div>
                        <!-- Controls -->
                        <a class="left carousel-control" href="#myCarousel" data-slide="prev">
                            <span class="icon-prev"></span>
                        </a>
                        <a class="right carousel-control" href="#myCarousel" data-slide="next">
                            <span class="icon-next"></span>
                        </a>  
                    </div>
                    <p class="name-img"><%out.print(dwc.getAcceptednameusage());%> - Mammadia</p>
                    <!-- /.carousel -->


                    <div class="map-detail-obs">
                        <a href="#"><img class="img-responsive img-rounded" src="resources/assets/img/carte.png"/></a>
                    </div>
                    <p align="center">
                        <button type="button" class="btn btn-primary"><span class="fa fa-download"></span> Télécharger la fiche</button>
                    </p>
                    <%if (expert == 0 || (chercheur == 0 && dwc.getIdUtilisateurUpload() == u.getId())) {%>
                    <br>
                    <p align="center">
                        <button type="button" onclick="showCommentaires()" class="btn btn-primary"> Commentaires</button>
                    </p>
                    <%}%>
                    <p>&nbsp;</p>                                           

                </div>

                <div class="col-md-9">
                    <div class="desc-primary">
                        <p class="titre">
                            Record-Level Terms
                        </p>
                        <ul>
                            <li>InstitutionCode: <%out.print(dwc.getInstitutioncode());%></li>
                            <li>CollectionCode: <%out.print(dwc.getCollectioncode());%></li>
                            <li>DatasetName: <%out.print(dwc.getDatasetname());%></li>
                            <li>OwnerInstitutionCode: <%out.print(dwc.getOwnerinstitutioncode());%></li>
                            <li>BasisOfRecord: <%out.print(dwc.getBasisofrecord());%></li>
                            <li>InformationWithheld: <%out.print(dwc.getInformationwithheld());%></li>
                            <li>DataGeneralizations: <%out.print(dwc.getDatageneralizations());%></li>
                            <li>DynamicProperties: <%out.print(dwc.getDynamicproperties());%></li>                            
                        </ul>
                    </div>

                    <div class="desc-primary">
                        <p class="titre">
                            Taxon
                        </p>
                        <ul>
                            <li>TaxonID: <%out.print(dwc.getTaxonId());%></li>
                            <li>ScientificNameID: <%out.print(dwc.getScientificNameId());%></li>
                            <li>AcceptedNameUsageID: <%out.print(dwc.getAcceptedNameUsageId());%></li>
                            <li>ParentNameUsageID: <%out.print(dwc.getParentNameUsageId());%></li>
                            <li>OriginalNameUsageID: <%out.print(dwc.getOriginalNameUsageId());%></li>
                            <li>NameAccordingToID: <%out.print(dwc.getNameAccordingToId());%></li>
                            <li>NamePublishedInID: <%out.print(dwc.getNamePublishedInId());%></li>
                            <li>TaxonConceptID: <%out.print(dwc.getTaxonConceptId());%></li>
                            <li>ScientificName: <%out.print(dwc.getScientificname());%></li>
                            <li>AcceptedNameUsage: <%out.print(dwc.getAcceptednameusage());%></li>
                            <li>ParentNameUsage: <%out.print(dwc.getParentNameUsage());%></li>
                            <li>OriginalNameUsage: <%out.print(dwc.getOriginalNameUsage());%></li>
                            <li>NameAccordingTo: <%out.print(dwc.getNameAccordingTo());%></li>
                            <li>NamePublishedIn: <%out.print(dwc.getNamePublishedIn());%></li>
                            <li>HigherClassification: <%out.print(dwc.getHigherclassification());%></li>
                            <li>Kingdom: <%out.print(dwc.getKingdom());%></li>
                            <li>Phylum: <%out.print(dwc.getPhylum());%></li>
                            <li>Class: <%out.print(dwc.getDarwinclass());%></li>
                            <li>Order: <%out.print(dwc.getDarwinorder());%></li>
                            <li>Family: <%out.print(dwc.getFamily());%></li>
                            <li>Genus: <%out.print(dwc.getGenus());%></li>
                            <li>Subgenus: <%out.print(dwc.getSubgenus());%></li>
                            <li>SpecificEpithet: <%out.print(dwc.getSpecificepithet());%></li>
                            <li>InfraspecificEpithet: <%out.print(dwc.getInfraspecificepithet());%></li>
                            <li>TaxonRank: <%out.print(dwc.getTaxonrank());%></li>
                            <li>VerbatimTaxonRank: <%out.print(dwc.getVerbatimtaxonrank());%></li>
                            <li>ScientificNameAuthorship: <%out.print(dwc.getScientificnameauthorship());%></li>
                            <li>VernacularName: <%out.print(dwc.getVernacularname());%></li>
                            <li>NomenclaturalCode: <%out.print(dwc.getNomenclaturalcode());%></li>
                            <li>TaxonomicStatus: <%out.print(dwc.getTaxonomicStatus());%></li>
                            <li>NomenclaturalStatus: <%out.print(dwc.getNomenclaturalStatus());%></li>
                            <li>TaxonRemarks: <%out.print(dwc.getTaxonremarks());%></li>
                        </ul>
                    </div> 

                    <div class="desc-primary">
                        <p class="titre">
                            Occurrence
                        </p>
                        <ul>
                            <li>OccurrenceID: <%out.print(dwc.getOccurrenceId());%></li>
                            <li>CatalogNumber: <%out.print(dwc.getCatalognumber());%></li>
                            <li>OccurrenceDetails: <%out.print(dwc.getOccurrenceDetails());%></li>
                            <li>OccurrenceRemarks: <%out.print(dwc.getOccurrenceremarks());%></li>
                            <li>RecordNumber: <%out.print(dwc.getRecordnumber());%></li>
                            <li>RecordedBy: <%out.print(dwc.getRecordedby());%></li>
                            <li>IndividualID: <%out.print(dwc.getIndividualId());%></li>
                            <li>IndividualCount: <%out.print(dwc.getIndividualcount());%></li>
                            <li>Sex: <%out.print(dwc.getSex());%></li>
                            <li>LifeStage: <%out.print(dwc.getLifestage());%></li>
                            <li>ReproductiveCondition: <%out.print(dwc.getReproductivecondition());%></li>
                            <li>Behavior: <%out.print(dwc.getBehavior());%></li>
                            <li>EstablishmentMeans: <%out.print(dwc.getEstablishmentMeans());%></li>
                            <li>OccurrenceStatus: <%out.print(dwc.getOccurrenceStatus());%></li>
                            <li>Preparations: <%out.print(dwc.getPreparations());%></li>
                            <li>Disposition: <%out.print(dwc.getDisposition());%></li>
                            <li>OtherCatalogNumbers: <%out.print(dwc.getOthercatalognumbers());%></li>
                            <li>PreviousIdentifications: <%out.print(dwc.getPreviousidentifications());%></li>
                            <li>AssociatedMedia: <%out.print(dwc.getAssociatedmedia());%></li>
                            <li>AssociatedReferences: <%out.print(dwc.getAssociatedreferences());%></li>
                            <li>AssociatedOccurrences: <%out.print(dwc.getAssociatedoccurrences());%></li>
                            <li>AssociatedSequences: <%out.print(dwc.getAssociatedsequences());%></li>
                            <li>AssociatedTaxa: <%out.print(dwc.getAssociatedtaxa());%></li>
                        </ul> 
                    </div> 

                    <div class="desc-primary">
                        <p class="titre">
                            Event
                        </p>
                        <ul>
                            <li>EventID <%out.print(dwc.getEventId());%></li>
                            <li>SamplingProtocol <%out.print(dwc.getSamplingprotocol());%></li>
                            <li>SamplingEffort <%out.print(dwc.getSamplingeffort());%></li>
                            <li>EventDate <%out.print(dwc.getEventdate());%></li>
                            <li>EventTime <%out.print(dwc.getEventtime());%></li>
                            <li>StartDayOfYear <%out.print(dwc.getStartdayofyear());%></li>
                            <li>EndDayOfYear <%out.print(dwc.getEnddayofyear());%></li>
                            <li>Year <%out.print(dwc.getDwcyear());%></li>
                            <li>Month <%out.print(dwc.getDwcmonth());%></li>
                            <li>Day <%out.print(dwc.getDwcday());%></li>
                            <li>VerbatimEventDate <%out.print(dwc.getVerbatimeventdate());%></li>
                            <li>Habitat <%out.print(dwc.getHabitat());%></li>
                            <li>FieldNumber <%out.print(dwc.getFieldnumber());%></li>
                            <li>FieldNotes <%out.print(dwc.getFieldnotes());%></li>
                            <li>EventRemarks <%out.print(dwc.getEventremarks());%></li>
                        </ul> 
                    </div> 

                    <div class="desc-primary">
                        <p class="titre">
                            Location
                        </p>
                        <ul>
                            <li>LocationID <%out.print(dwc.getLocationId());%></li>
                            <li>HigherGeographyID <%out.print(dwc.getHigherGeographyId());%></li>
                            <li>HigherGeography <%out.print(dwc.getHighergeography());%></li>
                            <li>Continent <%out.print(dwc.getContinent());%></li>
                            <li>WaterBody <%out.print(dwc.getWaterbody());%></li>
                            <li>IslandGroup <%out.print(dwc.getIslandgroup());%></li>
                            <li>Island <%out.print(dwc.getIsland());%></li>
                            <li>Country <%out.print(dwc.getCountry());%></li>
                            <li>CountryCode <%out.print(dwc.getCountrycode());%></li>
                            <li>StateProvince <%out.print(dwc.getStateprovince());%></li>
                            <li>County <%out.print(dwc.getCounty());%></li>
                            <li>Municipality <%out.print(dwc.getMunicipality());%></li>
                            <li>Locality <%out.print(dwc.getLocality());%></li>
                            <li>VerbatimLocality <%out.print(dwc.getVerbatimlocality());%></li>
                            <li>VerbatimElevation <%out.print(dwc.getVerbatimelevation());%></li>
                            <li>MinimumElevationInMeters <%out.print(dwc.getMinimumelevationinmeters());%></li>
                            <li>MaximumElevationInMeters <%out.print(dwc.getMaximumelevationinmeters());%></li>
                            <li>VerbatimDepth <%out.print(dwc.getVerbatimdepth());%></li>
                            <li>MinimumDepthInMeters <%out.print(dwc.getMinimumdepthinmeters());%></li>
                            <li>MaximumDepthInMeters <%out.print(dwc.getMaximumdepthinmeters());%></li>
                            <li>MinimumDistanceAboveSurfaceInMeters <%out.print(dwc.getMinimumdistanceabovesurfaceinmeters());%></li>
                            <li>MaximumDistanceAboveSurfaceInMeters <%out.print(dwc.getMaximumdistanceabovesurfaceinmeters());%></li>
                            <li>LocationAccordingTo <%out.print(dwc.getLocationaccordingto());%></li>
                            <li>LocationRemarks <%out.print(dwc.getLocationremarks());%></li>
                            <li>VerbatimCoordinates <%out.print(dwc.getVerbatimcoordinates());%></li>
                            <li>VerbatimLatitude <%out.print(dwc.getVerbatimlatitude());%></li>
                            <li>VerbatimLongitude <%out.print(dwc.getVerbatimlongitude());%></li>
                            <li>VerbatimCoordinateSystem <%out.print(dwc.getVerbatimcoordinatesystem());%></li>
                            <li>VerbatimSRS <%out.print(dwc.getVerbatimsrs());%></li>
                            <li>DecimalLatitude <%out.print(dwc.getDecimallatitude());%></li>
                            <li>DecimalLongitude <%out.print(dwc.getDecimallongitude());%></li>
                            <li>GeodeticDatum <%out.print(dwc.getGeodeticdatum());%></li>
                            <li>CoordinateUncertaintyInMeters <%out.print(dwc.getCoordinateuncertaintyinmeters());%></li>
                            <li>CoordinatePrecision <%out.print(dwc.getCoordinateprecision());%></li>
                            <li>PointRadiusSpatialFit <%out.print(dwc.getPointradiusspatialfit());%></li>
                            <li>FootprintWKT <%out.print(dwc.getFootprintwkt());%></li>
                            <li>FootprintSRS <%out.print(dwc.getFootprintsrs());%></li>
                            <li>FootprintSpatialFit <%out.print(dwc.getFootprintspatialfit());%></li>
                            <li>GeoreferencedBy <%out.print(dwc.getGeoreferencedby());%></li>
                            <li>GeoreferenceProtocol <%out.print(dwc.getGeoreferenceprotocol());%></li>
                            <li>GeoreferenceSources <%out.print(dwc.getGeoreferencesources());%></li>
                            <li>GeoreferenceVerificationStatus <%out.print(dwc.getGeoreferenceverificationstatus());%></li>
                            <li>GeoreferenceRemarks <%out.print(dwc.getGeoreferenceremarks());%></li>                                                                                                                
                        </ul>                        
                    </div>         
                    <div class="desc-primary">
                        <p class="titre">
                            Identification
                        </p>
                        <ul>
                            <li>identificationID <%out.print(dwc.getIdentificationId());%></li>
                            <li>identifiedBy <%out.print(dwc.getIdentifiedby());%></li>
                            <li>dateIdentified <%out.print(dwc.getDateidentified());%></li>
                            <li>identificationReferences <%out.print(dwc.getIdentificationreferences());%></li>
                            <li>identificationRemarks <%out.print(dwc.getIdentificationremarks());%></li>
                            <li>identificationQualifier <%out.print(dwc.getIdentificationqualifier());%></li>
                            <li>typeStatus <%out.print(dwc.getTypestatus());%></li>                            
                        </ul> 
                    </div>         

                </div>

                <div class="clearfix"></div>
                <%
                    Integer boutonValider = ((Integer) request.getAttribute("boutonValider"));
                    if (boutonValider == 0 && remarques.isEmpty()) {
                %>
                <div class="pull-right divider">
                    <button onclick="showCommentairFirst();" style="float: right; margin-left: 2px;" class="btn btn-success">Questionnable</button>
                    <button onclick="validate(1);" style="float: right; background-color: #4CAF50!important;" class="btn btn-success">Valider</button>                                                        
                </div>  
                <%}%>
                <%
                    if (chercheur == 0 && dwc.getIdUtilisateurUpload() == u.getId()) {
                %>
                <div class="pull-right divider">
                    <button type="button" class="btn btn-primary" onclick="window.location = 'addDarwinCore?id=<%out.print(dwc.getId());%>'"><span class="fa fa-edit"></span> Modifier</button>
                    <button type="button" class="btn btn-primary"><span class="fa fa-remove"></span> Supprimer </button>
                </div>  
                <%}%>                
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
    <div id='modal-liste-commentaires' class='modal fade' role='dialog' style='display:none !important' tabindex="-1">
        <div class='modal-dialog'>
            <div class='modal-content'>
                <div class="modal-header">
                    <button data-dismiss='modal' class='close' type='button'>x</button>
                    <h4 class="modal-title"><center>Liste des commentaires</center></h4>
                </div>
                <div class='modal-body'>
                    <div class='row'>
                        <div class='col-md-10 col-md-offset-1'>                            
                            <div class="col-sm-12">
                                <div id="commentaire" ng-repeat="cdc in liste">
                                    <div style="background: #fbffff; border-radius: 4px; margin-bottom: 5px;" class="form-control">
                                        {{cdc.commentaire}}
                                    </div>                    
                                </div>                
                                <form>
                                    <textarea id="textAreaComment" style="max-height: 50px; max-width: 90%; display: inline-block;"  class="form-control" placeholder="votre commentaire" ng-model="object.commentaire"></textarea>                    
                                    <input type="hidden" name="idDarwinCore" ng-model="object.idDarwinCore" ng-init="object.idDarwinCore = <%out.print(dwc.getId());%>" />                    
                                    <input type="hidden" name="idUtilisateur" ng-model="object.idUtilisateur" ng-init="object.idUtilisateur = <%out.print(u.getId());%>" />
                                    <input style="float: right; width: 10%; height: 50px;" type="button" ng-click="save()" value="OK" class="btn btn-success">
                                </form>
                            </div>                                    
                        </div>
                    </div>
                </div>                
            </div>
        </div>
    </div>
    <!-- end dwcnomie -->

</main>
<script src="<c:url value="/resources/assets/js/angular.js"/>"></script>
<script src="<c:url value="/resources/assets/js/appconfig.js"/>"></script>
<script src="<c:url value="/resources/assets/js/controller/liste.js"/>"></script>
<script>
                                        function showModal(status) {
                                            if (status == 0)
                                                $("#modal-ajout-confirmation-questionnable").modal({backdrop: 'static'});
                                            else
                                                $("#modal-ajout-confirmation-valide").modal({backdrop: 'static'});
                                        }
                                        ;

                                        function showCommentaires() {
                                            $("#modal-liste-commentaires").modal({backdrop: 'static'});
                                        }
                                        ;

                                        function showCommentairFirst() {
                                            $("#modal-ajout-commentaire-questionnable").modal({backdrop: 'static'});
                                            $('#boutonQuestionnable').html("<button type='button' id='boutonQuestionnable' onclick = 'validate(0)' class='btn btn-success btn-sm' data-dismiss='modal'>Continuer</button>");
                                        }

                                        function validate(status) {
                                            var valeurs = $('[name="dwc[]"]');
                                            var data = "?dwc[]=<%out.print(dwc.getId());%>&";
                                            var temp = $('#commentaires').val();
                                            if (temp == undefined)
                                                temp = "";
                                            data = data + "status=" + status + "&commentaires=" + temp;
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
//                                        window.location = 'profil';
                                                    } else if (json.etat == 0) {
                                                        $('.messageMod').html('L\'observation N° ' + json.n + ' a déja été marqué comme ' + json.status + ' par ' + json.expert);
                                                        showModal(status);
                                                    }
                                                    $('#commentaires').val("");
                                                }
                                            });
                                        }
                                        ;

                                        function continueValidate(status, etat) {
                                            var data = "?continuer=";
                                            var temp = $('#commentaires').val();
                                            if (temp == undefined)
                                                temp = "";
                                            data = data + etat + "&status=" + status + "&commentaires=" + temp;
                                            $.ajax({
                                                type: 'get',
                                                url: 'continuerValiderListDwc' + data,
                                                processData: false,
                                                contentType: false,
                                                cache: false,
                                                success: function (json) {
                                                    if (json.etat == 1) {
                                                        console.log(json.etat);
//                                        window.location = 'profil';
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