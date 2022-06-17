/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.sun.jersey.core.util.Base64;
import correo.Correos;
import datosRatos.DatosClienteRaro;
import datosRatos.DatosDomicilio;
import datosRatos.DatosPersonaMoral;
import datosRatos.DatosUsuarioRatos;
import datosRatos.DatosUtilidades;
import entidad.Cliente;
import entidad.Domicilio;
import entidad.Giro;
import entidad.Pais;
import entidad.PersonaMoral;
import entidad.TipoIdentificacion;
import entidad.UsuarioSistema;
import entidad.UsuarioTransitivo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javazoom.upload.MultipartFormDataRequest;
import javazoom.upload.UploadBean;
import javazoom.upload.UploadException;
import javazoom.upload.UploadFile;
import listaEntidad.Coincidencia;
import utilidades.Cadenas;
import utilidades.Fecha;
import utilidades.Mensajes;
import utilidades.Rutas;

/**
 *
 * @author israel.garcia
 */
@WebServlet(name = "ControlVerificacionPersonaMoral", urlPatterns = { "/VerificacionPersonaMoral" })
public class ControlVerificacionPersonaMoral extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 * @throws SQLException 
	 */
	protected void processRequest(HttpServletRequest requestMulti, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		// response.setContentType("text/html;charset=UTF-8");

		String imagenActa = "";
		String imagenCedula = "";
		String imagenComprobanteDomicilio = "";
		String imagenRlId = "";
		String imagenRlCedulafiscal = "";
		String imagenDeclaratoria = "";
		String imagenRlPoderNotarial = "";
		String c = "";

		if (MultipartFormDataRequest.isMultipartFormData(requestMulti)) {

			Cliente cliente = new Cliente();

			// cliente.setCliente_Id(UsuarioTransitivo.getCliente().getCliente_Id());

			/**
			 * GESTIONANDO LA CARGA DE LOS ARCHIVOS
			 */

			UploadBean upBean = new UploadBean();
			MultipartFormDataRequest request = null;
			try {
				request = new MultipartFormDataRequest(requestMulti);
			} catch (UploadException ex) {
				System.out.println("ControlVerificacionPersonaMoral.java requestMulti "+ex.toString());
			}

			Hashtable files = request.getFiles();
			UploadFile file = null;
			String zipFile = null;

			cliente.setCliente_Id(request.getParameter("Cliente_Id"));
			c = request.getParameter("Cliente_Id");
			// DOCUMENTOS GOBIERNO
			// CARGA DEL DOCUMENTO QUE ACREDITA LA EXISTENCIA
//			
//			try {
//				upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id() + Rutas.separador
//						+ Rutas.dirAreditaacion + Rutas.separador);
//				zipFile = request.getParameter("archivoExistencia");
//				
//				if (zipFile.length() > 0) {
//					String extension = "." + new Cadenas().getExtension(file.getFileName());
//					// file.setFileName( Rutas.dirAreditaacion + "_" +
//					// Fecha.getFechaHoraSistema() + ".pdf" ) ;
//					// upBean.store(request, "archivoExistencia");
//					// imagenExistencia = Rutas.rutaCarga + Rutas.separador +
//					// c.getCliente_Id() + Rutas.separador +
//					// Rutas.dirAreditaacion + Rutas.separador +
//					// file.getFileName();
//				}
//			} catch (Exception es) {
//				es.printStackTrace();
//			}

//			try {
//				upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id() + Rutas.separador
//						+ Rutas.dirFacultades + Rutas.separador);
//				file = (UploadFile) files.get("archivofFacultades");
//				if (file.getFileSize() > 0) {
//					// file.setFileName( Rutas.dirFacultades + "_" +
//					// Fecha.getFechaHoraSistema() + ".pdf" ) ;
//					// upBean.store(request, "archivofFacultades");
//					// imagenFFacultades = Rutas.rutaCarga + Rutas.separador +
//					// c.getCliente_Id() + Rutas.separador + Rutas.dirFacultades
//					// + Rutas.separador + file.getFileName();
//				}
//			} catch (Exception es) {
//				es.printStackTrace();
//			}
//			// FIN DOCUMENTOS GOBIERNO

			// CARGA DEl ACTA CONSTITUTIVA
			try {
				upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id() + Rutas.separador
						+ Rutas.dirActaConstitutiva + Rutas.separador);

				zipFile = request.getParameter("archivoActaZip");
				// if ( file.getFileSize() > 0 ){
				if (zipFile.length() > 0) {
					byte[] data = Base64.decode(zipFile);
					File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id()
							+ Rutas.separador + Rutas.dirActaConstitutiva + Rutas.separador + Rutas.dirActaConstitutiva
							+ "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
					try (OutputStream stream = new FileOutputStream(archivoZip)) {
						stream.write(data);

						imagenActa = Renombra(archivoZip, Rutas.dirActaConstitutiva, cliente.getCliente_Id(), ".zip");

					} catch (Exception ss) {
						Mensajes.setMensaje(
								"Ha excedido el tiempo de carga al servidor, por favor intente nuevamente.");
						requestMulti.setAttribute("resultado", Mensajes.mensaje);
						requestMulti.setAttribute("exito", "1");
						requestMulti.getRequestDispatcher("/estatus_clientes.jsp").forward(requestMulti, response);

					}
					// imagenActa = Renombra(archivoZip,
					// Rutas.dirActaConstitutiva, cliente.getCliente_Id(),
					// ".zip" );
				}
			} catch (Exception es) {
				// Mensajes.setMensaje("Se ha excedido el tiempo de carga al
				// servidor 贸n. \n " );
				// requestMulti.setAttribute("resultado", Mensajes.mensaje);
				// requestMulti.setAttribute("exito", "1");
				// requestMulti.getRequestDispatcher("/estatus_clientes.jsp").forward(requestMulti,
				// response);

				System.out.println("ControlVerificacionPersonaMoral.java ActaConstitutiva "+es.toString());
			}

			// CARGA CEDULA FISCAL
			try {
				upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id() + Rutas.separador
						+ Rutas.dirCedula + Rutas.separador);
				zipFile = request.getParameter("archivoCedulaZip");
				if (zipFile.length() > 0) {
					byte[] data = Base64.decode(zipFile);
					File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id()
							+ Rutas.separador + Rutas.dirCedula + Rutas.separador + Rutas.dirCedula + "_"
							+ Fecha.getFechaHoraSistema() + "_tmp.zip");
					try (OutputStream stream = new FileOutputStream(archivoZip)) {
						stream.write(data);
					}
					imagenCedula = Renombra(archivoZip, Rutas.dirCedula, cliente.getCliente_Id(), ".zip");
				}
			} catch (Exception es) {
				System.out.println("ControlVerificacionPersonaMoral.java CedulaFiscal "+es.toString());
			}

			// CARGA COMPROBANTE DE DOMICILIO
			try {
				upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id() + Rutas.separador
						+ Rutas.dirComprobanteDom + Rutas.separador);
				zipFile = request.getParameter("archivoDomicilioZip");
				if (zipFile.length() > 0) {
					byte[] data = Base64.decode(zipFile);
					File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id()
							+ Rutas.separador + Rutas.dirComprobanteDom + Rutas.separador + Rutas.dirComprobanteDom
							+ "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");

					try (OutputStream stream = new FileOutputStream(archivoZip)) {
						stream.write(data);
					}
					imagenComprobanteDomicilio = Renombra(archivoZip, Rutas.dirComprobanteDom, cliente.getCliente_Id(),
							".zip");
				}
			} catch (Exception es) {
				System.out.println("ControlVerificacionPersonaMoral.java ComprobanteDomicilio "+es.toString());
			}

			// CARGA ID REPRESENTANTE LEGAL
			try {
				upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id() + Rutas.separador
						+ Rutas.dirRlIdentificacion + Rutas.separador);
				zipFile = request.getParameter("archivoRlIdentificacionZip");
				if (zipFile.length() > 0) {
					byte[] data = Base64.decode(zipFile);
					File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id()
							+ Rutas.separador + Rutas.dirRlIdentificacion + Rutas.separador + Rutas.dirRlIdentificacion
							+ "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");

					try (OutputStream stream = new FileOutputStream(archivoZip)) {
						stream.write(data);
					}
					imagenRlId = Renombra(archivoZip, Rutas.dirRlIdentificacion, cliente.getCliente_Id(), ".zip");
				}
			} catch (Exception es) {
				System.out.println("ControlVerificacionPersonaMoral.java IdRepresentanteLegal "+es.toString());
			}

//			// CARGA CEDULA FISCAL CELULA FISCAL
//			try {
//				upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id() + Rutas.separador
//						+ Rutas.dirRlCedulaFiscal + Rutas.separador);
//				zipFile = request.getParameter("archivoCedulaRepresentanteZip");
//				if (zipFile.length() > 0) {
//					byte[] data = Base64.decode(zipFile);
//					File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id()
//							+ Rutas.separador + Rutas.dirRlCedulaFiscal + Rutas.separador + Rutas.dirRlCedulaFiscal
//							+ "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
//
//					try (OutputStream stream = new FileOutputStream(archivoZip)) {
//						stream.write(data);
//					}
//					imagenRlCedulafiscal = Renombra(archivoZip, Rutas.dirRlCedulaFiscal, cliente.getCliente_Id(),
//							".zip");
//				}
//			} catch (Exception es) {
//				es.printStackTrace();
//			}

			// CARGA PODER NOTARIAL PODER NOTARIAL
			try {
				upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id() + Rutas.separador
						+ Rutas.dirRlPoderNotarial + Rutas.separador);
				zipFile = request.getParameter("archivoPoderNotarialZip");
				if (zipFile.length() > 0) {
					byte[] data = Base64.decode(zipFile);
					File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id()
							+ Rutas.separador + Rutas.dirRlPoderNotarial + Rutas.separador + Rutas.dirRlPoderNotarial
							+ "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");

					try (OutputStream stream = new FileOutputStream(archivoZip)) {
						stream.write(data);
					}
					imagenRlPoderNotarial = Renombra(archivoZip, Rutas.dirRlPoderNotarial, cliente.getCliente_Id(),
							".zip");
				}
			} catch (Exception es) {

				System.out.println("ControlVerificacionPersonaMoral.java PoderNotarial "+es.toString());
			}

			// CARGA PODER NOTARIAL DECLARATORIA
			try {
				upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id() + Rutas.separador
						+ Rutas.dirDeclaratoria + Rutas.separador);
				zipFile = request.getParameter("archivoDeclaratoriaZip");
				if (zipFile.length() > 0) {
					byte[] data = Base64.decode(zipFile);
					File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id()
							+ Rutas.separador + Rutas.dirDeclaratoria + Rutas.separador + Rutas.dirDeclaratoria + "_"
							+ Fecha.getFechaHoraSistema() + "_tmp.zip");

					try (OutputStream stream = new FileOutputStream(archivoZip)) {
						stream.write(data);
					}
					imagenDeclaratoria = Renombra(archivoZip, Rutas.dirDeclaratoria, cliente.getCliente_Id(), ".zip");
				}
			} catch (Exception es) {

				System.out.println("ControlVerificacionPersonaMoral.java Declaratoria "+es.toString());
			}

			String nodeclaroBeneficiario = request.getParameter("nobeneficiario");
			String siDeclaroBeneficiario = request.getParameter("sibeneficiario");
			String declaroOrigen = request.getParameter("declaroOrigen");

			/**
			 * RECUPERANDO LOS DATOS DE LA PERSONA MORAL
			 */

			String tipoPersona = request.getParameter("tipoPersona");
			String tipoPersonaCambio = request.getParameter("tipoPersonaCambio");

			cliente.setTipoPersona(tipoPersona);
			cliente.setFechaRegistro("");
			cliente.setTelefono(request.getParameter("telefono"));
			Pais paisCliente = new Pais();
			paisCliente.setPais(request.getParameter("telPais"));
			cliente.setPais(paisCliente);
			cliente.seteMail(request.getParameter("correo"));

			int validado = 0;
			String estado = request.getParameter("Estatus");
            String estadoAnterior=request.getParameter("EstatusAnterior");
			String fechaValidado = null;
			if (estado != null && !estado.isEmpty() && estado.equals("A")) { // A
																				// =
																				// VALIDO
				validado = 1; // si no //esta dato nace no se recoje de la forma
								// //se evalua con sp de base de datos
				fechaValidado = request.getParameter("fechaValidacion"); // null
																			// en
																			// definicion
																			// de
																			// dd
				if (fechaValidado.isEmpty()) {
					fechaValidado = null;
				}
				// requestMulti.setAttribute("mensaje", "");
			}
			cliente.setRazonSocial(request.getParameter("razonSocial"));
			cliente.setEstado(estado);
			cliente.setValidado(validado);
			cliente.setFechaValidado(fechaValidado);

			try {
				if (paisCliente.getPais().trim().equals("MX")) {
					cliente.setTipoDomicilio("N");
				} else {
					cliente.setTipoDomicilio("E");
				}
			} catch (Exception es) {
				cliente.setTipoDomicilio("N");
			}

			// DECLARO BENEFICIARIO

			try {
				if (siDeclaroBeneficiario != null) {
					cliente.setDeclaroBeneficiario(1); // indica que si hay
														// beneficiario
				} else {
					cliente.setDeclaroBeneficiario(0); // indica que no hay
														// beneficiario
				}
			} catch (Exception es) {
				cliente.setDeclaroBeneficiario(0); // en caso de error no se
													// declara el beneficiario
			}

			// DECLARO ORIGEN
			try {
				if (declaroOrigen != null)
					cliente.setDeclaroOrigen(1);
			} catch (Exception es) {
				cliente.setDeclaroOrigen(0);
			}

			String usuariovalido = null;
			if (estado != null && !estado.isEmpty()) {
				try {
					HttpSession sesion = requestMulti.getSession();
					UsuarioSistema us = (UsuarioSistema) sesion.getAttribute("usuario");
					String usuario = us.getNumero_interno();
					usuariovalido = usuario;
				} catch (Exception es) {
					System.out.println("ControlVerificacionPersonaMoral.java usuarioValidoNoInterno "+es.toString());

				}

			}

			cliente.setUsuarioValido(usuariovalido);
			cliente.setFechaCorte(request.getParameter("fechacorte")); // null
			if (estado != null && !estado.isEmpty() && estado.equals("A")) {
				cliente.setMensaje("");
			} else {
				cliente.setMensaje(request.getParameter("mensaje")); // null

			}
			cliente.setNotas(request.getParameter("notas")); // null
			cliente.setUsuarioAsignado(request.getParameter("usuarioasignado"));
			cliente.setRiesgo(request.getParameter("riesgo"));
			cliente.setDescripcion(request.getParameter("Descripcion"));
			
			HttpSession sesion1 = requestMulti.getSession();
			String perfilito = (sesion1.getAttribute("perfilId").toString());
			String usuario = "";
			if (requestMulti.getSession().getAttribute("usuario") != null) {
				try {

					if (requestMulti.getSession().getAttribute("tipo") != null
							&& requestMulti.getSession().getAttribute("tipo") == "T") {
						UsuarioTransitivo ut = (UsuarioTransitivo) requestMulti.getSession().getAttribute("usuario");
						usuario = ut.getCliente().getCliente_Id();

					} else {
						//// UsuarioSistema us =
						//// (UsuarioSistema)request.getSession().getAttribute("usuario")
						//// ;
						UsuarioSistema us = (UsuarioSistema) requestMulti.getSession().getAttribute("usuario");
						usuario = us.getUsuario();

					}
				} catch (Exception es) {
					System.out.println("ControlVerificacionPersonaMoral.java usuarioTransitivo "+es.toString());
				}
			}


			/* CAPTURANDO DATOS DE COINCIDENCIAS */
			int tamanoArregloCoincidencias = Integer.parseInt(request.getParameter("tamanoArreglo"));
			System.out.println("tamano arreglo: " + tamanoArregloCoincidencias);

			// System.out.println("match 1: "+ request.getParameter("Desc0"));
			ArrayList<Coincidencia> coincidencias = new ArrayList<Coincidencia>();
			for (int i = 0; i < tamanoArregloCoincidencias; i++) {
				coincidencias.add(new Coincidencia(request.getParameter("matchid" + i), request.getParameter("Desc" + i)));
				
				if (request.getParameter("Desc" + i) != "" && request.getParameter("Desc" + i) != null && !request.getParameter("Desc" + i).isEmpty())
				new DatosClienteRaro().insertarPistaAuditDeslindamiento(cliente.getCliente_Id(),request.getParameter("matchid"+i), request.getParameter("Desc"+i), perfilito, usuario);
             
                
			}
			// System.out.println("id cliente avers "
			// +request.getParameter("Cliente_Id"));
			boolean actualizarCoincidenciasListas = new DatosClienteRaro()
					.actualizarCoincidencias(request.getParameter("Cliente_Id"), coincidencias);
			System.out.println("resultado de actualizaci髇 de coincidencias: " + actualizarCoincidenciasListas);

			//
			String rbloqueado = request.getParameter("chkFechaBloqueo");
			boolean bloqueado = false;
			String fechaBloqueo = "";
			if (rbloqueado != null) {
				bloqueado = true;
				fechaBloqueo = request.getParameter("fechaBloqueo");
			}
			cliente.setBloqueado(bloqueado);
			cliente.setFechaBloqueo(fechaBloqueo);

			String rBorrado = request.getParameter("borrado");
			boolean borrado = false;
			if (rBorrado != null && rBorrado.equals("true")) {
				borrado = true;
			}

			cliente.setBorrado(borrado);
						boolean agregarCliente = new DatosClienteRaro().insertar(cliente, perfilito, usuario);

			/**
			 * RECUPERANDO DATOS DE LA PERSONA MORAL
			 */

			String razonsocial = request.getParameter("razonSocial");
			String rfc = request.getParameter("RFCEmpresa");
			String fechaconstitucion = request.getParameter("fechaConstitucion");
			String wpaisEmpresa = request.getParameter("paisEmpresa");
			Pais paisEmpresa = new Pais(wpaisEmpresa);
			String rlnombre = request.getParameter("nombres");
			String rlapellidopaterno = request.getParameter("paterno");
			String rlapellidomaterno = request.getParameter("materno");
			String rlfechanacimiento = request.getParameter("fechaNacimiento");
			String rlrfc = request.getParameter("RFCRepreentante");
			String rlidentifica_id = request.getParameter("tipoIdentificacion");
			TipoIdentificacion rtipoId = new TipoIdentificacion(rlidentifica_id);
			String rlautoridademiteid = request.getParameter("autoridadEmite");
			String rlnumeroid = request.getParameter("numeroId");
			String rlcurp = request.getParameter("CURP");
			String rlidentificaciontipo = request.getParameter("otroTipoId");
			String giro_id = request.getParameter("giro");
			Giro giro = new Giro(giro_id);
			String fecharegistro = request.getParameter("now"); // recuperaado
																// de base de
																// datos
			 String rlNoPoder=request.getParameter("nopoder");
	            String rlNotaria=request.getParameter("rlnotaria");
	            String rlFechaNotarial=request.getParameter("rlfechaNotarial");
	            String noEscritura=request.getParameter("noescritura");
	            String fechaNotarial=request.getParameter("fechaNotarial");
	            String notaria=request.getParameter("notaria");


			/*
			 * 
			 * Estas variables ya fueron asignadas en la reques del file
			 * 
			 * String imagenactaconstitutiva =
			 * request.getParameter("archivoActa"); String imagencedulafiscal =
			 * request.getParameter("archivoCedulaEmpresa"); String imagenrlid =
			 * request.getParameter("archivoIDRepresentante"); String
			 * imagenrlcedulafiscal =
			 * request.getParameter("archivoCedulaRepresentante"); String
			 * imagenrlpodernotarial =
			 * request.getParameter("archivoPoderNotarial");
			 */

			/**
			 * AGREGANDO LOS ATRIBUTOS DE LA PERSONAL MORAL AL OBJETO PERSONAL
			 * MORAL
			 */

			PersonaMoral personaMoral = new PersonaMoral(cliente, razonsocial, rfc, fechaconstitucion, paisEmpresa,
					rlnombre, rlapellidopaterno, rlapellidomaterno, rlfechanacimiento, rlrfc, rtipoId,
					rlautoridademiteid, rlnumeroid, rlcurp, rlidentificaciontipo, giro, fecharegistro, imagenActa,
					imagenCedula, imagenRlId, imagenRlPoderNotarial, imagenDeclaratoria, rlNoPoder,
                    rlNotaria,
                    rlFechaNotarial,
                    noEscritura,
                    fechaNotarial,
                    notaria);
			System.out.println("rlnopoder "+   rlNoPoder);
            System.out.println("rlNotaria "+   rlNotaria);
            System.out.println("rlFechaNotarial "+   rlFechaNotarial);
            System.out.println("noEscritura "+   noEscritura);
            System.out.println("fechaNotarial "+   fechaNotarial);
            System.out.println("notaria "+   notaria);
			new DatosPersonaMoral().insertar(personaMoral, perfilito, usuario);
			// Actualizo tambi茅n el RFC en la tabla de varusuariostransitorio
			String vsql = "UPDATE varusuariotransitorio SET rfc = '" + request.getParameter("RFCEmpresa")
					+ "' WHERE idcliente = '" + cliente.getCliente_Id() + "'";
			new DatosUtilidades().ejecutaInstruccionUpdateSQL(vsql);
			// Y la tabla de avpersonamoral
			vsql = "UPDATE avpersonamoral SET rfc = '" + request.getParameter("RFCEmpresa") + "' WHERE cliente_id = '"
					+ cliente.getCliente_Id() + "'";
			new DatosUtilidades().ejecutaInstruccionUpdateSQL(vsql);

			/**
			 * RECUPERANDO LOS DATOS DEL DOMICILIO
			 */

			String calle = request.getParameter("calle");
			String exterior = request.getParameter("exterior");
			String interior = request.getParameter("interior");
			String colonia = request.getParameter("colonia");
			String cp = request.getParameter("cp");
			String estado_provi = request.getParameter("estado");
			String paisDomicilio = request.getParameter("paisDomicilio");
//			String ciu = request.getParameter("ciudad");
//			System.out.println("Pais Domicilio " + paisDomicilio + " ciudad : " + ciu);
			String delegacion= request.getParameter("delegacion");
			/**
			 * esta variable ya fue asignada durante el request del file String
			 * archivoDomicilio = request.getParameter("archivoDomicilio");
			 */

			/**
			 * AGREGANDO LOS ATRIBUTOS AL OBJETO DOMICILIO
			 */
			Domicilio domicilio = new Domicilio();
			domicilio.setCliente(cliente);
			domicilio.setCalle(calle);
			domicilio.setNumexterior(exterior);
			domicilio.setNuminterior(interior);
			domicilio.setColonia(colonia);
			domicilio.setCodpostal(cp);
			domicilio.setEstado_prov(estado_provi);
			domicilio.setPais(new Pais(paisDomicilio));
			domicilio.setImagencomprobantedom(imagenComprobanteDomicilio);
			domicilio.setDelegacionMunicipio(delegacion);
			new DatosDomicilio().insertarDomicilio(domicilio, perfilito, usuario);
			
			if ( estado.equals("V") || estado.equals("A") && estadoAnterior.equals("R")){
               	try{
                       Correos correos = new Correos();
                       correos.envioCorreoStatusRaV(cliente.getCliente_Id(), cliente.geteMail());
                   } catch ( Exception es ){
                       
                       System.out.println("Correo Inalcansable :"+es.toString());
                   }
               	
               }

			if (estado.equals("P") || estado.equals("S")) {
				boolean clienteValido = new DatosClienteRaro().getValidarCliente(cliente.getCliente_Id());
				boolean personaFisicaValida = new DatosClienteRaro().getValidarPersonaMoral(cliente.getCliente_Id());
				boolean domocilioValido = new DatosClienteRaro().getValidarDomicilio(cliente.getCliente_Id());

				
				if (clienteValido && personaFisicaValida && domocilioValido) {
					// V = POR VALIDAR POR EL AREA DE PLD
//					 boolean coinci=new
//					 DatosClienteRaro().obtenerCoincidenciasListas(cliente);
//					 System.out.println("coincidencias "+coinci);
					 
					 
					 
					String sql = "UPDATE AVCLIENTE SET ESTADO = 'V' WHERE cliente_id = '" + cliente.getCliente_Id()
							+ "'";
					new DatosUtilidades().ejecutaInstruccionUpdateSQL(sql);
					// Mensajes.mensaje = "Le confirmamos que ha ingresado
					// debidamente la informaci贸n solicitada a nuestro portal.
					// \n" ;
					// Mensajes.mensaje += "Se har谩 la revisi贸n pertinente de
					// sus documentos en un lapso de 24 a 48 hrs h谩biles,
					// estaremos en contacto con usted para informarle acerca
					// del estatus.";
					// Correo correos = new Correo();
					// correos.envioCorreoClienteValido(cliente.getRazonSocial(),
					// cliente.geteMail()); //se le notifica al cliente que su
					// informacion sera validada
					//
					// //Se le notifica al personal de PLD que la informaci贸n
					// debe ser validada
					// String correoDestino = new
					// DatosUsuarioRatos().getcorreosPorValidar(cliente.getCliente_Id());
					// correos.envioCorreoClientePorValidar(cliente.getCliente_Id(),
					// correoDestino);

				}
			}

			// Envio de correo si es cliente invalido
			String usuarioPLD = "";
			try {
				HttpSession sesion = requestMulti.getSession();
				UsuarioSistema us = (UsuarioSistema) sesion.getAttribute("usuario");
				usuarioPLD = us.getUsuario();
			} catch (Exception es) {
				System.out.println("ControlVerificacionPersonaMoral.java HttpSession "+es.toString());
				usuarioPLD = "";
			}

			String correoPLD = new DatosUsuarioRatos().getcorreosInValidacion(usuarioPLD);
			boolean envioCorreoInvalido = true;
			boolean envioCorreoInvalido2 = true;
			if (estado.equals("I") && cliente.getMensaje() != null & !cliente.getMensaje().isEmpty()) {
				String nombreUsuario = razonsocial;

				try {
					Correos correos = new Correos();

					envioCorreoInvalido = correos.envioCorreoClienteInvalido(nombreUsuario, cliente.getMensaje(),
							cliente.geteMail(), correoPLD);

				} catch (Exception es) {
					envioCorreoInvalido = false;
					System.out.println("ControlVerificacionPersonaMoral.java envioCorreoClienteInvalido "+es.toString());
					
				}
			}
//			} else if (estado.equals("A")) {
//				if(estadoAnterior.equals("N")){
//           		 String noCliente=new DatosClienteRaro().getNoClientePorSalesForce(cliente.getCliente_Id());
//
//           		 try{
//                   	 Correos correos = new Correos();
//                   	 System.out.println("*****insertando en contrato electr髇ico***********");
//              		new DatosClienteRaro().insertarContratosServiciosNoExistentes(noCliente,rfc,razonsocial);
//              		System.out.println("*****intentando enviar correo electr髇ico***********");
//                 	 
//                   	 envioCorreoInvalido=correos.envioCorreoClienteInactivo(new DatosUsuarioRatos().getCorreoEjecutivo(c),c,razonsocial);
//                   	 
//                   }catch(Exception e){
//                   	System.out.println("error al enviar correo inactivo");
//                   }
//           	 }
//				String nombreUsuario = razonsocial;
//				try {
//					Correos correos = new Correos();
//					String correoCopias = new DatosUsuarioRatos().getcorreosValidacion(c);
//					 envioCorreoInvalido2= correos.envioCorreoClienteValidoPrincipal(nombreUsuario, rfc, cliente.geteMail());
//                     envioCorreoInvalido = correos.envioCorreoClienteValidoEjecutivoSupervisor(nombreUsuario, rfc, correoCopias);
//
//				} catch (Exception es) {
//					envioCorreoInvalido = false;
//					es.printStackTrace();
//				}
//
//			}
				else if ( estado.equals("A") ){
               	 String nombreUsuario = razonsocial;
               	 if(estadoAnterior.equals("N")){
               		 String noCliente=new DatosClienteRaro().getNoClientePorSalesForce(cliente.getCliente_Id());
               		//falta la insercion del final en contratoelectrronico con sus validaciones
               		 try{
                       	 Correos correos = new Correos();
                       	 System.out.println("*****insertando en contrato electr髇ico***********");
                    		new DatosClienteRaro().insertarContratosServiciosNoExistentes(noCliente,rfc,razonsocial);
                    		System.out.println("*****intentando enviar correo electr髇ico de cliente inactivo a ejecutivo***********");
                       	 envioCorreoInvalido=correos.envioCorreoClienteInactivo(new DatosUsuarioRatos().getCorreoEjecutivo(c),c,razonsocial);
                       	 System.out.println("*****intentando enviar correo electr髇ico de cliente inactivo a ventas***********");
                       	 envioCorreoInvalido=correos.envioCorreoClienteInactivo("vengob2@efectivale.com.mx",c,razonsocial);
               		 }catch(Exception e){
                       	System.out.println("error al enviar correo inactivo :"+e.toString());
                       }
               	 }else{
               		 try{
               			 Correos correos = new Correos();
                            String correoCopias= new DatosUsuarioRatos().getcorreosValidacion(c);
                            System.out.println("*****intentando enviar correo electr髇ico de cliente valido a ejecutivo y supervisor***********");

                            envioCorreoInvalido = correos.envioCorreoClienteValidoEjecutivoSupervisor(nombreUsuario, rfc, correoCopias);
               		 }catch(Exception es){
               			 envioCorreoInvalido = false; 
               			System.out.println("ControlVerificacionPersonaMoral.java getcorreosValidacion "+es.toString());
    					
               		 }
               	 }
               	 
                   
                    try{
                       Correos correos = new Correos();
                       String correoCopias= new DatosUsuarioRatos().getcorreosValidacion(c);
                		System.out.println("*****intentando enviar correo electr髇ico de cliente valido a cliente principal***********");

                       envioCorreoInvalido2= correos.envioCorreoClienteValidoPrincipal(nombreUsuario, rfc, cliente.geteMail());
                		
                       
                   } catch ( Exception es ){
                       envioCorreoInvalido = false; 
                       System.out.println("ControlVerificacionPersonaMoral.java envioCorreoClienteValidoPrincipal "+es.toString());
   					
                   }
                    
                    
                    
                }

			// Haciendo el cambio del tipo de persona si son diferentres
			boolean personaCambiada = false;
			if (tipoPersona != null && tipoPersonaCambio != null && tipoPersona.trim() != tipoPersonaCambio) {
				personaCambiada = new DatosPersonaMoral().cambiarPersona(tipoPersonaCambio, cliente);
			}

			try (PrintWriter out = response.getWriter()) {
				/*
				 * TODO output your page here. You may use following sample
				 * code.
				 */
				if (envioCorreoInvalido && envioCorreoInvalido2) {

					Mensajes.setMensaje("Se ha guardado correctamente la informaci贸n. \n");
					requestMulti.setAttribute("resultado", Mensajes.mensaje);
					requestMulti.setAttribute("exito", "1");
					requestMulti.getRequestDispatcher("/estatus_clientes.jsp").forward(requestMulti, response);
				} else {
					Mensajes.setMensaje(
							"Se ha guardado correctamente la informaci贸n. \n No ha sido posible el env铆o de la notificacion por correo electr贸nico.");
					requestMulti.setAttribute("resultado", Mensajes.mensaje);
					requestMulti.setAttribute("exito", "1");
					requestMulti.getRequestDispatcher("/estatus_clientes.jsp").forward(requestMulti, response);
				}
			}
		} // fin del if si es multipar
	}

	/**
	 * Renombra los archivos para que los encuentre
	 * 
	 * @param original
	 *            archivo original
	 * @param directorio
	 *            dir donde se guarda el archivo
	 * @param cliente
	 *            cliente
	 * @p谩ram extension extension
	 * @return nombre del archivo renombrado
	 */
	String Renombra(UploadFile archivoZip, String directorio, String c, String zip) {
		File rename = new File(Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + directorio + Rutas.separador
				+ archivoZip.getFileName());
		File rename_tmp = new File(Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + directorio
				+ Rutas.separador + directorio + "_" + Fecha.getFechaHoraSistema() + "_tmp" + zip);
		rename.renameTo(rename_tmp);
		return rename_tmp.getName();
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on
	// the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (ServletException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (ServletException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

	private String Renombra(File archivoZip, String directorio, String c, String zip) {
		File rename = new File(
				Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + directorio + Rutas.separador + archivoZip);
		File rename_tmp = new File(Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + directorio
				+ Rutas.separador + directorio + "_" + Fecha.getFechaHoraSistema() + "_tmp" + zip);
		rename.renameTo(rename_tmp);
		return rename_tmp.getName();
	}

}
