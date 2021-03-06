	<%@page import="datosRatos.DatosEstatusCliente"%>
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
								de Lavado de Dinero-code</h1>
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
						<%@ include file="detectausuario.jsp"%>
					</p>
				</aside>
			</div>
			<div class="row">
				<div class="col-md-9 table-responsive">
				 <!-- en esta parte,estamos llamando al servlet que es control de busqueda -->
					<form id="frmEstatus" action="BusquedaEstatusCliente" method="post"
						autocomplete="off">
						<br>
						<table border="0">
							<tr>
								<td width="300px">
									<div class="md-form form-sm">
										<input class="form-control" onkeyup="mayus(this)" type="text"
											id="nombre" name="nombre"
											value="<%String vNombre = (String) request.getAttribute("nombre");
			if (vNombre != null) {
				out.println(vNombre);
			}%>">
										<label for="nombre">* Nombre</label>
									</div> <input type="hidden" id="move_offset" name="move_offset"
									value="">
								</td>
								<td width="50px"></td>
								<td width="300px">
									<div class="md-form form-sm">
										<input class="form-control" onkeyup="mayus(this)" type="text"
											id="rfc" name="rfc"
											value="<%String vrfc = (String) request.getAttribute("rfc");
			if (vrfc != null) {
				out.println(vrfc);
			}%>">
										<label for="rfc">* RFC</label>
									</div>
								</td>
								<td width="90px"></td>
								<td><a href="javascript:formSubmit('frmEstatus',0)"><img
										src="img/lupa.jpg" width="32" height="32" /></a></td>
								<td width="30px"></td>
								<td><a href="javascript:formSubmitXls('frmEstatus')"> <img
										src="img/excel.png" width="32" height="32" /> <!--<a href="javascript:Tabla2Excel('tblEstatusClientes', 'EstatusClientes')">
							<img src="img/excel.png" width="32" height="32" />-->
								</a></td>
							</tr>
							<tr>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="idCliente" name="idCliente"
											value="<%String vidCliente = (String) request.getAttribute("idCliente");
			if (vidCliente != null) {
				out.println(vidCliente);
			}%>">
										<label for="idCliente">* Id Salesforce</label>
									</div>
								</td>
								<td width="50px"></td>
								<td>
									<div class="md-form form-sm">
										<input class="form-control" type="text" onkeyup="mayus(this)"
											id="noCliente" name="noCliente"
											value="<%String vnoCliente = (String) request.getAttribute("noCliente");
			if (vnoCliente != null) {
				out.println(vnoCliente);
			}%>">
										<label for="noCliente">* No Cliente</label>
									</div>
								</td>
							</tr>
							<tr>
								<td><select class="browser-default" id="tipoPersona"
									name="tipoPersona">
										<option value="">* Tipo</option>
										<%
											String vtipoPersona;
											if (request.getAttribute("tipoPersona") == null)
												vtipoPersona = "";
											else
												vtipoPersona = (String) request.getAttribute("tipoPersona");

											if (vtipoPersona.compareTo("") != 0)
												out.println("<option value=''>Todos</option>)");
											else
												out.println("<option value='' selected='true'>Todos</option>)");

											if (vtipoPersona.compareTo("F") != 0)
												out.println("<option value='F'>Física</option>)");
											else
												out.println("<option value='F' selected='true'>Fisica</option>)");

											if (vtipoPersona.compareTo("M") != 0)
												out.println("<option value='M'>Moral</option>)");
											else
												out.println("<option value='M' selected='true'>Moral</option>)");

											if (vtipoPersona.compareTo("X") != 0)
												out.println("<option value='X'>Fideicomiso</option>)");
											else
												out.println("<option value='X' selected='true'>Fideicomiso</option>)");

											if (vtipoPersona.compareTo("G") != 0)
												out.println("<option value='G'>Gobierno</option>)");
											else
												out.println("<option value='G' selected='true'>Gobierno</option>)");
										%>


								</select></td>
								
								<td width="50px"></td>
								<td><select class="browser-default" id="estatus"
									name="estatus">
										<option value="">* Estatus</option>
										<%
											String vEstatusFiltro;
											if (request.getAttribute("estatus") == null)
												vEstatusFiltro = "";
											else
												vEstatusFiltro = (String) request.getAttribute("estatus");

											if (vEstatusFiltro.compareTo("") != 0)
												out.println("<option value=''>Todos</option>)");
											else
												out.println("<option value='' selected='true'>Todos</option>)");

											if (vEstatusFiltro.compareTo("S") != 0)
												out.println("<option value='S'>No iniciado</option>)");
											else
												out.println("<option value='S' selected='true'>No iniciado</option>)");

											if (vEstatusFiltro.compareTo("P") != 0)
												out.println("<option value='P'>Pendiente</option>)");
											else
												out.println("<option value='P' selected='true'>Pendiente</option>)");

											if (vEstatusFiltro.compareTo("V") != 0)
												out.println("<option value='V'>Por Validar</option>)");
											else
												out.println("<option value='V' selected='true'>Por Validar</option>)");

											if (vEstatusFiltro.compareTo("A") != 0)
												out.println("<option value='A'>Validado</option>)");
											else
												out.println("<option value='A' selected='true'>Validado</option>)");

											if (vEstatusFiltro.compareTo("I") != 0)
												out.println("<option value='I'>Invalido</option>)");
											else
												out.println("<option value='I' selected='true'>Invalido</option>)");

											if (vEstatusFiltro.compareTo("G") != 0)
												out.println("<option value='G'>Ventas Gobierno</option>)");
											else
												out.println("<option value='G' selected='true'>Ventas Gobierno</option>)");
											if (vEstatusFiltro.compareTo("N") != 0)
												out.println("<option value='N'>Inactivo</option>)");
											else
												out.println("<option value='N' selected='true'>Inactivo</option>)");
											if (vEstatusFiltro.compareTo("R") != 0)
													out.println("<option value='R'>Actualizar</option>)");
											else
											    out.println("<option value='R' selected='true'>Actualizar</option>)");
											
												
										%>

								</select></td>
								
								<td><select class="browser-default" id="bandera"
									name="bandera">
<!-- 									<option value="">Todos</option> -->
										<%
											String vBanderaFiltro;
											if (request.getAttribute("bandera") == null)
												vBanderaFiltro = "";
											else
												vBanderaFiltro = (String) request.getAttribute("bandera");

											if (vBanderaFiltro.compareTo("0") != 0)
												out.println("<option value=''>Todos</option>)");
											else
												out.println("<option value='' selected='true'>Todos</option>)");

											if (vEstatusFiltro.compareTo("1") != 0)
												out.println("<option value='1'>Riesgo</option>)");
											else
												out.println("<option value='1' selected='true'>Riesgo</option>)");
											
											if (vEstatusFiltro.compareTo("0") != 0)
													out.println("<option value='0'>Sin Riesgo</option>)");
											else
													out.println("<option value='0' selected='true'> Sin RiesgoRiesgo</option>)");
												
										%>

								</select></td>
							</tr>
							<tr>
								<td></td>
								</td>
								<td></td>
								<td></td>
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
		<div class="container">
			<table class="table table-sm table-striped table-bordered table-hover table-condensed"
				id="tblEstatusClientes" name="tblEstatusClientes">
				<tr class="info">
					<th>Id Sales Force</th>

					<th width="10%" >Id Cliente</th>
					<th width="10%">Nombre</th>
					<th width="10%">RFC</th>
					<th width="5%">Tipo</th>
					<th width="5%">Estatus</th>
					<th width="10%">Validó</th>
					<th width="10%">Fecha de validación</th>
					<th width="10%">Fecha de bloqueo</th>
					<th width="10%">Fecha ult. mod.</th>
					<th width="10%">Mensaje</th>
					<th width="10%">Notas</th>
					<th width="10%">Ejecutivo</th>
                <th width="10%">Coincidencia en listas</th>

				</tr>

				<%
					int OFFSET_VALUE = 50;
					String where = "";
					String usuario = "";
					String IdPerfil = "";
					String Offset = "";

					try {
						where = (String) request.getAttribute("where");
						Offset = (String) request.getAttribute("offset");
					} catch (Exception es) {
						es.printStackTrace();
					}

					if (Offset == null) {
						Offset = "0";
					}
					//Recuperando el usuario autenticado
					try {
						HttpSession sesion = request.getSession();
						usuario = sesion.getAttribute("user").toString();
						IdPerfil = sesion.getAttribute("perfilId").toString();

					} catch (Exception es) {
						es.printStackTrace();
					}

					//EstatusClientes[] c = new DatosEstatusCliente().getList(where, usuario, IdPerfil);
					EstatusClientes[] c = null;
					if (where != null) {
						c = new DatosEstatusCliente().getList(where, usuario, IdPerfil, Offset);
					}
					if (c != null) {

						for (int i = 0; i < c.length; i++) {

							String servlet = "";
							if (c[i].getTipoPersona().trim().equals("F")) {
								servlet = "EdicionPersonaFisica";
							} else if (c[i].getTipoPersona().trim().equals("M")) {
								servlet = "EdicionPersonaMoral";
							} else if (c[i].getTipoPersona().trim().equals("X")) {
								servlet = "EdicionFideicomiso";
							} else if (c[i].getTipoPersona().trim().equals("G")) {
								servlet = "EdicionGobierno";
							} else {
								servlet = "tipo_persona.jsp";
							}

							out.println("<tr>");
							out.println("<td><a href=\"" + servlet + "?idCliente=" + c[i].getClienteId().trim()
									+ "&esVerificacion=1&rfc=" + c[i].getRfc().trim().replace("&", "%26")
									+ "\" style=\"text-decoration: underline; color:blue;\">" + c[i].getClienteId() + "</a>");
							out.println("</td>");

							out.println("<td>" + c[i].getIdClienteNumerico());
							out.println("</td>");

							//<td><a href="verificacion.html">A-12345</a>
							//</td>					
							out.println("<td>" + c[i].getRazonSocial());
							out.println("</td>");
							out.println("<td>" + c[i].getRfc());
							out.println("</td>");
							out.println("<td>" + c[i].getTipoPersona());
							out.println("</td>");
							out.println("<td>" + c[i].getEstatus());
							out.println("</td>");
							out.println("<td>" + c[i].getUsuariovalido());
							out.println("</td>");
							out.println("<td>" + c[i].getFechaValido());
							out.println("</td>");
							out.println("<td>" + c[i].getFechaBloqueo());
							out.println("</td>");
							out.println("<td>" + c[i].getFechaModificacion());
							out.println("</td>");
							out.println("<td>" + c[i].getMensaje());
							out.println("</td>");
							out.println("<td>" + c[i].getNotas());
							out.println("</td>");
							out.println("<td>" + c[i].getEjecutivo());
							out.println("</td>");
							out.println("<td>");
							
							   if (c[i].getRiesgo() == 1 ){
							  	 out.println("<img src='img/checked.png' height='32' width='32'>");
							    }else{
							     out.println("<img src='img/no_checked.png' height='32' width='32'>");
							    }
							   
							out.println("</td>");
							out.println("</tr>");

						} //fin del for
						
						
						
						out.println("</table> ");
						out.println("<input type=" + (char) 34 + "hidden" + (char) 34 + " id=" + (char) 34 + "offset"
								+ (char) 34 + " name=" + (char) 34 + "offset" + (char) 34 + " value=" + (char) 34 + Offset
								+ (char) 34 + ">");
						// Pintar el retroceso
						if (Integer.parseInt(Offset) >= OFFSET_VALUE) {
							out.println("<a href=" + (char) 34 + "javascript:formSubmit('frmEstatus',-" + OFFSET_VALUE + ")"
									+ (char) 34 + "><img src='img/PREV.png' width='18' height='32' /></a>");
							//out.println("<input type='button' value='<' onclick=" + (char)34 + "javascript:formSubmit('frmEstatus', -" + OFFSET_VALUE + ")" + (char)34 + "> ");
						}

						out.println("<label>Del registro " + Offset + " al "
								+ Integer.toString(Integer.parseInt(Offset) + c.length) + "</label>");
						if ((c.length > (OFFSET_VALUE - 1))) {
							out.println("<a href=" + (char) 34 + "javascript:formSubmit('frmEstatus'," + OFFSET_VALUE + ")"
									+ (char) 34 + "><img src='img/SIG.png' width='18' height='32' /></a>");
							//out.println("<input type='button' value='>' onclick=" + (char)34 + "javascript:formSubmit('frmEstatus', " + OFFSET_VALUE + ")" + (char)34 + "> ");
						}
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
							return window.btoa(unescape(encodeURIComponent(s)))
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