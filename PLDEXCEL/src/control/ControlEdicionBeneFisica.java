/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import datosRatos.DatosBeneficiario;
import datosRatos.DatosDomicilio;
import entidad.BeneFisica;
import entidad.Domicilio;
import entidad.UsuarioTransitivo;
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
@WebServlet(name = "ControlEdicionBeneFisica", urlPatterns = {"/EdicionBeneFisica"})
public class ControlEdicionBeneFisica extends HttpServlet {

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
        
        // RECUPERANDO LOS DATOS DEL BENEFISICAS
        String idCliente = request.getParameter("idCliente");
        String nroBene = request.getParameter("nroBene");
        
        String where = " cliente_id = '" + idCliente + "'";
                where += " and nrobene ='" + nroBene + "'" ;
        BeneFisica bf  = new DatosBeneficiario().getBeneFisica(where);
                
        /** Obteniendo los datos del domicilio **/
//        Domicilio d = new DatosBeneficiario().getBeneDomicilio(where);
        
        try (PrintWriter out = response.getWriter()) {
            
            if ( bf != null){
            //DEL BENEFISICA 
            request.setAttribute("cliente_id",bf.getCliente().getCliente_Id());
            request.setAttribute("nrobene",bf.getNrobene());
            request.setAttribute("nombre",bf.getNombre());
            request.setAttribute("apellidopaterno",bf.getApellidopaterno());
            request.setAttribute("apellidomaterno",bf.getApellidomaterno());
            request.setAttribute("fechanacimiento",bf.getFechanacimiento());
            request.setAttribute("rfc",bf.getRfc());
            request.setAttribute("paisnacim",bf.getPaisnacim().getPais());
            request.setAttribute("actividad_id",bf.getActividad().getActividad_Id());
            request.setAttribute("identifica_id",bf.getIdentifica_id().getIdentifica_id());
            request.setAttribute("identificaciontipo",bf.getIdentificaciontipo());
            request.setAttribute("AutoridadEmiteId",bf.getAutoridadEmiteId());
            request.setAttribute("numeroid",bf.getNumeroid());
            request.setAttribute("paisnacio",bf.getPaisnacio().getPais());
            request.setAttribute("curp",bf.getCurp());
            request.setAttribute("tepais",bf.getTepais().getPais());
            request.setAttribute("telefono",bf.getTelefono());
            request.setAttribute("fecharegistro",bf.getFecharegistro());
            request.setAttribute("ImagenId",bf.getImagenId());
            request.setAttribute("imagencedulafiscal",bf.getImagencedulafiscal());
            request.setAttribute("imagencurp",bf.getImagenCurp());
            request.setAttribute("imagencompdomicilio", bf.getImagenCompDomicilio());
            request.setAttribute("correo", bf.getEmail());
            request.setAttribute("pais",bf.getPais());
            request.setAttribute("estado_prov",bf.getEstado_prov());
            request.setAttribute("ciudad",bf.getCiudad());
            request.setAttribute("colonia",bf.getColonia());
            request.setAttribute("calle",bf.getCalle());
            request.setAttribute("numexterior",bf.getNumexterior());
            request.setAttribute("numinterior",bf.getNuminterior());
            request.setAttribute("codpostal",bf.getCodpostal());
            }
            
            
//            if ( d != null){
//            //DEL DOMICILIO
//            request.setAttribute("cliente_id",d.getCliente().getCliente_Id());
//            request.setAttribute("nrobene",d.getNrobene());
//            request.setAttribute("pais",d.getPais().getPais());
//            request.setAttribute("estado_prov",d.getEstado_prov());
//            request.setAttribute("ciudad",d.getCiudad());
//            request.setAttribute("colonia",d.getColonia());
//            request.setAttribute("calle",d.getCalle());
//            request.setAttribute("numexterior",d.getNumexterior());
//            request.setAttribute("numinterior",d.getNuminterior());
//            request.setAttribute("codpostal",d.getCodpostal());
//            request.setAttribute("fecharegistro",d.getFecharegistro());
//            }
            
            //Enviando la informacion a la pantalla de beneficiario de persona física
            request.setAttribute("esEdicion", "1");
            request.getRequestDispatcher("/beneficiario_personafisica.jsp").forward(request, response);

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
