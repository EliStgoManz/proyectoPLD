/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datosRatos;

import entidad.UsuarioTransitivo;
import utilidades.Rutas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Israel Osiris GarcÃ­a
 */
public class DatosProspecto {
	boolean bandera;
	private Conexion2 cnn = new Conexion2();
	private ResultSet conjuntoResultados;

	public boolean agregarProspecto(String usuarioEjecutivo) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e1) {
			System.out.println("ERROR conexión  "+DatosProspecto.class.getName()+".agregarProspecto()  "+e1);
		}
		Statement instruccion;
		ResultSet conjuntoResultados;
		boolean resultado = false;
		String PERFIL_CLIENTE = "6";

		try {
			conex = cnn.getConnection("dbpld");

			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String usuario = UsuarioTransitivo.getCliente().getCliente_Id();
			String rfc = UsuarioTransitivo.getRfc();
			String nombre = UsuarioTransitivo.getRazonsocial();
			String correo = UsuarioTransitivo.getEmail();

			String consulta = "INSERT INTO  varUsuarioTransitorio (idCliente, rfc, razonsocial, email, clave_de_acceso, perfilid, ejecutivo) values ('";
			consulta += usuario + "','";
			consulta += rfc + "','";
			consulta += nombre + "','";
			consulta += correo + "','";
			consulta += usuario + "','";
			consulta += PERFIL_CLIENTE + "','";
			consulta += usuarioEjecutivo + "')";

			instruccion.executeUpdate(consulta);
			instruccion.close();
			resultado = true;
		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, "A-??????", "DatosProspecto line 67 : ", es.toString());
			System.out.println("ERROR sql agregarProspecto "+DatosProspecto.class.getName()+".agregarProspecto() "+es);
			resultado = false;
		}catch (Exception e) {
			System.out.println("ERROR  agregarProspecto "+DatosProspecto.class.getName()+".agregarProspecto()  "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosProspecto.class.getName()+".agregarProspecto()  "+es);
			}
		} // fin del finally

		return resultado;
	} // fin del metodo agregar prospecto

}
