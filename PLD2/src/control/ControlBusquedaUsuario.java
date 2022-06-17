/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Israel Osiris García
 */
@WebServlet(name = "ControlBusquedaUsuario", urlPatterns = {"/BusquedaUsuario"})
public class ControlBusquedaUsuario extends HttpServlet {

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
        
        
        
        
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String nombre = request.getParameter("nombre");
            String usuario = request.getParameter("usuario");
            String perfilId = request.getParameter("perfil");
            String estatusId = request.getParameter("estatus");

            String where ="";

            if ( !nombre.isEmpty() ){
                where = " u.apellido_y_nombres ILIKE('%" + nombre + "%') ";
            }

            if ( !usuario.isEmpty() ){
                if ( !where.isEmpty() ){
                    where += " AND u.usuario ILIKE('%" + usuario + "%')";
                } else {
                    where = " u.usuario ILIKE('%" + usuario + "%')";
                }
            } 

            if ( !perfilId.isEmpty() ){
                if ( !where.isEmpty()) {
                    where += " AND u.perfilId =" + perfilId ;
                } else {
                    where = " u.perfilId = " + perfilId;
                }
            }

            if ( !estatusId.isEmpty() ){
                if ( !where.isEmpty() ) {
                    where += " AND u.estatususuariosid = " + estatusId;
                } else {
                    where = " u.estatususuariosid = " + estatusId;
                }
            }



            //request.setAttribute("where", Mensajes.mensaje);
            request.setAttribute("where", where);
            request.setAttribute("exito", "1");
            request.getRequestDispatcher("/usuarios.jsp").forward(request, response);
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
