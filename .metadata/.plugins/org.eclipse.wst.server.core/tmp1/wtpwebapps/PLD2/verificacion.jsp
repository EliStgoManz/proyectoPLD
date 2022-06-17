<%@page import="entidad.TipoIdentificacion"%>
<%@page import="entidad.Pais"%>
<%@page import="datosRatos.DatosActividad"%>
<%@page import="datosRatos.DatosTipoIdentifiacion"%>
<%@page import="datosRatos.DatosPais"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entidad.UsuarioTransitivo"%>
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
							<h1 class="title title-contrata-ahora">Sistema de
								Prevención de Lavado de Dinero</h1>
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
					<h2 class="text-muted">
						Datos del cliente <small>revisión</small>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img
							src="img/imprimir.png" width="32" height="32" />
					</h2>
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
					<form action="" class="">
						<%  //Llamamos a los pa�ses desde la base de datos
							ArrayList listaPaises = new DatosPais().getList();
							ArrayList listaTipoIdentificacion = new DatosTipoIdentifiacion().getList();
							ArrayList listaActivida = new DatosActividad().getList("");
						%>
						<table border="0">

							<tr>
								<td><label for="tipo">* Estatus</label><br /> <select
									class="browser-default" name="" id="option">
										<option value="">Por validar</option>
										<option value="">Valido</option>
										<option value="">Invalidado</option>
										<option value="">Ventas Gobierno</option>
								</select></td>
								<td></td>
								<td>
									<div class="row">
										<div class="col-12">
											<div class="md-form form-sm">
												<input type="checkbox" id="tc" name="tc"
													data-validation-error-msg="Acepte Términos y Condiciones."
													required="" data-validation="required"> <label
													for="tc" class=""> Fecha de bloqueo </label><br> <input
													type="text" id="correo" class="form-control"
													value="01/04/2018">
											</div>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td><label for="tipo">* Fecha de validación</label><br />
									<input type="text" id="correo" class="form-control"
									value="01/04/2018" disabled></td>
								<td></td>
								<td><label for="tipo">* Fecha ult. mod.</label><br /> <input
									type="text" id="correo" class="form-control" value="01/04/2018"
									disabled></td>
							</tr>
							<tr>
								<td colspan="3"><label for="tipo">Mensaje:</label><br /> <textarea
										class="form-control">
							</textarea></td>
							</tr>
							<tr>
								<td colspan="3"><label for="tipo">Notas:</label><br /> <textarea
										class="form-control">
							</textarea></td>
							</tr>

							<tr>
								<td><label for="id">Id del cliente: <%
									try {
										out.println(UsuarioTransitivo.getCliente().getCliente_Id().toString());
									} catch (Exception es) {

									}
								%>

								</label></td>
								<td width="30px"></td>
								<td><select class="browser-default" name="" id="option">
										<option value="">* Tipo</option>
										<option value="">F�sica</option>
										<option value="">Moral</option>
										<option value="">Fideicomiso</option>
										<option value="">Gobierno</option>
								</select></td>
							</tr>
							<tr>
								<td><input class="form-control" type="text" id="correo"
									value="* Correo electrónico"></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td><select class="browser-default" name="" id="option">
										<option value="">* Telefono país</option>
										<%
											//Verificamos que tengamos pa�ses depu�s de la consulta a la base de datos
											if (listaPaises != null) {
												for (int i = 0; i < listaPaises.size(); i++) {
													Pais p = (Pais) listaPaises.get(i);
													out.println("<option value=\"" + p.getPais() + "\">" + p.getDescrpcion() + "</option>");
												}
											}
										%>
								</select></td>
								<td></td>
								<td><input class="form-control" type="text" id="correo"
									value="* Teléfono"></td>
							</tr>
							<tr>
								<td colspan="3">
									<h4>Datos del cliente</h4>
								</td>
							</tr>
							<tr>
								<td colspan="3"><input class="form-control" type="text"
									id="correo" size="80" value="* Nombres"></td>
							</tr>
							<tr>
								<td><input class="form-control" type="text" id="correo"
									value="* Apellido paterno"></td>
								<td></td>
								<td><input class="form-control" type="text" id="correo"
									value="* Apellido materno"></td>
							</tr>
							<tr>
								<td><label for="telefono">Fecha de nacimiento:</label><br />
									<input class="date" type="text" id="correo"> <img
									src="img/calendar.png" height="32" width="32" /></td>
								<td></td>
								<td><select class="browser-default" name="" id="option">
										<option value="">* Pa�s de nacimiento</option>
										<%
											//Verificamos que tengamos pa�ses depu�s de la consulta a la base de datos
											if (listaPaises != null) {
												for (int i = 0; i < listaPaises.size(); i++) {
													Pais p = (Pais) listaPaises.get(i);
													out.println("<option value=\"" + p.getPais() + "\">" + p.getDescrpcion() + "</option>");
												}
											}
										%>
								</select></td>
							</tr>
							<tr>
								<td></td>
								<td></td>
								<td><select class="browser-default" name="" id="option">
										<option value="">* Pa�s de nacionalidad</option>
										<%
											//Verificamos que tengamos pa�ses depu�s de la consulta a la base de datos
											if (listaPaises != null) {
												for (int i = 0; i < listaPaises.size(); i++) {
													Pais p = (Pais) listaPaises.get(i);
													out.println("<option value=\"" + p.getPais() + "\">" + p.getDescrpcion() + "</option>");
												}
											}
										%>
								</select></td>
							</tr>
							<tr>
								<td><input class="form-control" type="text" id="correo"
									value="* RFC"></td>
								<td></td>
								<td><input class="form-control" type="text" id="correo"
									value="* CURP"></td>
							</tr>
							<tr>
								<td colspan="3"><label for="tipo">Curp</label><br /> <input
									type="file" id="archivo">&nbsp;<img src="img/lupa.jpg"
									height="32" width="32" />
									<p class="help-block">Un solo archivo de máximo 10MB</p></td>
							</tr>
							<tr>
								<td colspan="3"><input class="form-control" type="text"
									id="correo" size="80" value="* Actividad económica"></td>
							</tr>
							<tr>
								<td><select class="browser-default" class="" name=""
									id="option">
										<option value="">* Tipo de ID</option>
										<%
											//Verificamos que tengamos pa�ses depu�s de la consulta a la base de datos
											if (listaTipoIdentificacion != null) {
												for (int i = 0; i < listaTipoIdentificacion.size(); i++) {
													TipoIdentificacion tid = (TipoIdentificacion) listaTipoIdentificacion.get(i);
													out.println(
															"<option value=\"" + tid.getIdentifica_id() + "\">" + tid.getDescripcion() + "</option>");
												}
											}
										%>
								</select></td>
								<td></td>
								<td><input class="form-control" type="text" id="correo"
									value="* No de ID"></td>
							</tr>
							<tr>
								<td><input class="form-control" type="text" id="correo"
									value="* Otro tipo de ID"></td>
								<td></td>
								<td><input class="form-control" type="text" id="correo"
									value="* Entidad que emite ID"></td>
							</tr>
							<tr>
								<td colspan="3"><label for="tipo">* Carga de ID</label><br />
									<input type="file" id="archivo">&nbsp;<img
									src="img/lupa.jpg" height="32" width="32" />
									<p class="help-block">Un solo archivo de máximo 10MB</p></td>
							</tr>
							<tr>
								<td colspan="3"><label for="tipo">* Carga de cedula
										fiscal</label><br /> <input type="file" id="archivo">&nbsp;<img
									src="img/lupa.jpg" height="32" width="32" />
									<p class="help-block">Un solo archivo de máximo 10MB</p></td>
							</tr>
							<tr>
								<td colspan="3">
									<h4>Domicilio</h4>
								</td>
							</tr>
							<tr>
								<td><input class="form-control" type="text" id="correo"
									value="* Calle"></td>
								<td></td>
								<td><input class="form-control" type="text" id="correo"
									value="* No Exterior"></td>
							</tr>
							<tr>
								<td></td>
								<td></td>
								<td><input class="form-control" type="text" id="correo"
									value="* No Interior"></td>
							</tr>
							<tr>
								<td><input class="form-control" type="text" id="correo"
									value="* Colonia"></td>
								<td></td>
								<td><input class="form-control" type="text" id="correo"
									value="* Codigo Postal"></td>
							</tr>
							<tr>
								<td colspan="3"><label for="tipo">* Comprobante de
										domicilio</label><br /> <input type="file" id="archivo">&nbsp;<img
									src="img/lupa.jpg" height="32" width="32" />
									<p class="help-block">Un solo archivo de máximo 10MB</p></td>
							</tr>
							<tr>
								<td colspan="3">
									<h4>Datos del beneficiario, controlador o dueño</h4>
								</td>
							</tr>
							<tr>
								<td colspan="3"><label> <input type="checkbox"
										name="" id=""> Se desconoce información del
										beneficiario
								</label></td>
							</tr>
							<tr>
								<td colspan="3">
									<p>Si conoce información del beneficiario</p>
								</td>
							</tr>
							<tr>
								<td><select class="browser-default" name="" id="option">
										<option value="">* Tipo</option>
										<option value="">F�sica</option>
										<option value="">Moral</option>
										<option value="">Fideicomiso</option>
										<option value="">Gobierno</option>
								</select></td>
								<td></td>
								<td>
									<button class="btn">Agregar beneficiario</button>
								</td>
							</tr>
							<tr>
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
					<p>
						<%@ include file="despliega_menu.jsp"%>
					</p>
					<a href="historial_cambios.jsp">Historial de cambios</a>
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
</body>
</html>