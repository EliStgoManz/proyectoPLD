<%@page import="utilidades.Mensajes"%>
<%@page import="entidad.Perfil"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%
	//VALIDACION DEL PERFIL DE USUARIO
	HttpSession sesion = request.getSession();
	String perfilId = null;

	try {
		perfilId = sesion.getAttribute("perfilId").toString();
		Perfil perfil = (Perfil) sesion.getAttribute("perfil");
		request.setAttribute("permiso", perfil.getAvisos());
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
<!-- Spinner styles -->
<link href="./css/spinner.css" rel="stylesheet">
</head>
<body>
	<!-- Spinner -->
	<div class="spinner-wrapper">
		<div class="container-spinner">
			<div class="gearbox">
				<div class="overlay"></div>
				<div class="gear one">
					<div class="gear-inner">
						<div class="bar"></div>
						<div class="bar"></div>
						<div class="bar"></div>
					</div>
				</div>
				<div class="gear two">
					<div class="gear-inner">
						<div class="bar"></div>
						<div class="bar"></div>
						<div class="bar"></div>
					</div>
				</div>
				<div class="gear three">
					<div class="gear-inner">
						<div class="bar"></div>
						<div class="bar"></div>
						<div class="bar"></div>
					</div>
				</div>
				<div class="gear four large">
					<div class="gear-inner">
						<div class="bar"></div>
						<div class="bar"></div>
						<div class="bar"></div>
						<div class="bar"></div>
						<div class="bar"></div>
						<div class="bar"></div>
					</div>
				</div>
			</div>
			<h1 class="spinner-title">Procesando...</h1>
		</div>
	</div>
	<!-- Spinner Fin-->

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
					<h2 class="text-muted">Generar avisos al SAT</h2>
				</article>
				<aside class="col-xs-12 col-sm-4 col-md-3 col-lg-3">
					<p class="lead">Bienvenido: Usuario PLD</p>
				</aside>
			</div>
			<div class="row">
				<div class="col-md-9 table-responsive">
					<form action="LeerExcel" id="send-file" method="post"
						enctype="multipart/form-data">
						<table border="0">
							<tr>
								<td><label for="tipo">Selecciona el archivo:</label></td>
								<td colspan="3"><input type="file" name="filetoupload"
									id="archivo" required="required"></td>

								<td colspan="1"><label for="mesreportado"> Mes
										Reportado: </label> <input type="month" name="mes_reportado"
									id="mesreportado" required="required"
									placeholder="* Fecha de nacimiento" /></td>
							</tr>
							<tr>
								<td></td>
								<td width="200"></td>
								<td width="30"></td>
								<td width="200">
									<button class="btn btn-danger" id="btn-procesar">Procesar</button>
								</td>
								<td width="200"></td>
							</tr>
							<tr>
								<td><label for="tipo">Mensaje:</label></td>
								<td colspan="4">
									<%
										String msg = (String) request.getAttribute("message");
										if (msg == null) {
											msg = "";
										}
									%> <textarea
										class="form-control"><%=msg%>
                                                    </textarea>
								</td>
							</tr>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td align="center">
									<button type="button" class="btn btn-danger" id="download">
										<img src="img/descargar.png" widht="32" height="32" />
									</button>
								</td>
								<td></td>
							</tr>
						</table>
					</form>
					<form id="download_file" method="post" action="DescargarArchivo">
						<%
							String file_name = (String) request.getAttribute("file_name");
							if (file_name != null) {
						%>
						<input type="hidden" name="file_name" id="file_name"
							value="<%=file_name%>" />
						<%
							}
						%>

					</form>
				</div>
				<div class="col-md-3">
					<p>
					<p>

						<%@ include file="despliega_menu.jsp"%>

					</p>
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
		<script>
			$(function() {
				$(document)
						.on(
								'click',
								'#download',
								function() {

									if ($('#file_name').length) {
										$('#download_file').submit();
									} else {
										alert('Porfavor Ingrese un archivo y presione "Procesar"');
									}
								});
			});
			$(document).on("click", "#btn-procesar", function(e) {
				if ($("#archivo").val() && $("#mesreportado").val()) {
					$('.spinner-wrapper').fadeIn('fast');
				} else {
					alert("Porfavor Seleccione un Archivo y Mes Reportado");
				}
			});
		</script>
		<script>
			$(document).ready(function() {
				//Asignando los persmisos
				document.getElementById('download').disabled = true;
				document.getElementById('btn-procesar').disabled = true;

				txtPersmiso = '${permiso}';
				if (txtPersmiso == 'E') {
					document.getElementById('download').disabled = false;
					document.getElementById('btn-procesar').disabled = false;
				}

			});
		</script>
</body>
</html>
