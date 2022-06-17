/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import datosRatos.DatosBeneficiario;
import datosRatos.DatosDomicilio;
import entidad.BeneFideicomiso;
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
 * @author Israel Osiris Garcia
 */
@WebServlet(name = "ControlEdicionBeneFideicomiso", urlPatterns = {"/EdicionBeneFideicomiso"})
public class ControlEdicionBeneFideicomiso extends HttpServlet {

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
        
        // RECUPERANDO LOS DATOS DEL BENEFIDEICOMISO
        String idCliente = request.getParameter("idCliente");
        String nroBene = request.getParameter("nroBene");
        
        String where = " cliente_id = '" + idCliente + "'";
                where += " and nrobene ='" + nroBene + "'" ;
        
        BeneFideicomiso bf  = new DatosBeneficiario().getBeneFideicomiso(where);
                
        /** Obteniendo los datos del domicilio **/
        //Domicilio d = new DatosBeneficiario().getBeneDomicilio(where);
        
        try (PrintWriter out = response.getWriter()) {
            
            if ( bf != null){
            //DEL BENEFIDEICOMISO
            request.setAttribute("cliente_id",bf.getCliente().getCliente_Id());
            request.setAttribute("nrobene",bf.getNrobene());
            request.setAttribute("razonsocial",bf.getRazonsocial());
            request.setAttribute("rfc",bf.getRfc());
            request.setAttribute("nrofideicomiso",bf.getNrofideicomiso());
            request.setAttribute("tepais",bf.getTepais());
            request.setAttribute("telefono",bf.getTelefono());
            request.setAttribute("tipodomi",bf.getTipodomi());
            request.setAttribute("fecharegistro",bf.getFecharegistro());
            request.setAttribute("imagencedulafiscal", bf.getImagencedulafiscal());
            request.setAttribute("institucionfiduciaria", bf.getInstitucionFiduciaria());
            request.setAttribute("imagenactaconstitutiva", bf.getImagenactaconstitutiva());
            request.setAttribute("imagenpodernotarial", bf.getImagenpodernotarial());
            request.setAttribute("imagenidrepresentantelegal", bf.getImagenIdRepresentante());
            request.setAttribute("rlnombre", bf.getRlnombre());
            request.setAttribute("rlapellidopaterno", bf.getRlapellidopaterno());
            request.setAttribute("rlapellidomaterno", bf.getRlapellidomaterno());
            request.setAttribute("rlfechanacimiento", bf.getFechanacimiento());
            request.setAttribute("rlrfc", bf.getRlRFC());
            request.setAttribute("rlcurp", bf.getRlCURP());
            request.setAttribute("rlidentifica_id", bf.getIdentifica_id().getIdentifica_id());
            request.setAttribute("rlautoridademiteid", bf.getRlAutoridadEmiteId());
            request.setAttribute("rlnumeroid", bf.getRlNumeroId());
            request.setAttribute("rlidentificaciontipo", bf.getRlIdentificacionTipo());
            request.setAttribute("email", bf.getEmail());
            request.setAttribute("pais",bf.getPais());
            request.setAttribute("estado_prov",bf.getEstado_prov());
            request.setAttribute("ciudad",bf.getCiudad());
            request.setAttribute("colonia",bf.getColonia());
            request.setAttribute("calle",bf.getCalle());
            request.setAttribute("numexterior",bf.getNumexterior());
            request.setAttribute("numinterior",bf.getNuminterior());
            request.setAttribute("codpostal",bf.getCodpostal());
            }

//
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
            
            request.setAttribute("esEdicion", "1");
            request.getRequestDispatcher("/beneficiario_fideicomiso.jsp").forward(request, response);
            
            
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
