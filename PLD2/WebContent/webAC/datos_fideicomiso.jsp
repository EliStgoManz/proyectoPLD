<%@page import="datosRatos.ConsultaWS"%>
<%@page import="entidad.Representante"%>
<%@page import="datosRatos.DatosRepLegal"%>
<%@page import="rfcCurpAppi.token"%>
<%@page import="datosRatos.DatosClienteRaro"%>
<%@page import="entidad.Cliente"%>
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
						Datos y Documentos del Cliente<small>, Cliente
							Fideicomiso.</small>
					</h2>
				</article>
				<aside class="col-xs-12 col-sm-4 col-md-3 col-lg-3">
					<p class="lead">
						Bienvenido: Bienvenido:
						<%@include file="detectausuario.jsp"%>
						<!--   Bienvenido:  out.println( vClienteParam );    -->
					</p>
				</aside>
			</div>
			<div class="row">
				<div class="col-md-9 table-responsive">
					<form onsubmit="return validarFomularioFideicomisos()"
						action="RegistroFideicomiso" method="post" id="frmFideicomiso"
						name="frmFideicomiso" enctype="multipart/form-data">
						<input type="hidden" id="tipoPersona" name="tipoPersona" value="X">
						<input type="hidden" id="Cliente_Id" name="Cliente_Id"
							value="<%out.print(vClienteParam);%>"> <input
							id="esEdicion" name="esEdicion" type="hidden"
							value="<c:out value="${esEdicion}" />"> <input
							id="estadoCliente" name="estadoCliente" type="hidden"
							value="<c:out value='${estado}' />">
						<%
							//Llamamos a los paï¿½ses desde la base de datos
							ArrayList listaPaises = new DatosPais().getList();
							ArrayList listaTipoIdentificacion = new DatosTipoIdentifiacion().getList();
							ArrayList listaActivida = new DatosActividad().getList("");
							//                                        UsuarioTransitivo.setSiBeneficiario("0");
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
										de conformidad con el art. 21 de la citada Ley.<br />
										Artículo 21. Los clientes o usuarios de quienes realicen
										Actividades Vulnerables les proporcionarán a éstos la
										información y documentación necesaria para el cumplimiento de
										las obligaciones que esta Ley establece. Quienes realicen las
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
									<label for="id"> <%
 	out.println(vClienteParam);
 %>
								</label></td>
								<td width="50px"></td>
								<td width="350px">
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="minus(this)"
											id="correo" name="correo"
											value="<%out.println(vMailParam);%>" readonly="readonly"
											maxlength="60"> <label for="correo">* Correo
											electrónico</label><br />
									</div>

								</td>
							</tr>
							<tr>
								<td><label>* Tel. país</label> <input type="hidden"
									id="idtelPais" name="idTelPais"
									value="<c:out value="${pais}" />"> <select
									class="browser-default" name="telPais" id="telPais">
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
											id="razonSocial" maxlength="120" name="razonSocial" size="80"
											value="<c:out value="${razonsocial}" />"> <label
											for="razonSocial">* Denominación o Razón social de la
											Institución Fiduciaria </label>
									</div>
								</td>
							</tr>
							<tr>
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
							<tr>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="rfc" name="rfc" maxlength="13"
											value="<%out.println(vRFCParam);%>" maxlength="12" readonly>
										<label for="rfc">* RFC</label>
									</div>
								</td>
								<td></td>
								<td></td>
							</tr>
							<tr>
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
										<input class="form-control" type="number" onkeyup="mayus(this)"
											id="noescritura" name="noescritura" maxlength="100"
											value="<c:out value="${noescritura}" />"> <label
											for="noescritura">* No de escritura</label>
									</div>
								</td>
								<td></td>
									<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="notaria" name="notaria" maxlength="100"
											value="<c:out value="${notaria}" />"> <label
											for="notaria">*Notaria</label>
									</div>
								</td>
									</tr>
								<tr >	
								<td><input class="" type="date" id="fechaNotarial"
									name="fechaNotarial"
									value="<c:out value="${fechaNotarial}" />"> <img
									src="img/calendar.png" height="32" width="32" /> <label
									for="fechaNotarial">* Fecha </label><br>
								<br></td>
								
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
							</tr>
							<tr>
							<tr>
								<td colspan="3"><label for="tipo">* Cedula fiscal</label><br />
									<input type="hidden" id="idarchivoCedula"
									name="idarchivoCedula"
									value="<c:out value="${imagencedulafiscal}" />"> <input
									type="hidden" id="archivoCedulaZip" name="archivoCedulaZip" />
									<input type="file" id="cedulaFiscal" name="cedulaFiscal"
									accept="document/pdf"
									onchange="validarArchivoActa('lupaCedula', this, document.getElementById('archivoCedulaZip'), document.getElementById('covers'))">&nbsp;
									<a href="javascript:PreviewImage('cedulaFiscal')"> <img
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
										<input class="form-control" type="text" id="cp" name="cp"
											onkeypress="return esNumero(this, event)" maxlength="5"
											value="<c:out value="${codpostal}" />"> <label
											for="cp"> * Código Postal</label>
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
								<td><label>* País</label> <input type="hidden"
									id="idPaisDomicilio" name="idPaisDomicilio"
									value="<c:out value="${paisDomicilio}" />"> <select
									class="browser-default" name="pais" id="pais">
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
											class="popuptext" id="myPopup">Recibo de pago por
											servicios domiciliados o Estados de cuenta bancarios, con
											antigüedad no mayor a tres meses a su fecha de emisión,
											Contrato de arrendamiento vigente, Constancia de Inscripción
											en el RFC.</span>
									</div> <a href="javascript:PreviewImage('archivoDomicilio')"> <img
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
									for="fFechaNacimiento">* Fecha de nacimiento</label><br> <br></td>
								<td></td>
								<td></td>

							</tr>

							<tr>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="fRFC" name="fRFC" maxlength="13"
											value="<c:out value="${rlrfc }" />"> <label
											for="fRFC">* RFC</label>
									</div>
								</td>
								<td></td>
								<td>
									<div class="md-form form-sm">
										<input type="text" onkeyup="mayus(this)" id="fCURP"
											name="fCURP" maxlength="18"
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
										<option value="">* Tipo de ID</option>
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
										<input class="form-control" type="text" id="fotroTipoId"
											name="fotroTipoId" maxlength="200"
											value="<c:out value="${rlidentificaciontipo}" />"> <label
											for="fotroTipoId">* Otro tipo id</label>
									</div>
								</td>
								<td></td>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="fautoridadEmite" maxlength="200" name="fautoridadEmite"
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
									name="archivofId" id="archivofId" accept="document/pdf"
									onchange="validarArchivoId('lupafId', this, document.getElementById('archivoRlIdentificacionZip'), document.getElementById('covers'))">&nbsp;
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
							<!--         <tr>
						<td colspan="3">
							<h4>Datos del beneficiario, controlador o dueño</h4>
						</td>
					</tr>	
					<tr>
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
					</tr>	
					<tr>
						<td colspan="3">
                                               <input type="hidden" id="capturado" name="capturado" value="0"> 
                                               <input type="hidden" id="idDeclaroBeneficiario" name="idNoBeneficiario" value="<c:out value="${declarobeneficiario}" />" >
                                               <input type="checkbox" id="sibeneficiario" name="sibeneficiario" class="" >
                                              <label for="sibeneficiario" class="bene">
                                                Tengo conocimiento del Dueño Beneficiario o Beneficiario Controlador
                                              </label><br>
						</td>						
					</tr>	
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
							<!-- <button class="btn" >Agregar beneficiario</button> - - >
                                                        
                                                        <a class="boton_personalizado" id ="btnBene" style="visibility:visible" href="javascript:lanzarBeneficiario()">Agregar beneficiario</a>
						</td>
					</tr> -->
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
 				//out.println("<td><a href=\"" + servlet + "?idCliente=" + b[i].getCliente_id() + "&nroBene=" + b[i].getNroBeneficiario() + "&esEdicion=1\" target=\"_blank\" style=\"text-decoration: underline; color:blue;\"><img src=\"img/lupa.jpg\" height=\"32\" width=\"32\" align=\"center\"></a>"); 
 				//out.println("<td><a href=\"" + servlet + "?idCliente=" + b[i].getCliente_id() + "&nroBene=" + b[i].getNroBeneficiario() + "&esEdicion=0\" target=\"_blank\" style=\"text-decoration: underline; color:blue;\"><img src=\"img/lupa.jpg\" height=\"32\" width=\"32\" align=\"center\"></a>"); 
 				out.println("<td><a href=\"" + servlet + "?idCliente=" + b[i].getCliente_id() + "&nroBene="
 						+ b[i].getNroBeneficiario() + "&estadoCliente=" + estatusCliente
 						+ "&esEdicion=0\" target=\"_blank\" style=\"text-decoration: underline; color:blue;\"><img src=\"img/lupa.jpg\" height=\"32\" width=\"32\" align=\"center\"></a>");
 				System.out.println("<td><a href=\"" + servlet + "?idCliente=" + b[i].getCliente_id().trim()
 						+ "&nroBene=" + b[i].getNroBeneficiario()
 						+ "&esEdicion=1\" target=\"_blank\" style=\"text-decoration: underline; color:blue;\"><img src=\"img/lupa.jpg\" height=\"32\" width=\"32\" align=\"center\"></a>");
 				out.println("</tr>");
 			}
 			out.println("</table>");
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

 	out.println("<a class='boton_personalizado' id ='btnBene' style='visibility:visible' href=" + (char) comilla
 			+ "javascript:Representante('" + vClienteParam + "')" + (char) comilla
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
													Efectivale son propios y de origen lícito. </label><br>
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
					//    Ejemplo De PROGRESS
					//      if ( val <= 30) {
					//        setTimeout( progress, 50 );  // 5 segundos 
					//      }else 
					//          if (val >= 30 && val < 50){
					//          setTimeout( progress, 100 );  // 10 segundos 
					//      }else 
					//          if (val >= 50 && val < 99){
					//          setTimeout( progress, 200 );  // 20 segundos 
					//      }
					//    ----------------------
					//    
					//      PROGRESSBAR DE 15 MIN
					if (val <= 50) {
						setTimeout(progress, 3600);
					} else if (val >= 51 && val < 99) {
						setTimeout(progress, 14400);
					}
					//      ----------------------
				}

				setTimeout(progress, 14400);
			}
		</script>
		<script>
			$(document)
					.ready(
							function() {
								$("#cover").hide();
								//        $("#covers").hide();
								txtEstatus = document
										.getElementById('estadoCliente').value;
								//alert(txtEstatus);    
								//                alert(txtEstatus);
								if (txtEstatus === 'A') {
									document.getElementById("btn-procesar").style.visibility = 'hidden';
									document.getElementById('btnBene').style.visibility = 'hidden';
									document.getElementById('btnClose').style.visibility = 'visible';
									//                        document.getElementById("telPais").disabled = true;
									//                        document.getElementById("actaConstitutiva").disabled = true;
									//                        document.getElementById("cedulaFiscal").disabled = true;
									//             
									var form = document
											.getElementById('frmFideicomiso');
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
									if ('MX' === selector.options[i].value
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
									if ('MX' === selector.options[i].value
											.trim()) {
										selector.selectedIndex = i;
										break;
									} else {//if
										selector.selectedIndex = 0;
									}
								}//for 

								esEdicion = document
										.getElementById('esEdicion').value;

								if (esEdicion === '1') { //la carga de la página proviene de una edición del usuario y no de un nuevo usuario
									/**
									 *  LAS SIGUIENTES FUNCIONES ASIGNAN LOS DATOS QUE SE RECUPERAN DE LA CAPTURA
									 */

									//ASIGNANDO EL TELÉFONO DEL PAÍS
									txtTelPais = document
											.getElementById('idtelPais').value; //llega de la respuesta
									if (txtTelPais !== null
											|| txtTelPais.length > 0) {
										var selector = document
												.getElementById('telPais');
										for (var i = 0; i < selector.length; i++) {
											if (txtTelPais === selector.options[i].value) {
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
									if (txtPais !== null || txtPais.length > 0) {
										var selector = document
												.getElementById('pais');
										for (var i = 0; i < selector.length; i++) {
											if (txtPais === selector.options[i].value) {
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

									// 
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

								}// es edicion
							}); //document ready
		</script>
		<script>
			function validarFomularioFideicomisos() {
				if (validarFormularioFideicomiso()) {
					$('.spinner-wrapper').fadeIn('fast');
					Prog();
					return true;
				} else {
					return false;
				}
			}

			//      function Representante(){
			//        estadoCliente = document.getElementById('estadoCliente').value;
			//        esEdicion = document.getElementById('esEdicion').value;
			//        
			//        window.open('alta_representante.jsp?esEdicion=' + document.getElementById('esEdicion').value + '&estadoCliente=' + document.getElementById('estadoCliente').value , '_blank');
			//    }

			function Representante(vIdCliente) {
				window.open('alta_representante.jsp?esEdicion=1&idCliente='
						+ vIdCliente, '_blank');
			}

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

			function validarFormularioFideicomiso() {
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
				txtNombreFideicomiso = document
						.getElementById('NombreFideicomiso').value;
				if (txtNombreFideicomiso == null
						|| txtNombreFideicomiso.length == 0
						|| /^\s+$/.test(txtNombreFideicomiso)) {
					alert('El nombre del fideicomiso no debe dejarse en blanco')
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

				//            txtfautoridadEmite = document.getElementById('fautoridadEmite').value;
				//            if ( txtfautoridadEmite == null || txtfautoridadEmite.length == 0 || /^\s+$/.test(txtfautoridadEmite)){
				//                alert ('La Autoridad Emitente no puede dejarse en blanco');
				//                document.getElementById('fautoridadEmite').focus();
				//                return false;
				//            }
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

				txtPais = document.getElementById('pais').value;
				if (txtPais === null || txtPais.length === 0
						|| /^\s+$/.test(txtPais)) {
					alert('Debe seleccionar el país del domicilio');
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

				txtFechaNacimiento = document
						.getElementById('fFechaNacimiento').value;
				if (txtFechaNacimiento === null
						|| txtFechaNacimiento.length === 0
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

				if (txtFechaNacimiento !== null
						&& txtFechaNacimiento.length > 0
						&& txtFechaNacimientoRFC !== null
						&& txtFechaNacimientoRFC.length > 0) {
					if (txtFechaNacimiento !== txtFechaNacimientoRFC) {
						alert('La fecha de nacimiento del Representante Legal no coincide con la fecha de nacimiento del RFC');
						document.getElementById('fRFC').focus();
						return false;
					}
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
				txtarchivofId = document.getElementById('archivofId').value;
				if (txtarchivofId.length > 0) {
					if (!comprueba_extension(txtarchivofId)) {
						alert('El archivo de identificación debe ser con la extensión .pdf');
						document.getElementById('archivofId').focus();
						return false;
					}

					input = document.getElementById('archivofId');
					if (!validarTamanoArchivo(input, 10)) {
						alert('El peso del archivo de identificación excede los 10 MB que tiene como límite nuestro sistema');
						document.getElementById('archivofId').focus();
						input = null;
						return false;

					}
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
				
				document.getElementById('btn-procesar').disabled=true;
	             alert("Validando RFC y CURP");
				if (validarCapturaBeneficiario() && validarRFC() && validarRFC2() && validarCURP()) {
					
					document.getElementById('btn-procesar').disabled=false;
					return true;
				} else {
					document.getElementById('btn-procesar').disabled=false;
					return false;
				}

			} //funcion validar formulario

			function validarCapturaBeneficiario() {

				txtdeclaroBeneficiario = document
						.getElementById('declaroBeneficiario').checked;
				txtdeclaroOrigen = document.getElementById('declaroOrigen').checked;
				txtCapturado = localStorage.getItem('capturado');
				return true;
				//        }
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
		
</body>
</html>



