<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="clearfix"></div>
<footer id="footer">
    <div class="container">
        <div class="row">

            <div class="col-md-1 animated" align="center">
                <img src="<c:url value="/resources/assets/img/logo-lemurs-blanc.png"/>" style="width: 60px; margin-left: 15px;" alt="">
            </div>

            <div class="col-md-2 animated fadeInUp" data-wow-delay="0.4s">
                <ul>
                    <li class="animated zoomIn"><a href="#"><spring:message code="footer.li.home"/></a></li>
                    <li class="animated zoomIn"><a href="#"><spring:message code="footer.li.who_are_we"/></a></li>
                    <li class="animated zoomIn"><a href="#"><spring:message code="footer.li.data_publication"/></a></li>
                    <li class="animated zoomIn"><a href="#"><spring:message code="footer.li.experts"/></a></li>
                </ul>
            </div>

            <div class="col-md-2 animated fadeInUp" data-wow-delay="0.4s">
                <ul>
                    <li class="animated zoomIn"><a href="#"><spring:message code="footer.li.link"/></a></li>
                    <li class="animated zoomIn"><a href="#"><spring:message code="footer.li.help"/></a></li>
                    <li class="animated zoomIn"><a href="#"><spring:message code="footer.li.legal_Notice"/></a></li>
                    <li class="animated zoomIn"><a href="#"><spring:message code="footer.li.contact"/></a></li>
                </ul>
            </div>

            <div class="col-md-4 animated fadeInUp" data-wow-delay="0.8s">
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
            <div class="col-md-3 animated fadeInUp" data-wow-delay="0.8s">
					<a href="http://jrsbiodiversity.org/" target="_blank"><img src="http://jrsbiodiversity.org/wp-content/themes/zero/images/jrs-logo.svg" style="border-radius: 4%; margin-top: 3%;padding:2%;"></a>
				</div>
        </div>
        
        <div class="footer-bottom">
			<div class="container" id="fter">
				<div class="row">
					<div class="col-md-12">
						<ul class="cash-out pull-left">
							<li>
								<a href="#">
									<img src="https://www.lemursportal.org/forum/resources/logos/logo_meef-48.jpg" alt="">	
								</a>
							</li>
							<li>
								<a href="#">
									<img src="https://www.lemursportal.org/forum/resources/logos/05_2016-Logo-48.jpg" alt="">	
								</a>
							</li>
							<li>
									<img src="https://www.lemursportal.org/forum/resources/logos/blank.png" alt="">	
							</li>
							<li>
								<a href="#">
									<img src="https://www.lemursportal.org/forum/resources/logos/logo_ADD-48.jpg" alt="">	
								</a>
							</li>
							<li>
								<a href="#">
									<img src="https://www.lemursportal.org/forum/resources/logos/logo_Aspinall-48.jpg" alt="">	
								</a>
							</li>
							<li>
								<a href="#">
									<img src="https://www.lemursportal.org/forum/resources/logos/logo_DPZ-48.jpg" alt="">	
								</a>
							</li>
							<li>
								<a href="#">
									<img src="https://www.lemursportal.org/forum/resources/logos/logo_ISSEDD-48.jpg" alt="">	
								</a>
							</li>
							<li>
								<a href="#">
									<img src="https://www.lemursportal.org/forum/resources/logos/logo_Valbio-48.jpg" alt="">	
								</a>
							</li>
							<li>
								<a href="#">
									<img src="https://www.lemursportal.org/resources/logos/MV_logo_kely.png" alt="">	
								</a>
							</li>
							<li>
								<a href="#">
									<img src="https://www.lemursportal.org/resources/logos/PGF_logo_kely.png" alt="">	
								</a>
							</li>
							<li>
								<a href="#">
									<img src="https://www.lemursportal.org/resources/logos/CI_logo_kely.png" alt="">	
								</a>
							</li>
						</ul>
						<p class="copyright-text pull-right" style="color:white; font-size:12px; margin: 12px 0 10px;"> Copyright © - Lemurs Portal 2017 <!-- All Rights Reserved--></p>
					</div>	<!-- End Of /.col-md-12 -->	
				</div>	<!-- End Of /.row -->	
			</div>	<!-- End Of /.container -->	
		</div>
    </div>
<!--     <div style="background: #080808; color:#fff!important; margin-top: 60px"> -->
<!--         <div class="row text-center" style="width: 100%;"> -->
<!--             <div class="footer-content">						 -->
<!--                 <p>Copyright &copy; - Lemurs Portal 2017</p> -->
<!--             </div> -->
<!--         </div> -->
<!--     </div> -->
</footer>
<!--js-->
<script src="<c:url value="/resources/assets/js/modernizr-2.6.2.min.js"/>"></script>
<script src="<c:url value="/resources/assets/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/assets/js/jquery.singlePageNav.min.js"/>"></script>
<script src="<c:url value="/resources/assets/js/jquery.fancybox.pack.js"/>"></script>
<script src="<c:url value="/resources/assets/js/owl.carousel.min.js"/>"></script>
<script src="<c:url value="/resources/assets/js/jquery.easing.min.js"/>"></script>
<script src="<c:url value="/resources/assets/js/jquery.slitslider.js"/>"></script>
<script src="<c:url value="/resources/assets/js/jquery.ba-cond.min.js"/>"></script>
<script src="<c:url value="/resources/assets/js/wow.min.js"/>"></script>
<script src="<c:url value="/resources/assets/js/main.js"/>"></script>
<script type="text/javascript">
    $("#maj").click(function () {
       $.getJSON('http://apiv3.iucnredlist.org/api/v3/country/getspecies/MG?token=a670c99f2bd446865c436bbd8740c237b6d436ee92a7a088f23d81f7cc6bc61d', function (datas) {
         for (var i = 0; i < datas.result.length; i++) {
         var x = datas.result[i].scientific_name;
         var y = datas.result[i].category;
         $.ajax({
         method: 'POST',
         data: {category: y, scientificname: x
         },
         datatype:'json',
         url: 'https://www.lemursportal.org/graphics',
         success: function (response) {
         //alert(response.msg);
         }
         });
         }
         Alert("Mise à jour terminée avec succès");
         });
         })
</script>
</body>
</html>