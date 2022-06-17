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
import datosRatos.DatosFideicomiso;
import datosRatos.DatosUsuarioRatos;
import datosRatos.DatosUtilidades;
import entidad.Cliente;
import entidad.Domicilio;
import entidad.Fideicomiso;
import entidad.Pais;
//import entidad.TipoIdentificacion;
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
@WebServlet(name = "ControlVerificacionFideicomiso", urlPatterns = {"/VerificacionFideicomiso"})
public class ControlVerificacionFideicomiso extends HttpServlet {

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
        
        String imagenActa = "";
        String imagenCedula= "";
        String imagenComproanteDomicilio= "";
        String imagenRlId= "";
        String imagenRlCedula= "";
        String imagenRlPoder= "";
        String cliente = "";
        if (MultipartFormDataRequest.isMultipartFormData(requestMulti)){ 
            
            
        
                
                /**
                 *  RECUJPERANDO LOS DATOS DEL CLIENTE
                 */
                Cliente c = new Cliente();

                
                // GESTIoN DE CARGA DE ARCHIVOS
            UploadBean upBean = new UploadBean();
            MultipartFormDataRequest request = null;
            try {
                request = new MultipartFormDataRequest(requestMulti);
            } catch (UploadException ex) {
            	System.out.println("ControlVerificacionFideicomiso.java request "+ex.toString());
                ex.printStackTrace();
            }
                
            
             Hashtable files = request.getFiles();
            UploadFile file  = null;
            String zipFile = null;
            
            c.setCliente_Id(request.getParameter("Cliente_Id"));            
            cliente = request.getParameter("Cliente_Id");            
             //DOCUMENTOS GOBIERNO
            //CARGA DEL DOCUMENTO QUE ACREDITA LA EXISTENCIA 
            try{
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirAreditaacion + Rutas.separador);
             file = (UploadFile) files.get("archivoExistencia");
//             if ( file.getFileSize() > 0 ){
//                file.setFileName( Rutas.dirAreditaacion + "_" + Fecha.getFechaHoraSistema() + ".pdf"  ) ;
//                upBean.store(request, "archivoExistencia");
//                imagenExistencia = Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirAreditaacion + Rutas.separador + file.getFileName();
//             }
             } catch ( Exception es ){
            	 System.out.println("ControlVerificacionFideicomiso.java archivoExistencia "+es.toString());
                 
                 es.printStackTrace();
             }
            
            try{
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirFacultades + Rutas.separador);
             file = (UploadFile) files.get("archivofFacultades");
//             if ( file.getFileSize() > 0 ){
//                file.setFileName( Rutas.dirFacultades + "_" + Fecha.getFechaHoraSistema() + ".pdf"  ) ;
//                upBean.store(request, "archivofFacultades");
//                imagenFFacultades = Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirFacultades + Rutas.separador + file.getFileName();
//             }
             } catch ( Exception es ){
                 es.printStackTrace();
                 System.out.println("ControlVerificacionFideicomiso.java archivofFacultades "+es.toString());
                 
             }
            // FIN DOCUMENTOS GOBIERNO
            
             //CARGA ACTA CONSTITUVIVA
            try{
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirActaConstitutiva + Rutas.separador);
             zipFile = request.getParameter("archivoActaZip");
             //if ( file.getFileSize() > 0 ){
              if (zipFile.length() > 0 ) {
                byte[] data = Base64.decode(zipFile);  
                File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirActaConstitutiva + Rutas.separador + Rutas.dirActaConstitutiva + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }             
                imagenActa = Renombra(archivoZip, Rutas.dirActaConstitutiva, c.getCliente_Id(), ".zip" );
             }
             } catch ( Exception es ){
            	 System.out.println("ControlVerificacionFideicomiso.java archivoActaZip "+es.toString());
                 
                 es.printStackTrace();
             }
            
            
            //CARGA CEDULA
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
            	 System.out.println("ControlVerificacionFideicomiso.java archivoCedulaZip "+es.toString());
                 
            	 es.printStackTrace();
             }
            
            
            //CARGA COMPROBANTE DE DOMCILIO
            try{
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirComprobanteDom + Rutas.separador);
             zipFile = request.getParameter("archivoDomicilioZip");
             if ( zipFile.length() > 0 ){
                byte[] data = Base64.decode(zipFile);
                File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirComprobanteDom + Rutas.separador + Rutas.dirComprobanteDom + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }
                imagenComproanteDomicilio = Renombra(archivoZip, Rutas.dirComprobanteDom, c.getCliente_Id(), ".zip" );
             }
             } catch ( Exception es ){
            	 System.out.println("ControlVerificacionFideicomiso.java archivoDomicilioZip "+es.toString());
                 
                 es.printStackTrace();
             }
            
            
            
            //CARGA ID REPRESENTANTE
            try{
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirRlIdentificacion + Rutas.separador);
             zipFile = request.getParameter("archivoRlIdentificacionZip");
             if ( zipFile.length() > 0 ){
                byte[] data = Base64.decode(zipFile);
                File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirRlIdentificacion + Rutas.separador + Rutas.dirRlIdentificacion + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }
                imagenRlId = Renombra(archivoZip, Rutas.dirRlIdentificacion, c.getCliente_Id(), ".zip" );
             }
             } catch ( Exception es ){
            	 System.out.println("ControlVerificacionFideicomiso.java archivoRlIdentificacionZip "+es.toString());
                 
                 es.printStackTrace();
             }
          
            
//            //CARGA CEDULA REPRESENTANTE
//            try{
//             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirRlCedulaFiscal + Rutas.separador);
//             zipFile = request.getParameter("archivoCedulaRepresentanteZip");
//             if ( zipFile.length() > 0 ){
//                byte[] data = Base64.decode(zipFile);
//                File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirRlCedulaFiscal + Rutas.separador + Rutas.dirRlCedulaFiscal + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
//                
//                try (OutputStream stream = new FileOutputStream(archivoZip)) {
//                   stream.write(data);
//                }
//                imagenRlCedula = Renombra(archivoZip, Rutas.dirRlCedulaFiscal, c.getCliente_Id(), ".zip" );
//             }
//             } catch ( Exception es ){
//            	 System.out.println("ControlVerificacionFideicomiso.java archivoCedulaRepresentanteZip "+es.toString());
//                 
//             }
            
            
            
            //CARGA PODER NOTARIAL
            try{
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirRlPoderNotarial + Rutas.separador);
             zipFile = request.getParameter("archivoPoderNotarialZip");
             if ( zipFile.length() > 0 ){
                byte[] data = Base64.decode(zipFile);
                File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirRlPoderNotarial + Rutas.separador + Rutas.dirRlPoderNotarial + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }
                imagenRlPoder = Renombra(archivoZip, Rutas.dirRlPoderNotarial, c.getCliente_Id(), ".zip" );
             }
             } catch ( Exception es ){
            	 System.out.println("ControlVerificacionFideicomiso.java archivoPoderNotarialZip "+es.toString());
                 es.printStackTrace();
             }
            
            
            
            String declaroBeneficiario = request.getParameter("declaroBeneficiario");
            String declaroOrigen = request.getParameter("declaroOrigen") ;
                
                
                
                
                
                
                String tipoPersona = request.getParameter("tipoPersona");
                String tipoPersonaCambio = request.getParameter("tipoPersonaCambio");
                
                c.setTipoPersona(tipoPersona);
                c.setFechaRegistro("");
                String tipoDomicilio = "";
                String  telPais = request.getParameter("telPais");
                if ( telPais.trim().equals("MX")){
                     tipoDomicilio= "N";
                } else  {
                     tipoDomicilio = "E";
                }

                c.setTipoDomicilio(tipoDomicilio);

                String  telefono = request.getParameter("telefono");
                c.setTelefono(telefono);

                Pais paisCliente = new Pais();
                paisCliente.setPais(telPais);
                c.setPais(paisCliente);

                String  correo = request.getParameter("correo");
                c.seteMail(correo);

                String estado = request.getParameter("Estatus");
                String estadoAnterior=request.getParameter("EstatusAnterior");
                
                int validado = 0;
                String fechaValidado = null;                
                if ( estado != null && !estado.isEmpty() && estado.equals("A")){ //A = VALIDO 
                        validado = 1; //si no    //esta dato nace no se recoje de la forma //se evalua con sp de base de datos 
                        fechaValidado = request.getParameter("fechaValidacion"); // null en definicion de dd
                        if ( fechaValidado.isEmpty() ){
                            fechaValidado = null;
                        }
                }
                c.setEstado(estado); //por validar ES EL ESTATUS        

                String  nombreCliente = request.getParameter("razonSocial"); //es razon social
                c.setRazonSocial(nombreCliente);

                c.setValidado(validado); // 0  = NO VALIDO

                c.setFechaValidado(fechaValidado);
                
                c.setRiesgo(request.getParameter("riesgo"));
    			c.setDescripcion(request.getParameter("Descripcion"));
    			
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
                    	System.out.println("ControlVerificacionFideicomiso.java requestMulti "+es.toString());
                        es.printStackTrace();
                    }
                }
                
                

                /*CAPTURANDO DATOS DE COINCIDENCIAS*/
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
               try{
                    if ( declaroOrigen != null)
                    c.setDeclaroOrigen( 1 );
                } catch( Exception es ){
                    c.setDeclaroOrigen( 0 );
                }
                
                
                String usuariovalido = null;
                if ( estado != null && !estado.isEmpty() ){
                    try{
                        HttpSession sesion = requestMulti.getSession();
                        UsuarioSistema us = ( UsuarioSistema) sesion.getAttribute("usuario");
                        String usuari = us.getNumero_interno();
                        usuariovalido = usuari;
                    } catch ( Exception es ){
                        es.printStackTrace();
                        System.out.println("ControlVerificacionFideicomiso.java HttpSession "+es.toString());
                         
                    }
                    
                }
                c.setUsuarioValido(usuariovalido); //null
                c.setFechaCorte(request.getParameter("fechacorte")); //null
                if (estado != null && !estado.isEmpty() && estado.equals("A")) {
                    c.setMensaje("");
                } else {
                    c.setMensaje(request.getParameter("mensaje")); //null
                }
                
                    c.setNotas(request.getParameter("notas"));
                c.setUsuarioAsignado( request.getParameter("usuarioasignado") );
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
                
//                String riesgo = request.getParameter("riesgo");
//                c.setRiesgo(riesgo);
//                
//                String Descripcion = request.getParameter("Descripcion");
//                c.setDescripcion(Descripcion);
//                
                
                
                boolean agregarCliente = new DatosClienteRaro().insertar(c,perfilito,usuario);


                /**
                 *  RECUPERANDO LA INFORMACION DEL FIDEICOMISO
                 */

                //Campo de cliente
                String razonsocial=request.getParameter("razonSocial");
                String rfc=request.getParameter("rfc");
                String nrofideicomiso=request.getParameter("noFideicomiso");
                String rlnombre=request.getParameter("fnombre");
                String rlapellidopaterno=request.getParameter("fpaterno");
                String rlapellidomaterno=request.getParameter("fmaterno");
                String rlfechanacimiento=request.getParameter("fFechaNacimiento");
                String rlrfc=request.getParameter("fRFC");
                String rlidentifica_id=request.getParameter("ftipoIdentificacion");
                TipoIdentificacion identificacion = new TipoIdentificacion(rlidentifica_id);

                String rlautoridademiteid=request.getParameter("fautoridadEmite");
                String rlnumeroid=request.getParameter("fNumeroId");
                String rlcurp=request.getParameter("fCURP");
                String rlidentificaciontipo=request.getParameter("fotroTipoId");
                String fecharegistro= ""; //DESDE BASE DE DATOS
                String institucionfiduciaria=request.getParameter("NombreFideicomiso");
                
                
                
                
                String rlNoPoder=request.getParameter("nopoder");
	            String rlNotaria=request.getParameter("rlnotaria");
	            String rlFechaNotarial=request.getParameter("rlfechaNotarial");
	            String noEscritura=request.getParameter("noescritura");
	            String fechaNotarial=request.getParameter("fechaNotarial");
	            String notaria=request.getParameter("notaria");
                /* ASIGNACIoN A PARTIR DE LA CARGA
                String imagenactaconstitutiva=request.getParameter("actaConstitutiva");
                String imagencedulafiscal=request.getParameter("cedulaFiscal");
                String imagenrlid=request.getParameter("archivofId");
                String imagenrlcedulafiscal=request.getParameter("archivofCedula");
                String imagenrlpodernotarial=request.getParameter("archivofPoderNotarial");
                */

                Fideicomiso f   = new Fideicomiso
                (c, razonsocial, rfc, nrofideicomiso, rlnombre, rlapellidopaterno, rlapellidomaterno, rlfechanacimiento, 
                        rlrfc, identificacion, rlautoridademiteid, rlnumeroid, rlcurp, rlidentificaciontipo, fecharegistro, 
                        imagenActa, imagenCedula, imagenRlId, imagenRlPoder, institucionfiduciaria,rlNoPoder,
                        rlNotaria,
                        rlFechaNotarial,
                        noEscritura,
                        fechaNotarial,
                        notaria);
                  
                boolean agregaFideicomiso = new DatosFideicomiso().insertar(f,perfilito,usuario);
            // Actualizo tambiÃ©n el RFC en la tabla de varusuariostransitorio
            String vsql = "UPDATE varusuariotransitorio SET rfc = '" + request.getParameter("rfc") + "' WHERE idcliente = '" + c.getCliente_Id() + "'";
            new DatosUtilidades().ejecutaInstruccionUpdateSQL(vsql); 
            // Y la tabla de avfideicomiso
            vsql = "UPDATE avfideicomiso SET rfc = '" + request.getParameter("rfc") + "' WHERE cliente_id = '" + c.getCliente_Id() + "'";
            new DatosUtilidades().ejecutaInstruccionUpdateSQL(vsql);                              


                /**
                 *  RECUPERANDO LOS DATOS DEL DOMICILIO
                 */

                String calle = request.getParameter("calle");
                String exterior = request.getParameter("exterior");
                String interior = request.getParameter("interior");
                String colonia = request.getParameter("colonia");
                String cp = request.getParameter("cp");
                String estado_prov = request.getParameter("estado");
                String paisDomicilio = request.getParameter("pais");
                String delegacion = request.getParameter("delegacion");
                
                /*ASIGNACIO DE LA CARGA
                String archivoDomicilio = request.getParameter("archivoDomicilio");
                */
                
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
                domicilio.setCiudad(""); //no existe en form y bd es not null
                domicilio.setPais( new Pais(paisDomicilio) );
                domicilio.setImagencomprobantedom(imagenComproanteDomicilio);
                domicilio.setDelegacionMunicipio(delegacion);
                new DatosDomicilio().insertarDomicilio(domicilio,perfilito,usuario);
                
                if ( estado.equals("V") || estado.equals("A") && estadoAnterior.equals("R")){
                	try{
                        Correos correos = new Correos();
                        correos.envioCorreoStatusRaV(cliente, correo);
                    } catch ( Exception es ){
                    	System.out.println("ControlVerificacionFideicomiso.java envioCorreoStatusRaV "+es.toString());
                        
                        es.printStackTrace();
                    }
                	
                }
                
                 if ( estado.equals("P") || estado.equals("S") ){
                	 
                    boolean clienteValido = new DatosClienteRaro().getValidarCliente(c.getCliente_Id());
                    boolean personaFisicaValida = new DatosClienteRaro().getValidarPersonaFideicomiso(c.getCliente_Id());
                    boolean domocilioValido = new DatosClienteRaro().getValidarDomicilio(c.getCliente_Id());

                    if ( clienteValido && personaFisicaValida && domocilioValido){
                        // V = POR VALIDAR POR EL Ã�REA DE PLD
//                    	boolean coinci=new DatosClienteRaro().obtenerCoincidenciasListas(c);
//                        System.out.println("coincidencias "+coinci);
                        String sql = "UPDATE AVCLIENTE SET ESTADO = 'V' WHERE cliente_id = '" + c.getCliente_Id() + "'";
                        new DatosUtilidades().ejecutaInstruccionUpdateSQL(sql);
//                       Mensajes.mensaje =  "Le confirmamos que ha ingresado debidamente la informaciÃ³n solicitada a nuestro portal. \n" ;
//                        Mensajes.mensaje += "Se harÃ¡ la revisiÃ³n pertinente de sus documentos en un lapso de 24 a 48 hrs hÃ¡biles, estaremos en contacto con usted para informarle acerca del estatus.";
//                        Correo correos = new Correo();
//                        //correos.envioCorreoClienteValido(nombreCliente, correo); //se le notifica al cliente que su informacion sera validada
//
//                        //Se le notifica al personal de PLD que la informaciï¿½n debe ser validada
//                        String correoDestino = new DatosUsuarioRatos().getcorreosPorValidar(c.getCliente_Id() ); 
//                        correos.envioCorreoClientePorValidar(c.getCliente_Id(), correoDestino);

                    } 
                 }
                
                 
                //Envio de correo si es cliente invalido
                String usuarioPLD = "";
                try{
                    HttpSession sesion = requestMulti.getSession();
                    UsuarioSistema us = ( UsuarioSistema) sesion.getAttribute("usuario");
                    usuarioPLD = us.getUsuario();
                } catch  ( Exception es ){
                	System.out.println("ControlVerificacionFideicomiso.java getUsuario "+es.toString());
                     es.printStackTrace();
                    usuarioPLD = "";
                }
                
                
                
                String correoPLD = new DatosUsuarioRatos().getcorreosInValidacion(usuarioPLD);
                boolean envioCorreoInvalido = true;
                boolean envioCorreoInvalido2 = true;
                String nombreUsuario = nombreCliente;
                if (estado.equals("I") && c.getMensaje() != null & !c.getMensaje().isEmpty()){
                   
                    
                    
                    
                    
                    try{
                        Correos correos = new Correos();
                        
                        envioCorreoInvalido = correos.envioCorreoClienteInvalido(nombreUsuario, c.getMensaje(), correo, correoPLD);
                        
                    } catch ( Exception es ){
                        envioCorreoInvalido = false; 
                        es.printStackTrace();
                    } }
                    else if ( estado.equals("A") ){
                   	 nombreUsuario =nombreCliente;
                   		 String noCliente=new DatosClienteRaro().getNoClientePorSalesForce(cliente);;
                   		//falta la insercion del final en contratoelectrronico con sus validaciones
                   		 try{
                           	 Correos correos = new Correos();
                           	 System.out.println("*****insertando en contrato electrónico***********");
                        		new DatosClienteRaro().insertarContratosServiciosNoExistentes(noCliente,rfc,razonsocial);
                        		System.out.println("*****intentando enviar correo electrónico de cliente inactivo a ejecutivo***********");
                           	 envioCorreoInvalido=correos.envioCorreoClienteInactivo(new DatosUsuarioRatos().getCorreoEjecutivo(cliente),cliente,razonsocial);
                           	 System.out.println("*****intentando enviar correo electrónico de cliente inactivo a ventas***********");
                           	 envioCorreoInvalido=correos.envioCorreoClienteInactivo("vengob2@efectivale.com.mx",cliente,razonsocial);
                   		 }catch(Exception e){
                           	System.out.println("error al enviar correo inactivo");
                           }
                   	 }else{
                   		
                   		 try{
                   			 Correos correos = new Correos();
                                String correoCopias= new DatosUsuarioRatos().getcorreosValidacion(cliente);
                                System.out.println("*****intentando enviar correo electrónico de cliente valido a ejecutivo y supervisor***********");

                                envioCorreoInvalido = correos.envioCorreoClienteValidoEjecutivoSupervisor(nombreUsuario, rfc, correoCopias);
                   		 }catch(Exception es){
                   			 envioCorreoInvalido = false; 
                   			System.out.println("ControlVerificacionFideicomiso.java envioCorreoClienteValidoEjecutivoSupervisor "+es.toString());
                            
                                es.printStackTrace();
                   		 }
                   	 }
                   	 
                       
                        try{
                           Correos correos = new Correos();
                           String correoCopias= new DatosUsuarioRatos().getcorreosValidacion(cliente);
                    		System.out.println("*****intentando enviar correo electrónico de cliente valido a cliente principal***********");

                           envioCorreoInvalido2= correos.envioCorreoClienteValidoPrincipal(nombreUsuario, rfc, correo);
                    		
                           
                       } catch ( Exception es ){
                           envioCorreoInvalido = false; 
                           System.out.println("ControlVerificacionFideicomiso.java envioCorreoClienteValidoPrincipal "+es.toString());
                           
                           es.printStackTrace();
                       }
                        
                        
                        
                    
//                 } else if ( estado.equals("A") ){
//                	 
//                	 if(estadoAnterior.equals("N")){
//                		 String noCliente=new DatosClienteRaro().getNoClientePorSalesForce(cliente);
//
//                		 try{
//                        	 Correos correos = new Correos();
//                        	 System.out.println("*****insertando en contrato electrónico***********");
//                        		new DatosClienteRaro().insertarContratosServiciosNoExistentes(noCliente,rfc,razonsocial);
//                        		System.out.println("*****intentando enviar correo electrónico***********");
//                           	 
//                        	 envioCorreoInvalido=correos.envioCorreoClienteInactivo(new DatosUsuarioRatos().getCorreoEjecutivo(cliente),cliente,razonsocial);
//                        	 
//                        }catch(Exception e){
//                        	System.out.println("error al enviar correo inactivo");
//                        }
//                	 }
//                	 
//                     String nombreUsuario = nombreCliente;
//                     try{
//                        Correos correos = new Correos();
//                        String correoCopias= new DatosUsuarioRatos().getcorreosValidacion(cliente);
//                        envioCorreoInvalido2= correos.envioCorreoClienteValidoPrincipal(nombreUsuario, rfc, correo);
//                        envioCorreoInvalido = correos.envioCorreoClienteValidoEjecutivoSupervisor(nombreUsuario, rfc, correoCopias);
//                        
//                    } catch ( Exception es ){
//                        envioCorreoInvalido = false; 
//                        es.printStackTrace();
//                    }
//                     
//                 }
                
//                boolean personaCambiada = false; 
//                if ( tipoPersona != null && tipoPersonaCambio != null && tipoPersona != tipoPersonaCambio ){
//                    personaCambiada = new DatosFideicomiso().cambiarPersona(tipoPersonaCambio, c);
//                }
                
                 boolean cambioPersona = false;
                if ( tipoPersona != null && tipoPersonaCambio != null && tipoPersona != tipoPersonaCambio){
                    cambioPersona = new DatosFideicomiso().cambiarPersona(tipoPersonaCambio, c);
                }
                
                
                 if ( envioCorreoInvalido){
                    
                    Mensajes.setMensaje("Se ha guardado correctamente la informaciÃ³n. \n" );
                    requestMulti.setAttribute("resultado", Mensajes.mensaje);
                    requestMulti.setAttribute("exito", "1");
                    requestMulti.getRequestDispatcher("/estatus_clientes.jsp").forward(requestMulti, response);
                } else {
                    Mensajes.setMensaje("Se ha guardado correctamente la informaciÃ³n. \n No ha sido posible el envÃ­o de la notificacion por correo electrÃ³nicio." );
                    requestMulti.setAttribute("resultado", Mensajes.mensaje);
                    requestMulti.setAttribute("exito", "1");
                    requestMulti.getRequestDispatcher("/estatus_clientes.jsp").forward(requestMulti, response);
                }
                
                
        }//if multipart
        
        //response.setContentType("text/html;charset=UTF-8");
        
           
        
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
