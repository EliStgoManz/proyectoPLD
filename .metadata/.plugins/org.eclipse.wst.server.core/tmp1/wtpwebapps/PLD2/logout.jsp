<%-- 
    Document   : logout
    Created on : 30/05/2018, 01:22:07 PM
    Author     : israel.garcia
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>JSP Page</title>
</head>
<body>
	<%
		HttpSession sesion;
		try {

			sesion = request.getSession();
			if (sesion != null) {
				if (sesion.getAttribute("usuario") != null) {
					sesion.invalidate();
					response.sendRedirect("login.jsp");
				} else {
					sesion.invalidate();
					sesion = null;
					response.sendRedirect("login.jsp");
				}
			}
		} catch (Exception es) {

			response.sendRedirect("login.jsp");
		}
	%>
	<script>
		
	</script>
</body>
</html>
