<%@page import="entidad.PistaAudit"%>
<%@page import="datosRatos.DatosPistaAudit"%>
<%@page import="java.util.ArrayList"%>
<%@page import="datosRatos.DatosEstatusCliente"%>
<%@page import="datosRatos.DatosPistaAudit"%>
<%@page import="entidad.EstatusClientes"%>
<%@page import="utilidades.Rutas"%>
<%@page import="java.io.File"%>
<%@page import="utilidades.PerfilUsuario"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="org.apache.commons.io.comparator.LastModifiedFileComparator"%>
<%@page import="entidad.Perfil"%>
<%@page import="utilidades.Mensajes"%>

<%
//VALIDACION DEL PERFIL DE USUARIO
// 	HttpSession sesion = request.getSession();

// 	String perfilId = null;
	

// 	try {
// 		perfilId = sesion.getAttribute("perfilId").toString();
// 	    	Perfil perfil = (Perfil) sesion.getAttribute("perfil");
// 		request.setAttribute("permiso", perfil.getVerificacion());
		
// 		if (perfil.getVerificacion().equals("N")) {
// 			Mensajes.setMensaje("No tiene persmisos para ver esta pantalla.");
// 			request.setAttribute("resultado", Mensajes.mensaje);
// 			request.setAttribute("exito", "1");
// 			request.getRequestDispatcher("/mensajes.jsp").forward(request, response);

// 		}
// 	} catch (Exception es) {
// 		System.out.println("Es un perfil de cliente");
		
// 	}
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
</head>
<body onload="startTimer(90)">
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
					<h2 class="text-muted">Archivos Logs</h2>
				</article>
				<aside class="col-xs-12 col-sm-4 col-md-3 col-lg-3">
					<p class="lead">
						Bienvenido:
						<%@include file="detectausuario.jsp"%>
					</p>
					

				</aside>
			</div>
		</div>
		<form id="frmHistorial" action="" method="post"
			autocomplete="off">
			<br>

			<div class="container">
				<table cssclass="table table-sm table-striped table-bordered table-hover table-condensed"
					id="tblEstatusClientes" name="tblEstatusClientes">
					<tr class="info">
					<td colspan="3"><label for="tipo">Logs</label><br />  
					 <img
									src="img/lupa.jpg" height="32" width="32" style="display: none"
									id="lupaCurp" />
							</a>
 <%
 	
 String idCLiente = "";
// 	String rutaOrigen = Rutas.rutaCarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirCurp
// 			+ Rutas.separador;
	
// 	String rutaDestino = Rutas.rutaDescarga + Rutas.separador + idCLiente + Rutas.separador + Rutas.dirCurp
// 			+ Rutas.separador;
	


	String rutaOrigen = Rutas.rutaCarga + Rutas.separador +"Logs/";
	String rutaDestino = Rutas.rutaDescarga + Rutas.separador + "Logs" + Rutas.separador;
	System.out.println("ruta "+ rutaOrigen);
	System.out.println("ruta 2 "+ rutaDestino);
	
	File[] listaArchivos = new File(rutaOrigen).listFiles();
	if (listaArchivos != null && listaArchivos.length > 0) {
		Arrays.sort(listaArchivos, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
		out.println("<INPUT type='hidden' id='HayArchivoCurp' name='HayArchivoCurp' value='si'");
		int a = 0;
// 		if ((perfilId.compareTo("1") == 0) || (perfilId.compareTo("2") == 0)) { // Puede ver el historial                                                                                                                                                                                
			out.println("<p>Historial de documentos guardados:</p>");
// 			a = listaArchivos.length;
// 		} else {
// 			out.println("<p>Último archivo guardado:</p>");
// 			a = 1;
// 		}
		for (int i = 0; i < a; i++) {
			File archivoDestino = new File(rutaDestino + listaArchivos[i].getName());
			System.out.println(archivoDestino.getAbsolutePath());
			System.out.println(archivoDestino.getParent());

			//String dirRootDes = new File(Rutas.rutaDescarga + Rutas.separador + idCLiente).getParentFile().getName();
			String dirRootDes = Rutas.rutaDescarga + "/Logs/";
			if (!new File(archivoDestino.getParent()).exists()) {
				try {
					new File(archivoDestino.getParent()).mkdirs();
				} catch (Exception es) {
					es.printStackTrace();
				}
			}

			try {
				out.println("<p>" + listaArchivos[i].getName() + " <a href=\"javascript:window.open('"
						+ dirRootDes + "/" + listaArchivos[i].getName()
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
					</table>
					<button class="btn btn-danger" id="btnClose"
						onclick="window.close()"><a href="estatus_clientes.jsp">Cerrar</a></button>
					</form>

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
					<script src="js/validaciones.js"></script>
					<script src="js/bootstrap.min.js"></script>
					<script src="js/mdb.min.js.descarga"></script>

					
					<script>
						
					</script>
</body>
</html>