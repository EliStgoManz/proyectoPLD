/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;


import datosRatos.DatosConfiguracion;
import datosRatos.DatosCrearLog;
import datosRatos.DatosUsuarioRatos;
import entidad.EstatusUsuario;
import entidad.UsuarioSistema;
import entidad.UsuarioTransitivo;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utilidades.Archivos;
import utilidades.Mensajes;
import utilidades.Rutas;

/**
 *
 * @author Israel Osiris GarcÃ­a
 */
public class ControlLogin extends HttpServlet {

    
    String RFCIngresado;
    String clave_de_acceso_ingresada;
    HttpSession sesion;
    boolean errorBaseDeDatos = true;
    boolean estaActivo = false;
    private int ESTATUS_ACTIVO = 1;
   
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
        
        boolean usaurioValido;
        boolean hayconfiguracion = false;
        
       // response.setContentType("text/html;charset=UTF-8");
        //recuperacion de la informaciÃ³n del formulario        
        String usuario = "";
        usuario = request.getParameter("usuario");
        String pass ="";
        pass = request.getParameter("contrasena");
        
        //Obteniendo la sesion
        sesion = request.getSession();
        
        try{    
            if ( new DatosConfiguracion().get() ){
                //lo que pasa si el sistema tiene configuracion
                hayconfiguracion = true;
                try{
        			DatosCrearLog D=new DatosCrearLog();
        			D.Delete(Rutas.rutaCarga);
        			System.out.println("Entro al log delete");
        			    
        		}catch(Exception e){
        			System.out.println("Error en delete log "+ e);
        		}

                if ( validarUsuario(usuario, pass, response)){

                    String tipo = (String) sesion.getAttribute("tipo");
                    switch ( tipo ){
                        case "T":
                                UsuarioTransitivo ut = (UsuarioTransitivo) sesion.getAttribute("usuario");
                                if ( ut.isPrimeraVez() ){
                                    response.sendRedirect("contrasena.jsp");
                                    break;
                                } else {
                                    request.setAttribute("rfc", usuario);
                                    request.setAttribute("esEdicion", "1");
                                    request.getRequestDispatcher("/tipo_persona.jsp?idCliente=" + ut.getCliente().getCliente_Id() + "&rfc=" + usuario.trim().trim().replace("&", "%26")).forward(request, response);
                                    break;
                                }
                        case "S":
                                UsuarioSistema us = ( UsuarioSistema ) sesion.getAttribute("usuario");
                                if ( us.isPrimeraVez()){
                                    response.sendRedirect("contrasena.jsp");
                                    break;
                                } else {
                                    String supervisor = us.getSupervisor().getUsuario();
                                    if ( supervisor == null){
                                        supervisor ="";
                                    }
                                    
                                    request.setAttribute("supervisorId", supervisor );
                                    response.sendRedirect("estatus_clientes.jsp");
                                    break;
                                }

                    }


                } else { //si no es valida la autenticacion

                        if ( !errorBaseDeDatos){
                            Mensajes.setMensaje("El usuario o contraseña no son correctos.");
                            request.setAttribute("resultado", Mensajes.mensaje);
                            request.setAttribute("exito", "1");
                            request.getRequestDispatcher("/login.jsp").forward(request, response);
                        } else{

                            //Creamos el artibuto resultado en la respuesta, y agregamos el mensaje
                            if (  estaActivo ){
                                Mensajes.setMensaje("El usuario o contraseña no son correctos.");
                            } else {
                                Mensajes.setMensaje("El usuario no esta activo.");
                            }
                            
                            request.setAttribute("resultado", Mensajes.mensaje);
                            request.setAttribute("exito", "1");
                            request.getRequestDispatcher("/login.jsp").forward(request, response);
                        }// if error base de datos   
                }//fin else

            } else { 
                //lo que pasa si el correo no tiene configuracion

                    try (PrintWriter out = response.getWriter()) {
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>Error de configuracion</title>");            
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<h1>Error de configuración:<h1>");
                        out.println("<p>Uno o varios parámetros de configuración faltan. </p>");
                        out.println("<p>Verifique la tabla de configuración. </p>");
                        out.println("</body>");
                        out.println("</html>");
                    }
            }//fin de configuracion
        } catch ( Exception es ){
            try (PrintWriter out = response.getWriter()) {
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Error de configuración</title>");            
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>Error de configuracion:<h1>");
                    out.println("<p>Error de conexión, puede deberse a la red o a la configuracion de base de datos.</p>");
                    out.println("</body>");
                    out.println("</html>");
                }
        }
        
        if ( hayconfiguracion ){
            //Archivos.deleteSubDirectorys( new File(Rutas.rutaDescarga), new File ( Rutas.rutaDescarga) ) ;
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

    private boolean validarUsuario(String usuario, String pass, HttpServletResponse response) throws IOException{
        boolean valido = false;
        
        DatosUsuarioRatos datosUsuario = new DatosUsuarioRatos();
        try{
        if ( datosUsuario.getDatosUsuarioExterno( usuario, sesion )){ //validando que exista el USUARIO EXTERNO
            //COMPARACION DE SI LA CONTRASEÃ‘A INGRESADA ES CORRECTA
            if ( sesion.getAttribute("usuario") instanceof UsuarioTransitivo ){
                UsuarioTransitivo ut = (UsuarioTransitivo) sesion.getAttribute("usuario");
                if ( ut.getClave_de_acceso().equals(pass) ){
                    valido = true; 
                }
            } 
        } else if ( datosUsuario.getDatosUsuarioInterno(usuario, sesion) ) {
            UsuarioSistema us = (UsuarioSistema) sesion.getAttribute("usuario");
            EstatusUsuario e = us.getEstatus();
            
            if ( us.getClave_de_acceso().equals(pass) && e.getEstatusUsuarioId() == ESTATUS_ACTIVO){
                valido = true;
            }
            
            if ( e.getEstatusUsuarioId() == ESTATUS_ACTIVO ){
                estaActivo = true;
            }
        }
        return valido; 
        } catch ( Exception  es ){
            try (PrintWriter out = response.getWriter()) {
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Error de configuracion</title>");            
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>Error de configuracion:<h1>");
                    out.println("<p>Error de conexiÃ³n, puede deberse a la red o a la configuracion de base de datos.</p>");
                    out.println("</body>");
                    out.println("</html>");
                }
             return errorBaseDeDatos = false;
             
        }
            
    }
    
    
    
}// fin de la clase
