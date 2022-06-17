<%@page import="datosRatos.ConsultaWS"%>
<%@page import="entidad.Representante"%>
<%@page import="entidad.RepLegal"%>
<%@page import="datosRatos.DatosRepLegal"%>
<%@page import="datosRatos.DatosClienteRaro"%>
<%@page import="entidad.Cliente"%>
<%@page import="utilidades.Archivos"%>
<%@page import="utilidades.Rutas"%>
<%@page import="entidad.Beneficiario"%>
<%@page import="datosRatos.DatosBeneficiario"%>
<%@page import="java.io.File"%>
<%@page import="utilidades.Mensajes"%>
<%@page import="utilidades.PerfilUsuario"%>
<%@include file="valida_login.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="entidad.Giro"%>
<%@page import="datosRatos.DatosGiro"%>
<%@page import="entidad.Actividad"%>
<%@page import="entidad.TipoIdentificacion"%>
<%@page import="entidad.Pais"%>
<%@page import="datosRatos.DatosActividad"%>
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
<link href="./css/spinner.css" rel="stylesheet">
<link href="./css/jquery-ui.css" rel="stylesheet">

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
						Datos y Documentos del Cliente Persona Moral,<small>
							Consulado, Embajada u Organismo Internacional.</small>
					</h2>
				</article>
				<aside class="col-xs-12 col-sm-4 col-md-3 col-lg-3">
					<p class="lead">
						Bienvenido:
						<%@include file="detectausuario.jsp"%>
						<!--  Bienvenido:  out.println( vClienteParam );     -->

					</p>
				</aside>
			</div>
			<div class="row">
				<div class="col-md-9 table-responsive">
					<!--  -->
					<form onsubmit="return validarFomularioMoralS()"
						action="registroPersonaMoral" method="post" id="frmPersonaMoral"
						name="frmPersonaMoral" enctype="multipart/form-data">
						<%
							//Llamamos a los paï¿½ses desde la base de datos
							ArrayList listaPaises = new DatosPais().getList();
							ArrayList listaTipoIdentificacion = new DatosTipoIdentifiacion().getList();
							//ArrayList listaActivida = new DatosActividad().getList();
						%>

						<input type="hidden" id="tipoPersona" name="tipoPersona" value="M">
						<input type="hidden" id="Cliente_Id" name="Cliente_Id"
							value="<%out.print(vClienteParam);%>"> <input
							id="esEdicion" name="esEdicion" type="hidden"
							value="<c:out value="${esEdicion}" />"> <input
							id="estadoCliente" name="estadoCliente" type="hidden"
							value="<c:out value='${estado}' />">

						<table border="0" style="overflow: hidden;">
							<tr>
								<td colspan="3">
									<p class="text-justify">
										Con el fin de cumplir con las obligaciones de Identificación,
										Verificación y envío de Avisos que marca la "Ley Federal para
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
								<td><label>* Tel país</label> <input type="hidden"
									id="idtelPais" name="idTelPais"
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
											id="razonSocial" name="razonSocial" maxlength="120" size="80"
											value="<c:out value="${razonsocial}" />"> <label
											for="razonSocial">* Denominación o Razón social</label>
									</div>
								</td>
							</tr>
							<tr>
								<td><input class="date" type="date" id="fechaConstitucion"
									name="fechaConstitucion"
									value="<c:out value="${fechaconstitucion}" />"> <label
									for="fechaConstitucion">* Fecha de Constitución</label></td>
								<td><img src="img/calendar.png" height="32" width="32" />
								</td>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="RFCEmpresa" name="RFCEmpresa" maxlength="13"
											value="<%out.print(vRFCParam);%>" readonly="readonly">
										<label for="RFCEmpresa">* RFC </label>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="3"><br> <label>* Giro</label>
									<div class="md-form form-sm">

										<input type="hidden" id="idGiro" name="idGiro"
											value="<c:out value="${giro_id}" />"> <select
											class="browser-default" name="giro" id="giro"
											style="width: 96.5%;">
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
								<td colspan="0" style="display: none;">
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
									id="idPaisEmpresa" name="idPaisEmpresa"
									value="<c:out value="${paisEmpresa}" />"> <select
									class="browser-default" name="paisEmpresa" id="paisEmpresa"
									style="width: 90%;">
										<option value="">* Pais</option>
										<%
											//Verificamos que tengamos paï¿½ses depuï¿½s de la consulta a la base de datos
											if (listaPaises != null) {
												for (int i = 0; i < listaPaises.size(); i++) {
													Pais p = (Pais) listaPaises.get(i);
													out.println("<option value=\"" + p.getPais() + "\">" + p.getDescrpcion() + "</option>");
												}
											}
										%>
								</select><br></td>
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
									type="file" name="archivoActa" id="archivoActa"
									accept="document/pdf"
									onchange="validarArchivoActaConst('lupaActa', this, document.getElementById('archivoActaZip'), document.getElementById('covers'))">&nbsp;
									<a href="javascript:PreviewImage('archivoActa')"> <img
										src="img/lupa.jpg" height="32" width="32"
										style="display: none" id="lupaActa" />
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
								<td colspan="3"><label for="tipo">* Cédula fiscal</label><br />
									<input type="hidden" id="idarchivoCedula"
									name="idarchivoCedula"
									value="<c:out value="${imagencedulafiscal}" />"> <input
									type="hidden" id="archivoCedulaZip" name="archivoCedulaZip" />
									<input type="file" name="archivoCedulaEmpresa"
									id="archivoCedulaEmpresa" accept="document/pdf"
									onchange="validarArchivoCedulaFiscal('lupaCedula', this, document.getElementById('archivoCedulaZip'), document.getElementById('covers'))">&nbsp;
									<a href="javascript:PreviewImage('archivoCedulaEmpresa')">
										<img src="img/lupa.jpg" height="32" width="32"
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
 		out.println(
 				"<INPUT type='hidden' id='HayArchivoCedulaEmpresa' name='HayArchivoCedulaEmpresa' value='si'");
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
 		out.println(
 				"<INPUT type='hidden' id='HayArchivoCedulaEmpresa' name='HayArchivoCedulaEmpresa' value='no'");
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
											for="exterior">* No Exterior</label>
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
											for="colonia">* Colonia</label>
									</div>
								</td>
								<td></td>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" id="cp" name="cp"
											onkeypress="return esNumero(this, event)" maxlength="5"
											value="<c:out value="${codpostal}" />"> <label
											for="cp">* Código Postal</label>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="estado" name="estado" maxlength="100"
											value="<c:out value="${estado_prov}" />"> <label
											for="estado">* Estado</label>
									</div>
								</td>
								<td></td>
								<td><label>* Pais </label> <input type="hidden"
									id="idPaisDomicilio" name="idPaisDomicilio"
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
									<br /> <input type="hidden" id="idarchivoDomicilio"
									name="idarchivoDomicilio"
									value="<c:out value="${imagencomprobantedom}" />"> <input
									type="hidden" id="archivoDomicilioZip"
									name="archivoDomicilioZip" /> <input type="file"
									name="archivoDomicilio" id="archivoDomicilio"
									accept="document/pdf"
									onchange="if(!this.value.length)return false; validarArchivoComprobanteDomicilio('lupaDomicilio', this, document.getElementById('archivoDomicilioZip'), document.getElementById('covers'))">&nbsp;

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
 				//out.println("<p>" + listaArchivos[i].getName() + " <a href=\"/" + dirRootDes + "/" + idCLiente + Rutas.separador + Rutas.dirComprobanteDom + Rutas.separador + listaArchivos[i].getName() + "\" target=\"_blank\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>" );
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
							</tr>
							<tr>
								<td colspan="3">
									<h4>Datos del representante legal</h4>
								</td>
							</tr>
							<tr>
								<td colspan="3">
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="nombres" name="nombres" maxlength="200" size="80"
											value="<c:out value="${rlnombre}" />"> <label
											for="nombres">* Nombre(s)</label>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="paterno" name="paterno" maxlength="200"
											value="<c:out value="${rlapellidopaterno}" />"> <label
											for="paterno">* Apellido paterno</label>
									</div>
								</td>
								<td></td>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="materno" name="materno" maxlength="200"
											value="<c:out value="${rlapellidomaterno}" />"> <label
											for="materno">* Apellido materno</label>
									</div>
								</td>
							</tr>
							<tr>
								<td><input class="date" type="date" id="fechaNacimiento"
									name="fechaNacimiento"
									value="<c:out value="${rlfechanacimiento}" />"> <br>
									<label>* Fecha de nacimiento</label></td>
								<td><img src="img/calendar.png" height="32" width="32" />
								</td>
								<td></td>
							</tr>
							<tr>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="RFCRepreentante" name="RFCRepreentante" maxlength="13"
											value="<c:out value="${rlrfc}" />"> <label
											for="RFCRepreentante">* RFC</label>
									</div>
								</td>
								<td></td>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="CURP" name="CURP" maxlength="18"
											value="<c:out value="${rlcurp}" />"> <label
											for="CURP">* CURP</label>
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
											id="numeroId" name="numeroId" maxlength="40"
											value="<c:out value="${rlnumeroid}" />"> <label
											for="numeroId">* No de ID</label>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="otroTipoId" name="otroTipoId" maxlength="200"
											onkeyup="hayOtraIdentificacion()"
											value="<c:out value="${rlidentificaciontipo}" />"> <label
											for="otroTipoId">* Otro tipo de ID</label>
									</div>
								</td>
								<td></td>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="autoridadEmite" name="autoridadEmite" maxlength="200"
											value="<c:out value="${rlautoridademiteid}" />"> <label
											for="autoridadEmite">* Entidad que emite ID</label>
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
									for="rlfechaNotarial">* Fecha de Poder</label><br>
								<br></td>
									
									</tr>
								<tr >	
								
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="rlnotaria" name="rlnotaria" maxlength="100"
											value="<c:out value="${rlnotaria}" />"> <label
											for="notaria">*Notaria</label>
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
									name="archivoIDRepresentante" id="archivoIDRepresentante"
									accept="document/pdf"
									onchange=" validarArchivoId('lupaIdRepresentante', this, document.getElementById('archivoRlIdentificacionZip'), document.getElementById('covers'))">&nbsp;
									<a href="javascript:PreviewImage('archivoIDRepresentante')">
										<img src="img/lupa.jpg" height="32" width="32"
										style="display: none" id="lupaIdRepresentante" />
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
 		out.println(
 				"<INPUT type='hidden' id='HayArchivoIDRepresentante' name='HayArchivoIDRepresentante' value='si'");
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
 		out.println(
 				"<INPUT type='hidden' id='HayArchivoIDRepresentante' name='HayArchivoIDRepresentante' value='no'");
 	}
 %></td>
							</tr>
							<!--					<tr>
						<td colspan="3">
                                                        <label for="tipo">* Carga de cédula fiscal</label><br/>		
                                                        <input type="hidden" id="idarchivoCedulaRepresentante" name="idarchivoCedulaRepresentante" value="<c:out value="${imagenrlcedulafiscal}" />">
                                                        <input type="hidden" id="archivoCedulaRepresentanteZip" name="archivoCedulaRepresentanteZip" />
                                                        <input type="file" name="archivoCedulaRepresentante" id="archivoCedulaRepresentante" accept="document/pdf" onchange="validarArchivoCedulaFiscal('lupaCedulaRepresentante', this, document.getElementById('archivoCedulaRepresentanteZip'))" >&nbsp;
                                                        <a href="javascript:PreviewImage('archivoCedulaRepresentante')">
                                                            <img src="img/lupa.jpg" height="32" width="32" style="display:none" id="lupaCedulaRepresentante" />
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
				out.println(
						"<INPUT type='hidden' id='HayArchivoCedulaRepresentante' name='HayArchivoCedulaRepresentante' value='si'");
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

			} else {
				out.println(
						"<INPUT type='hidden' id='HayArchivoCedulaRepresentante' name='HayArchivoCedulaRepresentante    ' value='no'");
			}%>	
						</td>
					</tr>	-->
							<tr>
								<td colspan="3"><label for="tipo">* Poder notarial
								</label><br /> <input type="hidden" id="idarchivoPoderNotarial"
									name="idarchivoPoderNotarial"
									value="<c:out value="${imagenpodernotarial}" />"> <input
									type="hidden" id="archivoPoderNotarialZip"
									name="archivoPoderNotarialZip" /> <input type="file"
									name="archivoPoderNotarial" id="archivoPoderNotarial"
									accept="document/pdf"
									onchange="validarArchivoPoderNotarial('lupaPoderNotarial', this, document.getElementById('archivoPoderNotarialZip'), document.getElementById('covers'))">&nbsp;
									<a href="javascript:PreviewImage('archivoPoderNotarial')">
										<img src="img/lupa.jpg" height="32" width="32"
										style="display: none" id="lupaPoderNotarial" />
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
 				"<INPUT type='hidden' id='HayArchivoPoderNotarial' name='HayArchivoPoderNotarial' value='si'");
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
 				//new Archivos().copyFile(listaArchivos[i].getAbsolutePath(), archivovalidarArchivoActaConst.getAbsolutePath());
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
 				"<INPUT type='hidden' id='HayArchivoPoderNotarial' name='HayArchivoPoderNotarial' value='no'");
 	}
 %></td>
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
 		System.out.println("Excepción durante la carga de la tabla de Representante Legal");
 	}

 	String idCliente = "";
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
 			out.println("<br/>");
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
									<h4>Información del Dueño Beneficiario o Beneficiario
										Controlador y Origen de los Recursos.</h4>
								</td>
							</tr>
							<tr>

								<td>Descarga Declaratoria Dueño Beneficiario<br /> <a
									align="center"
									href="https://www.efectivale.com.mx/aml/DECLARATORIAS/Declaracion_Cliente_Persona_Moral_v04.pdf"
									target="_blank"> <img src="img/descargar.png" widht="32"
										height="32" /></a>
								</td>


								<td colspan="3"><label for="tipo">* Declaración
										Firmada</label><br /> <input type="hidden" id="idarchivoDeclaratoria"
									name="idarchivoDeclaratoria"
									value="<c:out value="${imagendeclaratoria}" />"> <input
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
							<!--		<tr>
						<td colspan="3">
                                                <div class="row">
                                                <div class="col-12">
                                                  <div class="md-form form-sm">
                                                      <input type="checkbox" id="nobeneficiario" name="nobeneficiario" >
                                                      <label for="nobeneficiario" class="bene"  >
                                                        Declaro que soy el único beneficiario de los servicios proporcionados por Efectivale
                                                      </label><br>
                                                  </div>
                                                </div>
                                                </div>
						</td>
					</tr>	-->

							<tr>
								<td colspan="3">
									<div class="md-form form-sm">
										<input type="hidden" id="capturado" name="capturado" value="0">
										<input type="hidden" id="idDeclaroBeneficiario"
											name="NoBeneficiario"
											value="<c:out value="${declarobeneficiario}" />"> <input
											type="hidden" id="capturado" name="capturado" value="0">
										<input type="hidden" id="checado" name="checado" value="0">
										<input type="checkbox" id="sibeneficiario"
											name="sibeneficiario" class=""> <label
											for="sibeneficiario" class="bene"> Tengo conocimiento
											del Dueño Beneficiario o Beneficiario Controlador </label><br>
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
										//
									%> <br>
								<br>
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<!-- COMIENZA LA CONSUTLA SI TIENE BENEFICIARIOS --> <%
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
 		System.out.println("Excepción durante la carga de la tabla de bebeficiarios");
 	}

 	idCliente = "";
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
 	out.println("<a class='boton_personalizado' id ='btnBene' style='visibility:visible' href="
 			+ (char) comillas + "javascript:lanzarBeneficiario('" + vClienteParam + "')" + (char) comillas
 			+ ">Agregar Dueño Beneficiario o Beneficiario Controlaldor.</a>");
 	out.println("<a class='' align='left' id ='btnAct' href=" + (char) comillas
 			+ "javascript:ActualizaTablaBene('" + vClienteParam + "','" + w.consultarWsBene() + "')"
 			+ (char) comillas
 			+ "style='visibility:visible' > <img src='img/actualiza.png' height='32' width='32' align='center'></a>");
 	out.println("<input type='hidden' id='WsBene' value='" + w.consultarWsBene() + "'>");
 %>
								</td>

							</tr>

							<!--	<tr>
						<td colspan="3">
							<h4>Datos del origen de los recursos</h4>
						</td>
					</tr>	-->
							<tr>
								<td colspan="3">
									<div class="row">
										<div class="col-12">
											<!--      <div class="md-form form-sm">
                                                      <input type="hidden" id="idDeclaroOrigen" name="idDeclaroOrigen" value="<c:out value="${declaroorigen}" />" >
                                                      <input type="checkbox" id="declaroOrigen" name="declaroOrigen" >
                                                      <label for="declaroOrigen" class="">
                                                        Declaro para todos los efectos a que haya lugar, que los recursos utilizados para obtener los servicios proporcionados por EFECTIVALE son propios y de procedencia lícita
                                                      </label><br>
                                                  </div> -->
										</div>
									</div>
								</td>
							</tr>
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
				Overlay TextOverlay
				<iframe id="frame" frameborder="" scrolling="no" onclick="off()"></iframe>
			</div>
		</div>

		<script>
			$(document)
					.ready(
							function() {
								//       alert("Entro");
								$("#cover").hide();
								//  $("#covers").hide();
								txtEstatus = document
										.getElementById('estadoCliente').value;

								//                alert("Entro2");
								if (txtEstatus == 'A') {

									document.getElementById("btn-procesar").style.visibility = 'hidden';
									document.getElementById('btnClose').style.visibility = 'visible';
									document.getElementById('btnBene').style.visibility = 'hidden';
									document.getElementById('btnRepresentante').style.visibility = 'hidden';
									//                        document.getElementById("telPais").disabled = true;
									//                        document.getElementById("fechaConstitucion").disabled = true;
									//                        document.getElementById("giro").disabled = true;
									//                        document.getElementById("paisEmpresa").disñabled = true;
									//                        document.getElementById("archivoActa").disabled = true;
									//                        document.getElementById("archivoCedulaEmpresa").disabled = true;
									//                        document.getElementById("paisDomicilio").disabled = true;
									//                        document.getElementById("archivoDomicilio").disabled = true;
									//                        document.getElementById("fechaNacimiento").disabled = true;
									//                        document.getElementById("tipoIdentificacion").disabled = true;
									//                        document.getElementById("archivoIDRepresentante").disabled = true;
									//                        document.getElementById("archivoCedulaRepresentante").disabled = true;
									//                        document.getElementById("archivoPoderNotarial").disabled = true;
									//                        document.getElementById("nobeneficiario").disabled = true;
									//                        document.getElementById("sibeneficiario").disabled = true;
									//                        document.getElementById("tipoBeneficiario").disabled = true;
									//                        document.getElementById("declaroOrigen").disabled = true;
									//                        document.getElementById("fechaBloqueo").disabled = true;
									var form = document
											.getElementById('frmPersonaMoral');

									var elements = form.elements;
									for (var i = 0, len = elements.length; i < len; ++i) {
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

								//paisEmpresa
								var selector = document
										.getElementById('paisEmpresa');
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

								//ASIGNANDO GIRO NO APLICA COMO PREDETERMINADO
								var selector = document.getElementById('giro');
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
								estadoCliente = '${estado}';

								if (esEdicion == 1) { //la carga de la página proviene de una edición del usuario y no de un nuevo usuario
									/*
									txtActa = document.getElementById('idarchivoActa').value;
									document.getElementById('archivoActa').value = txtActa;
									 */
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

									//ASIGNANDO EL PAÍS TELÉFONO

									txtTelPais = document
											.getElementById('idtelPais').value; //llega de la respuesta

									if (txtTelPais != null
											|| txtTelPais.length > 0) {

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
									//alert("entroGIRO");
									//ASIGNANDO GIRO

									txtGiroId = document
											.getElementById('idGiro').value; //llega de la respuesta

									if (txtGiroId != null
											|| txtGiroId.length > 0) {

										var selector = document
												.getElementById('giro');
										for (var i = 0; i < selector.length; i++) {
											if (txtGiroId == selector.options[i].value) {
												selector.selectedIndex = i;
												document
														.getElementById('buscarGiro').value = selector.options[selector.selectedIndex].text;
												break;
											} else {//if
												selector.selectedIndex = 0;
											}
										}//for 
									}

									//ASIGNANDO PAÍS EMPRESA
									txtPaisEmpresa = document
											.getElementById('idPaisEmpresa').value; //llega de la respuesta
									if (txtPaisEmpresa != null
											|| txtPaisEmpresa.length > 0) {
										var selector = document
												.getElementById('paisEmpresa');
										for (var i = 0; i < selector.length; i++) {
											if (txtPaisEmpresa == selector.options[i].value) {
												selector.selectedIndex = i;
												break;
											} else {//if
												selector.selectedIndex = 0;
											}
										}//for 
									}

									//ASIGNANDO PAÍS DOMICILIO
									txtPaisDomicilio = document
											.getElementById('idPaisDomicilio').value; //llega de la respuesta
									if (txtPaisDomicilio != null
											|| txtPaisDomicilio.length > 0) {
										var selector = document
												.getElementById('paisDomicilio');
										for (var i = 0; i < selector.length; i++) {
											if (txtPaisDomicilio == selector.options[i].value) {
												selector.selectedIndex = i;
												break;
											} else {//if
												selector.selectedIndex = 0;
											}
										}//for 
									}

									//Mostrando los check de las declaraciones       
									chkSiBeneficiario = document
											.getElementById('idDeclaroBeneficiario').value;
									document.getElementById('sibeneficiario').checked = false;
									if (chkSiBeneficiario == "1") {
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

								}//es edicion
							}); //fin ready
		</script>
		<script>
			function validarFomularioMoralS() {
				if (validarFomularioMoral()) {
					//  alert("BarraProg");
					$('.spinner-wrapper').fadeIn('fast');
					Prog();
					return true;
				} else {
					return false;
				}
			}

			function validarFomularioMoral() {
				//          alert("VALIDAformulario");
				txtTelPais = document.getElementById('telPais').value;
				if (txtTelPais == null || txtTelPais.length == 0
						|| /^\s+$/.test(txtTelPais)) {
					alert('Debe seleccionar la clave de teléfono de país');
					document.getElementById('telPais').focus();
					return false;
				}

				//Que el telefono no este vacio

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
				//        alert("1");

				txtrazonSocial = document.getElementById('razonSocial').value;
				if (txtrazonSocial == null || txtrazonSocial.length == 0
						|| /^\s+$/.test(txtrazonSocial)) {
					alert('La razón social no debe dejarse en blanco')
					document.getElementById('razonSocial').focus();
					return false;
				}

				txtfechaConstitucion = document
						.getElementById('fechaConstitucion').value;
				if (txtfechaConstitucion == null
						|| txtfechaConstitucion.length == 0
						|| /^\s+$/.test(txtfechaConstitucion)) {
					alert('Debe indicar una fecha de constitución');
					document.getElementById('fechaConstitucion').focus();
					return false;
				}
				//    alert("2");
				txtGiro = document.getElementById('giro').value;
				if (txtGiro == null || txtGiro.length == 0
						|| /^\s+$/.test(txtGiro)) {
					alert('Debe seleccionar un giro');
					document.getElementById('giro').focus();
					return false;
				}

				txtCurp = document.getElementById('CURP').value;
				if (txtCurp.length !== 0) {

					if (txtCurp.length !== 18) {
						alert('El CURP debe ser de 18 caracteres');
						document.getElementById('CURP').focus();
						return false;
					}
				}
				//    alert("3");

				txtfechaConstitucion = getFechaNac(txtfechaConstitucion);
				txtRFCEmpresa = document.getElementById('RFCEmpresa').value;
				txtFechaRFC = getFechaNacRFC('X' + txtRFCEmpresa);
				if (txtfechaConstitucion != ''
						&& txtfechaConstitucion.length > 0) {
					if (txtfechaConstitucion != txtFechaRFC) {
						alert('La fecha de constitución no coincide con la fecha del RFC');
						document.getElementById('RFCEmpresa').focus();
						return false;
					}
				}
				//    alert("4");
				txtpaisEmpresa = document.getElementById('paisEmpresa').value;
				if (txtpaisEmpresa == null || txtpaisEmpresa.length == 0
						|| /^\s+$/.test(txtpaisEmpresa)) {
					alert('Debe seleccionar un país para la empresa');
					document.getElementById('paisEmpresa').focus();
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
				
				
				txtCalle = document.getElementById('calle').value;
				if (txtCalle == null || txtCalle.length == 0
						|| /^\s+$/.test(txtCalle)) {
					alert('La calle no puede dejarse en blanco');
					document.getElementById('calle').focus();
					return false;
				}

				//     alert("5");        
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
				//   alert("6");
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

				txtpaisDomicilio = document.getElementById('paisDomicilio').value;
				if (txtpaisDomicilio == null || txtpaisDomicilio.length == 0
						|| /^\s+$/.test(txtpaisDomicilio)) {
					alert('El páis del domicilio no puede dejarse en blanco');
					document.getElementById('paisDomicilio').focus();
					return false;
				}

				//             txtestado = document.getElementById('estado').value;
				//            if ( txtestado == null || txtestado.length == 0 || /^\s+$/.test(txtestado)){
				//                    alert('El Estado no debe dejarse en blanco')
				//                    document.getElementById('estado').focus();
				//                    return false;
				//            }
				//      alert("7");         
				txtNombres = document.getElementById('nombres').value;
				if (txtNombres == null || txtNombres.length == 0
						|| /^\s+$/.test(txtNombres)) {
					alert('El nombre no debe dejarse en blanco')
					document.getElementById('nombres').focus();
					return false;
				}

				txtPaterno = document.getElementById('paterno').value;
				if (txtPaterno == null || txtPaterno.length == 0
						|| /^\s+$/.test(txtPaterno)) {
					alert('El apellido paterno no debe dejarse en blanco')
					document.getElementById('paterno').focus();
					return false;
				}

				txtMaterno = document.getElementById('materno').value;
				if (txtMaterno == null || txtMaterno.length == 0
						|| /^\s+$/.test(txtMaterno)) {
					alert('El apellido materno no debe dejarse en blanco')
					document.getElementById('materno').focus();
					return false;
				}

				//    alert("8");
				txtFechaNacimiento = document.getElementById('fechaNacimiento').value;
				if (txtFechaNacimiento == null
						|| txtFechaNacimiento.length == 0
						|| /^\s+$/.test(txtFechaNacimiento)) {
					alert('No ha indicado una fecha de nacimiento del representante legal ');
					document.getElementById('fechaNacimiento').focus();
					return false;
				}

				txtRFCRepresentante = document
						.getElementById('RFCRepreentante').value;
				if (txtRFCRepresentante.length !== 0) {
					if (txtRFCRepresentante.length !== 13) {
						alert('El RFC del representante legal debe ser de 13 caracteres');
						document.getElementById('RFCRepreentante').focus();
						return false;
					}
				}

				txtFechaNacimiento = getFechaNac(txtFechaNacimiento); //normalizando 
				b = document.getElementById('RFCRepreentante').value;
				txtFechaNacimientoRFC = '';
				if (b.length > 0 && b != '') {
					txtFechaNacimientoRFC = getFechaNacRFC(document
							.getElementById('RFCRepreentante').value);
				}

				//    alert("9");
				if (txtFechaNacimiento != null && txtFechaNacimiento.length > 0
						&& txtFechaNacimientoRFC != null
						&& txtFechaNacimientoRFC.length > 0) {
					if (txtFechaNacimiento != txtFechaNacimientoRFC) {
						alert('La fecha de nacimiento no coincide con la fecha de nacimiento del RFC');
						document.getElementById('RFCRepreentante').focus();
						return false;
					}
				}

				txtCurp = document.getElementById('CURP').value;
				if (txtCurp.length !== 0) {

					if (txtCurp.length !== 18) {
						alert('El CURP debe ser de 18 caracteres');
						document.getElementById('CURP').focus();
						return false;
					}
				}

				//    alert("10");
				//ARCHIVO ID REPRESENTANTE

				txtarchivoIDRepresentante = document
						.getElementById('archivoIDRepresentante').value;
				if (txtarchivoIDRepresentante.length > 0) {

					if (!comprueba_extension(txtarchivoIDRepresentante)) {
						alert('El archivo de Id Representante Legal debe ser con la extensión .pdf o .jpg');
						document.getElementById('archivoIDRepresentante')
								.focus();
						return false;
					}

					input = document.getElementById('archivoIDRepresentante');
					if (!validarTamanoArchivo(input, 10)) {
						alert('El peso del archivo de Id Representant Legal excede los 10 MB que tiene como límite nuestro sistema');
						document.getElementById('archivoIDRepresentante')
								.focus();
						input = null;
						return false;
					}
				}
				// Archivo Declaratoria
				txtarchivoDeclaratoria = document
						.getElementById('archivoDeclaratoria').value;
				if (txtarchivoDeclaratoria.length > 0) {

					if (!comprueba_extension(txtarchivoDeclaratoria)) {
						alert('El archivo Declaratoria Firmada debe ser con la extensión .pdf o .jpg');
						document.getElementById('archivoDeclaratoria').focus();
						return false;
					}
					//    alert("11");

					input = document.getElementById('archivoDeclaratoria');
					if (!validarTamanoArchivo(input, 10)) {
						alert('El peso del archivo Declaratoria Firmada excede los 10 MB que tiene como límite nuestro sistema');
						document.getElementById('archivoDeclaratoria').focus();
						input = null;
						return false;
					}
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

				//alert("12");
				txtNumeroId = document.getElementById('numeroId').value;
				if (txtNumeroId == null || txtNumeroId.length == 0
						|| /^\s+$/.test(txtNumeroId)) {
					alert('El número de identificación del Representante Legal no puede dejarse en blanco');
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
				
				
				// alert("12.1");
				//Validando que una de las dos declaratorias tenga selección.
				//chkNoBeneficiario = document.getElementById('nobeneficiario').checked;
				chkSiBeneficiario = document.getElementById('sibeneficiario').checked;

				if (document.getElementById('archivoActa').value.length == 0)
					document.getElementById('archivoActaZip').value = '';
				if (document.getElementById('archivoCedulaEmpresa').value.length == 0)
					document.getElementById('archivoCedulaZip').value = '';
				if (document.getElementById('archivoDomicilio').value.length == 0)
					document.getElementById('archivoDomicilioZip').value = '';
				if (document.getElementById('archivoIDRepresentante').value.length == 0)
					document.getElementById('archivoRlIdentificacionZip').value = '';
				if (document.getElementById('archivoPoderNotarial').value.length == 0)
					document.getElementById('archivoPoderNotarialZip').value = '';
				if (document.getElementById('archivoDeclaratoria').value.length == 0)
					document.getElementById('archivoDeclaratoriaZip').value = '';

				document.getElementById('btn-procesar').disabled=true;
				alert("Validando RFC y CURP");
	               
				if (validarCapturaBeneficiario() && validarRFC() && validarRFC2() && validarCURP()) {
					
					document.getElementById('btn-procesar').disabled=false;
					return true;
				} else {
					document.getElementById('btn-procesar').disabled=false;
					return false;
				}

				
			}
			//fin de la funcion validar formuario
		</script>
		<script>
	function validarRFC(){
		var txtbRFC = document.getElementById('RFCEmpresa').value;

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
			    	document.getElementById('RFCEmpresa').focus();
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
		var txtbRFC = document.getElementById('RFCRepreentante').value;

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
			    	document.getElementById('RFCRepreentante').focus();
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
		
		var txtCurp = document.getElementById('CURP').value;

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
			    	document.getElementById('CURP').focus();
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
			function validarCapturaBeneficiario() {

				txtSiBeneficiario = document.getElementById('sibeneficiario').checked;
				txtCapturado = localStorage.getItem('capturado');
				if (txtSiBeneficiario) {
					//return true;
					if (txtCapturado == '0') {
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

			function Representante(vIdCliente) {
				estadoCliente = document.getElementById('estadoCliente').value;
				esEdicion = document.getElementById('esEdicion').value;

				window.open('alta_representante.jsp?esEdicion='
						+ document.getElementById('esEdicion').value
						+ '&estadoCliente='
						+ document.getElementById('estadoCliente').value
						+ '&idCliente=' + vIdCliente, '_blank');
			}

			function lanzarBeneficiario(vIdCliente) {
				estadoCliente = document.getElementById('estadoCliente').value;
				esEdicion = document.getElementById('esEdicion').value;
				//alert(esEdicion);
				txtSiBeneficiario = document.getElementById('sibeneficiario').checked;
				if (txtSiBeneficiario) {
					e = document.getElementById('tipoBeneficiario');
					sel = e.options[e.selectedIndex].value;

					switch (sel) {
					case 'F':
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
						//document.getElementById('capturado').value = '1';
						break;
					case 'M':
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
						//document.getElementById('capturado').value = '1';
						break;
					case 'X':
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
						//document.getElementById('capturado').value = '1';
						break;
					default:
						alert('Debe seleccionar el tipo de beneficiario');
					}//SWITCH

				} else {

					alert('Para agregar un beneficiario debe declarar que conoce la información del mismo');
				}
			}//lanzabeneficiario

			jQuery(function() {
				$("#buscarGiro").autocomplete("listGiro.jsp");

			}); //buscar el giro

			function validarGiro() {
				alert("entraGIRO");
				//Asigna al select el valor lozalizado en la lista de actividades escrita

				txtbuscarGiro = document.getElementById('buscarGiro').value;
				var selector = document.getElementById('giro');
				//alert ( selector.options[2].text );
				if (txtbuscarGiro != null || txtbuscarGiro.length > 0) {
					for (var i = 0; i < selector.length; i++) {
						if (txtbuscarGiro == selector.options[i].text) {
							selector.selectedIndex = i;
							break;
						} else {//if
							selector.selectedIndex = 0;
						}
					}//for
				}//fin del for
			}//fin funcion validarActivdadEco

			function hayOtraIdentificacion() {
				txtOtraIdentificacion = document.getElementById('otroTipoId').value;

				if (txtOtraIdentificacion.trim().length > 0
						|| txtOtraIdentificacion != '') {
					document.getElementById('tipoIdentificacion').selectedIndex = 14;
				}
			}
		</script>
		<script>
			function ActualizaTablaRep(vIdCliente, urlService) {
				var str = "";
				str += "<br/>";
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

			function llamadaRepresentanteActualiz() {

				txtcliente = document.getElementById('Cliente_Id').value;
				txtrutaRep = document.getElementById('WsRep').value;

				ActualizaTablaRep(txtcliente, txtrutaRep);
			}

			function llamadaBeneActualiz() {

				txtcliente = document.getElementById('Cliente_Id').value;
				txtrutaBene = document.getElementById('WsBene').value;

				ActualizaTablaBene(txtcliente, txtrutaBene);
			}
		</script>


		<script>
			function Prog() {
				var progressbar = $("#progressbar"), progressLabel = $(".progress-label");
				//var tiempo = 200;
				//alert("entro1");
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
			// When the user clicks on <div>, open the popup
			function myFunction() {
				var popup = document.getElementById("myPopup");
				popup.classList.toggle("show");
			}
		</script>
</body>
</html>