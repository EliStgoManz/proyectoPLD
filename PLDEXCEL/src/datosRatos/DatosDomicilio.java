/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datosRatos;

import entidad.Cliente;
import entidad.Domicilio;
import entidad.Pais;
import entidad.UsuarioTransitivo;
import utilidades.Rutas;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

/**
 *
 * @author Israel Osiris Garcia
 */

public class DatosDomicilio {
	private Conexion2 cnn = new Conexion2();
	private ResultSet conjuntoResultados;
	boolean bandera;

	public boolean agregarClienteNacional(Domicilio d) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión agregarClienteNacional"+DatosDomicilio.class.getName()+ " "+e);
		}
		Statement instruccion;
		ResultSet conjuntoResultados;
		boolean resultado = false;

		try {
			conex = cnn.getConnection("dbpld");

			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String consulta = "INSERT INTO avdomicilionac(cliente_id, colonia, calle, numexterior, numinterior, codpostal, fecharegistro, imagencomprobantedom) ";

			consulta += " values (";
			consulta += "'" + d.getCliente().getCliente_Id() + "',";
			consulta += "'" + d.getColonia() + "',";
			consulta += "'" + d.getCalle() + "',";
			consulta += "'" + d.getNumexterior() + "',";
			consulta += "'" + d.getNuminterior() + "',";
			consulta += "'" + d.getCodpostal() + "',";
			consulta += "" + "now()" + ",";
			consulta += "'" + d.getImagencomprobantedom() + "')";

			instruccion.executeUpdate(consulta);
			resultado = true;
		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, d.getCliente().getCliente_Id(), "DatosDomicilio line 70 : ", es.toString());
			System.out.println("ERROR sql agregarClienteNacional()"+DatosDomicilio.class.getName()+ " "+es);
			resultado = false;
		} // fin del catch
		catch (Exception e) {
			System.out.println("ERROR conexión agregarClienteNacional()"+DatosDomicilio.class.getName()+ " "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión agregarClienteNacional()"+DatosDomicilio.class.getName()+ " "+es);
			}

		} // fin del finally

		return resultado;
	} // fin del metodo agregar cliente nacional

	public boolean agregarClienteExtranjero(Domicilio d) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e1) {
			System.out.println("ERROR conexión agregarClienteExtranjero()"+DatosDomicilio.class.getName()+ " "+e1);
		}
		Statement instruccion;
		ResultSet conjuntoResultados;
		boolean resultado = false;

		try {
			conex = cnn.getConnection("dbpld");

			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String consulta = "INSERT INTO public.avdomicilioext(cliente_id, pais, estado_prov, ciudad, colonia, calle, numexterior, numinterior, codpostal, fecharegistro, imagencomprobantedom) ";

			consulta += " values (";
			consulta += "'" + UsuarioTransitivo.getCliente().getCliente_Id() + "',";
			consulta += "'" + d.getPais().getPais() + "',";
			consulta += "'" + d.getEstado_prov() + "',";
			consulta += "'" + d.getCiudad() + "',";
			consulta += "'" + d.getColonia() + "',";
			consulta += "'" + d.getCalle() + "',";
			consulta += "'" + d.getNumexterior() + "',";
			consulta += "'" + d.getNuminterior() + "',";
			consulta += "'" + d.getCodpostal() + "',";
			consulta += "" + "now()" + ",";
			consulta += "'" + d.getImagencomprobantedom() + "')";

			instruccion.executeUpdate(consulta);
			resultado = true;
		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, UsuarioTransitivo.getCliente().getCliente_Id(), "DatosDomicilio line 136 : ",es.toString());
			System.out.println("ERROR sql agregarClienteExtranjero()"+DatosDomicilio.class.getName()+ " "+es);
			resultado = false;
		}catch (Exception e) {
			System.out.println("ERROR  agregarClienteExtranjero()"+DatosDomicilio.class.getName()+ " "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión agregarClienteExtranjero()"+DatosDomicilio.class.getName()+ " "+es);
			}
		} // fin del finally

		return resultado;
	} // fin del metodo agregar cliente extranjero

	
	@SuppressWarnings("finally")
	public boolean insertarDomicilio(Domicilio d, String perfilid, String usuarioEdicion) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión insertarDomicilio()"+DatosDomicilio.class.getName()+ " "+e);
		}
		CallableStatement instruccion;
		boolean resultado = false;
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

		SimpleDateFormat formato;
		formato = new SimpleDateFormat("yyyy-MM-dd");

		try {
			instruccion = conex.prepareCall("{call usp_change_domicilio(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			instruccion.setString(1, d.getCliente().getCliente_Id());
			instruccion.setString(2, d.getPais().getPais());
			instruccion.setString(3, d.getEstado_prov());
			if (d.getCiudad() != null && !d.getCiudad().trim().isEmpty()) {
				instruccion.setString(4, d.getCiudad());
			} else {
				instruccion.setString(4, "");
			}
			instruccion.setString(5, d.getColonia());
			instruccion.setString(6, d.getCalle());
			instruccion.setString(7, d.getNumexterior());
			instruccion.setString(8, d.getNuminterior());
			instruccion.setString(9, d.getCodpostal());
			instruccion.setDate(10, sqlDate);
			instruccion.setString(11, d.getImagencomprobantedom());
			instruccion.setInt(12, Integer.parseInt(perfilid));
			instruccion.setString(13, usuarioEdicion);
			instruccion.setString(14, d.getDelegacionMunicipio());

			instruccion.execute();
			resultado = true;
			instruccion.close();
			conex.close();

		} catch (SQLException exSql) {
			System.out.println("ERROR sql insertarDomicilio()"+DatosDomicilio.class.getName()+ " "+exSql);
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, d.getCliente().getCliente_Id(), "DatosDomicilio line 218 : ", exSql.toString());
			// Seteamos el fracaso de la transaccion
			resultado = false;
		} catch (Exception es) {
			System.out.println("ERROR insertarDomicilio()"+DatosDomicilio.class.getName()+ " "+es);
			// Seteamos el fracaso de la transaccion
			resultado = false;
		} finally {
			try {
				if (conex != null) {
					conex.close();
				}
			}catch (final SQLException es) {
				System.out.println("ERROR cerrar conexión insertarDomicilio()"+DatosDomicilio.class.getName()+ " "+es);
			} // fin del cath
			return resultado;
		} // fin del finally

	}

	public Domicilio get(String where) {
		Connection conex = null;
		Statement instruccion;
		boolean hayDatos = false;
		Domicilio d = null;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "	SELECT cliente_id, pais, estado_prov, ciudad, colonia, calle, numexterior, numinterior, codpostal, fecharegistro, imagencomprobantedom, delegacion "
					+ "	FROM avdomicilioext ";

			if (where != null) {
				if (!where.isEmpty()) {
					consulta += " WHERE " + where;
				}
			}

			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			while (conjuntoResultados.next()) {

				String cliente_id = conjuntoResultados.getString("cliente_id");
				String pais = conjuntoResultados.getString("pais");
				String estado_prov = conjuntoResultados.getString("estado_prov");
				String ciudad = conjuntoResultados.getString("ciudad");
				String colonia = conjuntoResultados.getString("colonia");
				String calle = conjuntoResultados.getString("calle");
				String numexterior = conjuntoResultados.getString("numexterior");
				String numinterior = conjuntoResultados.getString("numinterior");
				String codpostal = conjuntoResultados.getString("codpostal");
				String fecharegistro = conjuntoResultados.getString("fecharegistro");
				String imagencomprobantedom = conjuntoResultados.getString("imagencomprobantedom");
				String delegacion = conjuntoResultados.getString("delegacion");

				Cliente c = new Cliente();
				c.setCliente_Id(cliente_id);

				Pais p = new Pais();
				p.setPais(pais);

				d = new Domicilio();
				d.setCliente(c);
				d.setPais(p);
				d.setEstado_prov(estado_prov);
				d.setCiudad(ciudad);
				d.setColonia(colonia);
				d.setCalle(calle);
				d.setNumexterior(numexterior);
				d.setNuminterior(numinterior);
				d.setCodpostal(codpostal);
				d.setFecharegistro(fecharegistro);
				d.setImagencomprobantedom(imagencomprobantedom);
				d.setDelegacionMunicipio(delegacion);

				hayDatos = true;

			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();
		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, where, "DatosDomicilio line 325 : ", es.toString());
			System.out.println("ERROR sql get()"+DatosDomicilio.class.getName()+ " "+es);
			bandera = false;
		}catch (Exception e) {
			System.out.println("ERROR get()()"+DatosDomicilio.class.getName()+ " "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión get()"+DatosDomicilio.class.getName()+ " "+es);
			}
		} // fin del finally
		return d;
	} // fin del metodo get

}