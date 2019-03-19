<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta property="og:image" content="https://www.lemursportal.org/resources/img/favicon.ico">
        <link rel="icon" href="https://www.lemursportal.org/resources/img/favicon.ico" type="image/x-icon">
        <title>Lémuriens</title>		
        <meta name="description" content="">
        <meta name="keywords" content="">       
        <meta name="viewport" content="width=device-width, initial-scale=1">	
        <!--css-->
        <link href="<c:url value="https://fonts.googleapis.com/css?family=Open+Sans:400,300,700" />" rel="stylesheet">		
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
                                    <li class="divider"></li>
                                    <li><a href="#" onclick="window.location = 'gestionShp'">&nbsp; Shapefiles</a></li>
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
                                        <li><a href="#" onclick="window.location = 'profil'"><i class="fa fa-dashboard"></i>&nbsp; <spring:message code="header.label.my_account"/></a></li>
                                    <li class="divider"></li>
                                    <li><a href="modification-observations" onclick="window.location = 'modification-observations'"><i class="fa fa-align-left"></i>&nbsp; <spring:message code="header.label.my_data"/></a></li>
                                    <li class="divider"></li>
                                    <li><a href="logout" onclick="logout()"><i class="fa fa-power-off"></i>&nbsp; <spring:message code="header.label.disconnect"/></a></li>
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
                            <li><a onclick="window.location = 'https://www.lemursportal.org/forum/login'" href="https://www.lemursportal.org/forum/login"><spring:message code="login.button.submit"/></a></li>
                            </c:if>
                        <li class="dropdown language" id="lang-select"><a
                                href="#"
                                class="dropdown-toggle" data-toggle="dropdown" role="button"
                                aria-haspopup="true" aria-expanded="false"> <i
                                    class="fa fa-globe"></i><span>${pageContext.response.locale.language} </span><i class="fa fa-caret-down"></i>
                            </a>
                            <ul class="dropdown-menu">
                                <li id="en"><a href="#" onclick="changeLanguage('en')">English</a></li>
                                <li id="fr"><a href="#" onclick="changeLanguage('fr')">Français</a></li>
                                <li id="mg"><a href="#" onclick="changeLanguage('mg')">Malagasy</a></li>
                            </ul>
                        </li>
                        <script>
                            function changeLanguage(lang) {
                                var link = window.location.href;
                                if (link.indexOf('lang') >= 0) {
                                    var debut = link.indexOf('lang');
                                    link = link.substring(0, debut) + 'lang=' + lang + link.substring(debut + 8, link.length);
                                } else if (link.indexOf('?') >= 0) {
                                    link += '&lang=' + lang;
                                } else {
                                    link += '?lang=' + lang;
                                }
                                window.location = link;
                            }
                        </script>
                        <li <c:if test="${utilisateur.nom==''||utilisateur.nom==null}">style='display: none;'</c:if> class="dropdown">
                                <a href="#"
                                   class="dropdown-toggle" data-toggle="dropdown" role="button"
                                   aria-haspopup="true" aria-expanded="false"> 
                                    <i class="fa fa-bell"></i>
                                </a>
                                <style>
                                    .notif > li {
                                        border-bottom: solid;
                                        border-width: 1px;
                                        border-bottom-color: #b6b6a9;
                                    }                                
                                    .notif-head {
                                        border-style: solid;
                                        border-width: 1px;
                                        border-top: none; 
                                        border-left: none;
                                        border-right: none;
                                        border-color: #b6b6a93d;
                                    }
                                    .notif-title {
                                        margin-left: 5px; 
                                        font-weight: bolder;
                                        color: #000000ad;
                                    }
                                    .notif-author {
                                        font-size: 8px;
                                        float: right;
                                        font-weight: 500;
                                    }
                                    .notif-body > span {
                                        margin-left: 5px;
                                        color: #000000ad;
                                    }
                                    .notif-footer > span {
                                        font-size: 8px;
                                        float: left;
                                        font-weight: 500;
                                        margin-left: 5px;
                                    }
                                    .notif-list:hover {                                    
                                        background-color: #43212129!important;
                                    }
                                    .notif-list{
                                        cursor: pointer;
                                    }
                                </style>
                                <ul class="dropdown-menu notif" style="width: 290px; max-height: 390px; overflow-y: auto;"></ul>
                                <script>
                                    window.onload = function () {
                                        $.ajax({
                                            type: 'get',
                                            url: 'notifications',
                                            success: function (json) {
                                                showNotification(json);
                                            }
                                        });
                                    };
                                    function showNotification(json) {
                                        var liste = '';
                                        var bellColor = false;
                                        for (var i = 0; i < json.length; i++) {
                                            var utilisateur = json[i].idUtilisateur;
                                            if (utilisateur == null) {
                                                utilisateur = json[i].idFormulaire;
                                            }
                                            var backgroundColor = '';
                                            if (json[i].vue == -1) {
                                                backgroundColor = 'style="background-color: beige;"';
                                                bellColor = true;
                                            }
                                            liste += '<li onclick="showPopUpNotification(' + json[i].id + ')" class="notif-list" ' + backgroundColor + '>';
                                            liste += '    <div class="notif-head">';
                                            liste += '        <span class="notif-title">' + json[i].categorie + '</span>';
                                            liste += '        <span class="notif-author">par : ' + utilisateur + ' </span>';
                                            liste += '    </div>';
                                            liste += '    <div class="notif-body">';
                                            var text = '';
                                            if (json[i].categorie == 'Download') {
                                                text = ' <spring:message code="notification.message.observation.downloaded"/>';
                                            } else if (json[i].categorie == 'Added') {
                                                text = ' <spring:message code="notification.message.observation.added"/>';
                                            }
                                            liste += '        <span> ' + json[i].nbrFille + text + '</span>';
                                            liste += '    </div>';
                                            liste += '    <div class="notif-footer">';
                                            liste += '        <span style="margin-right:  5px;font-size: 8px;font-weight: 600;"> ' + json[i].dateTimeString + '</span>';
                                            liste += '    </div><br>';
                                            liste += '</li>';
                                        }
                                        $('.notif').append(liste);
                                        if (bellColor) {
                                            $('.fa-bell').css('color', '#fc6');
                                        }
                                    }

                                    function showPopUpNotification(id) {
                                        $.ajax({
                                            type: 'get',
                                            url: 'notification/' + id,
                                            success: function (json) {
                                                makePopUpNotification(json);
                                            }
                                        });
                                    }
                                    function makePopUpNotification(json) {
                                        $('#notification-title').text(json.categorie);
                                        if (json.formulaire != null) {
                                            $('#notification-name').text(json.formulaire.nom + ' ' + json.formulaire.prenom);
                                            makeTableOnNotification('detailAssociationFormulaireObservations?formulaire=' + json.idFormulaire, json.id);
                                        } else {
                                            $('#notification-name').text(json.utilisateur.nom + ' ' + json.utilisateur.prenom);
                                            makeTableOnNotification('detailAssociationNotificationObservations?notification=' + json.id, json.id);
                                        }
                                        $('#notification-date').text(json.dateString);
                                        $('#modal-notifications').modal({backdrop: 'static'});
                                    }
                                    function makeTableOnNotification(link, id) {
                                        $('.removable_notification').remove();
                                        $('#loader-notification').show();
                                        $.ajax({
                                            type: 'get',
                                            url: link,
                                            success: function (json) {
                                                buildTable(json);
                                                seen(id);
                                            }
                                        });
                                    }
                                    function buildTable(json) {
                                        var ligne = '';
                                        for (var i = 0; i < json.length; i++) {
                                            ligne += '<tr class="removable_notification">';
                                            ligne += '<td class="number text-right"><a style="color: #818181;" href="detailLemurien?id=1">' + json[i].idObservation + '</a></td>';
                                            ligne += '<td class="text-left"><a href="detailLemurien?id=1">' + json[i].scientificname + '</a></td>';
                                            ligne += '<td class="text-left">' + json[i].locality + '</td>';
                                            ligne += '<td class="text-left">' + json[i].dwcyear + '</td>';
                                            ligne += '<td class="text-left">' + json[i].institutioncode + '</td>';
                                            ligne += '</tr>';
                                        }
                                        $('#loader-notification').hide();
                                        $('#tbody-notification').append(ligne);
                                    }
                                    function seen(id) {
                                        $.ajax({
                                            type: 'put',
                                            url: 'notification/' + id + '?seen=2',
//                                            contentType: 'application/x-www-form-urlencoded',
                                            success: function (json) {
                                                console.log('seen');
                                            }
                                        });
                                    }
                            </script>
                        </li>
                        <c:if test="${role10001 == 10001}">
                            <li>
                                <a onclick="sychro()"><i class="fa fa-refresh"></i></a>
                            </li>
                            <script>
                                function sychro() {
                                    $('#modal-synchro-error').hide();
                                    $('#modal-synchro-spinner').modal('show');
                                    $.ajax({
                                        type: 'post',
                                        url: 'synchro_inaturalist',
                                        success: function (json) {
                                            if (json) {
                                                $('#modal-synchro-spinner').modal('hide');
                                            } else {
                                                $('#modal-synchro-error').show();
                                            }
                                        }
                                    });
                                };
                            </script>
                        </c:if>
                            <li>
                                <a href="showDraft" onclick="window.location = 'showDraft'"><i class="fa fa-table"></i></a>
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
        <div id='modal-notifications' class='modal fade' role='dialog' style='display:none !important' tabindex="-1">
            <div class='modal-dialog'>
                <div class='modal-content'>
                    <div class="modal-header">
                        <button data-dismiss='modal' class='close' type='button'>x</button>
                        <h4 class="modal-title"><center id="notification-title">Notification</center></h4>
                    </div>
                    <div class='modal-body'>
                        <div class='row'>
                            <div class='col-md-10 col-md-offset-1'>
                                <label>Name :</label> <span id="notification-name"></span><br>
                                <label>Date :</label> <span id="notification-date"></span>                                
                                <div class="table-responsive" style="max-height: 485px;">
                                    <table class="table table-hover">
                                        <tbody id="tbody-notification">
                                            <tr style="background-color: rgba(0, 0, 0, 0.01); color: #deaa45; font-weight: 700;">
                                                <td class="number text-center">Id</td>
                                                <td class="text-left">Nom scientifique </td>
                                                <td class="text-left">Localisation</td>
                                                <td class="text-left">Date</td>
                                                <td class="text-left">Institution</td>                                                                                               
                                            </tr>                                        
                                        </tbody>                                        
                                    </table>
                                    <img id="loader-notification" src="resources/assets/img/loaderB32.gif" class="img-responsive" style="margin: 5px auto;">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class='modal-footer'>
                        <button type='button' class='btn btn-default btn-sm' data-dismiss='modal'>OK</button>
                    </div>
                </div>
            </div>
        </div>
        <div id='modal-synchro-spinner' class='modal fade' role='dialog' style='display:none !important' tabindex="-1">
            <div class='modal-dialog'>
                <div class='modal-content'>
                    <div class="modal-header">
                        <button data-dismiss='modal' class='close' type='button'>x</button>
                        <h4 class="modal-title"><center>Upload</center></h4>
                    </div>
                    <div class='modal-body'>
                        <div class='row'>
                            <div class='col-md-10 col-md-offset-1'>                                                        
                                <img src="resources/assets/img/loaderB32.gif" class="img-responsive" style="margin: 5px auto;">
                                <h4 id="modal-synchro-error" style="color: red; display: none;"> ERROR </h4>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>