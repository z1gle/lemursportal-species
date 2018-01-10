<%@page import="org.wcs.lemurs.modele_vue.VueRoleUtilisateur"%>
<%@page import="java.util.List"%>
<%@page import="org.wcs.lemurs.model.Utilisateur"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/inc/header.jsp"/>  
<main class="site-content" role="main">

    <!-- taxonomie -->
    <section id="taxonomie">
        <div class="banner-interieur" style="background:url(/lemurs/resources/assets/img/parallax/fexpert.jpg) no-repeat center center;">
            <div class="container" style="margin-top: 5%;">
                <div class="col-md-6 col-md-offset-3">
                    <!-- Search Form -->
                    <form role="form">
                        <!-- Search Field -->
                        <div class="row search-header">
                            <h4 class="text-left">Rechercher un espèce</h4>
                            <div class="form-group">
                                <div class="input-group">
                                    <input class="form-control" type="text" name="search" placeholder="Nom scientifique de l'espÃ¨ce" required/>
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
        <div class="utilisateur" ng-controller="utilisateur">
            <div class="container">
                <h1 class="titre-page">Lemurien - <span>Détail</span></h1>
                <div class="row">
                    <div class="well col-md-12">
                        <div class="profile col-md-4 photo-user" align="center">
                            <img class="img-responsive img-circle" src="/lemurs/resources/assets/img/user-default.jpg"/>
                        </div>
                        <div class="profile col-md-8">
                            <div class="col-sm-12">
                                <div class="col-xs-12 col-sm-12">
                                    <%
                                        Utilisateur s = (Utilisateur) request.getAttribute("utilisateur");
                                        List<VueRoleUtilisateur> roles = (List<VueRoleUtilisateur>) (List<?>) request.getAttribute("roles");
                                    %>
                                    <h2><%out.print(s.getPrenom() + " " + s.getNom());%></h2>
                                    <p><strong>Poste: </strong> <%out.print(s.getPoste());%> </p>
                                    <p><strong>Localisation: </strong> <%out.print(s.getLocalisation());%> </p>
                                    <p><strong>Biographie: </strong> <%out.print(s.getBiographie());%> </p>
                                    <p><strong>Rôle: </strong>
                                        <%for (VueRoleUtilisateur r : roles) {%>
                                        <span class="tags"><%out.print(r.getDesignation());%></span> 
                                        <%}%>                                                
                                    </p>
                                </div>             
                            </div>
                            <div class="col-xs-12 divider text-center statistique-user">
                                <div class="col-xs-12 col-sm-4 emphasis">
                                    <h2><strong> 0 </strong></h2>                    
                                    <p><small>Commentaires</small></p>

                                </div>
                                <div class="col-xs-12 col-sm-4 emphasis">
                                    <h2><strong>0</strong></h2>                    
                                    <p><small>Vidéos</small></p>
                                </div>
                                <div class="col-xs-12 col-sm-4 emphasis">
                                    <h2><strong>0</strong></h2>                    
                                    <p><small>Photos</small></p>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="pull-right divider">
                                <button type="button" class="btn btn-primary"><span class="fa fa-edit"></span> Editer profil</button>
                                <button type="button" class="btn btn-primary"><span class="fa fa-remove"></span> Supprimer </button>
                                <button type="button" class="btn btn-primary"><span class="fa fa-list"></span> Liste </button>
                            </div>  
                        </div>  
                    </div>
                </div>
            </div>
        </div>
        <!-- End Contenu -->

    </section>
    <!-- end taxonomie -->

</main>
<jsp:include page="/WEB-INF/inc/footer.jsp"/>  