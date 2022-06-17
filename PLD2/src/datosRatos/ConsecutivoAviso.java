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
import java.text.ParseException;

/**
 *
 * @author Salvador Valenzuela
 */
public class ConsecutivoAviso {
	private Conexion2 cnn = new Conexion2();
	private ResultSet consecutivo;

	public int getConsecutivo() {
		Connection conex = null;
		Statement instruccion;
		int cons = 0;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement();
			String consulta_obten_consecutivo = "SELECT avisosconsecutivo FROM varconfiguracion";
			consecutivo = instruccion.executeQuery(consulta_obten_consecutivo);
			boolean val = consecutivo.next();
			if (val != false) {
				while (val) {
					cons = consecutivo.getInt("avisosconsecutivo");
					val = consecutivo.next();
				}
			}
		} catch (SQLException es) {
			System.out.println("ERROR consulta sql "+ConsecutivoAviso.class.getName()+ ".getConsecutivo() "+es);
		} catch (Exception e) {
			System.out.println("ERROR get consecultivo "+ConsecutivoAviso.class.getName()+ ".getConsecutivo() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+ConsecutivoAviso.class.getName()+".getConsecutivo() "+es);
			}
		} 
		return cons;
	}

	public void setConsecutivo(int valor) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e1) {
			System.out.println("ERROR crear conexión "+ConsecutivoAviso.class.getName()+ ".setConsecutivo() "+e1);
		}
		
		Statement instruccion;
		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "UPDATE varconfiguracion SET avisosconsecutivo = " + Integer.toString(valor) + "";
			instruccion.executeUpdate(consulta);
		} catch (SQLException es) {
			System.out.println("ERROR consulta "+ConsecutivoAviso.class.getName()+".setConsecutivo()  "+es);
		} catch (Exception e) {
			System.out.println("ERROR setConsecutivo "+ConsecutivoAviso.class.getName()+".setConsecutivo()  "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+ConsecutivoAviso.class.getName()+ ".setConsecutivo() "+es);
			}
		} // fin del finally
	}
}
