/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datosRatos;

import entidad.Cliente;
import entidad.Fideicomiso;
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
public class DatosFideicomiso {

	private Conexion2 cnn = new Conexion2();
	private ResultSet conjuntoResultados;
	boolean bandera;

	public boolean insertar(Fideicomiso f, String perfilid, String usuarioEdicion) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+DatosFideicomiso.class.getName()+".insertar() "+e);
		}
		CallableStatement instruccion;
		boolean resultado = false;
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		java.sql.Date sqlFechaNacimiento = null;
		java.sql.Date sqlFecha = null;
		java.sql.Date sqlRlFecha = null;

		SimpleDateFormat formato;
		formato = new SimpleDateFormat("yyyy-MM-dd");

		// Adaptabndo la fecha de nacimiento al tipo de datos que acepte la base de datos
		String fechaNacimiento = f.getRlfechanacimiento();
		try {
			java.util.Date utilFechaNacimiento = formato.parse(fechaNacimiento);
			sqlFechaNacimiento = new java.sql.Date(utilFechaNacimiento.getTime());
		} catch (ParseException es) {
			sqlFechaNacimiento = sqlDate;
		} catch (Exception es) {
			sqlFechaNacimiento = sqlDate;
		}

		// Adaptando la fecha al tipo de dato que acepte la base de datos
		String fecha = f.getFechaNotarial();
		try {
			java.util.Date utilFecha = formato.parse(fecha);
			sqlFecha = new java.sql.Date(utilFecha.getTime());
		} catch (Exception es) {
			sqlFecha = sqlDate;
		}
		
		// Adaptando la Rlfecha al tipo de dato que acepte la base de datos
		String rlfecha = f.getRlFechaNotarial();
		try {
			java.util.Date utilFecha = formato.parse(rlfecha);
			sqlRlFecha = new java.sql.Date(utilFecha.getTime());
		} catch (Exception es) {
			sqlRlFecha = sqlDate;
		}

		try {
			instruccion = conex.prepareCall(
					"{call usp_change_fideicomiso(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			instruccion.setString(1, f.getCliente().getCliente_Id());
			instruccion.setString(2, f.getRazonsocial());
			instruccion.setString(3, f.getRfc());
			instruccion.setString(4, f.getNrofideicomiso());
			instruccion.setString(5, f.getRlnombre());
			instruccion.setString(6, f.getRlapellidopaterno());
			instruccion.setString(7, f.getRlapellidomaterno());
			instruccion.setDate(8, sqlFechaNacimiento);
			instruccion.setString(9, f.getRlrfc());
			if (f.getIdentificacion().getIdentifica_id() == null || f.getIdentificacion().getIdentifica_id().isEmpty()
					|| f.getIdentificacion().getIdentifica_id().length() == 0) {
				instruccion.setNull(10, java.sql.Types.NULL);
			} else {
				instruccion.setString(10, f.getIdentificacion().getIdentifica_id());
			}
			instruccion.setString(11, f.getRlautoridademiteid());
			instruccion.setString(12, f.getRlnumeroid());
			instruccion.setString(13, f.getRlcurp());
			instruccion.setString(14, f.getRlidentificaciontipo());
			instruccion.setDate(15, sqlDate);
			instruccion.setString(16, f.getImagenactaconstitutiva());
			instruccion.setString(17, f.getImagencedulafiscal());
			instruccion.setString(18, f.getImagenrlid());
			// instruccion.setString(19, f.getImagenrlcedulafiscal());
			instruccion.setString(19, f.getImagenrlpodernotarial());
			instruccion.setString(20, f.getInstitucionFiduciaria());
			instruccion.setInt(21, Integer.parseInt(perfilid));
			instruccion.setString(22, usuarioEdicion);
			instruccion.setString(23, f.getNoEscritura());
			instruccion.setDate(24, sqlFecha);
			instruccion.setString(25, f.getNotaria());
			instruccion.setString(26, f.getRlNoPoder());
			instruccion.setDate(27, sqlRlFecha);
			instruccion.setString(28, f.getRlNotaria());

			instruccion.execute();

			// Seteamos el exito de la transaccion
			resultado = true;

			// Limpieza del ambiente
			instruccion.close();
			conex.close();

		} catch (SQLException exSql) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, f.getCliente().getCliente_Id(), "DatosFideicomiso line 135 : ", exSql.toString());
			System.out.println("ERROR insertarFideicomiso SQL "+DatosFideicomiso.class.getName()+".insertar() "+exSql);
			// Seteamos el fracaso de la transaccion
			resultado = false;

		} catch (Exception es) {
			System.out.println("ERROR insertarFideicomiso "+DatosFideicomiso.class.getName()+".insertar() "+es);
			resultado = false; // Seteamos el fracaso de la transaccion
		} finally {
			try {
				if (conex != null) {
					conex.close();
				}
			}catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosFideicomiso.class.getName()+".insertar() "+es);
			} // fin del cath
			return resultado;
		} // fin del finally
	}// Fin del metodo insertar

	
	
	public Fideicomiso get(String where) {
		Connection conex = null;
		Statement instruccion;
		boolean hayDatos = false;
		Fideicomiso f = null;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String consulta = "SELECT cliente_id, razonsocial, rfc, nrofideicomiso, rlnombre, rlapellidopaterno, rlapellidomaterno, rlfechanacimiento, rlrfc, rlidentifica_id, rlautoridademiteid, rlnumeroid, rlcurp, rlidentificaciontipo, fecharegistro, imagenactaconstitutiva, imagencedulafiscal, imagenrlid, imagenrlpodernotarial, institucionfiduciaria,no_escritura,fecha,notaria,rlno_poder,rlfecha,rlnotaria "
					+ "	FROM avfideicomiso  ";

			if (where != null) {
				if (!where.isEmpty()) {
					consulta += " WHERE " + where;
				}
			}

			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			while (conjuntoResultados.next()) {
				String cliente_id = conjuntoResultados.getString("cliente_id");
				String razonsocial = conjuntoResultados.getString("razonsocial").trim();
				String rfc = conjuntoResultados.getString("rfc").trim();
				String nrofideicomiso = conjuntoResultados.getString("nrofideicomiso").trim();
				String rlnombre = conjuntoResultados.getString("rlnombre").trim();
				String rlapellidopaterno = conjuntoResultados.getString("rlapellidopaterno").trim();
				String rlapellidomaterno = conjuntoResultados.getString("rlapellidomaterno").trim();
				String rlfechanacimiento = conjuntoResultados.getString("rlfechanacimiento").trim();
				String rlrfc = conjuntoResultados.getString("rlrfc").trim();
				String rlidentifica_id = conjuntoResultados.getString("rlidentifica_id").trim();
				String rlautoridademiteid = conjuntoResultados.getString("rlautoridademiteid").trim();
				String rlnumeroid = conjuntoResultados.getString("rlnumeroid").trim();
				String rlcurp = conjuntoResultados.getString("rlcurp").trim();
				String rlidentificaciontipo = conjuntoResultados.getString("rlidentificaciontipo").trim();
				String fecharegistro = conjuntoResultados.getString("fecharegistro").trim();
				String imagenactaconstitutiva = conjuntoResultados.getString("imagenactaconstitutiva").trim();
				String imagencedulafiscal = conjuntoResultados.getString("imagencedulafiscal").trim();
				String imagenrlid = conjuntoResultados.getString("imagenrlid").trim();
				// String
				// imagenrlcedulafiscal=conjuntoResultados.getString("imagenrlcedulafiscal").trim();
				String imagenrlpodernotarial = conjuntoResultados.getString("imagenrlpodernotarial").trim();
				String institucionfiduciaria = conjuntoResultados.getString("institucionfiduciaria").trim();
				String no_escritura = conjuntoResultados.getString("no_escritura").trim();
				String fecha = conjuntoResultados.getString("fecha").trim();
				String notaria = conjuntoResultados.getString("notaria").trim();
				String rlno_poder = conjuntoResultados.getString("rlno_poder").trim();
				String rlfecha = conjuntoResultados.getString("rlfecha").trim();
				String rlnotaria = conjuntoResultados.getString("rlnotaria").trim();

				Cliente c = new Cliente();
				c.setCliente_Id(cliente_id);
				TipoIdentificacion identificacion = new TipoIdentificacion(rlidentifica_id);

				f = new Fideicomiso(c, razonsocial, rfc, nrofideicomiso, rlnombre, rlapellidopaterno, rlapellidomaterno,
						rlfechanacimiento, rlrfc, identificacion, rlautoridademiteid, rlnumeroid, rlcurp,
						rlidentificaciontipo, fecharegistro, imagenactaconstitutiva, imagencedulafiscal, imagenrlid,
						imagenrlpodernotarial, institucionfiduciaria, rlno_poder, rlnotaria, rlfecha, no_escritura,
						fecha, notaria);

			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();
		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, where, "DatosFideicomiso line 243 : ", es.toString());
			System.out.println("ERROR getFideicomisoSQL "+DatosFideicomiso.class.getName()+".get() "+es);
			bandera = false;
		}catch (Exception e) {
			System.out.println("ERROR getFideicomiso "+DatosFideicomiso.class.getName()+".get() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosFideicomiso.class.getName()+".get() "+es);
			}
		} // fin del finally
		return f;
	} // fin del metodo get
	
	

	public boolean cambiarPersona(String personaDestino, Cliente c) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+DatosFideicomiso.class.getName()+".cambiarPersona() "+e);
		}
		CallableStatement instruccion;
		boolean resultado = false;

		try {
			instruccion = conex.prepareCall("{call usp_changetipopersona_fideicomiso(?,?)}");
			instruccion.setString(1, personaDestino);
			instruccion.setString(2, c.getCliente_Id());
			System.out.println("consulta: " + instruccion);
			instruccion.execute();

			// Seteamos el exito de la transaccion
			resultado = true;

			// Limpieza del ambiente
			instruccion.close();
			conex.close();

		} catch (SQLException exSql) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, c.getCliente_Id(), "DatosFideicomiso line 297 : ", exSql.toString());
			System.out.println("ERROR cambiar persona FideicomisoSQL "+DatosFideicomiso.class.getName()+".cambiarPersona() "+exSql);
			// Seteamos el fracaso de la transaccion
			resultado = false;
		} catch (Exception es) {
			System.out.println("ERROR cambiar persona Fideicomiso"+DatosFideicomiso.class.getName()+".cambiarPersona() "+es);
			// Seteamos el fracaso de la transaccion
			resultado = false;
		} finally {
			try {
				if (conex != null) {
					conex.close();
				}
			} // fin del try
			catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosFideicomiso.class.getName()+".cambiarPersona() "+es);
			} // fin del cath
			return resultado;
		} // fin del finally
	}// Fin del metodo cambiarPersona
}