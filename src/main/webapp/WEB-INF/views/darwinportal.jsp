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
                        <h1 class="titre-page">Observation - <span>Liste</span></h1>
                        <div class="row">
                            <div class="col-md-12">
                                <!-- Stat -->
                                <h5 class="stat">Tous (<b>{{liste.length}}</b>)</h5>
                                <!-- End Stat -->
                                <!-- Vignette -->
                                <div class="col-lg-2 col-md-2 col-sm-4 col-xs-12" ng-repeat="dwc in liste">
                                    <div class="vignette">
                                        <img src="<c:url value="/resources/assets/img/fexpert.jpg"/>" alt="{{dwc.scientificname}}" >
                                        <div class="offer">
                                            <span><i class="fa fa-comment"></i>120</span>
                                            <span class="pull-right valid"><i class="fa fa-check"></i></span>
                                        </div>
                                        <div class="detail">
                                            <a href="#">
                                                <h3>{{dwc.scientificname}}</h3>
                                                <p><i class="fa fa-angle-down"></i></p>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                                <!-- End Vignette -->
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
                            <!-- END PAGINATION -->
                        </div>
                    </div>
                </div>
                <!-- End Contenu -->

            </section>
            <!-- end darwin -->

        </main>
        <div class="clearfix"></div>
        <footer id="footer">
            <div class="container">
                <div class="row">

                    <div class="col-md-1 animated" align="center">
                        <img src="<c:url value="/resources/assets/img/logo-lemurs-blanc.png"/>" style="width: 60px; margin-left: 15px;" alt="">
                    </div>

                    <div class="col-md-2 animated fadeInUp" data-wow-delay="0.4s">
                        <ul>
                            <li class="animated zoomIn"><a href="#">Accueil</a></li>
                            <li class="animated zoomIn"><a href="#">Qui sommes-nous</a></li>
                            <li class="animated zoomIn"><a href="#">Datapublication</a></li>
                            <li class="animated zoomIn"><a href="#">Les experts</a></li>
                        </ul>
                    </div>

                    <div class="col-md-3 animated fadeInUp" data-wow-delay="0.4s">
                        <ul>
                            <li class="animated zoomIn"><a href="#">Liens</a></li>
                            <li class="animated zoomIn"><a href="#">Aide</a></li>
                            <li class="animated zoomIn"><a href="#">Mentions légales</a></li>
                            <li class="animated zoomIn"><a href="#">Contact</a></li>
                        </ul>
                    </div>

                    <div class="col-md-5 animated fadeInUp" data-wow-delay="0.8s">
                        <div class="row">
                            <div class="col-xs-2">                	
                                <a href="http://www.fapbm.org/" target="_blank"><img src="<c:url value="/resources/assets/img/part1.png"/>"></a>
                            </div>
                            <div class="col-xs-2">

                                <a href="http://www.gerp.mg" target="_blank"><img src="<c:url value="/resources/assets/img/part2.png"/>"></a>
                            </div>
                            <div class="col-xs-2">									
                                <a href="http://www.primate-sg.org/" target="_blank"><img src="<c:url value="/resources/assets/img/part3.png"/>"></a>
                            </div>
                            <div class="col-xs-2">									
                                <a href="http://madagascar.wcs.org" target="_blank"><img src="<c:url value="/resources/assets/img/part4.png"/>"></a>
                            </div>
                            <div class="col-xs-2">									
                                <a href="http://data.rebioma.net/" target="_blank"><img src="<c:url value="/resources/assets/img/part5.png"/>"></a>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <div style="background: #080808; color:#fff!important; margin-top: 70px">
                <div class="row text-center">
                    <div class="footer-content">						
                        <p>Copyright &copy; - Lemurs Portal 2017</p>
                    </div>
                </div>
            </div>
        </footer>

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

        <!--js-->
        <script src="<c:url value="/resources/assets/js/modernizr-2.6.2.min.js"/>"></script>
        <script src="<c:url value="/resources/assets/js/jquery-1.11.1.min.js"/>"></script>
        <script src="<c:url value="/resources/assets/js/bootstrap.min.js"/>"></script>
        <script src="<c:url value="/resources/assets/js/jquery.singlePageNav.min.js"/>"></script>
        <script src="<c:url value="/resources/assets/js/jquery.fancybox.pack.js"/>"></script>
        <script src="<c:url value="http://maps.google.com/maps/api/js?key=AIzaSyBEDUsy60BmI8bgJg88BrCTL-_cETf8oMc&callback=initMap"/>"></script>
        <script src="<c:url value="/resources/assets/js/owl.carousel.min.js"/>"></script>
        <script src="<c:url value="/resources/assets/js/jquery.easing.min.js"/>"></script>
        <script src="<c:url value="/resources/assets/js/jquery.slitslider.js"/>"></script>
        <script src="<c:url value="/resources/assets/js/jquery.ba-cond.min.js"/>"></script>
        <script src="<c:url value="/resources/assets/js/wow.min.js"/>"></script>
        <script src="<c:url value="/resources/assets/js/main.js"/>"></script>
        <script src="<c:url value="/resources/assets/js/angular.js"/>"></script>
        <script src="<c:url value="/resources/assets/js/appconfig.js"/>"></script>
        <script src="<c:url value="/resources/assets/js/controller/darwincontroller.js"/>"></script>
    </body>
</html>