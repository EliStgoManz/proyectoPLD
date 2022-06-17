/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import datosRatos.DatosBeneficiario;
import datosRatos.DatosDomicilio;
import entidad.BeneMoral;
import entidad.Domicilio;
import entidad.UsuarioTransitivo;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Israel Osiris Garcia
 */
@WebServlet(name = "ControlEdicionBeneMoral", urlPatterns = {"/EdicionBeneMoral"})
public class ControlEdicionBeneMoral extends HttpServlet {

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
        String where = " cliente_id = '" + idCliente + "' ";
               where += " and nrobene ='" + nroBene + "'" ;
        
        BeneMoral bm  = new DatosBeneficiario().getBeneMoral(where);
                
        /** Obteniendo los datos del domicilio **/
      //  Domicilio d = new DatosBeneficiario().getBeneDomicilio(where);
        
        try (PrintWriter out = response.getWriter()) {
            
            if ( bm != null){
            //DEL BENEMORAL
            request.setAttribute("cliente_id",bm.getCliente().getCliente_Id());
            request.setAttribute("nrobene",bm.getNrobene());
            request.setAttribute("razonsocial",bm.getRazonsocial());
            request.setAttribute("rfc",bm.getRfc());
            request.setAttribute("pais",bm.getPais().getPais());
            request.setAttribute("fechaconstitucion",bm.getFechaconstitucion());
            request.setAttribute("tepais",bm.getTepais().getPais());
            request.setAttribute("telefono",bm.getTelefono());
            request.setAttribute("tipodomi",bm.getTipodomi());
            request.setAttribute("giro_id",bm.getGiro().getGiro_id());
            request.setAttribute("fecharegistro",bm.getFecharegistro());
            request.setAttribute("imagencedulafiscal",bm.getImagencedulafiscal());
            request.setAttribute("imagenactaconstituttiva",bm.getImagenactaconstitutiva());
            request.setAttribute("imagendomicilio",bm.getImagencompdomicilio());  
            request.setAttribute("imagenpodernotarial",bm.getImagenpodernotarial());
            request.setAttribute("imagenrepresentantelegal",bm.getImagenidrepresentantelegal());
            request.setAttribute("correo",bm.getEmail());
            request.setAttribute("rlnombre",bm.getRlNombre());
            request.setAttribute("rlapellidopaterno",bm.getRlApellidoPaterno());
            request.setAttribute("rlapellidomaterno",bm.getRlApellidoMaterno());
            request.setAttribute("rlfechanacimiento",bm.getRLFechaNacimiento());
            request.setAttribute("rlrfc",bm.getRLRFC());
            request.setAttribute("rlcurp",bm.getRLCURP());
            request.setAttribute("identifica_id",bm.getIdentifica_id().getIdentifica_id());
            request.setAttribute("rlautoridademiteid",bm.getRLAutoridadEmiteId());
            request.setAttribute("rlnumeroid",bm.getRLNumeroID());
            request.setAttribute("rlidentificaciontipo",bm.getRLIdentificacionTipo());
            request.setAttribute("rlpais",bm.getRlPais());
            request.setAttribute("estado_prov",bm.getEstado_prov());
            request.setAttribute("ciudad",bm.getCiudad());
            request.setAttribute("colonia",bm.getColonia());
            request.setAttribute("calle",bm.getCalle());
            request.setAttribute("numexterior",bm.getNumexterior());
            request.setAttribute("numinterior",bm.getNuminterior());
            request.setAttribute("codpostal",bm.getCodpostal());
            }
                       
//
//
//            if ( d != null){
//            //DEL DOMICILIO
//            request.setAttribute("cliente_id",d.getCliente().getCliente_Id());
//            request.setAttribute("nrobene",d.getNrobene());
//            request.setAttribute("paisDomicilio",d.getPais().getPais());
//            request.setAttribute("estado_prov",d.getEstado_prov());
//            request.setAttribute("ciudad",d.getCiudad());
//            request.setAttribute("colonia",d.getColonia());
//            request.setAttribute("calle",d.getCalle());
//            request.setAttribute("numexterior",d.getNumexterior());
//            request.setAttribute("numinterior",d.getNuminterior());
//            request.setAttribute("codpostal",d.getCodpostal());
//            request.setAttribute("fecharegistro",d.getFecharegistro());
//            }
//            //Enviado los datos a la pagna de salida
            request.setAttribute("esEdicion", "1");
            request.getRequestDispatcher("/beneficiario_personamoral.jsp").forward(request, response);

            
            
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
