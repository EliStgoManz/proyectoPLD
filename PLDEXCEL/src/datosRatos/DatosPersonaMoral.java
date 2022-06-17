/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datosRatos;

import entidad.Cliente;
import entidad.Giro;
import entidad.Pais;
import entidad.PersonaMoral;
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
public class DatosPersonaMoral {

	private Conexion2 cnn = new Conexion2();
	private ResultSet conjuntoResultados;
	boolean bandera;

	
	public boolean insertar(PersonaMoral p, String perfilid, String usuarioEdicion) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+DatosPersonaMoral.class.getName()+".insertar() "+e);
		}
		
		CallableStatement instruccion;
		boolean resultado = false;
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		System.out.println("utilDate:" + utilDate);
		System.out.println("sqlDate:" + sqlDate);

		java.sql.Date sqlFechaConstitucion = null;
		java.sql.Date sqlFechaNacimiento = null;
		java.sql.Date sqlFecha = null;
		java.sql.Date sqlRlFecha = null;

		SimpleDateFormat formato;
		formato = new SimpleDateFormat("yyyy-MM-dd");

		// Adaptando la fecha de Constitucion al tipo de dato que acepte la base
		// de datos
		String fechaConstitucion = p.getFechaConstitucion();
		try {
			java.util.Date utilFechaConstitucion = formato.parse(fechaConstitucion);
			sqlFechaConstitucion = new java.sql.Date(utilFechaConstitucion.getTime());
		} catch (Exception es) {
			sqlFechaConstitucion = sqlDate;
		}
		
		// Adaptando la fecha al tipo de dato que acepte la base de datos
		String fecha = p.getFechaNotarial();
		try {
			java.util.Date utilFecha = formato.parse(fecha);
			sqlFecha = new java.sql.Date(utilFecha.getTime());
		} catch (Exception es) {
			sqlFecha = sqlDate;
		}
		
		// Adaptando la Rlfecha al tipo de dato que acepte la base de datos
		String rlfecha = p.getRlFechaNotarial();
		try {
			java.util.Date utilFecha = formato.parse(rlfecha);
			sqlRlFecha = new java.sql.Date(utilFecha.getTime());
		} catch (Exception es) {
			sqlRlFecha = sqlDate;
		}

		// Adaptabndo la fecha de nacimiento al tipo de datos que acepte la base de datos
		String fechaNacimiento = "";
		if (p.getRLFechaNacimiento() != null && !p.getRLFechaNacimiento().isEmpty()) {
			fechaNacimiento = p.getRLFechaNacimiento();
		} else {
			fechaNacimiento = "1900-01-01"; // si no declaro fecha se pone una fecha generica
		}

		try {
			java.util.Date utilFechaNacimiento = formato.parse(fechaNacimiento);
			sqlFechaNacimiento = new java.sql.Date(utilFechaNacimiento.getTime());
		} catch (Exception es) {
			sqlFechaNacimiento = sqlDate;
		}

		try {
			instruccion = conex.prepareCall(
					"{call usp_change_personamoral(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			instruccion.setString(1, p.getCliente().getCliente_Id());
			instruccion.setString(2, p.getRazonSocial());
			instruccion.setString(3, p.getRFC());
			instruccion.setDate(4, sqlFechaConstitucion); // fecha de
															// constitucion
			instruccion.setString(5, p.getPais().getPais());
			instruccion.setString(6, p.getRLNombre());
			instruccion.setString(7, p.getRLApellidoPaterno());
			instruccion.setString(8, p.getRLApellidoMaterno());
			instruccion.setDate(9, sqlFechaNacimiento); // fecha de nacimiento
			instruccion.setString(10, p.getRLRFC());

			if (p.getIdentificacion().getIdentifica_id() != null
					&& !p.getIdentificacion().getIdentifica_id().isEmpty()) {
				instruccion.setString(11, p.getIdentificacion().getIdentifica_id());
			} else {
				instruccion.setString(11, "14");
			}
			instruccion.setString(12, p.getRLAutoridadEmiteId());
			instruccion.setString(13, p.getRLNumeroId());
			instruccion.setString(14, p.getRLCURP());
			instruccion.setString(15, p.getRLIdentificacionTipo());
			instruccion.setString(16, p.getGiro().getGiro_id());
			instruccion.setDate(17, sqlDate); // fecha de registro se asigna en
												// sp
			instruccion.setString(18, p.getImagenActaConstitutiva());
			instruccion.setString(19, p.getImagenCedulaFiscal());
			instruccion.setString(20, p.getImagenRLId());
			instruccion.setString(21, p.getImagenRLPoderNotarial());
			instruccion.setString(22, p.getImagenDeclaratoria());
			instruccion.setInt(23, Integer.parseInt(perfilid));
			instruccion.setString(24, usuarioEdicion);
			instruccion.setString(25, p.getNoEscritura());
			instruccion.setDate(26, sqlFecha);
			instruccion.setString(27, p.getNotaria());
			instruccion.setString(28, p.getRlNoPoder());
			instruccion.setDate(29, sqlRlFecha);
			instruccion.setString(30, p.getRlNotaria());
			instruccion.execute();

			// Seteamos el exito de la transaccion
			resultado = true;

			// Limpieza del ambiente
			instruccion.close();
			conex.close();

		} catch (SQLException exSql) {			
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, p.getCliente().getCliente_Id(), "DatosPersonaMoral line 159 : ", exSql.toString());
			System.out.println("ERROR insertar personaMoral SQL "+DatosPersonaMoral.class.getName()+".insertar() "+exSql);
			// Seteamos el fracaso de la transaccion
			resultado = false;
		} catch (Exception es) {
			System.out.println("ERROR insertar personaMoral "+DatosPersonaMoral.class.getName()+".insertar() "+es);
			// Seteamos el fracaso de la transaccion
			resultado = false;
		} finally {
			try {
				if (conex != null) {
					conex.close();
				}
			}catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosPersonaMoral.class.getName()+".insertar() "+es);
			} // fin del cath
			return resultado;
		} // fin del finally
	}// Fin del metodo insertar

	
	
	public PersonaMoral get(String where) {
		Connection conex = null;
		Statement instruccion;
		boolean hayDatos = false;
		PersonaMoral pm = null;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "SELECT cliente_id, razonsocial, rfc, fechaconstitucion, pais, rlnombre, rlapellidopaterno, rlapellidomaterno, rlfechanacimiento, rlrfc, rlidentifica_id, rlautoridademiteid, rlnumeroid, rlcurp, rlidentificaciontipo, giro_id, fecharegistro, imagenactaconstitutiva, imagencedulafiscal, imagenrlid, imagenrlpodernotarial, imagendeclaratoria,no_escritura,fecha,notaria,rlno_poder,rlfecha,rlnotaria "
					+ "	FROM avpersonamoral ";

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
				String fechaconstitucion = conjuntoResultados.getString("fechaconstitucion").trim();
				String pais = conjuntoResultados.getString("pais").trim();
				String rlnombre = conjuntoResultados.getString("rlnombre").trim();
				String rlapellidopaterno = conjuntoResultados.getString("rlapellidopaterno").trim();
				String rlapellidomaterno = conjuntoResultados.getString("rlapellidomaterno").trim();
				String rlfechanacimiento = conjuntoResultados.getString("rlfechanacimiento").trim();
				String rlrfc = conjuntoResultados.getString("rlrfc").trim();
				String identifica_id = conjuntoResultados.getString("rlidentifica_id").trim();
				String rlautoridademiteid = conjuntoResultados.getString("rlautoridademiteid").trim();
				String rlnumeroid = conjuntoResultados.getString("rlnumeroid").trim();
				String rlcurp = conjuntoResultados.getString("rlcurp").trim();
				String rlidentificaciontipo = conjuntoResultados.getString("rlidentificaciontipo").trim();
				String giro_id = conjuntoResultados.getString("giro_id").trim();
				String fecharegistro = conjuntoResultados.getString("fecharegistro").trim();
				String imagenactaconstitutiva = conjuntoResultados.getString("imagenactaconstitutiva").trim();
				String imagencedulafiscal = conjuntoResultados.getString("imagencedulafiscal").trim();
				String imagenrlid = conjuntoResultados.getString("imagenrlid").trim();
				String imagenrlpodernotarial = conjuntoResultados.getString("imagenrlpodernotarial").trim();
				String imagendeclaratoria = conjuntoResultados.getString("imagendeclaratoria").trim();
				String no_escritura = conjuntoResultados.getString("no_escritura").trim();
				String fecha = conjuntoResultados.getString("fecha").trim();
				String notaria = conjuntoResultados.getString("notaria").trim();
				String rlno_poder = conjuntoResultados.getString("rlno_poder").trim();
				String rlfecha = conjuntoResultados.getString("rlfecha").trim();
				String rlnotaria = conjuntoResultados.getString("rlnotaria").trim();

				Cliente c = new Cliente();
				c.setCliente_Id(cliente_id);

				Pais p = new Pais();
				p.setPais(pais);

				TipoIdentificacion identificacion = new TipoIdentificacion(identifica_id);

				Giro g = new Giro(giro_id);

				pm = new PersonaMoral(c, razonsocial, rfc, fechaconstitucion, p, rlnombre, rlapellidopaterno,
						rlapellidomaterno, rlfechanacimiento, rlrfc, identificacion, rlautoridademiteid, rlnumeroid,
						rlcurp, rlidentificaciontipo, g, fecharegistro, imagenactaconstitutiva, imagencedulafiscal,
						imagenrlid, imagenrlpodernotarial, imagendeclaratoria, rlno_poder, rlnotaria, rlfecha,
						no_escritura, fecha, notaria);
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();
		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, where, "DatosPersonaMoral line 265 : ", es.toString());
			System.out.println("ERROR get personaMoral SQL "+DatosPersonaMoral.class.getName()+".get() "+es);
			bandera = false;
		}catch (Exception e) {
			System.out.println("ERROR getPersonaMoral "+DatosPersonaMoral.class.getName()+".get() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosPersonaMoral.class.getName()+".get() "+es);
			}
		} // fin del finally
		return pm;
	} // fin del metodo get

	
	
	public boolean cambiarPersona(String personaDestino, Cliente c) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+DatosPersonaMoral.class.getName()+".cambiarPersona() "+e);
		}
		CallableStatement instruccion;
		boolean resultado = false;

		try {
			instruccion = conex.prepareCall("{call usp_changetipopersona_moral(?,?)}");
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
			System.out.println("ERROR cambiar personaMoral SQL "+DatosPersonaMoral.class.getName()+".cambiarPersona() "+exSql);
			// Seteamos el fracaso de la transaccion
			resultado = false;
		} catch (Exception es) {
			System.out.println("ERROR cambiar personaMoral "+DatosPersonaMoral.class.getName()+".cambiarPersona() "+es);
			// Seteamos el fracaso de la transaccion
			resultado = false;
		} finally {
			try {
				if (conex != null) {
					conex.close();
				}
			}catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosPersonaMoral.class.getName()+".cambiarPersona() "+es);
			} // fin del cath
			return resultado;
		} // fin del finally
	}// Fin del metodo cambiarPersona

}