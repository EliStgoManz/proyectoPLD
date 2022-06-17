<%-- 
    Document   : despliega_menu
    Created on : 14/06/2018, 02:38:40 PM
    Author     : israel.garcia
--%>
<%@page import="utilidades.PerfilUsuario"%>
<%
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
%>