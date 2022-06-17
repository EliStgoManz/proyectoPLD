/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datosRatos;

import entidad.EstatusClientes;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import utilidades.PerfilUsuario;

/**
 *
 * @author Israel Osiris Garcï¿½a
 */
public class XlsEstatusCliente {
	boolean bandera;
	private Conexion2 cnn = new Conexion2();
	private ResultSet conjuntoResultados;

	
	
	public EstatusClientes[] getList(String where, String usuario, String IdPerfil) {
		Connection conex = null;
		Statement instruccion;
		boolean hayDatos = false;
		ArrayList lista = new ArrayList<>();
		EstatusClientes[] clientes = null;
		String whereUsuario = "  c.usuarioAsignado IN ( SELECT numero_interno FROM varusuarios WHERE usuario = '"
				+ usuario + "' OR supervisor = '" + usuario + "' )";
		whereUsuario += " OR v.ejecutivo IN ( SELECT numero_interno FROM varusuarios where usuario = '" + usuario
				+ "' or supervisor = '" + usuario + "') ";

		// Se quita el discriminante si se trata de un administrador
		if (IdPerfil.trim().equals(PerfilUsuario.Administrador) || IdPerfil.trim().equals(PerfilUsuario.PLD)
				|| IdPerfil.trim().equals(PerfilUsuario.Admin_Telemarketing)
				|| IdPerfil.trim().equals(PerfilUsuario.SOPORTE)) {
			whereUsuario = "";
		}

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String consulta = " SELECT coalesce(c.cliente_id,'') as clienteId, "
					+ "coalesce(v.razonsocial,'') as razonSocial," + "coalesce(v.rfc,'') as rfc,"
					+ "coalesce(c.tipopersona,'') as tipoPersona," + "coalesce(c.estado,'') as estado,"
					+ "coalesce(d.usuario,'') as usuarioValido, " + "c.fechavalidado," + "c.fecharegistro,"
					+ "c.fechaBloqueo," + "coalesce(c.mensaje,'') as mensaje,"
					+ "coalesce(v.cliente_id,'') as idNumerico, " + "coalesce(c.notas,'') as notas,"
					+ "coalesce(e.apellido_y_nombres,'') as ejecutivo, " + "c.riesgo as riesgo"
					+ " FROM avcliente as c " + " inner join varusuariotransitorio as v on c.cliente_id = v.idCliente "
					+ " left join varusuarios d on c.usuariovalido = d.numero_interno "
					+ " left join varusuarios e on v.ejecutivo = e.numero_interno";
			if (where != null) {
				if (!where.isEmpty() && !whereUsuario.isEmpty()) { // Para
																	// supervisor
																	// y
																	// ejecutivo
					consulta += " WHERE " + where + " AND (" + whereUsuario + ")";
				} else if (!whereUsuario.isEmpty()) {
					consulta += " WHERE " + whereUsuario;
				} else if (!where.isEmpty() && whereUsuario.isEmpty()) {
					consulta += " WHERE " + where;
				}
			} else {
				if (!whereUsuario.isEmpty()) {
					consulta += " WHERE " + whereUsuario;
				}
			}

			consulta += " order by fecharegistro DESC ";
			System.out.println("consulta: " + consulta);
			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			while (conjuntoResultados.next()) {
				String clienteId = conjuntoResultados.getString("clienteId");
				String razonSocial = conjuntoResultados.getString("razonSocial");
				String rfc = conjuntoResultados.getString("rfc");
				String tipoPersona = conjuntoResultados.getString("tipoPersona");
				String estatus = conjuntoResultados.getString("estado");
				String usuariovalido = conjuntoResultados.getString("usuarioValido");
				String fechaValido = "";
				String fechaBloqueo = "";
				String fechaModificacion = "";
				String idClienteNumerico = conjuntoResultados.getString("idNumerico");
				try {
					fechaValido = conjuntoResultados.getString("fechavalidado");
				} catch (Exception es) {
					fechaValido = "";
					System.out.println("ERROR validaFecha getList() "+XlsEstatusCliente.class.getName()+"  "+es);
				}

				try {
					fechaBloqueo = conjuntoResultados.getString("fechaBloqueo");
				} catch (Exception es) {
					fechaBloqueo = "";
					System.out.println("ERROR validaFechaBloqueo getList() "+XlsEstatusCliente.class.getName()+"  "+es);
				}

				try {
					fechaModificacion = conjuntoResultados.getString("fecharegistro");
				} catch (Exception es) {
					fechaModificacion = "";
					System.out.println("ERROR validaFechaModificación getList() "+XlsEstatusCliente.class.getName()+"  "+es);
				}
				String mensaje = conjuntoResultados.getString("mensaje");
				String notas = conjuntoResultados.getString("notas");
				if (IdPerfil.trim().equals(PerfilUsuario.Ejecutivo_de_ventas)
						|| IdPerfil.trim().equals(PerfilUsuario.Supervisor)
						|| IdPerfil.trim().equals(PerfilUsuario.Admin_Telemarketing)) {
					notas = "";
				}
				String ejecutivo = conjuntoResultados.getString("ejecutivo");
				int riesgo = conjuntoResultados.getInt("riesgo");

				EstatusClientes ec = new EstatusClientes(clienteId, razonSocial, rfc, tipoPersona, estatus,
						usuariovalido, fechaValido, fechaBloqueo, fechaModificacion, mensaje, idClienteNumerico, notas,
						ejecutivo, riesgo);
				lista.add(ec);
				hayDatos = true;

			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

			// Normalizando el arreglo
			if (lista.size() > 0) { // si es que hay supervisores
				clientes = new EstatusClientes[lista.size()];
				for (int i = 0; i < lista.size(); i++) {
					clientes[i] = (EstatusClientes) lista.get(i);
				}
			} else {
				clientes = null;
			}

		} catch (SQLException es) {
			System.out.println("ERROR sql getList "+XlsEstatusCliente.class.getName()+".getList() "+es);
			bandera = false;
		} catch (Exception e) {
			System.out.println("ERROR getList "+XlsEstatusCliente.class.getName()+"  "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+XlsEstatusCliente.class.getName()+".getList()  "+es);
			}
		} // fin del finally
		return clientes;
	}// fin del mï¿½teodo getSupervisor

}
