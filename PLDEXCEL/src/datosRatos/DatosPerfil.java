/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datosRatos;

import entidad.Pais;
import entidad.Perfil;
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
public class DatosPerfil {
	// DeclaraciÃ³n de miembros de clase
	boolean bandera;
	private Conexion2 cnn = new Conexion2();
	private ResultSet conjuntoResultados;

	public Perfil[] getList(String where) {
		ArrayList lista = new ArrayList();
		Connection conex = null;
		Statement instruccion;
		Perfil[] listaperfiles = null;

		try {

			conex = cnn.getConnection("dbpld");

			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String consulta = "SELECT perfilid, descripcion, prospectos, captura, verificacion, avisos, usuarios, perfiles "
					+ "	FROM varperfilusuario ";

			if (!where.isEmpty() || where.length() > 0) {
				consulta += " " + where;
			}

			consulta += " order by perfilid";

			conjuntoResultados = instruccion.executeQuery(consulta);

			while (conjuntoResultados.next()) {

				int PerfilId = conjuntoResultados.getInt("perfilid");
				String descripcion = conjuntoResultados.getString("descripcion");
				String prospectos = conjuntoResultados.getString("prospectos");
				String captura = conjuntoResultados.getString("captura");
				String verificaion = conjuntoResultados.getString("verificacion");
				String avisos = conjuntoResultados.getString("avisos");
				String usuarios = conjuntoResultados.getString("usuarios");
				String perfiles = conjuntoResultados.getString("perfiles");
				Perfil p = new Perfil(PerfilId, descripcion, prospectos, captura, verificaion, avisos, usuarios,
						perfiles);
				lista.add(p);
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

			// Normalizando el listado
			if (lista.size() > 0) {
				listaperfiles = new Perfil[lista.size()];
				for (int i = 0; i < lista.size(); i++) {
					listaperfiles[i] = (Perfil) lista.get(i);
				} // for
			} // if

		} catch (SQLException es) {
			System.out.println("ERROR sql  getList "+DatosPerfil.class.getName()+".getList()  "+es);
		}catch (Exception e) {
			System.out.println("ERROR obtener datos perfil  getList "+DatosPerfil.class.getName()+".getList()  "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosPerfil.class.getName()+".getList()  "+es);
			}
		} // fin del finally
		return listaperfiles;
	}// fin del metodo getList

	
	
	
	
	
	public boolean actualizar(int idPerfil, String pProspectos, String pCaptura, String pVerificacion, String pAvisos,
			String pUsuarios, String pPerfiles) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+DatosPerfil.class.getName()+".actualizar()  "+e);
		}
		CallableStatement instruccion;
		boolean resultado = false;
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

		try {
			instruccion = conex.prepareCall("{call usp_change_Perfil(?,?,?,?,?,?,?)}");
			instruccion.setInt(1, idPerfil);
			instruccion.setString(2, pProspectos);
			instruccion.setString(3, pCaptura);
			instruccion.setString(4, pVerificacion);
			instruccion.setString(5, pAvisos);
			instruccion.setString(6, pUsuarios);
			instruccion.setString(7, pPerfiles);

			instruccion.execute();

			// Seteamos el exito de la transaccion
			resultado = true;

			// Limpieza del ambiente
			instruccion.close();
			conex.close();

		} catch (SQLException exSql) {
			System.out.println("ERROR sql  actualizar sql "+DatosPerfil.class.getName()+".actualizar()  "+exSql);
			// Seteamos el fracaso de la transaccion
			resultado = false;
		} catch (Exception es) {
			System.out.println("ERROR actualizarDatosPerfil "+DatosPerfil.class.getName()+".actualizar()  "+es);
			// Seteamos el fracaso de la transaccion
			resultado = false;
		} finally {
			try {
				if (conex != null) {
					conex.close();
				}
			}catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosPerfil.class.getName()+".actualizar()  "+es);
			} // fin del cath
			return resultado;
		} // fin del finally

	}
}// fin de la clase DatosPerfil
