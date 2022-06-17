<%@page import="control.DigitoAlfanumerico"%>
<%@page import="datosRatos.ConsultaWS"%>
<%@page import="entidad.Representante"%>
<%@page import="listaEntidad.*"%>
<%@page import="datosRatos.DatosRepLegal"%>
<%@page import="datosRatos.DatosRepLegal"%>
<%@page import="datosRatos.DatosClienteRaro"%>
<%@page import="entidad.Cliente"%>
<%@page import="entidad.Perfil"%>
<%@page import="utilidades.Archivos"%>
<%@page import="entidad.Beneficiario"%>
<%@page import="datosRatos.DatosBeneficiario"%>
<%@page import="utilidades.Rutas"%>
<%@page import="java.io.File"%>
<%@page import="utilidades.Mensajes"%>
<%@page import="utilidades.PerfilUsuario"%>
<%@page import="entidad.TipoIdentificacion"%>
<%@page import="entidad.Pais"%>
<%@page import="datosRatos.DatosActividad"%>
<%@page import="datosRatos.DatosTipoIdentifiacion"%>
<%@page import="datosRatos.DatosPais"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.parser.JSONParser"%>
<%@page import="org.json.simple.parser.ParseException"%>
<%@page import="java.util.Calendar"%>
<%@page import="rfcCurpAppi.token"%>
<%@page
	import="org.apache.commons.io.comparator.LastModifiedFileComparator"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	//VALIDACION DEL PERFIL DE USUARIO
	HttpSession sesion = request.getSession();
	String perfilId = null;

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
		Perfil perfil = (Perfil) sesion.getAttribute("perfil");
		request.setAttribute("permiso", perfil.getVerificacion());
		if (perfil.getVerificacion().equals("N")) {
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
<link href="./css/jquery-ui.css" rel="stylesheet">
<link href="./css/style.css" rel="stylesheet" />
<link href="./css/spinner.css" rel="stylesheet">
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
	font-size: 20px;
	color: #ffffff;
	background-color: #dc3545;
	border-radius: 6px;
	border: 0px solid #dc3545;
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

.TitleCell{
	font-size: small;
	vertical-align: text-top;
	width: 100px;
}
.InfoCell{
	font-size: small;
	vertical-align: text-top;
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
			<div id="progressbar">
				<div class="progress-label"></div>
			</div>
			<h1 class="spinner-title">Procesando...</h1>
		</div>
	</div>
	<!-- Spinner Fin-->

	<header id="header">
		<div id="Imp1" class="container-fluid">
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
	<h1 class="title title-contrata-ahora white-text">Sistema de
		Prevención de Lavado de Dinero</h1>
	<div class="container">
		<div id="Imp2" class="main row">
			<article class="col-xs-12 col-sm-8 col-md-9 col-lg-9">
				<h2 class="text-muted">
					Datos y Documentos del Cliente <small>, Cliente
						Fideicomiso.</small>
				</h2>
				<%
				 Calendar cal= Calendar.getInstance();
				  int year= cal.get(Calendar.YEAR);
				out.println("<p id='indicadorAlfa' style='visibility: hidden'>INDICADOR: "+new DigitoAlfanumerico().calcularCodigoAlfaNumerico(vRFCParam,year)+"</p>");
				
				%>
			</article>
			<aside class="col-xs-12 col-sm-4 col-md-3 col-lg-3">
				<p class="lead">
					Bienvenido:
					<%@ include file="detectausuario.jsp"%>
					<!--   Bienvenido:  out.println( vClienteParam );    -->
				</p>
			</aside>
		</div>
		<div class="row">
			<div class="col-md-9 table-responsive">
				<form onsubmit="return validarFomularioFideicomisoS()"
					action="VerificacionFideicomiso" method="post" id="frmFideicomiso"
					name="frmFideicomiso" enctype="multipart/form-data">
					<%
						ArrayList listaPaises = new DatosPais().getList();
						ArrayList listaTipoIdentificacion = new DatosTipoIdentifiacion().getList();
					%>
					<input type="hidden" id="tipoPersona" name="tipoPersona"
						value="<c:out value="${tipopersona}" />"> <input
						type="hidden" id="tipoPersonaCambio" name="tipoPersonaCambio"
						value="<c:out value="${tipopersona}" />"> <input
						type="hidden" id="Cliente_Id" name="Cliente_Id"
						value="<%out.print(vClienteParam);%>"> <input
						id="esEdicion" name="esEdicion" type="hidden"
						value="<c:out value="${esEdicion}" />">
					<!-- CAMPOS PARA COMPLETAR LA VERIFICACION -->
					<input id="usuarioasignado" name="usuarioasignado" type="hidden"
						value="<c:out value="${usuarioasignado}" />"> <input
						id="fechacorte" name="fechacorte" type="hidden"
						value="<c:out value="${fechacorte}" />"> <input
						id="borrado" name="borrado" type="hidden"
						value="<c:out value="${borrado}" />">
						<input type="hidden" id="idRiesgo" name="idRiesgo" value="<c:out value="${riesgo}" />">

					<table border="0">
					<input type="hidden" id="EstatusAnterior" name="EstatusAnterior" value="<c:out value="${estado}" />">
                         <tr>

							<td><label id="Imp3" for="Estatus">* Estatus </label><br />
								<select class="browser-default" name="Estatus" id="Estatus"
								onchange="setFechaValidacion()">
									<option value="S" disabled="true">No iniciado</option>
									<option value="V">Por validar</option>
									<option value="A">Valido</option>
									<option value="I">Invalidado</option>
									<option value="G">Ventas Gobierno</option>
									<option value="P">Pendiente</option>
									<option value="N">Inactivo</option>
									<option value="B">Bloqueado</option>
							</select></td>
							<td></td>

							<td>
								<div class="row">
									<div class="col-12">

										<input type="checkbox" id="chkFechaBloqueo"
											name="chkFechaBloqueo" class=""> <label id="Imp33"
											for="chkFechaBloqueo"> Fecha de bloqueo </label><br> <br>
										<input type="date" id="fechaBloqueo" name="fechaBloqueo"
											value="<c:out value="${fechabloqueo}" />">

									</div>
								</div>

							</td>
						</tr>
						<tr id="Imp4">
							<td><label for="fechaValidacion">* Fecha de
									validación</label><br /> <input type="date" id="fechaValidacion"
								name="fechaValidacion" class="form-control"
								value="<c:out value="${fechavalidado}" />" readonly></td>
							<td></td>
							<td><label for="fechaModificacion">* Fecha de Alta.</label><br />
								<input type="date" id="fechaModificacion"
								name="fechaModificacion"
								value="<c:out value="${fecharegistro}" />" readonly></td>
						</tr>

						<tr>
							<td colspan="3"><label for="tipo">Mensaje:</label><br /> <textarea
									id="mensaje" name="mensaje" class="form-control">
							</textarea></td>
						</tr>
						<tr>
							<td colspan="3"><label for="tipo">Notas:</label><br /> <textarea
									id="notas" name="notas" class="form-control">
							</textarea></td>
						</tr>
						<tr>
							<td id="Imp5" width="350px"><label for="id">Id del
									cliente:</label><br /> <label for="id"> <%
 	out.print(vClienteParam);
 %>
							</label></td>
							<td width="50px"></td>
							<td><label id="Imp55" for="tipoP">Tipo de Persona:</label> <select
								class="browser-default" name="tipoP" id="tipoP"
								onchange="cambiarTipoPersona()">
									<option value="">* Tipo</option>
									<!--<option value=" //F">Física</option>-->
									<option value="M">Moral</option>
									<option value="X">Fideicomiso</option>
									<option value="G">Gobierno</option>
							</select></td>


						</tr>

						<tr id="Imp6">
							<td width="350px">* Correo electrónico <input
								class="form-control" type="email" onkeyup="minus(this)"
								name="correo" id="correo" maxlength="60"
								value="<%out.print(vMailParam);%>">
							</td>
						</tr>
						<tr>
							<td><input type="hidden" id="idtelPais" name="idTelPais"
								value="<c:out value="${pais}" />"> <select
								class="browser-default" name="telPais" id="telPais"
								style="width: 80%;">
									<option id="Imp7" value="">* Teléfono país</option>
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
							<td id="Imp77">
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										id="telefono" name="telefono"
										onkeypress="return esNumero(this, event)" maxlength="10"
										value="<c:out value="${telefono}" />"> <label
										for="telefono">* Teléfono</label>
								</div>
							</td>
						</tr>
						<tr id="Imp8">
							<td colspan="3">
								<h4>Datos y Documentos</h4>
							</td>
						</tr>
						<tr id="Imp9">
							<td colspan="3">
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										id="razonSocial" maxlength="120" name="razonSocial" size="80"
										value="<c:out value="${razonsocial}" />"> <label
										for="telefono">* Denominación o Razón social de la
										Institución Fiduciaria </label>
								</div>
							</td>
						</tr>

						<tr id="Imp10">
							<td colspan="3">
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										id="NombreFideicomiso" maxlength="120"
										name="NombreFideicomiso" size="80"
										value="<c:out value="${institucionfiduciaria}" />"> <label
										for="NombreFideicomiso">* Nombre del Fideicomiso </label>
								</div>
							</td>
						</tr>
						<tr id="Imp11">
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										id="rfc" name="rfc" maxlength="13"
										value="<%out.print(vRFCParam);%>" maxlength="12" readonly>
									<label for="rfc">* RFC</label>
								</div>
							</td>
							<td></td>
							<td></td>
						</tr>
						<tr id="Imp12">
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										id="noFideicomiso" name="noFideicomiso" maxlength="40"
										value="<c:out value="${nrofideicomiso}" />"> <label
										for="noFideicomiso">* No. Fideicomiso</label>
								</div>

							</td>
							<td></td>
							<td></td>
						</tr>
						
						<tr id="Imp17">
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="noescritura" name="noescritura" maxlength="100"
											value="<c:out value="${noescritura}" />"> <label
											for="noescritura">* No de Escritura</label>
									</div>
								</td>
								<td></td>
								<td><input class="" type="date" id="fechaNotarial"
									name="fechaNotarial"
									value="<c:out value="${fechaNotarial}" />"> <img
									src="img/calendar.png" height="32" width="32" /> <label
									for="fechaNotarial">* Fecha de Escritura</label><br>
								<br></td>
								
									
									</tr>
								<tr >	
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="notaria" name="notaria" maxlength="100"
											value="<c:out value="${notaria}" />"> <label
											for="notaria">*Notaria</label>
									</div>
								</td>
								
					</tr>
						
						
						
						<tr>
							<td colspan="3"><label for="tipo">* Acta
									constitutiva</label><br /> <input type="hidden" id="idarchivoActa"
								name="idarchivoActa"
								value="<c:out value="${imagenactaconstitutiva}" />"> <input
								type="hidden" id="archivoActaZip" name="archivoActaZip" /> <input
								type="file" id="actaConstitutiva" name="actaConstitutiva"
								accept="document/pdf"
								onchange="validarArchivoActaConst('lupaActa', this, document.getElementById('archivoActaZip'), document.getElementById('covers'))">&nbsp;
								<a href="javascript:PreviewImage('actaConstitutiva')"> <img
									src="img/lupa.jpg" height="32" width="32" style="display: none"
									id="lupaActa" />
							</a>
								<p class="help-block">Un solo archivo de máximo 25MB</p> <%
 	String idCLiente = vClienteParam;
 	String rutaOrigen = Rutas.rutaCarga + Rutas.separador + idCLiente + Rutas.separador
 			+ Rutas.dirActaConstitutiva + Rutas.separador;
 	String rutaDestino = Rutas.rutaDescarga + Rutas.separador + idCLiente + Rutas.separador
 			+ Rutas.dirActaConstitutiva + Rutas.separador;

 	File[] listaArchivos = new File(rutaOrigen).listFiles();
 	if (listaArchivos != null && listaArchivos.length > 0) {
 		Arrays.sort(listaArchivos, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
 		out.println("<INPUT type='hidden' id='HayArchivoActa' name='HayArchivoActa' value='si'");
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

 			//String dirRootDes = new File(Rutas.rutaDescarga + Rutas.separador + idCLiente ).getParentFile().getName();
 			String dirRootDes = Rutas.rutaDescarga + "/" + idCLiente;
 			if (!new File(archivoDestino.getParent()).exists()) {
 				try {
 					new File(archivoDestino.getParent()).mkdirs();
 				} catch (Exception es) {
 					es.printStackTrace();
 				}
 			}

 			try {
 				//new Archivos().copyFile(listaArchivos[i].getAbsolutePath(), archivoDestino.getAbsolutePath());
 				//out.println("<p>" + listaArchivos[i].getName() + " <a href=\"/" + dirRootDes + "/" + idCLiente + Rutas.separador + Rutas.dirActaConstitutiva + Rutas.separador + listaArchivos[i].getName() + "\" target=\"_blank\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>" );
 				out.println("<p>" + listaArchivos[i].getName() + " <a href=\"javascript:window.open('"
 						+ dirRootDes + "/" + Rutas.dirActaConstitutiva + "/" + listaArchivos[i].getName()
 						+ "', 'clearcache=yes')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>");
 			} catch (Exception es) {
 				es.printStackTrace();
 			}

 		} //for
 	} else {
 		out.println("<INPUT type='hidden' id='HayArchivoActa' name='HayArchivoActa' value='no'");
 	}
 %></td>
						</tr>
						
						<tr>
							<td colspan="3"><label for="tipo">* Cedula fiscal</label><br />
								<input type="hidden" id="idarchivoCedula" name="idarchivoCedula"
								value="<c:out value="${imagencedulafiscal}" />"> <input
								type="hidden" id="archivoCedulaZip" name="archivoCedulaZip" />
								<input type="file" id="cedulaFiscal" name="cedulaFiscal"
								accept="document/pdf"
								onchange="validarArchivoCedulaFiscal('lupaCedula', this, document.getElementById('archivoCedulaZip'), document.getElementById('covers'))">&nbsp;
								<a href="javascript:PreviewImage('cedulaFiscal')"> <img
									src="img/lupa.jpg" height="32" width="32" style="display: none"
									id="lupaCedula" />
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
 		out.println("<INPUT type='hidden' id='HayArchivoCedula' name='HayArchivoCedula' value='si'");
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

 			//String dirRootDes = new File(Rutas.rutaDescarga + Rutas.separador + idCLiente ).getParentFile().getName();
 			String dirRootDes = Rutas.rutaDescarga + "/" + idCLiente;
 			if (!new File(archivoDestino.getParent()).exists()) {
 				try {
 					new File(archivoDestino.getParent()).mkdirs();
 				} catch (Exception es) {
 					es.printStackTrace();
 				}
 			}

 			try {
 				//new Archivos().copyFile(listaArchivos[i].getAbsolutePath(), archivoDestino.getAbsolutePath());
 				//out.println("<p>" + listaArchivos[i].getName() + " <a href=\"/" + dirRootDes + "/" + idCLiente + Rutas.separador + Rutas.dirCedula + Rutas.separador + listaArchivos[i].getName() + "\" target=\"_blank\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>" );
 				out.println("<p>" + listaArchivos[i].getName() + " <a href=\"javascript:window.open('"
 						+ dirRootDes + "/" + Rutas.dirCedula + "/" + listaArchivos[i].getName()
 						+ "', 'clearcache=yes')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>");
 			} catch (Exception es) {
 				es.printStackTrace();
 			}

 		} //for
 	} else {
 		out.println("<INPUT type='hidden' id='HayArchivoCedula' name='HayArchivoCedula' value='no'");
 	}
 %></td>
						</tr>				
						<tr>
							<td colspan="3" id="Imp13">
								<h4>Domicilio</h4>
							</td>
							<td></td>
						</tr>
						<tr id="Imp14">
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										id="calle" name="calle" maxlength="100"
										value="<c:out value="${calle}" />"> <label for="calle">*
										Calle</label>
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
						<tr id="Imp16">
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										id="colonia" name="colonia" maxlength="100"
										value="<c:out value="${colonia}" />"> <label
										for="colonia"> * Colonia</label>
								</div>
							</td>
							<td></td>
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										id="cp" name="cp" onkeypress="return esNumero(this, event)"
										maxlength="5" value="<c:out value="${codpostal}" />">
									<label for="cp"> * Código Postal</label>
								</div>
							</td>
						</tr>
						<tr>
							<td id="Imp17">
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										id="estado" name="estado" maxlength="100"
										value="<c:out value="${estado_prov}" />"> <label
										for="estado"> * Estado</label>
								</div>
							</td>
							<td></td>
							<td><input type="hidden" id="idPaisDomicilio"
								name="idPaisDomicilio"
								value="<c:out value="${paisDomicilio}" />"> <select
								class="browser-default" name="pais" id="pais"
								style="width: 90%;">
									<option id='Imp177' value="">* País</option>
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
									domicilio</label><br /> <input type="hidden" id="idarchivoDomicilio"
								name="idarchivoDomicilio"
								value="<c:out value="${imagencomprobantedom}" />"> <input
								type="hidden" id="archivoDomicilioZip"
								name="archivoDomicilioZip" /> <input type="file"
								id="archivoDomicilio" name="archivoDomicilio"
								accept="document/pdf"
								onchange="validarArchivoComprobanteDomicilio('lupaDomicilio', this, document.getElementById('archivoDomicilioZip'), document.getElementById('covers'))">&nbsp;
								<div class="popup" onclick="myFunction()">
									<img src="img/signo.jpg" height="32" width="32" /> <span
										class="popuptext" id="myPopup">Recibo de Luz, Predial.</span>
								</div> <a href="javascript:PreviewImage('archivoDomicilio')"> <img
									src="img/lupa.jpg" height="32" width="32" style="display: none"
									id="lupaDomicilio" />
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
 		out.println("<INPUT type='hidden' id='HayArchivoDomicilio' name='HayArchivoDomicilio' value='si'");
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
 		out.println("<INPUT type='hidden' id='HayArchivoDomicilio' name='HayArchivoDomicilio' value='no'");
 	}
 %></td>
						</tr>
						<tr id="Imp18">
							<td colspan="3">
								<h4>Datos del representante legal</h4>
							</td>
						</tr>
						<tr id="Imp19">
							<td colspan="3">
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										id="fnombre" name="fnombre" maxlength="200" size="80"
										value="<c:out value="${rlnombre}" />"> <label
										for="fnombre">* Nombre(s)</label>
								</div>
							</td>
						</tr>
						<tr id="Imp20">
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										id="fpaterno" name="fpaterno" maxlength="200"
										value="<c:out value="${rlapellidopaterno}" />"> <label
										for="fpaterno">* Apellido paterno</label>
								</div>
							</td>
							<td></td>
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										id="fmaterno" name="fmaterno" maxlength="200"
										value="<c:out value="${rlapellidomaterno}" />"> <label
										for="fmaterno">* Apellido materno</label>
								</div>
							</td>
						</tr>
						<tr id="Imp21">
							<td><input class="" type="date" id="fFechaNacimiento"
								name="fFechaNacimiento"
								value="<c:out value="${rlfechanacimiento}" />"> <img
								src="img/calendar.png" height="32" width="32" /> <label
								for="fFechaNacimiento">* Fecha de nacimiento</label><br> <br></td>
							<td></td>
							<td></td>

						</tr>

						<tr id="Imp22">
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										id="fRFC" name="fRFC" maxlength="13"
										value="<c:out value="${rlrfc }" />"> <label for="fRFC">*
										RFC</label>
								</div>
							</td>
							<td></td>
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										id="fCURP" name="fCURP" maxlength="18"
										value="<c:out value="${rlcurp}" />"> <label
										for="fCURP">* CURP</label>
								</div>
							</td>
						</tr>
						<tr>
							<td><input type="hidden" id="idTipoIdentificacion"
								name="idTipoIdentificacion"
								value="<c:out value="${rlidentifica_id}" />"> <label
								for="ftipoIdentificacion">* Tipo ID</label> <select
								class="browser-default" class="" name="ftipoIdentificacion"
								id="ftipoIdentificacion">
									<option id="Imp23" value="">* Tipo de ID</option>
									<%
										//Verificamos que tengamos paï¿½ses depuï¿½s de la consulta a la base de datos
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
							<td id="Imp233">
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										id="fNumeroId" name="fNumeroId" maxlength="40"
										value="<c:out value="${rlnumeroid}" />"> <label
										for="fNumeroId">* No. Id</label>
								</div>
							</td>
						</tr>
						<tr id="Imp24">
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" id="fotroTipoId"
										name="fotroTipoId" maxlength="200"
										onkeypress="hayOtraIdentificacion()"
										value="<c:out value="${rlidentificaciontipo}" />"> <label
										for="fotroTipoId">* Otro tipo id</label>
								</div>
							</td>
							<td></td>
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										id="fautoridadEmite" name="fautoridadEmite" maxlength="200"
										value="<c:out value="${rlautoridademiteid}" />"> <label
										for="fautoridadEmite">* Entidad que emite ID</label>
								</div>
							</td>
						</tr>


	
						
						
						
						<tr id="Imp17">
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="nopoder" name="nopoder" maxlength="100"
											value="<c:out value="${nopoder}" />"> <label
											for="nopoder">* No de Poder</label>
									</div>
								</td>
								<td></td>
								<td><input class="" type="date" id="rlfechaNotarial"
									name="rlfechaNotarial"
									value="<c:out value="${rlfechaNotarial}" />"> <img
									src="img/calendar.png" height="32" width="32" /> <label
									for="rlfechaNotarial">* Fecha de Poder </label><br>
								<br></td>
								
								</tr>
								<tr >	
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="rlnotaria" name="rlnotaria" maxlength="100"
											value="<c:out value="${rlnotaria}" />"> <label
											for="rlnotaria">*Notaria</label>
									</div>
								</td>
								
					</tr>
						
						
						
						
						
						
						
						
						<tr>
							<td colspan="3"><label for="tipo">* Carga de ID</label><br />
								<input type="hidden" id="idarchivoRlIdentificacion"
								name="idarchivoRlIdentificacion"
								value="<c:out value="${imagenrlid}" />"> <input
								type="hidden" id="archivoRlIdentificacionZip"
								name="archivoRlIdentificacionZip" /> <input type="file"
								name="archivofId" id="archivofId" accept="document/pdf"
								onchange="validarArchivoId('lupafId', this, document.getElementById('archivoRlIdentificacionZip'), document.getElementById('covers'))">&nbsp;
								<a href="javascript:PreviewImage('archivofId')"> <img
									src="img/lupa.jpg" height="32" width="32" style="display: none"
									id="lupafId" />
							</a>
								<p class="help-block">Un solo archivo de máximo 10MB</p> <%
 	idCLiente = vClienteParam;
 	rutaOrigen = Rutas.rutaCarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirRlIdentificacion
 			+ Rutas.separador;
 	rutaDestino = Rutas.rutaDescarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirRlIdentificacion
 			+ Rutas.separador;
 	listaArchivos = new File(rutaOrigen).listFiles();
 	if (listaArchivos != null && listaArchivos.length > 0) {
 		Arrays.sort(listaArchivos, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
 		out.println("<INPUT type='hidden' id='HayArchivofID' name='HayArchivofID' value='si'");
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

 			//String dirRootDes = new File(Rutas.rutaDescarga + Rutas.separador + idCLiente ).getParentFile().getName();
 			String dirRootDes = Rutas.rutaDescarga + "/" + idCLiente;
 			if (!new File(archivoDestino.getParent()).exists()) {
 				try {
 					new File(archivoDestino.getParent()).mkdirs();
 				} catch (Exception es) {
 					es.printStackTrace();
 				}
 			}

 			try {
 				//new Archivos().copyFile(listaArchivos[i].getAbsolutePath(), archivoDestino.getAbsolutePath());
 				//out.println("<p>" + listaArchivos[i].getName() + " <a href=\"/" + dirRootDes + "/" + idCLiente + Rutas.separador + Rutas.dirRlIdentificacion + Rutas.separador + listaArchivos[i].getName() + "\" target=\"_blank\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>" );
 				out.println("<p>" + listaArchivos[i].getName() + " <a href=\"javascript:window.open('"
 						+ dirRootDes + "/" + Rutas.dirRlIdentificacion + "/" + listaArchivos[i].getName()
 						+ "', 'clearcache=yes')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>");
 			} catch (Exception es) {
 				es.printStackTrace();
 			}

 		} //for
 	} else {
 		out.println("<INPUT type='hidden' id='HayArchivofID' name='HayArchivofID' value='no'");
 	}
 %></td>
						</tr>

						<!--	<tr>
						<td colspan="3">
							<label for="tipo">* Carga de cédula fiscal</label><br/>
                                                        <input type="hidden" id="idarchivoCedulaRepresentante" name="idarchivoCedulaRepresentante" value="<c:out value="${imagenrlcedulafiscal}" />">
                                                        <input type="hidden" id="archivoCedulaRepresentanteZip" name="archivoCedulaRepresentanteZip" />
                                                        <input type="file" name="archivofCedula" id="archivofCedula" accept="document/pdf" onchange="validarArchivoCedulaFiscal('lupafCedula', this, document.getElementById('archivoCedulaRepresentanteZip'))" >&nbsp;
                                                        <a href="javascript:PreviewImage('archivofCedula')">
                                                            <img src="img/lupa.jpg" height="32" width="32" style="display:none" id="lupafCedula" />
                                                        </a>
							<p class="help-block">Un solo archivo de máximo 10MB</p>
                                                         <%idCLiente = vClienteParam;
			rutaOrigen = Rutas.rutaCarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirRlCedulaFiscal
					+ Rutas.separador;
			rutaDestino = Rutas.rutaDescarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirRlCedulaFiscal
					+ Rutas.separador;
			listaArchivos = new File(rutaOrigen).listFiles();
			if (listaArchivos != null && listaArchivos.length > 0) {
				Arrays.sort(listaArchivos, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
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

					//String dirRootDes = new File(Rutas.rutaDescarga + Rutas.separador + idCLiente ).getParentFile().getName();
					String dirRootDes = Rutas.rutaDescarga + "/" + idCLiente;
					if (!new File(archivoDestino.getParent()).exists()) {
						try {
							new File(archivoDestino.getParent()).mkdirs();
						} catch (Exception es) {
							es.printStackTrace();
						}
					}

					try {
						//new Archivos().copyFile(listaArchivos[i].getAbsolutePath(), archivoDestino.getAbsolutePath());
						//out.println("<p>" + listaArchivos[i].getName() + " <a href=\"/" + dirRootDes + "/" + idCLiente + Rutas.separador + Rutas.dirRlCedulaFiscal + Rutas.separador + listaArchivos[i].getName() + "\" target=\"_blank\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>" );
						out.println("<p>" + listaArchivos[i].getName() + " <a href=\"javascript:window.open('"
								+ dirRootDes + "/" + Rutas.dirRlCedulaFiscal + "/" + listaArchivos[i].getName()
								+ "', 'clearcache=yes')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>");
					} catch (Exception es) {
						es.printStackTrace();
					}

				} //for
			}%>
														
						</td>
					</tr>	-->
						<tr>
							<td colspan="3"><label for="tipo">* Poder notarial</label><br />
								<input type="hidden" id="idarchivoPoderNotarial"
								name="idarchivoPoderNotarial"
								value="<c:out value="${imagenrlcedulafiscal}" />"> <input
								type="hidden" id="archivoPoderNotarialZip"
								name="archivoPoderNotarialZip" /> <input type="file"
								name="archivofPoderNotarial" id="archivofPoderNotarial"
								accept="document/pdf"
								onchange="validarArchivoPoderNotarial('lupafPoderNotarial', this, document.getElementById('archivoPoderNotarialZip'), document.getElementById('covers'))">&nbsp;
								<a href="javascript:PreviewImage('archivofPoderNotarial')">
									<img src="img/lupa.jpg" height="32" width="32"
									style="display: none" id="lupafPoderNotarial" />
							</a>
								<p class="help-block">Un solo archivo de máximo 25MB</p> <%
 	idCLiente = vClienteParam;
 	rutaOrigen = Rutas.rutaCarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirRlPoderNotarial
 			+ Rutas.separador;
 	rutaDestino = Rutas.rutaDescarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirRlPoderNotarial
 			+ Rutas.separador;
 	listaArchivos = new File(rutaOrigen).listFiles();
 	if (listaArchivos != null && listaArchivos.length > 0) {
 		Arrays.sort(listaArchivos, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
 		out.println(
 				"<INPUT type='hidden' id='HayArchivofPoderNotarial' name='HayArchivofPoderNotarial' value='si'");
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

 			//String dirRootDes = new File(Rutas.rutaDescarga + Rutas.separador + idCLiente ).getParentFile().getName();
 			String dirRootDes = Rutas.rutaDescarga + "/" + idCLiente;
 			if (!new File(archivoDestino.getParent()).exists()) {
 				try {
 					new File(archivoDestino.getParent()).mkdirs();
 				} catch (Exception es) {
 					es.printStackTrace();
 				}
 			}

 			try {
 				//new Archivos().copyFile(listaArchivos[i].getAbsolutePath(), archivoDestino.getAbsolutePath());
 				//out.println("<p>" + listaArchivos[i].getName() + " <a href=\"/" + dirRootDes + "/" + idCLiente + Rutas.separador + Rutas.dirRlPoderNotarial + Rutas.separador + listaArchivos[i].getName() + "\" target=\"_blank\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>" );
 				out.println("<p>" + listaArchivos[i].getName() + " <a href=\"javascript:window.open('"
 						+ dirRootDes + "/" + Rutas.dirRlPoderNotarial + "/" + listaArchivos[i].getName()
 						+ "', 'clearcache=yes')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>");
 			} catch (Exception es) {
 				es.printStackTrace();
 			}

 		} //for
 	} else {
 		out.println(
 				"<INPUT type='hidden' id='HayArchivofPoderNotarial' name='HayArchivofPoderNotarial' value='no'");
 	}
 %></td>
						</tr>
						<!--                    <tr>
//						<td colspan="3">
//							<h4>Datos del beneficiario, controlador o dueño</h4>
//						</td>
//					</tr>	
//					<tr>
//						<td colspan="3">
//                                                <div class="row">
//                                                <div class="col-12">
//                                                  <div class="md-form form-sm">
//                                                      <input type="checkbox" id="nobeneficiario" name="nobeneficiario" >
//                                                      <label for="nobeneficiario" class="bene"  >
//                                                        Declaro que soy el único beneficiario de los servicios proporcionados por Efectivale
//                                                      </label><br>
//                                                  </div>
//                                                </div>
//                                                </div>
//						</td>
//					</tr>	
//					<tr>
//						<td colspan="3">
//                                               <input type="hidden" id="capturado" name="capturado" value="0"> 
//                                               
//                                                
//                                                <input type="hidden" id="idDeclaroBeneficiario" name="idNoBeneficiario" value="<c:out value="${declarobeneficiario}" />" >
//                                               <input type="checkbox" id="sibeneficiario" name="sibeneficiario" class="" >
//                                              <label for="sibeneficiario" class="bene">
//                                                Si conoce información del beneficiario
//                                              </label><br>
//						</td>						
//					</tr>	
//					<tr>
//
//						<td>							
//							<select class="browser-default" name="tipoBeneficiario" id="tipoBeneficiario">
//								<option value="">* Tipo</option>
//								<option value="F">Física</option>
//								<option value="M">Moral</option>
//								<option value="X">Fideicomiso</option>
//								
//							</select>
//						</td>
//						<td>
//						</td>
//						<td>
//							 <button class="btn" >Agregar beneficiario</button>
//                                                        
//                                                        <a class="boton_personalizado" id ="btnBene" style="visibility:visible" href="javascript:lanzarBeneficiario()">Agregar Dueño Beneficiario o Beneficiario Controlaldor.</a>
//						</td>
//					</tr>  -->
						<tr>
							<td colspan="4" id="Imp25">
								<!-- COMIENZA LA CONSUTLA SI TIENE BENEFICIARIOS --> <%
 	String esEdicion = "";
 	String estatusCliente = "";
 	if (request.getAttribute("estado") != null) {
 		estatusCliente = request.getAttribute("estado").toString();
 	} else {
 		estatusCliente = "";
 	}
 	perfilId = sesion.getAttribute("perfilId").toString();
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
 		if (b != null && b.length > 0) {

 			out.println("<script>");
 			out.println("document.getElementById('capturado').value = '1';");
 			out.println("localStorage.setItem('capturado','1');");
 			out.println("</script>");

 			out.println("<h4>Dueño Beneficiarios</h4>");
 			out.println("<table>");
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
 				//out.println("<td><a href=\"" + servlet + "?idCliente=" + b[i].getCliente_id() + "&nroBene=" + b[i].getNroBeneficiario() + "&esEdicion=1\" target=\"_blank\" style=\"text-decoration: underline; color:blue;\"><img src=\"img/lupa.jpg\" height=\"32\" width=\"32\" align=\"center\"></a>"); 
 				out.println("<td><a href=\"" + servlet + "?idCliente=" + b[i].getCliente_id() + "&nroBene="
 						+ b[i].getNroBeneficiario() + "&estadoCliente=" + estatusCliente
 						+ "&esEdicion=0\" target=\"_blank\" style=\"text-decoration: underline; color:blue;\"><img src=\"img/lupa.jpg\" height=\"32\" width=\"32\" align=\"center\"></a>");
 				out.println("</tr>");
 			}
 			out.println("</table>");
 		} else {
 			out.println("<script>");
 			out.println("localStorage.setItem('capturado','0');");
 			out.println("</script>");
 		} //si hay beneficiario
 	} //si es edicion
 %>
							</td>
							<!--
                                      
                                        </tr>
                                                    
                                       <tr>
                                           
                                         <td>
							<!-- <button class="btn" >Agregar beneficiario</button>-->

						</tr>

						<tr id="Imp26">
							<td colspan="4">
								<!-- COMIENZA LA CONSUTLA SI TIENE BENEFICIARIOS --> <%
 	int comillas = 34;
 	ConsultaWS w = new ConsultaWS();
 	esEdicion = "";
 	estatusCliente = "";
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
 		System.out.println("Excepción durante la carga de la tabla de Representante Legal");
 	}

 	idCliente = "";
 	if (request.getAttribute("cliente_id") != null) {
 		idCliente = request.getAttribute("cliente_id").toString();
 	}

 	if (esEdicion != null && !esEdicion.isEmpty() && idCliente != null && !idCliente.isEmpty()) {
 		Representante[] b = new DatosRepLegal().getRepList(idCliente);
 		out.println("<div id='divv1'>");
 		if (b != null && b.length > 0) {

 			out.println("<script>");
 			//out.println("document.getElementById('capturado').value = '1';");
 			out.println("localStorage.setItem('capturado','1');");
 			out.println("</script>");

 			out.println("<h4>Representante Legal</h4>");
 			out.println("<table  >");
 			out.println("<thead>");
 			out.println("<tr>");
 			out.println("<td>No. Rep</td>");
 			out.println("<td>Nombre/Razón Social</td>");
 			//                                                                out.println("<td>Tipo Persona</td>");
 			out.println("<td>Consultar</td>");
 			out.println("</tr>");
 			out.println("</thead>");
 			for (int i = 0; i < b.length; i++) {
 				String servlet = "";
 				//                                                            if ( b[i].getTipoPersona().equals("F")){
 				//                                                                servlet="EdicionBeneFisica";
 				//                                                            } else if( b[i].getTipoPersona().equals("M")){
 				//                                                                servlet = "EdicionBeneMoral";
 				//                                                            } else if( b[i].getTipoPersona().equals("X")){
 				//                                                                servlet ="EdicionBeneFideicomiso";
 				//                                                            }
 				servlet = "EdicionRepresentante";
 				out.println("<tr style=\"border-bottom:1px solid #000000\">");
 				out.println("<td>" + b[i].getNroRepresentantes() + "</td>");
 				out.println("<td>" + b[i].getNombreCompleto() + "</td>");
 				//                                                            out.println("<td>" + b[i].getTipoPersonaDesc()+ "</td>");
 				//out.println("<td><a href=\"" + servlet + "?idCliente=" + b[i].getCliente_id() + "&nrorep=" + b[i].getNroRepresentantes() + "&esEdicion=0\" target=\"_blank\" style=\"text-decoration: underline; color:blue;\"><img src=\"img/lupa.jpg\" height=\"32\" width=\"32\" align=\"center\"></a>"); 
 				out.println("<td><a href=\"" + servlet + "?idCliente=" + b[i].getCliente_id() + "&nrorep="
 						+ b[i].getNroRepresentantes() + "&estadoCliente=" + estatusCliente
 						+ "&esEdicion=0\" target=\"_blank\" style=\"text-decoration: underline; color:blue;\"><img src=\"img/lupa.jpg\" height=\"32\" width=\"32\" align=\"center\"></a>");
 				out.println("</tr>");
 			}
 			out.println("</table>");

 			out.println("</br>");
 			out.println("<br>");
 		} else {
 			out.println("<script>");
 			out.println("localStorage.setItem('capturado','0');");
 			out.println("</script>");
 			out.println("<br>");
 		} //si hay beneficiario
 	} else { //es primera vez
 		boolean a = new DatosRepLegal().limpiaRepresentantes(vClienteParam);
 		out.println("<script>");
 		out.println("localStorage.setItem('capturado','0');");
 		out.println("</script>");
 		out.println("<br>");
 	}
 	out.println("</div>");

 	int comilla = 34;
 	out.println("<a class='boton_personalizado' id ='btnRep' style='visibility:visible' href=" + (char) comilla
 			+ "javascript:Representante('" + vClienteParam + "')" + (char) comilla
 			+ ">Agrega Representante Legal.</a>");
 	out.println("<a class='' align='left' id ='btnAct2' href=" + (char) comilla
 			+ "javascript:ActualizaTablaRep('" + vClienteParam + "','" + w.consultarWsRepLegal() + "')"
 			+ (char) comilla
 			+ "style='visibility:visible' > <img src='img/actualiza.png' height='32' width='32' align='center'></a>");
 	out.println("<br>");
 	out.println("</br>");
 	out.println("<input type='hidden' id='WsRep' value='" + w.consultarWsRepLegal() + "'>");
 	out.println("</br>");
 %>
							</td>
						</tr>

						<tr id="Imp27">
							<td colspan="3">
								<h4>Origen de los recursos / Información y documentación</h4>
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<div class="row">
									<div class="col-12">
										<div class="md-form form-sm">
											<input type="hidden" id="iddeclaroOrigen"
												name="iddeclaroOrigen"
												value="<c:out value="${declaroorigen}" />"> <input
												type="checkbox" id="declaroOrigen" name="declaroOrigen">
											<label id="Imp28" for="declaroOrigen" class="">
												Declaro para todos los efectos a que haya lugar, que los
												recursos utilizados para obtener los servicios
												proporcionados por Efectivale son propios y de origen
												lícito. </label><br>
										</div>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<div class="row">
									<div class="col-12">
										<div class="md-form form-sm">
											<input type="hidden" id="iddeclaroBeneficiario"
												name="iddeclaroBeneficiario"
												value="<c:out value="${declarobeneficiario}" />"> <input
												type="checkbox" id="declaroBeneficiario"
												name="declaroBeneficiario"> <label id="Imp29"
												for="declaroBeneficiario" class="bene"> A través del
												presente declaro que la información y documentación provista
												al amparo de este formato es correcta, verdadera y vigente a
												la fecha en la que se proporcionó y que en caso de que la
												misma cambie materialmente notificaré tan pronto sea posible
												a Efectivale. </label><br>
										</div>
									</div>
								</div>
							</td>
						</tr>
							<tr>
		<td>
			</td>
</tr>
<tr>
<td colspan=4>
									
	<%  
//  	String Cliente,TipoPersona = "";
//     String cl = request.getAttribute("Cliente_id").toString();
//     out.println("este es el cliente id "+ vClienteParam);
//  	TipoPersona = request.getAttribute("tipopersona").toString();
//  	String cli = "";
//  	out.println("<script> cli = document.getElementById('Cliente_Id').value;  </script>"); 
//  	System.out.print("cliente " + cli);
 	
//  	System.out.print("cliente " + idCliente);
//  	System.out.print("Tipo " + TipoPersona);
 	 
 //	out.println("<div id='div3' width:'200'>");
//  	ArrayList<Coincidencia> a = new ArrayList<Coincidencia>();
//  	     OperacionesCoincidencias v = new OperacionesCoincidencias();
//  	     try{
 	    	
 	    	
// //  	    	a = v.armarCoincidenciasAmostrarEnPantalla(a,"A-333444", "F");
//  	    	a = v.armarCoincidenciasAmostrarEnPantalla(vClienteParam, "F");
 	    	
//  	     }catch(Exception e){
//  	    	 out.print("ERRRRRROOOOOOOOOR  " + e.toString());
//  	     }
 	     
//  		 out.println("<INPUT type='hidden' id='tamanoArreglo' name='tamanoArreglo'  value="+a.size()+">");

 	     
//  		 if (a != null && a.size() > 0) {	
//  			out.println("<h4>Coincidencias En Listas Negras.</h4>");
//  			for (int i = 0; i < a.size(); i++) {
//  				out.println("<tr style=\"border-bottom:1px solid #000000\">"); 
//  				JSONParser parser = new JSONParser();
//  				JSONObject json1 = (JSONObject) parser.parse(a.get(i).getCuerpoJson());
//  			out.println("<table >"); 
 			
//  			out.println("<tr style=\"border-bottom:1px solid #000000\">"); 			
//  			out.println("<tr><td class='TitleCell' >Nombre:</td><td class='InfoCell' style='color:red' width=200>"+json1.get("firstName")+" "+json1.get("lastName")+"</td><td class='TitleCell' width=100>Categoría:</td><td class='InfoCell' width=300>"+json1.get("categoryWc")+"</td><td class='TitleCell'>Tipo Lista :</td><td class='InfoCell'width=150>"+json1.get("cveTipoLista")+"</td></tr>"); 
//  			out.println("<tr><td class='TitleCell' >Subcategoría:</td><td  class='InfoCell'>"+json1.get("subCategory")+"</td><td class='TitleCell'  >Localización:</td><td class='InfoCell' width=200>"+json1.get("locations")+"</td><td class='TitleCell' >Creación:</td><td class='InfoCell'>"+json1.get("entered")+"</td></tr>"); 			
//  			out.println("<tr><td class='TitleCell' >Identificador:</td><td class='InfoCell'>"+json1.get("uId")+"</td><td  class='TitleCell' >Alias:</td><td class='InfoCell' width=200>"+json1.get("aliases").toString().replaceAll(",",", ")+"</td><td class='TitleCell' >Actualizado:</td><td class='InfoCell'>"+json1.get("updated")+"</td></tr>"); 			
//  			out.println("<tr><td class='TitleCell' >Fuentes externas:</td><td class='InfoCell'>"+json1.get("externalSources").toString().replaceAll(" ","  ")+"</td><td  valign=top >Info adicional:</td><td class='InfoCell' width=200>"+json1.get("furtherInfo")+"</td><td class='TitleCell' >Sexo:</td><td class='InfoCell'>"+json1.get("genero")+"</td></tr>"); 			
//  			out.println("<tr><td class='TitleCell' >Lugar nac:</td><td class='InfoCell'>"+json1.get("placeBirth")+"</td><td class='TitleCell'  >Ligado a:</td><td class='InfoCell' width=200>"+json1.get("linkedTo").toString().replaceAll(";","; ")+"</td><td class='TitleCell' >Implicado:</td><td class='InfoCell'><INPUT type='hidden' id='matchid"+i+"' name='matchid"+i+"' value='"+a.get(i).getMatchid()+"' >"+a.get(i).getImplicado()+"</td> </tr>"); 			
//  			out.println("<tr><td class='TitleCell' >Coincidencia:</td><td class='InfoCell' style='color:red'>"+json1.get("score")+"</td><td class='TitleCell' >Palabras Clave:</td><td class='InfoCell' width=200>"+json1.get("keywords").toString().replaceAll("~","~ ")+"</td><td></td><td></td></tr>"); 			
// 			out.println("<tr style='border-bottom:1pt solid black;'><td class='TitleCell' >Descripción:</td><td class='InfoCell' colspan=5><textarea name='Desc"+i+"' id='Desc"+i+"' cols='40'>"+a.get(i).getDescripcion()+"</textarea>");
 			
//  			/*
 			
//  			*/
//  			out.println("</table>" );
 			
//  		 }
//  		 }
 				
 		 
 			
 			
//  			out.println("<table>"); 
//  			out.println("<thead>");
//  			out.println("<tr>");
//  			out.println("<th>Coincidencia </th>");
//  			out.println("<th>Implicado</th>");
//  			out.println("<th>Descripcion </th>");
//  			out.println("</tr>");
//  			out.println("</thead>");
//   			for (int i = 0; i < a.size(); i++) {
//  				out.println("<tr style=\"border-bottom:1px solid #000000\">");

//                out.println("<tr style=\"border-bottom:1px solid #000000\">");
//                out.println("<table>"); 
//  			    out.println(" <td><INPUT type='hidden'  id='Info' name='Info' value='"+a.get(i).getCuerpoJson()+"' >"+a.get(i).getCuerpoJson()+"</td>");
//  			    out.println(" <td><INPUT type='hidden' id='matchid"+i+"' name='matchid"+i+"' value='"+a.get(i).getMatchid()+"' >"+a.get(i).getImplicado()+"</td>");

// 				out.println("<td>");
//  			    out.println(" <textarea name='Desc"+i+"' id='Desc"+i+"' cols='40'>"+a.get(i).getDescripcion()+"</textarea>");
//  			   out.println("</td>");
 			           
 			                       
// 			    out.println("</tr>");
// 			    out.println("</table>"); 
//   			}
//  			out.println("</table>");
//  			out.println("</br>");
//  		}

//  	out.println("<br>");
//  	out.println("</br>");
 %>
	 	
</td>
</tr>
					
							<tr>
							<td><a class="boton_personalizado2" id="btnClose"
								style="visibility: hidden" href="estatus_clientes.jsp">Cerrar</a>
							</td>
							<td></td>
							<td>
								<button class="btn btn-danger" name="btnGuardar" id="btnGuardar">Guardar</button>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="col-md-3">
				<p>
					<%@ include file="despliega_menu.jsp"%>
				</p>
				<!-- <button class="btn" >MENU HISTORIAL</button>-->
				<%
					comilla = 34;

					out.println("<a id='Hist' style='visibility:visible' href=" + (char) comilla + "javascript:Historial('"
							+ vClienteParam + "')" + (char) comilla + ">Historial de cambios.</a>");
					out.println("<br>");
				%>
				<a id="Impr"
					href="javascript:imprimir(Imp1,Imp2,Imp3,Imp33,Imp4,Imp5,Imp55,Imp6,Imp7,Imp77,Imp8,Imp9,Imp10,Imp11,Imp12,Imp13,Imp14,Imp15,Imp16,Imp17,Imp177,Imp18,Imp19,Imp20,Imp21,Imp22,Imp23,Imp233,Imp24,Imp25,Imp26,Imp27,Imp28,Imp29,Imp30)">Imprimir
					pantalla.</a>
			</div>
		</div>
	</div>
	<!--IMAGENES QUE SALEN EN LOS CHECKS DEL IMPRESOR-->
	<div id='check'>
		<br> <img src='img/checked.png' height='32' width='32'>

	</div>
	<div id='no_check'>
		<br> <img src='img/no_checked.png' height='32' width='32'>

	</div>
	<!--    -->
	<footer>
		<div class="row footer-menu-bottom" id="Imp30">
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

	<script src="js/jquery.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/mdb.min.js.descarga"></script>
	<script src="js/validaciones.js"></script>
	<script src="js/jquery-1.12.4.js"></script>
	<script src="js/jquery-ui.js"></script>
	<script>
		function setFechaValidacion() {
			if (document.getElementById('Estatus').value == 'A') {
				document.getElementById('fechaValidacion').valueAsDate = new Date();

			} else {
				document.getElementById('fechaValidacion').value = '';
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
					progressLabel.text(progressbar.progressbar("value") + "%");
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
		function validarFomularioFideicomisoS() {
			if (validarFormularioFideicomiso()) {
				$('.spinner-wrapper').fadeIn('fast');
				Prog();
				return true;
			} else {
				return false;
			}
		}

		function validarFormularioFideicomiso() {

			/**
			 *  VALIDACIONES DE LA VERIFICACION
			 */

			//el bloqueo
			//txtSiBeneficiario = document.getElementById('sibeneficiario').checked;
			txtIsBloqueo = document.getElementById('chkFechaBloqueo').checked;
			if (txtIsBloqueo) {
				txtFechaBloqueo = document.getElementById('fechaBloqueo').value;
				if (txtFechaBloqueo.length <= 0 || txtFechaBloqueo == '') {
					document.getElementById('fechaBloqueo').focus();
					alert('Debe indicar una fecha de bloqueo');
					document.getElementById('fechaBloqueo').focus();
					return false;
				}
			}

			//Si es Estatus es Invalidado, el texto del mensaje debe de estar capturado.
			txtEstatus = document.getElementById('Estatus').value;
			if (txtEstatus == "I") {
				txtMensaje = document.getElementById('mensaje').value;
				if (txtMensaje.length <= 0 || txtMensaje == '') {
					document.getElementById('mensaje').focus();
					alert('El texto del mensaje debe estar capturado');
					document.getElementById('mensaje').focus();
					return false;
				}
			}

			//Que el correo tenga formato correcto
			object = document.getElementById('correo');
			valueForm = object.value;

			//Patron para el correo
			var patron = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,4})+$/;
			if (valueForm.search(patron) === 0) {

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
			if (txtTelPais === null || txtTelPais.length === 0
					|| /^\s+$/.test(txtTelPais)) {
				alert('Debe seleccionar la clave de teléfono de país');
				document.getElementById('telPais').focus();
				return false;
			}

			txtTelefono = document.getElementById('telefono').value;
			if (txtTelefono == null || txtTelefono.length == 0
					|| /^\s+$/.test(txtTelefono)) {
				alert('El número telefónico no debe dejarse en blanco')
				document.getElementById('telefono').focus();

				return false;
			}
			txtTelefono = document.getElementById('telefono').value;
			if (txtTelefono.length != 0) {
				if (txtTelefono.length != 10) {
					alert('El número telefónico debe ser de 10 dígitos');
					document.getElementById('telefono').focus();
					return false;
				}
			}

			txtrazonSocial = document.getElementById('razonSocial').value;
			if (txtrazonSocial == null || txtrazonSocial.length == 0
					|| /^\s+$/.test(txtrazonSocial)) {
				alert('La razón social no debe dejarse en blanco')
				document.getElementById('razonSocial').focus();
				return false;
			}

			txtNombreFideicomiso = document.getElementById('NombreFideicomiso').value;
			if (txtNombreFideicomiso == null
					|| txtNombreFideicomiso.length == 0
					|| /^\s+$/.test(txtNombreFideicomiso)) {
				alert('El Nombre de Fideicomiso no debe dejarse en blanco')
				document.getElementById('NombreFideicomiso').focus();
				return false;
			}

			txtnoFideicomiso = document.getElementById('noFideicomiso').value;
			if (txtnoFideicomiso == null || txtnoFideicomiso.length == 0
					|| /^\s+$/.test(txtnoFideicomiso)) {
				alert('Debe indicar un número de fideicomiso')
				document.getElementById('noFideicomiso').focus();
				return false;
			}
	
			txtNoEscritura=document.getElementById('noescritura').value;
			if (txtNoEscritura == null || txtNoEscritura.length == 0
					|| /^\s+$/.test(txtNoEscritura)) {
				alert('El número de escritura no puede dejarse en blanco');
				document.getElementById('noescritura').focus();
				return false;
			}
			

			txtNotaria=document.getElementById('notaria').value;
			if (txtNotaria == null || txtNotaria.length == 0
					|| /^\s+$/.test(txtNotaria)) {
				alert('La notaria no puede dejarse en blanco');
				document.getElementById('notaria').focus();
				return false;
			}
			
			txtFechaNotaria=document.getElementById('fechaNotarial').value;
			if (txtFechaNotaria == null || txtFechaNotaria.length == 0
					|| /^\s+$/.test(txtFechaNotaria)) {
				alert('La fecha del instrmento notarial no puede dejarse en blanco');
				document.getElementById('fechaNotarial').focus();
				return false;
			}
			
			
			//ARCHIVO CÉDULA FISCAL
			if (txtEstatus == "A") {
				txtcedulaFiscal = document.getElementById('cedulaFiscal').value;
				txtHayArchivoCedula = document
						.getElementById('HayArchivoCedula').value;
				if (txtcedulaFiscal.length == 0) {
					if (txtHayArchivoCedula === 'no') {
						alert('Para poder validar debe subir un archivo de Cédula Fiscal');
						document.getElementById('cedulaFiscal').focus()
						return false;
					}
				}
			}

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

			txtPais = document.getElementById('pais').value;
			if (txtPais === null || txtPais.length === 0
					|| /^\s+$/.test(txtPais)) {
				alert('Debe seleccionar el país del domicilio');
				document.getElementById('pais').focus();
				return false;
			}

			txtFechaNacimiento = document.getElementById('fFechaNacimiento').value;
			if (txtFechaNacimiento === null || txtFechaNacimiento.length === 0
					|| /^\s+$/.test(txtFechaNacimiento)) {
				alert('No ha indicado una fecha de nacimiento para el Representante Legal');
				document.getElementById('fFechaNacimiento').focus();
				return false;
			}

			txtFechaNacimiento = getFechaNac(txtFechaNacimiento); //normalizando 
			b = document.getElementById('fRFC').value;
			txtFechaNacimientoRFC = '';
			if (b.length > 0 && b !== '') {
				txtFechaNacimientoRFC = getFechaNacRFC(document
						.getElementById('fRFC').value);
			}

			if (txtFechaNacimiento !== null && txtFechaNacimiento.length > 0
					&& txtFechaNacimientoRFC !== null
					&& txtFechaNacimientoRFC.length > 0) {
				if (txtFechaNacimiento !== txtFechaNacimientoRFC) {
					alert('La fecha de nacimiento del Representante Legal no coincide con la fecha de nacimiento del RFC');
					document.getElementById('fRFC').focus();
					return false;
				}
			}

			//ARCHIVO COMPROBANTE DE DOMICILIO 
			if (txtEstatus == "A") {
				txtarchivoDomicilio = document
						.getElementById('archivoDomicilio').value;
				txtHayArchivoDomicilio = document
						.getElementById('HayArchivoDomicilio').value;
				if (txtarchivoDomicilio.length == 0) {
					if (txtHayArchivoDomicilio === 'no') {
						alert('Para poder validar debe subir un archivo de Commprobante de Domicilio');
						document.getElementById('archivoDomicilio').focus()
						return false;
					}
				}
			}

			txtarchivoDomicilio = document.getElementById('archivoDomicilio').value;
			if (txtarchivoDomicilio.length > 0) {
				if (!comprueba_extension(txtarchivoDomicilio)) {
					alert('El archivo de compropante de domicilio debe ser con la extensión .pdf');
					document.getElementById('archivoDomicilio').focus();
					return false;
				}

				input = document.getElementById('archivoDomicilio');
				if (!validarTamanoArchivo(input, 10)) {
					alert('El peso del archivo de comprobante de domicilio excede los 10 MB que tiene como límite nuestro sistema');
					document.getElementById('archivoDomicilio').focus();
					input = null;
					return false;

				}
			}

			txtNombres = document.getElementById('fnombre').value;
			if (txtNombres == null || txtNombres.length == 0
					|| /^\s+$/.test(txtNombres)) {
				alert('El nombre no debe dejarse en blanco')
				document.getElementById('fnombre').focus();
				return false;
			}

			txtPaterno = document.getElementById('fpaterno').value;
			if (txtPaterno == null || txtPaterno.length == 0
					|| /^\s+$/.test(txtPaterno)) {
				alert('El apellido paterno no debe dejarse en blanco')
				document.getElementById('fpaterno').focus();
				return false;
			}

			txtMaterno = document.getElementById('fmaterno').value;
			if (txtMaterno == null || txtMaterno.length == 0
					|| /^\s+$/.test(txtMaterno)) {
				alert('El apellido materno no debe dejarse en blanco')
				document.getElementById('fmaterno').focus();
				return false;
			}

			txtRFCRepresentante = document.getElementById('fRFC').value;
			if (txtRFCRepresentante.length !== 0) {
				if (txtRFCRepresentante.length !== 13) {
					alert('El RFC del representante Legal debe ser de 13 caracteres');
					document.getElementById('fRFC').focus();
					return false;
				}
			}

			txtfCURP = document.getElementById('fCURP').value;
			if (txtfCURP.length !== 0) {
				if (txtfCURP.length !== 18) {
					alert('El CURP del Representante Legal debe ser de 18 caracteres');
					document.getElementById('fCURP').focus();
					return false;
				}
			}

			txtftipoIdentificacion = document
					.getElementById('ftipoIdentificacion').value;
			if (txtftipoIdentificacion == null
					|| txtftipoIdentificacion.length == 0) {
				if (ftipoIdentificacion == null
						|| ftipoIdentificacion.length == 0
						|| /^\s+$/.test(ftipoIdentificacion)) {
					alert('Debe selccionar un tipo de identificacion para el Representante Legal');
					document.getElementById('ftipoIdentificacion').focus();
					return false;
				}
				alert('Debe selccionar un tipo de identificacion para el Representante Legal');
				document.getElementById('ftipoIdentificacion').focus();
				return false;
			}

			txtNumeroId = document.getElementById('fNumeroId').value;
			if (txtNumeroId == null || txtNumeroId.length == 0
					|| /^\s+$/.test(txtNumeroId)) {
				alert('El número de identificación del Representante Legal no puede dejarse en blanco');
				document.getElementById('fNumeroId').focus();
				return false;
			}
			txtautoridadEmite = document.getElementById('fautoridadEmite').value;
			if (txtautoridadEmite == null || txtautoridadEmite.length == 0
					|| /^\s+$/.test(txtautoridadEmite)) {
				alert('La autoridad que emite no puede dejarse en blanco');
				document.getElementById('fautoridadEmite').focus();
				return false;
			}
			
			
			txtNoPoderrl= document.getElementById('nopoder').value;
			if (txtNoPoderrl == null || txtNoPoderrl.length == 0
					|| /^\s+$/.test(txtNoPoderrl)) {
				alert('El No. de poder no puede dejarse en blanco');
				document.getElementById('nopoder').focus();
				return false; 
			}
			
			txtNotariarl= document.getElementById('rlnotaria').value;
			if (txtNotariarl == null || txtNotariarl.length == 0
					|| /^\s+$/.test(txtNotariarl)) {
				alert('La notaria del representante no puede dejarse en blanco');
				document.getElementById('rlnotaria').focus();
				return false;
			}
			
			txtFechaNotaria=document.getElementById('rlfechaNotarial').value;
			if (txtFechaNotaria == null || txtFechaNotaria.length == 0
					|| /^\s+$/.test(txtFechaNotaria)) {
				alert('La fecha del poder del representante no puede dejarse en blanco');
				document.getElementById('rlfechaNotarial').focus();
				return false;
			}
			//ARCHIVO IDENTIFICACON
			if (txtEstatus == "A") {
				txtarchivofId = document.getElementById('archivofId').value;
				txtHayArchivofID = document.getElementById('HayArchivofID').value;
				if (txtarchivofId.length == 0) {
					if (txtHayArchivofID === 'no') {
						alert('Para poder validar debe subir un archivo de ID del Representante Legal');
						document.getElementById('archivofId').focus()
						return false;
					}
				}
			} 
                        

			//ARCHIVO PODER NOTARIAL
			if (txtEstatus == "A") {
				txtarchivofPoderNotarial = document
						.getElementById('archivofPoderNotarial').value;
				txtHayArchivofPoderNotarial = document
						.getElementById('HayArchivofPoderNotarial').value;
				if (txtarchivofPoderNotarial.length == 0) {
					if (txtHayArchivofPoderNotarial === 'no') {
						alert('Para poder validar debe subir un archivo de Poder Notarial del Representante Legal');
						document.getElementById('archivofPoderNotarial')
								.focus()
						return false;
					}
				}
			}
                        
			//Validando que las dos declaratorias tenga selección.

			chkdeclaroOrigen = document.getElementById('declaroOrigen').checked;
			if (!chkdeclaroOrigen) {
				alert('Debe seleccionar el origen de los recursos');
				return false;
			}
			chkdeclaroBeneficiario = document
					.getElementById('declaroBeneficiario').checked;
			if (!chkdeclaroBeneficiario) {
				alert('Debe seleccionar la veracidad de la información');
				return false;
			}

			if (document.getElementById('actaConstitutiva').value.length == 0)
				document.getElementById('archivoActaZip').value = '';
			if (document.getElementById('cedulaFiscal').value.length == 0)
				document.getElementById('archivoCedulaZip').value = '';
			if (document.getElementById('archivoDomicilio').value.length == 0)
				document.getElementById('archivoDomicilioZip').value = '';
			if (document.getElementById('archivofId').value.length == 0)
				document.getElementById('archivoRlIdentificacionZip').value = '';
			if (document.getElementById('archivofPoderNotarial').value.length == 0)
				document.getElementById('archivoPoderNotarialZip').value = '';

			 document.getElementById('btnGuardar').disabled=true;
             alert("Validando RFC y CURP");
			if (validarCapturaBeneficiario() && validarRFC() && validarRFC2() && validarCURP()) {
				
				document.getElementById('btnGuardar').disabled=false;
				return true;
			} else {
				document.getElementById('btnGuardar').disabled=false;
				return false;
			}
		}
                
    </script>
    		<script>
	function validarRFC(){
		var txtbRFC = document.getElementById('rfc').value;

		var obj="";
		var bandera=null;
		
		if(txtbRFC != null && !txtbRFC == ""){
		
		$.ajax({
			async:false,
			crossDomain : true,
			type : "GET",
// 			url : urlService,
			url : "http://localhost:8080/PLD2/ServeletApiRFC",
			contentType : "application/json",
			dataType : "text",
			data : {
				rfc : txtbRFC
			},
			success : function(data) {
			  obj = JSON.parse(data);
				
				if(obj.respuesta=="RFC Valido"){
			    	bandera = true;
			    }else{
			    	alert("El RFC ingresado no se encuentra en la lista de RFC no cancelados");
			    	bandera = false;
			    	document.getElementById('rfc').focus();
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
	
	function validarRFC2(){
		var txtbRFC = document.getElementById('fRFC').value;

		var obj="";
		var bandera=null;
		
		if(txtbRFC != null && !txtbRFC==""){
		
		$.ajax({
			async:false,
			crossDomain : true,
			type : "GET",
// 			url : urlService,
			url : "http://localhost:8080/PLD2/ServeletApiRFC",
			contentType : "application/json",
			dataType : "text",
			data : {
				rfc : txtbRFC
			},
			success : function(data) {
			  obj = JSON.parse(data);
				
				if(obj.respuesta=="RFC Valido"){
			    	bandera = true;
			    }else{
			    	alert("El RFC ingresado no se encuentra en la lista de RFC no cancelados");
			    	bandera = false;
			    	document.getElementById('fRFC').focus();
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
		
		var txtCurp = document.getElementById('fCURP').value;

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
					alert("CURP no valido en Listas");
			    	bandera = false;
			    	document.getElementById('fCURP').focus();
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
    function validarDeslindamiento() {
			txtEstatus = document.getElementById('Estatus').value;
			txtRiesgo = document.getElementById('idRiesgo').value;
			txtDes = document.getElementById('Descripcion').value;
			
			if(txtRiesgo == "1" && txtEstatus == "A"){
				if (txtDes == null || txtDes.length == 0 || /^\s+$/.test(txtDes)) {
					    alert('Debe capturar el motivo en Descripción');
					    document.getElementById('Descripcion').focus();
					return false;
				}else{
					document.getElementById('idRiesgo').value ='0';
					return true;
				}
				
			}else{
				return true;
			} 			
		}
     </script>  
    <script>

		//EL SIGUIENTE SCRIPT SE ENCARGA DE EVITAR QUE LOS CHECK BOX 
		//DE LAS DECLARATORIAS DE BENEFICIARIOS SE MUESTREN AMBOS AL MISMO TIEMPO

		function ActualizaTablaRep(vIdCliente, urlService) {
			var str = "";
			str = "<h4 id='titulo'>Representante Legal</h4>";
			str += "<table>";
			str += "<thead>";
			str += "<tr>";
			str += "<td>No. Rep</td>";
			str += "<td>Nombre/Razón Social</td>";
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
							var servlet = "EdicionRepresentante";
							for (var i = 0; i < obj.Representantes.length; i++) {
								str += "<tr style=\"border-bottom:1px solid #000000\">";
								str += "<td>" + obj.Representantes[i].nrorep
										+ "</td>";
								str += "<td>"
										+ obj.Representantes[i].nombre_completo
										+ "</td>";
								str += "<td><a href=\""
										+ servlet
										+ "?idCliente="
										+ obj.Representantes[i].client_id
										+ "&nrorep="
										+ obj.Representantes[i].nrorep
										+ "&estadoCliente="
										+ obj.Representantes[i].estadoCliente
										+ "&esEdicion=0\" target=\"_blank\" style=\"text-decoration: underline; color:blue;\"><img src=\"img/lupa.jpg\" height=\"32\" width=\"32\" align=\"center\"></a></td>";
								str += "</tr>";
							}
							str += "</table>";
							str += "<br/>";
							$("#divv1").html(str);
						},
						error : function(xhr, var1, var2) {
							console.log("errorcito");

						}
					});

		}
		function llamadaRepresentanteActualiz() {

			txtcliente = document.getElementById('Cliente_Id').value;
			txtrutaRep = document.getElementById('WsRep').value;

			ActualizaTablaRep(txtcliente, txtrutaRep);

		}

		function validarCapturaBeneficiario() {
			txtdeclaroBeneficiario = document
					.getElementById('declaroBeneficiario').checked;
			txtdeclaroOrigen = document.getElementById('declaroOrigen').checked;
			txtCapturado = localStorage.getItem('capturado');
			//        
			//        if ( txtdeclaroBeneficiario){
			//            if ( txtCapturado === '0' ){
			//                alert('Debe capturar por lo menos un beneficiario');
			//               return false;
			//            } else {
			return true;
			//            }
			//        }
		}

	
		function cambiarTipoPersona() {

			tp = document.getElementById('tipoP').value;
			tipoPersona = document.getElementById('tipoPersona').value;

			if (tp != tipoPersona) {

				if (confirm("Se cambiará el tipo de persona\n ¿desea continuar?")) {
					document.getElementById('tipoPersonaCambio').value = tp;
					alert("Debe presionar el botón guardar, para terminar de realizar el cambio");
				} else {
					alert("No se ha cambiado el tipo de persona");
				}

			}
		}
	</script>



	<script>
		$(document)
				.ready(
						function() {
							$("#cover").hide();
							//        $("#covers").hide();
							PERFIL_ADMINISTRADOR = 1;
							PERFIL_PLD = 2;
							document.getElementById("chkFechaBloqueo").disabled = true;
							document.getElementById("fechaBloqueo").disabled = true;
							document.getElementById("check").style.visibility = 'hidden';
							document.getElementById("no_check").style.visibility = 'hidden';
							//    document.getElementById("fechaValidacion").disabled = true;
							//    document.getElementById("fechaModificacion").disabled = true;

							esEdicion = document.getElementById('esEdicion').value;
							if (esEdicion == 1) { //la carga de la página proviene de una edición del usuario y no de un nuevo usuario

								/*
								 *  REGLAS DE NEGOCIO
								 */
								//Este formulario genera un candado ya que una vez verificado ya ni el 
								//cliente ni los perfiles de PLD podrán realizar modificaciones, 
								//únicamente el administrador del sistema.
								txtEstatus = '${estado}';

								if (txtEstatus == 'A') {
									//document.getElementById("btnGuardar").disabled = true;
									document.getElementById("btnGuardar").style.visibility = 'hidden';
									//                                document.getElementById('btnBene').style.visibility = 'hidden';
									document.getElementById('btnClose').style.visibility = 'visible';
									//                                document.getElementById("Estatus").disabled = true;
									//                                document.getElementById("mensaje").disabled = true;
									//                                document.getElementById("tipoP").disabled = true;
									//                                document.getElementById("telPais").disabled = true;
									//                                document.getElementById("actaConstitutiva").disabled = true;
									//                                document.getElementById("cedulaFiscal").disabled = true;
									//                                document.getElementById("pais").disabled = true;
									//                                document.getElementById("archivoDomicilio").disabled = true;
									//                                document.getElementById("fFechaNacimiento").disabled = true;
									//                                document.getElementById("ftipoIdentificacion").disabled = true;
									//                                document.getElementById("archivofId").disabled = true;
									//                                document.getElementById("archivofCedula").disabled = true;
									//                                document.getElementById("archivofPoderNotarial").disabled = true;
									//                                document.getElementById("nobeneficiario").disabled = true;
									//                                document.getElementById("sibeneficiario").disabled = true;
									//                                document.getElementById("tipoBeneficiario").disabled = true;
									//                                document.getElementById("declaroOrigen").disabled = true;
									var form = document
											.getElementById("frmFideicomiso");
									var elements = form.elements;
									for (var i = 0, len = elements.length; i < len; ++i) {
										elements[i].readOnly = true;
										elements[i].disabled = true;
									}
								}

								txtPerfilUsuarioSistema = "";
	<%String perfil = (String) sesion.getAttribute("perfilId");
			out.println("txtPerfilUsuarioSistema ='" + perfil + "';");%>
			//SOLO ADMINISTRADOR PUEDE VER EL INDICADOR SI ESTÁ VALIDADO
			if (txtPerfilUsuarioSistema == PERFIL_ADMINISTRADOR && txtEstatus == 'A' ) {
				document.getElementById("indicadorAlfa").style.visibility = 'visible';
				}else{
					document.getElementById("indicadorAlfa").style.visibility = 'hidden';
				}
			
		//UNICAMENTE EL ADMINISTRADOR DEL SISTEMA PUEDE REALIZAR MODIFICACIONES
								if (txtPerfilUsuarioSistema == PERFIL_ADMINISTRADOR||txtPerfilUsuarioSistema == PERFIL_PLD) {
									//document.getElementById("btnGuardar").disabled = false;
									document.getElementById("chkFechaBloqueo").disabled = false;
									document.getElementById("btnGuardar").style.visibility = 'visible';
									document.getElementById('btnClose').style.visibility = 'hidden';

									document.getElementById('notas').value = '${notas.trim()}';
									document.getElementById('Hist').style.visibility = 'visible';
									document.getElementById('Impr').style.visibility = 'visible';
									var form = document
											.getElementById("frmFideicomiso");
									var elements = form.elements;
									for (var i = 0, len = elements.length; i < len; ++i) {
										elements[i].readOnly = false;
										elements[i].disabled = false;
									}
								} 
// 								else if (txtPerfilUsuarioSistema == PERFIL_PLD) {
// 									document.getElementById('notas').value = '${notas.trim()}';
// 									document.getElementById('Hist').style.visibility = 'visible';
// 									document.getElementById('Impr').style.visibility = 'visible';

// 								}
								else {
									document.getElementById('Hist').style.visibility = 'hidden';
									document.getElementById('Impr').style.visibility = 'hidden';
								}

								//Aplicando los persmisos si aún no ha sido verificado
								if (txtPerfilUsuarioSistema !== PERFIL_ADMINISTRADOR
										&& txtEstatus !== 'A') {
									txtPermiso = '${permiso}';
									if (txtPermiso == 'L') {
										//                                    document.getElementById("btnGuardar").disabled = true;
										document.getElementById("btnGuardar").style.visibility = 'hidden';
										//                                    document.getElementById('btnBene').style.visibility = 'hidden';
										document.getElementById("btnRep").style.visibility = 'hidden';
										document.getElementById("btnAct2").style.visibility = 'hidden';
										document.getElementById('btnClose').style.visibility = 'visible';
										//                                    document.getElementById("Estatus").disabled = true;
										//                                    document.getElementById("mensaje").disabled = true;
										//                                    document.getElementById("tipoP").disabled = true;
										//                                    document.getElementById("telPais").disabled = true;
										//                                    document.getElementById("actaConstitutiva").disabled = true;
										//                                    document.getElementById("cedulaFiscal").disabled = true;
										//                                    document.getElementById("pais").disabled = true;
										//                                    document.getElementById("archivoDomicilio").disabled = true;
										////                                    document.getElementById("fFechaNacimiento").disabled = true;
										//                                    document.getElementById("ftipoIdentificacion").disabled = true;
										//                                    document.getElementById("archivofId").disabled = true;
										//                                    document.getElementById("archivofCedula").disabled = true;
										//                                    document.getElementById("archivofPoderNotarial").disabled = true;
										//                                    document.getElementById("nobeneficiario").disabled = true;
										//                                    document.getElementById("sibeneficiario").disabled = true;
										//                                    document.getElementById("tipoBeneficiario").disabled = true;
										//                                    document.getElementById("declaroOrigen").disabled = true;
										var form = document
												.getElementById("frmFideicomiso");
										var elements = form.elements;
										for (var i = 0, len = elements.length; i < len; ++i) {
											elements[i].readOnly = true;
											elements[i].disabled = true;
										}
									}
								}

								//Campos de verificación

								document.getElementById('mensaje').value = '${mensaje.trim()}';
								//El estatus del expediente
								txtEstatus = '${estado}';
								var Estats = "";
								if (txtEstatus != null || txtEstatus.length > 0) {
									var selector = document
											.getElementById('Estatus');
									for (var i = 0; i < selector.length; i++) {
										if (txtEstatus == selector.options[i].value) {
											selector.selectedIndex = i;
											//                                  
											break;
										} else {//if
											selector.selectedIndex = 0;
										}
									}//for 
								}

								//El tipo de  persona 
								var tipPer = "";
								txtTipoPersona = '${tipopersona}';
								if (txtTipoPersona != null
										|| txtTipoPersona.length > 0) {
									var selector = document
											.getElementById('tipoP');
									for (var i = 0; i < selector.length; i++) {
										if (txtTipoPersona == selector.options[i].value) {
											selector.selectedIndex = i;
											//                                 
											break;
										} else {//if
											selector.selectedIndex = 0;
										}
									}//for 
								}

								//Check bloqueado
								isBloqueado = '${bloqueado}';
								if (isBloqueado == 'true') {
									document.getElementById('chkFechaBloqueo').checked = true;
								} else {
									document.getElementById('chkFechaBloqueo').checked = false;
								}

								/**
								 *  LAS SIGUIENTES FUNCIONES ASIGNAN LOS DATOS QUE SE RECUPERAN DE LA CAPTURA
								 */

								//ASIGNANDO EL TELÉFONO DEL PAÍS
								txtTelPais = document
										.getElementById('idtelPais').value; //llega de la respuesta
								if (txtTelPais != null || txtTelPais.length > 0) {
									var selector = document
											.getElementById('telPais');
									for (var i = 0; i < selector.length; i++) {
										if (txtTelPais == selector.options[i].value) {
											selector.selectedIndex = i;
											break;
										} else { //if
											selector.selectedIndex = 0;
										}
									}//for 
								}

								// ASIGNANDO EL PAIS DEL DOMICILIO
								txtPais = document
										.getElementById('idPaisDomicilio').value; //llega de la respuesta
								if (txtPais != null || txtPais.length > 0) {
									var selector = document
											.getElementById('pais');
									for (var i = 0; i < selector.length; i++) {
										if (txtPais == selector.options[i].value) {
											selector.selectedIndex = i;
											break;
										} else { //if
											selector.selectedIndex = 0;
										}
									}//for 
								}

								//ASIGNANDO TIPO DE IDENTIFICACIÓN
								//alert(document.getElementById('idTipoIdentificacion').value);
								txtTipoIdentificacion = document
										.getElementById('idTipoIdentificacion').value; //llega de la respuesta
								if (txtTipoIdentificacion != null
										|| txtTipoIdentificacion.length > 0) {
									var selector = document
											.getElementById('ftipoIdentificacion');
									for (var i = 0; i < selector.length; i++) {
										if (txtTipoIdentificacion == selector.options[i].value) {
											selector.selectedIndex = i;
											break;
										} else {//if
											selector.selectedIndex = 0;
										}
									}//for 
								}
                                                                //Mostrando los check de la deslindacion  
// 			txtRiesgo = document.getElementById('idRiesgo').value;
// 			document.getElementById('riesgo').checked = false;
// 			if (txtRiesgo == "1") {
//                             document.getElementById('riesgo').checked = true;
// 			}

								//Mostrando los check de las declaraciones       

								txtdeclaroBeneficiario = document
										.getElementById('iddeclaroBeneficiario').value;
								txtdeclaroOrigen = document
										.getElementById('iddeclaroOrigen').value;
								if (txtdeclaroBeneficiario === '1') {
									document
											.getElementById('declaroBeneficiario').checked = true;
								}
								if (txtdeclaroOrigen == '1') {
									document.getElementById('declaroOrigen').checked = true;
								}

							}// es edicion
						}); //document ready

		function Representante(vIdCliente) {
			window.open('alta_representante.jsp?esEdicion=1&idCliente='
					+ vIdCliente, '_blank');
		}

		function Historial(vIdCliente) {
			window.open('historial_cambios.jsp?idCliente=' + vIdCliente,
					'_blank');
		}

		function imprimir(Imp1, Imp2, Imp3, Imp33, Imp4, Imp5, Imp55, Imp6,
				Imp7, Imp8, Imp9, Imp10, Imp11, Imp12, Imp13, Imp14, Imp15,
				Imp16, Imp17, Imp18, Imp19, Imp20, Imp21, Imp22, Imp23, Imp24,
				Imp25, Imp26, Imp27, Imp28, Imp29, Imp30) {
			//alert(""+tipPer);
			//alert(""+Estats);
			txtdeclaroBeneficiario = document
					.getElementById('iddeclaroBeneficiario').value;
			txtdeclaroOrigen = document.getElementById('iddeclaroOrigen').value;

			var printContents = document.getElementById('Imp1').innerHTML
					+ document.getElementById('Imp2').innerHTML
					+ document.getElementById('Imp3').innerHTML
					+ document.getElementById('Estatus').options[document
							.getElementById('Estatus').selectedIndex].text;
			if (document.getElementById('chkFechaBloqueo').checked == 1)
				printContents += document.getElementById('check').innerHTML;
			else if (document.getElementById('chkFechaBloqueo').checked == 0)
				printContents += document.getElementById('no_check').innerHTML;
			printContents += document.getElementById('Imp33').innerHTML
					+ document.getElementById('Imp4').innerHTML
					+ document.getElementById('Imp5').innerHTML
					+ document.getElementById('Imp55').innerHTML
					+ document.getElementById('tipoP').options[document
							.getElementById('tipoP').selectedIndex].text
					+ document.getElementById('Imp6').innerHTML
					+ document.getElementById('Imp7').innerHTML
					+ document.getElementById('telPais').options[document
							.getElementById('telPais').selectedIndex].text
					+ document.getElementById('Imp77').innerHTML
					+ document.getElementById('Imp8').innerHTML
					+ document.getElementById('Imp9').innerHTML
					+ document.getElementById('Imp10').innerHTML
					+ document.getElementById('Imp11').innerHTML
					+ document.getElementById('Imp12').innerHTML
					+ document.getElementById('Imp13').innerHTML
					+ document.getElementById('Imp14').innerHTML
					+ document.getElementById('Imp15').innerHTML
					+ document.getElementById('Imp16').innerHTML
					+ document.getElementById('Imp17').innerHTML
					+ document.getElementById('Imp177').innerHTML
					+ document.getElementById('pais').options[document
							.getElementById('pais').selectedIndex].text
					+ document.getElementById('Imp18').innerHTML
					+ document.getElementById('Imp19').innerHTML
					+ document.getElementById('Imp20').innerHTML
					+ document.getElementById('Imp21').innerHTML
					+ document.getElementById('Imp22').innerHTML
					+ document.getElementById('Imp23').innerHTML
					+ document.getElementById('ftipoIdentificacion').options[document
							.getElementById('ftipoIdentificacion').selectedIndex].text
					+ document.getElementById('Imp233').innerHTML
					+ document.getElementById('Imp24').innerHTML
					+ document.getElementById('Imp25').innerHTML
					+ document.getElementById('Imp26').innerHTML
					+ document.getElementById('Imp27').innerHTML;
			if (document.getElementById('declaroOrigen').checked == 1)
				printContents += document.getElementById('check').innerHTML;
			else if (document.getElementById('declaroOrigen').checked == 0)
				printContents += document.getElementById('no_check').innerHTML;
			printContents += document.getElementById('Imp28').innerHTML;
			if (document.getElementById('declaroBeneficiario').checked == 1)
				printContents += document.getElementById('check').innerHTML;
			else if (document.getElementById('declaroBeneficiario').checked == 0)
				printContents += document.getElementById('no_check').innerHTML;
			printContents += document.getElementById('Imp29').innerHTML
					+ document.getElementById('Imp30').innerHTML;
			//       w= $(document).ready(function()
			w = window.open();
			w.document.write(printContents);
			w.document.close(); // necessary for IE >= 10
			w.focus(); // necessary for IE >= 10
			w.print();
			w.close();
			//        return true;
		}
	</script>

</body>
</html>