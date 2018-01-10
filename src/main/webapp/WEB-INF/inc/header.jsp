<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>DarwinCore</title>		
        <meta name="description" content="">
        <meta name="keywords" content="">       
        <meta name="viewport" content="width=device-width, initial-scale=1">	
        <!--css-->
        <link href="<c:url value="http://fonts.googleapis.com/css?family=Open+Sans:400,300,700" />" rel="stylesheet">		
        <link href="<c:url value="/resources/assets/css/bootstrap.min.css" />" rel="stylesheet">		
        <link href="<c:url value="/resources/assets/css/font-awesome.min.css" />" rel="stylesheet">		
        <link href="<c:url value="/resources/assets/css/jquery.fancybox.css" />" rel="stylesheet">		
        <link href="<c:url value="/resources/assets/css/owl.carousel.css" />" rel="stylesheet">		
        <link href="<c:url value="/resources/assets/css/slit-slider.css" />" rel="stylesheet">		
        <link href="<c:url value="/resources/assets/css/animate.css" />" rel="stylesheet">		
        <link href="<c:url value="/resources/assets/css/main.css" />" rel="stylesheet">		
        <link href="<c:url value="/resources/assets/css/styles.css" />" rel="stylesheet">
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
                        <a href="#body"><img src="<c:url value="/resources/assets/img/Logo_LemursPortal_Final.png"/>"></a>
                    </h1>
                    <!-- /logo -->
                </div>

                <!-- main nav -->
                <nav class="collapse navbar-collapse navbar-right" role="navigation">
                    <ul id="nav" class="nav navbar-nav">
                        <li><a href="#body">Accueil</a></li>
                        <li><a href="#about">Qui sommes-nous</a></li>
                        <li><a href="#datapub">Datapublication</a></li>
                        <li><a href="#experts">Les experts</a></li>
                            <c:if test="${utilisateur.nom!=''&&utilisateur.nom!=null}">
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    <i class="fa fa-user" style="color:#fff!important; margin-left:25px; margin-right:10px;"></i>
                                    Bonjour, <c:out value="${utilisateur.nom}"></c:out> <b class="caret"></b>
                                    </a>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" onclick="window.location = 'profil'"><i class="fa fa-dashboard"></i>&nbsp; Mon compte</a></li>
                                        <li class="divider"></li>
                                        <li><a href="logout" onclick="window.location = 'logout'"><i class="fa fa-power-off"></i>&nbsp; Déconnexion</a></li>
                                    </ul>
                                </li>
                        </c:if>
                    </ul>
                </nav>
                <!-- /main nav -->

            </div>
        </header>       