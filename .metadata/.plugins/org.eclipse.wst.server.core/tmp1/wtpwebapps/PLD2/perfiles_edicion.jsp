<%@page import="utilidades.Mensajes"%>
<%@page import="utilidades.PerfilUsuario"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="entidad.Perfil"%>
<%@page import="datosRatos.DatosPerfil"%>
<%
	//VALIDACION DEL PERFIL DE USUARIO
	HttpSession sesion = request.getSession();
	String perfilId = null;

	try {
		perfilId = sesion.getAttribute("perfilId").toString();
		Perfil perfil = (Perfil) sesion.getAttribute("perfil");
		request.setAttribute("permiso", perfil.getPerfiles());
		if (perfil.getVerificacion().equals("N")) {
			Mensajes.setMensaje("No tiene persmisos para ver esta pantalla.");
			request.setAttribute("resultado", Mensajes.mensaje);
			request.setAttribute("exito", "1");
			request.getRequestDispatcher("/mensajes.jsp").forward(request, response);

		}
	} catch (Exception es) {
		System.out.println("Es un perfil de cliente");
		response.sendRedirect("/tipo_persona.jsp");
	}
%>
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
	<h5 class="title title-contrata-ahora white-text">
		Sistema de Prevención de Lavado de Dinero
		</h1>
		<div class="container">
			<div class="main row">
				<article class="col-xs-12 col-sm-8 col-md-9 col-lg-9">
					<h2 class="text-muted">Catálogo de perfiles</h2>
				</article>
				<aside class="col-xs-12 col-sm-4 col-md-3 col-lg-3">
					<p class="lead">
						Bienvenido:
						<%@ include file="detectausuario.jsp"%>
					</p>
				</aside>
			</div>
			<div class="row">

				<div class="col-md-9 table-responsive">
					<br>
					<form action="PermisosPerfil" method="post">
						<table border="0">

							<tr>
								<td width="200px">
									<div class="md-form form-sm">
										<input class="form-control" type="text" id="perfil"
											name="perfil" value="<c:out value="${perfil}" />" readonly>
										<input class="form-control" type="hidden" id="perfilId"
											name="perfilId" value="<c:out value="${perfilId}" />">
										<label for="perfil">* Perfil</label>
									</div>
								</td>
								<td width="50px"></td>
								<td></td>
								<td width="200px"></td>
								<td width="170px"></td>
								<!--
						<td>
							<img src="img/lupa.jpg" width="32" height="32" />
						</td>
						<td width="30px">
							<img src="img/nuevo.png" width="32" height="32" />						
						</td>						
						<td>
							<img src="img/borrar.png" width="32" height="32" />
						</td>						-->
							</tr>
							<tr>
								<td>Prospectos:</td>
								<td><select class="browser-default" name="pProspectos"
									id="pProspectos">
										<option value="N">Ninguno</option>
										<option value="L">Lectura</option>
										<option value="E">Edición</option>
										<option value="B">Bloqueo</option>
								</select></td>

							</tr>
							<tr>
								<td>Captura:</td>
								<td><select class="browser-default" name="pCaptura"
									id="pCaptura" />" >
									<option value="N">Ninguno</option>
									<option value="L">Lectura</option>
									<option value="E">Edición</option>
									<option value="B">Bloqueo</option> </select></td>

							</tr>
							<tr>
								<td>Verificación:</td>
								<td><select class="browser-default" name="pVerificacion"
									id="pVerificacion">
										<option value="N">Ninguno</option>
										<option value="L">Lectura</option>
										<option value="E">Edición</option>
										<option value="B">Bloqueo</option>
								</select></td>

							</tr>
							<tr>
								<td>Avisos:</td>
								<td><select class="browser-default" name="pAvisos"
									id="pAvisos">
										<option value="N">Ninguno</option>
										<option value="L">Lectura</option>
										<option value="E">Edición</option>
										<option value="B">Bloqueo</option>
								</select></td>

							</tr>
							<tr>
								<td>Usuarios:</td>
								<td><select class="browser-default" name="pUsuarios"
									id="pUsuarios">
										<option value="N">Ninguno</option>
										<option value="L">Lectura</option>
										<option value="E">Edición</option>
										<option value="B">Bloqueo</option>
								</select></td>

							</tr>
							<tr>
								<td>Perfiles:</td>
								<td><select class="browser-default" name="pPerfiles"
									id="pPerfiles">
										<option value="N">Ninguno</option>
										<option value="L">Lectura</option>
										<option value="E">Edición</option>
										<option value="B">Bloqueo</option>
								</select></td>

							</tr>
							<tr>
								<td></td>
								</td>
								<td></td>
								<td></td>
								<td>
									<button class="btn btn-danger" id="btnGuardar">Guardar</button>
								</td>
							</tr>


							<tr>
								<td></td>
								</td>
								<td></td>
								<td></td>

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
							target="_top"> TÃ©rminos y Condiciones </a></li>
					</ul>
				</div>
			</div>
		</footer>
		<script src="js/jquery.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/mdb.min.js.descarga"></script>
		<script>
			$(document).ready(function() {
				document.getElementById('btnGuardar').disabled = true;
				txtPermiso = '${permiso}';
				if (txtPermiso == 'E') {
					document.getElementById('btnGuardar').disabled = false;
				}

				orderPermisos();
			});

			function orderPermisos() {
				//Asigna al select el valor lozalizado en la lista 

				e = '${prospectos}';
				var selector = document.getElementById('pProspectos');
				if (e != null || e.length > 0) {
					for (var i = 0; i < selector.length; i++) {
						if (e == selector.options[i].value) {
							selector.selectedIndex = i;
							break;
						} else {//if
							selector.selectedIndex = 0;
						}
					}//for
				}//fin del for

				e = '${captura}';
				var selector = document.getElementById('pCaptura');
				if (e != null || e.length > 0) {
					for (var i = 0; i < selector.length; i++) {
						if (e == selector.options[i].value) {
							selector.selectedIndex = i;
							break;
						} else {//if
							selector.selectedIndex = 0;
						}
					}//for
				}//fin del for

				e = '${verificacion}';
				var selector = document.getElementById('pVerificacion');
				if (e != null || e.length > 0) {
					for (var i = 0; i < selector.length; i++) {
						if (e == selector.options[i].value) {
							selector.selectedIndex = i;
							break;
						} else {//if
							selector.selectedIndex = 0;
						}
					}//for
				}//fin del for

				e = '${avisos}';
				var selector = document.getElementById('pAvisos');
				if (e != null || e.length > 0) {
					for (var i = 0; i < selector.length; i++) {
						if (e == selector.options[i].value) {
							selector.selectedIndex = i;
							break;
						} else {//if
							selector.selectedIndex = 0;
						}
					}//for
				}//fin del for

				e = '${usuarios}';

				var selector = document.getElementById('pUsuarios');

				if (e != null || e.length > 0) {
					for (var i = 0; i < selector.length; i++) {
						if (e == selector.options[i].value) {
							selector.selectedIndex = i;
							break;
						} else {//if
							selector.selectedIndex = 0;
						}
					}//for
				}//fin del for

				e = '${perfiles}';
				var selector = document.getElementById('pPerfiles');

				if (e != null || e.length > 0) {
					for (var i = 0; i < selector.length; i++) {
						if (e == selector.options[i].value) {
							selector.selectedIndex = i;
							break;
						} else {//if
							selector.selectedIndex = 0;
						}
					}//for
				}//fin del for

			}//fin funcion validarActivdadEco
		</script>
</body>
</html>