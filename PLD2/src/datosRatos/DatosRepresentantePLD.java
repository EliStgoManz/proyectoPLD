/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datosRatos;

import entidad.RepresentantePLD;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author israel.garcia
 */
public class DatosRepresentantePLD {
	// DeclaraciÃ³n de miembros de clase
	boolean bandera;
	private Conexion2 cnn = new Conexion2();
	private ResultSet conjuntoResultados;

	public RepresentantePLD[] getList(String where) {
		ArrayList lista = new ArrayList();
		Connection conex = null;
		Statement instruccion;
		RepresentantePLD[] listaperfiles = null;

		try {

			conex = cnn.getConnection("dbpld");

			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String consulta = "SELECT id_siseg, nombre, email FROM representantes_pld where estatususuariosid=1";
			if (!where.isEmpty() || where.length() > 0) {
				consulta += " " + where;
			}
			consulta += " order by nombre";
			conjuntoResultados = instruccion.executeQuery(consulta);
			while (conjuntoResultados.next()) {
				int id_siseg = conjuntoResultados.getInt("id_siseg");
				String nombre = conjuntoResultados.getString("nombre");
				RepresentantePLD p = new RepresentantePLD(id_siseg, nombre);
				String email = conjuntoResultados.getString("email");
				
				lista.add(p);
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

			// Normalizando el listado
			if (lista.size() > 0) {
				listaperfiles = new RepresentantePLD[lista.size()];
				for (int i = 0; i < lista.size(); i++) {
					listaperfiles[i] = (RepresentantePLD) lista.get(i);
				} // for
			} // if

		} catch (SQLException es) {
			System.out.println("ERROR sql getList "+DatosRepresentantePLD.class.getName()+".getList()   "+es);
		}catch (Exception e) {
			System.out.println("ERROR obtener representantes getList "+DatosRepresentantePLD.class.getName()+".getList()   "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión getList "+DatosRepresentantePLD.class.getName()+".getList()  "+es);
			}

		} // fin del finally

		return listaperfiles;
	}// fin del metodo getList

}// fin de la clase DatosPerfil
