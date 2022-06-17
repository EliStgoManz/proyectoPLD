<%@page import="utilidades.Mensajes"%>
<%@page import="utilidades.PerfilUsuario"%>
<%@page import="entidad.Perfil"%>
<%@page import="datosRatos.DatosPerfil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
					<p class="lead">Bienvenido: Administrador</p>
				</aside>
			</div>
			<div class="row">
				<div class="col-md-9 table-responsive">
					<br>
					<form id="frmPerfil" action="BusquedaPerfil" method="post"
						autocomplete="off">
						<input type="hidden" name="respuesta" id="respuesta"
							value="<c:out value="${resultado}" />" /> <input type="hidden"
							name="exito" id="exito" value="<c:out value="${exito}" />" />
						<table border="0">



							<tr>
								<td width="200px"><input type="hidden" name="respuesta"
									id="respuesta" value="<c:out value="${resultado}" />" /> <input
									type="hidden" name="exito" id="exito"
									value="<c:out value="${exito}" />" /> <input type="hidden"
									name="where" id="where" value="<c:out value="${where}" />" />
									<div class="md-form form-sm">
										<input class="form-control" type="text" id="perfil"
											name="perfil" value=""> <label for="perfil">*
											Perfil</label>
									</div></td>
								<td width="50px"></td>
								<td></td>
								<td width="200px"></td>
								<td width="170px"></td>
								<td><a href="javascript:enviarForma()"><img
										src="img/lupa.jpg" width="32" height="32"
										onclick="javascript:enviarForma()" /></a></td>
								<!--
                                                <td width="30px">
							<img src="img/nuevo.png" width="32" height="32" />						
						</td>	-->
								<!--
						<td>
							<img src="img/borrar.png" width="32" height="32" />
						</td>
                                                -->
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
					<table class="table table-striped table-bordered table-hover">
						<tr class="info">
							<th>Perfil</th>
							<th>Prospectos</th>
							<th>Captura</th>
							<th>Verificación</th>
							<th>Avisos</th>
							<th>Usuarios</th>
							<th>Perfiles</th>
						</tr>


						<%
							String where = "";
							try {
								where = (String) request.getAttribute("where").toString();
							} catch (Exception es) {
								where = "";
								es.printStackTrace();
							}
							if (where == null) {
								where = "";
							}

							Perfil[] p = new DatosPerfil().getList(where);
							if (p != null) {
								String check = "<td align=\"center\"><img src=\"./img/checked.png\" /></td>";
								String noCheck = "<td align=\"center\"><img src=\"./img/no_checked.png\" /></td>";

								for (int i = 0; i < p.length; i++) {
									out.println("<tr>");
									out.println("<td>");
									out.println("<a href=\"EdicionPerfil?perfil=" + p[i].getDescripcion() + "&perfilId="
											+ p[i].getPerfilId() + "\" style=\"text-decoration: underline; color:blue;\">"
											+ p[i].getDescripcion() + "</a>");
									out.println("</td>");

									if (p[i].getProspectos().equals("N")) {
										out.println(noCheck);
									} else {
										out.println(check);
									}

									if (p[i].getCaptura().equals("N")) {
										out.println(noCheck);
									} else {
										out.println(check);
									}

									if (p[i].getVerificacion().equals("N")) {
										out.println(noCheck);
									} else {
										out.println(check);
									}

									if (p[i].getAvisos().equals("N")) {
										out.println(noCheck);
									} else {
										out.println(check);
									}

									if (p[i].getUsuarios().equals("N")) {
										out.println(noCheck);
									} else {
										out.println(check);
									}

									if (p[i].getPerfiles().equals("N")) {
										out.println(noCheck);
									} else {
										out.println(check);
									}

									out.println("</tr>");
								} //for perfiles
							}
						%>



					</table>
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
			function enviarForma() {
				document.getElementById('frmPerfil').submit();
			}
		</script>


		<script>
			var txtExito = document.getElementById('exito').value;
			var txtRespuesta = document.getElementById('respuesta').value;
			if (txtExito == '1' && txtRespuesta.length > 0) {
				alert(txtRespuesta);
			}
		</script>
</body>
</html>