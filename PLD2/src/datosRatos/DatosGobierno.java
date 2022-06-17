/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datosRatos;

import entidad.Cliente;
import entidad.Giro;
import entidad.Gobierno;
import entidad.Pais;
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
public class DatosGobierno {
	private Conexion2 cnn = new Conexion2();
	private ResultSet conjuntoResultados;
	boolean bandera;
	
	

	// public boolean insertar(Gobierno g){
	public boolean insertar(Gobierno g, String perfilid, String usuarioEdicion) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+DatosGobierno.class.getName()+".insertar() "+e);
		}
		CallableStatement instruccion;
		boolean resultado = false;
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		System.out.println("utilDate:" + utilDate);
		System.out.println("sqlDate:" + sqlDate);
		java.sql.Date sqlFechaCreacion = null;
		java.sql.Date sqlFechaNacimiento = null;

		SimpleDateFormat formato;
		formato = new SimpleDateFormat("yyyy-MM-dd");

		// Adaptando la fecha de Constitucion al tipo de dato que acepte la base de datos
		String fechaCreacion = g.getFechacreacion();
		try {
			java.util.Date utilFechaConstitucion = formato.parse(fechaCreacion);
			sqlFechaCreacion = new java.sql.Date(utilFechaConstitucion.getTime());
		} catch (ParseException es) {
			sqlFechaCreacion = sqlDate;
		}
		
		// Adaptabndo la fecha de nacimiento al tipo de datos que acepte la base de datos
		String fechaNacimiento = g.getRlfechanacimiento();
		try {
			java.util.Date utilFechaNacimiento = formato.parse(fechaNacimiento);
			sqlFechaNacimiento = new java.sql.Date(utilFechaNacimiento.getTime());
		} catch (ParseException es) {
			sqlFechaNacimiento = sqlDate;
		}

		try {
			instruccion = conex
					.prepareCall("{call usp_change_personagobierno(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			//
			instruccion.setString(1, g.getCliente().getCliente_Id());
			instruccion.setString(2, g.getRazonsocial());
			if (g.getActividadobjetosocial() != null && !g.getActividadobjetosocial().isEmpty()
					&& g.getActividadobjetosocial() != "") {
				instruccion.setString(3, g.getActividadobjetosocial());
			} else {
				instruccion.setString(3, "1000000");
			}

			instruccion.setString(4, g.getRfc());
			if (g.getFechacreacion() != null && !g.getFechacreacion().isEmpty()) {
				instruccion.setDate(5, sqlFechaCreacion);
			} else {
				instruccion.setNull(5, java.sql.Types.DATE);
			}
			if (g.getPais().getPais() != null && !g.getPais().getPais().isEmpty()) {
				instruccion.setString(6, g.getPais().getPais());
			} else {
				instruccion.setString(6, "MX");
			}
			// if ( g.getGiro().getGiro_id() != null &&
			// !g.getGiro().getGiro_id().isEmpty()){
			// instruccion.setString(7, g.getGiro().getGiro_id());
			// } else {
			// instruccion.setNull(7, java.sql.Types.NULL);
			// }
			instruccion.setString(7, "1000000");
			instruccion.setString(8, g.getRlnombre());
			instruccion.setString(9, g.getRlapellidopaterno());
			instruccion.setString(10, g.getRlapellidomaterno());
			if (g.getRlfechanacimiento() != null && !g.getRlfechanacimiento().isEmpty()) {
				instruccion.setDate(11, sqlFechaNacimiento);
			} else {
				instruccion.setNull(11, java.sql.Types.DATE);
			}
			instruccion.setString(12, g.getRlrfc());

			if (g.getIdentificacion().getIdentifica_id() != null
					&& !g.getIdentificacion().getIdentifica_id().isEmpty()) {
				instruccion.setString(13, g.getIdentificacion().getIdentifica_id());
			} else {
				instruccion.setString(13, "14");
			}

			instruccion.setString(14, g.getRlautoridademiteid());
			instruccion.setString(15, g.getRlnumeroid());
			instruccion.setString(16, g.getRlcurp());
			instruccion.setString(17, g.getRlidentificaciontipo());
			instruccion.setDate(18, sqlDate);
			instruccion.setString(19, g.getImagenacreditacion());

			instruccion.setString(20, g.getImagencedulafiscal());
			instruccion.setString(21, g.getImagenrlid());
			instruccion.setString(22, g.getImagenrlfacultades());
			instruccion.setInt(23, Integer.parseInt(perfilid));
			instruccion.setString(24, usuarioEdicion);

			instruccion.execute();

			// Seteamos el exito de la transaccion
			resultado = true;

			// Limpieza del ambiente
			instruccion.close();
			conex.close();

		} catch (SQLException exSql) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, g.getCliente().getCliente_Id(), "DatosGobierno line 147 : ", exSql.toString());
			System.out.println("ERROR insertarGobiernoSQL "+DatosGobierno.class.getName()+".insertar() "+exSql);
			resultado = false; // Seteamos el fracaso de la transaccion
		} catch (Exception es) {
			System.out.println("ERROR insertar Gobierno "+DatosGobierno.class.getName()+".insertar() "+es);
			resultado = false; // Seteamos el fracaso de la transaccion
		} finally {
			try {
				if (conex != null) {
					conex.close();
				}
			} // fin del try
			catch (SQLException es) {
				System.out.println("ERROR cerrarConexión "+DatosGobierno.class.getName()+".insertar() "+es);
			} // fin del cath
			return resultado;
		} // fin del finally
	}// Fin del metodo insertar

	
	
	
	public Gobierno get(String where) {
		Connection conex = null;
		Statement instruccion;
		boolean hayDatos = false;
		Gobierno g = null;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "SELECT cliente_id, razonsocial, actividadobjetosocial, rfc, fechacreacion, pais, giro_id, rlnombre, rlapellidopaterno, rlapellidomaterno, rlfechanacimiento, rlrfc, rlidentifica_id, rlautoridademiteid, rlnumeroid, rlcurp, rlidentificaciontipo, fecharegistro, imagenacreditacion, imagencedulafiscal, imagenrlid, imagenrlfacultades "
					+ "	FROM avgobierno ";

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
				String actividadobjetosocial = conjuntoResultados.getString("actividadobjetosocial").trim();
				String rfc = conjuntoResultados.getString("rfc").trim();
				String fechacreacion = conjuntoResultados.getString("fechacreacion").trim();
				String pais = conjuntoResultados.getString("pais").trim();
				String giro_id = conjuntoResultados.getString("giro_id").trim();
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
				String fecharegistro = conjuntoResultados.getString("fecharegistro").trim();
				String imagenacreditacion = conjuntoResultados.getString("imagenacreditacion").trim();
				String imagencedulafiscal = conjuntoResultados.getString("imagencedulafiscal").trim();
				String imagenrlid = conjuntoResultados.getString("imagenrlid").trim();
				String imagenrlfacultades = conjuntoResultados.getString("imagenrlfacultades").trim();

				Cliente c = new Cliente();
				c.setCliente_Id(cliente_id);
				Pais p = new Pais(pais);
				Giro gir = new Giro(giro_id);
				TipoIdentificacion identificacion = new TipoIdentificacion(identifica_id);

				g = new Gobierno(c, razonsocial, actividadobjetosocial, rfc, fechacreacion, p, gir, rlnombre,
						rlapellidopaterno, rlapellidomaterno, rlfechanacimiento, rlrfc, identificacion,
						rlautoridademiteid, rlnumeroid, rlcurp, rlidentificaciontipo, imagenrlid, imagenrlfacultades,
						fecharegistro, imagenacreditacion, imagencedulafiscal);

			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();
		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, where, "DatosGobierno line 252 : ", es.toString());
			System.out.println("ERROR getGobiernoSQL "+DatosGobierno.class.getName()+".get() "+es);
			bandera = false;
		} // fin del catch
		catch (Exception e) {
			System.out.println("ERROR getGobierno "+DatosGobierno.class.getName()+".get() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosGobierno.class.getName()+".get() "+es);
			}
		} // fin del finally
		return g;
	} // fin del metodo get

	
	
	public boolean cambiarPersona(String personaDestino, Cliente c) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+DatosGobierno.class.getName()+".cambiarPersona() "+e);
		}
		CallableStatement instruccion;
		boolean resultado = false;

		try {
			instruccion = conex.prepareCall("{call usp_changetipopersona_gobierno(?,?)}");
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
			L.Log(Rutas.rutaCarga, c.getCliente_Id(), "DatosGobierno line 307 : ", exSql.toString());
			System.out.println("ERROR cambiarPersonaGobiernoSQL "+DatosGobierno.class.getName()+".cambiarPersona() "+exSql);
			resultado = false;  // Seteamos el fracaso de la transaccion			
		} catch (Exception es) {
			System.out.println("ERROR cambiarPersonaGobierno "+DatosGobierno.class.getName()+".cambiarPersona() "+es);
			resultado = false; // Seteamos el fracaso de la transaccion
		} finally {
			try {
				if (conex != null) {
					conex.close();
				}
			}catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosGobierno.class.getName()+".cambiarPersona() "+es);
			} // fin del cath
			return resultado;
		} // fin del finally
	}// Fin del metodo cambiarPersona
}
