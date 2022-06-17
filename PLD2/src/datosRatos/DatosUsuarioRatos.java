/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datosRatos;

import entidad.Cliente;
import entidad.EstatusUsuario;
import entidad.Perfil;
import entidad.RepresentantePLD;
import entidad.PersonaMoral;
import entidad.Supervisor;
import entidad.UsuarioSistema;
import entidad.UsuarioTransitivo;
import listaEntidad.ClienteMoral;
import utilidades.Rutas;

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
public class DatosUsuarioRatos {

	// cliente_id character(15) COLLATE pg_catalog."default",

	private String tipoUsuario = "";
	private final int ESTATUS_ACTIVO = 1;
	private final int ESTATUS_INACTIVO = 2;
	boolean bandera;
	private Conexion2 cnn = new Conexion2();
	private ResultSet conjuntoResultados;

	public DatosUsuarioRatos() {
	}

	
	public String getCorreoEjecutivo(String idcliente) {
		Connection conex = null;
		Statement instruccion;
		String correos = "";

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "select correo from varusuarios  as vu inner join varusuariotransitorio as"
					+ " vut on vut.ejecutivo=vu.numero_interno where vut.idcliente='" + idcliente + "';";

			conjuntoResultados = instruccion.executeQuery(consulta);

			if (conjuntoResultados.next()) {
				correos = conjuntoResultados.getString("correo") + ",";
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

		} catch (SQLException es) {
			DatosCrearLog L = new DatosCrearLog();
			L.Log(Rutas.rutaCarga, idcliente, "DatosUsuarioRatos line 87 : ", es.toString());
			System.out.println("ERROR buscar correo SQL "+DatosUsuarioRatos.class.getName()+".getCorreoEjecutivo() "+es);
		}catch (Exception e) {
			System.out.println("ERROR buscar correo "+DatosUsuarioRatos.class.getName()+".getCorreoEjecutivo() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosUsuarioRatos.class.getName()+".getCorreoEjecutivo() "+es);
			}
		} // fin del finally
		return correos;
	}

	
	
	public boolean getDatosUsuarioExterno(String usuario, HttpSession sesion) {
		Connection conex = null;
		Statement instruccion;
		boolean hayDatos = false;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "SELECT RFC, idCliente, clave_de_acceso, primeravez, email, perfilid from  varusuariotransitorio where RFC = '"
					+ usuario + "' ";
			consulta += " LIMIT 1";
			
			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			while (conjuntoResultados.next()) {
				UsuarioTransitivo ut = new UsuarioTransitivo();
				ut.setClave_de_acceso(conjuntoResultados.getString("clave_de_acceso"));
				ut.setRfc(conjuntoResultados.getString("RFC"));
				/** CREACIÃ“N DEL OBJETO DE CLIENTE **/
				Cliente cliente = new Cliente();
				cliente.setCliente_Id(conjuntoResultados.getString("idCliente"));
				ut.setCliente(cliente);
				ut.setPrimeraVez(conjuntoResultados.getBoolean("primeraVez"));
				ut.setEmail(conjuntoResultados.getString("email"));
				int perfilId = conjuntoResultados.getInt("perfilid");
				sesion.setAttribute("tipo", "T");
				sesion.setAttribute("usuario", ut);
				sesion.setAttribute("user", cliente.getCliente_Id());
				sesion.setAttribute("id", ut.getRfc());
				sesion.setAttribute("pass", ut.getClave_de_acceso());
				sesion.setAttribute("perfilId", perfilId);

				hayDatos = true;

			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

		} catch (SQLException es) {
			System.out.println("ERROR getDatos usuario SQL "+DatosUsuarioRatos.class.getName()+".getDatosUsuarioExterno() "+es);
			bandera = false;
		}catch (Exception e) {
			System.out.println("ERROR getDatos usuario "+DatosUsuarioRatos.class.getName()+".getDatosUsuarioExterno() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosUsuarioRatos.class.getName()+".getDatosUsuarioExterno() "+es);
			}
		} // fin del finally
		return hayDatos;
	}// fin metodo getDatosUsauriosExternos

	
	
	public boolean getDatosUsuarioInterno(String u, HttpSession sesion) {
		Connection conex = null;
		Statement instruccion;
		boolean hayDatos = false;

		try {
			// conex = cn.getConnection("dbpld");
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "SELECT numero_interno, usuario, clave_de_acceso, perfilid, apellido_y_nombres, estatususuariosid, supervisor, correo, primeravez "
					+ "	FROM varusuarios where usuario = '" + u + "'";

			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			if (conjuntoResultados.next()) {
				String numero_interno = conjuntoResultados.getString("numero_interno");
				String usuario = conjuntoResultados.getString("usuario");
				String clave_de_acceso = conjuntoResultados.getString("clave_de_acceso");
				String apellido_y_nombres = conjuntoResultados.getString("apellido_y_nombres");
				Perfil perfil = new Perfil();
				perfil.setPerfilId(conjuntoResultados.getInt("perfilid"));
				EstatusUsuario estatus = new EstatusUsuario();
				estatus.setEstatusUsuarioId(conjuntoResultados.getInt("estatususuariosid"));
				String supervisor = conjuntoResultados.getString("supervisor");
				String correo = conjuntoResultados.getString("correo");
				boolean primeraVez = conjuntoResultados.getBoolean("primeravez");

				Supervisor s = new Supervisor(supervisor);

				UsuarioSistema us = new UsuarioSistema();
				us.setNumero_interno(numero_interno);
				us.setUsuario(usuario);
				us.setClave_de_acceso(clave_de_acceso);
				us.setApellido_y_nombres(apellido_y_nombres);
				us.setPerfilid(perfil);
				us.setEstatus(estatus);
				us.setSupervisor(s);
				us.setCorreo(correo);
				us.setPrimeraVez(primeraVez);

				sesion.setAttribute("tipo", "S");
				sesion.setAttribute("usuario", us);
				sesion.setAttribute("user", us.getUsuario());
				sesion.setAttribute("id", usuario);
				sesion.setAttribute("pass", clave_de_acceso);
				sesion.setAttribute("perfilId", perfil.getPerfilId().toString());

				getPermisosUsuario(sesion);

				hayDatos = true;

			} // fin del while
			conjuntoResultados.close();
			instruccion.close();
			conex.close();
		} catch (SQLException es) {
			System.out.println("ERROR getDatos usuario SQL "+DatosUsuarioRatos.class.getName()+".getDatosUsuarioInterno() "+es);
			bandera = false;
		}catch (Exception e) {
			System.out.println("ERROR getDatos usuario "+DatosUsuarioRatos.class.getName()+".getDatosUsuarioInterno() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosUsuarioRatos.class.getName()+".getDatosUsuarioInterno() "+es);
			}
		} // fin del finally
		return hayDatos;
	}// fin del mÃ©todo getDatosUsuarioExterno

	
	
	public boolean getPermisosUsuario(HttpSession sesion) {
		Connection conex = null;
		Statement instruccion;
		boolean hayDatos = false;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String perfilId = "";
			String consulta = "";
			try {
				perfilId = sesion.getAttribute("perfilId").toString();
				consulta = "SELECT perfilid, descripcion, prospectos, captura, verificacion, avisos, usuarios, perfiles "
						+ " FROM varperfilusuario where perfilid = " + perfilId + " LIMIT 1";
			} catch (Exception es) {
				System.out.println("ERROR varPerfilUsuario SQL "+DatosUsuarioRatos.class.getName()+".getPermisosUsuario() "+es);
			}
			
			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			while (conjuntoResultados.next()) {
				String descripcion = conjuntoResultados.getString("descripcion");
				String prospectos = conjuntoResultados.getString("prospectos");
				String captura = conjuntoResultados.getString("captura");
				String verificacion = conjuntoResultados.getString("verificacion");
				String avisos = conjuntoResultados.getString("avisos");
				String usuarios = conjuntoResultados.getString("usuarios");
				String perfiles = conjuntoResultados.getString("perfiles");

				Perfil perfil = new Perfil(Integer.parseInt(perfilId), descripcion, prospectos, captura, verificacion,
						avisos, usuarios, perfiles);
				sesion.setAttribute("perfil", perfil);
				hayDatos = true;
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

		} catch (SQLException es) {
			System.out.println("ERROR varPerfilUsuario SQL "+DatosUsuarioRatos.class.getName()+".getPermisosUsuario() "+es);
			bandera = false;
		}catch (Exception e) {
			System.out.println("ERROR varPerfilUsuario "+DatosUsuarioRatos.class.getName()+".getPermisosUsuario() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosUsuarioRatos.class.getName()+".getPermisosUsuario() "+es);
			}
		} // fin del finally
		return hayDatos;
	}// fin del mÃ©todo getDatosUsuarioExterno

	
	public boolean cambiaContrasena(String claveNueva, String tipo, String usuario) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+DatosUsuarioRatos.class.getName()+".cambiaContrasena() "+e);
		}
		
		Statement instruccion;
		boolean resultado = false;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "";
			switch (tipo) {
			case "T":
				consulta = "UPDATE varUsuarioTransitorio set primeravez = false, clave_de_acceso = '" + claveNueva
						+ "' WHERE rfc = '" + usuario + "'";
				break;
			case "S":
				consulta = "UPDATE varusuarios set primeravez = false, clave_de_acceso = '" + claveNueva
						+ "' WHERE usuario = '" + usuario + "'";
				break;
			}
			instruccion.executeUpdate(consulta);
			resultado = true;
		} catch (SQLException es) {
			System.out.println("ERROR actualiza contraseña SLQ"+DatosUsuarioRatos.class.getName()+".cambiaContrasena() "+es);
			resultado = false;
		}catch (Exception e) {
			System.out.println("ERROR actualiza contraseña "+DatosUsuarioRatos.class.getName()+".cambiaContrasena() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosUsuarioRatos.class.getName()+".cambiaContrasena() "+es);
			}
		} // fin del finally
		return resultado;
	} // fin del mÃ©todo cambiaContrasena

	
	public boolean existeCorreo(String correo, String usuario) {
		Connection conex = null;
		Statement instruccion;
		boolean hayDatos = false;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "SELECT email FROM varusuariotransitorio WHERE email = '" + correo + "' AND rfc ='"
					+ usuario + "' LIMIT 1";
			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			while (conjuntoResultados.next()) {
				String bandera = conjuntoResultados.getString("email");
				tipoUsuario = "T";
				hayDatos = true;

			} // fin del while

			consulta = "";
			consulta = " SELECT correo FROM varusuarios where correo = '" + correo + "' AND usuario = '" + usuario
					+ "' LIMIT 1 ";
			conjuntoResultados = instruccion.executeQuery(consulta);
			while (conjuntoResultados.next()) {
				String bandera = conjuntoResultados.getString("correo");
				tipoUsuario = "S";
				hayDatos = true;
			}

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

		} catch (SQLException es) {
			System.out.println("ERROR buscar correo SLQ "+DatosUsuarioRatos.class.getName()+".existeCorreo() "+es);
			bandera = false;
		}catch (Exception e) {
			System.out.println("ERROR buscar correo "+DatosUsuarioRatos.class.getName()+".existeCorreo() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosUsuarioRatos.class.getName()+".existeCorreo() "+es);
			}
		} // fin del finally
		return hayDatos;
	}// fin del metodo existeCorreo
	
	

	public boolean actualizaContrasena(String claveNueva, String email, String usuario) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+DatosUsuarioRatos.class.getName()+".actualizaContrasena() "+e);
		}
		
		Statement instruccion;
		ResultSet conjuntoResultados;
		boolean resultado = false;
		String consulta = "";

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			bandera = existeCorreo(email, usuario);
			if (tipoUsuario.equals("T")) {
				// se trata de un usuario transitorio
				consulta = "UPDATE varUsuarioTransitorio set primeravez = true, clave_de_acceso = '" + claveNueva
						+ "' WHERE email = '" + email + "' AND rfc ='" + usuario + "'";
			} else if (tipoUsuario.equals("S")) {
				// Se trata de un usuario de sistema
				consulta = "update varusuarios set primeravez = true, clave_de_acceso = '" + claveNueva
						+ "' WHERE correo = '" + email + "' AND usuario = '" + usuario + "'";

			}

			instruccion.executeUpdate(consulta);
			resultado = true;
		} catch (SQLException es) {
			System.out.println("ERROR actualiza contraseña SQL "+DatosUsuarioRatos.class.getName()+".actualizaContrasena() "+es);
			resultado = false;
		}catch (Exception e) {
			System.out.println("ERROR actualiza contraseña "+DatosUsuarioRatos.class.getName()+".actualizaContrasena() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosUsuarioRatos.class.getName()+".actualizaContrasena() "+es);
			}
		} // fin del finally
		return true;
	} // fin del metodo actualzicion de contraseÃ±a

	
	
	public boolean existeRFC() {
		Connection conex = null;
		Statement instruccion;
		boolean hayDatos = false;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "SELECT RFC from  varusuariotransitorio where RFC = '" + UsuarioTransitivo.getRfc()
					+ "' ";
			consulta += " LIMIT 1";
			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			while (conjuntoResultados.next()) {
				String rfc = conjuntoResultados.getString("RFC");
				hayDatos = true;

			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

		} catch (SQLException es) {
			System.out.println("ERROR busca RFC SQL "+DatosUsuarioRatos.class.getName()+".existeRFC() "+es);
			bandera = false;
		}catch (Exception e) {
			System.out.println("ERROR busca RFC "+DatosUsuarioRatos.class.getName()+".existeRFC() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosUsuarioRatos.class.getName()+".existeRFC() "+es);
			}
		} // fin del finally
		return hayDatos;
	}// fin del mÃ©todo getrfc

	
	
	public boolean existeCorreoProspectos(String correo) {
		Connection conex = null;
		Statement instruccion;
		boolean hayDatos = false;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "SELECT email FROM varusuariotransitorio where email = '" + correo + "' limit 1";
			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			while (conjuntoResultados.next()) {
				String rfc = conjuntoResultados.getString("email");
				hayDatos = true;
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

		} catch (SQLException es) {
			System.out.println("ERROR busca correo SQL "+DatosUsuarioRatos.class.getName()+".existeCorreoProspectos() "+es);
			bandera = false;
		}catch (Exception e) {
			System.out.println("ERROR busca correo "+DatosUsuarioRatos.class.getName()+".existeCorreoProspectos() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosUsuarioRatos.class.getName()+".existeCorreoProspectos() "+es);
			}
		} // fin del finally
		return hayDatos;
	}// fin del mÃ©todo getrfc

	
	
	public boolean existeClienteProspectos(String cliente) {
		Connection conex = null;
		Statement instruccion;
		boolean hayDatos = false;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "SELECT idcliente FROM varusuariotransitorio where idCliente  ='" + cliente + "' limit 1";
			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			while (conjuntoResultados.next()) {
				String rfc = conjuntoResultados.getString("idcliente");
				hayDatos = true;
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

		} catch (SQLException es) {
			System.out.println("ERROR busca clienteProspecto SQL "+DatosUsuarioRatos.class.getName()+".existeClienteProspectos() "+es);
			bandera = false;
		}catch (Exception e) {
			System.out.println("ERROR busca clienteProspecto "+DatosUsuarioRatos.class.getName()+".existeClienteProspectos() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosUsuarioRatos.class.getName()+".existeClienteProspectos() "+es);
			}
		} // fin del finally
		return hayDatos;
	}// fin del mÃ©todo getrfc

	
	
	
	public boolean changeUsuarioSistema(UsuarioSistema u, String nuevaContrasena) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+DatosUsuarioRatos.class.getName()+".changeUsuarioSistema() "+e);
		}
		
		CallableStatement instruccion;
		boolean resultado = false;

		try {
			instruccion = conex.prepareCall("{call usp_change_Usuario(?,?,?,?,?,?,?,?,?)}");
			instruccion.setString(1, u.getUsuario());
			instruccion.setString(2, u.getApellido_y_nombres());
			instruccion.setInt(3, u.getPerfilid().getPerfilId());

			// Existe la posiblidad que desde la pantalla de ediciï¿½n de
			// usuarios este dato este null por eso este tratamiento
			if (u.getEstatus().getEstatusUsuarioId() != null) {
				instruccion.setInt(4, u.getEstatus().getEstatusUsuarioId());
			} else {
				instruccion.setInt(4, ESTATUS_ACTIVO);
			}
			instruccion.setString(5, u.getSupervisor().getUsuario());
			instruccion.setString(6, u.getCorreo());
			instruccion.setString(7, nuevaContrasena);
			instruccion.setString(8, u.getNumero_interno());
			instruccion.setInt(9, u.getId_siseg().getId_siseg());

			instruccion.execute();

			// Seteamos el exito de la transaccion
			resultado = true;

			// Limpieza del ambiente
			instruccion.close();
			conex.close();

		} catch (SQLException exSql) {
			System.out.println("ERROR cambiar usuario SQL "+DatosUsuarioRatos.class.getName()+".changeUsuarioSistema() "+exSql);
			// Seteamos el fracaso de la transaccion
			resultado = false;
		} catch (Exception es) {
			System.out.println("ERROR cambiar usuario "+DatosUsuarioRatos.class.getName()+".changeUsuarioSistema() "+es);
			resultado = false;
		} finally {
			try {
				if (conex != null) {
					conex.close();
				}
			} // fin del try
			catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosUsuarioRatos.class.getName()+".changeUsuarioSistema() "+es);
			} // fin del cath
			return resultado;
		} // fin del finally
	}// Fin del mï¿½todo insertar

	
	
	public UsuarioSistema[] getSupervisor() {
		Connection conex = null;
		Statement instruccion;
		boolean hayDatos = false;
		ArrayList<UsuarioSistema> lista = new ArrayList<>();
		UsuarioSistema[] supervisores = null;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String consulta = "SELECT numero_interno, usuario, u.perfilid, apellido_y_nombres, u.estatususuariosid, supervisor, correo, u.id_siseg "
					+ "	FROM varusuarios as u " + "	JOIN varperfilusuario as p  on u.perfilId = p.perfilid "
					+ "   left join representantes_pld as r on u.id_siseg=r.id_siseg "
					+ "	where  p.descripcion LIKE ('%Supervisor%')  order by u.apellido_y_nombres ";
			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			while (conjuntoResultados.next()) {
				String numero_interno = conjuntoResultados.getString("numero_interno");
				String usuario = conjuntoResultados.getString("usuario");
				Integer perfilId = conjuntoResultados.getInt("perfilid");
				String nombre = conjuntoResultados.getString("apellido_y_nombres");
				Integer estatusId = conjuntoResultados.getInt("estatususuariosid");
				String supervisor = conjuntoResultados.getString("supervisor");
				String correo = conjuntoResultados.getString("correo");
				Integer id_siseg = conjuntoResultados.getInt("id_siseg");

				Perfil p = new Perfil();
				p.setPerfilId(perfilId);

				RepresentantePLD r = new RepresentantePLD();
				r.setId_siseg(id_siseg);

				EstatusUsuario e = new EstatusUsuario();
				e.setEstatusUsuarioId(estatusId);

				Supervisor s = new Supervisor();
				s.setUsuario(supervisor);

				UsuarioSistema us = new UsuarioSistema(numero_interno, usuario, nombre, p, e, s, correo, r);
				lista.add(us);
				hayDatos = true;

			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();
			// Normalizando el arreglo
			if (lista.size() > 0) { // si es que hay supervisores
				supervisores = new UsuarioSistema[lista.size()];
				for (int i = 0; i < lista.size(); i++) {
					supervisores[i] = (UsuarioSistema) lista.get(i);
				}
			}

		} catch (SQLException es) {
			System.out.println("ERROR get supervisores SLQ "+DatosUsuarioRatos.class.getName()+".getSupervisor() "+es);
			bandera = false;
		}catch (Exception e1) {
			System.out.println("ERROR get supervisores "+DatosUsuarioRatos.class.getName()+".getSupervisor() "+e1);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosUsuarioRatos.class.getName()+".getSupervisor() "+es);
			}
		} // fin del finally
		return supervisores;
	}// fin del mï¿½teodo getSupervisor

	
	
	
	
	/**
	 * Retorna una lista de todos los usuarios del sistema, esta pantalla
	 * pertenece a usuarios.jsp
	 * 
	 * @return
	 */
	public UsuarioSistema[] getList(String where) {
		Connection conex = null;
		Statement instruccion;
		boolean hayDatos = false;
		ArrayList<UsuarioSistema> lista = new ArrayList<>();
		UsuarioSistema[] usuarios = null;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String consulta = "	select u.usuario, u.apellido_y_nombres, p.descripcion as Perfil, e.descripcion as Estatus, uu.usuario as Supervisor, uu.apellido_y_nombres as SuperNombre, u.perfilId, u.estatususuariosid, u.correo,u.id_siseg, r.nombre as nombrerep "
					+ " from varusuarios as u  " + "	JOIN varperfilusuario as p on u.perfilid = p.perfilid "
					+ "	JOIN varestatususuario as e on u.estatususuariosid = e.estatususuariosid "
					+ "	left join varusuarios as uu on u.supervisor = uu.usuario "
					+ "   left join representantes_pld as r on u.id_siseg=r.id_siseg ";

			if (where != null) {
				if (!where.isEmpty()) {
					consulta += " WHERE " + where;
				}
			}

			consulta += " ORDER BY estatus, usuario";

			System.out.println("consulta usuarios: " + consulta);
			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			while (conjuntoResultados.next()) {

				String usuario = conjuntoResultados.getString("usuario");
				String nombre = conjuntoResultados.getString("apellido_y_nombres");
				String perfil = conjuntoResultados.getString("perfil");
				String estatusUsuario = conjuntoResultados.getString("estatus");
				String superUsuario = conjuntoResultados.getString("supervisor"); // como
																					// usuario
				String superNomnbre = conjuntoResultados.getString("supernombre");
				String correo = conjuntoResultados.getString("correo");

				int perfilId = conjuntoResultados.getInt("perfilId");
				String nombrerep = conjuntoResultados.getString("nombrerep");
				if (nombrerep == null) {
					nombrerep = "";
				}
				int id_siseg = conjuntoResultados.getInt("id_siseg");

				int estatusUsuarioId = conjuntoResultados.getInt("estatususuariosid");

				Perfil p = new Perfil(perfilId, perfil);
				RepresentantePLD r = new RepresentantePLD(id_siseg, nombrerep);
				Supervisor s = new Supervisor(superUsuario, superNomnbre);
				EstatusUsuario e = new EstatusUsuario(estatusUsuarioId, estatusUsuario);

				UsuarioSistema us = new UsuarioSistema();
				us.setUsuario(usuario);
				us.setApellido_y_nombres(nombre);
				us.setPerfilid(p);
				us.setEstatus(e);
				us.setSupervisor(s);
				us.setCorreo(correo);
				us.setId_siseg(r);

				lista.add(us);
				hayDatos = true;

			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

			// Normalizando el arreglo
			if (lista.size() > 0) { // si es que hay supervisores
				usuarios = new UsuarioSistema[lista.size()];
				for (int i = 0; i < lista.size(); i++) {
					usuarios[i] = (UsuarioSistema) lista.get(i);
				}
			} else {
				usuarios = null;
			}

		} catch (SQLException es) {
			System.out.println("ERROR get usuarios SQL "+DatosUsuarioRatos.class.getName()+".getList() "+es);
			bandera = false;
		} // fin del catch
		catch (Exception e1) {
			System.out.println("ERROR get usuarios  "+DatosUsuarioRatos.class.getName()+".getList() "+e1);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosUsuarioRatos.class.getName()+".getList() "+es);
			}
		} // fin del finally
		return usuarios;
	}// fin del mï¿½teodo getSupervisor

	
	
	public String[] getListaUsuariosPLD() {
		Connection conex = null;
		Statement instruccion;
		ArrayList lista = new ArrayList();
		String[] listaUsuarios = null;
		String PERFIL_PLD = "2";

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = " select numero_interno from varusuarios where perfilid = " + PERFIL_PLD
					+ " AND estatususuariosid = '1' order by numero_interno ";
			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			while (conjuntoResultados.next()) {
				String usuario = conjuntoResultados.getString("numero_interno");
				lista.add(usuario);
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

			if (lista.size() > 0) {
				listaUsuarios = new String[lista.size()];
				for (int i = 0; i < lista.size(); i++) {
					listaUsuarios[i] = lista.get(i).toString();
				}
			}

		} catch (SQLException es) {
			System.out.println("ERROR getlista usuarios SQL "+DatosUsuarioRatos.class.getName()+".getListaUsuariosPLD() "+es);
			bandera = false;
		}catch (Exception e) {
			System.out.println("ERROR getlista usuarios "+DatosUsuarioRatos.class.getName()+".getListaUsuariosPLD() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosUsuarioRatos.class.getName()+".getListaUsuariosPLD() "+es);
			}
		} // fin del finally
		return listaUsuarios;
	}// fin del mï¿½todo asignarUsuario

	
	
	public String getUltimoUsuarioAsignado() {
		Connection conex = null;
		Statement instruccion;
		String ultimoUsuario = "";

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = " SELECT usuario FROM espUltimoAsignado ";
			conjuntoResultados = instruccion.executeQuery(consulta);

			while (conjuntoResultados.next()) {
				ultimoUsuario = conjuntoResultados.getString("usuario");
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

		} catch (SQLException es) {
			System.out.println("ERROR get usuarios SQL "+DatosUsuarioRatos.class.getName()+".getUltimoUsuarioAsignado() "+es);
			bandera = false;
		}catch (Exception e) {
			System.out.println("ERROR get usuarios "+DatosUsuarioRatos.class.getName()+".getUltimoUsuarioAsignado() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosUsuarioRatos.class.getName()+".getUltimoUsuarioAsignado() "+es);
			}
		} // fin del finally
		return ultimoUsuario;
	}// fin del mï¿½todo existeCorreo

	
	
	public boolean actualizaUltimoUsuarioAsignado(String usuarioAsignado) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+DatosUsuarioRatos.class.getName()+".actualizaUltimoUsuarioAsignado() "+e);
		}
		
		Statement instruccion;
		boolean resultado = false;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "";
			consulta = "UPDATE espUltimoAsignado set usuario = '" + usuarioAsignado + "'";
			instruccion.executeUpdate(consulta);
			resultado = true;
		} catch (SQLException es) {
			System.out.println("ERROR actualiza usuarios SQL "+DatosUsuarioRatos.class.getName()+".actualizaUltimoUsuarioAsignado() "+es);
			resultado = false;
		}catch (Exception e) {
			System.out.println("ERROR actualiza usuarios "+DatosUsuarioRatos.class.getName()+".actualizaUltimoUsuarioAsignado() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosUsuarioRatos.class.getName()+".actualizaUltimoUsuarioAsignado() "+es);
			}
		} // fin del finally
		return resultado;
	} // fin del mÃ©todo actualizaUltimoUsuarioAsignado
	
	
	

	public String[] getSupervisores() {
		Connection conex = null;
		Statement instruccion;
		ArrayList lista = new ArrayList();
		String[] listaUsuarios = null;
		String PERFIL_PLD = "4";

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = " select usuario from varusuarios where perfilid = " + PERFIL_PLD + "  order by usuario ";
			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			while (conjuntoResultados.next()) {
				String usuario = conjuntoResultados.getString("usuario");
				lista.add(usuario);
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

			if (lista.size() > 0) {
				listaUsuarios = new String[lista.size()];
				for (int i = 0; i < lista.size(); i++) {
					listaUsuarios[i] = lista.get(i).toString();
				}
			}

		} catch (SQLException es) {
			System.out.println("ERROR lista supervisores SQL "+DatosUsuarioRatos.class.getName()+".getSupervisores() "+es);
			bandera = false;
		}catch (Exception e) {
			System.out.println("ERROR lista supervisores "+DatosUsuarioRatos.class.getName()+".getSupervisores() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosUsuarioRatos.class.getName()+".getSupervisores() "+es);
			}
		} // fin del finally
		return listaUsuarios;
	}// fin del mï¿½todo asignarUsuario
	
	

	public boolean eliminar(String usuario) {
		Connection conex = null;
		try {
			conex = cnn.getConnection("dbpld");
		} catch (Exception e) {
			System.out.println("ERROR conexión "+DatosUsuarioRatos.class.getName()+".eliminar() "+e);
		}
		
		Statement instruccion;
		boolean resultado = false;

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = " update varusuarios set estatususuariosid = '2'";
			consulta += " where  usuario = '" + usuario + "'";

			instruccion.executeUpdate(consulta);
			resultado = true;
		} catch (SQLException es) {
			System.out.println("ERROR actualiza estatus usuario SQL "+DatosUsuarioRatos.class.getName()+".eliminar() "+es);
			resultado = false;
		}catch (Exception e) {
			System.out.println("ERROR actualiza estatus usuario "+DatosUsuarioRatos.class.getName()+".eliminar() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosUsuarioRatos.class.getName()+".eliminar() "+es);
			}
		} // fin del finally
		return resultado;
	} // fin del mÃ©todo cambiaContrasena
	
	
	
	public String getcorreosPorValidar(String idcliente) {
		Connection conex = null;
		Statement instruccion;
		String correos = "";
		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// Esta consulta manda los 3 correos correo ejecutivo de tmk, correo
			// supervisor tkm y correo cliente (Ejecutivo PLD)
			// String consulta = "select correo from varusuarios as a join
			// varusuariotransitorio as b on a.numero_interno = b.ejecutivo " +
			// "where b.idcliente = '" + idcliente + "' union select correo from
			// varusuarios where usuario in (select supervisor from varusuarios
			// " +
			// "where numero_interno = (select ejecutivo from
			// varusuariotransitorio where idcliente = '" + idcliente + "')) " +
			// "union select correo from varusuarios where numero_interno in
			// (select usuarioasignado from avcliente " +
			// "where cliente_id = '" + idcliente + "')" ;
			// Esta consulta por cambios manda al correo cliente (Ejecutivo PLD)
			String consulta = "select correo from varusuarios where numero_interno in (select usuarioasignado from avcliente "
					+ "where cliente_id = '" + idcliente + "')";

			conjuntoResultados = instruccion.executeQuery(consulta);

			while (conjuntoResultados.next()) {
				correos += conjuntoResultados.getString("correo") + ",";
			} // fin del while
			System.out.println("correos a enviar cliente por validar " + correos);
			if (!correos.isEmpty()) {
				correos = correos.substring(0, correos.length() - 1);
			}
			conjuntoResultados.close();
			instruccion.close();
			conex.close();

		} catch (SQLException es) {
			System.out.println("ERROR busca correo SQL "+DatosUsuarioRatos.class.getName()+".getcorreosPorValidar() "+es);
		}catch (Exception e) {
			System.out.println("ERROR busca correo "+DatosUsuarioRatos.class.getName()+".getcorreosPorValidar() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosUsuarioRatos.class.getName()+".getcorreosPorValidar() "+es);
			}
		} // fin del finally
		return correos;
	}// fin del mï¿½todo getList
	
	

	
	public String getcorreosValidacion(String idcliente) {
		Connection conex = null;
		Statement instruccion;
		String correos = "";

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "select correo from varusuarios as a join varusuariotransitorio as b on a.numero_interno = b.ejecutivo "
					+ "where b.idcliente = '" + idcliente
					+ "' union select correo from varusuarios where usuario in (select supervisor from varusuarios "
					+ "where numero_interno = (select ejecutivo from varusuariotransitorio where idcliente = '"
					+ idcliente + "'))";

			conjuntoResultados = instruccion.executeQuery(consulta);

			while (conjuntoResultados.next()) {
				correos += conjuntoResultados.getString("correo") + ",";
			} // fin del while
			System.out.println("correos a enviar cliente validado " + correos);
			if (!correos.isEmpty()) {
				correos = correos.substring(0, correos.length() - 1);
			}
			conjuntoResultados.close();
			instruccion.close();
			conex.close();

		} catch (SQLException es) {
			System.out.println("ERROR get correo SQL "+DatosUsuarioRatos.class.getName()+".getcorreosValidacion() "+es);
		}catch (Exception e) {
			System.out.println("ERROR get correo "+DatosUsuarioRatos.class.getName()+".getcorreosValidacion() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosUsuarioRatos.class.getName()+".getcorreosValidacion() "+es);
			}
		} // fin del finally
		return correos;
	}// fin del mï¿½todo getList

	
	
	public String getcorreosInValidacion(String ejecutivoPLD) {
		Connection conex = null;
		Statement instruccion;
		String correos = "";

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String consulta = "select correo from varusuarios where usuario = '" + ejecutivoPLD + "'";
			conjuntoResultados = instruccion.executeQuery(consulta);

			while (conjuntoResultados.next()) {
				correos += conjuntoResultados.getString("correo");
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();

		} catch (SQLException es) {
			System.out.println("ERROR get correo SQL "+DatosUsuarioRatos.class.getName()+".getcorreosInValidacion() "+es);
		}catch (Exception e) {
			System.out.println("ERROR get correo "+DatosUsuarioRatos.class.getName()+".getcorreosInValidacion() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosUsuarioRatos.class.getName()+".getcorreosInValidacion() "+es);
			}
		} // fin del finally

		return correos;
	}// fin del mï¿½todo getList

	
	
	/**
	 * Obtiene el id de usuario del administrador para ponerlo como default
	 * asignado cuando se guarda por primera vez un cliente
	 * 
	 * @return id del usuario administrador
	 */
	public String getUsuarioDefault() {
		Connection conex = null;
		Statement instruccion;
		ArrayList lista = new ArrayList();
		String usuario = "";
		String PERFIL_ADMINISTRADOR = "1";

		try {
			conex = cnn.getConnection("dbpld");
			// crea objeto Statement para consultar la base de datos
			instruccion = conex.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String consulta = " select numero_interno from varusuarios where perfilid = " + PERFIL_ADMINISTRADOR
					+ " AND estatususuariosid = '1' order by numero_interno ";
			conjuntoResultados = instruccion.executeQuery(consulta);
			bandera = true;

			if (conjuntoResultados.next()) {
				usuario = conjuntoResultados.getString("numero_interno");
			} // fin del while

			conjuntoResultados.close();
			instruccion.close();
			conex.close();
		} catch (SQLException es) {
			System.out.println("ERROR get num imterno usuario SQL "+DatosUsuarioRatos.class.getName()+".getUsuarioDefault() "+es);
			bandera = false;
		}catch (Exception e) {
			System.out.println("ERROR get num imterno usuario "+DatosUsuarioRatos.class.getName()+".getUsuarioDefault() "+e);
		} finally {
			try {
				if (conex != null)
					conex.close();
			} catch (SQLException es) {
				System.out.println("ERROR cerrar conexión "+DatosUsuarioRatos.class.getName()+".getUsuarioDefault() "+es);
			}
		} // fin del finally

		return usuario;
	}
}// fin clase
