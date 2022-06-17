<%@page import="datosRatos.DatosClienteRaro"%>
<%@ include file="valida_login.jsp"%>
<%@page import="entidad.Cliente"%>
<%@page import="entidad.UsuarioTransitivo"%>
<!DOCTYPE html>
<html lang="es">
<head>
<title>Efectivale - PLD</title>
<meta charset="UTF-8">
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
<script>
	function SelectType() {
		//window.location.href = 'datos_persona_fisica.html';
		var e = document.getElementById("ddlTipo");
                var sesleccion = e.options[e.selectedIndex].value;
                
                <%String vClienteParam = request.getParameter("idCliente");
			String vRFCParam = request.getParameter("rfc");

			String vURL = "";%>
                
		var strUser = e.options[e.selectedIndex].value;	
		if (strUser == 'Fisica')
                    <%vURL = "datos_persona_fisica.jsp?idCliente=" + vClienteParam + "&rfc="
					+ vRFCParam.trim().replace("&", "%26");%>
                    window.location.href = '<%out.print(vURL);%>';                                                
		else if (strUser == 'Moral')
                    <%vURL = "datos_persona_moral.jsp?idCliente=" + vClienteParam + "&rfc="
					+ vRFCParam.trim().replace("&", "%26");%>                                
                    window.location.href = '<%out.print(vURL);%>';                                                                                
		else if (strUser == 'Fideicomiso')
                    <%vURL = "datos_fideicomiso.jsp?idCliente=" + vClienteParam + "&rfc=" + vRFCParam.trim().replace("&", "%26");%>                                                                
                    window.location.href = '<%out.print(vURL);%>';                                                                            
		else if (strUser == 'Gobierno')
                    <%vURL = "datos_gobierno.jsp?idCliente=" + vClienteParam + "&rfc=" + vRFCParam.trim().replace("&", "%26");%>                                                                                                
                    window.location.href = '<%out.print(vURL);%>';                                                                            
                else if ( alert('Debe seleccionar el tipo de persona') );
	}
	</script>
</head>
<body>
	<%
		//INSTRUCCIONES PARA MANDAR PANTALLA DE EDICION SI EXISTE CLIENTE PARCIALMENTE CAPTURADO
		//         String rfc  = request.getAttribute("rfc").toString();
		if (request.getAttribute("esEdicion") != null && request.getAttribute("esEdicion").toString().equals("1")) {

			Cliente x = new DatosClienteRaro().existeClienteRFC(vRFCParam);

			if (x != null && !x.getTipoPersona().isEmpty()) {
				String tipoPersona = x.getTipoPersona();
				if (tipoPersona.equals("F")) {
					UsuarioTransitivo.setCliente(x);
					response.sendRedirect("EdicionPersonaFisica?idCliente=" + vClienteParam + "&rfc="
							+ vRFCParam.trim().replace("&", "%26") + "&ver=no");
				} else if (tipoPersona.equals("M")) {
					UsuarioTransitivo.setCliente(x);
					response.sendRedirect("EdicionPersonaMoral?idCliente=" + vClienteParam + "&rfc="
							+ vRFCParam.trim().replace("&", "%26") + "&ver=no");
				} else if (tipoPersona.equals("X")) {
					UsuarioTransitivo.setCliente(x);
					response.sendRedirect("EdicionFideicomiso?idCliente=" + vClienteParam + "&rfc="
							+ vRFCParam.trim().replace("&", "%26") + "&ver=no");
				} else if (tipoPersona.equals("G")) {
					UsuarioTransitivo.setCliente(x);
					response.sendRedirect("EdicionGobierno?idCliente=" + vClienteParam + "&rfc="
							+ vRFCParam.trim().replace("&", "%26") + "&ver=no");
				}
			} //if cliente de null
		} // if request rfc diferente de null
	%>


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
						Bienvenido <small>Seleccione por favor el tipo de persona:</small>
					</h2>
				</article>
				<aside class="col-xs-12 col-sm-4 col-md-3 col-lg-3">
					<p class="lead">
						Bienvenido:
						<%@ include file="detectausuario.jsp"%>
					</p>
				</aside>
			</div>
			<div class="row">
				<div class="col-md-9 table-responsive">
					<table border="0">
						<tr>
							<td width="100px"><label for="id">Id del cliente:</label></td>
							<td width="300px"><label for="id"> <%
 	if (vClienteParam == null) {
 		response.sendRedirect("prospectos.jsp");
 	} else {
 		out.println(vClienteParam);
 	}
 %></label></td>
							<td width="30px"></td>
							<td>
								<%
									out.println("<select class='browser-default' name='' id='ddlTipo'>");
									if (vRFCParam.length() == 12) {
										out.println("<option value=''>* Tipo de persona</option>");
										out.println("<option value='Moral'>Moral</option>");
										out.println("<option value='Fideicomiso'>Fideicomiso</option>");
										out.println("<option value='Gobierno'>Gobierno</option>");

									} else if (vRFCParam.length() == 13) {
										out.println("<option value=''>* Tipo de persona</option>");
										out.println("<option value='Fisica'>Física</option>");

									}
									out.println("</select>");
								%>

							</td>
						</tr>
						<tr>
							<td height="20px"></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td>
								<button class="btn btn-danger" onclick="SelectType()">Siguiente</button>
							</td>
						</tr>
					</table>
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
							target="_top"> TÃ©rminos y Condiciones </a></li>
					</ul>
				</div>
			</div>
		</footer>
		<script src="js/jquery.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script>
            var txtExito = document.getElementById('exito').value;
            var txtRespuesta = document.getElementById('respuesta').value;
            if ( txtExito == '1' && txtRespuesta.length > 0 ) {
                alert(txtRespuesta);
                document.getElementById('exito').value = '0';
            }
        </script>
</body>
</html>
