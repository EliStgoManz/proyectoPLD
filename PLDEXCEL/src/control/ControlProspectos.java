/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;


import correo.Correos;
import datosRatos.DatosProspecto;
import datosRatos.DatosUsuarioRatos;
import datosRatos.DatosUtilidades;
import entidad.Cliente;
import entidad.UsuarioSistema;
import entidad.UsuarioTransitivo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import static java.lang.System.out;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utilidades.Mensajes;
import utilidades.Rutas;

/**
 *
 * @author Israel Osiris García
 */
@WebServlet(name = "ControlProspectos", urlPatterns = {"/registroProspecto"})
public class ControlProspectos extends HttpServlet {
    
    /**
     * DEFINICION DE CONSTANTES PARA PLANTILLA
     */
    
    private final String NOMBRE_USAUARIO ="##NOMBREUSUARIO" ;
    private final String USUARIO = "##USUARIO";
    private final String CONTRASENA = "##CONTRASENA";
    private final String ENLACE = "##ENLACE";
    boolean hayPlantilla;
    String resultadoEnvio;
    String laRuta  = "";
    
    String partePlantilla;
    
    
    
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        //Recuperando la información del formulaio
        Cliente cliente = new Cliente();
        cliente.setCliente_Id( request.getParameter("usuario"));
        UsuarioTransitivo.setCliente(cliente);
        UsuarioTransitivo.setRazonsocial( request.getParameter("nombre") );
        UsuarioTransitivo.setEmail( request.getParameter("correo") );
        UsuarioTransitivo.setRfc( request.getParameter("rfc") );
 
        
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            boolean existeRFC = new DatosUsuarioRatos().existeRFC();
            //boolean existeCorreo= new DatosUsuarioRatos().existeCorreoProspectos(UsuarioTransitivo.getEmail());
            boolean existeCliente = new DatosUsuarioRatos().existeClienteProspectos( cliente.getCliente_Id() );
            
            request.setAttribute("usuario", cliente.getCliente_Id());
            request.setAttribute("nombre", UsuarioTransitivo.getRazonsocial());
            request.setAttribute("correo", UsuarioTransitivo.getEmail());
            request.setAttribute("rfc", UsuarioTransitivo.getRfc());
            
            
                if (existeRFC){
                Mensajes.mensaje = "El RFC ya existe en la base de datos";                         
                request.setAttribute("resultado", Mensajes.mensaje);
                request.setAttribute("exito", "1");
                request.getRequestDispatcher("/prospectos.jsp").forward(request, response);
                System.out.println("YA EXISTE EL RFC");
            /*} else if ( existeCorreo ){
                Mensajes.mensaje = "El correo ya existe en la base de datos";                         
                request.setAttribute("resultado", Mensajes.mensaje);
                request.setAttribute("exito", "1");
                request.getRequestDispatcher("/prospectos.jsp").forward(request, response);
                System.out.println("YA CORREO YA EXISTE EN LA BASE DE DATOS ");*/
            } else if ( existeCliente ){
                Mensajes.mensaje = "El IdSalesforce ya existe en la base de datos";                         
                request.setAttribute("resultado", Mensajes.mensaje);
                request.setAttribute("exito", "1");
                request.getRequestDispatcher("/prospectos.jsp").forward(request, response);
                System.out.println("YA CORREO YA EXISTE EN LA BASE DE DATOS ");
            } else {
                if ( insertar(request) ){
                        String resultado ="";
                        
                    
                        boolean bandera = envioCorreo(UsuarioTransitivo.getRfc(), cliente.getCliente_Id(), UsuarioTransitivo.getRazonsocial(), UsuarioTransitivo.getEmail());
                        //Mensajes.mensaje = "Se ha agregado al prospecto existosamente. \n" + laRuta + "\n" + resultadoEnvio + "\n PARTE DE LA PLANTILLA: " + partePlantilla;
                        if ( bandera ) {
                            Mensajes.mensaje = "Se ha agregado al prospecto existosamente.";
                        } else {
                            Mensajes.mensaje = "Se ha agregado al prospecto existosamente.\n No ha sido posible enviar el correo electrónico.";
                        }
                        

                        //Quitando los datos del request
                        request.setAttribute("usuario", "");
                        request.setAttribute("nombre", "");
                        request.setAttribute("correo", "");
                        request.setAttribute("rfc", "");

                        //Creamos el artibuto resultado en la respuesta, y agregamos el mensaje
                        request.setAttribute("resultado", Mensajes.mensaje);
                        request.setAttribute("exito", "1");
                        request.setAttribute("esEdicion", "0");
                        request.getRequestDispatcher("/prospectos.jsp").forward(request, response);
                        
                        
                        
                        
                } else {
                    Mensajes.mensaje = "No se ha registrado el prospecto, puede deberse a un problema con la base de datos.";                         
                    request.setAttribute("exito", "1");
                    
                    request.getRequestDispatcher("/prospectos.jsp").forward(request, response);
                }//fin if insertar 
            }
            
            /*
            if ( ! new DatosUsuarioRatos().existeRFC()){
                if ( insertar() ){
                        boolean bandera = envioCorreo(UsuarioTransitivo.getRfc(), cliente.getCliente_Id(), UsuarioTransitivo.getRazonsocial(), UsuarioTransitivo.getEmail());
                        Mensajes.mensaje = "Se ha agregado al prospecto existosamente.";                         
                        //Creamos el artibuto resultado en la respuesta, y agregamos el mensaje
                        request.setAttribute("resultado", Mensajes.mensaje);
                        request.setAttribute("exito", "1");
                        request.setAttribute("esEdicion", "0");
                        request.getRequestDispatcher("/prospectos.jsp").forward(request, response);
                } else {
                    Mensajes.mensaje = "No se ha registrado el prospecto";                         
                    request.setAttribute("exito", "1");
                    request.getRequestDispatcher("/prospectos.jsp").forward(request, response);
                }//fin if insertar 
            } else { // lo que ocurre si el RFc YA EXISTE
                Mensajes.mensaje = "El RFC ya existe en la base de datos";                         
                request.setAttribute("resultado", Mensajes.mensaje);
                request.setAttribute("exito", "1");
                request.getRequestDispatcher("/prospectos.jsp").forward(request, response);
                System.out.println("YA EXISTE EL RFC");
            }*/
           
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
    
    
    
    private boolean insertar (HttpServletRequest request){
        boolean resultado = false;
        UsuarioSistema us = null;
        try{
            HttpSession sesion = request.getSession();
            us = (UsuarioSistema) sesion.getAttribute("usuario");
            //us = (UsuarioSistema) sesion.getAttribute("numero_interno");
        } catch ( Exception es ){
            us = new UsuarioSistema();
            System.out.println("ControlProspectos.java HttpSession "+es.toString());
            
            es.printStackTrace();
        }
        
        
        
           try{
               String usuarioEjecutivo = "";
               
//               if ( us.getUsuario() != null && !us.getUsuario().isEmpty()){
//                   usuarioEjecutivo = us.getUsuario();
//               }

                if ( us.getNumero_interno()!= null && !us.getNumero_interno().isEmpty()){
                   usuarioEjecutivo = us.getNumero_interno();
                }
               
               DatosProspecto datosProspecto = new DatosProspecto();
               resultado = datosProspecto.agregarProspecto(usuarioEjecutivo);
               


                //Haciendo el preregistro para generar Estado No INICIADO 
               String sql = "INSERT INTO avcliente(" +
                            "	cliente_id, " +
                            "	tipopersona," +
                            "	fecharegistro," +
                            "	tipodomicilio," +
                            "	telefono," +
                            "	pais," +
                            "	email," +
                            "	estado, " +
                            "	razonsocial," +
                            "	validado," +
                            "	fechavalidado," +
                            "	declarobeneficiario," +
                            "	declaroorigen," +
                            "	usuariovalido," +
                            "	fechacorte," +
                            "	mensaje," +
                            "	usuarioasignado," +
                            "	bloqueado," +
                            "	fechabloqueo," +
                            "	borrado " +
                            "   )";
                    sql +=" VALUES  ('" + UsuarioTransitivo.getCliente().getCliente_Id() + "',";
                    sql += " '', "; // tipo persona
                    sql += " now(),";
                    sql += " '', ";
                    sql += " '', ";
                    sql += " 'MX', ";
                    sql += " '" + UsuarioTransitivo.getEmail() + "', ";
                    sql += " 'S', ";    // S = NO INICIADO
                    sql += "'" + UsuarioTransitivo.getRazonsocial() + "',";
                    sql += " 0, ";
                    sql += "null, ";
                    sql += " 0, ";
                    sql += " 0, ";
                    sql += "null, ";
                    sql += "null, ";
                    sql += "'', ";
                    sql += "null, "; //usaurio asignado
                    sql += "false,";
                    sql += "null,";
                    sql += "false)";
                resultado = new DatosUtilidades().ejecutaInstruccionUpdateSQL(sql);
               
               
               
           } catch(Exception es){
        	   System.out.println("ControlProspectos.java agregarProspecto "+es.toString());
               
               es.printStackTrace();
           }
        return resultado;
                
    }
    
    private boolean envioCorreo(String usuario, String contrasena, String nombre, String correo ){
        /*invocando a las funciones que envian el correo*/
//        MailSender ms = new MailSender();
        String ruta = Rutas.rutaPlantillas;
        String plantillaHTML = "";
        try {
            plantillaHTML = new Correos().getPlantilla("nuevo_usuario_interno.html"); //LA PLANTILLA PARA NUEVO USUARIO
            partePlantilla = plantillaHTML.substring(1, 100);
        } catch (Exception ex) {
        	System.out.println("ControlProspectos.java getPlantilla "+ex.toString());
             }
        String mensaje="";
        boolean result= false;
        //Reemplazando las variables de la plantilla
        mensaje = plantillaHTML.replace(NOMBRE_USAUARIO, nombre);
        plantillaHTML = mensaje;
        
        mensaje = plantillaHTML.replace(USUARIO, usuario);
        plantillaHTML = mensaje;
        
        mensaje = plantillaHTML.replace(CONTRASENA, contrasena);
        plantillaHTML = mensaje;
        
        mensaje = plantillaHTML.replace(ENLACE, Rutas.rutaWebSistema);
        
               
        String asunto = "Bienvenido a Efectivale";
        
        if ( !mensaje.isEmpty()){
                result = MailSender.send(correo,"", "" ,
                asunto, true, new StringBuffer(mensaje),true); 
                resultadoEnvio = "Resultado del envío del mensaje : " + result;
                out.print("Resultado del envío del mensaje : " + result); 
        }
        return result;
                
    }
    
    
    

}
