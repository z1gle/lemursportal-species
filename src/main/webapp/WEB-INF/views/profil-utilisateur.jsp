<%@page import="org.wcs.lemurs.modele_vue.VueRoleUtilisateur"%>
<%@page import="java.util.List"%>
<%@page import="org.wcs.lemurs.model.Utilisateur"%>
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

    <body id="body">

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
                        <a href="#body"><img src="/lemurs/resources/assets/img/Logo_LemursPortal_Final.png"/></a>
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
                                <li><a href="#"><i class="fa fa-power-off"></i>&nbsp; Déconnexion</a></li>
                            </ul>
                        </li>
                    </ul>
                </nav>
                <!-- /main nav -->

            </div>
        </header>


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
                        <h1 class="titre-page">Utilisateur - <span>Profil</span></h1>
                        <div class="row">
                            <div class="well col-md-12">
                                <div class="profile col-md-4 photo-user" align="center">
                                    <img class="img-responsive img-circle" src="/lemurs/resources/assets/img/user-default.jpg"/>
                                </div>
                                <div class="profile col-md-8">
                                    <div class="col-sm-12">
                                        <div class="col-xs-12 col-sm-12">
                                            <% 
                                                Utilisateur s = (Utilisateur)request.getAttribute("utilisateur");
                                                List<VueRoleUtilisateur> roles = (List<VueRoleUtilisateur>)(List<?>)request.getAttribute("roles");
                                            %>
                                            <h2><%out.print(s.getPrenom()+" "+s.getNom());%></h2>
                                            <p><strong>Poste: </strong> <%out.print(s.getPoste());%> </p>
                                            <p><strong>Localisation: </strong> <%out.print(s.getLocalisation());%> </p>
                                            <p><strong>Biographie: </strong> <%out.print(s.getBiographie());%> </p>
                                            <p><strong>Rôle: </strong>
                                                <%for(VueRoleUtilisateur r : roles) {%>
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
        <div class="clearfix"></div>
        <footer id="footer">
            <div class="container">
                <div class="row">

                    <div class="col-md-1 animated" align="center">
                        <img src="/lemurs/resources/assets/img/logo-lemurs-blanc.png" style="width: 60px; margin-left: 15px;" alt="">
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
                                <a href="http://www.fapbm.org/" target="_blank"><img src="/lemurs/resources/assets/img/part1.png" alt=""></a>
                            </div>
                            <div class="col-xs-2">

                                <a href="http://www.gerp.mg" target="_blank"><img src="/lemurs/resources/assets/img/part2.png" alt=""></a>
                            </div>
                            <div class="col-xs-2">									
                                <a href="http://www.primate-sg.org/" target="_blank"><img src="/lemurs/resources/assets/img/part3.png" alt=""></a>
                            </div>
                            <div class="col-xs-2">									
                                <a href="http://madagascar.wcs.org" target="_blank"><img src="/lemurs/resources/assets/img/part4.png" alt=""></a>
                            </div>
                            <div class="col-xs-2">									
                                <a href="http://data.rebioma.net/" target="_blank"><img src="/lemurs/resources/assets/img/part5.png" alt=""></a>
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
        <script src="<c:url value="/resources/assets/js/controller/utilisateur.js"/>"></script>
    </body>
</html>