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
 * @author israel.garcia
 */
@WebServlet(name = "ControlPersonaMoral", urlPatterns = {"/registroPersonaMoral"})
public class ControlPersonaMoral extends HttpServlet {

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
        //response.setContentType("text/html;charset=UTF-8");
        
        String imagenActa ="";
        String imagenCedula ="";
        String imagenComprobanteDomicilio  ="";
        String imagenRlId = "";
        String imagenRlPoderNotarial =""; 
        String imagenDeclaratoria ="";
        String imagenCompDomicilio ="";
            sesion = requestMulti.getSession();                       
        
        
        Cliente cliente = new Cliente();
        
        
        if (MultipartFormDataRequest.isMultipartFormData(requestMulti)){ 
            


            /**
             * GESTIONANDO LA CARGA DE LOS ARCHIVOS
             */
            
            
            
            UploadBean upBean = new UploadBean();
            MultipartFormDataRequest request = null;
            try {
                request = new MultipartFormDataRequest(requestMulti);
            } catch (UploadException ex) {
            	System.out.println("ControlPersonaMoral.java request "+ex.toString());
                
                ex.printStackTrace();
            }
             
            Hashtable files = request.getFiles();
            UploadFile file  = null;
            String zipFile = null;
            cliente.setCliente_Id(request.getParameter("Cliente_Id")); 
            String c = request.getParameter("Cliente_Id"); 
            String estadoAnterior;
            
            if (request.getParameter("EstatusAnterior")==null){
            	 estadoAnterior="";	
            }
            estadoAnterior=request.getParameter("EstatusAnterior");
            
            //ARCHIVOS PARA GOB.
             //CARGA DEL DOCUMENTO QUE ACREDITA LA EXISTENCIA x
//            try{
//             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id() + Rutas.separador + Rutas.dirAreditaacion + Rutas.separador);
//             file = (UploadFile) files.get("archivoExistencia");
//             if ( file.getFileSize() > 0 ){
//                  String extension = "." + new Cadenas().getExtension(file.getFileName());
////                file.setFileName( Rutas.dirAreditaacion + "_" + Fecha.getFechaHoraSistema() + extension  ) ;
////                upBean.store(request, "archivoExistencia");
////                imagenExistencia = Rutas.rutaCarga + Rutas.separador + c.getCliente_Id() + Rutas.separador + Rutas.dirAreditaacion + Rutas.separador + file.getFileName();
//             }
//             } catch ( Exception es ){
//            	 System.out.println("ControlPersonaMoral.java request "+ex.toString());
//                 
//                 es.printStackTrace();
//             }
//            
            
            //CARGA DE LA DOC ACREDITACIONDE FACULTADES 
//            try{
//             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id() + Rutas.separador + Rutas.dirFacultades + Rutas.separador);
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
            
             //CARGA DEl ACTA CONSTITUTIVA
            try{
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id() + Rutas.separador + Rutas.dirActaConstitutiva + Rutas.separador);
             zipFile = request.getParameter("archivoActaZip");
             //if ( file.getFileSize() > 0 ){
              if (zipFile.length() > 0 ) {
                byte[] data = Base64.decode(zipFile);  
                File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id() + Rutas.separador + Rutas.dirActaConstitutiva + Rutas.separador + Rutas.dirActaConstitutiva + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }             
                imagenActa = Renombra(archivoZip, Rutas.dirActaConstitutiva, cliente.getCliente_Id(), ".zip" );
             }
             } catch ( Exception es ){
            	 System.out.println("ControlPersonaMoral.java archivoActaZip "+es.toString());
                 
                 es.printStackTrace();
             }
            
            
             //CARGA CEDULA FISCAL
            try{
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id() + Rutas.separador + Rutas.dirCedula + Rutas.separador);
             zipFile = request.getParameter("archivoCedulaZip");
             if ( zipFile.length() > 0 ){
                byte[] data = Base64.decode(zipFile); 
                File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id() + Rutas.separador + Rutas.dirCedula + Rutas.separador + Rutas.dirCedula + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }
                imagenCedula = Renombra(archivoZip, Rutas.dirCedula, cliente.getCliente_Id(), ".zip" );
             }
             } catch ( Exception es ){
            	 System.out.println("ControlPersonaMoral.java archivoCedulaZip "+es.toString());
                 
                 es.printStackTrace();
             }
             
            //CARGA COMPROBANTE DE DOMICILIO
            try{
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id() + Rutas.separador + Rutas.dirComprobanteDom + Rutas.separador);
             zipFile = request.getParameter("archivoDomicilioZip");
             if ( zipFile.length() > 0 ){
                byte[] data = Base64.decode(zipFile);
                File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id() + Rutas.separador + Rutas.dirComprobanteDom + Rutas.separador + Rutas.dirComprobanteDom + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }
                imagenCompDomicilio = Renombra(archivoZip, Rutas.dirComprobanteDom, cliente.getCliente_Id(), ".zip" );
             }
             } catch ( Exception es ){
            	 System.out.println("ControlPersonaMoral.java archivoDomicilioZip "+es.toString());
                 
                 es.printStackTrace();
             }
            
            //CARGA ID REPRESENTANTE LEGAL
            try{
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id() + Rutas.separador + Rutas.dirRlIdentificacion + Rutas.separador);
             zipFile = request.getParameter("archivoRlIdentificacionZip");
             if ( zipFile.length() > 0 ){
                byte[] data = Base64.decode(zipFile);
                File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id() + Rutas.separador + Rutas.dirRlIdentificacion + Rutas.separador + Rutas.dirRlIdentificacion + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }
                imagenRlId = Renombra(archivoZip, Rutas.dirRlIdentificacion, cliente.getCliente_Id(), ".zip" );
             }
             } catch ( Exception es ){
            	 System.out.println("ControlPersonaMoral.java archivoRlIdentificacionZip "+es.toString());
                 
                 es.printStackTrace();
            }
            
            
            
            //CARGA PODER NOTARIAL REPRESENTANTE LEGAl
            try{
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id() + Rutas.separador + Rutas.dirRlPoderNotarial + Rutas.separador);
             zipFile = request.getParameter("archivoPoderNotarialZip");
             if ( zipFile.length() > 0 ){
                byte[] data = Base64.decode(zipFile);
                File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id() + Rutas.separador + Rutas.dirRlPoderNotarial + Rutas.separador + Rutas.dirRlPoderNotarial + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }
                imagenRlPoderNotarial = Renombra(archivoZip, Rutas.dirRlPoderNotarial, cliente.getCliente_Id(), ".zip" );
             }
             } catch ( Exception es ){
            	 System.out.println("ControlPersonaMoral.java archivoPoderNotarialZip "+es.toString());
                 
                 es.printStackTrace();
            }
            
             //CARGA PODER NOTARIAL DECLARATORIA
            try{
             upBean.setFolderstore(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id() + Rutas.separador + Rutas.dirDeclaratoria + Rutas.separador);
             zipFile = request.getParameter("archivoDeclaratoriaZip");
             if ( zipFile.length() > 0 ){
                byte[] data = Base64.decode(zipFile);
                File archivoZip = new File(Rutas.rutaCarga + Rutas.separador + cliente.getCliente_Id() + Rutas.separador + Rutas.dirDeclaratoria + Rutas.separador + Rutas.dirDeclaratoria + "_" + Fecha.getFechaHoraSistema() + "_tmp.zip");
                
                try (OutputStream stream = new FileOutputStream(archivoZip)) {
                   stream.write(data);
                }
                imagenDeclaratoria = Renombra(archivoZip, Rutas.dirDeclaratoria, cliente.getCliente_Id(), ".zip" );
             }
             } catch ( Exception es ){
            	 System.out.println("ControlPersonaMoral.java archivoDeclaratoriaZip "+es.toString());
                 es.printStackTrace();
            }
            
            
            
             
            String nodeclaroBeneficiario = request.getParameter("nobeneficiario");
            String siDeclaroBeneficiario = request.getParameter("sibeneficiario");
            String declaroOrigen = request.getParameter("declaroOrigen") ;

            /**
             *      RECUPERANDO LOS DATOS DE LA PERSONA MORAL
             */


            String tipoPersona = request.getParameter("tipoPersona");
            
            

            cliente.setTipoPersona(tipoPersona);
           
            cliente.setFechaRegistro("");
            
            cliente.setTelefono( request.getParameter("telefono"));
            Pais paisCliente = new Pais();
            paisCliente.setPais( request.getParameter("telPais"));
            cliente.setPais(paisCliente);
            cliente.seteMail( request.getParameter("correo"));
            cliente.setEstado("P");
            cliente.setRazonSocial( request.getParameter("razonSocial"));
            cliente.setValidado(0);
            cliente.setFechaValidado("");
            
            try{
                if ( paisCliente.getPais().trim().equals("MX") ){
                    cliente.setTipoDomicilio("N");
                } else {
                    cliente.setTipoDomicilio("E");   
                }
            } catch (Exception es) {
                cliente.setTipoDomicilio("N");
            }
            
            //*AQUITA ESTA LA BANDERA PARA SABER QUE SI EXISTE EL BENEFICIARIO

            try{
                  if ( siDeclaroBeneficiario != null ){
                      cliente.setDeclaroBeneficiario(1); //indica que si hay beneficiario
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

            cliente.setUsuarioValido(""); //null
            cliente.setFechaCorte(""); //null
            cliente.setMensaje(""); //null
            cliente.setUsuarioAsignado( new DatosUsuarioRatos().getUsuarioDefault() );
            cliente.setRiesgo("off");	
            cliente.setDescripcion("Cliente no manda Descripcion");
            
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
                    	System.out.println("ControlPersonaMoral.java requestMulti "+es.toString());
                        
                        es.printStackTrace();
                    }
                }
                
             // Candado para el registro de usuario con otra sesion
                System.out.println("Usuario: " + usuario);
                System.out.println("Cliente_id: " + cliente.getCliente_Id());
                
                if (usuario.equals(cliente.getCliente_Id()) == false && perfilito.equals("6")){
//                	
                	System.out.println("Entrorrrr");
                	
               	 Mensajes.mensaje =  "El Usuario modificado no coincide con el usuario de la Sesi髇, \n " ;
               	 Mensajes.mensaje += "Error de sesi髇, vuelva a intentarlo.";
               	requestMulti.setAttribute("resultado", Mensajes.mensaje);
                requestMulti.setAttribute("exito", "1");
                requestMulti.getRequestDispatcher("/mensajes_login.jsp").forward(requestMulti, response);
                
                
                }else{
                
            boolean agregarCliente = new DatosClienteRaro().insertar(cliente,perfilito,usuario);


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
            TipoIdentificacion tipoIdentificacion = new TipoIdentificacion(); 
            tipoIdentificacion.setIdentifica_id( request.getParameter("tipoIdentificacion"));
                
            String rlautoridademiteid = request.getParameter("autoridadEmite");
            String rlnumeroid = request.getParameter("numeroId");
            String rlcurp = request.getParameter("CURP");
            String rlidentificaciontipo = request.getParameter("otroTipoId");
            String giro_id = request.getParameter("giro");
                Giro giro = new Giro ( giro_id );
            String fecharegistro = request.getParameter("now"); //recuperaado de base de datos
            String rlNoPoder=request.getParameter("nopoder");
            String rlNotaria=request.getParameter("rlnotaria");
            String rlFechaNotarial=request.getParameter("rlfechaNotarial");
            String noEscritura=request.getParameter("noescritura");
            String fechaNotarial=request.getParameter("fechaNotarial");
            String notaria=request.getParameter("notaria");

            /*
            
            Estas variables ya fueron asignadas en la reques del file
            
            String imagenactaconstitutiva = request.getParameter("archivoActa");
            String imagencedulafiscal = request.getParameter("archivoCedulaEmpresa");
            String imagenrlid = request.getParameter("archivoIDRepresentante");
            String imagenrlcedulafiscal = request.getParameter("archivoCedulaRepresentante");
            String imagenrlpodernotarial = request.getParameter("archivoPoderNotarial");
            */

            /**
             * AGREGANDO LOS ATRIBUTOS DE LA PERSONAL MORAL AL OBJETO PERSONAL MORAL
             */

            PersonaMoral personaMoral = new PersonaMoral(
                    cliente,
                    razonsocial, 
                    rfc, 
                    fechaconstitucion, 
                    paisEmpresa, 
                    rlnombre, 
                    rlapellidopaterno, 
                    rlapellidomaterno, 
                    rlfechanacimiento, 
                    rlrfc, 
                    tipoIdentificacion, 
                    rlautoridademiteid, 
                    rlnumeroid, 
                    rlcurp, 
                    rlidentificaciontipo, 
                    giro,
                    fecharegistro, 
                    imagenActa, 
                    imagenCedula, 
                    imagenRlId, 
                    imagenRlPoderNotarial,
                    imagenDeclaratoria,
                    rlNoPoder,
                    rlNotaria,
                    rlFechaNotarial,
                    noEscritura,
                    fechaNotarial,
                    notaria
            );

            System.out.println("rlnopoder "+   rlNoPoder);
            System.out.println("rlNotaria "+   rlNotaria);
            System.out.println("rlFechaNotarial "+   rlFechaNotarial);
            System.out.println("noEscritura "+   noEscritura);
            System.out.println("fechaNotarial "+   fechaNotarial);
            System.out.println("notaria "+   notaria);
            new DatosPersonaMoral().insertar(personaMoral,perfilito,usuario);


            /**
             *  RECUPERANDO LOS DATOS DEL DOMICILIO
             */

            String calle = request.getParameter("calle");
            String exterior = request.getParameter("exterior");
            String interior = request.getParameter("interior");
            String colonia = request.getParameter("colonia");
            String cp = request.getParameter("cp");
            String delegacion= request.getParameter("delegacion");
            //String estado = request.getParameter("estadoCliente");
            String estado = "P"; //se refiere al estatus
            String estadoDomicilio = request.getParameter("estado");
            String paisDomicilio = request.getParameter("paisDomicilio");
            /**     esta variable ya fue asignada durante el request del file
            String archivoDomicilio = request.getParameter("archivoDomicilio");
            * */

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
            domicilio.setPais( new Pais(paisDomicilio) );
            domicilio.setImagencomprobantedom(imagenCompDomicilio);
            domicilio.setDelegacionMunicipio(delegacion);
            
            new DatosDomicilio().insertarDomicilio(domicilio,perfilito,usuario);
        
            
            //Validacion de la completitud de los formularios
                boolean clienteValido = new DatosClienteRaro().getValidarCliente(cliente.getCliente_Id());
                boolean personaFisicaValida = new DatosClienteRaro().getValidarPersonaMoral(cliente.getCliente_Id());
                boolean domocilioValido = new DatosClienteRaro().getValidarDomicilio(cliente.getCliente_Id());
                
                try{
                if (estadoAnterior.equals("R")){
                	
                        Correos correos = new Correos();
//                        String correoCliente= new DatosUsuarioRatos().getcorreosPorValidar(cliente.getCliente_Id());
//                        System.out.println("Correo Para Estatus RRRRR	: " + correo);
                        correos.envioCorreoStatusRaV(cliente.getCliente_Id(), cliente.geteMail());
                }} 
                	catch ( Exception es ){
                		System.out.println("envioCorreoStatusRaV : "+ es.toString());
                        es.printStackTrace();
                }
                
                
                if ( clienteValido && personaFisicaValida && domocilioValido){
                    if (sesion.getAttribute("perfilId").toString().compareTo("6") == 0) { // es cliente hay que hacer la asignaci贸n y mandar correo
                        // V = POR VALIDAR POR EL AREA DE PLD
//                    	boolean coinci=new DatosClienteRaro().obtenerCoincidenciasListas(cliente);
//                        System.out.println("coincidencias "+coinci);
                        String sql = "UPDATE AVCLIENTE SET ESTADO = 'V', usuarioasignado = '" + new ControlUsuario().getUsuarioAsignado() + "' WHERE cliente_id = '" + cliente.getCliente_Id() + "'";
                        new DatosUtilidades().ejecutaInstruccionUpdateSQL(sql);
                        Mensajes.mensaje =  "Le confirmamos que ha ingresado debidamente la informaci贸n solicitada a nuestro portal. \n" ;
                        Mensajes.mensaje += "Se har谩 la revisi贸n pertinente de sus documentos en un lapso de 24 a 48 hrs h谩biles, estaremos en contacto con usted para informarle acerca del estatus.";
                        Correos correos = new Correos();
//                        ClaseCorreoLlamar corre = new ClaseCorreoLlamar();
                        //correos.envioCorreoClienteValido(cliente.getRazonSocial(), cliente.geteMail()); //se le notifica al cliente que su informacion sera validada
                        //Se le notifica al personal de PLD que la informaci贸n debe ser validada
                        String correoDestino = new DatosUsuarioRatos().getcorreosPorValidar(cliente.getCliente_Id()); 
                        correos.envioCorreoClientePorValidar(cliente.getCliente_Id(), correoDestino);
//                          corre.mandarCorreo(cliente.getCliente_Id(), correoDestino);
                    } else {
                        String sql = "UPDATE AVCLIENTE SET ESTADO = 'V' WHERE cliente_id = '" + cliente.getCliente_Id() + "'";
                        new DatosUtilidades().ejecutaInstruccionUpdateSQL(sql);                        
                        Mensajes.mensaje =  "Le confirmamos que ha ingresado debidamente la informaci贸n solicitada a nuestro portal. \n" ;
                        Mensajes.mensaje += "Se har谩 la revisi贸n pertinente de sus documentos en un lapso de 24 a 48 hrs h谩biles, estaremos en contacto con usted para informarle acerca del estatus.";                        
                    }                    
                } else {
                    Mensajes.setMensaje("Se ha guardado correctamente la informaci贸n.\n Favor de llenar la totalidad de los campos para que puedan ser enviados a validaci贸n. \n" );
                    String sql = "UPDATE AVCLIENTE SET ESTADO = 'P' WHERE cliente_id = '" + cliente.getCliente_Id() + "'";
                    new DatosUtilidades().ejecutaInstruccionUpdateSQL(sql);
                }
            
            
        
            try (PrintWriter out = response.getWriter()) {
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
        }//fin del if si es multipar
        }//candado cliente 
    }

    /**
     * Renombra los archivos para que los encuentre
     * 
     * @param original archivo original
     * @param directorio dir donde se guarda el archivo
     * @param cliente cliente
     * @p谩ram extension extension
     * @return nombre del archivo renombrado
     */
     
   
     String Renombra(UploadFile archivoZip, String directorio, String cliente, String zip){
        File rename = new File(Rutas.rutaCarga + Rutas.separador + cliente + Rutas.separador + directorio + Rutas.separador + archivoZip.getFileName());
        File rename_tmp = new File(Rutas.rutaCarga + Rutas.separador + cliente + Rutas.separador + directorio + Rutas.separador + directorio + "_" + Fecha.getFechaHoraSistema() + "_tmp" + zip);
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

     private String Renombra(File archivoZip, String directorio, String cliente, String zip) {
        File rename = new File(Rutas.rutaCarga + Rutas.separador + cliente + Rutas.separador + directorio + Rutas.separador + archivoZip);
        File rename_tmp = new File(Rutas.rutaCarga + Rutas.separador + cliente + Rutas.separador + directorio + Rutas.separador + directorio + "_" + Fecha.getFechaHoraSistema() +"_tmp"+ zip);
        rename.renameTo(rename_tmp);
        return rename_tmp.getName();
    }
     

    
}
