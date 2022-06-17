<%@page import="datosRatos.DatosRep_PLD"%>
<%@page import="utilidades.PerfilUsuario"%>
<%@page import="utilidades.Mensajes"%>
<%@ include file="valida_login.jsp"%>
<%@page import="entidad.RepLegal_PLD"%>
<%@page import="entidad.EstatusUsuario"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="datosRatos.DatosEstatususuario"%>
<%@page import="entidad.EstatusUsuario"%>
<%@page import="entidad.Perfil"%>
<%@page import="datosRatos.DatosPerfil"%>
<%
	//VALIDACION DEL PERFIL DE USUARIO
	HttpSession sesion = request.getSession();
	String perfilId = null;

	try {
		perfilId = sesion.getAttribute("perfilId").toString();
		Perfil perfil = (Perfil) sesion.getAttribute("perfil");
		request.setAttribute("permiso", perfil.getUsuarios());
		if (perfil.getVerificacion().equals("N")) {
			Mensajes.setMensaje("No tiene persmisos para ver esta pantalla.");
			request.setAttribute("resultado", Mensajes.mensaje);
			request.setAttribute("exito", "1");
			request.getRequestDispatcher("/mensajes.jsp").forward(request, response);

		}
	} catch (Exception es) {
		System.out.println("Es un perfil de cliente" + es.toString());
		response.sendRedirect("/tipo_persona.jsp");
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
</head>
<body>


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
					<h2 class="text-muted">Catálogo de representantes</h2>
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
					<form id="frmUsuarios" action="BusquedaRepre" method="post"
						autocomplete="off">
						<br> <input type="hidden" name="respuesta" id="respuesta"
							value="<c:out value="${resultado}" />" /> <input type="hidden"
							name="exito" id="exito" value="<c:out value="${exito}" />" /> <input
							type="hidden" name="where" id="where"
							value="<c:out value="${where}" />" />
						<table border="0" name="" id="">

							<tr>
								<td width="200px">
									<div class="md-form form-sm">
										<input class="form-control" onkeyup="mayus(this)" type="text"
											id="usuario" name="usuario" value=""> <label
											for="nombre">* Número de Empleado</label>
									</div>
								</td>
								<td width="50px"></td>
								<td width="200px">
									<div class="md-form form-sm">
										<input class="form-control" onkeyup="mayus(this)" type="text"
											id="nombre" name="nombre" value=""> <label
											for="usuario">* Nombre</label>
									</div>
								</td>
								
								<td width="170px"></td>
								<td><a href="javascript:formSubmit('frmUsuarios')"> <img
										src="img/lupa.jpg" width="32" height="32" />
								</a></td>
								<td width="30px"><a href="edicion_representante.jsp"> <img
										id="nuevo" src="img/nuevo.png" width="32" height="32"
										style="display: none" />
								</a></td>
								<td><a href="javascript:formUsuarioSubmint()"
									id="linkBorrar"><img src="img/borrar.png" width="32"
										height="32" id="imgBorrar" style="display: none" /></a></td>
					
							</tr>
							

							<tr>
								<td></td>
								</td>
								<td></td>
								<td></td>
								</td>
							</tr>
						</table>
					</form>


					<table class="table table-striped table-bordered table-hover"
						id="tblUsuarios" name="tblUsuarios">

						<tr class="info">
							<th>Numero De Usuario</th>
							<th>Nombre</th>
							<th>Email</th>
							<th>Estatus</th>
							<th>Seleccionar</th>
						</tr>


						<%
							String where= "";
							
							
						try {
							where = (String) request.getAttribute("where");
						} catch (Exception es) {
							es.printStackTrace();
						}
							
							String where2 = "";
							
							try {
								where2 = (String) request.getAttribute("where2");
							} catch (Exception es) {
								es.printStackTrace();
							}

							RepLegal_PLD[] legal = new DatosRep_PLD().getList(where);
							if (legal != null) {

								for (int i = 0; i < legal.length; i++) {

									int u = legal[i].getId_siseg();

									out.println("<tr>");
									out.println("<td>");
									out.println("<form action=\"EdicionRepresentante_pld\" method=\"post\" id=\"frmEdicionusuario" + i + "\">");
									out.println("<input id=\"usuario\" name=\"usuario\" type=\"hidden\" value=\"" + u + "\">");
									out.println("<a href=\"javascript:formSubmit('frmEdicionusuario" + i + "') "
											+ "\"   style=\"text-decoration: underline; color:blue;\">" + legal[i].getId_siseg()
											+ "</a></form>");
									out.println("</td>");
									out.println("<td>" + legal[i].getNombre());
									out.println("</td>");
									out.println("<td>" + legal[i].getEmail());
									out.println("</td>");
									out.println("<td>" + legal[i].getEstatus().getDescripcion());
									out.println("</td>");
									
									out.println("<td align=\"center\">");
									out.println(
											"<form id=\"frmBorrarUsuario\" name=\"frmBorrarUsuario\" action=\"BorrarUsuarioRepre\" method=\"post\" >");
									out.println("<input type=\"hidden\" id=\"idUsuario\" name=\"idUsuario\" value=\""
											+ legal[i].getId_siseg() + "\" />");
									out.println(
											"<input type=\"checkbox\"  onclick=\"getIdUsuario(this)\" class=\"browser-default\" id=\"user"
													+ Integer.toString(i) + "\" name=\"user" + Integer.toString(i) + "\" value=\""
													+ legal[i].getId_siseg() + "\">");
									out.println("<label for=\"" + "user" + Integer.toString(i) + "\"   \"></label>");
									out.println("</form>");
									out.println("</td>");
									out.println("</tr>");
								}
							}
						%>

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
                function getIdUsuario(check){
                    document.getElementById('idUsuario').value = check.value;
                }
            </script>

		<script>
                function formUsuarioSubmint(){
                    document.getElementById('frmBorrarUsuario').submit();
                }
            </script>

		<script>
            function formSubmit(formulario)
            {   
                document.getElementById( formulario ).submit();
            }    
            </script>

		<script>
                var txtExito = document.getElementById('exito').value;
                var txtRespuesta = document.getElementById('respuesta').value;

                if ( txtExito == '1' && txtRespuesta.length > 0 ){
                    alert (txtRespuesta);
                    document.getElementById('exito').value = '0';
                }
            </script>

		<script>
            $( document ).ready(function() {
                
            const PERFIL_ADMINISTRADOR = 1;
            const PERFIL_ADMINISTRADOR_TELEMARKETING = 3;
            const PERFIL_SUPERVISOR = 4;
            const PERFIL_EJECUTIVO_DE_VENTAS = 5;
            
            
                //Asignando persmisos de usuario
//                document.getElementById('imgBorrar').style.display='none';
//                
//                txtPermiso = '${permiso}';
//                if ( txtPermiso == 'E'){
//                    document.getElementById('imgBorrar').style.display='block';
//                }
                
            
            
//              txtPerfilUsuarioSistema = "";
                        <%String perfil = (String) sesion.getAttribute("perfilId");
			out.println("txtPerfilUsuarioSistema ='" + perfil + "';");%>
                //alert(txtPerfilUsuarioSistema);
                if (txtPerfilUsuarioSistema == PERFIL_ADMINISTRADOR) {
                    document.getElementById("nuevo").style.display = "block";
                    document.getElementById("imgBorrar").style.display = "block";                    
                }
//            if ( txtPerfilUsuarioSistema == PERFIL_ADMINISTRADOR_TELEMARKETING  ) {
//                            document.getElementById("nuevo").style.display = "none";
//                            
//
//                        }
                
            });
            
                    
            
            </script>
</body>
</html>