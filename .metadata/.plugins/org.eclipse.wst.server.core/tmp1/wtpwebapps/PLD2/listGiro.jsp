<%@page import="datosRatos.Conexion2"%>
<%@page import="datosRatos.DatosGiro"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>

<%
	Conexion2 cnn = new Conexion2();
	ResultSet conjuntoResultados;

	Connection conex = null;
	Statement instruccion;

	try {

		conex = cnn.getConnection("dbpld");
		String where = (String) request.getParameter("q");

		// crea objeto Statement para consultar la base de datos
		instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		String consulta = "SELECT giro_id, descripcion FROM avgiromercantil ";
		String order = " order by descripcion ";

		if (where != "" || where.length() > 0) {
			consulta += " WHERE descripcion ILIKE('%" + where + "%')";
		}
		consulta += order;

		conjuntoResultados = instruccion.executeQuery(consulta);

		while (conjuntoResultados.next()) {

			String descripcion = conjuntoResultados.getString("descripcion");
			out.println(descripcion + "\n");

		} //fin del while

		conjuntoResultados.close();
		instruccion.close();
		conex.close();

	} catch (SQLException es) {
		out.println("error: " + es);
		es.printStackTrace();
	} //fin del catch
	finally {
		try {
			if (conex != null)
				conex.close();
		} catch (SQLException es) {
			es.printStackTrace();
		}
	} //fin del finally
%>