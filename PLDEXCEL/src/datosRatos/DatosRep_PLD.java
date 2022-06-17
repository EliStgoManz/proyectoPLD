/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datosRatos;

import entidad.Cliente;
import entidad.EstatusUsuario;
import entidad.Perfil;
import entidad.PersonaMoral;
import entidad.Supervisor;
import entidad.UsuarioSistema;
import entidad.RepLegal_PLD;
import entidad.UsuarioTransitivo;
import listaEntidad.ClienteMoral;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;
import org.json.simple.parser.ParseException;

/**
 *
 * @author israel.garcia
 */
public class DatosRep_PLD {

	// cliente_id character(15) COLLATE pg_catalog."default",

	private String tipoUsuario = "";
	private final int ESTATUS_ACTIVO = 1;
	private final int ESTATUS_INACTIVO = 2;
	boolean bandera;
	private Conexion2 cnn = new Conexion2();
	private ResultSet conjuntoResultados;

	public DatosRep_PLD() {

	}

	public boolean changeUsuarioSistema(RepLegal_PLD u) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+DatosRep_PLD.class.getName()+".DatosRep_PLD() "+e);
		}
		CallableStatement instruccion;
		boolean resultado = false;

		try {
			instruccion = conex.prepareCall("{call usp_change_representantes_pld(?,?,?,?)}");
			instruccion.setInt(1, u.getId_siseg());
			instruccion.setString(2, u.getNombre());
			if (u.getEstatus().getEstatusUsuarioId() != null) {
				instruccion.setInt(3, u.getEstatus().getEstatusUsuarioId());
			} else {
				instruccion.setInt(3, ESTATUS_ACTIVO);
			}
			instruccion.setString(4, u.getEmail());
			System.out.println("entro a usp " + instruccion.toString());

			instruccion.execute();
			// Seteamos el exito de la transaccion
			resultado = true;
			// Limpieza del ambiente
			instruccion.close();
			conex.close();
		} catch (SQLException exSql) {
			System.out.println("ERROR cambiarUsuario SQL  "+DatosRep_PLD.class.getName()+".usp_change_representantes_pld() "+exSql);
			// Seteamos el fracaso de la transaccion
			resultado = false;
		} catch (Exception es) {
			System.out.println("ERROR cambiarUsuario "+DatosRep_PLD.class.getName()+".usp_change_representantes_pld() "+es);
			// Seteamos el fracaso de la transaccion
			resultado = false;
		} finally {
			try {
				if (conex != null) {
					conex.close();
				}
			}catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosRep_PLD.class.getName()+".usp_change_representantes_pld() "+es);
			} // fin del cath
			return resultado;
		} // fin del finally
	}// Fin del mï¿½todo insertar

	/**
	 * Retorna una lista de todos los usuarios del sistema, esta pantalla
	 * pertenece a usuarios.jsp
	 * 
	 * @return
	 */
	public RepLegal_PLD[] getList(String where) {
		Connection conex = null;
		Statement instruccion;
		boolean hayDatos = false;
		ArrayList<RepLegal_PLD> lista = new ArrayList<>();
		RepLegal_PLD[] legal = null;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "	select u.id_siseg, u.nombre, e.descripcion as Estatus, u.estatususuariosid, u.email "
					+ " from representantes_pld u "
					+ " JOIN varestatususuario as e on u.estatususuariosid = e.estatususuariosid ";

			if (where != null) {
				if (!where.isEmpty()) {
					consulta += " WHERE u.estatususuariosid='1' and " + where;
				} else {
					consulta += " WHERE u.estatususuariosid='1' ";
				}
			} else {
				consulta += " WHERE u.estatususuariosid='1' ";
			}

			consulta += " ORDER BY u.id_siseg, u.nombre";
			System.out.println("consulta usuarios: " + consulta);
			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			while (conjuntoResultados.next()) {

				int id_siseg = conjuntoResultados.getInt("id_siseg");
				String nombre = conjuntoResultados.getString("nombre");
				String estatusUsuario = conjuntoResultados.getString("estatus");
				String email = conjuntoResultados.getString("email");

				int estatusRepreId = conjuntoResultados.getInt("estatususuariosid");

				EstatusUsuario e = new EstatusUsuario(estatusRepreId, estatusUsuario);

				RepLegal_PLD us = new RepLegal_PLD();
				us.setId_siseg(id_siseg);
				us.setNombre(nombre);
				us.setEstatus(e);
				us.setEmail(email);

				lista.add(us);
				hayDatos = true;

			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

			// Normalizando el arreglo
			if (lista.size() > 0) { // si es que hay supervisores
				legal = new RepLegal_PLD[lista.size()];
				for (int i = 0; i < lista.size(); i++) {
					legal[i] = (RepLegal_PLD) lista.get(i);
				}
			} else {
				legal = null;
			}
		} catch (SQLException es) {
			System.out.println("ERROR getUsuarios sistema SQL "+DatosRep_PLD.class.getName()+".getList() "+es);
			bandera = false;
		}catch (Exception e1) {
			System.out.println("ERROR getUsuarios sistema "+DatosRep_PLD.class.getName()+".getList() "+e1);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosRep_PLD.class.getName()+".getList() "+es);
			}
		} // fin del finally

		return legal;
	}// fin del mï¿½teodo getSupervisor

	
	public boolean eliminar(String usuario) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+DatosRep_PLD.class.getName()+".eliminar() "+e);
		}
		Statement instruccion;
		boolean resultado = false;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = " update representantes_pld set estatususuariosid = '2'";
			consulta += " where  id_siseg = '" + usuario + "'";

			instruccion.executeUpdate(consulta);
			resultado = true;
		} catch (SQLException es) {
			System.out.println("ERROR eliminar usuario SQL "+DatosRep_PLD.class.getName()+".eliminar() "+es);
			resultado = false;
		}catch (Exception e) {
			System.out.println("ERROR eliminar usuario sistema "+DatosRep_PLD.class.getName()+".eliminar() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosRep_PLD.class.getName()+".eliminar() "+es);
			}
		} // fin del finally

		return resultado;
	} // fin del mÃ©todo cambiaContrasena

	public boolean eliminar(int id_siseg) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+DatosRep_PLD.class.getName()+".eliminar() "+e);
		}
		Statement instruccion;

		boolean resultado = false;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String consulta = " update representantes_pld set activo = false";
			consulta += " where  id_siseg = '" + id_siseg + "'";

			instruccion.executeUpdate(consulta);
			resultado = true;
		} catch (SQLException es) {
			System.out.println("ERROR eliminar usuario sistema SQL "+DatosRep_PLD.class.getName()+".eliminar() "+es);
			resultado = false;
		}catch (Exception e) {
			System.out.println("ERROR eliminar usuario sistema "+DatosRep_PLD.class.getName()+".eliminar() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosRep_PLD.class.getName()+".eliminar() "+es);
			}
		} // fin del finally
		return resultado;
	} // fin del mÃ©todo cambiaContrasena

	/**
	 * Obtiene el id de usuario del administrador para ponerlo como default
	 * asignado cuando se guarda por primera vez un cliente
	 * 
	 * @return id del usuario administrador
	 */

}// fin clase
