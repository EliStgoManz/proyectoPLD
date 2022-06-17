<%@page import="datosRatos.ConsultaWS"%>
<%@page import="entidad.Representante"%>
<%@page import="datosRatos.DatosRepLegal"%>
<%@page import="rfcCurpAppi.token"%>
<%@page import="datosRatos.DatosRepLegal"%>
<%@page import="datosRatos.DatosClienteRaro"%>
<%@page import="entidad.Cliente"%>
<%@page import="utilidades.Archivos"%>
<%@page import="utilidades.Rutas"%>
<%@page import="java.io.File"%>
<%@page import="entidad.Beneficiario"%>
<%@page import="datosRatos.DatosBeneficiario"%>
<%@page import="utilidades.Mensajes"%>
<%@page import="utilidades.PerfilUsuario"%>
<%@include file="valida_login.jsp"%>
<%@page import="entidad.UsuarioSistema"%>
<%@page import="entidad.Actividad"%>
<%@page import="entidad.Giro"%>
<%@page import="datosRatos.DatosGiro"%>
<%@page import="entidad.TipoIdentificacion"%>
<%@page import="entidad.Pais"%>
<%@page import="datosRatos.DatosActividad"%>
<%@page import="datosRatos.DatosTipoIdentifiacion"%>
<%@page import="datosRatos.DatosPais"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
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
	font-size: 11px;
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
						Datos y Documentos del Cliente Gobierno, Incluyendo <small>Ayuntamientos,
							Estados, Municipios y Federaciones.</small>
					</h2>
				</article>
				<aside class="col-xs-12 col-sm-4 col-md-3 col-lg-3">
					<p class="lead">
						Bienvenido:
						<%@include file="detectausuario.jsp"%>
						<!--   Bienvenido:  out.println( vClienteParam );    -->
					</p>
				</aside>
			</div>
			<div class="row">
				<div class="col-md-9 table-responsive">
					<form onsubmit="return validarFormularioGobiernoS()"
						action="Gobierno" method="post" id="frmGobierno"
						name="frmGobierno" enctype="multipart/form-data">
						<input type="hidden" id="tipoPersona" name="tipoPersona" value="G">
						<input type="hidden" id="Cliente_Id" name="Cliente_Id"
							value="<%out.print(vClienteParam);%>"> <input
							type="hidden" name="respuesta" id="respuesta"
							value="<c:out value="${resultado}" />" /> <input type="hidden"
							name="exito" id="exito" value="<c:out value="${exito}" />" /> <input
							id="esEdicion" name="esEdicion" type="hidden"
							value="<c:out value="${esEdicion}" />"> <input
							id="estadoCliente" name="estadoCliente" type="hidden"
							value="<c:out value='${estado}' />">

						<%
							//Llamamos a los paï¿½ses desde la base de datos
							ArrayList listaPaises = new DatosPais().getList();
							ArrayList listaTipoIdentificacion = new DatosTipoIdentifiacion().getList();
							ArrayList listaGiro = new DatosGiro().getList("");
							//                                    UsuarioTransitivo.setSiBeneficiario("0");
						%>
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
											out.println(vClienteParam);
										%>
								</label></td>
								<td width="50px"></td>
								<td width="350px"><label for="tipo">* Correo
										electrónico</label><br /> <input class="form-control" type="text"
									onkeyup="minus(this)" id="correo" name="correo"
									value="<%out.println(vMailParam);%>" readonly="readonly"
									maxlength="60"></td>
							</tr>
							<tr>
								<td><label>* Tel. país</label> <input type="hidden"
									id="idTelPais" name="idTelPais"
									value="<c:out value="${pais}" />"> <select
									class="browser-default" name="telPais" id="telPais"
									style="width: 80%;">
										<option value="">* Teléfono país</option>
										<%
											//Verificamos que tengamos paï¿½ses depuï¿½s de la consulta a la base de datos
											if (listaPaises != null) {
												for (int i = 0; i < listaPaises.size(); i++) {
													Pais p = (Pais) listaPaises.get(i);
													out.println("<option value=\"" + p.getPais().trim() + "\">" + p.getDescrpcion() + "</option>");
												}
											}
										%>

								</select></td>
								<td></td>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" id="telefono"
											name="telefono" onkeypress="return esNumero(this, event)"
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
											id="nombreCliente" name="nombreCliente" maxlength="200"
											size="80" value="<c:out value="${razonsocial}" />"> <label
											for="nombreCliente">* Denominación o Razón social</label>
									</div>
								</td>
							</tr>
							<tr>
								<td><input class="" type="date" id="fechaCreacion"
									name="fechaCreacion" value="<c:out value="${fechacreacion}" />">
									<img src="img/calendar.png" height="32" width="32" /> <label
									for="fechaCreacion">* Fecha creación </label></td>
								<td></td>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="rfcCliente" name="rfcCliente" maxlength="12"
											value="<%out.println(vRFCParam);%>" readonly> <label
											for="rfcCliente">* RFC</label>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="3"><br> <label>* Actividad u
										objeto social</label> <input type="hidden" id="idActividad"
									name="idActividad"
									value="<c:out value="${actividadobjetosocial}" />"> <select
									class="browser-default" name="actividadObjeto"
									id="actividadObjeto" style="width: 99%;">
										<option value="">* Actividad u objeto social</option>


										<%
											//Verificamos que tengamos giros en la base de dato
											if (listaGiro != null) {
												for (int i = 0; i < listaGiro.size(); i++) {
													Giro g = (Giro) listaGiro.get(i);
													out.println("<option value=\"" + g.getGiro_id() + "\">" + g.getDescropcion() + "</option>");
												}
											}
										%>

								</select> <br>
								<br></td>
							</tr>
							<tr>
								<td colspan="3" style="display: none;"><br>
								<br>
									<div class="md-form form-sm">
										<input type="text" onkeyup="mayus(this)"
											id="buscarActivodadObjeto" name="buscarActivodadObjeto"
											class="input_text" onfocusout="validarActividadEco()">
										<label for="buscarGiroObjeto">* Actividad u objeto
											social</label><br /> <br>
									</div></td>


							</tr>

							<tr>
								<td colspan="3" hidden=""><label>* Giro</label>
									<div class="md-form form-sm">

										<input type="hidden" id="idGiro" name="idGiro"
											value="<c:out value="${giro_id}" />"> <select
											class="browser-default" name="giro" id="giro"
											style="width: 150px;">
											<option value="">* Giro</option>

											<%
												ArrayList listaGiros = new DatosGiro().getList("");
												//Verificamos que tengamos giros en la base de dato
												if (listaGiros != null) {
													for (int i = 0; i < listaGiros.size(); i++) {
														Giro g = (Giro) listaGiros.get(i);
														out.println("<option value=\"" + g.getGiro_id() + "\">" + g.getDescropcion() + "</option>");
													}
												}
											%>

										</select>
									</div></td>
							</tr>
							<tr>
								<td colspan="3" style="display: none;">

									<div class="md-form form-sm">
										<input type="text" onkeyup="mayus(this)" id="buscarGiro"
											name="buscarGiro" class="input_text"
											onfocusout="validarGiro()"> <label for="buscarGiro">*
											Giro</label><br /> <br>
									</div>

								</td>
							</tr>
							<tr>

								<td><label>* Pais</label> <input type="hidden"
									id="idNacionalidadCliente" name="idNacionalidadCliente"
									value="<c:out value="${paisGobierno}" />"> <select
									class="browser-default" name="nacionalidadCliente"
									id="nacionalidadCliente" style="width: 90%;">
										<option value="">* Nacionalidad</option>
										<%
											//Verificamos que tengamos paï¿½ses depuï¿½s de la consulta a la base de datos
											if (listaPaises != null) {
												for (int i = 0; i < listaPaises.size(); i++) {
													Pais p = (Pais) listaPaises.get(i);
													out.println("<option value=\"" + p.getPais().trim() + "\">" + p.getDescrpcion() + "</option>");
												}
											}
										%>
								</select></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
							<tr>
								<td colspan="3"><label for="tipo">* Documento legal
										para acreditar la existencia</label><br /> <input type="hidden"
									id="idarchivoExistencia" name="idarchivoExistencia"
									value="<c:out value="${imagenExistencia}" />"> <input
									type="hidden" id="archivoExistenciaZip"
									name="archivoExistenciaZip" /> <input type="file"
									name="archivoExistencia" id="archivoExistencia"
									accept="document/pdf"
									onchange="validarArchivoDocLegal('lupaExistencia', this, document.getElementById('archivoExistenciaZip'), document.getElementById('covers'))">&nbsp;
									<a href="javascript:PreviewImage('archivoExistencia')"> <img
										src="img/lupa.jpg" height="32" width="32"
										style="display: none" id="lupaExistencia" />
								</a>
									<p class="help-block">Un solo archivo de máximo 25MB</p> <%
 	String idCLiente = vClienteParam;
 	String rutaOrigen = Rutas.rutaCarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirAreditaacion
 			+ Rutas.separador;
 	String rutaDestino = Rutas.rutaDescarga + Rutas.separador + idCLiente + Rutas.separador
 			+ Rutas.dirAreditaacion + Rutas.separador;

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
 						+ dirRootDes + "/" + Rutas.dirAreditaacion + "/" + listaArchivos[i].getName()
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
							</tr>
							<tr>
							<tr>
								<td colspan="3"><label for="tipo">* Cedula fiscal</label><br />
									<input type="hidden" id="idarchivoCedula"
									name="idarchivoCedula"
									value="<c:out value="${imagenCedula}" />"> <input
									type="hidden" id="archivoCedulaZip" name="archivoCedulaZip" />
									<input type="file" name="archivoCedula" id="archivoCedula"
									accept="document/pdf"
									onchange="validarArchivoCedulaFiscal('lupaCedula', this, document.getElementById('archivoCedulaZip'), document.getElementById('covers'))">&nbsp;
									<a href="javascript:PreviewImage('archivoCedula')"> <img
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
							</tr>
							<tr>
								<td colspan="3">
									<h4>Domicilio</h4>
								</td>
								<td></td>
							</tr>
							<tr>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="calle" name="calle" maxlength="100"
											value="<c:out value="${calle}" />"> <label
											for="calle">* Calle</label>
									</div>
								</td>
								<td></td>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="exterior" name="exterior" maxlength="56"
											value="<c:out value="${numexterior}" />"> <label
											for="exterior"> * Exterior</label>
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
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="estado" name="estado" maxlength="100"
											value="<c:out value="${estado_prov}" />"> <label
											for="estado"> * Estado</label>
									</div>
								</td>
								<td></td>
								<td><label>* País</label> <input type="hidden" id="idPais"
									name="idPais" value="<c:out value="${paisDomicilio}" />">
									<select class="browser-default" name="pais" id="pais"
									style="width: 90%;">
										<option value="">* País</option>
										<%
											//Verificamos que tengamos paï¿½ses depuï¿½s de la consulta a la base de datos
											if (listaPaises != null) {
												for (int i = 0; i < listaPaises.size(); i++) {
													Pais p = (Pais) listaPaises.get(i);
													out.println("<option value=\"" + p.getPais().trim() + "\">" + p.getDescrpcion() + "</option>");
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
									<br /> <input type="hidden" id="idarchivoDomicilio"
									name="idarchivoDomicilio"
									value="<c:out value="${archivoComprobanteDomicilio}" />">
									<input type="hidden" id="archivoDomicilioZip"
									name="archivoDomicilioZip" /> <input type="file"
									name="archivoDomicilio" id="archivoDomicilio"
									accept="document/pdf"
									onchange="validarArchivoComprobanteDomicilio('lupaDomicilio', this, document.getElementById('archivoDomicilioZip'), document.getElementById('covers'))">&nbsp;

									<a href="javascript:PreviewImage('archivoDomicilio')"> <img
										src="img/lupa.jpg" height="32" width="32"
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
							</tr>
							<tr>
								<td colspan="3">
									<h4>Datos del funcionario público que realiza el acto</h4>
								</td>
							</tr>
							<tr>
								<td colspan="3">
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="fnombre" name="fnombre" maxlength="200" size="80"
											value="<c:out value="${rlnombre}" />"> <label
											for="fnombre">* Nombre(s)</label>
									</div>
								</td>
							</tr>
							<tr>
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
							<tr>
								<td><input class="" type="date" id="fFechaNacimiento"
									name="fFechaNacimiento"
									value="<c:out value="${rlfechanacimiento}" />"> <img
									src="img/calendar.png" height="32" width="32" /> <label
									for="fFechaNacimiento">* Fecha nacimiento</label><br>
								<br></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="fRFC" name="fRFC" maxlength="13"
											value="<c:out value="${rlrfc}" />"> <label for="fRFC">*
											RFC</label>
									</div>
								</td>
								<td></td>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="fCURP" name="fCURP" value="<c:out value="${rlcurp}" />"
											maxlength="18"> <label for="fCURP">* CURP</label>
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
											id="fNumeroId" name="fNumeroId" maxlength="40"
											value="<c:out value="${rlnumeroid}" />"> <label
											for="fNumeroId">* No. Id</label>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="fotroTipoId" name="fotroTipoId" maxlength="200"
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
							<tr>
								<td colspan="3"><label for="tipo">* Carga de ID</label><br />
									<input type="hidden" id="idarchivofId" name="idarchivofId"
									value="<c:out value="${imagenFId}" />"> <input
									type="hidden" id="archivofIdZip" name="archivofIdZip" /> <input
									type="file" name="archivofId" id="archivofId"
									accept="document/pdf"
									onchange="validarArchivoId('lupafId', this, document.getElementById('archivofIdZip'), document.getElementById('covers'))">&nbsp;
									<a href="javascript:PreviewImage('archivofId')"> <img
										src="img/lupa.jpg" height="32" width="32"
										style="display: none" id="lupafId" />
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
							<!--					<tr>
						<td colspan="3">
							<label for="tipo">* Carga de cédula fiscal</label><br/>
                                                        <input type="hidden" id="idarchivofCedula" name="idarchivofCedula" value="<c:out value="${imagenFCedulafiscal}" />">
                                                        <input type="hidden" id="archivofCedulaZip" name="archivofCedulaZip" />
                                                        <input type="file" name="archivofCedula" id="archivofCedula" accept="document/pdf" onchange="validarArchivoCedulaFiscal('lupafCedula', this, document.getElementById('archivofCedulaZip'))" >&nbsp;
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
								<td colspan="3"><label for="tipo">* Documento que
										acredite las facultades del funcionario público</label><br /> <input
									type="hidden" id="idarchivofFacultades"
									name="idarchivofFacultades"
									value="<c:out value="${imagenFFacultades}" />"> <input
									type="hidden" id="archivofFacultadesZip"
									name="archivofFacultadesZip" /> <input type="file"
									name="archivofFacultades" id="archivofFacultades"
									accept="document/pdf"
									onchange="validarArchivoFacultades('lupafFacultades', this, document.getElementById('archivofFacultadesZip'), document.getElementById('covers'))">&nbsp;
									<a href="javascript:PreviewImage('archivofFacultades')"> <img
										src="img/lupa.jpg" height="32" width="32"
										style="display: none" id="lupafFacultades" />
								</a>
									<p class="help-block">Un solo archivo de máximo 25MB</p> <%
 	idCLiente = vClienteParam;
 	rutaOrigen = Rutas.rutaCarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirFacultades
 			+ Rutas.separador;
 	rutaDestino = Rutas.rutaDescarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirFacultades
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
 						+ dirRootDes + "/" + Rutas.dirFacultades + "/" + listaArchivos[i].getName()
 						+ "', 'clearcache=yes')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>");
 			} catch (Exception es) {
 				es.printStackTrace();
 			}

 		} //for
 	}
 %></td>
							</tr>

							<!--	
					<tr>

						<td>							
							<select class="browser-default" name="tipoBeneficiario" id="tipoBeneficiario">
								<option value="">* Tipo</option>
								<option value="F">Física</option>
								<option value="M">Moral</option>
								<option value="X">Fideicomiso</option>
								
							</select>
						</td>
						<td>
						</td>
						<td>
							<!-- <button class="btn" >Agregar beneficiario</button>-
                                                        
                                                        <a class="boton_personalizado" id ="btnBene" style="visibility:visible" href="javascript:lanzarBeneficiario()">Agregar Dueño Beneficiario o Beneficiario Controlaldor.</a>
						</td>
					</tr>    -->
							<tr>
								<td colspan="4">
									<!-- COMIENZA LA CONSUTLA SI TIENE BENEFICIARIOS --> <%
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
 		if (b != null && b.length > 0) {

 			out.println("<script>");
 			//out.println("document.getElementById('capturado').value = '1';");
 			out.println("localStorage.setItem('capturado','1');");
 			out.println("</script>");

 			out.println("<h4>Dueño Beneficiarios</h4>");
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
 		} else {
 			out.println("<script>");
 			out.println("localStorage.setItem('capturado','0');");
 			out.println("</script>");
 		} //si hay beneficiario
 	} else { //es primera vez
 		//new DatosBeneficiario().limpiaBeneficiarios(idCliente);                                                    
 		out.println("<script>");
 		out.println("localStorage.setItem('capturado','0');");
 		out.println("</script>");
 	}
 %>
								</td>

							</tr>
							<tr>
								<td>
									<!-- <button class="btn" >Agregar beneficiario</button>--> <%
 	
 %>
								</td>
							</tr>

							<tr>
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
 				servlet = "EdicionRepresentante";
 				out.println("<tr style=\"border-bottom:1px solid #000000\">");
 				out.println("<td>" + b[i].getNroRepresentantes() + "</td>");
 				out.println("<td>" + b[i].getNombreCompleto() + "</td>");
 				//                                                            out.println("<td>" + b[i].getTipoPersonaDesc()+ "</td>");
 				//out.println("<td><a href=\"" + servlet + "?idCliente=" + b[i].getCliente_id() + "&nrorep=" + b[i].getNroRepresentantes() + "&esEdicion=0\" target=\"_blank\" style=\"text-decoration: underline; color:blue;\"><img src=\"img/lupa.jpg\" height=\"32\" width=\"32\" align=\"center\"></a>"); 
 				out.println("<td><a href=\"" + servlet + "?idCliente=" + b[i].getCliente_id() + "&nrorep="
 						+ b[i].getNroRepresentantes() + "&estadoCliente=" + estatusCliente
 						+ "&esEdicion=0\" target=\"_blank\" style=\"text-decoration: underline; color:blue;\"><img src=\"img/lupa.jpg\" height=\"32\" width=\"32\" align=\"center\"></a>");
 				System.out.println("<td><a href=\"" + servlet + "?idCliente=" + b[i].getCliente_id().trim()
 						+ "&nrorep=" + b[i].getNroRepresentantes()
 						+ "&esEdicion=0\" target=\"_blank\" style=\"text-decoration: underline; color:blue;\"><img src=\"img/lupa.jpg\" height=\"32\" width=\"32\" align=\"center\"></a>");
 				out.println("</tr>");
 			}
 			out.println("</table>");

 			out.println("</br>");
 			out.println("</br>");
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
 	out.println("<a class='boton_personalizado' id ='btnRepresentante' style='visibility:visible' href="
 			+ (char) comilla + "javascript:Representante('" + vClienteParam + "')" + (char) comilla
 			+ ">Agrega Representante Legal.</a>");
 	out.println("<a class='' align='left' id ='btnAct2' href=" + (char) comilla
 			+ "javascript:ActualizaTablaRep('" + vClienteParam + "','" + w.consultarWsRepLegal() + "')"
 			+ (char) comilla
 			+ "style='visibility:visible' > <img src='img/actualiza.png' height='32' width='32' align='center'></a>");
 	out.println("<br>");
 	out.println("</br>");
 	out.println("<input type='hidden' id='WsRep' value='" + w.consultarWsRepLegal() + "'>");
 %>
								</td>

							</tr>
							<tr>
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
												<label for="declaroOrigen" class=""> Declaro para
													todos los efectos a que haya lugar, que los recursos
													utilizados para obtener los servicios proporcionados por
													Efectivale son de origen lícito. </label><br>
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
													value="<c:out value="${declarobeneficiario}" />">
												<input type="checkbox" id="declaroBeneficiario"
													name="declaroBeneficiario"> <label
													for="declaroBeneficiario" class="bene"> A través
													del presente declaro que la información y documentación
													provista al amparo de este formato es correcta, verdadera y
													vigente a la fecha en la que se proporcionó y que en caso
													de que la misma cambie materialmente notificaré tan pronto
													sea posible a Efectivale. </label><br>
											</div>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td><a class="boton_personalizado2" id="btnClose"
									style="visibility: hidden" href="login.jsp">Cerrar</a></td>
								<td></td>
								<td>
									<input type="button" onClick="validarRFC()" class="btn btn-danger" name="btnGuardar" id="btn-procesar" value="Guardar"> 
     						
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
		<script src="js/bootstrap.min.js"></script>
		<script src="js/mdb.min.js.descarga"></script>
		<script src="js/validaciones.js"></script>
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
								//                $("#covers").hide();
								txtEstatus = document
										.getElementById('estadoCliente').value;

								if (txtEstatus == 'A') {
									//                    alert("entra validado");
									document.getElementById("btn-procesar").style.visibility = 'hidden';
									document.getElementById('btnRepresentante').style.visibility = 'hidden';
									document.getElementById('btnClose').style.visibility = 'visible';
									//                        document.getElementById('btnBene').style.visibility = 'hidden';
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
											.getElementById('frmGobierno');
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

								//nacionalidadCliente
								var selector = document
										.getElementById('nacionalidadCliente');
								for (var i = 0; i < selector.length; i++) {
									if ('MX' == selector.options[i].value
											.trim()) {
										selector.selectedIndex = i;
										break;
									} else {//if
										selector.selectedIndex = 0;
									}
								}//for 

								//pais
								var selector = document.getElementById('pais');
								for (var i = 0; i < selector.length; i++) {
									if ('MX' == selector.options[i].value
											.trim()) {
										selector.selectedIndex = i;
										break;
									} else {//if
										selector.selectedIndex = 0;
									}
								}//for

								//Asgiando la Activodad (Giro) no aplica predeterminada 
								var selector = document
										.getElementById('actividadObjeto');
								for (var i = 0; i < selector.length; i++) {
									if ('1000000' == selector.options[i].value
											.trim()) {
										selector.selectedIndex = i;
										break;
									} else {//if
										selector.selectedIndex = 0;
									}
								}//for

								esEdicion = document
										.getElementById('esEdicion').value;

								if (esEdicion == 1) { //la carga de la página proviene de una edición del usuario y no de un nuevo usuario
									//ASIGNANDO EL TELÉFONO DEL PAÍS
									txtidTelPais = document
											.getElementById('idTelPais').value;
									if (txtidTelPais != null
											|| txtidTelPais.length > 0) {

										var selector = document
												.getElementById('telPais');
										for (var i = 0; i < selector.length; i++) {
											if (txtidTelPais == selector.options[i].value) {
												selector.selectedIndex = i;
												break;
											} else { //if
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
									//ASIGNANDO ACTIVIDAD U OBJETO SOCIAL 
									txtidActividad = document
											.getElementById('idActividad').value; //llega de la respuesta
									if (txtidActividad != null
											|| txtidActividad.length > 0) {
										var selector = document
												.getElementById('actividadObjeto');

										for (var i = 0; i < selector.length; i++) {
											if (txtidActividad == selector.options[i].value) {
												selector.selectedIndex = i;
												document
														.getElementById('buscarActivodadObjeto').value = selector.options[selector.selectedIndex].text;
												break;
											} else {//if
												selector.selectedIndex = 0;
											}
										}//for 
									}

									//ASIGNANDO PAIS NACIONALIDAD CLIENTE
									txtNacionalidad = document
											.getElementById('idNacionalidadCliente').value; //llega de la respuesta
									if (txtNacionalidad != null
											|| txtNacionalidad.length > 0) {
										var selector = document
												.getElementById('nacionalidadCliente');
										for (var i = 0; i < selector.length; i++) {
											if (txtNacionalidad == selector.options[i].value) {
												selector.selectedIndex = i;
												break;
											} else { //if
												selector.selectedIndex = 0;
											}
										}//for 
									}

									//ASIGNANDO PAIS DOMICILIO
									txtPaisDom = document
											.getElementById('idPais').value; //llega de la respuesta
									if (txtPaisDom != null
											|| txtPaisDom.length > 0) {
										var selector = document
												.getElementById('pais');
										for (var i = 0; i < selector.length; i++) {
											if (txtPaisDom == selector.options[i].value) {
												selector.selectedIndex = i;
												break;
											} else { //if
												selector.selectedIndex = 0;
											}
										}//for 
									}

									//                    }

									txtdeclaroBeneficiario = document
											.getElementById('iddeclaroBeneficiario').value;
									txtdeclaroOrigen = document
											.getElementById('iddeclaroOrigen').value;
									if (txtdeclaroBeneficiario === '1') {
										document
												.getElementById('declaroBeneficiario').checked = true;
									}
									if (txtdeclaroOrigen == '1') {
										document
												.getElementById('declaroOrigen').checked = true;
									}

									//                    //ASIGNANDO ACTIVIDAD U OBJETO SOCIAL 
									//                    txtidGiro = document.getElementById('idGiro').value; //llega de la respuesta
									//                    if(txtidGiro != null || txtidGiro.length > 0){                          
									//                        var selector = document.getElementById('giro');
									//                        
									//                        for ( var i = 0; i<selector.length; i++){
									//                            if ( txtidGiro == selector.options[i].value ) {
									//                                selector.selectedIndex = i; 
									////                                document.getElementById('buscarGiroObjeto').value = selector.options[selector.selectedIndex].text;
									//                                break;
									//                            }else {//if
									//                                selector.selectedIndex = 0;
									//                            }
									//                        }//for 
									//                    }   

								}//ready

							});
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
			function validarFormularioGobiernoS() {
				if (validarFormularioGobierno()) {
					$('.spinner-wrapper').fadeIn('fast');
					Prog();
					return true;
				} else {
					return false;
				}
				//return validarFormularioGobierno();
			}

			function validarFormularioGobierno() {
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
				if (txtTelefono.length !== 0) {
					if (txtTelefono.length !== 10) {
						alert('El Telefono debe ser de 10 caracteres');
						document.getElementById('telefono').focus();
						return false;
					}
				}

				txtnombreCliente = document.getElementById('nombreCliente').value;
				if (txtnombreCliente == null || txtnombreCliente.length == 0
						|| /^\s+$/.test(txtnombreCliente)) {
					alert('La razón social no debe dejarse en blanco')
					document.getElementById('nombreCliente').focus();
					return false;
				}

				txtfechaCreacion = document.getElementById('fechaCreacion').value;
				if (txtfechaCreacion == null || txtfechaCreacion.length == 0
						|| /^\s+$/.test(txtfechaCreacion)) {
					alert('No ha indicado una fecha de creación');
					document.getElementById('fechaCreacion').focus();
					return false;
				}

				//Validacion de la fecha de cración vs RFC del cliente (Gobierno)
				txtfechaCreacion = document.getElementById('fechaCreacion').value;
				txtFechaNacimientoRFC = document.getElementById('rfcCliente').value;
				if (txtfechaCreacion.length > 0
						&& txtFechaNacimientoRFC.length > 0) {

					txtfechaCreacion = getFechaNac(txtfechaCreacion); //normalizando 
					txtFechaNacimientoRFC = getFechaNacRFC('X'
							+ txtFechaNacimientoRFC); //normalizando

					if (txtfechaCreacion != txtFechaNacimientoRFC) {
						alert('La fecha de creación no coincide con la fecha del RFC');
						document.getElementById('fechaCreacion').focus();
						return false;
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
				if (txtcp.length !== 0) {
					if (txtcp.length !== 5) {
						alert('El Código Postal debe ser de 5 caracteres');
						document.getElementById('cp').focus();
						return false;
					}
				}
				txtIDPAIS = document.getElementById('pais').value;
				if (txtIDPAIS == null || txtIDPAIS.length == 0
						|| /^\s+$/.test(txtIDPAIS)) {
					alert('El País no puede dejarse en blanco');
					document.getElementById('pais').focus();
					return false;
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

				txtfechaRL = document.getElementById('fFechaNacimiento').value;
				if (txtfechaRL == null || txtfechaRL.length == 0
						|| /^\s+$/.test(txtfechaRL)) {
					alert('No ha indicado una fecha de nacimiento del Funcionario Público');
					document.getElementById('fFechaNacimiento').focus();
					return false;
				}

				//Validanco la fecha de nacimiento VS Fecha de nacimiento del RFC del representante legal
				txtFechaNacimientoRL = getFechaNac(document
						.getElementById('fFechaNacimiento').value);
				txtFechaNacimientoRFCRL = getFechaNacRFC(document
						.getElementById('fRFC').value);
				if (txtFechaNacimientoRL != '' && txtFechaNacimientoRFCRL != '') {
					if (txtFechaNacimientoRL != txtFechaNacimientoRFCRL) {
						alert('La fecha de nacimiento no coincide con la fecha de nacimiento del RFC del funcionario público ');
						document.getElementById('fFechaNacimiento').focus();
						return false;
					}
				}

				txtRFCRepresentante = document.getElementById('fRFC').value;
				if (txtRFCRepresentante.length !== 0) {
					if (txtRFCRepresentante.length !== 13) {
						alert('El RFC del funcionario público debe ser de 13 caracteres');
						document.getElementById('fRFC').focus();
						return false;
					}
				}

				//Validando longitud del CURP
				txtCURP = document.getElementById('fCURP').value;
				if (txtCURP.length > 0 && txtCURP.length != 18) {
					alert('El CURP del funcionario público debe ser de 18 caracteres');
					document.getElementById('fCURP').focus();
					return false;
				}

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
				//            txtGiro  = document.getElementById('giro').value;
				//            txtidGiro  = document.getElementById('idGiro').value;
				//            if  ( txtidGiro == null || txtidGiro.length ==0 ){
				//                if ( txtGiro == null || txtGiro.length == 0 || /^\s+$/.test(txtGiro)){
				//                    alert('Debe selccionar un Giro');
				//                    document.getElementById('giro').focus();
				//                    return false;
				//                }
				//            }

				txtNumeroId = document.getElementById('fNumeroId').value;
				if (txtNumeroId == null || txtNumeroId.length == 0
						|| /^\s+$/.test(txtNumeroId)) {
					alert('El número de identificación del Funcionario Público no puede dejarse en blanco');
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
				//Validando que una de las dos declaratorias tenga selección.
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

				if (document.getElementById('archivoExistencia').value.length == 0)
					document.getElementById('archivoExistenciaZip').value = '';
				if (document.getElementById('archivoCedula').value.length == 0)
					document.getElementById('archivoCedulaZip').value = '';
				if (document.getElementById('archivoDomicilio').value.length == 0)
					document.getElementById('archivoDomicilioZip').value = '';
				if (document.getElementById('archivofId').value.length == 0)
					document.getElementById('archivofIdZip').value = '';
				if (document.getElementById('archivofFacultades').value.length == 0)
					document.getElementById('archivofFacultadesZip').value = '';

			
				if (validarCapturaBeneficiario() ) {
					return true;
				} else {
					return false;
				}

			} //fin validarformulariogobierno 

			jQuery(function() {
				$("#buscarActivodadObjeto").autocomplete("list.jsp");

			});

			jQuery(function() {
				$("#buscarGiro").autocomplete("listGiro.jsp");

			});
		</script>
		<% 
						String rutarfc = Rutas.wsrfc; 
						
		%>
				<script>
				$(document).ajaxStart(function() {
					 var cubierta = document.getElementById("cover");
						cubierta.style.display = "block";
				      
				    });
				    $(document).ajaxStop(function() {
				    	var cubierta = document.getElementById("cover");
						cubierta.style.display = "none";
				    }); 
				    
	function validarRFC(){
		var txtRFC = document.getElementById('rfcCliente').value;
		var txtbRFC = document.getElementById('fRFC').value;
		
		var obj="";
		var ruta = "<%= rutarfc %>";
		if(ruta != "0"){
		if(txtRFC != null && txtRFC != ""){
			
		$.ajax({
			async:true,
			crossDomain : true,
			type : "GET",
// 			url : urlService,
			url : ruta,
			contentType : "application/json",
			dataType : "text",
			data : {
				rfc : txtRFC
			},
			success : function(data) {
			  obj = JSON.parse(data);
				
				if(obj.respuesta=="RFC Valido"){
					if(txtbRFC != null && txtbRFC != ""){
						
					
					$.ajax({
						async:true,
						crossDomain : true,
						type : "GET",
//			 			url : urlService,
						url : ruta,
						contentType : "application/json",
						dataType : "text",
						data : {
							rfc : txtbRFC
						},
						success : function(data) {
						  obj = JSON.parse(data);
							
							if(obj.respuesta=="RFC Valido"){
								$('#frmGobierno').submit();
						    }else{ 
						    	alert("El RFC ingresado no se encuentra en la lista de RFC no cancelados");
						    	document.getElementById('fRFC').focus();
						    }
						
							
						},
						error : function(xhr, var1, var2) {
							console.log("errorcito");
							alert("No se a podido validar RFC, time out!,  ruta wsrfc:"+ruta+"");
							}
					});
					
			    }
					
				}
					else{
			    	alert("El RFC ingresado no se encuentra en la lista de RFC no cancelados");

			    	document.getElementById('rfcCliente').focus();
			    }
				
			},
			error : function(xhr, var1, var2) {
				console.log("errorcito");
				alert("No se a podido validar RFC time out! Vuelva a intentarlo!");
			}
		});
		
		}else{
			$('#frmGobierno').submit();
		}
		}else{
			$('#frmGobierno').submit();
		}
		
	
	}
	
	</script>

		<script>
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
									str += "<td>"
											+ obj.Representantes[i].nrorep
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

			function validarActividadEco() {
				//Asigna al select el valor lozalizado en la lista de actividades escrita

				txtTextoActividad = document
						.getElementById('buscarActivodadObjeto').value;
				var selector = document.getElementById('actividadObjeto');
				//alert ( selector.options[2].text );
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

			function Representante(vIdCliente) {
				estadoCliente = document.getElementById('estadoCliente').value;
				esEdicion = document.getElementById('esEdicion').value;

				window.open('alta_representante.jsp?esEdicion='
						+ document.getElementById('esEdicion').value
						+ '&estadoCliente='
						+ document.getElementById('estadoCliente').value
						+ '&idCliente=' + vIdCliente, '_blank');
			}
		</script>

		<!--        <script>
        function validarGiro(){
      //Asigna al select el valor lozalizado en la lista de actividades escrita
      
       txtGiro = document.getElementById('buscarGiro').value;
       var selector = document.getElementById('giro');
       if ( txtGiro != null || txtGiro.length > 0 ){
            for ( var i = 0; i<selector.length; i++){
                    if ( txtGiro == selector.options[i].text ) {
                        selector.selectedIndex = i;
                        break;
                    }else {//if
                        selector.selectedIndex = 0;
                    }
            }//for
        }//fin del if
        }//fin funcion validarGiro
        </script>-->

		<script>
			function validarCapturaBeneficiario() {

				txtdeclaroBeneficiario = document
						.getElementById('declaroBeneficiario').checked;
				txtdeclaroOrigen = document.getElementById('declaroOrigen').checked;
				txtCapturado = localStorage.getItem('capturado');

				return true;
			}
		</script>
</body>
</html>