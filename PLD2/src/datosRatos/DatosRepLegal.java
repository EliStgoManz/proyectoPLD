/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datosRatos;

/**
 *
 * @author Aldo Ulises
 */

import entidad.Cliente;
import entidad.RepLegal;
import entidad.Representante;
import entidad.TipoIdentificacion;
import utilidades.Rutas;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DatosRepLegal {
	private Conexion2 cnn = new Conexion2();
	private ResultSet conjuntoResultados;
	boolean bandera;

	
	
	public boolean changeRepLegal(RepLegal b, String perfilid, String usuarioEdicion) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+DatosRepLegal.class.getName()+".changeRepLegal() "+e);
		}
		
		CallableStatement instruccion;
		boolean resultado = false;

		// rellenos
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		java.sql.Date sqlFechaNacimiento = null;
		java.sql.Date sqlFechaNa = null;

		SimpleDateFormat formato;
		formato = new SimpleDateFormat("yyyy-MM-dd");
		String fechaNacimiento = b.getRLFechaNacimiento();
		String fechaZeros = "2001-01-01";

		try {
			instruccion = conex.prepareCall("{call usp_change_replegal(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			instruccion.setString(1, b.getCliente().getCliente_Id());
			instruccion.setInt(2, b.getNrorep());
			instruccion.setString(3, b.getRLNombre());
			instruccion.setString(4, b.getRLApellidoPaterno());
			instruccion.setString(5, b.getRLApellidoMaterno());
			if (b.getRLFechaNacimiento() != null && !b.getRLFechaNacimiento().isEmpty()
					&& !b.getRLFechaNacimiento().equals("")) {
				java.util.Date utilFechaNacimiento = formato.parse(fechaNacimiento);
				sqlFechaNacimiento = new java.sql.Date(utilFechaNacimiento.getTime());
				instruccion.setDate(6, sqlFechaNacimiento);
			} else {
				java.util.Date utilFechaNa = formato.parse(fechaZeros);
				sqlFechaNa = new java.sql.Date(utilFechaNa.getTime());
				instruccion.setDate(6, sqlFechaNa);
			}

			instruccion.setString(7, b.getRLRFC());
			instruccion.setString(8, b.getRLCURP());
			// instruccion.setString(9, b.getRLIdentifica());
			if (b.getIdentifica_id().getIdentifica_id() != null && !b.getIdentifica_id().getIdentifica_id().isEmpty()) {
				instruccion.setString(9, b.getIdentifica_id().getIdentifica_id());
			} else {
				instruccion.setString(9, "14");
			}
			instruccion.setString(10, b.getRLAutoridadEmiteId());
			instruccion.setString(11, b.getRLNumeroID());
			instruccion.setString(12, b.getRLIdentificacionTipo());
			instruccion.setString(13, b.getImagenRLID());
			instruccion.setString(14, b.getImagenRLPoderNotarial());
			instruccion.setInt(15, Integer.parseInt(perfilid));
			instruccion.setString(16, usuarioEdicion);

			instruccion.execute();

			// Seteamos el exito de la transaccion
			resultado = true;

			// Limpieza del ambiente
			instruccion.close();
			conex.close();
		} catch (SQLException exSql) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, b.getCliente().getCliente_Id(), "DatosRepLegal line 103 : ", exSql.toString());
			System.out.println("ERROR cammbio representanteLegal SQL "+DatosRepLegal.class.getName()+".changeRepLegal() "+exSql);
			// Seteamos el fracaso de la transaccion
			resultado = false;
		} catch (Exception es) {
			System.out.println("ERROR cambio representante legal "+DatosRepLegal.class.getName()+".changeRepLegal() "+es);
			// Seteamos el fracaso de la transaccion
			resultado = false;
		} finally {
			try {
				if (conex != null) {
					conex.close();
				}
			}catch (SQLException es) {
				System.out.println("ERROR cerrrar conexión "+DatosRepLegal.class.getName()+".changeRepLegal() "+es);
			} // fin del cath
			return resultado;
		} // fin del finally
	}// Fin del metodo insertar

	
	
	
	public boolean NOMExist(String idCliente, String NOM, String PAT, String MAT) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e1) {
			System.out.println("ERROR conexión "+DatosRepLegal.class.getName()+".NOMExist() "+e1);
		}
		
		Statement instruccion;
		boolean resultado = false;
		String consulta = "";
		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			consulta = "SELECT count(nrorep) as nrorep FROM replegal WHERE cliente_id = '" + idCliente
					+ "' AND rlnombre = '" + NOM + "'" + "' AND rlapellidopaterno = '" + PAT + "'"
					+ "' AND rlapellidomaterno = '" + MAT + "'";
			conjuntoResultados = instruccion.executeQuery(consulta);
			if (conjuntoResultados.next()) {
				if (conjuntoResultados.getInt("nrorep") == 1)
					resultado = true;
			}

		} catch (Exception e) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, idCliente, "DatosRepLegal line 161 : ", e.toString());
			System.out.println("ERROR existe representanteLegal "+DatosRepLegal.class.getName()+".NOMExist() "+e);
		} finally {
			try {
				conex.close();
			} catch (Exception ex) {
				System.out.println("ERROR cerrar conexión "+DatosRepLegal.class.getName()+".NOMExist() "+ex);
			}
		}
		return resultado;
	}

	
	public RepLegal getRepLegal(String where) {
		Connection conex = null;
		Statement instruccion;
		boolean hayDatos = false;
		RepLegal b = null;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String consulta = "SELECT cliente_id, nrorep, rlnombre, rlapellidopaterno, rlapellidomaterno, rlfechanacimiento, rlrfc, rlcurp, rlidentifica_id, rlautoridademiteid, rlnumeroid, rlidentificaciontipo, imagenrlid, imagenrlpodernotarial  "
					+ "	FROM replegal";

			if (where != null) {
				if (!where.isEmpty()) {
					consulta += " WHERE " + where;
				}
			}

			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			while (conjuntoResultados.next()) {
				String cliente_id = conjuntoResultados.getString("cliente_id");
				int nrorep = conjuntoResultados.getInt("nrorep");
				String rlnombre = conjuntoResultados.getString("rlnombre").trim();
				String rlapellidopaterno = conjuntoResultados.getString("rlapellidopaterno").trim();
				String rlapellidomaterno = conjuntoResultados.getString("rlapellidomaterno").trim();
				String rlfechanacimiento = conjuntoResultados.getString("rlfechanacimiento").trim();
				String rlrfc = conjuntoResultados.getString("rlrfc").trim();
				String rlcurp = conjuntoResultados.getString("rlcurp").trim();
				String identifica_id = conjuntoResultados.getString("rlidentifica_id").trim();
				String rlautoridademiteid = conjuntoResultados.getString("rlautoridademiteid").trim();
				String rlnumeroid = conjuntoResultados.getString("rlnumeroid").trim();
				String rlidentificaciontipo = conjuntoResultados.getString("rlidentificaciontipo").trim();
				String imagenrlid = conjuntoResultados.getString("imagenrlid").trim();
				String imagenrlpodernotarial = conjuntoResultados.getString("imagenrlpodernotarial").trim();
				Cliente c = new Cliente();
				c.setCliente_Id(cliente_id);
				TipoIdentificacion identificacion = new TipoIdentificacion(identifica_id);

				b = new RepLegal(c, nrorep, rlnombre, rlapellidopaterno, rlapellidomaterno, rlfechanacimiento, rlrfc,
						rlcurp, identificacion, rlautoridademiteid, rlnumeroid, rlidentificaciontipo, imagenrlid,
						imagenrlpodernotarial);

			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();
		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, where, "DatosRepLegal line 237 : ", es.toString());
			System.out.println("ERROR get representante legal SLQ "+DatosRepLegal.class.getName()+".getRepLegal() "+es);
			bandera = false;
		}catch (Exception e) {
			System.out.println("ERROR get representante legal "+DatosRepLegal.class.getName()+".getRepLegal() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosRepLegal.class.getName()+".getRepLegal() "+es);
			}
		} // fin del finally
		return b;
	} // fin del metodo get

	
	
	public Integer getNumeroRepresentantes(String idCliente) {
		Connection conex = null;
		Statement instruccion;
		Integer nroRepresentantes = null;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "select  max(nrorep) as nrorep" + " from  replegal where cliente_id ='" + idCliente
					+ "' ";

			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = false;

			while (conjuntoResultados.next()) {
				nroRepresentantes = conjuntoResultados.getInt("nrorep");
				bandera = true;

			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();
		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, idCliente, "DatosRepLegal line 299 : ", es.toString());
			System.out.println("ERROR num representanteLegal SLQ "+DatosRepLegal.class.getName()+".getNumeroRepresentantes() "+es);
			bandera = false;
		}catch (Exception e) {
			System.out.println("ERROR num representanteLegal "+DatosRepLegal.class.getName()+".getNumeroRepresentantes() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosRepLegal.class.getName()+".getNumeroRepresentantes() "+es);
			}
		} // fin del finally
		return nroRepresentantes;
	}

	
	
	public Representante[] getRepList(String idCliente) {
		Connection conex = null;
		Statement instruccion;
		Representante[] listaRepresentante = null;
		ArrayList lista = null;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "SELECT cliente_id, nrorep, "
					+ "COALESCE(rlnombre,'') || ' ' || COALESCE(rlapellidopaterno,'') || ' ' || COALESCE(rlapellidomaterno,'') as nombreCompleto "
					+ "from replegal where cliente_id = '" + idCliente + "' ";
			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;
			lista = new ArrayList();

			while (conjuntoResultados.next()) {
				String cliente_id = conjuntoResultados.getString("cliente_id");
				int nrorep = conjuntoResultados.getInt("nrorep");
				String nombreCompleto = conjuntoResultados.getString("nombreCompleto");
				// String tipoPersonaDesc =
				// conjuntoResultados.getString("tipoPersonaDesc");
				// String tipoPersona =
				// conjuntoResultados.getString("tipoPersona");
				lista.add(new Representante(cliente_id, nrorep, nombreCompleto));
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

			// Normalizando la lista
			if (lista != null) {
				listaRepresentante = new Representante[lista.size()];
				for (int i = 0; i < listaRepresentante.length; i++) {
					listaRepresentante[i] = (Representante) lista.get(i);
				}
			}

		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, idCliente, "DatosRepLegal line 377 : ", es.toString());
			System.out.println("ERROR listaRepresentante SLQ "+DatosRepLegal.class.getName()+".getRepList() "+es);
			bandera = false;
		}catch (Exception e) {
			System.out.println("ERROR listaRepresentante "+DatosRepLegal.class.getName()+".getRepList() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosRepLegal.class.getName()+".getRepList() "+es);
			}
		} // fin del finally
		return listaRepresentante;
	} // fin del metodo get

	
	public boolean limpiaRepresentantes(String idCliente) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e1) {
			System.out.println("ERROR conexión "+DatosRepLegal.class.getName()+".limpiaRepresentantes() "+e1);
		}
		boolean resultado = false;
		String consulta = "";

		try {
			Statement instruccion = conex.createStatement();
			consulta = "DELETE FROM replegal where cliente_id ='" + idCliente + "'";
			instruccion.execute(consulta);
			instruccion.close();
			// Seteamos el exito de la transaccion
			resultado = true;
		} catch (Exception e) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, idCliente, "DatosRepLegal line 422 : ", e.toString());
			System.out.println("ERROR eliminaRepresentante SLQ "+DatosRepLegal.class.getName()+".limpiaRepresentantes() "+e);
		} finally {
			try {
				conex.close();
			} catch (Exception ex) {
				System.out.println("ERROR cerrar conexión "+DatosRepLegal.class.getName()+".limpiaRepresentantes() "+ex);
			}
		}
		// Limpieza del ambiente
		return resultado;
	}

	
	public int getelNoRep(String idCliente, String Nrorep) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e1) {
			System.out.println("ERROR conexión "+DatosRepLegal.class.getName()+".getelNoRep() "+e1);
		}
		
		Statement instruccion;
		int resultado = 0;
		String consulta = "";
		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// if (tipo.compareTo("F") == 0) {
			consulta = "SELECT nrorep FROM replegal WHERE cliente_id = '" + idCliente + "' AND nrorep = '" + Nrorep
					+ "'";
			conjuntoResultados = instruccion.executeQuery(consulta);
			if (conjuntoResultados.next())
				resultado = conjuntoResultados.getInt("nrorep");
			// }
		} catch (Exception e) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, idCliente, "DatosRepLegal line 461 : ", e.toString());
			System.out.println("ERROR numRepresentante "+DatosRepLegal.class.getName()+".getelNoRep() "+e);
		} finally {
			try {
				conex.close();
			} catch (Exception ex) {
				System.out.println("ERROR cerrar conexión "+DatosRepLegal.class.getName()+".getelNoRep() "+ex);
			}
		}
		return resultado;
	}
}
