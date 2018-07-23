<%@page import="java.util.List"%>
<%@page import="org.wcs.lemurs.model.Utilisateur"%>
<%@page import="org.wcs.lemurs.model.DarwinCore"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/inc/header.jsp"/>  
<main class="site-content" role="main" ng-controller="controller">   
    <input type="hidden" value="commentDwc" id="set"/>
    <%
        DarwinCore dwc = (DarwinCore) request.getAttribute("dwc");
    %>
    <input type="hidden" value="<%out.print(dwc.getId());%>" id="idDwc"/>
    <input type="hidden" value="<%out.print(dwc.getIdUtilisateurUpload());%>" id="idUtilisateur"/>
    <input type="hidden" value="getCommentDwc" id="load"/>
    <!-- taxonomie -->
    <section id="taxonomie">
        <div class="banner-interieur-pliss" style="background:url(resources/assets/img/parallax/fexpert_modif.jpg) no-repeat center center; height: 125px; background-color: beige;"></div>
        <div class="container-fluid header-pliss">
            <div class="row" style="background-color: beige;margin-left: -15px; height: 45px; margin-bottom: -10px;">
                <!--<div class="col-md-8 col-sm-8">-->
                <form id="form-search">
                    <!-- Search Field -->                                                    
                    <div class="form-group" style="margin-bottom: 0px; margin-top: 5px; margin-left: 10px;">
                        <div class="input-group" style="width: 100%;">                                             
                            <div class="form-group badge-checkboxes">                                            
                                <div>
                                    <input id="form-search" ng-keyup="$event.keyCode == 13 ? rechercherAvancee() : null" name="espece" type="text" placeholder="search by scientific name" class="checkbox-inline" style="height: 20px; border-radius: 15px; width: 26%; border-style: solid;border-width: 1px; float: left;">                                        
                                    <c:choose>
                                        <c:when test="${utilisateur.nom!=''&&utilisateur.nom!=null}">
                                            <label style="float: left; margin-top: -3px;" class="checkbox-inline">
                                                <input ng-click="rechercherAvancee()" id="publique" name="etat[]" type="checkbox" value="1" checked="">
                                                <span class="badge" style="margin-left: 15px;">Publique</span>
                                            </label>
                                            <label style="float: left; margin-top: -3px;" class="checkbox-inline">
                                                <input ng-click="rechercherAvancee()" id="privee" name="etat[]" type="checkbox" value="0">
                                                <span class="badge" style="">Privée</span>
                                            </label>
                                        </c:when>
                                        <c:otherwise>
                                            <label style="float: left; margin-top: -3px;" class="checkbox-inline">
                                                <input ng-click="rechercherAvancee()" id="publique" name="etat[]" disabled="" type="checkbox" value="1" checked="">
                                                <span class="badge" style="margin-left: 15px;">Publique</span>
                                            </label>
                                            <label style="float: left; margin-top: -3px;" class="checkbox-inline">
                                                <input ng-click="rechercherAvancee()" id="privee" disabled="" name="etat[]" type="checkbox" value="0">
                                                <span class="badge" style="">Privée</span>
                                            </label>
                                        </c:otherwise>
                                    </c:choose>
                                    <select name="validation" id="etat" class="checkbox-inline" style="height: 20px; border-radius: 15px; border-style: solid;border-width: 1px; width: 26%; float: left;">
                                        <option value="-999"><spring:message code="data.status.all_occurences"/></option>
                                        <option value="1"><spring:message code="data.status.all_reliable_reviews_data"/></option>
                                        <option value="-1"><spring:message code="data.status.all_awaiting_review"/></option>
                                        <option value="0"><spring:message code="data.status.all_questionnable_reviews_data"/></option>                                                    
                                    </select>
                                    <c:if test="${utilisateur.nom!=''&&utilisateur.nom!=null}">
                                        <select name="validationMine" id="myEtat" class="checkbox-inline" style="height: 20px; border-radius: 15px; border-style: solid;border-width: 1px; width: 26%; float: left;">
                                            <option value="-999"><spring:message code="data.status.disable"/></option>
                                            <option value="-1000"><spring:message code="data.status.my_occurences"/></option>
                                            <option value="1"><spring:message code="data.status.my_reliable_reviews_data"/></option>
                                            <option value="-1"><spring:message code="data.status.my_awaiting_review"/></option>
                                            <option value="0"><spring:message code="data.status.my_questionnable_reviews_data"/></option>
                                            <option value="-2"><spring:message code="data.status.my_invalidated"/></option>
                                        </select>
                                    </c:if>
                                    <!--<span class="input-group-btn">-->
                                    <a href="#" title="search" style="padding: 0px;height: 20px;float: left;margin-left: 6px;" ng-click="rechercherAvancee()" class="btn"><i style="color: darkgrey" class="fa fa-search"></i></a>
                                    <!--</span>-->
                                    <!--<select name="validationMine" id="myEtat" ng-model="modelePourFaireMarcherOnChange.id" ng-change="rechercherAvancee()" class="checkbox-inline" style="height: 20px; border-radius: 15px; border-style: solid;border-width: 1px; width: 5%; float: left;">-->
                                </div>                                            
                            </div>
                        </div>
                    </div>                        
                </form>
            </div>                
        </div>
        <!-- Contenu -->
        <div class="detail-result">
            <div class="container">
                <%
                    Utilisateur u = (Utilisateur) request.getAttribute("utilisateur");
                %>  
                <h1 class="titre-page">Observation - <span><%out.print(dwc.getScientificname());%></span></h1>
                <div class="row">
                    <div class="intro">
                        <%
                            List<String> remarques = null;
                            Integer chercheur = ((Integer) request.getAttribute("chercheur"));
                            Integer expert = ((Integer) request.getAttribute("expert"));
                            if (expert == 0 || (chercheur == 0 && dwc.getIdUtilisateurUpload().intValue() == u.getId().intValue())) {
                                remarques = (List<String>) request.getAttribute("remarques");
                                if (!remarques.isEmpty()) {
                                    for (String s : remarques) {
                        %>
                        <span class="tags" style="background: #d9534f;"><%out.print(s);%></span>
                        <%
                                    }
                                }
                            }
                        %>
                        <p>Excitavit hic ardor milites per municipia plurima, quae isdem conterminant, dispositos et castella, sed quisque serpentes latius pro viribus repellere moliens, nunc globis confertos, aliquotiens et dispersos multitudine superabatur ingenti, quae nata et educata inter editos recurvosque ambitus montium eos ut loca plana persultat et mollia, missilibus obvios eminus lacessens et ululatu truci perterrens.</p>
                        <p>Sed tamen haec cum ita tutius observentur, quidam vigore artuum inminuto rogati ad nuptias ubi aurum dextris manibus cavatis offertur, inpigre vel usque Spoletium pergunt. haec nobilium sunt instituta.</p>
                    </div>

                    <div class="detail col-md-12 animated fadeInUp" data-wow-delay="0.8s" style="background:url(resources/assets/img/fexpert.jpg) center center no-repeat;">                                                          
                        <div class="col-md-6 description">
                            <h4><%out.print(dwc.getAcceptednameusage());%></h4>
                            <p><span class="label">Nom scientifique:</span><%out.print(dwc.getScientificname());%></p>
                            <p><span class="label">Localisation:</span><%out.print(dwc.getLocality());%></p>
                            <p><span class="label">Ordre:</span><%out.print(dwc.getDarwinorder());%></p>
                            <p><span class="label">Classe:</span><%out.print(dwc.getDarwinclass());%></p>
                            <p><span class="label">Genre:</span><%out.print(dwc.getGenus());%></p>
                            <p><span class="label">Date:</span><%out.print(dwc.getDateidentified());%>(verifier)</p>
                            <p><span class="label">Statistique:</span>789(verifier)</p>
                            <div class="bottom"><a href="#"><i class="fa fa-globe"></i> voir le map</a></div>
                        </div>
                        <div class="col-md-3"></div>
                        <div class="col-md-3 statistique">
                            <div><i class="fa fa-comment"></i><br/>{{liste.length}}</div>
                            <div><i class="fa fa-camera"></i><br/>0</div>
                            <div><i class="fa fa-image"></i><br/>0</div>
                            <div><i class="fa fa-check valid"></i></div>
                        </div>
                    </div>
                    <%
                        Integer boutonValider = ((Integer) request.getAttribute("boutonValider"));
                        if (boutonValider == 0 && remarques.isEmpty()) {
                    %>
                    <div class="pull-right divider">
                        <button type="button" class="btn btn-primary" ng-click="valider(<%out.print(dwc.getId());%>)"><span class="fa fa-edit"></span> Valider</button>                        
                    </div>  
                    <%}%>
                    <%
                        if (chercheur == 0 && dwc.getIdUtilisateurUpload().intValue() == u.getId().intValue()) {
                    %>
                    <div class="pull-right divider">
                        <button type="button" class="btn btn-primary" onclick="window.location = 'addDarwinCore?id=<%out.print(dwc.getId());%>'"><span class="fa fa-edit"></span> Modifier</button>
                        <button type="button" class="btn btn-primary"><span class="fa fa-remove"></span> Supprimer </button>
                    </div>  
                    <%}%>
                </div>
            </div>
        </div>
        <!--Commentaire-->
        <%if (expert == 0 || (chercheur == 0 && dwc.getIdUtilisateurUpload().intValue() == u.getId().intValue())) {%>
        <div class="detail-result">
            <div class="container">
                <h3>Commentaires</h3>
                <div id="commentaire" ng-repeat="cdc in liste">
                    <div style="background: #fbffff; border-radius: 4px; margin-bottom: 5px;" class="form-control">
                        {{cdc.commentaire}}
                    </div>                    
                </div>                
                <form>
                    <textarea style="max-height: 50px; max-width: 90%; display: inline-block;"  class="form-control" placeholder="votre commentaire" ng-model="object.commentaire"></textarea>                    
                    <input type="hidden" name="idDarwinCore" ng-model="object.idDarwinCore" ng-init="object.idDarwinCore = <%out.print(dwc.getId());%>" />                    
                    <input type="hidden" name="idUtilisateur" ng-model="object.idUtilisateur" ng-init="object.idUtilisateur = <%out.print(u.getId());%>" />
                    <input style="float: right; width: 10%; height: 50px;" type="button" ng-click="save()" value="Commenter" class="btn btn-success">
                </form>                
            </div>
        </div>
        <%}%>
        <!-- End Contenu -->

    </section>
    <!-- end taxonomie -->

</main>
<script src="<c:url value="/resources/assets/js/angular.js"/>"></script>
<script src="<c:url value="/resources/assets/js/appconfig.js"/>"></script>
<script src="<c:url value="/resources/assets/js/controller/liste.js"/>"></script>
<jsp:include page="/WEB-INF/inc/footer.jsp"/>  