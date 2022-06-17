/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datosRatos;

import entidad.Cliente;
import listaEntidad.Consumo;
import utilidades.Rutas;
import entidad.Pais;
import entidad.UsuarioTransitivo;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import listaEntidad.*;

/**
 *
 * @author Israel Osiris Garcia
 */
public class DatosClienteRaro {
	String wslistaclienteid = "";
	private Conexion2 cnn = new Conexion2();
	private ResultSet conjuntoResultados;
	private ResultSet conjuntoResultados2;
	private ResultSet conjuntoResultados3;
	private OperacionesCoincidencias opco = new OperacionesCoincidencias();
	boolean bandera;

	public boolean actualizarCoincidencias(String clienteid, ArrayList<Coincidencia> coincidencia) {
		try {
			for (int i = 0; i < coincidencia.size(); i++) {
				opco.ingresarDescripcionCoincidencia(clienteid, coincidencia.get(i).getDescripcion(),
						coincidencia.get(i).getMatchid());
			}
		} catch (Exception e) {
			if (e.getMessage().equals("La consulta no retornó ningún resultado."))
				System.out.println("descripcion actualizada correctamente");
			else {
				System.out.println("ERROR  editar descripcion coincidencia "+DatosClienteRaro.class.getName()+".actualizarCoincidencias() "+e );				
				return false;
			}
		}
		return true;
	}

	public boolean obtenerCoincidenciasListas(Cliente c) {
		System.out.println("entrando a ver las coincidencias");
		listaCliente cliente = null;
		System.out.println("mira el estado " + c.getEstado());

		try {
			consumolista ls = new consumolista();
			// consumopeticion cp=new consumopeticion();
			String idcliente = c.getCliente_Id();
			String tipoPersona = c.getTipoPersona();
			String razonsocial = c.getRazonSocial();
			if (tipoPersona.equals("F")) {
				cliente = new ClienteFisico(idcliente, tipoPersona, razonsocial);
				cliente = (ClienteFisico) ls.llenarDatosCliente(cliente);
				System.out.println(cliente);
			} else if (tipoPersona.equals("M")) {
				cliente = new ClienteMoral(idcliente, tipoPersona, razonsocial);
				cliente = (ClienteMoral) ls.llenarDatosCliente(cliente);
			} else if (tipoPersona.equals("X") || tipoPersona.equals("G")) {
				cliente = new ClienteGobFid(idcliente, tipoPersona, razonsocial);
				cliente = (ClienteGobFid) ls.llenarDatosCliente(cliente);
			}
			OperacionesDemonio o = new OperacionesDemonio();
			return o.Revision(cliente);
			// return true;
		} catch (Exception e) {
			System.out.println("ERROR  buscar coincidencias "+DatosClienteRaro.class.getName()+".obtenerCoincidenciasListas() "+e );				
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, c.getCliente_Id(), "DatosClienteRaro line 88 : ", e.toString());
		}
		return false;
	}

	
	
	public String getClienteVar(String rfc) {
		Connection conex = null;
		Statement instruccion;
		String clienteid = "";
		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "select cliente_id from varusuariotransitorio where rfc = '" + rfc + "'";
			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;
			while (conjuntoResultados.next()) {
				clienteid = conjuntoResultados.getString("cliente_id");
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();
		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, clienteid.toString(), "DatosClienteRaro line 107 : ", es.toString());
			System.out.println("ERROR  buscar datos clienteRaro SQL"+DatosClienteRaro.class.getName()+".getClienteVar() "+es );
			bandera = false;
		}catch (Exception e) {	
			System.out.println("ERROR  buscar datos clienteRaro "+DatosClienteRaro.class.getName()+".getClienteVar() "+e );
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosClienteRaro.class.getName()+".getClienteVar() "+es );
			}
		} // fin del finally
		return clienteid;
	}// fin metodo

	
	public String obtenerCliente_idService() {
		Connection conex = null;
		Conexion2 c = new Conexion2();
		Statement instruccion;
		ResultSet conjuntoResultados = null;

		try {
			conex = c.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+DatosClienteRaro.class.getName()+".obtenerCliente_idService() "+e );			
		}
		
		try {
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "SELECT * from varconfiguracion where configuracion_id=1;";
			conjuntoResultados = instruccion.executeQuery(consulta);
			if (conjuntoResultados.next()) {
				wslistaclienteid = conjuntoResultados.getString("wslistaclienteid");
		    }
			conjuntoResultados.close();
		} catch (SQLException e) {
			System.out.println("ERROR obtener cliente SQL "+DatosClienteRaro.class.getName()+".obtenerCliente_idService() "+e );
		}finally {
			try {
				conex.close();
			} catch (SQLException e) {
				System.out.println("ERROR cerrar conexión "+DatosClienteRaro.class.getName()+".obtenerCliente_idService() "+e );
			}
		}	
		return wslistaclienteid;
	}

	
	public boolean fueRevisado(String clienteid) throws SQLException {
		Conexion2 cnn = new Conexion2();
		Connection conex = null;
		Statement instruccion = null;
		ResultSet conjuntoResultados;
		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "select count(*) from pistaaudit where campotexto='RevisionListas' and afectado='"
					+ clienteid + "'";
			conjuntoResultados = instruccion.executeQuery(consulta);
			if (conjuntoResultados.next()) {
				if (conjuntoResultados.getInt("count") > 0) {
					System.out.println("si hubo revision");
					return true;
				}
				conjuntoResultados.close();
				System.out.println("no hubo revision");

				return false;
			}
			return false;
		} catch (Exception e) {
			System.out.println("ERROR contar registro pistaAudit "+DatosClienteRaro.class.getName()+".fueRevisado() "+e );
			return false;
		}finally {
			instruccion.close();
			conex.close();
		}
	}

	public boolean insertar(Cliente c, String perfilid, String usuarioEdicion) {
		//System.out.println("entro a insertar de datosclientes raro");
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+DatosClienteRaro.class.getName()+".insertar() "+e );
		}
		CallableStatement instruccion;
		boolean resultado = false;

		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		System.out.println("utilDate:" + utilDate);
		System.out.println("sqlDate:" + sqlDate);
		java.sql.Date sqlFechaRegistro = null;
		java.sql.Date sqlFechaRiesgo = null;
		java.sql.Date sqlFechaValidado = null;
		java.sql.Date sqlFechaCorte = null;
		java.sql.Date sqlFechaBloqueo = null;
		SimpleDateFormat formato;
		formato = new SimpleDateFormat("yyyy-MM-dd");

		// Adaptando la fecha de registro
		String fechaRegistro = c.getFechaRegistro();
		try {
			java.util.Date utilFechaRegistro = formato.parse(fechaRegistro);
			sqlFechaRegistro = new java.sql.Date(utilFechaRegistro.getTime());
		} catch (Exception es) {
			sqlFechaRegistro = sqlDate;
		}

		// Adaptabndo la fecha de validado
		String fechaValidado = c.getFechaValidado();
		try {
			java.util.Date utilFechaValiado = formato.parse(fechaValidado);
			sqlFechaValidado = new java.sql.Date(utilFechaValiado.getTime());
		} catch (Exception es) {
			sqlFechaValidado = null;
		}

		// Adaptabndo la fecha de riesgo
		String fechaRiesgo = c.getFechaRiesgo();
		try {
			java.util.Date utilFechaRiesgo = formato.parse(fechaRiesgo);
			sqlFechaRiesgo = new java.sql.Date(utilFechaRiesgo.getTime());
		} catch (Exception es) {
			sqlFechaCorte = null;
		}

		// Adaptabndo la fecha de corte
		String fechaCorte = c.getFechaCorte();
		try {
			java.util.Date utilFechaCorte = formato.parse(fechaCorte);
			sqlFechaCorte = new java.sql.Date(utilFechaCorte.getTime());
		} catch (Exception es) {
			sqlFechaCorte = null;
		}

		// Adaptabndo la fecha de bloqueo
		String fechaBloqueo = c.getFechaBloqueo();
		try {
			java.util.Date utilFechaBloqueo = formato.parse(fechaBloqueo);
			sqlFechaBloqueo = new java.sql.Date(utilFechaBloqueo.getTime());
		} catch (Exception es) {
			sqlFechaBloqueo = null;
		}
		
		try {
			instruccion = conex
					.prepareCall("{call usp_change_cliente(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			instruccion.setString(1, c.getCliente_Id());
			instruccion.setString(2, c.getTipoPersona());
			instruccion.setNull(3, java.sql.Types.DATE);
			instruccion.setString(4, c.getTipoDomicilio());
			instruccion.setString(5, c.getTelefono());
			instruccion.setString(6, c.getPais().getPais());
			instruccion.setString(7, c.geteMail());

			if (c.getEstado().equals("A") /* !fueRevisado(c.getCliente_Id()) */) {
				// System.out.println("el cliente no ha sido revisado en las
				// listas negras por lo tanto no peude validarse, aún,realizando
				// revisión ahora");
				if (existeApp() == false) {
					if (obtenerCoincidenciasListas(c)) {
						c.setEstado("V");
					} else {
						c.setEstado("A");
					}
				}

			}

			instruccion.setString(8, c.getEstado());
			System.out.println("Estado : " + c.getEstado());
			instruccion.setString(9, c.getRazonSocial());
			instruccion.setInt(10, c.getValidado());
			// if(fechaValidado != null && !fechaValidado.isEmpty()) {
			// instruccion.setDate(11, sqlFechaValidado);
			// }else if ( c.getEstado()=="A"){
			// instruccion.setDate(11, sqlFechaValidado);
			// }else{
			// instruccion.setNull(11, java.sql.Types.VARCHAR);
			// }

			if (fechaValidado != null && !fechaValidado.isEmpty()) {
				instruccion.setDate(11, sqlFechaValidado);
			} else {
				instruccion.setNull(11, java.sql.Types.DATE);
			}

			instruccion.setInt(12, c.getDeclaroBeneficiario());
			instruccion.setInt(13, c.getDeclaroOrigen());
			if (c.getUsuarioValido() != null && !c.getUsuarioValido().isEmpty()) {
				instruccion.setString(14, c.getUsuarioValido());
			} else {
				instruccion.setNull(14, java.sql.Types.VARCHAR);
			}
			if (fechaCorte != null && !fechaCorte.isEmpty()) {
				instruccion.setDate(15, sqlFechaCorte);
			} else {
				instruccion.setNull(15, java.sql.Types.DATE);
			}
			if (c.getMensaje() != null && !c.getMensaje().isEmpty()) {
				instruccion.setString(16, c.getMensaje());
			} else {
				c.setMensaje("");
				instruccion.setString(16, c.getMensaje());
			}

			instruccion.setString(17, c.getUsuarioAsignado());

			instruccion.setBoolean(18, c.isBloqueado());

			if (fechaBloqueo != null && !fechaBloqueo.isEmpty()) {
				instruccion.setDate(19, sqlFechaBloqueo);
			} else {
				instruccion.setNull(19, java.sql.Types.DATE);
			}
			instruccion.setBoolean(20, c.isBorrado());
			instruccion.setString(21, c.getNotas());

			System.out.println("Riesgo  : " + c.getRiesgo());

			if (c.getRiesgo() == null || c.getRiesgo().equals("off")) {
				instruccion.setString(22, "0");
			} else if (c.getRiesgo().equals("on")) {
				instruccion.setString(22, "1");
			}
			instruccion.setString(23, c.getDescripcion());
			instruccion.setInt(24, Integer.parseInt(perfilid));
			instruccion.setString(25, usuarioEdicion);
			instruccion.execute();

			Statement instruccion2 = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			String consulta = "UPDATE varusuariotransitorio SET email = '" + c.geteMail() + "', razonsocial='"
					+ c.getRazonSocial() + "' WHERE idcliente ='" + c.getCliente_Id() + "'";
			instruccion2.executeUpdate(consulta);
			resultado = true;

			instruccion2.close();
			instruccion.close();
			conex.close();

		} catch (SQLException exSql) {
			System.out.println("ERROR insertar datos clienteRaro SQL "+DatosClienteRaro.class.getName()+".insertar() "+exSql );
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, c.getCliente_Id(), "DatosClienteRaro line 305 : ", exSql.toString());
			// Seteamos el fracaso de la transaccion
			resultado = false;
		} catch (Exception es) {
			System.out.println("ERROR insertar datos clienteRaro "+DatosClienteRaro.class.getName()+".insertar() "+es );
			// Seteamos el fracaso de la transaccion
			resultado = false;
		} finally {
			try {
				if (conex != null) {
					conex.close();
				}
			}catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosClienteRaro.class.getName()+".insertar() "+es );
			} // fin del cath
			return resultado;
		} // fin del finally
	}// Fin del metodo insertar

	
	private boolean existeApp() throws SQLException {
		Connection conex = null;
		Conexion2 c = new Conexion2();
		Statement instruccion=null;
		ResultSet conjuntoResultados = null;
		
		try {
			conex = c.getConnection("dbpld");
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "Select*from varconfiguracion where wslistaobtenertoken='0' ;";
			conjuntoResultados = instruccion.executeQuery(consulta);

			if (conjuntoResultados.next()) {
				System.out.println("No Appi");
				conjuntoResultados.close();
				return true;
			} else {
				System.out.println("Appi jalando");
				conjuntoResultados.close();
				return false;
			}
		} catch (Exception e) {
			System.out.println("ERROR No funcionó Appi "+DatosClienteRaro.class.getName()+".existeApp() "+e );
			return false;
		}finally {
			instruccion.close();
			conex.close();
		}

	}

	public boolean agregarCliente(Cliente c) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+DatosClienteRaro.class.getName()+".agregarCliente() "+e );
		}
		
		Statement instruccion;
		ResultSet conjuntoResultados;
		boolean resultado = false;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// String consulta = "INSERT INTO varUsuarioTransitorio (idCliente,
			// rfc, razonsocial, email, clave_de_acceso) values ('";

			String consulta = "INSERT INTO avcliente(";
			consulta += "cliente_id, tipopersona, fecharegistro, tipodomicilio, telefono, pais, email, estado, razonsocial, validado, fechavalidado, declarobeneficiario, declaroorigen,  fechacorte,  usuarioasignado)";
			consulta += "	VALUES (";
			consulta += "'" + UsuarioTransitivo.getCliente().getCliente_Id().trim() + "',";
			consulta += "'" + c.getTipoPersona() + "',";
			consulta += "" + "now()" + ",";
			consulta += "'" + c.getTipoDomicilio() + "',";
			consulta += "'" + c.getTelefono() + "',";
			consulta += "'" + c.getPais().getPais() + "',";
			consulta += "'" + c.geteMail() + "',";
			consulta += "'" + c.getEstado() + "',";
			consulta += "'" + c.getRazonSocial() + "',";
			consulta += "'" + c.getValidado() + "',";
			consulta += "" + "null" + ",";
			consulta += "'" + c.getDeclaroBeneficiario() + "',";
			consulta += "'" + c.getDeclaroOrigen() + "',";
			// consulta += "'" + c.getUsuarioValido() + "',";
			consulta += "" + "null" + ",";
			// consulta += "'" + c.getMensaje() + "',";
			consulta += "'" + c.getUsuarioAsignado() + "')";
			// consulta += "'" + c.getNotas() + "',";
			// consulta += "'" + "0" + "'";

			instruccion.executeUpdate(consulta);
			
			instruccion.close();
			resultado = true;
		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, c.getCliente_Id(), "DatosClienteRaro line 384 : ", es.toString());
			System.out.println("ERROR insertar avcliente SQL "+DatosClienteRaro.class.getName()+".agregarCliente() "+es );
			resultado = false;
		}catch (Exception e) {
			System.out.println("ERROR insertar avcliente "+DatosClienteRaro.class.getName()+".agregarCliente() "+e );
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosClienteRaro.class.getName()+".agregarCliente() "+es);
			}
		} // fin del finally
		return resultado;
	} // fin del metodo agregar cliente
	

	public Cliente get(String where) {
		Connection conex = null;
		Statement instruccion;
		Statement instruccion2;
		Statement instruccion3;
		boolean hayDatos = false;
		Cliente c = null;
		c = new Cliente();

		String cliente_id = "";

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "	SELECT cliente_id, tipopersona, fecharegistro, tipodomicilio, telefono, pais, email, estado, razonsocial, validado, fechavalidado, declarobeneficiario, declaroorigen, usuariovalido, fechacorte, usuarioasignado, bloqueado, fechabloqueo, borrado, riesgo"
					+ "	FROM avcliente ";

			if (where != null) {
				if (!where.isEmpty()) {
					consulta += " WHERE " + where;
				}
			}

			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			while (conjuntoResultados.next()) {
				cliente_id = conjuntoResultados.getString("cliente_id");
				String tipopersona = conjuntoResultados.getString("tipopersona");
				String fecharegistro = conjuntoResultados.getString("fecharegistro");
				String tipodomicilio = conjuntoResultados.getString("tipodomicilio");
				String telefono = conjuntoResultados.getString("telefono");
				String pais = conjuntoResultados.getString("pais");
				String email = conjuntoResultados.getString("email");
				String estado = conjuntoResultados.getString("estado");
				String razonsocial = conjuntoResultados.getString("razonsocial");
				Integer validado = conjuntoResultados.getInt("validado");
				String fechavalidado = conjuntoResultados.getString("fechavalidado");
				Integer declarobeneficiario = conjuntoResultados.getInt("declarobeneficiario");
				Integer declaroorigen = conjuntoResultados.getInt("declaroorigen");
				String usuariovalido = conjuntoResultados.getString("usuariovalido");
				String fechacorte = conjuntoResultados.getString("fechacorte");
				String usuarioasignado = conjuntoResultados.getString("usuarioasignado");
				Boolean bloqueado = conjuntoResultados.getBoolean("bloqueado");
				String fechabloqueo = conjuntoResultados.getString("fechabloqueo");
				Boolean borrado = conjuntoResultados.getBoolean("borrado");
				String riesgo = conjuntoResultados.getInt("riesgo") + "";
				// String
				// fechariesgo=conjuntoResultados.getString("fechaanalisis");

				c.setCliente_Id(cliente_id);
				Pais p = new Pais();
				p.setPais(pais);
				c.setTipoPersona(tipopersona);
				c.setFechaRegistro(fecharegistro);
				c.setTipoDomicilio(tipodomicilio);
				c.setTelefono(telefono);
				c.setPais(p);
				c.seteMail(email);
				c.setEstado(estado);
				c.setRazonSocial(razonsocial);
				c.setValidado(validado);
				c.setFechaValidado(fechavalidado);
				c.setDeclaroBeneficiario(declarobeneficiario);
				c.setDeclaroOrigen(declaroorigen);
				c.setUsuarioValido(usuariovalido);
				c.setFechaCorte(fechacorte);
				c.setUsuarioAsignado(usuarioasignado);
				c.setBloqueado(bloqueado);
				c.setFechaBloqueo(fechabloqueo);
				c.setBorrado(borrado);
				c.setRiesgo(riesgo);
				// c.setFechaRiesgo(fechariesgo);
				hayDatos = true;

			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();
		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, cliente_id, "DatosClienteRaro line 561 : ", es.toString());
			System.out.println("ERROR buscar clienteRaro SQL "+DatosClienteRaro.class.getName()+".get() "+es);
			bandera = false;
		} catch (Exception e) {
			System.out.println("ERROR buscar clienteRaro "+DatosClienteRaro.class.getName()+".get() "+e);
		}

		try {
			conex = cnn.getConnection("dbpld");
			instruccion2 = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta2 = "SELECT REPLACE(REPLACE(REPLACE(mensaje,CHR(10),' ') , CHR(13),' ') ,'','') AS mensaje FROM avcliente where cliente_id='"
					+ cliente_id + "';";
			conjuntoResultados2 = instruccion2.executeQuery(consulta2);
			while (conjuntoResultados2.next()) {
				String mensaje = conjuntoResultados2.getString("mensaje");
				c.setMensaje(mensaje);
			}

			conjuntoResultados2.close();
			instruccion2.close();
			conex.close();
		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, cliente_id, "DatosClienteRaro line 589 : ", es.toString());
			System.out.println("ERROR  clienteRaro SQL "+DatosClienteRaro.class.getName()+".get() "+es);
			bandera = false;
		} catch (Exception e) {
			System.out.println("ERROR clienteRaro "+DatosClienteRaro.class.getName()+".get() "+e);
		}

		try {
			conex = cnn.getConnection("dbpld");
			instruccion3 = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta3 = "SELECT REPLACE(REPLACE(REPLACE(notas,CHR(10),' ') , CHR(13),' ') ,'','') AS notas FROM avcliente where cliente_id='"
					+ cliente_id + "';";
			conjuntoResultados3 = instruccion3.executeQuery(consulta3);
			while (conjuntoResultados3.next()) {
				String notas = conjuntoResultados3.getString("notas");

				c.setNotas(notas);
			}
			conjuntoResultados3.close();
			instruccion3.close();
			conex.close();
		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, cliente_id, "DatosClienteRaro line 616 : ", es.toString());
			System.out.println("ERROR buscar clienteRaro "+DatosClienteRaro.class.getName()+".get() "+es);
			bandera = false;
		} // fin del catch
		catch (Exception e) {
			System.out.println("ERROR buscar clienteRaro "+DatosClienteRaro.class.getName()+".get() "+e);
		}finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosClienteRaro.class.getName()+".get() "+es);
			}
		} // fin del finally
		return c;
	} // fin del metodo get
	
	

	public Cliente existeClienteRFC(String rfc) {
		Connection conex = null;
		Statement instruccion;
		Cliente c = null;
		
		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "SELECT cl.cliente_id as idCliente, cl.tipopersona as tp " + " FROM avcliente as cl "
					+ " join varusuariotransitorio as ut on cl.cliente_id = ut.idcliente " + " where ut.rfc = '" + rfc
					+ "'";
			conjuntoResultados = instruccion.executeQuery(consulta);

			while (conjuntoResultados.next()) {
				String tipoPersona = conjuntoResultados.getString("tp");
				String idCliente = conjuntoResultados.getString("idCliente");

				c = new Cliente();
				c.setCliente_Id(idCliente);
				c.setTipoPersona(tipoPersona);

			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, c.getCliente_Id(), "DatosClienteRaro line 624 : ", es.toString());
			System.out.println("ERROR buscar RFC SQL "+DatosClienteRaro.class.getName()+".existeClienteRFC() "+es);
			bandera = false;
		}catch (Exception e) {
			System.out.println("ERROR buscar RFC "+DatosClienteRaro.class.getName()+".existeClienteRFC() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosClienteRaro.class.getName()+".existeClienteRFC() "+es);
			}
		} // fin del finally
		return c;
	}// fin del mÃ©todo getrfc

	
	
	public boolean getValidarCliente(String cliente_id) {
		Connection conex = null;
		Statement instruccion;
		boolean validado = false;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "SELECT sp_valida_cliente ('" + cliente_id + "')";

			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			while (conjuntoResultados.next()) {
				validado = conjuntoResultados.getBoolean("sp_valida_cliente");
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, cliente_id, "DatosClienteRaro line 683 : ", es.toString());
			System.out.println("ERROR validarCliente SQL "+DatosClienteRaro.class.getName()+".getValidarCliente() "+es);
			bandera = false;
		}catch (Exception e) {
			System.out.println("ERROR validarCliente"+DatosClienteRaro.class.getName()+".getValidarCliente() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosClienteRaro.class.getName()+".getValidarCliente() "+es);
			}
		} // fin del finally
		return validado;
	}// fin metodo

	
	public boolean getValidarPersonaFisica(String cliente_id) {
		Connection conex = null;
		Statement instruccion;
		boolean validado = false;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "SELECT sp_valida_personafisica ('" + cliente_id + "')";

			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			while (conjuntoResultados.next()) {
				validado = conjuntoResultados.getBoolean("sp_valida_personafisica");
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();
		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, cliente_id, "DatosClienteRaro line 742 : ", es.toString());
			System.out.println("ERROR validar persona física SQL "+DatosClienteRaro.class.getName()+".getValidarPersonaFisica() "+es);
			bandera = false;
		}catch (Exception e) {
			System.out.println("ERROR validar persona física "+DatosClienteRaro.class.getName()+".getValidarPersonaFisica() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosClienteRaro.class.getName()+".getValidarPersonaFisica() "+es);
				
			}
		} // fin del finally
		return validado;
	}// fin metodo

	
	
	public boolean getValidarPersonaMoral(String cliente_id) {
		Connection conex = null;
		Statement instruccion;
		boolean validado = false;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "SELECT sp_valida_personamoral ('" + cliente_id + "')";

			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			while (conjuntoResultados.next()) {
				validado = conjuntoResultados.getBoolean("sp_valida_personamoral");
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, cliente_id, "DatosClienteRaro line 801 : ", es.toString());
			System.out.println("ERROR validar persona moral SQL "+DatosClienteRaro.class.getName()+".getValidarPersonaMoral() "+es);
			bandera = false;
		}catch (Exception e) {
			System.out.println("ERROR validar persona moral "+DatosClienteRaro.class.getName()+".getValidarPersonaMoral() "+e);	
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosClienteRaro.class.getName()+".getValidarPersonaMoral() "+es);
			}
		} // fin del finally
		return validado;
	}// fin metodo

	
	
	public boolean getValidarPersonaFideicomiso(String cliente_id) {
		Connection conex = null;
		Statement instruccion;
		boolean validado = false;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "SELECT sp_valida_fideicomiso ('" + cliente_id + "')";

			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			while (conjuntoResultados.next()) {
				validado = conjuntoResultados.getBoolean("sp_valida_fideicomiso");
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, cliente_id, "DatosClienteRaro line 860 : ", es.toString());
			System.out.println("ERROR validar persona fideicomiso SQL "+DatosClienteRaro.class.getName()+".getValidarPersonaFideicomiso() "+es);
			bandera = false;
		} catch (Exception e) {
			System.out.println("ERROR validar persona fideicomiso "+DatosClienteRaro.class.getName()+".getValidarPersonaFideicomiso() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosClienteRaro.class.getName()+".getValidarPersonaFideicomiso() "+es);
			}
		} // fin del finally
		return validado;
	}// fin metodo

	
	
	public boolean getValidarPersonaGobierno(String cliente_id) {
		Connection conex = null;
		Statement instruccion;
		boolean validado = false;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "SELECT sp_valida_gobierno ('" + cliente_id + "')";

			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			while (conjuntoResultados.next()) {
				validado = conjuntoResultados.getBoolean("sp_valida_gobierno");
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, cliente_id, "DatosClienteRaro line 919 : ", es.toString());
			System.out.println("ERROR validar persona Gobierno SQL "+DatosClienteRaro.class.getName()+".getValidarPersonaGobierno() "+es);
			bandera = false;
		}catch (Exception e) {
			System.out.println("ERROR validar persona Gobierno "+DatosClienteRaro.class.getName()+".getValidarPersonaGobierno() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosClienteRaro.class.getName()+".getValidarPersonaGobierno() "+es);
			}
		} // fin del finally
		return validado;
	}// fin metodo

	
	
	public boolean getValidarDomicilio(String cliente_id) {
		Connection conex = null;
		Statement instruccion;
		boolean validado = false;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "SELECT sp_valida_domicilioext ('" + cliente_id + "')";
			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			while (conjuntoResultados.next()) {
				validado = conjuntoResultados.getBoolean("sp_valida_domicilioext");
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, cliente_id, "DatosClienteRaro line 977 : ", es.toString());
			System.out.println("ERROR validar domicilio clienteRaro SQL "+DatosClienteRaro.class.getName()+".getValidarDomicilio() "+es);
			bandera = false;
		}catch (Exception e) {
			System.out.println("ERROR validar domicilio clienteRaro "+DatosClienteRaro.class.getName()+".getValidarDomicilio() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosClienteRaro.class.getName()+".getValidarDomicilio() "+es);
			}
		} // fin del finally
		return validado;
	}// fin metodo
	
	
	
	public boolean apagarBanderariesgo(String Cliente) {
		Connection conex = null;
		Statement instruccion;
		String resultado = "";
		boolean bandera = false;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "select * from avcoincidencias where (explicacion is null or explicacion = '') and cliente_id='"
					+ Cliente + "';";
			conjuntoResultados = instruccion.executeQuery(consulta);
			if (conjuntoResultados.next()) {
				// resultado = conjuntoResultados.getString("cliente_id");
				// System.out.println("el Count del "+Cliente+" es de : "+
				// resultado);
				bandera = true;
				// new
				// OperacionesCoincidencias().editarUltimaRevision(Cliente,"0");

			}

			conjuntoResultados.close();
			instruccion.close();
			// conex.close();

		} catch (SQLException es) {
			System.out.println("ERROR avcoincidencia SQL "+DatosClienteRaro.class.getName()+".apagarBanderariesgo() "+es);
		} catch (Exception e) {
			System.out.println("ERROR avcoincidencia "+DatosClienteRaro.class.getName()+".apagarBanderariesgo() "+e);
		} finally {
			try {
				conex.close();
			} catch (SQLException e) {
				System.out.println("ERROR cerrar conexión "+DatosClienteRaro.class.getName()+".apagarBanderariesgo() "+e);
			}
			return bandera;
		} // fin del finally

	}
	
	
	
	public String getNoClientePorSalesForce(String salesForce) {
		Connection conex = null;
		Statement instruccion;
		String resultado = "";

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "SELECT*from varusuariotransitorio where idcliente='" + salesForce + "';";
			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			if (conjuntoResultados.next()) {
				resultado = conjuntoResultados.getString("cliente_id");
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, salesForce, "DatosClienteRaro line 624 : ", es.toString());
			System.out.println("ERROR get numCliente SQL "+DatosClienteRaro.class.getName()+".getNoClientePorSalesForce() "+es);
		}catch (Exception e) {
			System.out.println("ERROR get numCliente "+DatosClienteRaro.class.getName()+".getNoClientePorSalesForce() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosClienteRaro.class.getName()+".getNoClientePorSalesForce() "+es);
			}
			System.out.println("el noCliente de " + salesForce + " es " + resultado);
			return resultado;
		} // fin del finally
	}// fin metodo

	
	public boolean verificaExistenciaContratoPorCliente(String noCliente, String servicio) {
		Connection conex = null;
		Statement instruccion;
		boolean resultado = false;

		try {
			conex = cnn.getConnection("dbcaa");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// String consulta = "SELECT*from contratoelectronico where
			// clienteid='"+noCliente+"' and servicio='"+servicio+"';";
			String consulta = "SELECT*from contratoelectronico where clienteid='" + noCliente + "' and trim(servicio)='"
					+ servicio + "';";

			conjuntoResultados = instruccion.executeQuery(consulta);

			if (conjuntoResultados.next()) {
				if (conjuntoResultados.getString("estatus").equals("X"))
					resultado = false;
				else
					resultado = true;
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

		} catch (SQLException es) {
			System.out.println("ERROR verifica contrato SLQ "+DatosClienteRaro.class.getName()+".verificaExistenciaContratoPorCliente() "+es);
		}catch (Exception e) {
			System.out.println("ERROR verifica contrato "+DatosClienteRaro.class.getName()+".verificaExistenciaContratoPorCliente() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosClienteRaro.class.getName()+".verificaExistenciaContratoPorCliente() "+es);
			}
			return resultado;
		} // fin del finally

	}
	
	
	public boolean insertarPistaAuditDeslindamiento(String cliente, String match, String Descripcion, String perfil,
			String usuario) throws SQLException {
		System.out.println("" + cliente + " " + match + " " + Descripcion + " " + perfil + " " + usuario);
		Connection conex = null;
		Statement instruccion=null;
		try {
			conex = cnn.getConnection("dbpld");
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "insert into pistaaudit (fecha,clienteid,campotexto,valor_original,valor_nuevo,perfilid,afectado) "
					+ "values('now()','" + usuario + "','Coincidencia:  " + match + "','','" + Descripcion + "','"
					+ perfil + "','" + cliente + "')";

			instruccion.executeQuery(consulta);
			
			instruccion.close();
			

		} catch (Exception es) {
				System.out.println("ERROR inserta pistaAudit "+DatosClienteRaro.class.getName()+".insertarPistaAuditDeslindamiento() "+es);
				return false;
			
		}finally {
			instruccion.close();
			conex.close();
		}
			return true;
		 // fin del finally
	}
	
	
	
	public boolean insertarContratoElectronico(String noCliente, String rfc, String razonSocial, String servicio) {
		Connection conex = null;
		Statement instruccion;
		boolean resultado = false;

		try {
			conex = cnn.getConnection("dbcaa");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// String consulta = "SELECT*from contratoelectronico where
			// clienteid='"+noCliente+"' and servicio='"+servicio+"';";
			String consulta = "insert into contratoelectronico (clienteid,rfc,razonsocial,servicio,fechachreacion,estatus) "
					+ "values(" + noCliente + ",'" + rfc + "','" + razonSocial + "','" + servicio + "',now(),'I')";

			instruccion.executeQuery(consulta);
			resultado = true;

			instruccion.close();
			conex.close();

		} catch (Exception es) {
			if (es.toString().contains("La consulta no retornó ningún resultado."))
				resultado = true;
		}finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosClienteRaro.class.getName()+".insertarContratoElectronico() "+es);
				
			}
			return resultado;
		} // fin del finally
	}
	
	
	
	public ArrayList<String> getRegistroContratoInformix(String noCliente) {
		Connection cone = null;
		Statement instruccion;
		ArrayList<String> servicios = new ArrayList<String>();
		
		try {
			cone = new ConexionInformix().getConexion();
			// crea objeto Statement para consultar la base de datos
			instruccion = cone.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "EXECUTE PROCEDURE consultaservicioscontrato(" + noCliente + ")";

			conjuntoResultados = instruccion.executeQuery(consulta);

			while (conjuntoResultados.next()) {
				if (!conjuntoResultados.getString("nombre").contains("---")) {
					servicios.add(conjuntoResultados.getString("nombre").trim());
					System.out.println("*********servicio: " + conjuntoResultados.getString("nombre"));
				}

			} // fin del while
			conjuntoResultados.close();
			instruccion.close();
			cone.close();
		} catch (Exception e) {
			System.out.println("ERROR obtener registro informix "+DatosClienteRaro.class.getName()+".getRegistroContratoInformix() "+e);
		}
		return servicios;
	}

	public void insertarContratosServiciosNoExistentes(String noCliente, String rfc, String razonSocial) {
		ArrayList<String> contratos = getRegistroContratoInformix(noCliente);
		for (int i = 0; i < contratos.size(); i++) {
			System.out.println("servicio a revisar **" + contratos.get(i) + "***");
			if (!verificaExistenciaContratoPorCliente(noCliente, contratos.get(i))) {
				insertarContratoElectronico(noCliente, rfc, razonSocial, contratos.get(i));
			}
		}
	}

	public String getClienteExterno(String RFC) {
		Connection cone = null;
		Statement instruccion;
		boolean validado = false;
		String idCliente = "";

		try {
			cone = new ConexionInformix().getConexion();
			// crea objeto Statement para consultar la base de datos
			instruccion = cone.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "select first 1 tnucl from tcons where trim(trfcc) = '" + RFC.trim() + "'";

			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			while (conjuntoResultados.next()) {
				idCliente = conjuntoResultados.getString("tnucl");
			} // fin del while

			if (idCliente.compareTo("") == 0) {
				idCliente = "-10001";
			}
			conjuntoResultados.close();
			instruccion.close();
			cone.close();

		} catch (SQLException es) {
			idCliente = "-10002";
			bandera = false;
		}finally {
			try {
				if (cone != null)
					cone.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosClienteRaro.class.getName()+".getClienteExterno() "+es);
			}
		} // fin del finally
		return idCliente;
	}// fin metodo

	
	
	public Integer getDocID(String idcliente) {
		Connection conex = null;
		Statement instruccion;
		Integer validado = null;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "select length (imagenid) as imgid from avpersonafisica where cliente_id = '" + idcliente
					+ "'";
			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			while (conjuntoResultados.next()) {
				validado = conjuntoResultados.getInt("imgid");
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

		} catch (SQLException es) {
			System.out.println("ERROR  consulta SQL "+DatosClienteRaro.class.getName()+".getDocID() "+es);
			bandera = false;
		}catch (Exception e) {
			System.out.println("ERROR getDoc "+DatosClienteRaro.class.getName()+".getDocID() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosClienteRaro.class.getName()+".getDocID() "+es);
			}
		} // fin del finally
		return validado;
	}// fin metodo

}
