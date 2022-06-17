        /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import correo.Correos;
import com.sun.jersey.core.util.Base64;
import datosRatos.DatosClienteRaro;
import datosRatos.DatosDomicilio;
import datosRatos.DatosPersonaFisica;
import datosRatos.DatosUsuarioRatos;
import datosRatos.DatosUtilidades;
import entidad.Actividad;
import entidad.Cliente;
import entidad.Domicilio;
import entidad.Pais;
import entidad.PersonaFisica;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.StyleContext.SmallAttributeSet;

import javazoom.upload.MultipartFormDataRequest;
import javazoom.upload.UploadBean;
import javazoom.upload.UploadException;
import javazoom.upload.UploadFile;
import utilidades.Cadenas;
import utilidades.Fecha;
import utilidades.Mensajes;
import utilidades.Rutas;
import listaEntidad.*;
/**
 *
 * @author Israel Osiris García
 */
@WebServlet(name = "ControlVerificacionPersonaFisica", urlPatterns = {"/VerificacionPersonaFisica"})
public class ControlVerificacionPersonaFisica extends HttpServlet {
    
    boolean hayBeneficiario = false;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException 
     */
    protected void processRequest(HttpServletRequest requestMulti, HttpServletResponse response)
            throws ServletException, IOException, UploadException, SQLException {
            //response.setContentType("text/html;charset=UTF-8");
            System.out.println("entrando al process");
            String imagenId ="";
            String imagenCedula = "";
            String imagenCurp = "";
            String imagenDeclaratoria = "";
            String archivoComprobanteDomicilio = "";

            
            
        
         if (MultipartFormDataRequest.isMultipartFormData(requestMulti)){
             UploadBean upBean = new UploadBean();
             
             MultipartFormDataRequest request = new MultipartFormDataRequest(requestMulti);
             Hashtable files = request.getFiles();
             UploadFile file  = null;
             String zipFile = null;
             String c = request.getParameter("Cliente_Id");             
             
             try{
             //CARGA DEL ID
                upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + Rutas.dirIdentificacion + Rutas.separador);
                 zipFile = request.getParameter("archivoIdPFZip");
                if ( zipFile.length() > 0 ){
                    byte[] data = Base64.decode(zipFile);  
                    File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + Rutas.dirIdentificacion + Rutas.separador + Rutas.dirIdentificacion + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }             
                imagenId = Renombra(archivoZip, Rutas.dirIdentificacion, c, ".zip" );
                }
             
             } catch ( Exception es ){
            	 System.out.println("ControlVerificacionPersonaFisica.java archivoIdPFZip "+es.toString());
					
             }
             
             try{
             //CARGA DE CEDULA FISCAL
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + Rutas.dirCedula + Rutas.separador);
             zipFile = request.getParameter("archivoCedulaZip");
             if ( zipFile.length() > 0 ){
                    byte[] data = Base64.decode(zipFile);  
                    File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + Rutas.dirCedula + Rutas.separador + Rutas.dirCedula + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }             
                imagenCedula = Renombra(archivoZip, Rutas.dirCedula, c, ".zip" );
                }
             } catch ( Exception es ){
            	 System.out.println("ControlVerificacionPersonaFisica.java archivoCedulaZip "+es.toString());
 				
             }
             
            try{ 
            //carga del comprobanteDomicilio
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + Rutas.dirComprobanteDom + Rutas.separador);
             zipFile = request.getParameter("archivoComprobanteDomZip");
             if ( zipFile.length() > 0 ){
                    byte[] data = Base64.decode(zipFile);  
                    File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + Rutas.dirComprobanteDom + Rutas.separador + Rutas.dirComprobanteDom + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }             
                archivoComprobanteDomicilio = Renombra(archivoZip, Rutas.dirComprobanteDom, c, ".zip" );
                }
             } catch ( Exception es ){
            	 System.out.println("ControlVerificacionPersonaFisica.java archivoComprobanteDomZip "+es.toString());
  				
             }
             
             try{
             //CARGA DE Curp
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + Rutas.dirCurp + Rutas.separador);
             zipFile = request.getParameter("archivoCurpZip");
             if ( zipFile.length() > 0 ){
                    byte[] data = Base64.decode(zipFile);  
                    File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + Rutas.dirCurp + Rutas.separador + Rutas.dirCurp + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }             
                imagenCurp = Renombra(archivoZip, Rutas.dirCurp, c, ".zip" );
                }
             } catch ( Exception es ){
            	 System.out.println("ControlVerificacionPersonaFisica.java archivoCurpZip "+es.toString());
   				
             }
             
             try{
             //CARGA DE Declaratoria
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + Rutas.dirDeclaratoria + Rutas.separador);
             zipFile = request.getParameter("archivoDeclaratoriaZip");
             if ( zipFile.length() > 0 ){
                    byte[] data = Base64.decode(zipFile);  
                    File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + Rutas.dirDeclaratoria + Rutas.separador + Rutas.dirDeclaratoria + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }             
                imagenDeclaratoria = Renombra(archivoZip, Rutas.dirDeclaratoria, c, ".zip" );
                }
             } catch ( Exception es ){
            	 System.out.println("ControlVerificacionPersonaFisica.java archivoDeclaratoriaZip "+es.toString());
    				
             }
             
             
             
            
                /***
                 *  CONSTRUYENDO LA RAZÓN SOCIAL
                 */
                String Cnombre = request.getParameter("nombres");
                String Cpaterno = request.getParameter("paterno");
                String Cmaterno = request.getParameter("materno");


                /**
                 *      RECUPERANDO LOS DATOS DEL CLIENTE
                 */
                Cliente cliente = new Cliente();
                String tipoDomicilio = "";
                if ( request.getParameter("telPais") != null){
                    String paisValidar = request.getParameter("telPais").toString().trim();
                    if ( paisValidar.equals("MX")){
                        tipoDomicilio = "N";
                    } else {
                        tipoDomicilio = "E";
                    }
                }
                String tipoPersona = request.getParameter("tipoPersona");
                String fechaRegistro = ""; //en la base de datos es now 
                //String tipoDomicilio Asignado arriba
                String telefono = request.getParameter("telefono");
                Pais telPais = new Pais(); telPais.setPais( request.getParameter("telPais") ); //codigo de pais
                String correo = request.getParameter("correo");
//                String estado = ""; 
//                estado = request.getParameter("Estatus"); //se refiere al estatus
                String estado = request.getParameter("Estatus");
                String estadoAnterior=request.getParameter("EstatusAnterior");
                String razonSocial = Cnombre + " " + Cpaterno + " " + Cmaterno ;
                int validado = 0;
                String fechaValidado = null;                
                if ( estado != null && !estado.isEmpty() && estado.equals("A")){ //A = VALIDO 
                        validado = 1; //si no    //esta dato nace no se recoje de la forma //se evalua con sp de base de datos 
                        fechaValidado = request.getParameter("fechaValidacion"); // null en definicion de dd
                        if ( fechaValidado.isEmpty() ){
                            fechaValidado = null;
                        }
                }
                String nodeclaroBeneficiario = request.getParameter("nobeneficiario");
                String siDeclaroBeneficiario = request.getParameter("sibeneficiario"); //not null
                String declaroOrigen = request.getParameter("declaroOrigen") ;
                String usuariovalido = null;
                if ( estado != null && !estado.isEmpty() ){
                    try{
                        HttpSession sesion = requestMulti.getSession();
                        UsuarioSistema us = ( UsuarioSistema) sesion.getAttribute("usuario");
                        String usuario = us.getNumero_interno();
                        usuariovalido = usuario;
                    } catch ( Exception es ){
                    	System.out.println("ControlVerificacionPersonaFisica.java HttpSession "+es.toString());
        				
                        
                    }
                    
                }
                String fechacorte = request.getParameter("fechacorte");
                String mensaje = request.getParameter("mensaje").trim(); //null
                if (estado != null && !estado.isEmpty() && estado.equals("A")) {
                    mensaje = "";
                }
                String notas = request.getParameter("notas").trim();
                
                
                String usuarioasignado = request.getParameter("usuarioasignado");
                
                String riesgo = request.getParameter("riesgo");
//                
                String Descripcion = request.getParameter("Descripcion"); 
                
                
                HttpSession sesion1 = requestMulti.getSession();
                String perfilito=(sesion1.getAttribute("perfilId").toString());
                
                
                String usuario="";
                if ( requestMulti.getSession().getAttribute("usuario") != null){
                    try{

                        if ( requestMulti.getSession().getAttribute("tipo") != null &&  requestMulti.getSession().getAttribute("tipo")  == "T"){
                            UsuarioTransitivo ut = (UsuarioTransitivo)requestMulti.getSession().getAttribute("usuario") ;
                           usuario = ut.getCliente().getCliente_Id();
                            
                        } else {
                            ////UsuarioSistema us = (UsuarioSistema)request.getSession().getAttribute("usuario") ;
                            UsuarioSistema us = (UsuarioSistema)requestMulti.getSession().getAttribute("usuario") ;
                           usuario = us.getUsuario();
                           
                        }
                    } catch (Exception es ){
                    	System.out.println("ControlVerificacionPersonaFisica.java requestMulti "+es.toString());
        				
                    }
                }
                
//                
//                /*CAPTURANDO DATOS DE COINCIDENCIAS*/
                int tamanoArregloCoincidencias=Integer.parseInt(request.getParameter("tamanoArreglo"));
                System.out.println("tamano arreglo: "+ tamanoArregloCoincidencias);
//               
               // System.out.println("match 1: "+ request.getParameter("Desc0"));
                ArrayList<Coincidencia>coincidencias= new ArrayList<Coincidencia>();
                for(int i=0;i<tamanoArregloCoincidencias;i++){
                	coincidencias.add(new Coincidencia(request.getParameter("matchid"+i), request.getParameter("Desc"+i)));
//                	System.out.println("VALORES " +  cliente.getCliente_Id() );
//                	System.out.println("VALORES " + request.getParameter("matchid"+i));
//                	System.out.println("VALORES " + request.getParameter("Desc"+i));
//                	System.out.println("VALORES " + perfilito);
//                	System.out.println("VALORES " + usuario);
            		
                	if (request.getParameter("Desc" + i) != null && request.getParameter("Desc" + i) != "" && !request.getParameter("Desc" + i).isEmpty()){                		
                		new DatosClienteRaro().insertarPistaAuditDeslindamiento(c,request.getParameter("matchid"+i), request.getParameter("Desc"+i), perfilito, usuario);
                	}
                	
                }
                
                
               // System.out.println("id cliente avers " +request.getParameter("Cliente_Id"));
                 boolean actualizarCoincidenciasListas = new DatosClienteRaro().actualizarCoincidencias(request.getParameter("Cliente_Id"),coincidencias);
                System.out.println("resultado de actualizaci�n de coincidencias: "+actualizarCoincidenciasListas);
                //*DETERMINAR  SI EXISTE EL BENEFICIARIO

                try{
                      if ( siDeclaroBeneficiario != null ){
                          cliente.setDeclaroBeneficiario(1); //indica que si hay beneficiario
                          hayBeneficiario = true;
                      } else {
                          cliente.setDeclaroBeneficiario(0); //indica que no hay beneficiario
                      }
                 } catch ( Exception es ){
                        cliente.setDeclaroBeneficiario(0); //en caso de error no se declara el beneficiario
                 }
                //declaro origen
                try{
                    if ( declaroOrigen != null)
                    cliente.setDeclaroOrigen( 1 );
                } catch( Exception es ){
                    cliente.setDeclaroOrigen( 0 );
                }
                
                String rbloqueado = request.getParameter("chkFechaBloqueo");
                boolean bloqueado = false;
                String fechaBloqueo = "";
                if ( rbloqueado != null ){
                    bloqueado = true;
                    fechaBloqueo = request.getParameter("fechaBloqueo");
                }
                
//                boolean riesgo = request.getParameter("riesgo");
                
                
                
                String rBorrado = request.getParameter("borrado"); 
                boolean borrado = false;
                if ( rBorrado!= null && rBorrado.equals("true")){
                    borrado = true;
                }
               
                
                                
                cliente.setCliente_Id(c);
                cliente.setTipoPersona(tipoPersona);
                cliente.setFechaRegistro(fechaRegistro);
                cliente.setTipoDomicilio(tipoDomicilio);
                cliente.setTelefono( telefono );
                cliente.setPais(telPais);
                cliente.seteMail(correo);
                cliente.setEstado(estado);
                cliente.setRazonSocial(razonSocial);
                cliente.setValidado(validado);
                cliente.setFechaValidado(fechaValidado);
                cliente.setUsuarioValido(usuariovalido); //ATENCIoN ESTO ES PARA QUE PASE LA REFERENCIA
                cliente.setFechaCorte(fechacorte);
                cliente.setMensaje(mensaje);
                cliente.setUsuarioAsignado(usuarioasignado); //ATENCIoN ESTO ES PARA QUE PASE LA REFERENCIA 
                cliente.setBloqueado(bloqueado);
                cliente.setFechaBloqueo(fechaBloqueo);
                cliente.setBorrado(borrado);
                cliente.setNotas(notas);
                
                cliente.setRiesgo(riesgo);	
                cliente.setFechaRiesgo("");
                cliente.setDescripcion(Descripcion);
               

                /**
                 *      RECUPERANDO LOS DATOS DE LA PERSONA FiSICA
                 */

                Pais paisNacimiento = new Pais(); 
                TipoIdentificacion tipoIdentificacion = new TipoIdentificacion(); 
                Pais paisNacionaldad = new Pais(); 
                Actividad actividad = new Actividad(); 
                
                String nombre = request.getParameter("nombres");
                String paterno = request.getParameter("paterno");
                String materno = request.getParameter("materno");
                String fechaNacimiento = request.getParameter("fechaNaciento");
                String rfc = request.getParameter("RFC");
                paisNacimiento.setPais( request.getParameter("paisNacimiento"));
                actividad.setActividad_Id( request.getParameter("actividadEco") );
                tipoIdentificacion.setIdentifica_id( request.getParameter("tipoIdentificacion"));
                String otroTipoId = request.getParameter("otroTipoId"); //en base de datos identificaciontipo 
                String numeroId = request.getParameter("numeroId");
                String autoridadEmite = request.getParameter("autoridadEmite");
                String CURP  = request.getParameter("curp");
                paisNacionaldad.setPais ( request.getParameter("paisNacionalidad"));
                //String imagenId = request.getParameter("archivoIdPF");
                //String imagenCedula = request.getParameter("archivocedulaPF");

                /**
                 *      VACIANDO LOS DATOS AL OBJETO DE LA PERSONA FiSICA
                 */
                

                PersonaFisica personaFisica = new PersonaFisica();
                personaFisica.setCliente(cliente);
                personaFisica.setNombre(nombre);
                personaFisica.setApellidoPaterno(paterno);
                personaFisica.setApellidoMaterno(materno);
                personaFisica.setFechaNacimiento(fechaNacimiento);
                personaFisica.setRFC(rfc);
                personaFisica.setPaisnacimiento(paisNacimiento);
                personaFisica.setActividad(actividad);
                personaFisica.setIdentificacion(tipoIdentificacion);
                personaFisica.setIdentificacionTipo(otroTipoId); //se trtta de otro id que en base de datos es identificaciontipo
                personaFisica.setNumeroId(numeroId);
                personaFisica.setAutoridadEmiteId(autoridadEmite);
                personaFisica.setCURP(CURP);
                personaFisica.setPaisnacionalidad(paisNacionaldad);
                personaFisica.setImagenId(imagenId);
                personaFisica.setImagenCedulaFiscal(imagenCedula);
                personaFisica.setImagenCurp(imagenCurp);
                personaFisica.setImagenDeclaratoria(imagenDeclaratoria);







                /**
                 *      RECUPERANDO LOS DATOS DEL DOMICILIO FISCAL
                 */
                // ya esta arriba tipoDomicilio

                String calle = request.getParameter("calle");   
                String exterior = request.getParameter("exterior");
                String interior = request.getParameter("interior");
                String colonia = request.getParameter("colonia");                
                String estadoDomicilio = request.getParameter("estado");
                String ciudad = "";
                String cp = request.getParameter("cp");
                String delegacion=request.getParameter("delegacion");
                Pais paisDomicilio = new Pais(); 
                paisDomicilio.setPais( request.getParameter("paisDomicilio"));





                /**
                 *      VACIANDO LOS DATOS AL OBJETO DEL DOMICILIO FISCAL DE LA PERSONA FiSICA
                 */


                /**
             *  AGREGANDO LOS ATRIBUTOS AL OBJETO DOMICILIO
             */ 
            Domicilio domicilio = new Domicilio();
            domicilio.setCliente(cliente);
            domicilio.setCalle(calle);
            domicilio.setNumexterior(exterior);
            domicilio.setNuminterior(interior);
            domicilio.setColonia(colonia);
            domicilio.setCodpostal(cp);
            domicilio.setEstado_prov(estadoDomicilio);
            domicilio.setCiudad(ciudad);
            domicilio.setPais( paisDomicilio  );
            domicilio.setImagencomprobantedom(archivoComprobanteDomicilio);
            domicilio.setDelegacionMunicipio(delegacion);
            

                

                cliente.determinaTipoPersona(personaFisica);
                cliente.setDomicilio(domicilio);
//                HttpSession sesion1 = requestMulti.getSession();
//                String perfilito=(sesion1.getAttribute("perfilId").toString());
                
//                String usuario="";
//                if ( requestMulti.getSession().getAttribute("usuario") != null){
//                    try{
//
//                        if ( requestMulti.getSession().getAttribute("tipo") != null &&  requestMulti.getSession().getAttribute("tipo")  == "T"){
//                            UsuarioTransitivo ut = (UsuarioTransitivo)requestMulti.getSession().getAttribute("usuario") ;
//                           usuario = ut.getCliente().getCliente_Id();
//                            
//                        } else {
//                            ////UsuarioSistema us = (UsuarioSistema)request.getSession().getAttribute("usuario") ;
//                            UsuarioSistema us = (UsuarioSistema)requestMulti.getSession().getAttribute("usuario") ;
//                           usuario = us.getUsuario();
//                           
//                        }
//                    } catch (Exception es ){
//                        es.printStackTrace();
//                    }
//                }
                //boolean agregarCliente = new DatosClienteRaro().agregarCliente(cliente);
                boolean agregarCliente = new DatosClienteRaro().insertar(cliente,perfilito,usuario);
                boolean agregarFisica = new DatosPersonaFisica().insertar(personaFisica,perfilito,usuario);
                boolean agregarDomicilio = new DatosDomicilio().insertarDomicilio(domicilio,perfilito,usuario);
                // Actualizo también el RFC en la tabla de varusuariostransitorio
                String vsql = "UPDATE varusuariotransitorio SET rfc = '" + rfc + "' WHERE idcliente = '" + cliente.getCliente_Id() + "'";
                new DatosUtilidades().ejecutaInstruccionUpdateSQL(vsql);  
                // Y la tabla de avpersonafisica
                vsql = "UPDATE avpersonafisica SET rfc = '" + rfc + "' WHERE cliente_id = '" + cliente.getCliente_Id() + "'";
                new DatosUtilidades().ejecutaInstruccionUpdateSQL(vsql);                  
                
                if ( estado.equals("P") || estado.equals("S") ){
                    //Validacion de la completitud de los formularios
                    boolean clienteValido = new DatosClienteRaro().getValidarCliente(c);
                    boolean personaFisicaValida = new DatosClienteRaro().getValidarPersonaFisica(c);
                    boolean domocilioValido = new DatosClienteRaro().getValidarDomicilio(c);


                        //Si la info del cliente esta completa se le setea el estatus de X = por validar 
                    if ( clienteValido && personaFisicaValida && domocilioValido){
                    	
                    	
                    	
                        // V = POR VALIDAR POR EL �REA DE PLD
//                    	boolean coinci=new DatosClienteRaro().obtenerCoincidenciasListas(cliente);
//                    	boolean deslindarLista = new DatosClienteRaro().apagarBanderariesgo(c);
//                        System.out.println("coincidencias "+coinci);
                    	
                    	String sql = "UPDATE AVCLIENTE SET ESTADO = 'V' WHERE cliente_id = '" + cliente.getCliente_Id() + "'";
                        new DatosUtilidades().ejecutaInstruccionUpdateSQL(sql);
                        Mensajes.mensaje =  "Le confirmamos que ha ingresado debidamente la información solicitada a nuestro portal. \n" ;
                        Mensajes.mensaje += "Se hará la revisión pertinente de sus documentos en un lapso de 24 a 48 hrs hábiles, estaremos en contacto con usted para informarle acerca del estatus.";
                        Correos correos = new Correos();
                        correos.envioCorreoClienteValido(razonSocial, correo); //se le notifica al cliente que su informaci�n ser� validada


                       // Se le notifica al personal de PLD que la informaci�n debe ser validada
                        String correoDestino = new DatosUsuarioRatos().getcorreosPorValidar(cliente.getCliente_Id());
                        correos.envioCorreoClientePorValidar(c, correoDestino);

                    }
                    
                }
                  
               
                
                

                //Envio de correo si es cliente invalido
                String usuarioPLD = "";
                try{
                    HttpSession sesion = requestMulti.getSession();
                    UsuarioSistema us = ( UsuarioSistema) sesion.getAttribute("usuario");
                    usuarioPLD = us.getUsuario();
                } catch  ( Exception es ){
                    usuarioPLD = "";
                }
                
                if ( estado.equals("V") || estado.equals("A") && estadoAnterior.equals("R")){
                	try{
                        Correos correos = new Correos();
                        correos.envioCorreoStatusRaV(cliente.getCliente_Id(), correo);
                    } catch ( Exception es ){
                    	System.out.println("ControlVerificacionPersonaFisica.java envioCorreoStatusRaV "+es.toString());
        				
                    }
                	
                }
                
                
//                boolean deslindarLista = new DatosClienteRaro().apagarBanderariesgo(c);
                
                String correoPLD = new DatosUsuarioRatos().getcorreosInValidacion(usuarioPLD);
                boolean envioCorreoInvalido = true;
                boolean envioCorreoInvalido2=true;
                
                if (estado.equals("I") && mensaje != null & !mensaje.isEmpty()){
                    String nombreUsuario = nombre + " " + paterno + " " + materno;
                    
                    try{
                        Correos correos = new Correos();
                        
                        envioCorreoInvalido = correos.envioCorreoClienteInvalido(nombreUsuario, mensaje, correo, correoPLD);
                        
                    } catch ( Exception es ){
                        envioCorreoInvalido = false; 
                        System.out.println("ControlVerificacionPersonaFisica.java envioCorreoClienteInvalido "+es.toString());
        				
                    }
                 } else if ( estado.equals("A") ){
                	 
                	 
                	 
                	 String nombreUsuario = nombre + " " + paterno + " " + materno;
                	 if(estadoAnterior.equals("N")){
                		 String noCliente=new DatosClienteRaro().getNoClientePorSalesForce(cliente.getCliente_Id());
                		//falta la insercion del final en contratoelectrronico con sus validaciones
                		 try{
                        	 Correos correos = new Correos();
                        	 System.out.println("*****insertando en contrato electr�nico***********");
                     		new DatosClienteRaro().insertarContratosServiciosNoExistentes(noCliente,rfc,razonSocial);
                     		System.out.println("*****intentando enviar correo electr�nico de cliente inactivo a ejecutivo***********");
                        	 envioCorreoInvalido=correos.envioCorreoClienteInactivo(new DatosUsuarioRatos().getCorreoEjecutivo(c),c,razonSocial);
                        	 System.out.println("*****intentando enviar correo electr�nico de cliente inactivo a ventas***********");
                        	 envioCorreoInvalido=correos.envioCorreoClienteInactivo("vengob2@efectivale.com.mx",c,razonSocial);
                		 }catch(Exception e){
                        	System.out.println("error al enviar correo inactivo");
                        }
                	 }else{
                		 try{
                			 Correos correos = new Correos();
                             String correoCopias= new DatosUsuarioRatos().getcorreosValidacion(c);
                             System.out.println("*****intentando enviar correo electr�nico de cliente valido a ejecutivo y supervisor***********");

                             envioCorreoInvalido = correos.envioCorreoClienteValidoEjecutivoSupervisor(nombreUsuario, rfc, correoCopias);
                		 }catch(Exception es){
                			 envioCorreoInvalido = false; 
                			 System.out.println("ControlVerificacionPersonaFisica.java getcorreosValidacion "+es.toString());
             				
                		 }
                	 }
                	 
                    
                     try{
                        Correos correos = new Correos();
                        String correoCopias= new DatosUsuarioRatos().getcorreosValidacion(c);
                 		System.out.println("*****intentando enviar correo electr�nico de cliente valido a cliente principal***********");

                        envioCorreoInvalido2= correos.envioCorreoClienteValidoPrincipal(nombreUsuario, rfc, correo);
                 		
                        
                    } catch ( Exception es ){
                        envioCorreoInvalido = false; 
                        System.out.println("ControlVerificacionPersonaFisica.java getcorreosValidacion "+es.toString());
         				
                    }
                     
                     
                 }
                //}// if hay beneficiario

                try (PrintWriter out = response.getWriter()) {
                    
                    if ( envioCorreoInvalido && envioCorreoInvalido2 ){
                    
                        Mensajes.setMensaje("Se ha guardado correctamente la información. \n" );
                        requestMulti.setAttribute("resultado", Mensajes.mensaje);
                        requestMulti.setAttribute("exito", "1");
                        requestMulti.getRequestDispatcher("/estatus_clientes.jsp").forward(requestMulti, response);
                    } else {
                        Mensajes.setMensaje("Se ha guardado correctamente la información. \n No ha sido posible el envío de la notificación por correo electrónico." );
                        requestMulti.setAttribute("resultado", Mensajes.mensaje);
                        requestMulti.setAttribute("exito", "1");
                        requestMulti.getRequestDispatcher("/estatus_clientes.jsp").forward(requestMulti, response);
                    }
                }
        }//is multipart
    }//fin del metodo get

    /**
     * Renombra los archivos para que los encuentre
     * 
     * @param original archivo original
     * @param directorio dir donde se guarda el archivo
     * @param cliente cliente
     * @páram extension extension
     * @return nombre del archivo renombrado
     */
       String Renombra(UploadFile archivoZip, String directorio, String c, String zip){
        File rename = new File(Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + directorio + Rutas.separador + archivoZip.getFileName());
        File rename_tmp = new File(Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + directorio + Rutas.separador + directorio + "_" + Fecha.getFechaHoraSistema() + "_tmp" + zip);
        rename.renameTo(rename_tmp);
        return rename_tmp.getName();
    }    
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (UploadException ex) {
            Logger.getLogger(ControlVerificacionPersonaFisica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (UploadException ex) {
            Logger.getLogger(ControlVerificacionPersonaFisica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
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
        File rename = new File(Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + directorio + Rutas.separador + archivoZip);
        File rename_tmp = new File(Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + directorio + Rutas.separador + directorio + "_" + Fecha.getFechaHoraSistema() +"_tmp"+ zip);
        rename.renameTo(rename_tmp);
        return rename_tmp.getName();
    }

}
