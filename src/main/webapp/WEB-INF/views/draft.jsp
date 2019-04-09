<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/inc/header.jsp"/>  
<main class="site-content" role="main" ng-controller="draft">
    <!-- darwin -->
    <section id="draft">
        <div class="banner-interieur-pliss" style="background:url(resources/assets/img/parallax/fexpert_modif.jpg) no-repeat center center; height: 125px; background-color: beige;"></div>        
        <div class="row">
            <div class="col-md-4">
                <div class="row">
                    <div class="col-md-6"><input type="radio" name="filter" checked ng-click="get(-1)"> Institutioncode</div>
                    <div class="col-md-6"><input type="radio" name="filter" ng-click="get(1)"> Scientificname</div>
                </div>
            </div>
            <div class="col-md-8">
                <h5 style="float: right;">Total number of data : <span id="ttl">{{total}}</span></h5>
            </div>
        </div>
        <!-- Contenu -->
        <div class="vignette-result" style="margin-top: 0px;">
            <div class="container- fluid">                
                <div class="row">
                    <div class="panel panel-primary" style="margin-left: 15px; margin-right: 15px;">                            
                        <!-- TABLE RESULT -->                            
                        <h5>Data from inaturalist</h5>
                        <div class="table-responsive">
                            <jsp:include page="/WEB-INF/inc/loader-spinner.jsp"/>
                            <table class="table table-hover">
                                <tbody>
                                    <tr style="background-color: black; color: #deaa45; font-weight: 700;">
                                        <td class="text-center label-icsn">Institutioncode</td>
                                        <td class="text-right">Public</td>
                                        <td class="text-right">Private</td>
                                        <td class="text-right">Waiting</td>
                                        <td class="text-right">Questionnable</td>
                                        <td class="text-right">Reliable</td>
                                        <td class="text-right">Invalid</td>
                                        <td class="text-right">Total</td>
                                    </tr>
                                    <tr ng-repeat="draft in liste1">
                                        <td class="text-left">{{draft.institutioncode}}{{draft.scientificname}}</td>
                                        <td class="text-right">{{draft.publique}}</td>
                                        <td class="text-right">{{draft.privee}}</td>
                                        <td class="text-right">-</td>
                                        <td class="text-right">-</td>
                                        <td class="text-right">{{draft.tout}}</td>                                            
                                        <td class="text-right">-</td>                                            
                                        <td class="text-right t1">{{draft.tout}}</td>                                            
                                    </tr>                                                   
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td class="text-center">Total :</td>
                                        <td class="text-right" id="t1">{{t1}}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>                            
                        <hr>
                        <h5>Data from REBIOMA</h5>
                        <div class="table-responsive">
                            <jsp:include page="/WEB-INF/inc/loader-spinner.jsp"/>
                            <table class="table table-hover">
                                <tbody>
                                    <tr style="background-color: black; color: #deaa45; font-weight: 700;">
                                        <td class="text-center label-icsn">Institutioncode</td>
                                        <td class="text-right">Public</td>
                                        <td class="text-right">Private</td>
                                        <td class="text-right">Waiting</td>
                                        <td class="text-right">Questionnable</td>
                                        <td class="text-right">Reliable</td>
                                        <td class="text-right">Invalid</td>
                                        <td class="text-right">Total</td>
                                    </tr>
                                    <tr ng-repeat="draft in liste2">
                                        <td class="text-left">{{draft.institutioncode}}{{draft.scientificname}}</td>
                                        <td class="text-right">{{draft.publique}}</td>
                                        <td class="text-right">{{draft.privee}}</td>
                                        <td class="text-right">-</td>
                                        <td class="text-right">-</td>
                                        <td class="text-right">{{draft.tout}}</td>                                            
                                        <td class="text-right">-</td>                                            
                                        <td class="text-right t2">{{draft.tout}}</td>                                            
                                    </tr>                                                   
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td class="text-center">Total :</td>
                                        <td class="text-right" id="t2">{{t2}}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>                            
                        <hr>
                        <h5>Data from lemursportal</h5>
                        <div class="table-responsive">
                            <jsp:include page="/WEB-INF/inc/loader-spinner.jsp"/>
                            <table class="table table-hover">
                                <tbody>
                                    <tr style="background-color: black; color: #deaa45; font-weight: 700;">
                                        <td class="text-center label-icsn">Institutioncode</td>
                                        <td class="text-right">Public</td>
                                        <td class="text-right">Private</td>
                                        <td class="text-right">Waiting</td>
                                        <td class="text-right">Questionnable</td>
                                        <td class="text-right">Reliable</td>
                                        <td class="text-right">Invalid</td>
                                        <td class="text-right">Total</td>
                                    </tr>
                                    <tr ng-repeat="draft in liste3">
                                        <td class="text-left">{{draft.institutioncode}}{{draft.scientificname}}</td>
                                        <td class="text-right">{{draft.publique}}</td>
                                        <td class="text-right">{{draft.privee}}</td>
                                        <td class="text-right">{{draft.waiting}}</td>
                                        <td class="text-right">{{draft.questionnable}}</td>
                                        <td class="text-right">{{draft.reliable}}</td>                                            
                                        <td class="text-right">{{draft.invalid}}</td>                                            
                                        <td class="text-right t3">{{draft.tout}}</td>                                            
                                    </tr>                                                   
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td class="text-center">Total :</td>
                                        <td class="text-right" id="t3">{{t3}}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>                            
                    </div>
                </div>
            </div>
        </div>
        <!-- End Contenu -->
        <script>
                    function calc(cl) {
                        var temporary = $('.' + cl);
                        var index = 0;
                        for (var i = 0; i < temporary.length; i++) {
                            index = index + parseInt(temporary[i].innerText);
                        }
                        $('#' + cl).html(index);
                    }
                    function calcAll() {
                        $('#ttl').text(parseInt($('#t1')[0].innerText) + parseInt($('#t2')[0].innerText)
                                + parseInt($('#t3')[0].innerText));
                    }
        </script>
    </section>
    <!-- end taxonomie -->
</main>
<script src="<c:url value="/resources/assets/js/angular.js"/>"></script>
<script src="<c:url value="/resources/assets/js/appconfig.js"/>"></script>
<script src="<c:url value="/resources/assets/js/controller/draftcontroller.js"/>"></script>
<jsp:include page="/WEB-INF/inc/footer.jsp"/>  