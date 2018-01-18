<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/inc/header.jsp"/>  
<main class="site-content" role="main">       
    <section id="taxonomie">
        <div class="banner-interieur" style="background:url(resources/assets/img/parallax/fexpert.jpg) no-repeat center center;">
            <div class="container" style="margin-top: 15%;">
                <div class="col-md-6 col-md-offset-3" style="border-radius: 15px;">
                    <form class="form-signin" action="autentification" method="POST">
                        <h2 class="form-signin-heading">Connexion</h2>
                        <label for="inputEmail" class="sr-only">Identifiant</label>
                        <input style="border-radius: 5px; margin-bottom: 1px;" type="text" name="login" id="inputEmail" class="form-control" placeholder="Identifiant" required="" autofocus="">
                        <label for="inputPassword" class="sr-only">Mot de passe</label>
                        <input style="border-radius: 5px; margin-bottom: 1px;" name="password" type="password" id="inputPassword" class="form-control" placeholder="mot de passe" required="">                        
                        <button style="border-radius: 5px;" class="btn btn-lg btn-primary btn-block" type="submit">Connexion</button>
                    </form>
                    <!-- Search Form -->
<!--                    <form role="form" action="autentification" method="POST">
                         Search Field 
                        <div class="row search-header">
                            <h4 class="text-left">Connexion</h4>
                            <div class="form-group">
                                <div class="input-group">
                                    <label>Identifiant: </label>
                                    <input class="form-control" type="text" name="login" placeholder="identifiant" required/>                                    
                                </div>
                                <div class="input-group">
                                    <label>Mot de passe: </label>                                    
                                    <input class="form-control" name="password" type="password" placeholder="identifiant" required/>                                    
                                </div>                                
                            </div>
                            <span>
                                <input class="btn btn-success" type="submit" value="login"/>
                            </span>
                        </div>
                    </form>-->
                    <!-- End of Search Form -->
                </div>
            </div>
        </div>
    </section>

<!--    <form action="autentification" method="POST">

        Login: <input name="login" type="text">
        Mot de passe: <input name="password" type="password">
        <input type="submit" value="login"/>
    </form>-->

</main>
<script src="<c:url value="/resources/assets/js/angular.js"/>"></script>
<script src="<c:url value="/resources/assets/js/appconfig.js"/>"></script>
<jsp:include page="/WEB-INF/inc/footer.jsp"/>  