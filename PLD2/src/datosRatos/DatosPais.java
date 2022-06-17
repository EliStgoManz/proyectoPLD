/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datosRatos;

import entidad.Pais;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author israel.garcia
 */
public class DatosPais {

	// DeclaraciÃ³n de miembros de clase
	boolean bandera;
	private Conexion2 cnn = new Conexion2();
	private ResultSet conjuntoResultados;

	public ArrayList getList() {
		ArrayList listaPaises = new ArrayList();
		Connection conex = null;
		Statement instruccion;

		try {

			conex = cnn.getConnection("dbpld");

			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String consulta = "SELECT pais, descripcion FROM public.avpaises ORDER BY descripcion";
			conjuntoResultados = instruccion.executeQuery(consulta);

			while (conjuntoResultados.next()) {

				String pais = conjuntoResultados.getString("pais");
				String descripcion = conjuntoResultados.getString("descripcion");
				if (pais != null) {
					pais = pais.trim();
				}

				listaPaises.add(new Pais(pais, descripcion));

			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

		} catch (SQLException es) {
			System.out.println("ERROR datos país  getList() "+DatosPais.class.getName()+"  "+es);
		}catch (Exception e) {
			System.out.println("ERROR obtener datos país  getList() "+DatosPais.class.getName()+"  "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión  getList() "+DatosPais.class.getName()+"  "+es);
			}
		} // fin del finally

		return listaPaises;
	}
}
