<%@page import="entidad.Perfil"%>
<%@page import="utilidades.Archivos"%>
<%@page import="utilidades.Rutas"%>
<%@page import="java.io.File"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="utilidades.Mensajes"%>
<%@page import="datosRatos.DatosBeneficiario"%>
<%@include file="valida_login.jsp"%>
<%@page import="entidad.Actividad"%>
<%@page import="datosRatos.DatosActividad"%>
<%@page import="entidad.TipoIdentificacion"%>
<%@page import="entidad.Pais"%>
<%@page import="datosRatos.DatosTipoIdentifiacion"%>
<%@page import="datosRatos.DatosPais"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="entidad.UsuarioTransitivo"%>
<%@page import="rfcCurpAppi.token"%>
<%@page
	import="org.apache.commons.io.comparator.LastModifiedFileComparator"%>
<!DOCTYPE html>
<%
	HttpSession sesion = request.getSession();
	//VALIDANDO LA CANTIDAD DE BENEFICIARIOS PARA ESTE CLIENTE
	String perfilId = sesion.getAttribute("perfilId").toString();
	int numBenefiiarios = 1;
	String elNoBene = "";
	String idCliente = (String) request.getParameter("idCliente");
	String esEdicion = (String) request.getParameter("esEdicion");
	String estadoCliente = (String) request.getParameter("estadoCliente");
	request.setAttribute("estadoCliente", estadoCliente);
	numBenefiiarios = new DatosBeneficiario().getNumeroBeneficiario(idCliente);

	if (esEdicion != null && esEdicion.equals("1")) {
		if (numBenefiiarios >= 5) {
			Mensajes.setMensaje("No es posible agregar m�s de cinco beneficiarios.");
			request.setAttribute("resultado", Mensajes.mensaje);
			request.setAttribute("exito", "1");
			request.getRequestDispatcher("/mensajes.jsp").forward(request, response);
		}
	} else {
		if (numBenefiiarios >= 5 && esEdicion.compareTo("0") != 0) {
			Mensajes.setMensaje("No es posible agregar m�s de cinco beneficiarios.");
			request.setAttribute("resultado", Mensajes.mensaje);
			request.setAttribute("exito", "1");
			request.getRequestDispatcher("/mensajes.jsp").forward(request, response);
		}
	}

	if (request.getAttribute("nrobene") != null) {
		elNoBene = request.getAttribute("nrobene").toString().trim();
	} else {
		elNoBene = String.valueOf(numBenefiiarios + 1).trim();
	}
%>
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
<link href="./css/spinner.css" rel="stylesheet">
<link href="./css/jquery-ui.css" rel="stylesheet">




<style type="text/css">
.boton_personalizado {
	text-decoration: none;
	padding: 10px;
	font-weight: 600;
	font-size: 20px;
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


</head>
<body onload="startTimer(90)">
	<div id="cover"></div>
	<div id="covers" style="display: none">
		<div class="container-spinner">
			<h1 class="spinner-title">Comprimiendo...</h1>
		</div>
	</div>
	<!--  Spinner -->
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
							<h1 class="title title-contrata-ahora">Sistema de Prevenci�n
								de Lavado de Dinero</h1>
						</div>
					</div>
				</div>
			</div>
		</div>
	</header>
	<h5 id="Imp2" class="title title-contrata-ahora white-text">Sistema
		de Prevenci�n de Lavado de Dinero</h5>
	<div class="container">
		<div id="Imp3" class="main row">
			<article class="col-xs-12 col-sm-8 col-md-9 col-lg-9">
				<h2 class="text-muted">
					Datos y Documentos del Due�o Beneficiario o <small>
						Beneficiario Controlador, Persona F�sica.</small>
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
				<%
					//Llamamos a los pa�ses desde la base de datos
					ArrayList listaPaises = new DatosPais().getList();
					ArrayList listaTipoIdentificacion = new DatosTipoIdentifiacion().getList();
					ArrayList listaActivida = new DatosActividad().getList("");

					//                                    UsuarioTransitivo.setSiBeneficiario("0");
				%>
				<form onsubmit="return validarBeneficiarioPersonaFisica()"
					action="BenefisiarioPersonaFisica" method="post"
					name="frmBeneFisica" id="frmBenefisica"
					enctype="multipart/form-data">


					<%
						idCliente = (String) request.getParameter("idCliente");

						out.println("<input type=\"hidden\" id=\"bIdCliente\" name=\"bIdCliente\" value=\"" + idCliente + "\">");
					%>

					<input type="hidden" id="exito" name="exito"> <input
						type="hidden" id="esEdicion" name="esEdicion"
						value="<c:out value="${esEdicion}" />"> <input
						type="hidden" id="nroBene" name="nroBene"
						value="<c:out value="${nrobene}" />"> <input
						type="hidden" id="estadoCliente" name="estadoCliente"
						value="<c:out value="${estadoCliente}" />"> <input
						type="hidden" id="perfilId" name="perfilId"
						value="<c:out value="${perfilId}" />">


					<table border="0" id="">
						<tr id="Imp4">
							<td colspan="3">
								<h4>Datos y Documentos.</h4>
							</td>
						</tr>

						<tr>
							<td><label id="Imp5"> * Tel�fono pa�s </label> <select
								class="browser-default" name="btelPais" id="btelPais">
									<option value="">* Tel�fono pa�s</option>
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
							<td id="Imp55">
								<div class="md-form form-sm">
									<input class="form-control" type="text" name="btelefono"
										id="btelefono" maxlength="10"
										onkeypress="return esNumero(this, event)" maxlength="10"
										value="<c:out value="${telefono}" />"> <label
										for="btelefono" class=""> * Tel�fono </label>
								</div>
							</td>
						</tr>
						<tr id="Imp6">
							<td colspan="3">
								<div class="md-form form-sm">

									<label for="tipo">* Correo electr�nico </label><br /> <input
										class="form-control" type="text" onkeyup="minus(this)"
										id="correo" name="correo" value="<c:out value="${correo}" />"
										maxlength="60">
								</div>

							</td>
						</tr>
						<tr id="Imp7">
							<td colspan="3">
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="bnombres" id="bnombres" maxlength="200" size="80"
										value="<c:out value="${nombre}" />"> <label
										for="bnombres" class=""> * Nombre(s) </label>
								</div>
							</td>
						</tr>
						<tr id="Imp8">
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="bpaterno" id="bpaterno" maxlength="200"
										value="<c:out value="${apellidopaterno}" />"> <label
										for="bpaterno" class=""> * Apellido paterno</label>
								</div>
							</td>
							<td></td>

							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="bmaterno" id="bmaterno" maxlength="200"
										value="<c:out value="${apellidomaterno}" />"> <label
										for="bmaterno" class=""> * Apellido materno</label>
								</div>
							</td>
						</tr>
						<tr>
							<td id="Imp9"><input type="date" name="bfechaNaciento"
								id="bfechaNaciento" placeholder="* Fecha de nacimiento"
								value="<c:out value="${fechanacimiento}" />"> <img
								src="img/calendar.png" height="32" width="32" /><label>*Fecha
									de nacimiento</label></td>
							<td></td>
							<td><label id="Imp10"> * Pa�s de nacimiento</label> <select
								class="browser-default" name="bpaisNacimiento"
								id="bpaisNacimiento">
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

							<td><label id="Imp11"> * Pa�s de nacionalidad </label> <select
								class="browser-default" name="bpaisNacionalidad"
								id="bpaisNacionalidad">
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

						<tr id="Imp12">
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this);"
										name="bRFC" id="bRFC" maxlength="13"
										value="<c:out value="${rfc}" />"> <label for="bRFC">*
										RFC</label>
								</div>
							</td>
							<td></td>

							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="bcurp" id="bcurp" size="18" maxlength="18"
										value="<c:out value="${curp}" />"> <label for="bcurp">*
										CURP</label>
								</div>
							</td>
						</tr>

						<tr>
							<td colspan="3"><label id="Imp13">* Actividad
									econ�mica </label> <select class="browser-default" name="bactividadEco"
								id="bactividadEco" style="width: 99%;">
									<option value="">* Actividad econ�mica</option>


									<%
										//Verificamos que tengamos pa�ses depu�s de la consulta a la base de datos
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
										id="bbuscarActividadEco" name="bbuscarActividadEco"
										class="input_text" onfocusout="validarActividadEco()">
									<label for="bbuscarActividadEco">* Actividad econ�imica</label><br />
									<br>
								</div>

							</td>


						</tr>
						<tr>
							<td><label id="Imp14" for="tipoIdentificacion">*
									Tipo ID</label> <input type="hidden" id="TipoIdentificacion"
								name="TipoIdentificacion"
								value="<c:out value="${identifica_id}" />"> <select
								class="browser-default" class="" name="tipoIdentificacion"
								id="tipoIdentificacion" onchange="hayIdentificacion()">
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
							<td id="Imp15">
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="numeroId" id="numeroId" maxlength="40"
										value="<c:out value="${numeroid}" />"> <label
										for="numeroId" class=""> * No. de ID</label>
								</div>
							</td>
						</tr>

						<tr id="Imp16">

							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text"
										onchange="hayOtraIdentificacion()" onkeyup="mayus(this)"
										name="otroTipoId" id="otroTipoId"
										value="<c:out value="${identificaciontipo}" />"> <label
										for="otroTipoId" class=""> * Otro tipo de ID</label>
								</div>
							</td>
							<td></td>
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="autoridadEmite" id="autoridadEmite"
										value="<c:out value="${AutoridadEmiteId}" />"> <label
										for="autoridadEmite" class=""> * Entidad que emite ID</label>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="3"><label for="tipo">* Carga de ID</label><br />
								<input type="hidden" id="idarchivoIdPF" name="idarchivoIdPF"
								value="<c:out value="${imagenid}" />"> <input
								type="hidden" id="archivoIdPFZip" name="archivoIdPFZip" /> <input
								type="file" name="archivoIdPF" id="archivoIdPF"
								accept="document/pdf"
								onchange="validarArchivoId('lupaId', this, document.getElementById('archivoIdPFZip'), document.getElementById('covers'))">&nbsp;
								<a href="javascript:PreviewImage('archivoIdPF')"> <img
									src="img/lupa.jpg" height="32" width="32" style="display: none"
									id="lupaId" />
							</a>
								<p class="help-block">Un solo archivo de m�ximo 10MB</p> <%
 	String idCLiente = idCliente;
 	String rutaOrigen = Rutas.rutaCarga + Rutas.separador + idCLiente + Rutas.separador
 			+ Rutas.dirIdentificacionBene + elNoBene + Rutas.separador;
 	String rutaDestino = Rutas.rutaDescarga + Rutas.separador + idCLiente + Rutas.separador
 			+ Rutas.dirIdentificacionBene + elNoBene + Rutas.separador;

 	File[] listaArchivos = new File(rutaOrigen).listFiles();

 	if (listaArchivos != null && listaArchivos.length > 0) {
 		Arrays.sort(listaArchivos, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
 		out.println("<INPUT type='hidden' id='HayArchivoIdPF' name='HayArchivoIdPF' value='si'");
 		int a = 0;
 		if ((perfilId.compareTo("1") == 0) || (perfilId.compareTo("2") == 0)) { // Puede ver el historial                                                                                                                                                                                
 			out.println("<p>Historial de documentos guardados:</p>");
 			a = listaArchivos.length;
 		} else {
 			out.println("<p>�ltimo archivo guardado:</p>");
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
 				//new Archivos().copyFile(listaArchivos[i].getAbsolutePath(), archivoDestino.getAbsolutePath());
 				out.println("<p>" + listaArchivos[i].getName() + " <a href=\"javascript:window.open('"
 						+ dirRootDes + "/" + Rutas.dirIdentificacionBene + elNoBene + "/"
 						+ listaArchivos[i].getName()
 						+ "', 'clearcache=yes')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>");
 				//out.println("<p> �ltimo archivo guardado:" + archivo.getName() + " <a href=\"javascript:window.open('" + dirRootDes  + "/" + Rutas.dirCedula + "/" + archivo.getName() + "')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"></a></p>" );
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
							<td colspan="3"><label for="tipo">* CURP</label><br /> <input
								type="hidden" id="idarchivoCurp" name="idarchivoCurp"
								value="<c:out value="${imagencurp}" />"> <input
								type="hidden" id="archivoCurpZip" name="archivoCurpZip" /> <input
								type="file" name="archivoCurp" id="archivoCurp"
								accept="document/pdf"
								onchange="validarArchivoCurp('lupaCurp', this, document.getElementById('archivoCurpZip'), document.getElementById('covers'))">&nbsp;
								<a href="javascript:PreviewImage('archivoCurp')"> <img
									src="img/lupa.jpg" height="32" width="32" style="display: none"
									id="lupaCurp" />
							</a>
								<p class="help-block">Un solo archivo de m�ximo 10MB</p> <%
 	idCLiente = idCliente;
 	rutaOrigen = Rutas.rutaCarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirCurpBene + elNoBene
 			+ Rutas.separador;
 	rutaDestino = Rutas.rutaDescarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirCurpBene
 			+ elNoBene + Rutas.separador;

 	listaArchivos = new File(rutaOrigen).listFiles();

 	if (listaArchivos != null && listaArchivos.length > 0) {
 		Arrays.sort(listaArchivos, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
 		out.println("<INPUT type='hidden' id='HayArchivoIdPF' name='HayArchivoIdPF' value='si'");
 		int a = 0;
 		if ((perfilId.compareTo("1") == 0) || (perfilId.compareTo("2") == 0)) { // Puede ver el historial                                                                                                                                                                                
 			out.println("<p>Historial de documentos guardados:</p>");
 			a = listaArchivos.length;
 		} else {
 			out.println("<p>�ltimo archivo guardado:</p>");
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
 				//new Archivos().copyFile(listaArchivos[i].getAbsolutePath(), archivoDestino.getAbsolutePath());
 				out.println("<p>" + listaArchivos[i].getName() + " <a href=\"javascript:window.open('"
 						+ dirRootDes + "/" + Rutas.dirCurpBene + elNoBene + "/" + listaArchivos[i].getName()
 						+ "', 'clearcache=yes')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>");
 				//out.println("<p> �ltimo archivo guardado:" + archivo.getName() + " <a href=\"javascript:window.open('" + dirRootDes  + "/" + Rutas.dirCedula + "/" + archivo.getName() + "')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"></a></p>" );
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
							<td colspan="3"><label for="tipo">* Carga de c�dula
									fiscal</label><br /> <input type="hidden" id="idarchivoCedulaFiscal"
								name="idarchivoCedulaFical"
								value="<c:out value="${imagencedulafiscal}" />"> <input
								type="hidden" id="cedulaPFZip" name="cedulaPFZip" /> <input
								type="file" name="cedulaPF" id="cedulaPF" accept="document/pdf"
								onchange="validarArchivoCedulaFiscal('lupaCedula', this, document.getElementById('cedulaPFZip'), document.getElementById('covers'))">&nbsp;
								<a href="javascript:PreviewImage('cedulaPF')"> <img
									src="img/lupa.jpg" height="32" width="32" style="display: none"
									id="lupaCedula" />
							</a>
								<p class="help-block">Un solo archivo de m�ximo 10MB</p> <%
 	idCLiente = idCliente;
 	rutaOrigen = Rutas.rutaCarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirCedulaBene
 			+ elNoBene + Rutas.separador;
 	rutaDestino = Rutas.rutaDescarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirCedulaBene
 			+ elNoBene + Rutas.separador;

 	listaArchivos = new File(rutaOrigen).listFiles();
 	if (listaArchivos != null && listaArchivos.length > 0) {
 		Arrays.sort(listaArchivos, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
 		out.println("<INPUT type='hidden' id='HayArchivoIdPF' name='HayArchivoIdPF' value='si'");
 		int a = 0;
 		if ((perfilId.compareTo("1") == 0) || (perfilId.compareTo("2") == 0)) { // Puede ver el historial                                                                                                                                                                                
 			out.println("<p>Historial de documentos guardados:</p>");
 			a = listaArchivos.length;
 		} else {
 			out.println("<p>�ltimo archivo guardado:</p>");
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
 				//new Archivos().copyFile(listaArchivos[i].getAbsolutePath(), archivoDestino.getAbsolutePath());
 				out.println("<p>" + listaArchivos[i].getName() + " <a href=\"javascript:window.open('"
 						+ dirRootDes + "/" + Rutas.dirCedulaBene + elNoBene + "/" + listaArchivos[i].getName()
 						+ "', 'clearcache=yes')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>");
 				//out.println("<p> �ltimo archivo guardado:" + archivo.getName() + " <a href=\"javascript:window.open('" + dirRootDes  + "/" + Rutas.dirCedula + "/" + archivo.getName() + "')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"></a></p>" );
 			} catch (Exception es) {
 				es.printStackTrace();
 			}

 		} //for
 	} else {
 		out.println("<INPUT type='hidden' id='HayArchivoIdPF' name='HayArchivoIdPF' value='no'");
 	}
 %></td>
						</tr>
						<tr id="Imp17">
							<td colspan="3">
								<h4>Domicilio Due�o Beneficiario</h4>
							</td>

							<td></td>
						</tr>


						<tr id="Imp18">
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="bcalle" id="bcalle" maxlength="100"
										value="<c:out value="${calle}" />"> <label
										for="bcalle" class=""> * Calle</label>
								</div>

							</td>
							<td></td>
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="bexterior" id="bexterior" maxlength="56"
										value="<c:out value="${numexterior}" />"> <label
										for="bexterior" class=""> * No Exterior </label>
								</div>
							</td>
						</tr>
						<tr id="Imp19">
							<td></td>
							<td></td>
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="binterior" id="binterior" maxlength="40"
										value="<c:out value="${numinterior}" />"> <label
										for="binterior" class=""> * No Interior </label>
								</div>
							</td>
						</tr>
						<tr id="Imp20">
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="bcolonia" id="bcolonia" maxlength="100"
										value="<c:out value="${colonia}" />"> <label
										for="bcolonia" class=""> * Colonia </label>
								</div>
							</td>
							<td></td>
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" name="bcp" id="bcp"
										onkeypress="return esNumero(this, event)"
										onkeypress="return esCodigoPostal(this)" maxlength="5"
										value="<c:out value="${codpostal}" />"> <label
										for="bcp" class=""> * C�digo Postal</label>
								</div>
							</td>
						</tr>
						<tr id="Imp21">
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="bestado" id="bestado" maxlength="100"
										value="<c:out value="${estado_prov}" />"> <label
										for="bestado" class=""> * Estado</label>
								</div>
							</td>
							<td>
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="bCiudad" id="bCiudad" maxlength="100"
										value="<c:out value="${ciudad}" />"> <label
										for="bCiudad" class=""> * Ciudad</label>
								</div>
							</td>

						</tr>
						<tr>
							<td><label id="Imp22"> * Pa�s </label> <select
								class="browser-default" name="bpaisDomicilio"
								id="bpaisDomicilio">
									<option value="">* Pa�s</option>
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
							<td colspan="3"><label for="tipo">* Comprobante de
									domicilio</label><br /> <input type="hidden"
								id="idarchivoCompDomicilio" name="idarchivoCompDomicilio"
								value="<c:out value="${imagencompdomicilio}" />"> <input
								type="hidden" id="compDomicilioZip" name="compDomicilioZip" />
								<input type="file" name="compDomicilio" id="compDomicilio"
								accept="document/pdf"
								onchange="validarArchivoComprobanteDomicilio('lupaDomicilio', this, document.getElementById('compDomicilioZip'), document.getElementById('covers'))">&nbsp;
								<div class="popup" onclick="myFunction()">
									<img src="img/signo.jpg" height="32" width="32" /> <span
										class="popuptext" id="myPopup">Recibo de pago por
										servicios domiciliados o Estados de cuenta bancarios, con
										antig�edad no mayor a tres meses a su fecha de emisi�n,
										Contrato de arrendamiento vigente, Constancia de Inscripci�n
										en el RFC.</span>
								</div> <a href="javascript:PreviewImage('compDomicilio')"> <img
									src="img/lupa.jpg" height="32" width="32" style="display: none"
									id="lupaDomicilio" />
							</a>
								<p class="help-block">Un solo archivo de m�ximo 10MB</p> <%
 	idCLiente = idCliente;
 	rutaOrigen = Rutas.rutaCarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirComprobanteDomBene
 			+ elNoBene + Rutas.separador;
 	rutaDestino = Rutas.rutaDescarga + Rutas.separador + idCLiente + Rutas.separador
 			+ Rutas.dirComprobanteDomBene + elNoBene + Rutas.separador;

 	listaArchivos = new File(rutaOrigen).listFiles();
 	if (listaArchivos != null && listaArchivos.length > 0) {
 		Arrays.sort(listaArchivos, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
 		out.println("<INPUT type='hidden' id='HayArchivoDomicilio' name='HayArchivoDomicilio' value='si'");
 		int a = 0;
 		if ((perfilId.compareTo("1") == 0) || (perfilId.compareTo("2") == 0)) { // Puede ver el historial                                                                                                                                                                                
 			out.println("<p>Historial de documentos guardados:</p>");
 			a = listaArchivos.length;
 		} else {
 			out.println("<p>�ltimo archivo guardado:</p>");
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
 				//new Archivos().copyFile(listaArchivos[i].getAbsolutePath(), archivoDestino.getAbsolutePath());
 				out.println("<p>" + listaArchivos[i].getName() + " <a href=\"javascript:window.open('"
 						+ dirRootDes + "/" + Rutas.dirComprobanteDomBene + elNoBene + "/"
 						+ listaArchivos[i].getName()
 						+ "', 'clearcache=yes')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>");
 				//out.println("<p> �ltimo archivo guardado:" + archivo.getName() + " <a href=\"javascript:window.open('" + dirRootDes  + "/" + Rutas.dirCedula + "/" + archivo.getName() + "')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"></a></p>" );
 			} catch (Exception es) {
 				es.printStackTrace();
 			}

 		} //for
 	} else {
 		out.println("<INPUT type='hidden' id='HayArchivoDomicilio' name='HayArchivoDomicilio' value='no'");
 	}
 %></td>
						</tr>
						<tr>
						</tr>
						<tr>
						</tr>
						<tr>
						</tr>
						<tr>
							<td colspan="2">

								<button class="btn btn-danger" id="btn-procesar">Guardar</button>




							</td>
							<td>
								<!--<a class="boton_personalizado2" id ="btnClose" style="visibility:hidden" href="login.jsp">Cerrar</a>-->
								<button class="btn btn-danger" id="btnClose"
									onclick="window.close()">Cerrar</button>
							</td>
						</tr>







					</table>

				</form>






				<div id="sobrecapa">
					<div id="textSobrecapa">
						<!-- comienza la informaci�n del beneficiario -->

						<iframe id="frameContenedor" src="beneficiario_personafisica.jsp"
							frameborder="10" scrolling="yes"> </iframe>

						<!-- termino la parde del beneficiario-->
					</div>
					<!-- textSobre -->
				</div>
				<!-- SOBRECAPA -->



			</div>
			<div class="col-md-3">
				<p>
					<a id="Impr"
						href="javascript:imprimir(Imp1,Imp2,Imp3,Imp4,Imp5,Imp55,Imp6,Imp7,Imp8,Imp9,Imp10,Imp11,Imp12,Imp13,Imp14,Imp15,Imp16,Imp17,Imp18,Imp19,Imp20,Imp21,Imp22)">Imprimir
						pantalla.</a>

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
						target="_top"> T�rminos y Condiciones </a></li>
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

	<!-- Los siguientes divs para la previsualizaci�n de los archivos pdf -->
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
							//       $("#covers").hide();
							txtEstatus = '${estadoCliente}';
							txtPerfilId = '${perfilId}';
							//alert(txtEstatus);
							document.getElementById('btnClose').style.visibility = 'hidden';

							if (txtEstatus == 'A') {
								//                                alert("entra validado");
								document.getElementById("btn-procesar").style.visibility = 'hidden';
								document.getElementById('btnClose').style.visibility = 'visible';
								var form = document
										.getElementById('frmBenefisica');
								var elements = form.elements;
								for (var i = 0, len = elements.length; i < len; ++i) {
									//alert("for");
									elements[i].readOnly = true;
									elements[i].disabled = true;
									document.getElementById('btnClose').disabled = false;
								}
							}
							document.getElementById('Impr').style.visibility = 'hidden';
							if (txtPerfilId == '1' || txtPerfilId == '2') {
								//                                alert("entra adm  o pld");
								document.getElementById("btn-procesar").style.visibility = 'visible';
								document.getElementById('btnClose').style.visibility = 'hidden';
								document.getElementById('Impr').style.visibility = 'visible';
								var form = document
										.getElementById('frmBenefisica');
								var elements = form.elements;
								for (var i = 0, len = elements.length; i < len; ++i) {
									//alert("for");
									elements[i].readOnly = false;
									elements[i].disabled = false;
								}
							}

							if (txtPerfilId == '3' || txtPerfilId == '4'
									|| txtPerfilId == '5') {
								//                                alert("entra admtmk, tmk o sup");
								document.getElementById("btn-procesar").style.visibility = 'hidden';
								document.getElementById('btnClose').style.visibility = 'visible';
								var form = document
										.getElementById('frmBenefisica');
								var elements = form.elements;
								for (var i = 0, len = elements.length; i < len; ++i) {
									//alert("for");
									elements[i].readOnly = true;
									elements[i].disabled = true;
									document.getElementById('btnClose').disabled = false;
								}
							}

							if (txtPerfilId == '2' && txtEstatus == 'A') {
								//                                alert("entra pld validado");
								document.getElementById("btn-procesar").style.visibility = 'hidden';
								document.getElementById('btnClose').style.visibility = 'visible';
								var form = document
										.getElementById('frmBenefisica');
								var elements = form.elements;
								for (var i = 0, len = elements.length; i < len; ++i) {
									//alert("for");
									elements[i].readOnly = true;
									elements[i].disabled = true;
									document.getElementById('btnClose').disabled = false;
								}
							}

							//telPais

							var selector = document.getElementById('btelPais');
							for (var i = 0; i < selector.length; i++) {
								if ('MX' == selector.options[i].value.trim()) {
									selector.selectedIndex = i;
									break;
								} else {//if
									selector.selectedIndex = 0;
								}
							}//for 
							//bpaisNacimiento
							var selector = document
									.getElementById('bpaisNacimiento');
							for (var i = 0; i < selector.length; i++) {
								if ('MX' == selector.options[i].value.trim()) {
									selector.selectedIndex = i;
									break;
								} else {//if
									selector.selectedIndex = 0;
								}
							}//for 
							//bpaisNacionalidad
							var selector = document
									.getElementById('bpaisNacionalidad');
							for (var i = 0; i < selector.length; i++) {
								if ('MX' == selector.options[i].value.trim()) {
									selector.selectedIndex = i;
									break;
								} else {//if
									selector.selectedIndex = 0;
								}
							}//for 

							//bpaisDomicilio
							var selector = document
									.getElementById('bpaisDomicilio');
							for (var i = 0; i < selector.length; i++) {
								if ('MX' == selector.options[i].value.trim()) {
									selector.selectedIndex = i;
									break;
								} else {//if
									selector.selectedIndex = 0;
								}
							}//for 
							//ASIGNANDO NO APLICA COMO ACTIVODAD ECO PREDET
							var selector = document
									.getElementById('bactividadEco');
							for (var i = 0; i < selector.length; i++) {
								if ('1000000' == selector.options[i].value
										.trim()) {
									selector.selectedIndex = i;
									break;
								} else {//if
									selector.selectedIndex = 0;
								}
							}//for 

							esEdicion = document.getElementById('esEdicion').value;
							if (esEdicion == '1') {

								//                    alert('aqui1');
								//                    txtbRFC = document.getElementById('bRFC').disabled= false;
								//                    alert('aqui2');

								//ASIGNANDO EL PA�S TEL�FONO
								txtTelPais = '${tepais}';
								if (txtTelPais != null || txtTelPais.length > 0) {
									var selector = document
											.getElementById('btelPais');
									for (var i = 0; i < selector.length; i++) {
										if (txtTelPais == selector.options[i].value) {
											selector.selectedIndex = i;
											break;
										} else {//if
											selector.selectedIndex = 0;
										}
									}//for 
								}

								//ASIGNANDO EL PA�S DE NACIMIENTO
								txtPaisNacimiento = '${paisnacim}';
								if (txtPaisNacimiento != null
										|| txtPaisNacimiento.length > 0) {
									var selector = document
											.getElementById('bpaisNacimiento');
									for (var i = 0; i < selector.length; i++) {
										if (txtPaisNacimiento == selector.options[i].value) {
											selector.selectedIndex = i;
											break;
										} else {//if
											selector.selectedIndex = 0;
										}
									}//for 
								}

								//ASIGNANDO EL PA�S DE NACIONALIDAD
								txtPaisNaciionalidad = '${paisnacio}';
								if (txtPaisNaciionalidad != null
										|| txtPaisNaciionalidad.length > 0) {
									var selector = document
											.getElementById('bpaisNacionalidad');
									for (var i = 0; i < selector.length; i++) {
										if (txtPaisNaciionalidad == selector.options[i].value) {
											selector.selectedIndex = i;
											break;
										} else {//if
											selector.selectedIndex = 0;
										}
									}//for 
								}

								//ASIGNANDO LA ACTIVIDAD ECON�MICA 
								txtActividadId = '${actividad_id}';
								if (txtActividadId != null
										|| txtActividadId.length > 0) {
									var selector = document
											.getElementById('bactividadEco');
									for (var i = 0; i < selector.length; i++) {
										if (txtActividadId == selector.options[i].value) {
											selector.selectedIndex = i;
											break;
										} else {//if
											selector.selectedIndex = 0;
										}
									}//for 
								}

							} //fin es edicion
							//   ASIGNANDO TIPO DE IDENTIFICACI�N 
							txtTipoIdentificacion = document
									.getElementById('TipoIdentificacion').value; //llega de la respuesta
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
						}); //fin del ready
	</script>


	<script>
		jQuery(function() {
			$("#buscarActividadEco").autocomplete("list.jsp");
		});
		jQuery(function() {
			$("#bbuscarActividadEco").autocomplete("list.jsp");
		});
	</script>

	<script>
		function hayOtraIdentificacion() {
			txtOtraIdentificacion = document.getElementById('otroTipoId').value;
			if (txtOtraIdentificacion.length > 0 || txtOtraIdentificacion != '') {
				document.getElementById('tipoIdentificacion').selectedIndex = 14;
			}
		}

		function hayIdentificacion() {
			document.getElementById('otroTipoId').value = '';
		}
		//
	</script>

	<script>
		function validarActividadEco() {
			//Asigna al select el valor lozalizado en la lista de actividades escrita

			txtTextoActividad = document.getElementById('bbuscarActividadEco').value;
			var selector = document.getElementById('bactividadEco');

			if (txtTextoActividad != null || txtTextoActividad.length > 0) {
				for (var i = 0; i < selector.length; i++) {
					if (txtTextoActividad == selector.options[i].text) {
						selector.selectedIndex = i;
						break;
					} else {//if
						selector.selectedIndex = 0;
					}
				}//for
			}//fin del for
		}//fin funcion validarActivdadEco
	</script>

	<script>
		function hayOtraActividad() {
			var txtOtraActividad = document.getElementById('otraactividadEco').value;
			if (txtOtraActividad != null || txtOtraActividad.length > 0) {
				document.getElementById('buscarActividadEco').value = '';
				document.getElementById('actividadEco').selectedIndex = 0;

			}
		}
	</script>


	<script>
		function validarBeneficiarioPersonaFisica() {
			window.addEventListener("keypress", function(event) {
				if (event.keyCode == 13) {
					event.preventDefault();
				}
			}, false);
			//para que bloque el ENTER y no permita registrar duplicado
			if (validals()) {
				$('.spinner-wrapper').fadeIn('fast');
				Prog();
				return true;
			} else {
				return false;
			}
		}
		function validals() {
			txtbnombres = document.getElementById('bnombres').value;
			if (txtbnombres == null || txtbnombres.length == 0
					|| /^\s+$/.test(txtbnombres)) {
				alert('El nombre no puede dejarse en blanco');
				document.getElementById('bnombres').focus();
				return false;
			}
			//        
			txtbpaterno = document.getElementById('bpaterno').value;
			if (txtbpaterno == null || txtbpaterno.length == 0
					|| /^\s+$/.test(txtbpaterno)) {
				alert('El apellido paterno no puede dejarse en blanco');
				document.getElementById('bpaterno').focus();
				return false;
			}
			//        
			txtbmaterno = document.getElementById('bmaterno').value;
			if (txtbmaterno == null || txtbmaterno.length == 0
					|| /^\s+$/.test(txtbmaterno)) {
				alert('El apellido materno no puede dejarse en blanco');
				document.getElementById('bmaterno').focus();
				return false;
			}
			//        

			txtbcurp = document.getElementById('bcurp').value;
			if (txtbcurp.length > 0) {
				if (txtbcurp.length != 18) {
					alert('La longitud del CURP debe ser de 18 caracteres');
					document.getElementById('bcurp').focus();
					return false;
				}
			}
			//  

			txtbcp = document.getElementById('bcp').value;
			if (txtbcp.length != 0) {
				if (txtbcp.length != 5) {
					alert('La longitud del c�digo postal debe ser de cinco d�gitos');
					document.getElementById('bcp').focus();
					return false;
				}
			}
			txtbRFC = document.getElementById('bRFC').value;
			if (txtbRFC.length > 0) {
				if (txtbRFC.length != 13) {
					alert('La longitud del RFC debe ser de 13 caracteres');
					document.getElementById('bRFC').focus();
					return false;
				}
			}
			txtFechaNacimiento = document.getElementById('bfechaNaciento').value;
			if (txtbRFC.length == 13 && txtFechaNacimiento <= 0) {
				alert('Debe indicar fecha de nacimiento');
				document.getElementById('bfechaNaciento').focus();
				return false;
			}

			txtFechaNacimiento = getFechaNac(txtFechaNacimiento); //normalizando 
			b = document.getElementById('bRFC').value;
			txtFechaNacimientoRFC = '';
			if (b.length > 0 && b != '') {
				txtFechaNacimientoRFC = getFechaNacRFC(document
						.getElementById('bRFC').value);
			}

			if (txtFechaNacimiento != null && txtFechaNacimiento.length > 0
					&& txtFechaNacimientoRFC != null
					&& txtFechaNacimientoRFC.length > 0) {
				if (txtFechaNacimiento != txtFechaNacimientoRFC) {
					alert('La fecha de nacimiento no coincide con la fecha de nacimiento del RFC');
					document.getElementById('bRFC').focus();
					return false;
				}
			}

			txttelefono = document.getElementById('btelefono').value;
			if (txttelefono.length !== 0) {
				if (txttelefono.length !== 10) {
					alert('El N�mero de Tel�fono debe ser de 10 dig�tos');
					document.getElementById('btelefono').focus();
					return false;
				}
			}

			if (document.getElementById('archivoIdPF').value.length == 0)
				document.getElementById('archivoIdPFZip').value = '';
			if (document.getElementById('archivoCurp').value.length == 0)
				document.getElementById('archivoCurpZip').value = '';
			if (document.getElementById('cedulaPF').value.length == 0)
				document.getElementById('cedulaPFZip').value = '';
			if (document.getElementById('compDomicilio').value.length == 0)
				document.getElementById('compDomicilioZip').value = '';

			localStorage.setItem('capturado', '1');
			
            document.getElementById('btn-procesar').disabled=true;
            alert("Validando RFC y CURP");
			if(validarRFC() && validarCURP()){
				document.getElementById('btn-procesar').disabled=false;
				return true;
			}else{
				document.getElementById('btn-procesar').disabled=false;
			return false;
		}
			
		}//funcion validar beneficiario
	</script>
	<script>
	function validarRFC(){
		var txtbRFC = document.getElementById('bRFC').value;

		var obj="";
		var bandera=null;
		
		if( txtbRFC != null && !txtbRFC==""){
		
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
			    	document.getElementById('bRFC').focus();
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
		
		var txtCurp = document.getElementById('bcurp').value;
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
			    	document.getElementById('bcurp').focus();

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
		function imprimir(Imp1, Imp2, Imp3, Imp4, Imp5, Imp55, Imp6, Imp7,
				Imp8, Imp9, Imp10, Imp11, Imp12, Imp13, Imp14, Imp15, Imp16,
				Imp17, Imp18, Imp19, Imp20, Imp21, Imp22) {
			//alert(""+tipPer);
			//alert(""+Estats);
			var printContents = document.getElementById('Imp1').innerHTML
					+ document.getElementById('Imp2').innerHTML
					+ document.getElementById('Imp3').innerHTML
					+ document.getElementById('Imp4').innerHTML
					+ document.getElementById('Imp5').innerHTML
					+ document.getElementById('btelPais').options[document
							.getElementById('btelPais').selectedIndex].text
					+ document.getElementById('Imp55').innerHTML
					+ document.getElementById('Imp6').innerHTML
					+ document.getElementById('Imp7').innerHTML
					+ document.getElementById('Imp8').innerHTML
					+ document.getElementById('Imp9').innerHTML
					+ document.getElementById('Imp10').innerHTML
					+ document.getElementById('bpaisNacimiento').options[document
							.getElementById('bpaisNacimiento').selectedIndex].text
					+ document.getElementById('Imp11').innerHTML
					+ document.getElementById('bpaisNacionalidad').options[document
							.getElementById('bpaisNacionalidad').selectedIndex].text
					+ document.getElementById('Imp12').innerHTML
					+ document.getElementById('Imp13').innerHTML
					+ document.getElementById('bactividadEco').options[document
							.getElementById('bactividadEco').selectedIndex].text
					+ document.getElementById('Imp14').innerHTML
					+ document.getElementById('tipoIdentificacion').options[document
							.getElementById('tipoIdentificacion').selectedIndex].text
					+ document.getElementById('Imp15').innerHTML
					+ document.getElementById('Imp16').innerHTML
					+ document.getElementById('Imp17').innerHTML
					+ document.getElementById('Imp18').innerHTML
					+ document.getElementById('Imp19').innerHTML
					+ document.getElementById('Imp20').innerHTML
					+ document.getElementById('Imp21').innerHTML
					+ document.getElementById('Imp22').innerHTML
					+ document.getElementById('bpaisDomicilio').options[document
							.getElementById('bpaisDomicilio').selectedIndex].text;
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

