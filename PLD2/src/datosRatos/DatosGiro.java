/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datosRatos;

import entidad.Giro;
import utilidades.Rutas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Israel Osiris Garcia
 */
public class DatosGiro {
	boolean bandera;
	private Conexion2 cnn = new Conexion2();
	private ResultSet conjuntoResultados;

	public ArrayList getList(String where) {
		ArrayList listaGiros = new ArrayList();
		Connection conex = null;
		Statement instruccion;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "SELECT giro_id, descripcion FROM avgiromercantil ";
			String order = " order by descripcion";

			if (where != "" || where.length() > 0) {
				consulta += "WHERE descripcion ILIKE('%" + where + "%')";
			}
			consulta += order;

			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			while (conjuntoResultados.next()) {

				String giro_id = conjuntoResultados.getString("giro_id");
				String descripcion = conjuntoResultados.getString("descripcion");
				listaGiros.add(new Giro(giro_id, descripcion));

			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, "A-??????", "DatosGiro line 70 : ", es.toString());
			System.out.println("ERROR sql  getList() "+DatosGiro.class.getName()+"  "+es);
			bandera = false;
		} // fin del catch
		catch (Exception e) {
			System.out.println("ERROR datos giro mercantil  getList() "+DatosGiro.class.getName()+"  "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				 System.out.println("ERROR cerrar conexión  getList() "+DatosGiro.class.getName()+"  "+es);
			}
		} // fin del finally

		return listaGiros;
	}
}
