/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import datosRatos.DatosUsuarioRatos;
import entidad.UsuarioSistema;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utilidades.Mensajes;

/**
 *
 * @author israel.garcia
 */

//SE LANZA DESDE usuarios.jsp Y MENEJA LA TRANSACCI�N DE PRESENTAR AL USUARIO QUE SE EDITAR�
@WebServlet(name = "ControlEdicionUsuario", urlPatterns = {"/EdicionUsuarios"}) 
public class ControlEdicionUsuario extends HttpServlet {

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
//        response.setContentType("text/html;charset=UTF-8");
//        request.setCharacterEncoding ("UTF-8");
//        response.setCharacterEncoding("UTF-8");
        
        
        
        
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            
            String usuario = request.getParameter("usuario");
            String where = " u.usuario ILIKE('%" + usuario + "%')";        
            UsuarioSistema[] usuarios = new DatosUsuarioRatos().getList( where );
                        
            
            if ( usuarios!= null && usuarios.length >0 ){ //si encontro el usuario en la base de datos
                UsuarioSistema u = usuarios[0];
                //usuario
                String nombre = u.getApellido_y_nombres();
                String perfilId = u.getPerfilid().getPerfilId().toString();
                String id_siseg = u.getId_siseg().getId_siseg().toString();
                String correo = u.getCorreo();
                String supervisor = u.getSupervisor().getUsuario();
                
                request.setAttribute("usuario", usuario);
                request.setAttribute("nombre", nombre);
                request.setAttribute("perfilId", perfilId);
                request.setAttribute("correo", correo);
                request.setAttribute("supervisor", supervisor);
                request.setAttribute("id_siseg", id_siseg);
                request.setAttribute("esEdicion", "1");
                request.getRequestDispatcher("/edicion_usuario.jsp").forward(request, response);
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

}
