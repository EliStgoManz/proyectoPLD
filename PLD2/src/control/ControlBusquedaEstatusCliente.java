/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Israel Osiris Garc�a
 */


//esta notacion @webserlet agregamos el path a donde vamos a ingresar por el navegador
@WebServlet(name = "ControlBusquedaEstatusCliente", urlPatterns = {"/BusquedaEstatusCliente"})
//clase que extiende desde la clase Servlet
public class ControlBusquedaEstatusCliente extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        String nombre = request.getParameter("nombre");
        String rfc = request.getParameter("rfc");
        String idCliente = request.getParameter("idCliente");
        String noCliente = request.getParameter("noCliente");
        String tipoPersona = request.getParameter("tipoPersona");
        String estatus = request.getParameter("estatus");
        String bandera = request.getParameter("bandera");
        String offset = request.getParameter("offset");        
        String move_offset = request.getParameter("move_offset");        
                
        String where = "";
        
        if ( !nombre.isEmpty()){
            where =" v.razonSocial ILIKE('%" + nombre + "%') ";
        }
        
        if ( !rfc.isEmpty() ){
            if ( !where.isEmpty()){
                where += " AND v.rfc ILIKE('%" + rfc + "%') ";
            } else {
                where = "  v.rfc ILIKE('%" + rfc + "%') ";
            }
        }
        
       
        
        if ( !noCliente.isEmpty()){
            if ( !where.isEmpty()){
                where += " AND v.cliente_id =('" + noCliente + "') ";
            } else {
                where = "  v.cliente_id =('" + noCliente + "') ";
            }
        }
        
        
         if ( !idCliente.isEmpty()){
            if ( !where.isEmpty()){
                where += " AND c.cliente_id ILIKE('%" + idCliente + "%') ";
            } else {
                where = "  c.cliente_id ILIKE('%" + idCliente + "%') ";
            }
        }
        
        
         if ( !tipoPersona.isEmpty()){
            if ( !where.isEmpty()){
                where += " AND c.tipopersona LIKE('%" + tipoPersona + "%') ";
            } else {
                where = "  c.tipopersona ILIKE('%" + tipoPersona + "%') ";
            }
        }
        
          if ( !estatus.isEmpty()){
        	  
            if ( !where.isEmpty()){
                where += " AND c.estado LIKE('%" + estatus + "%') ";
            } else {
                where = "  c.estado ILIKE('%" + estatus + "%') ";
            }
        }

        if(bandera.equals("1")){
        	
        	if ( !where.isEmpty()){
                where += " AND c.riesgo LIKE('%" + bandera + "%') ";
            } else {
                where = "  c.riesgo ILIKE('%" + bandera + "%') ";
            }
        }
        if(bandera.equals("0")){
        	
        	if ( !where.isEmpty()){
                where += " AND c.riesgo LIKE('%" + bandera + "%') or c.riesgo is null ";
            } else {
                where = "  c.riesgo ILIKE('%" + bandera + "%') or c.riesgo is null ";
            }
        }
        
        if (offset!=null) {
            if (move_offset.compareTo("0") != 0)
                offset = Integer.toString(Integer.parseInt(offset) + Integer.parseInt(move_offset));
            else
                offset="0";
        }
        request.setAttribute("tipoPersona", tipoPersona);
        request.setAttribute("estatus", estatus);
        request.setAttribute("idCliente", idCliente);
        request.setAttribute("noCliente", noCliente);
        request.setAttribute("rfc", rfc);
        request.setAttribute("nombre", nombre);
        request.setAttribute("offset", offset);
        request.setAttribute("where", where);
        request.setAttribute("exito", "1");
        request.getRequestDispatcher("/estatus_clientes.jsp").forward(request, response);
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
