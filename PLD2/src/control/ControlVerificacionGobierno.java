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
import datosRatos.DatosGobierno;
import datosRatos.DatosUsuarioRatos;
import datosRatos.DatosUtilidades;
import entidad.Cliente;
import entidad.Domicilio;
import entidad.Giro;
import entidad.Gobierno;
import entidad.Pais;
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
import javax.swing.JOptionPane;
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
@WebServlet(name = "ControlVerificacionGobierno", urlPatterns = {"/VerificacionGobierno"})
public class ControlVerificacionGobierno extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        //response.setContentType("text/html;charset=UTF-8");
    	
    	System.out.println("aqui esta entrando para editar la primera vez");
        String imagenExistencia = "" ;
        String imagenCedula = "";
        String imagenComrpobanteDomicilio ="";

        String imagenFId = "";
        String imagenFCedulafiscal ="";
        String imagenFFacultades ="";
        String Ccliente = "";
        
        
        if (MultipartFormDataRequest.isMultipartFormData(requestMulti)){ 
            
            Cliente c = new Cliente();
            
            /**
             * GESTIONANDO LA CARGA DE LOS ARCHIVOS
             */
            
            
            
            UploadBean upBean = new UploadBean();
            MultipartFormDataRequest request = null;
            try {
                request = new MultipartFormDataRequest(requestMulti);
            } catch (UploadException ex) {
            	System.out.println("ControlVerificacionGobierno.java request "+ex.toString());
 				
            }
            
            Hashtable files = request.getFiles();
            UploadFile file  = null;
            String zipFile = null;
            c.setCliente_Id(request.getParameter("Cliente_Id"));            
            Ccliente = request.getParameter("Cliente_Id");                        
           
             //CARGA DEL DOCUMENTO QUE ACREDITA LA EXISTENCIA 
            try{
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirAreditaacion + Rutas.separador);
             zipFile = request.getParameter("archivoExistenciaZip");
             if (zipFile.length() > 0 ) { 
                byte[] data = Base64.decode(zipFile);  
//                 JOptionPane.showMessageDialog(null, data);
//                 System.out.print(data);
                File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirAreditaacion + Rutas.separador + Rutas.dirAreditaacion + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }             
                imagenExistencia = Renombra(archivoZip, Rutas.dirAreditaacion, c.getCliente_Id(), ".zip" );
             }
             } catch ( Exception es ){
            	 System.out.println("ControlVerificacionGobierno.java archivoExistenciaZip "+es.toString());
  				
             }
            
            
            //CARGA DE CEDULA FISCAL
            try{
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirCedula + Rutas.separador);
             zipFile = request.getParameter("archivoCedulaZip");
             if ( zipFile.length() > 0 ){
                byte[] data = Base64.decode(zipFile);  
                File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirCedula + Rutas.separador + Rutas.dirCedula + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }             
                imagenCedula = Renombra(archivoZip, Rutas.dirCedula, c.getCliente_Id(), ".zip" );
             }
             } catch ( Exception es ){
            	 System.out.println("ControlVerificacionGobierno.java archivoCedulaZip "+es.toString());
   				
             }

            //COMPROPBANDE DE DOMICILIO
            try{
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirComprobanteDom + Rutas.separador);
            zipFile = request.getParameter("archivoDomicilioZip");
             if ( zipFile.length() > 0 ){
                byte[] data = Base64.decode(zipFile);  
                File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirComprobanteDom + Rutas.separador + Rutas.dirComprobanteDom + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }             
                imagenComrpobanteDomicilio = Renombra(archivoZip, Rutas.dirComprobanteDom, c.getCliente_Id(), ".zip" );
             }
             } catch ( Exception es ){
            	 System.out.println("ControlVerificacionGobierno.java archivoDomicilioZip "+es.toString());
    				
             }
            
            
            //CARGA DE LA IDENTIFICACIoN DEL FUNCIONARIO 
            try{
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirRlIdentificacion + Rutas.separador);
             zipFile = request.getParameter("archivofIdZip");
             if ( zipFile.length() > 0 ){
                byte[] data = Base64.decode(zipFile);  
                File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirRlIdentificacion + Rutas.separador + Rutas.dirRlIdentificacion + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }             
                imagenFId = Renombra(archivoZip, Rutas.dirRlIdentificacion, c.getCliente_Id(), ".zip" );
             }
             } catch ( Exception es ){
            	 System.out.println("ControlVerificacionGobierno.java archivofIdZip "+es.toString());
     			
             }
            
            
            //CARGA DE LA CEDULA FISCAL DEL FUNCIONARIO 
            try{
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirRlCedulaFiscal + Rutas.separador);
             zipFile = request.getParameter("archivofCedulaZip");
             if ( zipFile.length() > 0 ){
                byte[] data = Base64.decode(zipFile);  
                File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirRlCedulaFiscal + Rutas.separador + Rutas.dirRlCedulaFiscal + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }             
                imagenFCedulafiscal = Renombra(archivoZip, Rutas.dirRlCedulaFiscal, c.getCliente_Id(), ".zip" );
             }
             } catch ( Exception es ){
            	 System.out.println("ControlVerificacionGobierno.java archivofCedulaZip "+es.toString());
      			
             }
            
            
            //CARGA DE LA DOC ACREDITACIONDE FACULTADES 
            try{
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirFacultades + Rutas.separador);
             zipFile = request.getParameter("archivofFacultadesZip");
             if ( zipFile.length() > 0 ){    
                byte[] data = Base64.decode(zipFile);  
                File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirFacultades + Rutas.separador + Rutas.dirFacultades + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }             
                imagenFFacultades = Renombra(archivoZip, Rutas.dirFacultades, c.getCliente_Id(), ".zip" );
             }
             } catch ( Exception es ){
            	 System.out.println("ControlVerificacionGobierno.java archivofFacultadesZip "+es.toString());
       			
             }
            
            
               String declaroBeneficiario = request.getParameter("declaroBeneficiario");
                String declaroOrigen = request.getParameter("declaroOrigen") ;
                /**
                 *  RECUJPERANDO LOS DATOS DEL CLIENTE
                 */
                
                String tipoPersona = request.getParameter("tipoPersona");
                String tipoPersonaCambio = request.getParameter("tipoPersonaCambio");
                
                c.setTipoPersona(tipoPersona);
                c.setFechaRegistro("");
                String tipoDomicilio = "";
                String  telPais = request.getParameter("telPais");
                if ( telPais.trim().equals("MX") ){
                     tipoDomicilio= "N";
                } else  {
                     tipoDomicilio = "E";
                }
                c.setTipoDomicilio(tipoDomicilio);

                String  telefono = request.getParameter("telefono");
                c.setTelefono(telefono);

                Pais paisTel = new Pais();
                paisTel.setPais(telPais);
                c.setPais(paisTel);

                String  correo = request.getParameter("correo");
                c.seteMail(correo);
                
                String estado = "";
                String estadoAnterior=request.getParameter("EstatusAnterior");
                estado = request.getParameter("Estatus"); //se refiere al estatus
                int validado = 0;
                String fechaValidado = "";                
                if ( estado != null && !estado.isEmpty() && estado.equals("A")){ //A = VALIDO 
                        validado = 1; //si no    //esta dato nace no se recoje de la forma //se evalua con sp de base de datos 
                        fechaValidado = request.getParameter("fechaValidacion"); // null en definicion de dd
                        if ( fechaValidado.isEmpty() ){
                            fechaValidado = "";
                        }
                }
                c.setEstado(estado); //por validar
                c.setRazonSocial(tipoPersona);
                c.setValidado( validado );
                String riesgo = request.getParameter("riesgo");
                c.setRiesgo(riesgo);	
////                cliente.setFechaRiesgo("");
                String Descripcion = request.getParameter("Descripcion"); 
                c.setDescripcion(Descripcion);
                
                String  nombreCliente = request.getParameter("nombreCliente"); //es razon social
                c.setRazonSocial(nombreCliente);

                

                c.setFechaValidado(fechaValidado);
                
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
                    	 System.out.println("ControlVerificacionGobierno.java requestMulti "+es.toString());
                			
                    }
                }
                
                
//                /*CAPTURANDO DATOS DE COINCIDENCIAS*/
                int tamanoArregloCoincidencias=Integer.parseInt(request.getParameter("tamanoArreglo"));
                System.out.println("tamano arreglo: "+ tamanoArregloCoincidencias);
               
               // System.out.println("match 1: "+ request.getParameter("Desc0"));
                ArrayList<Coincidencia>coincidencias= new ArrayList<Coincidencia>();
                for(int i=0;i<tamanoArregloCoincidencias;i++){
                	coincidencias.add(new Coincidencia(request.getParameter("matchid"+i), request.getParameter("Desc"+i)));
                	
                	if (request.getParameter("Desc" + i) != "" && request.getParameter("Desc" + i) != null && !request.getParameter("Desc" + i).isEmpty())
        				new DatosClienteRaro().insertarPistaAuditDeslindamiento(request.getParameter("Cliente_Id"),request.getParameter("matchid"+i), request.getParameter("Desc"+i), perfilito, usuario);
                     
                }
               // System.out.println("id cliente avers " +request.getParameter("Cliente_Id"));
                 boolean actualizarCoincidenciasListas = new DatosClienteRaro().actualizarCoincidencias(request.getParameter("Cliente_Id"),coincidencias);
                System.out.println("resultado de actualización de coincidencias: "+actualizarCoincidenciasListas);
               

                

                 //*AQUITA ESTA LA BANDERA PARA SABER QUE SI EXISTE EL BENEFICIARIO

                 try{
                      if ( declaroBeneficiario != null ){
                          c.setDeclaroBeneficiario(1); //indica que si hay beneficiario
                      } else {
                          c.setDeclaroBeneficiario(0); //indica que no hay beneficiario
                      }
                 } catch ( Exception es ){
                        c.setDeclaroBeneficiario(0); //en caso de error no se declara el beneficiario
                 }
                //declaro origen
                try{
                    if ( declaroOrigen != null)
                    c.setDeclaroOrigen( 1 );
                } catch( Exception es ){
                    c.setDeclaroOrigen( 0 );
                }
                
                
                String usuariovalido = null;
                if ( estado != null && !estado.isEmpty()){
                    try{
                        HttpSession sesion = requestMulti.getSession();
                        UsuarioSistema us = ( UsuarioSistema) sesion.getAttribute("usuario");
                        String usuari = us.getNumero_interno();
                        usuariovalido = usuari;
                    } catch ( Exception es ){
                    	System.out.println("ControlVerificacionGobierno.java HttpSession "+es.toString());
                        
                    }
                    
                }
                
                c.setUsuarioValido(usuariovalido); //null
                c.setFechaCorte(request.getParameter("fechacorte")); //null
                if (estado != null && !estado.isEmpty() && estado.equals("A")) {
                    c.setMensaje("");
                } else {
                    c.setMensaje(request.getParameter("mensaje")); //null
                    
                }
                c.setUsuarioAsignado( request.getParameter("usuarioasignado") ) ;
                
                String rbloqueado = request.getParameter("chkFechaBloqueo");
                boolean bloqueado = false;
                String fechaBloqueo = "";
                if ( rbloqueado != null ){
                    bloqueado = true;
                    fechaBloqueo = request.getParameter("fechaBloqueo");
                }
                c.setBloqueado(bloqueado);
                c.setFechaBloqueo(fechaBloqueo);
                
                
                
                String rBorrado = request.getParameter("borrado"); 
                boolean borrado = false;
                if ( rBorrado!= null && rBorrado.equals("true")){
                    borrado = true;
                }
                c.setBorrado(borrado);
                c.setNotas(request.getParameter("notas")); //null
                 
                
                boolean agregarCliente = new DatosClienteRaro().insertar(c,perfilito,usuario);

                
                

                /**
                 * RECUPERANDO LOS DATOS DEL GOBIERNO
                 */



                String  fechaCreacion = request.getParameter("fechaCreacion");
                String  rfcCliente = request.getParameter("rfcCliente");
                String giro = request.getParameter("giro");
                String  actividadObjeto = request.getParameter("actividadObjeto");
                String  nacionalidadCliente = request.getParameter("nacionalidadCliente");
                String  archivoExistencia = request.getParameter("archivoExistencia");
                String  archivoCedula = request.getParameter("archivoCedula");

                String  calle = request.getParameter("calle");
                String  exterior = request.getParameter("exterior");
                String  interior  = request.getParameter("interior");
                String  colonia = request.getParameter("colonia");
                String  cp = request.getParameter("cp");
                String  estado_prov = request.getParameter("estado");
                String paisDomicilio = request.getParameter("pais");
                String  paisCliente = request.getParameter("nacionalidadCliente");
                String  archivoDomicilio = request.getParameter("archivoDomicilio");
                String delegacion=request.getParameter("delegacion");

                String  fnombre = request.getParameter("fnombre");
                String  fpaterno = request.getParameter("fpaterno");
                String  fmaterno = request.getParameter("fmaterno");
                String  fFechaNacimiento = request.getParameter("fFechaNacimiento");
                String  fRFC = request.getParameter("fRFC");
                String  fCURP = request.getParameter("fCURP");
                TipoIdentificacion tipoIdentificacion = new TipoIdentificacion(); 
                tipoIdentificacion.setIdentifica_id( request.getParameter("tipoIdentificacion"));
                
                
                String  fNumeroId = request.getParameter("fNumeroId");
                String  ftipoIdentificacion = request.getParameter("fotroTipoId");
                String  fautoridadEmite = request.getParameter("fautoridadEmite");
                
                
//                String  archivofId = request.getParameter("archivofId");
//                String  archivofCedula = request.getParameter("archivofCedula");
//                String  archivofFacultades = request.getParameter("archivofFacultades");
                

                /**
                 * GUARDANO LOS DATOS EN LA ENTIDAD GOBIERNO
                 */
                //Cliente c = new Cliente();
                c.setCliente_Id(request.getParameter("Cliente_Id"));

                Pais ppaisCliente = new Pais();
                ppaisCliente.setPais(paisCliente);

                Giro obgiro = new Giro(); 
                obgiro.setGiro_id(giro);


                Gobierno g = new Gobierno();
                g.setCliente(c);
                g.setRazonsocial(nombreCliente);
                g.setActividadobjetosocial(actividadObjeto);
                g.setRfc(rfcCliente);
                g.setFechacreacion(fechaCreacion);
                g.setPais(ppaisCliente);    
                g.setGiro(obgiro);
                g.setRlnombre(fnombre);
                g.setRlapellidopaterno(fpaterno);
                g.setRlapellidomaterno(fmaterno);
                g.setRlfechanacimiento(fFechaNacimiento);
                g.setRlrfc(fRFC);   //DEL SELECT INE | PASAPORTE | ETC.
                g.setRlidentificaciontipo(ftipoIdentificacion); 
                g.setRlautoridademiteid(fautoridadEmite);
                g.setRlnumeroid(fNumeroId);
                g.setRlcurp(fCURP);
                g.setIdentificacion(tipoIdentificacion); 
                g.setFecharegistro("");
                g.setImagenacreditacion(imagenExistencia);
                g.setImagencedulafiscal(imagenCedula);
                g.setImagenrlid(imagenFId);
                g.setImagenrlfacultades(imagenFFacultades);
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
                
//                  boolean agregarGobierno = new DatosGobierno().insertar(g,perfilito,usuario);
                boolean agregarGobierno = new DatosGobierno().insertar(g,perfilito,usuario);
            String vsql = "UPDATE varusuariotransitorio SET rfc = '" + request.getParameter("rfcCliente") + "' WHERE idcliente = '" + c.getCliente_Id() + "'";
            new DatosUtilidades().ejecutaInstruccionUpdateSQL(vsql);      
            // Y la tabla de avgobierno
            vsql = "UPDATE avgobierno SET rfc = '" + request.getParameter("rfcCliente") + "' WHERE cliente_id = '" + c.getCliente_Id() + "'";
            new DatosUtilidades().ejecutaInstruccionUpdateSQL(vsql);                                          

                /**
                 *  AGREGANDO LOS ATRIBUTOS AL OBJETO DOMICILIO
                 */ 

                Domicilio domicilio = new Domicilio();
                domicilio.setCliente(c);
                domicilio.setCalle(calle);
                domicilio.setNumexterior(exterior);
                domicilio.setNuminterior(interior);
                domicilio.setColonia(colonia);
                domicilio.setCodpostal(cp);
                domicilio.setEstado_prov(estado_prov);
                domicilio.setPais( new Pais(paisDomicilio) );
                domicilio.setImagencomprobantedom(imagenComrpobanteDomicilio);
                domicilio.setDelegacionMunicipio(delegacion);
                boolean agregarDomicilio = new DatosDomicilio().insertarDomicilio(domicilio,perfilito,usuario);
                
//                System.out.println("entrando a la validacion de P S");
                if ( estado.equals("V") || estado.equals("A") && estadoAnterior.equals("R")){
               	try{
                    Correos correos = new Correos();
                    correos.envioCorreoStatusRaV(Ccliente, correo);
                } catch ( Exception es ){
                	System.out.println("ControlVerificacionGobierno.java envioCorreoStatusRaV "+es.toString());
                    
                }
            	
            } 
                 if ( estado.equals("P") || estado.equals("S") ){
//                	 System.out.println("si entroa el estatus P o S");
                    //Validacion de la completitud de los formularios
                   boolean clienteValido = new DatosClienteRaro().getValidarCliente(c.getCliente_Id());
                   boolean personaFisicaValida = new DatosClienteRaro().getValidarPersonaGobierno(c.getCliente_Id());
                   boolean domocilioValido = new DatosClienteRaro().getValidarDomicilio(c.getCliente_Id());
                   System.out.println("cliente valido "+clienteValido);
                   System.out.println("persona valida "+clienteValido);
                   System.out.println("domicilio valido  "+clienteValido);

                   if ( clienteValido && personaFisicaValida && domocilioValido){
                       // V = POR VALIDAR POR EL AREA DE PLD
//                	   System.out.println("si es valida la informacion");
//                	   boolean coinci=new DatosClienteRaro().obtenerCoincidenciasListas(c);
//                       System.out.println("coincidencias "+coinci);
                       

                      String sql = "UPDATE AVCLIENTE SET ESTADO = 'V' WHERE cliente_id = '" + c.getCliente_Id() + "'";
                       new DatosUtilidades().ejecutaInstruccionUpdateSQL(sql);
//                       Mensajes.mensaje =  "Le confirmamos que ha ingresado debidamente la informaciÃ³n solicitada a nuestro portal. \n" ;
//                       Mensajes.mensaje += "Se harÃ¡ la revisiÃ³n pertinente de sus documentos en un lapso de 24 a 48 hrs hÃ¡biles, estaremos en contacto con usted para informarle acerca del estatus.";
//                       Correo correos = new Correo();
//                       correos.envioCorreoClienteValido(nombreCliente, correo); //se le notifica al cliente que su informacion sera validada
//
//                       //Se le notifica al personal de PLD que la informacion debe ser validada
//                       String correoDestino = new DatosUsuarioRatos().getcorreosPorValidar(c.getCliente_Id()); 
                       //correos.envioCorreoClientePorValidar(c.getCliente_Id(), correoDestino);

                   }
                 }
                
                
                
                
                
                //Envio de correo si es cliente invalido
                String usuarioPLD = "";
                try{
                    HttpSession sesion = requestMulti.getSession();
                    UsuarioSistema us = ( UsuarioSistema) sesion.getAttribute("usuario");
                    usuarioPLD = us.getUsuario();
                } catch  ( Exception es ){
                	System.out.println("ControlVerificacionGobierno.java HttpSession "+es.toString());
                    
                    usuarioPLD = "";
                }
                
                
                
                String correoPLD = new DatosUsuarioRatos().getcorreosInValidacion(usuarioPLD);
                boolean envioCorreoInvalido = true;
                boolean envioCorreoInvalido2 = true;
                String correoCopias="";
                if (estado.equals("I") && c.getMensaje() != null & !c.getMensaje().isEmpty()){
                    String nombreUsuario = nombreCliente;
                    try{
                        Correos correos = new Correos();
                        
                        envioCorreoInvalido = correos.envioCorreoClienteInvalido(nombreUsuario, c.getMensaje(), correo, correoPLD);
                        
                    } catch ( Exception es ){
                        envioCorreoInvalido = false; 
                        System.out.println("ControlVerificacionGobierno.java envioCorreoClienteInvalido "+es.toString());
                    }}
//                 } else if ( estado.equals("A") ){
//                	 if(estadoAnterior.equals("N")){
//                		 String noCliente=new DatosClienteRaro().getNoClientePorSalesForce(Ccliente);
//
//                		 try{
//                        	 Correos correos = new Correos();
//                        	 System.out.println("*****insertando en contrato electrónico***********");
//                       		new DatosClienteRaro().insertarContratosServiciosNoExistentes(noCliente,fRFC,nombreCliente);
//                       		System.out.println("*****intentando enviar correo electrónico***********");
//                          	 
//                        	 envioCorreoInvalido=correos.envioCorreoClienteInactivo(new DatosUsuarioRatos().getCorreoEjecutivo(Ccliente),Ccliente,nombreCliente);
//                        	 
//                        }catch(Exception e){
//                        	System.out.println("error al enviar correo inactivo");
//                        }
//                	 }
//                	 
//                     String nombreUsuario = nombreCliente;
//                    
//                     try{
//                        Correos correos = new Correos();
//                        correoCopias= new DatosUsuarioRatos().getcorreosValidacion(Ccliente);
//                        envioCorreoInvalido2= correos.envioCorreoClienteValidoPrincipal(nombreUsuario, rfcCliente, correo);
//                        envioCorreoInvalido = correos.envioCorreoClienteValidoEjecutivoSupervisor(nombreUsuario, rfcCliente, correoCopias);
//                        
//                        
//                    } catch ( Exception es ){
//                        envioCorreoInvalido = false; 
//                        es.printStackTrace();
//                    }
//                     
//                 }
                    else if ( estado.equals("A") ){
                   	 String nombreUsuario =nombreCliente;
                   	 if(estadoAnterior.equals("N")){
                   		 String noCliente=new DatosClienteRaro().getNoClientePorSalesForce(Ccliente);
                   		//falta la insercion del final en contratoelectrronico con sus validaciones
                   		 try{
                           	 Correos correos = new Correos();
                           	 System.out.println("*****insertando en contrato electrónico***********");
                        		new DatosClienteRaro().insertarContratosServiciosNoExistentes(noCliente,fRFC,nombreCliente);
                        		System.out.println("*****intentando enviar correo electrónico de cliente inactivo a ejecutivo***********");
                           	 envioCorreoInvalido=correos.envioCorreoClienteInactivo(new DatosUsuarioRatos().getCorreoEjecutivo(Ccliente),Ccliente,nombreCliente);
                           	 System.out.println("*****intentando enviar correo electrónico de cliente inactivo a ventas***********");
                           	 envioCorreoInvalido=correos.envioCorreoClienteInactivo("vengob2@efectivale.com.mx",Ccliente,nombreCliente);
                   		 }catch(Exception e){
                           	System.out.println("error al enviar correo inactivo");
                           }
                   	 }else{
                   		 try{
                   			 Correos correos = new Correos();
                                 correoCopias= new DatosUsuarioRatos().getcorreosValidacion(Ccliente);
                                System.out.println("*****intentando enviar correo electrónico de cliente valido a ejecutivo y supervisor***********");

                                envioCorreoInvalido = correos.envioCorreoClienteValidoEjecutivoSupervisor(nombreUsuario, rfcCliente, correoCopias);
                   		 }catch(Exception es){
                   			 envioCorreoInvalido = false; 
                   			System.out.println("ControlVerificacionGobierno.java getcorreosValidacion "+es.toString());
                            
                   		 }
                   	 }
                   	 
                       
                        try{
                           Correos correos = new Correos();
                    		System.out.println("*****intentando enviar correo electrónico de cliente valido a cliente principal***********");

                           envioCorreoInvalido2= correos.envioCorreoClienteValidoPrincipal(nombreUsuario, rfcCliente, correo);
                    		
                           
                       } catch ( Exception es ){
                           envioCorreoInvalido = false; 
                           es.printStackTrace();
                           System.out.println("ControlVerificacionGobierno.java envioCorreoClienteValidoPrincipal "+es.toString());
                           
                       }
                        
                        
                        
                    }
                boolean cambioPersona = false;
                if ( tipoPersona != null && tipoPersonaCambio != null && tipoPersona != tipoPersonaCambio){
                    cambioPersona = new DatosGobierno().cambiarPersona(tipoPersonaCambio, c);
                }
                
                
    }//fin if multipart
        
        try (PrintWriter out = response.getWriter()) {
            Mensajes.setMensaje("Se ha guardado correctamente la informaciÃ³n.");
            requestMulti.setAttribute("resultado", Mensajes.mensaje);
            requestMulti.setAttribute("exito", "1");
            requestMulti.getRequestDispatcher("/estatus_clientes.jsp").forward(requestMulti, response);
        }
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
		} catch (ServletException | IOException | SQLException e) {
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

//    private String Renombra(File archivoZip, String dirAreditaacion, String cliente_Id, String zip) {
//      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//   
//    }
 String Renombra(UploadFile original, String directorio, String cliente, String zip){
        File rename = new File(Rutas.rutaCarga + Rutas.separador + cliente + Rutas.separador + directorio + Rutas.separador + original.getFileName());
        File rename_tmp = new File(Rutas.rutaCarga + Rutas.separador + cliente + Rutas.separador + directorio + Rutas.separador + directorio + "_" + Fecha.getFechaHoraSistema() +"_tmp"+ zip);
        rename.renameTo(rename_tmp);
        return rename_tmp.getName();
    } 
     private String Renombra(File archivoZip, String directorio, String c, String zip) {
        File rename = new File(Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + directorio + Rutas.separador + archivoZip);
        File rename_tmp = new File(Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + directorio + Rutas.separador + directorio + "_" + Fecha.getFechaHoraSistema() +"_tmp"+ zip);
        rename.renameTo(rename_tmp);
        return rename_tmp.getName();
    }
}
