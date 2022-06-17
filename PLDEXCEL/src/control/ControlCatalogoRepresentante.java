/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;


import datosRatos.DatosRep_PLD;
import entidad.EstatusUsuario;
import entidad.Perfil;
import entidad.RepLegal_PLD;
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
@WebServlet(name = "ControlCatalogoRepresentante", urlPatterns = {"/UsuariosRepre"})
public class ControlCatalogoRepresentante extends HttpServlet {
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
        RepLegal_PLD us = new RepLegal_PLD();
        //NOMBRE
        us.setNombre( request.getParameter("nombre") );
        //USUARIO
        us.setId_siseg(Integer.parseInt(request.getParameter("usuario")));
        
        us.setEmail(request.getParameter("email"));
        //PEFIL
        String esNuevo = request.getParameter("esEdicion");
//        try{
//            Integer perfilId = Integer.parseInt( request.getParameter("perfilId") );
//            Perfil p = new Perfil();
//            p.setPerfilId(perfilId);
//            us.setPerfilid(p);
//        } catch (Exception es ){
//            Perfil p = new Perfil();
//            p.setPerfilId(null);
//            us.setPerfilid(p);
//        }
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
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
                        
            if ( new DatosRep_PLD().changeUsuarioSistema(us)){
            //Creamos el artibuto resultado en la respuesta, y agregamos el mensaje
                
                    //Lo que pasa si se logro agregar al usuario y y enviar el correo
                    Mensajes.mensaje = "Se ha agregado el representante ";
                    request.setAttribute("resultado", Mensajes.mensaje);
                    request.setAttribute("exito", "1");
                    request.getRequestDispatcher("/rep_pld.jsp").forward(request, response);
             } else  {
                    //Lo que pasa si se logro agregar al usuario y y enviar el correo
                    Mensajes.mensaje = "No ha sido posible agregar el representante Efectivale"; 
                    request.setAttribute("resultado", Mensajes.mensaje);
                    request.setAttribute("exito", "1");
                    request.getRequestDispatcher("/rep_pld.jsp").forward(request, response);
            }
        }//try//try
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

}//fin clase

