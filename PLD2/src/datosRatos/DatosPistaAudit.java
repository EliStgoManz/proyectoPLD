/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datosRatos;

import entidad.PistaAudit;
import utilidades.Rutas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author gibran.matias
 */
public class DatosPistaAudit {

	public ArrayList<PistaAudit> cambios(String idsales) {
		ArrayList<PistaAudit> respuesta = new ArrayList<>();
		Conexion2 cnn = new Conexion2();
		Connection conex = null;
		Statement instruccion;
		ResultSet conjuntoResultados;
		try {

			conex = cnn.getConnection("dbpld");

			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String consulta = "SELECT fecha,clienteid,campotexto, replace(valor_original,  '" + (char) 34 + (char) 34
					+ "', ' ') as valor_original, replace(valor_nuevo,  '" + (char) 34 + (char) 34
					+ "', ' ') as valor_nuevo,descripcion,afectado\n"
					+ "from pistaaudit inner join varperfilusuario on pistaaudit.perfilid=varperfilusuario.perfilid\n"
					+ "where afectado='" + idsales + "' order by fecha desc;";
			conjuntoResultados = instruccion.executeQuery(consulta);
			while (conjuntoResultados.next()) {
				String fecha = conjuntoResultados.getString("fecha");
				String clienteId = conjuntoResultados.getString("clienteid");
				String campoTexto = conjuntoResultados.getString("campotexto");
				String valorAnterior = conjuntoResultados.getString("valor_original");
				String valorNuevo = conjuntoResultados.getString("valor_nuevo");
				String perfil = conjuntoResultados.getString("descripcion");
				String usuarioAfectado = conjuntoResultados.getString("afectado");
				respuesta.add(new PistaAudit(fecha, clienteId, campoTexto, valorAnterior, valorNuevo, perfil,
						usuarioAfectado));
			} // fin del while
			conjuntoResultados.close();
			instruccion.close();
			conex.close();
		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, idsales, "DatosPistaAudit line 60 : ", es.toString());
			System.out.println("ERROR sql cambios "+DatosPistaAudit.class.getName()+".cambios()()  "+es);
		} // fin del catch
		finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosPistaAudit.class.getName()+".cambios()()  "+es);
			}
			return respuesta;
		} // fin del finally

	}

}
