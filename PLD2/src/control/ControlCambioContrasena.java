/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import datosRatos.DatosUsuarioRatos;
import entidad.UsuarioSistema;
import entidad.UsuarioTransitivo;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utilidades.Mensajes;

/**
 *
 * @author israel.garcia
 */
@WebServlet(name = "ControlCambioContrasena", urlPatterns = {"/CambioContrasena"})
public class ControlCambioContrasena extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    String mensaje;
    HttpSession sesion;
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            //response.setContentType("text/html;charset=UTF-8");
        
        String claveactual = request.getParameter("cveactual");
        String nuevaClave = request.getParameter("cvenueva");
        String nuevaClave2 = request.getParameter("cvenueva2");
        sesion = request.getSession();
        String tipo = (String) sesion.getAttribute("tipo");
        String usuario = (String) sesion.getAttribute("id");
        
        
        
        
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            boolean resultado = actualizarContrasena(nuevaClave, tipo, usuario);
            if ( resultado ){
                
                //Con el siguiente se actualiza la contrase�a dentro de las varibles del sistema
                switch ( tipo ){
                    case "T": 
                        UsuarioTransitivo ut = new UsuarioTransitivo();
                        ut.setClave_de_acceso(nuevaClave);
                        
                        sesion.setAttribute("usuario", ut );
                        sesion.setAttribute("pass", nuevaClave);
                        Mensajes.mensaje = "La contraseña se ha cambiado exitosamente";
                        
                        //Creamos el artibuto resultado en la respuesta, y agregamos el mensaje
                        request.setAttribute("resultado", Mensajes.mensaje);
                        request.setAttribute("exito", "1");
                        request.setAttribute("esEdicion", "1");
                        request.setAttribute("rfc", ut.getRfc());
                        request.getRequestDispatcher("/tipo_persona.jsp?idCliente=" + request.getParameter("Cliente_Id").trim() + "&rfc=" + usuario.trim().replace("&", "%26")).forward(request, response);
                        break;
                    case "S":
                        //UsuarioSistema us = new UsuarioSistema();
                        //sesion.setAttribute("usuario", us);
                        sesion.setAttribute("pass", nuevaClave);
                        Mensajes.mensaje = "La contraseña se ha cambiado exitosamente";
                        
                        //Creamos el artibuto resultado en la respuesta, y agregamos el mensaje
                        request.setAttribute("resultado", Mensajes.mensaje);
                        request.setAttribute("exito", "1");
                        request.getRequestDispatcher("/estatus_clientes.jsp").forward(request, response);
                        break;
                }                
                
                Mensajes.mensaje = "La contraseña se ha cambiado exisotamente";                         


                
                
                
                
                
                
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

    private boolean actualizarContrasena(String nuevaClave, String tipo, String usuario){
        boolean resultado = false;
        UsuarioTransitivo.getRfc();
        
        try{
            resultado = new DatosUsuarioRatos().cambiaContrasena(nuevaClave, tipo, usuario);
        } catch( Exception es)
        {
            es.printStackTrace();
        }        
        return resultado;
                
    }
    
}
