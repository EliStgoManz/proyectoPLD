<%@page import="datosRatos.ConsultaWS"%>
<%@page import="datosRatos.DatosClienteRaro"%>
<%@page import="entidad.Cliente"%>
<%@page import="utilidades.Archivos"%>
<%@page import="datosRatos.DatosBeneficiario"%>
<%@page import="entidad.Beneficiario"%>
<%@page import="java.io.File"%>
<%@page import="utilidades.Rutas"%>
<%@page import="utilidades.PerfilUsuario"%>
<%@page import="utilidades.Mensajes"%>
<%@include file="valida_login.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="entidad.Actividad"%>
<%@page import="datosRatos.DatosActividad"%>
<%@page import="entidad.TipoIdentificacion"%>
<%@page import="entidad.Pais"%>
<%@page import="datosRatos.DatosTipoIdentifiacion"%>
<%@page import="datosRatos.DatosPais"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="rfcCurpAppi.token"%>
<%@page
	import="org.apache.commons.io.comparator.LastModifiedFileComparator"%>


<%
	//VALIDACION DEL PERFIL DE USUARIO
	HttpSession sesion = request.getSession();
	String perfilId = null;
	String permiso = "";

	String vClienteParam = request.getParameter("idCliente");
	String vRFCParam = request.getParameter("rfc");
	String vMailParam = "";
	Cliente c = new Cliente();
	if (vClienteParam != null) {
		c = new DatosClienteRaro().get("trim(cliente_id) = trim('" + vClienteParam + "')");
		vMailParam = c.geteMail();
	}

	try {
		perfilId = sesion.getAttribute("perfilId").toString();

		if (perfilId.equals(PerfilUsuario.Admin_Telemarketing) || perfilId.equals(PerfilUsuario.Supervisor)
				|| perfilId.equals(PerfilUsuario.Ejecutivo_de_ventas)) {
			Mensajes.setMensaje("No tiene persmisos para ver esta pantalla.");
			request.setAttribute("resultado", Mensajes.mensaje);
			request.setAttribute("exito", "1");
			request.getRequestDispatcher("/mensajes.jsp").forward(request, response);

		}
	} catch (Exception es) {
		System.out.println("Es un perfil de cliente");
		response.sendRedirect("/tipo_persona.jsp?idCliente=" + vClienteParam + "&rfc="
				+ vRFCParam.trim().replace("&", "%26"));
	}
%>

<!DOCTYPE html>
<html>
<head>
<title>Efectivale - PLD</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
<link href="./webAC/css/style.css" rel="stylesheet">
<!-- Spinner styles -->
<link href="./css/spinner.css" rel="stylesheet">
<link href="./css/jquery-ui.css" rel="stylesheet">
<link href="./css/style.css" rel="stylesheet" />


<style type="text/css">
.ui-progressbar {
	position: relative;
}

.progress-label {
	position: absolute;
	left: 50%;
	top: 4px;
	font-weight: bold;
	text-shadow: 1px 1px 0 #fff;
}

.popup {
	position: relative;
	display: inline-block;
	cursor: pointer;
}

/* The actual popup (appears on top) */
.popup .popuptext {
	visibility: hidden;
	width: 160px;
	background-color: #555;
	color: #fff;
	text-align: center;
	border-radius: 6px;
	padding: 8px 0;
	position: absolute;
	z-index: 1;
	bottom: 125%;
	left: 50%;
	margin-left: -80px;
}

/* Popup arrow */
.popup .popuptext::after {
	content: "";
	position: absolute;
	top: 100%;
	left: 50%;
	margin-left: -5px;
	border-width: 5px;
	border-style: solid;
	border-color: #555 transparent transparent transparent;
}

/* Toggle this class when clicking on the popup container (hide and show the popup) */
.popup .show {
	visibility: visible;
	-webkit-animation: fadeIn 1s;
	animation: fadeIn 1s
}

/* Add animation (fade in the popup) */
@-webkit-keyframes fadeIn {
	from {opacity: 0;
}

to {
	opacity: 1;
}

}
@keyframes fadeIn {
	from {opacity: 0;
}

to {
	opacity: 1;
}

}
#cover {
	position: fixed;
	height: 100%;
	width: 100%;
	top: 0;
	left: 0;
	background: rgba(0, 0, 0, 0.5);
	z-index: 9999;
}

#covers {
	position: fixed;
	height: 100%;
	width: 100%;
	top: 0;
	left: 0;
	background: rgba(0, 0, 0, 0.5);
	z-index: 9999;
}

.spinner-title {
	margin-top: 20px;
	text-align: center;
	text-transform: uppercase;
	color: #fff;
	font-size: 19px;
	padding-top: 10px;
	text-shadow: 1px 1px 0px #111;
}

.container-spinner {
	position: absolute;
	top: 50%;
	left: 50%;
	margin-left: -100px;
	height: 150px;
	width: 200px;
	margin-top: -75px;
}
</style>
<script>
	// When the user clicks on <div>, open the popup
	function myFunction() {
		var popup = document.getElementById("myPopup");
		popup.classList.toggle("show");
	}
</script>
<style type="text/css">
.boton_personalizado {
	text-decoration: none;
	padding: 10px;
	font-weight: 600;
	font-size: 11px;
	color: #ffffff;
	background-color: grey;
	border-radius: 6px;
	border: 0.5px solid #212529;
}

.boton_personalizado2 {
	text-decoration: none;
	padding: 13px;
	font-weight: 600;
	font-size: 11px;
	color: #ffffff;
	background-color: #dc3545;
	border-radius: 6px;
	border: 0px solid #dc3545;
}
</style>




</head>
<body onload="startTimer(90)">
	<div id="cover"></div>
	<div id="covers" style="display: none">
		<div class="container-spinner">
			<h1 class="spinner-title">Comprimiendo...</h1>
		</div>
	</div>


	<!-- Spinner -->
	<div class="spinner-wrapper">
		<div class="container-spinner">
			<!--       <div class="gearbox">
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
      
   
            </div>  -->

			<div id="progressbar">
				<div class="progress-label"></div>
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
					<h2 class="text-muted">
						Datos y Documentos<small>, Persona Física.</small>
					</h2>
				</article>
				<aside class="col-xs-12 col-sm-4 col-md-3    col-lg-3">
					<p class="lead">
						Bienvenido:
						<%@include file="detectausuario.jsp"%>
						<!--   Bienvenido:  out.println( vClienteParam );    -->
					</p>
				</aside>
			</div>
			<div class="row">
				<div class="col-md-9 table-responsive">
					<form onsubmit="return validarFormularioFisicaV();"
						id="frmPersonaFisica1" action="registroPersonaFisica"
						method="post" autocomplete="off" enctype="multipart/form-data">
						<%
							//Llamamos a los paï¿½ses desde la base de datos
							ArrayList listaPaises = new DatosPais().getList();
							ArrayList listaTipoIdentificacion = new DatosTipoIdentifiacion().getList();
							ArrayList listaActivida = new DatosActividad().getList("");
						%>
						<input type="hidden" name="respuesta" id="respuesta"
							value="<c:out value="${resultado}" />" /> <input type="hidden"
							name="exito" id="exito" value="<c:out value="${exito}" />" /> <input
							type="hidden" id="tipoPersona" name="tipoPersona" value="F">
						<input type="hidden" id="Cliente_Id" name="Cliente_Id"
							value="<%out.print(vClienteParam);%>"> <input
							id="esEdicion" name="esEdicion" type="hidden"
							value="<c:out value='${esEdicion}' />"> <input
							id="estadoCliente" name="estadoCliente" type="hidden"
							value="<c:out value='${estado}' />">


						<table border="0">
							<tr>
								<td colspan="3">
									<p class="text-justify">
										Con el fin de cumplir con las obligaciones de Identificación,
										Verificación y envío de Avisos que marca la"Ley Federal para
										la Prevención e Identificación de Operaciones con Recursos de
										Procedencia Ilicita", hemos creado un formulario que deberá
										ser completado por nuestros Clientes/Prospectos.<br /> Cabe
										aclarar que el llenado de los formularios y la entrega de la
										documentación correspondiente no es opcional, es obligatoria
										de conformidad con el art. 21 de la citada Ley.<br /> Artículo
										21. Los clientes o usuarios de quienes realicen Actividades
										Vulnerables les proporcionarán a éstos la información y
										documentación necesaria para el cumplimiento de las
										obligaciones que esta Ley establece. Quienes realicen las
										Actividades Vulnerables deberán abstenerse, sin
										responsabilidad alguna, de llevar a cabo el acto u operación
										de que se trate, cuando sus clientes o usuarios se nieguen a
										proporcionarles la referida información o documentación.<br />
										<br> <a href="aviso.html">Aviso de Privacidad</a><br>
									</p>
								</td>
							</tr>
							<tr>
								<td width="350px"><label for="id">Id del cliente:</label><br />
									<label for="id">
										<%
											out.print(vClienteParam);
										%>
								</label></td>
								<td width="50px"></td>
								<td width="350px">* Correo electrónico </br> <input
									class="form-control" type="email" onkeyup="minus(this)"
									name="correo" id="correo"
									value="<%out.println(vMailParam);%>" readonly="readonly"
									maxlength="60">
								</td>
							</tr>

							<tr>
								<td><label for="telPais" class=""> * Teléfono país</label>
									<input type="hidden" id="idtelPais" name="idTelPais"
									value="<c:out value="${pais}" />"> <select
									class="browser-default" name="telPais" id="telPais"
									style="width: 80%;">
										<option value="">* Teléfono país</option>
										<%
											//Verificamos que tengamos paï¿½ses depuï¿½s de la consulta a la base de datos
											if (listaPaises != null) {
												for (int i = 0; i < listaPaises.size(); i++) {
													Pais p = (Pais) listaPaises.get(i);
													out.println("<option value=\"" + p.getPais() + "\">" + p.getDescrpcion() + "</option>");
												}
											}
										%>

								</select></td>
								<td></td>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" name="telefono"
											id="telefono" onkeypress="return esNumero(this, event)"
											maxlength="10" value="<c:out value="${telefono}" />">
										<label for="telefono">* Teléfono</label>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="3">
									<h4>Datos y Documentos</h4>
								</td>
							</tr>
							<tr>
								<td colspan="3">

									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											name="nombres" id="nombres" maxlength="200" size="80"
											value="<c:out value="${nombre}" />"> <label
											for="nombres" class=""> * Nombre(s)</label>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											name="paterno" id="paterno" maxlength="200"
											value="<c:out value="${apellidopaterno}" />"> <label
											for="paterno" class=""> * Apellido paterno </label>
									</div>
								</td>
								<td></td>

								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											name="materno" id="materno" maxlength="200"
											value="<c:out value="${apellidomaterno}" />"> <label
											for="materno" class=""> * Apellido materno </label>
									</div>
								</td>
							</tr>
							<tr>
								<td><input class="chzn-select" type="date"
									name="fechaNaciento" id="fechaNaciento"
									placeholder="* Fecha de nacimiento"
									value="<c:out value="${fechanacimiento}" />"> <label>*Fecha
										de nacimiento</label></td>
								<td><img src="img/calendar.png" height="32" width="32" />
								</td>
								<td><label for="paisNacimiento" class=""> * País de
										Nacimiento </label> <input type="hidden" id="idPaisNacimiento"
									name="idPaisNacimiento" value="<c:out value="${paisnacim}" />">
									<select class="browser-default" name="paisNacimiento"
									id="paisNacimiento" style="width: 90%;">
										<option value="">* País de nacimiento</option>
										<%
											//Verificamos que tengamos paï¿½ses depuï¿½s de la consulta a la base de datos
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

								<td><br> <label for="paisNacionalidad" class="">
										* País de Nacionalidad </label> <input type="hidden"
									id="idPaisNacionalidad" name="idPaisNacionalidad"
									value="<c:out value="${paisnacio}" />"> <select
									class="browser-default" name="paisNacionalidad"
									id="paisNacionalidad" style="width: 90%;">
										<option value="">* País de nacionalidad</option>
										<%
											//Verificamos que tengamos paï¿½ses depuï¿½s de la consulta a la base de datos
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
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											onkeyup="mayus(this);" name="RFC" id="RFC" maxlength="13"
											value="<%out.print(vRFCParam);%>" readonly="readonly">
										<label for="RFC">* RFC</label><br /> <br>
									</div>
								</td>
								<td></td>

								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											name="curp" id="curp" size="18" maxlength="18"
											value="<c:out value="${curp}" />"> <label for="curp">*
											CURP</label>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="3"><label for="tipo">CURP</label><br /> <input
									type="hidden" id="idarchivoCurp" name="idarchivoCurp"
									value="<c:out value="${imagenCurp}" />"> <input
									type="hidden" id="archivoCurpZip" name="archivoCurpZip" /> <input
									type="file" name="archivoCurp" id="archivoCurp"
									accept="document/pdf"
									onchange="validarArchivoCurp('lupaId', this, document.getElementById('archivoCurpZip'), document.getElementById('covers'))">&nbsp;
									<a href="javascript:PreviewImage('archivoCurp')"> <img
										src="img/lupa.jpg" height="32" width="32"
										style="display: none" id="lupaId" />
								</a>
									<p class="help-block">Un solo archivo de máximo 10MB</p> <%
 	String idCLiente = vClienteParam;
 	String rutaOrigen = Rutas.rutaCarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirCurp
 			+ Rutas.separador;
 	String rutaDestino = Rutas.rutaDescarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirCurp
 			+ Rutas.separador;

 	File[] listaArchivos = new File(rutaOrigen).listFiles();
 	if (listaArchivos != null && listaArchivos.length > 0) {
 		Arrays.sort(listaArchivos, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
 		out.println("<INPUT type='hidden' id='HayArchivoCurp' name='HayArchivoCurp' value='si'");
 		int a = 0;
 		if ((perfilId.compareTo("1") == 0) || (perfilId.compareTo("2") == 0)) { // Puede ver el historial                                                                                                                                                                                
 			out.println("<p>Historial de documentos guardados:</p>");
 			a = listaArchivos.length;
 		} else {
 			out.println("<p>Último archivo guardado:</p>");
 			a = 1;
 		}
 		for (int i = 0; i < a; i++) {
 			File archivoDestino = new File(rutaDestino + listaArchivos[i].getName());
 			System.out.println(archivoDestino.getAbsolutePath());
 			System.out.println(archivoDestino.getParent());

 			//String dirRootDes = new File(Rutas.rutaDescarga + Rutas.separador + idCLiente).getParentFile().getName();
 			String dirRootDes = Rutas.rutaDescarga + "/" + idCLiente;
 			if (!new File(archivoDestino.getParent()).exists()) {
 				try {
 					new File(archivoDestino.getParent()).mkdirs();
 				} catch (Exception es) {
 					es.printStackTrace();
 				}
 			}

 			try {
 				out.println("<p>" + listaArchivos[i].getName() + " <a href=\"javascript:window.open('"
 						+ dirRootDes + "/" + Rutas.dirCurp + "/" + listaArchivos[i].getName()
 						+ "' , 'clearcache=yes')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>");
 			} catch (Exception es) {
 				es.printStackTrace();
 			}

 		} //for
 	} else {
 		out.println("<INPUT type='hidden' id='HayArchivoCurp' name='HayArchivoCurp' value='no'");
 	}
 %></td>
							</tr>
							<tr>
								<td colspan="3"><label for="paisNacimiento" class="">
										* Actividad económica </label> <input type="hidden"
									id="idActividadEco" name="idActividadEco"
									value="<c:out value="${actividad_id}" />"> <select
									class="browser-default" name="actividadEco" id="actividadEco"
									style="width: 96%;">
										<option value="">* Actividad económica</option>
										<%
											//Verificamos que tengamos países depuï¿½s de la consulta a la base de datos
											if (listaActivida != null) {
												for (int i = 0; i < listaActivida.size(); i++) {
													Actividad a = (Actividad) listaActivida.get(i);
													out.println("<option value=\"" + a.getActividad_Id() + "\">" + a.getDescripcion() + "</option>");
												}
											}
										%>
								</select> <br>
								<br></td>
							</tr>
							<tr>
								<td colspan="3" style="display: none;">
									<div class="md-form form-sm">
										<input type="text" onkeyup="mayus(this)"
											id="buscarActividadEco" name="buscarActividadEco"
											class="input_text" onfocusout="validarActividadEco()">
										<label for="buscarActividadEco">* Actividad económica</label><br />
										<br>
									</div>

								</td>


							</tr>

							<tr>
								<td><label for="tipoIdentificacion">* Tipo ID</label> <input
									type="hidden" id="idTipoId" name="idTipoId"
									value="<c:out value="${identifica_id}" />"> <select
									class="browser-default" class="" name="tipoIdentificacion"
									id="tipoIdentificacion">
										<option value="">* Tipo de ID</option>
										<%
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
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											name="numeroId" id="numeroId" maxlength="40"
											value="<c:out value="${numeroid}" />"> <label
											for="numeroId" class=""> * No. de ID</label>
									</div>
								</td>
							</tr>

							<tr>

								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											name="otroTipoId" id="otroTipoId" maxlength="200"
											onkeyup="hayOtraIdentificacion()"
											value="<c:out value="${identificaciontipo}" />"> <label
											for="otroTipoId" class=""> * Otro tipo de ID</label>
									</div>
								</td>
								<td></td>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											name="autoridadEmite" id="autoridadEmite" maxlength="200"
											value="<c:out value="${autoridademiteid}" />"> <label
											for="autoridadEmite" class=""> * Entidad que emite ID</label>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="3"><label for="tipo">* Carga de ID</label><br />
									<input type="hidden" id="idarchivoIdPF" name="idarchivoIdPF"
									value="<c:out value="${imagenrlid}" />"> <input
									type="hidden" id="archivoIdPFZip" name="archivoIdPFZip" /> <input
									type="file" name="archivoIdPF" id="archivoIdPF"
									accept="document/pdf"
									onchange="validarArchivoId('lupaIdRepresentante', this, document.getElementById('archivoIdPFZip'), document.getElementById('covers'))">&nbsp;
									<a href="javascript:PreviewImage('archivoIdPF')"> <img
										src="img/lupa.jpg" height="32" width="32"
										style="display: none" id="lupaIdRepresentante" />
								</a>

									<p class="help-block">Un solo archivo de máximo 10MB</p> <%
 	idCLiente = vClienteParam;
 	rutaOrigen = Rutas.rutaCarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirIdentificacion
 			+ Rutas.separador;
 	rutaDestino = Rutas.rutaDescarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirIdentificacion
 			+ Rutas.separador;

 	listaArchivos = new File(rutaOrigen).listFiles();
 	if (listaArchivos != null && listaArchivos.length > 0) {
 		Arrays.sort(listaArchivos, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
 		out.println("<INPUT type='hidden' id='HayArchivoIdPF' name='HayArchivoIdPF' value='si'");
 		int a = 0;
 		if ((perfilId.compareTo("1") == 0) || (perfilId.compareTo("2") == 0)) { // Puede ver el historial                                                                                                                                                                                
 			out.println("<p>Historial de documentos guardados:</p>");
 			a = listaArchivos.length;
 		} else {
 			out.println("<p>Último archivo guardado:</p>");
 			a = 1;
 		}
 		for (int i = 0; i < a; i++) {
 			File archivoDestino = new File(rutaDestino + listaArchivos[i].getName());
 			System.out.println(archivoDestino.getAbsolutePath());
 			System.out.println(archivoDestino.getParent());

 			//String dirRootDes = new File(Rutas.rutaDescarga + Rutas.separador + idCLiente).getParentFile().getName();
 			String dirRootDes = Rutas.rutaDescarga + "/" + idCLiente;
 			if (!new File(archivoDestino.getParent()).exists()) {
 				try {
 					new File(archivoDestino.getParent()).mkdirs();
 				} catch (Exception es) {
 					es.printStackTrace();
 				}
 			}

 			try {
 				out.println("<p>" + listaArchivos[i].getName() + " <a href=\"javascript:window.open('"
 						+ dirRootDes + "/" + Rutas.dirIdentificacion + "/" + listaArchivos[i].getName()
 						+ "', 'clearcache=yes')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>");
 			} catch (Exception es) {
 				es.printStackTrace();
 			}

 		} //for
 	} else {
 		out.println("<INPUT type='hidden' id='HayArchivoIdPF' name='HayArchivoIdPF' value='no'");
 	}
 %></td>
							</tr>
							<tr>
								<td colspan="3"><label for="tipo">* Carga de cédula
										fiscal</label><br /> <input type="hidden" id="idarchivoCedula"
									name="idarchivoCedula"
									value="<c:out value="${imagenCedula}" />"> <input
									type="hidden" id="archivoCedulaZip" name="archivoCedulaZip" />
									<input type="file" name="cedulaPF" id="cedulaPF"
									accept="document/pdf"
									onchange="validarArchivoCedulaFiscal('lupaCedula', this, document.getElementById('archivoCedulaZip'), document.getElementById('covers'))">&nbsp;
									<a href="javascript:PreviewImage('cedulaPF')"> <img
										src="img/lupa.jpg" height="32" width="32"
										style="display: none" id="lupaCedula" />
								</a>
									<p class="help-block">Un solo archivo de máximo 10MB</p> <%
 	idCLiente = vClienteParam;
 	rutaOrigen = Rutas.rutaCarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirCedula
 			+ Rutas.separador;
 	rutaDestino = Rutas.rutaDescarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirCedula
 			+ Rutas.separador;

 	listaArchivos = new File(rutaOrigen).listFiles();
 	if (listaArchivos != null && listaArchivos.length > 0) {
 		Arrays.sort(listaArchivos, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
 		out.println("<INPUT type='hidden' id='HayArchivoIdPF' name='HayArchivoIdPF' value='si'");
 		int a = 0;
 		if ((perfilId.compareTo("1") == 0) || (perfilId.compareTo("2") == 0)) { // Puede ver el historial                                                                                                                                                                                
 			out.println("<p>Historial de documentos guardados:</p>");
 			a = listaArchivos.length;
 		} else {
 			out.println("<p>Último archivo guardado:</p>");
 			a = 1;
 		}
 		for (int i = 0; i < a; i++) {
 			File archivoDestino = new File(rutaDestino + listaArchivos[i].getName());
 			System.out.println(archivoDestino.getAbsolutePath());
 			System.out.println(archivoDestino.getParent());

 			//String dirRootDes = new File(Rutas.rutaDescarga + Rutas.separador + idCLiente).getParentFile().getName();
 			String dirRootDes = Rutas.rutaDescarga + "/" + idCLiente;
 			if (!new File(archivoDestino.getParent()).exists()) {
 				try {
 					new File(archivoDestino.getParent()).mkdirs();
 				} catch (Exception es) {
 					es.printStackTrace();
 				}
 			}

 			try {
 				out.println("<p>" + listaArchivos[i].getName() + " <a href=\"javascript:window.open('"
 						+ dirRootDes + "/" + Rutas.dirCedula + "/" + listaArchivos[i].getName()
 						+ "', 'clearcache=yes')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>");
 			} catch (Exception es) {
 				es.printStackTrace();
 			}
 		} //for
 	} else {
 		out.println("<INPUT type='hidden' id='HayArchivoIdPF' name='HayArchivoIdPF' value='no'");
 	}
 %></td>
							</tr>

							<tr>
								<td colspan="3">
									<h4>Domicilio</h4>
								</td>
								<td></td>
							</tr>

							<tr>
								<td>
									<!--
                                                    <select class="browser-default" id="tipoDomicilio" name="tipoDomicilio">
                                                        <option value="">* Tipo domicilio</option>    
                                                        <option value="N">Nacional</option>
                                                            <option value="E">Exranjero</option>
                                                    </select><br>--> <input
									type="hidden" name="tipoDomicilio" id="tipoDomicilio" value="N">

								</td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											name="calle" id="calle" maxlength="100"
											value="<c:out value="${calle}" />"> <label
											for="calle" class=""> * Calle</label>
									</div>
								</td>
								<td></td>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											name="exterior" id="exterior" maxlength="56"
											value="<c:out value="${numexterior}" />"> <label
											for="exterior" class=""> * No Exterior </label>
									</div>
								</td>
							</tr>
							<tr id="Imp21">

							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="delegacion" id="delegacion" maxlength="100"
										value="<c:out value="${delegacion}" />"> <label
										for="delegacion" class=""> * Delegación o Municipio </label>
							</td>
							 							<td></td> 
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="interior" id="interior" maxlength="40"
										value="<c:out value="${numinterior}" />"> <label
										for="interior" class="">  No Interior </label>
								</div>
							</td>
						</tr>

							<tr>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											name="colonia" id="colonia" maxlength="100"
											value="<c:out value="${colonia}" />"> <label
											for="colonia" class=""> * Colonia </label>
									</div>
								</td>
								<td></td>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" name="cp" id="cp"
											maxlength="5" onkeypress="return esNumero(this, event)"
											value="<c:out value="${codpostal}" />"> <label
											for="cp" class=""> * Código Postal </label>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											name="estado" id="estado" maxlength="100"
											value="<c:out value="${estado_prov}" />"> <label
											for="estado" class=""> * Estado </label>
									</div>
								</td>
								<td></td>
								<td><label for="paisDomicilio">* País</label><br /> <input
									type="hidden" id="idPaisDomicilio" name="idPaisDomicilio"
									value="<c:out value="${paisDomicilio}" />"> <select
									class="browser-default" name="paisDomicilio" id="paisDomicilio"
									style="width: 90%;">
										<option value="">* País</option>
										<%
											//Verificamos que tengamos paï¿½ses depuï¿½s de la consulta a la base de datos
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
								<td colspan="3"><label for="tipo">* Comprobante de
										domicilio</label>
									<div class="popup" onclick="myFunction()">
										<img src="img/signo.jpg" height="32" width="32" /> <span
											class="popuptext" id="myPopup">Recibo de pago por
											servicios domiciliados o Estados de cuenta bancarios, con
											antigüedad no mayor a tres meses a su fecha de emisión,
											Contrato de arrendamiento vigente, Constancia de Inscripción
											en el RFC.</span>
									</div>
									<br /> <input type="hidden" id="idarchivoComprobanteDom"
									name="idarchivoComprobanteDom"
									value="<c:out value="${archivoComprobanteDomicilio}" />">
									<input type="hidden" id="archivoComprobanteDomZip"
									name="archivoComprobanteDomZip" /> <input type="file"
									name="comprobanteDomicilio" id="comprobanteDomicilio"
									accept="document/pdf"
									onchange="validarArchivoComprobanteDomicilio('lupaDomicilio', this, document.getElementById('archivoComprobanteDomZip'), document.getElementById('covers'))">&nbsp;

									<a href="javascript:PreviewImage('comprobanteDomicilio')">
										<img src="img/lupa.jpg" height="32" width="32"
										style="display: none" id="lupaDomicilio" />
								</a>

									<p class="help-block">Un solo archivo de máximo 10MB</p> <%
 	idCLiente = vClienteParam;
 	rutaOrigen = Rutas.rutaCarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirComprobanteDom
 			+ Rutas.separador;
 	rutaDestino = Rutas.rutaDescarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirComprobanteDom
 			+ Rutas.separador;

 	listaArchivos = new File(rutaOrigen).listFiles();
 	if (listaArchivos != null && listaArchivos.length > 0) {
 		Arrays.sort(listaArchivos, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
 		out.println("<INPUT type='hidden' id='HayArchivoIdPF' name='HayArchivoIdPF' value='si'");
 		int a = 0;
 		if ((perfilId.compareTo("1") == 0) || (perfilId.compareTo("2") == 0)) { // Puede ver el historial                                                                                                                                                                                
 			out.println("<p>Historial de documentos guardados:</p>");
 			a = listaArchivos.length;
 		} else {
 			out.println("<p>Último archivo guardado:</p>");
 			a = 1;
 		}
 		for (int i = 0; i < a; i++) {
 			File archivoDestino = new File(rutaDestino + listaArchivos[i].getName());
 			System.out.println(archivoDestino.getAbsolutePath());
 			System.out.println(archivoDestino.getParent());

 			//String dirRootDes = new File(Rutas.rutaDescarga + Rutas.separador + idCLiente).getParentFile().getName();
 			String dirRootDes = Rutas.rutaDescarga + "/" + idCLiente;
 			if (!new File(archivoDestino.getParent()).exists()) {
 				try {
 					new File(archivoDestino.getParent()).mkdirs();
 				} catch (Exception es) {
 					es.printStackTrace();
 				}
 			}

 			try {
 				out.println("<p>" + listaArchivos[i].getName() + " <a href=\"javascript:window.open('"
 						+ dirRootDes + "/" + Rutas.dirComprobanteDom + "/" + listaArchivos[i].getName()
 						+ "', 'clearcache=yes')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>");
 			} catch (Exception es) {
 				es.printStackTrace();
 			}

 		} //for
 	} else {
 		out.println("<INPUT type='hidden' id='HayArchivoIdPF' name='HayArchivoIdPF' value='no'");
 	}
 %></td>
							</tr>
							<tr>
								<td colspan="3">
									<h4 id="tituloBeneficiario">Información del Dueño
										Beneficiario o Beneficiario Controlador y Origen de los
										Recursos.</h4>
								</td>
							</tr>
							<tr>
								<td>Descarga Declaratoria Dueño Beneficiario<br /> <a
									align="center"
									href="https://www.efectivale.com.mx/aml/DECLARATORIAS/Declaracion_Cliente_Persona_Fisica_v03.pdf"
									target="_blank"> <img src="img/descargar.png" widht="32"
										height="32" /></a>
								</td>

								<td colspan="3"><label for="tipo">* Declaración
										firmada Dueño Beneficiario</label><br /> <input type="hidden"
									id="idarchivoDeclaratoria" name="idarchivoDeclaratoria"
									value="<c:out value="${imagenDeclaratoria}" />"> <input
									type="hidden" id="archivoDeclaratoriaZip"
									name="archivoDeclaratoriaZip" /> <input type="file"
									name="archivoDeclaratoria" id="archivoDeclaratoria"
									accept="document/pdf"
									onchange="validarArchivoDeclaratoria('lupaDeclaratoria', this, document.getElementById('archivoDeclaratoriaZip'), document.getElementById('covers'))">&nbsp;
									<a href="javascript:PreviewImage('archivoDeclaratoria')"> <img
										src="img/lupa.jpg" height="32" width="32"
										style="display: none" id="lupaDeclaratoria" />
								</a>

									<p class="help-block">Un solo archivo de máximo 10MB</p> <%
 	idCLiente = vClienteParam;
 	rutaOrigen = Rutas.rutaCarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirDeclaratoria
 			+ Rutas.separador;
 	rutaDestino = Rutas.rutaDescarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirDeclaratoria
 			+ Rutas.separador;

 	listaArchivos = new File(rutaOrigen).listFiles();
 	if (listaArchivos != null && listaArchivos.length > 0) {
 		Arrays.sort(listaArchivos, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
 		out.println(
 				"<INPUT type='hidden' id='HayArchivoDeclaratoria' name='HayArchivoDeclaratoria' value='si'");
 		int a = 0;
 		if ((perfilId.compareTo("1") == 0) || (perfilId.compareTo("2") == 0)) { // Puede ver el historial                                                                                                                                                                                
 			out.println("<p>Historial de documentos guardados:</p>");
 			a = listaArchivos.length;
 		} else {
 			out.println("<p>Último archivo guardado:</p>");
 			a = 1;
 		}
 		for (int i = 0; i < a; i++) {
 			File archivoDestino = new File(rutaDestino + listaArchivos[i].getName());
 			System.out.println(archivoDestino.getAbsolutePath());
 			System.out.println(archivoDestino.getParent());

 			//String dirRootDes = new File(Rutas.rutaDescarga + Rutas.separador + idCLiente).getParentFile().getName();
 			String dirRootDes = Rutas.rutaDescarga + "/" + idCLiente;
 			if (!new File(archivoDestino.getParent()).exists()) {
 				try {
 					new File(archivoDestino.getParent()).mkdirs();
 				} catch (Exception es) {
 					es.printStackTrace();
 				}
 			}

 			try {
 				out.println("<p>" + listaArchivos[i].getName() + " <a href=\"javascript:window.open('"
 						+ dirRootDes + "/" + Rutas.dirDeclaratoria + "/" + listaArchivos[i].getName()
 						+ "', 'clearcache=yes')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>");
 			} catch (Exception es) {
 				es.printStackTrace();
 			}

 		} //for
 	} else {
 		out.println(
 				"<INPUT type='hidden' id='HayArchivoDeclaratoria' name='HayArchivoDeclaratoria' value='no'");
 	}
 %></td>
							</tr>
							<tr>
								<!--        <td colspan="3">
                                        <div class="row">
                                        <div class="col-12">
                                          <div class="md-form form-sm">
                                              
                                              
                                              <input type="checkbox" id="nobeneficiario" name="nobeneficiario" >
                                              <label for="nobeneficiario" class="bene" id="lblNoBeneficiario"  >
                                                Declaro que soy el único beneficiario de los servicios proporcionados por Efectivale
                                              </label><br>
                                          </div>
                                        </div>
                                      </div>
					</td> -->
							</tr>
							<tr>
								<td colspan="3">
									<div class="md-form form-sm">
										<input type="hidden" id="capturado" name="capturado" value="0">
										<input type="hidden" id="idDeclaroBeneficiario"
											name="NoBeneficiario"
											value="<c:out value="${declarobeneficiario}" />"> <input
											type="checkbox" id="sibeneficiario" name="sibeneficiario"
											class=""> <label for="sibeneficiario" class="bene">
											Tengo conocimiento del Dueño Beneficiario o Beneficiario
											Controlador </label><br>
									</div>
								</td>
							</tr>
							<tr>

								<td><select class="browser-default" name="tipoBeneficiario"
									id="tipoBeneficiario">
										<option value="">* Tipo</option>
										<option value="F">Física</option>
										<option value="M">Moral</option>
										<option value="X">Fideicomiso</option>

								</select></td>
								<td></td>
								<td>
									<%
										
									%> <br>
								<br>
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<!-- COMIENZA LA CONSUTLA SI TIENE BENEFICIARIOS --> <%
 	ConsultaWS w = new ConsultaWS();
 	String esEdicion = "";
 	String estatusCliente = "";
 	if (request.getAttribute("estado") != null) {
 		estatusCliente = request.getAttribute("estado").toString();
 	} else {
 		estatusCliente = "";
 	}
 	try {
 		if (request.getAttribute("esEdicion") != null) {
 			esEdicion = request.getAttribute("esEdicion").toString();
 		}
 	} catch (Exception es) {
 		es.printStackTrace();
 		System.out.println("Excepción durante la carga de la tabla de bebeficiarios");
 	}

 	String idCliente = "";
 	if (request.getAttribute("cliente_id") != null) {
 		idCliente = request.getAttribute("cliente_id").toString();
 	}

 	if (esEdicion != null && !esEdicion.isEmpty() && idCliente != null && !idCliente.isEmpty()) {

 		Beneficiario[] b = new DatosBeneficiario().getBeneList(idCliente);

 		out.println("<div id='divv'>");

 		if (b != null && b.length > 0) {

 			out.println("<script>");
 			//out.println("document.getElementById('capturado').value = '1';");
 			out.println("localStorage.setItem('capturado','1');");
 			out.println("</script>");

 			out.println("<h4>Dueño Beneficiario</h4>");
 			out.println("<table  >");
 			out.println("<thead>");
 			out.println("<tr>");
 			out.println("<td>No. Bene</td>");
 			out.println("<td>Nombre/Razón Social</td>");
 			out.println("<td>Tipo Persona</td>");
 			out.println("<td>Consultar</td>");
 			out.println("</tr>");
 			out.println("</thead>");
 			for (int i = 0; i < b.length; i++) {
 				String servlet = "";
 				if (b[i].getTipoPersona().equals("F")) {
 					servlet = "EdicionBeneFisica";
 				} else if (b[i].getTipoPersona().equals("M")) {
 					servlet = "EdicionBeneMoral";
 				} else if (b[i].getTipoPersona().equals("X")) {
 					servlet = "EdicionBeneFideicomiso";
 				}
 				out.println("<tr style=\"border-bottom:1px solid #000000\">");
 				out.println("<td>" + b[i].getNroBeneficiario() + "</td>");
 				out.println("<td>" + b[i].getNombreCompleto() + "</td>");
 				out.println("<td>" + b[i].getTipoPersonaDesc() + "</td>");
 				//out.println("<td><a href=\"" + servlet + "?idCliente=" + b[i].getCliente_id() + "&nroBene=" + b[i].getNroBeneficiario() + "&esEdicion=0\" target=\"_blank\" style=\"text-decoration: underline; color:blue;\"><img src=\"img/lupa.jpg\" height=\"32\" width=\"32\" align=\"center\"></a>"); 
 				out.println("<td><a href=\"" + servlet + "?idCliente=" + b[i].getCliente_id() + "&nroBene="
 						+ b[i].getNroBeneficiario() + "&estadoCliente=" + estatusCliente
 						+ "&esEdicion=0\" target=\"_blank\" style=\"text-decoration: underline; color:blue;\"><img src=\"img/lupa.jpg\" height=\"32\" width=\"32\" align=\"center\"></a>");
 				System.out.println("<td><a href=\"" + servlet + "?idCliente=" + b[i].getCliente_id().trim()
 						+ "&nroBene=" + b[i].getNroBeneficiario()
 						+ "&esEdicion=0\" target=\"_blank\" style=\"text-decoration: underline; color:blue;\"><img src=\"img/lupa.jpg\" height=\"32\" width=\"32\" align=\"center\"></a>");
 				out.println("</tr>");
 			}
 			out.println("</table>");
 			out.println("</br>");

 		} else {
 			out.println("<script>");
 			out.println("localStorage.setItem('capturado','0');");
 			out.println("</script>");
 		} //si hay beneficiario
 	} else { //es primera vez
 		boolean a = new DatosBeneficiario().limpiaBeneficiarios(vClienteParam);
 		out.println("<script>");
 		out.println("localStorage.setItem('capturado','0');");
 		out.println("</script>");
 	}

 	out.println("</div>");
 	int comillas = 34;
 	out.println("<a class='boton_personalizado' id ='btnBene' style='visibility:visible' href="
 			+ (char) comillas + "javascript:lanzarBeneficiario('" + vClienteParam + "')" + (char) comillas
 			+ ">Agregar Dueño Beneficiario o Beneficiario Controlaldor.</a>");
 	out.println("<a class='' align='left' id ='btnAct' href=" + (char) comillas
 			+ "javascript:ActualizaTablaBene('" + vClienteParam + "','" + w.consultarWsBene() + "')"
 			+ (char) comillas
 			+ "style='visibility:visible' > <img src='img/actualiza.png' height='32' width='32' align='center'></a>");
 	out.println("<br>");
 	out.println("</br>");
 	out.println("<input type='hidden' id='WsBene' value='" + w.consultarWsBene() + "'>");
 %>
								</td>

							</tr>


							<!--        <tr>
                                            <td colspan="3"><br><br>
							<h4>Datos del origen de los recursos</h4>
						</td>
					</tr>
                                        
                                        <tr>
                                            <td colspan="3">
                                                <div class="row">
                                                <div class="col-12">
                                                  <div class="md-form form-sm">
                                                      <input type="hidden" id="idDeclaroOrigen" name="idDeclaroOrigen" value="<c:out value="${declaroorigen}" />" >
                                                      
                                                      <input type="checkbox" id="declaroOrigen" name="declaroOrigen" >
                                                      <label for="declaroOrigen" class="">
                                                        Declaro para todos los efectos a que haya lugar, que los recursos utilizados para obtener los servicios proporcionados por EFECTIVALE son propios y de procedencia lícita
                                                      </label><br>
                                                  </div>
                                                </div>
                                              </div>						
						</td>
					</tr>  -->

							<tr>
								<td><a class="boton_personalizado2" id="btnClose"
									style="visibility: hidden" href="login.jsp">Cerrar</a></td>
								<td></td>
								<td>
									<button class="btn btn-danger" id="btn-procesar">Guardar</button>
								</td>
							</tr>
						</table>

					</form>
					<!-- formulario principal-->

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
		<script src="js/bootstrap.min.js"></script>
		<script src="js/validaciones.js"></script>
		<script src="js/mdb.min.js.descarga"></script>
		<script src="js/jquery-1.4.2.min.js"></script>
		<script src="js/jquery.autocomplete.js"></script>
		<script src="js/jquery-1.12.4.js"></script>
		<script src="js/jquery-ui.js"></script>




		<!-- Los siguientes divs para la previsualización de los archivos pdf -->
		<div onclick="off()" id="viewer">
			<div id="cerrarVisor">
				<img src="img/cancelar50x50.png" id="imgCerrarVisor"
					alt="Cerrar vista previa">
			</div>


			<div id="text">
				<iframe id="frame" frameborder="" scrolling="no" onclick="off()"></iframe>
			</div>
		</div>

		<script>
			$(document)
					.ready(
							function() {
								$("#cover").hide();
								//    $("#covers").hide();
								txtEstatus = document
										.getElementById('estadoCliente').value;
								if (txtEstatus === 'A') {
									//                    alert("entra validado");
									document.getElementById("btn-procesar").style.visibility = 'hidden';
									document.getElementById('btnClose').style.visibility = 'visible';
									document.getElementById('btnBene').style.visibility = 'hidden';

									//                        document.getElementById("telPais").disabled = true;
									//                        document.getElementById("fechaCreacion").disabled = true;
									//                        document.getElementById("actividadObjeto").disabled = true;
									//                        document.getElementById("nacionalidadCliente").disabled = true;
									//                        document.getElementById("archivoExistencia").disabled = true;
									//                        document.getElementById("archivoCedula").disabled = true;
									//                        document.getElementById("pais").disabled = true;
									//                        document.getElementById("archivoDomicilio").disabled = true;
									//                        document.getElementById("fFechaNacimiento").disabled = true;
									//                        document.getElementById("ftipoIdentificacion").disabled = true;
									//                        document.getElementById("archivofId").disabled = true;
									//                        document.getElementById("archivofCedula").disabled = true;
									//                        document.getElementById("archivofFacultades").disabled = true;
									//                        document.getElementById("nobeneficiario").disabled = true;
									//                        document.getElementById("sibeneficiario").disabled = true;
									//                        document.getElementById("tipoBeneficiario").disabled = true;
									//                        document.getElementById("declaroOrigen").disabled = true;
									var form = document
											.getElementById('frmPersonaFisica1');
									var elements = form.elements;
									for (var i = 0, len = elements.length; i < len; ++i) {
										//                                alert("for");
										elements[i].readOnly = true;
										elements[i].disabled = true;
									}
								}

								//ASIGNANDO MEXICO COMO PAÍS PREDETERMINADO
								//telPais
								var selector = document
										.getElementById('telPais');
								for (var i = 0; i < selector.length; i++) {
									if ('MX' == selector.options[i].value
											.trim()) {
										selector.selectedIndex = i;
										break;
									} else {//if
										selector.selectedIndex = 0;
									}
								}//for 

								//paisNacimiento
								var selector = document
										.getElementById('paisNacimiento');
								for (var i = 0; i < selector.length; i++) {
									if ('MX' == selector.options[i].value
											.trim()) {
										selector.selectedIndex = i;
										break;
									} else {//if
										selector.selectedIndex = 0;
									}
								}//for 

								//paisNacionalidad
								var selector = document
										.getElementById('paisNacionalidad');
								for (var i = 0; i < selector.length; i++) {
									if ('MX' == selector.options[i].value
											.trim()) {
										selector.selectedIndex = i;
										break;
									} else {//if
										selector.selectedIndex = 0;
									}
								}//for 

								//paisDomicilio
								var selector = document
										.getElementById('paisDomicilio');
								for (var i = 0; i < selector.length; i++) {
									if ('MX' == selector.options[i].value
											.trim()) {
										selector.selectedIndex = i;
										break;
									} else {//if
										selector.selectedIndex = 0;
									}
								}//for 
								//ASIGNANDO NO APLICA COMO ACTIVODAD ECO PREDET
								var selector = document
										.getElementById('actividadEco');
								for (var i = 0; i < selector.length; i++) {
									if ('1000000' == selector.options[i].value
											.trim()) {
										selector.selectedIndex = i;
										break;
									} else {//if
										selector.selectedIndex = 0;
									}
								}//for 

								//FUNCIONES CUÁNDO ES EDICION 
								esEdicion = document
										.getElementById('esEdicion').value;
								estadoCliente = document
										.getElementById('estadoCliente').value;

								if (esEdicion == '1') { //la carga de la página proviene de una edición del usuario y no de un nuevo usuario

									//ASIGNANDO ACTIVIDAD ECONOMICA
									txtActEcoId = document
											.getElementById('idActividadEco').value; //llega de la respuesta
									if (txtActEcoId != null
											|| txtActEcoId.length > 0) {
										var selector = document
												.getElementById('actividadEco');
										for (var i = 0; i < selector.length; i++) {
											if (txtActEcoId == selector.options[i].value) {
												selector.selectedIndex = i;
												document
														.getElementById('buscarActividadEco').value = selector.options[selector.selectedIndex].text;
												break;
											} else {//if
												selector.selectedIndex = 0;
											}
										}//for 
									}

									//ASIGNANDO TIPO DE INDENTIFICACION
									txtTipoIdentificacion = document
											.getElementById('idTipoId').value; //llega de la respuesta

									if (txtTipoIdentificacion != null
											|| txtTipoIdentificacion.length > 0) {
										var selector = document
												.getElementById('tipoIdentificacion');
										for (var i = 0; i < selector.length; i++) {
											if (txtTipoIdentificacion == selector.options[i].value) {
												selector.selectedIndex = i;
												break;
											} else {//if
												selector.selectedIndex = 0;
											}
										}//for 
									}

									//                  
									//Mostrando los check de las declaraciones  
									txtDeclaroBeneficiario = document
											.getElementById('idDeclaroBeneficiario').value;
									document.getElementById('sibeneficiario').checked = false;
									if (txtDeclaroBeneficiario == "1") {
										document
												.getElementById('sibeneficiario').checked = true;
									}

									//Mostrando mensajes
									var txtExito = document
											.getElementById('exito').value;
									var txtRespuesta = document
											.getElementById('respuesta').value;
									if (txtExito == '1'
											&& txtRespuesta.length > 0) {
										alert(txtRespuesta);
									}

								}//fin es edicion
							});// fin ready document
		</script>
		<script>
			function ActualizaTablaBene(vIdCliente, urlService) {

				var str = "";
				str = "<h4 id='titulo'>Dueño Beneficiario</h4>";
				str += "<table>";
				str += "<thead>";
				str += "<tr>";
				str += "<td>No. Bene</td>";
				str += "<td>Nombre/Razón Social</td>";
				str += "<td>Tipo Persona</td>";
				str += "<td>Consultar</td>";
				str += "</tr>";
				str += "</thead>";

				$
						.ajax({
							type : "GET",
							url : urlService,
							contentType : "application/json",
							dataType : "text",
							data : {
								idsales : vIdCliente
							},
							success : function(data) {
								var obj = JSON.parse(data);
								var servlet;
								if (obj.Beneficiarios != null
										&& obj.Beneficiarios.length > 0) {
									for (var i = 0; i < obj.Beneficiarios.length; i++) {
										if (obj.Beneficiarios[i].tipo_person == "F") {
											servlet = "EdicionBeneFisica";
										} else if (obj.Beneficiarios[i].tipo_person == "M") {
											servlet = "EdicionBeneMoral";
										} else if (obj.Beneficiarios[i].tipo_person == "X") {
											servlet = "EdicionBeneFideicomiso";
										}

										str += "<tr style=\"border-bottom:1px solid #000000\">";
										str += "<td>"
												+ obj.Beneficiarios[i].nrobene
												+ "</td>";
										str += "<td>"
												+ obj.Beneficiarios[i].nombre_completo
												+ "</td>";
										str += "<td>"
												+ obj.Beneficiarios[i].tipo_persona
												+ "</td>";
										str += "<td><a href=\""
												+ servlet
												+ "?idCliente="
												+ obj.Beneficiarios[i].cliente_id
												+ "&nroBene="
												+ obj.Beneficiarios[i].nrobene
												+ "&estadoCliente="
												+ obj.Beneficiarios[i].estadoCliente
												+ "&esEdicion=0\" target=\"_blank\" style=\"text-decoration: underline; color:blue;\"><img src=\"img/lupa.jpg\" height=\"32\" width=\"32\" align=\"center\"></a></td>";
										str += "</tr>";
									}
									str += "</table>";
									str += "<br/>";
									$("#divv").html(str);
									localStorage.setItem('capturado', '1');
								} else {
									localStorage.setItem('capturado', '0');
								}
							},
							error : function(xhr, var1, var2) {
								console.log("errorcito");

							}
						});

			}
			function llamadaBeneActualiz() {

				txtcliente = document.getElementById('Cliente_Id').value;
				txtrutaBene = document.getElementById('WsBene').value;

				ActualizaTablaBene(txtcliente, txtrutaBene);
			}

			function validarFormularioFisicaV() {
				if (validarFomularioFisica()) {
					$('.spinner-wrapper').fadeIn('fast');
					Prog();
					return true;
				} else {
					return false;
				}
			}
		</script>

		<script>
			function Prog() {
				var progressbar = $("#progressbar"), progressLabel = $(".progress-label");
				//var tiempo = 200;
				progressbar.progressbar({
					value : false,
					change : function() {
						progressLabel.text(progressbar.progressbar("value")
								+ "%");
					},
					complete : function() {
						progressLabel.text("96% Loading..");
					}
				});

				function progress() {
					var val = progressbar.progressbar("value") || 0;

					progressbar.progressbar("value", val + 1);

					if (val <= 50) {
						setTimeout(progress, 3600);
					} else if (val >= 51 && val < 99) {
						setTimeout(progress, 14400);
					}

				}

				setTimeout(progress, 14400);
			}
		</script>
		<script>
			function validarFomularioFisica() {

				//Que el correo tenga formato correcto
				object = document.getElementById('correo');
				valueForm = object.value;
				// Patron para el correo
				var patron = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,4})+$/;
				if (valueForm.search(patron) == 0) {

				} else {
					alert('El correo electrónico debe tener un formato válido');
					document.getElementById('correo').focus();
					return false;
				}
				//Que el correo no pertenezca a efectivale
				txtCorreo = document.getElementById('correo').value;
				var n = txtCorreo.search('efectivale');
				if (n > 0) {
					alert('El correo electrónico no debe pertenecer a Efectivale');
					document.getElementById('correo').focus();
					return false;
				}
				txtTelPais = document.getElementById('telPais').value;

				if (txtTelPais == null || txtTelPais.length == 0
						|| /^\s+$/.test(txtTelPais)) {
					alert('La clave de teléfono de país no puede dejarse en blanco');
					document.getElementById('telPais').focus();
					return false;
				}

				//Que el telefono no este vacio

				txtTelefono = document.getElementById('telefono').value;
				if (txtTelefono == null || txtTelefono.length == 0
						|| /^\s+$/.test(txtTelefono)) {
					alert('El número telefónico no debe dejarse en blanco');
					document.getElementById('telefono').focus();

					return false;
				}
				//que la longitud del telefóno se solo de 10 dígitos
				txtTelefono = document.getElementById('telefono').value;
				if (txtTelefono.length != 0) {
					if (txtTelefono.length != 10) {
						alert('El número telefónico debe ser de 10 dígitos');
						document.getElementById('telefono').focus();
						return false;
					}
				}

				txtNombres = document.getElementById('nombres').value;
				if (txtNombres == null || txtNombres.length == 0
						|| /^\s+$/.test(txtNombres)) {
					alert('El nombre no debe dejarse en blanco');
					document.getElementById('nombres').focus();
					return false;
				}

				txtPaterno = document.getElementById('paterno').value;
				if (txtPaterno == null || txtPaterno.length == 0
						|| /^\s+$/.test(txtPaterno)) {
					alert('El apellido paterno no debe dejarse en blanco');
					document.getElementById('paterno').focus();
					return false;
				}

				txtMaterno = document.getElementById('materno').value;
				if (txtMaterno == null || txtMaterno.length == 0
						|| /^\s+$/.test(txtMaterno)) {
					alert('El apellido materno no debe dejarse en blanco');
					document.getElementById('materno').focus();
					return false;
				}

				txtFechaNacimiento = document.getElementById('fechaNaciento').value;
				if (txtFechaNacimiento == null
						|| txtFechaNacimiento.length == 0
						|| /^\s+$/.test(txtFechaNacimiento)) {
					alert('No ha indicado una fecha de nacimiento');
					document.getElementById('fechaNaciento').focus();
					return false;

				}
				//normalizando fecha nac yymmdd
				txtFechaNacimiento = getFechaNac(txtFechaNacimiento);
				txtFechaNacimientoRFC = getFechaNacRFC(document
						.getElementById('RFC').value);

				if (txtFechaNacimiento != txtFechaNacimientoRFC) {
					alert('La fecha de nacimiento no coincide con la fecha de nacimiento del RFC');
					document.getElementById('fechaNaciento').focus();
					return false;
				}

				txtPaisNacimiento = document.getElementById('paisNacimiento').value;
				if (txtPaisNacimiento == null || txtPaisNacimiento.length == 0
						|| /^\s+$/.test(txtPaisNacimiento)) {
					alert('Debe seleccionar un país de nacimiento');
					document.getElementById('paisNacimiento').focus();
					return false;

				}

				txtCurp = document.getElementById('curp').value;
				if (txtCurp == null || txtCurp.length == 0
						|| /^\s+$/.test(txtCurp)) {
					alert('El CURP no debe dejarse en blanco');
					document.getElementById('curp').focus();
					return false;
				}

				txtCurp = document.getElementById('curp').value;
				if (txtCurp.length != 0) {
					if (txtCurp.length != 18) {
						alert('El CURP debe ser de 18 caracteres');
						document.getElementById('curp').focus();
						return false;
					}
				}

				txtActivodadEco = document.getElementById('actividadEco').value;
				if (txtActivodadEco == null || txtActivodadEco.length == 0
						|| /^\s+$/.test(txtActivodadEco)) {
					alert('Debe indicar una actividad económica');
					document.getElementById('actividadEco').focus();
					return false;
				}
				//            
				//            tipoIdentificacion = document.getElementById('tipoIdentificacion').value;
				//            txtotroTipoId  = document.getElementById('otroTipoId').value;
				//            
				//            if  ( txtotroTipoId == null || txtotroTipoId.length ==0 ){
				//                if ( tipoIdentificacion == null || tipoIdentificacion.length == 0 || /^\s+$/.test(tipoIdentificacion)){
				//                    alert('Debe selccionar un tipo de identificacion');
				//                    document.getElementById('tipoIdentificacion').focus();
				//                    return false;
				//                }
				//            }

				tipoIdentificacion = document
						.getElementById('tipoIdentificacion').value;
				if (tipoIdentificacion == null
						|| tipoIdentificacion.length == 0) {
					if (tipoIdentificacion == null
							|| tipoIdentificacion.length == 0
							|| /^\s+$/.test(tipoIdentificacion)) {
						alert('Debe selccionar un tipo de identificacion para el Representante Legal');
						document.getElementById('tipoIdentificacion').focus();
						return false;
					}
					alert('Debe selccionar un tipo de identificacion para el Representante Legal');
					document.getElementById('tipoIdentificacion').focus();
					return false;
				}
				//            otroTipoId  = document.getElementById('otroTipoId').value;
				//            if  ( otroTipoId == null || otroTipoId.length ==0 ){
				//                if ( otroTipoId == null || otroTipoId.length == 0 || /^\s+$/.test(otroTipoId)){
				//                    alert('Debe selccionar un Otro tipo id');
				//                    document.getElementById('otroTipoId').focus();
				//                    return false;
				//                }
				//                alert('Debe selccionar un Otro tipo id');
				//                    document.getElementById('tipoIdentificacion').focus();
				//                    return false;
				//            }
				txtNumeroId = document.getElementById('numeroId').value;
				if (txtNumeroId == null || txtNumeroId.length == 0
						|| /^\s+$/.test(txtNumeroId)) {
					alert('El número de identificación no puede dejarse en blanco');
					document.getElementById('numeroId').focus();
					return false;
				}
				txtautoridadEmite = document.getElementById('autoridadEmite').value;
				if (txtautoridadEmite == null || txtautoridadEmite.length == 0
						|| /^\s+$/.test(txtautoridadEmite)) {
					alert('La autoridad que emite no puede dejarse en blanco');
					document.getElementById('autoridadEmite').focus();
					return false;
				}

				txtarchivocedulaPF = document.getElementById('cedulaPF').value;
				if (txtarchivocedulaPF.length != 0) {
					if (!comprueba_extension(txtarchivocedulaPF)) {
						alert('El archivo de cédula fiscal debe ser con la extensión .pdf')
						document.getElementById('cedulaPF').focus();
						return false;
					}

					input = document.getElementById('cedulaPF');
					if (!validarTamanoArchivo(input, 10)) {
						alert('El tamaño del archivo de cédula fiscal excede los 10 MB que tiene como límite nuestro sistema');
						input = null;
						document.getElementById('cedulaPF').focus();
						return false;
					}
				}

				/**
				 * 
				 * VALIDANDO LOS CAMPOS DEL DOMICILIO DE LA PERSONA FÍSICA
				 */

				txtCalle = document.getElementById('calle').value;
				if (txtCalle == null || txtCalle.length == 0
						|| /^\s+$/.test(txtCalle)) {
					alert('La calle no puede dejarse en blanco');
					document.getElementById('calle').focus();
					return false;
				}

				txtExterior = document.getElementById('exterior').value;
				if (txtExterior == null || txtExterior.length == 0
						|| /^\s+$/.test(txtExterior)) {
					alert('El número exterior no puede dejarse en blanco');
					document.getElementById('exterior').focus();
					return false;
				}
				
				
				txtDelegMunic=document.getElementById('delegacion').value;
				if ( txtDelegMunic == null || txtDelegMunic.length == 0 || /^\s+$/.test(txtDelegMunic)){
				    alert('La delegación o municipio no pueden dejarse en blanco');
				    document.getElementById('delegacion').focus();
				    return false;
				}
				
				/*txtInterior = document.getElementById('interior').value;
				if ( txtInterior == null || txtInterior.length == 0 || /^\s+$/.test(txtInterior)){
				    alert('El interior no puede dejarse en blanco');
				    return false;
				}
				 */
				txtColonia = document.getElementById('colonia').value;
				if (txtColonia == null || txtColonia.length == 0
						|| /^\s+$/.test(txtColonia)) {
					alert('La colonia no puede dejarse en blanco');
					document.getElementById('colonia').focus();
					return false;
				}

				txtcp = document.getElementById('cp').value;
				if (txtcp == null || txtcp.length == 0 || /^\s+$/.test(txtcp)) {
					alert('El código postal no puede dejarse en blanco');
					document.getElementById('cp').focus();
					return false;
				}
				txtcp = document.getElementById('cp').value;
				if (txtcp.length != 0) {
					if (txtcp.length != 5) {
						alert('El Código Postal debe ser de 5 dígitos');
						document.getElementById('cp').focus();
						return false;
					}
				}

				//            txtcomprobanteDomicilio = document.getElementById('comprobanteDomicilio').value;
				//            
				//            if (  txtcomprobanteDomicilio.length > 0 ){
				//                if ( !comprueba_extension(txtcomprobanteDomicilio)){
				//                    alert ('El archivo de comprobante de domicilio debe ser con la extensión .pdf')
				//                    document.getElementById('comprobanteDomicilio').focus();
				//                    return false;                    
				//                }
				//                
				//                input = document.getElementById('comprobanteDomicilio');
				//                if ( !validarTamanoArchivo( input, 10 )){
				//                    alert('El peso del archivo de comprobande de domicilio excede los 10 MB que tiene como límite nuestro sistema');
				//                    input = null;
				//                    document.getElementById('comprobanteDomicilio').focus();
				//                    return false;
				//                }
				//            }

				txtPaisDomicilio = document.getElementById('paisDomicilio').value;
				if (txtPaisDomicilio == null || txtPaisDomicilio.length == 0
						|| /^\s+$/.test(txtPaisDomicilio)) {
					alert('El país del domicilio no puede dejarse en blanco');
					document.getElementById('paisDomicilio').focus();
					return false;
				}

				chkSiBeneficiario = document.getElementById('sibeneficiario').checked;

				if (document.getElementById('archivoCurp').value.length == 0)
					document.getElementById('archivoCurpZip').value = '';
				if (document.getElementById('archivoIdPF').value.length == 0)
					document.getElementById('archivoIdPFZip').value = '';
				if (document.getElementById('cedulaPF').value.length == 0)
					document.getElementById('archivoCedulaZip').value = '';
				if (document.getElementById('comprobanteDomicilio').value.length == 0)
					document.getElementById('archivoComprobanteDomZip').value = '';
				if (document.getElementById('archivoDeclaratoria').value.length == 0)
					document.getElementById('archivoDeclaratoriaZip').value = '';

				document.getElementById('btn-procesar').disabled=true;
				alert("Validando RFC y CURP");
				if (validarCapturaBeneficiario() && validarRFC() && validarCURP()) {
					
					document.getElementById('btn-procesar').disabled=false;
					return true;
				} else {
					document.getElementById('btn-procesar').disabled=false;
					return false;
				}
			}
			//fin de la funcion validar formuario
		</Script>
		<script>
	function validarRFC(){
		
		var txtRFC = document.getElementById('RFC').value;
	
		var obj="";
		var bandera=null;
		
		if(txtRFC != null && txtRFC != ""){
			
		
		$.ajax({
			async:false,
			crossDomain : true,
			type : "GET",
// 			url : urlService,
			url : "http://localhost:8080/PLD2/ServeletApiRFC",
			contentType : "application/json",
			dataType : "text",
			data : {
				rfc : txtRFC
			},
			
			success : function(data) {
			  obj = JSON.parse(data);
				
				if(obj.respuesta=="RFC Valido"){
// 					alert("RFC Valido");
			    	bandera = true;
// 			    	return true;
			    }else{
			    	alert("El RFC ingresado no se encuentra en la lista de RFC no cancelados");
			    	bandera = false;
			    	document.getElementById('RFC').focus();
// 			    	return false;
			    }
				
			},
			error : function(xhr, var1, var2) {
				console.log("errorcito");

			}
		});
	}else{
		bandera = true;
	}

		return bandera;
	
	}
	
	function validarCURP(){
		
		var txtCurp = document.getElementById('curp').value;
		
		var obj="";
		var bandera=null;
		
		if(txtCurp != null && !txtCurp==""){
		
		$.ajax({
			async:false,
			crossDomain : true,
			type : "GET",
// 			url : urlService,
			url : "http://localhost:8080/PLD2/ServeletApiCURP",
			contentType : "application/json",
			dataType : "text",
			data : {
				curp : txtCurp
			},
			
			success : function(data) {
			  obj = JSON.parse(data);
				
				if(obj.respuesta=="ERROR"){
					alert("CURP no valido");
			    	bandera = false;
			    	document.getElementById('curp').focus();
// 			    	return false;
// 			    	
			    }else{
			    	
// 			    	alert("CURP Valido");
			    	bandera = true;
// 			    	return true;
			    }
				
			},
			error : function(xhr, var1, var2) {
				console.log("errorcito");

			}
		});
		
		}else{
			bandera = true;
		}

		return bandera;
		
	}
	
	</script>

		<script>
			function hayOtraIdentificacion() {
				txtOtraIdentificacion = document.getElementById('otroTipoId').value;

				if (txtOtraIdentificacion.trim().length > 0
						|| txtOtraIdentificacion !== '') {
					document.getElementById('tipoIdentificacion').selectedIndex = 0;
				}
			}
			//    

			function validarCapturaBeneficiario() {

				txtSiBeneficiario = document.getElementById('sibeneficiario').checked;
				txtCapturado = localStorage.getItem('capturado');
				if (txtSiBeneficiario) {
					//return true;
					if (txtCapturado === '0') {
						alert('Debe capturar por lo menos un beneficiario');
						return false;
					} else {
						return true;
					}
				} else {
					//$('.spinner-wrapper').fadeIn('fast');
					$('#sibeneficiario')
							.click(
									function() {
										if (this.checked) {
											document
													.getElementById('sibeneficiario').checked = false;
										}
									});
					return true;
				}
			}

			function lanzarBeneficiario(vIdCliente) {
				estadoCliente = document.getElementById('estadoCliente').value;
				txtSiBeneficiario = document.getElementById('sibeneficiario').checked;
				if (txtSiBeneficiario) {
					e = document.getElementById('tipoBeneficiario');
					sel = e.options[e.selectedIndex].value;

					switch (sel) {
					case 'F':

						document.getElementById('capturado').value = '1';

						//alert(estadoCliente);    
						//document.getElementById('esEdicion').value = '1';
						//alert(document.getElementByID('esEdicion').value);
						window
								.open(
										'beneficiario_personafisica.jsp?esEdicion='
												+ document
														.getElementById('esEdicion').value
												+ '&estadoCliente='
												+ document
														.getElementById('estadoCliente').value
												+ '&idCliente=' + vIdCliente,
										'_blank');
						break;
					case 'M':
						document.getElementById('capturado').value = '1';
						window
								.open(
										'beneficiario_personamoral.jsp?esEdicion='
												+ document
														.getElementById('esEdicion').value
												+ '&estadoCliente='
												+ document
														.getElementById('estadoCliente').value
												+ '&idCliente=' + vIdCliente,
										'_blank');

						break;
					case 'X':
						document.getElementById('capturado').value = '1';
						window
								.open(
										'beneficiario_fideicomiso.jsp?esEdicion='
												+ document
														.getElementById('esEdicion').value
												+ '&estadoCliente='
												+ document
														.getElementById('estadoCliente').value
												+ '&idCliente=' + vIdCliente,
										'_blank');
						break;
					default:
						alert('Debe seleccionar el tipo de beneficiario');
					}//SWITCH

				} else {
					alert('Para agregar un beneficiario debe inicar que conoce la información del mismo');
				}
			}
		</script>
</body>
</html>

