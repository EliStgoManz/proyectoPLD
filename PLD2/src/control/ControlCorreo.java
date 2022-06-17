/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import correo.Correos;
import datosRatos.DatosConfiguracion;
import datosRatos.DatosUsuarioRatos;
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
 * @author Israel Osiris García
 */
@WebServlet(name = "ControlCorreo", urlPatterns = {"/ControlCorreo"}) //CAMBIO DE CONTRASEÑA O RECUPERACION DE CONTRASEñA


public class ControlCorreo extends HttpServlet {
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
       // response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            //Recuperado la confiracion
            boolean bandera = new DatosConfiguracion().get();
            
            String correo = request.getParameter("correo").trim();
            String usuario = request.getParameter("usuario").trim();
            String tipo = "";
            
            //Validando si existe el correo en la base de datos
            if ( new DatosUsuarioRatos().existeCorreo(correo,usuario)){
                String nuevaContrasena = PasswordGenerator.getPassword(
		PasswordGenerator.MINUSCULAS+
		PasswordGenerator.MAYUSCULAS+
		PasswordGenerator.ESPECIALES,8);
                
                
                
                
                if ( envioCorreo(nuevaContrasena, correo ) ){ //La actualizcion en el sistema para la contraseña
                    DatosUsuarioRatos du = new DatosUsuarioRatos();
                    if ( du.actualizaContrasena(nuevaContrasena, correo, usuario) ) { //DENTRO DEL METODO SE DETERMINA SI ES S O T
                        Mensajes.setMensaje("Se le ha enviado al correo " + correo + " la nueva contraseña");
                        request.setAttribute("resultado", Mensajes.mensaje);
                        request.setAttribute("exito", "1");
                        request.getRequestDispatcher("/login.jsp").forward(request, response);
                    }
                    
                } else {
                    Mensajes.setMensaje("Existe un problema con el envío de su correo, intente de nuevo");
                    request.setAttribute("resultado", Mensajes.mensaje);
                    request.setAttribute("exito", "1");
                    request.getRequestDispatcher("/recuperar_password.jsp").forward(request, response);
                }
                               
                                
            } else {
                //Lo que ocurre si el correo no existe en la base de datos
                //Creamos el artibuto resultado en la respuesta, y agregamos el mensaje
                Mensajes.setMensaje("El correo electrónico no existe, intente de nuevo");
                request.setAttribute("resultado", Mensajes.mensaje);
                request.setAttribute("exito", "1");
                request.getRequestDispatcher("/recuperar_password.jsp").forward(request, response);
            }

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

    
    private boolean envioCorreo(String contrasena,  String correo ){
        /*invocando a las funciones que envian el correo*/        
//        MailSender ms = new MailSender();
        String plantillaHTML = "";
        try {
            plantillaHTML = new Correos().getPlantilla("recuperar_contrasena.html"); //getPlantilla(); //LA PLANTILLA PARA NUEVO USUARIO
        } catch (IOException ex) {
            Logger.getLogger(ControlCorreo.class.getName()).log(Level.SEVERE, null, ex);
        }
        String mensaje="";
        //Reemplazando las variables de la plantilla
        
        
        
                
        mensaje = plantillaHTML.replace(CONTRASENA, contrasena);
        plantillaHTML = mensaje;
        
        mensaje = plantillaHTML.replace(ENLACE, Rutas.rutaWebSistema);
        plantillaHTML = mensaje;
        
        
        
        String asunto = "Recuperación de contraseña";
        boolean result = MailSender.send( correo,"", "" ,
                asunto, true, new StringBuffer(mensaje),true); 
                out.print("Resultado del envío del mensaje : " + result); 
        return result;
                
    }//fin envioCorreo
    
    private String getPlantillax(){
        String plantilla ="";
        File file = null;
        String ruta  = Rutas.rutaPlantillas +  Rutas.separador + "recuperar_contrasena.html";       
        
        
        
            
        try {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(ruta),
                        "UTF8"));
                String sCadena = "";
                try {
                    while ((sCadena = in.readLine())!=null) {
                        plantilla += sCadena;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ControlCorreo.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(ControlCorreo.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ControlCorreo.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
        return plantilla;
    }//fin getPlantilla 
}
