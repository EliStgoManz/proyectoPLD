package control;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
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
import static java.lang.System.out;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author israel.garcia
 */
@WebServlet(urlPatterns = {"/registroPersonaFisica"})

public class ControlPersonaFisica extends HttpServlet {
    
    boolean hayBeneficiario = false;
    private final String NOMBRE_USAUARIO ="##NOMBREUSUARIO" ;
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
            throws ServletException, IOException, UploadException {

            String imagenId ="";
            String imagenCedula = "";
            String imagenCurp = "";
            String imagenDeclaratoria = "";
            String archivoComprobanteDomicilio = "";
            
            //Obteniendo la sesion            
            sesion = requestMulti.getSession();               
            
        
         if (MultipartFormDataRequest.isMultipartFormData(requestMulti)){
             UploadBean upBean = new UploadBean();
             
             MultipartFormDataRequest request = new MultipartFormDataRequest(requestMulti);
             Hashtable files = request.getFiles();
             UploadFile file  = null;
             String c = request.getParameter("Cliente_Id");             
             String zipFile = null;
             try{
             //CARGA DEL ID
                upBean.setFolderstore( Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + Rutas.dirIdentificacion + Rutas.separador);
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
            	 System.out.println("ControlPersonaFisica.java archivoIdPFZip "+es.toString());
                 
                 es.printStackTrace();
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
            	 System.out.println("ControlPersonaFisica.java archivoCedulaZip "+es.toString());
                 
                 es.printStackTrace();
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
            	 System.out.println("ControlPersonaFisica.java archivoComprobanteDomZip "+es.toString());
                 
                 es.printStackTrace();
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
            	 System.out.println("ControlPersonaFisica.java archivoCurpZip "+es.toString());
                 
                 es.printStackTrace();
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
            	 System.out.println("ControlPersonaFisica.java archivoDeclaratoriaZip "+es.toString());
                 
                 es.printStackTrace();
             }
             
            
                /***
                 *  CONSTRUYENDO LA RAZ�N SOCIAL
                 */
                String Cnombre = request.getParameter("nombres");
                String Cpaterno = request.getParameter("paterno");
                String Cmaterno = request.getParameter("materno");
                String estadoAnterior;
                
                if (request.getParameter("EstatusAnterior")==null){
                	 estadoAnterior="";	
                }
                estadoAnterior=request.getParameter("EstatusAnterior");

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
                Pais telPais = new Pais(); telPais.setPais( request.getParameter("telPais") ); //c�digo de pa�s
                String correo = request.getParameter("correo");
                String estado = "P"; //se refiere al estatus
//                <String estadoCliente = request.getParameter("estadoCliente");
                String razonSocial = Cnombre + " " + Cpaterno + " " + Cmaterno ;
                int validado = 0; //si no    //esta dato nace no se recoje de la forma
                String fechaValidado = null; // null en definicion de dd
                String nodeclaroBeneficiario = request.getParameter("nobeneficiario");
                String siDeclaroBeneficiario = request.getParameter("sibeneficiario"); //not null
                String declaroOrigen = request.getParameter("declaroOrigen") ;
                String usuariovalido = null;
                String fechacorte = null;
                String mensaje = null;
                String usuarioasignado = new DatosUsuarioRatos().getUsuarioDefault();
                 
                
               
                
                
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
                
                cliente.setCliente_Id(c);
                cliente.setTipoPersona(tipoPersona);
                cliente.setFechaRegistro(fechaRegistro);
                cliente.setTipoDomicilio(tipoDomicilio);
                cliente.setTelefono( telefono );
                cliente.setPais(telPais);
                cliente.seteMail(correo);
                cliente.setEstado("P");
                cliente.setRazonSocial(razonSocial);
                cliente.setValidado(validado);
                cliente.setFechaValidado(fechaValidado);
                cliente.setUsuarioValido(usuariovalido); //ATENCI�N ESTO ES PARA QUE PASE LA REFERENCIA
                cliente.setFechaCorte(fechacorte);
                cliente.setMensaje(mensaje);
                cliente.setUsuarioAsignado(usuarioasignado); //ATENCI�N ESTO ES PARA QUE PASE LA REFERENCIA 
                cliente.setBloqueado(false);
                cliente.setFechaBloqueo(null);
                cliente.setBorrado(false);
                
//                cliente.setRiesgo("off");	
//                cliente.setDescripcion("Cliente no manda Descripcion"); 

                /**
                 *      RECUPERANDO LOS DATOS DE LA PERSONA F�SICA
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
                String delegacion=request.getParameter("delegacion");
                //String imagenId = request.getParameter("archivoIdPF");
                //String imagenCedula = request.getParameter("archivocedulaPF");

                /**
                 *      VACIANDO LOS DATOS AL OBJETO DE LA PERSONA F�SICA
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
                Pais paisDomicilio = new Pais(); 
                paisDomicilio.setPais( request.getParameter("paisDomicilio"));





                /**
                 *      VACIANDO LOS DATOS AL OBJETO DEL DOMICILIO FISCAL DE LA PERSONA F�SICA
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
                    	System.out.println("ControlPersonaFisica.java requestMulti "+es.toString());
                        
                        es.printStackTrace();
                    }
                }
                
                
                // Candado para el registro de usuario con otra sesion
                System.out.println("Usuario: " + usuario);
                System.out.println("Cliente_id: " + cliente.getCliente_Id());
                
                
                
                
                if (usuario.equals(cliente.getCliente_Id()) == false && perfilito.equals("6")){
//                	
                	System.out.println("Entrorrrr");
                	
               	 Mensajes.mensaje =  "El Usuario modificado no coincide con el usuario de la Sesi�n, \n " ;
               	 Mensajes.mensaje += "Error de sesi�n, vuelva a intentarlo.";
               	requestMulti.setAttribute("resultado", Mensajes.mensaje);
                requestMulti.setAttribute("exito", "1");
                requestMulti.getRequestDispatcher("/mensajes_login.jsp").forward(requestMulti, response);
                
                
                }else{
               
                
                //boolean agregarCliente = new DatosClienteRaro().agregarCliente(cliente);
                boolean agregarCliente = new DatosClienteRaro().insertar(cliente,perfilito,usuario);
                boolean agregarFisica = new DatosPersonaFisica().insertar(personaFisica,perfilito,usuario);
                boolean agregarDomicilio = new DatosDomicilio().insertarDomicilio(domicilio,perfilito,usuario);
                try{
                if (estadoAnterior.equals("R")){
                	
                        Correos correos = new Correos();
//                        String correoCliente= new DatosUsuarioRatos().getcorreosPorValidar(cliente.getCliente_Id());
//                        System.out.println("Correo Para Estatus RRRRR	: " + correo);
                        correos.envioCorreoStatusRaV(cliente.getCliente_Id(), correo);
                    } 
                }catch ( Exception es ){
                	System.out.println("ControlPersonaFisica.java envioCorreoStatusRaV "+es.toString());
                        es.printStackTrace();
                    }
                	
                
                
                
                //Validacion de la completitud de los formularios
                boolean clienteValido = new DatosClienteRaro().getValidarCliente(c);
                boolean personaFisicaValida = new DatosClienteRaro().getValidarPersonaFisica(c);
                boolean domocilioValido = new DatosClienteRaro().getValidarDomicilio(c); 
                
                
                    //Si la info del cliente esta completa se le setea el estatus de X = por validar 
                if ( clienteValido && personaFisicaValida && domocilioValido){
                	//new DatosClienteRaro().obtenerCoincidenciasListas(cliente);
                    if (sesion.getAttribute("perfilId").toString().compareTo("6") == 0) { // es cliente hay que hacer la asignación y mandar correo
                        // V = POR VALIDAR POR EL AREA DE PLD
                        String sql = "UPDATE AVCLIENTE SET ESTADO = 'V', usuarioasignado = '" + new ControlUsuario().getUsuarioAsignado() + "' WHERE cliente_id = '" + cliente.getCliente_Id() + "'";
                        new DatosUtilidades().ejecutaInstruccionUpdateSQL(sql);
                        Mensajes.mensaje =  "Le confirmamos que ha ingresado debidamente la información solicitada a nuestro portal. \n" ;
                        Mensajes.mensaje += "Se hará la revisión pertinente de sus documentos en un lapso de 24 a 48 hrs hábiles, estaremos en contacto con usted para informarle acerca del estatus.";
                        Correos correos = new Correos();
//                        ClaseCorreoLlamar corre = new ClaseCorreoLlamar();
                        //correos.envioCorreoClienteValido(razonSocial, correo); //se le notifica al cliente que su información será validada
//                        Se le notifica al personal de PLD que la información debe ser validada
                        String correoDestino = new DatosUsuarioRatos().getcorreosPorValidar(cliente.getCliente_Id());
                        correos.envioCorreoClientePorValidar(c, correoDestino);
//                        corre.mandarCorreo(c, correoDestino);
                        
                        
                    } else {
                        String sql = "UPDATE AVCLIENTE SET ESTADO = 'V' WHERE cliente_id = '" + cliente.getCliente_Id() + "'";
                        new DatosUtilidades().ejecutaInstruccionUpdateSQL(sql);                        
                        Mensajes.mensaje =  "Le confirmamos que ha ingresado debidamente la información solicitada a nuestro portal. \n" ;
                        Mensajes.mensaje += "Se hará la revisión pertinente de sus documentos en un lapso de 24 a 48 hrs hábiles, estaremos en contacto con usted para informarle acerca del estatus.";                        
                    }
                } else {
                    Mensajes.setMensaje("Se ha guardado correctamente la información.\n Favor de llenar la totalidad de los campos para que puedan ser enviados a validación. \n" );
                    String sql = "UPDATE AVCLIENTE SET ESTADO = 'P' WHERE cliente_id = '" + cliente.getCliente_Id() + "'";
                    new DatosUtilidades().ejecutaInstruccionUpdateSQL(sql);
                }


                try (PrintWriter out = response.getWriter()) {
                 
                    String tipo = (String) sesion.getAttribute("tipo");
                    if(tipo.compareTo("T")==0){
                        /* TODO output your page here. You may use following sample code. */
                        String laRuta = Rutas.rutaCarga + Rutas.separador + c + Rutas.separador + Rutas.dirIdentificacion + Rutas.separador;                    
                        requestMulti.setAttribute("resultado", Mensajes.mensaje);
                        requestMulti.setAttribute("exito", "1");
                        requestMulti.getRequestDispatcher("/mensajes_login.jsp").forward(requestMulti, response);
                    } else { // Es un usuario de sistema
                        Mensajes.setMensaje("Se ha guardado correctamente la información. \n" );
                        requestMulti.setAttribute("resultado", Mensajes.mensaje);
                        requestMulti.setAttribute("exito", "1");
                        requestMulti.getRequestDispatcher("/estatus_clientes.jsp").forward(requestMulti, response);                        
                    }
                    
                }
        }//is multipart
       } //Else de candado de usuario
        
    }//fin del m�todo get

    /**
     * Renombra los archivos para que los encuentre
     * 
     * @param original archivo original
     * @param directorio dir donde se guarda el archivo
     * @param cliente cliente
     * @páram extension extension
     * @return nombre del archivo renombrado
     */
//     String Renombra(UploadFile original, String directorio, String cliente, String extension){
//        File rename = new File(Rutas.rutaCarga + Rutas.separador + cliente + Rutas.separador + directorio + Rutas.separador + original.getFileName());
//        File rename_tmp = new File(Rutas.rutaCarga + Rutas.separador + cliente + Rutas.separador + directorio + Rutas.separador + directorio + "_" + Fecha.getFechaHoraSistema() + extension);
//        rename.renameTo(rename_tmp);
//        return rename_tmp.getName();
//    }    
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
            Logger.getLogger(ControlPersonaFisica.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ControlPersonaFisica.class.getName()).log(Level.SEVERE, null, ex);
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
