<%@include file="valida_login.jsp"%>
<%@page import="entidad.UsuarioSistema"%>
<%@page import="entidad.Supervisor"%>
<%@page import="utilidades.Mensajes"%>
<%@page import="entidad.UsuarioTransitivo"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Efectivale - PLD</title>
<meta name="viewport"
	content="widht=device-widht, user-scalable=no, initial-scale=1.0, minimun-scale=1.0">
<meta charset="UTF-8">
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
	<h5 class="title title-contrata-ahora white-text">
		Sistema de Prevención de Lavado de Dinero
		</h1>
		<div class="container">
			<div class="main row">
				<article class="col-xs-12 col-sm-8 col-md-9 col-lg-9">
					<h2 class="text-muted">Mantenimiento de contraseñas</h2>
				</article>
				<aside class="col-xs-12 col-sm-4 col-md-3 col-lg-3">
					<p class="lead">
						Bienvenido:
						<%@include file="detectausuario.jsp"%>
					</p>
				</aside>
			</div>
			<div class="row">
				<div class="col-md-9 table-responsive">



					<form onsubmit="return validarContrasena()"
						action="CambioContrasena" method="post">

						<input type="hidden" id="Cliente_Id" name="Cliente_Id"
							value="<%@ include file="detectausuario.jsp" %>">
						<!-- name="frmModificacionContrasena" action = "CambioContrasena" onsubmit="return validarFormulario()" method = "post" class="">-->
						<table border="0">
							<tr>
								<td><label for="id">Usuario:</label></td>
								<td><label for="id">
								 <%@include	file="detectausuario.jsp"%></label></td>
								<td width="30px"></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td>
									<div class="md-form form-sm">
										<input class="" type="password" name="cveactual"
											id="cveactual" maxlength="9"> <label for="cveactual">*
											Contraseña actual</label>
									</div>
								</td>

								<td></td>
								<td></td>
								<td></td>
							</tr>

							<tr>
								<td></td>
								<td><br> <label for="id">La contraseña debe
										contener ocho caracteres, </label> <br> <label for="id">mínimo
										una máyuscula, una minúscula, al menos un número</label> <br> <label
									for="id">y uno de los siguientes caracteres especiales:</label>
									<br> <label for="id"> ($ @ ! % * ? &). </label><br> <br>
									<div class="md-form form-sm">
										<input class="" type="password" name="cvenueva" id="cvenueva"
											maxlength="8"> <label for="cvenueva">*
											Contraseña nueva</label>
									</div></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td>
									<div class="md-form form-sm">
										<input class="" type="password" name="cvenueva2"
											id="cvenueva2" maxlength="8"> <label for="cvenueva2">*
											Confirmacion </label>
									</div>
								</td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td><input type="hidden" name="respuesta" id="respuesta"
									value="<c:out value="${resultado}" />" /> <input type="hidden"
									name="exito" id="exito" value="<c:out value="${exito}" />" />
								</td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td>
									<button class="btn btn-danger">Guardar</button>
								</td>

							</tr>
						</table>
					</form>
				</div>

				<div class="col-md-3">


					<p>
						<%@ include file="despliega_menu.jsp"%>
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
		<script src="js/jquery-3.3.1.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/mdb.min.js.descarga"></script>


		<script>
			var txtExito = document.getElementById('exito').value;
			var txtRespuesta = document.getElementById('respuesta').value;
			if (txtExito == '1' && txtRespuesta.length > 0) {
				alert(txtRespuesta);
				document.getElementById('exito').value = '0';
			}
		</script>

		<script>
			function validarContrasena() {
		<%String pass = "";
			if (request.getSession().getAttribute("pass") != null) {
				pass = request.getSession().getAttribute("pass").toString();
			}%>
			txtContrasenaActual =
		<%out.println("'" + pass + "'");%>
			;
				txtActualIngresada = document.getElementById('cveactual').value;

				if (txtActualIngresada == '' || txtActualIngresada.length == 0) {
					alert('Debe capturar la contraseña actual');
					document.getElementById('cveactual').focus();
					return false;

				}

				if (txtContrasenaActual != txtActualIngresada) {
					alert('La contraseña actual no coincide con la contraseña capturada');
					document.getElementById('cveactual').focus();
					return false;

				}

				txtNueva = document.getElementById('cvenueva').value;
				txtNueva2 = document.getElementById('cvenueva2').value;
				if (txtNueva.length != 8) {
					alert('La nueva contraseña debe contener ocho caracteres');
					document.getElementById('cvenueva').focus();
					return false;
				}

				resultado = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,}$/
						.test(txtNueva);

				if (!resultado) {
					alert('La contraseña debe contener ocho caracteres, mínimo una máyuscula, una minúscula, al menos un número y uno de los siguientes caracteres especiales ($,@,!,%,*,?,&)');
					document.getElementById('cvenueva').focus();
					return false;

				}

				if (txtNueva != txtNueva2) {
					alert('La nueva contraseña no coincide con su confirmación');
					document.getElementById('cvenueva').focus();
					return false;

				}

			} //fin validarContrasena
		</script>
</body>
</html>