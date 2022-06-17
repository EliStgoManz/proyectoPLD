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
import datosRatos.DatosFideicomiso;
import datosRatos.DatosUsuarioRatos;
import datosRatos.DatosUtilidades;
import entidad.Cliente;
import entidad.Domicilio;
import entidad.Fideicomiso;
import entidad.Pais;
import entidad.TipoIdentificacion;
import entidad.UsuarioSistema;
import entidad.UsuarioTransitivo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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
import utilidades.Cadenas;
import utilidades.Fecha;
import utilidades.Mensajes;
import utilidades.Rutas;

/**
 *
 * @author Israel Osiris Garcia
 */
@WebServlet(name = "ControlRegistroFideicomiso", urlPatterns = {"/RegistroFideicomiso"})
public class ControlRegistroFideicomiso extends HttpServlet {

        HttpSession sesion;    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest requestMulti, HttpServletResponse response)
            throws ServletException, IOException {
        
        String imagenActa = "";
        String imagenCedula= "";
        String imagenComproanteDomicilio= "";
        String imagenRlId= "";
//        String imagenRlCedula= "";
        String imagenRlPoder= "";
            sesion = requestMulti.getSession();                       
        if (MultipartFormDataRequest.isMultipartFormData(requestMulti)){ 
            
            
        
                
                /**
                 *  RECUJPERANDO LOS DATOS DEL CLIENTE
                 */
                Cliente c = new Cliente();
                

                // GESTION DE CARGA DE ARCHIVOS
            UploadBean upBean = new UploadBean();
            MultipartFormDataRequest request = null;
            try {
                request = new MultipartFormDataRequest(requestMulti);
            } catch (UploadException ex) {
                ex.printStackTrace();
            }
                
            
             Hashtable files = request.getFiles();
            UploadFile file  = null;
            String zipFile = null;
            
            c.setCliente_Id(request.getParameter("Cliente_Id"));            
            //ARCHIVOS PARA GOB.
             //CARGA DEL DOCUMENTO QUE ACREDITA LA EXISTENCIA 
//            try{
//             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirAreditaacion + Rutas.separador);
//             file = (UploadFile) files.get("archivoExistencia");
//             if ( file.getFileSize() > 0 ){
//                String extension = "." + new Cadenas().getExtension(file.getFileName());                 
////                file.setFileName( Rutas.dirAreditaacion + "_" + Fecha.getFechaHoraSistema() + extension  ) ;
////                upBean.store(request, "archivoExistencia");
////                imagenExistencia = Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirAreditaacion + Rutas.separador + file.getFileName();
//             }
//             } catch ( Exception es ){
//                 es.printStackTrace();
//             }
            
            
            //CARGA DE LA DOC ACREDITACIONDE FACULTADES 
//            try{
//             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirFacultades + Rutas.separador);
//             file = (UploadFile) files.get("archivofFacultades");
//             if ( file.getFileSize() > 0 ){
//                String extension = "." + new Cadenas().getExtension(file.getFileName());                 
////                file.setFileName( Rutas.dirFacultades + "_" + Fecha.getFechaHoraSistema() + extension ) ;
////                upBean.store(request, "archivofFacultades");
////                imagenFFacultades = Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirFacultades + Rutas.separador + file.getFileName();
//             }
//             } catch ( Exception es ){
//                 es.printStackTrace();
//             }
            // FIN ARCHIVOS GOB
            
            
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
            	 System.out.println("ControlRegistroFideicomiso.java archivoActaZip "+es.toString());
                 
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
            	 System.out.println("ControlRegistroFideicomiso.java archivoCedulaZip "+es.toString());
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
            	 System.out.println("ControlRegistroFideicomiso.java archivoDomicilioZip "+es.toString());
                 
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
            	 System.out.println("ControlRegistroFideicomiso.java archivoRlIdentificacionZip "+es.toString());
                 es.printStackTrace();
             }
          
            
            //CARGA CEDULA REPRESENTANTE
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
//                 es.printStackTrace();
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
            	 System.out.println("ControlRegistroFideicomiso.java archivoPoderNotarialZip "+es.toString());
                 
                 es.printStackTrace();
             }
            
            
            
            String declaroBeneficiario = request.getParameter("declaroBeneficiario");
            String declaroOrigen = request.getParameter("declaroOrigen") ;
            
                String tipoPersona = request.getParameter("tipoPersona");
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

                c.setEstado("P"); //por validar ES EL ESTATUS        

                String  nombreCliente = request.getParameter("razonSocial"); //es razon social
                c.setRazonSocial(nombreCliente);

                c.setValidado(0); // 0  = NO VALIDO

                c.setFechaValidado("");

                
                String estadoAnterior;
                
                if (request.getParameter("EstatusAnterior")==null){
                	 estadoAnterior="";	
                }
                estadoAnterior=request.getParameter("EstatusAnterior");
                
                
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
                c.setUsuarioValido(""); //null
                c.setFechaCorte(""); //null
                c.setMensaje(""); //null
                c.setUsuarioAsignado( new DatosUsuarioRatos().getUsuarioDefault() );
                c.setRiesgo("off");	
                c.setDescripcion("Cliente no manda Descripcion");
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
                        es.printStackTrace();
                        System.out.println("ControlRegistroFideicomiso.java requestMulti "+es.toString());
                        
                    }
                }
             // Candado para el registro de usuario con otra sesion
                System.out.println("Usuario: " + usuario);
                System.out.println("Cliente_id: " + c.getCliente_Id());
                
                if (usuario.equals(c.getCliente_Id()) == false && perfilito.equals("6")){
//                	
                	System.out.println("Entrorrrr");
                	
               	 Mensajes.mensaje =  "El Usuario modificado no coincide con el usuario de la Sesión, \n " ;
               	 Mensajes.mensaje += "Error de sesión, vuelva a intentarlo.";
               	requestMulti.setAttribute("resultado", Mensajes.mensaje);
                requestMulti.setAttribute("exito", "1");
                requestMulti.getRequestDispatcher("/mensajes_login.jsp").forward(requestMulti, response);
                
                
                }else{
                
                
                
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
                String fecharegistro= ""; //DESDE BASE DE DATO
                String institucionfiduciaria=request.getParameter("NombreFideicomiso");
                
                
                
                
                String rlNoPoder=request.getParameter("nopoder");
	            String rlNotaria=request.getParameter("rlnotaria");
	            String rlFechaNotarial=request.getParameter("rlfechaNotarial");
	            String noEscritura=request.getParameter("noescritura");
	            String fechaNotarial=request.getParameter("fechaNotarial");
	            String notaria=request.getParameter("notaria");
                // ASIGNACIÃ³N A PARTIR DE LA CARGA
//                String imagenactaconstitutiva=request.getParameter("actaConstitutiva");
//                String imagencedulafiscal=request.getParameter("cedulaFiscal");
//                String imagenrlid=request.getParameter("archivofId");
//                String imagenrlcedulafiscal=request.getParameter("archivofCedula");
//                String imagenrlpodernotarial=request.getParameter("archivofPoderNotarial");
//                
               

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



                /**
                 *  RECUPERANDO LOS DATOS DEL DOMICILIO
                 */

                String calle = request.getParameter("calle");
                String exterior = request.getParameter("exterior");
                String interior = request.getParameter("interior");
                String colonia = request.getParameter("colonia");
                String cp = request.getParameter("cp");
                String estado = "P"; //se refiere al estatus
                String estadoDomicilio = request.getParameter("estado");
//                String estado = request.getParameter("estadoCliente");
                String paisDomicilio = request.getParameter("pais");
                String delegacion=request.getParameter("delegacion");
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
                domicilio.setEstado_prov(estadoDomicilio);
                domicilio.setPais( new Pais(paisDomicilio) );
                domicilio.setImagencomprobantedom(imagenComproanteDomicilio);
                domicilio.setDelegacionMunicipio(delegacion);
                new DatosDomicilio().insertarDomicilio(domicilio,perfilito,usuario);
                
                
                
                //Validacion de la completitud de los formularios
                boolean clienteValido = new DatosClienteRaro().getValidarCliente(c.getCliente_Id());
                boolean personaFisicaValida = new DatosClienteRaro().getValidarPersonaFideicomiso(c.getCliente_Id());
                boolean domocilioValido = new DatosClienteRaro().getValidarDomicilio(c.getCliente_Id());
                try{
                if (estadoAnterior.equals("R")){
                	
                        Correos correos = new Correos();
//                        String correoCliente= new DatosUsuarioRatos().getcorreosPorValidar(cliente.getCliente_Id());
//                        System.out.println("Correo Para Estatus RRRRR	: " + correo);
                        correos.envioCorreoStatusRaV(c.getCliente_Id(), correo);
                    } 
                }catch ( Exception es ){
                	System.out.println("ControlRegistroFideicomiso.java envioCorreoStatusRaV "+es.toString());
                        es.printStackTrace();
                }
                	
                
                
                
                if ( clienteValido && personaFisicaValida && domocilioValido){
                    if (sesion.getAttribute("perfilId").toString().compareTo("6") == 0) { // es cliente hay que hacer la asignaciÃ³n y mandar correo                                        
                        // V = POR VALIDAR POR EL Ã�REA DE PLD
//                       	boolean coinci=new DatosClienteRaro().obtenerCoincidenciasListas(c);
//                        System.out.println("coincidencias "+coinci);
                        String sql = "UPDATE AVCLIENTE SET ESTADO = 'V', usuarioasignado = '" + new ControlUsuario().getUsuarioAsignado() + "' WHERE cliente_id = '" + c.getCliente_Id() + "'";
                        new DatosUtilidades().ejecutaInstruccionUpdateSQL(sql);
                        Mensajes.mensaje =  "Le confirmamos que ha ingresado debidamente la informaciÃ³n solicitada a nuestro portal. \n" ;
                        Mensajes.mensaje += "Se harÃ¡ la revisiÃ³n pertinente de sus documentos en un lapso de 24 a 48 hrs hÃ¡biles, estaremos en contacto con usted para informarle acerca del estatus.";
                        Correos correos = new Correos();
//                        ClaseCorreoLlamar corre = new ClaseCorreoLlamar();
                        //correos.envioCorreoClienteValido(nombreCliente, correo); //se le notifica al cliente que su informacion sera validada
                        //Se le notifica al personal de PLD que la informaciï¿½n debe ser validada
                        String correoDestino = new DatosUsuarioRatos().getcorreosPorValidar(c.getCliente_Id() ); 
                        correos.envioCorreoClientePorValidar(c.getCliente_Id(), correoDestino);
//                        corre.mandarCorreo(c.getCliente_Id(), correoDestino);
                    } else {
//                    	boolean coinci=new DatosClienteRaro().obtenerCoincidenciasListas(c);
                        String sql = "UPDATE AVCLIENTE SET ESTADO = 'V' WHERE cliente_id = '" + c.getCliente_Id() + "'";
                        new DatosUtilidades().ejecutaInstruccionUpdateSQL(sql);                        
                        Mensajes.mensaje =  "Le confirmamos que ha ingresado debidamente la informaciÃ³n solicitada a nuestro portal. \n" ;
                        Mensajes.mensaje += "Se harÃ¡ la revisiÃ³n pertinente de sus documentos en un lapso de 24 a 48 hrs hÃ¡biles, estaremos en contacto con usted para informarle acerca del estatus.";                        
                    }                     
                } else {
                    Mensajes.setMensaje("Se ha guardado correctamente la informaciÃ³n.\n Favor de llenar la totalidad de los campos para que puedan ser enviados a validaciÃ³n. \n" );
                    String sql = "UPDATE AVCLIENTE SET ESTADO = 'P' WHERE cliente_id = '" + c.getCliente_Id() + "'";
                    new DatosUtilidades().ejecutaInstruccionUpdateSQL(sql);
                }
                
        }//if multipart
        
        //response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            /* TODO output your page here. You may use following sample code. */
            
                    String tipo = (String) sesion.getAttribute("tipo");
                    if(tipo.compareTo("T")==0){           
                        requestMulti.setAttribute("resultado", Mensajes.mensaje);
                        requestMulti.setAttribute("exito", "1");
                        requestMulti.getRequestDispatcher("/mensajes_login.jsp").forward(requestMulti, response);
                    } else { // Es un usuario de sistema
                        Mensajes.setMensaje("Se ha guardado correctamente la informaciÃ³n. \n" );
                        requestMulti.setAttribute("resultado", Mensajes.mensaje);
                        requestMulti.setAttribute("exito", "1");
                        requestMulti.getRequestDispatcher("/estatus_clientes.jsp").forward(requestMulti, response);                        
                    }            
        }
        }//candado de sesion
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
        processRequest(request, response);
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
        processRequest(request, response);
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
