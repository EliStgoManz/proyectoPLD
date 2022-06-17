/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datosRatos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Israel Osiris Garc√≠a
 */
public class DatosUtilidades {

	/**
	 * Definicion de miembros de clase
	 */
	private Conexion2 cnn = new Conexion2();

	public boolean ejecutaInstruccionUpdateSQL(String sql) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexiÛn  "+DatosUtilidades.class.getName()+".ejecutaInstruccionUpdateSQL()  "+e);
		}
		Statement instruccion;
		boolean resultado = false;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String consulta = sql;
			// System.out.println("query insert: " + consulta);
			instruccion.executeUpdate(consulta);
			instruccion.close();
			resultado = true;
		} catch (SQLException es) {
			System.out.println("ERROR sql ejecutaInstruccionUpdateSQL "+DatosUtilidades.class.getName()+".ejecutaInstruccionUpdateSQL()  "+es);
			resultado = false;
		} catch (Exception e) {
			System.out.println("ERROR  ejecutaInstruccionUpdateSQL "+DatosUtilidades.class.getName()+".ejecutaInstruccionUpdateSQL()  "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException e) {
				System.out.println("ERROR cerrar conexiÛn ejecutaInstruccionUpdateSQL "+DatosUtilidades.class.getName()+".ejecutaInstruccionUpdateSQL()  "+e);
			}
		} // fin del finally

		return resultado;
	} // fin del m√©todo ejecutaInstrucci√≥nSQL

}
