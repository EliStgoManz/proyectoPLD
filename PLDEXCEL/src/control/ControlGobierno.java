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
@WebServlet(name = "ControlGobierno", urlPatterns = {"/Gobierno"})
public class ControlGobierno extends HttpServlet {

        HttpSession sesion;    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param requestMulti servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest requestMulti, HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        System.out.println("aqui entro en admin por primera vez");
        String imagenExistencia = "" ;
        String imagenCedula = "";
        String imagenComrpobanteDomicilio ="";
        String imagenFId = "";
        String imagenFFacultades ="";
        String zipFile = null;
            sesion = requestMulti.getSession();                       
        
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
            	System.out.println("ControlGobierno.java request "+ex.toString());
                
                ex.printStackTrace();
            }
            
            Hashtable files = request.getFiles();
            UploadFile file  = null;
            c.setCliente_Id(request.getParameter("Cliente_Id")); 
            
            String estadoAnterior;
            
            if (request.getParameter("EstatusAnterior")==null){
            	 estadoAnterior="";	
            }
            estadoAnterior=request.getParameter("EstatusAnterior");
            
           // String perfilid=(request.getParameter("perfilId"));
            
            
             //CARGA DEL DOCUMENTO QUE ACREDITA LA EXISTENCIA 
            try{
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirAreditaacion + Rutas.separador);
             zipFile = request.getParameter("archivoExistenciaZip");
             if (zipFile.length() > 0 ) {
                byte[] data = Base64.decode(zipFile);  
                File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirAreditaacion + Rutas.separador + Rutas.dirAreditaacion + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }             
                imagenExistencia = Renombra(archivoZip, Rutas.dirAreditaacion, c.getCliente_Id(), ".zip" );
             }
             } catch ( Exception es ){
            	 System.out.println("ControlGobierno.java archivoExistenciaZip "+es.toString());
                 
                 es.printStackTrace();
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
            	 System.out.println("ControlGobierno.java archivoCedulaZip "+es.toString());
                 es.printStackTrace();
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
            	 System.out.println("ControlGobierno.java archivoDomicilioZip "+es.toString());
             
                 es.printStackTrace();
             }
            
            
            //CARGA DE LA IDENTIFICACION DEL FUNCIONARIO 
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
            	 System.out.println("ControlGobierno.java archivofIdZip "+es.toString());
                 
                 es.printStackTrace();
             }
            
            
//            //CARGA DE LA CEDULA FISCAL DEL FUNCIONARIO 
//            try{
//             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirRlCedulaFiscal + Rutas.separador);
//             zipFile = request.getParameter("archivofCedulaZip");
//             if ( zipFile.length() > 0 ){
//                byte[] data = Base64.decode(zipFile);  
//                File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirRlCedulaFiscal + Rutas.separador + Rutas.dirRlCedulaFiscal + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
//                try (OutputStream stream = new FileOutputStream(archivoZip)) {
//                   stream.write(data);
//                }             
//                imagenFCedulafiscal = Renombra(archivoZip, Rutas.dirRlCedulaFiscal, c.getCliente_Id(), ".zip" );
//             }
//             } catch ( Exception es ){
//                 es.printStackTrace();
//             }
//            
            
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
            	 System.out.println("ControlGobierno.java archivofFacultadesZip "+es.toString());
                 
                 es.printStackTrace(); 
             }
            
            
                String declaroBeneficiario = request.getParameter("declaroBeneficiario");
                String declaroOrigen = request.getParameter("declaroOrigen") ;
                /**
                 *  RECUJPERANDO LOS DATOS DEL CLIENTE
                 */
                
                String tipoPersona = request.getParameter("tipoPersona");
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

                c.setEstado("P"); //pendiente (cuando el cliente ingreso, pero no ha terminado de subir su informacion)
                c.setRazonSocial(tipoPersona);

                String  nombreCliente = request.getParameter("nombreCliente"); //es razon social
                c.setRazonSocial(nombreCliente);

                c.setValidado(0);

                c.setFechaValidado("");
                
               // String  perfilid = request.getParameter("fechaCreacion");
               // String  usuarioEdicion = request.getParameter("rfcCliente");
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
                c.setUsuarioAsignado( new DatosUsuarioRatos().getUsuarioDefault() ) ;
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
                    	System.out.println("ControlGobierno.java requestMulti "+es.toString());
                        
                        es.printStackTrace();
                    }
                }
                
             // Candado para el registro de usuario con otra sesion
                System.out.println("Usuario: " + usuario);
                System.out.println("Cliente_id: " + c.getCliente_Id());
                if (usuario.equals(c.getCliente_Id()) == false && perfilito.equals("6")){
//                	
                	System.out.println("Entrorrrr");
                	
               	 Mensajes.mensaje =  "El Usuario modificado no coincide con el usuario de la Sesi髇, \n " ;
               	 Mensajes.mensaje += "Error de sesi髇, vuelva a intentarlo.";
               	requestMulti.setAttribute("resultado", Mensajes.mensaje);
                requestMulti.setAttribute("exito", "1");
                requestMulti.getRequestDispatcher("/mensajes_login.jsp").forward(requestMulti, response);
                
                
                }else{
                
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
                String estado = "P"; //se refiere al estatus
//                String  estado = request.getParameter("estadoCliente");
                String paisDomicilio = request.getParameter("pais");
                String  paisCliente = request.getParameter("nacionalidadCliente");
                String  archivoDomicilio = request.getParameter("archivoDomicilio");
                String delegacion= request.getParameter("delegacion");

                String  fnombre = request.getParameter("fnombre");
                String  fpaterno = request.getParameter("fpaterno");
                String  fmaterno = request.getParameter("fmaterno");
                String  fFechaNacimiento = request.getParameter("fFechaNacimiento");
                String  fRFC = request.getParameter("fRFC");
                String  fCURP = request.getParameter("fCURP");
                TipoIdentificacion tipoIdentificacion = new TipoIdentificacion(); 
                tipoIdentificacion.setIdentifica_id( request.getParameter("tipoIdentificacion"));
                String  fNumeroId = request.getParameter("fNumeroId");
                String  fotroTipoId = request.getParameter("fotroTipoId");
                String  fautoridadEmite = request.getParameter("fautoridadEmite");
                String estadoDomicilio = request.getParameter("estado");
                /* INFORMACION RECUPARADA DE LA CARGA
                String  archivofId = request.getParameter("archivofId");
                String  archivofCedula = request.getParameter("archivofCedula");
                String  archivofFacultades = request.getParameter("archivofFacultades");
                */

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
                g.setRlrfc(fRFC);
                g.setIdentificacion(tipoIdentificacion);   //DEL SELECT INE | PASAPORTE | ETC.
                //g.setRlidentificaciontipo(ftipoIdentificacion); 
                g.setRlautoridademiteid(fautoridadEmite);
                g.setRlnumeroid(fNumeroId);
                g.setRlcurp(fCURP);
                g.setRlidentificaciontipo(fotroTipoId); 
                g.setFecharegistro("");
                g.setImagenacreditacion(imagenExistencia);
                g.setImagencedulafiscal(imagenCedula);
                g.setImagenrlid(imagenFId);
                g.setImagenrlfacultades(imagenFFacultades);
//                String usuario="";
//                try{
//
//                    if ( requestMulti.getSession().getAttribute("tipo") != null &&  requestMulti.getSession().getAttribute("tipo")  == "T"){
//                        UsuarioTransitivo ut = (UsuarioTransitivo)requestMulti.getSession().getAttribute("usuario") ;
//                        usuario = ut.getCliente().getCliente_Id();
//                       
//                    } else {
//                        ////UsuarioSistema us = (UsuarioSistema)request.getSession().getAttribute("usuario") ;
//                        UsuarioSistema us = (UsuarioSistema)requestMulti.getSession().getAttribute("usuario") ;
//                       usuario = us.getUsuario();
//                       
//                    }
//                } catch (Exception es ){
//                    es.printStackTrace();
//                }
                
                
//                boolean agregarGobierno = new DatosGobierno().insertar(g,"1",usuario);
                boolean agregarGobierno = new DatosGobierno().insertar(g,perfilito,usuario);


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
                domicilio.setImagencomprobantedom(imagenComrpobanteDomicilio);
                domicilio.setDelegacionMunicipio(delegacion);
                boolean agregarDomicilio = new DatosDomicilio().insertarDomicilio(domicilio,perfilito,usuario);
                
                
                
                //Validacion de la completitud de los formularios
                boolean clienteValido = new DatosClienteRaro().getValidarCliente(c.getCliente_Id());
                boolean personaFisicaValida = new DatosClienteRaro().getValidarPersonaGobierno(c.getCliente_Id());
                boolean domocilioValido = new DatosClienteRaro().getValidarDomicilio(c.getCliente_Id());
                
                try{
                	
                if (estadoAnterior.equals("R")){
                	
                        Correos correos = new Correos();
//                        String correoCliente= new DatosUsuarioRatos().getcorreosPorValidar(cliente.getCliente_Id());
//                        System.out.println("Correo Para Estatus RRRRR	: " + correo);
                        correos.envioCorreoStatusRaV(c.getCliente_Id(), correo);
                    }
                } catch ( Exception es ){
                	System.out.println("ControlGobierno.java envioCorreoStatusRaV "+es.toString());
                    
                        es.printStackTrace();
                }
                	
                
                
                
                if ( clienteValido && personaFisicaValida && domocilioValido){
                    if (sesion.getAttribute("perfilId").toString().compareTo("6") == 0) { // es cliente hay que hacer la asignaci贸n y mandar correo                    
                        // V = POR VALIDAR POR EL AREA DE PLD
//                       	boolean coinci=new DatosClienteRaro().obtenerCoincidenciasListas(c);
//                        System.out.println("coincidencias "+coinci);
                        String sql = "UPDATE AVCLIENTE SET ESTADO = 'V', usuarioasignado = '" + new ControlUsuario().getUsuarioAsignado() + "' WHERE cliente_id = '" + c.getCliente_Id() + "'";
                        new DatosUtilidades().ejecutaInstruccionUpdateSQL(sql);
                        Mensajes.mensaje =  "Le confirmamos que ha ingresado debidamente la informaci贸n solicitada a nuestro portal. \n" ;
                        Mensajes.mensaje += "Se har谩 la revisi贸n pertinente de sus documentos en un lapso de 24 a 48 hrs h谩biles, estaremos en contacto con usted para informarle acerca del estatus.";
                        Correos correos = new Correos();
//                        ClaseCorreoLlamar corre = new ClaseCorreoLlamar();
                        //correos.envioCorreoClienteValido(nombreCliente, correo); //se le notifica al cliente que su informacion sera validada
                        //Se le notifica al personal de PLD que la informacion debe ser validada
                        String correoDestino = new DatosUsuarioRatos().getcorreosPorValidar(c.getCliente_Id()); 
                        correos.envioCorreoClientePorValidar(c.getCliente_Id(), correoDestino);
//                          corre.mandarCorreo(c.getCliente_Id(), correoDestino);
                    } else {
//                    	boolean coinci=new DatosClienteRaro().obtenerCoincidenciasListas(c);
                        String sql = "UPDATE AVCLIENTE SET ESTADO = 'V' WHERE cliente_id = '" + c.getCliente_Id() + "'";
                        new DatosUtilidades().ejecutaInstruccionUpdateSQL(sql);                        
                        Mensajes.mensaje =  "Le confirmamos que ha ingresado debidamente la informaci贸n solicitada a nuestro portal. \n" ;
                        Mensajes.mensaje += "Se har谩 la revisi贸n pertinente de sus documentos en un lapso de 24 a 48 hrs h谩biles, estaremos en contacto con usted para informarle acerca del estatus.";                        
                    }                    
                } else {
                    Mensajes.setMensaje("Se ha guardado correctamente la informaci贸n.\n Favor de llenar la totalidad de los campos para que puedan ser enviados a validaci贸n. \n" );
                    String sql = "UPDATE AVCLIENTE SET ESTADO = 'P' WHERE cliente_id = '" + c.getCliente_Id() + "'";
                    new DatosUtilidades().ejecutaInstruccionUpdateSQL(sql);
                }
                
                
                
                
                
                
    }//fin if multipart
        
        
        
        
        
        
        
        
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            /* TODO output your page here. You may use following sample code. */
            
                    String tipo = (String) sesion.getAttribute("tipo");
                    if(tipo.compareTo("T")==0){            
                        requestMulti.setAttribute("resultado", Mensajes.mensaje);
                        requestMulti.setAttribute("exito", "1");
                        requestMulti.getRequestDispatcher("/mensajes_login.jsp").forward(requestMulti, response);
                    } else { // Es un usuario de sistema
                        Mensajes.setMensaje("Se ha guardado correctamente la informaci贸n. \n" );
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

    String Renombra(UploadFile archivoZip, String directorio, String c, String zip){
        File rename = new File(Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + directorio + Rutas.separador + archivoZip.getFileName());
        File rename_tmp = new File(Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + directorio + Rutas.separador + directorio + "_" + Fecha.getFechaHoraSistema() + "_tmp" + zip);
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
