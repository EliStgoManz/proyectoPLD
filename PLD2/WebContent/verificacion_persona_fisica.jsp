<%@page import="control.DigitoAlfanumerico"%>
<%@page import="datosRatos.DatosClienteRaro"%>
<%@page import="rfcCurpAppi.token"%>
<%@page import="datosRatos.ConsultaWS"%>
<%@page import="entidad.Cliente"%>
<%@page import="listaEntidad.OperacionesCoincidencias" %>
<%@page import="listaEntidad.Coincidencia" %>
<%@page import="entidad.Perfil"%>
<%@page import="utilidades.Archivos"%>
<%@page import="datosRatos.DatosBeneficiario"%>
<%@page import="entidad.Beneficiario"%>
<%@page import="java.io.File"%>
<%@page import="utilidades.Rutas"%>
<%@page import="utilidades.PerfilUsuario"%>
<%@page import="utilidades.Mensajes"%>
<%@ include file="valida_login.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="entidad.Actividad"%>
<%@page import="datosRatos.DatosActividad"%>
<%@page import="entidad.TipoIdentificacion"%>
<%@page import="entidad.Pais"%>
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
<%
	//VALIDACION DEL PERFIL DE USUARIO
	HttpSession sesion = request.getSession();

	String perfilId = null;//verifica si el perfil del usuario no es nulo
	String user = sesion.getAttribute("user").toString();
	String vClienteParam = request.getParameter("idCliente");
	String vRFCParam = request.getParameter("rfc");
	String fechaRegis= request.getAttribute("fecharegistro").toString();
	System.out.println("la fecha es "+fechaRegis);

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
	
	<div id="bloq" style="width: 60%; hegth: 300px; bakground: gray"></div>
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
							<h1 class="title title-contrata-ahora">Sistema de Prevención
								de Lavado de Dinero</h1>
						</div>
					</div>
				</div>
			</div>
		</div>
	</header>
	<h5 class="title title-contrata-ahora white-text">Sistema de
		Prevención de Lavado de Dinero</h5>
	<div class="container">
		<div id="Imp2" class="main row">
			<article class="col-xs-12 col-sm-8 col-md-9 col-lg-9">
				<h2 class="text-muted">
					Datos y Documentos del Cliente<small>, Persona Física
						Revisión-prueba de jsp</small>
				</h2>
				<%
				 Calendar cal= Calendar.getInstance();
				  int year= cal.get(Calendar.YEAR);
				out.println("<p id='indicadorAlfa' style='visibility: hidden'>INDICADOR: "+new DigitoAlfanumerico().calcularCodigoAlfaNumerico(vRFCParam,year)+"</p>");
				
				%>
				
			</article>
			<aside class="col-xs-12 col-sm-4 col-md-3    col-lg-3">
				<p class="lead">
					Bienvenido:
					<%@ include file="detectausuario.jsp"%>
					<!--   Bienvenido:  out.println( vClienteParam );    -->
				</p>
			</aside>
		</div>
		<div class="row">
			<div class="col-md-9 table-responsive">
				<form onsubmit="return validarFomulario()"
					action="VerificacionPersonaFisica" id="frmPersonaFisica"
					method="post" autocomplete="off" enctype="multipart/form-data">
					<%	//Llamamos a los paï¿½ses desde la base de datos
						ArrayList listaPaises = new DatosPais().getList();
						ArrayList listaTipoIdentificacion = new DatosTipoIdentifiacion().getList();
						ArrayList listaActivida = new DatosActividad().getList("");
					%>
					<input type="hidden" name="respuesta" id="respuesta"
						value="<c:out value="${resultado}" />" /> <input type="hidden"
						name="exito" id="exito" value="<c:out value="${exito}" />" /> <input
						type="hidden" id="tipoPersona" name="tipoPersona"
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
						
						<% 
						String rutarfc = Rutas.wsrfc; 
						
						%>
						



					<table border="0">
                                                
					
					<input type="hidden" id="EstatusAnterior" name="EstatusAnterior" value="<c:out value="${estado}" />">		
						<tr>
							<td id="celdaEstatus" name="celdaEstatus"><label id="Imp3"
								for="Estatus">* Estatus </label><br /> 
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
									<option value="R" disabled="true">Actualizar</option>
							</select></td>
							<td></td>
							<td>
								<div class="row">
									<div class="col-12">

										<input type="checkbox" id="chkFechaBloqueo"
											name="chkFechaBloqueo" class=""> <label id="Imp33"
											for="chkFechaBloqueo"> Fecha de bloqueo </label><br>
										<br> <input type="date" id="fechaBloqueo"
											name="fechaBloqueo" value="<c:out value="${fechabloqueo}" />">

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
							<td colspan="3"><label for="tipo">Notas: </label><br /> <textarea
									id="notas" name="notas" class="form-control"> 
                                    </textarea></td>
						</tr>
						<tr>
							<td id="Imp5" width="350px"><label for="id">Id del
									cliente: </label><br /> <label for="id">
									<%
										out.println(vClienteParam);
									%>
							</label></td>
							<td width="50px"></td>
							<td id="celdaTipo" name="celdaTipo"><label id="Imp55"
								for="tipoP">Tipo de Persona: </label> <select
								class="browser-default" name="tipoP" id="tipoP">
									<option value="">* Tipo</option>
									<option value="F">Física</option>
							</select></td>


						</tr>
						<tr id="Imp6">
							<td width="350px">* Correo electrónico <input
								class="form-control" type="email" onkeyup="minus(this)"
								name="correo" id="correo" maxlength="60"
								value="<%out.println(vMailParam);%>">
							</td>
						</tr>
						<tr>
							<td><label id="Imp7" for="telPais" class=""> *
									Teléfono país </label> <input type="hidden" id="idtelPais"
								name="idTelPais" value="<c:out value="${pais}" />"> <select
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
							<td id="Imp77">
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="telefono" id="telefono"
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
										name="nombres" id="nombres" maxlength="200" size="80"
										value="<c:out value="${nombre}" />"> <label
										for="nombres" class=""> * Nombre(s)</label>
								</div>
							</td>
						</tr>
						<tr id="Imp10">
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
							<td id="Imp11"><input class="chzn-select" type="date"
								name="fechaNaciento" id="fechaNaciento"
								placeholder="* Fecha de nacimiento"
								value="<c:out value="${fechanacimiento}" />"> <label>*Fecha
									de nacimiento </label></td>
							<td><img src="img/calendar.png" height="32" width="32" /></td>
							<td><label id="Imp111" for="paisNacimiento" class="">
									* País de Nacimiento </label> <input type="hidden"
								id="idPaisNacimiento" name="idPaisNacimiento"
								value="<c:out value="${paisnacim}" />"> <select
								class="browser-default" name="paisNacimiento"
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

							<td><br> <label id="Imp12" for="paisNacionalidad"
								class=""> * País de Nacionalidad </label> <input type="hidden"
								id="idPaisNacionalidad" name="idPaisNacionalidad"
								value="<c:out value="${paisnacio}" />"> <select
								class="browser-default" name="paisNacionalidad"
								id="paisNacionalidad" style="width: 80%;">
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
							<td id="Imp13">
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										onkeyup="mayus(this);" name="RFC" id="RFC" maxlength="13"
										value="<%out.print(vRFCParam);%>" readonly="readonly">
									<label for="RFC">* RFC</label><br /> <br>
								</div>
							</td>
							<td></td>

							<td id="Imp14">
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="curp" id="curp" size="18" maxlength="18"
										value="<c:out value="${curp}" />"> <label for="RFC">*
										CURP</label>
								</div>
							</td>
						<tr>
							<td colspan="3"><label for="tipo">CURP</label><br /> <input
								type="hidden" id="idarchivoCurp" name="idarchivoCurp"
								value="<c:out value="${imagenCurp}" />"> <input
								type="hidden" id="archivoCurpZip" name="archivoCurpZip" /> <input
								type="file" name="archivoCurp" id="archivoCurp"
								accept="document/pdf"
								onchange="validarArchivoCurp('lupaCurp', this, document.getElementById('archivoCurpZip'), document.getElementById('covers'))">&nbsp;
								<a href="javascript:PreviewImage('archivoCurp')"> <img
									src="img/lupa.jpg" height="32" width="32" style="display: none"
									id="lupaCurp" />
							</a>
								<p class="help-block">Un solo archivo de máximo 10MB</p> 
								<%
 	String idCLiente = vClienteParam;
 	String rutaOrigen = Rutas.rutaCarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirCurp
 			+ Rutas.separador;
 	
 	String rutaDestino = Rutas.rutaDescarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirCurp
 			+ Rutas.separador;
 	
//  	System.out.println("ruta "+ rutaOrigen);
// 	System.out.println("ruta 2 "+ rutaDestino);

//  	String rutaOrigen = Rutas.rutaCarga + Rutas.separador +"Logs"+ Rutas.separador;
//  	String rutaDestino = Rutas.rutaDescarga + Rutas.separador + "Logs" + Rutas.separador;
 	
 	
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
 %>
 </td>
						</tr>
						<tr>
							<td colspan="3"><label id="Imp15" for="paisNacimiento"
								class=""> * Actividad económica </label> <input type="hidden"
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
							<td><label id="Imp16" for="tipoIdentificacion">*
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
							<td id="Imp17">
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="numeroId" id="numeroId" maxlength="40"
										value="<c:out value="${numeroid}" />"> <label
										for="numeroId" class=""> * No. de ID</label>
								</div>
							</td>
						</tr>

						<tr id="Imp18">

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
								type="hidden" id="archivoIdPFZip" name="archivoIdPFZip" /> <!--<input type="file" name="archivoIdPF" id="archivoIdPF" accept="document/pdf" onchange="validarArchivoId('lupaId', this)" value="<c:out value="${imagenid}" />">&nbsp;-->
								<input type="file" name="archivoIdPF" id="archivoIdPF"
								accept="document/pdf"
								onchange="validarArchivoId('lupaId', this, document.getElementById('archivoIdPFZip'), document.getElementById('covers'))">&nbsp;
								<a href="javascript:PreviewImage('archivoIdPF')"> <img
									src="img/lupa.jpg" height="32" width="32" style="display: none"
									id="lupaId" />
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
								name="idarchivoCedula" value="<c:out value="${imagenCedula}" />">
								<input type="hidden" id="archivoCedulaZip"
								name="archivoCedulaZip" /> <input type="file" name="cedulaPF"
								id="cedulaPF" accept="document/pdf"
								onchange="validarArchivoCedulaFiscal('lupaCedula', this, document.getElementById('archivoCedulaZip'), document.getElementById('covers'))">&nbsp;
								<a href="javascript:PreviewImage('cedulaPF')"> <img
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
 		out.println("<INPUT type='hidden' id='HayArchivoCedulaPF' name='HayArchivoCedulaPF' value='si'");
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
 		out.println("<INPUT type='hidden' id='HayArchivoCedulaPF' name='HayArchivoCedulaPF' value='no'");
 	}
 %></td>
						</tr>

						<tr id="Imp19">
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
						<tr id="Imp20">
							<td>
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="calle" id="calle" maxlength="100"
										value="<c:out value="${calle}" />"> <label for="calle"
										class=""> * Calle</label>
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
						<tr id="Imp22">
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
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="cp" id="cp" onkeypress="return esNumero(this, event)"
										maxlength="5" value="<c:out value="${codpostal}" />">
									<label for="cp" class=""> * Código Postal </label>
								</div>
							</td>
						</tr>
						<tr>
							<td id="Imp23">
								<div class="md-form form-sm">
									<input class="form-control" type="text" onkeyup="mayus(this)"
										name="estado" id="estado" maxlength="100"
										value="<c:out value="${estado_prov}" />"> <label
										for="estado" class=""> * Estado </label>
								</div>
							</td>
							<td></td>
							<td><label id="Imp24" for="paisDomicilio">* País </label><br />
								<input type="hidden" id="idPaisDomicilio" name="idPaisDomicilio"
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

								<a href="javascript:PreviewImage('comprobanteDomicilio')"> <img
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
 		out.println(
 				"<INPUT type='hidden' id='HayArchivoComprobanteDom' name='HayArchivoComprobanteDom  ' value='no'");
 	}
 %></td>
						</tr>

						<tr id="Imp24">
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
								name="comprobanteDeclaratoria" id="comprobanteDeclaratoria"
								accept="document/pdf"
								onchange="validarArchivoDeclaratoria('lupaDeclaratoria', this, document.getElementById('archivoDeclaratoriaZip'), document.getElementById('covers'))">&nbsp;
								<a href="javascript:PreviewImage('comprobanteDeclaratoria')">
									<img src="img/lupa.jpg" height="32" width="32"
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
							<td colspan="3">
								<div class="row">
									<div class="col-12">
										<div class="md-form form-sm">
											<input type="hidden" id="idDeclaroBeneficiario"
												name="idNoBeneficiario"
												value="<c:out value="${declarobeneficiario}" />"> <input
												type="hidden" id="capturado" name="capturado" value="0">
											<input type="hidden" id="checado" name="checado" value="0">

											<input type="checkbox" id="sibeneficiario"
												name="sibeneficiario" class=""> <label
												for="sibeneficiario" class=""> Tengo conocimiento
												del Dueño Beneficiario o Beneficiario Controlador </label><br>
										</div>
									</div>
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


						</tr>
						<tr id="Imp25">
							<td colspan="4">
								<!-- COMIENZA LA CONSUTLA SI TIENE BENEFICIARIOS --> 
								<%
 	// int comillas = 34;
 	//  out.println("<a class='boton_personalizado' id ='btnBene' style='visibility:visible' href=" + (char)comillas + "javascript:lanzarBeneficiario('" + vClienteParam + "')" + (char)comillas + ">Agregar Dueño Beneficiario o Beneficiario Controlaldor.</a>");

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
 	//out.println("<a class='boton_personalizado' align='left' id ='btnAct' href=" + (char)comillas + "javascript:ActualizaTablaBene('" + vClienteParam + "','" + w.consultarWsBene() + "')"+ (char)comillas +"style='visibility:visible' > Actualizar Beneficiarios.</a>");
 	out.println("<script>");
 	//out.println("document.getElementById('capturado').value = '1';");
 	out.println("localStorage.setItem('capturado','1');");
 	out.println("</script>");

 	out.println("<div id='divv'>");

 	if (esEdicion != null && !esEdicion.isEmpty() && idCliente != null && !idCliente.isEmpty()) {
 		Beneficiario[] b = new DatosBeneficiario().getBeneList(idCliente);
 		if (b != null && b.length > 0) {

 			out.println("<h4>Dueño Beneficiario</h4>");
 			out.println("<table>");
 			out.println("<thead>");
 			out.println("<tr>");
 			out.println("<td>No. Bene</td>");
 			out.println("<td>Nombre/Razón Social</td>");
 			out.println("<td>Tipo Persona</td>");
 			out.println("<td>Consultar</td>");
 			out.println("</tr>");
 			out.println("</thead>");
 			// out.println("<a class='boton_personalizado' align='left' id ='btnAct' href=" + (char)comillas + "javascript:ActualizaTablaBene('" + vClienteParam + "','" + w.consultarWsBene() + "')"+ (char)comillas +"style='visibility:visible' > Actualizar Beneficiarios.</a>");

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
 				//                           document.getElementById("divv").innerHTML+="<td><a href=\"" + servlet + "?idCliente=" + obj.Beneficiarios[i].cliente_id() + "&nroBene=" + obj.Beneficiarios[i].nrobene + "&estadoCliente=" + obj.Beneficiarios[i].estadoCliente + "&esEdicion=0\" target=\"_blank\" style=\"text-decoration: underline; color:blue;\"><img src=\"img/lupa.jpg\" height=\"32\" width=\"32\" align=\"center\"></a>"; 

 				System.out.println("<td><a href=\"" + servlet + "?idCliente=" + b[i].getCliente_id().trim()
 						+ "&nroBene=" + b[i].getNroBeneficiario()
 						+ "&esEdicion=0\" target=\"_blank\" style=\"text-decoration: underline; color:blue;\"><img src=\"img/lupa.jpg\" height=\"32\" width=\"32\" align=\"center\"></a>");
 				out.println("</tr>");
 			}
 			out.println("</table>");
 			//  out.println("</div>");
 			out.println("</br>");
 			//  out.println("<a class='boton_personalizado' align='left' id ='btnAct' href=" + (char)comillas + "javascript:ActualizaTablaBene('" + vClienteParam + "','" + w.consultarWsBene() + "')"+ (char)comillas +"style='visibility:visible' > Actualizar Beneficiarios.</a>");

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
 	out.println("<input type='hidden' id='WsBene' name='WsBene' value='" + w.consultarWsBene() + "'> ");
 	out.println("</br>");
 %>
</td>
</tr>
<tr>
<td colspan=4>
			<%  
			 	ArrayList<Coincidencia> a = new ArrayList<Coincidencia>();
			 	     OperacionesCoincidencias v = new OperacionesCoincidencias();
			 	     try{
			 	    	a = v.armarCoincidenciasAmostrarEnPantalla(vClienteParam, "F");
			 	    	
			 	     }catch(Exception e){
			 	    	 out.print("ERRRRRROOOOOOOOOR  " + e.toString());
			 	     }
			 	     
			 		 out.println("<INPUT type='hidden' id='tamanoArreglo' name='tamanoArreglo'  value="+a.size()+">");
			 		out.println("<INPUT type='hidden' id='fueRevisado' name='fueRevisado'  value="+new DatosClienteRaro().fueRevisado(vClienteParam)+">");
			 		System.out.println("tamaño del arreglo de coinc "+a.size());
			 		 if (a != null && a.size() > 0) {	
			 			out.println("<h4>Coincidencias En Listas.</h4>");
			 			
			 			for (int i = 0; i < a.size(); i++) {
			 				out.println("<tr style=\"border-bottom:1px solid #000000\">"); 
			 				JSONParser parser = new JSONParser();
			 				JSONObject json1 = (JSONObject) parser.parse(a.get(i).getCuerpoJson());
			 			out.println("<table >"); 
			 			out.println("<tr style=\"border-bottom:1px solid #000000\">"); 			
			 			out.println("<tr><td class='TitleCell' >Nombre:</td><td class='InfoCell' style='color:red' width=200>"+json1.get("firstName")+" "+json1.get("lastName")+"</td> <td></td> <td class='TitleCell' width=100>Categoría:</td><td class='InfoCell' width=300>"+json1.get("categoryWc")+"</td><td class='TitleCell'>Tipo Lista :</td><td class='InfoCell'width=150>"+json1.get("cveTipoLista")+"</td></tr>"); 
			 			out.println("<tr><td class='TitleCell' >Rfc: </td> <td class='InfoCell' style='color:red'>"+json1.get("rfc")+"</td> <td class='TitleCell' >Fecha de Nacimiento: </td> <td class='InfoCell' style='color:black'>"+json1.get("dob")+"</td></tr>");
			 			out.println("<tr><td class='TitleCell' >Subcategoría:</td><td  class='InfoCell'>"+json1.get("subCategory")+"</td> <td></td> <td class='TitleCell'  >Localización:</td><td class='InfoCell' width=200>"+json1.get("locations")+"</td><td class='TitleCell' >Creación:</td><td class='InfoCell'>"+json1.get("entered")+"</td></tr>"); 			
			 			out.println("<tr><td class='TitleCell' >Identificador:</td><td class='InfoCell'>"+json1.get("uId")+"</td> <td></td> <td  class='TitleCell' >Alias:</td><td class='InfoCell' width=200>"+json1.get("aliases").toString().replaceAll(",",", ")+"</td> <td  valign=top >Info adicional:</td><td class='InfoCell'>"+json1.get("furtherInfo")+"</td> </tr>"); 			
			 			out.println("<tr><td class='TitleCell' >Fuentes externas:</td><td class='InfoCell' width=100>"+json1.get("externalSources").toString().replaceAll(" ","  ")+"</td> <td></td> <td class='TitleCell'>Sexo:</td><td class='InfoCell'>"+json1.get("genero")+"</td> <td class='TitleCell' >Actualizado:</td><td class='InfoCell'>"+json1.get("updated")+"</td> </tr>"); 			
			 			out.println("<tr><td class='TitleCell' >Lugar nac:</td><td class='InfoCell'>"+json1.get("placeBirth")+"</td> <td></td> <td class='TitleCell'  >Ligado a:</td><td class='InfoCell' width=200>"+json1.get("linkedTo").toString().replaceAll(";","; ")+"</td><td class='TitleCell' >Implicado:</td><td class='InfoCell'><INPUT type='hidden' id='matchid"+i+"' name='matchid"+i+"' value='"+a.get(i).getMatchid()+"' >"+a.get(i).getImplicado()+"</td> </tr>"); 			
			 			out.println("<tr><td class='TitleCell' >Coincidencia:</td><td class='InfoCell' style='color:red'>"+json1.get("score")+"</td> <td></td> <td class='TitleCell' >Palabras Clave:</td><td class='InfoCell' width=200>"+json1.get("keywords").toString().replaceAll("~","~ ")+"</td><td></td><td></td></tr>"); 			
						
			 			out.println("<tr style='border-bottom:1pt solid black;'><td class='TitleCell' >Descripción:</td><td class='InfoCell' colspan=5><textarea name='Desc"+i+"' id='Desc"+i+"' cols='40'>"+a.get(i).getDescripcion()+"</textarea>");
			 			
			 			out.println("</table>" );
			 		 }
			 		 }
			 				
 %>	
	 	
</td>
</tr>
			
						<tr>
							<td><a class="boton_personalizado2" id="btnClose"
								style="visibility: hidden" href="estatus_clientes.jsp">Cerrar</a>
							</td>
							<td></td>
							<td>
								<input type="button" onClick="validarRFC()" class="btn btn-danger" name="btnGuardar" id="btnGuardar" value="Guardar"> 
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
				<!-- <button class="btn" >MENU HISTORIAL</button>-->
				<%
					int comilla = 34;
					out.println("<a id='Hist' style='visibility:visible' href=" + (char) comilla + "javascript:Historial('"
							+ vClienteParam + "')" + (char) comilla + ">Historial de cambios.</a>");
				%>
				<br> <a id="Impr"
					href="javascript:imprimir(Imp1,Imp2,Imp3,Imp33,Imp4,Imp5,Imp55,Imp6,Imp7,Imp77,Imp8,Imp9,Imp10,Imp11,Imp111,Imp12,Imp13,Imp14,Imp15,Imp16,Imp17,Imp18,Imp19,Imp20,Imp21,Imp22,Imp23,Imp24,Imp25,Imp26)">Imprimir
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
	<footer id="Imp26">
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
		$(document).ready(
				
						function() {
							
							$("#cover").hide();
// 							$("#covers2").show();
							PERFIL_ADMINISTRADOR = 1;
							PERFIL_PLD = 2;
							document.getElementById("chkFechaBloqueo").disabled = true;
							document.getElementById("check").style.visibility = 'hidden';
							document.getElementById("no_check").style.visibility = 'hidden';
							//                document.getElementById("fechaBloqueo").disabled = true;
							//                document.getElementById("fechaValidacion").disabled = true;
							//                document.getElementById("fechaModificacion").disabled = true;

							//FUNCIONES CUÁNDO ES EDICION 
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
									document.getElementById("btnGuardar").style.visibility = 'hidden';
									document.getElementById('btnClose').style.visibility = 'visible';
									document.getElementById('btnBene').style.visibility = 'hidden';
									//                        document.getElementById("archivoIdPF").disabled = true;
									//                        document.getElementById("cedulaPF").disabled = true;
									//                        document.getElementById("comprobanteDomicilio").disabled = true;
									//                        document.getElementById("Estatus").disabled = true;
									//                        document.getElementById("chkFechaBloqueo").disabled = true;
									//                        document.getElementById("tipoP").disabled = true;
									//                        document.getElementById("telPais").disabled = true;
									//                        document.getElementById("paisNacimiento").disabled = true;
									//                        document.getElementById("paisNacionalidad").disabled = true;
									//                        document.getElementById("actividadEco").disabled = true;
									//                        document.getElementById("tipoIdentificacion").disabled = true;
									//                        document.getElementById("paisDomicilio").disabled = true;
									//                        document.getElementById("tipoBeneficiario").disabled = true;
									//                        document.getElementById("nobeneficiario").disabled = true;
									//                        document.getElementById("sibeneficiario").disabled = true;
									//                        document.getElementById("fechaBloqueo").disabled = true;
									//                        document.getElementById("declaroOrigen").disabled = true;
									//                        document.getElementById("mensaje").disabled = true;
									//celdaEstatus.style.display = 'none';
									//$( "#Estatus" ).prop( "disabled", true );
									//document.getElementById("celdaEstatus").disabled = true;
									var form = document
											.getElementById('frmPersonaFisica');
									var elements = form.elements;
									for (var i = 0, len = elements.length; i < len; ++i) {
										elements[i].readOnly = true;
										elements[i].disabled = true;
									}
								}

								txtPerfilUsuarioSistema = "";
	<% 
	String perfil = (String) sesion.getAttribute("perfilId");
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
									document.getElementById("btnGuardar").style.visibility = 'visible';
									document.getElementById('btnClose').style.visibility = 'hidden';
									document.getElementById('btnBene').style.visibility = 'visible';
									document.getElementById('notas').value = '${notas.trim()}';
									document.getElementById('Hist').style.visibility = 'visible';
									document.getElementById('Impr').style.visibility = 'visible';

									var form = document
											.getElementById('frmPersonaFisica');
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
										//document.getElementById("btnGuardar").disabled = true;
										document.getElementById("btnGuardar").style.visibility = 'hidden';
										document.getElementById('btnClose').style.visibility = 'visible';
										document.getElementById('btnBene').style.visibility = 'hidden';
										document.getElementById('btnAct').style.visibility = 'hidden';
										//                            document.getElementById("archivoIdPF").disabled = true;
										//                            document.getElementById("cedulaPF").disabled = true;
										//                            document.getElementById("comprobanteDomicilio").disabled = true;
										//                            document.getElementById("Estatus").disabled = true;
										//                            document.getElementById("chkFechaBloqueo").disabled = true;
										//                            document.getElementById("tipoP").disabled = true;
										//                            document.getElementById("telPais").disabled = true;
										//                            document.getElementById("paisNacimiento").disabled = true;
										//                            document.getElementById("paisNacionalidad").disabled = true;
										//                            document.getElementById("actividadEco").disabled = true;
										//                            document.getElementById("tipoIdentificacion").disabled = true;
										//                            document.getElementById("paisDomicilio").disabled = true;
										//                            document.getElementById("tipoBeneficiario").disabled = true;
										//                            document.getElementById("nobeneficiario").disabled = true;
										//                            document.getElementById("sibeneficiario").disabled = true;
										//                            document.getElementById("fechaBloqueo").disabled = true;
										//                            document.getElementById("declaroOrigen").disabled = true;
										//                            document.getElementById("mensaje").disabled = true;
										var form = document
												.getElementById("frmPersonaFisica");
										var elements = form.elements;
										for (var i = 0, len = elements.length; i < len; ++i) {
											elements[i].readOnly = true;
											elements[i].disabled = true;
										}
									}
								}

								document.getElementById('mensaje').value = '${mensaje.trim()}';
								//El estatus del expediente
								txtEstatus = '${estado}';
								//                        alert(txtEstatus);
								if (txtEstatus != null || txtEstatus.length > 0) {
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

								//El estatus del expediente

								if (txtEstatus != null || txtEstatus.length > 0) {
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
									document.getElementById('chkFechaBloqueo').checked = true;
								} else {
									document.getElementById('chkFechaBloqueo').checked = false;
								}

								/*
								 *  ORGANIZANDO INFORMACIÓN DE LA RESPUESTA
								 */
								//ASIGNANDO EL PAÍS TELÉFONO
								txtTelPais = document
										.getElementById('idtelPais').value; //llega de la respuesta
								if (txtTelPais != null || txtTelPais.length > 0) {
									var selector = document
											.getElementById('telPais');
									for (var i = 0; i < selector.length; i++) {
										if (txtTelPais == selector.options[i].value) {
											selector.selectedIndex = i;
											break;
										} else {//if
											selector.selectedIndex = 0;
										}
									}//for 
								}

								//ASIGNANDO EL PAÍS NACIMIENTO
								txtPaisNac = document
										.getElementById('idPaisNacimiento').value; //llega de la respuesta
								if (txtPaisNac != null || txtPaisNac.length > 0) {
									var selector = document
											.getElementById('paisNacimiento');
									for (var i = 0; i < selector.length; i++) {
										if (txtPaisNac == selector.options[i].value) {
											selector.selectedIndex = i;
											break;
										} else {//if
											selector.selectedIndex = 0;
										}
									}//for 
								}

								//ASIGNANDO EL PAÍS NACIONALIDAD
								txtPaisNacio = document
										.getElementById('idPaisNacionalidad').value; //llega de la respuesta
								if (txtPaisNacio != null
										|| txtPaisNacio.length > 0) {
									var selector = document
											.getElementById('paisNacionalidad');
									for (var i = 0; i < selector.length; i++) {
										if (txtPaisNacio == selector.options[i].value) {
											selector.selectedIndex = i;
											break;
										} else {//if
											selector.selectedIndex = 0;
										}
									}//for 
								}

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

								//ASIGNANDO PAÍS DOMICILIO
								txtPaisDomi = document
										.getElementById('idPaisDomicilio').value; //llega de la respuesta
								if (txtPaisDomi != null
										|| txtPaisDomi.length > 0) {
									var selector = document
											.getElementById('paisDomicilio');
									for (var i = 0; i < selector.length; i++) {
										if (txtPaisDomi == selector.options[i].value) {
											selector.selectedIndex = i;
											break;
										} else {//if
											selector.selectedIndex = 0;
										}
									}//for 
								}

								//Mostrando los check de las declaraciones  
								txtDeclaroBeneficiario = document
										.getElementById('idDeclaroBeneficiario').value;
								document.getElementById('sibeneficiario').checked = false;
								if (txtDeclaroBeneficiario == "1") {
									document.getElementById('sibeneficiario').checked = true;
								}
								
								//Mostrando los check de la deslindacion  
								txtRiesgo = document.getElementById('idRiesgo').value;
								document.getElementById('riesgo').checked = false;
								if (txtRiesgo == "1") {
									document.getElementById('riesgo').checked = true;
								}

								//Mostrando mensajes
								var txtExito = document.getElementById('exito').value;
								var txtRespuesta = document
										.getElementById('respuesta').value;
								if (txtExito == '1' && txtRespuesta.length > 0) {
									alert(txtRespuesta);
								}

							}//fin es edicion
						});// fin ready document
	</script>
	
	
	
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
	
	txtEstatus = document.getElementById('Estatus').value;			
	if (txtEstatus != 'I') {
		var txtRFC = document.getElementById('RFC').value;
		var obj="";
		var ruta = "<%= rutarfc %>";
		
	
		if(ruta != "0"){
			
			if(txtRFC != null && txtRFC != ""){
				$.ajax({
					async : true,
					crossDomain : true,
					type : "GET",
					url : ruta,
					contentType : "application/json",
					dataType : "text",
					data : {
						rfc : txtRFC,
				},
					
					success : function(data) {
					  obj = JSON.parse(data);
						if(obj.respuesta+"" == "RFC Valido"){
							$('#frmPersonaFisica').submit();
					    }else{
					    	
					    	alert("El RFC ingresado no se encuentra en la lista de RFC no cancelados");
					    	document.getElementById('RFC').focus();	
					    }
					},
					
					error : function(xhr, var1, var2) {
						console.log("errorcito");
						alert("No se a podido validar RFC, time out!,  ruta wsrfc:"+ruta+"");
					}
				});
			}else{
				$('#frmPersonaFisica').submit();
			}
			
		}else{
			$('#frmPersonaFisica').submit();
		}
		
		

}else{
	$('#frmPersonaFisica').submit();
}
}
</script>
	
	
<script>
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
		function Historial(vIdCliente) {
			window.open('historial_cambios.jsp?idCliente=' + vIdCliente,
					'_blank');
		}
	</script>
	<script>
		function lanzarBeneficiario(vIdCliente) {
			txtSiBeneficiario = document.getElementById('sibeneficiario').checked;
			if (txtSiBeneficiario) {
				e = document.getElementById('tipoBeneficiario');
				sel = e.options[e.selectedIndex].value;

				switch (sel) {
				case 'F':
					window.open(
							'beneficiario_personafisica.jsp?esEdicion=1&idCliente='
									+ vIdCliente, '_blank');
					document.getElementById('capturado').value = '1';
					break;
				case 'M':
					window.open(
							'beneficiario_personamoral.jsp?esEdicion=1&idCliente='
									+ vIdCliente, '_blank');
					document.getElementById('capturado').value = '1';
					break;
				case 'X':
					window.open(
							'beneficiario_fideicomiso.jsp?esEdicion=1&idCliente='
									+ vIdCliente, '_blank');
					document.getElementById('capturado').value = '1';
					break;
				default:
					alert('Debe seleccionar el tipo de beneficiario');
				}//SWITCH

			} else {
				alert('Para agregar un beneficiario debe declarar que conoce la información del mismo');
			}
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
		function validarFomulario() {
			 
			if (validarFomularios()) {
				
				$('.spinner-wrapper').fadeIn('fast');
				Prog();

				return true;
			} else {
				return false;
			}

		}

		function validarFomularios() {
			
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
				alert('La clave de teléfono de país no puede dejarse en blanco');
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

			//que la longitud del telefóno se solo de 10 dígitos
			txtTelefono = document.getElementById('telefono').value;
			if (txtTelefono.length != 0)
				if (txtTelefono.length != 10) {
					alert('El número telefónico debe ser de 10 dígitos');
					document.getElementById('telefono').focus();
					return false;
				}

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

			txtFechaNacimiento = document.getElementById('fechaNaciento').value;
			if (txtFechaNacimiento == null || txtFechaNacimiento.length == 0
					|| /^\s+$/.test(txtFechaNacimiento)) {
				alert('No ha indicado una fecha de nacimiento');
				document.getElementById('fechaNaciento').focus();
				return false;

			}

			//normalizando fecha nac yymmdd
			txtFechaNacimiento = getFechaNac(txtFechaNacimiento);
			txtFechaNacimientoRFC = getFechaNacRFC(document.getElementById('RFC').value);
			
			
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

			txtPaisNacionalidad = document.getElementById('paisNacionalidad').value;
			if (txtPaisNacionalidad == null || txtPaisNacionalidad.length == 0
					|| /^\s+$/.test(txtPaisNacionalidad)) {
				alert('Debe seleccionar un país de  nacionalidad');
				document.getElementById('paisNacionalidad').focus();
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
					|| /^\s+$/.test(txtActivodadEco) || txtActivodadEco == "1000000" ) {
				alert('Debe indicar una actividad económica');
				document.getElementById('actividadEco').focus();
				return false;
			}

			tipoIdentificacion = document.getElementById('tipoIdentificacion').value;
			txtotroTipoId = document.getElementById('otroTipoId').value;
			if (txtotroTipoId == null || txtotroTipoId.length == 0) {
				if (tipoIdentificacion == null
						|| tipoIdentificacion.length == 0
						|| /^\s+$/.test(tipoIdentificacion)) {
					alert('Debe selccionar un tipo de identificacion');
					document.getElementById('tipoIdentificacion').focus();
					return false;
				}
			}

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

			//ARCHIVO DE IDENTIFICACION
			if (txtEstatus == "A") {
				txtarchivoIdPF = document.getElementById('archivoIdPF').value;
				txtHayArchivoIdPF = document.getElementById('HayArchivoIdPF').value;
				if (txtarchivoIdPF.length == 0) {
					if (txtHayArchivoIdPF === 'no') {
						alert('Para poder validar debe subir un archivo de Identificación');
						document.getElementById('archivoIdPF').focus()
						return false;
					}
				}
			}
			//ARCHIVO DE Declaratoria
			if (txtEstatus == "A") {
				txtarchivoDeclaratoria = document
						.getElementById('comprobanteDeclaratoria').value;
				txtHayArchivoDeclaratoria = document
						.getElementById('HayArchivoDeclaratoria').value;
				if (txtarchivoDeclaratoria.length == 0) {
					if (txtHayArchivoDeclaratoria === 'no') {
						alert('Para poder validar debe subir un archivo de Declaratoria');
						document.getElementById('comprobanteDeclaratoria')
								.focus()
						return false;
					}
				}
			}

			//ARCHIVO DE CÉDULA FISCAL
			if (txtEstatus == "A") {
				txtarchivocedulaPF = document.getElementById('cedulaPF').value;
				txtHayArchivoCedulaPF = document
						.getElementById('HayArchivoCedulaPF').value;
				if (txtarchivocedulaPF.length == 0) {
					if (txtHayArchivoCedulaPF === 'no') {
						alert('Para poder validar debe subir un archivo de Cédula Fiscal');
						document.getElementById('cedulaPF').focus()
						return false;
					}
				}
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

			//ARCHIVO DE COMPROBANTE DE DOMICILIO
			if (txtEstatus == "A") {
				txtcomprobanteDomicilio = document
						.getElementById('comprobanteDomicilio').value;
				txtHayArchivoComprobanteDom = document
						.getElementById('HayArchivoComprobanteDom').value;
				if (txtcomprobanteDomicilio.length == 0) {
					if (txtHayArchivoComprobanteDom === 'no') {
						alert('Para poder validar debe subir un archivo de Comprobante de Domicilio');
						document.getElementById('comprobanteDomicilio').focus()
						return false;
					}
				}
			}

			txtPaisDomicilio = document.getElementById('paisDomicilio').value;
			if (txtPaisDomicilio == null || txtPaisDomicilio.length == 0
					|| /^\s+$/.test(txtPaisDomicilio)) {
				alert('El país del domicilio no puede dejarse en blanco');
				document.getElementById('paisDomicilio').focus();
				return false;
			}
			
			
//          chkSiBeneficiario = document.getElementById('sibeneficiario').checked;
			

			if (document.getElementById('archivoCurp').value.length == 0)
				document.getElementById('archivoCurpZip').value = '';
			if (document.getElementById('archivoIdPF').value.length == 0)
				document.getElementById('archivoIdPFZip').value = '';
			if (document.getElementById('cedulaPF').value.length == 0)
				document.getElementById('archivoCedulaZip').value = '';
			if (document.getElementById('comprobanteDomicilio').value.length == 0)
				document.getElementById('archivoComprobanteDomZip').value = '';
			if (document.getElementById('comprobanteDeclaratoria').value.length == 0)
				document.getElementById('archivoDeclaratoriaZip').value = '';

			
			
					
				
				
			if (validarCapturaBeneficiario()) {
				 return true;
			} else {
				return false;
			}
			
			
			
					

		}
		//fin de la funcion validar formuario
		
	</script>
	
	
	<script>
		jQuery(function() {
			$("#buscarActividadEco").autocomplete("list.jsp");

		}); //buscar la actividad económica

		function validarActividadEco() {
			//Asigna al select el valor lozalizado en la lista de actividades escrita
			txtTextoActividad = document.getElementById('actividadEco').value;
			var selector = document.getElementById('buscarActividadEco');
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

		//            function hayOtraIdentificacion() {
		//                txtOtraIdentificacion = document.getElementById('otroTipoId').value;
		//
		//                if (txtOtraIdentificacion.trim().length > 0 || txtOtraIdentificacion != '') {
		//                    document.getElementById('tipoIdentificacion').selectedIndex = 0;
		//                }
		//            }

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

		function validarBeneficiario() {
			//Esto ocurre en el navegados, nunca jamás llego al servidor
			//validar que se haya seleccionao un país para el teléfono
			txtbTelPais = document.getElementById('btelPais').value;
			if (txtbTelPais == null || txtbTelPais.length == 0
					|| /^\s+$/.test(txtbTelPais)) {
				alert('Debe seleccionar un país de teléfono');
				document.getElementById('btelPais').focus();
				return false;
			}

			txtbTelefono = document.getElementById('btelefono').value;
			if (txtbTelefono == null || txtbTelefono.length == 0
					|| /^\s+$/.test(txtbTelefono)) {
				alert('Debe capturar un número telefónico');
				document.getElementById('btelefono').focus();
				return false;
			}

			txtbnombres = document.getElementById('bnombres').value;
			if (txtbnombres == null || txtbnombres.length == 0
					|| /^\s+$/.test(txtbnombres)) {
				alert('El nombre no puede dejarse en blanco');
				document.getElementById('bnombres').focus();
				return false;
			}

			txtbpaterno = document.getElementById('bpaterno').value;
			s
			if (txtbpaterno == null || txtbpaterno.length == 0
					|| /^\s+$/.test(txtbpaterno)) {
				alert('El apellido paterno no puede dejarse en blanco');
				document.getElementById('bpaterno').focus();
				return false;
			}

			txtbmaterno = document.getElementById('bmaterno').value;
			s
			if (txtbmaterno == null || txtbmaterno.length == 0
					|| /^\s+$/.test(txtbmaterno)) {
				alert('El apellido materno no puede dejarse en blanco');
				document.getElementById('bmaterno').focus();
				return false;
			}

			txtbfechaNaciento = document.getElementById('bfechaNaciento').value;
			if (txtbfechaNaciento == null || txtbfechaNaciento.length == 0
					|| /^\s+$/.test(txtbfechaNaciento)) {
				alert('La fecha de nacimiento no puede dejarse en blanco');
				document.getElementById('bfechaNacimiento').focus();
				return false;
			}

			txtbRFC = document.getElementById('bRFC').vale;
			if (txtbRFC == null || txtbRFC.length == 0 || /^\s+$/.test(txtbRFC)) {
				alert('El RFC no pude dejarse en blanco');
				document.getElementById('bRFC').focus();
				return false;
			}

			//normalizando fecha nac yymmdd
			txtbfechaNaciento = getFechaNac(txtbfechaNaciento);
			txtbFechaNacimientoRFC = getFechaNacRFC(document
					.getElementById('bRFC').value);

			if (txtbfechaNaciento != txtbFechaNacimientoRFC) {
				alert('La fecha de nacimiento no coincide con la fecha de nacimiento del RFC');
				document.getElementById('bRFC').focus();
				return false;
			}

			txtbcurp = document.getElementById('bcurp').value;
			if (txtbcurp == null || txtbcurp.length == 0
					|| /^\s+$/.test(txtbcurp)) {
				alert('El CURP no puede dejarse en blanco');
				document.getElementById('bcurp').focus();
				return false;
			}
			
			txtbactividadEco = document.getElementById('bactividadEco').value;
			
			if (txtbactividadEco == null || txtbactividadEco.length == 0
					|| /^\s+$/.test(txtbactividadEco)) {
				alert('La actividad no puede dejarse en blanco');
				document.getElementById('bactividadEco').focus();
				return false;
			}

			txtbpaisNacimiento = document.getElementById('bpaisNacimiento').value;
			if (txtbpaisNacimiento == null || txtbpaisNacimiento.length == 0
					|| /^\s+$/.test(txtbpaisNacimiento)) {
				alert('Debe seleccionar el país nacimiento');
				document.getElementById('bpaisNacimiento').focus();
				return false;
			}

			txtbpaisNacionalidad = document.getElementById('bpaisNacionalidad').value;
			if (txtbpaisNacionalidad == null
					|| txtbpaisNacionalidad.length == 0
					|| /^\s+$/.test(txtbpaisNacionalidad)) {
				alert('');
				return false;
			}

			//VALIDANDO DOMICILIO DEL BENEFICIARIO
			txtbcalle = document.getElementById('bcalle').value;
			if (txtbcalle == null || txtbcalle.length == 0
					|| /^\s+$/.test(txtbcalle)) {
				alert('La calle no puede dejarse en blanco');
				document.getElementById('bcalle').focus();
				return false;
			}

			txtbexterior = document.getElementById('bexterior').value;
			if (txtbexterior == null || txtbexterior.length == 0
					|| /^\s+$/.test(txtbexterior)) {
				alert('El número exterior no puede dejarse en blanco');
				document.getElementById('bexterior').focus();
				return false;
			}

			/*
			 txtbinterior  = document.getElementById('binterior').value;
			 if ( txtbinterior == null || txtbinterior.length == 0 || /^\s+$/.test(txtbinterior) ){
			 alert('La calle no puede dejarse en blanco');
			 return false;
			 }*/

			txtbcolonia = document.getElementById('bcolonia').value;
			if (txtbcolonia == null || txtbcolonia.length == 0
					|| /^\s+$/.test(txtbcolonia)) {
				alert('La colonia no puede dejarse en blanco');
				document.getElementById('bcolonia').focus();
				return false;
			}

			txtbcp = document.getElementById('bcp').value;
			if (txtbcp == null || txtbcp.length == 0 || /^\s+$/.test(txtbcp)) {
				alert('El código postal no puede dejarse en blanco');
				document.getElementById('bcp').focus();
				return false;
			}

			txtbCiudad = document.getElementById('bCiudad').value;
			if (txtbCiudad == null || txtbCiudad.length == 0
					|| /^\s+$/.test(txtbCiudad)) {
				alert('La ciudad no puede dejarse en blanco');
				document.getElementById('bCiudad').focus();
				return false;
			}

			txtbpaisDomicilio = document.getElementById('bpaisDomicilio').value;
			if (txtbpaisDomicilio == null || txtbpaisDomicilio.length == 0
					|| /^\s+$/.test(txtbpaisDomicilio)) {
				alert('El país no puede dejarse en blanco');
				document.getElementById('bpaisDomicilio').focus();
				return false;
			}

		}//funcion validar beneficiario
	</script>


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
		function imprimir(Imp1, Imp2, Imp3, Imp33, Imp4, Imp5, Imp55, Imp6,
				Imp7, Imp77, Imp8, Imp9, Imp10, Imp11, Imp111, Imp12, Imp13,
				Imp14, Imp15, Imp16, Imp17, Imp18, Imp19, Imp20, Imp21, Imp22,
				Imp23, Imp24, Imp25, Imp26) {
			var printContents = document.getElementById('Imp1').innerHTML
					+ document.getElementById('Imp2').innerHTML
					+ document.getElementById('Imp3').innerHTML
					+ document.getElementById('Estatus').options[document
							.getElementById('Estatus').selectedIndex].text;
			if (document.getElementById('chkFechaBloqueo').checked == 1)
				printContents += document.getElementById('check').innerHTML;
			else if (document.getElementById('chkFechaBloqueo').checked == 0)
				printContents += document.getElementById('no_check').innerHTML;

			printContents += document.getElementById('Imp4').innerHTML
					+ document.getElementById('Imp33').innerHTML
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
					+ document.getElementById('Imp111').innerHTML
					+ document.getElementById('paisNacimiento').options[document
							.getElementById('paisNacimiento').selectedIndex].text
					+ document.getElementById('Imp12').innerHTML
					+ document.getElementById('paisNacionalidad').options[document
							.getElementById('paisNacionalidad').selectedIndex].text
					+ document.getElementById('Imp13').innerHTML
					+ document.getElementById('Imp14').innerHTML
					+ document.getElementById('Imp15').innerHTML
					+ document.getElementById('actividadEco').options[document
							.getElementById('actividadEco').selectedIndex].text
					+ document.getElementById('Imp16').innerHTML
					+ document.getElementById('tipoIdentificacion').options[document
							.getElementById('tipoIdentificacion').selectedIndex].text
					+ document.getElementById('Imp17').innerHTML
					+ document.getElementById('Imp18').innerHTML
					+ document.getElementById('Imp19').innerHTML
					+ document.getElementById('Imp20').innerHTML
					+ document.getElementById('Imp21').innerHTML
					+ document.getElementById('Imp22').innerHTML
					+ document.getElementById('Imp23').innerHTML
					+ document.getElementById('Imp24').innerHTML
					+ document.getElementById('paisDomicilio').options[document
							.getElementById('paisDomicilio').selectedIndex].text
					+ document.getElementById('Imp25').innerHTML
					+ document.getElementById('Imp26').innerHTML;
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

