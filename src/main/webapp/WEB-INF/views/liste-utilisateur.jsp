<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html lang="en" class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html lang="en" class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html lang="en" class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>Utilisateur</title>		
        <meta name="description" content="">
        <meta name="keywords" content="">       
        <meta name="viewport" content="width=device-width, initial-scale=1">		
        <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,700' rel='stylesheet' type='text/css'>		
        <link rel="stylesheet" href="/lemurs/resources/assets/css/font-awesome.min.css">		
        <link rel="stylesheet" href="/lemurs/resources/assets/css/jquery.fancybox.css">		
        <link rel="stylesheet" href="/lemurs/resources/assets/css/bootstrap.min.css">		
        <link rel="stylesheet" href="/lemurs/resources/assets/css/owl.carousel.css">		
        <link rel="stylesheet" href="/lemurs/resources/assets/css/slit-slider.css">		
        <link rel="stylesheet" href="/lemurs/resources/assets/css/animate.css">		
        <link rel="stylesheet" href="/lemurs/resources/assets/css/main.css">
        <link rel="stylesheet" href="/lemurs/resources/assets/css/styles.css">		
        <script src="/lemurs/resources/assets/js/modernizr-2.6.2.min.js"></script>
    </head>

    <body id="body" ng-app="app">

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
                        <a href="#body"><img src="img/Logo_LemursPortal_Final.png"/></a>
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
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <i class="fa fa-user" style="color:#fff!important; margin-left:25px; margin-right:10px;"></i>
                                Bonjour, Rakotoarisoa <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="#"><i class="fa fa-dashboard"></i>&nbsp; Mon compte</a></li>
                                <li class="divider"></li>
                                <li><a href="#"><i class="fa fa-power-off"></i>&nbsp; DÃ©connexion</a></li>
                            </ul>
                        </li>
                    </ul>
                </nav>
                <!-- /main nav -->

            </div>
        </header>


        <main class="site-content" role="main" ng-controller="utilisateur">

            <!-- taxonomie -->
            <section id="taxonomie">
                <div class="banner-interieur" style="background:url(img/parallax/fexpert.jpg) no-repeat center center;">
                    <div class="container" style="margin-top: 5%;">
                        <div class="col-md-6 col-md-offset-3">
                            <!-- Search Form -->
                            <form role="form" ng-submit="rechercher()">
                                <!-- Search Field -->
                                <div class="row search-header">
                                    <h4 class="text-left">Rechercher</h4>
                                    <div class="form-group">
                                        <div class="input-group">
                                            <input class="form-control" type="text" ng-model="user.nom" name="search" placeholder="Nom de l'utilisateur" required/>
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
                <div class="utilisateur">
                    <div class="container">
                        <h1 class="titre-page">Utilsateur - <span>Liste</span></h1>
                        <div class="row">
                            <div class="col-md-12"> 
                                <h5 class="stat">685 utilisateurs</h5>
                                <div class="padding"></div>

                                <div class="panel panel-primary">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">&nbsp;</h3>
                                        <span class="pull-right">
                                            <!-- Tabs -->
                                            <ul class="nav panel-tabs">
                                                <li class="add-one"><a href="#"><i class="fa fa-plus"></i> Ajouter</a></li>
                                            </ul>
                                        </span>
                                    </div>

                                    <!-- TABLE RESULT -->
                                    <div class="table-responsive">
                                        <table class="table table-hover">
                                            <tbody>
                                                <tr ng-repeat="dwc in liste">
                                                    <td class="number text-center">{{dwc.id}}</td>
                                                    <td class="text-center">{{dwc.prenom}} {{dwc.nom}}</td>
                                                    <td class="text-center">{{dwc.role}}</td>
                                                    <td class="text-center">0</td>
                                                    <td class="text-center">0</td>
                                                    <td class="text-center">0</td>
                                                    <td class="text-center">0</td>
                                                </tr>                                                
                                            </tbody>
                                        </table>
                                    </div>
                                    <!-- END TABLE RESULT -->

                                    <!-- BEGIN PAGINATION -->
                                    <ul class="pagination">
                                        <li class="disabled"><a href="#"><</a></li>
                                        <li class="active"><a href="#">1</a></li>
                                        <li><a href="#">2</a></li>
                                        <li><a href="#">3</a></li>
                                        <li><a href="#">4</a></li>
                                        <li><a href="#">5</a></li>
                                        <li><a href="#">></a></li>
                                    </ul>
                                    <!-- END PAGINATION -->

                                    <!-- END RESULT -->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- End Contenu -->

            </section>
            <!-- end taxonomie -->

        </main>
        <div class="clearfix"></div>
        <footer id="footer">
            <div class="container">
                <div class="row">

                    <div class="col-md-1 animated" align="center">
                        <img src="img/logo-lemurs-blanc.png" style="width: 60px; margin-left: 15px;" alt="">
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
                            <li class="animated zoomIn"><a href="#">Mentions lÃ©gales</a></li>
                            <li class="animated zoomIn"><a href="#">Contact</a></li>
                        </ul>
                    </div>

                    <div class="col-md-5 animated fadeInUp" data-wow-delay="0.8s">
                        <div class="row">
                            <div class="col-xs-2">                	
                                <a href="http://www.fapbm.org/" target="_blank"><img src="img/part1.png" alt=""></a>
                            </div>
                            <div class="col-xs-2">

                                <a href="http://www.gerp.mg" target="_blank"><img src="img/part2.png" alt=""></a>
                            </div>
                            <div class="col-xs-2">									
                                <a href="http://www.primate-sg.org/" target="_blank"><img src="img/part3.png" alt=""></a>
                            </div>
                            <div class="col-xs-2">									
                                <a href="http://madagascar.wcs.org" target="_blank"><img src="img/part4.png" alt=""></a>
                            </div>
                            <div class="col-xs-2">									
                                <a href="http://data.rebioma.net/" target="_blank"><img src="img/part5.png" alt=""></a>
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
        </footer>

        <script src="/lemurs/resources/assets/js/jquery-1.11.1.min.js"></script>
        <script src="/lemurs/resources/assets/js/bootstrap.min.js"></script>
        <script src="/lemurs/resources/assets/js/jquery.singlePageNav.min.js"></script>
        <script src="/lemurs/resources/assets/js/jquery.fancybox.pack.js"></script>
        <script src="http://maps.google.com/maps/api/js?key=AIzaSyBEDUsy60BmI8bgJg88BrCTL-_cETf8oMc&callback=initMap"></script>
        <script src="/lemurs/resources/assets/js/owl.carousel.min.js"></script>
        <script src="/lemurs/resources/assets/js/jquery.easing.min.js"></script>
        <script src="/lemurs/resources/assets/js/jquery.slitslider.js"></script>
        <script src="/lemurs/resources/assets/js/jquery.ba-cond.min.js"></script>
        <script src="/lemurs/resources/assets/js/wow.min.js"></script>
        <script src="/lemurs/resources/assets/js/main.js"></script>                   
        <script src="<c:url value="/resources/assets/js/angular.js"/>"></script>
        <script src="<c:url value="/resources/assets/js/appconfig.js"/>"></script>        
        <script src="<c:url value="/resources/assets/js/controller/utilisateur.js"/>"></script>     
    </body>
</html>