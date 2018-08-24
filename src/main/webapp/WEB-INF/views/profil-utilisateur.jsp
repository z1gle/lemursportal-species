<%@page import="org.wcs.lemurs.modele_vue.VueRoleUtilisateur"%>
<%@page import="java.util.List"%>
<%@page import="org.wcs.lemurs.model.Utilisateur"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/inc/header.jsp"/>  
<main class="site-content" role="main" ng-controller="object" style="background: url(resources/assets/img/forest-background.jpg) no-repeat center center fixed;-webkit-background-size: cover;-moz-background-size: cover;-o-background-size: cover;background-size: cover;">
    <style>
        /* USER PROFILE PAGE */
        /*adjust the height of all part of the row*/  
        .two-part-row {
            display: -webkit-box;
            display: -webkit-flex;
            display: -ms-flexbox;
            display:         flex;
            flex-wrap: wrap;
        }
        .two-part-row > [class*='col-'] {
            display: flex;
            flex-direction: column;
        }
        body {
            background-color: beige;
        }
        .card {
            margin-top: 20px;
            padding: 30px;
            background-color: rgba(214, 224, 226, 0.2);
            -webkit-border-top-left-radius:5px;
            -moz-border-top-left-radius:5px;
            border-top-left-radius:5px;
            -webkit-border-top-right-radius:5px;
            -moz-border-top-right-radius:5px;
            border-top-right-radius:5px;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
        }
        .card.hovercard {
            position: relative;
            padding-top: 0;
            overflow: hidden;
            text-align: center;
            background-color: #fff;
            background-color: rgba(255, 255, 255, 1);
        }
        .card.hovercard .card-background {
            height: 130px;
        }
        .card-background img {
            -webkit-filter: blur(25px);
            -moz-filter: blur(25px);
            -o-filter: blur(25px);
            -ms-filter: blur(25px);
            filter: blur(25px);
            margin-left: -100px;
            margin-top: -200px;
            min-width: 130%;
        }
        .card.hovercard .useravatar {
            position: absolute;
            top: 15px;
            left: 0;
            right: 0;
        }
        .card.hovercard .useravatar img {
            width: 100px;
            height: 100px;
            max-width: 100px;
            max-height: 100px;
            -webkit-border-radius: 50%;
            -moz-border-radius: 50%;
            border-radius: 50%;
            border: 5px solid rgba(255, 255, 255, 0.5);
        }
        .card.hovercard .card-info {
            position: absolute;
            bottom: 14px;
            left: 0;
            right: 0;
        }
        .card.hovercard .card-info .card-title {
            padding:0 5px;
            font-size: 20px;
            line-height: 1;
            color: #262626;
            background-color: rgba(255, 255, 255, 0.1);
            -webkit-border-radius: 4px;
            -moz-border-radius: 4px;
            border-radius: 4px;
        }
        .card.hovercard .card-info {
            overflow: hidden;
            font-size: 12px;
            line-height: 20px;
            color: #737373;
            text-overflow: ellipsis;
        }
        .card.hovercard .bottom {
            padding: 0 20px;
            margin-bottom: 17px;
        }
        .btn-pref .btn {
            -webkit-border-radius:0 !important;
        }
        .btn-profil-lemurs {
            background-color: black;
            color: #deaa45;
            font-weight: 700;
        }
        .btn-profil-lemurs-active {
            background-color: #A18029!important;
            color: white!important;
            font-weight: 700;
        }
        .btn-profil-lemurs:hover {
            background-color: #deaa45!important;
            color: white!important;
            font-weight: 700;
        }
    </style>
    <!-- taxonomie -->
    <section id="taxonomie">
        <div class="banner-interieur-pliss" style="background:url(resources/assets/img/parallax/fexpert_modif.jpg) no-repeat center center; height: 125px; background-color: black;"></div>            
        <!-- Contenu -->
        <div class="utilisateur" >
            <div class="container">
                <!--<h1 class="titre-page">Utilisateur - <span>Profil</span></h1>-->
                <%
                    boolean expert = false;
                    Utilisateur s = (Utilisateur) request.getAttribute("utilisateur");
                    Integer nbrCommentaire = (Integer) request.getAttribute("nbrCommentaire");
                    List<VueRoleUtilisateur> roles = (List<VueRoleUtilisateur>) (List<?>) request.getAttribute("roles");
                    for (VueRoleUtilisateur r : roles) {
                        if (r.getDesignation().compareTo("EXPERT") == 0) {
                            expert = true;
                        }
                    }
                %>
                <div class="row">
                    <div class="col-lg-12 col-sm-12">
                        <div class="card hovercard">
                            <div class="card-background">
                                <img class="card-bkimg" alt="" src="resources/assets/img/lem.jpg">                                
                            </div>
                            <div class="useravatar"><!--http://localhost:8082/LemursPortal-web/resources/-->
                                <img alt="" src="https://www.lemursportal.org/forum/resources/<%out.print(s.getPhotoProfil());%>">
                            </div>
                            <div class="card-info"> <span class="card-title"><%out.print(s.getPrenom() + " " + s.getNom());%></span>

                            </div>
                        </div>
                        <div class="btn-pref btn-group btn-group-justified btn-group-lg" role="group" aria-label="...">
                            <div class="btn-group" role="group">
                                <button type="button" id="stars" class="btn btn-profil-lemurs-active" href="#tab1" data-toggle="tab">
                                    <div class="hidden-xs">Information personnel</div>
                                </button>
                            </div>
                            <div class="btn-group" role="group">
                                <button type="button" id="favorites" class="btn btn-default btn-profil-lemurs" href="#tab2" data-toggle="tab">
                                    <div class="hidden-xs">Statistique chercheur</div>
                                </button>
                            </div>
                            <%if (expert) {%>
                            <div class="btn-group" role="group">
                                <button type="button" id="following" class="btn btn-default btn-profil-lemurs" href="#tab3" data-toggle="tab">
                                    <div class="hidden-xs">Statistique expert</div>
                                </button>
                            </div>
                            <%}%>
                        </div>

                        <div class="well">
                            <div class="tab-content">
                                <div class="tab-pane fade in active" id="tab1">
                                    <div class="row two-part-row">
                                        <div class="col-md-6 col-sm-12 col-xs-12">
                                            <div style="height: 90%;">
                                                <div class="row">
                                                    <div class="col-md-4">Nom :</div>
                                                    <div style="font-weight: 700;" class="col-md-8"><% out.print(s.getNom()); %></div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-4">Prénom :</div>
                                                    <div style="font-weight: 700;" class="col-md-8"><% out.print(s.getPrenom()); %></div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-4">E-mail :</div>
                                                    <div style="font-weight: 700;" class="col-md-8"><% out.print(s.getEmail()); %></div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-4">Institution :</div>
                                                    <div style="font-weight: 700;" class="col-md-8"><% out.print(s.getInstitution()); %></div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-4">Poste :</div>
                                                    <div style="font-weight: 700;" class="col-md-8"><% out.print(s.getPoste()); %></div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-4">Rôle :</div>
                                                    <div style="font-weight: 700;" class="col-md-8">
                                                        <%for (VueRoleUtilisateur r : roles) {%>
                                                        <span class="tags"><%out.print(r.getDesignation());%></span> 
                                                        <% } %>
                                                    </div>
                                                </div>
                                            </div>
<!--                                            <div class="row">
                                                <div class="col-xs-12 divider text-center statistique-user">
                                                    <div class="col-xs-12 col-sm-4 emphasis">
                                                        <h2><strong> <%out.print(nbrCommentaire);%> </strong></h2>                    
                                                        <p><small>Commentaires</small></p>

                                                    </div>
                                                    <div class="col-xs-12 col-sm-4 emphasis">
                                                        <h2><strong>13</strong></h2>                    
                                                        <p><small>Vidéos</small></p>
                                                    </div>
                                                    <div class="col-xs-12 col-sm-4 emphasis">
                                                        <h2><strong>11</strong></h2>                    
                                                        <p><small>Photos</small></p>
                                                    </div>
                                                </div>
                                            </div>-->
                                        </div>
                                        <div style="border-left-style: solid;border-left-width: 1px;max-height: 500px;overflow-y: auto; margin-bottom: 20px;" class="col-md-6 col-sm-12 col-xs-12">
                                            <h4 style="text-align: center;">Biographie</h4>
                                            <p><%out.print(s.getBiographie());%></p>
                                        </div>
                                    </div>
                                </div>
                                <div class="tab-pane fade in" id="tab2">
                                    <%
                                        Integer observationEnAttenteChercheur = (Integer) request.getAttribute("observationEnAttenteChercheur");
                                        Integer observationTotaleChercheur = (Integer) request.getAttribute("observationTotaleChercheur");
                                        Integer observationQuestionnableChercheur = (Integer) request.getAttribute("observationQuestionnableChercheur");
                                        Integer observationValideChercheur = (Integer) request.getAttribute("observationValideChercheur");
                                    %>
                                    <table class="table">
                                        <tr>
                                            <td><a href="observationAValider?etatValidation=-21"><spring:message code="data.status.my_awaiting_review"/></a></td>
                                            <td><a href="observationAValider?etatValidation=-21"><%out.print(observationEnAttenteChercheur);%></a></td>
                                        </tr>
                                        <tr>
                                            <td><a href="observationAValider?etatValidation=21"><spring:message code="data.status.my_reliable_reviews_data"/></a></td>
                                            <td><a href="observationAValider?etatValidation=21"><%out.print(observationValideChercheur);%></a></td>
                                        </tr>
                                        <tr>
                                            <td><a href="observationAValider?etatValidation=20"><spring:message code="data.status.my_questionnable_reviews_data"/></a></td>
                                            <td><a href="observationAValider?etatValidation=20"><%out.print(observationQuestionnableChercheur);%></a></td>
                                        </tr>
                                        <tr>
                                            <td><spring:message code="data.status.my_invalidated"/></td>
                                            <td><%out.print(observationTotaleChercheur - (observationEnAttenteChercheur + observationValideChercheur + observationQuestionnableChercheur));%></td>
                                        </tr>
                                        <tr>
                                            <td><a href="observationAValider?etatValidation=-2999"><spring:message code="data.status.my_occurences"/></a></td>
                                            <td><a href="observationAValider?etatValidation=-2999"><%out.print(observationTotaleChercheur);%></a></td>
                                        </tr>
                                    </table>
                                </div>
                                <%if (expert) {%>
                                <div class="tab-pane fade in" id="tab3">
                                    <%
                                        Integer observationEnAttente = (Integer) request.getAttribute("observationEnAttente");
                                        Integer observationTotale = (Integer) request.getAttribute("observationTotale");
                                        Integer observationQuestionnable = (Integer) request.getAttribute("observationQuestionnable");
                                        Integer observationValide = (Integer) request.getAttribute("observationValide");
                                    %>
                                    <table class="table">
                                        <tr>
                                            <td><a href="observationAValider?etatValidation=-1"><spring:message code="data.status.all_awaiting_review"/></a></td>
                                            <td><a href="observationAValider?etatValidation=-1"><%out.print(observationEnAttente);%></a></td>
                                        </tr>
                                        <tr>
                                            <td><a href="observationAValider?etatValidation=1"><spring:message code="data.status.all_reliable_reviews_data"/></a></td>
                                            <td><a href="observationAValider?etatValidation=1"><%out.print(observationValide);%></a></td>
                                        </tr>
                                        <tr>
                                            <td><a href="observationAValider?etatValidation=0"><spring:message code="data.status.all_questionnable_reviews_data"/></a></td>
                                            <td><a href="observationAValider?etatValidation=0"><%out.print(observationQuestionnable);%></a></td>
                                        </tr>                                        
                                        <tr>
                                            <td><a href="observationAValider?etatValidation=-999"><spring:message code="data.status.all_occurences"/></a></td>
                                            <td><a href="observationAValider?etatValidation=-999"><%out.print(observationTotale);%></a></td>
                                        </tr>
                                    </table>
                                </div>
                                <%}%>
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
<script src="<c:url value="/resources/assets/js/angular.js"/>"></script>
<script src="<c:url value="/resources/assets/js/appconfig.js"/>"></script>
<script src="<c:url value="/resources/assets/js/controller/modification.js"/>"></script>
<script>
    $(document).ready(function () {
        $(".btn-pref .btn").click(function () {
            $(".btn-pref .btn").removeClass("btn-profil-lemurs-active").addClass("btn-profil-lemurs");
            // $(".tab").addClass("active"); // instead of this do the below 
            $(this).removeClass("btn-profil-lemurs").addClass("btn-profil-lemurs-active");
        });
    });
</script>
<jsp:include page="/WEB-INF/inc/footer.jsp"/>  