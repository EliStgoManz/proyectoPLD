
<%
	try {
		HttpSession sesion = request.getSession();
		String usuario = null;
		if (sesion == null) {
			response.sendRedirect("logout.jsp");
			usuario = sesion.getAttribute("usaurio").toString();
		} else {
			usuario = sesion.getAttribute("usuario").toString();

			if (usuario == null || usuario.isEmpty()) {
				response.sendRedirect("logout.jsp");
			}
		}
	} catch (Exception es) {
		response.sendRedirect("logout.jsp");
		es.printStackTrace();
	}
%>