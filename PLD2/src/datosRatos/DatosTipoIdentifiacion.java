/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datosRatos;

import entidad.TipoIdentificacion;
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
public class DatosTipoIdentifiacion {
	boolean bandera;
	private Conexion2 cnn = new Conexion2();
	private ResultSet conjuntoResultados;

	public ArrayList getList() {
		ArrayList listaTipoIdentificacion = new ArrayList();
		Connection conex = null;
		Statement instruccion;
		try {

			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String consulta = "SELECT identifica_id, descripcion, esotro FROM avtipoidentificacion order by descripcion";
			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			while (conjuntoResultados.next()) {

				String identifica_id = conjuntoResultados.getString("identifica_id").trim();
				String descripcion = conjuntoResultados.getString("descripcion").trim();
				int esOtro = conjuntoResultados.getInt("esotro");
				listaTipoIdentificacion.add(new TipoIdentificacion(identifica_id, descripcion, esOtro));
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, "A-??????", "DatosTipoIdentificacion line 60 : ", es.toString());
			System.out.println("ERROR sql getList() "+DatosTipoIdentifiacion.class.getName()+"  "+es);
			bandera = false;
		} // fin del catch
		catch (Exception e) {
			System.out.println("ERROR obtener identificación "+DatosTipoIdentifiacion.class.getName()+".getList()   "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosTipoIdentifiacion.class.getName()+".getList()  "+es);
			}

		} // fin del finally

		return listaTipoIdentificacion;
	}
}
