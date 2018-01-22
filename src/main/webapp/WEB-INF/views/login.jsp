<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>Lémuriens</title>		
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
        <script src="<c:url value="/resources/assets/js/jquery-1.11.1.min.js"/>"></script>
    </head>

    <body id="body" style="background:url(<c:url value="/resources/assets/img/bg-lemurs.jpg"/>) no-repeat center center; background-size:cover;">

        <main class="site-content" role="main">

            <!-- taxonomie -->
            <!-- Contenu -->
            <div class="container">
                <div class="row">
                    <div class="login-page" align="center">
                        <img class="img-responsive" src="<c:url value="/resources/assets/img/logo-lemursportal.png"/>" border="0">
                        <p class="connexion-rs">Se connecter avec :</p>
                        <a href=""><img src="<c:url value="/resources/assets/img/icon-fb.png"/>" border="0"></a>
                        <a href=""><img src="<c:url value="/resources/assets/img/icon-tw.png"/>" border="0"></a>
                        <form class="no-border-btn" style="display: inline;">
                            <button type="submit" style="background: none; padding: 0px; margin: 0px;">
                                <img src="<c:url value="/resources/assets/img/icon-gplus.png"/>" border="0">
                            </button>
                            <input name="scope" value="" type="hidden">
                            <input name="access_type" value="offline" type="hidden">
                        </form>

                        <form class="no-border-btn" style="display: inline;">
                            <button type="submit" style="background: none; padding: 0px; margin: 0px;">
                                <img src="<c:url value="/resources/assets/img/icon-yahoo.png"/>" border="0">
                            </button>
                            <input name="scope" value="email" type="hidden">
                        </form>

                        <div class="form">
                            <form class="login-form" name="loginForm"  action="autentification" method="POST">
                                <input name="" value="" type="hidden">


                                <input class="email" name="login" placeholder="Identifiant" type="text">
                                <input class="pwd" name="password" placeholder="Votre mot de passe" type="password">
                                <button class="submit" type="submit">Se connecter</button>
                                <p class="message">
                                    <a href="" class="left">S'inscrire ?</a> &nbsp;&nbsp;
                                    <a href="#" class="right">Mot de passe oublié ?</a>
                                </p>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <!-- End Contenu -->

            <!-- end taxonomie -->

        </main>
        <div class="clearfix"></div>

    </body>
</html>