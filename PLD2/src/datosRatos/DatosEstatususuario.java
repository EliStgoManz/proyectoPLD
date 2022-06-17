/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datosRatos;

import entidad.EstatusUsuario;
import utilidades.Rutas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author israel.garcia
 */
public class DatosEstatususuario {

	// DeclaraciÃ³n de miembros de clase
	boolean bandera;
	private Conexion2 cnn = new Conexion2();
	private ResultSet conjuntoResultados;

	public EstatusUsuario[] getList() {
		ArrayList lista = new ArrayList();
		Connection conex = null;
		Statement instruccion;
		EstatusUsuario[] listaEstatus = null;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "SELECT estatususuariosid, descripcion FROM varestatususuario  order by descripcion";
			conjuntoResultados = instruccion.executeQuery(consulta);

			while (conjuntoResultados.next()) {
				int EstatusUsuarioId = conjuntoResultados.getInt("estatususuariosid");
				String descripcion = conjuntoResultados.getString("descripcion");

				EstatusUsuario e = new EstatusUsuario(EstatusUsuarioId, descripcion);
				lista.add(e);
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

			// Normalizando el listado
			if (lista.size() > 0) {
				listaEstatus = new EstatusUsuario[lista.size()];
				for (int i = 0; i < lista.size(); i++) {
					listaEstatus[i] = (EstatusUsuario) lista.get(i);
				} // for
			} // if

		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, "A-??????", "DatosEstatususuario line 76 : ", es.toString());
			   System.out.println("ERROR sql  getList() "+DatosEstatususuario.class.getName()+"  "+es);
		}catch (Exception e1) {
			 System.out.println("ERROR getEstatusUsuario getList() "+DatosEstatususuario.class.getName()+"  "+e1);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				 System.out.println("ERROR cerrar conexión  getList() "+DatosEstatususuario.class.getName()+"  "+es);
			}
		} // fin del finally
		return listaEstatus;
	}

}
