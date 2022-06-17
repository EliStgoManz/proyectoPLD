<%@page import="control.DigitoAlfanumerico"%>
<%@page import="datosRatos.ConsultaWS"%>
<%@page import="entidad.Representante"%>
<%@page import="rfcCurpAppi.token"%>
<%@page import="listaEntidad.*"%>
<%@page import="datosRatos.DatosRepLegal"%>
<%@page import="datosRatos.DatosClienteRaro"%>
<%@page import="entidad.Cliente"%>
<%@page import="entidad.Perfil"%>
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
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.parser.JSONParser"%>
<%@page import="org.json.simple.parser.ParseException"%>
<%@page import="java.util.Calendar"%>
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
		//esta es la que puse
		request.setAttribute("perfiliddd", perfilId);
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
				<div id="Imp1" class="col-12 col-sm-12 col-md-5 col-lg-8 col-xl-7">
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
				<article id="Imp2" class="col-xs-12 col-sm-8 col-md-9 col-lg-9">
					<h2 class="text-muted">
						Datos y Documentos del Cliente Gobierno, Incluyendo <small>Ayuntamientos,
							Estados, Municipios y Federaciones.</small>
					</h2>
					<%
				  Calendar cal= Calendar.getInstance();
				  int year= cal.get(Calendar.YEAR);
				  out.println("<p id='indicadorAlfa' style='visibility: hidden'>INDICADOR: "+new DigitoAlfanumerico().calcularCodigoAlfaNumerico(vRFCParam,year)+"</p>");
				
				%>
				</article>
				<aside id="Imp3" class="col-xs-12 col-sm-4 col-md-3 col-lg-3">
					<p class="lead">
						Bienvenido:
						<%@include file="detectausuario.jsp"%>
						<!--   Bienvenido:  out.println( vClienteParam );    -->
					</p>
				</aside>
			</div>
			<div class="row">
				<div class="col-md-9 table-responsive">
					<form onsubmit="return validarFomularioGobiernoS()"
						action="VerificacionGobierno" method="post" id="frmGobierno"
						name="frmGobierno" enctype="multipart/form-data">
						<input type="hidden" id="tipoPersona" name="tipoPersona"
							value="<c:out value="${tipopersona}" />"> <input
							type="hidden" id="tipoPersonaCambio" name="tipoPersonaCambio"
							value="<c:out value="${tipopersona}" />"> <input
							type="hidden" id="Cliente_Id" name="Cliente_Id"
							value="<%out.print(vClienteParam);%>"> <input
							type="hidden" name="respuesta" id="respuesta"
							value="<c:out value="${resultado}" />"> <input
							type="hidden" name="exito" id="exito"
							value="<c:out value="${exito}" />"> <input
							id="esEdicion" name="esEdicion" type="hidden"
							value="<c:out value="${esEdicion}" />">
						<!-- CAMPOS PARA COMPLETAR LA VERIFICACION -->
						<input id="usuarioasignado" name="usuarioasignado" type="hidden"
							value="<c:out value="${usuarioasignado}" />"> <input
							id="fechacorte" name="fechacorte" type="hidden"
							value="<c:out value="${fechacorte}" />"> <input
							id="borrado" name="borrado" type="hidden"
							value="<c:out value="${borrado}"/>">
							<input type="hidden" id="idRiesgo" name="idRiesgo" value="<c:out value="${riesgo}" />">
						<%
							ArrayList listaPaises = new DatosPais().getList();
							ArrayList listaTipoIdentificacion = new DatosTipoIdentifiacion().getList();
							ArrayList listaGiro = new DatosGiro().getList("");
							//  UsuarioTransitivo.setSiBeneficiario("0");
						%>
						<table border="0">
						<input type="hidden" id="EstatusAnterior" name="EstatusAnterior" value="<c:out value="${estado}" />">
                                                                                                   
							<tr>
								<td><label id="Imp4" for="Estatus">* Estatus</label><br />
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
												name="chkFechaBloqueo" class=""> <label id="Imp5">
												Fecha de bloqueo </label><br>
											<br> <input id="Imp55" type="date" id="fechaBloqueo"
												name="fechaBloqueo"
												value="<c:out value="${fechabloqueo}" />">

										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td id="Imp6"><label for="fechaValidacion">* Fecha
										de validación</label><br /> <input type="date" id="fechaValidacion"
									name="fechaValidacion" class="form-control"
									value="<c:out value="${fechavalidado}" />" readonly></td>
								<td></td>
								<td id="Imp7"><label for="fechaModificacion">*
										Fecha de Alta.</label><br /> <input type="date"
									id="fechaModificacion" name="fechaModificacion"
									value="<c:out value="${fecharegistro}" />" readonly></td>
							</tr>
							<tr>
								<td colspan="3"><label for="tipo">Mensaje:</label><br /> <textarea
										id="mensaje" name="mensaje" class="form-control"
										value="<c:out value="${mensaje}" />">
							</textarea></td>
							</tr>
							<tr>
								<td colspan="3"><label for="tipo">Notas:</label><br /> <textarea
										id="notas" name="notas" class="form-control"
										value="<c:out value="${notas}"/> ">
							</textarea></td>
							</tr>
							<tr>
								<td id="Imp8" width="350px"><label for="id">Id del
										cliente:</label><br /> <label for="id">
										<%
											out.println(vClienteParam);
										%>
								</label></td>
								<td width="50px"></td>
								<td><label id="Imp9" for="tipoP">Tipo de Persona: </label>
									<select class="browser-default" name="tipoP" id="tipoP"
									onchange="cambiarTipoPersona()">
										<option value="">* Tipo</option>
										<!--<option value="F">Física</option>-->
										<option value="M">Moral</option>
										<option value="X">Fideicomiso</option>
										<option value="G">Gobierno</option>
								</select></td>


							</tr>
							<tr id="Imp10">
								<td width="350px">* Correo electrónico </br> <input
									class="form-control" type="email" onkeyup="minus(this)"
									name="correo" id="correo" maxlength="60"
									value="<%out.print(vMailParam);%>">
								</td>
							</tr>
							<tr>
								<td><label id="Imp11">* Tel. país </label> <input
									type="hidden" id="idTelPais" name="idTelPais"
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
								<td id="Imp12">
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="telefono" name="telefono"
											onkeypress="return esNumero(this, event)" maxlength="10"
											value="<c:out value="${telefono}" />"> <label
											for="telefono">* Teléfono</label>
									</div>
								</td>
							</tr>
							<tr id="Imp13">
								<td colspan="3">
									<h4>Datos y Documentos</h4>
								</td>
							</tr>
							<tr id="Imp14">
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
								<td id="Imp15"><input class="" type="date"
									id="fechaCreacion" name="fechaCreacion"
									value="<c:out value="${fechacreacion}" />"> <img
									src="img/calendar.png" height="32" width="32" /> <label
									for="fechaCreacion">* Fecha creación </label></td>
								<td></td>
								<td id="Imp16">
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="rfcCliente" maxlength="12" name="rfcCliente"
											value="<%out.print(vRFCParam);%>" readonly> <label
											for="rfcCliente">* RFC</label>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="3"><br> <label id="Imp17">*
										Actividad u objeto social </label> <input type="hidden"
									id="idActividad" name="idActividad"
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

								<td><label id="Imp18" for="buscarGiro">* Pais </label> <input
									type="hidden" id="idNacionalidadCliente"
									name="idNacionalidadCliente"
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
 		out.println("<INPUT type='hidden' id='HayArchivoExistencia' name='HayArchivoExistencia' value='si'");
 		int a = 0;
 		if ((perfilId.compareTo("1") == 0) || (perfilId.compareTo("2") == 0)) {
 			//Puede ver el historial                                                                                                                                                                                
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
 				//out.println("<p>" + listaArchivos[i].getName() + " <a href=\"/" + dirRootDes + "/" + idCLiente + Rutas.separador + Rutas.dirAreditaacion + Rutas.separador + listaArchivos[i].getName() + "\" target=\"_blank\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>" );
 				out.println("<p>" + listaArchivos[i].getName() + " <a href=\"javascript:window.open('"
 						+ dirRootDes + "/" + Rutas.dirAreditaacion + "/" + listaArchivos[i].getName()
 						+ "')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>");
 			} catch (Exception es) {
 				es.printStackTrace();
 			}

 		}
 		//for
 	} else {
 		out.println("<INPUT type='hidden' id='HayArchivoExistencia' name='HayArchivoExistencia' value='no'");
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
 						+ "')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>");
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
							<tr>
								<td colspan="3" id="Imp19">
									<h4>Domicilio</h4>
								</td>
								<td></td>
							</tr>

							<td></td>
							</tr>
							<tr>
								<td>
									<div id="Imp20" class="md-form form-sm">
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
							<tr id="Imp23">
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
										<input class="form-control" type="text"
											onkeypress="return esNumero(this, event)" id="cp" name="cp"
											maxlength="5" value="<c:out value="${codpostal}" />">
										<label for="cp"> * Código Postal</label>
									</div>
								</td>
							</tr>
							<tr>
								<td id="Imp24">
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="estado" name="estado" maxlength="100"
											value="<c:out value="${estado_prov}" />"> <label
											for="estado"> * Estado</label>
									</div>
								</td>
								<td></td>
								<td><label id="Imp25" for="estado"> * País </label> <input
									type="hidden" id="idPais" name="idPais"
									value="<c:out value="${paisDomicilio}" />"> <select
									class="browser-default" name="pais" id="pais"
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
										domicilio</label> <input type="hidden" id="idarchivoComprobanteDom"
									name="idarchivoComprobanteDom"
									value="<c:out value="${imagenComrpobanteDomicilio}" />">
									<br> <input type="hidden" id="archivoDomicilioZip"
									name="archivoDomicilioZip" /> <input type="file"
									name="archivoDomicilio" id="archivoDomicilio"
									accept="document/pdf"
									onchange="validarArchivoComprobanteDomicilio('lupaDomicilio', this, document.getElementById('archivoDomicilioZip'), document.getElementById('covers'))">&nbsp;
									<div class="popup" onclick="myFunction()">
										<img src="img/signo.jpg" height="32" width="32" /> <span
											class="popuptext" id="myPopup">Recibo de pago por
											servicios domiciliados o Estados de cuenta bancarios, con
											antigüedad no mayor a tres meses a su fecha de emisión,
											Contrato de arrendamiento vigente, Constancia de Inscripción
											en el RFC.</span>
									</div>
									<br /> <a href="javascript:PreviewImage('archivoDomicilio')">
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
 		out.println(
 				"<INPUT type='hidden' id='HayArchivoComprobanteDom' name='HayArchivoComprobanteDom' value='si'");
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
 						+ "')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>");
 			} catch (Exception es) {
 				es.printStackTrace();
 			}

 		} //for
 	} else {
 		out.println(
 				"<INPUT type='hidden' id='HayArchivoComprobanteDom' name='HayArchivoComprobanteDom' value='no'");
 	}
 %></td>
							</tr>
							</tr>
							<tr id="Imp26">
								<td colspan="3">
									<h4>Datos del funcionario público que realiza el acto</h4>
								</td>
							</tr>
							<tr id="Imp27">
								<td colspan="3">
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="fnombre" name="fnombre" maxlength="200" size="80"
											value="<c:out value="${rlnombre}" />"> <label
											for="fnombre">* Nombre(s)</label>
									</div>
								</td>
							</tr>
							<tr id="Imp28">
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
							<tr id="Imp29">
								<td><input class="" type="date" id="fFechaNacimiento"
									name="fFechaNacimiento"
									value="<c:out value="${rlfechanacimiento}" />"> <img
									src="img/calendar.png" height="32" width="32" /> <label
									for="fFechaNacimiento">* Fecha nacimiento</label><br>
								<br></td>
								<td></td>
								<td></td>
							</tr>
							<tr id="Imp30">
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
											id="fCURP" name="fCURP" maxlength="18"
											value="<c:out value="${rlcurp}" />"> <label
											for="fCURP">* CURP</label>
									</div>
								</td>
							</tr>
							<tr>
								<td><label id="Imp31" for="tipoIdentificacion">*
										Tipo ID </label> <input type="hidden" id="idTipoId" name="idTipoId"
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
								<td id="Imp32">
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="fNumeroId" name="fNumeroId" maxlength="40"
											value="<c:out value="${rlnumeroid}" />"> <label
											for="fNumeroId">* No. Id</label>
									</div>
								</td>
							</tr>
							<tr id="Imp33">
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
 						+ "')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>");
 			} catch (Exception es) {
 				es.printStackTrace();
 			}

 		} //for
 	} else {
 		out.println("<INPUT type='hidden' id='HayArchivofID' name='HayArchivofID' value='no'");
 	}
 %></td>
							</tr>
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
 		out.println("<INPUT type='hidden' id='HayArchivoFacultades' name='HayArchivoFacultades' value='si'");
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
 				//out.println("<p>" + listaArchivos[i].getName() + " <a href=\"/" + dirRootDes + "/" + idCLiente + Rutas.separador + Rutas.dirFacultades + Rutas.separador + listaArchivos[i].getName() + "\" target=\"_blank\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>" );
 				out.println("<p>" + listaArchivos[i].getName() + " <a href=\"javascript:window.open('"
 						+ dirRootDes + "/" + Rutas.dirFacultades + "/" + listaArchivos[i].getName()
 						+ "')\"> <img src=\"img/lupa.jpg\" height=\"32\" width=\"32\"> </a>");
 			} catch (Exception es) {
 				es.printStackTrace();
 			}

 		} //for
 	} else {
 		out.println("<INPUT type='hidden' id='HayArchivoFacultades' name='HayArchivoFacultades' value='no'");
 	}
 %></td>
							</tr>

							<tr>
								<td>
									<!-- <button class="btn" >Agregar beneficiario</button>--> <%
 	
 %>
								</td>
							</tr>

							<tr id="Imp34">
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
 		System.out.println("Excepción durante la carga de la tabla de replegal");
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
 %>
								</td>

							</tr>

							<tr id="Imp35">
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
												<label id="Imp36" for="declaroOrigen" class="">
													Declaro para todos los efectos a que haya lugar, que los
													recursos utilizados para obtener los servicios
													proporcionados por Efectivale son de origen lícito. </label><br>
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
													name="declaroBeneficiario"> <label id="Imp37"
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
							
							
<!-- 		<tr> -->
<!-- <td colspan=4> -->
									
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
//  			out.println("<tr><td class='TitleCell' >Fuentes externas:</td><td class='InfoCell'>"+json1.get("externalSources").toString().replaceAll(" ","      ")+"</td><td  valign=top >Info adicional:</td><td class='InfoCell' width=200>"+json1.get("furtherInfo")+"</td><td class='TitleCell' >Sexo:</td><td class='InfoCell'>"+json1.get("genero")+"</td></tr>"); 			
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
	 	
<!-- </td> -->
<!-- </tr> -->
					
	
							
							
							
	
							<tr>
								<td><a class="boton_personalizado2" id="btnClose"
									style="visibility: hidden" href="estatus_clientes.jsp">Cerrar</a>
								</td>
								<td></td>
								<td>
									<button class="btn btn-danger" id="btnGuardar"
										name="btnGuardar ">Guardar</button>
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
					%>
					<br> <a id="Impr"
						href="javascript:imprimir(Imp1,Imp2,Imp3,Imp4,Imp5,Imp55,Imp6,Imp7,Imp8,Imp9,Imp10,Imp11,Imp12,Imp13,Imp14,Imp15,Imp16,Imp17,Imp18,Imp19,Imp20,Imp21,Imp22,Imp23,Imp24,Imp25,Imp26,Imp27,Imp28,Imp29,Imp30,Imp31,Imp32,Imp33,Imp34,Imp35,Imp37,Imp38)">Imprimir
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
		<footer id="Imp38">
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
		<script src="js/jquery-1.12.4.js"></script>
		<script src="js/jquery-ui.js"></script>
		<script src="js/jquery.autocomplete.js"></script>



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
								//     $("#covers").hide();
								PERFIL_ADMINISTRADOR = 1;
								PERFIL_PLD = 2;
								document.getElementById("chkFechaBloqueo").disabled = true;
								document.getElementById("check").style.visibility = 'hidden';
								document.getElementById("no_check").style.visibility = 'hidden';
								esEdicion = document
										.getElementById('esEdicion').value;

								if (esEdicion == 1) { //la carga de la página proviene de una edición del usuario y no de un nuevo usuario
									/*
									 *  REGLAS DE NEGOCIO
									 */

									//Este formulario genera un candado ya que una vez verificado ya ni el 
									//cliente ni los perfiles de PLD podrán realizar modificaciones, 
									//únicamente el administrador del sistema.
									txtEstatus = '${estado}';
									if (txtEstatus == 'A') {
										//                                document.getElementById("btnGuardar").disabled = true;
										document.getElementById("btnGuardar").style.visibility = 'hidden';
										document.getElementById('btnClose').style.visibility = 'visible';
										var form = document
												.getElementById("frmGobierno");
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
									if (txtPerfilUsuarioSistema == PERFIL_ADMINISTRADOR||txtPerfilUsuarioSistema == PERFIL_PLD ) {
										//document.getElementById("btnGuardar").disabled = false;
										document.getElementById("btnGuardar").style.visibility = 'visible';
										document.getElementById("btnClose").style.visibility = 'hidden';
										document
												.getElementById("chkFechaBloqueo").disabled = false;
										//                                 document.getElementById("fechaBloqueo").disabled = false;
										document.getElementById('Hist').style.visibility = 'visible';
										document.getElementById('Impr').style.visibility = 'visible';
										//El mensaje y notas

										document.getElementById('notas').value = '${notas.trim()}';

										var form = document
												.getElementById("frmGobierno");
										var elements = form.elements;
										for (var i = 0, len = elements.length; i < len; ++i) {
											elements[i].readOnly = false;
											elements[i].disabled = false;
										}

									} 
// 									else if (txtPerfilUsuarioSistema == PERFIL_PLD) {
// 										document.getElementById('notas').value = '${notas.trim()}';
// 										document.getElementById('Hist').style.visibility = 'visible';
// 										document.getElementById('Impr').style.visibility = 'visible';

// 									}
									else {
										document.getElementById('Hist').style.visibility = 'hidden';
										document.getElementById('Impr').style.visibility = 'hidden';
									}

									//Aplicando los persmisos si aún no ha sido verificado
									if (txtPerfilUsuarioSistema !== PERFIL_ADMINISTRADOR
											&& txtEstatus !== 'A') {
										txtPermiso = '${permiso}';
										if (txtPermiso == 'L') {
											//document.getElementById("btnGuardar").disabled = true;
											document
													.getElementById("btnGuardar").style.visibility = 'hidden';
											document.getElementById("btnRep").style.visibility = 'hidden';
											document.getElementById("btnAct2").style.visibility = 'hidden';
											document.getElementById('btnClose').style.visibility = 'visible';
											var form = document
													.getElementById("frmGobierno");
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
									if (txtEstatus != null
											|| txtEstatus.length > 0) {
										var selector = document
												.getElementById('Estatus');
										for (var i = 0; i < selector.length; i++) {
											if (txtEstatus == selector.options[i].value) {
												selector.selectedIndex = i;
												break;
											} else {//if
												selector.selectedIndex = 0;
											}
										}//for 
									}

									//El tipo de  persona 
									txtTipoPersona = '${tipopersona}';
									if (txtTipoPersona != null
											|| txtTipoPersona.length > 0) {
										var selector = document
												.getElementById('tipoP');
										for (var i = 0; i < selector.length; i++) {
											if (txtTipoPersona == selector.options[i].value) {
												selector.selectedIndex = i;
												break;
											} else {//if
												selector.selectedIndex = 0;
											}
										}//for 
									}

									//Check bloqueado
									isBloqueado = '${bloqueado}';
									if (isBloqueado == 'true') {
										document
												.getElementById('chkFechaBloqueo').checked = true;
									} else {
										document
												.getElementById('chkFechaBloqueo').checked = false;
									}

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
									txtidGiro = document
											.getElementById('idGiro').value; //llega de la respuesta
									if (txtidGiro != null
											|| txtidGiro.length > 0) {
										var selector = document
												.getElementById('giro');

										for (var i = 0; i < selector.length; i++) {
											if (txtidGiro == selector.options[i].value) {
												selector.selectedIndex = i;
												//                                document.getElementById('buscarGiroObjeto').value = selector.options[selector.selectedIndex].text;
												break;
											} else {//if
												selector.selectedIndex = 0;
											}
										}//for 
									}
                                                                        
                                                                        //Mostrando los check de la deslindacion  
//                                                                         txtRiesgo = document.getElementById('idRiesgo').value;
//                                                                         document.getElementById('riesgo').checked = false;
//                                                                         if (txtRiesgo == "1") {
//                                                                            document.getElementById('riesgo').checked = true;
//                                                                         }
                        
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
			function validarFomularioGobiernoS() {
				if (validarFormularioGobierno()) {

					$('.spinner-wrapper').fadeIn('fast');
					Prog();
					return true;
				} else {
					return false;
				}
			}

			function validarFormularioGobierno() {

				txtIsBloqueo = document.getElementById('chkFechaBloqueo').checked;
				if (txtIsBloqueo) {
					txtFechaBloqueo = document.getElementById('fechaBloqueo').value;
					if (txtFechaBloqueo.length <= 0 || txtFechaBloqueo == '') {
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
						alert('El texto del mensaje debe estar capturado');
						document.getElementById('mensaje').focus();
						return false;
					}
				}
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
				

				//DOCUMENTO PARA ACREDITAR LA EXISTENCIA
				if (txtEstatus == "A") {
					txtarchivoExistencia = document
							.getElementById('archivoExistencia').value;
					txtHayArchivoExistencia = document
							.getElementById('HayArchivoExistencia').value;
					if (txtarchivoExistencia.length == 0) {
						if (txtHayArchivoExistencia === 'no') {
							alert('Para poder validar debe subir un Documento para Acreditar la Existencia');
							document.getElementById('archivoExistencia')
									.focus()
							return false;
						}
					}
				}

				//CÉDULA FISCAL
				if (txtEstatus == "A") {
					txtarchivoCedula = document.getElementById('archivoCedula').value;
					txtHayArchivoCedula = document
							.getElementById('HayArchivoCedula').value;
					if (txtarchivoCedula.length == 0) {
						if (txtHayArchivoCedula === 'no') {
							alert('Para poder validar debe subir un archivo de Cédula Fiscal');
							document.getElementById('archivoCedula').focus()
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
				if (txtcp.length !== 0) {
					if (txtcp.length !== 5) {
						alert('El Código Postal debe ser de 5 caracteres');
						document.getElementById('cp').focus();
						return false;
					}
				}

				//COMPROBANTE DE COMICILIO
				if (txtEstatus == "A") {
					txtarchivoDomicilio = document
							.getElementById('archivoDomicilio').value;
					txtHayArchivoComprobanteDom = document
							.getElementById('HayArchivoComprobanteDom').value;
					if (txtarchivoDomicilio.length == 0) {
						if (txtHayArchivoComprobanteDom === 'no') {
							alert('Para poder validar debe subir un archivo de Comprobante de Domicilio');
							document.getElementById('archivoDomicilio').focus()
							return false;
						}
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

				//Validando longitud del CURP
				txtCURP = document.getElementById('fCURP').value;
				if (txtCURP.length > 0 && txtCURP.length != 18) {
					alert('El CURP del funcionario público debe ser de 18 caracteres');
					document.getElementById('fCURP').focus();
					return false;
				}
				tipoIdentificacion = document.getElementById('idTipoId').value;
				txtotroTipoId = document.getElementById('fotroTipoId').value;
				if (txtotroTipoId == null || txtotroTipoId.length == 0) {
					if (tipoIdentificacion == null
							|| tipoIdentificacion.length == 0
							|| /^\s+$/.test(tipoIdentificacion)) {
						alert('Debe selccionar un tipo de identificacion para el Funcionario Público');
						document.getElementById('idTipoId').focus();
						return false;
					}
				}

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
				//ID FUNCIONARIO
				if (txtEstatus == "A") {
					txtarchivofId = document.getElementById('archivofId').value;
					txtHayArchivofID = document.getElementById('HayArchivofID').value;
					if (txtarchivofId.length == 0) {
						if (txtHayArchivofID === 'no') {
							alert('Para poder validar debe subir un archivo de ID del Funcionario Público');
							document.getElementById('archivofId').focus()
							return false;
						}
					}
				}
				
				//DOUMENTO FACULTADES FUNCIONARIO
				if (txtEstatus == "A") {
					txtarchivofFacultades = document
							.getElementById('archivofFacultades').value;
					txtHayArchivoFacultades = document
							.getElementById('HayArchivoFacultades').value;
					if (txtarchivofFacultades.length == 0) {
						if (txtHayArchivoFacultades === 'no') {
							alert('Para poder validar debe subir un archivo de Facultades del Funcionario Público');
							document.getElementById('archivofFacultades')
									.focus()
							return false;
						}
					}
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
                                
                        
				// console.log( document.getElementById('archivoCedulaZip').value);
				if (document.getElementById('archivoExistencia').value.length == 0)
					document.getElementById('archivoExistenciaZip').value = '';
				if (document.getElementById('archivoCedula').value.length == 0) {
					document.getElementById('archivoCedulaZip').value = '';

				}

				if (document.getElementById('archivoDomicilio').value.length == 0)
					document.getElementById('archivoDomicilioZip').value = '';
				if (document.getElementById('archivofId').value.length == 0)
					document.getElementById('archivofIdZip').value = '';
				if (document.getElementById('archivofFacultades').value.length == 0)
					document.getElementById('archivofFacultadesZip').value = '';

				txtdeclaroBeneficiario = document.getElementById('declaroBeneficiario').checked;
				txtdeclaroOrigen = document.getElementById('declaroOrigen').checked;
				txtCapturado = localStorage.getItem('capturado');

				//        return validarCapturaBeneficiario();
                                
				document.getElementById('btnGuardar').disabled=true;
				
               alert("Validando RFC y CURP");
			if (validarRFC() && validarRFC2() && validarCURP()) {
				
				document.getElementById('btnGuardar').disabled=false;
				return true;
			} else {
				document.getElementById('btnGuardar').disabled=false;
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
		<script>
	function validarRFC(){
		var txtbRFC = document.getElementById('rfcCliente').value;

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
			    	document.getElementById('rfcCliente').focus();
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
//  		function validarDeslindamiento() {
//  				txtEstatus = document.getElementById('Estatus').value;
//  	 			txtRiesgo = document.getElementById('idRiesgo').value;
//  	 			txtDes = document.getElementById('Descripcion').value;
 	 			
//  	 			if(txtRiesgo == "1" && txtEstatus == "A"){
//  	 				if (txtDes == null || txtDes.length == 0 || /^\s+$/.test(txtDes)) {
//  						    alert('Debe capturar el motivo en Descripción');
//  						    document.getElementById('Descripcion').focus();
//  						return false;
//  					}else{
//  						document.getElementById('idRiesgo').value ='0';
//  						return true;
//  					}
 	 				
//  	 			}else{
//  	 				return true;
//  	 			} 			
//  	 		}
     </script> 
                
		<script>
			function Representante() {
				estadoCliente = document.getElementById('estadoCliente').value;
				esEdicion = document.getElementById('esEdicion').value;

				window.open('alta_representante.jsp?esEdicion='
						+ document.getElementById('esEdicion').value
						+ '&estadoCliente='
						+ document.getElementById('estadoCliente').value,
						'_blank');
			}

			//         
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
									console.log(obj.Representantes[i].nrorep);
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
				alert("validarActividadEco");
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
				window.open('alta_representante.jsp?esEdicion=1&idCliente='
						+ vIdCliente, '_blank');
			}
			function Historial(vIdCliente) {
				window.open('historial_cambios.jsp?idCliente=' + vIdCliente,
						'_blank');
			}

			function setFechaValidacion() {
				if (document.getElementById('Estatus').value == 'A') {
					document.getElementById('fechaValidacion').valueAsDate = new Date();

				} else {
					document.getElementById('fechaValidacion').value = '';
				}
			}

			function validarGiro() {
				//Asigna al select el valor lozalizado en la lista de actividades escrita
				alert("validarGiro");
				txtGiro = document.getElementById('buscarGiro').value;
				var selector = document.getElementById('giro');
				if (txtGiro != null || txtGiro.length > 0) {
					for (var i = 0; i < selector.length; i++) {
						if (txtGiro == selector.options[i].text) {
							selector.selectedIndex = i;
							break;
						} else {//if
							selector.selectedIndex = 0;
						}
					}//for
				}//fin del if
			}//fin funcion validarGiro

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

			//function validarCapturaBeneficiario(){
			//             
			//        txtdeclaroBeneficiario = document.getElementById('declaroBeneficiario').checked;
			//        txtdeclaroOrigen = document.getElementById('declaroOrigen').checked;
			//        txtCapturado = localStorage.getItem('capturado');
			//        if ( txtSiBeneficiario){
			//            //return true;
			//            //alert(txtCapturado);
			//            if ( txtCapturado === '0' ){
			//                alert('Debe capturar por lo menos un beneficiario');
			//               return false;
			//            } else {
			//                return true;
			//            }
			//        } else {
			//        //$('.spinner-wrapper').fadeIn('fast');
			//         $('#sibeneficiario').click(function() {
			//         if ( this.checked ){
			//             document.getElementById('nobeneficiario').checked = false;
			//         }
			//
			//        });
			//        return true;
			//        }
			//    }
		</script>
		<script>
			function imprimir(Imp1, Imp2, Imp3, Imp4, Imp5, Imp55, Imp6, Imp7,
					Imp8, Imp9, Imp10, Imp11, Imp12, Imp13, Imp14, Imp15,
					Imp16, Imp17, Imp18, Imp19, Imp20, Imp21, Imp22, Imp23,
					Imp24, Imp25, Imp26, Imp27, Imp28, Imp29, Imp30, Imp31,
					Imp32, Imp33, Imp34, Imp35, Imp37, Imp38) {
				var printContents = document.getElementById('Imp1').innerHTML
						+ document.getElementById('Imp2').innerHTML
						+ document.getElementById('Imp3').innerHTML
						+ document.getElementById('Imp4').innerHTML
						+ document.getElementById('Estatus').options[document
								.getElementById('Estatus').selectedIndex].text;
				if (document.getElementById('chkFechaBloqueo').checked == 1)
					printContents += document.getElementById('check').innerHTML;
				else if (document.getElementById('chkFechaBloqueo').checked == 0)
					printContents += document.getElementById('no_check').innerHTML;
				printContents += document.getElementById('Imp5').innerHTML
						+ document.getElementById('Imp55').innerHTML
						+ document.getElementById('Imp6').innerHTML
						+ document.getElementById('Imp7').innerHTML
						+ document.getElementById('Imp8').innerHTML
						+ document.getElementById('Imp9').innerHTML
						+ document.getElementById('tipoP').options[document
								.getElementById('tipoP').selectedIndex].text
						+ document.getElementById('Imp10').innerHTML
						+ document.getElementById('Imp11').innerHTML
						+ document.getElementById('telPais').options[document
								.getElementById('telPais').selectedIndex].text
						+ document.getElementById('Imp12').innerHTML
						+ document.getElementById('Imp13').innerHTML
						+ document.getElementById('Imp14').innerHTML
						+ document.getElementById('Imp15').innerHTML
						+ document.getElementById('Imp16').innerHTML
						+ document.getElementById('Imp17').innerHTML
						+ document.getElementById('actividadObjeto').options[document
								.getElementById('actividadObjeto').selectedIndex].text
						+ document.getElementById('Imp18').innerHTML
						+ document.getElementById('nacionalidadCliente').options[document
								.getElementById('nacionalidadCliente').selectedIndex].text
						+ document.getElementById('Imp19').innerHTML
						+ document.getElementById('Imp20').innerHTML
						+ document.getElementById('Imp21').innerHTML
						+ document.getElementById('Imp22').innerHTML
						+ document.getElementById('Imp23').innerHTML
						+ document.getElementById('Imp24').innerHTML
						+ document.getElementById('Imp25').innerHTML
						+ document.getElementById('pais').options[document
								.getElementById('pais').selectedIndex].text
						+ document.getElementById('Imp26').innerHTML
						+ document.getElementById('Imp27').innerHTML
						+ document.getElementById('Imp28').innerHTML
						+ document.getElementById('Imp29').innerHTML
						+ document.getElementById('Imp30').innerHTML
						+ document.getElementById('Imp31').innerHTML
						+ document.getElementById('tipoIdentificacion').options[document
								.getElementById('tipoIdentificacion').selectedIndex].text
						+ document.getElementById('Imp32').innerHTML
						+ document.getElementById('Imp33').innerHTML
						+ document.getElementById('Imp34').innerHTML
						+ document.getElementById('Imp35').innerHTML;
				if (document.getElementById('declaroOrigen').checked == 1)
					printContents += document.getElementById('check').innerHTML;
				else if (document.getElementById('declaroOrigen').checked == 0)
					printContents += document.getElementById('no_check').innerHTML;
				printContents += document.getElementById('Imp36').innerHTML;
				if (document.getElementById('declaroBeneficiario').checked == 1)
					printContents += document.getElementById('check').innerHTML;
				else if (document.getElementById('declaroBeneficiario').checked == 0)
					printContents += document.getElementById('no_check').innerHTML;
				printContents += document.getElementById('Imp37').innerHTML
						+ document.getElementById('Imp38').innerHTML;
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