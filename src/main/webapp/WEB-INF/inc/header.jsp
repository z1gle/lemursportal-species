<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>Lémuriens</title>		
        <meta name="description" content="">
        <meta name="keywords" content="">       
        <meta name="viewport" content="width=device-width, initial-scale=1">	
        <!--css-->
        <link href="<c:url value="http://fonts.googleapis.com/css?family=Open+Sans:400,300,700" />" rel="stylesheet">		
        <link href="<c:url value="/resources/assets/css/bootstrap.min.css" />" rel="stylesheet">		
        <link href="<c:url value="/resources/assets/css/font-awesome.min.css" />" rel="stylesheet">		
        <link href="<c:url value="/resources/assets/css/jquery.fancybox.min.css" />" rel="stylesheet">		
        <link href="<c:url value="/resources/assets/css/owl.carousel.min.css" />" rel="stylesheet">		
        <link href="<c:url value="/resources/assets/css/slit-slider.min.css" />" rel="stylesheet">		
        <link href="<c:url value="/resources/assets/css/animate.min.css" />" rel="stylesheet">		
        <link href="<c:url value="/resources/assets/css/main.min.css" />" rel="stylesheet">		
        <link href="<c:url value="/resources/assets/css/styles_.min.css" />" rel="stylesheet">
        <script src="<c:url value="/resources/assets/js/jquery-1.11.1.min.js"/>"></script>
    </head>
    <body ng-app="app">
        <!-- Navigation -->
        <header id="navigation" class="navbar-inverse navbar-fixed-top animated-header">
            <div class="container">
                <div class="navbar-header">

                    <!-- responsive nav button -->
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <!-- /responsive nav button -->

                    <!-- logo -->
                    <h1 class="navbar-brand">
                        <a href="#" data-toggle='modal' data-target='#modal-service'><img src="<c:url value="/resources/assets/img/Logo_LemursPortal_Final.png"/>"></a>
                    </h1>
                    <!-- /logo -->
                </div>

                <!-- main nav -->
                <nav class="collapse navbar-collapse navbar-right" role="navigation">
                    <ul id="nav" class="nav navbar-nav">
                        <li><a onclick="window.location = 'https://www.lemursportal.org'" href="/"><spring:message code="header.label.home"/></a></li>
                        <li><a onclick="window.location = 'taxonomi'" href="taxonomi"><spring:message code="header.label.species"/></a></li>
                        <li><a onclick="window.location = 'darwinportal'" href="darwinportal"><spring:message code="header.label.observations"/></a></li>
                        <li><a onclick="window.location = 'visualisation'" href="visualisation"><spring:message code="header.label.map"/></a></li>
                        <li><a onclick="window.location = '/forum/experts/'" href="/forum/experts/"><spring:message code="header.label.experts"/></a></li>                        

                        <c:if test="${role10001 == 10001}">
                            <!--<li><a onclick="window.location = 'listeUtilisateur'" href="listeUtilisateur">Utilisateurs</a></li>-->
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    Admin <b class="caret"></b>
                                </a>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a href="#" onclick="window.location = 'listeUtilisateur'">&nbsp; Utilisateur</a></li>
                                    <li class="divider"></li>
                                    <li><a href="#" onclick="window.location = 'gestionModele'">&nbsp; Modeles</a></li>
                                </ul>
                            </li>
                        </c:if>


                        <c:if test="${utilisateur.nom!=''&&utilisateur.nom!=null}">
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    <i class="fa fa-user" style="color:#fff!important; margin-left:25px; margin-right:10px;"></i>
                                    <spring:message code="header.label.greating"/>, <c:out value="${utilisateur.nom}"></c:out> <b class="caret"></b>
                                    </a>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" onclick="window.location = 'profil'"><i class="fa fa-dashboard"></i>&nbsp; Mon compte</a></li>
                                        <li class="divider"></li>
                                        <li><a href="modification-observations" onclick="window.location = 'modification-observations'"><i class="fa fa-align-left"></i>&nbsp; Mes données</a></li>
                                        <li class="divider"></li>
                                        <li><a href="logout" onclick="logout()"><i class="fa fa-power-off"></i>&nbsp; Déconnexion</a></li>
                                    </ul>
                                </li>
                                <script>
//                                    function logout() {
//                                        $.ajax({
//                                            type: 'post',
//                                            url: 'http://localhost:8085/LemursPortal-web/logout',
//                                            success: function (json) {
//                                                window.location = 'logout';
//                                            }
//                                        });
//                                    }
                                    function logout() {
                                        $.ajax({
                                            type: 'post',
                                            url: 'https://www.lemursportal.org/forum/logout',
                                            success: function (json) {
                                                window.location = 'logout';
                                            }
                                        });
                                    }
                                </script>
                        </c:if>
                        <c:if test="${utilisateur.nom==''||utilisateur.nom==null}">
                            <!--                            <li><a onclick="window.location = 'http://localhost:8085/LemursPortal-web/login'" href="http://localhost:8085/LemursPortal-web/login">Se connecter</a></li>-->
                            <li><a onclick="window.location = 'https://www.lemursportal.org/forum/login'" href="https://www.lemursportal.org/forum/login">Se connecter</a></li>
                            </c:if>
                        <li>
                            <form>
                                <select id="langue" name="lang" onchange="submit()" style=" margin-top: 12px;">
                                    <option><spring:message code="global.label.language" /></option>
                                    <option value="fr">Français</option>
                                    <option value="en">English</option>
                                    <option value="mg">Malagasy</option>
                                </select>
                            </form>
                        </li>
                    </ul>                    
                </nav>
                <!-- /main nav -->                
            </div>
<!--            <div style="float: right;margin-top: -55px;margin-right:  5px;">
                <a style="padding: 0px;" class="btn" href="https://www.lemursportal.org/forum"><i class="fa fa-comments-o fa-2x"></i></a>
            </div>-->
        </header>
        <div id='modal-service' class='modal fade' role='dialog' style='display:none !important' tabindex="-1">
            <div style="width: 255px;" class='modal-dialog'>
                <div class='modal-content' style="background-color: #000000d4; margin-top: 150px;">
                    <div style="margin-left: -10px;" class='modal-body row'>                        
                        <div class="col-md-6">
                            <a title="Home page" href="/">
                                <div class="service-item">
                                    <div class="service-icon">
                                        <img style="transform: rotate(-47deg); max-height: 45px; margin-left: 26px; margin-bottom: 22px;" src="<c:url value="/resources/assets/img/Logo_LemursPortal_Final.png"/>">
                                    </div>                                    
                                </div>
                            </a>
                        </div>
                        <div class="col-md-6">
                            <a title="Online forum" href="https://www.lemursportal.org/forum?lang=${pageContext.response.locale.language}">
                                <div class="service-item">
                                    <div class="service-icon">
                                        <i style="margin-left: 20px;" class="fa fa-comments-o fa-3x"></i>
                                    </div>                                    
                                </div>
                            </a>
                        </div>
                    </div>                    
                </div>
            </div>
        </div>