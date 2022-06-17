<%@page import="entidad.PistaAudit"%>
<%@page import="datosRatos.DatosPistaAudit"%>
<%@page import="java.util.ArrayList"%>
<%@page import="datosRatos.DatosEstatusCliente"%>
<%@page import="datosRatos.DatosPistaAudit"%>
<%@page import="entidad.EstatusClientes"%>

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
					<h2 class="text-muted">Estatus de clientes</h2>
				</article>
				<aside class="col-xs-12 col-sm-4 col-md-3 col-lg-3">
					<p class="lead">
						Bienvenido:
						<%@include file="detectausuario.jsp"%>
					</p>
					<button class="btn btn-danger" id="btnClose"
						onclick="window.close()">Cerrar</button>

				</aside>
			</div>
		</div>
		<form id="frmHistorial" action="BusquedaEstatusCliente" method="post"
			autocomplete="off">
			<br>

			<div class="container">
				<table css
					class="table table-sm table-striped table-bordered table-hover table-condensed"
					id="tblEstatusClientes" name="tblEstatusClientes">
					<tr class="info">
						<th>Fecha</th>

						<th>Editó</th>
						<th>Perfil</th>
						<th>Campo</th>
						<th>Valor anterior</th>
						<th>Valor nuevo</th>



					</tr>

					<%
						String vClienteParam = request.getParameter("idCliente");
						String where = "";
						String usuario = "";
						String IdPerfil = "";

						try {
							where = (String) request.getAttribute("where");

						} catch (Exception es) {
							es.printStackTrace();
						}

						//Recuperando el usuario aitenticado
						try {
							HttpSession sesion = request.getSession();
							usuario = sesion.getAttribute("user").toString();
							IdPerfil = sesion.getAttribute("perfilId").toString();

						} catch (Exception es) {
							es.printStackTrace();
						}

						//EstatusClientes[] c = new DatosEstatusCliente().getList(where, usuario, IdPerfil);
						DatosPistaAudit auditoria = new DatosPistaAudit();
						ArrayList<PistaAudit> c = auditoria.cambios(vClienteParam);

						if (c != null) {

							for (int i = 0; i < c.size(); i++) {

								out.println("<tr>");

								out.println("<td>" + c.get(i).getFecha());
								out.println("</td>");
								out.println("<td>" + c.get(i).getClienteId());
								out.println("</td>");
								out.println("<td>" + c.get(i).getPerfil());
								out.println("</td>");
								out.println("<td>" + c.get(i).getCampoTexto());
								out.println("</td>");
								out.println("<td>" + c.get(i).getValorAnterior());
								out.println("</td>");
								out.println("<td>" + c.get(i).getValorNuevo());
								out.println("</td>");

								out.println("</tr>");

							} //fin del for
							out.println("</table> ");
							//                                            out.println("<input type=" + (char)34 + "hidden" + (char)34 + " id=" + (char)34 + "offset" + (char)34 + " name=" + (char)34 + "offset" + (char)34 + " value=" + (char)34 + Offset + (char)34 + ">");                                                                                            
							//                                            // Pintar el retroceso
							//                                            if (Integer.parseInt(Offset) >= OFFSET_VALUE) {
							//                                                out.println("<a href=" + (char)34 + "javascript:formSubmit('frmHistorial',-" + OFFSET_VALUE + ")" + (char)34 + "><img src='img/PREV.png' width='18' height='32' /></a>");
							//                                                //out.println("<input type='button' value='<' onclick=" + (char)34 + "javascript:formSubmit('frmEstatus', -" + OFFSET_VALUE + ")" + (char)34 + "> ");
							//                                            }
							//                                            
							//                                            out.println("<label>Del registro " + Offset + " al " + Integer.toString(Integer.parseInt(Offset) + c.size()) + "</label>");
							//                                            if ((c.size() > (OFFSET_VALUE - 1))) {       
							//                                                out.println("<a href=" + (char)34 + "javascript:formSubmit('frmHistorial'," + OFFSET_VALUE + ")" + (char)34 + "><img src='img/SIG.png' width='18' height='32' /></a>");                                                
							//                                                //out.println("<input type='button' value='>' onclick=" + (char)34 + "javascript:formSubmit('frmEstatus', " + OFFSET_VALUE + ")" + (char)34 + "> ");
							//                                            }                                            
						} else {
							out.println("</table> ");
						}
					%>
					</div>
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
						function formSubmit(formulario, move_offset) {
							document.getElementById('move_offset').value = move_offset;
							var SentForm = document.getElementById(formulario);
							SentForm.action = "BusquedaEstatusCliente";
							SentForm.target = "";
							SentForm.submit();
						}
					</script>

					<script>
						function formSubmitXls(formulario) {
							var SentForm = document.getElementById(formulario);
							SentForm.action = "XlsEstatusCliente";
							//                var w = window.open('about:blank','Popup_Window','toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=400,height=300,left = 312,top = 234');                
							//                SentForm.target="Popup_Window";
							SentForm.submit();
						}
					</script>

					<script>
						var Tabla2Excel = (function() {
							var uri = 'data:application/vnd.ms-excel;base64,', template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>', base64 = function(
									s) {
								return window
										.btoa(unescape(encodeURIComponent(s)))
							}, format = function(s, c) {
								return s.replace(/{(\w+)}/g, function(m, p) {
									return c[p];
								})
							}
							return function(table, nombre) {
								if (!table.nodeType)
									table = document.getElementById(table)
								var ctx = {
									worksheet : nombre || 'Worksheet',
									table : table.innerHTML
								}
								window.location.href = uri
										+ base64(format(template, ctx))
							}
						})();
					</script>

					<script>
						var txtExito = document.getElementById('exito').value;
						var txtRespuesta = document.getElementById('respuesta').value;
						if (txtExito == '1' && txtRespuesta.length > 0) {
							alert(txtRespuesta);
							document.getElementById('exito').value = '0';
						}
					</script>
</body>
</html>