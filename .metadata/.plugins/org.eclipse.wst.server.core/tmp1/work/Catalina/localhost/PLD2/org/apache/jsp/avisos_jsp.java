/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2022-06-16 22:21:21 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import utilidades.Mensajes;
import entidad.Perfil;
import utilidades.PerfilUsuario;

public final class avisos_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/despliega_menu.jsp", Long.valueOf(1606845630000L));
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=ISO-8859-1");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

	//VALIDACION DEL PERFIL DE USUARIO
	HttpSession sesion = request.getSession();
	String perfilId = null;

	try {
		perfilId = sesion.getAttribute("perfilId").toString();
		Perfil perfil = (Perfil) sesion.getAttribute("perfil");
		request.setAttribute("permiso", perfil.getAvisos());
		if (perfil.getVerificacion().equals("N")) {
			Mensajes.setMensaje("No tiene persmisos para ver esta pantalla.");
			request.setAttribute("resultado", Mensajes.mensaje);
			request.setAttribute("exito", "1");
			request.getRequestDispatcher("/mensajes.jsp").forward(request, response);

		}
	} catch (Exception es) {
		System.out.println("Es un perfil de cliente");
		response.sendRedirect("/tipo_persona.jsp");
	}

      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>Efectivale - PLD</title>\r\n");
      out.write("<meta name=\"viewport\"\r\n");
      out.write("\tcontent=\"widht=device-widht, user-scalable=no, initial-scale=1.0, minimun-scale=1.0\">\r\n");
      out.write("<link rel=\"stylesheet\"\r\n");
      out.write("\thref=\"https://maxcdn.bootstrapcdn.com/font-awesome/4.6.0/css/font-awesome.min.css\">\r\n");
      out.write("<!-- Bootstrap core CSS -->\r\n");
      out.write("<link href=\"./css/bootstrap.min.css\" rel=\"stylesheet\">\r\n");
      out.write("<!-- Material Design Bootstrap **** -->\r\n");
      out.write("<link href=\"./css/mdb.min.css\" rel=\"stylesheet\">\r\n");
      out.write("<!-- Your custom styles (optional) -->\r\n");
      out.write("<link href=\"./css/style.css\" rel=\"stylesheet\">\r\n");
      out.write("<link href=\"./css/forms.css\" rel=\"stylesheet\">\r\n");
      out.write("<link href=\"./css/xlarge.css\" rel=\"stylesheet\">\r\n");
      out.write("<link href=\"./css/large.css\" rel=\"stylesheet\">\r\n");
      out.write("<link href=\"./css/medium.css\" rel=\"stylesheet\">\r\n");
      out.write("<link href=\"./css/small.css\" rel=\"stylesheet\">\r\n");
      out.write("<link href=\"./css/xsmall.css\" rel=\"stylesheet\">\r\n");
      out.write("<!-- Spinner styles -->\r\n");
      out.write("<link href=\"./css/spinner.css\" rel=\"stylesheet\">\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<!-- Spinner -->\r\n");
      out.write("\t<div class=\"spinner-wrapper\">\r\n");
      out.write("\t\t<div class=\"container-spinner\">\r\n");
      out.write("\t\t\t<div class=\"gearbox\">\r\n");
      out.write("\t\t\t\t<div class=\"overlay\"></div>\r\n");
      out.write("\t\t\t\t<div class=\"gear one\">\r\n");
      out.write("\t\t\t\t\t<div class=\"gear-inner\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"bar\"></div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"bar\"></div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"bar\"></div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"gear two\">\r\n");
      out.write("\t\t\t\t\t<div class=\"gear-inner\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"bar\"></div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"bar\"></div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"bar\"></div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"gear three\">\r\n");
      out.write("\t\t\t\t\t<div class=\"gear-inner\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"bar\"></div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"bar\"></div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"bar\"></div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"gear four large\">\r\n");
      out.write("\t\t\t\t\t<div class=\"gear-inner\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"bar\"></div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"bar\"></div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"bar\"></div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"bar\"></div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"bar\"></div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"bar\"></div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<h1 class=\"spinner-title\">Procesando...</h1>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<!-- Spinner Fin-->\r\n");
      out.write("\r\n");
      out.write("\t<header id=\"header\">\r\n");
      out.write("\t\t<div class=\"container-fluid\">\r\n");
      out.write("\t\t\t<div class=\"row align-items-center pt-2 pb-2\">\r\n");
      out.write("\t\t\t\t<div class=\"col-12 col-sm-12 col-md-4 col-lg-2 col-xl-3\">\r\n");
      out.write("\t\t\t\t\t<a class=\"navbar-brand\" href=\"/\"> <img\r\n");
      out.write("\t\t\t\t\t\tsrc=\"./img/logo-main.png\" width=\"398px\"\r\n");
      out.write("\t\t\t\t\t\tclass=\"img-fluid float-left logo mr-auto\" style=\"maxwidth: 160px;\"\r\n");
      out.write("\t\t\t\t\t\talt=\"Logo Efectivale\">\r\n");
      out.write("\t\t\t\t\t</a>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"col-12 col-sm-12 col-md-5 col-lg-8 col-xl-7\">\r\n");
      out.write("\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"col-12 header-contact-numbers\">\r\n");
      out.write("\t\t\t\t\t\t\t<h1 class=\"title title-contrata-ahora\">Sistema de Prevención\r\n");
      out.write("\t\t\t\t\t\t\t\tde Lavado de Dinero</h1>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</header>\r\n");
      out.write("\t<h5 class=\"title title-contrata-ahora white-text\">\r\n");
      out.write("\t\tSistema de Prevención de Lavado de Dinero\r\n");
      out.write("\t\t</h1>\r\n");
      out.write("\t\t<div class=\"container\">\r\n");
      out.write("\t\t\t<div class=\"main row\">\r\n");
      out.write("\t\t\t\t<article class=\"col-xs-12 col-sm-8 col-md-9 col-lg-9\">\r\n");
      out.write("\t\t\t\t\t<h2 class=\"text-muted\">Generar avisos al SAT</h2>\r\n");
      out.write("\t\t\t\t</article>\r\n");
      out.write("\t\t\t\t<aside class=\"col-xs-12 col-sm-4 col-md-3 col-lg-3\">\r\n");
      out.write("\t\t\t\t\t<p class=\"lead\">Bienvenido: Usuario PLD</p>\r\n");
      out.write("\t\t\t\t</aside>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t<div class=\"col-md-9 table-responsive\">\r\n");
      out.write("\t\t\t\t\t<form action=\"LeerExcel\" id=\"send-file\" method=\"post\"\r\n");
      out.write("\t\t\t\t\t\tenctype=\"multipart/form-data\">\r\n");
      out.write("\t\t\t\t\t\t<table border=\"0\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td><label for=\"tipo\">Selecciona el archivo:</label></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td colspan=\"3\"><input type=\"file\" name=\"filetoupload\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tid=\"archivo\" required=\"required\"></td>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t<td colspan=\"1\"><label for=\"mesreportado\"> Mes\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\tReportado: </label> <input type=\"month\" name=\"mes_reportado\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tid=\"mesreportado\" required=\"required\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tplaceholder=\"* Fecha de nacimiento\" /></td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td width=\"200\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td width=\"30\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td width=\"200\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<button class=\"btn btn-danger\" id=\"btn-procesar\">Procesar</button>\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td width=\"200\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td><label for=\"tipo\">Mensaje:</label></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td colspan=\"4\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");

										String msg = (String) request.getAttribute("message");
										if (msg == null) {
											msg = "";
										}
									
      out.write(" <textarea\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\tclass=\"form-control\">");
      out.print(msg);
      out.write("\r\n");
      out.write("                                                    </textarea>\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td align=\"center\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<button type=\"button\" class=\"btn btn-danger\" id=\"download\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<img src=\"img/descargar.png\" widht=\"32\" height=\"32\" />\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</button>\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td></td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t</form>\r\n");
      out.write("\t\t\t\t\t<form id=\"download_file\" method=\"post\" action=\"DescargarArchivo\">\r\n");
      out.write("\t\t\t\t\t\t");

							String file_name = (String) request.getAttribute("file_name");
							if (file_name != null) {
						
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<input type=\"hidden\" name=\"file_name\" id=\"file_name\"\r\n");
      out.write("\t\t\t\t\t\t\tvalue=\"");
      out.print(file_name);
      out.write("\" />\r\n");
      out.write("\t\t\t\t\t\t");

							}
						
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t</form>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"col-md-3\">\r\n");
      out.write("\t\t\t\t\t<p>\r\n");
      out.write("\t\t\t\t\t<p>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t");
      out.write('\n');
      out.write('\n');

	HttpSession ses = request.getSession();
	if (ses != null && ses.getAttribute("perfilId") != null) {
		String perfil = ses.getAttribute("perfilId").toString();
		if (perfil != null && !perfil.isEmpty()) {
			if (perfil.equals(PerfilUsuario.Administrador)) {
				out.println("<a href=\"avisos.jsp\">Genera avisos</a><br>");
				out.println("<a href=\"bloqueo_masivo.jsp\">Mantenimiento</a><br>");
				out.println("<a href=\"estatus_clientes.jsp\">Estatus Clientes</a><br>");
				out.println("<a href=\"perfiles.jsp\">Perfiles</a><br>");
				out.println("<a href=\"prospectos.jsp\">Alta de prospectos</a><br>");
				out.println("<a href=\"usuarios.jsp\">Usuarios</a><br>");
				out.println("<a href=\"rep_pld.jsp\">Representantes Legales</a><br>");
				out.println("<a href=\"contrasena.jsp\">Cambio de contraseña</a><br>");
				out.println("<a href=\"DespliegaLog.jsp\">Logs</a><br>");
				out.println("<a href=\"logout.jsp\">Salir</a>");
			} else if (perfil.equals(PerfilUsuario.PLD)) {
				out.println("<a href=\"avisos.jsp\">Genera avisos</a><br>");
				out.println("<a href=\"prospectos.jsp\">Alta prospectos</a><br>");
				out.println("<a href=\"estatus_clientes.jsp\">Estatus Clientes</a><br>");
				out.println("<a href=\"contrasena.jsp\">Cambio de contraseña</a><br>");
				out.println("<a href=\"logout.jsp\">Salir</a>");
			} else if (perfil.equals(PerfilUsuario.SOPORTE)) {
				out.println("<a href=\"avisos.jsp\">Genera avisos</a><br>");
				out.println("<a href=\"prospectos.jsp\">Alta prospectos</a><br>");
				out.println("<a href=\"estatus_clientes.jsp\">Estatus Clientes</a><br>");
				out.println("<a href=\"contrasena.jsp\">Cambio de contraseña</a><br>");
				out.println("<a href=\"DespliegaLog.jsp\">Logs</a><br>");
				out.println("<a href=\"logout.jsp\">Salir</a>");
			} else if (perfil.equals(PerfilUsuario.Admin_Telemarketing)) {
				out.println("<a href=\"estatus_clientes.jsp\">Estatus Clientes</a><br>");
				out.println("<a href=\"prospectos.jsp\">Alta de prospectos</a><br>");
				out.println("<a href=\"usuarios.jsp\">Usuarios</a><br>");
				out.println("<a href=\"contrasena.jsp\">Cambio de contraseña</a><br>");
				out.println("<a href=\"logout.jsp\">Salir</a>");
			} else if (perfil.equals(PerfilUsuario.Supervisor)) {
				out.println("<a href=\"estatus_clientes.jsp\">Estatus Clientes</a><br>");
				out.println("<a href=\"prospectos.jsp\">Alta de prospectos</a><br>");
				out.println("<a href=\"contrasena.jsp\">Cambio de contraseña</a><br>");
				out.println("<a href=\"logout.jsp\">Salir</a>");
			} else if (perfil.equals(PerfilUsuario.Ejecutivo_de_ventas)) {
				out.println("<a href=\"estatus_clientes.jsp\">Estatus Clientes</a><br>");
				out.println("<a href=\"prospectos.jsp\">Alta de prospectos</a><br>");
				out.println("<a href=\"contrasena.jsp\">Cambio de contraseña</a><br>");
				out.println("<a href=\"logout.jsp\">Salir</a>");
			} else if (perfil.equals(PerfilUsuario.Cliente)) {
				//out.println("<a href=\"tipo_persona.jsp\">Capturar datos</a><br>");
				out.println("<a href=\"contrasena.jsp\">Cambio de contraseña</a><br>");
				out.println("<a href=\"logout.jsp\">Salir</a>");
			}

		} else {
			response.sendRedirect("logout.jsp");
		}

	} else {
		response.sendRedirect("logout.jsp");
	}

      out.write("\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t</p>\r\n");
      out.write("\t\t\t\t\t</p>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<footer>\r\n");
      out.write("\t\t\t<div class=\"row footer-menu-bottom\">\r\n");
      out.write("\t\t\t\t<div class=\"col-12\">\r\n");
      out.write("\t\t\t\t\t<ul>\r\n");
      out.write("\t\t\t\t\t\t<li>Todos los Derechos Reservados Efectivale S. de R.L</li>\r\n");
      out.write("\t\t\t\t\t\t<li><a\r\n");
      out.write("\t\t\t\t\t\t\thref=\"http://www.efectivale.com/comunicados/comunicado_LAAT.htm\"\r\n");
      out.write("\t\t\t\t\t\t\ttarget=\"_top\"> Disposiciones Vigentes de la LAAT </a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a\r\n");
      out.write("\t\t\t\t\t\t\thref=\"http://www.efectivale.com.mx/avisos/avisos_de_privacidad.html\"\r\n");
      out.write("\t\t\t\t\t\t\ttarget=\"_top\"> Aviso de Privacidad </a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a\r\n");
      out.write("\t\t\t\t\t\t\thref=\"http://www.efectivale.com.mx/terminos/TCGCLIENTESDespensa-Comida.pdf\"\r\n");
      out.write("\t\t\t\t\t\t\ttarget=\"_top\"> TÃ©rminos y Condiciones </a></li>\r\n");
      out.write("\t\t\t\t\t</ul>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</footer>\r\n");
      out.write("\t\t<script src=\"js/jquery.js\"></script>\r\n");
      out.write("\t\t<script src=\"js/bootstrap.min.js\"></script>\r\n");
      out.write("\t\t<script>\r\n");
      out.write("\t\t\t$(function() {\r\n");
      out.write("\t\t\t\t$(document)\r\n");
      out.write("\t\t\t\t\t\t.on(\r\n");
      out.write("\t\t\t\t\t\t\t\t'click',\r\n");
      out.write("\t\t\t\t\t\t\t\t'#download',\r\n");
      out.write("\t\t\t\t\t\t\t\tfunction() {\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\tif ($('#file_name').length) {\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t$('#download_file').submit();\r\n");
      out.write("\t\t\t\t\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\talert('Porfavor Ingrese un archivo y presione \"Procesar\"');\r\n");
      out.write("\t\t\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\t\t});\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t\t$(document).on(\"click\", \"#btn-procesar\", function(e) {\r\n");
      out.write("\t\t\t\tif ($(\"#archivo\").val() && $(\"#mesreportado\").val()) {\r\n");
      out.write("\t\t\t\t\t$('.spinner-wrapper').fadeIn('fast');\r\n");
      out.write("\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\talert(\"Porfavor Seleccione un Archivo y Mes Reportado\");\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t</script>\r\n");
      out.write("\t\t<script>\r\n");
      out.write("\t\t\t$(document).ready(function() {\r\n");
      out.write("\t\t\t\t//Asignando los persmisos\r\n");
      out.write("\t\t\t\tdocument.getElementById('download').disabled = true;\r\n");
      out.write("\t\t\t\tdocument.getElementById('btn-procesar').disabled = true;\r\n");
      out.write("\r\n");
      out.write("\t\t\t\ttxtPersmiso = '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${permiso}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("';\r\n");
      out.write("\t\t\t\tif (txtPersmiso == 'E') {\r\n");
      out.write("\t\t\t\t\tdocument.getElementById('download').disabled = false;\r\n");
      out.write("\t\t\t\t\tdocument.getElementById('btn-procesar').disabled = false;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t</script>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
