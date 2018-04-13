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
        <div class="banner-interieur" style="background:url(resources/assets/img/parallax/fexpert.jpg) no-repeat center center;">
            <div class="container" style="margin-top: 5%;">
                <div class="col-md-6 col-md-offset-3">
                    <!-- Search Form -->
                    <form role="form" ng-submit="rechercher()">
                        <!-- Search Field -->
                        <div class="row search-header">
                            <h4 class="text-left">Rechercher un espèce</h4>
                            <div class="form-group">
                                <div class="input-group">
                                    <input class="form-control" type="text" name="search" ng-model="object.scientificname" placeholder="Nom scientifique de l'espèce" required/>
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