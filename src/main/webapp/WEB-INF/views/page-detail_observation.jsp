<%@page import="java.util.List"%>
<%@page import="org.wcs.lemurs.model.Utilisateur"%>
<%@page import="org.wcs.lemurs.model.DarwinCore"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/inc/header.jsp"/>  
<style>
    #map {
        height: 400px;
        width: 100%;
    }
</style>
<style>
    @media only screen and (max-width: 992px) and (min-width: 767px){
        .header-pliss {
            padding-top: 72px;
            height: -10px;
            background-color: beige;
        }        
    }
</style>
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
        <div class="banner-interieur-pliss" style="background:url(resources/assets/img/parallax/fexpert_modif.jpg) no-repeat center center; height: 125px; background-color: beige;"></div>
        <div class="container-fluid header-pliss">
            <div class="row header-pliss" style="margin-bottom: 10px;border-bottom:  solid;border-bottom-width: 1px;border-bottom-color: beige;">
                <!-- Stat -->                                        
                <!--<h1 style="font-size:  14px;font-weight:  600;width:  100%;float: left;margin-top: 9px; color: #a18029; margin-left: 30px;"><%//out.print(dwc.getAcceptednameusage());%> | <small>Details</small></h1>-->                
                <h1 style="font-size:  14px;font-weight:  600;width:  100%;float: left;margin-top: 9px; color: #a18029; margin-left: 30px;"><%out.print(dwc.getScientificname());%> | <small>Details</small></h1>                
                <!-- End Stat -->                    
            </div>                
        </div>
        <!-- Contenu -->
        <div class="detail-obs">
            <div class="container-fluid">                
                <div class="col-md-3">
                    <%
                        List<String> remarques = null;
                        Integer chercheur = ((Integer) request.getAttribute("chercheur"));
                        Integer expert = ((Integer) request.getAttribute("expert"));
                        if (expert == 0 || (dwc.getIdUtilisateurUpload() != null && dwc.getIdUtilisateurUpload().intValue() == u.getId().intValue())) {
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
                            <div ng-repeat="photo in photos" ng-if="photo.profil == true" class="item active">
                                <img ng-src="{{photo.chemin}}" class="img-responsive">
                            </div>
                            <div ng-repeat="photo in photos" ng-if="photo.profil == false" class="item">
                                <img ng-src="{{photo.chemin}}" class="img-responsive">
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
                    <p class="name-img"><%out.print(dwc.getAcceptednameusage());%></p>
                    <!-- /.carousel -->

                    <iframe class="img-responsive" ng-src="{{trustSrc(videos.src)}}">
                    </iframe>


                    <div class="map-detail-obs" id="map">
                        <!--                        <a href="#"><img class="img-responsive img-rounded" src="resources/assets/img/carte.png"/></a>-->
                    </div>                    
                    <%if (expert == 0 || (dwc.getIdUtilisateurUpload() != null && dwc.getIdUtilisateurUpload().intValue() == u.getId().intValue())) {%>
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
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        InstitutionCode:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getInstitutioncode());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        CollectionCode:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getCollectioncode());%>
                                    </div>                                                                        
                                </li>                            
                            </div>
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        DatasetName:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getDatasetname());%>
                                    </div>                                                                        
                                </li>                            
                            </div>
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        ToOwnerInstitutionCode:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getOwnerinstitutioncode());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        BasisOfRecord:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getBasisofrecord());%>
                                    </div>                                                                        
                                </li>                            
                            </div>
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        InformationWithheld:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getInformationwithheld());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        DataGeneralizations:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getDatageneralizations());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        DynamicProperties:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getDynamicproperties());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                                                 
                        </ul>
                    </div>

                    <div class="desc-primary">
                        <p class="titre">
                            Taxon
                        </p>
                        <ul>
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        TaxonID:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getTaxonId());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        ScientificNameID:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getScientificNameId());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        AcceptedNameUsageID:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getAcceptedNameUsageId());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        ParentNameUsageID:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getParentNameUsageId());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        OriginalNameUsageID:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getOriginalNameUsageId());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        NameAccordingToID:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getNameAccordingToId());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        NamePublishedInID:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getNamePublishedInId());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        TaxonConceptID:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getTaxonConceptId());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        ScientificName:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getScientificname());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        AcceptedNameUsage:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getAcceptednameusage());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        ParentNameUsage:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getParentNameUsage());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        OriginalNameUsage:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getOriginalNameUsage());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        NameAccordingTo:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getNameAccordingTo());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        NamePublishedIn:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getNamePublishedIn());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        HigherClassification:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getHigherclassification());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        Kingdom:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getKingdom());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        Phylum:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getPhylum());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        Class:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getDarwinclass());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        Order:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getDarwinorder());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        Family:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getFamily());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        Genus:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getGenus());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        Subgenus:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getSubgenus());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        SpecificEpithet:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getSpecificepithet());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        InfraspecificEpithet:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getInfraspecificepithet());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        TaxonRank:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getTaxonrank());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        VerbatimTaxonRank:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getVerbatimtaxonrank());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        ScientificNameAuthorship:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getScientificnameauthorship());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        VernacularName:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getVernacularname());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        NomenclaturalCode:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getNomenclaturalcode());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        TaxonomicStatus:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getTaxonomicStatus());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        NomenclaturalStatus:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getNomenclaturalStatus());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        TaxonRemarks:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getTaxonremarks());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                        </ul>
                    </div> 

                    <div class="desc-primary">
                        <p class="titre">
                            Occurrence
                        </p>
                        <ul>
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        OccurrenceID:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getOccurrenceId());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        CatalogNumber:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getCatalognumber());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        OccurrenceDetails:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getOccurrenceDetails());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        OccurrenceRemarks:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getOccurrenceremarks());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        RecordNumber:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getRecordnumber());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        RecordedBy:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getRecordedby());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        IndividualID:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getIndividualId());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        IndividualCount:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getIndividualcount());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        Sex:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getSex());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        LifeStage:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getLifestage());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        ReproductiveCondition:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getReproductivecondition());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        Behavior:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getBehavior());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        EstablishmentMeans:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getEstablishmentMeans());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        OccurrenceStatus:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getOccurrenceStatus());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        Preparations:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getPreparations());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        Disposition:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getDisposition());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        OtherCatalogNumbers:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getOthercatalognumbers());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        PreviousIdentifications:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getPreviousidentifications());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        AssociatedMedia:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getAssociatedmedia());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        AssociatedReferences:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getAssociatedreferences());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        AssociatedOccurrences:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getAssociatedoccurrences());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        AssociatedSequences:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getAssociatedsequences());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        AssociatedTaxa:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getAssociatedtaxa());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                                                        
                        </ul> 
                    </div> 

                    <div class="desc-primary">
                        <p class="titre">
                            Event
                        </p>
                        <ul>
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        EventID:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getEventId());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        SamplingProtocol:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getSamplingprotocol());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        SamplingEffort:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getSamplingeffort());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        EventDate:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getEventdate());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        EventTime:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getEventtime());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        StartDayOfYear:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getStartdayofyear());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        EndDayOfYear:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getEnddayofyear());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        Year:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getDwcyear());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        Month:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getDwcmonth());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        Day:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getDwcday());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        VerbatimEventDate:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getVerbatimeventdate());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        Habitat: 
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getHabitat());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        FieldNumber: 
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getFieldnumber());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        FieldNotes: 
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getFieldnotes());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        EventRemarks: 
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getEventremarks());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                        </ul> 
                    </div> 

                    <div class="desc-primary">
                        <p class="titre">
                            Location
                        </p>
                        <ul>
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        LocationID: 
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getLocationId());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        HigherGeographyID: 
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getHigherGeographyId());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        HigherGeography:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getHighergeography());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        Continent: 
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getContinent());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        WaterBody:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getWaterbody());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        IslandGroup:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getIslandgroup());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        Island:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getIsland());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        Country:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getCountry());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        CountryCode:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getCountrycode());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        StateProvince:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getStateprovince());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        County:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getCounty());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        Municipality: 
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getMunicipality());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        Locality:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getLocality());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        VerbatimLocality: 
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getVerbatimlocality());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        VerbatimElevation: 
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getVerbatimelevation());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        MinimumElevationInMeters:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getMinimumelevationinmeters());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        MaximumElevationInMeters:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getMaximumelevationinmeters());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        VerbatimDepth:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getVerbatimdepth());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        MinimumDepthInMeters:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getMinimumdepthinmeters());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        MaximumDepthInMeters: 
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getMaximumdepthinmeters());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        MinimumDistanceAboveSurfaceInMeters:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getMinimumdistanceabovesurfaceinmeters());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        MaximumDistanceAboveSurfaceInMeters:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getMaximumdistanceabovesurfaceinmeters());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        LocationAccordingTo:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getLocationaccordingto());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        LocationRemarks:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getLocationremarks());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        VerbatimCoordinates:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getVerbatimcoordinates());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        VerbatimLatitude:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getVerbatimlatitude());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        VerbatimLongitude:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getVerbatimlongitude());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        VerbatimCoordinateSystem:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getVerbatimcoordinatesystem());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        VerbatimSRS
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getVerbatimsrs());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        DecimalLatitude:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getDecimallatitude());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        DecimalLongitude:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getDecimallongitude());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        GeodeticDatum:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getGeodeticdatum());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        CoordinateUncertaintyInMeters:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getCoordinateuncertaintyinmeters());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        CoordinatePrecision:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getCoordinateprecision());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        PointRadiusSpatialFit:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getPointradiusspatialfit());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        FootprintWKT:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getFootprintwkt());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        FootprintSRS: 
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getFootprintsrs());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        FootprintSpatialFit: 
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getFootprintspatialfit());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        GeoreferencedBy: 
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getGeoreferencedby());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        GeoreferenceProtocol:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getGeoreferenceprotocol());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        GeoreferenceSources:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getGeoreferencesources());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        GeoreferenceVerificationStatus:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getGeoreferenceverificationstatus());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        GeoreferenceRemarks:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getGeoreferenceremarks());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                                                                                                                                       
                        </ul>                        
                    </div>         
                    <div class="desc-primary">
                        <p class="titre">
                            Identification
                        </p>
                        <ul>
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        identificationID:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getIdentificationId());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        identifiedBy:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getIdentifiedby());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        dateIdentified:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getDateidentified());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        identificationReferences:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getIdentificationreferences());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        identificationRemarks:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getIdentificationremarks());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        identificationQualifier:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getIdentificationqualifier());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                            
                            <div class="row">
                                <li>
                                    <div class="col-md-5">
                                        typeStatus:
                                    </div>
                                    <div class="col-md-7">
                                        <%out.print(dwc.getTypestatus());%>
                                    </div>                                                                        
                                </li>                            
                            </div>                                                     
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
                    if (dwc.getIdUtilisateurUpload()!=null && dwc.getIdUtilisateurUpload().intValue() == u.getId().intValue()) {
                        int j = 5;
                %>
                <div class="pull-right divider">
                    <button type="button" class="btn btn-primary" onclick="window.location = 'addDarwinCore?id=<%out.print(dwc.getId());%>'"><span class="fa fa-edit"></span> Modifier</button>
                    <button type="button" class="btn btn-primary" onclick="del(<%out.print(dwc.getId());%>)"><span class="fa fa-remove"></span> Supprimer </button>
                </div>  
                <%}%>                
            </div>
        </div>
        <%
            if (dwc.getIdUtilisateurUpload()!=null && dwc.getIdUtilisateurUpload().intValue() == u.getId().intValue()) {
        %>   
        <div class="clearfix"></div>
        <div class="col-md-12">
            <div class="tabbable-panel">
                <button style="float: right;" class="btn btn-primary" onclick="$('#modal-upload-photo').modal({backdrop: 'static'});">Ajout Photo</button>
                <form id="uploadVideo" method="POST">
                    <input id="lien" style="float: left;" type="text" placeholder="veuiller insérer le lien de la vidéo">                    
                    <button ng-click="uploadVideo()">Enregistrer</button>
                </form>
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
                                        {{cdc.prenom}} {{cdc.nom}}> {{cdc.commentaire}} ({{cdc.dateCommentaire}})
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
    <div id='modal-alert' class='modal fade' role='dialog' style='display:none !important' tabindex="-1">
        <div class='modal-dialog'>
            <div class='modal-content'>
                <div class="modal-header">
                    <button data-dismiss='modal' class='close' type='button'>x</button>
                    <h4 class="modal-title"><center>REMARQUE</center></h4>
                </div>
                <div class='modal-body'>
                    <div class='row'>
                        <div class='col-md-10 col-md-offset-1'>                            
                            <div class="col-sm-12">
                                {{alerte}}
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
    <div id='modal-upload-photo' class='modal fade' role='dialog' style='display:none !important' tabindex="-1">
        <div class='modal-dialog'>
            <div class='modal-content'>
                <div class="modal-header">
                    <button data-dismiss='modal' class='close' type='button'>x</button>
                    <h4 class="modal-title"><center>Ajouter une photo</center></h4>
                </div>
                <form id="uploadPhoto" method="POST" enctype="multipart/form-data">
                    <div class='modal-body'>                    
                        <input style="margin-bottom: 1px;" id="photo" class="form-control" type="file">
                        <div style="margin-bottom: 1px;" class="form-control"><input style="margin-right: 5px;" type="checkbox" value="1" id="profil" name="profil">Photo profil</div>
                        <input class="form-control" type="date" id="datePrisePhoto" name="datePrisePhoto" placeholder="veuiller insérer la date quand la photo a été prise">                                            
                        Termes et conditions
                        <a href="#" onclick="window.open('resources/assets/policy.pdf', '_blank')">telecharger ici</a>
                    </div>
                    <div class='modal-footer'>
                        <div style="float: left;">*Les termes et conditions doivent être accépter pour envoyer des photos</div>
                        <button class="btn btn-success" ng-click="uploadPhoto()">Accepter et enregistrer</button>
                        <button type='button' class='btn btn-default btn-sm' onclick="$('#link').val('')" data-dismiss='modal'>Annuler</button>                    
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- end dwcnomie -->

</main>
<script src="<c:url value="/resources/assets/js/angular.js"/>"></script>
<script src="<c:url value="/resources/assets/js/appconfig.js"/>"></script>
<script src="<c:url value="/resources/assets/js/controller/page_detail_observation.js"/>"  charset="utf-8"></script>
<script>
                            function del(idDwc) {
                                $.ajax({
                                    method: 'POST',
                                    url: 'delDwc?idDwc=' + idDwc,
                                    success: function (json) {
                                        window.location = 'darwinportal';
                                    },
                                    error: function (json) {
                                        console.log(json.statusText);
                                    }
                                });
                            }
                            ;

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
<script>
    function initMap() {
        var centre = {lat: <%out.print(dwc.getDecimallatitude());%>, lng: <%out.print(dwc.getDecimallongitude());%>};
        var mark = {lat: <%out.print(dwc.getDecimallatitude());%>, lng: <%out.print(dwc.getDecimallongitude());%>};
        var map = new google.maps.Map(document.getElementById('map'), {
            zoom: 6,
            center: centre
        });
        var marker = new google.maps.Marker({
            position: mark,
            map: map
        });

//        var transitLayer = new google.maps.TransitLayer();
//        transitLayer.setMap(map);
    }
//    google.maps.event.addDomListener(window, "load", initMap);
</script>
<jsp:include page="/WEB-INF/inc/footer.jsp"/>  