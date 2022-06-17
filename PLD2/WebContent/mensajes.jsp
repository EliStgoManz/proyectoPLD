<%@ include file="valida_login.jsp"%>

<!DOCTYPE html>
<html>
<head>
<title>Efectivale - PLD</title>
<meta name="viewport"
	content="widht=device-widht, user-scalable=no, initial-scale=1.0, minimun-scale=1.0">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.0/css/font-awesome.min.css">
<!-- Bootstrap core CSS -->
<link href="./css/bootstrap.min.css" rel="stylesheet">
<!-- Material Design Bootstrap **** -->
<link href="./css/mdb.min.css" rel="stylesheet">
<!-- Your custom styles (optional) -->
<link href="./css/style.css" rel="stylesheet">
<link href="./css/forms.css" rel="stylesheet">
<link href="./css/xlarge.css" rel="stylesheet">
<link href="./css/large.css" rel="stylesheet">
<link href="./css/medium.css" rel="stylesheet">
<link href="./css/small.css" rel="stylesheet">
<link href="./css/xsmall.css" rel="stylesheet">







</head>
<body>


	<header id="header">
		<div class="container-fluid">
			<div class="row align-items-center pt-2 pb-2">
				<div class="col-12 col-sm-12 col-md-4 col-lg-2 col-xl-3">
					<a class="navbar-brand" href="/"> <img
						src="./img/logo-main.png" width="398px"
						class="img-fluid float-left logo mr-auto" style="maxwidth: 160px;"
						alt="Logo Efectivale">
					</a>
				</div>
				<div class="col-12 col-sm-12 col-md-5 col-lg-8 col-xl-7">
					<div class="row">
						<div class="col-12 header-contact-numbers">
							<h1 class="title title-contrata-ahora">Sistema de Prevención
								de Lavado de Dinero</h1>
						</div>
					</div>
				</div>
			</div>
		</div>
	</header>
	<h1 class="title title-contrata-ahora white-text">
		Sistema de Prevención de Lavado de Dinero
		</h1>
		<div class="container">
			<div class="main row">
				<article class="col-xs-12 col-sm-8 col-md-9 col-lg-9">
					<h2 class="text-muted">Mensaje del sistema</h2>
				</article>
				<aside class="col-xs-12 col-sm-4 col-md-3 col-lg-3">
					<p class="lead">
						Bienvenido:
						<%@include file="detectausuario.jsp" %>
					</p>
				</aside>
			</div>
			<div class="row">
				<div class="col-md-9 table-responsive">
					<p>${resultado}</p>
					<p>
						<button class="btn btn-danger" onclick="actualiza_cierra()">Cerrar</button>

					</p>
				</div>
				<div class="col-md-3">
					
					<p>
						<%
							UsuarioSistema us;
							UsuarioTransitivo ut;
							HttpSession sesion;

							sesion = request.getSession();
                            Object us1 = sesion.getAttribute("usuario");
							if (us1 instanceof UsuarioSistema) {
								us = (UsuarioSistema) sesion.getAttribute("usuario");
								if (sesion.getAttribute("pass").equals(us.getClave_de_acceso())) {
						%>
						<%@include file="despliega_menu.jsp"%>
						<%
							}
							} else {
								ut = (UsuarioTransitivo) sesion.getAttribute("usuario");
								if (sesion.getAttribute("pass").equals(ut.getClave_de_acceso())) {
						%>
						<%@include file="despliega_menu.jsp"%>
						<%
							}
							}
						%>
					</p>
				</div>
			</div>
		</div>
		<footer>
			<div class="row footer-menu-bottom">
				<div class="col-12">
					<ul>
						<li>Todos los Derechos Reservados Efectivale S. de R.L</li>
						<li><a
							href="http://www.efectivale.com/comunicados/comunicado_LAAT.htm"
							target="_top"> Disposiciones Vigentes de la LAAT </a></li>
						<li><a
							href="http://www.efectivale.com.mx/avisos/avisos_de_privacidad.html"
							target="_top"> Aviso de Privacidad </a></li>
						<li><a
							href="http://www.efectivale.com.mx/terminos/TCGCLIENTESDespensa-Comida.pdf"
							target="_top"> Términos y Condiciones </a></li>
					</ul>
				</div>
			</div>
		</footer>
		<script src="js/jquery.js"></script>
		<script src="js/bootstrap.min.js"></script>

		<script>
			function actualiza_cierra() {
				let
				val = document.referrer; //Pagina anterior
				val = val.toUpperCase(); // Mayus

				if (val.indexOf("REPRESENTANTE") > -1) { //Buscar entre cadena
					window.opener.llamadaRepresentanteActualiz();
				}
				if (val.indexOf("BENE") > -1) {
					window.opener.llamadaBeneActualiz();
				}

				if (val.indexOf("REP_PLD") > -1 || val.indexOf("ESTATUS_PLD") >-1){
					window.location.href="rep_pld.jsp";
				}
				
			
				
				window.close();

			}
		</script>
</body>


</html>