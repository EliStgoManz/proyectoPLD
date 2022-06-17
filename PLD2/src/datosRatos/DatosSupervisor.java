/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datosRatos;

import entidad.Perfil;
import entidad.Supervisor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author israel.garcia
 */
public class DatosSupervisor {

	boolean bandera;
	private Conexion2 cnn = new Conexion2();
	private ResultSet conjuntoResultados;

	public Supervisor[] getList() {
		ArrayList lista = new ArrayList();
		Connection conex = null;
		Statement instruccion;
		Supervisor[] listaSupervisores = null;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "SELECT usuario,  apellido_y_nombres FROM varusuarios where perfilid = 4 ";
			conjuntoResultados = instruccion.executeQuery(consulta);

			while (conjuntoResultados.next()) {
				String usuario = conjuntoResultados.getString("usuario");
				String nombre = conjuntoResultados.getString("apellido_y_nombres");
				Supervisor s = new Supervisor(usuario, nombre);
				lista.add(s);
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

			// Normalizando el listado
			if (lista.size() > 0) {
				listaSupervisores = new Supervisor[lista.size()];
				for (int i = 0; i < lista.size(); i++) {
					listaSupervisores[i] = (Supervisor) lista.get(i);
				} // for
			} // if

		} catch (SQLException es) {
			System.out.println("ERROR sql getList "+DatosSupervisor.class.getName()+".getList()   "+es);
		}catch (Exception e) {
			System.out.println("ERROR  getList "+DatosSupervisor.class.getName()+".getList()   "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión  "+DatosSupervisor.class.getName()+".getList()   "+es);
			}
		} // fin del finally
		return listaSupervisores;
	}// fin del metodo getList
}
