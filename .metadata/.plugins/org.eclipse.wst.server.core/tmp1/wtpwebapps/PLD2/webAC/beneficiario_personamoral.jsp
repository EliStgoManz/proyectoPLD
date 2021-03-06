<%@page import="datosRatos.DatosTipoIdentifiacion"%>
<%@page import="entidad.TipoIdentificacion"%>
<%@page import="utilidades.Mensajes"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="utilidades.Archivos"%>
<%@page import="utilidades.Rutas"%>
<%@page import="java.io.File"%>
<%@page import="datosRatos.DatosBeneficiario"%>
<%@include file="valida_login.jsp"%>
<%@page import="datosRatos.DatosGiro"%>
<%@page import="entidad.Giro"%>
<%@page import="entidad.Pais"%>
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
			Mensajes.setMensaje("No es posible agregar m?s de cinco beneficiarios.");
			request.setAttribute("resultado", Mensajes.mensaje);
			request.setAttribute("exito", "1");
			request.getRequestDispatcher("/mensajes.jsp").forward(request, response);
		}
	} else {
		if (numBenefiiarios >= 5 && esEdicion.compareTo("0") != 0) {
			Mensajes.setMensaje("No es posible agregar m?s de cinco beneficiarios.");
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
							<h1 class="title title-contrata-ahora">Sistema de Prevenci?n
								de Lavado de Dinero</h1>
						</div>
					</div>
				</div>
			</div>
		</div>
	</header>
	<h5 id="Imp2" class="title title-contrata-ahora white-text">Sistema
		de Prevenci?n de Lavado de Dinero</h5>
	<div class="container">
		<div id="Imp3" class="main row">
			<article class="col-xs-12 col-sm-8 col-md-9 col-lg-9">
				<h2 class="text-muted">
					Datos y Documentos del Due?o Beneficiario o <small>Beneficiario
						Controlador, Persona Moral.</small>
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
					ArrayList listaGiro = new DatosGiro().getList("");
					ArrayList listaTipoIdentificacion = new DatosTipoIdentifiacion().getList();
					//                                    UsuarioTransitivo.setSiBeneficiario("0");
				%>
				<form onsubmit="return validarBeneficiarioPersonaMoral()"
					action="BeneficiarioPersonaMoral" method="post" name="frmBeneMoral"
					id="frmBeneMoral" enctype="multipart/form-data">

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
								<h4>
									Datos y Documentos<small></small>
								</h4>
							</td>
						</tr>

						<tr>
							<td><label id="Imp5"> * Tel?fono pa?s </label> <select
								class="browser-default" name="btelPais" id="btelPais">
									<option value="">* Tel?fono pa?s</option>
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
										id="btelefono" onkeypress="return esNumero(this, event)"
										maxlength="10" value="<c:out value="${telefono}" />">
									<label for="btelefono" class=""> * Tel?fono </label>
								</div>
							</td>
						</tr>
						<tr id="Imp6">
							<td colspan="3">
								<div class="md-form form-sm">

									<label for="tipo">* Correo electr?nico</label><br /> <input
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
										name="bRazonSocial" id="bRazonSocial" maxlength="200"
										size="80" value="<c:out value="${razonsocial}" />"> <label
										for="bRazonSocial" class=""> * Denominaci?n o Raz?n
										social </label>
								</div>
							</td>
						</tr>
						<tr>
							<td id="Imp8"><input type="date" name="bfechaConstitucion"
								id="bfechaConstitucion" placeholder="* Fecha de constitucion"
								value="<c:out value="${fechaconstitucion}" />"> <img
								src="img/calendar.png" height="32" width="32" /><label>*Fecha
									de constiticion</label></td>
							<td></td>
							<td><label id="Imp9"> * Pa?s </label> <select
								class="browser-default" name="bpais" id="bpais">
									<option value="">* Pa?s</option>
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


						<tr id="Imp10">

							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										onkeyup="mayus(this);" name="bRFC" id="bRFC" maxlength="12"
										value="<c:out value="${rfc}" />"> <label for="bRFC">*
										RFC</label>
								</div>
							</td>
							<td></td>


						</tr>

						<tr>
							<td colspan="3"><label for="bGiro" id="Imp11">*
									Giro</label> <select class="browser-default" name="bGiro" id="bGiro"
								style="width: 99%;">
									<option value="">* Giro</option>


									<%
										//Verificamos que tengamos pa?ses depu�s de la consulta a la base de datos
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
							<td colspan="3" style="display: none;">
								<div class="md-form form-sm">
									<input type="text" onkeyup="mayus(this)" id="bbuscarGiro"
										name="bbuscarGiro" class="input_text"
										onfocusout="validarActividadEco()"> <label
										for="bbuscarActividadEco">* Buscar giro</label><br /> <br>
								</div>

							</td>


						</tr>
						<tr>
							<td colspan="3"><label for="tipo">* Comprobante Acta
									Constitutiva</label><br /> <input type="hidden"
								id="idarchivoActaConstitutiva" name="idarchivoActaConstitutiva"
								value="<c:out value="${imagenActaConstitutiva}" />"> <input
								type="hidden" id="ActaConstitutivaZip"
								name="ActaConstitutivaZip" /> <input type="file"
								name="ActaConstitutiva" id="ActaConstitutiva"
								accept="document/pdf"
								onchange="validarArchivoActaConst('lupaActa', this, document.getElementById('ActaConstitutivaZip'), document.getElementById('covers'))">&nbsp;
								<a href="javascript:PreviewImage('ActaConstitutiva')"> <img
									src="img/lupa.jpg" height="32" width="32" style="display: none"
									id="lupaActa" />
							</a>
								<p class="help-block">Un solo archivo de m?ximo 25MB</p> <%
 	String idCLiente = idCliente;
 	String rutaOrigen = Rutas.rutaCarga + Rutas.separador + idCLiente + Rutas.separador
 			+ Rutas.dirActaConstitutivaBene + elNoBene + Rutas.separador;
 	String rutaDestino = Rutas.rutaDescarga + Rutas.separador + idCLiente + Rutas.separador
 			+ Rutas.dirActaConstitutivaBene + elNoBene + Rutas.separador;
 	File[] listaArchivos = new File(rutaOrigen).listFiles();
 	if (listaArchivos != null && listaArchivos.length > 0) {
 		Arrays.sort(listaArchivos, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
 		out.println("<INPUT type='hidden' id='HayArchivoIdPF' name='HayArchivoIdPF' value='si'");
 		int a = 0;
 		if ((perfilId.compareTo("1") == 0) || (perfilId.compareTo("2") == 0)) { // Puede ver el historial                                                                                                                                                                                
 			out.println("<p>Historial de documentos guardados:</p>");
 			a = listaArchivos.length;
 		} else {
 			out.println("<p>?ltimo archivo guardado:</p>");
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
 						+ dirRootDes + "/" + Rutas.dirActaConstitutivaBene + elNoBene + "/"
 						+ listaArchivos[i].getName()
 						+ "', 'clearcache=yes')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>");
 				//out.println("<p> ?ltimo archivo guardado:" + archivo.getName() + " <a href=\"javascript:window.open('" + dirRootDes  + "/" + Rutas.dirCedula + "/" + archivo.getName() + "')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"></a></p>" );
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
							<td colspan="3"><label for="tipo">* Carga de c?dula
									fiscal</label><br /> <input type="hidden" id="idarchivocedulaPF"
								name="idarchivocedulaPF"
								value="<c:out value="${imagencedula}" />"> <input
								type="hidden" id="cedulaPFZip" name="cedulaPFZip" /> <input
								type="file" name="cedulaPF" id="cedulaPF" accept="document/pdf"
								onchange="validarArchivoCedulaFiscal('lupaCedula', this, document.getElementById('cedulaPFZip'), document.getElementById('covers'))">&nbsp;
								<a href="javascript:PreviewImage('cedulaPF')"> <img
									src="img/lupa.jpg" height="32" width="32" style="display: none"
									id="lupaCedula" />
							</a>
								<p class="help-block">Un solo archivo de m?ximo 10MB</p> <%
 	idCLiente = idCliente;
 	rutaOrigen = Rutas.rutaCarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirCedulaBene
 			+ elNoBene + Rutas.separador;
 	rutaDestino = Rutas.rutaDescarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirCedulaBene
 			+ elNoBene + Rutas.separador;
 	listaArchivos = new File(rutaOrigen).listFiles();
 	if (listaArchivos != null && listaArchivos.length > 0) {
 		Arrays.sort(listaArchivos, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
 		out.println("<INPUT type='hidden' id='HayArchivoCedula' name='HayArchivoCedula' value='si'");
 		int a = 0;
 		if ((perfilId.compareTo("1") == 0) || (perfilId.compareTo("2") == 0)) { // Puede ver el historial                                                                                                                                                                                
 			out.println("<p>Historial de documentos guardados:</p>");
 			a = listaArchivos.length;
 		} else {
 			out.println("<p>?ltimo archivo guardado:</p>");
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
 						+ dirRootDes + "/" + Rutas.dirCedulaBene + elNoBene + "/" + listaArchivos[i].getName()
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
						<tr id="Imp12">
							<td colspan="3">
								<h4>Domicilio Due?o Beneficiario</h4>
							</td>

							<td></td>
						</tr>


						<tr id="Imp13">
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
						<tr id="Imp14">
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
						<tr id="Imp15">
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
									<input class="form-control" type="text" onkeyup="mayus(this)"
										onkeypress="return esNumero(this, event)" maxlength="5"
										name="bcp" id="bcp" value="<c:out value="${codpostal}" />">
									<label for="bcp" class=""> * C?digo Postal</label>
								</div>
							</td>
						</tr>
						<tr id="Imp16">
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
							<td><label id="Imp17"> * Pa?s </label> <select
								class="browser-default" name="bpaisDomicilio"
								id="bpaisDomicilio">
									<option value="">* Pa?s</option>
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
								id="idarchivocompDomicilio" name="idarchivocompDomicilio"
								value="<c:out value="${imagencompdomicilio}" />"> <input
								type="hidden" id="compDomicilioZip" name="compDomicilioZip" />
								<input type="file" name="compDomicilio" id="compDomicilio"
								accept="document/pdf"
								onchange="validarArchivoComprobanteDomicilio('lupaComp', this, document.getElementById('compDomicilioZip'), document.getElementById('covers'))">&nbsp;
								<div class="popup" onclick="myFunction()">
									<img src="img/signo.jpg" height="32" width="32" /> <span
										class="popuptext" id="myPopup">Recibo de pago por
										servicios domiciliados o Estados de cuenta bancarios, con
										antig?edad no mayor a tres meses a su fecha de emisi?n,
										Contrato de arrendamiento vigente, Constancia de Inscripci?n
										en el RFC.</span>
								</div> <a href="javascript:PreviewImage('compDomicilio')"> <img
									src="img/lupa.jpg" height="32" width="32" style="display: none"
									id="lupaComp" />
							</a>
								<p class="help-block">Un solo archivo de m?ximo 10MB</p> <%
 	idCLiente = idCliente;
 	rutaOrigen = Rutas.rutaCarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirComprobanteDomBene
 			+ elNoBene + Rutas.separador;
 	rutaDestino = Rutas.rutaDescarga + Rutas.separador + idCLiente + Rutas.separador
 			+ Rutas.dirComprobanteDomBene + elNoBene + Rutas.separador;

 	listaArchivos = new File(rutaOrigen).listFiles();
 	if (listaArchivos != null && listaArchivos.length > 0) {
 		Arrays.sort(listaArchivos, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
 		out.println("<INPUT type='hidden' id='HayArchivoIdPF' name='HayArchivoIdPF' value='si'");
 		int a = 0;
 		if ((perfilId.compareTo("1") == 0) || (perfilId.compareTo("2") == 0)) { // Puede ver el historial                                                                                                                                                                                
 			out.println("<p>Historial de documentos guardados:</p>");
 			a = listaArchivos.length;
 		} else {
 			out.println("<p>?ltimo archivo guardado:</p>");
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
 				//out.println("<p> ?ltimo archivo guardado:" + archivo.getName() + " <a href=\"javascript:window.open('" + dirRootDes  + "/" + Rutas.dirCedula + "/" + archivo.getName() + "')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"></a></p>" );
 			} catch (Exception es) {
 				es.printStackTrace();
 			}

 		} //for
 	} else {
 		out.println("<INPUT type='hidden' id='HayArchivoIdPF' name='HayArchivoIdPF' value='no'");
 	}
 %></td>
						</tr>


						<tr id="Imp18">
							<td colspan="3">
								<h4>Datos y Documentos del representante legal</h4>
							</td>
						</tr>
						<tr id="Imp19">
							<td colspan="3">
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="bnombre" id="bnombre" maxlength="200" size="80"
										value="<c:out value="${rlnombre}" />"> <label
										for="bnombre" class=""> * Nombre(s) </label>
								</div>
							</td>
						</tr>
						<tr id="Imp20">
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="bpaterno" id="bpaterno" maxlength="200"
										value="<c:out value="${rlapellidopaterno}" />"> <label
										for="bpaterno" class=""> * Apellido paterno</label>
								</div>
							</td>
							<td></td>
						</tr>
						<tr id="Imp21">

							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="bmaterno" id="bmaterno" maxlength="200"
										value="<c:out value="${rlapellidomaterno}" />"> <label
										for="bmaterno" class=""> * Apellido materno</label>
								</div>
							</td>
						</tr>
						<tr id="Imp22">
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										onkeyup="mayus(this);" name="rlRFC" id="rlRFC" maxlength="13"
										value="<c:out value="${rlrfc}" />"> <label for="rlRFC">*
										RFC</label>
								</div>
							</td>
							<td></td>


						</tr>
						<tr id="Imp23">

							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										onkeyup="mayus(this);" name="bCURP" id="bCURP" maxlength="18"
										value="<c:out value="${rlcurp}" />"> <label
										for="bCURP">* CURP</label>
								</div>
							</td>
							<td></td>


						</tr>
						<tr id="Imp24">
							<td><input type="date" name="bfechaNacimiento"
								id="bfechaNacimiento" placeholder="* Fecha de constitucion"
								value="<c:out value="${rlfechanacimiento}" />"> <img
								src="img/calendar.png" height="32" width="32" /><label>*Fecha
									de Nacimiento</label></td>
							<td></td>

						</tr>
						<tr>
							<td><label id="Imp25" for="tipoIdentificacion">*
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
							<td id="Imp26">
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="numeroId" id="numeroId" maxlength="40"
										value="<c:out value="${rlnumeroid}" />"> <label
										for="numeroId" class=""> * No. de ID</label>
								</div>
							</td>
						</tr>

						<tr id="Imp27">

							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text"
										onchange="hayOtraIdentificacion()" onkeyup="mayus(this)"
										name="otroTipoId" id="otroTipoId"
										value="<c:out value="${rlidentificaciontipo}" />"> <label
										for="otroTipoId" class=""> * Otro tipo de ID</label>
								</div>
							</td>
							<td></td>
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="autoridadEmite" id="autoridadEmite"
										value="<c:out value="${rlautoridademiteid}" />"> <label
										for="autoridadEmite" class=""> * Entidad que emite ID</label>
								</div>
							</td>
						</tr>

						<tr>
							<td colspan="3"><label for="tipo">* ID Representante
									legal</label><br /> <input type="hidden"
								id="idarchivoRepresentanteLegal"
								name="idarchivoRepresentanteLegal"
								value="<c:out value="${imagenIdRepresentante}" />"> <input
								type="hidden" id="RepresentanteLegalZip"
								name="RepresentanteLegalZip" /> <input type="file"
								name="RepresentanteLegal" id="RepresentanteLegal"
								accept="document/pdf"
								onchange="validarArchivoIdLegal('lupaIdRep', this, document.getElementById('RepresentanteLegalZip'), document.getElementById('covers'))">&nbsp;
								<a href="javascript:PreviewImage('RepresentanteLegal')"> <img
									src="img/lupa.jpg" height="32" width="32" style="display: none"
									id="lupaIdRep" />
							</a>
								<p class="help-block">Un solo archivo de m?ximo 10MB</p> <%
 	idCLiente = idCliente;
 	rutaOrigen = Rutas.rutaCarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirIdentificacionBene
 			+ elNoBene + Rutas.separador;
 	rutaDestino = Rutas.rutaDescarga + Rutas.separador + idCLiente + Rutas.separador
 			+ Rutas.dirIdentificacionBene + elNoBene + Rutas.separador;
 	listaArchivos = new File(rutaOrigen).listFiles();
 	if (listaArchivos != null && listaArchivos.length > 0) {
 		Arrays.sort(listaArchivos, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
 		out.println("<INPUT type='hidden' id='HayArchivoCedula' name='HayArchivoCedula' value='si'");
 		int a = 0;
 		if ((perfilId.compareTo("1") == 0) || (perfilId.compareTo("2") == 0)) { // Puede ver el historial                                                                                                                                                                                
 			out.println("<p>Historial de documentos guardados:</p>");
 			a = listaArchivos.length;
 		} else {
 			out.println("<p>?ltimo archivo guardado:</p>");
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
 						+ dirRootDes + "/" + Rutas.dirIdentificacionBene + elNoBene + "/"
 						+ listaArchivos[i].getName()
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
							<td colspan="3"><label for="tipo">* Poder notarial</label><br />
								<input type="hidden" id="idarchivoPoderNotarial"
								name="idarchivoPoderNotarial"
								value="<c:out value="${imagenPoderNotarial}" />"> <input
								type="hidden" id="PoderNotarialZip" name="PoderNotarialZip" />
								<input type="file" name="PoderNotarial" id="PoderNotarial"
								accept="document/pdf"
								onchange="validarArchivoPoderNotarial('lupaPoderN', this, document.getElementById('PoderNotarialZip'), document.getElementById('covers'))">&nbsp;
								<a href="javascript:PreviewImage('PoderNotarial')"> <img
									src="img/lupa.jpg" height="32" width="32" style="display: none"
									id="lupaPoderN" />
							</a>
								<p class="help-block">Un solo archivo de m?ximo 25MB</p> <%
 	idCLiente = idCliente;
 	rutaOrigen = Rutas.rutaCarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirRlPoderNotarialBene
 			+ elNoBene + Rutas.separador;
 	rutaDestino = Rutas.rutaDescarga + Rutas.separador + idCLiente + Rutas.separador
 			+ Rutas.dirRlPoderNotarialBene + elNoBene + Rutas.separador;

 	listaArchivos = new File(rutaOrigen).listFiles();
 	if (listaArchivos != null && listaArchivos.length > 0) {
 		Arrays.sort(listaArchivos, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
 		out.println("<INPUT type='hidden' id='HayArchivoIdPF' name='HayArchivoIdPF' value='si'");
 		int a = 0;
 		if ((perfilId.compareTo("1") == 0) || (perfilId.compareTo("2") == 0)) { // Puede ver el historial                                                                                                                                                                                
 			out.println("<p>Historial de documentos guardados:</p>");
 			a = listaArchivos.length;
 		} else {
 			out.println("<p>?ltimo archivo guardado:</p>");
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
 						+ dirRootDes + "/" + Rutas.dirRlPoderNotarialBene + elNoBene + "/"
 						+ listaArchivos[i].getName()
 						+ "', 'clearcache=yes')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>");
 				//out.println("<p> ?ltimo archivo guardado:" + archivo.getName() + " <a href=\"javascript:window.open('" + dirRootDes  + "/" + Rutas.dirCedula + "/" + archivo.getName() + "')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"></a></p>" );
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
						<!-- comienza la informaci?n del beneficiario -->

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
						href="javascript:imprimir(Imp1,Imp2,Imp3,Imp4,Imp5,Imp55,Imp6,Imp7,Imp8,Imp9,Imp10,Imp11,Imp12,Imp13,Imp14,Imp15,Imp16,Imp17,Imp18,Imp19,Imp20,Imp21,Imp22,Imp23,Imp24,Imp25,Imp26,Imp27)">Imprimir
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
						target="_top"> T?rminos y Condiciones </a></li>
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

	<!-- Los siguientes divs para la previsualizaci?n de los archivos pdf -->
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
							txtEstatus = '${estadoCliente}';
							txtPerfilId = '${perfilId}';
							//alert(txtEstatus);
							document.getElementById('btnClose').style.visibility = 'hidden';

							if (txtEstatus == 'A') {
								//                                alert("entra validado");
								document.getElementById("btn-procesar").style.visibility = 'hidden';
								document.getElementById('btnClose').style.visibility = 'visible';
								var form = document
										.getElementById('frmBeneMoral');
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
										.getElementById('frmBeneMoral');
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
										.getElementById('frmBeneMoral');
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
										.getElementById('frmBeneMoral');
								var elements = form.elements;
								for (var i = 0, len = elements.length; i < len; ++i) {
									//alert("for");
									elements[i].readOnly = true;
									elements[i].disabled = true;
									document.getElementById('btnClose').disabled = false;
								}
							}

							//ASIGNANDO MEXICO COMO PA?S PREDETERMINADO
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

							//bpais
							var selector = document.getElementById('bpais');
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
							var selector = document.getElementById('bGiro');
							for (var i = 0; i < selector.length; i++) {
								if ('1000000' == selector.options[i].value
										.trim()) {
									selector.selectedIndex = i;
									break;
								} else {//if
									selector.selectedIndex = 0;
								}
							}//for 

							//ASIGNANDO LOS DATOS CU?NDO VIENEN DE LA CAPTURA

							esEdicion = document.getElementById('esEdicion').value;
							if (esEdicion == '1') {

								//ASIGNANDO EL PA?S TEL?FONO
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

								//ASIGNANDO EL PA?S EMPRESA
								txtPais = '${pais}';
								if (txtPais != null || txtPais.length > 0) {
									var selector = document
											.getElementById('bpais');
									for (var i = 0; i < selector.length; i++) {
										if (txtPais == selector.options[i].value) {
											selector.selectedIndex = i;
											break;
										} else {//if
											selector.selectedIndex = 0;
										}
									}//for 
								}

								//ASIGNANDO EL GIRO
								txtGiro = '${giro_id}';
								if (txtGiro != null || txtGiro.length > 0) {
									var selector = document
											.getElementById('bGiro');
									for (var i = 0; i < selector.length; i++) {
										if (txtGiro == selector.options[i].value) {
											selector.selectedIndex = i;
											break;
										} else {//if
											selector.selectedIndex = 0;
										}
									}//for 
								}

							}//esEdicion

							//ASIGNANDO TIPO DE IDENTIFICACI?N    
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
							var enter = "0";

						});
	</script>


	<script>
		jQuery(function() {
			$("#bbuscarGiro").autocomplete("listGiro.jsp");
		});
	</script>

	<script>
		function validarActividadEco() {
			//Asigna al select el valor lozalizado en la lista de actividades escrita

			txtTextoGiro = document.getElementById('bbuscarGiro').value;
			var selector = document.getElementById('bGiro');
			//alert ( selector.options[2].text );
			if (txtTextoGiro != null || txtTextoGiro.length > 0) {
				for (var i = 0; i < selector.length; i++) {
					if (txtTextoGiro == selector.options[i].text) {
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
		function validarBeneficiarioPersonaMoral() {
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

			txtbRazonSocial = document.getElementById('bRazonSocial').value;
			if (txtbRazonSocial == null || txtbRazonSocial.length == 0
					|| /^\s+$/.test(txtbRazonSocial)) {
				alert('La raz?n social no puede dejarse en blanco');
				document.getElementById('bRazonSocial').focus();
				return false;
			}

			txtbRFC = document.getElementById('bRFC').value;
			if (txtbRFC.length !== 0) {
				if (txtbRFC.length !== 12) {
					alert('El RFC debe ser de 12 caracteres');
					document.getElementById('bRFC').focus();
					return false;

				}

			}

			txtfechaConstitucion = document
					.getElementById('bfechaConstitucion').value;
			txtfechaConstitucion = getFechaNac(txtfechaConstitucion);
			txtRFCEmpresa = document.getElementById('bRFC').value;
			if (txtRFCEmpresa.length == 12) {
				txtFechaRFC = getFechaNacRFC('X' + txtRFCEmpresa);
				if (txtfechaConstitucion != ''
						&& txtfechaConstitucion.length > 0) {
					if (txtfechaConstitucion != txtFechaRFC) {
						alert('La fecha de constituci?n no coincide con la fecha del RFC');
						document.getElementById('bRFC').focus();
						return false;
					}
				}
			}
			txtcp = document.getElementById('bcp').value;
			if (txtcp.length !== 0) {
				if (txtcp.length !== 5) {
					alert('El C?digo Postal debe ser de 5 caracteres');
					document.getElementById('bcp').focus();
					return false;
				}
			}
			txttelefono = document.getElementById('btelefono').value;
			if (txttelefono.length !== 0) {
				if (txttelefono.length !== 10) {
					alert('El N?mero de Tel?fono debe ser de 10 dig?tos');
					document.getElementById('btelefono').focus();
					return false;
				}
			}
			txtrlRFC = document.getElementById('rlRFC').value;
			if (txtrlRFC.length !== 0) {
				if (txtrlRFC.length !== 13) {
					alert('El RFC debe ser de 13 caracteres');
					document.getElementById('rlRFC').focus();
					return false;

				}
			}
			txtFechaNacimiento = document.getElementById('bfechaNacimiento').value;
			if (txtbRFC.length == 13 && txtFechaNacimiento <= 0) {
				alert('Debe indicar fecha de nacimiento');
				document.getElementById('bfechaNacimiento').focus();
				return false;
			}

			txtFechaNacimiento = getFechaNac(txtFechaNacimiento); //normalizando 
			b = document.getElementById('rlRFC').value;
			txtFechaNacimientoRFC = '';
			if (b.length > 0 && b != '') {
				txtFechaNacimientoRFC = getFechaNacRFC(document
						.getElementById('rlRFC').value);
			}
			if (txtFechaNacimiento != null && txtFechaNacimiento.length > 0
					&& txtFechaNacimientoRFC != null
					&& txtFechaNacimientoRFC.length > 0) {
				if (txtFechaNacimiento != txtFechaNacimientoRFC) {
					alert('La fecha de nacimiento no coincide con la fecha de nacimiento del RFC');
					document.getElementById('rlRFC').focus();
					return false;
				}
			}
			txtbCURP = document.getElementById('bCURP').value;
			if (txtbCURP.length !== 0) {
				if (txtbCURP.length !== 18) {
					alert('El CURP debe ser de 18 caracteres');
					document.getElementById('bCURP').focus();
					return false;

				}
			}
			if (document.getElementById('ActaConstitutiva').value.length == 0)
				document.getElementById('ActaConstitutivaZip').value = '';
			if (document.getElementById('cedulaPF').value.length == 0)
				document.getElementById('cedulaPFZip').value = '';
			if (document.getElementById('compDomicilio').value.length == 0)
				document.getElementById('compDomicilioZip').value = '';
			if (document.getElementById('RepresentanteLegal').value.length == 0)
				document.getElementById('RepresentanteLegalZip').value = '';
			if (document.getElementById('PoderNotarial').value.length == 0)
				document.getElementById('PoderNotarialZip').value = '';

			localStorage.setItem('capturado', '1');
			
			document.getElementById('btn-procesar').disabled=true;
			alert("Validando RFC y CURP");
			
			if(validarRFC() && validarRFC2() && validarCURP()){
				return true;
			}else{
				document.getElementById('btn-procesar').disabled=false;
			return false;
			}

		}//funcion validar beneficiario
	</script>
	
	<script>
// 	CUR130604IK5
	function validarRFC(){
		var txtbRFC = document.getElementById('bRFC').value;

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
	
	function validarRFC2(){
		var txtbRFC = document.getElementById('rlRFC').value;

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
			    	document.getElementById('rlRFC').focus();
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
		
		var txtCurp = document.getElementById('bCURP').value;

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
			    	document.getElementById('bCURP').focus();
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
		function Prog() {
			$('.spinner-wrapper').fadeIn('fast');
			var progressbar = $("#progressbar"), progressLabel = $(".progress-label");
			//var tiempo = 200;
			//alert("entro1");
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
				Imp17, Imp18, Imp19, Imp20, Imp21, Imp22, Imp23, Imp24, Imp25,
				Imp26, Imp27) {
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
					+ document.getElementById('bpais').options[document
							.getElementById('bpais').selectedIndex].text
					+ document.getElementById('Imp10').innerHTML
					+ document.getElementById('Imp11').innerHTML
					+ document.getElementById('bGiro').options[document
							.getElementById('bGiro').selectedIndex].text
					+ document.getElementById('Imp12').innerHTML
					+ document.getElementById('Imp13').innerHTML
					+ document.getElementById('Imp14').innerHTML
					+ document.getElementById('Imp15').innerHTML
					+ document.getElementById('Imp16').innerHTML
					+ document.getElementById('Imp17').innerHTML
					+ document.getElementById('bpaisDomicilio').options[document
							.getElementById('bpaisDomicilio').selectedIndex].text
					+ document.getElementById('Imp18').innerHTML
					+ document.getElementById('Imp19').innerHTML
					+ document.getElementById('Imp20').innerHTML
					+ document.getElementById('Imp21').innerHTML
					+ document.getElementById('Imp22').innerHTML
					+ document.getElementById('Imp23').innerHTML
					+ document.getElementById('Imp24').innerHTML
					+ document.getElementById('Imp25').innerHTML
					+ document.getElementById('tipoIdentificacion').options[document
							.getElementById('tipoIdentificacion').selectedIndex].text
					+ document.getElementById('Imp26').innerHTML
					+ document.getElementById('Imp27').innerHTML;
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

