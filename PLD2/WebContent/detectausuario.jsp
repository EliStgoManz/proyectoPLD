<%-- 
    Document   : detectausuario
    Created on : 5/06/2018, 08:25:42 AM
    Author     : Israel Osiris García
--%>

<%@page import="entidad.UsuarioSistema"%>
<%@page import="entidad.UsuarioTransitivo"%>
<%
	if (request.getSession().getAttribute("usuario") != null) {
		try {

			if (request.getSession().getAttribute("tipo") != null
					&& request.getSession().getAttribute("tipo") == "T") {
				UsuarioTransitivo ut = (UsuarioTransitivo) request.getSession().getAttribute("usuario");
				String usuario = ut.getCliente().getCliente_Id();
				out.println(usuario);
			} else {
				
				////UsuarioSistema us = (Usuario Sistema)request.getSession().getAttribute("usuario") ;
				UsuarioSistema us = (UsuarioSistema) request.getSession().getAttribute("usuario");
				String usuario = us.getUsuario();
				out.println(usuario);
			}
		} catch (Exception es) {
			es.printStackTrace();
		}
	}
%>