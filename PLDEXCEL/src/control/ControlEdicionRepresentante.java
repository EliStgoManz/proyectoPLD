/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

/**
 *
 * @author Aldo Ulises
 */
import datosRatos.DatosBeneficiario;
import datosRatos.DatosDomicilio;
import datosRatos.DatosRepLegal;
import entidad.BeneMoral;
import entidad.Domicilio;
import entidad.RepLegal;
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
@WebServlet(name = "ControlEdicionRepresentante", urlPatterns = {"/EdicionRepresentante"})
public class ControlEdicionRepresentante extends HttpServlet {

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
        String nroRep = request.getParameter("nrorep");
        String where = " cliente_id = '" + idCliente + "' ";
               where += " and nrorep ='" + nroRep + "'" ;
        
        RepLegal bm  = new DatosRepLegal().getRepLegal(where);
                
        /** Obteniendo los datos del domicilio **/
        try (PrintWriter out = response.getWriter()) {
            
            if ( bm != null){
            //DEL Representante
            request.setAttribute("cliente_id",bm.getCliente().getCliente_Id());
            request.setAttribute("nrorep",bm.getNrorep());
            request.setAttribute("rlnombre",bm.getRLNombre());
            request.setAttribute("rlapellidopaterno",bm.getRLApellidoPaterno());
            request.setAttribute("rlapellidomaterno",bm.getRLApellidoMaterno());
            request.setAttribute("rlfechanacimiento",bm.getRLFechaNacimiento());
            request.setAttribute("rlrfc",bm.getRLRFC());
            request.setAttribute("rlcurp",bm.getRLCURP());
            request.setAttribute("identifica_id",bm.getIdentifica_id().getIdentifica_id());
            request.setAttribute("rlautoridademiteid",bm.getRLAutoridadEmiteId());
            request.setAttribute("rlnumeroid",bm.getRLNumeroID());
            request.setAttribute("rlidentificaciontipo",bm.getRLIdentificacionTipo());
            request.setAttribute("imagenrlid",bm.getImagenRLID());
            request.setAttribute("imagenrlpodernotarial",bm.getImagenRLPoderNotarial());
            }
            //Enviado los datos a la pagna de salida
            request.setAttribute("esEdicion", "0");
            request.getRequestDispatcher("/alta_representante.jsp").forward(request, response);

            
            
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