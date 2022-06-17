<%@page import="entidad.TipoIdentificacion"%>
<%@page import="datosRatos.DatosTipoIdentifiacion"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="utilidades.Archivos"%>
<%@page import="utilidades.Rutas"%>
<%@page import="java.io.File"%>
<%@page import="utilidades.Mensajes"%>
<%@page import="datosRatos.DatosBeneficiario"%>
<%@include file="valida_login.jsp"%>
<%@page import="datosRatos.DatosGiro"%>
<%@page import="entidad.Giro"%>
<%@page import="entidad.Pais"%>
<%@page import="datosRatos.DatosPais"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="rfcCurpAppi.token"%>

<%--<%@page import="entidad.UsuarioTransitivo"%>--%>
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
			Mensajes.setMensaje("No es posible agregar más de cinco beneficiarios.");
			request.setAttribute("resultado", Mensajes.mensaje);
			request.setAttribute("exito", "1");
			request.getRequestDispatcher("/mensajes.jsp").forward(request, response);
		}
	} else {
		if (numBenefiiarios >= 5 && esEdicion.compareTo("0") != 0) {
			Mensajes.setMensaje("No es posible agregar más de cinco beneficiarios.");
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
<link href="./css/jquery-ui.css" rel="stylesheet">
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
	<h5 id="Imp1" class="title title-contrata-ahora white-text">Sistema
		de Prevención de Lavado de Dinero</h5>
	<div class="container">
		<div id="Imp2" class="main row">
			<article class="col-xs-12 col-sm-8 col-md-9 col-lg-9">
				<h2 class="text-muted">
					Datos y Documentos del Dueño Beneficiario o <small>
						Beneficiario Controlador, Fideicomiso.</small>
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
					//Llamamos a los paï¿½ses desde la base de datos
					ArrayList listaPaises = new DatosPais().getList();
					ArrayList listaGiro = new DatosGiro().getList("");

					ArrayList listaTipoIdentificacion = new DatosTipoIdentifiacion().getList();
					//ArrayList listaActivida = new DatosActividad().getList();

					//                                    UsuarioTransitivo.setSiBeneficiario("0");
				%>

				<form onsubmit="return validarBeneficiarioFideicomiso()"
					action="BeneficiarioFideicomiso" method="post"
					name="frmBenefideicomiso" id="frmBenefideicomiso"
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
						<tr id="Imp3">
							<td colspan="3">
								<h4>
									Datos y Documentos.<small> </small>
								</h4>
							</td>
						</tr>

						<tr>
							<td><label id="Imp4"> * Teléfono país </label> <select
								class="browser-default" name="btelPais" id="btelPais">
									<option value="">* Teléfono país</option>
									<%
										try {
											//Verificamos que tengamos paï¿½ses depuï¿½s de la consulta a la base de datos
											if (listaPaises != null) {
												for (int i = 0; i < listaPaises.size(); i++) {
													Pais p = (Pais) listaPaises.get(i);
													out.println("<option value=\"" + p.getPais() + "\">" + p.getDescrpcion() + "</option>");
												}
											}
										} catch (Exception e) {
											System.out.println(e.getMessage());
										}
									%>

							</select></td>
							<td></td>
							<td id="Imp5">
								<div class="md-form form-sm">
									<input class="form-control" type="text" name="btelefono"
										id="btelefono" onkeypress="return esNumero(this, event)"
										maxlength="10" value="<c:out value="${telefono}" />">
									<label for="btelefono" class=""> * Teléfono </label>
								</div>
							</td>

						</tr>
						<tr id="Imp55">
							<td colspan="3">
								<div class="md-form form-sm">

									<label for="correo">* Correo electrónico</label><br /> <input
										class="form-control" type="text" onkeyup="minus(this)"
										id="correo" name="correo" value="<c:out value="${email}" />"
										maxlength="200">
								</div>

							</td>
						</tr>
						<tr id="Imp6">
							<td colspan="3">
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="bRazonSocial" id="bRazonSocial" maxlength="200"
										size="80" value="<c:out value="${razonsocial}" />"> <label
										for="bRazonSocial" class=""> * Denominación o Razón
										social de la Institución Fiduciaria</label>
								</div>
							</td>
						</tr>
						<tr id="Imp7">
							<td colspan="3">
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="bInstitucionFiduciaria" id="bInstitucionFiduciaria"
										maxlength="200" size="80"
										value="<c:out value="${institucionfiduciaria}" />"> <label
										for="bInstitucionFiduciaria" class=""> * Nombre del
										Fideicomiso</label>
								</div>
							</td>
						</tr>
						<tr id="Imp8">
							<td colspan="1">
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										onkeyup="mayus(this);" name="bRFC" id="bRFC" maxlength="12"
										value="<c:out value="${rfc}" />"> <label for="bRFC">*
										RFC</label>
								</div>
							</td>
							<td></td>
							<td colspan="3">
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="noFideicomiso" id="noFideicomiso" maxlength="40"
										value="<c:out value="${nrofideicomiso}" />"> <label
										for="noFideicomiso">* Nro. Fideicomiso</label>
								</div>
							</td>
						</tr>

						<tr>
							<td colspan="3"><label for="tipo">* Comprobante Acta
									Constitutiva</label><br /> <input type="hidden"
								id="idarchivoActaConstitutiva" name="idarchivoActaConstitutiva"
								value="<c:out value="${imagenactaconstitutiva}" />"> <input
								type="hidden" id="ActaConstitutivaZip"
								name="ActaConstitutivaZip" /> <input type="file"
								name="ActaConstitutiva" id="ActaConstitutiva"
								accept="document/pdf"
								onchange="validarArchivoActaConst('lupaActa', this, document.getElementById('ActaConstitutivaZip'), document.getElementById('covers'))">&nbsp;
								<a href="javascript:PreviewImage('ActaConstitutiva')"> <img
									src="img/lupa.jpg" height="32" width="32" style="display: none"
									id="lupaActa" />
							</a>
								<p class="help-block">Un solo archivo de máximo 25MB</p> <%
 	String idCLiente = idCliente;
 	String rutaOrigen = Rutas.rutaCarga + Rutas.separador + idCLiente + Rutas.separador
 			+ Rutas.dirActaConstitutivaBene + elNoBene + Rutas.separador;
 	String rutaDestino = Rutas.rutaDescarga + Rutas.separador + idCLiente + Rutas.separador
 			+ Rutas.dirActaConstitutivaBene + elNoBene + Rutas.separador;
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
 				//out.println("<p> Último archivo guardado:" + archivo.getName() + " <a href=\"javascript:window.open('" + dirRootDes  + "/" + Rutas.dirCedula + "/" + archivo.getName() + "')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"></a></p>" );
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
							<td colspan="3"><label for="tipo">* Carga de cédula
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
								<p class="help-block">Un solo archivo de máximo 10MB</p> <%
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

						<tr id="Imp9">
							<td colspan="3">
								<h4>Domicilio Dueño Beneficiario</h4>
							</td>

							<td></td>
						</tr>


						<tr id="Imp10">
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="bcalle" id="bcalle" maxlength="100"
										value="<c:out value="${calle}" />"> <label
										for="bcalle" class=""> * Calle</label>
								</div>

							</td>
							<td></td>

						</tr>
						<tr id="Imp11">
							<td colspan="2">
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="bexterior" id="bexterior" maxlength="56"
										value="<c:out value="${numexterior}" />"> <label
										for="bexterior" class=""> * No Exterior </label>
								</div>
							</td>

							<td colspan="2">
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="binterior" id="binterior" maxlength="40"
										value="<c:out value="${numinterior}" />"> <label
										for="binterior" class=""> * No Interior </label>
								</div>
							</td>
						</tr>
						<tr id="Imp12">
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
									<input class="form-control" type="text"
										onkeypress="return esNumero(this, event)" maxlength="5"
										name="bcp" id="bcp" value="<c:out value="${codpostal}" />">
									<label for="bcp" class=""> * Código Postal</label>
								</div>
							</td>
						</tr>
						<tr id="Imp13">
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
							<td><label id="Imp14"> * País </label> <select
								class="browser-default" name="bpaisDomicilio"
								id="bpaisDomicilio">
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
							</select>
						</tr>
						<tr id="Imp15">
							<td colspan="3">
								<h4>Datos y Documentos del representante legal</h4>
							</td>
						</tr>
						<tr id="Imp16">
							<td colspan="3">
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="bnombre" id="bnombre" maxlength="200"
										value="<c:out value="${rlnombre}" />"> <label
										for="bnombre" class=""> * Nombre(s) </label>
								</div>
							</td>
						</tr>
						<tr id="Imp17">
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="bpaterno" id="bpaterno" maxlength="200"
										value="<c:out value="${rlapellidopaterno}" />"> <label
										for="bpaterno" class=""> * Apellido paterno</label>
								</div>
							</td>
						<tr id="Imp18">

							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="bmaterno" id="bmaterno" maxlength="200"
										value="<c:out value="${rlapellidomaterno}" />"> <label
										for="bmaterno" class=""> * Apellido materno</label>
								</div>
							</td>
						</tr>
						<tr id="Imp19">

							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										onkeyup="mayus(this);" name="rlRFC" id="rlRFC" maxlength="13"
										value="<c:out value="${rlrfc}" />"> <label for="rlRFC">*
										RFC</label>
								</div>
							</td>

						</tr>
						<tr id="Imp20">

							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										onkeyup="mayus(this);" name="bCURP" id="bCURP" maxlength="18"
										value="<c:out value="${rlcurp}" />"> <label
										for="bCURP">* CURP</label>
								</div>
							</td>

						</tr>
						<tr id="Imp21">
							<td><input type="date" name="bfechaNacimiento"
								id="bfechaNacimiento" placeholder="* Fecha de constitucion"
								value="<c:out value="${rlfechanacimiento}" />"> <img
								src="img/calendar.png" height="32" width="32" /><label>*Fecha
									de Nacimiento</label></td>
							<td></td>

						</tr>
						<tr>
							<td><label id="Imp22">* Tipo ID </label> <input
								type="hidden" id="TipoIdentificacion" name="TipoIdentificacion"
								value="<c:out value="${rlidentifica_id}" />"> <select
								class="browser-default" class="" name="tipoIdentificacion"
								id="tipoIdentificacion">
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
							<td id="Imp23">
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										id="numeroId" name="numeroId" maxlength="40"
										value="<c:out value="${rlnumeroid}" />"> <label
										for="numeroId">* No de ID</label>
								</div>
							</td>
						</tr>
						<tr id="Imp24">
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
						<tr>
							<td colspan="3"><label for="tipo">* ID Representante
									legal</label><br /> <input type="hidden"
								id="idarchivoRepresentanteLegal"
								name="idarchivoRepresentanteLegal"
								value="<c:out value="${imagenrepresentantelegal}" />"> <input
								type="hidden" id="RepresentanteLegalZip"
								name="RepresentanteLegalZip" /> <input type="file"
								name="RepresentanteLegal" id="RepresentanteLegal"
								accept="document/pdf"
								onchange="validarArchivoIdLegal('lupaId', this, document.getElementById('RepresentanteLegalZip'), document.getElementById('covers'))">&nbsp;
								<a href="javascript:PreviewImage('RepresentanteLegal')"> <img
									src="img/lupa.jpg" height="32" width="32" style="display: none"
									id="lupaId" />
							</a>
								<p class="help-block">Un solo archivo de máximo 10MB</p> <%
 	idCLiente = idCliente;
 	rutaOrigen = Rutas.rutaCarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirIdentificacionBene
 			+ elNoBene + Rutas.separador;
 	rutaDestino = Rutas.rutaDescarga + Rutas.separador + idCLiente + Rutas.separador
 			+ Rutas.dirIdentificacionBene + elNoBene + Rutas.separador;
 	listaArchivos = new File(rutaOrigen).listFiles();
 	if (listaArchivos != null && listaArchivos.length > 0) {
 		Arrays.sort(listaArchivos, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
 		out.println("<INPUT type='hidden' id='HayArchivoId' name='HayArchivoId' value='si'");
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
 						+ dirRootDes + "/" + Rutas.dirIdentificacionBene + elNoBene + "/"
 						+ listaArchivos[i].getName()
 						+ "', 'clearcache=yes')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>");
 			} catch (Exception es) {
 				es.printStackTrace();
 			}

 		} //for
 	} else {
 		out.println("<INPUT type='hidden' id='HayArchivoId' name='HayArchivoId' value='no'");
 	}
 %></td>
						</tr>
						<tr>
							<td colspan="3"><label for="tipo">* Poder notarial</label><br />
								<input type="hidden" id="idarchivoPoderNotarial"
								name="idarchivoPoderNotarial"
								value="<c:out value="${imagenpodernotarial}" />"> <input
								type="hidden" id="PoderNotarialZip" name="PoderNotarialZip" />
								<input type="file" name="PoderNotarial" id="PoderNotarial"
								accept="document/pdf"
								onchange="validarArchivoPoderNotarial('lupaPoderN', this, document.getElementById('PoderNotarialZip'), document.getElementById('covers'))">&nbsp;
								<a href="javascript:PreviewImage('PoderNotarial')"> <img
									src="img/lupa.jpg" height="32" width="32" style="display: none"
									id="lupaPoderN" />
							</a>
								<p class="help-block">Un solo archivo de máximo 25MB</p> <%
 	idCLiente = idCliente;
 	rutaOrigen = Rutas.rutaCarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirRlPoderNotarialBene
 			+ elNoBene + Rutas.separador;
 	rutaDestino = Rutas.rutaDescarga + Rutas.separador + idCLiente + Rutas.separador
 			+ Rutas.dirRlPoderNotarialBene + elNoBene + Rutas.separador;

 	listaArchivos = new File(rutaOrigen).listFiles();
 	if (listaArchivos != null && listaArchivos.length > 0) {
 		Arrays.sort(listaArchivos, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
 		out.println("<INPUT type='hidden' id='HayArchivoPoderN' name='HayArchivoPoderN' value='si'");
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
 				//new Archivos().copyFile(listaArchivos[i].getAbsolutePath(), archivoDestino.getAbsolutePath());
 				out.println("<p>" + listaArchivos[i].getName() + " <a href=\"javascript:window.open('"
 						+ dirRootDes + "/" + Rutas.dirRlPoderNotarialBene + elNoBene + "/"
 						+ listaArchivos[i].getName()
 						+ "', 'clearcache=yes')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>");
 				//out.println("<p> Último archivo guardado:" + archivo.getName() + " <a href=\"javascript:window.open('" + dirRootDes  + "/" + Rutas.dirCedula + "/" + archivo.getName() + "')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"></a></p>" );
 			} catch (Exception es) {
 				es.printStackTrace();
 			}

 		} //for
 	} else {
 		out.println("<INPUT type='hidden' id='HayArchivoPoderN' name='HayArchivoPoderN' value='no'");
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
						<!-- comienza la información del beneficiario -->

						<iframe id="frameContenedor" src="beneficiario_fideicomiso.jsp"
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
						href="javascript:imprimir(Imp1,Imp2,Imp3,Imp4,Imp5,Imp55,Imp6,Imp7,Imp8,Imp9,Imp10,Imp11,Imp12,Imp13,Imp14,Imp15,Imp16,Imp17,Imp18,Imp19,Imp20,Imp21,Imp22,Imp23,Imp24)">Imprimir
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
							txtEstatus = '${estadoCliente}';
							txtPerfilId = '${perfilId}';
							//alert(txtEstatus);
							document.getElementById('btnClose').style.visibility = 'hidden';

							if (txtEstatus == 'A') {
								//                                alert("entra validado");
								document.getElementById("btn-procesar").style.visibility = 'hidden';
								document.getElementById('btnClose').style.visibility = 'visible';
								var form = document
										.getElementById('frmBenefideicomiso');
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
										.getElementById('frmBenefideicomiso');
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
										.getElementById('frmBenefideicomiso');
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
										.getElementById('frmBenefideicomiso');
								var elements = form.elements;
								for (var i = 0, len = elements.length; i < len; ++i) {
									//alert("for");
									elements[i].readOnly = true;
									elements[i].disabled = true;
									document.getElementById('btnClose').disabled = false;
								}
							}
							//ASIGNANDO MEXICO COMO PAÍS PREDETERMINADO
							//btelPais
							var selector = document.getElementById('btelPais');
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

							//Cargando los datos de edición
							esEdicion = document.getElementById('esEdicion').value;

							if (esEdicion == '1') {

								//ASIGNANDO EL PAÍS TELÉFONO
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

								//ASIGNANDO EL PAÍS DOMICILIO
								txtPais = '${pais}';
								if (txtPais != null || txtPais.length > 0) {
									var selector = document
											.getElementById('bpaisDomicilio');
									for (var i = 0; i < selector.length; i++) {
										if (txtPais == selector.options[i].value) {
											selector.selectedIndex = i;
											break;
										} else {//if
											selector.selectedIndex = 0;
										}
									}//for 
								}

							}//esEdicion
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

						});
	</script>

	<script>
		function validarBeneficiarioFideicomiso() {
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
				alert('La razón social no puede dejarse en blanco');
				document.getElementById('bRazonSocial').focus();
				return false;
			}

			txtbcp = document.getElementById('bcp').value;
			if (txtbcp.length != 0) {
				if (txtbcp.length != 5) {
					alert('La longitud del código postal debe ser de cinco dígitos');
					document.getElementById('bcp').focus();
					return false;
				}
			}
			txtbRFC = document.getElementById('bRFC').value;
			if (txtbRFC.length > 0) {
				if (txtbRFC.length != 12) {
					alert('La longitud del RFC debe ser de 12 caracteres');
					document.getElementById('bRFC').focus();
					return false;
				}
			}

			txtrlRFC = document.getElementById('rlRFC').value;
			if (txtrlRFC.length > 0) {
				if (txtrlRFC.length != 13) {
					alert('La longitud del RFC debe ser de 13 caracteres');
					document.getElementById('rlRFC').focus();
					return false;
				}

			}
			txtFechaNacimiento = document.getElementById('bfechaNacimiento').value;
			if (txtrlRFC.length == 13 && txtFechaNacimiento <= 0) {
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

			txtbcurp = document.getElementById('bCURP').value;
			if (txtbcurp.length > 0) {
				if (txtbcurp.length != 18) {
					alert('La longitud del CURP debe ser de 18 caracteres');
					document.getElementById('bCURP').focus();
					return false;
				}
			}
			txttelefono = document.getElementById('btelefono').value;
			if (txttelefono.length !== 0) {
				if (txttelefono.length !== 10) {
					alert('El Número de Teléfono debe ser de 10 digítos');
					document.getElementById('btelefono').focus();
					return false;
				}
			}
			
			if (document.getElementById('ActaConstitutiva').value.length == 0)
				document.getElementById('ActaConstitutivaZip').value = '';
			if (document.getElementById('cedulaPF').value.length == 0)
				document.getElementById('cedulaPFZip').value = '';
			if (document.getElementById('RepresentanteLegal').value.length == 0)
				document.getElementById('RepresentanteLegalZip').value = '';
			if (document.getElementById('PoderNotarial').value.length == 0)
				document.getElementById('PoderNotarialZip').value = '';
			
			localStorage.setItem('capturado', '1');
			
			document.getElementById('btn-procesar').disabled=true;
			alert("Validando RFC y CURP");
			if(validarRFC() && validarRFC2() && validarCURP()){
				document.getElementById('btn-procesar').disabled=false;
				return true;
			}else{
				document.getElementById('btn-procesar').disabled=false;
			return false;
		}
			
		}

	</script>
	
	<script>
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
				Imp17, Imp18, Imp19, Imp20, Imp21, Imp22, Imp23, Imp24) {
			//alert(""+tipPer);
			//alert(""+Estats);
			var printContents = document.getElementById('Imp1').innerHTML
					+ document.getElementById('Imp2').innerHTML
					+ document.getElementById('Imp3').innerHTML
					+ document.getElementById('Imp4').innerHTML
					+ document.getElementById('btelPais').options[document
							.getElementById('btelPais').selectedIndex].text
					+ document.getElementById('Imp5').innerHTML
					+ document.getElementById('Imp55').innerHTML
					+ document.getElementById('Imp6').innerHTML
					+ document.getElementById('Imp7').innerHTML
					+ document.getElementById('Imp8').innerHTML
					+ document.getElementById('Imp9').innerHTML
					+ document.getElementById('Imp10').innerHTML
					+ document.getElementById('Imp11').innerHTML
					+ document.getElementById('Imp12').innerHTML
					+ document.getElementById('Imp13').innerHTML
					+ document.getElementById('Imp14').innerHTML
					+ document.getElementById('bpaisDomicilio').options[document
							.getElementById('bpaisDomicilio').selectedIndex].text
					+ document.getElementById('Imp15').innerHTML
					+ document.getElementById('Imp16').innerHTML
					+ document.getElementById('Imp17').innerHTML
					+ document.getElementById('Imp18').innerHTML
					+ document.getElementById('Imp19').innerHTML
					+ document.getElementById('Imp20').innerHTML
					+ document.getElementById('Imp21').innerHTML
					+ document.getElementById('Imp22').innerHTML
					+ document.getElementById('tipoIdentificacion').options[document
							.getElementById('tipoIdentificacion').selectedIndex].text
					+ document.getElementById('Imp23').innerHTML
					+ document.getElementById('Imp24').innerHTML;
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

