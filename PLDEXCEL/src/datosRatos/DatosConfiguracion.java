/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datosRatos;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import utilidades.Rutas;

/**
 *
 * @author israel.garcia
 */
public class DatosConfiguracion {
	boolean bandera;
	private Conexion2 cnn = new Conexion2();
	private ResultSet conjuntoResultados;

	
	public boolean get() {
		Connection conex = null;
		Statement instruccion;
		
		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "   SELECT rutadocumentos, rutaplantillas,rutaDescargas, hostsmtp, senderaddress, puerto, contrasena, rutatemporales, rutasistema,"
					+ " wstokenrfccurp, wsrfc, wverificasionrfc " + "	FROM varconfiguracion ";
			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = false;

			while (conjuntoResultados.next()) {
				Rutas.setRutaCarga(conjuntoResultados.getString("rutadocumentos"));
				Rutas.setRutaPlantillas(conjuntoResultados.getString("rutaplantillas"));
				Rutas.setHostSmtp(conjuntoResultados.getString("hostsmtp"));
				Rutas.setSenderAddress(conjuntoResultados.getString("senderaddress"));
				Rutas.setPort(conjuntoResultados.getString("puerto"));
				Rutas.setPassword(conjuntoResultados.getString("contrasena"));
				Rutas.setRutaTemporales(conjuntoResultados.getString("rutatemporales"));
				Rutas.setRutaWebSistema(conjuntoResultados.getString("rutaSistema"));
				Rutas.setRutaDescarga(conjuntoResultados.getString("rutaDescargas"));
				Rutas.setWstokenrfccurp(conjuntoResultados.getString("wstokenrfccurp"));
				Rutas.setWsrfc(conjuntoResultados.getString("wsrfc"));
				Rutas.setWverificasionrfc(conjuntoResultados.getString("wverificasionrfc"));

				bandera = true;
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

		} catch (SQLException es) {
			System.out.println("ERROR get datos configuración SQL "+DatosConfiguracion.class.getName()+".get() "+es);
			bandera = false;
		}catch (Exception e) {
			System.out.println("ERROR get datos configuración "+DatosConfiguracion.class.getName()+".get() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosConfiguracion.class.getName()+".get() "+es);
			}
		} // fin del finally
		return bandera;
	}// fin del metodo existeCorreo
}
