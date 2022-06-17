/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datosRatos;

import entidad.Actividad;
import entidad.Cliente;
import entidad.Pais;
import entidad.PersonaFisica;
import entidad.TipoIdentificacion;
import utilidades.Rutas;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Israel Osiris Garcia
 */
public class DatosPersonaFisica {
	private Conexion2 cnn = new Conexion2();
	private ResultSet conjuntoResultados;
	boolean bandera;

	
	public boolean insertar(PersonaFisica p, String perfilid, String usuarioEdicion) {
		Connection conex = null;
		Connection conex2 = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+DatosPersonaFisica.class.getName()+".insertar() "+e);
		}
		CallableStatement instruccion;
		boolean resultado = false;
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		System.out.println("utilDate:" + utilDate);
		System.out.println("sqlDate:" + sqlDate);

		java.sql.Date sqlFechaNacimiento = null;
		SimpleDateFormat formato;
		formato = new SimpleDateFormat("yyyy-MM-dd");

		// Adaptabndo la fecha de nacimiento al tipo de datos que acepte la base de datos
		String fechaNacimiento = "";
		if (p.getFechaNacimiento() != null && !p.getFechaNacimiento().isEmpty()) {
			fechaNacimiento = p.getFechaNacimiento();
		} else {
			fechaNacimiento = "1900-01-01"; // si no declaro fecha se pone una fecha generica
		}

		try {
			java.util.Date utilFechaNacimiento = formato.parse(fechaNacimiento);
			sqlFechaNacimiento = new java.sql.Date(utilFechaNacimiento.getTime());
		} catch (ParseException es) {
			sqlFechaNacimiento = sqlDate;
		}

		try {
			instruccion = conex
					.prepareCall("{call usp_change_personafisica(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			instruccion.setString(1, p.getCliente().getCliente_Id());
			instruccion.setString(2, p.getNombre());
			instruccion.setString(3, p.getApellidoPaterno());
			instruccion.setString(4, p.getApellidoMaterno());
			instruccion.setDate(5, sqlFechaNacimiento);
			instruccion.setString(6, p.getRFC());
			instruccion.setString(7, p.getPaisnacimiento().getPais());
			if (p.getActividad().getActividad_Id() != null && !p.getActividad().getActividad_Id().isEmpty()) {
				instruccion.setString(8, p.getActividad().getActividad_Id());
			} else {
				instruccion.setNull(8, java.sql.Types.NULL);
			}
			if (p.getIdentificacion().getIdentifica_id() != null
					&& !p.getIdentificacion().getIdentifica_id().isEmpty()) {
				instruccion.setString(9, p.getIdentificacion().getIdentifica_id());
			} else {
				instruccion.setString(9, "14");
			}
			instruccion.setString(10, p.getIdentificacionTipo());
			instruccion.setString(11, p.getNumeroId());
			instruccion.setString(12, p.getAutoridadEmiteId());
			instruccion.setString(13, p.getCURP());
			if (p.getPaisnacionalidad().getPais() != null && !p.getPaisnacionalidad().getPais().isEmpty()) {
				instruccion.setString(14, p.getPaisnacionalidad().getPais());
			} else {
				instruccion.setNull(14, java.sql.Types.NULL);
			}
			instruccion.setDate(15, sqlDate);
			instruccion.setString(16, p.getImagenId());
			instruccion.setString(17, p.getImagenCedulaFiscal());
			instruccion.setString(18, p.getImagenCurp());
			instruccion.setString(19, p.getImagenDeclaratoria());
			instruccion.setInt(20, Integer.parseInt(perfilid));
			instruccion.setString(21, usuarioEdicion);

			instruccion.execute();

			// Seteamos el exito de la transaccion
			resultado = true;

			// Limpieza del ambiente
			instruccion.close();
			conex.close();

		} catch (SQLException exSql) {
			System.out.println("ERROR guardar personaF SQL "+DatosPersonaFisica.class.getName()+".insertar() "+exSql);
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, p.getCliente().getCliente_Id(), "DatosPersonaFisica line 130 : ", exSql.toString());
			// Seteamos el fracaso de la transaccion
			resultado = false;
		} catch (Exception es) {
			System.out.println("ERROR guardar personaF "+DatosPersonaFisica.class.getName()+".insertar() "+es);
			// Seteamos el fracaso de la transaccion
			resultado = false;
		} finally {
			try {
				if (conex != null) {
					conex.close();
				}
			} // fin del try
			catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosPersonaFisica.class.getName()+".insertar() "+es);
			} // fin del cath
			return resultado;
		} // fin del finally
	}// Fin del metodo insertar

	
	
	public boolean agregarCliente(PersonaFisica p) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+DatosPersonaFisica.class.getName()+".agregarCliente() "+e);
		}
		Statement instruccion;
		ResultSet conjuntoResultados;
		boolean resultado = false;
		boolean noHayId = false;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			if (p.getIdentificacion().getIdentifica_id() == null || p.getIdentificacion().getIdentifica_id().isEmpty()
					|| p.getIdentificacion().getIdentifica_id().length() == 0) {
				noHayId = true;
			}

			String consulta = "INSERT INTO avpersonafisica ( cliente_id, nombre, apellidopaterno, apellidomaterno, fechanacimiento, rfc, paisnacim, actividad_id, identifica_id, identificaciontipo , numeroid , autoridademiteid, curp, paisnacio, fecharegistro, imagenid, imagencedulafiscal, imagencurp, imagendeclaratoria) ";
			// cliente_id, nombre, apellidopaterno, apellidomaterno,
			// fechanacimiento, rfc, paisnacim, actividad_id, identifica_id,
			// identificaciontipo ,
			// numeroid , autoridademiteid, curp, paisnacio, fecharegistro,
			// imagenid, imagencedulafiscal) ";

			consulta += " values (";
			consulta += "'" + p.getCliente().getCliente_Id() + "',";
			consulta += "'" + p.getNombre() + "',";
			consulta += "'" + p.getApellidoPaterno() + "',";
			consulta += "'" + p.getApellidoMaterno() + "',";
			consulta += "'" + p.getFechaNacimiento() + "',";
			consulta += "'" + p.getRFC() + "',";
			consulta += "'" + p.getPaisnacimiento().getPais() + "',";
			consulta += "'" + p.getActividad().getActividad_Id() + "',";
			if (noHayId) {
				consulta += "null,";
			} else {
				consulta += "'" + p.getIdentificacion().getIdentifica_id() + "',";
			}

			consulta += "'" + p.getIdentificacionTipo() + "',";
			consulta += "'" + p.getNumeroId() + "',";
			consulta += "'" + p.getAutoridadEmiteId() + "',";
			consulta += "'" + p.getCURP() + "',";
			consulta += "'" + p.getPaisnacionalidad().getPais() + "',";
			consulta += "" + "now()" + ","; // fecha de registro
			consulta += "'" + p.getImagenId() + "',";
			consulta += "'" + p.getImagenCedulaFiscal() + "',";
			consulta += "'" + p.getImagenCurp() + "',";
			consulta += "'" + p.getImagenDeclaratoria() + "')";

			instruccion.executeUpdate(consulta);
			resultado = true;
		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, p.getCliente().getCliente_Id(), "DatosPersonaFisica line 222 : ", es.toString());
			System.out.println("ERROR insertar persona física SQL"+DatosPersonaFisica.class.getName()+".agregarCliente() "+es);
			resultado = false;
		}catch (Exception e) {
			System.out.println("ERROR insertar persona física "+DatosPersonaFisica.class.getName()+".agregarCliente() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosPersonaFisica.class.getName()+".agregarCliente() "+es);
			}
		} // fin del finally
		return resultado;
	} // fin del metodo agregar prospecto

	
	
	public PersonaFisica get(String where) {
		Connection conex = null;

		Statement instruccion;
		boolean hayDatos = false;
		PersonaFisica pf = null;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String consulta = "	SELECT cliente_id, nombre, apellidopaterno, apellidomaterno, fechanacimiento, rfc, paisnacim, actividad_id, identifica_id, identificaciontipo, numeroid, autoridademiteid, curp, paisnacio, fecharegistro, imagenid, imagencedulafiscal, imagencurp, imagendeclaratoria"
					+ " FROM avpersonafisica ";

			if (where != null) {
				if (!where.isEmpty()) {
					consulta += " WHERE " + where;
				}
			}

			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;
			while (conjuntoResultados.next()) {

				String cliente_id = conjuntoResultados.getString("cliente_id");
				String nombre = conjuntoResultados.getString("nombre").trim();
				String apellidopaterno = conjuntoResultados.getString("apellidopaterno").trim();
				String apellidomaterno = conjuntoResultados.getString("apellidomaterno").trim();
				String fechanacimiento = conjuntoResultados.getString("fechanacimiento").trim();
				String rfc = conjuntoResultados.getString("rfc").trim();
				String paisnacim = conjuntoResultados.getString("paisnacim").trim();
				String actividad_id = conjuntoResultados.getString("actividad_id").trim();
				String identifica_id = conjuntoResultados.getString("identifica_id").trim();
				String identificaciontipo = conjuntoResultados.getString("identificaciontipo").trim();
				String numeroid = conjuntoResultados.getString("numeroid").trim();
				String autoridademiteid = conjuntoResultados.getString("autoridademiteid").trim();
				String curp = conjuntoResultados.getString("curp").trim();
				String paisnacio = conjuntoResultados.getString("paisnacio").trim();
				String fecharegistro = conjuntoResultados.getString("fecharegistro").trim();
				String imagenid = conjuntoResultados.getString("imagenid").trim();
				String imagencedulafiscal = conjuntoResultados.getString("imagencedulafiscal").trim();
				String imagencurp = conjuntoResultados.getString("imagencurp").trim();
				String imagendeclaratoria = conjuntoResultados.getString("imagendeclaratoria").trim();

				pf = new PersonaFisica();
				Cliente c = new Cliente();
				c.setCliente_Id(cliente_id.trim());

				Pais paisNacimiento = new Pais();
				paisNacimiento.setPais(paisnacim);

				Pais paisNacionalidad = new Pais();
				paisNacionalidad.setPais(paisnacio);

				Actividad actividad = new Actividad();
				actividad.setActividad_Id(actividad_id);

				TipoIdentificacion identificacion = new TipoIdentificacion(identifica_id);

				pf.setNombre(nombre);
				pf.setApellidoPaterno(apellidopaterno);
				pf.setApellidoMaterno(apellidomaterno);
				pf.setFechaNacimiento(fechanacimiento);
				pf.setRFC(rfc);
				pf.setPaisnacimiento(paisNacimiento);
				pf.setActividad(actividad);
				pf.setIdentificacion(identificacion);
				pf.setIdentificacionTipo(identificaciontipo);
				pf.setNumeroId(numeroid);
				pf.setAutoridadEmiteId(autoridademiteid);
				pf.setCURP(curp);
				pf.setPaisnacionalidad(paisNacionalidad);
				pf.setFechaRegistro(fecharegistro);
				pf.setImagenId(imagenid);
				pf.setImagenCedulaFiscal(imagencedulafiscal);
				pf.setImagenCurp(imagencurp);
				pf.setImagenDeclaratoria(imagendeclaratoria);

			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();
		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, where, "DatosPersonaFisica line 347 : ", es.toString());
			bandera = false;
			System.out.println("ERROR get personaFísica SQL "+DatosPersonaFisica.class.getName()+".get() "+es);
		}catch (Exception e) {
			System.out.println("ERROR get "+DatosPersonaFisica.class.getName()+".get() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosPersonaFisica.class.getName()+".get() "+es);
			}
		} // fin del finally
		return pf;
	} // fin del metodo get
}
