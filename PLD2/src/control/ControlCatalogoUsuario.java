/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import correo.Correos;
import datosRatos.DatosUsuarioRatos;
import entidad.EstatusUsuario;
import entidad.Perfil;
import entidad.RepresentantePLD;
import entidad.Supervisor;
import entidad.UsuarioSistema;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utilidades.Mensajes;
import utilidades.PasswordGenerator;
import utilidades.Rutas;

/**
 *
 * @author israel.garcia
 */
@WebServlet(name = "ControlCatalogoUsuario", urlPatterns = {"/UsuariosSistema"})
public class ControlCatalogoUsuario extends HttpServlet {
       /**
     * DEFINICION DE CONSTANTES PARA PLANTILLA
     */
    
    private final String NOMBRE_USAUARIO ="##NOMBREUSUARIO" ;
    private final String USUARIO = "##USUARIO";
    private final String CONTRASENA = "##CONTRASENA";
    private final String ENLACE = "##ENLACE";

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
        
        /** 
         * RECUPERANDO Y ALMACENANDO LA INFORMACION DEL USUARIO SISTEMA
         */
        UsuarioSistema us = new UsuarioSistema();
        //NOMBRE
        us.setApellido_y_nombres( request.getParameter("nombre") );
        //USUARIO
        us.setUsuario( request.getParameter("usuario"));
        //PEFIL
        String esNuevo = request.getParameter("esEdicion");
        System.out.println("Edicion : " +esNuevo);
        try{
            Integer perfilId = Integer.parseInt( request.getParameter("perfilId") );
            Perfil p = new Perfil();
            p.setPerfilId(perfilId);
            us.setPerfilid(p);
        } catch (Exception es ){
            Perfil p = new Perfil();
            p.setPerfilId(null);
            us.setPerfilid(p);
        }
      //Representante
//        String esNuevo = request.getParameter("esEdicion");
        try{
            Integer id_sisegId = Integer.parseInt( request.getParameter("id_sisegId") );
            RepresentantePLD r = new RepresentantePLD();
            r.setId_siseg(id_sisegId);
            us.setId_siseg(r);
        } catch (Exception es ){
            RepresentantePLD r = new RepresentantePLD();
            r.setId_siseg(null);
            us.setId_siseg(r);
        }
        //ESTATUS
        try{
            Integer estatusId = Integer.parseInt( request.getParameter("estatus") );
            EstatusUsuario es = new EstatusUsuario();
            es.setEstatusUsuarioId(estatusId);
            us.setEstatus(es);
        } catch ( Exception es ){
            EstatusUsuario est = new EstatusUsuario();
            est.setEstatusUsuarioId(null);
            us.setEstatus(est);
            
        }
        //SUPERVISOR
        Supervisor s = new Supervisor();
        s.setUsuario( request.getParameter("supervisor"));
        us.setSupervisor(s);
        //Correo
        us.setCorreo( request.getParameter("correo")) ;
        
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String nuevaContrasena = PasswordGenerator.getPassword(
		PasswordGenerator.MINUSCULAS+
		PasswordGenerator.MAYUSCULAS+
		PasswordGenerator.ESPECIALES,8);
            
            if ( new DatosUsuarioRatos().changeUsuarioSistema(us, nuevaContrasena)){
            	
            //Creamos el artibuto resultado en la respuesta, y agregamos el mensaje
                if (esNuevo.compareTo("1") != 0) {
                    
                
                if ( envioCorreo( us.getUsuario(), nuevaContrasena, us.getApellido_y_nombres(), us.getCorreo() )  ){
                    //Lo que pasa si se logro agregar al usuario y y enviar el correo
                    Mensajes.mensaje = "Se ha agregado el usuario " + request.getParameter("usuario");
                    request.setAttribute("resultado", Mensajes.mensaje);
                    request.setAttribute("exito", "1");
                    request.getRequestDispatcher("/usuarios.jsp").forward(request, response);
                } else {
                    //Lo que pasa si se logro agregar al usuario pero no se mando el correo
                    Mensajes.mensaje = "Se ha agregado el usuario " + request.getParameter("usuario") + " \n No fue posible enviar el correo.";
                    request.setAttribute("resultado", Mensajes.mensaje);
                    request.setAttribute("exito", "1");
                    request.getRequestDispatcher("/usuarios.jsp").forward(request, response);
                }
                } else {
                    Mensajes.mensaje = "Se ha modificado correctamente el usuario " + request.getParameter("usuario") + "";
                    request.setAttribute("resultado", Mensajes.mensaje);
                    request.setAttribute("exito", "1");
                    request.getRequestDispatcher("/usuarios.jsp").forward(request, response);
                }
            } else {
                    //Lo que pasa si se logro agregar al usuario y y enviar el correo
                    Mensajes.mensaje = "No ha sido posible agregar el usuario"; 
                    request.setAttribute("resultado", Mensajes.mensaje);
                    request.setAttribute("exito", "1");
                    request.getRequestDispatcher("/usuarios.jsp").forward(request, response);
            }
        }//try//try
    }
    
    private boolean envioCorreo(String usuario, String contrasena, String nombre, String correo ){
        /*invocando a las funciones que envian el correo*/
//        MailSender ms = new MailSender();
//        String ruta = getClass().getResource("/Plantillas/nuevo_usuario_interno.html").toString();
        String plantillaHTML ="";
        try {
            plantillaHTML = new Correos().getPlantilla("nuevo_usuario_interno.html"); //getPlantilla(); //LA PLANTILLA PARA NUEVO USUARIO
        } catch (IOException ex) {
            Logger.getLogger(ControlCatalogoUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        String mensaje="";
        //Reemplazando las variables de la plantilla
        mensaje = plantillaHTML.replace(NOMBRE_USAUARIO, nombre);
        plantillaHTML = mensaje;
        
        mensaje = plantillaHTML.replace(USUARIO, usuario);
        plantillaHTML = mensaje;
        
        mensaje = plantillaHTML.replace(CONTRASENA, contrasena);
        plantillaHTML = mensaje;
        
        mensaje = plantillaHTML.replace(ENLACE, Rutas.rutaWebSistema);
               
        String asunto = "Bienvenido a Efectivale";
        boolean result = MailSender.send(correo,"", "" ,
                asunto, true, new StringBuffer(mensaje),true); 
                out.print("Resultado del envío del mensaje : " + result); 
        return result;
                
    }
    
    private String getPlantillax(){
        String plantilla ="";
        File file = null;
        String resource = "/Plantillas/nuevo_usuario_interno.html";
        URL res = getClass().getResource(resource);
        file = new File ( res.getPath() );
        
        String cadena;
        FileReader f;
        try {
            f = new FileReader( file );
            BufferedReader b = new BufferedReader(f);
            while((cadena = b.readLine())!=null) {
                plantilla += cadena;
            }   
            b.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ControlProspectos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ControlProspectos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        return plantilla;
    }//fin getPlantilla

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

}//fin clase
